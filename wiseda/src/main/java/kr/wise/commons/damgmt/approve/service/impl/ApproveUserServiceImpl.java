package kr.wise.commons.damgmt.approve.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.ApproveUserService;
import kr.wise.commons.damgmt.approve.service.WaaAprg;
import kr.wise.commons.damgmt.approve.service.WaaAprgUser;
import kr.wise.commons.damgmt.approve.service.WaaAprgUserMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("approveUserService")
public class ApproveUserServiceImpl implements ApproveUserService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaAprgUserMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	public List<WaaAprgUser> getListById(String aprgId) {
		logger.debug("getListByAprgId");
		List<WaaAprgUser> list = mapper.selectListById(aprgId, null);
		logger.debug("{}", list);
		return list;

	}



	@Override
	public WaaAprg findAprg(WaaAprgUser search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int regAprgUser(WaaAprgUser record, String aprgId) throws Exception {
		logger.debug("ApproveUserServiceImpl.Java regAprgUser Method");
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) || "U".equals(tmpStatus)) {  // 신규...
			
			record.setRegTypCd("C");
			record.setObjVers(1);
			
		} else if("D".equals(tmpStatus)) {
				record.setRegTypCd("D");
			
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
			//result = menuMapper.updateByPrimaryKeySelective(record);
		} 
		record.setAprgId(aprgId);
		mapper.updateExpDtm(record);
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}

	@Override
	public int regList(ArrayList<WaaAprgUser> list, String aprgId) throws Exception {
		int result = 0;

		for (WaaAprgUser record : list) {

			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += regAprgUser(record, aprgId);
			}

		}

		return result;
	}

	@Override
	public int delList(ArrayList<WaaAprgUser> list) throws Exception {
		int result = 0;
		for (WaaAprgUser WaaAprgUser : list) {
			String aprgId = WaaAprgUser.getAprgId();
			String userId = WaaAprgUser.getUserId();
			if (aprgId != null && !"".equals(aprgId) && userId != null && !"".equals(userId)) {
				WaaAprgUser.setIbsStatus("D");

				result += regAprgUser(WaaAprgUser, aprgId);
			}
		}

		return result;
	}



	@Override
	public List<WaaAprgUser> getList(WaaAprgUser search) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<WaaAprgUser> getListById(String aprgId, String userNm) {
		logger.debug("getListById");
		List<WaaAprgUser> list = mapper.selectListById(aprgId, userNm);
		logger.debug("{}", list);
		return list;
	}

}
