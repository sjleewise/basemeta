/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UserService.java
 * 2. Package : kr.wise.cmvw.user.service
 * 3. Comment :
 * 4. 작성자  : (ycyoo)유연철
 * 5. 작성일  : 2013. 4. 24.
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 24. 		: 신규 개발.
 */
package kr.wise.commons.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.RSA;
import kr.wise.commons.cmm.security.UtilEncryption;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.sysmgmt.dept.service.WaaDeptMapper;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
//import seed.EncryptUtils;

/**
 * <PRE>
 * 1. ClassName : UserService
 * 2. Package  : kr.wise.cmvw.user.service
 * 3. Comment  :
 * 4. 작성자   : (ycyoo)유연철
 * 5. 작성일   : 2013. 4. 24.
 * </PRE>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaUserMapper mapper;
	
	@Inject
	private WaaDeptMapper deptMapper;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	@Override
	public List<WaaUser> getList(WaaUser search, String aes_key) {

		List<WaaUser> list = mapper.selectList(search);
		
		WaaUser user = null;
		UtilEncryption ue = null;
		try {
			ue = new UtilEncryption(aes_key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0; i<list.size(); i++) {
			user = list.get(i);
			try {
				if(user.getEmailAddr()!= null && !user.getEmailAddr().equals("")) {
					user.setEmailAddr(ue.decrypt(user.getEmailAddr()));
				}
				if(user.getUserTelno()!= null && !user.getUserTelno().equals("")) {
					user.setUserTelno(ue.decrypt(user.getUserTelno()));
				}
				if(user.getUserHtelno()!= null && !user.getUserHtelno().equals("")) {
					user.setUserHtelno(ue.decrypt(user.getUserHtelno()));
				}
				
				list.set(i, user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		for(int i=0; i< list.size(); i++){
//		     list.get(i).setLoginAcPwd(EncryptUtils.getDecData(list.get(i).getLoginAcPwd(), list.get(i).getLoginAcId()));	
//		}
		return list;
	}

	@Override
	public List<WaaUser> getListOrderByDeptNm(WaaUser search, String aes_key) {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		search.setUserId(user.getUniqId());
		

		List<WaaUser> list = mapper.selectListOrderByDeptNm(search);
		
		WaaUser user2 = null;
		UtilEncryption ue = null;
		try {
			ue = new UtilEncryption(aes_key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0; i<list.size(); i++) {
			user2 = list.get(i);
			try {
				if(user2.getEmailAddr()!= null && !user2.getEmailAddr().equals("")) {
					user2.setEmailAddr(ue.decrypt(user2.getEmailAddr()));
				}
				if(user2.getUserTelno()!= null && !user2.getUserTelno().equals("")) {
					user2.setUserTelno(ue.decrypt(user2.getUserTelno()));
				}
				if(user2.getUserHtelno()!= null && !user2.getUserHtelno().equals("")) {
					user2.setUserHtelno(ue.decrypt(user2.getUserHtelno()));
				}
				
				list.set(i, user2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public WaaUser findUser(WaaUser search, String aes_key) {
		WaaUser user = mapper.selectByPrimaryKey(search.getUserId());
		
		UtilEncryption ue = null;
		try {
			ue = new UtilEncryption(aes_key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(user.getEmailAddr()!= null && !user.getEmailAddr().equals("")) {
				user.setEmailAddr(ue.decrypt(user.getEmailAddr()));
			}
			if(user.getUserTelno()!= null && !user.getUserTelno().equals("")) {
				user.setUserTelno(ue.decrypt(user.getUserTelno()));
			}
			if(user.getUserHtelno()!= null && !user.getUserHtelno().equals("")) {
				user.setUserHtelno(ue.decrypt(user.getUserHtelno()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return user;
	}

	@Override
	public int regUser(WaaUser record, String aes_key) {
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
        String decPw="";
		//부서ID를 기반으로 데이터가 있는지 확인하여 없을경우 공백처리...
		if(record.getDeptId() != null && StringUtils.hasText(record.getDeptId())) {
			WaaDept tmpDeptVo = deptMapper.selectByPrimaryKey(record.getDeptId());
			if(tmpDeptVo == null || !tmpDeptVo.getDeptId().equals(record.getDeptId())) {
				record.setDeptId(null);
			}
		}
		
		//엑셀업로드관련 (부서명은 있는데 부서ID가 없는 경우 update 시켜주기)
		if(record.getDeptNm() != null && StringUtils.hasText(record.getDeptNm()) && record.getDeptId().equals("")){
			WaaDept deptvo = deptMapper.selectDeptIdByDeptNm(record.getDeptNm());
			if(deptvo != null ){
				record.setDeptId(deptvo.getDeptId());
			}
		}
		
		
		//사용자ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getUserId())) {  // 신규...
			
			record.setObjVers(1);
			record.setRegTypCd("C");
			record.setLoginAcPwd("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"); // 암호화된 "1"이 기본 비밀번호
			
			record.setSaltKey(UtilEncryption.getRandomStr(9));
			record.setLoginAcPwdEnc(KISA_SHA256.SHA256_Encrpyt("1" + record.getSaltKey())); // 암호화된 "1" + "wise1012"(salt)이 기본 비밀번호
			
//			if(WiseConfig.SECURITY_APPLY.equals("Y")){
//			    record.setLoginAcPwd(EncryptUtils.getEncData(record.getLoginAcPwd(),record.getLoginAcId()));
//		    }
		} else if("U".equals(tmpStatus) || StringUtils.hasText(record.getUserId())) {
			//사용자ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WaaUser tmpVO = mapper.selectByPrimaryKey(record.getUserId());
			if (tmpVO == null || !tmpVO.getUserId().equals(record.getUserId())) { 
				
				record.setObjVers(1);
				record.setRegTypCd("C");
				record.setLoginAcPwd("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"); // 암호화된 "1"이 기본 비밀번호
				record.setSaltKey(UtilEncryption.getRandomStr(9));
				record.setLoginAcPwdEnc(KISA_SHA256.SHA256_Encrpyt("1" + record.getSaltKey()));
			} else {
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(record.getObjVers()+1);
				}
//				if(WiseConfig.SECURITY_APPLY.equals("Y")){
//				  decPw = EncryptUtils.getDecData(record.getLoginAcPwd(), record.getLoginAcId());
//				  if(decPw==null||decPw.equals("")){
//			         record.setLoginAcPwd(seed.EncryptUtils.getEncData(record.getLoginAcPwd(), record.getLoginAcId()));
//				  }
//			    }
				// 수정 상태일때 insert시  기존 비밀번호가 전달될 수 있도록
				record.setLoginAcPwd(tmpVO.getLoginAcPwd());
				record.setSaltKey(tmpVO.getSaltKey());
				record.setLoginAcPwdEnc(tmpVO.getLoginAcPwdEnc());
				record.setRegTypCd("U");
				mapper.updateExpDtm(record);
			}
		} 
		
		try {
			UtilEncryption ue = null;
			ue = new UtilEncryption(aes_key);
			
			if(record.getEmailAddr()!=null && !record.getEmailAddr().equals(""))
				record.setEmailAddr(ue.encrypt((record.getEmailAddr())));
			if(record.getUserTelno()!= null && !record.getUserTelno().equals(""))
				record.setUserTelno(ue.encrypt(record.getUserTelno()));
			if(record.getUserHtelno()!= null && !record.getUserHtelno().equals(""))
				record.setUserHtelno(ue.encrypt(record.getUserHtelno()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		record.setUserId(record.getLoginAcId());		
		record.setRqstUserId(user.getUniqId());
		record.setAprvUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;

		
		
	}
	
	@Override
	public int userPwInit(WaaUser userVo) {
		// TODO Auto-generated method stub
		int result = 0;
		
		userVo.setLoginAcPwd("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"); // 암호화된 "1"이 기본 비밀번호
		userVo.setSaltKey(UtilEncryption.getRandomStr(9));
		userVo.setLoginAcPwdEnc(KISA_SHA256.SHA256_Encrpyt("1" + userVo.getSaltKey())); // 암호화된 "1" + "wise1012"(salt)이 기본 비밀번호
		userVo.setIsLock("N");
		userVo.setLoginFailCount(0);
		
		
		result = mapper.updateUserPwInit(userVo);
		return result;
	}

	@Override
	public int regList(ArrayList<WaaUser> list, String aes_key) {

		int result = 0;

		for (WaaUser waaUser : list) {
			result += regUser(waaUser, aes_key);
		}

		return result;

	}


	@Override
	public int delList(ArrayList<WaaUser> list) {

		int result = 0;

		for (WaaUser WaaUser : list) {
			String id = WaaUser.getUserId();
			if (id != null && !"".equals(id)) {
				WaaUser.setIbsStatus("D");

				result += mapper.updateExpDtm(WaaUser);
			}
		}

		return result;

	}

	/** insomnia 
	 * @throws Exception */
	public List<WaaUser> getUserListbyDept(WaaUser search, String aes_key) throws Exception {
		String userid = ((LoginVO) UserDetailHelper.getAuthenticatedUser()).getUniqId();
		search.setUserId(userid);

		//부서의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("DEPT");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
				
		search.setLvlList(lvlList);
		
		List<WaaUser> list = mapper.selectListbyDept(search);
		
		WaaUser user = null;
		UtilEncryption ue = null;
		try {
			ue = new UtilEncryption(aes_key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0; i<list.size(); i++) {
			user = list.get(i);
			try {
				if(user.getEmailAddr()!= null && !user.getEmailAddr().equals("")) {
					user.setEmailAddr(ue.decrypt(user.getEmailAddr()));
				}
				if(user.getUserTelno()!= null && !user.getUserTelno().equals("")) {
					user.setUserTelno(ue.decrypt(user.getUserTelno()));
				}
				if(user.getUserHtelno()!= null && !user.getUserHtelno().equals("")) {
					user.setUserHtelno(ue.decrypt(user.getUserHtelno()));
				}
				
				list.set(i, user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	/** yeonho */
	//유저 정보(사용자그룹 포함) 조회
	@Override
	public WaaUser getUserInfo(String userid) {
		return mapper.selectUserInfo(userid);
	}

	/** 15.10.29 pOOh */
	//사용자정보 변경
	@Override
	public int chngUserInfo(WaaUser record) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
//		record.setSaltKey(mapper.selectSaltKey(record.getUserId()));
		record.setSaltKey(UtilEncryption.getRandomStr(9));
		
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			record.setLoginAcPwdEnc(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd() + record.getSaltKey()));
			record.setLoginAcPwd(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd()));
	    }
		
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY==null || WiseConfig.SECURITY_APPLY.equals("N")){
			record.setLoginAcPwd(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd()));
			record.setLoginAcPwdEnc(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd() + record.getSaltKey()));
	    }
		
		record.setUserId(record.getUserId());		
		record.setRqstUserId(record.getUserId());
		record.setAprvUserId(record.getUserId());
		//logger.debug("serviceimpl");
		//logger.debug("{}", record);
		
		result = mapper.updateUserInfo(record);
		return result;
	}

	@Override
	public int getUserCnt(WaaUser waaUser) {
		// TODO Auto-generated method stub
		return mapper.selectUserCnt(waaUser);
	}

	@Override
	public int checkBfPw(LoginVO loginVO, String salt) {
		// TODO Auto-generated method stub
		WaaUser usrVo = new WaaUser();
		
		usrVo.setUserId(loginVO.getSecuredUsername());
		usrVo.setLoginAcPwdEnc(KISA_SHA256.SHA256_Encrpyt(loginVO.getSecuredBfPassword() + salt));
		usrVo.setLoginAcPwd(KISA_SHA256.SHA256_Encrpyt(loginVO.getSecuredBfPassword()));
		
		return mapper.selectBfPw(usrVo);
	}

	@Override
	public String selectSaltKey(String id) {
		// TODO Auto-generated method stub
		return mapper.selectSaltKey(id);
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return mapper.idCheck(userId);
	}

	@Override
	public void register(WaaUser record) throws Exception {
		
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			record.setLoginAcPwd(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd()));
	    }
		mapper.register(record);
	}

	@Override
	public void updateVerify(String userId) throws Exception {
		mapper.updateVerify(userId);
	}
}
