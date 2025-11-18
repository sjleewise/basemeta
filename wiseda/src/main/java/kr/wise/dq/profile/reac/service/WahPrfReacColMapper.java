package kr.wise.dq.profile.reac.service;

import java.util.Date;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WahPrfReacColMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("relColSno") Integer relColSno, @Param("expDtm") Date expDtm);

    int insert(WahPrfReacColVO record);

    int insertSelective(WamPrfReacColVO record);

    WahPrfReacColVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("relColSno") Integer relColSno, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfReacColVO record);

    int updateByPrimaryKey(WahPrfReacColVO record);
    
    //이력테이블 만료
    int updateWahPrf(WamPrfReacColVO record);
    
}