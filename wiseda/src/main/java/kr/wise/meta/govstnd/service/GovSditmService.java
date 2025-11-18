package kr.wise.meta.govstnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface GovSditmService {
	List<GovSditm> getdiagSditmList(GovSditm data);
	
	int register(List<GovSditm> reglist) throws Exception;
	
	int delDiagSditmList(ArrayList<GovSditm> list) throws Exception;
	
	List<HashMap> getInfoSys();
	
	int regAutoCreGovSdtimDmn (String dbSchId) throws Exception;
}
