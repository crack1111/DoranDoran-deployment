package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.pager.Pager;

@Repository
public class HouseDaoImpl implements HouseDao {

	@Autowired
	SqlSession sql;

	@Override
	public List<House> list() {
		return sql.selectList("house.list");
	}

	@Override
	public void add(House item) {
		sql.insert("house.add", item);
	}

	@Override
	public void update(House item) {
		sql.update("house.update", item);
	}

	@Override
	public void delete(Long houseId) {
		sql.delete("house.delete", houseId);
	}
	

	@Override
	public void deletePhotos(Long houseId) {
		sql.delete("house.deletePhotos", houseId);
	}
	
	@Override
	public void deletePhoto(Integer photoId) {
		sql.delete("house.deletePhoto", photoId);
	}
	
	@Override
	public Photo getPhoto(Integer photoId) {
		return sql.selectOne("house.getPhoto", photoId);
	}

	@Override
	public House item(Long houseId) {
		return sql.selectOne("house.item", houseId);

	}

	@Override
	public void addPhoto(Photo photo) {
		sql.insert("house.add_photo", photo);
	}

	@Override
	public House houseItem(Long houseId) {
		return sql.selectOne("house.item", houseId);
	}

	@Override
	public void updateCurrentpeople(Long houseId) {
		sql.update("house.updateCurrentpeople", houseId);
	}

	@Override
	public List<House> search(int search, String keyword, Pager pager) {
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("keyword", keyword);
	    map.put("perPage", pager.getPerPage());
	    map.put("offset", pager.getOffset());

		return sql.selectList("house.search", map);
	}

	@Override
	public void rateUpdate(float rate, Long houseId) {
		Map<String, Object> param = new HashMap<>();
		param.put("rate", rate);
		param.put("houseId", houseId);

		sql.update("house.rateUpdate", param);
	}

	@Override
	public List<House> hostHouseList(String hostId) {
		return sql.selectList("house.hostHouseList", hostId);
	}

	@Override
	public void addHouseOptionRel(Long houseId, Long optionId) {
		Map<String, Long> map = new HashMap<>();
		map.put("houseId", houseId);
		map.put("optionId", optionId);

		sql.insert("house.addHouseOptionRel", map);
	}

	@Override
	public List<House> indexHouseList(Map<String, Object> params) {
		return sql.selectList("house.indexHouseList", params);
	}

	@Override
	public void deleteCurrentpeople(Long houseId) {
		sql.update("house.deleteCurrentpeople", houseId);
	}

	@Override
	public List<House> searchHouses(String city, String gu, String type) {
		Map<String, String> map = new HashMap<>();
		map.put("city", city);
		map.put("gu", gu);
		map.put("type", type);

		return sql.selectList("house.searchHouses", map);
	}

	@Override
	public List<House> searchAllHouses(String city, String type) {
		Map<String, Object> map = new HashMap<>();
		map.put("city", city);
		map.put("type", type);
		return sql.selectList("house.searchAllHouses", map);
	}

	@Override
	public List<House> nearbyList(Map<String, Object> params2) {
		return sql.selectList("house.nearbyList", params2);
	}

	@Override
	public List<House> nearbyStay(Map<String, Object> params3) {
		return sql.selectList("house.nearbyList", params3);
	}

	@Override
	public List<House> nearbyShare(Map<String, Object> params4) {
		return sql.selectList("house.nearbyList", params4);
	}

	@Override
	public List<House> houseListByType(String type, Pager pager) {	
		Map<String, Object> map = new HashMap<>();
	    map.put("perPage", pager.getPerPage());
	    map.put("type", type);  
	    map.put("offset", pager.getOffset());
		return sql.selectList("house.houseListByType", map);
	}

	@Override
	public List<House> houseListByOption(String option, Pager pager) {
		Map<String, Object> map = new HashMap<>();
	    map.put("perPage", pager.getPerPage());
	    map.put("option", option);  
	    map.put("offset", pager.getOffset());
		return sql.selectList("house.houseListByOption", map);
	}

	@Override
	public List<House> searchAllHousesByOption(String city, String option) {
		Map<String, Object> map = new HashMap<>();
		map.put("city", city);
		map.put("option", option);
		return sql.selectList("house.searchAllHousesByOption", map);
	}

	@Override
	public List<House> searchHousesByOption(String city, String gu, String option) {
		Map<String, Object> map = new HashMap<>();
		map.put("city", city);
		map.put("gu", gu);
		map.put("option", option);
		return sql.selectList("house.searchHousesByOption", map);
	}

	@Override
	public void increseViewCount(Long houseId) {
		sql.update("house.increseViewCount", houseId);
	}

	@Override
	public int countHouses(Map<String, Object> params) {
		return sql.selectOne("house.countHouses", params);
	}

	@Override
	public List<House> searchnearbyList(Map<String, Object> params2, Pager pager) {
		params2.put("page", pager.getPage());         // Pager 객체에서 page 값 꺼내기
	    params2.put("perPage", pager.getPerPage());
	    params2.put("offset", pager.getOffset());
		return sql.selectList("house.searchnearbyList", params2);
	}

	@Override
	public int countHousesbySearch(int search, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("keyword", keyword);
		
		return sql.selectOne("house.countHousesbySearch", map);
	}

}
