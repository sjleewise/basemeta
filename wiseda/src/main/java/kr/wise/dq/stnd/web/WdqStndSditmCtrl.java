package kr.wise.dq.stnd.web;

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
import kr.wise.dq.stnd.service.WdqStndSditmService;
import kr.wise.dq.stnd.service.WdqmSditm;
import kr.wise.dq.stnd.service.WdqmStwdCnfg;
import kr.wise.dq.stnd.service.WdqmWhereUsed;

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
 * 2. FileName  : StndSdtimCtrl.java
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 3. 오후 2:10:35
 * </PRE>
 */
@Controller
public class WdqStndSditmCtrl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WdqStndSditmService WdqStndSditmService;

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

	@RequestMapping("/dq/stnd/getsditmlist.do")
	@ResponseBody
	public IBSheetListVO<WdqmSditm> getStndItemList(@ModelAttribute WdqmSditm data, Locale locale) {

		logger.debug("req vo:{}", data);

		List<WdqmSditm> list = WdqStndSditmService.getStndItemList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmSditm>(list, list.size());

	}


	/** 표준 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditminfo_dtl.do")
	public String selectWordInfoDetail(String sditmId, ModelMap model) {
		logger.debug(" {}", sditmId);

		// 메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if (!UtilObject.isNull(sditmId)) {

			WdqmSditm result = WdqStndSditmService.selectSditmDetail(sditmId);
			model.addAttribute("result", result);
		}
		model.addAttribute("codeMap",getcodeMap());
		return "/dq/stnd/sditminfo_dtl";
	}
	
	/** 표준 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/notsditminfo_dtl.do")
	public String selectNotWordInfoDetail(String sditmId, ModelMap model) {
		logger.debug(" {}", sditmId);
		
		// 메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if (!UtilObject.isNull(sditmId)) {
			
			WdqmSditm result = WdqStndSditmService.selectSditmDetail(sditmId);
			model.addAttribute("result", result);
		}
		model.addAttribute("codeMap",getcodeMap());
		return "/dq/stnd/notsditminfo_dtl";
	}

	/** 표준항목 검색 팝업.... @return insomnia */
	@RequestMapping("/dq/stnd/popup/stnditem_pop.do")
	public String goStndItemPopup(@ModelAttribute("search") WdqmSditm searchvo, ModelMap model) {
		logger.debug("search:{}", searchvo);

		model.addAttribute("codeMap",getcodeMap());
		
		return "/dq/stnd/popup/stnditem_pop";
	}

	/** 표준항목 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditmchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmSditm> selectMenuList(@ModelAttribute("searchVO") WdqmSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WdqmSditm> list = WdqStndSditmService.selectSditmChangeList(sditmId);
		return new IBSheetListVO<WdqmSditm>(list, list.size());
	}

	/** 표준항목 구성항목 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditminit_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwdCnfg> selectSditmInitList(@ModelAttribute("searchVO") WdqmSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WdqmStwdCnfg> list = WdqStndSditmService.selectSditmInitList(sditmId);
		return new IBSheetListVO<WdqmStwdCnfg>(list, list.size());
	}

	/** 표준항목 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditmwhereused_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmWhereUsed> selectSditmWhereUsedList(@ModelAttribute("searchVO") WdqmSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WdqmWhereUsed> list = WdqStndSditmService.selectSditmWhereUsedList(sditmId);
		return new IBSheetListVO<WdqmWhereUsed>(list, list.size());
	}

//	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));

		codeMap.put("dmngibs", dmngibs);
		codeMap.put("dmngId", codeListService.getCodeList("dmng"));
		codeMap.put("infotpibs", infotpibs);
		codeMap.put("dmnginfotp", dmnginfotp);
		codeMap.put("infotpId", codeListService.getCodeList("infotp"));

		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
//		//표준구분 멀티사전용
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));
		return codeMap;
	}
	
	/** 표준항목 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditmcomparison_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmSditm> selectSditmComparisonList(@ModelAttribute("searchVO") WdqmSditm searchVO, String sditmId) throws Exception {
		logger.debug("{}", sditmId);
		List<WdqmSditm> list = WdqStndSditmService.selectSditmComparisonList(sditmId);
		return new IBSheetListVO<WdqmSditm>(list, list.size());
	}

}
