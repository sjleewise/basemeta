package kr.wise.dq.gov.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.gov.service.GovTblMapper;
import kr.wise.dq.gov.service.GovTblService;
import kr.wise.dq.gov.service.GovTblVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("GovTblService")
public class GovTblServiceImpl implements GovTblService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private GovTblMapper govTblMapper;

	public List<GovTblVO> selectGovTblList(GovTblVO searchVO) {
		return govTblMapper.selectGovTblList(searchVO);
	}

	public GovTblVO selectGovTblDetail(String GovTblId) {
		return govTblMapper.selectGovTblDetail(GovTblId);
	}

	
	
	public int saveGovTbl(GovTblVO record) throws Exception {
		logger.debug("GovTblManageServiceImpl.Java saveGovTbl Method");
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		record.setWritUserId(userid);
		
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getColId())) {  // 신규...
			record.setColId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			result = govTblMapper.insertGovTbl(record);
			
		} else if("U".equals(tmpStatus)) {
			//
		}  else if ("D".equals(tmpStatus)) {

		result = govTblMapper.updateExpDtm(record);

		}
		
		record.setWritUserId(user.getUniqId());
		
		return result;
	}
	
	
	public int regGovTbl(ArrayList<GovTblVO> list) throws Exception {
		
		
		//=====================================
		int result = 0;
		for (GovTblVO record : list) {
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += saveGovTbl(record);
			}
		}
		return result;
	}


	@Override
	public int deleteGovTbl(ArrayList<GovTblVO> record) throws Exception {
		int result = 0;
		
		for(GovTblVO saveVo : record) {
			saveVo.setIbsStatus("D");
		}
		result = regGovTbl(record);
		
		
		return result;
	}


}