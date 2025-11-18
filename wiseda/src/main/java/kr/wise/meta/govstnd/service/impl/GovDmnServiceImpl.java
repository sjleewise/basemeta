package kr.wise.meta.govstnd.service.impl;

import java.util.*;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.meta.stnd.service.AsisColItemMap;
import kr.wise.meta.stnd.service.AsisColItemMapMapper;
import kr.wise.meta.stnd.service.AsisVsStndDicService; 
import kr.wise.meta.govstnd.service.DiagDmnMapper;
import kr.wise.meta.govstnd.service.GovDmnService;
import kr.wise.meta.govstnd.service.GovDmnVo;
import kr.wise.meta.govstnd.service.GovDmnMapper;
import kr.wise.meta.govstnd.service.GovSditm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("govDmnService")
public class GovDmnServiceImpl implements GovDmnService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Inject
//	private DiagDmnMapper diagDmnMapper;
	
	@Inject
	private GovDmnMapper govDmnMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Override
	public List<GovDmnVo> getDomainList(GovDmnVo data) {
		return govDmnMapper.getDomainList(data);
	}


	@Override
	public int register(ArrayList<GovDmnVo> list) throws Exception {
		int result=0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String regUserId = user.getUniqId();
		
		if(list != null){
			for(GovDmnVo vo : list){
				
				vo.setRegUserId(regUserId);
				
				result += saveGovDmn(vo);
				
			}
		}		
		
		return result;
	}
	
	@Override
	public int delDiagDmnList(ArrayList<GovDmnVo> list) throws Exception {
		int result = 0;
		
		for(GovDmnVo saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = register(list);
		
		return result;

	}

	private int saveGovDmn(GovDmnVo saveVo) throws Exception{
		int result =0;
		String tmpstatus = saveVo.getIbsStatus();
		
//		 result += govDmnMapper.checkDmnLnm(saveVo);
		
		if("I".equals(tmpstatus)) {
			String id =  objectIdGnrService.getNextStringId();
			saveVo.setDmnId(id);
			saveVo.setRegTypCd("C");
			
			result = govDmnMapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)) {
			//String id =  objectIdGnrService.getNextStringId();
			//saveVo.setDmnId(id);
			saveVo.setRegTypCd("U");
			result = govDmnMapper.updateDiagDmnList(saveVo);

		} else if ("D".equals(tmpstatus)) {
			//String id =  objectIdGnrService.getNextStringId();
			//saveVo.setDmnId(id);
			//result = govDmnMapper.delDiagDmnList(saveVo);
			saveVo.setRegTypCd("D");
			result = govDmnMapper.updateDiagDmnList(saveVo);

		}
		
//		if(!"D".equals(tmpstatus)) {
//			check(saveVo);
//		}
		
		return result;
	}
	
	public int check(GovDmnVo saveVo){
		int result =0;
		
			result += govDmnMapper.checkVrfRmk(saveVo);
			result += govDmnMapper.updateDupDmn(saveVo);
		
		return result;
	}

	

}
