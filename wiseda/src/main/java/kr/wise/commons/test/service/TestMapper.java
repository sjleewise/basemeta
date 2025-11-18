package kr.wise.commons.test.service;

import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface TestMapper {

	void insertTest(Map<String, String> param) throws Exception;

	void updateTest(Map<String, String> param) throws Exception;

	void deleteTest(Map<String, String> param) throws Exception;

	HashMap selectTest(Map<String, String> param) throws Exception;

	/** @param param insomnia */
	void insertTest1(Map<String, String> param);

}
