package kr.wise.advisor.prepare.udefoutlier.elev.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcElevMapper {

	WadUodcElev selectDetailInfo(WadUodcElev search);

	List<WadUodcElev> selectElevColList(WadUodcElev search);

	int updateElev(WadUodcElev mstData);

	int insertElev(WadUodcElev mstData);

	int updateElevAnaDaseId(WadUodcElev mstData);

	int updateElevAnaDaseColId(WadUodcElev mstData);

	List<WadUodcElev> selectUodcElevColLstForScrt(WadUodcElev search);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}
