/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeManagerService.java
 * 2. Package : kr.wise.commons.code.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 17. 오전 9:13:35
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 17. :            : 신규 개발.
 */
package kr.wise.commons.code.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CodeManagerService.java
 * 3. Package  : kr.wise.commons.code.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 17. 오전 9:13:35
 * </PRE>
 */
public interface CodeManagerService {

	/** @param searchvo
	/** @return insomnia */
	List<WaaCommDcd> getcodelist(WaaCommDcd searchvo);

	/** @param searchVO
	/** @return insomnia */
	WaaCommDcd selectCodeDetail(WaaCommDcd searchVO);

	/** @param saveVO
	/** @return insomnia
	 * @throws Exception */
	int saveCode(WaaCommDcd saveVO) throws Exception;

	/** @param searchvo
	/** @return insomnia */
	List<WaaCommDtlCd> getcodeDtllist(WaaCommDcd searchvo);

	/** @param list
	/** @param saveVO
	/** @return insomnia */
	int regCodeDtlList(ArrayList<WaaCommDtlCd> list, WaaCommDcd saveVO) throws Exception;

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delCodeList(List<WaaCommDcd> list) throws Exception;

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delDtlCodeList(List<WaaCommDtlCd> list) throws Exception;

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regCodeList(List<WaaCommDcd> list) throws Exception;

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regDtlCodeList(ArrayList<WaaCommDtlCd> list) throws Exception;

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regCodeDtlListWithout(ArrayList<WaaCommDtlCd> list) throws Exception;

}
