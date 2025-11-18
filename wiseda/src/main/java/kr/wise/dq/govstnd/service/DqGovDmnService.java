package kr.wise.dq.govstnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DqGovDmnService {

	List<DqGovDmnVo> getDomainList(DqGovDmnVo data);
	
	int register(ArrayList<DqGovDmnVo> list)throws Exception ;
	
	int delDiagDmnList(ArrayList<DqGovDmnVo> list) throws Exception;


}
