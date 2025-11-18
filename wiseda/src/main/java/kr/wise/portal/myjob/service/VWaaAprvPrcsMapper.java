package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface VWaaAprvPrcsMapper {

	public List<VWaaAprvPrcs> apprReqList(Map<String, Object> param);

	public List<VWaaAprvPrcs> selectRqstList(String userId);
	
	public VWaaAprvPrcs searchApprCount(Map<String, String> param) throws Exception;
}