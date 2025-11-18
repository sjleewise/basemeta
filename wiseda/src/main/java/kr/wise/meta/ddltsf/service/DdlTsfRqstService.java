/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTsfRqstService.java
 * 2. Package : kr.wise.meta.ddltsf.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 8. 23. 오후 5:02:03
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 8. 23. :            : 신규 개발.
 */
package kr.wise.meta.ddltsf.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WaqDdlIdx;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlTsfRqstService.java
 * 3. Package  : kr.wise.meta.ddltsf.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 8. 23. 오후 5:02:03
 * </PRE>
 */
public interface DdlTsfRqstService extends CommonRqstService {

	/** @param search
	/** @return insomnia */
	List<WamDdlTsfObj> getDdlTsfList(WaaDbMapVo search);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfTbl> getDdlTsfTblRqstList(WaqMstr search);

	/** @param searchVo
	/** @return yeonho */
	WaqDdlTsfTbl getDdlTsfTblRqstDetail(WaqDdlTsfTbl searchVo);

	/** @param searchVo
	/** @return yeonho */
//	WaqDdlTsfTbl getDdlTsfIdxRqstDetail(WaqDdlTsfTbl searchVo);

	/** @param reqmst
	/** @param list
	/** @return yeonho 
	 * @param dbmsInfo 
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlTsfTbl> list, WaqDdlTsfTbl dbmsInfo) throws Exception;

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfCol> getDdlTsfColRqstList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfRel> getDdlTsfRelRqstList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdxCol> getDdlTsfIdxColRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return yeonho 
	 * @throws Exception */
	int delDdlTsfTblRqst(WaqMstr reqmst, ArrayList<WaqDdlTsfTbl> list) throws Exception;

	WaqDdlIdx getDdlTsfIdxRqstDetail(WaqDdlIdx searchVo);

}
