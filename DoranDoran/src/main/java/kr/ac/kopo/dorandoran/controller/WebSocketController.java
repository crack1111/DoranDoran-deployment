package kr.ac.kopo.dorandoran.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import kr.ac.kopo.dorandoran.model.Alert;
import kr.ac.kopo.dorandoran.model.Message;
import kr.ac.kopo.dorandoran.service.AlertService;
import kr.ac.kopo.dorandoran.service.ChatroomService;

@Controller
public class WebSocketController {

	@Autowired
	ChatroomService service;

	@Autowired
	AlertService alertService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/chatroom/{chatroomId}")
	public void sendMessage(@DestinationVariable Long chatroomId, Message item, Principal principal) {
		item.setChatroomId(chatroomId);

		Message saved = service.messageandalert(item, principal.getName());

		messagingTemplate.convertAndSend("/topic/chatroom/" + chatroomId, saved);
	}

	public void sendAlertToUser(String username, Alert alert) {
		System.out.println("⚡️ sendToUser 호출: " + username + ", alert: " + alert);
		messagingTemplate.convertAndSendToUser(username, "/queue/alerts", alert);
	}

}
