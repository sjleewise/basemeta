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
<title>DDL테이블이관 상세정보</title>

<script type="text/javascript">
var tblSpacJson = ${codeMap.tblSpac} ;

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			ddlTblPnm	: "required",
			srcDbSchPnm 	: "required", 
			tgtDbSchPnm 	: "required", 
			//tblSpacPnm	: "required",
			objDcd		: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlTblPnm	: requiremessage,
			srcDbSchPnm 	: requiremessage, 
			tgtDbSchPnm 	: requiremessage,
			//tblSpacPnm	: requiremessage,
			objDcd		: requiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
		
	
	/*
	double_select(tblSpacJson, $("#frmInput #tblSpacTypCd"));
   	$('select', $("#frmInput #tblSpacTypCd").parent()).change(function(){
   		double_select(tblSpacJson, $(this)); 
   	});
   	*/
   	      	
	
	$("#divInputBtn").show();
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();

			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	}).hide();
	
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화"); 
		resetForm($("form#frmInput"));		
	}).hide();
		
	
	//DBMS 스키마 검색 팝업 호출(소스)
	$("#DbmsSchemaPop").click(function(){

    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbmap_pop.do' />", 800, 600);
    });
	
	//DBMS 스키마 검색 팝업 호출(타겟)
	$("#DbmsSchemaPopTgt").click(function(){

    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbmap_pop.do' />", 800, 600);
    });
    
    //오브젝트 검색팝업 호출
	$("#objPnmPop").click(function(){

		if($("#frmInput #objDcd").val() == "TBL") {
			openLayerPop("<c:url value='/meta/ddl/popup/ddltblforidx_pop.do' />", 800, 600);
		} else if($("#frmInput #objDcd").val() == "IDX") {
			openLayerPop("<c:url value='/meta/ddl/popup/ddlidx_pop.do' />", 800, 600);
		} else {
			showMsgBox("ERR", "테이블, 인덱스 여부를 선택해주세요.");
		}
    	
    });
  
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
			
	$("#frmInput #rqstDcd").attr("disabled", true);
	
	//========테이블스페이스 콤보 세팅======
	var dbConnTrgId = '${result.tgtDbConnTrgId}';	
	
	//alert(dbConnTrgId);
		
   	getTableSpac($("#tblSpacId"), dbConnTrgId); 		 
	
	$("#frmInput #tblSpacId").val('${result.tblSpacPnm}'); 
	//======================================
		
	
});

$(window).load(function(){
	
	$("#frmInput #tblSpacPnm").val('${result.tblSpacPnm}');
});

//DBMS 정보 팝업 리턴값 처리
function returnDbMapPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
// 	if($("#frmInput #dbmsInfo").val() == "SRC") {
		$("#frmInput #srcDbConnTrgPnm").val(retjson.srcDbConnTrgPnm);
		$("#frmInput #srcDbSchPnm").val(retjson.srcDbSchPnm);
		$("#frmInput #tgtDbConnTrgPnm").val(retjson.tgtDbConnTrgPnm);
		$("#frmInput #tgtDbSchPnm").val(retjson.tgtDbSchPnm);
		
// 	} else if ($("#frmInput #dbmsInfo").val() == "TGT") {
// 	}
// 	$("#frmInput #tblSpacPnm").val(retjson.dbTblSpacPnm);
// 	$("#frmInput #tblSpacPnm").val(retjson.tblSpacPnm);
}

//DDL테이블 정보 팝업 리턴값 처리
function returnDdlTblPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #ddlTblPnm").val(retjson.ddlTblPnm);
	$("#frmInput #ddlTblLnm").val(retjson.ddlTblLnm);
}

//인덱스 정보 팝업 리턴값 처리
function returnDdlIdxPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #ddlTblPnm").val(retjson.ddlIdxPnm);
	$("#frmInput #ddlTblLnm").val(retjson.ddlIdxLnm);
}



