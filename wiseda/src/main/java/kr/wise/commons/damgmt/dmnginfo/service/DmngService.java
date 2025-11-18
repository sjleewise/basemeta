package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmngService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 18. 오전 9:45:34
 * </PRE>
 */ 
public interface DmngService {
	
	public List<WaaDmng> getList(WaaDmng search) ;
	
	public WaaDmng findDmng(WaaDmng search) ;
	
	public int regDmng(WaaDmng record) throws Exception;

	public int regDmngList(ArrayList<WaaDmng> list, WaaBscLvl bscLvl) throws Exception ;


	public int delDmngList(ArrayList<WaaDmng> list);

	/** @param string
	/** @return yeonho */
	public WaaDmng getDmngId(String dmngLnm);
}
