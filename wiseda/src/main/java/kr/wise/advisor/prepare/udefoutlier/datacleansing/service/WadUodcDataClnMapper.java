package kr.wise.advisor.prepare.udefoutlier.datacleansing.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadUodcDataClnMapper {

	public WadUodcDataCln selectDataClnDetail(WadUodcDataCln search);

	public List<WadUodcDataClnCol> selectDataClnColList(WadUodcDataClnCol search);

	public int updateDataCln(WadUodcDataCln mstData);

	public int insertDataCln(WadUodcDataCln mstData);

	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	public int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
	
}
