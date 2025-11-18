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
package kr.wise.meta.pdmrel.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.pdmrel.service.PdmRelMstRqstService;
import kr.wise.meta.pdmrel.service.WaePdmRelCol;
import kr.wise.meta.pdmrel.service.WaqPdmRelCol;
import kr.wise.meta.pdmrel.service.WaqPdmRelMst;

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
@Controller("pdmRelRqstCtrl")
public class PdmRelRqstCtrl {

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
	private RequestMstService requestMstService;

	@Inject
	private PdmRelMstRqstService pdmRelMstRqstService;

	
	@Inject
	private ApproveLineServie approveLineServie;

	
	static class WaqPdmRelMsts extends HashMap<String, ArrayList<WaqPdmRelMst>> {}
	
	static class WaqPdmRelCols extends HashMap<String, ArrayList<WaqPdmRelCol>> {}
	
	static class WaePdmRelCols extends HashMap<String, ArrayList<WaePdmRelCol>> {}

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
		//관계유형코드
		codeMap.put("relTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REL_TYP_CD")));
		codeMap.put("relTypCd", cmcdCodeService.getCodeList("REL_TYP_CD"));
		//카디널리티유형코드
		codeMap.put("crdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CRD_TYP_CD")));
		codeMap.put("crdTypCd", cmcdCodeService.getCodeList("CRD_TYP_CD"));
		//Parent Optionality 유형코드
		codeMap.put("paOptTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PA_OPT_TYP_CD")));
		codeMap.put("paOptTypCd", cmcdCodeService.getCodeList("PA_OPT_TYP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));


		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

    
    /** 물리모델관계 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/pdmrel/pdmrelation_rqst.do")
    public String goModelPdmRelRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		
    		reqmst.setBizDcd("PRL");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	
    	return "/meta/pdmrel/pdmrelation_rqst"; 

    }
    
    
    /** 물리모델 관계 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/pdmrel/getPdmRelMstRqstList.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmRelMst> getPdmRelRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqPdmRelMst> list = pdmRelMstRqstService.getPdmRelRqstList(search);

		return new IBSheetListVO<WaqPdmRelMst>(list, list.size());
	}
    
    /** 물리모델 관계 요청 상세리스트 조회 */
    @RequestMapping("/meta/pdmrel/ajaxgrid/pdmrelation_rqst_dtl.do")
    public String getPdmRelRqstDetail(WaqPdmRelMst search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqPdmRelMst resultvo =  pdmRelMstRqstService.getPdmRelRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}


    	return "/meta/pdmrel/pdmrelation_rqst_dtl"; 
    }
    
   
    
    /** 물리모델 관계MST 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/pdmrel/regpdmrelrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmRelRqstList(@RequestBody WaqPdmRelMsts data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqPdmRelMst> list = data.get("data");

		int result = pdmRelMstRqstService.register(reqmst, list);   
		
		result += pdmRelMstRqstService.check(reqmst);

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
    
    
    /** 물리모델 관계 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/pdmrel/delPdmRelMstRqstList.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delPdmRelRqstList(@RequestBody WaqPdmRelMsts data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqPdmRelMst> list = data.get("data");

		int result = pdmRelMstRqstService.delPdmRelRqst(reqmst, list);

		result += pdmRelMstRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    /** 물리모델 관계컬럼 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/pdmrel/regPdmRelColRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmRelColRqstList(@RequestBody WaqPdmRelCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqPdmRelCol> list = data.get("data"); 

		int result = pdmRelMstRqstService.regPdmRelColList(reqmst, list);  
		
		result += pdmRelMstRqstService.check(reqmst);

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
    
    /** 물리모델 관계컬럼 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/pdmrel/getPdmRelColRqstList.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmRelCol> getPdmRelColRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqPdmRelCol> list = pdmRelMstRqstService.getPdmRelColRqstList(search);

		return new IBSheetListVO<WaqPdmRelCol>(list, list.size());
	}
    
    /** 물리모델 관계컬럼 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/pdmrel/delPdmRelColRqstList.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delPdmRelColRqstList(@RequestBody WaqPdmRelCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqPdmRelCol> list = data.get("data");

		int result = pdmRelMstRqstService.delPdmRelColRqst(reqmst, list); 

		result += pdmRelMstRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    /** 물리모델관계 승인 meta */
    @RequestMapping("/meta/pdmrel/approvePdmRel.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePdmRel(@RequestBody WaqPdmRelMsts data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}


		ArrayList<WaqPdmRelMst> list = data.get("data");

		int result  = pdmRelMstRqstService.approve(reqmst, list);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
    
    /** 물리모델 관계 변경대상 요청 리스트 추가... @throws Exception insomnia */
    @RequestMapping("/meta/pdmrel/regWam2WaqPdmRel.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2WaqPdmRel(@RequestBody WaqPdmRelMsts data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqPdmRelMst> list = data.get("data");

		int result = pdmRelMstRqstService.regWam2Waq(reqmst, list); 
		
		result += pdmRelMstRqstService.check(reqmst);

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
    
    //모델관계 엑셀업로드
   	@RequestMapping("/meta/pdmrel/popup/pdmrel_xls.do")
   	public String goPdmRelXlsPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {

   		logger.debug("{}", search);
   		return "/meta/pdmrel/popup/pdmrel_xls";
   	}
    
    
   
    //물리모델 관계 엑셀업로드
    @RequestMapping("/meta/pdmrel/regPdmRelMstXlsRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmRelxlsRqstList(@RequestBody WaePdmRelCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaePdmRelCol> list = data.get("data");

		String resmsg;
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		int result = pdmRelMstRqstService.regPdmxlsRelList(reqmst, list);

		
		result += pdmRelMstRqstService.check(reqmst);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}


		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

   }
    
}
