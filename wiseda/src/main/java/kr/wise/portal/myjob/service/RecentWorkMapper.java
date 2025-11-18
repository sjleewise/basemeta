package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface RecentWorkMapper {

	RecentWorkVO selectUserId(Map<String, String> param) throws Exception;
	
	List<RecentWorkVO> selectRecentWork(Map<String, Object> param1)throws Exception;

	String selectsvnUserId(Map<String, Object> param);

}