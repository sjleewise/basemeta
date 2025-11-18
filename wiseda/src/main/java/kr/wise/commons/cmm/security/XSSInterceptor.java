package kr.wise.commons.cmm.security;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class XSSInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("XSSInterceptor preHandle");
		Enumeration<String> e = request.getParameterNames();
		
		while(e.hasMoreElements()) {
			String key = e.nextElement();
            String values[] = request.getParameterValues(key);
//            logger.debug("values >>> " + values.length);
            if(values != null) {
            	if(values.length == 1) {
            		request.setAttribute(key, cleanXSS(values[0]));
            	} else {
            		for(int i=0, s=values.length; i<s; i++) {
//            			logger.debug("before values[" + i + "] >>> " + values[i]);
            			values[i] = cleanXSS(values[i]);
//            			logger.debug("after values[" + i + "] >>> " + values[i]);
            		}
            		request.setAttribute(key, values);
            	}
            }
		}
		return true;
	}
	
	public String cleanXSS(String value) {
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		
		value = value.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		value = value.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
		value = value.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		value = value.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
		value = value.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		value = value.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
		value = value.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		value = value.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		value = value.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		value = value.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		value = value.replaceAll("<(I|i)(M|m)(G|g)", "&lt;img");
		value = value.replaceAll("</(I|i)(M|m)(G|g)", "&lt;img");
		value = value.replaceAll("<(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
		value = value.replaceAll("</(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
		value = value.replaceAll("(A|a)(L|l)(E|e)(R|r)(T|t)", "");
		
		return value;
	}

}
