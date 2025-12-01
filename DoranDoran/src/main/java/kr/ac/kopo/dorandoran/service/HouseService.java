package kr.ac.kopo.dorandoran.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.pager.Pager;

public interface HouseService {

	List<House> list();

	void add(House item, List<MultipartFile> uploadFile);

	void update(House item, List<MultipartFile> uploadFiles);
	
	void delete(Long houseId);

	House item(Long houseId);

	List<House> houseList(String type, String option, Map<String, Object> params2, Pager pager);
	
	List<House> indexHouseList(Map<String, Object> params);

	House houseItem(Long houseId);

	void updateCurrentpeople(Long houseId);

	List<House> search(int search, String keyword, Pager pager);

	List<House> hostHouseList(String hostId);

	List<House> searchHouses(String city, String gu, String type, String option);

	List<House> nearbyList(Map<String, Object> params2);

	List<House> nearbyStay(Map<String, Object> params3);

	List<House> nearbyShare(Map<String, Object> params4);

	void increaseViewCount(Long houseId);

	int countHouses(String type, String option, Map<String, Object> params);

	List<House> searchnearbyList(Map<String, Object> params2, Pager pager);

	int countHousesbySearch(int search, String keyword);

}
