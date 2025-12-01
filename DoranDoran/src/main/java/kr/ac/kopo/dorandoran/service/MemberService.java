package kr.ac.kopo.dorandoran.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import kr.ac.kopo.dorandoran.model.Member;

public interface MemberService {

	List<Member> list();

	void add(Member item);

	void update(Member item);

	void delete(String memberId);

	Member item(String memberId);

	boolean login(Member item);

	void signup(Member item);

	Member memberItem(String ownerId);

	String getPassword(String memberId);

	void updatePassword(String memberId, String updatePassword);

	Map<String, String> validateHandling(Errors errors);

	boolean Duplicate(String type, String value);

}
