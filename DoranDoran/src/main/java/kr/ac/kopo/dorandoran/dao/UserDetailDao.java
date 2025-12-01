package kr.ac.kopo.dorandoran.dao;

import kr.ac.kopo.dorandoran.model.Member;

public interface UserDetailDao {

	Member item(String username);

}
