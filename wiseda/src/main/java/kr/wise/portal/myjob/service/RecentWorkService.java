package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;


public interface RecentWorkService {
	
	public List<RecentWorkVO> selectRecentWork(Map<String, Object> param) throws Exception;
	
	RecentWorkVO selectUserId(Map<String, String> param) throws Exception;

	public String selectsvnUserId(Map<String, Object> param) throws Exception;
	
}


