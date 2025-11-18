package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmSditmMapper {
    int deleteByPrimaryKey(String sditmId);

    int insert(WdqmSditm record);

    int insertSelective(WdqmSditm record);

    WdqmSditm selectByPrimaryKey(String sditmId);

    int updateByPrimaryKeySelective(WdqmSditm record);

    int updateByPrimaryKey(WdqmSditm record);

	List<WdqmSditm> selectSditmList(WdqmSditm data);
	
	List<WdqmSditm> selectSditmChangeList(@Param("sditmId") String sditmId);
	
	List<WdqmSditm> getSditmTransList(WdqmSditm record);
	
	int saveSditmTransYnPrc(WdqmSditm record);
	
	List<WdqmSditm> selectSditmComparisonList(String sditmId);
}