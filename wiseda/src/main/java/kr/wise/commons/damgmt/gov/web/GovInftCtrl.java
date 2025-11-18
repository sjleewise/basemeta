
package kr.wise.commons.damgmt.gov.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.gov.service.GovInftService;
import kr.wise.commons.damgmt.gov.service.WaaGovInft;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmngInfotpMapCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho(김연호)
 * 6. 작성일   : 2014. 4. 18. 오전 9:31:03
 * </PRE>
 */ 
@Controller("govInftCtrl")
@RequestMapping("/commons/damgmt/gov/*")
public class GovInftCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;
	
	static class WaaGovInfts extends HashMap<String, ArrayList<WaaGovInft>> { }
	
	@Inject
	private GovInftService service;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
		codeMap = new HashMap<String, Object>();
		
		//시스템영역 코드리스트 JSON 
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		codeMap.put("sysarea", sysarea);
		
//		codeMap.put("bisibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIS")));

		return codeMap;
	}
	
	@RequestMapping("subjbis_map_lst.do")
	public String subjbisMapPage(Model model) throws Exception {
		return "/commons/damgmt/gov/subjbis_map_lst";
	}
	
	@RequestMapping("getSubjBisMapList.do")
	@ResponseBody
	public IBSheetListVO<WaaGovInft> getSubjBisMapList(@ModelAttribute WaaGovInft search) {
		logger.debug("{}", search);
		List<WaaGovInft> list = service.getSubjBisMapList(search);
		
		return new IBSheetListVO<WaaGovInft>(list, list.size());
	}
	
	@RequestMapping("regSubjBisMap.do")
	@ResponseBody
	public IBSResultVO<WaaGovInft> regSubjBisMap(@RequestBody WaaGovInfts data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaGovInft> list = data.get("data");

		int result = service.regSubjBisMap(list);
		
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaaGovInft>(result, resmsg, action);
	}
	
	
	
	@RequestMapping("govinft_lst.do")
	public String formpage(Model model) throws Exception {
		
		return "/commons/damgmt/gov/govinft_lst";
	}
	
	@RequestMapping("govInftTotList.do")
	@ResponseBody
	public IBSheetListVO<WaaGovInft> selectTotList(@ModelAttribute WaaGovInft search) {
		logger.debug("{}", search);
		List<WaaGovInft> list = service.getTotList(search);
		
		return new IBSheetListVO<WaaGovInft>(list, list.size());
	}
	
	@RequestMapping("govInftCsvDown.do")
	public void govInftCsvDown(HttpServletResponse response, @ModelAttribute WaaGovInft search) throws Exception {
		logger.debug("{}", search);
		String filepath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.GOV_FILE_PATH, null, Locale.getDefault()); 
		
		service.govInftCsvDown(search, filepath, response);
	}
	
	@RequestMapping("govInftMapCsvDown.do")
	public void govInftMapCsvDown(HttpServletResponse response, @ModelAttribute WaaGovInft search) throws Exception {
		logger.debug("{}", search);
		String filepath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.GOV_FILE_PATH, null, Locale.getDefault());
		
		service.govInftMapCsvDown(search, filepath, response);
	}
}
