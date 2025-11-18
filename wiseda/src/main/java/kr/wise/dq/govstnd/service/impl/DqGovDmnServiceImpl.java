package kr.wise.dq.govstnd.service.impl;

import java.util.*;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.meta.stnd.service.AsisColItemMap;
import kr.wise.meta.stnd.service.AsisColItemMapMapper;
import kr.wise.meta.stnd.service.AsisVsStndDicService; 
//import kr.wise.dq.govstnd.service.DiagDmnMapper;
import kr.wise.dq.govstnd.service.DqGovDmnService;
import kr.wise.dq.govstnd.service.DqGovDmnVo;
import kr.wise.dq.govstnd.service.DqGovDmnMapper;
import kr.wise.dq.govstnd.service.DqGovSditm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("dqGovDmnService")
public class DqGovDmnServiceImpl implements DqGovDmnService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Inject
//	private DiagDmnMapper diagDmnMapper;
	
	@Inject
	private DqGovDmnMapper govDmnMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Override
	public List<DqGovDmnVo> getDomainList(DqGovDmnVo data) {
		return govDmnMapper.getDomainList(data);
	}


	@Override
	public int register(ArrayList<DqGovDmnVo> list) throws Exception {
		int result=0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String regUserId = user.getUniqId();
		
		if(list != null){
			for(DqGovDmnVo vo : list){
				
				vo.setRegUserId(regUserId);
				
				result += saveGovDmn(vo);
				
			}
		}		
		
		return result;
	}
	
	@Override
	public int delDiagDmnList(ArrayList<DqGovDmnVo> list) throws Exception {
		int result = 0;
		
		for(DqGovDmnVo saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = register(list);
		
		return result;

	}

	private int saveGovDmn(DqGovDmnVo saveVo) throws Exception{
		int result =0;
		String tmpstatus = saveVo.getIbsStatus();
		
//		 result += govDmnMapper.checkDmnLnm(saveVo);
		
		if("I".equals(tmpstatus)) {
			String id =  objectIdGnrService.getNextStringId();
			saveVo.setDmnId(id);
			
			result = govDmnMapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)) {
			result = govDmnMapper.updateDiagDmnList(saveVo);

		} else if ("D".equals(tmpstatus)) {
			saveVo.setRegTypCd("D");
			result = govDmnMapper.updateDiagDmnList(saveVo);

		}
		
//		if(!"D".equals(tmpstatus)) {
//			check(saveVo);
//		}
		
		return result;
	}
	
	public int check(DqGovDmnVo saveVo){
		int result =0;
		
			result += govDmnMapper.checkVrfRmk(saveVo);
			result += govDmnMapper.updateDupDmn(saveVo);
		
		return result;
	}

	

}
