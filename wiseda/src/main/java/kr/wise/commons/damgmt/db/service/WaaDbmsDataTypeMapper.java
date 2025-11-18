package kr.wise.commons.damgmt.db.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WaaDbmsDataTypeMapper.java
 * 3. Package  : kr.wise.commons.damgmt.db.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 22. 오후 6:25:22
 * </PRE>
 */ 
@Mapper
public interface WaaDbmsDataTypeMapper {
    int deleteByPrimaryKey(@Param("dbmsDataTypeId") String dbmsDataTypeId, @Param("expDtm") Date expDtm);

    int insert(WaaDbmsDataType record);

    int insertSelective(WaaDbmsDataType record);

    WaaDbmsDataType selectByPrimaryKey(@Param("dbmsDataTypeId") String dbmsDataTypeId);

    int updateByPrimaryKeySelective(WaaDbmsDataType record);

    int updateByPrimaryKey(WaaDbmsDataType record);
    
    List<WaaDbmsDataType> selectList(WaaDbmsDataType record);

	int updateExpDtm(WaaDbmsDataType waaDbmsDataType);

	/** @param dbmsDataTypeId
	/** @return yeonho */
	WaaDbmsDataType selectByPrimaryKeyForMapping(String dbmsDataTypeId);
}