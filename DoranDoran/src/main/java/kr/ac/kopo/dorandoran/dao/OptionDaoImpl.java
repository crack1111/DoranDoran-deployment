package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Option;

@Repository
public class OptionDaoImpl implements OptionDao {

	@Autowired
	SqlSession sql;
	
	@Override
	public List<Option> optionList() {
		return sql.selectList("option.list");
	}

	@Override
	public Option item(Long optionId) {
		return sql.selectOne("option.item", optionId);
	}

}
