package kr.wise.dq.dqrs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqrsResultMapper {

	List<DqrsResult> selectDqrsResult(DqrsResult search);

	DqrsPoiResult selectAnaExecuteStatus(DqrsPoiResult search);

	DqrsDbConnTrgVO selectByDbms(DqrsDbConnTrgVO search);

	DqrsPoiResult selectAnaTargetTableStatus(DqrsPoiResult search);

	List<DqrsPoiResult> selectValAnaResultList(DqrsPoiResult search);

	List<DqrsPoiResult> selectAnaTargetTableList(DqrsPoiResult search);

	List<DqrsPoiResult> selectDmnList(DqrsPoiResult search);

	List<DqrsPoiResult> selectRefItegrityList(DqrsPoiResult data);

	List<DqrsPoiResult> selectBrList(DqrsPoiResult search);

	List<DqrsPoiResult> selectExcInfoList(DqrsPoiResult search);

	List<DqrsPoiResult> selectPrfInfo(DqrsPoiResult data);

	//////////표준/구조 품질 POI 다운로드
	String selectDbConnTrgURL(DqrsPoiGapResult search);

	DqrsPoiGapResult selectGovCnt(DqrsPoiGapResult search);

	DqrsPoiGapResult selectStndDtm(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovSditmTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovSditmList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovDmnList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectStructResultList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectDbcTblList(DqrsPoiGapResult search);

	DqrsPoiGapResult selectModelDtm(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectDbcColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectPdmTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectPdmColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectTblDbList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectColDbList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovStructResultList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovDbcTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovDbcColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovTblDbList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> selectGovColDbList(DqrsPoiGapResult search);

	List<DqrsDbConnTrgVO> selectInfoSys(DqrsDbConnTrgVO search);

	int updateInfoSys(DqrsDbConnTrgVO saveVo);
	
}
