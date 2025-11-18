/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaGapService.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.08.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.08. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import java.util.List;

public interface MtaGapService {

	/** 메타데이터GAP분석 목록 조회 */
	public List<MtaGapVO> getMtaGapAnalyze(MtaGapVO search);

	public List<MtaGapVO> getMtaColGapList(MtaGapVO search);
	
}
