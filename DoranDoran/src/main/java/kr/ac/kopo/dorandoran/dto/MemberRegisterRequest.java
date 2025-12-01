package kr.ac.kopo.dorandoran.dto;

import lombok.Data;

@Data
public class MemberRegisterRequest {
    
    private String memberAddress;

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
}