/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRequestCtrl.java
 * 2. Package : kr.wise.dq.stnd.web
 * 3. Comment : 표준단어 등록요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 12:54:05
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.dq.stnd.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.stnd.service.WdqStndWordAbrService;
import kr.wise.dq.stnd.service.WdqStndWordRqstService;
import kr.wise.dq.stnd.service.WdqmStwdAbr;
import kr.wise.dq.stnd.service.WdqpWordDv;
import kr.wise.dq.stnd.service.WdqqStwd;
import kr.wise.dq.stnd.web.WdqStndWordCtrl.WdqmStwdAbrs;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordRequestCtrl.java
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 4. 오후 12:54:05
 * </PRE>
 */
@Controller
public class WdqStndWordRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;
	
	@Inject
	private RequestApproveService requestApproveService;

	private Map<String, Object> codeMap;

	@Inject
	private WdqStndWordRqstService WdqStndWordRqstService;
	
	@Inject
	private WdqStndWordAbrService WdqStndWordAbrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private UserService userService;
	

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;




	static class WdqqStwds extends HashMap<String, ArrayList<WdqqStwd>> { }
	
	static class WdqpWordDvs extends HashMap<String, ArrayList<WdqpWordDv>> { }

	/** 표준단어등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/dq/stnd/stndword_rqst.do")
	public String goStndwordrqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

//    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
        model.addAttribute("codeMap",getcodeMap());
		return "/dq/stnd/stndword_rqst";
	}

    /** 표준단어등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/dq/stnd/stndword_rqst_ifm.do")
    public String goStndwordrqstIframe(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
        model.addAttribute("codeMap",getcodeMap());
    	return "/dq/stnd/stndword_rqst_ifm";
    }



    /** 표준단어요청 상세내용 조회 @return insomnia */
    @RequestMapping("/dq/stnd/ajaxgrid/stndword_rqst_dtl.do")
    public String getstndwordrqstdtl (WdqqStwd searchVO, ModelMap model) {

    	logger.debug("searchVO:{}", searchVO);

    	if (searchVO.getRqstSno() == null){
    		model.addAttribute("saction", "I");
    	} else {
    		WdqqStwd resultVO = WdqStndWordRqstService.getStndWordRqstDetail(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}
        model.addAttribute("codeMap",getcodeMap());
    	return "/dq/stnd/stndword_rqst_dtl";
    }

    @RequestMapping("/dq/stnd/popup/stndword_xls.do")
    public String gostndwordxls(@ModelAttribute("search") WdqqStwd search, ModelMap model) {
        model.addAttribute("codeMap",getcodeMap());
    	return "/dq/stnd/popup/stndword_xls";
    }


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
//	@ModelAttribute("codeMap")
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
		//단어구분코드
		codeMap.put("wdDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD")));
		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));

		//표준구분 이베이코리아
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}


    /** 표준단어 요청 리스트 조회 - IBSheet JSON @return insomnia */
    @RequestMapping("/dq/stnd/getstwdrqstlist.do")
	@ResponseBody
	public IBSheetListVO<WdqqStwd> getStndWordRqstlist(@ModelAttribute WaqMstr search) {
		logger.debug("searchVO:{}", search);

		List<WdqqStwd> list = WdqStndWordRqstService.getStndWordRqstist(search);

		return new IBSheetListVO<WdqqStwd>(list, list.size());
	}




	/** 표준단어 요청 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/dq/stnd/regStndWordRqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStndWordRqstlist(@RequestBody WdqqStwds data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
		logger.debug("/dq/stnd/regStndWordRqstlist.do");
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WdqqStwd> list = data.get("data");

		int result = WdqStndWordRqstService.register(reqmst, list);
		
		String stwdDcd = UtilString.null2Blank(session.getAttribute("stwdDcd"));
		
		//동음의의어:H,이음동의어:A,둘다허용:T,유니크:U 
		reqmst.setStwdKeyDcd(stwdDcd); 

		result += WdqStndWordRqstService.check(reqmst);


//		int result = WdqStndWordRqstService.regStndWordRqstlist(list);
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


	/** 표준단어 변경대상 추가..(팝업에서 호출함) @throws Exception insomnia */
	@RequestMapping("/dq/stnd/regWam2WaqStndword.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WdqqStwds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WdqqStwd> list = data.get("data");

		int result = WdqStndWordRqstService.regWam2Waq(reqmst, list);

		result += WdqStndWordRqstService.check(reqmst);

//		int result = WdqStndWordRqstService.regStndWordRqstlist(list);
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

	/** 표준단어 요청 삭제 - IBSheet 용...
	 * @throws Exception */
	@RequestMapping("/dq/stnd/delstwdrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delStndWordRqstList(@RequestBody WdqqStwds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WdqqStwd> list = data.get("data");

		int result = WdqStndWordRqstService.delStndWordRqstList(reqmst, list);
		result += WdqStndWordRqstService.check(reqmst);

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

	/** 표준단어 요청 삭제 - IBSheet 용...
     * @throws Exception */
    @RequestMapping("/dq/stnd/delstwdrqstlist_old.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> deleteProgramList(@RequestParam("joinkey") String checkjoin, WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{} \ndelKey:{}", reqmst, checkjoin);

    	int result = WdqStndWordRqstService.delStndWordRqst(reqmst, checkjoin);
    	result += WdqStndWordRqstService.check(reqmst);

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

    

	
	/** 표준단어 요청 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/dq/stnd/regStndWordByAbr.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStndWordByAbr(@RequestBody WdqmStwdAbrs data, WaqMstr reqmst, Locale locale) throws Exception {

		ArrayList<WdqmStwdAbr> list = data.get("data");
		logger.debug("data:{}",  data);

		reqmst.setBizDcd("DIC");
		reqmst.setBizDtlCd("STWD");
		reqmst.setRqstStepCd("N");
		//요청번호가 없을 경우 요청번호를 먼저 채번한다.
		reqmst = requestMstService.getBizInfoInit(reqmst);
		
		//REQ 저장
		int result = WdqStndWordAbrService.regStndWordByAbr(reqmst, list);
		WdqStndWordRqstService.check(reqmst);

		String resmsg ;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}
		logger.debug("reqmst:{}",  reqmst);
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, "REQ_WRITE");

	}

    /** 표준단어 요청서 결재처리... @return insomnia
     * @throws Exception */
    @RequestMapping("/dq/stnd/approveStndWord.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveStndWord(@RequestBody WdqqStwds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

//		//결재자인지 확인한다.
//		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
//		if(!checkapprove) {
//			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
//			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
//		}

		ArrayList<WdqqStwd> list = data.get("data");

		int result = WdqStndWordRqstService.approve(reqmst, list);

		//WdqStndWordRqstService.check(reqmst);

//		int result = WdqStndWordRqstService.regStndWordRqstlist(list);

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
		
		//승인요청 메일전송, 2019.02.11 
		//String reqURL = request.getRequestURI();
		//int tempResult = requestApproveService.sendMail(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

    /** 단어  자동분할화면 이동... @return String */
    @RequestMapping("/dq/stnd/worddiv_lst.do")
    public String goStndWordDivPage(@ModelAttribute("search") WdqqStwd search) {

    	return "/dq/stnd/worddiv_lst";
    }

}
