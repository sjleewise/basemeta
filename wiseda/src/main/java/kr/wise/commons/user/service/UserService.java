package kr.wise.commons.user.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.cmm.LoginVO;

public interface UserService {

	public List<WaaUser> getList(WaaUser search, String aes_key) ;

	public List<WaaUser> getListOrderByDeptNm(WaaUser search, String aes_key) ;

	public WaaUser findUser(WaaUser search, String aes_key) ;

	public int regUser(WaaUser record, String aes_key) ;

	public int regList(ArrayList<WaaUser> list, String aes_key) ;


	public int delList(ArrayList<WaaUser> list) ;

	/** @param search
	/** @return insomnia 
	 * @throws Exception */
	public List<WaaUser> getUserListbyDept(WaaUser search, String aes_key) throws Exception;

	WaaUser getUserInfo(String userid);
	
		/** 15.10.29 pOOh */
	public int chngUserInfo(WaaUser record) ;
	
	public int userPwInit(WaaUser userVo);

	public int getUserCnt(WaaUser waaUser);

	public int checkBfPw(LoginVO loginVO, String salt);

	public String selectSaltKey(String id);

	int idCheck(String userId) throws Exception;
	
	void register(WaaUser record) throws Exception;
	
	void updateVerify(String userId) throws Exception;
}
