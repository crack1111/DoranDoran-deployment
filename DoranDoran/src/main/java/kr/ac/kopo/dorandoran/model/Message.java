package kr.ac.kopo.dorandoran.model;


public class Message {
	private Long msgId;
	private String memberId;
	private Long chatroomId;
	private String msgContent;
	private String msgTimestamp;
	
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getMsgTimestamp() {
		return msgTimestamp;
	}
	public void setMsgTimestamp(String msgTimestamp) {
		this.msgTimestamp = msgTimestamp;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Long getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(Long chatroomId) {
		this.chatroomId = chatroomId;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
}
