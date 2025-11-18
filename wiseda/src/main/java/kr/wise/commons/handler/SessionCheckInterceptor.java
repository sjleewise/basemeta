package kr.wise.commons.handler;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	
	private Set<String> permittedURL;
	
	public void setPermittedURL(Set<String> permittedURL) {
		this.permittedURL = permittedURL;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		HttpSession session = request.getSession(false);
//		if(session == null || session.getAttribute("SESSION_LISTENER") == null) {
//			response.sendRedirect(request.getContextPath() + "/");
//			return false;
//		}
////		return super.preHandle(request, response, handler);
//		return true;
		String userip = request.getRemoteAddr();
		String requestURI = request.getRequestURI(); //요청 URI
		boolean isPermittedURL = false; 

		LoginVO loginVO = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
//		if(loginVO != null && userip.equals(loginVO.getIp())){
		if(loginVO != null){
			return true;
		}else{
			
//			for(Iterator<String> it = this.permittedURL.iterator(); it.hasNext();){
//				String urlPattern = request.getContextPath() + (String) it.next();
//
//				if(Pattern.matches(urlPattern, requestURI)){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
//					isPermittedURL = true;
//				}
//				
//				
//			}
//			
			if(!isPermittedURL){
				String ajaxheader = request.getHeader("X-Requested-With");
				if(StringUtils.hasText(ajaxheader) && ajaxheader.toLowerCase().equals("xmlhttprequest")) {
					response.setStatus(401);
					return false;
				}
				response.sendRedirect(request.getContextPath() + "/loginform.do");
				
				return false;
//				ModelAndView modelAndView = new ModelAndView("redirect:/uat/uia/egovLoginUsr.do");			
//				throw new ModelAndViewDefiningException(modelAndView);
			}else{
				return true;
			}
//			response.sendRedirect(request.getContextPath() + "/loginform.do");
			
//			return false;
			
		}
		
	}

}

