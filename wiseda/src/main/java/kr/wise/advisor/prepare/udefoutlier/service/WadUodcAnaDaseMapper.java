package kr.wise.advisor.prepare.udefoutlier.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper 
public interface WadUodcAnaDaseMapper {
    int deleteByPrimaryKey(String anaDaseId);

    int insert(WadUodcAnaDase record);

    int insertSelective(WadUodcAnaDase record);

    WadUodcAnaDase selectByPrimaryKey(String anaDaseId);

    int updateByPrimaryKeySelective(WadUodcAnaDase record);

    int updateByPrimaryKey(WadUodcAnaDase record);

	int updateFkUodcAnaDase(WadUodcAnaDase anaVo);

	int deleteByUdfOtlDtcId(String udfOtlDtcId);

	List<WadUodcAnaDase> selectAnaDaseIdByFk(WadUodcAnaDase search);

	int deleteByCreCompId(WadUodCreComp param);

	List<WadUodcAnaDase> selectAnaDaseIdSaveres(WadUodcAnaDase search); 
	
	
}