package kr.ac.kopo.dorandoran.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.ac.kopo.dorandoran.model.Review;

public interface ReviewService {

	List<Review> list();

	void add(Review item, List<MultipartFile> uploadFile);

	Review item(Long reviewId);

	void update(Review item, List<MultipartFile> uploadFile);

	void delete(Long reviewId);

	List<Review> reviewList(Long houseId);

	List<Review> myReviewList(String memberId);

	List<Review> houseReviewList(Long houseId);

	boolean existsContract(Long contractId);

}
