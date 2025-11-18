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
package kr.wise.meta.symn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public interface SymnItemRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WaqSymnItem getStndItemRqstDetail(WaqSymnItem searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqSymnItem> getStndItemRqstList(WaqMstr search);

	/** @param search
	/** @return insomnia */
	WaqSymnItem getItemWordInfo(WaqSymnItem search);

/*	List<WapDvCanAsm> getItemDivisionList(WapDvCanDic data) throws Exception;
	List<WapDvCanAsm> getItemDivList(WapDvCanDic data);
	List<WapDvCanAsm> getItemDvRqstList(WapDvCanDic data);
	
	Map<String, String>  regItemAutoDiv( List<WapDvCanAsm> list,WapDvCanDic record2) throws Exception;
	Map<String, String>  delItemAutoDiv( List<WapDvCanAsm> list) throws Exception;
	public int regStndItemByDiv(WaqMstr waqMstr, ArrayList<WapDvCanAsm> list);  */

	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqSymnItem> list) throws Exception;
	int delStndItemRqstList(WaqMstr reqmst, ArrayList<WaqSymnItem> list) throws Exception;
	int regitemdivision(WaqMstr reqmst, List<WaqSymnItem> list) throws Exception;



	int regApprove(WaqMstr reqmst, List<WaqSymnItem> list);

	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	
	//용어 존재 여부 확인(표준요청 탭 셀렉트용)
	int checkExistsWaqItem(WaqMstr reqmst);
	
	//재상신용 
	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

	int delSymnItemRqstList(WaqMstr reqmst, ArrayList<WaqSymnItem> list)  throws Exception ;

}
