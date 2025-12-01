package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Alert;

@Repository
public class AlertDaoImpl implements AlertDao {

	@Autowired
	SqlSession sql;
	
	@Override
	public List<Alert> list() {
		
		return sql.selectList("alert.list");
	}

	@Override
	public void contractCompleteadd(Alert alert) {
		sql.insert("alert.contractcompleteadd", alert);
	}

	@Override
	public void contractAdd(Alert alert) {
		sql.insert("alert.contractadd", alert);
	}

	@Override
	public List<Alert> item(String username) {
		return sql.selectList("alert.item", username);
	}

	@Override
	public void delete(Long alertId) {
		sql.delete("alert.delete", alertId);
	}

	@Override
	public void chatalert(Alert alert) {
		sql.insert("alert.chatalert", alert);
	}

	@Override
	public boolean existsChatAlert(String memberId) {
		Integer count = sql.selectOne("alert.existsChatAlert", memberId);
		return count != null && count > 0;
	}

	@Override
	public void alertread(String memberId) {
		sql.update("alert.read", memberId);
	}

}
