package kr.wise.commons.sysmgmt.log.connlog.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;


/**
 * @Class Name : LoginLogAspect.java
 * @Description : 시스템 로그 생성을 위한 ASPECT 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.clg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
public class LoginLogAspect {

	@Inject
	private LoginLogService loginLogService;
	
	
	/**
	 * 로그인 로그정보를 생성한다.
	 * EgovLoginController.actionMain Method
	 * 
	 * @param 
	 * @return void
	 * @throws Exception 
	 */
	public void logLogin() throws Throwable {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		String uniqId = "";
		String ip = request.getRemoteAddr();

		/* Authenticated  */
        Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			uniqId = user.getUniqId();
//			ip = user.getIp();
    	}

    	LoginLog loginLog = new LoginLog();
    	loginLog.setLoginId(uniqId);
        loginLog.setLoginIp(ip);
        loginLog.setLoginMthd("I"); // 로그인:I, 로그아웃:O
        loginLog.setErrOccrrAt("N");
        loginLog.setErrorCode("");
        loginLogService.logInsertLoginLog(loginLog);

	}
	
	/**
	 * 로그아웃 로그정보를 생성한다.
	 * EgovLoginController.actionLogout Method
	 * 
	 * @param 
	 * @return void
	 * @throws Exception 
	 */
	public void logLogout() throws Throwable {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		String uniqId = "";
		String ip = request.getRemoteAddr();

		/* Authenticated  */
        Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			uniqId = user.getUniqId();
			ip = user.getIp();
    	}

    	LoginLog loginLog = new LoginLog();
    	loginLog.setLoginId(uniqId);
        loginLog.setLoginIp(ip);
        loginLog.setLoginMthd("O"); // 로그인:I, 로그아웃:O
        loginLog.setErrOccrrAt("N");
        loginLog.setErrorCode("");
        loginLogService.logInsertLoginLog(loginLog);
	}
}
