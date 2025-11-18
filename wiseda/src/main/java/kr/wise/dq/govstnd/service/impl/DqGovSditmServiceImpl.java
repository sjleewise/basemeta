package kr.wise.dq.govstnd.service.impl;

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
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.govstnd.service.DqGovDmnMapper;
import kr.wise.dq.govstnd.service.DqGovSditmService;
import kr.wise.dq.govstnd.service.DqGovSditm;
import kr.wise.dq.govstnd.service.DqGovSditmMapper;
import kr.wise.meta.stnd.service.WaqSditm;

@Service("dqGovSditmService")
public class DqGovSditmServiceImpl implements DqGovSditmService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqGovSditmMapper govSditmMapper;
	
	@Inject
	private DqGovDmnMapper govDmnMapper;
	
    @Inject
    private EgovIdGnrService objectIdGnrService;
	
	public List<DqGovSditm> getdiagSditmList(DqGovSditm data){
		return govSditmMapper.selectdiagSditmList(data);
	}
	
	/** 표준항목 요청서 리스트 저장 insomnia 
	 * @throws Exception 
	 */
	public int register(List<DqGovSditm> reglist) throws Exception {
		
		logger.debug("reglist:{}", reglist.toString());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		if(reglist != null) {
			for (DqGovSditm saveVo : (ArrayList<DqGovSditm>)reglist) {
				
				saveVo.setRegUserId(userid);

				//단건 저장...
				result += saveGovSditm(saveVo);
			}

		}

		return result;

	}

	/** @return insomnia 
	 * @throws Exception */
	public int saveGovSditm(DqGovSditm saveVo) throws Exception {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if("I".equals(tmpstatus)) {
			
			String id =  objectIdGnrService.getNextStringId();
			saveVo.setSditmId(id);
			
			result = govSditmMapper.insertDiagSditmList(saveVo);

		} else if ("U".equals(tmpstatus)) {
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			saveVo.setRegUserId(userid);
			result = govSditmMapper.updateDiagSditmList(saveVo);

		} else if ("D".equals(tmpstatus)) {

			result = govSditmMapper.deleteDiagSditmList(saveVo);

		}
		
		if(!"D".equals(tmpstatus)) {
			check(saveVo);
		}

//		//단어구성정보 셋팅(삭제 후 추가)
//		setStwdCnfg(saveVo);

		return result;
	}
	
	public int delDiagSditmList(ArrayList<DqGovSditm> list) throws Exception {
		
		int result = 0;
		
		for(DqGovSditm saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = register(list);
		
		return result;
	}
	
	@Override
	public List<HashMap> getInfoSys() {   

		return govSditmMapper.selectInfoSys();
	}
	
	public int check(DqGovSditm saveVo) {
		
		int result = 0;
		
		// Check
		result =+ govSditmMapper.checkVrfRmk(saveVo);
		result =+ govSditmMapper.checkDupGovSditm(saveVo);
		result =+ govSditmMapper.checkGovDmn(saveVo);
		result =+ govSditmMapper.updateNotDmn(saveVo);
		result =+ govSditmMapper.updateVrfRmk(saveVo);

		return result;
	}
	
	@Override
	public int regAutoCreGovSdtimDmn(String dbSchId) throws Exception {
		
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
//		String logId = govStndIdGnrService.getNextStringId();
		
		Map<String,String> saveTmp = new HashMap();
		
		saveTmp.put("dbSchId", dbSchId);
		saveTmp.put("userId", userid);
		
		govSditmMapper.deleteAutoCreGovSditm(saveTmp);
		result += govSditmMapper.insertAutoCreGovSditm(saveTmp);
		
		govDmnMapper.deleteAutoCreGovDmn(saveTmp);
		result = govDmnMapper.insertAutoCreGovDmn(saveTmp);
		
		govSditmMapper.updateSditmDmnId(dbSchId);
		
		return result;
	}
}
