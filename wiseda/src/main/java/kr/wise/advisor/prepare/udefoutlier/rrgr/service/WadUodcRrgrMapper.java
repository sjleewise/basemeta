package kr.wise.advisor.prepare.udefoutlier.rrgr.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcRrgrMapper {

	WadUodcRrgr selectDetailInfo(WadUodcRrgr search);

	List<WadUodcRrgr> selectRrgrColList(WadUodcRrgr search);

	int updateRrgr(WadUodcRrgr mstData);

	int insertRrgr(WadUodcRrgr mstData);

	int updateRrgrAnaDaseId(WadUodcRrgr mstData);

	int updateRrgrAnaDaseColId(WadUodcRrgr mstData);

	List<WadUodcRrgr> selectUodcRrgrColLstForScrt(WadUodcRrgr search);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}
