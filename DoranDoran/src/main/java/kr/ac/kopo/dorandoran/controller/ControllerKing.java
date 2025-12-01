package kr.ac.kopo.dorandoran.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.ac.kopo.dorandoran.model.Alert;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.service.AlertService;

@ControllerAdvice
public class ControllerKing {
	
	@Autowired
	AlertService alertService;
	
	 @ModelAttribute("member")
	    public Member addMemberToModel(@AuthenticationPrincipal Member member) {
	        return member;
	 }
	 
	 @ModelAttribute("alert")
	    public List<Alert> addAlertToModel(@AuthenticationPrincipal Member member) {
	        if (member != null) {
	            return alertService.item(member.getMemberId()); // 알림 조회 로직
	        }
	        return Collections.emptyList();
	 }
}
