package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqhPdmColMapper {
    int insert(WdqhPdmCol record);

    int insertSelective(WdqhPdmCol record);

	int wam2wah(WdqmPdmCol pdmCol);

	int wam2wahByDelTbl(WdqmPdmTbl pdmTbl);
}