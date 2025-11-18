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
import kr.wise.meta.stnd.service.MsgMapService;
import kr.wise.meta.stnd.service.WamErrMsgMap;

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

@Controller("MessageMapCtrl")
public class MessageMapCtrl {

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
	private MsgMapService msgMapService;
	
	@Inject
	private CodeListService codeListService;
	
	//private StndItemRqstService stndItemRqstService;


	//static class WaqSditms extends HashMap<String, ArrayList<WaqSditm>> {}
	
	static class WamErrMsgMaps extends HashMap<String, ArrayList<WamErrMsgMap>> {}
	
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
		
		return codeMap;
	}
	


	/** 메시지 조회 팝업....*/
	@RequestMapping("/meta/stnd/popup/messagemap_pop.do")
	public String goMessagemapPop(@ModelAttribute("search") WamErrMsgMap search ) {
		return "/meta/stnd/popup/messagemap_pop";
	}
	
	/** 유효값 조회 팝업....*/
	@RequestMapping("/meta/stnd/popup/stnddmncdval_pop.do")
	public String goDmnCdValPop(@ModelAttribute("search") WamErrMsgMap search ) {
		return "/meta/stnd/popup/stnddmncdval_pop";
	}
	
	/** 메시지 리스트 조회 */
	@RequestMapping("/meta/stnd/getMessageMapList.do")
	@ResponseBody
	public IBSheetListVO<WamErrMsgMap> getMessageMapList(@ModelAttribute WamErrMsgMap data, Locale locale) {
		
		logger.debug("reqvo:{}", data);
		
		List<WamErrMsgMap> list = msgMapService.getMapMessageList(data);
		
		
		return new IBSheetListVO<WamErrMsgMap>(list, list.size());
		
	}
	
     /**
	 * 메시지 조회 페이지로 이동한다.
	 * @return
	 */
	@RequestMapping("/meta/stnd/messagemap_lst.do")
	public String goMsgMapList(HttpSession session, @RequestParam(value="action",  required=false) String action, @RequestParam(value="objId", required=false) String msgMapId, String linkFlag, Model model) {
		logger.debug("{}", action);
		logger.debug("linkFlag : {}",linkFlag);
		
		model.addAttribute("action", action);
		model.addAttribute("msgMapId", msgMapId);
		model.addAttribute("linkFlag",linkFlag);

		return "/meta/stnd/messagemap_lst";
	}
	
		/** 메시지 상세정보 조회 */
	@RequestMapping("/meta/stnd/ajaxgrid/messagemap_dtl.do")
	public String selectMessageDetail(String msgMapId, ModelMap model) {
		logger.debug(" {}", msgMapId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(msgMapId)) {
			WamErrMsgMap result = msgMapService.selectMapMessageDetail(msgMapId);
			model.addAttribute("result", result);
		}
		return "/meta/stnd/messagemapinfo_dtl";
	}

	/** 메시지 변경이력 조회 -IBSheet json */
	/** 이상익 */
	@RequestMapping("/meta/stnd/ajaxgrid/messagemapchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamErrMsgMap> selectMessageChangeList(@ModelAttribute("searchVO") WamErrMsgMap searchVO, String msgMapId) throws Exception {

		logger.debug("{}", msgMapId);
		List<WamErrMsgMap> list = msgMapService.selectMapMessageChangeList(msgMapId);
		return new IBSheetListVO<WamErrMsgMap>(list, list.size());
	}


}
