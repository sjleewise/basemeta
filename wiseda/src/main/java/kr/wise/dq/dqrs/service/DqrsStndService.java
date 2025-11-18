package kr.wise.dq.dqrs.service;

import java.util.ArrayList;
import java.util.List;

public interface DqrsStndService {
	List<DqrsSditm> getDqrsSditmLst(DqrsSditm data);
	int saveDqrsSditmLst(List<DqrsSditm> reglist) throws Exception;
	int delDqrsSditmLst(ArrayList<DqrsSditm> list) throws Exception;

	List<DqrsDmn> getDqrsDmnLst(DqrsDmn data);
	int saveDqrsDmnLst(ArrayList<DqrsDmn> list)throws Exception ;
	int delDqrsDmnLst(ArrayList<DqrsDmn> list) throws Exception;
	
	List<DqrsSditm> getDqrsPubSditmLst(DqrsSditm data);
	List<DqrsDmn> getDqrsPubDmnLst(DqrsDmn data);
	
	
	int batchDeleteDqrsSditmLst(String allDbms, String allDbmsSchId, String delType) throws Exception;
	
	//표준준수 갭 조회
	List<DqrsResult> getDqrsStndColGapLst(DqrsResult search);
	
}
