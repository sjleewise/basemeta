package kr.wise.advisor.prepare.udefoutlier.ocv.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcOcvMapper {

	WadUodcOcv selectDetailInfo(WadUodcOcv search);

	List<WadUodcOcv> selectOcvColList(WadUodcOcv search);

	int updateOcv(WadUodcOcv mstData);

	int insertOcv(WadUodcOcv mstData);

	int updateOcvAnaDaseId(WadUodcOcv mstData);

	int updateOcvAnaDaseColId(WadUodcOcv mstData);

	List<WadUodcOcv> selectUodcOcvColLstForScrt(WadUodcOcv search);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}
