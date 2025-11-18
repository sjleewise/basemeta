package kr.wise.meta.mapping.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.meta.mapping.service.WamTblMap;

@Mapper
public interface WamTblMapMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(String tblMapId);

    int insert(WamTblMap record);

    int insertSelective(WamTblMap record);

    WamTblMap selectByPrimaryKey(String tblMapId);

    int updateByPrimaryKeySelective(WamTblMap record);

    int updateByPrimaryKey(WamTblMap record);
    
    List<WamTblMap> selectTblMapList(WamTblMap search) ;
    
    List<WamTblMap> selectTblMapListForColMap(WamTblMap search) ;
}