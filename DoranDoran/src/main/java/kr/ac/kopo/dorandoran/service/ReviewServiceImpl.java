package kr.ac.kopo.dorandoran.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.kopo.dorandoran.dao.HouseDao;
import kr.ac.kopo.dorandoran.dao.ReviewDao;
import kr.ac.kopo.dorandoran.model.Review;
import kr.ac.kopo.dorandoran.model.ReviewPhoto;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewDao dao;
	
	@Autowired
	HouseDao houseDao;

	@Autowired
	S3Service s3Service;
	
	final String path = "house/";
	
	final String uploadPath = "c:/reviewupload/";
	
	@Override
	public List<Review> list() {
		return dao.list();
	}
	
	@Transactional
	@Override
	public void add(Review item, List<MultipartFile> uploadFiles) {
		
		dao.add(item);
		
		for (MultipartFile file : uploadFiles) {
		    if (!file.isEmpty()) {
		        String originalFilename = file.getOriginalFilename();
		        String safeName = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
		        String uuid = UUID.randomUUID().toString();
		        String s3Key = "reviews/" + item.getReviewId() + "/" + uuid + "_" + safeName;

		        String imageUrl = s3Service.uploadFile(file, s3Key);

		        ReviewPhoto photo = new ReviewPhoto();
		        photo.setPhotoFilename(safeName);
		        photo.setPhotoUuid(uuid);
		        photo.setPhotoUrl(imageUrl);
			
			photo.setReviewId(item.getReviewId());
			dao.addPhoto(photo);
		}
		
		Float rate = item.getHouseRate();
		Long houseId = item.getHouseId();
		
		houseDao.rateUpdate(rate, houseId);
		}
	}
	@Override
	public Review item(Long reviewId) {
		return dao.item(reviewId);
	}

	@Override
	public void update(Review item, List<MultipartFile> uploadFiles) {
		dao.update(item);
		
		for (MultipartFile file : uploadFiles) {
			if (!file.isEmpty()) {
		        String originalFilename = file.getOriginalFilename();
		        String safeName = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
		        String uuid = UUID.randomUUID().toString();
		        String s3Key = "reviews/" + item.getReviewId() + "/" + uuid + "_" + safeName;
		        
		        String imageUrl = s3Service.uploadFile(file, s3Key);

		        ReviewPhoto photo = new ReviewPhoto();
		        photo.setPhotoFilename(safeName);
		        photo.setPhotoUuid(uuid);
		        photo.setPhotoUrl(imageUrl);
			photo.setReviewId(item.getReviewId());
			
			dao.addPhoto(photo);
		}
		
		Float rate = item.getHouseRate();
		Long houseId = item.getHouseId();
		
		houseDao.rateUpdate(rate, houseId);
		}
	}

	@Override
	public void delete(Long reviewId) {
		Review review = dao.item(reviewId);
		
		if (review != null && review.getReviewPhoto() != null) {
	        for (ReviewPhoto photo : review.getReviewPhoto()) {
	            if (photo.getPhotoUrl() != null) {
	            	String s3Key = s3Service.extractS3KeyFromUrl(photo.getPhotoUrl());
	            	s3Service.deleteFile(s3Key);
	            }
	        }
	    }
		
		dao.deletePhotos(reviewId);
		dao.delete(reviewId);
	}

	@Override
	@Cacheable(value = "reviewListCache", key = "#houseId")
	public List<Review> reviewList(Long houseId) {
		return dao.reviewList(houseId);
	}

	@Override
	public List<Review> myReviewList(String memberId) {
		return dao.myReviewList(memberId);
	}

	@Override
	public List<Review> houseReviewList(Long houseId) {
		return dao.houseReviewList(houseId);
	}

	@Override
	public boolean existsContract(Long contractId) {
		return dao.existsContract(contractId);
	}

}
