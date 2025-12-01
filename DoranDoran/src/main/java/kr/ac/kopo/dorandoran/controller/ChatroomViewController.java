package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.kopo.dorandoran.model.ChatRoom;
import kr.ac.kopo.dorandoran.service.ChatroomService;

@Controller
@RequestMapping("/chatroom")
public class ChatroomViewController {
	@Autowired
	ChatroomService service;

	final String path = "chatroom/";

	@GetMapping("/list")
	String chatroomlist() {
		return path + "list";
	}

	@GetMapping("/{chatroomId}")
	String chatroom(@PathVariable Long chatroomId, Model model, Principal principal) {
		String memberId = principal.getName();
		
		ChatRoom chatroom = service.item(chatroomId);
		
		if (chatroom == null) {
			model.addAttribute("errorMessage", "잘못된 접근입니다.");
	        return "fail";
	    }

	    // 2. user1 또는 user2가 로그인한 사용자와 같은지 체크
	    if (!memberId.equals(chatroom.getUser1()) && !memberId.equals(chatroom.getUser2())) {
	    	model.addAttribute("errorMessage", "잘못된 접근입니다.");
	        return "fail";
	    }
		
		model.addAttribute("memberId", memberId);

		return path + "chatroom";
	}
	
	@PostMapping("/create")
	String addchatroom(Principal principal, Model model, @RequestParam String hostId) {

		String memberId = principal.getName();
		
		if (memberId.equals(hostId)) {
	        model.addAttribute("errorMessage", "자기 자신과는 채팅방을 만들 수 없습니다.");
	        return path + "createchaterrorpage"; // 혹은 적절한 에러 핸들링 페이지로 리턴
	    }
		
		Optional<ChatRoom> existing = service.findChatroom(memberId, hostId);
		if (existing.isPresent()) {
			return "redirect:" + existing.get().getChatroomId();
		}
		
		ChatRoom item = new ChatRoom();
		item.setUser1(memberId);
		item.setUser2(hostId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedTimestamp = LocalDateTime.now().format(formatter);
		item.setChatroomTimestamp(formattedTimestamp);

		model.addAttribute("memberId", memberId);
		model.addAttribute("hostId", hostId);
		
	    service.addchatroom(item);
	    
	    Optional<ChatRoom> newRoom = service.findChatroom(memberId, hostId);  // 새로 저장된 채팅방을 찾아서 리디렉션
	    return "redirect:" + newRoom.get().getChatroomId();
	}
}
