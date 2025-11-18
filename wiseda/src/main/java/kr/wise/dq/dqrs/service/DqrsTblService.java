package kr.wise.dq.dqrs.service;

import java.util.ArrayList;
import java.util.List;

public interface DqrsTblService {

	List<DqrsTbl> getDqrsTblLst(DqrsTbl searchVO);

	DqrsTbl getDqrsTblDtl(String tblId);

	public int saveDqrsTbl(DqrsTbl record) throws Exception ;
	
	public int regDqrsTbl(ArrayList<DqrsTbl> saveVO) throws Exception;

	int delDqrsTbl(ArrayList<DqrsTbl> record) throws Exception;

	//구조품질 갭 분석
	List<DqrsResult> getDqrsMdlGapLst(DqrsResult search);

	List<DqrsResult> getDqrsMdlColGapLst(DqrsResult search);

}
