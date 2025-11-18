package kr.wise.commons.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.api.service.ApiMapper;
import kr.wise.commons.api.service.ApiService;
import kr.wise.commons.api.service.WamDdlColApi;
import kr.wise.commons.api.service.WamDdlTblApi;
import kr.wise.commons.api.service.WamDmnApi;
import kr.wise.commons.api.service.WamPdmColApi;
import kr.wise.commons.api.service.WamPdmTblApi;
import kr.wise.commons.api.service.WamSditmApi;
import kr.wise.commons.api.service.WamStwdApi;
import kr.wise.commons.api.service.WatDbcColApi;
import kr.wise.commons.api.service.WatDbcTblApi;
import kr.wise.commons.api.service.vStdTermApi;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.stnd.service.WamCdVal;

@Service("apiService")
public class ApiServiceImpl implements ApiService{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private ApiMapper mapper;
	
	@Override
	public List<WamPdmTblApi> getPdmTblList(Map<String, Object> param) {
		List<WamPdmTblApi> list = mapper.selectPdmTblList(param);
		return list;
	}

	@Override
	public int getPdmTblCount(Map<String, Object> param) {
		int count = mapper.selectPdmTblCnt(param);
		return count;
	}

	@Override
	public List<WamPdmColApi> getPdmColList(Map<String, Object> param) {
		List<WamPdmColApi> list = mapper.selectPdmColList(param);
		return list;
	}

	@Override
	public int getPdmColCount(Map<String, Object> param) {
		int count = mapper.selectPdmColCnt(param);
		return count;
	}


	@Override
	public List<WamStwdApi> getStndWordList(Map<String, Object> param) {
		List<WamStwdApi> list = mapper.selectStndWordList(param);
		return list;
	}
	
	@Override
	public int getStndWordCount(Map<String, Object> param) {
		int count = mapper.selectStndWordCnt(param);
		return count;
	}
	
	@Override
	public List<WamDmnApi> getDomainList(Map<String, Object> param) {
		List<WamDmnApi> list = mapper.selectDomainList(param);
		return list;
	}
	
	@Override
	public int getDomainCount(Map<String, Object> param) {
		int count = mapper.selectDomainCnt(param);
		return count;
	}

	@Override
	public List<WamSditmApi> getStndItemList(Map<String, Object> param) {
		List<WamSditmApi> list = mapper.selectSditmList(param);
		return list;
	}
	
	@Override
	public int getStndItemCount(Map<String, Object> param) {
		int count = mapper.selectSditmCnt(param);
		return count;
	}
	
	@Override
	public List<WamDdlTblApi> getDdlTblList(Map<String, Object> param) {
		List<WamDdlTblApi> list = mapper.selectDdlTblList(param);
		return list;
	}


	@Override
	public int getDdlTblCount(Map<String, Object> param) {
		int count = mapper.selectDdlTblCnt(param);
		return count;
	}

	@Override
	public List<WamDdlColApi> getDdlColList(Map<String, Object> param) {
		List<WamDdlColApi> list = mapper.selectDdlColList(param);
		return list;
	}

	@Override
	public int getDdlColCount(Map<String, Object> param) {
		int count = mapper.selectDdlColCnt(param);
		return count;
	}

	@Override
	public List<vStdTermApi> getVStdTermList(Map<String, Object> param) {
		List<vStdTermApi> list = mapper.selectVStdTermList(param);
		return list;
	}

	@Override
	public List<WatDbcTblApi> getDbcTblList(Map<String, Object> param) {
		
		List<WatDbcTblApi> list = mapper.selectDbcTblList(param);
		return list;
	}
	
	@Override
	public int getDbcTblCount(Map<String, Object> param) {
		int count = mapper.selectDbcTblCnt(param);
		return count;
	}

	@Override
	public List<WatDbcColApi> getDbcColList(Map<String, Object> param) {
		
		List<WatDbcColApi> list = mapper.selectDbcColList(param);
		return list;
	}
	
	@Override
	public int getDbcColCount(Map<String, Object> param) {
		int count = mapper.selectDbcColCnt(param);
		return count;
	}

	@Override
	public int getCdValCount(Map<String, Object> param) {
		int count = mapper.selectCdValCnt(param);
		return count;
	}

	@Override
	public List<WamCdVal> getCdValList(Map<String, Object> param) {
		List<WamCdVal> list = mapper.selectCdValList(param);
		return list;
	}
}
