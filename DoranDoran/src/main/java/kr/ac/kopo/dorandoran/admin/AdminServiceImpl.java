package kr.ac.kopo.dorandoran.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.pager.Pager;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao dao;
	
	@Override
	public List<Member> memberList(Pager pager) {
		return dao.memberList(pager);
	}

	@Override
	public List<House> houseList(Pager pager) {
		return dao.houseList(pager);
	}

	@Override
	public List<Contract> contractList(Pager pager) {
		return dao.contractList(pager);
	}

	@Override
	public int countRecentMember() {
		return dao.countRecentMember();
	}

	@Override
	public int countRecentHouse() {
		return dao.countRecentHouse();
	}

	@Override
	public long maleCount() {
		return dao.maleCount();
	}

	@Override
	public long femaleCount() {
		return dao.femaleCount();
	}

	@Override
	public List<Long> contractCount() {
	    List<Long> counts = new ArrayList<>(Collections.nCopies(12, 0L)); // 1~12월 모두 0으로 초기화
	    List<Map<String, Object>> raw = dao.contractCountByMonth();

	    for (Map<String, Object> row : raw) {
	        int month = ((Number) row.get("month")).intValue();
	        long count = ((Number) row.get("contract_count")).longValue();
	        counts.set(month - 1, count); // 인덱스는 달 - 1
	    }

	    return counts;
	}
	@Override
	public List<Long> houseCount() {
		List<Long> counts = new ArrayList<>(Collections.nCopies(12, 0L)); // 1~12월 모두 0으로 초기화
		List<Map<String, Object>> raw = dao.houseCountByMonth();
		
		for (Map<String, Object> row : raw) {
			int month = ((Number) row.get("month")).intValue();
			long count = ((Number) row.get("house_count")).longValue();
			counts.set(month - 1, count); // 인덱스는 달 - 1
		}
		
		return counts;
	}

	@Override
	public int countTotalMembers() {
		return dao.countTotalMembers();
	}

	@Override
	public int countTotalHouses() {
		return dao.countTotalHouses();
	}

	@Override
	public int countTotalContracts() {
		return dao.countTotalContracts();
	}



}
