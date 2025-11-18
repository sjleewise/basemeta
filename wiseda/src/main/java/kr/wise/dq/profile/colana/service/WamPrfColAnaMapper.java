package kr.wise.dq.profile.colana.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPrfColAnaMapper {
    int deleteByPrimaryKey(String prfId);

    int insert(WamPrfColAnaVO record);

    int insertSelective(WamPrfMstrVO record);

    /*상세조회*/
    WamPrfColAnaVO selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfColAnaVO record);

    int updateByPrimaryKey(WamPrfColAnaVO record);
    
    /*삭제*/
    int deleteWamPrfColAna(WaqMstr mstVO);
    
    /*등록*/
    int insertWamPrfColAna(WaqMstr mstVO);
    
    /*등록*/
    int insertWamPrfPC01ByPrfId(String prfId);
    
    /* 컬럼분석 프로파일  조회 */
    List<WamPrfMstrCommonVO> profileListColAna(WamPrfMstrCommonVO search);
    
}