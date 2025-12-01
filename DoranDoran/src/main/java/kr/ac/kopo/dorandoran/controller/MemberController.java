package kr.ac.kopo.dorandoran.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController { // 관리자가 회원들을 관리할 때 사용 (add X)

	@Autowired
	MemberService service;

	final String path = "member/";

	@GetMapping("/list")
	String memberList(Model model) {
		List<Member> list = service.list();

		model.addAttribute("list", list);

		return path + "list";
	}

	@GetMapping("/update/{memberId}")
	String update(@PathVariable String memberId, Model model) {
		Member item = service.item(memberId);

		model.addAttribute("item", item);

		return path + "update";
	}

	@PostMapping("/update/{memberId}")
	String update(@PathVariable String memberId, Member item) {
		item.setMemberId(memberId);

		service.update(item);

		return "redirect:list";
	}

	@GetMapping("/delete/{memberId}")
	void delete(@PathVariable String memberId) {
		service.delete(memberId);
	}

	@GetMapping("/detail/{memberId}")
	String item(@PathVariable String memberId, Model model) {
		Member item = service.item(memberId);

		model.addAttribute("item", item);

		return path + "detail";
	}
	
}
