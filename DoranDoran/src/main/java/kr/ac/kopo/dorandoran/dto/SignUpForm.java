package kr.ac.kopo.dorandoran.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String memberId;
	
	@NotBlank(message = "성명을 입력해주세요.")
	private String memberName;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 사이, 영문 대/소문자, 숫자, 특수문자를 사용해야합니다.")
	private String memberPassword;
	
	@NotBlank(message = "비밀번호 확인을 입력해주세요.")
	private String confirmPassword;
	
	@NotBlank(message = "성별을 선택해주세요.")
	private String memberGender;
	
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String memberEmail;
	private int memberRole;
	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String memberNickname;
	private Long memberRate;
	
	@NotBlank(message = "주소를 입력해주세요.")
	private String memberAddress;
	private String signupDate;

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

	public Long getMemberRate() {
		return memberRate;
	}

	public void setMemberRate(Long memberRate) {
		this.memberRate = memberRate;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(String signupDate) {
		this.signupDate = signupDate;
	}

	@Override
	public String toString() {
		return "SignUpForm [memberId=" + memberId + ", memberName=" + memberName + ", memberPassword=" + memberPassword
				+ ", memberGender=" + memberGender + ", memberEmail=" + memberEmail + ", memberRole=" + memberRole
				+ ", memberNickname=" + memberNickname + ", memberRate=" + memberRate + ", memberAddress="
				+ memberAddress + ", signupDate=" + signupDate + "]";
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
