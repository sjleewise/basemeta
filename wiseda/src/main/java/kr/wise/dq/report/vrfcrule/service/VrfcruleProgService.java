package kr.wise.dq.report.vrfcrule.service;

import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

public interface VrfcruleProgService {

	List<WamPrfMstrCommonVO> getVrfcruleProgQuality(WamPrfMstrCommonVO search);

}
