/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeGapService.java
 * 2. Package : kr.wise.meta.gap.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 23. 오후 4:20:54
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 23. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeGapService.java
 * 3. Package  : kr.wise.meta.gap.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 23. 오후 4:20:54
 * </PRE>
 */
public interface CodeGapService {

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
	List<CodeGapVO> getCodeGapMetaDev(CodeGapVO search);
	List<CodeGapVO> getCodeGapMetaReal(CodeGapVO search);
	List<CodeGapVO> getCodeGapMetaTsf(CodeGapVO search);
	List<CodeGapVO> getCodeGapDevReal(CodeGapVO search);
	List<CodeGapVO> getCmpCodeGapMetaDev(CodeGapVO search);
	List<CodeGapVO> getCmpCodeGapMetaReal(CodeGapVO search);
	List<CodeGapVO> getCmpCodeGapMetaTsf(CodeGapVO search);
	List<CodeGapVO> getCmpCodeGapDevReal(CodeGapVO search);
	
}
