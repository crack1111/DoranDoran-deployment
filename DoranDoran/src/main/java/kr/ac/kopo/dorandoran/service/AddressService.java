package kr.ac.kopo.dorandoran.service;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Address;

public interface AddressService {

	List<Address> cityList();

	List<String> guList(String city);

}
