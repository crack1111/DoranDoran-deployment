package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.model.Review;
import kr.ac.kopo.dorandoran.model.ReviewPhoto;

public interface ReviewDao {

	List<Review> list();

	void add(Review item);

	Review item(Long reviewId);

	void update(Review item);

	void delete(Long reviewId);

	List<Review> reviewList(Long houseId);

	void addPhoto(ReviewPhoto photo);

	List<Review> myReviewList(String memberId);

	List<Review> houseReviewList(Long houseId);

	void deletePhotos(Long reviewId);

	void deletePhoto(Integer photoId);

	Photo getPhoto(Integer photoId);

	boolean existsContract(Long contractId);

}
