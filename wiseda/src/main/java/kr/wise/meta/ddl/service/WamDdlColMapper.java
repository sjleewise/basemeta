package kr.wise.meta.ddl.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDdlColMapper.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 25. 오전 10:20:33
 * </PRE>
 */ 
@Mapper
public interface WamDdlColMapper {
    int deleteByPrimaryKey(String ddlColId);

    int insert(WamDdlCol record);

    int insertSelective(WamDdlCol record);

    WamDdlCol selectByPrimaryKey(WamDdlCol record);

    int updateByPrimaryKeySelective(WamDdlCol record);

    int updateByPrimaryKey(WamDdlCol record);

	List<WamDdlCol> selectColList(WamDdlTbl record);
    
	List<WamDdlCol> selectColListGap(WamDdlTbl record);
    
	List<WamDdlCol> selectDdlTblColChange(String ddlColId);

	List<WamDdlCol> selectDdlTblOneColChange(String ddlColId);
	/** @param searchVO
	/** @return meta */
	List<WamDdlCol> selectDdlTblColHistList(WamDdlTbl searchVO);

	/** @param search
	/** @return meta */
	WamDdlCol selectDdlTblColHistInfo(WamDdlCol search);

	List<WamDdlCol> selectDdlIdxForColList(WamDdlTbl search);

	List<WamDdlCol> selectColListForWah(WamDdlTbl searchVO); 
}