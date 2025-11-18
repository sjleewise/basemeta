/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbmsMappingService.java
 * 2. Package : kr.wise.commons.damgmt.db.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 7. 8. 오후 2:41:49
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 7. 8. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.damgmt.subjsch.service.WaaSubjDbSchMap;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DbmsMappingService.java
 * 3. Package  : kr.wise.commons.damgmt.db.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 7. 8. 오후 2:41:49
 * </PRE>
 */
public interface DbmsMappingService {

	/** @param search
	/** @return insomnia */
	List<WaaDbMap> getDbMapList(WaaDbMap search);
	
	/* 유성열 */
	int regDbMapList(ArrayList<WaaDbMap> list) throws Exception;

	/* 유성열 */
	int delDbMapList(ArrayList<WaaDbMap> list);
	
	WaaDbMap getDbMapDefaultOne(WaaDbMap search);

}
