package kr.ac.kopo.dorandoran.service;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Alert;

public interface AlertService {

	List<Alert> list();

	void contractCompleteadd(String memberId);

	void contractAdd(String memberId);

	List<Alert> item(String username);

	void delete(Long alertId);

	void alertread(String memberId);

	void chatalert(String memberId, String hostId, Long chatroomId);
}
