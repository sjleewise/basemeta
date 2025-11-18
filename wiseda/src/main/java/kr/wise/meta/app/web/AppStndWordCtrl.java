/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndWordCtrl.java
 * 2. Package : kr.wise.meta.app.web
 * 3. Comment : 표준단어 조회 컨트롤러
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.app.service.AppStndWordService;
import kr.wise.meta.app.service.WamAppStwd;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndWordCtrl.java
 * 3. Package  : kr.wise.meta.app.web
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
@Controller
public class AppStndWordCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AppStndWordService appStndWordService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	private Map<String, Object> codeMap;


	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/meta/app/getStndWordlist.do")
	@ResponseBody
	public IBSheetListVO<WamAppStwd> getAppStndWordList(@ModelAttribute WamAppStwd data, Locale locale) {
		
		logger.debug("reqvo:{}", data);
		
		List<WamAppStwd> list = appStndWordService.getStndWordList(data);
		
		
//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);
		
		return new IBSheetListVO<WamAppStwd>(list, list.size());
		
	}
	/** 표준단어 약어생성 메인페이지 호출 */
	/** yeonho */
	@RequestMapping("/meta/app/stwdabbreviated_lst.do")
	public String goAbbreviatedList(HttpSession session) {

		return "/meta/app/stwdabbreviated_lst";
	}


	/** 표준단어 조회 팝어.... insomnia */
	@RequestMapping("/meta/app/popup/appstndword_pop.do")
	public String goStndWordPop(@ModelAttribute("search") WamAppStwd search ) {
		return "/meta/app/popup/appstndword_pop";
	}


	/** 단어 변경이력 조회 -IBSheet json */
	@RequestMapping("/meta/app/ajaxgrid/appstwdchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamAppStwd> selectMenuList(@ModelAttribute("searchVO") WamAppStwd searchVO, String stwdId) throws Exception {

		logger.debug("{}", stwdId);
		List<WamAppStwd> list = appStndWordService.selectStndWordChangeList(stwdId);
		return new IBSheetListVO<WamAppStwd>(list, list.size());
	}



	/** 단어 상세정보 조회 */
	@RequestMapping("/meta/app/ajaxgrid/appstwdinfo_dtl.do")
	public String selectWordInfoDetail(String appStwdId, ModelMap model) {
		logger.debug("appStwdId : {}", appStwdId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(appStwdId)) {

			WamAppStwd result = appStndWordService.selectStndWordDetail(appStwdId);
			model.addAttribute("result", result);
		}
		return "/meta/app/appstwdinfo_dtl";
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
		
		
		String wdDcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD"));
		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));
		codeMap.put("wdDcdibs", wdDcdibs);
		

		return codeMap;
	}

}
