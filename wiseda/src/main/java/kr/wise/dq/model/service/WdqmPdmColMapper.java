package kr.wise.dq.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmPdmColMapper {
    int insert(WdqmPdmCol record);

    int insertSelective(WdqmPdmCol record);

	List<WdqmPdmCol> selectPdmColList(WdqmPdmCol search);
	
    List<WdqmPdmCol> selectList(WdqmPdmCol search);

	List<WdqmPdmCol> selectcolhisttList(WdqmPdmTbl search);

	WdqmPdmCol selectByPrimaryKey(String pdmTblId);

	int deleteByPrimaryKey(WdqmPdmCol pdmCol);

	int updateTblId(WdqmPdmCol pdmCol);

	int deleteByPrimaryKeyByDelTbl(WdqmPdmTbl pdmTbl);
}