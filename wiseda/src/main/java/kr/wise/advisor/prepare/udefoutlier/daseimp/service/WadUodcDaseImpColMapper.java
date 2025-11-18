package kr.wise.advisor.prepare.udefoutlier.daseimp.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpCol;
import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;
 
@Mapper 
public interface WadUodcDaseImpColMapper {
    int deleteByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId, @Param("colSno") Integer colSno);

    int insert(WadUodcDaseImpCol record);

    int insertSelective(WadUodcDaseImpCol record);

    WadUodcDaseImpCol selectByPrimaryKey(@Param("udfOtlDtcId") String udfOtlDtcId, @Param("creCompId") String creCompId, @Param("colSno") Integer colSno);

    int updateByPrimaryKeySelective(WadUodcDaseImpCol record);

    int updateByPrimaryKey(WadUodcDaseImpCol record);

	int deleteCreCompId(WadUodcDaseImp mstData);

	int deleteByUdfOtlDtcId(String udfOtlDtcId);

	List<WadUodcDaseImpCol> selectDaseImpColNm(WadUodcDaseImpCol search);  
}