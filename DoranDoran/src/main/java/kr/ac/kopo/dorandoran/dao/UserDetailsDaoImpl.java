package kr.ac.kopo.dorandoran.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Member;


@Repository
public class UserDetailsDaoImpl implements UserDetailDao {

	@Autowired
	SqlSession sql;
	
	@Override
	public Member item(String memberId) {
		
		return sql.selectOne("member.item", memberId);
	}

}
