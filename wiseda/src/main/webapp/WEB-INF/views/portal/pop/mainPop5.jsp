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
<title>메인팝업5</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function(){
	
	$("#closePop").click(function(){
		window.close();
	});
	
	$(".pop_main_guide map area").click(function(){
		var idx = $(".pop_main_guide map area").index(this);
		if(idx == 0){
			//프로파일 유형 등록
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 1){
			//측정결과
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 2){
			//데이터 표준화 지침서
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 3){
			//데이터 품질관리 가이드
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else{
			return false;
		}
	});		
	
	/* 
	$(".pop_main_bt a").click(function(){
		var idx = $(".pop_main_bt a").index(this);
		if(idx == 0){
			idx += 2;
			OpenWindow(getUrl(idx), idx, '1050px', '500px', 'yes');
		}else if(idx == 1){
			idx += 2;
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
	if(idx == "2"){
		url = "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=";
	}else{
		url = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=";
	}
	if (idx == "0"){
		//프로파일 유형 등록
		url 		+= "dqviews/profile/profile_col.jsp&MenuId=M1294130127125&page_navi=측정&gt;프로파일&gt;<font class=history_tit>컬럼프로파일 관리</font>";
	}else if (idx == "1"){
		//측정결과
   		url 		+= "dqviews/profile/profile_list.jsp&MenuId=M000004742&page_navi=측정&gt;프로파일&gt;<font class=history_tit>프로파일 조회</font>";     
	}else if (idx == "2"){
		//데이터 표준화 지침서
		//url		    += "admin/board/boarddetail.jsp?sBoardId=18";     
	}else if (idx == "3"){
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
<!-- 메인페이지 가이드 팝업 05_프로파일 800px X 602px -->
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">프로파일</div>
        <div class="pop_close"><a id="closePop"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
        	<div class="pop_main_guide"><img src='<c:url value="/img/main_pop_05.gif"/>' alt="프로파일" usemap="#Map" border="0">
              <map name="Map">
                <area shape="rect" coords="156,19,514,315" href="#" alt="프로파일 유형 등록">
                <area shape="rect" coords="549,124,696,205" href="#" alt="측정결과">
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