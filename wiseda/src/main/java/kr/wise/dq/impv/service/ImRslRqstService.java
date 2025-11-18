/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ImRslRqstService.java
 * 2. Package : kr.wise.dq.impv.service
 * 3. Comment :
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 10. 오후 3:29:51
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 10. :            : 신규 개발.
 */
package kr.wise.dq.impv.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ImRslRqstService.java
 * 3. Package  : kr.wise.dq.impv.service
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 10. 오후 3:29:51
 * </PRE>
 */

public interface ImRslRqstService extends CommonRqstService {

	/** @param search
	/** @return meta */
	List<WaqImActMstr> getImRslList(WaqImActMstr search);

	/** @param search
	/** @return meta */
	List<WaqImActMstr> getVrfImRslListIBS(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqImActMstr> getRslList(WaqImActMstr search);

	/**
	 * @param anaJobId
	 * @return
	 */
	List<WaqImActMstr> getImplHstLst(String anaJobId, String anaStrDtm);

	/**
	 * @param anaJobId
	 * @return
	 */
	List<WaqImActMstr> getImrslHstLst(String anaJobId, String anaStrDtm);



}
