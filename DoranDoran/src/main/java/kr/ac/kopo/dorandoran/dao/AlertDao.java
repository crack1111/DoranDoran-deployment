package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Alert;

public interface AlertDao {

	List<Alert> list();

	void contractCompleteadd(Alert alert);

	void contractAdd(Alert alert);

	List<Alert> item(String username);

	void delete(Long alertId);

	void chatalert(Alert alert);

	boolean existsChatAlert(String memberId);

	void alertread(String memberId);

}
