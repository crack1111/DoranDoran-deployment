package kr.ac.kopo.dorandoran.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import kr.ac.kopo.dorandoran.dao.MemberDao;
import kr.ac.kopo.dorandoran.model.Member;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao dao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	KakaoGeoService kakaoGeoService;

	@Override
	public List<Member> list() {
		return dao.list();
	}

	@Override
	public void add(Member item) {
		dao.add(item);
	}

	@Override
	@Transactional
	public void update(Member item) {
		Member existing = dao.item(item.getMemberId());

		if (!existing.getMemberAddress().equals(item.getMemberAddress())) {
			double[] coords = kakaoGeoService.getCoordinates(item.getMemberAddress());
			if (coords == null) {
				throw new RuntimeException("주소를 좌표로 변환할 수 없습니다.");
			}
			System.out.println("변환된 좌표: " + coords[0] + ", " + coords[1]);
			item.setMemberLatitude(coords[0]);
			item.setMemberLongitude(coords[1]);
		} else {
			item.setMemberLatitude(existing.getMemberLatitude());
			item.setMemberLongitude(existing.getMemberLongitude());
		}

		dao.update(item);
	}

	@Override
	public void delete(String memberId) {
		dao.delete(memberId);
	}

	@Cacheable(value = "member", key = "#memberId")
	@Override
	public Member item(String memberId) {
		return dao.item(memberId);
	}

	@Override
	public boolean login(Member item) {
		Member member = dao.item(item.getMemberId());

		if (member != null) {
			// 비밀번호 비교 (암호화된 비밀번호와 입력한 비밀번호 비교)
			if (passwordEncoder.matches(item.getMemberPassword(), member.getMemberPassword())) {
				BeanUtils.copyProperties(member, item);
				item.setMemberPassword(null); // 비밀번호는 사용자 객체에서 제외

				return true;
			}
		}
		return false;
	}

	@Override
	public void signup(Member item) {

		item.setMemberPassword(passwordEncoder.encode(item.getMemberPassword()));

		LocalDateTime now = LocalDateTime.now();

		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		item.setSignupDate(date);

		double[] coords = kakaoGeoService.getCoordinates(item.getMemberAddress());

		if (coords == null) {
			throw new RuntimeException("주소를 좌표로 변환할 수 없습니다.");
		}

		item.setMemberLatitude(coords[0]);
		item.setMemberLongitude(coords[1]);

		dao.add(item);
	}

	@Override
	public Member memberItem(String ownerId) {
		return dao.memberItem(ownerId);
	}

	@Override
	public String getPassword(String memberId) {
		return dao.getPassword(memberId);
	}

	@Override
	public void updatePassword(String memberId, String updatePassword) {
		String encodedPassword = passwordEncoder.encode(updatePassword);

		dao.updatePassword(memberId, encodedPassword);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();
		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}

	@Override
	public boolean Duplicate(String type, String value) {
	    boolean exists = false;

	    switch (type.toLowerCase()) {
	        case "id":
	            exists = dao.existsMemberId(value);
	            break;
	        case "nickname":
	            exists = dao.existsNickname(value);
	            break;
	        case "email":
	            exists = dao.existsEmail(value);
	            break;
	        default:
	            throw new IllegalArgumentException("유효하지 않은 타입입니다: " + type);
	    }
	    return exists;
	}

}
