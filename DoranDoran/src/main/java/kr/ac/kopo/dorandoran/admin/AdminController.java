package kr.ac.kopo.dorandoran.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.pager.Pager;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService service;

	final String path = "admin/";

	@GetMapping
	public String adminData(Model model, Pager pager) throws JsonProcessingException {
		List<Member> memberList = service.memberList(pager);
		List<House> houseList = service.houseList(pager);
		List<Contract> contractList = service.contractList(pager);
		int totalMemberCount = service.countTotalMembers();
		int totalHouseCount = service.countTotalHouses();
		int totalContractCount = service.countTotalContracts();

		long male = service.maleCount();
		long female = service.femaleCount();
		int newMemberCount = service.countRecentMember();
		int newHouseCount = service.countRecentHouse();

		List<Long> contractCounts = service.contractCount();
		List<Long> houseCounts = service.houseCount();

		String contractCountJson = new ObjectMapper().writeValueAsString(contractCounts); // JSON 문자열로 변환
		String houseCountJson = new ObjectMapper().writeValueAsString(houseCounts); // JSON 문자열로 변환

		model.addAttribute("memberList", memberList);
		model.addAttribute("houseList", houseList);
		model.addAttribute("contractList", contractList);
		model.addAttribute("newMemberCount", newMemberCount);
		model.addAttribute("newHouseCount", newHouseCount);
		model.addAttribute("totalMemberCount", totalMemberCount);
		model.addAttribute("totalHouseCount", totalHouseCount);
		model.addAttribute("totalContractCount", totalContractCount);
		model.addAttribute("male", male);
		model.addAttribute("female", female);
		model.addAttribute("contractCount", contractCountJson); // 문자열로 전달
		model.addAttribute("houseCount", houseCountJson); // 문자열로 전달

		model.addAttribute("currentPage", pager.getPage());
		model.addAttribute("perPage", pager.getPerPage());
		int totalMemberPages = (int) Math.ceil((double) totalMemberCount / pager.getPerPage());
		int totalHousePages = (int) Math.ceil((double) totalHouseCount / pager.getPerPage());
		int totalContractPages = (int) Math.ceil((double) totalContractCount / pager.getPerPage());
		model.addAttribute("totalMemberPages", totalMemberPages);
		model.addAttribute("totalHousePages", totalHousePages);
		model.addAttribute("totalContractPages", totalContractPages);

		return path + "index";
	}

	@GetMapping("/fragment/members")
	@ResponseBody
	public Object memberFragment(Pager pager) {
		List<Member> memberList = service.memberList(pager);
		int totalMemberCount = service.countTotalMembers();
		int totalPages = (int) Math.ceil((double) totalMemberCount / pager.getPerPage());

		return Map.of("members", memberList, "currentPage", pager.getPage(), "totalPages", totalPages);
	}

	@GetMapping("/fragment/houses")
	@ResponseBody
	public Object houseFragment(Pager pager) {
		List<House> houseList = service.houseList(pager);
		int totalHouseCount = service.countTotalHouses();
		int totalPages = (int) Math.ceil((double) totalHouseCount / pager.getPerPage());

		return Map.of("houses", houseList, "currentPage", pager.getPage(), "totalPages", totalPages);
	}

	@GetMapping("/fragment/contracts")
	@ResponseBody
	public Object contractFragment(Pager pager) {
		List<Contract> contractList = service.contractList(pager);
		int totalContractCount = service.countTotalContracts();
		int totalPages = (int) Math.ceil((double) totalContractCount / pager.getPerPage());

		return Map.of("contracts", contractList, "currentPage", pager.getPage(), "totalPages", totalPages);
	}

}
