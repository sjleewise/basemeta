package kr.wise.dq.profile.colana.service;

import java.util.Date;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WahPrfColAna;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfColAnaMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfColAna record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfColAna selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfColAna record);

    int updateByPrimaryKey(WahPrfColAna record);
    
    /*만료*/
    int updateWamPrfPC01(String prfId);
    
    /*만료*/
    int updateWahPrfColAna(WaqMstr mstVO);
    
    /*등록*/
    int insertWahPrfColAna(WaqMstr mstVO);
}