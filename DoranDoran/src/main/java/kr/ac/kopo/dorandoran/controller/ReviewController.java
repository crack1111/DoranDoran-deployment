package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.kopo.dorandoran.dao.ReviewDao;
import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.model.Photo;
import kr.ac.kopo.dorandoran.model.Review;
import kr.ac.kopo.dorandoran.model.ReviewPhoto;
import kr.ac.kopo.dorandoran.service.ContractService;
import kr.ac.kopo.dorandoran.service.HouseService;
import kr.ac.kopo.dorandoran.service.MemberService;
import kr.ac.kopo.dorandoran.service.ReviewService;
import kr.ac.kopo.dorandoran.service.S3Service;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService service;

	@Autowired
	HouseService houseService;

	@Autowired
	ContractService contractService;

	@Autowired
	MemberService memberService;

	@Autowired
	ReviewDao reviewDao;

	@Autowired
	S3Service s3Service;

	final String path = "review/";

	final String uploadPath = "c:/reviewupload/";

	@GetMapping("/list")
	String list(Model model) {
		List<Review> list = service.list();

		model.addAttribute("list", list);

		return path + "list";
	}

	@GetMapping("/add/{contractId}")
	String add(@PathVariable Long contractId, Principal principal, Model model) {
		String username = principal.getName();

		Member member = memberService.item(username);

		model.addAttribute("member", member);

		boolean hasContract = contractService.hasContract(username, contractId);
		
		if(hasContract == false) {
			return path + "add";
		}
		model.addAttribute("errorMessage", "잘못된 접근입니다.");
		return "fail";
	}

	@PostMapping("/add/{contractId}")
	String add(@PathVariable Long contractId, Principal principal, Review item, List<MultipartFile> uploadFile) {
		String memberId = principal.getName();

		Contract contract = contractService.item(contractId);
		
		Long houseId = contract.getHouseId();

		item.setContractId(contractId);

		List<ReviewPhoto> photos = new ArrayList<ReviewPhoto>();

		item.setReviewPhoto(photos);

		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = today.format(formatter);

		item.setHouseId(houseId);
		item.setMemberId(memberId);
		item.setReviewPost(formattedDate);

		service.add(item, uploadFile);

		return "redirect:/";
	}

	@GetMapping("/update/{reviewId}")
	String update(@PathVariable Long reviewId, Model model, Principal principal) {
		String memberId = principal.getName();
		
		Review item = service.item(reviewId);

		model.addAttribute("item", item);
		model.addAttribute("memberId", memberId);

		return path + "update";
	}

	@PostMapping("/update/{reviewId}")
	String update(@PathVariable Long reviewId, Review item, Principal principal, List<MultipartFile> uploadFile,
			@RequestParam(value = "deletePhotoIds", required = false) List<Integer> deletePhotoIds) {
		String memberId = principal.getName();

		List<ReviewPhoto> photos = new ArrayList<ReviewPhoto>();

		if (deletePhotoIds != null) {
			for (Integer photoId : deletePhotoIds) {
				Photo photo = reviewDao.getPhoto(photoId);
				String s3Key = s3Service.extractS3KeyFromUrl(photo.getPhotoUrl()); // URL에서 key 추출
				try {
					s3Service.deleteFile(s3Key); // key를 이용해서 삭제
				} catch (Exception e) {
				}
				reviewDao.deletePhoto(photoId); // DB 삭제는 마지막에
			}
		}

		item.setReviewPhoto(photos);

		item.setMemberId(memberId);
		service.update(item, uploadFile);

		return "redirect:/mypage/review/list/" + memberId;
	}

	@GetMapping("/delete/{reviewId}")
	String delete(@PathVariable Long reviewId) {
		service.delete(reviewId);

		return "redirect:list";

	}

}
