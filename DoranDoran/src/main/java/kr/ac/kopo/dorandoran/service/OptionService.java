package kr.ac.kopo.dorandoran.service;

import java.util.List;

import kr.ac.kopo.dorandoran.model.Option;

public interface OptionService {

	List<Option> optionList();

	Option item(Long optionId);

}
