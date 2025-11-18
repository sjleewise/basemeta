package kr.wise.commons.sysmgmt.menu.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.sysmgmt.menu.service.WaaUsergMenuAuth;
import kr.wise.commons.util.UtilJson;

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
 * 2. FileName  : UsergMenuMapCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 30. 오후 8:51:23
 * </PRE>
 */ 
@Controller
public class UsergMenuMapCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class WaaUsergMenuAuths extends HashMap<String, ArrayList<WaaUsergMenuAuth>> { }
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private UsergMenuMapService usergMenuMapService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 메뉴권한 리스트 조회 폼 */
	@RequestMapping("/commons/sys/menu/usergmenumap_lst.do")
	public String formpage(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/commons/sys/menu/usergmenumap_lst";
	}
	
	/** 사용자그룹에 대한 메뉴리스트 조회 -IBSheet json */
	@RequestMapping("/commons/sys/menu/usergmenumap_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaUsergMenuAuth> selectMenuList(WaaUsergMenuAuth search) throws Exception {
		logger.debug("searchVO : {}", search);
		
		//메뉴의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("MENU");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
				
		search.setLvlList(lvlList);
		
		List<WaaUsergMenuAuth> list = usergMenuMapService.selectMenuList(search);
		
		
		return new IBSheetListVO<WaaUsergMenuAuth>(list, list.size()); 
	}
	
	
	
	/** 사용자그룹에 대한 메뉴리스트 저장... - IBSheet JSON
	 * @throws Exception */
	/** yeonho */
	@RequestMapping("/commons/sys/menu/menuReglist.do")
	@ResponseBody
	public IBSResultVO<MenuManageVO> regSchList(@RequestBody WaaUsergMenuAuths data,  String usergId, Locale locale) throws Exception {
		logger.debug("{}", data);
		logger.debug("usergId : {}", usergId);
		ArrayList<WaaUsergMenuAuth> list = data.get("data");
		int result = usergMenuMapService.menuReglist(list, usergId);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<MenuManageVO>(result, resmsg, action);
	}
		
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		/** 공통코드 */
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//사용자그룹코드
		codeMap.put("usergTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("USERG_TYP_CD")));
		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));
		//메뉴구분코드
		codeMap.put("menuDcd", cmcdCodeService.getCodeList("MENU_DCD"));
		codeMap.put("menuDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MENU_DCD")));

		/** 목록성코드 */
		//사용자그룹명
		codeMap.put("usergroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("usergroup")));
		codeMap.put("usergroup", codeListService.getCodeList("usergroup"));
		

		return codeMap;
	}
}
