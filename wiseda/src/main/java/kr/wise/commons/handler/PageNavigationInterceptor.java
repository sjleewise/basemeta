
package kr.wise.commons.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Page Navigation 처리 class
 * @author wiseopen
 * @since 2014.04.17
 * @version 1.0
 * @see
 *
 */
public class PageNavigationInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Set<String> permittedURL;
	
	public void setPermittedURL(Set<String> permittedURL) {
		this.permittedURL = permittedURL;
	}

//	@Inject
//    private CommMenuService commMenuService;
	@Inject
	private UsergMenuMapService usergMenuMapService;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String servletPath = request.getServletPath();
		
		boolean isRejectUrl = false;
		
		//사용자가 관리자일 경우 true 리턴
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		if ("Y".equals(user.getIsAdminYn())) {
			logger.debug("관리자 접근 허용");
			return true;
		}
		
		String[] rejectUrls = {"/admin/[\\S]*", "/commons/sys/[\\S]*", "/commons/user/[\\S]*", 
				   "/commons/code/[\\S]*"};

		for (String rurl : rejectUrls) {
			if(Pattern.matches(rurl, servletPath)){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
				isRejectUrl = true;
			}
		}

		
		//포트에 따라 관리자 페이지는 접근 허용 불가하도록 처리한다....
		if (isRejectUrl) {
			if(servletPath.equals("/commons/user/gousrInfoChngPop.do") ||
				servletPath.equals("/commons/user/usrInfoChng.do") ){
				logger.debug("사용자 변경 팝업");
				return true;
			}else if(servletPath.equals("/commons/user/popup/userSearchPop.do") || 
				servletPath.equals("/commons/user/getuserlistbydept.do")){
				logger.debug("사용자 조회 팝업");
				return true;
			}else if(servletPath.equals("/commons/code/getComboIbs.do") ) {
				
				//일반유저로 접속시 오류 방지 
				return true;
			}else{
				logger.debug("관리자 권한 없슴");
				
				response.sendRedirect(request.getContextPath() + "/logout.do");  
								
				//response.sendRedirect(request.getContextPath() + "/error.do");
				
				return false;
			}
		}
		
		return true;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		String servletPath = request.getServletPath();
//		String servletPath = request.getRequestURI();
//		if (request.getQueryString() != null) {
//			servletPath += "?" + request.getQueryString();
//		}
		String urlPage = servletPath.substring(0,servletPath.indexOf("."));
		logger.debug("urlpage:{}\nservletPath:{}", urlPage, servletPath);
		
		boolean isPermittedURL = false;
		for(Iterator<String> it = this.permittedURL.iterator(); it.hasNext();){
//			String urlPattern = request.getContextPath() + (String) it.next();
			String urlPattern = (String) it.next();
			
//			logger.debug("\nurlpattern:{}\nservletpath:{}", urlPattern, servletPath);
			
			if(Pattern.matches(urlPattern, servletPath)){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
				isPermittedURL = true;
			}
			
//			logger.debug("\nisPermittedURL:{}", isPermittedURL);
			
		}
		
		if(!isPermittedURL && modelAndView != null && StringUtils.hasText(servletPath)){

//			Map<String, Object> menumap = usergMenuMapService.getMenuMap(servletPath);
			Map<String, Object> menumap = usergMenuMapService.getMenuMap2(servletPath);
			
			if ("Y".equals((String)menumap.get("ACCEPTYN"))) {
				if (menumap.containsKey("REQ_MENU")) {
					modelAndView.addObject("REQ_MENU", menumap.get("REQ_MENU"));
				}
				if (menumap.containsKey("TOP_MENU")) {
					modelAndView.addObject("TOP_MENU", menumap.get("TOP_MENU"));
				}
				if (menumap.containsKey("SUB_MENU")) {
	//				UtilJson.convertJsonString(menumap.get("SUB_MENU"));
					modelAndView.addObject("SUB_MENU", UtilJson.convertJsonString(menumap.get("SUB_MENU")));
				}
			} else {
				//오류페이지로 리다이렉트...
				logger.debug("메뉴권한이 없네:{}", servletPath);
				//modelAndView.setViewName("redirect:/error.do");
				
				response.sendRedirect(request.getContextPath() + "/notAuth.do");
				
			}
			
//			modelAndView.addObject("DATOP_MENU", );

//			List<WaaUsergMenuAuth> list = usergMenuMapService.getTopMenuList(servletPath);
//			if(list.size() > 0 ){
//				for(WaaUsergMenuAuth menuvo: list){
////					modelAndView.addObject("MENU_URL", commMenu.getMenuNav());
//				}
//			}else{
//				logger.debug("menu url not exists");
////				modelAndView.addObject("MENU_URL", "not exists");
//			}
		}
	}
}
