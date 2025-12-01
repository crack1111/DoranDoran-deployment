package kr.ac.kopo.dorandoran.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Member implements UserDetails {

	private static final long serialVersionUID = -3292882405778833903L;

	private String memberId;
	private String memberName;
	private String memberPassword;
	private String memberGender;
	private String memberEmail;
	private int memberRole;
	private String memberNickname;
	private String memberAddress;
	private String signupDate;
	private Double memberLatitude; // 위도
	private Double memberLongitude; // 경도

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public int getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(int memberRole) {
		this.memberRole = memberRole;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

		if (memberRole == 1)
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
		else if (memberRole == 2)
			list.add(new SimpleGrantedAuthority("ROLE_HOST"));
		else if (memberRole == 99)
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		else
			list.add(new SimpleGrantedAuthority("ROLE_GUEST"));

		return list;

	}

	@Override
	public String getPassword() {
		return memberPassword;
	}

	@Override
	public String getUsername() {
		return memberId;
	}

	public String getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(String signupDate) {
		this.signupDate = signupDate;
	}

	public Double getMemberLatitude() {
		return memberLatitude;
	}

	public void setMemberLatitude(Double memberLatitude) {
		this.memberLatitude = memberLatitude;
	}

	public Double getMemberLongitude() {
		return memberLongitude;
	}

	public void setMemberLongitude(Double memberLongitude) {
		this.memberLongitude = memberLongitude;
	}

}
