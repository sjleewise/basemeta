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
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.StndSymnService;
import kr.wise.meta.stnd.service.WamSditm;
import kr.wise.meta.stnd.service.WamSymn;

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
public class StndSymnCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;
	
	static class WamSymns extends HashMap<String, ArrayList<WamSymn>> { }
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private StndSymnService stndSymnService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** yeonho */
	@RequestMapping(value="/meta/stnd/symn_lst.do")
	public String goSymnList(String linkFlag, HttpSession session, @RequestParam(value="objId", required=false) String symnId, Model model) {
		model.addAttribute("symnId", symnId);
		model.addAttribute("linkFlag", linkFlag);
		
		return "/meta/stnd/symn_lst";
	}
	
	/** 유사어/금지어 상세 정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/symn_dtl.do")
	public String selectMenuDetail(String symnId, String saction, ModelMap model) {
		logger.debug(" {}", symnId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnId)) {

			WamSymn result = stndSymnService.selectSymnDetail(symnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/meta/stnd/symn_dtl";
	}
	
	/** 유사어/금지어 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/stnd/getSymnList.do")
	@ResponseBody
	public IBSheetListVO<WamSymn> selectList(@ModelAttribute WamSymn search) {
		logger.debug("searchVO : {}", search);
		List<WamSymn> list = stndSymnService.selectSymnList(search);
		
		return new IBSheetListVO<WamSymn>(list, list.size());
		
	}
	
	/** 표준단어ID 팝업 호출 */
	@RequestMapping("/meta/stnd/popup/selectStwdPop.do")
	public String goStwdIdPop() {
				
		return "/meta/stnd/popup/selectstwd_pop";
	}
	
	/** 유사어/금지어 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/meta/stnd/saveSymnRow.do")
    @ResponseBody
    public IBSResultVO<WamSymn> saveSymnRow(WamSymn saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	
    	
    	if(saveVO.getIbsStatus().equals("I")){
        	int symnCnt = stndSymnService.getSymnCnt(saveVO);
        	logger.debug("\n symnCnt: {}", symnCnt);
        	
        	if(symnCnt > 0){
        		int result = -1;
        		String resmsg = "중복된 유사어가 존재합니다. <br> 입력하신 값을 확인해주세요";
        		
            	String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

            	return new IBSResultVO<WamSymn>(result, resmsg, action);
        	}
    	}
    	
    	int result = stndSymnService.saveSymnRow(saveVO);

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

    	return new IBSResultVO<WamSymn>(saveVO, result, resmsg, action);
    }
	
    /** 유사어 리스트 삭제 - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/meta/stnd/deleteSymnList.do")
	@ResponseBody
	public IBSResultVO<WamSymn> deleteSymnList(@RequestBody WamSymns data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamSymn> list = data.get("data");

		int result = stndSymnService.deleteSymnList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WamSymn>(result, resmsg, action);
	}
	
	/** 유사어 리스트 등록(엑셀업로드용) - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/meta/stnd/saveSymns.do")
	@ResponseBody
	public IBSResultVO<WamSymn> SaveSymns(@RequestBody WamSymns data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamSymn> list = data.get("data");

		int result = stndSymnService.saveSymnList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WamSymn>(result, resmsg, action);
	}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)


		//공통 코드(요청구분 코드리스트)
		String symnDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SYMN_DCD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String stndAsrt = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT"));
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("symnDcdibs", symnDcd);
		codeMap.put("symnDcd", cmcdCodeService.getCodeList("SYMN_DCD"));
		codeMap.put("stndAsrtibs", stndAsrt);
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		return codeMap;
	}
	/** yeonho */
	@RequestMapping(value="/commons/damgmt/symn/admin_symn_lst.do")
	public String goSymnListAdmin(HttpSession session, @RequestParam(value="objId", required=false) String symnId, Model model) {
		model.addAttribute("symnId", symnId);
		return "/commons/damgmt/symn/admin_symn_lst";
	}
	
	/** 유사어/금지어 상세 정보 조회 */
	/** yeonho */
	@RequestMapping("/commons/damgmt/symn/ajaxgrid/admin_symn_dtl.do")
	public String selectMenuDetailAdmin(String symnId, String saction, ModelMap model) {
		logger.debug(" {}", symnId);

		//신규 입력으로 초기화 
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnId)) {

			WamSymn result = stndSymnService.selectSymnDetail(symnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/symn/admin_symn_dtl";
	}
	
	@RequestMapping("/commons/damgmt/symn/ajaxgrid/symnchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamSymn> selectMenuList(@ModelAttribute("searchVO") WamSymn searchVO, String symnId) throws Exception {

		logger.debug("{}", symnId);
		List<WamSymn> list = stndSymnService.selectSymnChangeList(symnId);
		return new IBSheetListVO<WamSymn>(list, list.size());
	}
	
	@RequestMapping(value="/meta/stnd/popup/symn_lst.do")
	public String goSymnPopList(@ModelAttribute("search") WamSymn search, Model model) {
//		model.addAttribute("symnId", symnId);
		return "/meta/stnd/popup/symn_pop";
	}
	
}
