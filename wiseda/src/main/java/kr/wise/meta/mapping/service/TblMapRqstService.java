/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : TblMapRqstService.java
 * 2. Package : kr.wise.meta.mapping.service
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 16. 09:34:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열   : 2014. 7. 16. :            : 신규 개발.
 */
package kr.wise.meta.mapping.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : TblMapRqstService.java
 * 3. Package  : kr.wise.meta.mapping.service
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 16. 09:34:00
 * </PRE>
 */
public interface TblMapRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return 유성열 */
	WaqTblMap getTblMapRqstDetail(WaqTblMap searchVo);

	/** @param search
	/** @return 유성열 */
	List<WaqTblMap> getTblMapRqstList(WaqMstr search);
	
	/** @param search
	/** @return 유성열 */
	List<WaqTblMap> getTgtTblList(WaqTblMap search);
	
	/** @param search
	/** @return 유성열 */
	List<WamTblMap> getTblMapList(WamTblMap search);

	/** @param reqmst
	/** @param list
	/** @return 유성열
	 * @throws Exception */
	int delTblMapRqst(WaqMstr reqmst, ArrayList<WaqTblMap> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return 유성열
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqTblMap> list) throws Exception;


}
