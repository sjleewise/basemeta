/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : SummaryService.java
 * 2. Package : kr.wise.advisor.prepare.summary.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 21. 오후 7:29:14
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 21. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.summary.service;

import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : SummaryService.java
 * 3. Package  : kr.wise.advisor.prepare.summary.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 21. 오후 7:29:14
 * </PRE>
 */
public interface SummaryService {

	/** @param search
	/** @return insomnia */
	WadVarSum getSummaryDtl(WadAnaVar search);

	/** @param search
	/** @return insomnia */
	int regSummarybyDs(WadDataSet search);

	/** @param search
	/** @return insomnia */
	List<WadVarSum> getHistoDtl(WadAnaVar search);

	/** @param search
	/** @return insomnia */
	int regHistobyVar(WadAnaVar search);

}
