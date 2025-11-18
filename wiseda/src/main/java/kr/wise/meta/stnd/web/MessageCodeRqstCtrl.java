/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageRqstCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 메시지 등록요청 ctrl
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 9. 24.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 9. 24. :            : 신규 개발.
 */
package kr.wise.meta.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.MsgCodeRqstService;
import kr.wise.meta.stnd.service.WaqActnMsg;

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

@Controller("MessageCodeRqstCtrl")
public class MessageCodeRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private MsgCodeRqstService msgCodeRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private CodeListService codeListService;
	
	static class WaqActnMsgs extends HashMap<String, ArrayList<WaqActnMsg>> {}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

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
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		
		//오류전문언어구분코드
		codeMap.put("errTlgLanDscd", cmcdCodeService.getCodeList("ERR_TLG_LAN_DSCD"));
		codeMap.put("errTlgLanDscdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ERR_TLG_LAN_DSCD")));
		
		return codeMap;
	}
	
	/** 메시지 등록요청서 입력 폼.... @throws Exception  */
    @RequestMapping("/meta/stnd/messagecode_rqst.do")
    public String goMessageRqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);
    	
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("MAC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}
    	
    	logger.debug("reqmst:{}", reqmst);
    	
    	model.addAttribute("waqMstr", reqmst);
    	
    	
    	return "/meta/stnd/messagecode_rqst";
    	
    }

    /** 메시지 요청서 상세정보 조회 @return  
     * @throws Exception */
    @RequestMapping("/meta/stnd/ajaxgrid/messagecode_rqst_dtl.do")
    public String goMessageRqstDetail(WaqActnMsg searchVo, ModelMap model) throws Exception {
    	logger.debug("searhcVo:{}", searchVo);
    	
    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	} else {
    		WaqActnMsg result = msgCodeRqstService.getMsgRqstDetail(searchVo);
    		model.addAttribute("result", result);
    		model.addAttribute("saction", "U");
    	}
    	
    	return "/meta/stnd/messagecode_rqst_dtl";
    }

    /** 메시지 리스트 등록 @throws Exception  */
    @RequestMapping("/meta/stnd/regmsgcoderqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMsgRqstList(@RequestBody WaqActnMsgs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqActnMsg> list = data.get("data");

		int result = msgCodeRqstService.register(reqmst, list);
	    
		logger.debug("메시지 검증 시작");
		result += msgCodeRqstService.check(reqmst);
        logger.debug("메시지 검증 종료");
		String resmsg;

		if(result > 0 ){
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
    
    /** 메시지 요청 리스트 조회.... @return  */
    @RequestMapping("/meta/stnd/getmsgcoderqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqActnMsg> getMsgRqstList (WaqMstr search) {
    	logger.debug("search:{}", search);

    	List<WaqActnMsg> list = msgCodeRqstService.getMsgRqstList(search);

    	return new IBSheetListVO<WaqActnMsg>(list, list.size());
    }
    
    @RequestMapping("/meta/stnd/delmsgcoderqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delMsgRqstList(@RequestBody WaqActnMsgs data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);

    	ArrayList<WaqActnMsg> list = data.get("data");

    	int result = msgCodeRqstService.delMsgRqstList(reqmst, list);
    	result += msgCodeRqstService.check(reqmst);

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
    
    //메시지 승인
    @RequestMapping("/meta/stnd/approveMsgCode.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveMsg (@RequestBody WaqActnMsgs data, WaqMstr reqmst, Locale locale) throws Exception {

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

    	List<WaqActnMsg> list = data.get("data");

    	int result = msgCodeRqstService.approve(reqmst, list);


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
    
 	/** 표준오류메시지 변경대상 추가..(팝업에서 호출함) @throws Exception insomnia */
	@RequestMapping("/meta/stnd/regWam2WaqMessageCode.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqActnMsgs data, WaqMstr reqmst, Locale locale) throws Exception {
		
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		
		ArrayList<WaqActnMsg> list = data.get("data");
		
		int result = msgCodeRqstService.regWam2Waq(reqmst, list);
		
		result += msgCodeRqstService.check(reqmst);
		
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
	
	@RequestMapping("/meta/stnd/popup/messagecode_xls.do")
    public String gomessagexls(@ModelAttribute("search") WaqActnMsg search) {

    	return "/meta/stnd/popup/messagecode_xls";
    }

	

}
