package kr.wise.commons.user.service;

import kr.wise.commons.cmm.LoginVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface UserMapper {
	
	LoginVO getLoginUser(LoginVO loginvo) throws Exception;
	
	LoginVO getLoginUserSso(LoginVO loginvo) throws Exception;
	
	LoginVO getLoginUserDA (LoginVO loginvo) throws Exception;

	LoginVO selectLoginUserbyAdmin(WaaUser uservo) throws Exception;

	int selectIdCnt(LoginVO loginVO);

	String selectSaltKey(LoginVO loginVO);

	int updateFailCnt(LoginVO resultVO);

	int selectFailCnt(LoginVO vo);

	int selectLockCnt(LoginVO vo);

	void updateIsLock(LoginVO vo);

	void updateFailCnt1(LoginVO vo);

	void updatePwdEnc(LoginVO loginVO);
}
