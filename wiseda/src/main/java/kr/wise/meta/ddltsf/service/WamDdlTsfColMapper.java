package kr.wise.meta.ddltsf.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.springframework.stereotype.Component;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDdlTsfColObjMapper.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 25. 오전 10:20:33
 * </PRE>
 */ 
@Mapper
public interface WamDdlTsfColMapper {
//    int deleteByPrimaryKey(String ddlColId);
//
//    int insert(WamDdlTsfColObj record);
//
//    int insertSelective(WamDdlTsfColObj record);
//
    WamDdlTsfColObj selectByPrimaryKey(String ddlColId);
//
//    int updateByPrimaryKeySelective(WamDdlTsfColObj record);
//
//    int updateByPrimaryKey(WamDdlTsfColObj record);

	List<WamDdlTsfColObj> selectColList(String ddlTblId);

	List<WamDdlTsfColObj> selectDdlTsfTblColChange(String ddlColId);
}