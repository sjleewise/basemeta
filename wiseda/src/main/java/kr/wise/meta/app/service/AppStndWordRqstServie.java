/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndWordRqstServie.java
 * 2. Package : kr.wise.meta.app.service
 * 3. Comment :
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndWordRqstServie.java
 * 3. Package  : kr.wise.meta.app.service
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
public interface AppStndWordRqstServie extends CommonRqstService {

	/** @param search
	/** @return insomnia */
	List<WaqAppStwd> getStndWordRqstist(WaqMstr search);

	/** @param list
	/** @return insomnia */
	int regStndWordRqstlist(ArrayList<WaqAppStwd> list);

	/** @param searchVO
	/** @return insomnia */
	WaqAppStwd getStndWordRqstDetail(WaqAppStwd searchVO);

	/** @param reqmst
	/** @param delkey
	/** @return insomnia */
	int delStndWordRqst(WaqMstr reqmst, String delkey);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqAppStwd> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delStndWordRqstList(WaqMstr reqmst, ArrayList<WaqAppStwd> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regapprove(WaqMstr reqmst, ArrayList<WaqAppStwd> list);

	/** @param mstVo
	/** @return insomnia
	 * @throws Exception */
	int regWaq2Wam(WaqMstr mstVo) throws Exception;

	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;
}
