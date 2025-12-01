package kr.ac.kopo.dorandoran.model;

public class Alert {
	private Long alertId;
	private String memberId;
	private String alertType;
	private String alertMessage;
	private boolean alertRead;
	private String alertTimestamp;
	private Long alertChatroomid;
	
	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public boolean isAlertRead() {
		return alertRead;
	}

	public void setAlertRead(boolean alertRead) {
		this.alertRead = alertRead;
	}

	public String getAlertTimestamp() {
		return alertTimestamp;
	}

	public void setAlertTimestamp(String alertTimestamp) {
		this.alertTimestamp = alertTimestamp;
	}

	public Long getAlertChatroomid() {
		return alertChatroomid;
	}

	public void setAlertChatroomid(Long alertChatroomid) {
		this.alertChatroomid = alertChatroomid;
	}


}
