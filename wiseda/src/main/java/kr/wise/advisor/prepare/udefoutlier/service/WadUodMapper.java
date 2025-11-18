package kr.wise.advisor.prepare.udefoutlier.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadUodMapper {

	int deleteByPrimaryKey(String udfOtlDtcId);

	int insert(WadUod record);

	int insertSelective(WadUod record);

	WadUod selectByPrimaryKey(String udfOtlDtcId);

	int updateByPrimaryKeySelective(WadUod record);

	int updateByPrimaryKey(WadUod record);

	List<WadUod> selectUdefOutlierList(WadUod search);

	int updateMdlScrtJson(WadUod record);    

	List<WadErrData> selectResViewColNm(WadErrData search);

	List<WadErrData> selectResultDataList(WadErrData pattvo);

	int updateOtlYn(WadErrData savevo);

	int updateOtlRpl(WadErrData data);

	int deleteOtlDataRpl(WadErrData data);

	int insertOtlDataRpl(WadErrData data);
}
