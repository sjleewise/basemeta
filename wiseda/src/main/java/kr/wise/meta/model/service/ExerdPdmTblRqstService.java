/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:41:08
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.meta.model.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:41:08
 * </PRE>
 */
public interface ExerdPdmTblRqstService extends CommonRqstService {

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
	int regExerd2Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception;

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
	int regPdmxlsColList(WaqMstr reqmst, List<WaqPdmCol> list);

	//exerd모델마트연계 관계정보 조회
	List<WaqPrfRelColVO> getExerdRelLst(WaqPrfRelColVO search);
	
	List<WaqPdmTbl> getExerdTblList(WaqPdmTbl search);
	
//	List<WaqPdmTbl> getExerdTblList(WaqPdmTbl search) throws Exception;


}
