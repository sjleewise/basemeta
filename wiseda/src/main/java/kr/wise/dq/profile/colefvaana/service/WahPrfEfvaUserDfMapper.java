package kr.wise.dq.profile.colefvaana.service;

import java.util.Date;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colefvaana.service.WahPrfEfvaUserDfVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfEfvaUserDfMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId,  @Param("expDtm") Date expDtm);

    int insert(WahPrfEfvaUserDfVO record);

    int insertSelective(WamPrfEfvaUserDfVO record);

    WahPrfEfvaUserDfVO selectByPrimaryKey(@Param("prfId") String prfId,  @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfEfvaUserDfVO record);

    int updateByPrimaryKey(WahPrfEfvaUserDfVO record);
    
    /*만료*/
    int updateWahPrfEfvaUserDfExpDtm(WamPrfEfvaUserDfVO record);
    
    int updateWahPrfExpDtm(String rqstNo);
    
    /*등록*/
    int insertWahPrfPC02UserDf(WaqMstr mstVO);
    
}