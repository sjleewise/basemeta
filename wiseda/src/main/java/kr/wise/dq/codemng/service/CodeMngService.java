package kr.wise.dq.codemng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.dq.bizrule.service.WamBrMstr;

public interface CodeMngService {

	int regCode(WaaCdRule saveVO, String saction) throws Exception;
	
	List<WaaCdRule> getCodeMngList(WaaCdRule search); 
	
	Map<String, String> delCodeList(ArrayList<WaaCdRule> list, WaaCdRule mstVo) throws Exception;
	
}
