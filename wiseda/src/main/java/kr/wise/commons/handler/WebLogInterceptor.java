package kr.wise.commons.handler;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.log.weblog.service.WebLog;
import kr.wise.commons.sysmgmt.log.weblog.service.WebLogService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WebLogInterceptor.java
 * 3. Package  : kr.wise.commons.handler
 * 4. Comment  : 웹로그 정보를 저장한다.
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 11. 27. 오후 6:08:45
 * </PRE>
 */ 
public class WebLogInterceptor extends HandlerInterceptorAdapter {
	
	@Inject
	private WebLogService webLogService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		WebLog webLog = new WebLog();
		String reqURL = request.getRequestURI();
		String uniqId = "";
		
		/* Authenticated  */
        Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			uniqId = user.getUniqId();
			//상세화면 호출시 web log 적재 제외
			if(reqURL.indexOf("/ajaxgrid") == -1) {
				webLog.setUrl(reqURL);
				webLog.setRqesterId(uniqId);
				webLog.setRqesterIp(request.getRemoteAddr());
				
				webLogService.logInsertWebLog(webLog);
			}
    	}
		return super.preHandle(request, response, handler);
	}
	
/*	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	

		WebLog webLog = new WebLog();
		String reqURL = request.getRequestURI();
		String uniqId = "";
		
		 Authenticated  
        Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			uniqId = user.getUniqId();
    	}
    	
		//상세화면 호출시 web log 적재 제외
		if(reqURL.indexOf("/ajaxgrid") == -1) {
			webLog.setUrl(reqURL);
			webLog.setRqesterId(uniqId);
			webLog.setRqesterIp(request.getRemoteAddr());
			
			webLogService.logInsertWebLog(webLog);
		}
    	
	}*/

}
