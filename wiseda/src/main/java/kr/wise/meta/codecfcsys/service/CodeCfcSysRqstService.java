/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcTblService.java
 * 2. Package : kr.wise.meta.dbc.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 22. 오후 4:20:41
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 22. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;



/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysRqstService.java
 * 3. Package  : kr.wise.meta.codecfcsys.service
 * 4. Comment  : 
 * 5. 작성자   : shshin
 * 6. 작성일   : 2014. 7. 29. 오후 4:20:41
 * </PRE>
 */
public interface CodeCfcSysRqstService extends CommonRqstService {

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSysItem> getCodeCfcSysRqstItemList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSys> getRqstList(WaqMstr search);

	/** @param searchVo
	/** @return meta */
	WaqCodeCfcSys getCodeCfcSysDetail(WaqCodeCfcSysItem searchVo);

	/** @param saveVo
	/** @return meta */
	int saveCodeCfcSysRqst(WaqCodeCfcSys saveVo);

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	int delCodeCfcSysRqst(WaqMstr reqmst, ArrayList<WaqCodeCfcSys> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqCodeCfcSys> list) throws Exception;

	/** @param reqmst meta */
	int deleteOldCodeCfcSysInfo(WaqMstr reqmst);



	

}
