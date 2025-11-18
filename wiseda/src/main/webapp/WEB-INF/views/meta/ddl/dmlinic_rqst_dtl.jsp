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
<title><s:message code="DMLDEMD.DTL.INFO" /></title><!-- DML요청상세정보 -->

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
			rqstSubj		: "required",
			rqstNm	: "required",
			prcDt 	: "required", 
			prcDbaNm 	: "required", 
		},
		messages: {
			rqstDcd		: requiremessage,
			rqstSubj		: requiremessage,
			rqstNm	: requiremessage,
			prcDt 	: requiremessage, 
			prcDbaNm 	: requiremessage, 
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = dbschema_grid.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmInput #rvwConts").val());
			dbschema_grid.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));
		/* var row = dbschema_grid.GetSelectRow();
		if(row < 1) {
			$("form#frmInput")[0].reset();
		    //선택행 셋팅..
		    var tmptit = "테이블을 선택하세요.";
		    $("#tbl_sle_title").html(tmptit);
		} else {
			tblClick(row);
		} */
		
	});
	
	
	//DBMS 스키마 검색 팝업 호출
	$("#DbmsSchemaPop").click(function(){
    	var param = "dbConnTrgPnm=" + $("form#frmInput #dbConnTrgPnm").val();
    		param += "&dbSchPnm=" + $("form#frmInput #dbUserPnm").val();
    		param += "&tblSpacTypCd=T";
    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbschema_pop.do' />", 600, 500, param);
    });
	
	//담당자 검색 팝업 호출
	$("#userPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	

    
// 	$("#frmInput #stwdLnm").focus();

	//alert('1');
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
	
	$("#frmInput #reqRsnCd").val('${result.reqRsnCd}');
	$("#frmInput #workObjtCd").val('${result.workObjtCd}');
	$("#frmInput #jobDvdCd").val('${result.jobDvdCd}');
	$("#frmInput #workDvdCd").val('${result.workDvdCd}');
	
	//check box 값 초기화...
	
	
	$( "#prcDt" ).datepicker();
	

});


//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #prcDbaId").val(retjson.userId);
	$("#frmInput #prcDbaNm").val(retjson.userNm);
}


function initdbschemaGrid()
{
    
    with(dbschema_grid){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DMLINIC.RQST.DTL'/>";
        /* No.|상태|선택|DBMS유형|DBMS버전|DBMSID|DBMS명|DBMS논리명|DB스키마ID|DB스키마|DB스키마(논리명)|테이블스페이스|설명 */
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50, SaveName:"ibsCheck",      Align:"Center", Edit:1, Sort:0},
                    {Type:"Combo",  Width:80,   SaveName:"dbmsTypCd",    Align:"Center", Edit:0},
                    {Type:"Combo",  Width:80,   SaveName:"dbmsVersCd",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:150, SaveName:"dbConnTrgId",   Align:"Left",   Edit:0},
                    {Type:"Text",   Width:150, SaveName:"dbConnTrgPnm",  Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"dbConnTrgLnm", Align:"Left",   Edit:0},
                    
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    Align:"Left", Edit:0}, 
					{Type:"Text",   Width:100,  SaveName:"dbSchLnm",    Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"dbTblSpacPnm",    Align:"Left", Edit:0},

                    {Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("dbmsTypCd", 	${codeMap.dbmstypcdibs});
         SetColProperty("dbmsVersCd", 	${codeMap.dbmsverscdibs});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("dbConnTrgId",1);
        SetColHidden("dbSchId",1);
        SetColHidden("dbmsVersCd",1);
//         SetColHidden("dbConnTrgLnm",1);
//         SetColHidden("dbSchLnm",1);
        
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(dbschema_grid);    
    //===========================
   
}


//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #tblSpacPnm").val(retjson.dbTblSpacPnm);
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbUserPnm").val(retjson.dbSchPnm);
// 	$("#frmInput #tblSpacPnm").val(retjson.tblSpacPnm);
}


function dbschema_grid_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
		//TODO : 조회 후 체크박스 처리 필요
		
	}
	
}

