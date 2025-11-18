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
package kr.wise.meta.pdmrel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmTbl;

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
public interface PdmRelMstRqstService extends CommonRqstService {
	
	/** @param search
	/** @return meta */
	List<WaqPdmRelMst> getPdmRelRqstList(WaqMstr search);

	/** @param search
	/** @return meta */
	WaqPdmRelMst getPdmRelRqstDetail(WaqPdmRelMst search);

	
	/** @param reqmst
	/** @param list
	/** @return meta */
	int delPdmRelRqst(WaqMstr reqmst, ArrayList<WaqPdmRelMst> list);


	int regPdmRelColList(WaqMstr reqmst, List<WaqPdmRelCol> list); 
	
	/** @param reqmst
	/** @param list
	/** @return meta */
	int regPdmxlsRelList(WaqMstr reqmst, List<WaePdmRelCol> list); 

	List<WaqPdmRelCol> getPdmRelColRqstList(WaqMstr search);

	int delPdmRelColRqst(WaqMstr reqmst, ArrayList<WaqPdmRelCol> list);

	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmRelMst> list) throws Exception;   




 
}
