package kr.wise.meta.ddl.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.meta.ddl.service.WamDdlIdxCol;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDdlIdxColMapper.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 29. 오후 3:03:03
 * </PRE>
 */ 
@Mapper
public interface WamDdlIdxColMapper {


	int deleteByPrimaryKey(String ddlIdxColId);

    int insert(WamDdlIdxCol record);

    int insertSelective(WamDdlIdxCol record);

    WamDdlIdxCol selectByPrimaryKey(String ddlIdxColId);

    int updateByPrimaryKeySelective(WamDdlIdxCol record);

    int updateByPrimaryKey(WamDdlIdxCol record);

	List<WamDdlIdxCol> selectDdlTblIdxColList(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> selectDdlIdxColList(WamDdlIdxCol search);

	/** @param ddlIdxColId
	/** @return meta */
	WamDdlIdxCol selectDdlIdxColInfo(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> selectColChangeList(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> selectDdlIdxColHistList(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	WamDdlIdxCol selectDdlIdxColHistInfo(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> selectIdxColChangeHistList(WamDdlIdxCol search);

	/** @param searchVO
	/** @return meta */
	List<WamDdlIdxCol> selectDdlTblIdxColHistList(WamDdlIdxCol searchVO);
	
	List<WamDdlIdxCol> selectOneColChangeList(WamDdlIdxCol search);
}