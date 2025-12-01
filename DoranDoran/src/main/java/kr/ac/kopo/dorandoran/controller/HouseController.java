package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.kopo.dorandoran.dao.HouseDao;
import kr.ac.kopo.dorandoran.model.Address;
import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.model.Option;
import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.model.Review;
import kr.ac.kopo.dorandoran.model.Wish;
import kr.ac.kopo.dorandoran.pager.Pager;
import kr.ac.kopo.dorandoran.service.AddressService;
import kr.ac.kopo.dorandoran.service.ContractService;
import kr.ac.kopo.dorandoran.service.HouseService;
import kr.ac.kopo.dorandoran.service.MemberService;
import kr.ac.kopo.dorandoran.service.OptionService;
import kr.ac.kopo.dorandoran.service.ReviewService;
import kr.ac.kopo.dorandoran.service.S3Service;
import kr.ac.kopo.dorandoran.service.WishService;

@Controller
@RequestMapping("/house")
public class HouseController {

	@Autowired
	HouseService houseService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	OptionService optionService;

	@Autowired
	ContractService contractService;

	@Autowired
	AddressService addressService;

	@Autowired
	MemberService memberService;

	@Autowired
	WishService wishService;

	@Autowired
	S3Service s3Service;

	@Autowired
	HouseDao houseDao;

	final String path = "house/";

	final String uploadPath = "c:/upload/";

	@InitBinder("house")
	public void initBinder(WebDataBinder binder) {
		// houseOption 필드 자동 바인딩 막기
		binder.setDisallowedFields("houseOption");
	}

	@GetMapping("/list")
	public String houselist(Model model, Principal principal, String type, String option,
			@RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude,
			@ModelAttribute Pager pager) {

		Map<String, Object> params = new HashMap<>();
		if (principal != null) {
			String username = principal.getName();
			Member member = memberService.item(username);

			params.put("memberLatitude", member.getMemberLatitude());
			params.put("memberLongitude", member.getMemberLongitude());

			// 타입으로 볼 때는 거리 필터 끄기
			if ("내주변".equals(type)) {
				params.put("useDistanceFilter", true);
			} else {
				params.put("useDistanceFilter", false);
			}
		} else {
			params.put("memberLatitude", latitude);
			params.put("memberLongitude", longitude);

			// 로그인 안 했으면 좌표 있을 때만 거리 필터 사용, 근데 타입 체크 추가
			if ("내주변".equals(type) && latitude != null && longitude != null) {
				params.put("useDistanceFilter", true);
			} else {
				params.put("useDistanceFilter", false);
			}
		}

		// 1. 전체 카운트 구하기 (페이징에 필요)
		int totalCount = houseService.countHouses(type, option, params);
		pager.setTotal(totalCount);

		// 2. 페이징 관련 옵션 세팅 (page, perPage 등은 이미 Pager 객체에 있음)
		// 필요시 타입, 옵션도 Pager에 세팅해서 넘겨도 됨
		pager.setOption(option);
		pager.setType(type);

		// 3. 실제 리스트 가져오기
		List<House> list = houseService.houseList(type, option, params, pager);

		model.addAttribute("list", list);
		model.addAttribute("pager", pager);

		// 로그인 안 한 경우 시/도 목록 같이 넘겨주려면
		List<Address> cityList = addressService.cityList();
		
		model.addAttribute("city", cityList);
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);

