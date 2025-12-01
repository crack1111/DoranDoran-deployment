package kr.ac.kopo.dorandoran.dao;

import java.util.List;
import java.util.Map;

import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.pager.Pager;

public interface HouseDao {

	List<House> list();

	void add(House item);

	void update(House item);

	void delete(Long houseId);

	House item(Long houseId);

	void addPhoto(Photo photo);

	House houseItem(Long houseId);

	void updateCurrentpeople(Long houseId);

	List<House> search(int search, String keyword, Pager pager);

	void rateUpdate(float rate, Long houseId);

	List<House> hostHouseList(String hostId);

	void addHouseOptionRel(Long houseId, Long optionId);

	List<House> indexHouseList(Map<String, Object> params);

	void deleteCurrentpeople(Long houseId);

	List<House> searchAllHouses(String city, String type);

	List<House> nearbyList(Map<String, Object> params2);

	List<House> nearbyStay(Map<String, Object> params3);

	List<House> nearbyShare(Map<String, Object> params4);

	List<House> houseListByType(String type, Pager pager);

	List<House> houseListByOption(String option, Pager pager);

	List<House> searchAllHousesByOption(String city, String option);

	List<House> searchHousesByOption(String city, String gu, String option);

	List<House> searchHouses(String city, String gu, String type);

	void increseViewCount(Long houseId);

	void deletePhotos(Long houseId);

	void deletePhoto(Integer photoId);

	Photo getPhoto(Integer photoId);

	int countHouses(Map<String, Object> params);

	List<House> searchnearbyList(Map<String, Object> params2, Pager pager);

	int countHousesbySearch(int search, String keyword);

}
