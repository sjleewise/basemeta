package kr.wise.commons.damgmt.db.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.db.service.DbmsDataTypeService;
import kr.wise.commons.damgmt.db.service.WaaDbmsDataType;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbmsDataTypeCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.db.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 22. 오후 3:26:07
 * </PRE>
 */ 
@Controller("DbmsDataTypeCtrl")
@RequestMapping("/commons/damgmt/db/*")
public class DbmsDataTypeCtrl {

	
	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaaDbmsDataTypes extends HashMap<String, ArrayList<WaaDbmsDataType>> { }
	
	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private DbmsDataTypeService datatypeService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	
	
	@Inject
	private MessageSource message;
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		String dbmstypcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmstypcdibs);
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCdibs);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		return codeMap;
		
	}
	
	@RequestMapping("dbmsdatatype_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbmsdatatype_lst";
	}
	
	/** DBMS타입 리스트 조회 - IBSheet JSON */
	@RequestMapping("datatypeSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbmsDataType> selectList(@ModelAttribute WaaDbmsDataType search) {
		logger.debug("{}", search);
		List<WaaDbmsDataType> list = datatypeService.getList(search);
		
		return new IBSheetListVO<WaaDbmsDataType>(list, list.size());
	}
	
	/** DBMS타입 등록 - IBSheet JSON */
	@RequestMapping("datatypeReglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbmsDataType> regList(@RequestBody WaaDbmsDataTypes data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaDbmsDataType> list = data.get("data");
//		logger.debug("어디서 걸리나");
		int result = datatypeService.regDataTypeList(list);
		String resmsg;
//		logger.debug("여긴가");
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDbmsDataType>(result, resmsg, action);
		
		
	}

	@RequestMapping("datatypeDellist.do")
	@ResponseBody
	public IBSResultVO<WaaDbmsDataType> delList(@RequestBody WaaDbmsDataTypes data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaDbmsDataType> list = data.get("data");
		
		int result = datatypeService.delDataTypeList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaDbmsDataType>(result, resmsg, action);
	}
	
	
	
}
