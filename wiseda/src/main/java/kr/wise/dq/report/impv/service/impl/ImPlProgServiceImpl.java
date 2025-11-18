package kr.wise.dq.report.impv.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.report.impv.service.ImPlProgService;
import kr.wise.dq.report.profile.service.ProgChartMapper;
import kr.wise.dq.report.profile.service.ProgChartVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ImPlProgServiceImpl.java
 * 3. Package  : kr.wise.dq.report.impv.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 17. 오후 5:01:23
 * </PRE>
 */ 
@Service("ImPlProgService")
public class ImPlProgServiceImpl implements ImPlProgService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	ProgChartMapper progChartMapper;
	
	@Override
	public List<ProgChartVO> getImPlProgQuality(ProgChartVO search) {
		logger.debug("sortType : {}", search.getSortTyp());
		
		return progChartMapper.selectImPlProgQuality(search);
		
	}

}
