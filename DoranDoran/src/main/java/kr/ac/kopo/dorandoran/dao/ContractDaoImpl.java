package kr.ac.kopo.dorandoran.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Contract;

@Repository
public class ContractDaoImpl implements ContractDao {

	@Autowired
	SqlSession sql;

	@Override
	public void add(Contract contract) {
		sql.insert("contract.add", contract);
	}

	@Override
	public List<Contract> list(int role, String memberId) {
		Map<String, Object> map = new HashMap<>();
		map.put("role", role);
		map.put("memberId", memberId);

		return sql.selectList("contract.list", map);
	}

	@Override
	public void accept(Long contractId) {
		sql.update("contract.accept", contractId);
	}

	@Override
	public void deny(Long contractId) {
		sql.update("contract.deny", contractId);
	}

	@Override
	public void delete(Long contractId) {
		sql.delete("contract.delete", contractId);
	}

	@Override
	public Contract item(Long contractId) {
		return sql.selectOne("contract.item", contractId);
	}

	@Override
	public void expired() {
		sql.update("contract.expired");
	}

	@Override
	public boolean hasContract(String tenantId, Long contractId) {
		Map<String, Object> map = new HashMap<>();
		map.put("tenantId", tenantId);
		map.put("contractId", contractId);

		return sql.selectOne("contract.hasContract", map);
	}

	@Override
	public List<Contract> expiredContracts(String currentDate) {
		return sql.selectList("contract.expiredContracts", currentDate);
	}

}
