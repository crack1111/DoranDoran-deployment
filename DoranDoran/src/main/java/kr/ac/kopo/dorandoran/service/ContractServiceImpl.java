package kr.ac.kopo.dorandoran.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.kopo.dorandoran.dao.ContractDao;
import kr.ac.kopo.dorandoran.dao.HouseDao;
import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.Member;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractDao dao;

	@Autowired
	HouseDao houseDao;
	
	@Override
	public void add(Contract contract) {
		dao.add(contract);
	}

	@Override
	public List<Contract> list(Member member) {
		int role = member.getMemberRole();
		String memberId = member.getMemberId();
		
		return dao.list(role, memberId);
	}

	@Override
	public void accept(Long contractId) {
		dao.accept(contractId);
	}
	
	@Transactional
	@Override
	public void deny(Long contractId) {
		Contract contract = dao.item(contractId);
		Long houseId = contract.getHouseId();
		
		houseDao.deleteCurrentpeople(houseId);
		
		dao.deny(contractId);
	}
	
	@Override
	public void delete(Long contractId) {
		dao.delete(contractId);
	}
	
	@Transactional
	@Scheduled(cron = "00 00 00 * * ?")//초 분 시 설정
    public void expired() {
        dao.expired();
        
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Contract> expiredContracts = dao.expiredContracts(currentDate); // 계약 상태가 '계약 만료'인 것

        // 3. 각 계약에 대해 house의 현재 인원을 감소시킴
        for (Contract contract : expiredContracts) {
            Long houseId = contract.getHouseId();
            houseDao.deleteCurrentpeople(houseId);
        }
    }
	
	@Override
	public Contract item(Long contractId) {
		return dao.item(contractId);
	}
	
	@Override
	public boolean hasContract(String tenantId, Long contractId) {
        return dao.hasContract(tenantId, contractId);
    }

}
