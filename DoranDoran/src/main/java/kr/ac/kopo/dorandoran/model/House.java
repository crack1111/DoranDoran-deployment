package kr.ac.kopo.dorandoran.model;

import java.util.List;

public class House {
	private Long houseId;
	private String hostId;
	private String houseName;
	private String houseType;
	private String houseAddress;
	private float houseRate;
	private String houseContent;
	private Review houseReview;
	private int housePrice;
	private int houseDeposit;
	private List<Photo> photo;
	private int houseMaxpeople;
	private int houseCurrentpeople;
	private int reviewCount;
	private float reviewSum;
	private List<Option> houseOption;
	private Photo photo1;
	private String registerDate;
	private Double houseLatitude; // 위도
	private Double houseLongitude; // 경도
	private Double distance;
	private int viewCount;

	public List<Photo> getPhoto() {
		return photo;
	}

	public void setPhoto(List<Photo> photo) {
		this.photo = photo;
	}

	public int getHouseDeposit() {
		return houseDeposit;
	}

	public void setHouseDeposit(int houseDeposit) {
		this.houseDeposit = houseDeposit;
	}

	public int getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	public void setHouseRate(Float houseRate) {
		this.houseRate = houseRate;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public float getHouseRate() {
		return houseRate;
	}

	public void setHouseRate(float houseRate) {
		this.houseRate = houseRate;
	}

	public String getHouseContent() {
		return houseContent;
	}

	public void setHouseContent(String houseContent) {
		this.houseContent = houseContent;
	}

	public Review getHouseReview() {
		return houseReview;
	}

	public void setHouseReview(Review houseReview) {
		this.houseReview = houseReview;
	}

	public int getHouseMaxpeople() {
		return houseMaxpeople;
	}

	public void setHouseMaxpeople(int houseMaxpeople) {
		this.houseMaxpeople = houseMaxpeople;
	}

	public int getHouseCurrentpeople() {
		return houseCurrentpeople;
	}

	public void setHouseCurrentpeople(int houseCurrentpeople) {
		this.houseCurrentpeople = houseCurrentpeople;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public float getReviewSum() {
		return reviewSum;
	}

	public void setReviewSum(float reviewSum) {
		this.reviewSum = reviewSum;
	}

	public List<Option> getHouseOption() {
		return houseOption;
	}

	public void setHouseOption(List<Option> houseOption) {
		this.houseOption = houseOption;
	}

	public Photo getPhoto1() {
		return photo1;
	}

	public void setPhoto1(Photo photo1) {
		this.photo1 = photo1;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public Double getHouseLatitude() {
		return houseLatitude;
	}

	public void setHouseLatitude(Double houseLatitude) {
		this.houseLatitude = houseLatitude;
	}

	public Double getHouseLongitude() {
		return houseLongitude;
	}

	public void setHouseLongitude(Double houseLongitude) {
		this.houseLongitude = houseLongitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

}