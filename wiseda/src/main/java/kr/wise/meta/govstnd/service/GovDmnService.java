package kr.wise.meta.govstnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface GovDmnService {

	List<GovDmnVo> getDomainList(GovDmnVo data);
	
	int register(ArrayList<GovDmnVo> list)throws Exception ;
	
	int delDiagDmnList(ArrayList<GovDmnVo> list) throws Exception;


}
