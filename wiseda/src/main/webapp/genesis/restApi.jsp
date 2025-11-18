<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page 
	import="java.util.*"
	import="org.springframework.http.converter.FormHttpMessageConverter"   
	import="org.springframework.http.converter.HttpMessageConverter"       
	import="org.springframework.http.converter.StringHttpMessageConverter" 
	import="org.springframework.util.LinkedMultiValueMap"                  
	import="org.springframework.util.MultiValueMap"                        
	import="org.springframework.web.client.RestTemplate"                   
	import="org.apache.commons.logging.LogFactory"            
%>

<%

	List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	converters.add(new FormHttpMessageConverter());
	converters.add(new StringHttpMessageConverter());
	
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.setMessageConverters(converters);
	
	// parameter 세팅
	MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	map.add("str", "thisistest");
	
	// REST API 호출
	String result = restTemplate.postForObject("http://localhost:8080/MartApi/getCategoryList", map, String.class);
	out.println("------------------ TEST 결과 ------------------<br>");
	out.println(result);
%>