/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestChgDtlsService.java
 * 2. Package : kr.wise.commons.rqstmst.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 25. 오후 3:37:50
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 25. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : RequestChgDtlsService.java
 * 3. Package  : kr.wise.commons.rqstmst.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 25. 오후 3:37:50
 * </PRE>
 */
public interface RequestChgDtlsService {

	/** @param search
	/** @param subInfo
	/** @return yeonho */
	List<WaqRqstChgDtls> getRqstChangeLst(WaqMstr search, String subInfo);

}
