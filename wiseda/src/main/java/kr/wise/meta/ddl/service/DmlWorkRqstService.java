/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DmlWorkRqstService.java
 * 2. Package : kr.wise.meta.ddl.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 14. 오후 3:09:29
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 14. :            : 신규 개발.
 */
package kr.wise.meta.ddl.service;

import kr.wise.commons.rqstmst.service.CommonRqstService;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DmlWorkRqstService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 14. 오후 3:09:29
 * </PRE>
 */
public interface DmlWorkRqstService extends CommonRqstService {

	/** @param search
	/** @return insomnia */
//	List<WaqDdlTbl> getDdlTblRqstList(WaqMstr search);

	/** @return insomnia
	 * @throws Exception */
//	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
//	int delDdlTblRqst(WaqMstr reqmst, ArrayList<WaqDdlTbl> list) throws Exception;

	/** @param searchVo
	/** @return insomnia */
//	WaqDdlTbl getDdlTblRqstDetail(WaqDdlTbl searchVo);

	/** @param search
	/** @return insomnia */
//	List<WaqDdlCol> getDdlColRqstList(WaqMstr search);

	/** @param search
	/** @return meta */
//	List<WaqDdlRel> getDdlRelRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return meta */
//	int delDdlRelRqst(WaqMstr reqmst, ArrayList<WaqDdlRel> list);

}
