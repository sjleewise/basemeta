package kr.wise.advisor.prepare.udefoutlier.saveres.service;

import kr.wise.advisor.prepare.udefoutlier.saveres.service.WadUodcSvRes;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcSvResMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int insert(WadUodcSvRes record);

    int insertSelective(WadUodcSvRes record);

    WadUodcSvRes selectByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int updateByPrimaryKeySelective(WadUodcSvRes record);

    int updateByPrimaryKey(WadUodcSvRes record);

	WadUodcSvRes selectUodcSvResDetail(WadUodcSvRes search);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);

	WadUodcSvRes selectUodcSaveResForScrt(WadUodcSvRes search); 

	  
}