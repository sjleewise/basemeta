package kr.wise.meta.result.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ResultMapper.java
 * 3. Package  : kr.wise.dq.result.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2019. 06. 05.
 * </PRE>
 */ 
@Mapper
public interface MtResultMapper {

	/**
	 * @param search
	 * @return meta
	 * 진단결과조회 */
	List<MtResultVO> selectResultList(MtResultVO search);

	MtResultVO selectDtm(MtResultVO search);

	List<MtResultVO> selectTblList(MtResultVO search);

	List<MtResultVO> selectDmnRule(MtResultVO search);

	List<MtResultVO> selectExecList(MtResultVO search);

	List<MtResultVO> selectErrList(MtResultVO search);
	
	BigDecimal selectTotCnt(MtResultVO search);

	BigDecimal selectRunCnt(MtResultVO search);

	List<MtResultDataVO> selectErrDataMaxColCnt(MtResultVO search);

	List<MtResultDataVO> selectErrListByDqi(MtResultVO search);

	List<MtResultDataVO> selectErrDataByDqi(MtResultVO search);

	String[] getDqiLnmLst();

	MtResultVO selectStndDtm(MtResultVO search);

	MtResultVO selectCnt(MtResultVO search);
	
	MtResultVO selectGovCnt(MtResultVO search);

	MtResultVO selectModelDtm(MtResultVO search);
	
	List<MtResultVO> selectSditmTblList(MtResultVO search);
	
	List<MtResultVO> selectSditmList(MtResultVO search);

	List<MtResultVO> selectDmnList(MtResultVO search);
	
	List<MtResultVO> selectGovSditmTblList(MtResultVO search);
	
	List<MtResultVO> selectGovSditmList(MtResultVO search);

	List<MtResultVO> selectGovDmnList(MtResultVO search);
	
	List<MtResultVO> selectModelStatus(MtResultVO search);
	
	List<MtResultVO> selectStructResultList(MtResultVO search);
	
	List<MtResultVO> selectDbcTblList(MtResultVO search);
	
	List<MtResultVO> selectDbcColList(MtResultVO search);
	
	List<MtResultVO> selectPdmTblList(MtResultVO search);
	
	List<MtResultVO> selectPdmColList(MtResultVO search);
	
	List<MtResultVO> selectGovStructResultList(MtResultVO search);
	
	List<MtResultVO> selectGovDbcTblList(MtResultVO search);
	
	List<MtResultVO> selectGovDbcColList(MtResultVO search);
	
	List<MtResultVO> selectGovTblList(MtResultVO search);
	
	List<MtResultVO> selectGovColList(MtResultVO search);
	
	String selectDbConnTrgURL(MtResultVO search);
	
	List<MtResultVO> selectTblDdList(MtResultVO search);
	
	List<MtResultVO> selectColDdList(MtResultVO search);

	List<MtResultVO> selectGovTblDdList(MtResultVO search);
	
	List<MtResultVO> selectGovColDdList(MtResultVO search);
}
