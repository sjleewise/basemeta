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
package kr.wise.dq.stnd.service;

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

public interface WdqStndWordService {

	/** @param data
	/** @return insomnia */
	List<WdqmStwd> getStndWordList(WdqmStwd data);

	WdqmStwd selectStndWordDetail(String stwdId);
	
	List<WdqmStwd> selectStndWordChangeList(String stwdId);
	
	List<WdqmWhereUsed> selectStwdWhereUsedList(WdqmStwd vo);
	
	public int regAbr(WdqmStwdAbr record) throws Exception ;
	
	String generateAbrList(ArrayList<WdqmStwdAbr> list) throws Exception;
	
	List<WdqmStwd> selectStndWordComparisonList(String stwdId);
	

}
