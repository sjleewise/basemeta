package kr.wise.dq.codemng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.codemng.service.CodeMngService;
import kr.wise.dq.codemng.service.WaaCdRule;
import kr.wise.dq.codemng.service.WaaCdRuleMapper;
import kr.wise.commons.cmm.service.EgovIdGnrService;



@Service("CodeMngService")
public class CodeMngServiceImpl implements CodeMngService{
	
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private EgovIdGnrService objectIdGnrService;
	@Inject
	private WaaCdRuleMapper waaCdRuleMapper;

	@Override
	public int regCode(WaaCdRule saveVO, String saction) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String id = objectIdGnrService.getNextStringId();
		
		if(saction == null || saction.equals("") || saction.equals("I")) {
			saction = "I";
			saveVO.setRegTypCd("C");
			saveVO.setCdRuleId(id);
		} else if(saction.equals("U")) {
			saveVO.setRegTypCd("U");
		}
		
		int result = 0;
				
		//요청자 셋팅
		saveVO.setFrsRqstUserId(userid);
		saveVO.setRqstUserId(userid);
		saveVO.setIbsStatus(saction);
		
		//WAQ_BR_MSTR 등록
		result += saveWaaCdRule(saveVO);
		
		return result;
	}
	
	
	
	public int saveWaaCdRule(WaaCdRule record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();
		
		if("I".equals(tmpststus)) {
			result = waaCdRuleMapper.insertSelective(record);
//			result += waqBrDqiMapMapper.insertWamDqi(record);
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			result = waaCdRuleMapper.updateByPrimaryKeySelective(record);
//			result += waqBrDqiMapMapper.updateWamDqi(record);
		} else if ("D".equals(tmpststus)){
			result = waaCdRuleMapper.deleteByPrimaryKey(record);
//			result += waqBrDqiMapMapper.deleteWamDqi(record);
		}
		
		return result;
	}
	@Override
	public List<WaaCdRule> getCodeMngList(WaaCdRule search) {
		return waaCdRuleMapper.selectCodeMngList(search);
	}
	
	
	/** 코드 삭제 */
	@Override
	public Map<String, String> delCodeList(ArrayList<WaaCdRule> list, WaaCdRule mstVo) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int res = 0;
				
		for (WaaCdRule record : list) {
			record.setIbsStatus("D");
			//WAQ 영역 삭제
			res += saveWaaCdRule(record);
		}
		
		resultMap.put("result", Integer.toString(res) );
    	
		return resultMap;
	}
}
