package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.kopo.dorandoran.model.Alert;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.service.AlertService;
import kr.ac.kopo.dorandoran.service.MemberService;

@RestController
@RequestMapping("/alert")
public class AlertController {
	
	@Autowired
	AlertService service;
	
	@Autowired
	MemberService memberService;
	
	final String path = "alert/";
	
	@GetMapping("/list")
	String list(Model model) {
		List<Alert> list = service.list();
		
		model.addAttribute("list", list);
		
		return path + "list";
	}
	
	@PutMapping("/read")
	public ResponseEntity<Void> alertRead(Principal principal) {
	    String username = principal.getName(); 
	    Member member = memberService.item(username); 

	    if (member == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    service.alertread(member.getMemberId());
	    return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/delete/{alertId}")
	@ResponseBody
	public ResponseEntity<Void> deleteAlert(@PathVariable Long alertId) {
		service.delete(alertId);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/item")
    public List<Alert> item(Principal principal) {
        String username = principal.getName();
        return service.item(username);
    }
	
}
