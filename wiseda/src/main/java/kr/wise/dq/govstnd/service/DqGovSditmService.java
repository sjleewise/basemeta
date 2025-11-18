package kr.wise.dq.govstnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DqGovSditmService {
	List<DqGovSditm> getdiagSditmList(DqGovSditm data);
	
	int register(List<DqGovSditm> reglist) throws Exception;
	
	int delDiagSditmList(ArrayList<DqGovSditm> list) throws Exception;
	
	List<HashMap> getInfoSys();
	
	int regAutoCreGovSdtimDmn (String dbSchId) throws Exception;
}
