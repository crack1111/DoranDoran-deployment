package kr.ac.kopo.dorandoran.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.ac.kopo.dorandoran.model.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddForm {
	
	@NotBlank(message = "이름을 입력해주세요.")
    private String houseName;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer housePrice;

    @NotNull(message = "보증금을 입력해주세요.")
    private Integer houseDeposit;

    @NotBlank(message = "종류를 설정해주세요.")
    private String houseType;

    @NotNull(message = "최대 인원을 설정해주세요.")
    private Integer houseMaxpeople;

    @NotNull(message = "최소 인원을 설정해주세요.")
    private Integer houseCurrentpeople;

    @NotBlank(message = "집 설명을 입력해주세요.")
    @Size(min = 50, message = "집 설명은 최소 50자 이상이어야 합니다.")
    private String houseContent;

    @NotBlank(message = "주소를 입력해주세요.")
    private String houseAddress;

    private List<Option> houseOption;
    
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public int getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}
	public int getHouseDeposit() {
		return houseDeposit;
	}
	public void setHouseDeposit(int houseDeposit) {
		this.houseDeposit = houseDeposit;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
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
	public String getHouseContent() {
		return houseContent;
	}
	public void setHouseContent(String houseContent) {
		this.houseContent = houseContent;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public List<Option> getHouseOption() {
		return houseOption;
	}
	public void setHouseOption(List<Option> houseOption) {
		this.houseOption = houseOption;
	}
}