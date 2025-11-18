package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;


/**
 * <PRE>
 * 1. ClassName : DdlGrtService
 * 2. FileName  : DdlGrtService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 08.
 * </PRE>
 */
public interface DdlGrtService {

	List<WamDdlGrt> getWamGrtList(WamDdlGrt search);
	
	WamDdlGrt getWamGrtDetail(String ddlGrtId, String rqstNo);
	
	List<WamDdlGrt> getGrtChangeList(WamDdlGrt search);

	WamDdlGrt selectDdlGrtInfo(String ddlGrtId, String rqstNo);

	WamDdlGrt getWahGrtDetail(WamDdlGrt grt);	
}
