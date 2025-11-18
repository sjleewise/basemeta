package kr.wise.dq.profile.coldtfrmana.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

@Mapper
public interface WamPrfDtfrmAnaMapper {
    int deleteByPrimaryKey(String prfId);

    int insert(WamPrfDtfrmAnaVO record);

    int insertSelective(WamPrfDtfrmAnaVO record);

    WamPrfDtfrmAnaVO selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfDtfrmAnaVO record);

    int updateByPrimaryKey(WamPrfDtfrmAnaVO record);
    
    int insertWamPrfPC03ByPrfId(String prfId);
    
    /* 날짜분석 프로파일  조회 */
    List<WamPrfMstrCommonVO> profileListDateAna(WamPrfMstrCommonVO search);
}