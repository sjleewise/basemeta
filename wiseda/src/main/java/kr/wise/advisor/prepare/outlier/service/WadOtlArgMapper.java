package kr.wise.advisor.prepare.outlier.service;

import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadOtlArgMapper {
    int deleteByPrimaryKey(@Param("otlDtcId") String otlDtcId, @Param("algArgId") String algArgId);

    int insert(WadOtlArg record);

    int insertSelective(WadOtlArg record);

    WadOtlArg selectByPrimaryKey(@Param("otlDtcId") String otlDtcId, @Param("algArgId") String algArgId);

    int updateByPrimaryKeySelective(WadOtlArg record);

    int updateByPrimaryKey(WadOtlArg record);

	/** @param saveotl insomnia */
	int deleteByotlDtcId(WadOtlDtc saveotl);

	/** @param otlDtcId
	/** @return insomnia */
	List<WaaAlgArg> selectArgbyId(String otlDtcId);

}