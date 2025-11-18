package kr.wise.dq.measure.bizrule.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.dq.measure.bizrule.service.DqJobMapper;
import kr.wise.dq.measure.bizrule.service.DqJobService;
import kr.wise.dq.measure.bizrule.service.DqJobVO;

import org.springframework.stereotype.Service;

@Service("dqjobService")
public class DqJobServiceImpl implements DqJobService {
    
	@Inject
	DqJobMapper dqjobMapper;
	
	public List<DqJobVO> selectDqJobList(DqJobVO search) throws Exception {
		
		return dqjobMapper.selectDqJobList(search);
	}

	
	public List<DqJobVO> selectDbmsEnmCode() throws Exception {

		return dqjobMapper.selectDbmsEnmCode();
	}


	public List<DqJobVO> selectErrStatusImpStatusCode() throws Exception {

		return dqjobMapper.selectErrStatusImpStatusCode();
	}
	
}
