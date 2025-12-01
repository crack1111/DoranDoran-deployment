package kr.ac.kopo.dorandoran.service;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Wish;

public interface WishService {

	List<Wish> wishList(String username);

	boolean toggle(Long houseId, String memberId);


}
