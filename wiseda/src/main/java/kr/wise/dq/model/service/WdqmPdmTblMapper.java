package kr.wise.dq.model.service;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmPdmTblMapper {
    int insert(WdqmPdmTbl record);

    int insertSelective(WdqmPdmTbl record);

	List<WdqmPdmTbl> selectPdmTblList(WdqmPdmTbl search);
    
	int updateSelective(WdqmPdmTbl search);
	
	int deleteByPrimaryKey(WdqmPdmTbl search);

	WdqmPdmTbl selectByPrimaryKey(@Param("pdmTblId") String pdmTblId);
	
	List<WdqmPdmTbl> selectList(WdqmPdmTbl search);

	List<WdqmPdmTbl> selectHisTbl(WdqmPdmTbl search);
}