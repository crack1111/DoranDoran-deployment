package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.ChatRoom;
import kr.ac.kopo.dorandoran.model.Message;

public interface ChatroomDao {

	List<ChatRoom> list(String memberId);

	void addchatroom(ChatRoom item);

	Message addmessage(Message item);

	List<Message> getmessages(Long chatroomId);

	ChatRoom findChatroom(String memberId, String hostId);

	ChatRoom item(Long chatroomId);

}
