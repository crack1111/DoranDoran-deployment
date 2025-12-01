package kr.ac.kopo.dorandoran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dorandoran.dao.WishDao;
import kr.ac.kopo.dorandoran.model.Wish;

@Service
public class WishServiceImpl implements WishService {
	
	@Autowired
	WishDao dao;
	
	@Override
	public List<Wish> wishList(String username) {
		return dao.list(username);
	}

	@Override
	public boolean toggle(Long houseId, String memberId) {
		if (dao.exists(houseId, memberId)) {
	        dao.delete(houseId, memberId);
	        return false;
	    } else {
	        dao.add(houseId, memberId);
	        return true;
	    }
	}


}
