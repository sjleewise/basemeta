/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CustomJacksonObjectMapper.java
 * 2. Package : kr.wise.egmd.helper
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 10. 오전 12:54:41
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 10. 		: 신규 개발.
 */
package kr.wise.commons.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * <PRE>
 * 1. ClassName : CustomSimpleMappingExceptionResolver
 * 2. Package  : kr.wise.egmd.helper
 * 3. Comment  : 에러발생 시 로그를 찍게끔 custom
 * 4. 작성자   : lsi
 * 5. 작성일   : 190328 
 * </PRE>
 */

public class CustomSimpleMappingExceptionResolver  extends SimpleMappingExceptionResolver {

	static final Logger logger = LoggerFactory.getLogger(CustomSimpleMappingExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("##ERROR 발생", ex);
		return super.resolveException(request, response, handler, ex);
	}

}
