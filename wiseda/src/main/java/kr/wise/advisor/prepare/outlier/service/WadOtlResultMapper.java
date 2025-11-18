package kr.wise.advisor.prepare.outlier.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.report.service.DataPatternVO;

@Mapper
public interface WadOtlResultMapper {
    int insert(WadOtlResult record);

    int insertSelective(WadOtlResult record);

	/** @param otlDtcId insomnia */
	int deletebyDtcId(String otlDtcId);

	/** @param savevo
	/** @return insomnia */
	int updateByPrimaryKey(WadOtlResult savevo);

	/** @param savevo
	/** @return insomnia */
	int deleteByPrimaryKey(WadOtlResult savevo);

	/** @param savevo
	/** @return insomnia */
	int deleteByresvo(WadOtlResult savevo);

	/** @param search
	/** @return insomnia */
	List<WadOtlResult> selectResultbyId(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	WadOtlResult selectResultbyPatternVO(DataPatternVO search);
}