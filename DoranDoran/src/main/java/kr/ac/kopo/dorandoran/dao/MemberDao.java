package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Member;

public interface MemberDao {

	List<Member> list();

	void add(Member item);

	void update(Member item);

	void delete(String memberId);

	Member item(String memberId);

	Member memberItem(String ownerId);

	String getPassword(String memberId);

	void updatePassword(String memberId, String encodedPassword);

	boolean existsMemberId(String value);

	boolean existsNickname(String value);

	boolean existsEmail(String value);

}
