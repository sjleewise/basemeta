package kr.wise.advisor.prepare.udefoutlier.coldasedv.service;

import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDv;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcColDaseDvMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int insert(WadUodcColDaseDv record);

    int insertSelective(WadUodcColDaseDv record);

    WadUodcColDaseDv selectByPrimaryKey(WadUodcColDaseDv search);

    int updateByPrimaryKeySelective(WadUodcColDaseDv record);

    int updateByPrimaryKey(WadUodcColDaseDv record);

	int updateColDaseDvAnaDaseId(WadUodcColDaseDv mstData);

	WadUodcColDaseDv selectDetailInfo(WadUodcColDaseDv search);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);   

	
}