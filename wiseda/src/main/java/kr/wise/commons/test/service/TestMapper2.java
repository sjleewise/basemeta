package kr.wise.commons.test.service;

import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface TestMapper2 {

	void insertTest(Map<String, String> param);

	void updateTest(Map<String, String> param);

	void deleteTest(Map<String, String> param);

	HashMap selectTest(Map<String, String> param);

}
