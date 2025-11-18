package kr.wise.meta.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.StndDomainService;
import kr.wise.meta.stnd.service.StndSditmService;
import kr.wise.meta.stnd.service.WamDmn;
import kr.wise.meta.stnd.service.WamSditm;
import kr.wise.meta.stnd.service.WamStwdCnfg;
import kr.wise.meta.stnd.service.WamWhereUsed;

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
 * 2. FileName  : TransMngCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 12.03
 * </PRE>
 */
@Controller("TransMngCtrl")
public class TransMngCtrl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private StndSditmService stndSditmService;

	@Inject
	private StndDomainService stndDomainService;
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	private Map<String, Object> codeMap;
	
	@Inject
	private MessageSource message;


	static class WamDmns extends HashMap<Object, ArrayList<WamDmn>> {}
	
	static class WamSditms extends HashMap<Object, ArrayList<WamSditm>> {}


	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	

	
	/** 테스트 변환관리 페이지 조회 */
	@RequestMapping("/meta/stnd/transmng_lst.do")
	public String getTransMngView(ModelMap model) {
		logger.debug(" {}", "테스트변환관리페이지 조회");

		//	model.addAttribute("result", result);
	//	}

		return "/meta/stnd/transmng_lst";
	}
	
	@RequestMapping("/meta/stnd/getdmntranslist.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> getDmnTransList(@ModelAttribute WamDmn data, Locale locale) {

		logger.debug("vo:{}", data);

		List<WamDmn> list = stndDomainService.getDmnTransList(data);


		return new IBSheetListVO<WamDmn>(list, list.size());

	}
	
	@RequestMapping("/meta/stnd/getsditmtranslist.do")
	@ResponseBody
	public IBSheetListVO<WamSditm> getSditmTransList(@ModelAttribute WamSditm data, Locale locale) {

		logger.debug("vo:{}", data);

		List<WamSditm> list = stndSditmService.getSditmTransList(data);


		return new IBSheetListVO<WamSditm>(list, list.size());

	}


	/** 도메인 변환대상 요청처리 저장 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/stnd/saveDmnTransYnPrc.do")
	@ResponseBody
	public IBSResultVO<WamDmn> regDmnTransYn(@RequestBody WamDmns data, Locale locale) throws Exception {
		
		logger.debug("{}", data);

		ArrayList<WamDmn> list = data.get("data");
		int result = stndDomainService.saveDmnTransYnPrc(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WamDmn>(result, resmsg, action);
	}
	
		/** 표준용어 변환대상 요청처리 저장 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/stnd/saveSditmTransYnPrc.do")
	@ResponseBody
	public IBSResultVO<WamSditm> regSditmTransYn(@RequestBody WamSditms data, Locale locale) throws Exception {
		
		logger.debug("{}", data);

		ArrayList<WamSditm> list = data.get("data");
		int result = stndSditmService.saveSditmTransYnPrc(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WamSditm>(result, resmsg, action);
	}


	@ModelAttribute("codeMap")
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
		
		//코드값유형코드
		codeMap.put("cdValTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD")));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		//코드값부여방식코드
		codeMap.put("cdValIvwCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD")));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		
		return codeMap;
	}

}
