package kr.wise.commons.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class IpObtainInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String clientIp = request.getRemoteAddr();
		 
		LoginVO loginVO = (LoginVO) UserDetailHelper.getAuthenticatedUser();
 
		if (loginVO != null) {
			loginVO.setIp(clientIp);
		}
 
		return true;
	}

}
