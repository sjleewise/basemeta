package kr.wise.advisor.prepare.udefoutlier.lof.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcLofMapper {

	WadUodcLof selectDetailInfo(WadUodcLof search);

	List<WadUodcLof> selectLofColList(WadUodcLof search);

	int updateLof(WadUodcLof mstData);

	int insertLof(WadUodcLof mstData);

	int updateLofAnaDaseId(WadUodcLof mstData);

	int updateLofAnaDaseColId(WadUodcLof mstData);

	List<WadUodcLof> selectUodcLofColLstForScrt(WadUodcLof search);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}
