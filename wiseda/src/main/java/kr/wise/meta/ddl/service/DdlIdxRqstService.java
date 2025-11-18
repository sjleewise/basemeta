/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlIdxRqstService.java
 * 2. Package : kr.wise.meta.ddl.service
 * 3. Comment :
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 6.  16:15:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 6. :            : 신규 개발.
 */
package kr.wise.meta.ddl.service;

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
 * 2. FileName  : DdlIdxRqstService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 6.  16:15:00
 * </PRE>
 */
public interface DdlIdxRqstService extends CommonRqstService {

	/** @param search
	/** @return meta */
	List<WaqDdlIdx> getDdlIdxRqstList(WaqMstr search);
	
	/** @param searchVo
	/** @return meta */
	WaqDdlIdx getDdlIdxRqstDetail(WaqDdlIdx searchVo);
	
	/** @param search
	/** @return meta */
	List<WaqDdlIdxCol> getDdlIdxColRqstList(WaqMstr search);
	
	/** @param search
	/** @return meta */
	WaqDdlIdxCol getDdlIdxColRqstDetail(WaqDdlIdxCol search);

	/** @param reqmst
	/** @param list
	/** @return meta */
	int regDdlIdxColList(WaqMstr reqmst, List<WaqDdlIdxCol> list);

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlIdx> list) throws Exception;


	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delDdlIdxRqst(WaqMstr reqmst, ArrayList<WaqDdlIdx> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return meta */
	int delDdlIdxColRqst(WaqMstr reqmst, ArrayList<WaqDdlIdxCol> list);

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	HashMap<String, String> regDdlIdxxlsColList(WaqMstr reqmst, List<WaqDdlIdxCol> list) throws Exception;

	
	List<WaqDdlIdx> selectDdlTsfIdxListForRqst(WaqDdlIdx search);

	int regTsfWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlIdx> list) throws Exception;

	List<WaqDdlIdx> getDdlTsfIdxRqstList(WaqMstr search);

	int regWaq2Wam(WaqMstr mstVo) throws Exception;

	int regWaq2WamCol(WaqMstr mstVo) throws Exception;              

	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

}
