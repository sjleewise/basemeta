<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>품질관리체계</title>

<script type="text/javascript">

$(document).ready(function(){
	
	$(".stitSub map area").click(function(){
		var idx = $(".stitSub map area").index(this);
// 		alert(idx);
		if(idx == 0){
			//데이터모델/DB
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 1){
			//데이터오너십
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 2){
			//데이터표준화
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 3){
			//CTQ 도출
// 			alter("3");
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 4){
			//BR 검증
// 			alert("4");
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 5){
			//데이터품질관리 체계 지침
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 6){
			//데이터 품질관리 체계 가이드
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else{
			return false;
		}
	});		
	
});

//URL 연결
function getUrl(idx){
	var url;     
	
	if (idx == "0"){
		//데이터모델/DB 
		url 		= "";
	}else if (idx == "1"){
		//데이터오너십
   		url 		= "";
	}else if (idx == "2"){
		//데이터표준화
		url 		= "";
	}else if (idx == "3"){
		//CTQ 도출
		url 		= "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/baseInfo/ctq.jsp&MenuId=M000004776";             
	}else if (idx == "4"){
		//BR 검증
		url 		= "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/bizrule/bizrule_list.jsp&MenuId=M000004745";           
	}else if (idx == "5"){
		//데이터품질관리 체계 지침
		url 		= "";             
	}else if (idx == "6"){
		//데이터 품질관리 체계 가이드
		url		    = "";                   
	}else{	
		return false;
	}
	return url;
}

</script>

</head>
<body>

        <!-- 오른쪽 내용 시작 -->
        <div class="right">
        	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 개발가이드 &gt; 산출물관리</div>
            <div class="stit">품질관리체계</div>
            <div class="stitSub">
<%--             	<img src='<c:url value="/img/01_01_img.gif"/>' alt="" usemap="#Map" border="0"><map name="Map"><area shape="rect" coords="324,215,460,272" href="#" alt="CTQ 도출"><area shape="rect" coords="481,215,619,274" href="#" alt="BR 검증"><area shape="rect" coords="419,446,605,478" href="#" alt="데이터품질관리 체계 지침"><area shape="rect" coords="610,445,797,478" href="#" alt="데이터 품질관리 체계 가이드"></map> --%>
            	<img src='<c:url value="/img/01_01_img.gif"/>' alt="" usemap="#Map" border="0"><map name="Map"><area shape="rect" coords="328,84,463,115"  alt="데이터모델/DB" ><area shape="rect" coords="487,84,620,116"  alt="데이터오너십" ><area shape="rect" coords="644,84,776,114"  alt="데이터표준화" ><area shape="rect" coords="324,215,460,272" href="#" alt="CTQ 도출"><area shape="rect" coords="481,215,619,274" href="#" alt="BR 검증"><area shape="rect" coords="419,446,605,478" href="#" alt="데이터품질관리 체계 지침"><area shape="rect" coords="610,445,797,478" href="#" alt="데이터 품질관리 체계 가이드"></map>
            </div>
        </div>
        <!-- 오른쪽 내용 끝 -->
       
</body>
</html>