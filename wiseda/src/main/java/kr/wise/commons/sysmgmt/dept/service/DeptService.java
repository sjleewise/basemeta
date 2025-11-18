package kr.wise.commons.sysmgmt.dept.service;

import java.util.ArrayList;
import java.util.List;


public interface DeptService {

	public List<WaaDept> getList(WaaDept search) ;

	public WaaDept findDept(WaaDept search) ;

	public int regDept(WaaDept record) throws Exception ;

	public int regList(ArrayList<WaaDept> list) throws Exception ;


	public int delList(ArrayList<WaaDept> list) throws Exception ;

	public int getDeptCnt(WaaDept waaDept);

}
