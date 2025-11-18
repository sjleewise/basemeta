<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>

<head>
<title></title>

<script type="text/javascript">
var prfId ="${search.prfId}";
var prfKndCd ="${search.prfKndCd}";
var anaStrDtm ="${search.anaStrDtm}";

$(document).ready(function() {
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	$( "#tabs" ).tabs().click(function(event){
	    event.preventDefault();	//브라우저 기본 이벤트 제거...
	});
    
	//일괄등록 버튼
	$("#btnPrfSchRqst").hide();
	
	//팝업 닫기
    $("div.pop_tit_close").click(function(){
    	window.close();
    });
	
    //div size 정의
//     divSize();
    
    //진단대상 상세정보 READONLY SETTING
	$("#frmAnaTrg input[type=text]").css("border-color","transparent").css("width", "47%");
    
    //상세정보 readonly
	//$("#frmInputPT01 input[type=text]").css("border-color","transparent").css("width", "99%").attr("readonly","true");
    
    
    //프로파일ID, 프로파일종류셋팅
    $("form[name=frmAnaTrg] #prfId").val(prfId);
	$("form[name=frmAnaTrg] #prfKndCd").val(prfKndCd); 
	$("form[name=frmAnaTrg] #schAnaStrDtm").val(anaStrDtm); 
	
    //컬럼관련 요소 hide
    if(prfKndCd.indexOf("PT") > -1){
		$("form[name=frmAnaTrg] #colPrfTrLayer").hide();
	    $("form[name=frmAnaTrg] #tblColGb").val("PT");
    }else{
    	$("div#div_objNm").html("<input type='hidden'  class='wd50p' name='objNm' id='objNm' />");
		$("form[name=frmAnaTrg] #colPrfTrLayer").show();
	    $("form[name=frmAnaTrg] #tblColGb").val("PC");
    }
	
	//텝 display 
	displayTab(prfKndCd);
	
	//상세정보 조회
	loadDtls();
});



$(window).on('load',function() {
});

$(window).resize(function() {
		//div size 정의
// 		divSize();
});

function doAction(sAction, param)
{
        
    switch(sAction)
    {
		//테이블 프로파일 상세 조회
	    case "SearchPrfDtl":
	    	var urls = '<c:url value="/dq/profile/getPrfDtl.do"/>';
			ajax2Json(urls, param, ajaxDtlCallback);
	    	break;	  
    
    }       
}	

function divSize(){
  // 진단대상 조회조건
//   $("#searchTrg_div").attr("style","width:99%;height:100%;top:right;");
  // 프로파일 상세 전체
//   $("#tblprf").attr("style","width:100%;height:100%;top:right;");
  //프로파일 텝
//   $("#tabs").attr("style","width:99%;top:right;");
}

function loadDtls(){
	var param = "";
	param = "";
  	param = "&prfId=${search.prfId}" ;
	param +="&prfKndCd=${search.prfKndCd}" ;
  	param +="&dbConnTrgId=${search.dbConnTrgId}";
  	param +="&dbSchId=${search.dbSchId}";
  	param +="&dbcTblNm=${search.dbcTblNm}";
  	param +="&objNm=${search.objNm}";
  	param +="&anaDgr=${search.anaDgr}";
  	param +="&dbcColNm=${search.dbcColNm}";
  	
   	//테이블 프로파일 상세조회
    if(prfKndCd.indexOf("PT") > -1){
    	//테이블, 컬럼프로파일 구분
    	param +="&tblColGb=PT";
		//진단대상 테이블 상세조회
		ajax2Json('<c:url value="/dq/profile/getAnaTrgTblDetail.do"/>', param, setAnaTgtDtl);
    }else{
	   //컬럼분석 프로파일 상세조회
		param +="&tblColGb=PC";
		//진단대상 컬럼 상세조회
	    ajax2Json('<c:url value="/dq/profile/getAnaTrgColDetail.do"/>', param, setAnaTgtDtl);
    }
  
}

