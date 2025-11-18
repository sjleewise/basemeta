package kr.wise.commons.cmm.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 
public class CrossScriptingFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(getClass());
	
    public FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
 
    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	RequestWrapper httpServletWrapper = new RequestWrapper((HttpServletRequest)request);
    	
    	if(httpServletWrapper.getMethod().equals("GET") || httpServletWrapper.getMethod().equals("POST")) {
    		chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
    	} else {
    		chain.doFilter(request, response);
    	}
    	
    	
//    	RequestWrapper httpServletWrapper = null;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpServletRequest req = (HttpServletRequest)request;
//        Boolean flag = true;
        
//        try {
//           httpServletWrapper = new RequestWrapper(req);
//           if(!httpServletWrapper.getRequestURI().toUpperCase().contains("DEL") ) {
//	           if(httpServletWrapper.getMethod().equals("POST")) {
//	        	   String tmp  = IOUtils.toString(httpServletWrapper.getReader());
//	        	   String tmp2 = tmp;
//	        	   tmp = httpServletWrapper.cleanXSS(tmp);
//	        	   
//	        	   if(tmp.equals(tmp2)) {
////	        		   tmp2 = tmp2.concat(" ");
////	        		   httpServletWrapper.resetInputStream(tmp2.getBytes());
////	        		   chain.doFilter(request, response);
////	        		   return;
//	        		   flag = true;
//	        	   } else {
//	        		   logger.debug("1");
//	        		   httpServletWrapper.resetInputStream(tmp.getBytes());
//	        		   logger.debug("2");
//	        		   flag = true;
//	        	   }
////	        	   flag = true;
//	           } else {
//	        	   flag = false;
//	           }
//           } else {
//        	   flag = false;
//           }
//        } catch (Exception e) {
//           res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"잘못된 요청입니다.");
//           return;
//        }
//
//        if(flag) {
//        	chain.doFilter(httpServletWrapper, res);
//        } else {
//        	chain.doFilter(request, response);
//        }
    }
}