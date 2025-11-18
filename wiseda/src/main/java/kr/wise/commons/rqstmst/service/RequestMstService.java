/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestMstService.java
 * 2. Package : kr.wise.commons.rqstmst.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 6. 오전 9:43:19
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 6. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service;

import java.util.List;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestMstService.java
 * 3. Package  : kr.wise.commons.rqstmst.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 6. 오전 9:43:19
 * </PRE>
 */
public interface RequestMstService {

	//==========요청 Mst Insert ============
	public int insertWaqMst(WaqMstr record) ;

	//==========요청 Mst Update ============
	public int updateWaqMst(WaqMstr record) ;

	//==========요청 Mst Delete ============
	public int deleteWaqMst(WaqMstr record) ;

	//=======요청 Mst 요청상태(RQST_PRC_STEP) Update =====
	public int updateRqstPrcStep(WaqMstr record) ;

	/** @param reqmst shshin
	 * @return */
	public WaqMstr getBizInfoInit(WaqMstr mstVo)  throws Exception;
	
	/** @param reqmst insomnia
	 * @return */
	public WaqMstr getrequestMst(WaqMstr mstVo);

	/** @param string
	/** @param string insomnia
	 * @param bizdtlnm */
	public int updateWaqMstrqstNm(String string, String tblnm, String colnm,  String bizdtlnm);
	
	public int updateWaqMstrqstNm2(String string, String tblnm, String colnm,  String bizdtlnm);

	/** @param rqstNo
	/** @param string
	/** @return insomnia */
	public String getrvwStatus(String rqstNo, String string);

	public int updateApproveInfo(WaqMstr reqmst);

	public int updateRequestInfor(WaqMstr reqmst);

	public int updateRequestMsterNm(WaqMstr reqmst);

	public WaaBizInfo getBizInfo(WaqMstr reqmst);

	/** yeonho */
	public List<WaqMstr> getRqstMyList(WaqMstr record);

	public List<WaqMstr> getRqstMyListForMain(WaqMstr record);

	public List<WaqMstr> getRqstToDoList(WaqMstr record);
	
	public List<WaqMstr> getRqstResultList(WaqMstr record);

	public List<WaqMstr> getRqstToDoListForMain(WaqMstr record);

	public int getRqstMyListCount(WaqMstr record);

	public int getRqstToDoListCount(WaqMstr record);
	
	public WaqMstr getRqstCount(WaqMstr record);
	
	public List<WaqMstr> getRejMyList(WaqMstr record);

}

