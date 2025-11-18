package kr.wise.dq.profile.colrngana.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

@Mapper
public interface WamPrfRngAnaMapper {
    int deleteByPrimaryKey(String prfId);

    int insert(WamPrfRngAnaVO record);

    int insertSelective(WamPrfRngAnaVO record);

    WamPrfRngAnaVO selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfRngAnaVO record);

    int updateByPrimaryKey(WamPrfRngAnaVO record);
    
    
    int insertWamPrfPC04ByPrfId(String prfId);
    
    //범위분석 프로파일 조회
    List<WamPrfMstrCommonVO> profileListRangeAna(WamPrfMstrCommonVO search);
}