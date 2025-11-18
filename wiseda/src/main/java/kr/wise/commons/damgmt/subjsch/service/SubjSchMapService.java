/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : SubjSchmapService.java
 * 2. Package : kr.wise.commons.damgmt.subjsch.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 5. 23. 오후 5:04:06
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 5. 23. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.subjsch.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : SubjSchmapService.java
 * 3. Package  : kr.wise.commons.damgmt.subjsch.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 23. 오후 5:04:06
 * </PRE>
 */
public interface SubjSchMapService {

	int regSubjSchMapList(ArrayList<WaaSubjDbSchMap> list);

	List<WaaSubjDbSchMap> getList(WaaSubjDbSchMap search);

	int delSubjSchMapList(ArrayList<WaaSubjDbSchMap> list);

}