function displayTab (prfKndCd){
	
	$("[id^='tab-']").css("display", "none");
	$("[id^='tabs-']").css("display", "none");
	
	
	//관계분석 저장
	if(prfKndCd == "PT01"){
		$("[id$='-pt01']").css("display", ""); 
		$("#tab-pt01 a").click();
	}
	//중복분석 저장
	if(prfKndCd == "PT02"){
		$("[id$='-pt02']").css("display", ""); 
		$("#tab-pt02 a").click();
	}
	//컬럼분석(PC01) 
	if(prfKndCd == "PC01"){
		$("[id$='-pc01']").css("display", ""); 
		$("#tab-pc01 a").click();
	}
	//코드분석(PC02) 
	else if(prfKndCd == "PC02"){
		$("[id$='-pc02']").css("display", "");
		$("#tab-pc02 a").click();
	}
	//날짜형식분석(PC03) 
	else if(prfKndCd == "PC03"){
		$("[id$='-pc03']").css("display", "");
		$("#tab-pc03 a").click();
	}
	//범위분석(PC04) 
	else if(prfKndCd == "PC04"){
		$("[id$='-pc04']").css("display", "");
		$("#tab-pc04 a").click();
	}
	//패턴분석(PC05) 
	else if(prfKndCd == "PC05"){
		$("[id$='-pc05']").css("display", "");
		$("#tab-pc05 a").click();
	}
	
}

</script>
</head>

<body>

<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt"><s:message code="PROF.DTL.INQ"/></div><!--프로파일 상세조회-->
		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
		<!-- 팝업 타이틀 끝 -->
		
<!-- 메뉴 메인 제목 -->
		<div style="clear:both; height:5px;"><span></span></div>

<!-- 팝업 내용 시작 -->
		<div class="pop_content">
<!-- 테이블프로파일 상세 조회 -->
			<div id="tblprf">
			
				<!-- 진단대상 상세 -->
				<%@include file="../dtl/prf_dtl.jsp" %>
				<!--  진단대상 상세 끝 -->
			
				<div style="clear:both; height:10px;"><span></span></div>
				<!-- 버튼영역  -->
				<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" />
				<div style="clear:both; height:5px;"><span></span></div>       
			
				
				<!-- 프로파일 종류별 텝 -->
				<!-- 선택 레코드의 내용을 탭처리... -->
				<div id="tabs">
				  <ul>
				    <li id="tab-pt01"><a href="#tabs-pt01"><s:message code="RLT.ANLY"/></a></li><!--관계분석-->
				    <li id="tab-pt02"><a href="#tabs-pt02"><s:message code="DUP.ANLY"/></a></li><!--중복분석-->
				    <li id="tab-pc01"><a href="#tabs-pc01"><s:message code="CLMN.ANLY"/></a></li><!--컬럼분석-->
				    <li id="tab-pc02"><a href="#tabs-pc02"><s:message code="CD.ANLY"/></a></li><!--코드분석-->
				    <li id="tab-pc03"><a href="#tabs-pc03"><s:message code="DATE.FRMT.ANLY"/></a></li><!--날짜형식분석-->
				    <li id="tab-pc04"><a href="#tabs-pc04"><s:message code="RNG.ANLY"/></a></li><!--범위분석-->
				    <li id="tab-pc05"><a href="#tabs-pc05"><s:message code="STRING.PTRN.ANLY"/></a></li><!--문자열패턴분석-->
				  </ul>
				  <div id="tabs-pt01">
				  	<div id="pt01_div">
				  		<%@include file="../dtl/tblrel_dtl.jsp" %>
				  	</div>
				  </div>
				  <div id="tabs-pt02">
				  	<div id="pt02_div">
				  		<%@include file="../dtl/tblunq_dtl.jsp" %>
				  	</div>
				  </div>	
				  <div id="tabs-pc01">
				  	<div id="pc01_div">
				  		<%@include file="../dtl/colana_dtl.jsp" %>
				  	</div>
				  </div>
				  <div id="tabs-pc02">
				  	<div id="pc02_div">
				  		<%@include file="../dtl/colefva_dtl.jsp" %>
				  	</div>
				  </div>
				  <div id="tabs-pc03">
				  	<div id="pc03_div">
				  		<%@include file="../dtl/coldtfrm_dtl.jsp" %>
				  	</div>
				  </div>
				  <div id="tabs-pc04">
				  	<div id="pc04_div">
				  		<%@include file="../dtl/colrng_dtl.jsp" %>
				  	</div>
				  </div>
				  <div id="tabs-pc05">
				  	<div id="pc05_div">
				  		<%@include file="../dtl/colptr_dtl.jsp" %>
				  	</div>
				  </div>
				 </div>
				<!-- 프로파일 종류별 텝 끝 -->
			</div>
			</div>
		</div>

</body>
</html>