package kr.wise.dq.profile.tblrel.service;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;

@Mapper
public interface WamPrfRelTblMapper {
    int deleteByPrimaryKey(String prfId);

    int insert(WamPrfRelTblVO record);

    int insertSelective(WamPrfRelTblVO record);

    WamPrfRelTblVO selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfRelTblVO record);

    int updateByPrimaryKey(WamPrfRelTblVO record);
    
    //등록
    int insertWamPrfPT01ByPrfId(String prfId);
}