/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRequestCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준단어 등록요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 12:54:05
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.meta.symn.web;

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
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.app.service.WamAppStwd;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.app.service.WaqAppStwd;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.stnd.service.StndDmnRqstService;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.StndTotRqstService;
import kr.wise.meta.stnd.service.StndWordRqstService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.service.WaqStndTot;
import kr.wise.meta.stnd.service.WaqStwd;
import kr.wise.meta.symn.service.SymnItemRqstService;
import kr.wise.meta.symn.service.WamSymnItem;
import kr.wise.meta.symn.service.WaqSymnItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;
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
 * 2. FileName  : SymnItemRqstCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  : 유사항목 등록요청 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 11. 26. 
 * </PRE>
 */
@Controller
public class SymnItemRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private SymnItemRqstService symnItemRqstService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;


	static class WaqSymnItems extends HashMap<String, ArrayList<WaqSymnItem>> { }
	
	
	

	/** 유사항목 등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/meta/symn/symnItem_rqst.do")
	public String goSymnItemrqstForm(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("SYM");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	return "/meta/symn/symnItem_rqst";
	} 
    
   


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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}


	/** 유사항목 요청 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/meta/symn/regSymnItemRqstList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regSymnItemRqstList(@RequestBody WaqSymnItems data, WaqMstr reqmst, Locale locale) {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		int result =0;
		String resmsg = "";
		String action = "";
		
		try {
		ArrayList<WaqSymnItem> list = data.get("data");

		 result = symnItemRqstService.register(reqmst, list);

		symnItemRqstService.check(reqmst);

//		int result = stndWordRqstService.regStndWordRqstlist(list);

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);
		
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			
			resmsg = e.getMessage();
			
			e.printStackTrace();
		}
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}


    /** 유사항목 요청서 결재처리... @return insomnia
     * @throws Exception */
    @RequestMapping("/meta/symn/regApproveSymnItem.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regApproveSymnItem(@RequestBody WaqSymnItems data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		/*
		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}
		*/

		ArrayList<WaqSymnItem> list = data.get("data");

		int result = symnItemRqstService.regApprove(reqmst, list); 


		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    @RequestMapping("/meta/symn/approveSymnItem.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> approveSymnItem(@RequestBody WaqSymnItems data, WaqMstr reqmst, Locale locale) throws Exception {
	
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
	
	
		ArrayList<WaqSymnItem> list = data.get("data");
	
		int result  = symnItemRqstService.approve(reqmst, list);
	
	
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
    
    
    /** 유사항목 요청 리스트 조회.... @return  */
    @RequestMapping("/meta/symn/getSymnItemRqstList.do")
    @ResponseBody
    public IBSheetListVO<WaqSymnItem> getSymnItemRqstList (WaqMstr search) {
    	logger.debug("search:{}", search);

    	List<WaqSymnItem> list = symnItemRqstService.getStndItemRqstList(search);

    	return new IBSheetListVO<WaqSymnItem>(list, list.size());
    }
    
    
    /** 유사항목 요청 삭제 - IBSheet 용...
	 * @throws Exception */
	@RequestMapping("/meta/symn/delSymnItemRqstList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delSymnItemRqstList(@RequestBody WaqSymnItems data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqSymnItem> list = data.get("data");

		int result = symnItemRqstService.delSymnItemRqstList(reqmst, list);
		result += symnItemRqstService.check(reqmst);

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

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
	
	
	/** 표준단어 변경대상 추가..(팝업에서 호출함) @throws Exception  */
	@RequestMapping("/meta/symn/regWam2WaqSymnItem.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqSymnItems data, WaqMstr reqmst, Locale locale)  {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		int result = -1;
		String resmsg = "";
		String action = "";
		
		try {
		ArrayList<WaqSymnItem> list = data.get("data");

		logger.info("1");
		result = symnItemRqstService.regWam2Waq(reqmst, list);
		logger.info("2");
		result += symnItemRqstService.check(reqmst);
		logger.info("3");
//		int result = appStndWordRqstServie.regStndWordRqstlist(list);
		

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		logger.info("3");
		action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		logger.info("4");
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		logger.info("5");
		} catch (Exception e) {
			
			resmsg = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
	
		
	@RequestMapping("/meta/symn/popup/symnItem_xls.do")
    public String goSymnItemXls(@ModelAttribute("search") WaqSymnItem search) {
		//logger.debug("search:{}", search);
		
    	return "/meta/symn/popup/symnItem_xls";
    }

}
