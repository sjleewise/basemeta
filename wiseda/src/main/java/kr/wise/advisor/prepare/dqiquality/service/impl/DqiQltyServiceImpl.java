package kr.wise.advisor.prepare.dqiquality.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dqiquality.service.DqiQltyMapper;
import kr.wise.advisor.prepare.dqiquality.service.DqiQltyService;
import kr.wise.advisor.prepare.dqiquality.service.DqiQltyVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("dqiQltyService")
public class DqiQltyServiceImpl implements DqiQltyService {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqiQltyMapper dqiQltyMapper;
	
	@Override
	public List<DqiQltyVo> getDqiQltyList(DqiQltyVo search) {
		// TODO Auto-generated method stub
		logger.debug("dqi별 품질현황 DqiQltyVo >>> \n" + search);
		
		return dqiQltyMapper.selectDqiQltyList(search);
	}

	@Override
	public List<DqiQltyVo> getDqiQltyBar(DqiQltyVo search) {
		// TODO Auto-generated method stub
		logger.debug("dqi별 품질현황 DqiQltyVo >>> \n" + search);
		
		return dqiQltyMapper.selectDqiQltyBar(search);
	}

	@Override
	public List<DqiQltyVo> getDqiQltyPie(DqiQltyVo search) {
		// TODO Auto-generated method stub
		logger.debug("dqi별 품질현황 DqiQltyVo >>> \n" + search);
		logger.debug("schId >>> " + search.getDbSchId());
		logger.debug("dqiId >>> " + search.getDqiId());
		logger.debug("anaDgr >>> " + search.getAnaDgr());
		
		return dqiQltyMapper.selectDqiQltyPie(search);
	}

}
