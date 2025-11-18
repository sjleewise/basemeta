package kr.wise.advisor.prepare.udefoutlier.isolationforest.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcIsfrMapper {

	WadUodcIsfr selectDetailInfo(WadUodcIsfr search);

	List<WadUodcIsfr> selectIsfrColList(WadUodcIsfr search);

	int updateIsfr(WadUodcIsfr mstData);

	int insertIsfr(WadUodcIsfr mstData);

	int updateIsfrAnaDaseId(WadUodcIsfr mstData);

	int updateIsfrAnaDaseColId(WadUodcIsfr mstData);

	List<WadUodcIsfr> selectUodcIsfrColLstForScrt(WadUodcIsfr search);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}
