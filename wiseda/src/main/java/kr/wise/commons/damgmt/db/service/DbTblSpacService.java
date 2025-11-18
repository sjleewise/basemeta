/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbTblSpacService.java
 * 2. Package : kr.wise.commons.damgmt.db.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 12. 오후 3:14:53
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 12. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service;

import java.util.ArrayList;
import java.util.List;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbTblSpacService.java
 * 3. Package  : kr.wise.commons.damgmt.db.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 12. 오후 3:14:53
 * </PRE>
 */
public interface DbTblSpacService {

	/** @param dbTblSpacId
	/** @return yeonho */
	WaaTblSpac selectDbTblSpacDetail(String dbTblSpacId);

	/** @param saveVO
	/** @return yeonho 
	 * @throws Exception */
	int regDbTblSpac(WaaTblSpac saveVO) throws Exception;

	/** @param searchf
	/** @return yeonho */
	List<WaaTblSpac> getDbTblSpacList(WaaTblSpac search);

	/** @param list
	/** @return yeonho */
	int delDbTblSpacList(ArrayList<WaaTblSpac> list);

	/** @param list
	/** @return yeonho 
	 * @throws Exception */
	int regDbTblSpacList(ArrayList<WaaTblSpac> list) throws Exception;

}
