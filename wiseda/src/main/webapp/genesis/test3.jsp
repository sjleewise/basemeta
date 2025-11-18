<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"
		 import="java.util.*"
		 
import="java.io.*"
import="java.net.URL"
import="java.net.URLConnection"
 
import="javax.xml.parsers.DocumentBuilder"
import="javax.xml.parsers.DocumentBuilderFactory"
import="org.apache.xerces.dom.DocumentImpl"
import="org.apache.xml.serialize.OutputFormat"
import="org.apache.xml.serialize.XMLSerializer"
import="org.w3c.dom.Document"
import="org.w3c.dom.Element"



%>

<script type="text/javascript">
	function deleteModelSvg(){
		//var modelName = document.deleteModelSvg.modelName.value;
		//document.location.href="/webviewer41/admin/batch/deleteModelSvg.do?modelName="+modelName;
		
	}
</script>
<%
	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+"\n<meta xmlns=\"http://www.tmaxsoft.co.kr/proframe/meta\">"
			+"\n<metaField defaultValue=\"\" description=\"\" fieldType=\"string\" length=\"1\" decimal=\"-1\" logicalName=\"외화기관간RP여부\" physicalName=\"fcrncy_ogn_amng_rp_f\" resourceGroup=\"DB_META\" resourceId=\"fcrncy_ogn_amng_rp_f010001\" revision=\"\" xmlns=\"\"/>"
			+"\n</meta>";
	String queryURL = "http://localhost:8080/restApi/getCategoryList";
	//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	//DocumentBuilder db = dbf.newDocumentBuilder();
	 
	//Document doc = db.parse(queryURL);  
	//out.println(doc.getBaseURI());
	//out.println(doc.toString());
	//doc.getBaseURI()
	
	System.out.println();
	
%>

