package kr.wise.dq.criinfo.anatrg.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.ConnectionHelper;
import kr.wise.commons.util.SchedulerUtils;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.codemng.service.WaaCdRule;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgColVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.criinfo.anatrg.service.WaaColRuleRel;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaExpTbl;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DqiCtrl.java
 * 2. Package : kr.wise.dq.criinfo.dqi.web
 * 3. Comment : 데이터품질지표 Controllor
 * 4. 작성자  : shshin(신상현)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    shshin(신상현) : 2014. 3. 19. :            : 신규 개발.
 *                    meta(김연호) : 2014. 4.  1. :            : 조회부분 구현.
 * </PRE>
 */
@Controller
public class AnaTrgCtrl {
	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private AnaTrgService anaTrgService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	ScheduleManagerService scheduleManagerService;	
	
	static class WaaExpTbls extends HashMap<String, ArrayList<WaaExpTbl>> { }
	
	static class WaaColRuleRels extends HashMap<String, ArrayList<WaaColRuleRel>> { }

	static class WamShdJobVO extends HashMap<String, ArrayList<WamShdJob>> { }

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	/** @return meta */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String dbmstypvers 		= UtilJson.convertJsonString(codeListService.getCodeList("dbmstypvers"));
		codeMap.put("dbmstypvers", dbmstypvers);
		//시스템영역 코드리스트 JSON

		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		String dbmsVersCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD"));
		String vrfcTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP"));
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		
		// DBMS + 스키마 (double_select)
		String connTrgDbmsSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2"));
		codeMap.put("connTrgDbmsSch", connTrgDbmsSch);
		
		//진단대상(DB_CONN_TRG_ID) 정보 (Grantor)
		List<CodeListVo> connTrgDbms2   = codeListService.getCodeList("connTrgDbms"); 
		codeMap.put("ConnTrgDbmsibs2", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms2)));
		codeMap.put("ConnTrgDbms2", connTrgDbms2);
		
		//스키마 Double Select(IBSheet) (Grantor)
		Map<String, ComboIBSVo> connTrgSchIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("ConnTrgSchibs2",  UtilJson.convertJsonString(connTrgSchIbs));
								
