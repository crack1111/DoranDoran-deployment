package kr.ac.kopo.dorandoran.model;

import java.util.List;

public class Contract {
	private Long contractId;
	private String ownerId;
	private String tenantId;
	private Long houseId;
	private String startDate;
	private String endDate;
	private String contractDate;
	private House house;
	private Member member;
	private Member owner;
	private Member tenant;
	private String contractStatus;
	private List<Photo> photo;
	private boolean reviewWritten;
	
	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public Member getTenant() {
		return tenant;
	}

	public void setTenant(Member tenant) {
		this.tenant = tenant;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String start) {
		this.startDate = start;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public List<Photo> getPhoto() {
		return photo;
	}

	public void setPhoto(List<Photo> photo) {
		this.photo = photo;
	}

	public boolean isReviewWritten() {
		return reviewWritten;
	}

	public void setReviewWritten(boolean reviewWritten) {
		this.reviewWritten = reviewWritten;
	}


}
