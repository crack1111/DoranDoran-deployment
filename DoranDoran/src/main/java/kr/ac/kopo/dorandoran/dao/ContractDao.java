package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Contract;

public interface ContractDao {

	void add(Contract contract);

	List<Contract> list(int role, String memberId);

	void accept(Long contractId);

	void deny(Long contractId);

	void delete(Long contractId);

	Contract item(Long contractId);

	void expired();

	boolean hasContract(String tenantId, Long contractId);

	List<Contract> expiredContracts(String currentDate);

}
