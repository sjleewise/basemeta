package kr.wise.advisor.prepare.udefoutlier.boxplot.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadUodcBoxplotColMapper {

	List<WadUodcBoxplotCol> selectBoxplotColList(WadUodcBoxplot search);

	int deleteBoxplotCol(WadUodcBoxplotCol bpVo);

	int insertWadUodcBoxplotCol(WadUodcBoxplotCol wadUodcBoxplotCol);

	int updateBoxplotAnaDaseId(WadUodcBoxplot mstData);

	int updateBoxplotAnaDaseColId(WadUodcBoxplot mstData);

	List<WadUodcBoxplotCol> selectUodcBoxplotColListForScrt(WadUodcBoxplot search);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId); 

}
