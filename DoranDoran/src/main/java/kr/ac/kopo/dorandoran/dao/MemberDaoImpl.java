package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	SqlSession sql;

	@Override
	public List<Member> list() {
		return sql.selectList("member.list");
	}

	@Override
	public void add(Member item) {
		sql.insert("member.add", item);
	}

	@Override
	public void update(Member item) {
		sql.update("member.update", item);
	}

	@Override
	public void delete(String memberId) {
		sql.delete("member.delete", memberId);
	}

	@Override
	public Member item(String memberId) {
		return sql.selectOne("member.item", memberId);
	}

	@Override
	public Member memberItem(String ownerId) {
		return sql.selectOne("member.item", ownerId);
	}

	@Override
	public String getPassword(String memberId) {
		return sql.selectOne("member.getPassword", memberId);
	}

	@Override
	public void updatePassword(String memberId, String encodedPassword) {
		Map<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("encodedPassword", encodedPassword);
		
		sql.update("member.updatePassword", map);
	}

	@Override
	public boolean existsMemberId(String value) {
		Integer count = sql.selectOne("member.existsMemberId", value);
		return count != null && count > 0;
	}

	@Override
	public boolean existsNickname(String value) {
		Integer count = sql.selectOne("member.existsNickname", value);
		return count != null && count > 0;
	}

	@Override
	public boolean existsEmail(String value) {
		Integer count = sql.selectOne("member.existsEmail", value);
		return count != null && count > 0;
	}

}
