package kr.wise.advisor.prepare.udefoutlier.daserowdv.service;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcDaseRowDvMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int insert(WadUodcDaseRowDv record);

    int insertSelective(WadUodcDaseRowDv record);

    WadUodcDaseRowDv selectByPrimaryKey(WadUodcDaseRowDv record);

    int updateByPrimaryKeySelective(WadUodcDaseRowDv record);

    int updateByPrimaryKey(WadUodcDaseRowDv record);

	int updateDaseRowDvAnaDaseId(WadUodcDaseRowDv mstData);
	
	WadUodcDaseRowDv selectDetailInfo(WadUodcDaseRowDv search);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);   
}