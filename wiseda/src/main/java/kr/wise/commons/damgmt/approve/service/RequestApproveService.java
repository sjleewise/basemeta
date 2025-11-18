/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestApproveServie.java
 * 2. Package : kr.wise.commons.damgmt.approve.servie
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 28. 오전 11:06:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 28. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.approve.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestApproveServie.java
 * 3. Package  : kr.wise.commons.damgmt.approve.servie
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 28. 오전 11:06:57
 * </PRE>
 */
public interface RequestApproveService {

	/** @param search
	/** @return insomnia */
	List<WaaRqstAprp> getRequestApproveList(WaaRqstAprp search);

	/** @param list
	/** @return insomnia */
	int regRequestApproveList(ArrayList<WaaRqstAprp> list);

	/**  insomnia */
	void updateAllbyLine();

	/** @param search
	/** @return insomnia */
	List<WaaAprPrc> getapproveline(WaqMstr search);

	/** @param reqmst insomnia
	 * @return */
	ArrayList<WaaRqstAprp> getapproveuserbyline(WaqMstr reqmst);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int submit(WaqMstr reqmst, ArrayList<WaaAprPrc> list);

	public int saveApproveProcess(WaaAprPrc savevo);

	/** @param mstVo
	/** @return insomnia */
	WaaAprPrc updateApproveProcessLine(WaqMstr mstVo);

	/** @param mstVo
	/** @return insomnia */
	int getCountApprovePorcess(WaqMstr mstVo);

	/** @param mstVo
	/** @param string insomnia */
	boolean setApproveProcess(WaqMstr mstVo, String tblnm);

	/** @param reqmst insomnia */
	int setWaqObjectID(WaqMstr reqmst);

	/** @param mstVo
	/** @return insomnia */
	boolean setApproveProcess(WaqMstr mstVo);
	
	boolean setApproveProcessWdq(WaqMstr mstVo);

}
