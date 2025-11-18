package kr.wise.advisor.prepare.udefoutlier.datafilter.service;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcDaseCndDvMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int insert(WadUodcDaseCndDv record);

    int insertSelective(WadUodcDaseCndDv record);

    WadUodcDaseCndDv selectByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int updateByPrimaryKeySelective(WadUodcDaseCndDv record);

    int updateByPrimaryKey(WadUodcDaseCndDv record);

	WadUodcDaseCndDv selectDetailInfo(WadUodcDaseCndDv search);

	int updateDaseCndDvAnaDaseId(WadUodcDaseCndDv mstData);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}