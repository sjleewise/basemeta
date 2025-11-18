package kr.wise.advisor.prepare.udefoutlier.datajoin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadUodcDaseJnColMapper {

	List<WadUodcDaseJnCol> selectJnColList(WadUodcDaseJn search);

	int insertWadUodcDaseJnCol(WadUodcDaseJnCol mstData);

	String getJnColSno(WadUodcDaseJnCol mstData);

	int deleteJnCol(WadUodcDaseJnCol cndVo);

	List<WadUodcDaseJnCol> getJnColPnm(WadUodcDaseJnCol wadUodcDaseJnCol);

	int updateDaseJnAnaDaseId(WadUodcDaseJn mstData);

	int updateDaseJnAnaDaseColId(WadUodcDaseJn mstData);

	List<WadUodcDaseJnCol> selectUodcDaseJnColLstForScrt(WadUodcDaseJn search);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);

}
