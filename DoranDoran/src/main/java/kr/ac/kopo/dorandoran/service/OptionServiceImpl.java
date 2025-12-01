package kr.ac.kopo.dorandoran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dorandoran.dao.OptionDao;
import kr.ac.kopo.dorandoran.model.Option;

@Service
public class OptionServiceImpl implements OptionService {
	@Autowired
	OptionDao dao;

	@Override
	public List<Option> optionList() {
		return dao.optionList();
	}

	@Override
	public Option item(Long optionId) {
		return dao.item(optionId);
	}

}
