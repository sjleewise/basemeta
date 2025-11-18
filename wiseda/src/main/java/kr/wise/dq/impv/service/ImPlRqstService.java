/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ImPlRqstService.java
 * 2. Package : kr.wise.dq.impv.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 10. 오후 3:29:43
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 10. :            : 신규 개발.
 */
package kr.wise.dq.impv.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.criinfo.bizarea.service.WaqBizAreaVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ImPlRqstService.java
 * 3. Package  : kr.wise.dq.impv.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 10. 오후 3:29:43
 * </PRE>
 */
public interface ImPlRqstService extends CommonRqstService {

	/** @param search
	/** @return meta */
	List<WamBrMstr> getErrBizruleList(WamBrMstr search);

	/** @param search
	/** @return meta */
	List<WaqCsAnaMstr> getVrfImPlListIBS(WaqMstr search);


	

}
