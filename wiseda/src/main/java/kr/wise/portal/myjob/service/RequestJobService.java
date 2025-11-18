package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;


public interface RequestJobService {


	
	public List<WaqReqMst> searchMyRegReq(Map<String, Object> param) throws Exception;

	public WaqReqMst searchMyRegCount(Map<String, String> param) throws Exception;
}
