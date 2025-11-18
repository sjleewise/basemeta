<!DOCTYPE html>
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
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	//폼검증
	 $("form[name=frmInputPC03]").validate({
		rules: {
			dqiLnm		: "required"
		},
		messages: {
			dqiLnm		: requiremessage
		}
	});
	
	// 품질지표 조회
    $("#frmInputPC03 #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFPC03"; 
    		param += "&dqiIds="+$("#frmInputPC03 #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });
});


$(window).on('load',function(){
});


$(window).resize( function(){
    }
);

function resetPC03(){
	//프로파일 상세정보 초기화
	$("form[name=frmInputPC03]")[0].reset();
}

function savePC03(){
	//입력필드 체크박스 확인
	if( $("form[name=frmInputPC03] dateFrmCd").val() == "" ){
		var message = "<s:message code='REQUIRED.INPT.ITEM' />";
		showMsgBox("INF", message); 
		return;
	}
	//저장
	var urls = '<c:url value="/dq/profile/registerProfile.do"/>';
		var param =   "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
		     param += "&"+$("form[name=frmInputPC03]").serialize(); //날짜형식분석 정보
		     
	ajax2Json(urls, param, ibscallback);
}

function setDtlPC03(data){
	//날짜형식코드
	$('form[name=frmInputPC03] #dateFrmCd').val(data.resultVO.dateFrmCd);
	//추가조건
	$('form[name=frmInputPC03] #adtCndSql').val(data.resultVO.adtCndSql);
	
	$('form[name=frmInputPC03] #dqiId').val(data.resultVO.dqiId); 
	$('form[name=frmInputPC03] #dqiLnm').val(data.resultVO.dqiLnm); 
}

//DQI 팝업 리턴값 처리
function returnDqiPopPC03 (ret) {
	//초기화
	$("#frmInputPC03 #dqiLnm").val("");
	$("#frmInputPC03 #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInputPC03 #dqiLnm").val() != null && $("#frmInputPC03 #dqiLnm").val() != "undefined" && $("#frmInputPC03 #dqiLnm").val() != "" ){
			$("#frmInputPC03 #dqiLnm").val($("#frmInputPC03 #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInputPC03 #dqiId").val($("#frmInputPC03 #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInputPC03 #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInputPC03 #dqiId").val(parsejson.dqiId);
		}
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
	<div id="searchPC03_div" >
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="DATE.FRMT.ANLY.DTL.INFO"/></div><!--날짜형식분석 상세정보-->
		<div style="clear:both; height:5px;"><span></span></div>
		
		<form id="frmInputPC03" name="frmInputPC03" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic2" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DATE.FRMT.ANLY'/>"><!--날짜형식분석-->
			   <caption><s:message code="DATE.FRMT.ANLY"/></caption><!--날짜형식분석-->
			   <colgroup>
			   	<col style="width:15%;" />
		   		<col style="width:85%;" />
			   </colgroup>
			       <tbody>   
			           <tr>
			           		<th scope="row" class="th_require"><s:message code="DATE.FRMT.CD"/></th><!--날짜형식코드-->	
			           		<td>
			           			<select id="dateFrmCd"  name="dateFrmCd" class="" style="">
								<c:forEach var="code" items="${codeMap.dateFrmCd}" varStatus="status">
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
								</select>
			           		</td>
			           </tr>
			       	   <tr>                               
			               <th scope="row"><label for="adtCndSql"><s:message code="ADDT.COND"/></label></th><!--추가조건-->

			               <td>
			                   <input type="text"  class="wd98p" name="adtCndSql" id="adtCndSql" />
			               </td>
			           </tr>
			           
					<tr>
		               <th scope="row" class="th_require"><label for="dqiSearch"><s:message code="QLTY.INDC"/></label></th><!--품질지표-->
		               <td>
		                   <input type="text"  style="width: 70%;" name="dqiLnm" id="dqiLnm" readonly />
		                   <input type="hidden" name="dqiId" id="dqiId" />
		                   &nbsp;&nbsp;&nbsp;
		                   <input style="background-color: #2682ca;" type="button" id="btnApply" name="btnApply" class="btn_save" value="<s:message code='INQ' />" />
		               </td>
		           </tr>			           
			           
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
	
<!-- </body> -->
<!-- </html> -->
