package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.model.Review;
import kr.ac.kopo.dorandoran.model.Wish;
import kr.ac.kopo.dorandoran.service.AlertService;
import kr.ac.kopo.dorandoran.service.ContractService;
import kr.ac.kopo.dorandoran.service.HouseService;
import kr.ac.kopo.dorandoran.service.MemberService;
import kr.ac.kopo.dorandoran.service.ReviewService;
import kr.ac.kopo.dorandoran.service.WishService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	MemberService memberService;

	@Autowired
	HouseService houseService;

	@Autowired
	ContractService contractService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	AlertService alertService;

	@Autowired
	WishService wishService;

	@Autowired
	PasswordEncoder passwordEncoder;

	final String path = "mypage/";
	final String profilePath = "profile/";
	final String housePath = "house/";
	final String contractPath = "contract/";
	final String updatePath = "update/";
	final String reviewPath = "review/";
	final String wishPath = "wish/";

	@GetMapping("/profile/{memberId}")
	String profile(@PathVariable String memberId, Model model, Principal principal) {
		String username = principal.getName();

		Member member = memberService.item(username);

		model.addAttribute("member", member);

		if (member.getMemberId() != null) {

			return path + "profile";
		}

		return "login";
	}

	@GetMapping("/profile/update/{memberId}")
	String update(@PathVariable String memberId, Principal principal, Model model) {
		String username = principal.getName();

		if (username.equals(memberId)) {

			Member item = memberService.item(username);

			model.addAttribute("item", item);

			return path + profilePath + "update";
		}

		return path + "profile";
	}

	@PostMapping("/profile/update/{memberId}")
	String update(@PathVariable String memberId, Member item) {
		item.setMemberId(memberId);

		memberService.update(item);

		return "redirect:../profile";
	}

	@GetMapping("/profile/delete/{memberId}")
	String delete() {
		return path + profilePath + "delete";
	}

	@PreAuthorize("#memberId == authentication.name") // 요청이 들어오기 전에 이미 막아버림
	@PostMapping("/profile/delete/{memberId}")
	public String delete(@PathVariable String memberId, String memberPassword, Principal principal,
			HttpServletRequest request, HttpServletResponse response) {

		Member item = memberService.item(memberId);

		if (passwordEncoder.matches(memberPassword, item.getMemberPassword())) {
			memberService.delete(memberId);
			new SecurityContextLogoutHandler().logout(request, response, null);
			return "redirect:/";
		}

		return path + profilePath + "delete";
	}

	@GetMapping("/contract/list/{memberId}")
	String list(@PathVariable String memberId, Model model, Principal principal) {
		String username = principal.getName();

		Member member = memberService.item(username);

		List<Contract> list = contractService.list(member);
		
		for (Contract contract : list) {
	        boolean reviewExists = reviewService.existsContract(contract.getContractId());
	        contract.setReviewWritten(reviewExists);  // Contract 모델에 reviewWritten 필드 필요
	    }

		model.addAttribute("list", list);
		model.addAttribute("member", member);

		return path + contractPath + "list";
	}

	@PutMapping("/contract/accept/{contractId}")
	public ResponseEntity<Void> accept(@PathVariable Long contractId, Principal principal) {
		Contract item = contractService.item(contractId);
		Long houseId = item.getHouseId();
		String memberId = item.getTenantId();

		contractService.accept(contractId);
		houseService.updateCurrentpeople(houseId);
		alertService.contractCompleteadd(memberId);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/contract/deny/{contractId}")
	public ResponseEntity<Void> deny(@PathVariable Long contractId, Principal principal) {

		contractService.deny(contractId);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/contract/delete/{contractId}")
	public ResponseEntity<Void> delete(@PathVariable Long contractId, Principal principal) {

		contractService.delete(contractId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/profile/update/currentpassword/{memberId}")
	String checkPassword() {
		return path + profilePath + updatePath + "currentpassword";
	}

	@PostMapping("/profile/update/currentpassword/{memberId}")
	String currentPassword(@PathVariable String memberId, String currentPassword, Principal principal) {

		String username = principal.getName();

		if (memberId.equals(username)) {
			String storedPassword = memberService.getPassword(username);

			if (passwordEncoder.matches(currentPassword, storedPassword)) {
				return "redirect:/mypage/profile/update/updatepassword/" + username;
			}
			return null;
		}

		return null;
	}

	@GetMapping("/profile/update/updatepassword/{memberId}")
	String updatePassword() {
		return path + profilePath + updatePath + "updatepassword";
	}

	@PostMapping("/profile/update/updatepassword/{memberId}")
	String updatePassword(@PathVariable String memberId, String updatePassword, Principal principal) {
		String username = principal.getName();

		if (username.equals(memberId)) {

			memberService.updatePassword(memberId, updatePassword);

			return "redirect:../../profile";
		}
		return null;
	}

	@GetMapping("/review/list/{memberId}")
	String reviewList(@PathVariable String memberId, Principal principal, Model model) {
		String username = principal.getName();

		if (username.equals(username)) {

			List<Review> list = reviewService.myReviewList(memberId);

			model.addAttribute("list", list);

			return path + reviewPath + "list";
		}
		return null;
	}

	@GetMapping("/house/list/{memberId}")
	String hostHouseList(@PathVariable String memberId, Principal principal, Model model) {
		String hostId = principal.getName();

		List<House> list = houseService.hostHouseList(hostId);

		model.addAttribute("list", list);

		return path + housePath + "list";
	}

	@GetMapping("/wish/list/{memberId}")
	String wishList(@PathVariable String memberId, Principal principal, Model model) {
		String username = principal.getName();
		if (username.equals(memberId)) {

			List<Wish> list = wishService.wishList(username);

			model.addAttribute("list", list);

			return path + wishPath + "list";
		}
		return null;
	}
}
