/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysItemRqstService.java
 * 2. Package : kr.wise.meta.codecfcsys.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 8. 오후 1:32:29
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 8. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.service;

import java.util.ArrayList;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysItemRqstService.java
 * 3. Package  : kr.wise.meta.codecfcsys.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 8. 오후 1:32:29
 * </PRE>
 */
public interface CodeCfcSysItemRqstService extends CommonRqstService {

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	int delCodeCfcSysItemRqstList(WaqMstr reqmst,
			ArrayList<WaqCodeCfcSysItem> list) throws Exception;

	/** @param reqmst meta */
	int deleteOldCodeCfcSysItemInfo(WaqMstr reqmst);

}
