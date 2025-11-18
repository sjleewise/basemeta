/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준단어 조회 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 24. 오후 11:13:07
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 24. :            : 신규 개발.
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
import kr.wise.meta.stnd.service.AsisColItemMap;
import kr.wise.meta.stnd.service.AsisVsStndDicService;
import kr.wise.meta.stnd.service.StndWordAbrService;
import kr.wise.meta.stnd.service.StndWordService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WamWhereUsed;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.web.StndItemRqstCtrl.WapDvCanAsms;

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
 * 2. FileName  : AsisColvsItemMapCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2016. 4. 14.
 * </PRE>
 */
@Controller
public class AsisColvsItemMapCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MessageSource message;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codelistService;
	
	@Inject
	private AsisVsStndDicService asisVsStndDicService;

	//private Map<String, Object> codeMap;


	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/meta/stnd/getasiscolvsitem.do")
	@ResponseBody
	public IBSheetListVO<AsisColItemMap> getStndWordList(@ModelAttribute AsisColItemMap data, Locale locale) {

		//logger.debug("reqvo:{}", data);

		List<AsisColItemMap> list = asisVsStndDicService.getAsisColvsItemList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<AsisColItemMap>(list, list.size());

	}
	
	@RequestMapping("/meta/stnd/getasiscolvsitemusemodel.do")
	@ResponseBody
	public IBSheetListVO<AsisColItemMap> getStndWordListUseModel(@ModelAttribute AsisColItemMap data, Locale locale) {

		//logger.debug("reqvo:{}", data);

		List<AsisColItemMap> list = asisVsStndDicService.getAsisColvsItemListUseModel(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<AsisColItemMap>(list, list.size());

	}

	/** AS-IS 컬럼 vs TO-BE 표준용어 매핑 */
	@RequestMapping("/meta/stnd/asiscolvsitem_lst.do")
	public String goAsisColvsItem() {
		
		return "/meta/stnd/asiscolvsitem_lst";
	}


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		Map<String, Object> codeMap = new HashMap<String, Object>(); 

		//시스템영역 코드리스트 JSON
//		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));


		//공통코드 - IBSheet Combo Code용
//		codeMap.put("regTypCdibs", regTypCd);
//		codeMap.put("regTypCdValue", cmcdCodeService.getCodeList("REG_TYP_CD"));


//		List<CodeListVo> abrNm = codelistService.getCodeList("abrTempList");

//		codeMap.put("abrNm", abrNm);
		
		
//		String wdDcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD"));
//		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));
//		codeMap.put("wdDcdibs", wdDcdibs);
		

		return codeMap;
	}

}
