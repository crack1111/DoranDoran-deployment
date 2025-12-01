package kr.ac.kopo.dorandoran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dorandoran.dao.AddressDao;
import kr.ac.kopo.dorandoran.model.Address;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	AddressDao dao;
	
	@Override
	public List<Address> cityList() {
		return dao.cityList();
	}

	@Override
	public List<String> guList(String city) {
		return dao.guList(city);
	}

}
