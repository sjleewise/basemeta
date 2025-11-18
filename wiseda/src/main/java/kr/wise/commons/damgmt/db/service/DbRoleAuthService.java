/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbRoleAuthService.java
 * 2. Package : kr.wise.commons.damgmt.db.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 13. 오후 1:06:56
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 13. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbRoleAuthService.java
 * 3. Package  : kr.wise.commons.damgmt.db.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 13. 오후 1:06:56
 * </PRE>
 */
public interface DbRoleAuthService {

	/** @param search
	/** @return yeonho */
	List<WaaDbPrivilege> getDbRoleAuthList(WaaDbPrivilege search);

	/** @param list
	/** @return yeonho 
	 * @throws Exception */
	int regDbRoleAuthList(ArrayList<WaaDbPrivilege> list) throws Exception;

	/** @param list
	/** @return yeonho */
	int delDbRoleAuthList(ArrayList<WaaDbPrivilege> list);

}
