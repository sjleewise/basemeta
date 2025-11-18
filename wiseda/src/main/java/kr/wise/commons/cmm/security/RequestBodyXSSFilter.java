package kr.wise.commons.cmm.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class RequestBodyXSSFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(getClass());
	
    private List<String> extUrl;
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
       RequestWrapper reqWrapper = null;
        String path = ((HttpServletRequest) req).getServletPath();
 
        try {
        	logger.debug("servletPath >>> " + path);
            if (!extUrl.contains(path)) {
            	logger.debug("1");
               reqWrapper  = new RequestWrapper(request);
               chain.doFilter(reqWrapper , response);
            } else {
            	logger.debug("2");
                chain.doFilter(request, response);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePattern = filterConfig.getInitParameter("extUrls");
       extUrl = Arrays.asList(excludePattern.split(","));
    }
 
    @Override
    public void destroy() {
    }
}