		return path + "list";
	}

	@GetMapping("/address/gu")
	@ResponseBody
	List<String> guList(String city) {
		return addressService.guList(city);
	}

	@GetMapping("/search")
	@ResponseBody
	List<House> searchHouses(String city, String gu, String type, String option) {
		return houseService.searchHouses(city, gu, type, option);
	}

	@GetMapping("/add")
	String add(Model model) {
		List<Option> optionList = optionService.optionList();

		model.addAttribute("list", optionList);

		return path + "add";
	}

	@PostMapping("/add")
	String add(House item, Principal principal, List<MultipartFile> uploadFile,
			@RequestParam(value = "houseOption", required = false) List<Long> houseOptionIds) throws Exception {

		String username = principal.getName();

		List<Photo> photos = new ArrayList<>();

		item.setPhoto(photos);
		item.setHostId(username);

		if (houseOptionIds != null && !houseOptionIds.isEmpty()) {
			List<Option> options = new ArrayList<>();

			for (Long optionId : houseOptionIds) {
				Option option = optionService.item(optionId);
				options.add(option);
			}
			item.setHouseOption(options);
		}

		String registerDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		item.setRegisterDate(registerDate);

		houseService.add(item, uploadFile);

		Long houseId = item.getHouseId();

		return "redirect:/house/detail/" + houseId;
	}

	@GetMapping("/update/{houseId}")
	String update(@PathVariable Long houseId, Model model) {
		House item = houseService.item(houseId);

		model.addAttribute("item", item);

		return path + "update";
	}

	@PostMapping("/update/{houseId}")
	String update(@PathVariable Long houseId, House item, Principal principal, List<MultipartFile> uploadFile,
			@RequestParam(value = "deletePhotoIds", required = false) List<Integer> deletePhotoIds) throws Exception {

		String username = principal.getName();

		List<Photo> photos = new ArrayList<Photo>();

		if (deletePhotoIds != null) {
			for (Integer photoId : deletePhotoIds) {
				Photo photo = houseDao.getPhoto(photoId);
				String s3Key = s3Service.extractS3KeyFromUrl(photo.getPhotoUrl()); // URL에서 key 추출
				try {
					s3Service.deleteFile(s3Key); // key를 이용해서 삭제
				} catch (Exception e) {
				}
				houseDao.deletePhoto(photoId); // DB 삭제는 마지막에
			}
		}

		item.setPhoto(photos);
		item.setHouseId(houseId);
		item.setHostId(username);

		houseService.update(item, uploadFile);

		return "redirect:../list";
	}

	@GetMapping("/delete/{houseId}")
	String delete(@PathVariable Long houseId, Principal principal) {
		String memberId = principal.getName();
		houseService.delete(houseId);
		return "redirect:/mypage/house/list/" + memberId;
	}

	@GetMapping("/detail/{houseId}")
	String item(@PathVariable Long houseId, Model model, Principal principal,
			@RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude) {
		houseService.increaseViewCount(houseId);

		House item = houseService.item(houseId);

		String hostId = item.getHostId();
		String tenantId = null;

		if (principal != null) {
			tenantId = principal.getName();

			String username = principal.getName();
			Member member = memberService.item(username);
			// 3. 위치 기반
			Map<String, Object> params3 = new HashMap<>();
			params3.put("memberLatitude", member.getMemberLatitude()); // ✅ 위도
			params3.put("memberLongitude", member.getMemberLongitude()); // ✅ 경도
			params3.put("houseType", "홈스테이"); // ✅ 필터링할 타입
			List<House> nearbyStay = houseService.nearbyStay(params3);

			Map<String, Object> params4 = new HashMap<>();
			params4.put("memberLatitude", member.getMemberLatitude()); // ✅ 위도
			params4.put("memberLongitude", member.getMemberLongitude()); // ✅ 경도
			params4.put("houseType", "쉐어하우스"); // ✅ 필터링할 타입
			List<House> nearbyShare = houseService.nearbyShare(params4);

			model.addAttribute("nearbyStay", nearbyStay);
			model.addAttribute("nearbyShare", nearbyShare);

			List<Contract> contractList = contractService.list(member);

			boolean hasContract = false;
			if (tenantId != null) {
				for (Contract contract : contractList) {
					if (tenantId.equals(contract.getTenant().getMemberId())
							&& houseId.equals(contract.getHouse().getHouseId()) && // 👈 이 조건 추가!
							!"계약 만료".equals(contract.getContractStatus())) {

						hasContract = true;
						break;
					}
				}
			}
			model.addAttribute("hasContract", hasContract);

			List<Wish> wishList = wishService.wishList(username);
			Set<Long> wishedSet = wishList.stream().map(Wish::getHouseId).collect(Collectors.toSet());
			model.addAttribute("wishedSet", wishedSet);
		} else if (principal == null && latitude != null && longitude != null) {
	        // 비로그인 상태면서 위치가 전달된 경우
	        Map<String, Object> nonloginParams = new HashMap<>();
	        nonloginParams.put("memberLatitude", latitude);
	        nonloginParams.put("memberLongitude", longitude);
	        nonloginParams.put("houseType", "쉐어하우스");
	        List<House> nearbyShare = houseService.nearbyShare(nonloginParams);
	        model.addAttribute("nearbyShare", nearbyShare);
	        
	        Map<String, Object> nonloginParams1 = new HashMap<>();
	        nonloginParams1.put("memberLatitude", latitude);
	        nonloginParams1.put("memberLongitude", longitude);
	        nonloginParams1.put("houseType", "홈스테이");
	        List<House> nearbyStay = houseService.nearbyStay(nonloginParams1);
	        model.addAttribute("nearbyStay", nearbyStay);

	        model.addAttribute("wishedSet", Set.of()); // 빈 세트 처리
	    } else {
			model.addAttribute("wishedSet", Set.of());
		}

		List<Review> reviewList = reviewService.reviewList(houseId);

		model.addAttribute("item", item);
		model.addAttribute("hostId", hostId);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);

		return path + "detail";
	}

	@GetMapping("/reviewlist/{houseId}")
	String reviewlist(@PathVariable Long houseId, Model model) {
		List<Review> list = reviewService.houseReviewList(houseId);
		House item = houseService.item(houseId);

		model.addAttribute("list", list);
		model.addAttribute("item", item);

		return path + "reviewlist";
	}

	House item(@PathVariable Long houseId) {
		return houseService.item(houseId);
	}
}
