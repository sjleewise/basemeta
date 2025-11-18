package kr.wise.advisor.prepare.udefoutlier.usrdef.service;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadUodcUsrdefMapper {

	WadUodcUsrdef selectDetailInfo(WadUodcUsrdef search);

	int updateUsrdef(WadUodcUsrdef mstData);

	int insertUsrdef(WadUodcUsrdef mstData);
	
	int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

	int deleteByUdfOtlDtcId(@Param("udfOtlDtcId") String udfOtlDtcId);

}
