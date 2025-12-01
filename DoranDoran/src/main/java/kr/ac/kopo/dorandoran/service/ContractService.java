package kr.ac.kopo.dorandoran.service;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.Member;

public interface ContractService {

	void add(Contract contract);

	List<Contract> list(Member member);

	void accept(Long contractId);

	void deny(Long contractId);

	void delete(Long contractId);

	Contract item(Long contractId);

	boolean hasContract(String tenantId, Long contractId);
}
