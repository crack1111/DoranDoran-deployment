package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Address;

public interface AddressDao {

	void addAddress(String city, String gu);

	List<Address> cityList();

	List<String> guList(String city);

}
