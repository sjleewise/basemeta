package kr.wise.dq.report.profile.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.anares.service.WamPrfResultMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.report.profile.service.ProfileProgService;
import kr.wise.dq.report.profile.service.ProgChartMapper;
import kr.wise.dq.report.profile.service.ProgChartVO;

import org.springframework.stereotype.Service;

@Service("ProfileProgService")
public class ProfileProgServiceImpl implements ProfileProgService {

	@Inject
	WamPrfResultMapper wamPrfResultMapper;
	
	@Inject
	ProgChartMapper progChartMapper;
	
	@Override
	public List<WamPrfMstrCommonVO> getProfileProgQuality(WamPrfMstrCommonVO search) {
		return wamPrfResultMapper.selectProfileProgQuality(search);
	}

	@Override
	public List<WamPrfMstrCommonVO> getProfileProgChart(
			WamPrfMstrCommonVO search) {
		return progChartMapper.selectProfileProgChart(search);
	}

	@Override
	public List<ProgChartVO> getProfileQuality(ProgChartVO search) {
		return progChartMapper.selectProfileQuality(search);
	}

}
