package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqhPdmTblMapper {
    int insert(WdqmPdmTbl record);

    int insertSelective(WdqmPdmTbl record);

	int wam2wah(WdqmPdmTbl pdmTbl);
}