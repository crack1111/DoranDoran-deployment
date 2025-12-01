package kr.ac.kopo.dorandoran.service;
import java.util.List;
import java.util.Optional;

import kr.ac.kopo.dorandoran.model.ChatRoom;
import kr.ac.kopo.dorandoran.model.Message;


public interface ChatroomService {

	List<ChatRoom> list(String memberId);

	void addchatroom(ChatRoom item);

	Message addmessage(Message item);

	List<Message> getmessages(Long chatroomId);

	Optional<ChatRoom> findChatroom(String memberId, String hostId);

	ChatRoom item(Long chatroomId);

	Message messageandalert(Message item, String senderId);

}
