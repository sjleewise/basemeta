/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AsisPdmTblRqstService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                     : . :            : 신규 개발.
 */
package kr.wise.meta.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AsisPdmTblRqstService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
public interface AsisPdmTblRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WaqPdmTbl getPdmTblRqstDetail(WaqPdmTbl searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqPdmTbl> getPdmTblRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delPdmTblRqst(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception;

	/** @param search
	/** @return insomnia */
	WaqPdmCol getPdmColRqstDetail(WaqPdmCol search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regPdmColList(WaqMstr reqmst, List<WaqPdmCol> list);

	/** @param search
	/** @return insomnia */
	List<WaqPdmCol> getPdmColRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delPdmColRqst(WaqMstr reqmst, ArrayList<WaqPdmCol> list);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	HashMap<String, String> regPdmxlsColList(WaqMstr reqmst, List<WaqPdmCol> list);

	/** @param search
	/** @return yeonho */
	List<WaqPdmRel> getPdmRelRqstList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	WaqPdmRel getPdmRelRqstDetail(WaqPdmRel search);

	/** @param reqmst
	/** @param list
	/** @return yeonho */
	int regPdmRelList(WaqMstr reqmst, List<WaqPdmRel> list);

	/** @param reqmst
	/** @param list
	/** @return yeonho */
	int delPdmRelRqst(WaqMstr reqmst, ArrayList<WaqPdmRel> list);

	/** @param reqmst
	/** @param list
	/** @return yeonho */
	int regPdmxlsRelList(WaqMstr reqmst, List<WaqPdmRel> list);

	//모델 재상신 
	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

 
}
