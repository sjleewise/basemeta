<META HTTP-EQUIV="contentType" CONTENT="text/html;charset=UTF-8">

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
	import="java.net.*"
	import="org.springframework.web.util.UriComponentsBuilder"
	
%>

<%

	List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	converters.add(new FormHttpMessageConverter());
	converters.add(new StringHttpMessageConverter());
	
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.setMessageConverters(converters);
	
	// parameter 세팅
	Map<String, String> map = new HashMap();
	//map.add("category_name", "_김영도97");
	
	request.getParameter("url");
	
	String ip	 = (request.getParameter("ip") 	== null) ?  request.getServerName() : request.getParameter("ip");
	String port	 = (request.getParameter("port") == null) ? ""+request.getServerPort() : request.getParameter("port");
	String path 	= (request.getParameter("path") == null) ? "/EraApi/getCategoryList" : request.getParameter("path");
	
	String category_name 	= (request.getParameter("category_name") == null) ? null : request.getParameter("category_name");
	
	
	UriComponentsBuilder  uriComponentsBuilder= UriComponentsBuilder.newInstance();
	uriComponentsBuilder.scheme("http");
	uriComponentsBuilder.port(port);
	uriComponentsBuilder.host(ip);
	uriComponentsBuilder.path(path);
	
	if(request.getParameter("category_name"		) != null) uriComponentsBuilder.queryParam("category_name"   	, request.getParameter("category_name") );
	if(request.getParameter("category_id"		) != null) uriComponentsBuilder.queryParam("category_id"   	, request.getParameter("category_id") );
	if(request.getParameter("subjectArea_name"	) != null) uriComponentsBuilder.queryParam("subjectArea_name"   , request.getParameter("subjectArea_name") );
	if(request.getParameter("subjectArea_id"	) != null) uriComponentsBuilder.queryParam("subjectArea_id"   	, request.getParameter("subjectArea_id") );
	if(request.getParameter("model_name"		) != null) uriComponentsBuilder.queryParam("model_name"   	, request.getParameter("model_name") );
	
	if(request.getParameter("model_id"		) != null) uriComponentsBuilder.queryParam("model_id"   			, request.getParameter("model_id") );
	if(request.getParameter("entity_name"		) != null) uriComponentsBuilder.queryParam("entity_name"   	, request.getParameter("entity_name") );
	if(request.getParameter("entity_name_like"	) != null) uriComponentsBuilder.queryParam("entity_name_like"   , request.getParameter("entity_name_like") );
	if(request.getParameter("entity_id"		) != null) uriComponentsBuilder.queryParam("entity_id"   	, request.getParameter("entity_id") );
	if(request.getParameter("viewLP"		) != null) uriComponentsBuilder.queryParam("viewLP"   		, request.getParameter("viewLP") );
	
	//URI uri = uriComponentsBuilder.build().encode("EUC-KR").toUri();
	URI uri = uriComponentsBuilder.build().encode().toUri();
	
	//System.out.println("ZZZZZZZ : " + uri.gets);
	System.out.println("ZZZZZZZ : " + uri.getQuery());
	long long1 = System.currentTimeMillis();
	String result = restTemplate.getForObject(uri, String.class);
	long long2 = System.currentTimeMillis();
		
	out.println("<br>출력 전, 소요시간 초 "+ (long2 - long1) / 1000 );
	out.println("<br>");
	out.println("<br>------------------ TEST 결과 ------------------<br>");
	out.println(result);
	
	long long3 = System.currentTimeMillis();
	out.println("<br>출력 후, 소요시간 초 "+ (long3 - long1) / 1000 );
%>