package kr.wise.dq.vrfcrule.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageMapper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.vrfcrule.service.VrfcruleMapper;
import kr.wise.dq.vrfcrule.service.VrfcruleService;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("vrfcruleService")
public class VrfcruleServiceImpl implements VrfcruleService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private EgovIdGnrService vrfcIdGnrService; 
	
	@Inject
	private VrfcruleMapper vrfcMapper;

	public List<VrfcruleVO> selectVrfcList(VrfcruleVO searchVO) {
		return vrfcMapper.selectVrfcList(searchVO);
	}

	public VrfcruleVO selectVrfcDetail(String vrfcId) {
		return vrfcMapper.selectVrfcDetail(vrfcId);
	}

	
	
	public int saveVrfc(VrfcruleVO record, String sAction) throws Exception {
		logger.debug("VrfcManageServiceImpl.Java saveVrfc Method");
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getVrfcId())) {  // 신규...
			record.setVrfcId(vrfcIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			
		} else if("U".equals(tmpStatus) || "D".equals(tmpStatus) || StringUtils.hasText(record.getVrfcId())) {
			if ("I".equals(tmpStatus)) {
				record.setRegTypCd("U");
			} else {
				record.setRegTypCd(tmpStatus);
			}
			
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
		} 
		
		vrfcMapper.updateExpDtm(record);
		record.setWritUserId(user.getUniqId());
		result = vrfcMapper.insertVrfc(record);
		
		return result;
	}
	
	
	public int regVrfc(ArrayList<VrfcruleVO> list) throws Exception {
		
		
		//=====================================
		int result = 0;
		for (VrfcruleVO record : list) {
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += saveVrfc(record, null);
			}
		}
		return result;
	}


	@Override
	public int deleteVrfc(ArrayList<VrfcruleVO> record) {
		int result = 0;
		try {
			for (VrfcruleVO vrfcruleVO : record) {
				String id = vrfcruleVO.getVrfcId();
				if (id != null && !"".equals(id)) {
					vrfcruleVO.setIbsStatus("D");
					result += vrfcMapper.deleteVrfc(id);
				}
			}
		} catch (Exception e) {
			result = -1;
		}
		return result;
	}

}