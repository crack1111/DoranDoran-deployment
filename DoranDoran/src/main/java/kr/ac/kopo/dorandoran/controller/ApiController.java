package kr.ac.kopo.dorandoran.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.kopo.dorandoran.service.MemberService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/check-duplicate")
    public Map<String, Boolean> checkDuplicate(String type, String value) {

        boolean exists = memberService.Duplicate(type, value);

        return Collections.singletonMap("exists", exists);
    }
}
