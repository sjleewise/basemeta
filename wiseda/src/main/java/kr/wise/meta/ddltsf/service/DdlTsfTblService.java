package kr.wise.meta.ddltsf.service;

import java.util.List;

import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DdlTsfTblService.java
 * 3. Package  : kr.wise.meta.ddlTsf.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 24. 오후 6:08:54
 * </PRE>
 */ 
public interface DdlTsfTblService {

	List<WamDdlTsfObj> getList(WamDdlTsfObj search);

	WamDdlTsfObj selectDdlTsfTblInfo(String ddlTblId);

	List<WamDdlTsfColObj> selectDdlTsfTblColList(String ddlTblId);
	
	List<WamDdlTsfObj> selectDdlTsfTblChangeList(String ddlTblId);

	WamDdlTsfColObj selectDdlTsfTblColInfo(String ddlColId);

	List<WamDdlTsfColObj> selectDdlTsfTblColChange(String ddlColId);
//
//	List<WamDdlIdxCol> selectDdlTblIdxColList(String ddlTblId);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfTbl> selectDdlTsfTblListForRqst(WaqDdlTsfTbl search);

	/** @param ddlIdxId
	/** @return yeonho */
	WaqDdlTsfIdx selectDdlTsfIdxInfo(String ddlIdxId);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdxCol> getTsfIdxColList(WamDdlIdxCol search);

	/** @param ddlIdxColId
	/** @return yeonho */
	WaqDdlTsfIdxCol selectDdlTsfIdxColInfo(String ddlIdxColId);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdx> getTsfIdxChangeList(WamDdlIdx search);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdxCol> getTsfIdxColChangeList(WamDdlIdxCol search);

}
