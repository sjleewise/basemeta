package kr.wise.advisor.prepare.udefoutlier.datacleansing.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcDataClnColMapper {

	String getDataClnColSno(WadUodcDataClnCol wadUodcDataClnCol);

	int insertWadUodcDataClnCol(WadUodcDataClnCol wadUodcDataClnCol);

	int deleteDataClnCol(WadUodcDataClnCol cndVo);

	int updateDataClnAnaDaseId(WadUodcDataCln mstData);

	int updateDaseClnAnaDaseColId(WadUodcDataCln mstData);

	List<WadUodcAnaDaseCol> selectAnaDaseColList(WadUodcDaseCndDv param);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);

	List<WadUodcDataClnCol> selectClnColList(WadUodcDataClnCol search);

}
