package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.model.service.WdqmPdmTbl;

@Mapper
public interface WdqqPdmTblMapper {
    int insert(WdqmPdmTbl record);

    int insertSelective(WdqmPdmTbl record);
}