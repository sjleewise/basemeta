package kr.wise.advisor.prepare.outlier.service;

import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadOtlValMapper {
    int deleteByPrimaryKey(@Param("otlDtcId") String otlDtcId, @Param("varSno") Integer varSno);

    int insert(WadOtlVal record);

    int insertSelective(WadOtlVal record);

    WadOtlVal selectByPrimaryKey(@Param("otlDtcId") String otlDtcId, @Param("varSno") Integer varSno);

    int updateByPrimaryKeySelective(WadOtlVal record);

    int updateByPrimaryKey(WadOtlVal record);

	/** @param otlDtcId insomnia */
	int deleteByotlDtcId(String otlDtcId);

	/** @param savevo insomnia */
	int deleteByvo(WadOtlVal savevo);
}