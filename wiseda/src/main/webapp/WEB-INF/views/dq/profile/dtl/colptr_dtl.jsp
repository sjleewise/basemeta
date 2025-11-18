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
	//사용자 정의 유효값 초기화
	initPtrUserDfGrid();
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	//폼검증
	 $("form[name=frmInputPC05]").validate({
		rules: {
			dqiLnm		: "required"
		},
		messages: {
			dqiLnm		: requiremessage
		}
	});
	//사용자정의 유효값 버튼
	//그리드 추가
	$("div#div_ptr_userdf #btnNew_PC05").click(function(){
		grid_ptr_userdf_sheet.DataInsert(0);
	});
	//엑셀 업로드
	$("div#div_ptr_userdf #btnExcelLoad_PC05").click(function(){
		grid_ptr_userdf_sheet.LoadExcel({Mode:"HeaderMatch"});
	});
	//엑셀다운로드
	$("div#div_ptr_userdf #btnExcelDown_PC05").click(function(){
		grid_ptr_userdf_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	}).hide();
	
	// 품질지표 조회
    $("#frmInputPC05 #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFPC05"; 
    		param += "&dqiIds="+$("#frmInputPC05 #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });
	
});


$(window).on('load',function(){
});


$(window).resize( function(){
// 	grid_ptr_userdf_sheet.FitColWidth();  
});

