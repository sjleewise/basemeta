package kr.wise.meta.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.app.service.AppStndSditmService;
import kr.wise.meta.app.service.WamAppSditm;

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
 * 2. FileName  : AppStndSdtimCtrl.java
 * 3. Package  : kr.wise.meta.app.web
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
@Controller
public class AppStndSditmCtrl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AppStndSditmService appStndSditmService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	private Map<String, Object> codeMap;



	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/meta/app/getsditmlist.do")
	@ResponseBody
	public IBSheetListVO<WamAppSditm> getAppStndItemList(@ModelAttribute WamAppSditm data, Locale locale) {

		logger.debug("req vo:{}", data);

		List<WamAppSditm> list = appStndSditmService.getStndItemList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamAppSditm>(list, list.size());

	}


	/** 표준 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/app/ajaxgrid/appsditminfo_dtl.do")
	public String selectWordInfoDetail(String appSditmId, ModelMap model) {
		logger.debug(" {}", appSditmId);

		// 메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if (!UtilObject.isNull(appSditmId)) {

			WamAppSditm result = appStndSditmService.selectSditmDetail(appSditmId);
			model.addAttribute("result", result);
		}

		return "/meta/app/appsditminfo_dtl";
	}

	/** 표준항목 검색 팝업.... @return insomnia */
	@RequestMapping("/meta/app/popup/appstnditem_pop.do")
	public String goStndItemPopup(@ModelAttribute("search") WamAppSditm searchvo, ModelMap model) {
		logger.debug("search:{}", searchvo);

		return "/meta/app/popup/appstnditem_pop";
	}

	/** 표준항목 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/app/ajaxgrid/sditmchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamAppSditm> selectMenuList(@ModelAttribute("searchVO") WamAppSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WamAppSditm> list = appStndSditmService.selectSditmChangeList(sditmId);
		return new IBSheetListVO<WamAppSditm>(list, list.size());
	}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
//		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
//		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
//		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
//
//		codeMap.put("dmngibs", dmngibs);
//		codeMap.put("dmngId", codeListService.getCodeList("dmng"));
//		codeMap.put("infotpibs", infotpibs);
//		codeMap.put("dmnginfotp", dmnginfotp);
//		codeMap.put("infotpId", codeListService.getCodeList("infotp"));

		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		return codeMap;
	}

}
