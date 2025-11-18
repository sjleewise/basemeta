package kr.wise.advisor.prepare.udefoutlier.service;

import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataCln;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv; 
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper 
public interface WadUodcAnaDaseColMapper {
    int deleteByPrimaryKey(@Param("anaDaseId") String anaDaseId, @Param("anaColSno") String anaColSno);

    int insert(WadUodcAnaDaseCol record);

    int insertSelective(WadUodcAnaDaseCol record);

    WadUodcAnaDaseCol selectByPrimaryKey(@Param("anaDaseId") String anaDaseId, @Param("anaColSno") String anaColSno);

    int updateByPrimaryKeySelective(WadUodcAnaDaseCol record);

    int updateByPrimaryKey(WadUodcAnaDaseCol record);

	int insertAnaColDataImp(WadUodcAnaDase anaVo);

	int deleteFkUodcAnaDaseCol(WadUodcAnaDase anaVo);

	int deleteByUdfOtlDtcId(String udfOtlDtcId);

	int insertAnaColDaseDv(WadUodcAnaDase anaVo);    
	
	int insertAnaColColDaseDv(WadUodcAnaDase anaVo);

	int insertAnaColDaseRowDv(WadUodcAnaDase anaVo);

	List<WadUodcAnaDaseCol> selectAnaDaseColList(WadUodcDaseCndDv search);

	int insertAnaColDaseCndDv(WadUodcAnaDase anaVo);

	int insertAnaColJnCol(WadUodcAnaDase anaVo);

	int deleteAnaDaseColJnCol(WadUodcAnaDase anaVo);

	int insertAnaColDataClnCol(WadUodcAnaDaseCol anaColVo);

	int insertAnaColBoxplotCol(WadUodcAnaDaseCol anaColVo);

	
}