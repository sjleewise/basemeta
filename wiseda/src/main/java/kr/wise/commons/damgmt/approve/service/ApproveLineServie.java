/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ApproveLineServie.java
 * 2. Package : kr.wise.commons.damgmt.approve.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 27. 오후 2:34:16
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 27. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.approve.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ApproveLineServie.java
 * 3. Package  : kr.wise.commons.damgmt.approve.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 27. 오후 2:34:16
 * </PRE>
 */
public interface ApproveLineServie {

	/** 요청서별 결재라인 리스트 조회 @return insomnia */
	List<WaaRqstBizApr> getapprovelinelist(WaaRqstBizApr search);

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regaprvlinelise(ArrayList<WaaRqstBizApr> list) throws Exception;

	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delaprvlinelist(ArrayList<WaaRqstBizApr> list) throws Exception;

	/** @param search
	/** @return insomnia */
	List<WaaAprPrc> getapproveprocess(WaqMstr search);

	/** @param reqmst
	/** @return insomnia */
	Boolean checkapproveuser(WaqMstr reqmst);

	int checkRequst(MstrAprPrcVO mapvo);

	int requestCancel(String rqst_no); 



}
