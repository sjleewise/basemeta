/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : LdmTblRqstService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:41:08
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.meta.ldm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ldm.service.WaqLdmEnty;
import kr.wise.meta.ldm.service.WaeLdmAttr;
import kr.wise.meta.ldm.service.WaqLdmAttr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : LdmTblRqstService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:41:08
 * </PRE>
 */
public interface LdmEntyRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WaqLdmEnty getLdmTblRqstDetail(WaqLdmEnty searchVo);  

	/** @param search
	/** @return insomnia */
	List<WaqLdmEnty> getLdmEntyRqstList(WaqMstr search); 

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delLdmTblRqst(WaqMstr reqmst, ArrayList<WaqLdmEnty> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqLdmEnty> list) throws Exception;

	/** @param search
	/** @return insomnia */
	WaqLdmAttr getLdmColRqstDetail(WaqLdmAttr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regLdmColList(WaqMstr reqmst, List<WaqLdmAttr> list); 

	/** @param search
	/** @return insomnia */
	List<WaqLdmAttr> getLdmAttrRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delLdmColRqst(WaqMstr reqmst, ArrayList<WaqLdmAttr> list);

	
	int regLdmXlsTblColList(WaqMstr reqmst, List<WaeLdmAttr> list);  

}