function initPtrUserDfGrid()
{
   
	with(grid_ptr_userdf_sheet){
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headers = [
					{Text:"<s:message code='DQ.HEADER.COLPTR_DTL'/>", Align:"Center"}   //|객체설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
				];
		//삭제|No.|상태|요청구분|등록유형|검증결과|검토상태코드|사용자정의패턴|사용자정의패턴명

		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
	                {Type:"DelCheck", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
					{Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
	                {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:0, Hidden:1},						
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0,},						
					{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",		Align:"Center", Edit:0,},						
					{Type:"Text",   Width:250,  SaveName:"userDfPtr",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:250,  SaveName:"userDfPtrNm",	Align:"Left", 	 Edit:1}
// 					{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
// 					{Type:"Date",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
// 					{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:0},
// 					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
// 					{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0},
// 					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0}
				];
		
		InitColumns(cols);
		
		SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
// 		SetColProperty("regTypCd", ${codeMap.regTypCd});
// 		SetColProperty("vrfCd", ${codeMap.vrfCd});
// 		SetColProperty("rvwStsCd", ${codeMap.rvwStsCd});
		
		InitComboNoMatchText(1, "");
		
		SetColHidden("regTypCd",1);
		SetColHidden("vrfCd",1);
		SetColHidden("rvwStsCd",1);
// 		SetColHidden("objDescn",1);
// 		SetColHidden("rqstDtm",1);
// 		SetColHidden("rqstUserId",1);
// 		SetColHidden("rqstUserNm",1);
// 		SetColHidden("rqstNo",1);
// 		SetColHidden("rqstSno",1);
	  
		FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_ptr_userdf_sheet);	
	//===========================
	
	//grid_sheet.SetColHidden(Col, Hidden)
   
}	

function resetPC05(){
	//프로파일 상세정보 초기화
	$("form[name=frmInputPC05]")[0].reset();
	//유효패턴 그리드 초기화
	grid_ptr_userdf_sheet.RemoveAll();
}

function savePC05(){
	

	
	//사용자정의유효패턴
	var ibsSaveJsonPc05UserDf =grid_ptr_userdf_sheet.GetSaveJson(0);
	if($("form[name=frmAnaTrg] #regTypCd").val() == "D"){
		ibsSaveJsonPc05UserDf = grid_ptr_userdf_sheet.GetSaveJson(1);
	}
	
	//문자열패턴분석 삭제 일 경우 사용자정의유효값 전체 삭제
	if($("form[name=frmAnaTrg] #regTypCd").val() != "D"){
		
		//동일 중복컬럼 확인
		var DupRow = grid_ptr_userdf_sheet.ColValueDupRows("userDfPtr", {"CaseSensitive" : 0});
		if(DupRow != ""){
			var message = "<s:message code='SAME.USER.DFNT.PTRN.EXIS'/>"; /*동일한 사용자정의패턴이 존재 합니다.*/
			showMsgBox("INF", message); 
			return;
		}
		
		
		//사용자정의 유효값 그리드 삭제 선택일경우 삭제대상 건수 와 전체건수 비교
		//패턴분석은 사용자정의 패턴 없을수도 있음
		/* if(grid_ptr_userdf_sheet.FindStatusRow("D") != ""){
			var arrDelRows = grid_ptr_userdf_sheet.FindStatusRow("D").split(";").length ;
			if(arrDelRows == grid_ptr_userdf_sheet.GetTotalRows()){
				var message = "<s:message code='ERR.EMPTY' arguments="삭제대상 사용자정의유효패턴 건수와 총건수가 동일합니다." />";
				showMsgBox("ERR", message); 
				return;
			}
		} */
	}
	
	if(!$("form[name=frmInputPC05]").valid()){
		var message = "<s:message code='REQUIRED.INPT.ITEM' />";
		showMsgBox("INF", message); 
		return;
	}
	
	//저장
	var param =   "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
		 param += "&"+$("form[name=frmInputPC05]").serialize(); //패턴분석 정보
	
  	if(ibsSaveJsonPc05UserDf.data.length == 0){
		var urls = '<c:url value="/dq/profile/registerProfile.do"/>';
		ajax2Json(urls, param, ibscallback);
	}else{ 
		var urls = '<c:url value="/dq/profile/registerProfilePc05UserDf.do"/>';
 		IBSpostJson2(urls, ibsSaveJsonPc05UserDf, param, ibscallback);
 	} 
}

function setDtlPC05(data){
	json2formmapping($("form[name=frmInputPC05]"), data.resultVO);
	//사용자 정의 패턴 조회
	param="";
	param = "&prfId="+data.resultVO.prfId; 
	grid_ptr_userdf_sheet.DoSearch("<c:url value="/dq/profile/getColPrfPC05UserLst.do" />", param);
	
}

//DQI 팝업 리턴값 처리
function returnDqiPopPC05 (ret) {
	//초기화
	$("#frmInputPC05 #dqiLnm").val("");
	$("#frmInputPC05 #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInputPC05 #dqiLnm").val() != null && $("#frmInputPC05 #dqiLnm").val() != "undefined" && $("#frmInputPC05 #dqiLnm").val() != "" ){
			$("#frmInputPC05 #dqiLnm").val($("#frmInputPC05 #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInputPC05 #dqiId").val($("#frmInputPC05 #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInputPC05 #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInputPC05 #dqiId").val(parsejson.dqiId);
		}
	}
}

function grid_ptr_userdf_sheetl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0 ) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
	<div id="searchPC05_div" >
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="STRING.PTRN.ANLY.DTL.INFO"/></div><!--문자열패턴분석 상세정보-->
		<div style="clear:both; height:5px;"><span></span></div>
		
		<form id="frmInputPC05" name="frmInputPC05" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic2" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRING.PTRN.ANLY'/>"><!-- /*문자열패턴분석*/ -->
			   <caption><s:message code="STRING.PTRN.ANLY"/></caption><!--문자열패턴분석-->
			   <colgroup>
			   	<col style="width:15%;" />
		   		<col style="width:85%;" />
			   </colgroup>
			       <tbody>   
			       	   <tr>                               
			               <th scope="row"><label for="adtCndSql"><s:message code="ADDT.COND"/></label></th><!--추가조건-->
			               <td>
			                   <input type="text"  class="wd98p" name="adtCndSql" id="adtCndSql" />
			               </td>
			           </tr>
		           <tr>
		               <th scope="row" class="th_require"><label for="dqiSearch"><s:message code="QLTY.INDC"/></label></th><!--품질지표-->
		               <td>
		                   <input type="text"  style="width: 70%;" name="dqiLnm" id="dqiLnm" readonly/>
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
			
		<div id="div_ptr_userdf" name="div_ptr_userdf">
			<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="USER.DFNT.PTRN.DTL.INFO"/></div><!--사용자정의패턴 상세정보-->
			<div class="tb_usr_ptr_comment"><s:message code="USR.PTR" /></div>
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="tb_usr_ptr_comment"><s:message code="USR.PTR2" /></div>
			<div style="clear:both; height:10px;"><span></span></div>
			
			<!-- 추가 엑셀내리기  -->
			<div class="divLstBtn" >	 
            <div class="bt03">
                <button class="btn_rqst_new" id="btnRqstNew_PC05" name="btnRqstNew_PC05"><s:message code="ADDT" /></button><!--추가-->
				  <ul class="add_button_menu" id="addButtonMenu_PC05">
				    <li class="btn_new" id="btnNew_PC05"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li><!--신규 추가-->
				    <li class="btn_excel_load" id="btnExcelLoad_PC05"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UP" /></a></li><!--엑셀 올리기-->
				  </ul>         
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown_PC05" name="btnExcelDown_PC05"><s:message code="EXCL.DOWNLOAD" /></button><!--엑셀 내리기-->
	    	</div>
        </div>
			<!-- 검색조건 입력폼 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<form id="frmInputPC05_userdf" name="frmInputPC05_userdf" method="post">
			<div id="grid_ptr_userdf" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_ptr_userdf_sheet", "100%", "130px");</script>            
			</div>
			</form>
		</div>		
			
			
	</div>
	
<!-- </body> -->
<!-- </html> -->
