package kr.wise.meta.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.StndSymnTrmService;
import kr.wise.meta.stnd.service.WamSymn;
import kr.wise.meta.stnd.service.WamSymnTrm;
import kr.wise.meta.stnd.web.StndSymnCtrl.WamSymns;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StndSymnTrmCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	static class WamSymnTrms extends HashMap<String, ArrayList<WamSymnTrm>> { }
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private StndSymnTrmService stndSymnTrmService;
	
	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)SYMN_TRM_DCD



		//공통 코드(요청구분 코드리스트)
		String symnDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SYMN_DCD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
//		String symnTrmDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SYMN_TRM_DCD"));
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("symnDcdibs", symnDcd);
		codeMap.put("symnDcd", cmcdCodeService.getCodeList("SYMN_DCD"));
//		codeMap.put("symnTrmDcdibs", symnTrmDcd);
//		codeMap.put("symnTrmDcd", cmcdCodeService.getCodeList("SYMN_TRM_DCD"));
		return codeMap;
	}
	
	
	@RequestMapping(value="/commons/damgmt/symn/admin_symntrm_lst.do")
	public String goSymnListAdmin(HttpSession session, @RequestParam(value="objId", required=false) String symnId, Model model) {
		model.addAttribute("symnId", symnId);
		return "/commons/damgmt/symn/admin_symntrm_lst";
	}
	
	
	/** 유사어/금지어 상세 정보 조회 */
	/** meta */
	@RequestMapping("/meta/stnd/ajaxgrid/symntrm_dtl.do")
	public String selectMenuDetail(String symnId, String saction, ModelMap model) {
		logger.debug(" {}", symnId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnId)) {

			WamSymnTrm result = stndSymnTrmService.selectSymnDetail(symnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/meta/stnd/symn_dtl";
	}
	
	/** 유사어/금지어 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/stnd/getSymnTrmList.do")
	@ResponseBody
	public IBSheetListVO<WamSymnTrm> selectList(@ModelAttribute WamSymnTrm search) {
		logger.debug("searchVO : {}", search);
		List<WamSymnTrm> list = stndSymnTrmService.selectSymnList(search);
		
		return new IBSheetListVO<WamSymnTrm>(list, list.size());
		
	}
	
	/** 표준단어ID 팝업 호출 */
	@RequestMapping("/meta/stnd/popup/selectStwdTrmPop.do")
	public String goStwdIdPop() {
				
		return "/meta/stnd/popup/selectstwd_pop";
	}
	
	/** 유사어/금지어 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/meta/stnd/saveSymnTrmRow.do")
    @ResponseBody
    public IBSResultVO<WamSymnTrm> saveSymnRow(WamSymnTrm saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = stndSymnTrmService.saveSymnRow(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamSymnTrm>(saveVO, result, resmsg, action);
    }
	
    /** 유사어 리스트 삭제 - IBSheet JSON */
	/** meta */
	@RequestMapping("/meta/stnd/deleteSymnTrmList.do")
	@ResponseBody
	public IBSResultVO<WamSymnTrm> deleteSymnList(@RequestBody WamSymnTrms data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamSymnTrm> list = data.get("data");

		int result = stndSymnTrmService.deleteSymnList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WamSymnTrm>(result, resmsg, action);
	}
	
	/** 유사어 리스트 등록(엑셀업로드용) - IBSheet JSON */
	/** meta */
	@RequestMapping("/meta/stnd/saveSymnsTrm.do")
	@ResponseBody
	public IBSResultVO<WamSymnTrm> SaveSymns(@RequestBody WamSymnTrms data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamSymnTrm> list = data.get("data");

		int result = stndSymnTrmService.saveSymnList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WamSymnTrm>(result, resmsg, action);
	}

	/** 유사어/금지어 상세 정보 조회 */
	/** meta */
	@RequestMapping("/commons/damgmt/symn/ajaxgrid/admin_symntrm_dtl.do")
	public String selectMenuDetailAdmin(String symnId, String saction, ModelMap model) {
		logger.debug(" {}", symnId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnId)) {

			WamSymnTrm result = stndSymnTrmService.selectSymnDetail(symnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/symn/admin_symntrm_dtl";
	}
	
	
	
	/**-----------------------------동음이의어hmnm----------------------------------------------------------*/
	@RequestMapping(value="/commons/damgmt/symn/admin_hmnm_lst.do")
	public String goHmnmListAdmin(HttpSession session, @RequestParam(value="objId", required=false) String symnId, Model model) {
		model.addAttribute("symnId", symnId);
		return "/commons/damgmt/symn/admin_hmnm_lst";
	}
	
	
	/** 동음이의어 상세 정보 조회 */
	/** meta */
	@RequestMapping("/meta/stnd/ajaxgrid/hmnm_dtl.do")
	public String selectHmnmMenuDetail(String symnId, String saction, ModelMap model) {
		logger.debug(" {}", symnId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnId)) {

			WamSymnTrm result = stndSymnTrmService.selectHmnmDetail(symnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/meta/stnd/symn_dtl";
	}
	
	/** 동음이의어 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/stnd/gethmnmList.do")
	@ResponseBody
	public IBSheetListVO<WamSymnTrm> selectHmnmList(@ModelAttribute WamSymnTrm search) {
		logger.debug("searchVO : {}", search);
		List<WamSymnTrm> list = stndSymnTrmService.selectHmnmList(search);
		
		return new IBSheetListVO<WamSymnTrm>(list, list.size());
		
	}
	
	
	/** 동음이의어 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/meta/stnd/savehmnmRow.do")
    @ResponseBody
    public IBSResultVO<WamSymnTrm> saveHmnmRow(WamSymnTrm saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = stndSymnTrmService.saveHmnmRow(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamSymnTrm>(saveVO, result, resmsg, action);
    }
	
    /** 동음이의어 리스트 삭제 - IBSheet JSON */
	/** meta */
	@RequestMapping("/meta/stnd/deletehmnmList.do")
	@ResponseBody
	public IBSResultVO<WamSymnTrm> deleteHmnmList(@RequestBody WamSymnTrms data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamSymnTrm> list = data.get("data");

		int result = stndSymnTrmService.deleteHmnmList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WamSymnTrm>(result, resmsg, action);
	}
	
	/** 동음이의어 리스트 등록(엑셀업로드용) - IBSheet JSON */
	/** meta */
	@RequestMapping("/meta/stnd/savehmnms.do")
	@ResponseBody
	public IBSResultVO<WamSymnTrm> SaveHmnms(@RequestBody WamSymnTrms data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamSymnTrm> list = data.get("data");

		int result = stndSymnTrmService.saveHmnmList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WamSymnTrm>(result, resmsg, action);
	}

	/** 동음이의어 상세 정보 조회 */
	/** meta */
	@RequestMapping("/commons/damgmt/symn/ajaxgrid/admin_hmnm_dtl.do")
	public String selectHmnmDetailAdmin(String symnId, String saction, ModelMap model) {
		logger.debug(" {}", symnId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnId)) {

			WamSymnTrm result = stndSymnTrmService.selectHmnmDetail(symnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/symn/admin_hmnm_dtl";
	}
	

}
