package kr.wise.meta.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.app.service.WamAppSditm;
@Mapper
public interface WamAppSditmMapper {
    int deleteByPrimaryKey(String appSditmId);

    int insert(WamAppSditm record);

    int insertSelective(WamAppSditm record);

    WamAppSditm selectByPrimaryKey(String appSditmId);

    int updateByPrimaryKeySelective(WamAppSditm record);

    int updateByPrimaryKey(WamAppSditm record);
    
	List<WamAppSditm> selectSditmList(WamAppSditm data);
	
	List<WamAppSditm> selectSditmChangeList(@Param("sditmId") String appSditmId);
}