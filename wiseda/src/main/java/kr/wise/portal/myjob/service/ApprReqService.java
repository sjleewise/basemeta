package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;

public interface ApprReqService {

    /**
     * 조건에 맞는 게시물 목록을 조회 한다.
     * 
     * @param boardVO
     * @return
     * @throws Exception
     */
	
	public List<VWaaAprvPrcs> apprReqList(Map<String, Object> param) throws Exception;
	
	public List<VWaaAprvPrcs> selectRqstList(String userId) throws Exception;
	
	public VWaaAprvPrcs searchApprCount(Map<String, String> param) throws Exception;

}