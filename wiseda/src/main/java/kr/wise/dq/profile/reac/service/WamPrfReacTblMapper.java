package kr.wise.dq.profile.reac.service;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPrfReacTblMapper {
    int deleteByPrimaryKey(String prfId);

    int insert(WamPrfReacTblVO record);

    int insertSelective(WamPrfReacTblVO record);

    WamPrfReacTblVO selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfReacTblVO record);

    int updateByPrimaryKey(WamPrfReacTblVO record);
    
    //등록
    int insertWamPrfReacByPrfId(String prfId);
}