package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.model.Review;
import kr.ac.kopo.dorandoran.model.ReviewPhoto;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	
	@Autowired
	SqlSession sql;

	@Override
	public List<Review> list() {
		return sql.selectList("review.list");
	}

	@Override
	public void add(Review item) {
		sql.insert("review.add", item);
	}

	@Override
	public Review item(Long reviewId) {
		return sql.selectOne("review.item", reviewId);
	}

	@Override
	public void update(Review item) {
		sql.update("review.update", item);
	}

	@Override
	public void delete(Long reviewId) {
		sql.delete("review.delete", reviewId);
	}
	

	@Override
	public void deletePhotos(Long reviewId) {
		sql.delete("review.deletePhotos", reviewId);
	}
	

	@Override
	public void deletePhoto(Integer photoId) {
		sql.delete("review.deletePhoto", photoId);
	}

	@Override
	public Photo getPhoto(Integer photoId) {
		return sql.selectOne("review.getPhoto", photoId);
	}

	@Override
	public List<Review> reviewList(Long houseId) {
		Map<String, Object> param = new HashMap<>();
		param.put("houseId", houseId); 

		return sql.selectList("review.reviewList", param);
	}

	@Override
	public void addPhoto(ReviewPhoto photo) {
		sql.insert("review.add_photo", photo);
		
	}

	@Override
	public List<Review> myReviewList(String memberId) {
		return sql.selectList("review.myReviewList", memberId);
	}

	@Override
	public List<Review> houseReviewList(Long houseId) {
		return sql.selectList("review.houseReviewList", houseId);
	}

	@Override
	public boolean existsContract(Long contractId) {
		Integer count = sql.selectOne("review.existsContract", contractId);
	    return count != null && count > 0;
	}

}
