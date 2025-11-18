package kr.wise.advisor.prepare.udefoutlier.coldasedv.service;

import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvCol;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcColDaseDvColMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId, @Param("colSno") Integer colSno);

    int insert(WadUodcColDaseDvCol record);

    int insertSelective(WadUodcColDaseDvCol record);

    WadUodcColDaseDvCol selectByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId, @Param("colSno") Integer colSno);

    int updateByPrimaryKeySelective(WadUodcColDaseDvCol record);

    int updateByPrimaryKey(WadUodcColDaseDvCol record);

	List<WadUodcColDaseDv> selectUodcColDaseDvColList(WadUodcColDaseDv search);

	int deleteCreCompId(WadUodcColDaseDv mstData);

	List<WadUodcColDaseDvCol> selectUodcColDaseDvColLstForScrt(WadUodcColDaseDv search);

	int delteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);   
}