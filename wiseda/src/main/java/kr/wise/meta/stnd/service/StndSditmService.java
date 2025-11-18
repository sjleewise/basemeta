package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndSditmService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 3. 오후 2:16:20
 * </PRE>
 */ 
public interface StndSditmService {

	List<WamSditm> getStndItemList(WamSditm data);
	
	WamSditm selectSditmDetail(String sditmId);
	
	List<WamSditm> selectSditmChangeList(String sditmId);
	
	List<WamStwdCnfg> selectSditmInitList(String sditmId);
	
	List<WamWhereUsed> selectSditmWhereUsedList(String sditmId);
	
	List<WamSditm> getSditmTransList(WamSditm data);
	
	int saveSditmTransYnPrc(ArrayList<WamSditm> list);
	
	List<WamSditm> selectSditmComparisonList(String sditmId);

	List<HashMap> selectSditmDbmsDataTypeList(String sditmId);

	String selectGovYn(String sditmId);

	WamSditm selectGovSditmDetail(String sditmId); 
	
}
