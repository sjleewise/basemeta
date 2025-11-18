package kr.wise.portal.myjob.impl;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.portal.myjob.service.RequestJobMapper;
import kr.wise.portal.myjob.service.RequestJobService;
import kr.wise.portal.myjob.service.WaqReqMst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("reqJobService")
public class RequestJobServiceImpl implements RequestJobService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private   RequestJobMapper requestJobMapper;

	public List<WaqReqMst> searchMyRegReq(Map<String, Object> param) throws Exception {
		return requestJobMapper.searchMyRegReq(param);
	}
	public WaqReqMst searchMyRegCount(Map<String, String> param) throws Exception {
		return requestJobMapper.searchMyRegCount(param);
		}	
}
