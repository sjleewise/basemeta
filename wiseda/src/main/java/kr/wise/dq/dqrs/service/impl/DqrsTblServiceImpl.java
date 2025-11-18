package kr.wise.dq.dqrs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dqrs.service.DqrsResult;
import kr.wise.dq.dqrs.service.DqrsTbl;
import kr.wise.dq.dqrs.service.DqrsTblMapper;
import kr.wise.dq.dqrs.service.DqrsTblService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("dqrsTblServiceImpl")
public class DqrsTblServiceImpl implements DqrsTblService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private DqrsTblMapper dqrsTblMapper;

	
	@Override
	public List<DqrsTbl> getDqrsTblLst(DqrsTbl searchVO) {
		return dqrsTblMapper.selectDqrsTblLst(searchVO);
	}
	
	@Override
	public DqrsTbl getDqrsTblDtl(String DqrsTblId) {
		return dqrsTblMapper.selectDqrsTblDtl(DqrsTblId);
	}

	
	
	public int saveDqrsTbl(DqrsTbl record) throws Exception {
		logger.debug("DqrsTblManageServiceImpl.Java saveDqrsTbl Method");
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
			result = dqrsTblMapper.insertDqrsTbl(record);
			
		} else if("U".equals(tmpStatus)) {
			//
		}  else if ("D".equals(tmpStatus)) {

		result = dqrsTblMapper.updateExpDtm(record);

		}
		
		record.setWritUserId(user.getUniqId());
		
		return result;
	}
	
	@Override
	public int regDqrsTbl(ArrayList<DqrsTbl> list) throws Exception {
		//=====================================
		int result = 0;
		for (DqrsTbl record : list) {
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += saveDqrsTbl(record);
			}
		}
		return result;
	}


	@Override
	public int delDqrsTbl(ArrayList<DqrsTbl> record) throws Exception {
		int result = 0;
		
		for(DqrsTbl saveVo : record) {
			saveVo.setIbsStatus("D");
		}
		result = regDqrsTbl(record);
		
		
		return result;
	}
	
	public int check(DqrsTbl record) {
		int result = 0;
		
			result += dqrsTblMapper.checkVrfRmk(record);
			result += dqrsTblMapper.checkDupDqrsTbl(record);
			result += dqrsTblMapper.checkDbms(record);
			result += dqrsTblMapper.updateVrfRmk(record);
			
		return result;
	}

	
	///////////구조품질 갭 분석
	@Override
	public List<DqrsResult> getDqrsMdlGapLst(DqrsResult search) {
		// TODO Auto-generated method stub
		return dqrsTblMapper.selectDqrsMdlGapLst(search);
	}
	
	@Override
	public List<DqrsResult> getDqrsMdlColGapLst(DqrsResult search) {
		// TODO Auto-generated method stub
		return dqrsTblMapper.selectDqrsMdlColGapLst(search);
	}



}