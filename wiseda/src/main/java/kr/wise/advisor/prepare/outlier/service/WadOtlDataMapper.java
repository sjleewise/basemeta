package kr.wise.advisor.prepare.outlier.service;

import java.util.Date;
import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.report.service.DataPatternVO;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadOtlDataMapper {
    int deleteByPrimaryKey(@Param("otlDtcId") String otlDtcId, @Param("anaStrDtm") Date anaStrDtm, @Param("otlSno") Integer otlSno);

    int insert(WadOtlData record);

    int insertSelective(WadOtlData record);

    WadOtlData selectByPrimaryKey(@Param("otlDtcId") String otlDtcId, @Param("anaStrDtm") Date anaStrDtm, @Param("otlSno") Integer otlSno);

    int updateByPrimaryKeySelective(WadOtlData record);

    int updateByPrimaryKey(WadOtlData record);

	/** @param otlDtcId
	/** @param varId insomnia */
	int deleteById(@Param("otlDtcId") String otlDtcId, @Param("anlVarId") String varId);

	/** @param savedata
	/** @return insomnia */
	int insertBoxPlotData(WadOtlData savedata);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> selectDatabydtcId(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> selectDataColList(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlChartData> selectChartData(WadOtlDtc search);

	/** @param varvo
	/** @return insomnia 
	 * @param dbSchPnm */
	List<Float> selectColData(@Param("varvo") WadAnaVar varvo, @Param("dbSchPnm") String dbSchPnm);

	/** @param resdata insomnia */
	int insertOutData(WadOtlData resdata);

	/** @param otlDtcId
	/** @return insomnia */
	List<WadOtlData> selectDataAll(String otlDtcId);

	/** @param otlDtcId insomnia */
	int deletebyDtcId(String otlDtcId);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> selectColNm(DataPatternVO search);

	/** @param pattvo
	/** @return insomnia */
	List<WadOtlData> selectDataList(DataPatternVO pattvo);

	/** @param search
	/** @param pattvo
	/** @return insomnia */
	List<WadOtlData> selectDataOne(@Param("dtcvo") WadOtlDtc search, @Param("pattvo") DataPatternVO pattvo);

	List<WadOtlResult> selectAnlVarId(DataPatternVO search);

	int updateOtlYn(WadOtlData savevo);

	int updateOtlRpl(WadOtlData data);

	int deleteOtlDataRpl(WadOtlData data);

	int insertOtlDataRpl(WadOtlData data);

}