package kr.ac.kopo.dorandoran.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.kopo.dorandoran.dao.ChatroomDao;
import kr.ac.kopo.dorandoran.model.ChatRoom;
import kr.ac.kopo.dorandoran.model.Message;

@Service
public class ChatroomServiceImpl implements ChatroomService {

	@Autowired
	AlertService alertService;
	
	@Autowired
	ChatroomDao dao;

	@Transactional
	@Override
	public List<ChatRoom> list(String memberId) {
		return dao.list(memberId);
	}

	@Transactional
	@Override
	public void addchatroom(ChatRoom item) {
		dao.addchatroom(item);
	}

	@Transactional
	@Override
	public Message addmessage(Message item) {
		return dao.addmessage(item); // 저장 후 결과 반환
	}

	@Transactional
	@Override
	public List<Message> getmessages(Long chatroomId) {
		return dao.getmessages(chatroomId);
	}

	@Override
	public Optional<ChatRoom> findChatroom(String memberId, String hostId) {
		ChatRoom chatRoom = dao.findChatroom(memberId, hostId);
        return Optional.ofNullable(chatRoom);
	}

	@Transactional
	@Override
	public ChatRoom item(Long chatroomId) {
		return dao.item(chatroomId);
	}

	@Transactional
	@Override
	public Message messageandalert(Message item, String senderId) {
		item.setMemberId(senderId);
		item.setMsgTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		Message saved = dao.addmessage(item);

	    ChatRoom chatRoom = dao.item(item.getChatroomId()); 

	    String memberId = chatRoom.getUser1();
	    String hostId = chatRoom.getUser2(); 
	    Long chatroomId = item.getChatroomId();

	    if (senderId.equals(hostId)) {
	        alertService.chatalert(memberId, hostId, chatroomId);
	    } else if (senderId.equals(memberId)) {
	        alertService.chatalert(hostId, memberId, chatroomId);
	    }

	    return saved;		
	}

}
