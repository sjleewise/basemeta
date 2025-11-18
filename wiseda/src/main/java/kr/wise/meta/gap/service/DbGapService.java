/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbGapService.java
 * 2. Package : kr.wise.meta.gap.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 24. 오후 5:08:03
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbGapService.java
 * 3. Package  : kr.wise.meta.gap.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 24. 오후 5:08:03
 * </PRE>
 */
public interface DbGapService {
	
	/** @param search
	/** @return yeonho */
	List<WatDbcGapTbl> getDbGapList(WatDbcGapTbl search);
	
	/** @param search
	/** @return yeonho */
	List<WatDbcGapCol> getGapColList(WatDbcGapCol search);

	/** @param search
	/** @return yeonho */
	List<WatDbcGapCol> getGapSrcList(WatDbcGapCol search);

	/** @param search
	/** @return yeonho */
	List<WatDbcGapCol> getGapTgtList(WatDbcGapCol search);
	
	
	List<WatDbcGapIdx> getGapIdxList(WatDbcGapIdx search);
	
	List<WatDbcGapIdxCol> getGapIdxColSrcList(WatDbcGapIdx search);
	
	List<WatDbcGapIdxCol> getGapIdxColTgtList(WatDbcGapIdx search);
	

}
