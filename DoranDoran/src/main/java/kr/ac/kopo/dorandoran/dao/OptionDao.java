package kr.ac.kopo.dorandoran.dao;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Option;

public interface OptionDao {

	List<Option> optionList();

	Option item(Long optionId);
	
}
