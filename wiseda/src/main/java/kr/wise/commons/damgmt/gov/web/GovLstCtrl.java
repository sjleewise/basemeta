
package kr.wise.commons.damgmt.gov.web;

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
import kr.wise.commons.damgmt.gov.service.GovLstMapService;
import kr.wise.commons.damgmt.gov.service.WaaGovCdVal;
import kr.wise.commons.damgmt.gov.service.WaaGovDmn;
import kr.wise.commons.damgmt.gov.service.WaaGovSditm;
import kr.wise.commons.damgmt.gov.service.WaaGovStwd;
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
@Controller("govLstCtrl")
@RequestMapping("/commons/damgmt/gov/*")
public class GovLstCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	
	
	static class WaaGovStwds extends HashMap<String, ArrayList<WaaGovStwd>> { }
	static class WaaGovSditms extends HashMap<String, ArrayList<WaaGovSditm>> { }

	static class WaaGovDmns extends HashMap<String, ArrayList<WaaGovDmn>> { }
	static class WaaGovCdVals extends HashMap<String, ArrayList<WaaGovCdVal>> { }

	private Map<String, Object> codeMap;
	
	@Inject
	private GovLstMapService service;
	
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
//		List<CodeListVo> dmng = codelistService.getCodeList("dmng"); 
//		List<CodeListVo> infotp = codelistService.getCodeList("infotp"); 
//
//		List<CodeListVo> dmnginfotp = codelistService.getCodeList("dmnginfotp");
//		codeMap.put("dmng", UtilJson.convertJsonString(codelistService.getCodeList("dmng")));
//		codeMap.put("infotp", UtilJson.convertJsonString(infotp));
//		codeMap.put("dmngibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("dmngLowDgr")));
//		codeMap.put("infotpibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(infotp)));
//		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codelistService.getCodeListIBS(dmnginfotp)));
//
//		//표준구분 이베이코리아
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
//
//		//전체경로로 표시
//		codeMap.put("dmngibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("dmngLowDgrStndAsrtFullPath")));
//		codeMap.put("infotpibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("infotpStndAsrtFullPath")));
				
		return codeMap;
	}
	
	
//	공통표준단어관리 //
	
	@RequestMapping("gov_stwd_lst.do")
	public String formpage(Model model) throws Exception {
		
		return "/commons/damgmt/gov/gov_stwd_lst";
	}
	
	@RequestMapping("govStwdLstSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaGovStwd> selectList(@ModelAttribute WaaGovStwd search) {
		logger.debug("{}", search);
		List<WaaGovStwd> list = service.getList(search);
		
		
		return new IBSheetListVO<WaaGovStwd>(list, list.size());
	}
//	
	@RequestMapping("govStwdLstReglist.do")
	@ResponseBody
	public IBSResultVO<WaaGovStwd> regList(@RequestBody WaaGovStwds data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaGovStwd> list = data.get("data");

		int result = service.regGovStwdLstList(list);
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaGovStwd>(result, resmsg, action);
	}
//
	@RequestMapping("govStwdLstDellist.do")
	@ResponseBody
	public IBSResultVO<WaaGovStwd> delList(@RequestBody WaaGovStwds data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaGovStwd> list = data.get("data");

		int result = service.delgovStwdLstList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaGovStwd>(result, resmsg, action);
	}

//	공통표준단어관리 //
	
	@RequestMapping("gov_sditm_lst.do")
	public String sditmFormpage(Model model) throws Exception {
		
		
		return "/commons/damgmt/gov/gov_sditm_lst";
	}
	
	@RequestMapping("govSditmLstSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaGovSditm> sditmSelectList(@ModelAttribute WaaGovSditm search) {
		logger.debug("{}", search);
		List<WaaGovSditm> list = service.sditmgetList(search);
		
		
		return new IBSheetListVO<WaaGovSditm>(list, list.size());
	}
//	
	@RequestMapping("govSditmLstReglist.do")
	@ResponseBody
	public IBSResultVO<WaaGovStwd> sditmregList(@RequestBody WaaGovSditms data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaGovSditm> list = data.get("data");

		int result = service.regGovSditmLstList(list);
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaGovStwd>(result, resmsg, action);
	}

	@RequestMapping("govSditmLstDellist.do")
	@ResponseBody
	public IBSResultVO<WaaGovSditm> sditmdelList(@RequestBody WaaGovSditms data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaGovSditm> list = data.get("data");

		int result = service.delgovSditmLstList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaGovSditm>(result, resmsg, action);
	}
	
