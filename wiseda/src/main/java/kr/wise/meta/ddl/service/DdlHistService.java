/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlHistService.java
 * 2. Package : kr.wise.meta.ddl.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 9. 15. 오전 11:14:47
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 9. 15. :            : 신규 개발.
 */
package kr.wise.meta.ddl.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DdlHistService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 9. 15. 오전 11:14:47
 * </PRE>
 */
public interface DdlHistService {

	/** @param search
	/** @return meta */
	List<WamDdlIdx> getIdxHistList(WamDdlIdx search);

	/** @param search
	/** @return meta */
	WamDdlIdx selectDdlIdxHistInfo(WamDdlIdx search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> getIdxColHistList(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	WamDdlIdxCol selectDdlIdxColInfo(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> getIdxChangeHistList(WamDdlIdx search);

	/** @param search
	/** @return meta */
	List<WamDdlIdxCol> getIdxColChangeHistList(WamDdlIdxCol search);

	/** @param search
	/** @return meta */
	List<WamDdlTbl> getDdlHistList(WamDdlTbl search);

	/** @param search
	/** @return meta */
	WamDdlTbl selectDdlTblInfoHist(WamDdlTbl search);

	/** @param searchVO
	/** @return meta */
	List<WamDdlCol> selectDdlTblColHistList(WamDdlTbl searchVO);

	/** @param searchVO
	/** @return meta */
	List<WamDdlRel> selectDdlTblRelHistList(WamDdlTbl searchVO);

	/** @param searchVO
	/** @return meta */
	List<WamDdlIdxCol> selectDdlTblIdxColHistList(WamDdlIdxCol searchVO);

	/** @param search
	/** @return meta */
	WamDdlCol selectDdlTblColHistInfo(WamDdlCol search);

}
