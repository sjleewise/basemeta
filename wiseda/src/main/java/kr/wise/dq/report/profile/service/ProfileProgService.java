package kr.wise.dq.report.profile.service;

import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

public interface ProfileProgService {

	List<WamPrfMstrCommonVO> getProfileProgQuality(WamPrfMstrCommonVO search);

	List<WamPrfMstrCommonVO> getProfileProgChart(WamPrfMstrCommonVO search);

	List<ProgChartVO> getProfileQuality(ProgChartVO search);

}
