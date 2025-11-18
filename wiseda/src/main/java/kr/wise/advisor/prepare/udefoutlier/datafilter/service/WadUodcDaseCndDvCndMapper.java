package kr.wise.advisor.prepare.udefoutlier.datafilter.service;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCnd;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.helper.grid.IBSheetListVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcDaseCndDvCndMapper {
	//매개변수명과 실제 쿼리에서 파라미터로 받는 변수명이 달라서 매개변수명 변경
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId, @Param("cndSno") Integer cndSno);

    int insert(WadUodcDaseCndDvCnd record);

    int insertSelective(WadUodcDaseCndDvCnd record);

    WadUodcDaseCndDvCnd selectByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId, @Param("jnSno") Integer jnSno);

    int updateByPrimaryKeySelective(WadUodcDaseCndDvCnd record);

    int updateByPrimaryKey(WadUodcDaseCndDvCnd record);

	int deleteCreCompId(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	List<WadUodcDaseCndDvCnd> selectUodcDataFilterColList(WadUodcDaseCndDv search);

	List<WadUodcDaseCndDvCnd> selectUodcDaseCndDvCndForScrt(WadUodcDaseCndDv search);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId); 
	
}