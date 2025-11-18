
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
import kr.wise.commons.damgmt.gov.service.GovStndMapService;
import kr.wise.commons.damgmt.gov.service.WaaGovDmnMap;
import kr.wise.commons.damgmt.gov.service.WaaGovSditmMap;
import kr.wise.commons.damgmt.gov.service.WaaGovStwdMap;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.WamSditm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("govStndMapCtrl")
@RequestMapping("/commons/damgmt/gov/*")
public class GovStndMapCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	
	
	static class WaaGovStwdMaps extends HashMap<String, ArrayList<WaaGovStwdMap>> { }
	static class WaaGovSditmMaps extends HashMap<String, ArrayList<WaaGovSditmMap>> { }
	static class WaaGovDmnMaps extends HashMap<String, ArrayList<WaaGovDmnMap>> { }

	private Map<String, Object> codeMap;
	
	@Inject
	private GovStndMapService service;
	
	@Inject
	private CodeListService codeListService;
	
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
		
		List<CodeListVo> infotp = codeListService.getCodeList("infotp");

		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng")));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(infotp)));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp")));
		codeMap.put("infotpId", infotp);
		
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		
		String cdValIvwCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD"));
		String cdValTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD"));

		codeMap.put("cdValIvwCdibs", cdValIvwCd);
		codeMap.put("cdValTypCdibs", cdValTypCd);
		
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

		return codeMap;
	}
	
	//공통표준 단어
	@RequestMapping("gov_stwd_map_lst.do")
	public String formpage(Model model) throws Exception {
		
		return "/commons/damgmt/gov/gov_stwd_map_lst";
	}

	@RequestMapping("ajaxgrid/gov_stwd_map_dtl.do")
	public String selectStwdMapDetail(String govStwdId, String saction, ModelMap model) {
		logger.debug(" {}", govStwdId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(govStwdId)) {

			WaaGovStwdMap result = service.selectStwdMapDetail(govStwdId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/gov/gov_stwd_map_dtl";
	}

	@RequestMapping("getGovStwdMapList.do")
	@ResponseBody
	public IBSheetListVO<WaaGovStwdMap> selectGovStwdMapList(@ModelAttribute WaaGovStwdMap search) {
		logger.debug("searchVO : {}", search);
		List<WaaGovStwdMap> list = service.selectGovStwdMapList(search);
		
		return new IBSheetListVO<WaaGovStwdMap>(list, list.size());
		
	}
	
	/** 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("saveGovStwdMapRow.do")
    @ResponseBody
    public IBSResultVO<WaaGovStwdMap> saveGovStwdMapRow(WaaGovStwdMap saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = service.saveGovStwdMapRow(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WaaGovStwdMap>(saveVO, result, resmsg, action);
    }
	
    /** 리스트 삭제 - IBSheet JSON */
	@RequestMapping("deleteGovStwdMapList.do")
	@ResponseBody
	public IBSResultVO<WaaGovStwdMap> deleteGovStwdMapList(@RequestBody WaaGovStwdMaps data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaGovStwdMap> list = data.get("data");

		int result = service.deleteGovStwdMapList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaGovStwdMap>(result, resmsg, action);
	}
	
	/** 유사어 리스트 등록(엑셀업로드용) - IBSheet JSON */
	@RequestMapping("saveGovStwdMapList.do")
	@ResponseBody
	public IBSResultVO<WaaGovStwdMap> saveGovStwdMapList(@RequestBody WaaGovStwdMaps data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaGovStwdMap> list = data.get("data");

		int result = service.saveGovStwdMapList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaGovStwdMap>(result, resmsg, action);
	}
	
	@RequestMapping("ajaxgrid/govstwdmapchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaGovStwdMap> selectGovStwdMapChangeList(String govStwdId) throws Exception {
		List<WaaGovStwdMap> list = service.selectGovStwdMapChangeList(govStwdId);
		return new IBSheetListVO<WaaGovStwdMap>(list, list.size());
	}
	
	@RequestMapping("popup/selectStwdPop.do")
	public String goStwdIdPop() {
				
		return "/commons/damgmt/gov/popup/selectstwd_pop";
	}
	
	//공통표준 도메인 매핑
	@RequestMapping("gov_dmn_map_lst.do")
	public String govdmnpage(Model model) throws Exception {
		
		return "/commons/damgmt/gov/gov_dmn_map_lst";
	}

	@RequestMapping("ajaxgrid/gov_dmn_map_dtl.do")
	public String selectDmnMapDetail(String govDmnId, String saction, ModelMap model) {
		logger.debug(" {}", govDmnId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(govDmnId)) {

			WaaGovDmnMap result = service.selectDmnMapDetail(govDmnId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/gov/gov_dmn_map_dtl";
	}

	@RequestMapping("getGovDmnMapList.do")
	@ResponseBody
	public IBSheetListVO<WaaGovDmnMap> selectGovDmnMapList(@ModelAttribute WaaGovDmnMap search) {
		logger.debug("searchVO : {}", search);
		List<WaaGovDmnMap> list = service.selectGovDmnMapList(search);
		
		return new IBSheetListVO<WaaGovDmnMap>(list, list.size());
		
	}
	
	/** 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("saveGovDmnMapRow.do")
    @ResponseBody
    public IBSResultVO<WaaGovDmnMap> saveGovDmnMapRow(WaaGovDmnMap saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = service.saveGovDmnMapRow(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WaaGovDmnMap>(saveVO, result, resmsg, action);
    }
	
    /** 리스트 삭제 - IBSheet JSON */
	@RequestMapping("deleteGovDmnMapList.do")
	@ResponseBody
	public IBSResultVO<WaaGovDmnMap> deleteGovDmnMapList(@RequestBody WaaGovDmnMaps data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaGovDmnMap> list = data.get("data");

		int result = service.deleteGovDmnMapList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaGovDmnMap>(result, resmsg, action);
	}
	
	/** 유사어 리스트 등록(엑셀업로드용) - IBSheet JSON */
	@RequestMapping("saveGovDmnMapList.do")
	@ResponseBody
	public IBSResultVO<WaaGovDmnMap> saveGovDmnMapList(@RequestBody WaaGovDmnMaps data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaGovDmnMap> list = data.get("data");

		int result = service.saveGovDmnMapList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaGovDmnMap>(result, resmsg, action);
	}
	
	@RequestMapping("ajaxgrid/govdmnmapchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaGovDmnMap> selectGovDmnMapChangeList(String govDmnId) throws Exception {
		List<WaaGovDmnMap> list = service.selectGovDmnMapChangeList(govDmnId);
		return new IBSheetListVO<WaaGovDmnMap>(list, list.size());
	}
	
	
	@RequestMapping("popup/selectDmnPop.do")
	public String goDmnIdPop(Model model) throws Exception {
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		return "/commons/damgmt/gov/popup/selectdmn_pop";
	}
	
	//공통표준 용어 매핑
	@RequestMapping("gov_sditm_map_lst.do")
	public String govsditmpage(Model model) throws Exception {
		
		return "/commons/damgmt/gov/gov_sditm_map_lst";
	}

	@RequestMapping("ajaxgrid/gov_sditm_map_dtl.do")
	public String selectSditmMapDetail(String govSditmId, String saction, ModelMap model) {
		logger.debug("govSditmId: {}", govSditmId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(govSditmId)) {

			WaaGovSditmMap result = service.selectSditmMapDetail(govSditmId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/gov/gov_sditm_map_dtl";
	}

	@RequestMapping("getGovSditmMapList.do")
	@ResponseBody
	public IBSheetListVO<WaaGovSditmMap> selectGovSditmMapList(@ModelAttribute WaaGovSditmMap search) {
		logger.debug("searchVO : {}", search);
		List<WaaGovSditmMap> list = service.selectGovSditmMapList(search);
		
		return new IBSheetListVO<WaaGovSditmMap>(list, list.size());
		
	}
	
	/** 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("saveGovSditmMapRow.do")
    @ResponseBody
    public IBSResultVO<WaaGovSditmMap> saveGovSditmMapRow(WaaGovSditmMap saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = service.saveGovSditmMapRow(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WaaGovSditmMap>(saveVO, result, resmsg, action);
    }
	
    /** 리스트 삭제 - IBSheet JSON */
	@RequestMapping("deleteGovSditmMapList.do")
	@ResponseBody
	public IBSResultVO<WaaGovSditmMap> deleteGovSditmMapList(@RequestBody WaaGovSditmMaps data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaGovSditmMap> list = data.get("data");

		int result = service.deleteGovSditmMapList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaGovSditmMap>(result, resmsg, action);
	}
	
	/** 유사어 리스트 등록(엑셀업로드용) - IBSheet JSON */
	@RequestMapping("saveGovSditmMapList.do")
	@ResponseBody
	public IBSResultVO<WaaGovSditmMap> saveGovSditmMapList(@RequestBody WaaGovSditmMaps data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaGovSditmMap> list = data.get("data");

		int result = service.saveGovSditmMapList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaGovSditmMap>(result, resmsg, action);
	}
	
	@RequestMapping("ajaxgrid/govsditmmapchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaGovSditmMap> selectGovSditmMapChangeList(String govSditmId) throws Exception {
		List<WaaGovSditmMap> list = service.selectGovSditmMapChangeList(govSditmId);
		return new IBSheetListVO<WaaGovSditmMap>(list, list.size());
	}
	
	@RequestMapping("popup/selectSditmPop.do")
	public String goSditmPop(@ModelAttribute("search") WamSditm searchvo, ModelMap model) {
		logger.debug("##search:{}", searchvo);

		model.addAttribute("codeMap",getcodeMap());		
		
		return "/commons/damgmt/gov/popup/selectsditm_pop";
	}
}
