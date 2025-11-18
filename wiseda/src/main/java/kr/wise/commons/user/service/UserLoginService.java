package kr.wise.commons.user.service;

import kr.wise.commons.cmm.LoginVO;

public interface UserLoginService {
	
	LoginVO actionLogin(LoginVO vo) throws Exception;
	
	LoginVO actionLogin(LoginVO vo, String salt) throws Exception;

	LoginVO actionSsoLogin(LoginVO loginVO) throws Exception;

	LoginVO actionLoginbyAdmin(WaaUser uservo) throws Exception;

	int idCnt(LoginVO loginVO);

	String getSaltKey(LoginVO loginVO);

	int updateFailCnt(LoginVO resultVO);

	int selectFailCnt(LoginVO loginVO);

	int selectLockCnt(LoginVO loginVO);

	void updateIsLock(LoginVO loginVO);

	void updateFailCnt1(LoginVO loginVO);
}
