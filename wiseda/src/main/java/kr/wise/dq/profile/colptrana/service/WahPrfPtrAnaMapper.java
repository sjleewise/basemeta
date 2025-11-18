package kr.wise.dq.profile.colptrana.service;

import java.util.Date;

import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WahPrfPtrAnaVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfPtrAnaMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfPtrAnaVO record);

//    int insertSelective(WahPrfPtrAnaVO record);

    int insertSelective(WamPrfPtrAnaVO record);
    
    WahPrfPtrAnaVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfPtrAnaVO record);

    int updateByPrimaryKey(WahPrfPtrAnaVO record);
    
    /* 사용자정의 패턴 만료*/
    int updateWahPrfPtrUserDfExpDtmByPrfId(String prfId);
    
    /* 사용자정의 패턴 만료*/
    int updateWahPrfPtrUserDfExpDtm(WamPrfPtrAnaVO record );
}