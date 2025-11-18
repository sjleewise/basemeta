/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstCtrl.java
 * 2. Package : kr.wise.meta.model.web
 * 3. Comment : 물리모델 등록 요청 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 1. 오후 4:22:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 1. :            : 신규 개발.
 */
package kr.wise.dq.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.model.service.WdqPdmTblRqstService;
import kr.wise.dq.model.service.WdqmPdmCol;
import kr.wise.dq.model.service.WdqmPdmTbl;
import kr.wise.dq.model.service.WdqqPdmCol;
import kr.wise.dq.model.service.WdqqPdmTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 물리모델 등록 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 1. 오후 4:22:41
 * </PRE>
 */
@Controller("wdqPdmTblRqstCtrl")
public class WdqPdmTblRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WdqPdmTblRqstService WdqPdmTblRqstService;
	
	@Inject
	private ApproveLineServie approveLineServie;

	static class WdqmPdmTbls extends HashMap<String, ArrayList<WdqmPdmTbl>> {}

	static class WdqmPdmCols extends HashMap<String, ArrayList<WdqmPdmCol>> {}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);

		//공통코드 - IBSheet Combo Code용
		//검토상태코드
//		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
//		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		//요청구분코드
//		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
//		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
//		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
//		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
//		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
//		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
		//결재그룹 코드 리스트
//		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//요청단계코드(RQST_STEP_CD)
//		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
//		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		//진단대상(DB_CONN_TRG_ID)
		String connTrgDbms   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgDbms"));
		codeMap.put("dbConnTrgibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("connTrgDbms")));
//		codeMap.put("dbConnTrg", connTrgDbms);
		
		//스키마
		String connTrgSch 		= UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("getConnTrgSchIdMdl")));
