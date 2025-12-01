package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Wish;

public interface WishDao {

	List<Wish> list(String username);

	void add(Long houseId, String memberId);

	void delete(Long houseId, String memberId);

	boolean exists(Long houseId, String memberId);


}
