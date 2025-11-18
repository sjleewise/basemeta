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
<title>메인팝업1</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function(){
	
	$("#closePop").click(function(){
		window.close();
	});
	
	$(".pop_main_guide map area").click(function(){
		var idx = $(".pop_main_guide map area").index(this);
		if(idx == 0){       
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes'); //표준단어약어 자동 생성
		}else if(idx == 1){ 
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes'); //도메인 자동분할 
		}else if(idx == 2){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes'); //표준항목 자동분할
		}else if(idx == 3){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes'); //도메인-인포타입
		}else if(idx == 4){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes'); //표준항목-인포타입(변경가능)
		}else{
			return false;
		}
	});		
	
	/* 
	$(".pop_main_bt a").click(function(){
		var idx = $(".pop_main_bt a").index(this);
		if(idx == 0){
			idx += 5;
			OpenWindow(getUrl(idx), idx, '1050px', '500px', 'yes'); //데이터 표준화 지침서
		}else if(idx == 1){
			idx += 5;
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes'); //데이터 품질관리 가이드
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
		//표준단어약어 자동 생성
		url 		+= "biz/stnd/term/terminit/terminit_ins.jsp";
	}else if (idx == "1"){
		 //도메인 자동분할 
   		url 		+= "biz/domn/domnsepr/domnsepr_ins.jsp";
	}else if (idx == "2"){ 
		//표준항목 자동분할
		url 		+= "biz/stnd/item/itemsepr/itemsepr_ins.jsp";
	}else if (idx == "3"){ 
		//도메인-인포타입
		url 		+= "biz/stnd/stndrqst_ins.jsp";
	}else if (idx == "4"){ 
		//표준항목-인포타입(변경가능)
		url 		+= "biz/stnd/stndrqst_ins.jsp";
	}else if (idx == "5"){ 
		//데이터 표준화 지침서
		//url 		+= "admin/board/boarddetail.jsp?sBoardId=18";
	}else if (idx == "6"){ 
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
<!-- 메인페이지 가이드 팝업 01_표준화 800px X 396px -->
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">표준화</div>
        <div class="pop_close"><a id="closePop"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
        	<div class="pop_main_guide"><img src='<c:url value="/img/main_pop_01.gif"/>' alt="표준화" usemap="#Map" border="0">
              <map name="Map">
                <area shape="rect" coords="12,21,159,73" href="#" alt="표준단어 약어 자동 생성">
                <area shape="rect" coords="225,21,370,71" href="#" alt="도메인 자동분할">
                <area shape="rect" coords="452,21,609,72" href="#" alt="표준용어 자동분할">
                <area shape="rect" coords="227,94,373,214" href="#" alt="도메인-인포타입">
                <area shape="rect" coords="453,94,609,215" href="#" alt="표준용어-인포타입(변경가능)">
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
