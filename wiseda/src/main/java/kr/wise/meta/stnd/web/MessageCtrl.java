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
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.MsgService;
import kr.wise.meta.stnd.service.WamMsg;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("MessageCtrl")
public class MessageCtrl {

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
	private MsgService msgService;
	//private StndItemRqstService stndItemRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CodeListService codelistService;

	//static class WaqSditms extends HashMap<String, ArrayList<WaqSditm>> {}
	
	static class WamMsgs extends HashMap<String, ArrayList<WamMsg>> {}
	
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
		
     	// 메시지 유형코드
		codeMap.put("msgPtrnDvcd", cmcdCodeService.getCodeList("MSG_PTRN_DVCD"));
		codeMap.put("msgPtrnDvcdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MSG_PTRN_DVCD")));
		
		//메시지 등록요청용 업무구분코드
		codeMap.put("bizDivCd", cmcdCodeService.getCodeList("BIZ_DIV_CD"));
		codeMap.put("bizDivCdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DIV_CD")));
		


		//목록성 코드(시스템영역 코드리스트)
//		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
//		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));
		//String dmng 		= UtilJson.convertJsonString(codeListService.getCodeList("dmng"));
		//String dmngibs 		= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		//String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		//String dmnginfotp 	= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
		//String infotpinfolst = UtilJson.convertJsonString(infotpService.getInfoTypeList());


//		codeMap.put("sysarea", sysarea);
//		codeMap.put("sysareaibs", sysareaibs);
		//codeMap.put("dmng", dmng);
		//codeMap.put("dmngibs", dmngibs);
		/*codeMap.put("infotpibs", infotpibs);
		codeMap.put("dmnginfotp", dmnginfotp);
		codeMap.put("infotpinfolst", infotpinfolst);
*/
		return codeMap;
	}
	

    	/** 메시지 조회 팝업.... lsi */
	@RequestMapping("/meta/stnd/popup/message_pop.do")
	public String goMessagePop(@ModelAttribute("search") WamMsg search ) {
		return "/meta/stnd/popup/message_pop";
	}

	/** 메시지 리스트 조회 */
	@RequestMapping("/meta/stnd/getMessageList.do")
	@ResponseBody
	public IBSheetListVO<WamMsg> getMessageList(@ModelAttribute WamMsg data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WamMsg> list = msgService.getMessageList(data);


		return new IBSheetListVO<WamMsg>(list, list.size());

	}
	
     /**
	 * 메시지 조회 페이지로 이동한다.
	 * @return
	 */
	@RequestMapping("/meta/stnd/message_lst.do")
	public String goStndStwdList(HttpSession session, @RequestParam(value="action",  required=false) String action, @RequestParam(value="objId", required=false) String msgId, String linkFlag, Model model) {
		logger.debug("{}", action);
		logger.debug("linkFlag : {}",linkFlag);
		
		model.addAttribute("action", action);
		model.addAttribute("msgId", msgId);
		model.addAttribute("linkFlag",linkFlag);

		return "/meta/stnd/message_lst";
	}
	
		/** 메시지 상세정보 조회 */
	@RequestMapping("/meta/stnd/ajaxgrid/message_dtl.do")
	public String selectMessageDetail(String msgId, ModelMap model) {
		logger.debug(" {}", msgId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(msgId)) {
			WamMsg result = msgService.selectMessageDetail(msgId);
			model.addAttribute("result", result);
		}
		return "/meta/stnd/messageinfo_dtl";
	}

	/** 메시지 변경이력 조회 -IBSheet json */
	/** 이상익 */
	@RequestMapping("/meta/stnd/ajaxgrid/messagechange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamMsg> selectMessageChangeList(@ModelAttribute("searchVO") WamMsg searchVO, String msgId) throws Exception {

		logger.debug("{}", msgId);
		List<WamMsg> list = msgService.selectMessageChangeList(msgId);
		return new IBSheetListVO<WamMsg>(list, list.size());
	}


}
