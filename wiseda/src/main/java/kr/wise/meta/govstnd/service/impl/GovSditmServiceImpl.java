package kr.wise.meta.govstnd.service.impl;

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
import kr.wise.meta.govstnd.service.GovSditmService;
import kr.wise.meta.govstnd.service.GovDmnMapper;
import kr.wise.meta.govstnd.service.GovSditm;
import kr.wise.meta.govstnd.service.GovSditmMapper;
import kr.wise.meta.stnd.service.WaqSditm;

@Service("govSditmService")
public class GovSditmServiceImpl implements GovSditmService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private GovSditmMapper govSditmMapper;
	
	@Inject
	private GovDmnMapper govDmnMapper;
	
    @Inject
    private EgovIdGnrService objectIdGnrService;
	
	public List<GovSditm> getdiagSditmList(GovSditm data){
		return govSditmMapper.selectdiagSditmList(data);
	}
	
	/** 표준항목 요청서 리스트 저장 insomnia 
	 * @throws Exception 
	 */
	public int register(List<GovSditm> reglist) throws Exception {
		
		logger.debug("reglist:{}", reglist.toString());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		if(reglist != null) {
			for (GovSditm saveVo : (ArrayList<GovSditm>)reglist) {
				
				saveVo.setRegUserId(userid);
				
				//단건 저장...
				result += saveGovSditm(saveVo);
			}

		}

		return result;

	}

	/** @return insomnia 
	 * @throws Exception */
	public int saveGovSditm(GovSditm saveVo) throws Exception {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if("I".equals(tmpstatus)) {
			
			String id =  objectIdGnrService.getNextStringId();
			saveVo.setSditmId(id);
			saveVo.setRegTypCd("C");
			result = govSditmMapper.insertDiagSditmList(saveVo);

		} else if ("U".equals(tmpstatus)) {
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			saveVo.setRegUserId(userid);
			saveVo.setRegTypCd("U");
			result = govSditmMapper.updateDiagSditmList(saveVo);


		} else if ("D".equals(tmpstatus)) {

			result = govSditmMapper.deleteDiagSditmList(saveVo);

		}
		//삭제가 아닐때, 검증돌림.
		if(!"D".equals(tmpstatus)) {
			check(saveVo);
		}

//		//단어구성정보 셋팅(삭제 후 추가)
//		setStwdCnfg(saveVo);

		return result;
	}
	
	public int delDiagSditmList(ArrayList<GovSditm> list) throws Exception {
		
		int result = 0;
		
		for(GovSditm saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = register(list);
		
		return result;
	}
	
	@Override
	public List<HashMap> getInfoSys() {   

		return govSditmMapper.selectInfoSys();
	}
	
	public int check(GovSditm saveVo) {
		
		int result = 0;
		
		// Check 
		//result =+ govSditmMapper.checkVrfRmk(saveVo);
		//result =+ govSditmMapper.checkDupGovSditm(saveVo);
		result =+ govSditmMapper.checkGovDmn(saveVo);
		//result =+ govSditmMapper.updateNotDmn(saveVo);
		//result =+ govSditmMapper.updateVrfRmk(saveVo);

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
