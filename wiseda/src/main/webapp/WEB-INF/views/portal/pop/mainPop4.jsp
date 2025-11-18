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
<title>메인팝업4</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function(){
	
	$("#closePop").click(function(){
		window.close();
	});
	
	$(".pop_main_guide map area").click(function(){
		var idx = $(".pop_main_guide map area").index(this);
		if(idx == 0){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 1){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 2){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 3){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 4){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 5){
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else{
			return false;
		}
	});		
	
	/* 
	$(".pop_main_bt a").click(function(){
		var idx = $(".pop_main_bt a").index(this);
		if(idx == 0){
			idx += 6;
			OpenWindow(getUrl(idx), idx, '1050px', '500px', 'yes');
		}else if(idx == 1){
			idx += 6;
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else{
			return false;
		}
	});
    */
	
});

//URL 연결
function getUrl(idx){
	var url;     
	if(idx == "6"){
		url = "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=";
	}else{
		url = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=";
	}
	if (idx == "0"){
		//데이터품질기준(DQI) 정의
		url 		+= "dqviews/baseInfo/dqi.jsp&MenuId=M000004775&page_navi=기준정보&gt;품질기준&gt;<font class=history_tit>데이터품질지표 관리</font>";
	}else if (idx == "1"){
		//핵심품질항목(CTQ) 정의
   		url 		+= "dqviews/baseInfo/ctq.jsp&MenuId=M000004776&page_navi=기준정보&gt;품질기준&gt;<font class=history_tit>중요정보항목 관리</font>";
	}else if (idx == "2"){
		//측정결과
		url 		+= "dqviews/bizrule/bizrule_list.jsp&MenuId=M000004745&page_navi=측정&gt;업무규칙&gt;<font class=history_tit>업무규칙 조회</font>";
	}else if (idx == "3"){
		//오류 원인 분석
		url 		+= "dqviews/impv/impv_cause.jsp&MenuId=M1319681959562&page_navi=개선&gt;개선활동&gt;<font class=history_tit>원인분석 관리</font>";             
	}else if (idx == "4"){
		//개선 활동
		url 		+= "dqviews/impv/impv_act.jsp&MenuId=M1320198324726&page_navi=개선&gt;개선활동&gt;<font class=history_tit>개선활동 관리</font>";           
	}else if (idx == "5"){
		//업무규칙정의서
		url 		+= "dqviews/bizrule/bizrule.jsp&MenuId=M1294130127156&page_navi=측정&gt;업무규칙&gt;<font class=history_tit>업무규칙 관리</font>";             
	}else if (idx == "6"){
		//데이터 표준화 지침서
		//url		    += "admin/board/boarddetail.jsp?sBoardId=18";                   
	}else if (idx == "7"){
		//데이터 품질관리 가이드
		//url 		+= "";
	}else{	
		return false;
	}
	return url;
}

</script>

</head>
<body>
<!-- 메인페이지 가이드 팝업 04_업무규칙 800px X 612px -->
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">업무규칙</div>
        <div class="pop_close"><a id="closePop"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
        	<div class="pop_main_guide"><img src='<c:url value="/img/main_pop_04.gif"/>' alt="업무규칙" usemap="#Map" border="0">
              <map name="Map">
                <area shape="rect" coords="205,73,460,125" href="#" alt="데이터품질기준(DQI) 정의">
                <area shape="rect" coords="205,132,460,184" href="#" alt="핵심품질항목(CTQ) 정의">
                <area shape="rect" coords="550,124,696,205" href="#" alt="측정결과">
                <area shape="rect" coords="550,267,696,329" href="#" alt="오류 원인 분석">
                <area shape="rect" coords="550,353,696,415" href="#" alt="개선 활동">
                <area shape="rect" coords="268,339,414,410" href="#" alt="업무규칙정의서">
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