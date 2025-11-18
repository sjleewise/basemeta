package kr.wise.dq.stnd.service;

import java.util.ArrayList;
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
public interface WdqStndSditmService {

	List<WdqmSditm> getStndItemList(WdqmSditm data);
	
	WdqmSditm selectSditmDetail(String sditmId);
	
	List<WdqmSditm> selectSditmChangeList(String sditmId);
	
	List<WdqmStwdCnfg> selectSditmInitList(String sditmId);
	
	List<WdqmWhereUsed> selectSditmWhereUsedList(String sditmId);
	
	List<WdqmSditm> getSditmTransList(WdqmSditm data);
	
	int saveSditmTransYnPrc(ArrayList<WdqmSditm> list);
	
	List<WdqmSditm> selectSditmComparisonList(String sditmId);
	
}