//	==================================공통도메인관리 =================================//
	
	@RequestMapping("gov_dmn_lst.do")
	public String dmnFormpage(Model model) throws Exception {
		
		
		return "/commons/damgmt/gov/gov_dmn_lst";
	}
	
	// 공통표준 도메인 리스트 조회
		@RequestMapping("govDmnLstSelectlist.do")
		@ResponseBody
		public IBSheetListVO<WaaGovDmn> sditmSelectList(@ModelAttribute WaaGovDmn search) {
			logger.debug("{}", search);
			List<WaaGovDmn> list = service.dmngetList(search);
			
			
			return new IBSheetListVO<WaaGovDmn>(list, list.size());
		}
	
	// 공통표준 도메인 리스트 저장
		@RequestMapping("govDmnLstReglist.do")
		@ResponseBody
		public IBSResultVO<WaaGovDmn> dmnregList(@RequestBody WaaGovDmns data, Locale locale) throws Exception {
			
			logger.debug("{}", data);
			ArrayList<WaaGovDmn> list = data.get("data");

			int result = service.regGovdmnLstList(list);
			String resmsg;
			
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}		
			String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
			return new IBSResultVO<WaaGovDmn>(result, resmsg, action);
		}
		
		//공통표준 도메인 리스트 삭제
		@RequestMapping("govDmnLstDellist.do")
		@ResponseBody
		public IBSResultVO<WaaGovDmn> dmndelList(@RequestBody WaaGovDmns data, Locale locale) {
			
			logger.debug("{}", data);
			ArrayList<WaaGovDmn> list = data.get("data");

			int result = service.delgovdmnLstList(list);
			
			String resmsg ;
			if (result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.DEL", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.DEL", null, locale);
			}
			
			String action = WiseMetaConfig.IBSAction.DEL.getAction();
			return new IBSResultVO<WaaGovDmn>(result, resmsg, action);
		}
		
		
//		==================================공통유효값코드관리=================================//
		
		@RequestMapping("gov_cdval_lst.do")
		public String cdValFormpage(Model model) throws Exception {
			
			return "/commons/damgmt/gov/gov_cdval_lst";
		}
		
		// 공통표준 유효값 조회
		@RequestMapping("govCdValLstSelectlist.do")
		@ResponseBody
		public IBSheetListVO<WaaGovCdVal> cdValSelectList(@ModelAttribute WaaGovCdVal search) {
			logger.debug("{}", search);
			List<WaaGovCdVal> list = service.cdValgetList(search);
			
			
			return new IBSheetListVO<WaaGovCdVal>(list, list.size());
		}
		
		// 공통표준 도메인 리스트 저장
		@RequestMapping("govCdValLstReglist.do")
		@ResponseBody
		public IBSResultVO<WaaGovCdVal> cdValregList(@RequestBody WaaGovCdVals data, Locale locale) throws Exception {
			
			logger.debug("{}", data);
			ArrayList<WaaGovCdVal> list = data.get("data");

			int result = service.regGovCdValLstList(list);
			String resmsg;
			
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}		
			String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
			return new IBSResultVO<WaaGovCdVal>(result, resmsg, action);
		}
		
		//공통표준 도메인 리스트 삭제
				@RequestMapping("govCdValLstDellist.do")
				@ResponseBody
				public IBSResultVO<WaaGovDmn> cdValdelList(@RequestBody WaaGovCdVals data, Locale locale) {
					
					logger.debug("{}", data);
					ArrayList<WaaGovCdVal> list = data.get("data");

					int result = service.delgovCdValLstList(list);
					
					String resmsg ;
					if (result > 0) {
						result = 0;
						resmsg = message.getMessage("MSG.DEL", null, locale);
					} else {
						result = -1;
						resmsg = message.getMessage("ERR.DEL", null, locale);
					}
					
					String action = WiseMetaConfig.IBSAction.DEL.getAction();
					return new IBSResultVO<WaaGovDmn>(result, resmsg, action);
				}

}
