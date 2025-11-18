
package kr.wise.commons.damgmt.dmnginfo.web;

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
import kr.wise.commons.damgmt.dmnginfo.service.DmngInfotpMapService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMap;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
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
@Controller("DmngInfotpMapCtrl")
@RequestMapping("/commons/damgmt/dmnginfo/*")
public class DmngInfotpMapCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	
	
	static class WaaDmngInfotpMaps extends HashMap<String, ArrayList<WaaDmngInfotpMap>> { }
	
	private Map<String, Object> codeMap;
	
	@Inject
	private DmngInfotpMapService service;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
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
		List<CodeListVo> dmng = codelistService.getCodeList("dmng"); 
		List<CodeListVo> infotp = codelistService.getCodeList("infotp"); 

		List<CodeListVo> dmnginfotp = codelistService.getCodeList("dmnginfotp");
		codeMap.put("dmng", UtilJson.convertJsonString(codelistService.getCodeList("dmng")));
		codeMap.put("infotp", UtilJson.convertJsonString(infotp));
//		codeMap.put("dmngibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("dmngLowDgr")));
//		codeMap.put("infotpibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(infotp)));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codelistService.getCodeListIBS(dmnginfotp)));

		//표준구분 이베이코리아
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

		//전체경로로 표시
		codeMap.put("dmngibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("dmngLowDgrStndAsrtFullPath")));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("infotpStndAsrtFullPath")));
				
		return codeMap;
	}
	
	@RequestMapping("dmngInfotpMap_lst.do")
	public String formpage(Model model) throws Exception {
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		
		return "/commons/damgmt/dmnginfo/dmngInfotpMap_lst";
	}
	
	@RequestMapping("dmngInfotpMapSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaDmngInfotpMap> selectList(@ModelAttribute WaaDmngInfotpMap search) {
		logger.debug("{}", search);
		List<WaaDmngInfotpMap> list = service.getList(search);
		
		
		return new IBSheetListVO<WaaDmngInfotpMap>(list, list.size());
	}
	
	@RequestMapping("dmngInfotpMapReglist.do")
	@ResponseBody
	public IBSResultVO<WaaDmngInfotpMap> regList(@RequestBody WaaDmngInfotpMaps data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaDmngInfotpMap> list = data.get("data");

		int result = service.regDmngInfotpMapList(list);
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDmngInfotpMap>(result, resmsg, action);
	}

	@RequestMapping("dmngInfotpMapDellist.do")
	@ResponseBody
	public IBSResultVO<WaaDmngInfotpMap> delList(@RequestBody WaaDmngInfotpMaps data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaDmngInfotpMap> list = data.get("data");

		int result = service.deldmngInfotpMapList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else if (result == -2) {
			result = -1;
			//선택한 도메인그룹 인포타입 매핑정보를 사용하는 도메인이 존재합니다.<br>도메인을 먼저 삭제하세요.
			resmsg = message.getMessage("ERR.REL.DMN", null, locale);
		} else if (result == -3) {
			result = -1;
			//선택한 도메인그룹 인포타입 매핑정보를 사용하는 표준용어가 존재합니다.<br>표준용어와 도메인을 먼저 삭제하세요.
			resmsg = message.getMessage("ERR.REL.SDITM", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaDmngInfotpMap>(result, resmsg, action);
	}

	

}
