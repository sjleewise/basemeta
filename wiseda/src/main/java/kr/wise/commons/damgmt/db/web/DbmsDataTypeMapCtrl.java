/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbmsDataTypeMapCtrl.java
 * 2. Package : kr.wise.commons.damgmt.db.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 5. 21. 오후 1:23:52
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 5. 21. :            : 신규 개발.
 */
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
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.db.service.DbmsDataTypeMapService;
import kr.wise.commons.damgmt.db.service.WaaDataTypeMapru;
import kr.wise.commons.damgmt.db.service.WaaDbmsDataType;
import kr.wise.commons.damgmt.db.web.DbmsDataTypeCtrl.WaaDbmsDataTypes;
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
 * 2. FileName  : DbmsDataTypeMapCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.db.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 21. 오후 1:23:52
 * </PRE>
 */
@Controller
public class DbmsDataTypeMapCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaaDataTypeMaprus extends HashMap<String, ArrayList<WaaDataTypeMapru>> { }
	
	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private DbmsDataTypeMapService dbmsDataTypeMapService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	
	
	@Inject
	private MessageSource message;
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		
		//목록성코드
		ComboIBSVo dbmsDataTypeibs = codeListService.getCodeListIBS("dbmsDataType");
		codeMap.put("dbmsDataTypeibs", UtilJson.convertJsonString(dbmsDataTypeibs));
//		codeMap.put("dbmsDataType", UtilJson.convertJsonString(dbmsDataType));
		
		//IBSheet Double Select
		String dbmsDataTypeDb 		= UtilJson.convertJsonString(codeListService.getCodeList("dbmsDataTyp"));
		codeMap.put("dbmsDataTypeDb", dbmsDataTypeDb);
				
		String dbmstypvers 		= UtilJson.convertJsonString(codeListService.getCodeList("dbmstypvers"));
	       // tblSpacPnm = UtilJson.convertJsonString(codeListService.getCodeListIBS("tblSpacPnm"));
	        //String idxSpacPnm = UtilJson.convertJsonString(codeListService.getCodeListIBS("idxSpacPnm"));
			
		codeMap.put("dbmstypvers", dbmstypvers);

		Map<String, ComboIBSVo> dbmsDataType = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbmsDataType"));
		codeMap.put("dbmsDataType",  UtilJson.convertJsonString(dbmsDataType));
		
		//공통코드
		String dbmstypcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmstypcdibs);
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCdibs);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		String dataTypeibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DATA_TYPE"));
		codeMap.put("dataTypeibs", dataTypeibs);
		codeMap.put("dataType", cmcdCodeService.getCodeList("DATA_TYPE"));
		
		String cndCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CND_CD"));
		codeMap.put("cndCdibs", cndCdibs);
		codeMap.put("cndCd", cmcdCodeService.getCodeList("CND_CD"));
		
		 
		return codeMap;
		
	}
	
	@RequestMapping("/commons/damgmt/db/dbmsdatatypemap_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbmsdatatypemap_lst";
	}
	
	/** DBMS타입 변환 리스트 조회 - IBSheet JSON */
	@RequestMapping("/commons/damgmt/db/datatypemapSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaDataTypeMapru> selectList(@ModelAttribute WaaDataTypeMapru search) {
		logger.debug("{}", search);
		List<WaaDataTypeMapru> list = dbmsDataTypeMapService.getList(search);
		
		return new IBSheetListVO<WaaDataTypeMapru>(list, list.size());
	}
	
	/** DBMS타입 변환 등록 - IBSheet JSON */
	@RequestMapping("/commons/damgmt/db/datatypemapReglist.do")
	@ResponseBody
	public IBSResultVO<WaaDataTypeMapru> regList(@RequestBody WaaDataTypeMaprus data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaDataTypeMapru> list = data.get("data");
//		logger.debug("어디서 걸리나");
		int result = dbmsDataTypeMapService.regDataTypeMapList(list);
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
		return new IBSResultVO<WaaDataTypeMapru>(result, resmsg, action);
		
		
	}
	
	@RequestMapping("/commons/damgmt/db/datatypemapDellist.do")
	@ResponseBody
	public IBSResultVO<WaaDataTypeMapru> delList(@RequestBody WaaDataTypeMaprus data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaDataTypeMapru> list = data.get("data");
		
		int result = dbmsDataTypeMapService.delDataTypeMapList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaDataTypeMapru>(result, resmsg, action);
	}
	
	 
	
}
