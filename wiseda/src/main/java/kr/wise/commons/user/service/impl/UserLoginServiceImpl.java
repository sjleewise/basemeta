package kr.wise.commons.user.service.impl;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.UtilEncryption;
import kr.wise.commons.user.service.UserLoginService;
import kr.wise.commons.user.service.UserMapper;
import kr.wise.commons.user.service.WaaUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;






//import seed.EncryptUtils;
import kr.wise.commons.WiseConfig;

@Service("loginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private UserMapper	userMapper;

	public LoginVO actionLogin(LoginVO vo) throws Exception {
		
		/** 건강보험 포탈용 로그인 */
//		LoginVO loginVO = userMapper.getLoginUser(vo);

		//vo.setPassword(EncryptUtils.getEncData(vo.getPassword(), vo.getId()));
		/** WISE DA 용 로그인 */
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			vo.setPassword(KISA_SHA256.SHA256_Encrpyt(vo.getPassword()));
	    }
		LoginVO loginVO = userMapper.getLoginUserDA(vo);
    	
		// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
    		return loginVO;
    	} else {
    		loginVO = new LoginVO();
    	}
    	return loginVO;
	}
	
	public LoginVO actionLogin(LoginVO vo, String salt) throws Exception {
		int idCnt = idCnt(vo);
		
		if(idCnt < 1) {
			LoginVO loginVO = new LoginVO();
			loginVO.setErrType("iderror");
			return loginVO;
		}
		
		log.debug("idCnt >>> " + idCnt);
		
//		String salt_key = getSaltKey(vo);
		
		/** WISE DA 용 로그인 */
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			String pass = vo.getPassword();
			vo.setPassword(KISA_SHA256.SHA256_Encrpyt(vo.getPassword()));
			if(WiseConfig.ENC_YN.equals("Y")) {
				if(salt == null || salt.equals("")) {
					LoginVO loginVO = userMapper.getLoginUserDA(vo);
			    	
					// 3. 결과를 리턴한다.
			    	if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
			    		salt = UtilEncryption.getRandomStr(9);
			    		log.debug("pass >>> " + pass);
			    		loginVO.setPasswordEnc(KISA_SHA256.SHA256_Encrpyt(pass + salt));
			    		loginVO.setSaltKey(salt);
			    		userMapper.updatePwdEnc(loginVO);
			    	}
				}
				vo.setPasswordEnc(KISA_SHA256.SHA256_Encrpyt(pass + salt));
			}
			
	    }
		
		LoginVO loginVO = userMapper.getLoginUserDA(vo);
    	
		// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
    		userMapper.updateFailCnt(loginVO);
    		
    		return loginVO;
    	} else {
    		loginVO = new LoginVO();
    		
    		int fcnt = userMapper.selectFailCnt(vo);
    		int lcnt = userMapper.selectLockCnt(vo);
    		
        	if(fcnt >= lcnt-1) {
        		userMapper.updateIsLock(vo);
    			loginVO.setErrType("lockerror");
    		} else {
    			userMapper.updateFailCnt1(vo);
    			loginVO.setErrType("pwerror");
    		}
    	}
    	return loginVO;
	}

	public LoginVO actionSsoLogin(LoginVO vo) throws Exception {
		LoginVO loginVO = userMapper.getLoginUserSso(vo);
		// 3. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}
		return loginVO;
	}

	@Override
	public LoginVO actionLoginbyAdmin(WaaUser uservo) throws Exception {
		return userMapper.selectLoginUserbyAdmin(uservo);
	}

	@Override
	public int idCnt(LoginVO loginVO) {
		// TODO Auto-generated method stub
		return userMapper.selectIdCnt(loginVO);
	}

	@Override
	public String getSaltKey(LoginVO loginVO) {
		// TODO Auto-generated method stub
		return userMapper.selectSaltKey(loginVO);
	}

	@Override
	public int updateFailCnt(LoginVO resultVO) {
		// TODO Auto-generated method stub
		return userMapper.updateFailCnt(resultVO);
	}

	@Override
	public int selectFailCnt(LoginVO loginVO) {
		// TODO Auto-generated method stub
		return userMapper.selectFailCnt(loginVO);
	}

	@Override
	public int selectLockCnt(LoginVO loginVO) {
		// TODO Auto-generated method stub
		return userMapper.selectLockCnt(loginVO);
	}

	@Override
	public void updateIsLock(LoginVO loginVO) {
		// TODO Auto-generated method stub
		userMapper.updateIsLock(loginVO);
	}

	@Override
	public void updateFailCnt1(LoginVO loginVO) {
		// TODO Auto-generated method stub
		userMapper.updateFailCnt1(loginVO);
	}
}
