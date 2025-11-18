/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MsgStdRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    :  :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgStdRqstService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
public interface MsgStdRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return  */
	WaqStdErrMsg getMsgRqstDetail(WaqStdErrMsg searchVo);

	/** @param search
	/** @return  */
	List<WaqStdErrMsg> getMsgRqstList(WaqMstr search);

	int regWam2Waq(WaqMstr reqmst, List<WaqStdErrMsg> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return  */
	int delMsgRqstList(WaqMstr reqmst, ArrayList<WaqStdErrMsg> list) throws Exception;

	int regWaq2Wam(WaqMstr mstVo) throws Exception;

}
