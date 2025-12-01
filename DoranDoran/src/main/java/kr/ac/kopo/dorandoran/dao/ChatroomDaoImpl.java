package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.ChatRoom;
import kr.ac.kopo.dorandoran.model.Message;

@Repository
public class ChatroomDaoImpl implements ChatroomDao {

	@Autowired
	SqlSession sql;

	@Override
	public List<ChatRoom> list(String memberId) {
		return sql.selectList("chatroom.list", memberId);
	}

	@Override
	public void addchatroom(ChatRoom item) {
		sql.insert("chatroom.add", item);
	}

	@Override
	public Message addmessage(Message item) {
		sql.insert("message.add", item);
		return item;
	}

	@Override
	public List<Message> getmessages(Long chatroomId) {
		return sql.selectList("message.get", chatroomId);
	}

	@Override
    public ChatRoom findChatroom(String memberId, String hostId) {
		Map<String, Object> params = new HashMap<>();
	    params.put("memberId", memberId);
	    params.put("hostId", hostId);
		
        return sql.selectOne("chatroom.findChatroom", params);
    }

	@Override
	public ChatRoom item(Long chatroomId) {
		return sql.selectOne("chatroom.item", chatroomId);
	}

}
