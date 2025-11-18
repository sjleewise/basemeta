package kr.wise.commons.api.service;

import java.util.List;
import java.util.Map;

import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.stnd.service.WamCdVal;


public interface ApiService {
	
	public List<WamPdmTblApi> getPdmTblList(Map<String, Object> param) ;

	public int getPdmTblCount(Map<String, Object> param) ;

	public List<WamPdmColApi> getPdmColList(Map<String, Object> param) ;

	public int getPdmColCount(Map<String, Object> param) ;
	
	public List<WamStwdApi> getStndWordList(Map<String, Object> param);
	
	public int getStndWordCount(Map<String, Object> param) ;
	
	public List<WamDmnApi> getDomainList(Map<String, Object> param);
	
	public int getDomainCount(Map<String, Object> param) ;

	public List<WamSditmApi> getStndItemList(Map<String, Object> param);
	
	public int getStndItemCount(Map<String, Object> param) ;
	
	public List<WamDdlTblApi> getDdlTblList(Map<String, Object> param) ;

	public int getDdlTblCount(Map<String, Object> param) ;

	public List<WamDdlColApi> getDdlColList(Map<String, Object> param) ;

	public int getDdlColCount(Map<String, Object> param) ;
	
	public List<vStdTermApi> getVStdTermList(Map<String, Object> param);
	
	public int getDbcTblCount(Map<String, Object> param) ;
	
	public List<WatDbcTblApi> getDbcTblList(Map<String, Object> param);
	
	public int getDbcColCount(Map<String, Object> param) ;
	
	public List<WatDbcColApi> getDbcColList(Map<String, Object> param);
	
	public int getCdValCount(Map<String, Object> param);
	
	public List<WamCdVal> getCdValList(Map<String, Object> param);
	
	
}
