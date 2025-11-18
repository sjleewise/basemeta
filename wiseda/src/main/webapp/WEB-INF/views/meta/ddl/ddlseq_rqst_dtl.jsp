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
<title>DDL시퀀스상세정보</title>

<script type="text/javascript">

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
			ddlSeqPnm	: "required",
			dbConnTrgPnm 	: "required", 
			dbSchPnm 	: "required",
			nmRlTypCd 	: "required", 
			usTypCd 	: "required",
			incby 	: "required", 
			strtwt 	: "required", 
			minval 	: "required", 
			maxval 	: "required",
			aplReqdt : "required", 
			grtLst : "required", 
			seqInitCd	: {
				required: function(){
					if($('#usTypCd').val() == 'D' ){
						return true;
					}
					return false;
				}
			},
			bizIfnc			: {
				required: function(){
					if($('#seqInitCd').val() == '8' || $('#seqInitCd').val() == '9'){
						return true;
					}
					return false;
				}
			},
			rqstResn		: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlSeqPnm	: requiremessage,
			dbConnTrgPnm 	: requiremessage, 
			dbSchPnm 	: requiremessage,
			nmRlTypCd 	: requiremessage,
			usTypCd 	: requiremessage,
			incby 	: requiremessage,
			strtwt 	: requiremessage,
			minval 	: requiremessage,
			maxval 	: requiremessage,
			aplReqdt 	: requiremessage,
			seqInitCd 	: requiremessage,
			bizIfnc 	: requiremessage,
			grtLst 	: requiremessage,
			rqstResn	: requiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	

	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmInput #rvwConts").val());
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//요청사유 길이 체크
	/* 	if (chechkByteSize($("#frmInput #rqstResn").val()) < 20) {
			showMsgBox("INF", "요청사유는 한글기준 최소 10자 이상 입력하세요.");
			return;
		} */
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		resetForm($("form#frmInput"));
		//케시사이즈초기값 100	
		$("#frmInput #cacheSz").val("100"); 
		$("#frmInput #cycYn").val("Y"); 

	});
	
	//명명규칙 변경 이벤트
	$("#nmRlTypCd").change(function(){ 
		if($("#frmInput #nmRlTypCd").val() == "E"){
			$("#tableTr").show();
			$("#colTr").hide();
			$("#l1l3Tr").hide();			
			
		}else if($("#frmInput #nmRlTypCd").val() == "C"){
			$("#tableTr").hide();
			$("#colTr").show();
			$("#l1l3Tr").hide();

		}else if($("#frmInput #nmRlTypCd").val() == "S"){
			$("#tableTr").hide();
			$("#colTr").hide();
			$("#l1l3Tr").show();
		}
		
		$("#frmInput #ddlSeqPnm").val("");
		$("#frmInput #pdmTblPnm").val("");
		$("#frmInput #pdmColPnm").val("");
		$("#frmInput #l1cdPnm").val("");
		$("#frmInput #l3cdPnm").val("");
	});
	
	
	//사용용도 변경 이벤트
	$("#usTypCd").change(function(){ 
		if($("#frmInput #usTypCd").val() != ""){
			if($("#frmInput #nmRlTypCd").val() == "E"){
				$("#frmInput #ddlSeqPnm").val($("#frmInput #pdmTblPnm").val() + "_S" + $("#frmInput #usTypCd").val() + "##");		
				
			}else if($("#frmInput #nmRlTypCd").val() == "C"){
				$("#frmInput #ddlSeqPnm").val($("#frmInput #pdmColPnm").val() + "_S" + $("#frmInput #usTypCd").val() + "#");

			}else if($("#frmInput #nmRlTypCd").val() == "S"){
				$("#frmInput #ddlSeqPnm").val($("#frmInput #l1cdPnm").val() + $("#frmInput #l3cdPnm").val() + "###" + "N_##" + "_S" + $("#frmInput #usTypCd").val()); 
						
			}
		}		
	});
    
	
	
	//DBMS 스키마 검색 팝업 호출
	$("#DbmsSchemaPop").click(function(){
    	var param = "dbConnTrgPnm=" + $("form#frmInput #dbConnTrgPnm").val();
    		param += "&dbSchPnm=" + $("form#frmInput #dbSchPnm").val();
    		param += "&tblSpacTypCd=T";
    		param += "&ddlTrgDcd=D";
    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbschema_pop.do' />", 900, 600, param);
    });
	
	//L1/L3코드 검색 팝업 호출
	$("#subjL1L3CdSearchPop").click(function(){
		var param = "sFlag=L"; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/subjAppCdSearchPop.do' />", 900, 600, param);
    });
	
	//권한목록 검색 팝업 호출
	$("#subjGrtLstSearchPop").click(function(){
		var param = "sFlag=G"; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/subjAppCdSearchPop.do' />", 900, 600, param);
    });
	
	//테이블 검색 팝업 호출
	$("#pdmTblSearchPop").click(function(){
		var url   = "<c:url value="/meta/model/popup/pdmtblSearchPop.do"/>";
		var param = "";
		openLayerPop(url, 900, 600, param);
    });
	
	//컬럼 검색 팝업 호출
	$("#pdmColSearchPop").click(function(){
		var param = "";
    	openLayerPop("<c:url value='/meta/model/popup/pdmcol_pop.do' />", 900, 600, param);
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
	if('${result.rqstDcd}'!=""){$("#frmInput #rqstDcd").val('${result.rqstDcd}');}
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');

	$("#frmInput #nmRlTypCd").val('${result.nmRlTypCd}');
	$("#frmInput #usTypCd").val('${result.usTypCd}');
	$("#frmInput #cycYn").val('${result.cycYn}');
	$("#frmInput #ordYn").val('${result.ordYn}');
	$("#frmInput #aplReqTypCd").val('${result.aplReqTypCd}');
	$("#frmInput #seqClasCd").val('${result.seqClasCd}');
	$("#frmInput #seqInitCd").val('${result.seqInitCd}');
	
	//케시사이즈초기값 100	
	if("${saction }" == "I"){
		$("#frmInput #cacheSz").val("100"); 
		$("#frmInput #cycYn").val("Y");
	}
	
	if("${result.nmRlTypCd}" == "E"){
		$("#tableTr").show();
		$("#colTr").hide();
		$("#l1l3Tr").hide();			
		
	}else if("${result.nmRlTypCd}" == "C"){
		$("#tableTr").hide();
		$("#colTr").show();
		$("#l1l3Tr").hide();

	}else if("${result.nmRlTypCd}" == "S"){
		$("#tableTr").hide();
		$("#colTr").hide();
		$("#l1l3Tr").show();
	}
	
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();
	
// 	$("#nmRlTypCd").change();
	//달력팝업 추가...
 	$( "#aplReqdt" ).datepicker();
	
 	//프로젝트 검색 팝업 호출
	$("#prjSearchPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/projSearchPop.do' />", 800, 600, param);
    }); 
	//sr번호/프로젝트번호 초기화 
	$("#prjbtnDelPop").click(function(){
		$("#frmInput #prjMngNo").val("");
		$("#frmInput #srMngNo").val("");
    });
	
	//요청서 상태에 따른 버튼 show //sr번호/프로젝트번호만 
	setDispRqstSrMngNoButton($("#mstFrm #rqstStepCd").val());	
	//DDL이관
	if($("#mstFrm #bizDcd").val() == "DTT" || $("#mstFrm #bizDcd").val() == "DTR"){
		$("#btnGridSave").hide();
		$("#btnReset").hide();
// 		$("#aplReqDtAllApply").show();
	}else{
// 		$("#aplReqDtAllApply").hide();
	}
	

});

