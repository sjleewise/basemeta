package kr.wise.portal.myjob.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.portal.myjob.service.ApprReqService;
import kr.wise.portal.myjob.service.VWaaAprvPrcs;
import kr.wise.portal.myjob.service.VWaaAprvPrcsMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("apprReqService")
public class ApprReqServiceImpl implements ApprReqService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private   VWaaAprvPrcsMapper appreqMapper;


	public List<VWaaAprvPrcs> apprReqList(Map<String, Object> param){
	return appreqMapper.apprReqList(param);
	}

	public List<VWaaAprvPrcs> selectRqstList(String userId){
		return appreqMapper.selectRqstList(userId);
	}
	

	public VWaaAprvPrcs searchApprCount(Map<String, String> param) throws Exception{
	return appreqMapper.searchApprCount(param);
	}
}