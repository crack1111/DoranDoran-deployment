package kr.ac.kopo.dorandoran.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.dorandoran.model.Contract;
import kr.ac.kopo.dorandoran.model.House;
import kr.ac.kopo.dorandoran.model.Member;
import kr.ac.kopo.dorandoran.pager.Pager;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
	SqlSession sql;

	@Override
	public List<Member> memberList(Pager pager) {
		int offset = (pager.getPage() - 1) * pager.getPerPage();
		Map<String, Object> map = new HashMap<>();
		map.put("page", pager.getPage());
		map.put("perPage", pager.getPerPage());
		map.put("offset", offset);

		return sql.selectList("admin.memberList", map);
	}

	@Override
	public List<House> houseList(Pager pager) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (pager.getPage() - 1) * pager.getPerPage());
		map.put("perPage", pager.getPerPage());
		return sql.selectList("admin.houseList", map);
	}

	@Override
	public List<Contract> contractList(Pager pager) {
		int offset = (pager.getPage() - 1) * pager.getPerPage();
		Map<String, Object> map = new HashMap<>();
		map.put("page", pager.getPage());
		map.put("perPage", pager.getPerPage());
		map.put("offset", offset);

		return sql.selectList("admin.contractList", map);
	}

	@Override
	public int countRecentMember() {
		LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String sevenDaysAgoStr = sevenDaysAgo.format(formatter);

		Map<String, Object> map = new HashMap<>();
		map.put("sevenDaysAgo", sevenDaysAgoStr);

		Integer count = sql.selectOne("admin.countRecentMember", map);
		return count == null ? 0 : count;
	}

	@Override
	public int countRecentHouse() {
		LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String sevenDaysAgoStr = sevenDaysAgo.format(formatter);

		Map<String, Object> map = new HashMap<>();
		map.put("sevenDaysAgo", sevenDaysAgoStr);

		Integer count = sql.selectOne("admin.countRecentHouse", map);
		return count == null ? 0 : count;

	}

	@Override
	public long maleCount() {
		return sql.selectOne("admin.maleCount");
	}

	@Override
	public long femaleCount() {
		return sql.selectOne("admin.femaleCount");
	}

	@Override
	public List<Map<String, Object>> contractCountByMonth() {
		return sql.selectList("admin.contractCount");
	}

	@Override
	public List<Map<String, Object>> houseCountByMonth() {
		return sql.selectList("admin.houseCount");
	}

	@Override
	public int countTotalMembers() {
		return sql.selectOne("admin.countTotalMembers");
	}

	@Override
	public int countTotalHouses() {
		return sql.selectOne("admin.countTotalHouses");
	}

	@Override
	public int countTotalContracts() {
		return sql.selectOne("admin.countTotalContracts");
	}

}
