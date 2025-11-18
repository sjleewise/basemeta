package kr.wise.dq.profile.anares.service;

import java.util.Date;
import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPrfResultMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("anaStrDtm") Date anaStrDtm);

    int insert(WamPrfResultVO record);

    int insertSelective(WamPrfResultVO record);

    WamPrfResultVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("anaStrDtm") Date anaStrDtm);

    int updateByPrimaryKeySelective(WamPrfResultVO record);

    int updateByPrimaryKey(WamPrfResultVO record);
    
    /* 프로파일 조회 */
    List<WamPrfResultVO> selectPrfLst(WamPrfResultVO search);
    
    /* 컬럼프로파일 분석결과 조회 */
    WamPrfResultVO selectColAnaResDtl(String prfId);

	/** @param search
	/** @return meta */
	/** 프로파일 품질추이 조회 */
	List<WamPrfMstrCommonVO> selectProfileProgQuality(WamPrfMstrCommonVO search);

	/** @param search
	/** @return meta */
	/** 프로파일 품질추이 차트조회 */
	List<WamPrfMstrCommonVO> selectProfileProgChart(WamPrfMstrCommonVO search);

	List<WamPrfResultVO> selectColAnaTrsLst(@Param("prfId") String prfId);

	List<WamPrfMstrCommonVO> selectVrfcruleProgQuality(WamPrfMstrCommonVO search);
    
}