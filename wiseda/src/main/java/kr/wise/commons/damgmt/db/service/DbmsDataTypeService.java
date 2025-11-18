package kr.wise.commons.damgmt.db.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.damgmt.dmnginfo.service.WaaDmng;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbmsDataTypeService.java
 * 3. Package  : kr.wise.commons.damgmt.db.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 22. 오후 4:17:03
 * </PRE>
 */ 
public interface DbmsDataTypeService {
	
	List<WaaDbmsDataType> getList(WaaDbmsDataType record);

	int regDataTypeList(ArrayList<WaaDbmsDataType> list) throws Exception;

	int delDataTypeList(ArrayList<WaaDbmsDataType> list);
	
	public int regDataType(WaaDbmsDataType record) throws Exception;
}
