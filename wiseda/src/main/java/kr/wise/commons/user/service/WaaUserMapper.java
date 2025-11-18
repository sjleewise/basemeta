package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.user.WaaUserg;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaUserMapper {

	int deleteAll();

	int deleteByPrimaryKey(String userId);

    int insert(WaaUser record);

    int insertSelective(WaaUser record);

    WaaUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(WaaUser record);

    int updateByPrimaryKey(WaaUser record);

    int deleteRegTypCd(WaaUser record);

    List<WaaUser> selectList(WaaUser record);

    List<WaaUser> selectListbyDept(WaaUser record);

    List<WaaUser> selectListOrderByDeptNm(WaaUser record);

	int updateExpDtm(WaaUser record);

	/** @param userid
	/** @return yeonho */
	WaaUser selectUserInfo(String userid);

    /** 15.10.29 pOOh */
    int updateUserInfo(WaaUser record);
    
    int updateUserPwInit(WaaUser userVo);

	int selectUserCnt(WaaUser waaUser);

	String selectSaltKey(String userId);

	int selectBfPw(WaaUser userVo);

	//??? ???
    int idCheck(String userId);
    
    //??? ????
    void register(WaaUser record);
    
    //????? ????
    void updateVerify(String userId);

}