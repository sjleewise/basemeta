package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmngInfotpMapService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 18. 오전 9:46:06
 * </PRE>
 */ 
public interface DmngInfotpMapService {
	public List<WaaDmngInfotpMap> getList(WaaDmngInfotpMap search) ;
	
	public WaaDmngInfotpMap findDmngInfotpMap(WaaDmngInfotpMap search) ;
	
	public int regdmngInfotpMap(WaaDmngInfotpMap record);
	
	public int deldmngInfotpMapList(ArrayList<WaaDmngInfotpMap> list) ;
	
	public int regDmngInfotpMapList(ArrayList<WaaDmngInfotpMap> list);
}
