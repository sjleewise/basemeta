/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 24. 오후 11:53:48
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 24. 오후 11:53:48
 * </PRE>
 */

public interface StndWordService {

	/** @param data
	/** @return insomnia */
	List<WamStwd> getStndWordList(WamStwd data);

	WamStwd selectStndWordDetail(String stwdId);
	
	List<WamStwd> selectStndWordChangeList(String stwdId);
	
	List<WamWhereUsed> selectStwdWhereUsedList(WamStwd vo);
	
	public int regAbr(WamStwdAbr record) throws Exception ;
	
	String generateAbrList(ArrayList<WamStwdAbr> list) throws Exception;
	
	List<WamStwd> selectStndWordComparisonList(String stwdId);

	String selectGovYn(String stwdId);
	
	WamStwd selectGovStndWordDetail(String stwdId);

}
