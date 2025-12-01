package kr.ac.kopo.dorandoran.admin;

import java.util.List;
import java.util.Map;

import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.pager.Pager;

public interface AdminDao {

	List<Member> memberList(Pager pager);

	List<House> houseList(Pager pager);

	int countRecentMember();

	int countRecentHouse();

	List<Contract> contractList(Pager pager);

	long maleCount();

	long femaleCount();

	List<Map<String, Object>> contractCountByMonth();

	List<Map<String, Object>> houseCountByMonth();

	int countTotalMembers();

	int countTotalHouses();

	int countTotalContracts();

}
