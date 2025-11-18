
package kr.wise.commons.damgmt.dmnginfo.web;

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
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoType;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : InfotpCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho(김연호)
 * 6. 작성일   : 2014. 4. 18. 오전 9:30:55
 * </PRE>
 */ 
@Controller("InfotpCtrl")
@RequestMapping("/commons/damgmt/dmnginfo/*")
public class InfotpCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	
	
	static class WaaInfoTypes extends HashMap<String, ArrayList<WaaInfoType>> { }
	
	private Map<String, Object> codeMap;
	
	@Inject
	private InfotpService service;
	
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
	
	@RequestMapping("infotp_lst.do")
	public String formpage(HttpSession session, @RequestParam(value="objId", required=false) String infotpId, Model model) {
		
		logger.debug("objId 조회 {}", infotpId);
		model.addAttribute("infotpId", infotpId);
		return "/commons/damgmt/dmnginfo/infotp_lst";
		
	}
	
	@RequestMapping("infotpSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaInfoType> selectList(@ModelAttribute WaaInfoType search) {
		logger.debug("{}", search);
		List<WaaInfoType> list = service.getList(search);
		
		
		return new IBSheetListVO<WaaInfoType>(list, list.size());
	}
	
	@RequestMapping("infotpReglist.do")
	@ResponseBody
	public IBSResultVO<WaaInfoType> regList(@RequestBody WaaInfoTypes data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaInfoType> list = data.get("data");
		
		int result = service.regInfotpList(list);
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaaInfoType>(result, resmsg, action);
		
		
	}

	@RequestMapping("infotpDellist.do")
	@ResponseBody
	public IBSResultVO<WaaInfoType> delList(@RequestBody WaaInfoTypes data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaInfoType> list = data.get("data");
		
		int result = service.delInfotpList(list);
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else if (result == -2) {
			result = -1;
			resmsg = message.getMessage("ERR.REL.INFOTPMAP", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaInfoType>(result, resmsg, action);
	}
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
		codeMap = new HashMap<String, Object>();
		
		//시스템영역 코드리스트 JSON 
		List<CodeListVo> datatpGroup = codelistService.getCodeList("usergroup"); 
		codeMap.put("usergroup", UtilJson.convertJsonString(datatpGroup));
		codeMap.put("usergp", UtilJson.convertJsonString(codelistService.getCodeListIBS(datatpGroup)));
		
		String dataType = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DATA_TYPE"));
		codeMap.put("dataTypeibs", dataType);
		
		//표준구분 이베이코리아
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

				
		return codeMap;
	}
	
	

}
