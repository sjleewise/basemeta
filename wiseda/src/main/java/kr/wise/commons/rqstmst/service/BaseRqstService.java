/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BaseRqstService.java
 * 2. Package : kr.wise.commons.rqstmst.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 23. 오후 1:10:48
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 23. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service;

import java.util.ArrayList;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BaseRqstService.java
 * 3. Package  : kr.wise.commons.rqstmst.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 23. 오후 1:10:48
 * </PRE>
 */
public interface BaseRqstService {

	/** @param list
	/** @return yeonho */
	int deleteRqstList(ArrayList<WaqMstr> list);

}
