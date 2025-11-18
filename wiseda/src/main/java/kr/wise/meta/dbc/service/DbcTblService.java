/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcTblService.java
 * 2. Package : kr.wise.meta.dbc.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 22. 오후 4:20:41
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 22. :            : 신규 개발.
 */
package kr.wise.meta.dbc.service;

import java.util.List;

import kr.wise.meta.ddl.service.WamDdlCol;
import kr.wise.meta.ddl.service.WamDdlIdxCol;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbcTblService.java
 * 3. Package  : kr.wise.meta.dbc.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 22. 오후 4:20:41
 * </PRE>
 */
public interface DbcTblService {

	/** @param search
	/** @return meta */
	List<WatDbcTbl> getList(WatDbcTbl search);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	WatDbcTbl selectDbcTblInfo(String dbSchId, String dbcTblNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcCol> selectDbcTblColList(String dbSchId, String dbcTblNm);


	/** @param dbSchId
	/** @param dbcTblNm
	/** @param dbcColNm
	/** @return meta */
	WatDbcCol selectDbcTblColInfo(String dbSchId, String dbcTblNm,
			String dbcColNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcIdxCol> selectDbcTblIdxColList(String dbSchId, String dbcTblNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcCol> selectDbcTblColListNonColor(String dbSchId, String dbcTblNm);

	/** @param search
	/** @return meta */
	List<WatDbcTbl> getHistList(WatDbcTbl search);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcTbl> selectDbcTblChangeList(String dbSchId, String dbcTblNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcCol> selectDbcColChangeList(String dbSchId, String dbcTblNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcIdxCol> selectDbcIdxColChangeList(String dbSchId, String dbcTblNm);
	
	List<WatDbcTbl> getDbcDpndList(String dbSchId, String dbcTblNm, String dbConnTrgLnm, String dbSchLnm);

}