//프로젝트 팝업 리턴값 처리
function returnPrjPop (ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	$("#frmInput #prjMngNo").val(retjson.prjMngNo);
	$("#frmInput #srMngNo").val(retjson.srMngNo);
	
}

//프로젝트 팝업 리턴값 처리
function returnPrjAllPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	$("#frmInput #prjMngNo").val(retjson.prjMngNo);
	$("#frmInput #srMngNo").val(retjson.srMngNo);
	vPrjMngNo = retjson.prjMngNo;
	vSrMngNo = retjson.srMngNo;
	doAction("updateSrMngNo");
}			

//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
	$("#frmInput #dbSchId").val(retjson.dbSchId);

}

//L1/L3코드 팝업 리턴값 처리
function returnSubjL1L3CdPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #l1cdPnm").val(retjson.l1cdPnm);
	$("#frmInput #l3cdPnm").val(retjson.l3cdPnm);
	$("#frmInput #ddlSeqPnm").val(retjson.l1cdPnm + retjson.l3cdPnm + "###" + "N_##" + "_S" + $("#frmInput #usTypCd").val());

}

//권한목록 팝업 리턴값 처리
function returnSubjGrtLstPop(ret) {

	var saveJson = jQuery.parseJSON(ret);
	
	var grtLst = "";
	for(var i=0; i<saveJson.data.length; i++) {
		grtLst += saveJson.data[i].l3cdPnm + ";";
		
	}

	$("#frmInput #grtLst").val(grtLst);
}

