package kr.wise.dq.report.vrfcrule.service;

import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProgChartMapper.java
 * 3. Package  : kr.wise.dq.report.profile.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 11. 오후 2:15:52
 * </PRE>
 */ 
@Mapper
public interface VrfcruleProgChartMapper {

	
	List<WamPrfMstrCommonVO> selectVrfcruleProgQuality(WamPrfMstrCommonVO search);


}
