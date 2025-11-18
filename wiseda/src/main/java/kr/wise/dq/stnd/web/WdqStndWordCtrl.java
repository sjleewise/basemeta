/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordCtrl.java
 * 2. Package : kr.wise.dq.stnd.web
 * 3. Comment : 표준단어 조회 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 24. 오후 11:13:07
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.stnd.web;

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
import kr.wise.dq.stnd.service.WdqStndWordAbrService;
import kr.wise.dq.stnd.service.WdqStndWordService;
import kr.wise.dq.stnd.service.WdqmStwd;
import kr.wise.dq.stnd.service.WdqmStwdAbr;
import kr.wise.dq.stnd.service.WdqmWhereUsed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordCtrl.java
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 24. 오후 11:13:07
 * </PRE>
 */
@Controller
public class WdqStndWordCtrl {

	static class WdqmStwdAbrs extends HashMap<String, ArrayList<WdqmStwdAbr>> { }

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MessageSource message;

	@Inject
	private WdqStndWordService WdqStndWordService;

	@Inject
	private WdqStndWordAbrService WdqStndWordAbrService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codelistService;

	private Map<String, Object> codeMap;


	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/dq/stnd/getStndWordlist.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwd> getStndWordList(@ModelAttribute WdqmStwd data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WdqmStwd> list = WdqStndWordService.getStndWordList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmStwd>(list, list.size());

	}
	/** 표준단어 약어생성 메인페이지 호출 */
	/** yeonho */
	@RequestMapping("/dq/stnd/stwdabbreviated_lst.do")
	public String goAbbreviatedList(HttpSession session) {

		return "/dq/stnd/stwdabbreviated_lst";
	}


	/** 표준단어 조회 팝어.... insomnia */
	@RequestMapping("/dq/stnd/popup/stndword_pop.do")
	public String goStndWordPop(@ModelAttribute("search") WdqmStwd search ) {
		return "/dq/stnd/popup/stndword_pop";
	}


	/** 단어 변경이력 조회 -IBSheet json */
	@RequestMapping("/dq/stnd/ajaxgrid/stwdchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwd> selectMenuList(@ModelAttribute("searchVO") WdqmStwd searchVO, String stwdId) throws Exception {

		logger.debug("{}", stwdId);
		List<WdqmStwd> list = WdqStndWordService.selectStndWordChangeList(stwdId);
		return new IBSheetListVO<WdqmStwd>(list, list.size());
	}



	/** 단어 상세정보 조회 */
	@RequestMapping("/dq/stnd/ajaxgrid/stwdinfo_dtl.do")
	public String selectWordInfoDetail(String stwdId, ModelMap model) {
		logger.debug(" {}", stwdId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(stwdId)) {

			WdqmStwd result = WdqStndWordService.selectStndWordDetail(stwdId);
			model.addAttribute("result", result);
		}
		return "/dq/stnd/stwdinfo_dtl";
	}


	/** 표준단어 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/stwdwhereused_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmWhereUsed> selectStwdWhereUsedList(@ModelAttribute("searchVO") WdqmStwd searchVO, String stwdId) throws Exception {

		logger.debug("{}", stwdId);
		List<WdqmWhereUsed> list = WdqStndWordService.selectStwdWhereUsedList(searchVO);
		return new IBSheetListVO<WdqmWhereUsed>(list, list.size());
	}

	/** 표준단어 약어생성 리스트 조회 -IBSheet json */
	@RequestMapping("/dq/stnd/ajaxgrid/searchStwdAbbreviated_lst.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwdAbr> searchStwdAbbreviated_lst(@ModelAttribute("searchVO") WdqmStwdAbr searchVO) throws Exception {

		logger.debug("{}", searchVO);
		List<WdqmStwdAbr> list = WdqStndWordAbrService.selectStwdAbbreviatedList(searchVO);

		return new IBSheetListVO<WdqmStwdAbr>(list, list.size());
	}

	/** 표준단어 약어생성 등록 - IBSheet JSON
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/generateStwdAbbreviated.do")
	@ResponseBody
	public IBSResultVO<WdqmStwdAbr> generateList(@RequestBody WdqmStwdAbrs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WdqmStwdAbr> list = data.get("data");
		String abrId = WdqStndWordService.generateAbrList(list);
		String resmsg;
		int result;
		String action;
		if(abrId != null) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
			action = abrId;
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
			action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		}


		return new IBSResultVO<WdqmStwdAbr>(result, resmsg, action);

	}
	
	
    @RequestMapping("/dq/stnd/delStwdAbrLst.do")
    @ResponseBody
    public IBSResultVO<WdqmStwdAbr> delStwdAbrLst(@RequestBody WdqmStwdAbrs data, Locale locale) throws Exception {
    	logger.debug("division:{}", data);
    	List<WdqmStwdAbr> list = data.get("data");
    	
    	//선택 단어 삭제
    	Map<String, String> resultMap = WdqStndWordAbrService.delStwdAbrLst(list);
    	int result = Integer.parseInt(resultMap.get("result"));
    	String resmsg;
    	
    	if(result > 0 ){
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	
    	return new IBSResultVO<WdqmStwdAbr>(resultMap, result, resmsg, action);
    }
    

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));


		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCdValue", cmcdCodeService.getCodeList("REG_TYP_CD"));

//		List<CodeListVo> abrNm = codelistService.getCodeList("abrTempList");
//		codeMap.put("abrNm", abrNm);
		
//		String wdDcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD"));
//		codeMap.put("wdDcdibs", wdDcdibs);
		
//		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));
		

//		//표준구분 이베이코리아
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
				
		return codeMap;
	}
	
	
	/** 단어 변경이력 조회 -IBSheet json */
	@RequestMapping("/dq/stnd/ajaxgrid/stwdcomparison_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwd> selectStwdComparisonList(@ModelAttribute("searchVO") WdqmStwd searchVO, String stwdId) throws Exception {

		logger.debug("{}", stwdId);
		List<WdqmStwd> list = WdqStndWordService.selectStndWordComparisonList(stwdId);
		return new IBSheetListVO<WdqmStwd>(list, list.size());
	}

}
