/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRqstServie.java
 * 2. Package : kr.wise.meta.stnd.web.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 1:11:02
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordRqstServie.java
 * 3. Package  : kr.wise.meta.stnd.web.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 4. 오후 1:11:02
 * </PRE>
 */
public interface StndWordRqstService extends CommonRqstService {

	/** @param search
	/** @return insomnia */
	List<WaqStwd> getStndWordRqstist(WaqMstr search);

	/** @param list
	/** @return insomnia */
	int regStndWordRqstlist(ArrayList<WaqStwd> list);

	/** @param searchVO
	/** @return insomnia */
	WaqStwd getStndWordRqstDetail(WaqStwd searchVO);

	/** @param reqmst
	/** @param delkey
	/** @return insomnia */
	int delStndWordRqst(WaqMstr reqmst, String delkey);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqStwd> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delStndWordRqstList(WaqMstr reqmst, ArrayList<WaqStwd> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regapprove(WaqMstr reqmst, List<WaqStwd> list); 

	/** @param mstVo
	/** @return insomnia
	 * @throws Exception */
	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	
	int checkExistsWaqWord(WaqMstr mstr);
	
	//재상신 등록용
    int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

	List<WapWordDv> getWordDivList(WapWordDv search) throws Exception;

	Map<String, String> delWordAutoDiv(List<WapWordDv> list) throws Exception;

	Map<String, String> regWordAutoDiv(List<WapWordDv> list) throws Exception; 

}