//		System.out.println("값 체크중" + connTrgDbms);
		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		codeMap.put("dbmsVersCdibs", dbmsVersCd);
		codeMap.put("devConnTrgSch", connTrgSch);
		
		codeMap.put("dbmstypcdibs", dbmsTypCd);
		codeMap.put("vrfcTypibs", vrfcTypCd);
		
		codeMap.put("dbmsverscdibs", dbmsVersCd);
		
		String schdKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SHD_KND_CD"));
		codeMap.put("schdKndCdibs", schdKndCd);
		
		codeMap.put("shdKndCd", cmcdCodeService.getCodeList("SHD_KND_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("dbmsTypCd",cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsVersCd",cmcdCodeService.getCodeList("DBMS_VERS_CD"));

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);
		
		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		
		
		return codeMap;
	}	


	@RequestMapping("/dq/criinfo/anatrg/anatrgdbms_lst.do")
	public String formDbmspage() {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/criinfo/anatrg/anatrgdbms_lst";
	}

	@RequestMapping("/dq/criinfo/anatrg/anatrgschema_lst.do")
	public String formSchemapage() {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		return "/dq/criinfo/anatrg/anatrgschema_lst";
	}

	//곽효신
	@RequestMapping("/dq/criinfo/anatrg/anadbmsrqst.do")
	public String anaDbmsRqst(String linkFlag, Model model) {
		logger.debug("linkFlag : {}", linkFlag);
		model.addAttribute("linkFlag", linkFlag);
		//  DBMS관리
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		return "/dq/criinfo/anatrg/anadbmsrqst";
	}

	@RequestMapping("/dq/criinfo/anatrg/popup/dbconntrg_pop.do")
	public String dbConnTrgPop(String sflag, ModelMap model) {
		//  DBMS관리
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}

		model.addAttribute("sflag", sflag);

		return "/dq/criinfo/anatrg/popup/dbconntrg_pop";
	}

	//테이블 조회 팝업
	@RequestMapping(value="/dq/criinfo/anatrg/popup/anatrgtbl_pop.do", produces="text/plain;charset=UTF-8")
	public String formTableSearchPopuppage(@ModelAttribute("searchVO") AnaTrgTblVO anaTrgTblVO,  ModelMap model ) {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}

		return "/dq/criinfo/anatrg/popup/anatrgtbl_pop";
	}

	// 테이블 조회 팝업
	@RequestMapping("/dq/criinfo/anatrg/getAnaTrgTblLst.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgTblVO> getAnaTrgTblLst(@ModelAttribute AnaTrgTblVO search, Locale locale) {
		logger.debug("{}", search);
		List<AnaTrgTblVO> list = anaTrgService.getAnaTrgTblLst(search);
		return new IBSheetListVO<AnaTrgTblVO>(list, list.size());
	}

	//테이블명 검색 팝업
	@RequestMapping("/dq/criinfo/anatrg/popup/tbllst_pop.do")
	public String tblLstPop(@ModelAttribute("search") AnaTrgTblVO anaTrgTblVO, ModelMap model) throws Exception{

		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		model.addAttribute("search", anaTrgTblVO);
		return "/dq/criinfo/anatrg/popup/tbllst_pop";
	}

	/* 진단대상 테이블 조회 */
	@RequestMapping("/dq/criinfo/anatrg/getPrfTblLst.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgTblVO> getPrfTblLst(AnaTrgTblVO search, Locale locale) {
		logger.debug("{}", search);
		List<AnaTrgTblVO> list = anaTrgService.getPrfTblLst(search);
		return new IBSheetListVO<AnaTrgTblVO>(list, list.size());
	}
	
	/* 진단대상 테이블 조회 (코드테이블 조회용 FontColor 삭제) */
	@RequestMapping("/dq/criinfo/anatrg/getPrfTblLstNotRedline.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgTblVO> getPrfTblLstNotRedline(AnaTrgTblVO search, Locale locale) {
		logger.debug("{}", search);
		List<AnaTrgTblVO> list = anaTrgService.getPrfTblLstNotRedline(search);
		return new IBSheetListVO<AnaTrgTblVO>(list, list.size());
	}

	//컬럼 조회 팝업
	@RequestMapping("/dq/criinfo/anatrg/popup/anatrgcol_pop.do")
	public String formColumnSearchPopuppage(@ModelAttribute("searchVO") AnaTrgTblVO anaTrgTblVO,  ModelMap model ) {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}

		return "/dq/criinfo/anatrg/popup/anatrgcol_pop";
	}

	//컬럼 조회 팝업
	@RequestMapping("/dq/criinfo/anatrg/getAnaTrgColLst.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgColVO> getAnaTrgColLst(@ModelAttribute AnaTrgTblVO search, Locale locale) {
		logger.debug("search {}", search);
		List<AnaTrgColVO> list = anaTrgService.getAnaTrgColLst(search);
		logger.debug("list {}", list);
		return new IBSheetListVO<AnaTrgColVO>(list, list.size());
	}

	/** 진단대상 DBMS 리스트 조회 - IBSheet JSON */
	/** @param search
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/selectAnaTrgDbms_lst.do")
	@ResponseBody
	public IBSheetListVO<WaaDbConnTrgVO> selectList(@ModelAttribute WaaDbConnTrgVO search) {
		logger.debug("{}", search);
		logger.debug("진단대상명 : {}", search.getDbConnTrgId());
		List<WaaDbConnTrgVO> list = anaTrgService.selectList(search);

		return new IBSheetListVO<WaaDbConnTrgVO>(list, list.size());

	}

	//팝업창 조회 버튼 결과
	@RequestMapping("/dq/criinfo/anatrg/popSelect.do")
	@ResponseBody
	//@ModelAttribute WaaDbConnTrgVO search
	public IBSheetListVO<WaaDbConnTrgVO> popSelect(@RequestParam("vrfcNm") String vrfcNm,@RequestParam("useYn") String useYn,@ModelAttribute WaaDbConnTrgVO search ) {
		logger.debug("{}", search);
//			search.setVrfcNm(vrfcNm);
//			search.setUseYn(useYn);
	
		//logger.debug("진단대상명 : {}", search.getDbConnTrgId());
		List<WaaDbConnTrgVO> list = anaTrgService.selectList(search);

		return new IBSheetListVO<WaaDbConnTrgVO>(list, list.size());

	}
	/** 진단대상 DBMS 상세 정보 조회 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/ajaxgrid/selectAnaTrgDbmsDetail.do")
	public String selectConnTrgDetail(String dbConnTrgId, ModelMap model) {
		logger.debug("진단대상 DBMS ID : {}", dbConnTrgId);


		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dbConnTrgId)) {

			WaaDbConnTrgVO result = anaTrgService.selectAnaTrgDbmsDetail(dbConnTrgId);
//			logger.debug("결과값이 제대로 나오나? {}", result);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}

		return "/dq/criinfo/anatrg/anatrgdbms_dtl";
	}
	/** 진단대상 DBMS 변경이력 리스트 조회 - IBSheet JSON */
	/** @param search
	/** @return meta */
	@RequestMapping("/dq/criinfo/ajaxgrid/getTrgDbmsHstLst.do")
	@ResponseBody
	public IBSheetListVO<WaaDbConnTrgVO> selectHstList(String dbConnTrgId) {

		logger.debug("변경이력조회 진단대상 ID : {}", dbConnTrgId);
		List<WaaDbConnTrgVO> list = anaTrgService.selectHstList(dbConnTrgId);

		return new IBSheetListVO<WaaDbConnTrgVO>(list, list.size());

	}

	/** 진단대상 스키마 리스트 조회 - IBSheet JSON */
	/** @param search
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/selectDbSch_lst.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> selectDbSchList(@ModelAttribute WaaDbSch search) {
		logger.debug("{}", search);
		logger.debug("진단대상명 : {}", search.getDbSchId());
		List<WaaDbSch> list = anaTrgService.selectDbSchList(search);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}

	/** 진단대상 스키마 상세 정보 조회 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/ajaxgrid/selectDbSch_dtl.do")
	public String selectDbSchDetail(String dbSchId, ModelMap model) {
		logger.debug("진단대상 스키마 ID : {}", dbSchId);


		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dbSchId)) {

			WaaDbSch result = anaTrgService.selectDbSchDetail(dbSchId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}

		return "/dq/criinfo/anatrg/anatrgschema_dtl";
	}
	
	@RequestMapping("/dq/criinfo/anatrg/trgtbl_rqst.do")
	public String goTrgTbl() {
		
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	
    	
		return "/dq/criinfo/anatrg/trgtbl_rqst";
	}
	
	/** 진단대상 테이블 정보 조회 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/getTrgTbl.do")
	@ResponseBody
	public IBSheetListVO<WaaExpTbl> getTrgTbl(WaaExpTbl vo, ModelMap model) { 
		 
		List<WaaExpTbl> list = anaTrgService.selectTrgTbl(vo);     
						
		return new IBSheetListVO<WaaExpTbl>(list, list.size()); 
	}
	
	
	@RequestMapping("/dq/criinfo/anatrg/regTrgTbl.do")
	@ResponseBody
	public IBSResultVO<WaaExpTbl> regTrgTblList(@RequestBody WaaExpTbls data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<WaaExpTbl> list = data.get("data");
		
		int result = anaTrgService.regTrgTblList(list);  
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaExpTbl>(result, resmsg, action);

	}
	
	/** 검증룰 적용 화면 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/chkruleaply_rqst.do")
	public String goCheckRuleAply() {
		
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	
    	
		return "/dq/criinfo/anatrg/chkruleaply_rqst";
	}
	
	/** 검증룰 적용 조회 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/getCheckRuleTbl.do")
	@ResponseBody
	public IBSheetListVO<WaaColRuleRel> getCheckRuleTbl(WaaExpTbl vo, ModelMap model) { 
		List<WaaColRuleRel> list = anaTrgService.getCheckRuleTbl(vo);  
						
		return new IBSheetListVO<WaaColRuleRel>(list, list.size()); 
	}
	
	@RequestMapping("/dq/criinfo/anatrg/regChkRuleAply.do")
	@ResponseBody
	public IBSResultVO<WaaExpTbl> regChkRuleAply(@RequestBody WaaColRuleRels data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<WaaColRuleRel> list = data.get("data"); 
		
		int result = anaTrgService.regChkRuleAply(list);    
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaExpTbl>(result, resmsg, action);

	}
	
	@RequestMapping("/dq/criinfo/anatrg/delChkRuleAply.do")
	@ResponseBody
	public IBSResultVO<WaaExpTbl> delRuleAply(@RequestBody WaaColRuleRels data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<WaaColRuleRel> list = data.get("data"); 
		
		int result = anaTrgService.delRuleAply(list);    
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaExpTbl>(result, resmsg, action);

	}
	
	/** 검증룰 팝업 화면 */
	@RequestMapping("/dq/criinfo/anatrg/popup/vrfcrule_pop.do")
	public String goVrfcSearchPop(@ModelAttribute("search") WaaColRuleRel search
								, Model model
								, @RequestParam(value="row") String row
								, @RequestParam(value="code") String code
								, Locale locale) {
		logger.debug("파람값은 : {}", row);
		model.addAttribute("row", row);
		model.addAttribute("code", code);
		model.addAttribute("search", search);
		return "/dq/criinfo/anatrg/popup/vrfcrule_pop";
	}
	
	@RequestMapping("/dq/criinfo/anatrg/popup/vrfcSelectlist.do")
	@ResponseBody
	public IBSheetListVO<VrfcruleVO> getVrfcRuleLst(WaaColRuleRel vo, Model model) { 
		VrfcruleVO vrfcVo = new VrfcruleVO();
		vrfcVo.setVrfcId(vo.getVrfcId());
		vrfcVo.setVrfcNm(vo.getVrfcNm());
		vrfcVo.setVrfcTyp(vo.getVrfcTyp());
		
		List<VrfcruleVO> list = anaTrgService.getVrfcRuleLst(vrfcVo);      
						
		return new IBSheetListVO<VrfcruleVO>(list, list.size()); 
	}
	
	/** 코드겁색  화면 */
	@RequestMapping("/dq/criinfo/anatrg/popup/cdRuleSelectlist.do")
	@ResponseBody
	public IBSheetListVO<HashMap> goCdSearchPop(@ModelAttribute("search") WaaCdRule search, Model model, Locale locale) {
		
		JSONArray json = cdRuleExec(search);
		
		if(json!=null){
			return new IBSheetListVO<HashMap>(json, json.size()); 
		}else{
			return new IBSheetListVO<HashMap>(json,0);
		}
	}
	
	//코드 조회 메소드
	private JSONArray cdRuleExec(WaaCdRule search) {
		boolean result = false;
		
		Connection con = null;
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		JSONArray json = null; 
		
		WaaDbConnTrgVO targetDbmsDM = anaTrgService.getDbmsInfo(search);
		
		logger.debug("targetDbmsDM:{}" + targetDbmsDM.getDbmsTypCd());
		
		String dbmsTypCd = UtilString.null2Blank(targetDbmsDM.getDbmsTypCd());
			
		logger.debug("dbmsTypCd:{}" + dbmsTypCd);
		
		try {
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getConnTrgDrvrNm(), targetDbmsDM.getConnTrgLnkUrl(), targetDbmsDM.getDbConnAcId(), targetDbmsDM.getDbConnAcPwd());
	
			
			List<WaaCdRule> cdRulelst = anaTrgService.getWaaCdRule(search); 
			
			String sql = "";
			String cdRuleId = "";
			String cdRuleNm = "";
			String sCdClsCd = "";
			String sCdClsNm = "";
			String sCdTypCd = "";
			
			int i = 0;
			
			for(WaaCdRule vo : cdRulelst){
				
				sCdClsCd = UtilString.null2Blank(vo.getCdClsColNm());
				sCdClsNm = UtilString.null2Blank(vo.getCdClsNmColNm()); 					
				cdRuleId = UtilString.null2Blank(vo.getCdRuleId());
				cdRuleNm = UtilString.null2Blank(vo.getCdRuleNm());
				sCdTypCd = UtilString.null2Blank(vo.getCdTypCd());
				
				if(sCdTypCd.equals("LC")) {
					
					if(i > 0) {
						
						sql += "\n UNION ALL ";
					}else{
														
						sql += "\n SELECT  코드분류, 코드분류명, 코드, 코드명,  CD_RULE_ID, CD_RULE_NM, CD_TYP_CD   ";
						sql += "\n   FROM ( ";
					}
									
					if(dbmsTypCd.equals("POS")){
						sql += "\n SELECT '' AS 코드분류, '' AS 코드분류명, 코드, 코드명 , CAST('" + cdRuleId +"' AS TEXT) AS CD_RULE_ID,  CAST('" + cdRuleNm + "' AS TEXT) AS CD_RULE_NM  CAST('" + sCdTypCd +"' AS TEXT)  AS CD_TYP_CD";
						sql += "\n   FROM DUAL ";
					}else{
						sql += "\n SELECT  '' AS 코드분류, '' AS 코드분류명, '' AS 코드, '' AS 코드명 , '" + cdRuleId +"' AS CD_RULE_ID,  '" + cdRuleNm + "' AS CD_RULE_NM, '" + sCdTypCd +"' AS CD_TYP_CD";
						sql += "\n   FROM DUAL";
					}
													
//					sql += UtilString.null2Blank(vo.getCdSql());
//					
//					sql += "\n   ) X ";
					
					i++;
					
				}else if(sCdTypCd.equals("CC")) {
					
					if(i > 0) {
						
						sql += "\n UNION ALL ";
					}else{
						sql += "\n SELECT  코드분류, 코드분류명, 코드, 코드명,  CD_RULE_ID, CD_RULE_NM, CD_TYP_CD   ";		
						sql += "\n FROM (";	
						
					}
									
					if(dbmsTypCd.equals("POS")){
						sql += "\n 		SELECT  코드분류, 코드분류명, 코드, 코드명,  CD_RULE_ID, CD_RULE_NM, CD_TYP_CD   ";
						sql += "\n   	FROM ( ";
						sql += "\n 			SELECT  코드분류, 코드분류명, '' AS 코드, '' AS 코드명, CAST('" + cdRuleId +"' AS TEXT) AS CD_RULE_ID,  CAST('" + cdRuleNm + "' AS TEXT) AS CD_RULE_NM  CAST('" + sCdTypCd +"' AS TEXT)  AS CD_TYP_CD";
						sql += "\n   		FROM ( ";
					}else{
						sql += "\n 		SELECT  코드분류, 코드분류명, 코드, 코드명,  CD_RULE_ID, CD_RULE_NM, CD_TYP_CD   ";
						sql += "\n   	FROM ( ";
						sql += "\n 			SELECT  코드분류, 코드분류명, '' AS 코드, '' AS 코드명 , '" + cdRuleId +"' AS CD_RULE_ID,  '" + cdRuleNm + "' AS CD_RULE_NM, '" + sCdTypCd +"' AS CD_TYP_CD";
						sql += "\n   		FROM ( ";
					}
													
					sql += UtilString.null2Blank(vo.getCdSql());
					
					sql += "\n   ) X ";
					
					sql += "\n ) XX ";
					sql += "\n GROUP BY 코드분류, 코드분류명, 코드, 코드명,  CD_RULE_ID, CD_RULE_NM, CD_TYP_CD " ;
					
					if(search.getCodeClsVal()!=null){
						sql += "\n HAVING UPPER(코드분류명) LIKE UPPER('%"+search.getCodeClsVal()+"%')";
						if(search.getCodeClsId()!=null) {
							sql += "\n AND UPPER(코드분류) LIKE UPPER('%"+search.getCodeClsId()+"%')";
						}
					} else {
						if(search.getCodeClsId()!=null) {
							sql += "\n HAVING UPPER(코드분류) LIKE UPPER('%"+search.getCodeClsId()+"%')";
						}
					}
					i++;
				}
				
			}
			
			sql += "\n ) A ";
			
			
			
			sql += "\n ORDER BY CASE WHEN CD_TYP_CD = 'LC' THEN 1 ELSE 2 END  " ;
			sql += "\n        , 코드분류, 코드분류명,코드, 코드명, CD_RULE_ID, CD_RULE_NM " ;
			
			
			logger.debug("\n" + sql);
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			json = resultSetToJsonConvert(rs);
			
			logger.debug("!!!!!"+json.toString());
						
		} catch(Exception e) {
			logger.debug(e.toString()); 
		}
		
		return json;
	}
	
	public JSONArray resultSetToJsonConvert(ResultSet rs ) throws Exception
	{
	    JSONArray json = new JSONArray();
	    ResultSetMetaData rsmd = rs.getMetaData();

	    while(rs.next()) {
	      int numColumns = rsmd.getColumnCount();
	      JSONObject obj = new JSONObject(); 

	      for (int i = 1; i < numColumns + 1; i++) {
	        String column_name = rsmd.getColumnName(i);
//	        logger.debug(column_name);
	        String sName = "";
	        
	        if(i == 5){
	        	sName = "cdRuleId"; // 코드룰id
	        	obj.put(sName, rs.getString(column_name));       	       
	        }else if(i == 6){ // 코드룰명
	        	sName = "cdRuleNm";
	        	obj.put(sName, rs.getString(column_name)); 	 
	        }else if(i == 1){ // 코드분류
	        	sName = "codeClsId";
	        	obj.put(sName, rs.getString(column_name)); 	        	    
	        }else if(i == 2){ // 코드분류명
	        	sName = "codeClsVal";
	        	obj.put(sName, rs.getString(column_name)); 	    
	        }else if(i == 3){ // 코드
		        	sName = "codeId";
		        	obj.put(sName, rs.getString(column_name)); 
	        }else if(i == 4){ // 코드명
	        	sName = "codeVal";
	        	obj.put(sName, rs.getString(column_name)); 	  
	        }else if(i == 7){ // 코드명
	        	sName = "cdTypCd";
	        	obj.put(sName, rs.getString(column_name)); 	  
	        }else{
	        	obj.put(column_name, rs.getString(column_name));
	        }
	        
	        
	      }

	      json.add(obj);
	    } 
	    
	    return json;	
	}
	
	
	/** 진단항목별 실행 화면 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/itm_ana_exec_rqst.do")
	public String goItmAnaExec() {
		
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	
    	
		return "/dq/criinfo/anatrg/itm_ana_exec_rqst";
	}
	
	/** 진단항목별  실행  조회 */
	/** @param menuId
	/** @param model
	/** @return meta */
	@RequestMapping("/dq/criinfo/anatrg/getItemAnaExec.do")
	@ResponseBody
	public IBSheetListVO<WaaColRuleRel> getItemAnaExec(WaaColRuleRel vo, ModelMap model) { 
		
		List<WaaColRuleRel> list = anaTrgService.getItmAnaExec(vo);  
						
		return new IBSheetListVO<WaaColRuleRel>(list, list.size()); 
	}
	
	@RequestMapping("/dq/criinfo/anatrg/execItmAna.do")
	@ResponseBody
	public IBSResultVO<WaaExpTbl> execItmAna(@RequestBody WamShdJobVO data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		int result = 0;    
		
		String resmsg;
		
		WamShd saveVO = new WamShd();  
		
		ArrayList<WamShdJob> list = data.get("data");  
		
		//스케줄관리 화면저장일 경우 스케줄명은 필수입력항목 스케줄명 미존재시 실시간분석실행
    	if(UtilString.null2Blank(saveVO.getShdLnm()).equals("") ){
    		String shdLnm = "";
    		
    		shdLnm = "실시간 진단항목 분석실행("+UtilDate.getCurrentDateHms()+")";
    		    		
    		saveVO.setShdLnm(shdLnm);
    		saveVO.setShdTypCd("O"); //배치형태  한번만
    		saveVO.setShdStrDtm(UtilDate.getCurrentDate()); //배치시작일자  //현재날짜
    		saveVO.setShdStrHr("00");  //시
    		saveVO.setShdStrMnt("00");  //분
    		saveVO.setShdUseYn("Y"); //스케줄사용여부
    		saveVO.setShdBprYn("N"); //일괄처리여부
    		saveVO.setRegTypCd("C");
    		saveVO.setShdKndCd("CR"); //검증룰 
    		
    		//스케줄관리 필터링 위해
    		saveVO.setObjDescn("ONLINE"); 
    	}
    	
    	logger.debug("saveVO:{}", saveVO);
    	
    	//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
    	
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		//Quartz server 구동 확인
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			//스케줄 마스터, 스케줄작업 저장
			result = scheduleManagerService.saveSchedule(list, saveVO);
			
			if(result > 0) {
	    		result = 0;
	    		resmsg = "진단항목 분석이 시작되었습니다."; //message.getMessage("MSG.QUARTZ", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = "진단항목 분석이 실패하였습니다."; //message.getMessage("ERR.SAVE", null, locale);
	    	}

			//Quartz 등록
			SchedulerUtils.registrySchedule(saveVO, schedulercmd);
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}
		
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();
		
		return new IBSResultVO<WaaExpTbl>(result, resmsg, action);

	}	

	@RequestMapping("/dq/criinfo/anatrg/exctrg_rqst.do")
	public String goExctrg() {
		
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	
    	
		return "/dq/criinfo/anatrg/exctrg_rqst";
	}
	
	@RequestMapping("/dq/criinfo/anatrg/getExpRuleTbl.do")
	@ResponseBody
	public IBSheetListVO<WaaExpTbl> getExpRuleTbl(WaaExpTbl vo, ModelMap model) { 
		 
		List<WaaExpTbl> list = anaTrgService.selectExpRuleTbl(vo);     
						
		return new IBSheetListVO<WaaExpTbl>(list, list.size()); 
	}
	
	@RequestMapping("/dq/criinfo/anatrg/regExpRuleTbl.do")
	@ResponseBody
	public IBSResultVO<WaaExpTbl> regExpRuleTbl(@RequestBody WaaExpTbls data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<WaaExpTbl> list = data.get("data");
		
		int result = anaTrgService.regExpRuleTbl(list);  
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaExpTbl>(result, resmsg, action);

	}
	
	@RequestMapping("/meta/stnd/delExpRule.do")
	@ResponseBody
	public IBSResultVO<WaaExpTbl> delExpRule(@RequestBody WaaExpTbls data,  Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
	
		ArrayList<WaaExpTbl> list = data.get("data");

		int result = anaTrgService.delExpRule(list);  
		
		String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    //	System.out.println(action);

    	//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
    	//reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaaExpTbl>( result, resmsg, action);
		
	}
	
	
	
	


}
