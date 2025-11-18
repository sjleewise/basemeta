/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : 
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 수도권 매립지 전용 기술용어관리 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2015. 3. 19.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2015. 3. 19. :            : 신규 개발.
 */
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
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.SlcGlossaryM;
import kr.wise.meta.stnd.service.SlcGlossaryService;
import kr.wise.meta.stnd.service.StndWordAbrService;
import kr.wise.meta.stnd.service.StndWordService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WamSymn;
import kr.wise.meta.stnd.service.WamWhereUsed;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.web.StndItemRqstCtrl.WapDvCanAsms;
import kr.wise.meta.stnd.web.StndSymnCtrl.WamSymns;

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

/**
 * <PRE>
 * 1. ClassName : 수도권 매립지 전용 기술용어관리
 * 2. FileName  : SlcGlossaryCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2015. 3. 19. 
 * </PRE>
 */
@Controller
public class SlcGlossaryCtrl {


	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MessageSource message;

	@Inject
	private SlcGlossaryService slcGlossaryService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codelistService;

	private Map<String, Object> codeMap;

	static class SlcGlossaryMs extends HashMap<String, ArrayList<SlcGlossaryM>> { }

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("regstDt", "updDt");
//	    binder.setDisallowedFields("updDt");
	}
	
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));

		return codeMap;
	}
	
	
	/** SLC 수도권매립지 용어집 페이지 호출	 * 	 */
	@RequestMapping("/meta/stnd/goSlcGlossarylist.do")
	public String goSlcGlossary() {
		
		return "/meta/stnd/slcglossary_lst";
	}

	
	/** SLC 용어 리스트 조회 - json list	 
	 * @throws Exception */
	@RequestMapping("/meta/stnd/getSlcGlossarylist.do")
	@ResponseBody
	public IBSheetListVO<SlcGlossaryM> getSlcGlossaryList(@ModelAttribute SlcGlossaryM data, Locale locale) throws Exception {

		logger.debug("reqvo:{}", data);

		List<SlcGlossaryM> list = slcGlossaryService.getSlcGlossaryList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<SlcGlossaryM>(list, list.size());

	}
	
	@RequestMapping("/meta/stnd/getSlcGlossaryXls.do")
	public String getSlcGlossaryXls(SlcGlossaryM data, ModelMap model, Locale locale) throws Exception {
		logger.debug("reqvo:{}", data);
		
		List list = slcGlossaryService.getSlcGlossaryListXls(data);
		
		model.addAttribute("SHEETDATA", list);
		
		logger.debug("result list:{}", list);
		
//		return "forward:/commons/xls/common_directxls";
		return "forward:/js/IBSheet/jsp/DirectDown2Excel.jsp";
	}

	
	/** SLC 용어 변경이력 조회 -IBSheet json */
	@RequestMapping("/meta/stnd/ajaxgrid/slcglossarychange_dtl.do")
	@ResponseBody
	public IBSheetListVO<SlcGlossaryM> getSlcGlossaryChange(@ModelAttribute("searchVO") SlcGlossaryM searchVO, String stwdId) throws Exception {

		logger.debug("{}", stwdId);
		List<SlcGlossaryM> list = slcGlossaryService.getSlcGlossaryChangeList(stwdId);
		return new IBSheetListVO<SlcGlossaryM>(list, list.size());
	}



	/** SLC 용어 상세정보 조회 
	 * @throws Exception */
	@RequestMapping("/meta/stnd/slcglossary_dtl.do")
	public String getSlcGlossaryDetail(@ModelAttribute("searchVO") SlcGlossaryM searchVO, ModelMap model) throws Exception {
		logger.debug(" {}", searchVO);
		
//		searchVO.setIbsStatus("I");
		model.addAttribute("saction", "I");

		//조회내용이 있을 경우 정보를 조회 하고 업데이트로 변경
		if( StringUtils.hasText(searchVO.getSlcGlossaryNm())) {

			SlcGlossaryM result = slcGlossaryService.getSlcGlossaryDetail(searchVO);
			result.setIbsStatus("U");
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/meta/stnd/slcglossary_dtl";
	}
	
	/** 매립지 기술용어 리스트 등록 - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/meta/stnd/regSlcGlossary.do")
	@ResponseBody
	public IBSResultVO<SlcGlossaryM> regSlcGlossary(@RequestBody SlcGlossaryMs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<SlcGlossaryM> list = data.get("data");

		int result = slcGlossaryService.regSlcGlossary(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<SlcGlossaryM>(result, resmsg, action);
	}
	
	@RequestMapping("/meta/stnd/delSlcGlossary.do")
	@ResponseBody
	public IBSResultVO<SlcGlossaryM> delSlcGlossary(@RequestBody SlcGlossaryMs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<SlcGlossaryM> list = data.get("data");

		int result = slcGlossaryService.delSlcGlossary(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		
		
		return new IBSResultVO<SlcGlossaryM>(result, resmsg, action);
	}


    



}
