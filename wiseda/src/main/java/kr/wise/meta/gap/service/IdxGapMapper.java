/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : IdxGapMapper.java
 * 2. Package : kr.wise.meta.gap.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 24. 오후 4:40:47
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : IdxGapMapper.java
 * 3. Package  : kr.wise.meta.gap.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 24. 오후 4:40:47
 * </PRE>
 */
@Mapper
public interface IdxGapMapper {

	/** @param search
	/** @return yeonho */
	List<IdxGapVO> selectIdxGapAnalyze(IdxGapVO search);

	/** @param search
	/** @return yeonho */
	List<IdxGapVO> selectDbcIdxColList(IdxGapVO search);

	/** @param search
	/** @return yeonho */
	List<IdxGapVO> selectDdlIdxColList(IdxGapVO search);

	
	List<IdxGapVO> selectDdlDbcIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> selectDdlDbcIdxColGapList(IdxGapVO search);
	
    List<IdxGapVO> selectDdlTsfIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> selectDdlTsfIdxColGapList(IdxGapVO search);
	
	List<IdxGapVO> selectDdlTsfDbcIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> selectDdlTsfDbcIdxColGapList(IdxGapVO search);
	
	List<IdxGapVO> selectDbcIdxGapList(IdxGapVO search);
	
	List<IdxGapVO> selectDbcIdxColGapList(IdxGapVO search);
}
