/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstCtrl.java
 * 2. Package : kr.wise.meta.model.web
 * 3. Comment : 논리모델 등록 요청 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 1. 오후 4:22:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 1. :            : 신규 개발.
 */
package kr.wise.meta.ldm.web;  

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
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.ldm.service.LdmEntyRqstService;
import kr.wise.meta.ldm.service.WaeLdmAttr;
import kr.wise.meta.ldm.service.WaqLdmAttr;
import kr.wise.meta.ldm.service.WaqLdmEnty;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : LdmEntyRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 논리모델 등록 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 1. 오후 4:22:41
 * </PRE>
 */
@Controller("LdmEntyRqstCtrl") 
public class LdmEntyRqstCtrl {

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
	private LdmEntyRqstService ldmEntyRqstService; 

	
	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqLdmEntys extends HashMap<String, ArrayList<WaqLdmEnty>> {}

	static class WaqLdmAttrs extends HashMap<String, ArrayList<WaqLdmAttr>> {}
	
	static class WaeLdmAttrs extends HashMap<String, ArrayList<WaeLdmAttr>> {} 
	
	

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

    /** 논리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/ldmenty_rqst.do")
    public String goLdmTblRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("LDM");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
        
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        
        int mapcount = approveLineServie.checkRequst(mapvo); 
                
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
        model.addAttribute("rqstbizdcd",reqmst.getBizDcd());
       
        
        return "/meta/ldm/ldmenty_rqst";

    }
    
   
   

    /** 논리모델 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/ldm/ajaxgrid/ldmenty_rqst_dtl.do")
    public String getpdmtblDetail(WaqLdmEnty searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqLdmEnty resultvo =  ldmEntyRqstService.getLdmTblRqstDetail(searchVo);
    		logger.debug("결과값 : " +resultvo.getRvwStsCd() );
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/ldm/ldmenty_rqst_dtl";

    }


    /** 논리모델 테이블 요청 리스트 조회 @return insomnia */
    @RequestMapping("/meta/ldm/getLdmEntyRqstList.do")
    @ResponseBody
	public IBSheetListVO<WaqLdmEnty> getLdmEntyRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqLdmEnty> list = ldmEntyRqstService.getLdmEntyRqstList(search);


		return new IBSheetListVO<WaqLdmEnty>(list, list.size());
	}

    /** 논리모델 테이블 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/regLdmEntyRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmTblRqstList(@RequestBody WaqLdmEntys data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqLdmEnty> list = data.get("data");
		
		int result = ldmEntyRqstService.register(reqmst, list);

		result += ldmEntyRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}


    /** 논리모델 테이블 요청 리스트 삭제.... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/delpdmtblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delPdmTblRqstList(@RequestBody WaqLdmEntys data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqLdmEnty> list = data.get("data");

		int result = ldmEntyRqstService.delLdmTblRqst(reqmst, list);

		result += ldmEntyRqstService.check(reqmst);

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

    /** 논리모델 테이블 변경대상 요청 리스트 추가... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/regWam2WaqLdmEnty.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqLdmEntys data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqLdmEnty> list = data.get("data");

		int result = ldmEntyRqstService.regWam2Waq(reqmst, list);

		
		
		result += ldmEntyRqstService.check(reqmst);

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

    /** 논리모델 테이블컬럼 엑셀업로드 팝업창 호출... @return insomnia */    
    @RequestMapping("/meta/ldm/popup/ldmentyattr_xls.do")
    public String goLdmTblColRqstxls(@ModelAttribute("search") WaqLdmAttr search) {

    	return "/meta/ldm/popup/ldmentyattr_xls";
    }

    @RequestMapping("/meta/ldm/ajaxgrid/ldmattr_rqst_dtl.do")
    public String getPdmColRqstDetail(WaqLdmAttr search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqLdmAttr resultvo =  ldmEntyRqstService.getLdmColRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}


    	return "/meta/ldm/ldmattr_rqst_dtl";
    }


    /** 논리모델 컬럼 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/regLdmAttrRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmColRqstList(@RequestBody WaqLdmAttrs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqLdmAttr> list = data.get("data");
		System.out.println("세이브브이오리스트 : " + list.toString());
		int result = ldmEntyRqstService.regLdmColList(reqmst, list);
		result += ldmEntyRqstService.check(reqmst);

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

    /** 논리모델 컬럼 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/ldm/getLdmAttrRqstList.do")
    @ResponseBody
    public IBSheetListVO<WaqLdmAttr> getPdmColRqstList(WaqMstr search,WaqLdmAttr waqPdmCol) {
		logger.debug("search:{}",search);
		logger.debug("waqPdmCol:{}",waqPdmCol);
 
		List<WaqLdmAttr> list = ldmEntyRqstService.getLdmAttrRqstList(search);

		return new IBSheetListVO<WaqLdmAttr>(list, list.size());
	}

    /** 논리모델 컬럼 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/delpdmcolrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delPdmColRqstList(@RequestBody WaqLdmAttrs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqLdmAttr> list = data.get("data");

		int result = ldmEntyRqstService.delLdmColRqst(reqmst, list);

		result += ldmEntyRqstService.check(reqmst);

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
    
    
    

    /** 논리모델 테이블 승인... @throws Exception insomnia */
    @RequestMapping("/meta/ldm/approveLdmEnty.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approvePdmTbl(@RequestBody WaqLdmEntys data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqLdmEnty> list = data.get("data");

		int result  = ldmEntyRqstService.approve(reqmst, list);
		//ddl자동발행 승인
//		logger.debug("ddl자동발행 승인");
//        result += ddlTblRqstService.approve(reqmst,null);
//		logger.debug("ddl자동발행 승인완료");
        
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
    

    
    // 엑셀 테이블 컬럼 일괄 저장 
    @RequestMapping("/meta/ldm/regLdmEntyAttrXlsRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regLdmTblColXlsRqstList(@RequestBody WaeLdmAttrs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaeLdmAttr> list = data.get("data");

		String resmsg;
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction(); 

		int result = 0;
		
		//테이블 컬럼 일괄 저장
		result = ldmEntyRqstService.regLdmXlsTblColList(reqmst, list); 
				
		//검증 
		result += ldmEntyRqstService.check(reqmst);


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
