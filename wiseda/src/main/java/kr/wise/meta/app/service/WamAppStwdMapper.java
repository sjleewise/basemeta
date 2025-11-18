package kr.wise.meta.app.service;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.app.service.WamAppStwd;
@Mapper
public interface WamAppStwdMapper {
    int deleteByPrimaryKey(String appStwdId);

    int insert(WamAppStwd record);

    int insertSelective(WamAppStwd record);

    WamAppStwd selectByPrimaryKey(String appStwdId);

    int updateByPrimaryKeySelective(WamAppStwd record);

    int updateByPrimaryKey(WamAppStwd record);
    
    List<WamAppStwd> selectList(WamAppStwd record);
    
    WamAppStwd selectWordDetail(String appStwdId);
    
	List<WamAppStwd> selectWordChangeList(String appStwdId);
}