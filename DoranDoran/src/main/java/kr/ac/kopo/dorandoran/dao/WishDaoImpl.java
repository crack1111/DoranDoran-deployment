package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Wish;

@Repository
public class WishDaoImpl implements WishDao {

	@Autowired
	SqlSession sql;

	@Override
	public List<Wish> list(String memberId) {
		return sql.selectList("wish.list", memberId);
	}

	@Override
	public void add(Long houseId, String memberId) {
		Map<String, Object> map = new HashMap<>();
		map.put("houseId", houseId);   
		map.put("memberId", memberId);
		sql.insert("wish.add", map);
	}

	@Override
	public void delete(Long houseId, String memberId) {
		Map<String, Object> map = new HashMap<>();
		map.put("houseId", houseId);   
		map.put("memberId", memberId);
		sql.delete("wish.delete", map);
	}

	@Override
	public boolean exists(Long houseId, String memberId) {
		Map<String, Object> map = new HashMap<>();
		map.put("houseId", houseId);   
		map.put("memberId", memberId);
		Integer count = sql.selectOne("wish.exists", map);
	    return count != null && count > 0;
	}

}
