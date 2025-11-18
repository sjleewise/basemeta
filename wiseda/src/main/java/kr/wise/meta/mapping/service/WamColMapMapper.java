package kr.wise.meta.mapping.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.meta.mapping.service.WamColMap;

@Mapper
public interface WamColMapMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(String colMapId);

    int insert(WamColMap record);

    int insertSelective(WamColMap record);

    WamColMap selectByPrimaryKey(String colMapId);

    int updateByPrimaryKeySelective(WamColMap record);

    int updateByPrimaryKey(WamColMap record);
    
    List<WamColMap> selectColMapList(WamColMap search) ;
}