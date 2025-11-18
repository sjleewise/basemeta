package kr.wise.dq.report.bizrule.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.bizrule.service.WamBrMstrMapper;
import kr.wise.dq.report.bizrule.service.BizruleProgService;
import kr.wise.dq.report.profile.service.ProgChartMapper;
import kr.wise.dq.report.profile.service.ProgChartVO;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleProgServiceImpl.java
 * 3. Package  : kr.wise.dq.report.bizrule.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 16. 오후 1:38:29
 * </PRE>
 */ 
@Service("BizruleProgService")
public class BizruleProgServiceImpl implements BizruleProgService {

	@Inject
	WamBrMstrMapper wamBrMstrMapper;
	
	@Inject
	ProgChartMapper progChartMapper;
	
	@Override
	public List<WamBrMstr> getBizruleProgQuality(WamBrMstr search) {
		return wamBrMstrMapper.selectBizruleProgQuality(search);
	}

	@Override
	public List<WamBrMstr> getBizareaProgQuality(WamBrMstr search) {
		return wamBrMstrMapper.selectBizareaProgQuality(search);
	}

	@Override
	public List<WamBrMstr> getDqiProgQuality(WamBrMstr search) {
		return wamBrMstrMapper.selectDqiProgQuality(search);
	}

	@Override
	public List<WamBrMstr> getCtqProgQuality(WamBrMstr search) {
		return wamBrMstrMapper.selectCtqProgQuality(search);
	}

	@Override
	public List<ProgChartVO> getBizruleQuality(ProgChartVO search) {
		return progChartMapper.selectBizruleQuality(search);
	}
	
	@Override
	public List<WamBrMstr> getDqiQuality(WamBrMstr search) {
		return wamBrMstrMapper.selectDqiQuality(search);
	}

}
