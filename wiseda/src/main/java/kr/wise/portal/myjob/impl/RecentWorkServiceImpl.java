package kr.wise.portal.myjob.impl;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.portal.myjob.service.RecentWorkMapper;
import kr.wise.portal.myjob.service.RecentWorkService;
import kr.wise.portal.myjob.service.RecentWorkVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("recentWorkService")
public class RecentWorkServiceImpl implements RecentWorkService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private   RecentWorkMapper recentWorkMapper;

	
	public RecentWorkVO selectUserId(Map<String, String> param) throws Exception {
		
		return recentWorkMapper.selectUserId(param);
	}
	
	public List<RecentWorkVO> selectRecentWork(Map<String, Object> param) throws Exception {
		
		return recentWorkMapper.selectRecentWork(param);
	}

	public String selectsvnUserId(Map<String, Object> param) throws Exception {
		return recentWorkMapper.selectsvnUserId(param);
	}

}



