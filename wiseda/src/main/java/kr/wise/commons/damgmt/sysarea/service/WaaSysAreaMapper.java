package kr.wise.commons.damgmt.sysarea.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.springframework.stereotype.Component;

@Mapper 
public interface WaaSysAreaMapper {
    int deleteByPrimaryKey(String sysAreaId);
    
    int deleteRegTyp(WaaSysArea record);

    int insert(WaaSysArea record);

    int insertSelective(WaaSysArea record);

    WaaSysArea selectByPrimaryKey(String sysAreaId);
    
    List<WaaSysArea> selectList(WaaSysArea record);

    int updateByPrimaryKeySelective(WaaSysArea record);

    int updateByPrimaryKey(WaaSysArea record);

	int deleteall();

	int delsysarealist(String[] ids);

	int updateExpDtm(WaaSysArea record);
}