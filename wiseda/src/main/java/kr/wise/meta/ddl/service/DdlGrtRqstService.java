/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlGrtRqstService.java
 * 2. Package : kr.wise.meta.ddl.service
 * 3. Comment :
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 6.  16:15:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 6. :            : 신규 개발.
 */
package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlGrtRqstService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 6.  16:15:00
 * </PRE>
 */
public interface DdlGrtRqstService extends CommonRqstService {

	/** @param search
	/** @return yeonho */
	List<WaqDdlGrt> getDdlGrtRqstList(WaqMstr search);
	
	/** @param searchVo
	/** @return yeonho */
	WaqDdlGrt getDdlGrtRqstDetail(WaqDdlGrt searchVo);
	
	/** @param reqmst
	/** @param list
	/** @return yeonho 
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlGrt> list) throws Exception;


	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delDdlGrtRqst(WaqMstr reqmst, ArrayList<WaqDdlGrt> list) throws Exception;


	int regWaq2Wam(WaqMstr mstVo) throws Exception;

	void updateDdlGrtScriptWaq(WaqMstr mstVo) throws Exception;

	//GRANTOR 오브젝트 삭제로 인한 권한 삭제
	int insertWaqDdlGrtGrtorObjDD(String rqstNo) throws Exception;

	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;
}
