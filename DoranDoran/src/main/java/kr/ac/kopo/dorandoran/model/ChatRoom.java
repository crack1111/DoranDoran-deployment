package kr.ac.kopo.dorandoran.model;

public class ChatRoom {
	private Long chatroomId;
	private String user1;
	private String user2;
	private String chatroomTimestamp;

	public Long getChatroomId() {
		return chatroomId;
	}

	public void setChatroomId(Long chatroomId) {
		this.chatroomId = chatroomId;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public String getChatroomTimestamp() {
		return chatroomTimestamp;
	}

	public void setChatroomTimestamp(String chatroomTimestamp) {
		this.chatroomTimestamp = chatroomTimestamp;
	}
}
