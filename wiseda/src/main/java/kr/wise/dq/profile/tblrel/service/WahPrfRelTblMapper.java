package kr.wise.dq.profile.tblrel.service;

import java.util.Date;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WahPrfRelTblVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfRelTblMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfRelTblVO record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfRelTblVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfRelTblVO record);

    int updateByPrimaryKey(WahPrfRelTblVO record);
}