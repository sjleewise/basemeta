/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 메시지 조회 ctrl
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
import javax.servlet.http.HttpSession;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.MsgChnlService;
import kr.wise.meta.stnd.service.WamChnlErrMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("MessageChnlCtrl")
public class MessageChnlCtrl {

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
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private MsgChnlService msgChnlService;
	
	@Inject
	private CodeListService codeListService;
	
	//private StndItemRqstService stndItemRqstService;


	//static class WaqSditms extends HashMap<String, ArrayList<WaqSditm>> {}
	
	static class WamChnlErrMsgs extends HashMap<String, ArrayList<WamChnlErrMsg>> {}
	
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
		

		//오류전문언어구분코드
		codeMap.put("errTlgLanDscd", cmcdCodeService.getCodeList("ERR_TLG_LAN_DSCD"));
		codeMap.put("errTlgLanDscdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ERR_TLG_LAN_DSCD")));
		
		//오류유형코드
		codeMap.put("errTpCd", cmcdCodeService.getCodeList("ERR_TP_CD"));
		codeMap.put("errTpCdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ERR_TP_CD")));
		
		//채널유형코드
		codeMap.put("chnlTpCd", cmcdCodeService.getCodeList("CHNL_TP_CD"));
		codeMap.put("chnlTpCdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CHNL_TP_CD")));
		
		//채널세부분류코드
		codeMap.put("chnlDtlsClcd", cmcdCodeService.getCodeList("CHNL_DTL_CD"));
		codeMap.put("chnlDtlsClcdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CHNL_DTL_CD")));
		
		//오류코드사용여부
		codeMap.put("errCdUsYn", cmcdCodeService.getCodeList("ERR_CD_US_YN"));
		codeMap.put("errCdUsYnibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ERR_CD_US_YN")));
		
		//채널유형코드 / 채널세부분류
		codeMap.put("chnlTpDtls", UtilJson.convertJsonString(codeListService.getCodeList("chnlTpDtls")));
		

		return codeMap;
	}
	


	/** 메시지 조회 팝업....*/
	@RequestMapping("/meta/stnd/popup/messagechnl_pop.do")
	public String goMessagePop(@ModelAttribute("search") WamChnlErrMsg search ) {
		return "/meta/stnd/popup/messagechnl_pop";
	}
	
	/** 메시지 리스트 조회 */
	@RequestMapping("/meta/stnd/getMessageChnlList.do")
	@ResponseBody
	public IBSheetListVO<WamChnlErrMsg> getMessageList(@ModelAttribute WamChnlErrMsg data, Locale locale) {
		
		logger.debug("reqvo:{}", data);
		
		List<WamChnlErrMsg> list = msgChnlService.getChnlMessageList(data);
		
		
		return new IBSheetListVO<WamChnlErrMsg>(list, list.size());
		
	}
	
     /**
	 * 메시지 조회 페이지로 이동한다.
	 * @return
	 */
	@RequestMapping("/meta/stnd/messagechnl_lst.do")
	public String goMsgList(HttpSession session, @RequestParam(value="action",  required=false) String action, @RequestParam(value="objId", required=false) String chnlMsgId, String linkFlag, Model model) {
		logger.debug("{}", action);
		logger.debug("linkFlag : {}",linkFlag);
		
		model.addAttribute("action", action);
		model.addAttribute("chnlMsgId", chnlMsgId);
		model.addAttribute("linkFlag",linkFlag);

		return "/meta/stnd/messagechnl_lst";
	}
	
		/** 메시지 상세정보 조회 */
	@RequestMapping("/meta/stnd/ajaxgrid/messagechnl_dtl.do")
	public String selectMessageDetail(String chnlMsgId, ModelMap model) {
		logger.debug(" {}", chnlMsgId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(chnlMsgId)) {
			WamChnlErrMsg result = msgChnlService.selectChnlMessageDetail(chnlMsgId);
			model.addAttribute("result", result);
		}
		return "/meta/stnd/messagechnlinfo_dtl";
	}

	/** 메시지 변경이력 조회 -IBSheet json */
	@RequestMapping("/meta/stnd/ajaxgrid/messagechnlchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamChnlErrMsg> selectMessageChangeList(@ModelAttribute("searchVO") WamChnlErrMsg searchVO, String chnlMsgId) throws Exception {

		logger.debug("{}", chnlMsgId);
		List<WamChnlErrMsg> list = msgChnlService.selectChnlMessageChangeList(chnlMsgId);
		return new IBSheetListVO<WamChnlErrMsg>(list, list.size());
	}


}
