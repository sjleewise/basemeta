package kr.wise.dq.profile.colefvaana.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
@Mapper
public interface WamPrfEfvaAnaMapper {
    int deleteByPrimaryKey(String prfId);

    int insert(WamPrfEfvaAnaVO record);

    int insertSelective(WamPrfMstrVO record);

    WamPrfEfvaAnaVO selectByPrimaryKey(String prfId);

    int updateByPrimaryKeySelective(WamPrfEfvaAnaVO record);

    int updateByPrimaryKey(WamPrfEfvaAnaVO record);
    
    /*삭제*/
    //int deleteWamPrfPC02(WaqMstr mstVO);
    
    /*등록*/
    int insertWamPrfPC02(WaqMstr mstVO);
    
    /*등록*/
    int insertWamPrfPC02ByPrfId(String prfId);
    
    /* 유효값분석 프로파일  조회 */
    List<WamPrfMstrCommonVO> profileListCodeAna(WamPrfMstrCommonVO search);
}