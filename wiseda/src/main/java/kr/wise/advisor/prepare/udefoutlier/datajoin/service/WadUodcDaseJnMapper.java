package kr.wise.advisor.prepare.udefoutlier.datajoin.service;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadUodcDaseJnMapper {

	WadUodcDaseJn selectDetailInfo(WadUodcDaseJn search);

	int updateDaseJn(WadUodcDaseJn mstData);

	int insertDaseJn(WadUodcDaseJn mstData);

	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);

}
