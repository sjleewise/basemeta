/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizruleService.java
 * 2. Package : kr.wise.dq.bizrule.service
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 4. 29. 오전 9:55:14
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 4. 29. :            : 신규 개발.
 */
package kr.wise.dq.bizrule.service;

import java.util.ArrayList;
import java.util.List;





/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : BizruleService.java
 * 3. Package  : kr.wise.dq.bizrule.service
 * 4. Comment  :
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 4. 29. 오전 9:55:14
 * </PRE>
 */
public interface BizruleService {

	List<WamBrMstr> getBizruleList(WamBrMstr search);

	WamBrMstr getBizruleDtlList(WamBrMstr search);

	/** @param search
	/** @return meta */
	List<WamBrMstr> getBizruleListPop(WamBrMstr search);

	/**
	 * @param search
	 * @return
	 */
	List<WamBrMstr> getvrtDbList(WamBrMstr search);

	/**
	 * @param search
	 * @return
	 */
	List<WamBrMstr> getvrtTblList(WamBrMstr search);

	/**
	 * @param search
	 * @return
	 */
	List<WamBrMstr> getvrtColList(WamBrMstr search);

	/**
	 * @param brId
	 * @return
	 */
	List<WamBrMstr> getBizRuleHstLst(String brId);
	

	List<WamBrMstr> getBizruleXlsxList(WamBrMstr search);        

}
