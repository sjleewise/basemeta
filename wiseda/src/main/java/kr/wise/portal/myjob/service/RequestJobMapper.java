package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface RequestJobMapper {
    
	public List<WaqReqMst> searchMyRegReq(Map<String, Object> param) throws Exception;

	public WaqReqMst searchMyRegCount(Map<String, String> Aparam)throws Exception;
    
}