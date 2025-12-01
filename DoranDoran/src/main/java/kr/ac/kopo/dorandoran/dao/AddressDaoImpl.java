package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Address;

@Repository
public class AddressDaoImpl implements AddressDao {

	@Autowired
	SqlSession sql;

	@Override
	public void addAddress(String city, String gu) {
		Map<String, String> map = new HashMap<>();
		map.put("city", city);
		map.put("gu", gu);
		sql.insert("address.add", map);
	}

	@Override
	public List<Address> cityList() {
		return sql.selectList("address.cityList");
	}

	@Override
	public List<String> guList(String city) {
		return sql.selectList("address.guList", city);
	}

}
