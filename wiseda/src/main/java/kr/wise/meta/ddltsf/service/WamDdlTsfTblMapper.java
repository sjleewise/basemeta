package kr.wise.meta.ddltsf.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDdlTsfObjMapper.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 24. 오후 6:50:53
 * </PRE>
 */ 
@Mapper
public interface WamDdlTsfTblMapper {
    int deleteByPrimaryKey(String ddlTblId);

    int insert(WamDdlTsfObj record);

    int insertSelective(WamDdlTsfObj record);

    WamDdlTsfObj selectByPrimaryKey(String ddlTblId);

    int updateByPrimaryKeySelective(WamDdlTsfObj record);

    int updateByPrimaryKeyWithBLOBs(WamDdlTsfObj record);

    int updateByPrimaryKey(WamDdlTsfObj record);
    
    List<WamDdlTsfObj> selectList(WamDdlTsfObj record);

	List<WamDdlTsfObj> selectDdlTsfTblChangeList(String ddlTblId);

	/** @param search
	/** @return yeonho */
	List<WamDdlTsfObj> selectDdlTblRqstList(WamDdlTsfObj search);

	/** @param record
	/** @return yeonho */
	int updateDdlTblRqstPrc(WamDdlTsfObj record);

	/** @param search
	/** @return yeonho */
	List<WamDdlTsfObj> selectDdlTblTsfRqstList(WamDdlTsfObj search);

	/** @param record
	/** @return yeonho */
	int updateDdlTblTsfRqstPrc(WamDdlTsfObj record);

	/** @param record
	/** @return yeonho */
	int updateDdlIdxTsfRqstPrc(WamDdlTsfObj record);
}