</script>
</head>
<body>
<!--     private String rqstSubj;	//요청서 제목 -->
<!--     private String rqstNm;		 -->
<!--     private String reqRsnCd; -->
<!--     private String workObjtCd; -->
<!--     private String jobDvdCd; -->
<!--     private String workDvdCd; -->
<!--     private String dbSchIds; -->
<!--     private String prcDt; -->
<!--     private String prcDbaId; -->
<!--     private String prcDbaNm; -->
<!--     private String atchFileId; -->
<!--     private String rqstDtlNm; -->
<!--     private String uiChk; -->
<!--     private String dbScrt; -->
<!--     private String etcNm; -->
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
	  	<input type="hidden" id="dbSchIds" name="dbSchIds" value="${result.dbSchIds}" >
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
	
	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="DB.JOB.DEMD.CNTN" /></div> <!-- DB작업 요청 내용 -->
 	<div style="clear:both; height:5px;"><span></span></div>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"><!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="TBL.NM" /><!-- 테이블 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                       <th scope="row" class="th_require"><label for="rqstSubj"><s:message code="TTL" /></label></th><!-- 제목 -->
                       <td colspan="3"><input type="text" id="rqstSubj" name="rqstSubj"  class="wd300" value="${result.rqstSubj }" title="<s:message code='MSG.TTL.INPT' />" /></td><!-- 제목은 반드시 입력해야 합니다. -->
                   </tr>
                   <tr>
                       <th scope="row"><label for="reqRsnCd"><s:message code="DEMD.RSN" /></label></th><!-- 요청사유 -->
                       <td>
                      	  <select id="reqRsnCd" class="" name="reqRsnCd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.reqRsnCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                       <th scope="row"><label for="workObjtCd"><s:message code="JOB.TRGT" /></label></th><!-- 작업대상 -->
                       <td>
                      	  <select id="workObjtCd" class="" name="workObjtCd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.workObjtCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                     </tr>
                     <tr>
                       <th scope="row"><label for="jobDvdCd"><s:message code="BZWR.DSTC" /></label></th><!-- 업무구분 -->
                       <td>
                      	  <select id="jobDvdCd" class="" name="jobDvdCd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.jobDvdCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                       <th scope="row"><label for="workDvdCd"><s:message code="JOB.DSTC" /></label></th><!-- 작업구분 -->
                       <td>
                      	  <select id="workDvdCd" class="" name="workDvdCd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.workDvdCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                       
                   </tr>
                   <tr>
                       <th scope="row"><label for="rqstNm"><s:message code="DEMD.DETL" /></label></th><!-- 요청내역 -->
                       <td colspan="3"><textarea id="rqstNm" name="rqstNm" class="wd98p">${result.rqstNm }</textarea></td>
                   </tr>
               </tbody>
           </table>
       </div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code="DB.MS.INFO" /></div><!-- DBMS 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="DB.MS.INFO" /></legend><!-- DBMS 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_dbschema" class="grid_dbschema">
	     <script type="text/javascript">createIBSheet("dbschema_grid", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "dbschema_grid", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="DTL.DEMD.CNTN" /></div><!-- 상세요청내용 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"><!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="TBL.NM" /><!-- 테이블 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                       <th scope="row" class="th_require"><label for="prcDt"><s:message code="APL.DEMD.DT" /></label></th> <!-- 적용요청일자 -->
                       <td colspan="3"><input type="text" id="prcDt" name="prcDt"  class="wd100" value="${result.prcDt }" title="<s:message code='MSG.APL.DEMD.DT.INPT' />" /></td> <!-- 적용요청일자는 반드시 입력해야 합니다. -->
                       
                   </tr>
                   <tr>
                       <th scope=
                       "row" class="th_require"><label for="prcDbaId"><s:message code="CHRG.DB.A" /></label></th> <!-- 담당DBA -->
                       <td colspan="3">
							<span class="input_inactive"><input type="hidden" id="prcDbaId" name="prcDbaId" class="wd220" value="${result.prcDbaId}"  title="<s:message code='MSG.CHRG.DB.A.NM.INPT' />" /></span> <!-- 담당DBA 명은 반드시 입력해야 합니다. -->
							<span class="input_inactive"><input type="text" id="prcDbaNm" name="prcDbaNm" class="wd220" value="${result.prcDbaNm}"  title="<s:message code='MSG.CHRG.DB.A.NM.INPT' />" /></span> <!-- 담당DBA 명은 반드시 입력해야 합니다. -->
							<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
							<button class="btnSearchPop" id="userPop" name="userPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						</td>
                       
                   </tr>
                   <tr>
                       <th scope="row"><label for="rqstDtlNm"><s:message code="DEMD.DETL.DTL" /></label></th> <!-- 요청내역(상세) -->
                       <td colspan="3"><textarea id="rqstDtlNm" name="rqstDtlNm" class="wd98p" rows="10">${result.rqstDtlNm }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="uiChk"><s:message code="UI.VRFC" /></label></th> <!-- UI검증 -->
                       <td colspan="3"><textarea id="uiChk" name="uiChk" class="wd98p" rows="10">${result.uiChk }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="dbScrt"><s:message code="DB.SQL.VRFC" /></label></th> <!-- DB(SQL 검증) -->
                       <td colspan="3"><textarea id="dbScrt" name="dbScrt" class="wd98p" rows="10">${result.dbScrt }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="etcNm"><s:message code="ETC.MTR" /></label></th> <!-- 기타사항 -->
                       <td colspan="3"><textarea id="etcNm" name="etcNm" class="wd98p" rows="10">${result.etcNm }</textarea></td>
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
