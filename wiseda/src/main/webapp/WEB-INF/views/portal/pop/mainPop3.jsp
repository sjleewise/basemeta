<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
<title>메인팝업3</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function(){
	
	$("#closePop").click(function(){
		window.close();
	});
	
	$(".pop_main_guide map area").click(function(){
		var idx = $(".pop_main_guide map area").index(this);
		
		//테이블 생성 요청
		OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
	});		
	
	/* 
	$(".pop_main_bt a").click(function(){
		var idx = $(".pop_main_bt a").index(this);
		if(idx == 0){
			idx += 1;
			//데이터 표준화 지침서
			OpenWindow(getUrl(idx), idx, '1050px', '500px', 'yes');
		}else if(idx == 1){
			idx += 1;
			//데이터 품질관리 가이드
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else{
			return false;
		}
	});
	*/
	
});

//URL 연결
function getUrl(idx){
	var url     = "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=";
	
	if (idx == "0"){
		//테이블 생성 요청
		url 		+= "biz/da/model/rqst/pdmmodelrqst_ins.jsp";
	}else if (idx == "1"){
   	    //코드등록
		url 		+= "biz/stnd/codebook/rqst/codebookrqst_ins.jsp";
	}else if (idx == "2"){
		//데이터 품질관리 가이드
		<%-- url = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src="; --%>
	}else{	
		return false;
	}
	return url;
}

</script>

</head>
<body>

<!-- 메인페이지 가이드 팝업 03_코드관리 800px X 462px -->
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">코드관리</div>
        <div class="pop_close"><a id="closePop"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
        	<div class="pop_main_guide"><img src='<c:url value="/img/main_pop_03.gif"/>' alt="코드관리" usemap="#Map" border="0">
              <map name="Map">
                <area shape="rect" coords="25,163,172,225" href="#" alt="테이블 생성 요청"><area shape="rect" coords="155,22,327,85" href="#" alt="코드 등록">
              </map>
        	</div>
            <div class="pop_main_bt" style="display: none;">
            	<a><img src='<c:url value="/img/main_pop_bt_01.gif"/>' alt="데이터 표준화 지침서"></a>
                <a><img src='<c:url value="/img/main_pop_bt_02.gif"/>' alt="데이터 품질관리 가이드"></a>
            </div>
        </div>
    </div>
</div>

</body>
</html>