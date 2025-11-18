
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
import kr.wise.commons.damgmt.dmnginfo.service.DmngService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmng;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmngCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho(김연호)
 * 6. 작성일   : 2014. 4. 18. 오전 9:31:18
 * </PRE>
 */ 
@Controller("DmngCtrl")
@RequestMapping("/commons/damgmt/dmnginfo/*")
public class DmngCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	
	
	static class WaaDmngs extends HashMap<String, ArrayList<WaaDmng>> { }
	
	private Map<String, Object> codeMap;
	
	@Inject
	private DmngService service;
	
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
	
	@RequestMapping("dmng_lst.do")
	public String formpage(HttpSession session, @RequestParam(value="objId", required=false) String dmngId, Model model) throws Exception {
		
		logger.debug("objId 조회 {}", dmngId);
		model.addAttribute("dmngId", dmngId);
			
		return "/commons/damgmt/dmnginfo/dmng_lst";
		
	}
	
	/** 도메인그룹 팝업 화면 */
	@RequestMapping("popup/dmnglst_pop.do")
	public String godmngSearchPop(@ModelAttribute("search") WaaDmng search, Model model, @RequestParam(value="row") String row, Locale locale) {
		logger.debug("파람값은 : {}", row);		 
		model.addAttribute("row", row);
		return "/commons/damgmt/dmnginfo/popup/dmnglst_pop";
	}

	
	@RequestMapping("dmngSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaDmng> selectList(@ModelAttribute WaaDmng search) throws Exception {
		logger.debug("{}", search);
		
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		List<Integer> lvlList = new ArrayList<Integer>();
		
		for (int i = 1; i <= data.getBscLvl(); i++) {
			lvlList.add(i);
		}
		
		search.setLvlList(lvlList);
		
		List<WaaDmng> list = service.getList(search);
		
		return new IBSheetListVO<WaaDmng>(list, list.size());
	}
	
	@RequestMapping("dmngReglist.do")
	@ResponseBody
	public IBSResultVO<WaaDmng> regList(@RequestBody WaaDmngs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDmng> list = data.get("data");
		//도메인그룹의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("DMNG");
				
		int result = service.regDmngList(list, bscLvl); 
		String resmsg;
				
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -5) {
			result = -1;
			resmsg = message.getMessage("ERR.BSCLVL", new Object[]{"도메인그룹",bscLvl.getBscLvl()}, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDmng>(result, resmsg, action);
		
		
	}

	@RequestMapping("dmngDellist.do")
	@ResponseBody
	public IBSResultVO<WaaDmng> delList(@RequestBody WaaDmngs data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaDmng> list = data.get("data");
		
		int result = service.delDmngList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else if (result == -2) {
			result = -1;
			//해당 도메인그룹에 인포타입이 존재합니다
			resmsg = message.getMessage("ERR.REL.INFOTPMAP", null, locale);
		} else if (result == -3) {
			result = -1;
			//하위 도메인그룹을 먼저삭제하세요.
			resmsg = message.getMessage("ERR.REL.CHDDMN", null, locale);	  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaDmng>(result, resmsg, action);
	}
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
		codeMap = new HashMap<String, Object>();
		
		//시스템영역 코드리스트 JSON 
//		String sysarea = codelistService.getCodeListJson("sysarea");
		List<CodeListVo> usergroup = codelistService.getCodeList("usergroup"); 
		codeMap.put("usergroup", UtilJson.convertJsonString(usergroup));
		codeMap.put("usergp", UtilJson.convertJsonString(codelistService.getCodeListIBS(usergroup)));
		
		//표준구분 이베이코리아
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

		
		return codeMap;
	}
	
	

}