//테이블 팝업 리턴값 처리
function returnPdmtblPop (ret) {
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #pdmTblPnm").val(retjson.pdmTblPnm);
	$("#frmInput #ddlSeqPnm").val(retjson.pdmTblPnm + "_S" + $("#frmInput #usTypCd").val() + "##");
	$("#frmInput #grtLst").val(retjson.l3cdPnm);
	
	if(retjson.prjMngNo != null){
		$("#frmInput #prjMngNo").val(retjson.prjMngNo);
		$("#frmInput #srMngNo").val(retjson.srMngNo);
	}	
}

//컬럼 팝업 리턴값 처리...
function returnPdmColPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #pdmColPnm").val(retjson.pdmColPnm);
	$("#frmInput #ddlSeqPnm").val(retjson.pdmColPnm + "_S" + $("#frmInput #usTypCd").val() + "#");
}



</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
    <fieldset>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
	<!-- 2018.11.02 사용안함  -->
	<%--
	<div style="clear:both; height:10px;"><span></span></div>
	<%-- <div class="stit">프로젝트 정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>프로젝트정보</legend> --%>
	<div class="tb_basic" style="display: none;/* 2018.11.02 베이직버전 사용안함  */">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>프로젝트정보</caption>
			<colgroup>
			<col style="width:15%;" />
			<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr >
					<th scope="row" id="tdsrMngNo">SR번호/서브프로젝트번호</th>
					<td colspan="3">
						<!-- 프로젝트넘버 저장을 위한 히든 컬럼 -->
<%-- 						<input type="hidden" id="prjMngNo" name="prjMngNo" value="${result.prjMngNo }"/> --%>
						<span class="input_inactive"><input type="text" class="wd240" id="srMngNo" name="srMngNo"  value="${result.srMngNo }" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd240" id="prjMngNo" name="prjMngNo"  value="${result.prjMngNo }" readonly="readonly"/></span>
						 	<button class="btnDelPop" id="prjbtnDelPop">삭제</button>
						 	<button class="btnSearchPop" id="prjSearchPop">검색</button>
