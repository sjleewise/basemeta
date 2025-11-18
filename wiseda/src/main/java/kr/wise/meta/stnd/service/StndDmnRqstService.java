/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDmnRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 15. 오후 4:27:44
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 15. :            : 신규 개발.
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
 * 2. FileName  : StndDmnRqstService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 15. 오후 4:27:44
 * </PRE>
 */
public interface StndDmnRqstService extends CommonRqstService {

	/** @return insomnia */
	List<WaqDmn> getInfotpList();

	/** @param searchVo
	/** @return insomnia */
	WaqDmn getStndDmnRqstDetail(WaqDmn searchVo);

	/** @param data
	/** @return insomnia */
	Map<String, String> getDmnStwdInfo(WaqDmn data);

	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getDmnDivisionlist(WapDvCanDic data) throws Exception ;

	/** @param search
	/** @return insomnia */
	List<WaqDmn> getDmnRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delList(WaqMstr reqmst, ArrayList<WaqDmn> list);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDmn> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delStndDmnRqst(WaqMstr reqmst, ArrayList<WaqDmn> list) throws Exception;

	/** @param search
	/** @return insomnia */
	List<WaqCdVal> getCdvalRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regCdvalList(WaqMstr reqmst, List<WaqCdVal> list);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delCdvalList(WaqMstr reqmst, List<WaqCdVal> list);

	/** @param data
	/** @return insomnia */
	WaqDmn getDmnInfoByDmnLnm(WaqDmn data);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regapprove(WaqMstr reqmst, List<WaqDmn> list);

	/** @param mstVo
	/** @return insomnia
	 * @throws Exception */
	int regWaq2Wam(WaqMstr mstVo) throws Exception;

	int regWaq2WamCdval(WaqMstr mstVo) throws Exception;

	/** @param reqmst
	/** @return yeonho */
	int delAllCdvalList(WaqMstr reqmst);
	
	
	/** @param data
	/** @return insomnia */
	List<WapDvCanAsm> getDmnDvRqstList(WapDvCanDic data);
	
	/** @param WapDvCanAsm
	/** @return yeonho */
	Map<String, String>  regDmnAutoDiv( List<WapDvCanAsm> list) throws Exception;
	
	
	Map<String, String>  delDmnAutoDiv( List<WapDvCanAsm> list) throws Exception;
	
	
	public int regStndDmnByDiv(WaqMstr waqMstr, ArrayList<WapDvCanAsm> list);  
	
	//요청서 도메인 존재여부 확인
	int checkExistsWaqDmn(WaqMstr reqmst);

	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

	List<WaqDmn> getDomainCdVal(WaqDmn data);

}
