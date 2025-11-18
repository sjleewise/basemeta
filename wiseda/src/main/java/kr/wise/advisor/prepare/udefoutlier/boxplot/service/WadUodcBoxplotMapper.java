package kr.wise.advisor.prepare.udefoutlier.boxplot.service;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadUodcBoxplotMapper {

	WadUodcBoxplot selectDetailInfo(WadUodcBoxplot search);

	int updateBoxplot(WadUodcBoxplot mstData);

	int insertBoxplot(WadUodcBoxplot mstData);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}
