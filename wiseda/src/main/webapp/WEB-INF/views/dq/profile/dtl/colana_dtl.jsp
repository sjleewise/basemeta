<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function(){
	
	//체크박스 전체 선택 해제
	$('form[name=frmInputPC01] #allChk').click(function(event){
		if ($("#allChk").is(":checked")) { 
	 		$('form[id=frmInputPC01] input:checkbox').prop("checked", true); 
		} else { 
	 		$('form[id=frmInputPC01] input:checkbox').prop("checked", false); 
		} 
	}); 
	
	//팝업 클릭이벤트시 reset 되는것 방지 위해
	$('form[name=frmInputPC01]').click(function(event){
		event.stopPropagation();	//브라우저 기본 이벤트 제거...
	}); 
	
});


$(window).on('load',function(){
});


$(window).resize( function(){
});


function resetPC01(){
	//프로파일 상세정보 초기화
	$("form[name=frmInputPC01]")[0].reset();
	//체크박스 해제
	$("form[name=frmInputPC01] input:checkbox").prop("checked", false);
}

function savePC01(){
	//입력필드 체크박스 확인
	if( $("form[name=frmInputPC01] input:checkbox:checked").length == 0 ){
		var message = "<s:message code='MSG.SELECT' arguments="<s:message code='DTL.INFO'/>" />"; /*상세정보*/

		showMsgBox("INF", message); 
		return;
	}
	//저장
	var urls = '<c:url value="/dq/profile/registerProfile.do"/>';
		var param =   "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
		     param += "&"+$("form[name=frmInputPC01]").serialize(); //컬럼분석 정보
		     
	ajax2Json(urls, param, ibscallback);
}

function setDtlPC01(data){
	
// 	$("input[type=checkbox]").attr("disabled", false);
	
	//최대최소값분석
	if(data.resultVO.minMaxValAnaYn == "Y"){
		$("form[id=frmInputPC01] #minMaxValAnaYn").prop("checked", true);
	}
	//널분석
	if(data.resultVO.aonlYn == "Y"){
		$('form[id=frmInputPC01] #aonlYn').prop("checked", true);
	}
	//길이분석
	if(data.resultVO.lenAnaYn == "Y"){
		$('form[id=frmInputPC01] #lenAnaYn').prop("checked", true);
	}
	//데이터빈도분석
	if(data.resultVO.crdAnaYn == "Y"){
		$('form[id=frmInputPC01] #crdAnaYn').prop("checked", true);
	}
	//데이터빈도분석
	if(data.resultVO.stdvAnaYn == "Y"){
		$('form[id=frmInputPC01] #stdvAnaYn').prop("checked", true);
	}
	//데이터빈도분석
	if(data.resultVO.vrnAnaYn == "Y"){
		$('form[id=frmInputPC01] #vrnAnaYn').prop("checked", true);
	}
	//데이터빈도분석
	if(data.resultVO.avgAnaYn == "Y"){
		$('form[id=frmInputPC01] #avgAnaYn').prop("checked", true);
	}
	//추가조건
	$('form[id=frmInputPC01] #adtCndSql').val(data.resultVO.adtCndSql); 
}

/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_pc01_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	var param = "dbConnTrgId="+grid_pc01.GetCellValue(row, "dbConnTrgId");
	 param += "&dbSchId="+grid_pc01.GetCellValue(row, "dbSchId");
	 param +="&dbcTblNm="+grid_pc01.GetCellValue(row, "dbcTblNm");
	 param +="&dbcColNm="+grid_pc01.GetCellValue(row, "dbcColNm");
	 param +="&prfId="+grid_pc01.GetCellValue(row, "prfId");
	 param +="&prfKndCd="+"PC01";
// 	 param +="&prfKndCd="+grid_pt01.GetCellValue(row, "prfId");
	
    //테이블 컬럼 프로파일 구분
	prfDtlPopup(param);	
}

function grid_pt02_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_pt02_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
<!-- 그리드 입력 입력 -->
<div id="searchPC01_div" >
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="CLMN.ANLY.DTL.INFO"/></div><!--컬럼분석 상세정보-->

		<div style="clear:both; height:5px;"><span></span></div>
		
		<form id="frmInputPC01" name="frmInputPC01" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.ANLY'/>">
			   <caption><s:message code="CLMN.ANLY"/></caption><!--컬럼분석-->
			   <colgroup>
			   <col style="width:10%;" />
			   <col style="width:10%;" />
			   <col style="width:15%;" />
			   <col style="width:25%;" />
			   <col style="width:15%;" />
			   <col style="width:25%;" />
			   </colgroup>
			       <tbody>   
			       	   <tr>  
			       		   <th rowspan="4" scope="row"><s:message code="WHL.CHC"/></th><!--전체선택-->

			               <td rowspan="4">
			                   <input type="checkbox" name="allChk" id="allChk" value="Y"/>
			               </td>
			               <th scope="row"><s:message code="DATA.FRQC.ANLY"/></th><!--데이터빈도분석-->
			               <td>
			                   <input type="checkbox" name="crdAnaYn" id="crdAnaYn"  value="Y"/>
			               </td>
			               <th scope="row"><s:message code="MAX.MIN.VAL.ANLY"/></th><!--최대최소값분석-->

			               <td>
			                   <input type="checkbox" name="minMaxValAnaYn" id="minMaxValAnaYn"  value="Y" />
			               </td>
			           </tr>
			           
			           <tr>
			           		<th scope="row"><s:message code="NULL.ANLY"/></th><!--NULL분석-->	
			           		<td>
			           			<input type="checkbox" name="aonlYn" id="aonlYn" value="Y" />
			           		</td>
			               <th scope="row"><s:message code="LNGT.ANLY"/></th><!--길이분석-->
			               <td>
			                   <input type="checkbox" name="lenAnaYn" id="lenAnaYn" value="Y"/>
			               </td>
			           </tr>
			           <tr>
			           		<th scope="row">표준편차분석</th><!--표준편차분석-->	
			           		<td>
			           			<input type="checkbox" name="stdvAnaYn" id="stdvAnaYn" value="Y" />
			           		</td>
			               <th scope="row">분산분석</th><!--분산분석-->
			               <td>
			                   <input type="checkbox" name="vrnAnaYn" id="vrnAnaYn" value="Y"/>
			               </td>
			           </tr>
			           <tr>
			           		<th scope="row">평균분석</th><!--평균분석-->	
			           		<td colspan="3">
			           			<input type="checkbox" name="avgAnaYn" id="avgAnaYn" value="Y" />
			           		</td>
			           </tr>
			       	   <tr>                               
			               <th scope="row"><label for="adtCndSql"><s:message code="ADDT.COND"/></label></th><!--추가조건-->

			               <td colspan="5">
			                   <input type="text"  class="wd98p" name="adtCndSql" id="adtCndSql" />
			               </td>
			           </tr>
			           
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
<!-- 그리드 입력 입력 End -->
	
<!-- </body> -->
<!-- </html> -->
