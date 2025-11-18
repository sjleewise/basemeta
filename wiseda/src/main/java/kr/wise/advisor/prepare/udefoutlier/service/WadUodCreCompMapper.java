package kr.wise.advisor.prepare.udefoutlier.service;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodCreCompMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int insert(WadUodCreComp record);

    int insertSelective(WadUodCreComp record);

    WadUodCreComp selectByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int updateByPrimaryKeySelective(WadUodCreComp record);

    int updateByPrimaryKey(WadUodCreComp record);

	WadUodCreComp selectUodCompForScrt(WadUodCreComp tmpVo); 
	
	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);
}