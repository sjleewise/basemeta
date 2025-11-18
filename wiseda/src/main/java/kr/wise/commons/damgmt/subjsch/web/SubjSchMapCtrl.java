/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : SubjSchMapCtrl.java
 * 2. Package : kr.wise.commons.damgmt.subjsch.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 5. 23. 오후 4:17:27
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 5. 23. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.subjsch.web;

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
import kr.wise.commons.damgmt.subjsch.service.SubjSchMapService;
import kr.wise.commons.damgmt.subjsch.service.WaaSubjDbSchMap;
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
 * 2. FileName  : SubjSchMapCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.subjsch.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 23. 오후 4:17:27
 * </PRE>
 */
@Controller
public class SubjSchMapCtrl {
Logger logger = LoggerFactory.getLogger(getClass());

	
	
	static class WaaSubjDbSchMaps extends HashMap<String, ArrayList<WaaSubjDbSchMap>> { }
	
	private Map<String, Object> codeMap;
	
	@Inject
	private SubjSchMapService subjSchMapService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 주제영역/스키마 매핑 화면 호출 */
	@RequestMapping("/commons/damgmt/subjsch/subjschmap_lst.do")
	public String formpage() {
		return "/commons/damgmt/subjsch/subjschmap_lst";
	}
	
	/** 주제영역/스키마 매핑 저장 */
	@RequestMapping("/commons/damgmt/subjsch/subjschSaveList.do")
	@ResponseBody
	public IBSResultVO<WaaSubjDbSchMap> regList(@RequestBody WaaSubjDbSchMaps data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaSubjDbSchMap> list = data.get("data");

		int result = subjSchMapService.regSubjSchMapList(list);
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaSubjDbSchMap>(result, resmsg, action);
	}
	
	/** 주제영역/스키마 조회 */
	@RequestMapping("/commons/damgmt/subjsch/subjschList.do")
	@ResponseBody
	public IBSheetListVO<WaaSubjDbSchMap> selectList(@ModelAttribute WaaSubjDbSchMap search) {
		logger.debug("{}", search);
		List<WaaSubjDbSchMap> list = subjSchMapService.getList(search);
		
		
		return new IBSheetListVO<WaaSubjDbSchMap>(list, list.size());
	}
	
	/** 주제영역/스키마 삭제 */
	@RequestMapping("/commons/damgmt/subjsch/subjschDellist.do")
	@ResponseBody
	public IBSResultVO<WaaSubjDbSchMap> delList(@RequestBody WaaSubjDbSchMaps data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaSubjDbSchMap> list = data.get("data");

		int result = subjSchMapService.delSubjSchMapList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaSubjDbSchMap>(result, resmsg, action);
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
		codeMap = new HashMap<String, Object>();
		
		//공토코드 
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		//목록성 코드
		List<CodeListVo> dbSch = codelistService.getCodeList("dbSch"); 
		codeMap.put("dbSch", UtilJson.convertJsonString(dbSch));
		codeMap.put("dbSchibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(dbSch)));
		
		//개발DB 스키마조회 
		List<CodeListVo> devDbSch = codelistService.getCodeList("devDbSch"); 
        codeMap.put("devDbSch", UtilJson.convertJsonString(devDbSch));
        codeMap.put("devDbSchibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(devDbSch))); 

		List<CodeListVo> subj = codelistService.getCodeList("subj"); 
		codeMap.put("subj", UtilJson.convertJsonString(subj));
		codeMap.put("subjibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(subj)));
		
		//주제영역 1레벨
		List<CodeListVo> subjLvl1 = codelistService.getCodeList("subjLvl1");  
        codeMap.put("subjLvl1", UtilJson.convertJsonString(subjLvl1));
        codeMap.put("subjLvl1ibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(subjLvl1)));

        //주제영역 2레벨
        List<CodeListVo> subjLvl2 = codelistService.getCodeList("subjLvl2");  
        codeMap.put("subjLvl2", UtilJson.convertJsonString(subjLvl2));
        codeMap.put("subjLvl2ibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(subjLvl2)));
        
        //주제영역 3레벨
        List<CodeListVo> subjLvl3 = codelistService.getCodeList("subjLvl3");  
        codeMap.put("subjLvl3", UtilJson.convertJsonString(subjLvl3));
        codeMap.put("subjLvl3ibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(subjLvl3)));

		return codeMap;
	}
}
