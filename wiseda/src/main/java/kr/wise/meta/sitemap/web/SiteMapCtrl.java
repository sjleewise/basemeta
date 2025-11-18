package kr.wise.meta.sitemap.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.HTMLInputFilter;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.menu.service.MenuManageService;
import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilString;


import kr.wise.commons.user.service.*;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.sitemap.service.SiteMapService;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.portal.totalsearch.service.TotalSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteMapCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
	@Inject
	private UserService userService;
	
	@Inject
	private SiteMapService siteMapService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MenuManageService menuManageService;
	
	@Inject
	private MessageSource message;

	
	  /** 사이트맵 화면 조회 */
		/** 20150701 이상익 */
	    @RequestMapping("/meta/sitemap/site_map.do")
		public String LoadSiteMap(@ModelAttribute("searchVO") MenuManageVO search,Model model,HttpServletRequest request) throws Exception {
	    	logger.debug("sitemap : {}","");

	    	String url = request.getServletPath();
	    	
//	    	System.out.println("현재 url : " +  url);
	    	
	    	//메뉴의 기본정보레벨 값을 불러온다.
			WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("MENU");
					
			List<Integer> lvlList = new ArrayList<Integer>();
			for (int i=0; i<bscLvl.getBscLvl(); i++){
				lvlList.add(i);
			}
			
        
			
	    	//로그인 유저의 정보를 화면에 담아 리턴한다. USERG_TYP_CD의 값이 AD(관리자)인 경우만 해당...
	    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
	    	String userid = user.getUniqId();
 
         	    	

	    	WaaUser tmpUser = userService.getUserInfo(userid);
	    	if(tmpUser != null) {
	    		model.addAttribute("usergTypCd", tmpUser.getUsergTypCd());
	    	}
	    	
	    	search.setLvlList(lvlList);
	        search.setUserId(userid);
	        search.setUserGroupId(tmpUser.getUsergId());
	        search.setMenuDcd("META");
			List<MenuManageVO> list = siteMapService.selectMenuListForSiteMap(search);
		    
			
	    	model.addAttribute("result", list);
	    	
			return "/meta/sitemap/site_map";
		}
	    
	    /** 사이트맵 즐겨찾기 저장... @throws Exception insomnia */
	    @RequestMapping("/meta/sitemap/regfavoritemenu.do")
	    @ResponseBody
		public IBSResultVO<MenuManageVO> regFavoriteMenu(@RequestBody String data,Locale locale) throws Exception {
            
	    	logger.debug("sitemap : {}",data);
//	    	System.out.println("okokok" + data.toString());
			String[] menuId = data.split("%7C");
//			System.out.println("dkdkdk" + menuId.toString());
			String resmsg;
	    	
			int result =1;
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
	    	String userid = user.getUniqId();
            
	    	WaaUser tmpUser = userService.getUserInfo(userid);
			
	    	result = siteMapService.deleteFavoriteMenu(userid, tmpUser.getUsergId());
	    	
	    	if(menuId[0] != ""){
			    for(int i=0 ; i< menuId.length ; i++){
			        result = siteMapService.saveFavoriteMenu(UtilString.replace(menuId[i],"=",""), userid, tmpUser.getUsergId());
			    }
	    	}
	    	
	    	if(result > 0 ){
	    		result = 0;
	    		resmsg = message.getMessage("MSG.SAVE", null, locale);
	    	} else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}
	    	String action = WiseMetaConfig.IBSAction.REG.getAction();
	    	
	    	return new IBSResultVO<MenuManageVO>(result, resmsg, action);
		}
	
		@RequestMapping("/meta/sitemap/favoriteList.json")
		@ResponseBody
		public List<MenuManageVO> getFavoriteListJSON() throws Exception{
			logger.debug("sitemap favorite list: {}");
			
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			
	    	String userid = user.getUniqId();
	    	MenuManageVO search = new MenuManageVO();
	    	WaaUser tmpUser = userService.getUserInfo(userid);
			search.setUserId(userid);
			search.setUserGroupId(tmpUser.getUsergId());
	        search.setMenuDcd("META");
			return siteMapService.selectFavoriteList(search);
		}


}