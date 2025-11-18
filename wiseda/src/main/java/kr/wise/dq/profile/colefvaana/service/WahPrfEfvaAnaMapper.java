package kr.wise.dq.profile.colefvaana.service;

import java.util.Date;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colefvaana.service.WahPrfEfvaAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfEfvaAnaMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfEfvaAnaVO record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfEfvaAnaVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfEfvaAnaVO record);

    int updateByPrimaryKey(WahPrfEfvaAnaVO record);
    
    /*만료*/
//    int updateWahPrfPC02(WaqMstr mstVO);
    
    /*등록*/
    int insertWahPrfPC02(WaqMstr mstVO);
}