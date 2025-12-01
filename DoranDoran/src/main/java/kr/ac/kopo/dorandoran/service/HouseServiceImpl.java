package kr.ac.kopo.dorandoran.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.kopo.dorandoran.dao.AddressDao;
import kr.ac.kopo.dorandoran.dao.HouseDao;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Option;
import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.pager.Pager;

@Service
public class HouseServiceImpl implements HouseService {

	@Autowired
	HouseDao dao;

	@Autowired
	AddressDao addressDao;

	@Autowired
	KakaoGeoService kakaoGeoService;

	@Autowired
	S3Service s3Service;

	final String path = "house/";

	final String uploadPath = "c:/upload/";

	@Override
	public List<House> list() {
		return dao.list();
	}

	@Transactional
	@Override
	public void add(House item, List<MultipartFile> uploadFiles) {
		double[] coords = kakaoGeoService.getCoordinates(item.getHouseAddress());

		if (coords == null) {
			throw new RuntimeException("주소를 좌표로 변환할 수 없습니다.");
		}

		item.setHouseLatitude(coords[0]);
		item.setHouseLongitude(coords[1]);

		dao.add(item);
		String address = item.getHouseAddress();
		String[] parts = address.split(" ");
		String city = parts[0];
		String gu = parts[1];

		addressDao.addAddress(city, gu);

		for (MultipartFile file : uploadFiles) {
			if (!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				String safeName = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
				String uuid = UUID.randomUUID().toString();
				String s3Key = "houses/" + item.getHouseId() + "/" + uuid + "_" + safeName;

				String imageUrl = s3Service.uploadFile(file, s3Key);

				Photo photo = new Photo();
				photo.setPhotoFilename(safeName);
				photo.setPhotoUuid(uuid);
				photo.setPhotoUrl(imageUrl);
				photo.setHouseId(item.getHouseId());

				dao.addPhoto(photo);
			}
		}

		if (item.getHouseOption() != null && !item.getHouseOption().isEmpty()) {
			for (Option option : item.getHouseOption()) {
				dao.addHouseOptionRel(item.getHouseId(), option.getOptionId());
			}
		}

	}

	@Override
	public void update(House item, List<MultipartFile> uploadFiles) {
		House existing = dao.item(item.getHouseId());

		if (!existing.getHouseAddress().equals(item.getHouseAddress())) {
			double[] coords = kakaoGeoService.getCoordinates(item.getHouseAddress());
			if (coords == null) {
				throw new RuntimeException("주소를 좌표로 변환할 수 없습니다.");
			}
			System.out.println("변환된 좌표: " + coords[0] + ", " + coords[1]);
			item.setHouseLatitude(coords[0]);
			item.setHouseLongitude(coords[1]);
		} else {
			item.setHouseLatitude(existing.getHouseLatitude());
			item.setHouseLongitude(existing.getHouseLongitude());
		}

		dao.update(item);

		for (MultipartFile file : uploadFiles) {
			if (!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				String safeName = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
				String uuid = UUID.randomUUID().toString();
				String s3Key = "houses/" + item.getHouseId() + "/" + uuid + "_" + safeName;

				String imageUrl = s3Service.uploadFile(file, s3Key);

				Photo photo = new Photo();
				photo.setPhotoFilename(safeName);
				photo.setPhotoUuid(uuid);
				photo.setPhotoUrl(imageUrl);
				photo.setHouseId(item.getHouseId());

				dao.addPhoto(photo);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Long houseId) {
		House house = dao.item(houseId);

		if (house != null && house.getPhoto() != null) {
			for (Photo photo : house.getPhoto()) {
				if (photo.getPhotoUrl() != null) {
					String s3Key = s3Service.extractS3KeyFromUrl(photo.getPhotoUrl());
					s3Service.deleteFile(s3Key);
				}
			}
		}

		dao.deletePhotos(houseId);
		dao.delete(houseId);
	}

	@Override
	public House item(Long houseId) {
		return dao.item(houseId);
	}
	
	@Override
	public List<House> houseList(String type, String option, Map<String, Object> params2, Pager pager) {
		if ("내주변".equals(type)) {
			return dao.searchnearbyList(params2, pager);
		} else if (type != null) {
			return dao.houseListByType(type, pager);
		} else if (option != null) {
			return dao.houseListByOption(option, pager);
		} else if (params2 != null) {
			return dao.searchnearbyList(params2, pager);
		}
	    System.out.println("params: " + params2);
		System.out.println("page: " + pager.getPage());
	    System.out.println("offset: " + pager.getOffset());
		return Collections.emptyList();
	}

	@Override
	public House houseItem(Long houseId) {
		return dao.houseItem(houseId);
	}

	@Override
	public void updateCurrentpeople(Long houseId) {
		dao.updateCurrentpeople(houseId);
	}

	@Override
	public List<House> search(int search, String keyword, Pager pager) {
		return dao.search(search, keyword, pager);
	}

	@Override
	public List<House> hostHouseList(String hostId) {
		return dao.hostHouseList(hostId);
	}

	@Cacheable(value = "houseListCache", key = "#params['orderBy']")
	@Override
	public List<House> indexHouseList(Map<String, Object> params) {
		return dao.indexHouseList(params);
	}

	@Override
	public List<House> searchHouses(String city, String gu, String type, String option) {

		if ((gu == null || gu.isEmpty()) && (option == null || option.isEmpty())) {
			// 구 없고 옵션도 없으면 타입 기준 검색
			return dao.searchAllHouses(city, type);
		} else if ((gu != null && !gu.isEmpty()) && (option == null || option.isEmpty())) {
			// 구 있고 옵션 없으면 구 + 타입 검색
			return dao.searchHouses(city, gu, type);
		} else if ((gu == null || gu.isEmpty()) && (option != null && !option.isEmpty())) {
			// 구 없고 옵션 있을 때
			return dao.searchAllHousesByOption(city, option);
		} else if ((gu != null && !gu.isEmpty()) && (option != null && !option.isEmpty())) {
			// 구 있고 옵션 있을 때
			return dao.searchHousesByOption(city, gu, option);
		} else {
			return dao.searchAllHouses(city, type);
		}

	}

	@Cacheable(value = "nearby", key = "T(java.util.Objects).hash(#params2['memberLatitude'], #params2['memberLongitude'])")
	@Override
	public List<House> nearbyList(Map<String, Object> params2) {
		return dao.nearbyList(params2);
	}

	@Override
	public List<House> searchnearbyList(Map<String, Object> params2, Pager pager) {
		return dao.searchnearbyList(params2, pager);
	}
	
	@Override
	@Cacheable(value = "nearbyStayCache", key = "#params3.toString()")
	public List<House> nearbyStay(Map<String, Object> params3) {
		return dao.nearbyStay(params3);
	}

	@Override
	@Cacheable(value = "nearbyShareCache", key = "#params4.toString()")
	public List<House> nearbyShare(Map<String, Object> params4) {
		return dao.nearbyShare(params4);
	}

	@Cacheable(value = "increaseViewCount", key = "#houseId")
	@Override
	public void increaseViewCount(Long houseId) {
		dao.increseViewCount(houseId);
	}

	@Override
	public int countHouses(String type, String option, Map<String, Object> params) {
		Boolean useDistanceFilter = (Boolean) params.getOrDefault("useDistanceFilter", false);

		params.put("type", type);
		params.put("option", option);
		params.put("useDistanceFilter", useDistanceFilter);

		return dao.countHouses(params);
	} 

	@Override
	public int countHousesbySearch(int search, String keyword) {
		return dao.countHousesbySearch(search, keyword);
	}
}
