package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.meta.ddltsf.service.WamDdlTsfObj;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DdlTblService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 24. 오후 6:08:54
 * </PRE>
 */ 
public interface DdlTblService {

	List<WamDdlTbl> getList(WamDdlTbl search);

	WamDdlTbl selectDdlTblInfo(String ddlTblId, String rqstNo);

	List<WamDdlTbl> selectDdlTblChangeList(String ddlTblId);

	List<WamDdlCol> selectDdlTblColList(WamDdlTbl searchVO);

	List<WamDdlCol> selectDdlTblColGapList(WamDdlTbl searchVO);
		
	WamDdlCol selectDdlTblColInfo(WamDdlCol searchVO);

	List<WamDdlCol> selectDdlTblColChangeList(String ddlColId);
	
	List<WamDdlCol> selectDdlTblOneColChangeList(String ddlColId);

	List<WamDdlIdxCol> selectDdlTblIdxColList(WamDdlIdxCol searchVO);

	/** @param search
	/** @return meta */
	List<WamDdlTbl> getDdlTblRqstList(WamDdlTbl search);

	/** @param list
	/** @return meta */
	int saveDdlTblRqstPrc(ArrayList<WamDdlTbl> list);

	/** @param search
	/** @return meta */
	List<WamDdlTsfObj> getDdlTblTsfRqstList(WamDdlTsfObj search);

	/** @param list
	/** @return meta */
	int saveDdlTblTsfRqstPrc(ArrayList<WamDdlTsfObj> list);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> getIdxList(WamDdlIdx search);

	/** @param ddlIdxId
	/** @return meta */
	WamDdlIdx selectDdlIdxInfo(String ddlIdxId, String rqstNo);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> getIdxColList(WamDdlIdxCol search);

	/** @param ddlIdxColId
	/** @return meta */
	WamDdlIdxCol selectDdlIdxColInfo(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> getIdxChangeList(WamDdlIdx search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> getIdxColChangeList(WamDdlIdxCol search);

	/** @param searchVO
	/** @return meta */
	List<WamDdlRel> selectDdlTblRelList(WamDdlTbl searchVO);

	/** @param ddlTblId
	/** @return meta */
	List<WamDdlRel> selectDdlTblRelChangeList(String ddlTblId);
	
	//파티션테이블 리스트 조회
	List<WamDdlTbl> getPartitionTblList(WamDdlTbl search);

    //파티션 여부 저장
	int savePartitionTblPrc(ArrayList<WamDdlTbl> list);
	
	//백업테이블동기화 관리
	List<WamTblMst> getTblMstList(WamTblMst search);
	
	WamTblMst selectTblMstDtl(String objId);
	
	int saveTblMst(ArrayList<WamTblMst> list) throws Exception;
	
	int delTblMstList(ArrayList<WamTblMst> list);
	
    //분리보관 관리
	List<WamTblMst> getKpMngList(WamTblMst search);
	
	WamTblMst selectKpMngDtl(String objId);
	
	int saveKpMng(ArrayList<WamTblMst> list) throws Exception;
	
	int delKpMngList(ArrayList<WamTblMst> list);
	
	//인덱스컬럼변경이력 조회
	List<WamDdlIdxCol> getIdxOneColChangeList(WamDdlIdxCol search);

	List<WamDdlCol> getDdlIdxForColList(WamDdlTbl search);

	List<WamDdlTbl> selectDdlTsfTblListForRqst(WamDdlTbl search);

	List<WamDdlTbl> getDdlTblListForPart(WamDdlTbl search);

	List<WamDdlTbl> getDdlAllObjForGrtList(WamDdlTbl search);

	List<WamDdlCol> selectDdlTblColListForWah(WamDdlTbl searchVO);   


//	//사용안함
//	List<WamDdlTbl> getPciYnList(WamDdlTbl search);
//	
//	//개인정보 분리보관여부 저장,사용안함사용안함
//	int savePciYnPrc(ArrayList<WamDdlTbl> list);
}
