package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.ac.kopo.dorandoran.dto.SignUpForm;
import kr.ac.kopo.dorandoran.model.Alert;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.model.Wish;
import kr.ac.kopo.dorandoran.pager.Pager;
import kr.ac.kopo.dorandoran.service.AlertService;
import kr.ac.kopo.dorandoran.service.HouseService;
import kr.ac.kopo.dorandoran.service.MemberService;
import kr.ac.kopo.dorandoran.service.WishService;

@Controller
public class RootController {

	@Autowired
	MemberService memberService;

	@Autowired
	HouseService houseService;

	@Autowired
	AlertService alertService;

	@Autowired
	WishService wishService;

	@GetMapping("/")
	String index(Principal principal, Model model, Long houseId, @RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude, Pager pager) {

		Map<String, Object> params = new HashMap<>();
		params.put("orderBy", "house.house_id DESC"); // 최신순
		List<House> recentList = houseService.indexHouseList(params);

		// 2. 평점 높은 순 (추천)
		Map<String, Object> params1 = new HashMap<>();
		params1.put("orderBy", "house.house_rate DESC"); // 평점순
		List<House> rateList = houseService.indexHouseList(params1);

		Map<String, Object> params3 = new HashMap<>();
		params3.put("orderBy", "house.view_count DESC");
		List<House> viewList = houseService.indexHouseList(params3);

		model.addAttribute("recentList", recentList);
		model.addAttribute("rateList", rateList);
		model.addAttribute("viewList", viewList);

		if (principal != null) {
			String username = principal.getName();
			Member member = memberService.item(username); // ✅ 딱 한 번만 호출

			Map<String, Object> params2 = new HashMap<>();
			params2.put("memberLatitude", member.getMemberLatitude());
			params2.put("memberLongitude", member.getMemberLongitude());
			List<House> nearbyList = houseService.nearbyList(params2);
			model.addAttribute("nearbyList", nearbyList);

			List<Alert> alert = alertService.item(username);
			String[] address = member.getMemberAddress().split(" ");
			String city = address[0];
			String dong = address[2];

			model.addAttribute("city", city);
			model.addAttribute("dong", dong);
			model.addAttribute("member", member);
			model.addAttribute("alert", alert);

			List<Wish> wishList = wishService.wishList(username);
			Set<Long> wishedSet = wishList.stream().map(Wish::getHouseId).collect(Collectors.toSet());
			model.addAttribute("wishedSet", wishedSet);
		} else if (principal == null && latitude != null && longitude != null) {
	        // 비로그인 상태면서 위치가 전달된 경우
	        Map<String, Object> nonloginParams = new HashMap<>();
	        nonloginParams.put("memberLatitude", latitude);
	        nonloginParams.put("memberLongitude", longitude);
	        List<House> nearbyList = houseService.nearbyList(nonloginParams);
	        model.addAttribute("nearbyList", nearbyList);

	        model.addAttribute("wishedSet", Set.of()); // 빈 세트 처리
	    } else {
			model.addAttribute("wishedSet", Set.of());
		}

		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);
		
		return "index";
	}

	@GetMapping("/login")
	String login(HttpServletRequest request) {
		String uri = request.getHeader("Referer");
		if (uri != null && !uri.contains("/login")) {
			request.getSession().setAttribute("prePage", uri);
		}

		return "login";
	}

	@GetMapping("/signup")
	String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	String signup(@Valid SignUpForm form, Errors errors, String isHost, Model model) {

		if (!form.getMemberPassword().equals(form.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "password.mismatch", "비밀번호가 일치하지 않습니다.");
		}

		if (errors.hasErrors()) {
			model.addAttribute("form", form);
			model.addAttribute("isMale", "Male".equals(form.getMemberGender()));
			model.addAttribute("isFemale", "Female".equals(form.getMemberGender()));
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			validatorResult.forEach(model::addAttribute);
			return "signup";
		}

		Member member = new Member();

		member.setMemberId(form.getMemberId());
		member.setMemberPassword(form.getMemberPassword());
		member.setMemberEmail(form.getMemberEmail());
		member.setMemberAddress(form.getMemberAddress());
		member.setMemberName(form.getMemberName());
		member.setMemberGender(form.getMemberGender());
		member.setMemberNickname(form.getMemberNickname());
		member.setMemberRole("true".equals(isHost) ? 2 : 1);

		memberService.signup(member);

		return "redirect:login";
	}

	@GetMapping("/mypage")
	String mypage(Model model, Principal principal) {
		String username = principal.getName();

		Member member = memberService.item(username);

		model.addAttribute("member", member);

		return "mypage";
	}

	@GetMapping("/search")
	String search(@RequestParam(defaultValue = "1")int page,  @RequestParam(defaultValue = "0") int search,
            @RequestParam(defaultValue = "") String keyword, Model model, Pager pager) {
		keyword = keyword.trim();
		
		pager.setSearch(search);
	    pager.setKeyword(keyword);
	    pager.setPage(page);
		
		int totalCount = houseService.countHousesbySearch(search, keyword);
	    pager.setTotal(totalCount);
		
		List<House> list = houseService.search(search, keyword, pager);
		model.addAttribute("keyword", keyword);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		System.out.println("page: " + page);
		System.out.println("perPage: " + pager.getPerPage());
		System.out.println("offset: " + pager.getOffset());
		System.out.println("totalCount: " + totalCount);
		System.out.println("list size: " + list.size());

		return "result";
	}

}
