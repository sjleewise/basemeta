<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title>DDL 파티션 상세조회</title>

<script type="text/javascript">


$(document).ready(function() {
	
	
	$( "#tabs_part" ).tabs();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
// 	$("#tabs").tabs();
	
                
    //그리드 초기화 
//     initGrid();
    
    // 엑셀내리기 Event Bind
//     $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
    
    
// 	$("#frmInputDdlScript #ddlscript").val(param.scrtInfo);
});

$(window).on('load',function() {
	//그리드 초기화
	
	$(window).resize();
// 	loadDetail();
	loadDetailObjScript();
	
	var ddlPartId = '${ddlPartId}';

    var param = "&ddlPartId=" + ddlPartId;
    
	loadDetail(param);
	
	var param1 = "ddlObjId="+ ddlPartId + "&ddlObjType=DDP";
	
	loadDetailObjScript(param1);
	
	part_main_sheet.DoSearch("<c:url value="/meta/ddl/getddlmainpartlst.do" />", param);
	sub_sheet.DoSearch("<c:url value="/meta/ddl/getddlsubpartlst.do" />", param);
	part_hist_sheet.DoSearch('<c:url value="/meta/ddl/getddlparthistlst.do" />', param);

});

$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	// grid_sheet.SetExtendLastCol(1);    
    
});

function doAction(sAction)
{
        
    switch(sAction)
    {
    }       
}
 
/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/

//상세정보호출
function loadDetail(param) {
	$('div#detailPartInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlpartinfo_dtl.do"/>', param, function(){
		$("#tabs_part").show();
	});
}

//상세정보호출
function loadDetailObjScript(param) {
	$('div#detailObjInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlScriptObj_dtl.do"/>', param, function(){});
// 	$('div#detailObjInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
}

</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">DDL파티션 상세조회</div>
    <div class="pop_tit_close"><a>창닫기</a></div>
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
    	<div id="tabs_part" style="display:none;">
		  <ul>
		    <li><a href="#tabs-1">DDL파티션 상세정보</a></li>
		    <li><a href="#tabs-2">주파티션목록</a></li>
		    <li><a href="#tabs-3">서브파티션목록</a></li>
		    <li><a href="#tabs-4">파티션이력</a></li>
		    <li><a href="#tabs-5">DDL</a></li>
		  </ul>
		  <div id="tabs-1">
				<div id="detailPartInfo"></div>
		  </div>
		  <div id="tabs-2">
				<%@include file="../ddlpartmain_list.jsp" %>
		  </div>
		  <div id="tabs-3">
				<%@include file="../ddlpartsub_list.jsp" %>
		  </div>
		   <div id="tabs-4">
				<%@include file="../ddlparthist_list.jsp" %>
		  </div>
		   <div id="tabs-5">
	 			<div id="detailObjInfoScript"></div>
		  </div>
		 </div>
	<div style="clear:both; height:5px;"><span></span></div>
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>