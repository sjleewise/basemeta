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
package kr.wise.dq.stnd.service;

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
public interface WdqStndItemRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WdqqSditm getStndItemRqstDetail(WdqqSditm searchVo);

	/** @param search
	/** @return insomnia */
	List<WdqqSditm> getStndItemRqstList(WaqMstr search);

	/** @param search
	/** @return insomnia */
	List<Map<String, Object>> getPersCode();

	/** @param search
	/** @return insomnia */
	WdqqSditm getItemWordInfo(WdqqSditm search);

	/** @param data
	/** @return insomnia */
	List<WdqpDvCanAsm> getItemDivisionList(WdqpDvCanDic data) throws Exception;
	
	
	/** @param data
	/** @return insomnia */
	List<WdqpDvCanAsm> getItemDivList(WdqpDvCanDic data);
	
	/** @param data
	/** @return insomnia */
	List<WdqpDvCanAsm> getItemDvRqstList(WdqpDvCanDic data);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, List<WdqqSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delStndItemRqstList(WaqMstr reqmst, ArrayList<WdqqSditm> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regitemdivision(WaqMstr reqmst, List<WdqqSditm> list) throws Exception;
	
	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	Map<String, String>  regItemAutoDiv( List<WdqpDvCanAsm> list,WdqpDvCanDic record2) throws Exception;
	
	Map<String, String>  delItemAutoDiv( List<WdqpDvCanAsm> list) throws Exception;
	
	public int regStndItemByDiv(WaqMstr waqMstr, ArrayList<WdqpDvCanAsm> list);  

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regapprove(WaqMstr reqmst, List<WdqqSditm> list);

	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	
	//용어 존재 여부 확인(표준요청 탭 셀렉트용)
	int checkExistsWaqItem(WaqMstr reqmst);
	
	//재상신용 
	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

	List<WdqqSditm> getUnuseStndItemRqstList(WdqqSditm data);
	

}
