package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.kopo.dorandoran.service.WishService;

@RestController
@RequestMapping("/wish")
public class WishController {

	@Autowired
	WishService wishService;

	@PostMapping("/toggle/{houseId}")
	public ResponseEntity<String> toggleWish(@PathVariable Long houseId, Principal principal) {
	    String memberId = principal.getName();
	    boolean isAdded = wishService.toggle(houseId, memberId);
	    return ResponseEntity.ok(isAdded ? "added" : "removed");
	}

}
