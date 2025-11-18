/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndItemRqstService.java
 * 2. Package : kr.wise.meta.app.service
 * 3. Comment :
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanDic;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndItemRqstService.java
 * 3. Package  : kr.wise.meta.app.service
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
public interface AppStndItemRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WaqAppSditm getStndItemRqstDetail(WaqAppSditm searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqAppSditm> getStndItemRqstList(WaqMstr search);

	/** @param search
	/** @return insomnia */
	WaqAppSditm getItemWordInfo(WaqAppSditm search);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, List<WaqAppSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delStndItemRqstList(WaqMstr reqmst, ArrayList<WaqAppSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regitemdivision(WaqMstr reqmst, List<WaqAppSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regapprove(WaqMstr reqmst, List<WaqAppSditm> list);

	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	
	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getItemDivisionList(WapDvCanDic data) throws Exception;
	
	
	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getItemDivList(WapDvCanDic data);

}
