<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>

<script type="text/javascript">

$(document).ready(function() {
	
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
	//데이터패턴 조회
	//loadDetail(param);
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
     	if ("${headerVO.popType}" == "I") {
     		parent.closeLayerPop();
     	} else {
     		window.close();
     	}
     });
});

$(window).on('load',function() {
});


$(window).resize( function(){
});

function doAction(sAction)
{
    switch(sAction)
    {
    }       
}

//상세정보호출
function loadDetail(param) {
	$('div#dataptrlst').load('<c:url value="/dq/profile/ajaxgrid/sql_dtl.do"/>', param, function(){});
	
	$('div#anaresdtl').load('<c:url value="/dq/profile/ajaxgrid/sql_dtl.do"/>', param, function(){});
	
	$('div#colanaresdtl').load('<c:url value="/dq/profile/ajaxgrid/sql_dtl.do"/>', param, function(){});
}

</script>
</head>

<body>
	<div class="pop_tit" >
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
	    <div class="pop_tit_txt"><s:message code="DATA.PTRN.INQ"/></div><!--데이터패턴 조회-->
	    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
	    <!-- 팝업 타이틀 끝 -->
	
	    <!-- 팝업 내용 시작 -->
	   <div class="pop_content">
			<div id="tabs">
				<ul>
					<li ><a href="#tabs-01"><s:message code="DATA.PTRN.INQ"/></a></li><!--데이터패턴조회-->
					<li ><a href="#tabs-02"><s:message code="ANLY.RSLT.DTL.INQ"/></a></li><!--분석결과상세조회-->
					<li ><a href="#tabs-03"><s:message code="CLMN.ANLY.DTL.INQ"/></a></li><!--컬럼분석상세조회-->

				</ul>
				<div id="tabs-01">
					<div id="dataptrlst"><%@include file="dataptr_lst.jsp" %></div>
				</div>
				<div id="tabs-02">
					<div id="anaresdtl"><%@include file="anares_dtl.jsp" %></div>
				</div>
				<div id="tabs-03">
					<div id="colanaresdtl"><%@include file="colanares_dtl.jsp" %></div>
				</div>
			</div>
			
		</div>
	</div>

</body>
</html>