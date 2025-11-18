package kr.wise.dq.profile.reac.service;

import java.util.Date;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WahPrfReacTblMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfReacTblVO record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfReacTblVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfReacTblVO record);

    int updateByPrimaryKey(WahPrfReacTblVO record);
}