package kr.wise.commons.cmm.security;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSSRequestWrapper extends HttpServletRequestWrapper { 
	Logger logger = LoggerFactory.getLogger(getClass());
	private Map<String, String[]> params = new HashMap<String, String[]>();
	
	public XSSRequestWrapper(HttpServletRequest request) throws Exception { 
		super(request); 
		this.params.putAll( request.getParameterMap());
		
		HttpUtil httpUtil = new HttpUtil();
		
		// Body String을 가져옴
		String jsonBody = httpUtil.getBodyDataByRequest(request); 
		if( jsonBody == null) { 
			return; 
		}
		try { 
			JSONObject obj = new JSONObject(jsonBody); 
			Set<String> keys = obj.keySet(); 
			for (String key : keys) { 
				setParameter(key, cleanXSS(obj.getString(key))); 
			} 
		} catch (Exception e) { 
			throw e; 
		} 
		logger.debug("XSSRequestWrapper end");
	} 
	
	@Override 
	public String getParameter(String name) { 
		String[] paramArr = getParameterValues(name); 
		if( paramArr != null && paramArr.length > 0) { 
			return paramArr[0]; 
		} else { 
			return null; 
		} 
	} 
	
	@Override 
	public Map<String, String[]> getParameterMap() { 
		return Collections.unmodifiableMap( params); 
	} 
	
	@Override public Enumeration<String> getParameterNames() { 
		return Collections.enumeration( params.keySet()); 
	} 
	
	@Override public String[] getParameterValues(String name) { 
		String[] result = null; 
		String[] value = params.get(name); 
		
		if( value != null) { 
			result = new String[ value.length]; 
			System.arraycopy(value, 0, result, 0, value.length); 
		} return result; 
	} 
	
	public void setParameter( String name, String value) { 
		String[] param = {value}; 
		params.put(name, param); 
	} 

	public String cleanXSS(String value) {
//		value = value.replaceAll("eval\\((.*)\\)", "");
//		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		
		value = value.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		value = value.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		value = value.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		value = value.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		value = value.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		value = value.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		value = value.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		value = value.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		value = value.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		value = value.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		value = value.replaceAll("<(I|i)(M|m)(G|g)", "&lt;img");
		value = value.replaceAll("</(I|i)(M|m)(G|g)", "&lt;img");
		value = value.replaceAll("<(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
		value = value.replaceAll("</(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
		value = value.replaceAll("(A|a)(L|l)(E|e)(R|r)(T|t)", "");
		
		//value = value.replaceAll("script", "");
		return value;
	}
}
