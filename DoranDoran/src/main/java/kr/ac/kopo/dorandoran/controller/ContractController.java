package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.service.AlertService;
import kr.ac.kopo.dorandoran.service.ContractService;
import kr.ac.kopo.dorandoran.service.HouseService;

@Controller
@RequestMapping("/contract")
public class ContractController {

	@Autowired
	HouseService houseService;

	@Autowired
	ContractService contractService;

	@Autowired 
	AlertService alertService;
	
	final String path = "contract/";

	@GetMapping("/{houseId}")
	String contract(@PathVariable Long houseId, Model model, Principal principal) {
		String username = principal.getName();

		House item = houseService.item(houseId);

		if (username.equals(item.getHostId())) {
			model.addAttribute("errorMessage", "자신의 집과 계약할 수 없습니다.");
	        return path + "failcontract";
	    }
		
		String hostId = item.getHostId();
		String houseName = item.getHouseName();
		String tenantId = username;

		model.addAttribute("hostId", hostId);
		model.addAttribute("houseName", houseName);
		model.addAttribute("tenantId", tenantId);

		return "contract";
	}

	@PostMapping("/{houseId}")
	String contract(@PathVariable Long houseId, Principal principal, String startDate, String endDate, Model model) {
		House item = houseService.item(houseId);

		Contract contract = new Contract();

		String username = principal.getName();

		if (item.getHouseCurrentpeople() != item.getHouseMaxpeople()) {

			contract.setHouseId(item.getHouseId());
			contract.setOwnerId(item.getHostId());
			contract.setTenantId(username);
			contract.setStartDate(startDate);
			contract.setEndDate(endDate);
			contract.setContractDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

			contractService.add(contract);
			
			String memberId = contract.getOwnerId(); 
			alertService.contractAdd(memberId);

			return path + "complete";
		} else {
			return path + "fail";
		}

	}

}
