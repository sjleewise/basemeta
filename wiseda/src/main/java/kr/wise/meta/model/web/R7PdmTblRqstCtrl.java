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
package kr.wise.meta.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.model.service.ModelPdmService;
import kr.wise.meta.model.service.PdmTblRqstService;
import kr.wise.meta.model.service.R7PdmTblRqstService;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
@Controller("r7PdmTblRqstCtrl")
public class R7PdmTblRqstCtrl {

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
	private R7PdmTblRqstService r7pdmTblRqstService;

	@Inject
	private PdmTblRqstService pdmTblRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private ModelPdmService modelPdmService;

	static class WaqPdmTbls extends HashMap<String, ArrayList<WaqPdmTbl>> {}

	static class WaqPdmCols extends HashMap<String, ArrayList<WaqPdmCol>> {}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);

		//공통코드 - IBSheet Combo Code용
		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
		//결재그룹 코드 리스트
//		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));

		return codeMap;
	}

    /** 물리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/model/r7_pdmtbl_rqst.do")
    public String goModelPdmTblRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		String reqid = requestIdGnrService.getNextStringId();
    		reqmst.setRqstNo(reqid);
    		reqmst.setBizDcd("R7P");
//    		reqmst.setBizDtlCd("TBL");
    		reqmst.setRqstStepCd("N");
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	
    	
    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//        System.out.println("요청번호 : " + reqmst.getRqstNo());
//        System.out.println("요청구분 : " + reqmst.getBizDcd());
//        System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        int mapcount = approveLineServie.checkRequst(mapvo); 
//        System.out.println("카운트 : " + mapcount);
        
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
	model.addAttribute("rqstbizdcd",reqmst.getBizDcd());

    	return "/meta/model/r7_pdmtbl_rqst";

    }


    /** R7 모델마트 테이블 검색 팝업... @return insomnia */
    @RequestMapping("/meta/model/popup/r7_pdmtblSearchPop.do")
    public String goR7MartTblPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
    	logger.debug("{}", search);

    	return "/meta/model/popup/r7_pdmTblSearch_pop";
    }

    /** 물리모델 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/model/ajaxgrid/r7_pdmtbl_rqst_dtl.do")
    public String getpdmtblDetail(WaqPdmTbl searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqPdmTbl resultvo =  r7pdmTblRqstService.getPdmTblRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/model/r7_pdmtbl_rqst_dtl";

    }

//
//
//    /** 물리모델 테이블 요청 리스트 조회 @return insomnia */
//    @RequestMapping("/meta/model/getpdmtblrqstlist.do")
//    @ResponseBody
//	public IBSheetListVO<WaqPdmTbl> getPdmTblRqstList(WaqMstr search) {
//
//		logger.debug("search:{}", search);
//
//		List<WaqPdmTbl> list = pdmTblRqstService.getPdmTblRqstList(search);
//
//
//		return new IBSheetListVO<WaqPdmTbl>(list, list.size());
//	}
//
//    /** 물리모델 테이블 요청 리스트 등록... @throws Exception insomnia */
//    @RequestMapping("/meta/model/r7_regpdmtblrqstlist.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmTblRqstList(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
//		ArrayList<WaqPdmTbl> list = data.get("data");
//
//		int result = r7pdmTblRqstService.register(reqmst, list);
//
//		result += r7pdmTblRqstService.check(reqmst);
//
//		String resmsg;
//
//		if(result > 0 ){
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
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
//
//    /** 물리모델 테이블 요청 리스트 삭제.... @throws Exception insomnia */
//    @RequestMapping("/meta/model/delpdmtblrqstlist.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> delPdmTblRqstList(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
//		ArrayList<WaqPdmTbl> list = data.get("data");
//
//		int result = pdmTblRqstService.delPdmTblRqst(reqmst, list);
//
//		result += pdmTblRqstService.check(reqmst);
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
    /** R7 모델마트 테이블 조회 - IBSHEET JSON @return insomnia */
    @RequestMapping("/meta/model/getmarttbllist.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmTbl> getMartTblList(WaqPdmTbl search, Locale loclae) {
    	logger.debug("{}", search);
    	List<WaqPdmTbl> list = r7pdmTblRqstService.getMartTblList(search);
    	
//    	WamPdmTbl searchm = new WamPdmTbl(); 
//    	
//    	List<WamPdmTbl> listm = modelPdmService.getPdmTblList(searchm);

    	return new IBSheetListVO<WaqPdmTbl>(list, list.size());

    }

    /** 물리모델 테이블 변경대상 요청 리스트 추가... @throws Exception insomnia */
    @RequestMapping("/meta/model/regMart2WaqPdmTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMart2Waqlist(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqPdmTbl> list = data.get("data");

		int result = r7pdmTblRqstService.regMart2Waq(reqmst, list);

		result += pdmTblRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
//
//    /** 물리모델 테이블 엑셀업로드 팝업창 호출... @return insomnia */
//    @RequestMapping("/meta/model/popup/pdmtbl_xls.do")
//    public String goPdmTblRqstxls(@ModelAttribute("search") WaqPdmTbl search) {
//
//    	return "/meta/model/popup/pdmtbl_xls";
//    }
//
//    /** 물리모델 컬럼 엑셀업로드 팝업창 호출... @return insomnia */
//    @RequestMapping("/meta/model/popup/pdmcol_xls.do")
//    public String goPdmColRqstxls(@ModelAttribute("search") WaqPdmCol search) {
//
//    	return "/meta/model/popup/pdmcol_xls";
//    }
//
//    @RequestMapping("/meta/model/ajaxgrid/pdmcol_rqst_dtl.do")
//    public String getPdmColRqstDetail(WaqPdmCol search, ModelMap model) {
//    	logger.debug("searchVO:{}", search);
//
//    	if(search.getRqstDtlSno() == null) {
//    		model.addAttribute("saction", "I");
//
//    	} else {
//    		WaqPdmCol resultvo =  pdmTblRqstService.getPdmColRqstDetail(search);
//    		model.addAttribute("result", resultvo);
//    		model.addAttribute("saction", "U");
//    	}
//
//
//    	return "/meta/model/pdmcol_rqst_dtl";
//    }
//
//
//    /** 물리모델 컬럼 요청 리스트 등록... @throws Exception insomnia */
//    @RequestMapping("/meta/model/regpdmcolrqstlist.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmColRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata{}", reqmst, data);
//
//		List<WaqPdmCol> list = data.get("data");
//
//		int result = pdmTblRqstService.regPdmColList(reqmst, list);
//		result += pdmTblRqstService.check(reqmst);
//
//		String resmsg;
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//   }
//
//    /** 물리모델 컬럼 요청 리스트 조회... @return insomnia */
//    @RequestMapping("/meta/model/getpdmcolrqstlist.do")
//    @ResponseBody
//    public IBSheetListVO<WaqPdmCol> getPdmColRqstList(WaqMstr search) {
//		logger.debug("search:{}",search);
//
//		List<WaqPdmCol> list = pdmTblRqstService.getPdmColRqstList(search);
//
//		return new IBSheetListVO<WaqPdmCol>(list, list.size());
//	}
//
//    /** 물리모델 컬럼 요청 리스트 삭제... @throws Exception insomnia */
//    @RequestMapping("/meta/model/delpdmcolrqstlist.do")
//    @ResponseBody
//    public IBSResultVO<WaqMstr> delPdmColRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
//		ArrayList<WaqPdmCol> list = data.get("data");
//
//		int result = pdmTblRqstService.delPdmColRqst(reqmst, list);
//
//		result += pdmTblRqstService.check(reqmst);
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
//    @RequestMapping("/meta/model/approvepdmtbl.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> approvePdmTbl(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
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
//		ArrayList<WaqPdmTbl> list = data.get("data");
//
//		int result  = pdmTblRqstService.approve(reqmst, list);
//
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
//    @RequestMapping("/meta/model/regpdmcolxlsrqstlist.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmColxlsRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata{}", reqmst, data);
//
//		List<WaqPdmCol> list = data.get("data");
//
//		String resmsg;
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		int result = pdmTblRqstService.regPdmxlsColList(reqmst, list);
//
//		//테이블이 존재하지 않음...
//		if(result == -999) {
//			result = -1;
//			resmsg = message.getMessage("ERR.NOTABLE", null, locale);
//
//			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//		}
//
//		result += pdmTblRqstService.check(reqmst);
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
}
