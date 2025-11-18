package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndSymnService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 9. 오후 1:54:00
 * </PRE>
 */ 
public interface StndSymnService {
	WamSymn selectSymnDetail(String symnId);
	
	List<WamSymn> selectSymnList(WamSymn search);

	int saveSymnRow(WamSymn saveVO) throws Exception;
	
	int deleteSymnList(ArrayList<WamSymn> list) throws Exception;
	
	int saveSymnList(ArrayList<WamSymn> list) throws Exception ;

	int deleteSymn(WamSymn record);

	List<WamSymn> selectSymnChangeList(String symnId);

	int getSymnCnt(WamSymn saveVO);
	
	
}
