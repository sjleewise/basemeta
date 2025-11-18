/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : IdxGapService.java
 * 2. Package : kr.wise.meta.gap.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 24. 오후 4:35:14
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : IdxGapService.java
 * 3. Package  : kr.wise.meta.gap.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 24. 오후 4:35:14
 * </PRE>
 */
public interface IdxGapService {

	/** @param search
	/** @return yeonho */
	List<IdxGapVO> getIdxGapAnalyze(IdxGapVO search);

	/** @param search
	/** @return yeonho */
	List<IdxGapVO> getDbcIdxColList(IdxGapVO search);

	/** @param search
	/** @return yeonho */
	List<IdxGapVO> getDdlIdxColList(IdxGapVO search);
	
	List<IdxGapVO> getDdlDbcIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> getDdlDbcIdxColGapList(IdxGapVO search);
	
	List<IdxGapVO> getDdlTsfIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> getDdlTsfIdxColGapList(IdxGapVO search);
	
	List<IdxGapVO> getDdlTsfDbcIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> getDdlTsfDbcIdxColGapList(IdxGapVO search);
	
	List<IdxGapVO> getDbcIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> getDbcIdxColGapList(IdxGapVO search);

}
