package kr.wise.advisor.prepare.outlier.service;

import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadOtlDtcMapper {
    int deleteByPrimaryKey(String otlDtcId);

    int insert(WadOtlDtc record);

    int insertSelective(WadOtlDtc record);

    WadOtlDtc selectByPrimaryKey(String otlDtcId);

    int updateByPrimaryKeySelective(WadOtlDtc record);

    int updateByPrimaryKey(WadOtlDtc record);

	/** @param saveotl
	/** @return insomnia */
	WadOtlDtc selectbydsnalg(WadOtlDtc saveotl);

	/** @param search
	/** @return insomnia */
	WadOtlDtcVo selectOtlDct(WadOtlDtc search);

	int updateOtlNm(WadOtlDtc saveotl);

	int deleteWamOtlDqiMap(String otlDtcId);

	int insertWamOtlDqiMap(WadOtlDtc saveotl); 
}