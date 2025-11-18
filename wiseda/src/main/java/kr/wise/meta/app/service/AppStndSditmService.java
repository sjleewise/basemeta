package kr.wise.meta.app.service;

import java.util.List;

import kr.wise.meta.app.service.WamAppSditm;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AppStndSditmService.java
 * 3. Package  : kr.wise.meta.app.service
 * 4. Comment  : 
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */ 
public interface AppStndSditmService {

	List<WamAppSditm> getStndItemList(WamAppSditm data);
	
	WamAppSditm selectSditmDetail(String sditmId);
	
	List<WamAppSditm> selectSditmChangeList(String sditmId);

}
