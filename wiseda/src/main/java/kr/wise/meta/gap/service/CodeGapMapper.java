/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeGapMapper.java
 * 2. Package : kr.wise.meta.gap.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 23. 오후 5:13:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 23. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeGapMapper.java
 * 3. Package  : kr.wise.meta.gap.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 23. 오후 5:13:16
 * </PRE>
 */
@Mapper
public interface CodeGapMapper {

	/** @param search
	/** @return yeonho */
	List<CodeGapVO> getCodeGapAnalyze(CodeGapVO search);

	/** @param search
	/** @return yeonho */
	List<CodeGapVO> getCodeValList(CodeGapVO search);

	/** @param search
	/** @return yeonho */
	List<CodeGapVO> getDmnValList(CodeGapVO search);

	//ibk신용정보용 갭분석
	List<CodeGapVO> selectCodeGapMetaDev(CodeGapVO search);
	List<CodeGapVO> selectCodeGapMetaReal(CodeGapVO search);
	List<CodeGapVO> selectCodeGapMetaTsf(CodeGapVO search);
	List<CodeGapVO> selectCodeGapDevReal(CodeGapVO search);
	List<CodeGapVO> selectCmpCodeGapMetaDev(CodeGapVO search);
	List<CodeGapVO> selectCmpCodeGapMetaReal(CodeGapVO search);
	List<CodeGapVO> selectCmpCodeGapMetaTsf(CodeGapVO search);
	List<CodeGapVO> selectCmpCodeGapDevReal(CodeGapVO search);
	
}
