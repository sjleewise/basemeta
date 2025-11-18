package kr.wise.meta.mapping.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.meta.mapping.service.WamCodMap;

@Mapper
public interface WamCodMapMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(String cdMapId);

    int insert(WamCodMap record);

    int insertSelective(WamCodMap record);

    WamCodMap selectByPrimaryKey(String cdMapId);

    int updateByPrimaryKeySelective(WamCodMap record);

    int updateByPrimaryKey(WamCodMap record);
    
    List<WamCodMap> selectCodMapList(WamCodMap search) ;
}