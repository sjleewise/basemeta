package kr.wise.commons.test.service;

import java.util.List;
import java.util.Map;

public interface TestMapper3 {

	void insertTest(Map<String, String> param);

	void updateTest(Map<String, String> param);

	void deleteTest(Map<String, String> param);

	List<Map> selectTest(Map<String, String> param);
	
	List<Map> selectProc(Map<String, String> param);

}
