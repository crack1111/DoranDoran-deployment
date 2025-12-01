package kr.ac.kopo.dorandoran.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.service.MemberService;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MemberService service;

	@Autowired
    PasswordEncoder passwordEncoder;

	public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        // 회원 정보를 DB에서 조회
	    Member member = service.item(username);

	    if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Member 객체를 그대로 반환 (UserDetails를 구현한 Member 사용)
        return member;
    }

}
