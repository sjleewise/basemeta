package kr.wise.meta.ddletc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlTbl;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WaqDdlEtcMapper.java
 * 3. Package  : kr.wise.meta.ddletc.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 29. 오후 3:03:03
 * </PRE>
 */ 
@Mapper
public interface WamDdlEtcMapper {

	List<WamDdlEtc> selectDdlEtcList(WamDdlEtc search);

	WamDdlEtc selectDdlEtcDetail(@Param("ddlEtcId")String ddlEtcId, @Param("rqstNo")String rqstNo);

	int updateDdlEtcRqstPrc(WamDdlTbl record);
	
}