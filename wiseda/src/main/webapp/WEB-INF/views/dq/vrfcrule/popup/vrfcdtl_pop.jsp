<!DOCTYPE html>
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
/* var prfKndCd ="PC01"; */
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
	$("#btnExec").hide();
	$("#btnSqlSearch").hide();
	$("#btnPrfSave").hide();
	$("#btnPrfDelete").hide();
	
	
  //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
        
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}   		
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
	
	var param = "&prfId="+prfId;
	loadSql(param);
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

//상세정보호출
function loadSql(param) {
	
	ajax2Json('<c:url value="/dq/vrfcrule/ajaxgrid/sql_dtl.do"/>', param, function(result){
		$("#sqlTabs-totalcntsql > textarea").html(result.totalCount);
		$("#sqlTabs-errcountsql > textarea").html(result.errorCount);
		$("#sqlTabs-patternsql > textarea").html(result.errorPattern);
	});
}

function displayTab (prfKndCd){
	
	$("[id^='tab-02']").css("display", "none");
	$("[id^='taba-02']").css("display", "none");
	
	
	//관계분석 저장
	if(prfKndCd == "PT01"){
		$("[id$='-pt01']").css("display", ""); 
		$("#tab-pt01 a").click();
	}
	//중복분석 저장
	if(prfKndCd == "PT02"){
		$("[id$='-pt02']").css("display", ""); 
		$("#tab-pt01 a").click();
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

//분석SQL조회 
function SearchSql(){
	var prfId = $("form[name=frmAnaTrg] #prfId").val();
	
	if(prfId == ""){
		var message = "<s:message code="INQ.PROF.CHC.1" />"; /* 조회할 프로파일을 선택하십시오. */
		showMsgBox("INF", message); 
		return;
	}
	
	var url   = "<c:url value="/dq/profile/popup/sql_pop.do"/>";
	var param = "?prfId=" + prfId ;
	     param += "&prfKndCd=" + $("form[name=frmAnaTrg] #prfKndCd").val();
	var popup = OpenWindow(url+param,"SQL","800","650","yes");
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
		<div style="width: 65%;float: left;">
			<div class="pop_content">
			    <%--
				<div class="stit"><s:message code="DIAG.TRGT.DTL.INFO"/></div><!-- 진단대상 상세정보 -->
				<div class="tb_basic" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
				   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
				   <colgroup>
				   <col style="width:12%;" />
				   <col style="width:38%;" />
				   <col style="width:12%;" />
				   <col style="width:38%;" />
				   </colgroup>
				       <tbody>   
				       		<tr>                               
				               <th scope="row"  class="th_require"><label for="dbConnTrgPnm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
				               <td>
				                   <input  type="text"  name="dbConnTrgPnm" id="dbConnTrgPnm"  class="b0wd98" value="${search.dbConnTrgPnm }" readonly/>
				               </td>
				               <th scope="row"  class="th_require"><label for="dbSchPnm"><s:message code="SCHEMA.NM" /></label></th><!-- 스키마명 -->
				               <td>
				                   <input type="text" name="dbSchPnm" id="dbSchPnm"  class="b0wd98" value="${search.dbSchPnm }" readonly />
				               </td>
				           </tr>                         
				           <tr>                               
				               <th scope="row"  class="th_require"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
				               <td>
				                   <input type="text" name="dbcTblNm" id="dbcTblNm"  class="b0wd98" value="${search.dbcTblNm }" readonly/>
				               </td>
				               <th scope="row"><label for="dbcTblKorNm"><s:message code="TBL.KRN.NM" /></label></th><!-- 테이블한글명 -->
				               <td>
				                   <input type="text" name="dbcTblKorNm" id="dbcTblKorNm"  class="b0wd98" value="${search.dbcTblKorNm }" readonly />
				               </td>
				           </tr>
				           <tr id="colPrfTrLayer">
				           		<th scope="row"  class="th_require"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!-- 컬럼명 -->	
				           		<td>
				           			<input type="text" name="dbcColNm" id="dbcColNm" class="b0wd98" value="${search.dbcColNm }"   readonly />
				           		</td>
				           		 <th scope="row"><label for="dbcColKorNm"><s:message code="CLMN.KRN.NM" /></label></th><!-- 컬럼한글명 -->
				               <td>
				                   <input type="text" name="dbcColKorNm" id="dbcColKorNm" class="b0wd98" value="${search.dbcColKorNm }"   readonly />
				               </td>
				           </tr>
				           
				           <tr id="colPrfTrLayer">                               
				               <th scope="row"><label for="pkYn">PK</label></th>
				               <td>
				                   <input type="text" name="pkYn" id="pkYn"  value="${search.pkYn }"  readonly/>
				               </td>
				               <th scope="row"><label for="nullYn"><s:message code="NULL.YN" /></label></th><!-- Null여부 -->
				                <td>
				                   <input type="text" name="nullYn" id="nullYn"  value="${search.nullYn }"  readonly />
				               </td>
				           </tr>
				           <tr id="colPrfTrLayer">                               
				                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th><!-- 데이터타입 -->
				               <td>
				                   <input type="text" name="dataType" id="dataType"  value="${search.dataType }" readonly />
				               </td>
				               <th scope="row"><label for="defltVal">DEFAULT</label></th>
				               <td>
				                   <input type="text" name="defltVal" id="defltVal"   value="${search.defltVal }"  readonly/>
				               </td>
				           </tr>
				           
				       </tbody>
				     </table>   
				</div>
				--%>
			</div>
			 
		
		 <!-- 팝업 내용 시작 -->
		   <div class="pop_content">
				<%-- <div class="stit"><s:message code="DIAG.TRGT.DTL.INFO"/></div> --%><!-- 진단대상 상세정보 -->
				<div id="tabs">
					<ul>
						<li id="tab-01"><a href="#tabs-01"><s:message code="DATA.PTRN.INQ"/></a></li><!--데이터패턴조회-->
						<li id="tab-02"><a href="#tabs-02"><s:message code="ANLY.RSLT.DTL.INQ"/></a></li><!--분석결과상세조회-->
	
					</ul>
					<div id="tabs-01">
						<div id="dataptrlst"><%@include file="dataptr_lst.jsp" %></div>
						
					</div>
					<div id="tabs-02">
						<div id="anaresdtl"></div>
	<%-- 					<%@include file="anares_dtl.jsp" %> --%>
					</div>
				</div>
				
			</div>
		</div>
		<div style="width: 35%;float: right;height: 100%">
			<div class="pop_content">
				<div style="clear:both; height:27px;"><span></span></div>
				<div class="ui-tabs ui-widget ui-widget-content ui-corner-all">
					 <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
						<li id="sqlTab-1" class="ui-state-default ui-corner-top ui-tabs-active ui-state-active"><a href="#"><s:message code="TOT.CCNT.SQL"/></a></li><!--오류건수SQL-->
					 </ul>
				 </div>
				 <div style="clear:both; height:5px;"><span></span></div>
				 <div id="sqlTabs-totalcntsql">
					<textarea class="wd99p" rows="11">${sqlVO.totalCount} </textarea>	
				 </div>
				 <div style="clear:both; height:20px;"><span></span></div>
				 <div class="ui-tabs ui-widget ui-widget-content ui-corner-all">
					 <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
						<li id="sqlTab-2" class="ui-state-default ui-corner-top ui-tabs-active ui-state-active"><a href="#"><s:message code="EROR.CCNT.SQL"/></a></li><!--오류건수SQL-->
					 </ul>
				 </div>
				 <div style="clear:both; height:5px;"><span></span></div>
				 <div id="sqlTabs-errcountsql">
					<textarea class="wd99p" rows="11">${sqlVO.errorCount}</textarea>	
				 </div>
				 <div style="clear:both; height:20px;"><span></span></div>
				<div class="ui-tabs ui-widget ui-widget-content ui-corner-all">
					 <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
						<li id="sqlTab-4" class="ui-state-default ui-corner-top ui-tabs-active ui-state-active"><a href="#"><s:message code="ERR.DATA.SQL"/></a></li><!--패턴SQL-->
					 </ul>
				 </div>
				 <div style="clear:both; height:5px;"><span></span></div>
				 <div id="sqlTabs-patternsql">
					<textarea  class="wd99p" rows="11">${sqlVO.errorPattern}</textarea>	
				 </div>	
			</div> 
		</div>
</body>
</html>