//		codeMap.put("connTrgSch", connTrgSch);
		
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regtypcd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD")));

		return codeMap;
	}

    /** 물리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/dq/model/pdmtbl_rqst.do")
    public String goModelPdmTblRqst(ModelMap model,HttpSession session) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

    	return "/dq/model/pdmtbl_rqst";

    }
    
   
    /** 물리모델 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/dq/model/ajaxgrid/pdmtbl_rqst_dtl.do")
    public String getpdmtblDetail(WdqqPdmTbl searchVo, ModelMap model) {

//    	logger.debug("searchVO:{}", searchVo);
//
//    	if(searchVo.getRqstSno() == null) {
//    		model.addAttribute("saction", "I");
//
//    	} else {
//    		WdqqPdmTbl resultvo =  pdmTblRqstService.getPdmTblRqstDetail(searchVo);
//    		logger.debug("결과값 : " +resultvo.getRvwStsCd() );
//    		model.addAttribute("result", resultvo);
//    		model.addAttribute("saction", "U");
//    	}

    	return "/dq/model/pdmtbl_rqst_dtl";

    }


    /** 물리모델 테이블 요청 리스트 조회 @return insomnia */
    @RequestMapping("/dq/model/getpdmtblrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WdqmPdmTbl> getPdmTblRqstList(WdqmPdmTbl search) {

		logger.debug("search:{}", search);

		List<WdqmPdmTbl> list = WdqPdmTblRqstService.getPdmTblList(search);


		return new IBSheetListVO<WdqmPdmTbl>(list, list.size());
	}

    /** 물리모델 테이블 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/dq/model/regpdmtblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WdqmPdmTbl> regPdmTblRqstList(@RequestBody WdqmPdmTbls data, Locale locale) throws Exception {
 
    	logger.debug("{}", data);
		ArrayList<WdqmPdmTbl> list = data.get("data");

		int result = WdqPdmTblRqstService.regList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WdqmPdmTbl>(result, resmsg, action);
	}


    /** 물리모델 테이블 요청 리스트 삭제.... @throws Exception insomnia */
    @RequestMapping("/dq/model/delpdmtblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WdqmPdmTbl> delPdmTblRqstList(@RequestBody WdqmPdmTbls data, Locale locale) throws Exception {

//		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WdqmPdmTbl> list = data.get("data");

		int result = WdqPdmTblRqstService.delList(list);

		String resmsg;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		
		return new IBSResultVO<WdqmPdmTbl>(result, resmsg, action);
	}

    /** 물리모델 테이블 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/dq/model/popup/pdmtbl_xls.do")
    public String goPdmTblRqstxls(@ModelAttribute("search") WdqqPdmTbl search) {

    	return "/dq/model/popup/pdmtbl_xls";
    }

    /** 물리모델 컬럼 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/dq/model/popup/pdmcol_xls.do")
    public String goPdmColRqstxls(@ModelAttribute("search") WdqqPdmCol search) {

    	return "/dq/model/popup/pdmcol_xls";
    }
    
    /** 물리모델 테이블컬럼 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/dq/model/popup/pdmtblcol_xls.do")
    public String goPdmTblColRqstxls(@ModelAttribute("search") WdqqPdmCol search) {

    	return "/dq/model/popup/pdmtblcol_xls"; 
    }


    @RequestMapping("/dq/model/getpdmcolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WdqmPdmCol> getPdmColRqstList(WdqmPdmCol search, ModelMap model) throws Exception {
    	logger.debug("searchVO:{}", search);
    	List<WdqmPdmCol> list = null;
    	try {
    		list = WdqPdmTblRqstService.getPdmColList(search);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		return new IBSheetListVO<WdqmPdmCol>(list, list.size());
    }


    /** 물리모델 컬럼 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/dq/model/regpdmcolrqstlist.do")
    @ResponseBody
	public IBSResultVO<WdqmPdmCol> regPdmColRqstList(@RequestBody WdqmPdmCols data, Locale locale) throws Exception {
		 
    	logger.debug("{}", data);
		ArrayList<WdqmPdmCol> list = data.get("data");

		int result = WdqPdmTblRqstService.regColList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = "regCol";

		return new IBSResultVO<WdqmPdmCol>(result, resmsg, action);

   }
    
    /** 물리모델 컬럼 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/dq/model/delpdmcolrqstlist.do")
    @ResponseBody
	public IBSResultVO<WdqmPdmCol> delPdmColRqstList(@RequestBody WdqmPdmCols data, Locale locale) throws Exception {
		 
    	logger.debug("{}", data);
		ArrayList<WdqmPdmCol> list = data.get("data");

		int result = WdqPdmTblRqstService.delColList(list);
		String resmsg;
		
		logger.debug("result >>> " + result);

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = "delCol";

		return new IBSResultVO<WdqmPdmCol>(result, resmsg, action);

   }
    
//
//    /** 물리모델 컬럼 요청 리스트 조회... @return insomnia */
//    @RequestMapping("/dq/model/getpdmcolrqstlist.do")
//    @ResponseBody
//    public IBSheetListVO<WdqqPdmCol> getPdmColRqstList(WaqMstr search,WdqqPdmCol WdqqPdmCol) {
//		logger.debug("search:{}",search);
//		logger.debug("WdqqPdmCol:{}",WdqqPdmCol);
// 
//		List<WdqqPdmCol> list = WdqPdmTblRqstService.getPdmColRqstList(search);
//
//		return new IBSheetListVO<WdqqPdmCol>(list, list.size());
//	}
//
//    /** 물리모델 컬럼 요청 리스트 삭제... @throws Exception insomnia */
//    @RequestMapping("/dq/model/delpdmcolrqstlist.do")
//    @ResponseBody
//    public IBSResultVO<WaqMstr> delPdmColRqstList(@RequestBody WdqqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
//		ArrayList<WdqqPdmCol> list = data.get("data");
//
//		int result = WdqPdmTblRqstService.delPdmColRqst(reqmst, list);
//
//		result += WdqPdmTblRqstService.check(reqmst);
//
//		String resmsg;
//
//		if(result > 0) {
//    		result = 0;
//    		resmsg = message.getMessage("MSG.DEL", null, locale);
//    	}
//    	else {
//    		result = -1;
//    		resmsg = message.getMessage("ERR.DEL", null, locale);
//    	}
//
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//	}
//
//    /** 물리모델 테이블 승인... @throws Exception insomnia */
//    @RequestMapping("/dq/model/approvepdmtbl.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> approvePdmTbl(@RequestBody WdqqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
//		String resmsg;
//
//		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
//		String userid = user.getUniqId();
//
//		//결재자인지 확인한다.
//		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
//		if(!checkapprove) {
//			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
//			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
//		}
//
//		ArrayList<WdqqPdmTbl> list = data.get("data");
//
//		int result  = WdqPdmTblRqstService.approve(reqmst, list);
//        
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("REQ.APPROVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
//		}
//
//		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//	}
//    
//
//    @RequestMapping("/dq/model/regpdmcolxlsrqstlist.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmColxlsRqstList(@RequestBody WdqqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata{}", reqmst, data);
//
//		List<WdqqPdmCol> list = data.get("data");
//
//		String resmsg;
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//테이블명도 함께 받는다
//		HashMap<String, String> map = WdqPdmTblRqstService.regPdmxlsColList(reqmst, list);
//		
////		int result = WdqPdmTblRqstService.regPdmxlsColList(reqmst, list);
//		
//		int result = Integer.parseInt( map.get("result") );
//		String colKey = map.get("colKey");
//
//		//테이블이 존재하지 않음...
//		if(result == -999) {
//			result = -1;
//			resmsg = message.getMessage("ERR.NOTABLE", new String[] {colKey}, locale);
//
//			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//		}
//
//		result += WdqPdmTblRqstService.check(reqmst);
//
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//   }
//    
//    // 엑셀 테이블 컬럼 일괄 저장 
//    @RequestMapping("/dq/model/regPdmTblColXlsRqstList.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmTblColXlsRqstList(@RequestBody WaePdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata{}", reqmst, data);
//
//		List<WaePdmCol> list = data.get("data");
//
//		String resmsg;
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		int result = 0;
//		
//		//테이블 컬럼 일괄 저장
//		result = WdqPdmTblRqstService.regPdmXlsTblColList(reqmst, list); 
//				
//		//검증 
//		result += WdqPdmTblRqstService.check(reqmst);
//
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//   }
//    
//    @RequestMapping("/dq/model/pdmTbl_ownerChg.do")
//    public String goModelPdmTblOwnerChg(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);
//
//       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
//    	if (!StringUtils.hasText(reqmst.getRqstNo())){
//    		reqmst.setBizDcd("PDM");
//    		reqmst = requestMstService.getBizInfoInit(reqmst);
//    	} else {
//    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
//    		reqmst = requestMstService.getrequestMst(reqmst);
//    	}
//
//    	logger.debug("reqmst:{}", reqmst);
//
//    	model.addAttribute("waqMstr", reqmst);
//    	
//    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//        mapvo.setRqst_no(reqmst.getRqstNo());
//        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
//        int mapcount = approveLineServie.checkRequst(mapvo); 
////        System.out.println("카운트 : " + mapcount);
//        
//        model.addAttribute("checkrequstcount", mapcount);
//        model.addAttribute("rqstno",reqmst.getRqstNo());
//        model.addAttribute("rqstbizdcd",reqmst.getBizDcd());
//
//    	return "/dq/model/pdmTbl_ownerChg";
//
//    }
//    
//    @RequestMapping("/dq/model/regpdmtblownerchg.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmTblOwnerChg(@RequestBody WdqqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
// 
//		logger.debug("\ndata:{}", data);
//		ArrayList<WdqqPdmTbl> list = data.get("data");
//		
//		int result = 0;
//		for(WdqqPdmTbl regVo: list) {
//			result += WdqPdmTblRqstService.changeOwner(regVo);
//		}
//		String resmsg;
//
//		if(result > 0 ){
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		}
//		else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		//reqmst = requestMstService.getrequestMst(reqmst);
//
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//	}

}
