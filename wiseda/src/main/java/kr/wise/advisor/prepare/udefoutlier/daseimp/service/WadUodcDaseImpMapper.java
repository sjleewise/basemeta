package kr.wise.advisor.prepare.udefoutlier.daseimp.service;

import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcDaseImpMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId);

    int insert(WadUodcDaseImp record);

    int insertSelective(WadUodcDaseImp record);

    WadUodcDaseImp selectByPrimaryKey(WadUodcDaseImp record);

    int updateByPrimaryKeySelective(WadUodcDaseImp record);

    int updateByPrimaryKey(WadUodcDaseImp record);

	List<WadUodcDaseImp> selectDaseImpList(WadUodcDaseImp search);

	int deleteByUdfOtlDtcId(String udfOtlDtcId);

	List<WadUodcDaseImpCol> selectDaseImpColList(WadUodcDaseImp search);

	WadUodcDaseImp selectDetailInfo(WadUodcDaseImp search);
	
	int updateDaseImpAnaDaseId(WadUodcDaseImp mstData);

	List<WadUodcDaseImpCol> selectDaseImpColForScrt(WadUodcDaseImp search);

	WadUodcDaseImpCol selectImpDataDcd(WadUodcDaseImp search);

	List<WadUodcDaseImpCol> selectDaseImpHiveColList(WadUodcDaseImp search);
}