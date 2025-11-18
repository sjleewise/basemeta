/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 28. 오전 8:48:38
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 28. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndItemRqstService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 28. 오전 8:48:38
 * </PRE>
 */
public interface StndItemRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WaqSditm getStndItemRqstDetail(WaqSditm searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqSditm> getStndItemRqstList(WaqMstr search);

	/** @param search
	/** @return insomnia */
	List<Map<String, Object>> getPersCode();

	/** @param search
	/** @return insomnia */
	WaqSditm getItemWordInfo(WaqSditm search);

	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getItemDivisionList(WapDvCanDic data) throws Exception;
	
	
	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getItemDivList(WapDvCanDic data);
	
	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getItemDvRqstList(WapDvCanDic data);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, List<WaqSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delStndItemRqstList(WaqMstr reqmst, ArrayList<WaqSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regitemdivision(WaqMstr reqmst, List<WaqSditm> list) throws Exception;
	
	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	Map<String, String>  regItemAutoDiv( List<WapDvCanAsm> list,WapDvCanDic record2) throws Exception;
	
	Map<String, String>  delItemAutoDiv( List<WapDvCanAsm> list) throws Exception;
	
	public int regStndItemByDiv(WaqMstr waqMstr, ArrayList<WapDvCanAsm> list);  

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regapprove(WaqMstr reqmst, List<WaqSditm> list);

	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	
	//용어 존재 여부 확인(표준요청 탭 셀렉트용)
	int checkExistsWaqItem(WaqMstr reqmst);
	
	//재상신용 
	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

	List<WaqSditm> getUnuseStndItemRqstList(WaqSditm data);

}
