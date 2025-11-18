package kr.wise.commons.api.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.stnd.service.WamCdVal;

@Mapper
public interface ApiMapper {

	List<WamPdmTblApi> selectPdmTblList(Map<String, Object> param);

	int selectPdmTblCnt(Map<String, Object> param);

	List<WamPdmColApi> selectPdmColList(Map<String, Object> param);

	int selectPdmColCnt(Map<String, Object> param);

	List<WamStwdApi> selectStndWordList(Map<String, Object> param);
	
	int selectStndWordCnt(Map<String, Object> param);
	
	List<WamDmnApi> selectDomainList(Map<String, Object> param);
	
	int selectDomainCnt(Map<String, Object> param);
	
	List<WamSditmApi> selectSditmList(Map<String, Object> param);
	
	int selectSditmCnt(Map<String, Object> param);
	
	List<WamDdlTblApi> selectDdlTblList(Map<String, Object> param);

	int selectDdlTblCnt(Map<String, Object> param);

	List<WamDdlColApi> selectDdlColList(Map<String, Object> param);

	int selectDdlColCnt(Map<String, Object> param);

	List<vStdTermApi> selectVStdTermList(Map<String, Object> param);
	
	int selectDbcTblCnt(Map<String, Object> param);
	
	List<WatDbcTblApi> selectDbcTblList(Map<String, Object> param);

	int selectDbcColCnt(Map<String, Object> param);
	
	List<WatDbcColApi> selectDbcColList(Map<String, Object> param);
	
	int selectCdValCnt(Map<String, Object> param);
	
	List<WamCdVal> selectCdValList(Map<String, Object> param);
}
