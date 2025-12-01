package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.kopo.dorandoran.model.ChatRoom;
import kr.ac.kopo.dorandoran.model.Message;
import kr.ac.kopo.dorandoran.service.ChatroomService;

@RestController
@RequestMapping("/chatroom")
public class ChatroomController {

	@Autowired
	ChatroomService service;

	@GetMapping
	List<ChatRoom> chatroomlist(Principal principal) {
		String memberId = principal.getName();

		return service.list(memberId);
	}

	@GetMapping("/{chatroomId}/messages")
	public List<Message> getmessages(@PathVariable Long chatroomId, Principal principal) {
		return service.getmessages(chatroomId);
	}

}
