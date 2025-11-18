package kr.wise.meta.stnd.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamSditmMapper {
    int deleteByPrimaryKey(String sditmId);

    int insert(WamSditm record);

    int insertSelective(WamSditm record);

    WamSditm selectByPrimaryKey(String sditmId);

    int updateByPrimaryKeySelective(WamSditm record);

    int updateByPrimaryKey(WamSditm record);

	List<WamSditm> selectSditmList(WamSditm data);
	
	List<WamSditm> selectSditmChangeList(@Param("sditmId") String sditmId);
	
	List<WamSditm> getSditmTransList(WamSditm record);
	
	int saveSditmTransYnPrc(WamSditm record);
	
	List<WamSditm> selectSditmComparisonList(String sditmId);
	
	List<HashMap> selectSditmDbmsDataTypeList(String sditmId);

	String selectGovYn(String sditmId);

	WamSditm selectGovByPrimaryKey(String sditmId); 
}