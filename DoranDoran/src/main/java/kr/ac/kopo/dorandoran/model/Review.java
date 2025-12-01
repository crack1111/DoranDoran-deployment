package kr.ac.kopo.dorandoran.model;

import java.util.List;

public class Review {
	private Long reviewId;
	private Long houseId;
	private String memberId;
	private String reviewContent;
	private float houseRate;
	private List<ReviewPhoto> reviewPhoto;
	private String reviewPost;
	private House house;
	private Long contractId;

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public float getHouseRate() {
		return houseRate;
	}

	public void setHouseRate(float houseRate) {
		this.houseRate = houseRate;
	}

	public List<ReviewPhoto> getReviewPhoto() {
		return reviewPhoto;
	}

	public void setReviewPhoto(List<ReviewPhoto> reviewPhoto) {
		this.reviewPhoto = reviewPhoto;
	}

	public String getReviewPost() {
		return reviewPost;
	}

	public void setReviewPost(String reviewPost) {
		this.reviewPost = reviewPost;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

}
