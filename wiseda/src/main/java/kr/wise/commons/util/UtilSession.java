/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UtilSession.java
 * 2. Package : kr.wise.egmd.util
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 12. 오후 3:50:31
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 12. 		: 신규 개발.
 */
package kr.wise.commons.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <PRE>
 * 1. ClassName : UtilSession
 * 2. Package  : kr.wise.egmd.util
 * 3. Comment  : 서비스단에서 세션 정보를 처리할 수 있는 유틸 클래스...
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 12.
 * </PRE>
 */

public class UtilSession {
	 /**
	  * attribute 값을 가져 오기 위한 method
	  * 
	  * @param String  attribute key name 
	  * @return Object attribute obj
	 */
	 public static Object getAttribute(String name) throws Exception {
	  return (Object)RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	 }
	 
	 /**
	  * attribute 설정 method
	  * 
	  * @param String  attribute key name 
	  * @param Object  attribute obj
	  * @return void
	 */
	 public static void setAttribute(String name, Object object) throws Exception {
	  RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
	 }
	 
	 /**
	  * 설정한 attribute 삭제 
	  * 
	  * @param String  attribute key name 
	  * @return void
	 */
	 public static void removeAttribute(String name) throws Exception {
	  RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
	 }
	 
	 /**
	  * session id 
	  * 
	  * @param void
	  * @return String SessionId 값
	 */
	 public static String getSessionId() throws Exception  {
	  return RequestContextHolder.getRequestAttributes().getSessionId();
	 }
	 
	 public static HttpServletRequest getCurrentRequest() {
		 return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest() ;
	 }


}
