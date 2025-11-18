package kr.wise.dq.report.vrfcrule.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.anares.service.WamPrfResultMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.report.vrfcrule.service.VrfcruleProgChartMapper;
import kr.wise.dq.report.vrfcrule.service.VrfcruleProgChartVO;
import kr.wise.dq.report.vrfcrule.service.VrfcruleProgService;

import org.springframework.stereotype.Service;

@Service("VrfcruleProgService")
public class VrfcruleProgServiceImpl implements VrfcruleProgService {

	@Inject
	WamPrfResultMapper wamPrfResultMapper;
	
	@Inject
	VrfcruleProgChartMapper vrfcruleProgChartMapper;
	
	@Override
	public List<WamPrfMstrCommonVO> getVrfcruleProgQuality(WamPrfMstrCommonVO search) {
		return vrfcruleProgChartMapper.selectVrfcruleProgQuality(search);
	}


}
