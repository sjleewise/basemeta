package kr.wise.portal.dashboard.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.portal.dashboard.service.DqMainChartMapper;
import kr.wise.portal.dashboard.service.DqMainChartService;
import kr.wise.portal.dashboard.service.DqMainChartVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleProgCtrl.java
 * 3. Package  : kr.wise.dq.report.bizrule.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 6. 20. 오후 5:48:53
 * </PRE>
 */ 
@Service("DqMainChartService")
public class DqMainChartServiceImpl implements DqMainChartService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	DqMainChartMapper dqMainChartMapper;
	
	@Override
	public List<DqMainChartVO> approveChart() {
		return dqMainChartMapper.getApproveChartList(); 
	}

	@Override
	public List<DqMainChartVO> analyzeChart(WaaDbConnTrgVO vo) {
		
		List<DqMainChartVO> list = dqMainChartMapper.getAnalyzeChartList(vo);
		
		logger.debug("DqMainChartVO:{}", list);
		
		return list;
	}

	@Override
	public List<DqMainChartVO> qualityChart() {
		return dqMainChartMapper.getQualityChartList();
	}

}
