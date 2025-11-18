<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page 
	import="java.util.*"
	import="org.springframework.http.*"
	import="org.springframework.http.converter.FormHttpMessageConverter"   
	import="org.springframework.http.converter.HttpMessageConverter"       
	import="org.springframework.http.converter.StringHttpMessageConverter" 
	import="org.springframework.util.LinkedMultiValueMap"                  
	import="org.springframework.util.MultiValueMap"                        
	import="org.springframework.web.client.RestTemplate"                   
	import="org.apache.commons.logging.LogFactory"   
	import="java.net.*"
	import="org.springframework.web.util.UriComponentsBuilder"
	
	import="org.jdom2.*"
	import="org.jdom2.input.*"
	import="java.io.*"
%>

<%!
	
	String sp = "&nbsp;";
	public List<Element> getXmlChildren(List<Element> parent, JspWriter print, int lv){
		String sp1 = "";
		for(int i=0; i< lv; i++) sp1 = sp1 + sp;
		try{
			
			for(Element child : parent){
				List<Element> grandChild = child.getChildren();
				//System.out.println("<br>"+grandChild.size()+child);
				
				if(grandChild.size() > 0){
					getXmlChildren(grandChild, print, lv + 1);
				}else{
					print.println("<br>"+sp1+child.getName() +":"+ child.getValue());
				}
			}
			print.println("<br>");
		}catch(Exception e){}

		return null;
	}
%>

<%

String baseUrl = "http://192.168.10.122:8080/EraApi/getComboEntityList?model_name=구매업무&entity_name=공급자";
String key = "";

 

try {
       SAXBuilder builder = new SAXBuilder();
       Document doc = builder.build(new java.net.URL(baseUrl+key));
       Element root = doc.getRootElement();  //result

       List<Element> list = root.getChildren("item");
		//System.out.println(list.size());
	
		
		getXmlChildren(list, out, 0);
		

  } catch (Exception e) {
       e.printStackTrace();
  }




	out.println("ok");


%>