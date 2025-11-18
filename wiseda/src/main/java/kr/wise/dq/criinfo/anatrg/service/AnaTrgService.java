/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileService.java
 * 2. Package : kr.wise.dq.criinfo.profile.service
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:31:01
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.criinfo.anatrg.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.codemng.service.WaaCdRule;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ColProfileService.java
 * 3. Package  : kr.wise.dq.criinfo.profile.service
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:31:01
 * </PRE>
 */
public interface AnaTrgService {
	//진단대상 조회 팝업
	List<AnaTrgTblVO> getAnaTrgTblLst(AnaTrgTblVO search);
	
	List<AnaTrgColVO> getAnaTrgColLst(AnaTrgTblVO search);
	
	//프로파일 진단대상 조회
	List<AnaTrgTblVO> getPrfTblLst(AnaTrgTblVO search);
	
	List<AnaTrgColVO> getPrfColLst(AnaTrgTblVO search);
	
	AnaTrgTblVO getAnaTrgTblDtl(AnaTrgTblVO search);
	
	AnaTrgTblVO getAnaTrgColDtl(AnaTrgTblVO search);

	List<WaaDbConnTrgVO> selectList(WaaDbConnTrgVO search);
	
	List<WaaDbConnTrgVO> selectHstList(String dbConnTrgId);
	
	//DBMS 스키마정보 곽효신
	List<WaaDbConnTrgVO> getDbSchList(String dbConnTrgId);
	
	WaaDbConnTrgVO selectAnaTrgDbmsDetail(String dbConnTrgId);
	
	List<WaaDbSch> selectDbSchList(WaaDbSch search);
	
	WaaDbSch selectDbSchDetail(String dbSchId);

	/** @param search
	/** @return meta */
	List<AnaTrgTblVO> getPrfTblLstNotRedline(AnaTrgTblVO search);

	/** @param search
	/** @return meta */
	List<AnaTrgColVO> getPrfColLstNotRedline(AnaTrgTblVO search);
	
	List<WaaExpTbl> selectTrgTbl(WaaExpTbl vo);  

	int regTrgTblList(ArrayList<WaaExpTbl> list) throws Exception;
	
	List<WaaColRuleRel> getCheckRuleTbl(WaaExpTbl vo);

	int regChkRuleAply(ArrayList<WaaColRuleRel> list) throws Exception; 

	int delRuleAply(ArrayList<WaaColRuleRel> list) throws Exception; 

	List<VrfcruleVO> getVrfcRuleLst(VrfcruleVO vo);

	AnaTrgTblVO getAnaTrgTblReacDtl(AnaTrgTblVO search);

	WaaDbConnTrgVO getDbmsInfo(WaaCdRule search); 

	List<WaaCdRule> getWaaCdRule(WaaCdRule search);

	List<WaaColRuleRel> getItmAnaExec(WaaColRuleRel vo);

	List<AnaTrgColVO> getPrfColLstCheck(AnaTrgTblVO search);

	List<AnaTrgTblVO> getPrfTblLstAna(AnaTrgTblVO search);

	List<WaaExpTbl> selectExpRuleTbl(WaaExpTbl vo);

	int regExpRuleTbl(ArrayList<WaaExpTbl> list) throws Exception;

	int delExpRule(ArrayList<WaaExpTbl> list) throws Exception;	
	
	//vrfcrulePoidown을 위한 dbms정보
	WaaDbConnTrgVO getByDbms(WaaDbConnTrgVO search);
	
}
