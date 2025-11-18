package kr.wise.dq.dqrs.service;

import java.util.ArrayList;
import java.util.List;

public interface DqrsResultService {

	List<DqrsResult> getDqrsResult(DqrsResult search);

	DqrsPoiResult getAnaExecuteStatus(DqrsPoiResult search);

	DqrsDbConnTrgVO getByDbms(DqrsDbConnTrgVO search);

	DqrsPoiResult getTargetTableStatus(DqrsPoiResult search);

	List<DqrsPoiResult> getValAnaResultList(DqrsPoiResult search);

	List<DqrsPoiResult> getTargetTableList(DqrsPoiResult search);

	List<DqrsPoiResult> getDmnList(DqrsPoiResult search);

	List<DqrsPoiResult> getRefIgList(DqrsPoiResult search);

	List<DqrsPoiResult> getBrList(DqrsPoiResult search);

	List<DqrsPoiResult> getExcInfoList(DqrsPoiResult search);

	List<DqrsPoiResult> getExcErrInfoList(DqrsPoiResult search);

	String getDbConnTrgURL(DqrsPoiGapResult search);

	DqrsPoiGapResult getGovCnt(DqrsPoiGapResult search);

	DqrsPoiGapResult getStndTerm(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovSditmTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovSditmList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovDmnList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getStructResultList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getDbcTblList(DqrsPoiGapResult search);

	DqrsPoiGapResult getModelTerm(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getDbcColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getPdmTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getPdmColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getTblDbList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getColDbList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovStructResultList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovDbcTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovDbcColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovTblList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovColList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovTblDbList(DqrsPoiGapResult search);

	List<DqrsPoiGapResult> getGovColDbList(DqrsPoiGapResult search);

	List<DqrsPoiResult> getExcErrInfoList(DqrsPoiResult data, boolean isDetail);

	List<DqrsDbConnTrgVO> getInfoSys(DqrsDbConnTrgVO search);

	int updateInfoSys(ArrayList<DqrsDbConnTrgVO> list);
	
	
}
