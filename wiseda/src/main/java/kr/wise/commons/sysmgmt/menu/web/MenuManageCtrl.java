package kr.wise.commons.sysmgmt.menu.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.menu.service.MenuManageService;
import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import kr.wise.commons.sysmgmt.menu.service.MenuStatVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;

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
 * 2. FileName  : MenuManageCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.web
 * 4. Comment  : 메뉴 관리 컨트롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 24. 오후 2:05:16
 * </PRE>
 */ 
@Controller
@RequestMapping("/commons/sys/menu")

public class MenuManageCtrl {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class MenuManageVOs extends HashMap<String, ArrayList<MenuManageVO>> { }
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MenuManageService menuManageService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 메뉴리스트 조회 폼 */
	@RequestMapping("/selectMenu.do")
	public String selectMenu(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/commons/sys/menu/menu_lst";
	}
	
	/** 메뉴리스트 조회 -IBSheet json */
	@RequestMapping("/ajaxgrid/selectMenuList.do")
	@ResponseBody
	public IBSheetListVO<MenuManageVO> selectMenuList(@ModelAttribute("searchVO") MenuManageVO search) throws Exception {
		
		//메뉴의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("MENU");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
				
		search.setLvlList(lvlList);
		
		List<MenuManageVO> list = menuManageService.selectMenuList(search);
		
		return new IBSheetListVO<MenuManageVO>(list, list.size()); 
	}
	
	/** 메뉴 저장 단건 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/saveMenuRow.do")
    @ResponseBody
    public IBSResultVO<MenuManageVO> saveMenuRow(MenuManageVO saveVO, String saction, Locale locale) throws Exception {
    	
    	log.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = menuManageService.saveMenu(saveVO, saction);

    	String resmsg ;

    	//메뉴의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("MENU");
		

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -5) {
			result = -1;
			resmsg = message.getMessage("ERR.BSCLVL", new Object[]{"메뉴",bscLvl.getBscLvl()}, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<MenuManageVO>(saveVO, result, resmsg, action);
    }

	
	/** 메뉴 상세 정보 조회 */
	@RequestMapping("/ajaxgrid/selectMenuDetail.do")
	public String selectMenuDetail(String menuId, String saction, ModelMap model) {
		log.debug(" {}", menuId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(menuId)) {

			MenuManageVO result = menuManageService.selectMenuDetail(menuId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		
//		return "/commons/sys/menu/menuDetail";
		return "/commons/sys/menu/menu_dtl";
	}
	
	/** 엑셀로 메뉴리스트 (여러건)등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/saveMenus.do")
	@ResponseBody
	public IBSResultVO<MenuManageVO> regList(@RequestBody MenuManageVOs data, Locale locale) throws Exception {
		log.debug("{}", data);
		ArrayList<MenuManageVO> list = data.get("data");
		int result = menuManageService.regMenu(list);

		String resmsg;

		//메뉴의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("MENU");
		

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -5) {
			result = -1;
			resmsg = message.getMessage("ERR.BSCLVL", new Object[]{"메뉴",bscLvl.getBscLvl()}, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<MenuManageVO>(result, resmsg, action);
	}
	
	
    /** 메뉴 삭제 for IBSheet
     * @throws Exception */
    @RequestMapping("/ajaxgrid/deleteMenu.do")
    @ResponseBody
    public IBSResultVO<MenuManageVO> deleteMenu(@RequestBody MenuManageVOs data, Locale locale) throws Exception {
    	
    	log.debug("menuNo:{}", data);
    	ArrayList<MenuManageVO> list = data.get("data");
    	int result = menuManageService.deleteMenu(list);
    	
    	
    	String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<MenuManageVO>(result, resmsg, action);
    }
   
    
    
	/** 메뉴접속통계 폼 */
	@RequestMapping("/selectStatMenu.do")
	public String selectStatMenu() {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/commons/sys/menu/menuStatList";
	}
	
	
	/** 메뉴접속통계 */
	@RequestMapping("/ajaxgrid/selectMenuStatTot.do")
	@ResponseBody
	public IBSheetListVO<MenuStatVO> selectConnStatTot(@ModelAttribute("searchVO") MenuStatVO searchVO) {
		log.debug("MenuStatVO : {}", searchVO);
		
		List<MenuStatVO> resultlist = menuManageService.selectMenuStatTot(searchVO);
		
		return new IBSheetListVO<MenuStatVO>(resultlist, resultlist.size()); 
	}

	/**사용자별 메뉴접속통계 */
	@RequestMapping("/ajaxgrid/selectMenuStat.do")
	@ResponseBody
	public IBSheetListVO<MenuStatVO> selectMenuStat(@ModelAttribute("searchVO") MenuStatVO searchVO) {
		log.debug("MenuStatVO : {}", searchVO);
		
		List<MenuStatVO> resultlist = menuManageService.selectMenuStat(searchVO);
		
		return new IBSheetListVO<MenuStatVO>(resultlist, resultlist.size());
	}
	
	/**상위메뉴ID 검색을 위한 메뉴ID조회 팝업 호출 */
	@RequestMapping("/selectMenuPopList.do")
	public String selectMenuPopList() {
				
		return "/commons/sys/menu/popup/menu_pop";
	}

	/**파일명 검색을 위한 파일명검색 팝업 호출 */
	@RequestMapping("/selectFilePopList.do")
	public String selectFilePopList() {
				
		return "/commons/sys/menu/popup/file_pop";
	}
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String usergrouplnm2 = UtilJson.convertJsonString(codelistService.getCodeList("userglnm"));
//		String usergrouplnm1 = UtilJson.convertJsonString(codelistService.getCodeListIBS("userglnm"));
		String usergTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("USERG_TYP_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

//		codeMap.put("userglnm", usergrouplnm2);
//		codeMap.put("userglnmibs", usergrouplnm1);
		//공통코드 - IBSheet Combo Code용
		codeMap.put("usergTypCdibs", usergTypCd);
		codeMap.put("regTypCdibs", regTypCd);

		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));

		codeMap.put("menuDcd", cmcdCodeService.getCodeList("MENU_DCD"));
		codeMap.put("menuDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MENU_DCD")));

		return codeMap;
	}
	
	/**상위메뉴ID 검색을 위한 메뉴ID조회 팝업 호출 */
	@RequestMapping("/selectMenuPopHelp.do")
	public String selectMenuPopHelpList(@ModelAttribute("search") CommonVo searchvo) {
		
		return "/commons/sys/menu/popup/menu_popHelp";
	}
}