</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno"     name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus"   name="ibsStatus" value="${saction }" >
	  	<input type="hidden" id="dbmsInfo"    name="dbmsInfo"/>
	  	<input type="hidden" id="dbConnTrgId" name="dbConnTrgId" value="${result.dbConnTrgId}"  />  
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" /> --%>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">DBMS 소스 정보</div> <!-- 소스 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>소스 정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>소스 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="*" />              
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require">DBMS / 스키마(소스)</th> <!-- DBMS 정보(소스) -->
					<td colspan="3">
						<span class="input_inactive"><input type="hidden" class="wd100" id="srcDbSchId" name="srcDbSchId"  value="${result.srcDbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="srcDbConnTrgPnm" name="srcDbConnTrgPnm"  value="${result.srcDbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="srcDbSchPnm" name="srcDbSchPnm"  value="${result.srcDbSchPnm}" readonly="readonly"/></span>
						
						<!-- 
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="DbmsSchemaPop">검색</button>
						 -->
					</td>
					
				</tr>
				
			</tbody>
		</table>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">DBMS 타겟 정보</div> <!-- 타겟 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>타겟 정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>타겟 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%" />
               <col style="width:15%;" />
               <col style="width:*" />
			</colgroup>
			<tbody>
				<tr>
					
					<th scope="row" class="th_require">DBMS / 스키마(타겟)</th> <!-- DBMS 정보(타겟) -->
					<td>					    
						<span class="input_inactive"><input type="text" class="wd100" id="tgtDbConnTrgPnm" name="tgtDbConnTrgPnm"  value="${result.tgtDbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="tgtDbSchPnm" name="tgtDbSchPnm"  value="${result.tgtDbSchPnm}" readonly="readonly"/></span>
						
						<input type="hidden" id="tgtDbConnTrgId" name="tgtDbConnTrgId"  value="${result.tgtDbConnTrgId}" />
						<input type="hidden" id="tgtDbSchId" name="tgtDbSchId"  value="${result.tgtDbSchId}" />
						
						<!-- 
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="DbmsSchemaPopTgt">검색</button>
						 -->
					</td>
					
					<th scope="row">테이블스페이스</th>   <!-- 테이블스페이스 -->
					<td>
					    <!-- 
						<select id="tblSpacTypCd" class="" name="tblSpacTypCd" disabled="disabled">
				            <option value="">선택</option>
				        </select>
				         --> 
				        <select id="tblSpacId" name="tblSpacId" class=""  style="width:180px" disabled>
				            <option value="">선택</option>
				        </select> 
						
					</td>
				</tr> 
								
			</tbody>
		</table>
	</div>
	
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">오브젝트 정보</div>  <!-- 오브젝트 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>오브젝트 이름</caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   
                   <tr>
                       <th scope="row" class="th_require"><label for="ddlTblPnm">테이블명(물리명)</label></th> <!-- 테이블명(물리명) -->
                       <td>
                       		<input type="text" id="ddlTblPnm" name="ddlTblPnm"  class="wd300" value="${result.ddlTblPnm }" readonly />
                       		<!-- 
                       		<button class="btnDelPop" >삭제</button>
							<button class="btnSearchPop" id="objPnmPop">검색</button>
							 -->
						</td>
                       <th scope="row"><label for="ddlTblLnm">테이블명(논리명)</label></th> <!-- 테이블명(논리명) -->
                       <td>
                       		<input type="text" id="ddlTblLnm" name="ddlTblLnm"  class="wd300"  value="${result.ddlTblLnm }"  readonly />
                       	</td>                       
                   </tr>
                   
                   <tr>
                       <th scope="row"><label for="tblChgTypCd">테이블변경유형</label></th>   <!-- 테이블변경유형 -->
                       <td colspan="3">
                      	  <select id="tblChgTypCd" class="" name="tblChgTypCd" disabled>
							<option value="">선택</option>
							<c:forEach var="code" items="${codeMap.tblChgTypCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                       
                   </tr>
                   <tr>
                       <th scope="row"><label for="objDescn">설명</label></th>  <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly >${result.objDescn }</textarea></td>
                   </tr>
                   
                   <!-- 
                   <tr>
                       <th scope="row"><label for="scrtInfo">스크립트정보</label></th>
                       <td colspan="3"><textarea id="scrtInfo" name="scrtInfo" class="wd98p" rows="10" readonly>${result.scrtInfo }</textarea></td>
                   </tr>
                    -->
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
