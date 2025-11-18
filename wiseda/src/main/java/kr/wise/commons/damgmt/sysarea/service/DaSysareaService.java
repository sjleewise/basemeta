package kr.wise.commons.damgmt.sysarea.service;

import java.util.ArrayList;
import java.util.List;

public interface DaSysareaService {
	
	public List<WaaSysArea> getSysareaList(WaaSysArea search);

	public int delsysarealist(WaaSysArea saveVO);
	
	public int regSysareaList(ArrayList<WaaSysArea> list) throws Exception;
	
//	public int regSysarea(WaaSysArea record) throws Exception;
	
	public int delSysareaList(ArrayList<WaaSysArea> list) throws Exception;
	
	public int regSysarea(WaaSysArea record, String uniqId) throws Exception;
	

}