<!-- 						 	<button class="prjAllApply" id="prjAllApply" 	name="prjAllApply">전체반영</button> -->
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">DBMS 정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>DBMS 정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DBMS 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require">DBMS 정보</th>
					<td colspan="3">
						<span class="input_inactive"><input type="hidden" class="wd100" id="dbSchId" name="dbSchId"  value="${result.dbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm"  value="${result.dbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm"  value="${result.dbSchPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="DbmsSchemaPop">검색</button>
					</td>
					
					
				</tr>
			</tbody>
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">시퀀스 정보</div>
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>
               시퀀스 이름
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>

                   <tr>
                       <th scope="row" class="th_require"><label for="ddlSeqPnm">시퀀스물리명</label></th>
                       <td colspan="3"><input type="text" id="ddlSeqPnm" name="ddlSeqPnm"  class="wd300" value="${result.ddlSeqPnm }" /></td>
                       <th scope="row" style="display:none;"><label for="ddlSeqLnm">시퀀스논리명</label></th>
                       <td style="display:none;"><input type="text" id="ddlSeqLnm" name="ddlSeqLnm"  class="wd300"  value="${result.ddlSeqLnm }"  /></td>
                       
                   </tr>
                    <tr style="display:none;/* 2018.11.02 베이직버전 사용안함  */">
                       <th scope="row" class="th_require"><label for="nmRlTypCd">명명규칙</label></th>
                       <td>
                       		<select id="nmRlTypCd" name="nmRlTypCd" value = "${result.nmRlTypCd}" class="wd100">
                        		<option value="" selected></option>
                        	<c:forEach var="code" items="${codeMap.nmRlTypCd}" varStatus="status" >
                               <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                       </td >
                       <th scope="row" class="th_require"><label for="usTypCd">사용용도</label></th>
                       <td>
                       		<select id="usTypCd" name="usTypCd" value = "${result.usTypCd}" class="wd100">
								<option value="" selected></option>
								<c:forEach var="code" items="${codeMap.usTypCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select>
                      </td>
                       
                   </tr>
                   
                   <tr id="tableTr" style="display:none;/* 2018.11.02 베이직버전 사용안함  */" >
                       <th scope="row" ><label for="pdmTblPnm">테이블명</label></th>
                       <td colspan="3">
                       		<input type="text" id="pdmTblPnm" name="pdmTblPnm"  class="wd300" value="${result.pdmTblPnm }" />
                       		<button class="btnDelPop" >삭제</button>
						 	<button class="btnSearchPop" id="pdmTblSearchPop">검색</button>
                       </td>
                   </tr>
                   <tr id="colTr" style="display:none;">
                   		<th scope="row"><label for="pdmColPnm">컬럼명</label></th>
                       <td colspan="3">
                       		<input type="text" id="pdmColPnm" name="pdmColPnm"  class="wd300"  value="${result.pdmColPnm }"  />
                       		<button class="btnDelPop" >삭제</button>
						 	<button class="btnSearchPop" id="pdmColSearchPop">검색</button>
                       </td>
                   </tr>
                   <tr id="l1l3Tr" style="display:none;"> 
						<th scope="row">L1코드/L3코드</th>
						<td colspan="3">
							<span class="input_inactive"><input type="text" class="wd150" id="l1cdPnm" name="l1cdPnm"  value="${result.l1cdPnm}" readonly="readonly"/></span>
							<span class="input_inactive"><input type="text" class="wd150" id="l3cdPnm" name="l3cdPnm"  value="${result.l3cdPnm}" readonly="readonly"/></span>
						 	<button class="btnDelPop" >삭제</button>
						 	<button class="btnSearchPop" id="subjL1L3CdSearchPop">검색</button>
						</td>
					</tr>
					<tr style="display:none;/* 2018.11.02 베이직버전 사용안함  */">
                   	   <th scope="row" class="th_require"><label for="grtLst">권한목록</label></th>
                       <td colspan="3">
                       		<input type="text" id="grtLst" name="grtLst"  class="wd400" value="${result.grtLst }" />
                       		<button class="btnDelPop" >삭제</button>
						 	<button class="btnSearchPop" id="subjGrtLstSearchPop">검색</button>
                       </td>
                   </tr>
					
					<tr>
                       <th scope="row" class="th_require" ><label for="incby">INCREMENT BY</label></th>
                       <td><input type="text" id="incby" name="incby"  class="wd300" value="${result.incby }" /></td>
                       <th scope="row" class="th_require" ><label for="strtwt">START WITH</label></th>
                       <td><input type="text" id="strtwt" name="strtwt"  class="wd300"  value="${result.strtwt }"  /></td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require" ><label for="minval">MIN VALUE</label></th>
                       <td><input type="text" id="minval" name="minval"  class="wd300"  value="${result.minval }"  /></td>
                       <th scope="row" class="th_require" ><label for="maxval">MAX VALUE</label></th>
                       <td><input type="text" id="maxval" name="maxval"  class="wd300" value="${result.maxval }" /></td>
                       
                   </tr>
                   <tr>
                       <th scope="row" ><label for="cycYn">CYCLE 여부</label></th>
                       <td>
                       		<select id="cycYn"  name="cycYn" value="${result.cycYn}" class="wd100">
							       <option value="" selected></option>
							       <option value="Y">Y</option>
							       <option value="N">N</option>
							</select>                       		
                       </td>
                       <th scope="row" ><label for="ordYn">ORDER 여부</label></th>
                       <td>
                       		<select id="ordYn"  name="ordYn" value="${result.ordYn}" class="wd100">
							       <option value="" selected></option>
							       <option value="Y">Y</option>
							       <option value="N">N</option>
							</select>                       		
                       </td>
                   </tr>
                   <tr>
                   	   <th scope="row" ><label for="cacheSz">CACHE SIZE</label></th>
                       <td colspan="3">
                       		<input type="text" id="cacheSz" name="cacheSz"  class="wd300" value="${result.cacheSz }" />
                       </td>
                   </tr>
		
                   <tr style="display:none;/* 2018.11.02 베이직버전 사용안함  */">
                       <th scope="row" ><label for="pckgNm">패키지명</label></th>
                       <td>
                       		<input type="text" id="pckgNm" name="pckgNm"  class="wd300" value="${result.pckgNm }" />                  		
                       </td>                   
                       <th scope="row" ><label for="seqClasCd">클래스명</label></th>
                       <td>
                       		<select id="seqClasCd"  name="seqClasCd" class="wd100">
								<option value="" selected>선택</option>
								<c:forEach var="code" items="${codeMap.seqClasCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>                       
							</select>                      		
                       </td>
					</tr>
					<tr style="display:none;/* 2018.11.02 베이직버전 사용안함  */">
                       <th scope="row"  class=""><label for="seqInitCd">초기화구분코드</label></th>
                       <td>
                       		<select id="seqInitCd"  name="seqInitCd" class="wd200">
								<option value="" selected>선택</option>
								<c:forEach var="code" items="${codeMap.seqInitCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>                       
							</select>                        		
                       </td>
                       <th scope="row" ><label for="bizIfnc">초기화실패업무영향도</label></th>
                       <td>
                       		<input type="text" id="bizIfnc" name="bizIfnc"  class="wd98p" value="${result.bizIfnc }" />                  		
                       </td>                        
                   </tr>                   
                   
                   <tr style="display:none;/* 2018.11.02 베이직버전 사용안함  */" >
                       <th scope="row" id="tdaplReqTypCd"><label for="aplReqTypCd">적용요청구분</label></th>
                       <td>
                      	  <select id="aplReqTypCd" class="" name="aplReqTypCd">
							<option value="">선택</option>
							<c:forEach var="code" items="${codeMap.aplReqTypCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                   	   <th scope="row" id="tdaplReqdt" class="th_require"><label for="aplReqdt">적용요청일자</label></th>
                       <td>
                       		<span class="input_inactive"><input type="text" id="aplReqdt" name="aplReqdt" class="dtlInput" value="${result.aplReqdt }" readonly /></span>
                       </td>
                   </tr>

                   <tr>
                       <th scope="row"><label for="objDescn">설명</label></th>
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="scrtInfo">스크립트정보</label></th>
                       <td colspan="3"><textarea id="scrtInfo" name="scrtInfo" class="wd98p" rows="10" readonly>${result.scrtInfo }</textarea></td>
                   </tr>
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
