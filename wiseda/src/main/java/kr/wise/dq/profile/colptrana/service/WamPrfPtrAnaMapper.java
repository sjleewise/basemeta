package kr.wise.dq.profile.colptrana.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.dq.profile.anares.service.WamPrfResultVO;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

@Mapper
public interface WamPrfPtrAnaMapper {
    int deleteByPrimaryKey(WamPrfPtrAnaVO recode);

    int insert(WamPrfPtrAnaVO record);

    int insertSelective(WamPrfPtrAnaVO record);

    WamPrfPtrAnaVO selectByPrimaryKeyByPrfKndCd(String prfId);
    
    List<WamPrfPtrAnaVO> selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfPtrAnaVO record);

    int updateByPrimaryKey(WamPrfPtrAnaVO record);
    
    int insertWamPrfPC05UserDfByPrfId(WamPrfPtrAnaVO record);

	List<WamPrfMstrCommonVO> profileListPtrAna(WamPrfMstrCommonVO search);
}