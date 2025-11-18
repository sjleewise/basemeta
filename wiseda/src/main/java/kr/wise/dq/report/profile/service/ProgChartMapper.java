package kr.wise.dq.report.profile.service;

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
public interface ProgChartMapper {

	
	/** @param search
	/** @return meta */
	List<WamPrfMstrCommonVO> selectProfileProgChart(WamPrfMstrCommonVO search);

	/** @param search
	/** @return meta */
	/** 개선계획 현황 조회 */
	List<ProgChartVO> selectImPlProgQuality(ProgChartVO search);

	/**
	 * @param search
	 * @return meta */
	 /** 개선결과 현황 조회 */
	List<ProgChartVO> selectImRslProgQuality(ProgChartVO search);

	
	/**
	 * @param search
	 * @return meta
	 * 진단대상별 품질현황 조회(프로파일) */
	List<ProgChartVO> selectProfileQuality(ProgChartVO search);

	/**
	 * @param search
	 * @return meta
	 * 진단대상별 품질현황 조회(업무규칙) */
	List<ProgChartVO> selectBizruleQuality(ProgChartVO search);



}
