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
	$("#frmInputPT02").validate({
		rules: {
			objNm		: "required",
			dqiLnm		: "required",
		},
		messages: {
			objNm		: requiremessage,
			dqiLnm		: requiremessage
		}
	});
	
	
	//중복컬럼 그리드 초기화
	initUnqColGird();
	
	//중복컬럼 버튼
	//그리드 추가
	$("#btnRqstNew_PT02").click(function(){
		//event.preventDefault();	//브라우저 기본 이벤트 제거...
    	colPopup();
	});
	
	//엑셀 업로드
	$("div#div_udq_col #btnExcelLoad_PT02").click(function(){
		grid_unq_col_sheet.LoadExcel({Mode:"HeaderMatch"});
	});
	
	//엑셀다운로드
	$("div#div_udq_col #btnExcelDown_PT02").click(function(){
		grid_unq_col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	}).hide();
	
	// 품질지표 조회
    $("#frmInputPT02 #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFPT02"; 
    		param += "&dqiIds="+$("#frmInputPT02 #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });		
});


$(window).on('load',function(){
});


$(window).resize( function(){
	grid_unq_col_sheet.FitColWidth();  
});

function initUnqColGird()
{
   
	with(grid_unq_col_sheet){
		var cfg = {SearchMode : 2,Page : 100	};
		SetConfig(cfg);

		var headers = [ {Text : "<s:message code='DQ.HEADER.TBLUNQ_DTL'/>", Align : "Center"	} ];
		//삭제|No|상태|중복컬럼명|중복컬럼한글명

		 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [
                   {Type:"DelCheck", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
   				   {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:0},
   	               {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                   {Type:"Text",   Width:300,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, KeyField:1}, 
                   {Type:"Text",   Width:300,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0},
               ];

		//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
		InitColumns(cols);

		InitComboNoMatchText(1, "");

		FitColWidth();
		
		//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다    
		SetExtendLastCol(1);
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_unq_col_sheet);	
	//===========================
	
	//grid_sheet.SetColHidden(Col, Hidden)
   
}

/* 컬럼조회  */
function colPopup(){
	if($("form[name=frmAnaTrg] #dbConnTrgId").val() == ""){
		var message = "<s:message code="VALID.PRFREQUIRED" />";
		showMsgBox("INF", message); 
		return;
	}
  	var url = '<c:url value="/dq/criinfo/anatrg/popup/anatrgcol_pop.do"/>';
	var param = $("form[name=frmAnaTrg]").serialize(); 
	openLayerPop(url ,800 ,650,  param);
} 

//DQI 팝업 리턴값 처리
function returnDqiPopPT02 (ret) {
	//초기화
	$("#frmInputPT02 #dqiLnm").val("");
	$("#frmInputPT02 #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInputPT02 #dqiLnm").val() != null && $("#frmInputPT02 #dqiLnm").val() != "undefined" && $("#frmInputPT02 #dqiLnm").val() != "" ){
			$("#frmInputPT02 #dqiLnm").val($("#frmInputPT02 #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInputPT02 #dqiId").val($("#frmInputPT02 #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInputPT02 #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInputPT02 #dqiId").val(parsejson.dqiId);
		}
	}
}

function returnAnaColPop (ret) {
	var iRow = "";
	var retjson = JSON.stringify(ret.data);
	var arrRetjson = jQuery.parseJSON(retjson);
	
	for(var i=0; i<arrRetjson.length; i++){
		//마지막행 생성
		iRow = grid_unq_col_sheet.DataInsert(-1);
		grid_unq_col_sheet.SetCellValue(iRow, "dbcColNm", arrRetjson[i].dbcColNm);
		grid_unq_col_sheet.SetCellValue(iRow, "dbcColKorNm", arrRetjson[i].dbcColKorNm);
	}
}

function resetPT02(){
	//프로파일 상세정보 초기화
	$("form[name=frmInputPT02]")[0].reset();
	//유효패턴 그리드 초기화
	grid_unq_col_sheet.RemoveAll();
}

function savePT02(){
	//form 체크
	if(!$("#frmInputPT02").valid()) return false;
	
	//중복컬럼
	//var ibsSaveJson=grid_unq_col_sheet.GetSaveJson(0);
	var ibsSaveJson=grid_unq_col_sheet.GetSaveJson(1);
	
	//중복분석 삭제 일 경우 중복컬럼 전체 삭제
	if($("form[name=frmAnaTrg] #regTypCd").val() != "D"){
		//중복컬럼 입력 확인
		if(grid_unq_col_sheet.GetTotalRows() == ""){ 
			var message = "<s:message code='ERR.EMPTY' arguments="<s:message code='DUP.CLMN.INPT'/>" />";//중복컬럼을 입력하십시오.
			showMsgBox("INF", message); 
			return;
		}
		
		//동일 중복컬럼 확인
		var DupRow = grid_unq_col_sheet.ColValueDupRows("dbcColNm");
		if(DupRow != ""){
			var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='SAME.DUP.CLMN.EXIS'/>" />";/*동일한 중복컬럼이 존재 합니다.*/
			showMsgBox("INF", message); 
			return;
		}
		
		//중복컬럼 그리드 삭제 선택일경우 삭제대상 건수 와 전체건수 비교
		if(grid_unq_col_sheet.FindStatusRow("D") != ""){
			var arrDelRows = grid_unq_col_sheet.FindStatusRow("D").split(";").length ;
			if(arrDelRows == grid_unq_col_sheet.GetTotalRows()){
				var message = "<s:message code='ERR.EMPTY' arguments="<s:message code='DEL.TRGT.DUP.CLMN.CCNT.TCNT.SAME'/>" />"; /*삭제대상 중복컬럼 건수와 총건수가 동일합니다.*/

				showMsgBox("INF", message); 
				return;
			}
		}
	}
	
	//저장
	var param =   "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
		 param += "&"+$("form[name=frmInputPT02]").serialize(); //중복분석 정보
		 
	var urls = '<c:url value="/dq/profile/registerProfilePT02.do"/>';
	IBSpostJson2(urls, ibsSaveJson, param, ibscallback);
}

function setDtlPT02(data){
	json2formmapping($("form[name=frmInputPT02]"), data.resultVO);
	//중복컬럼 조회
	var param = "&prfId="+data.resultVO.prfId; 
	grid_unq_col_sheet.DoSearch("<c:url value="/dq/profile/getPrfUnqColLst.do" />", param);
}

function grid_unq_col_sheetl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
	<div id="searchPT02_div" >
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="DUP.ANLY.DTL.INFO"/></div><!--중복분석 상세정보-->
		<div style="clear:both; height:5px;"><span></span></div>
		
		<form id="frmInputPT02" name="frmInputPT02" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DUP.ANLY'/>"><!-- 중복분석 -->

			   <caption><s:message code="DUP.ANLY"/></caption><!--중복분석-->

			   <colgroup>
			   	<col style="width:15%;" />
		   		<col style="width:85%;" />
			   </colgroup>
			       <tbody>
			       		<tr>                               
			               <th scope="row" class="th_require"><label for="objNm"><s:message code="DUP.ANLY.NM"/></label></th><!--중복분석명-->

			               <td colspan="4">
			                   <input type="text"  class="wd50p" name="objNm" id="objNm" />
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
			
		<div id="div_udq_col" name="div_udq_col">
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="stit"><s:message code="DUP.CLMN.DTL.INFO"/></div><!--중복컬럼 상세정보-->

			<div style="clear:both; height:5px;"><span></span></div>
			<!-- 추가 엑셀내리기  -->
			<div class="divLstBtn" >	 
            <div class="bt03">
                <button class="btn_rqst_new" id="btnRqstNew_PT02" name="btnRqstNew_PT02"><s:message code="ADDT" /></button><!--추가-->



        
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown_PT02" name="btnExcelDown_PT02"><s:message code="EXCL.DOWNLOAD" /></button><!--엑셀 내리기-->



                       
	    	</div>
        </div>
			<!-- 검색조건 입력폼 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<form id="frmInputPT02_udqcol" name="frmInputPT02_udqcol" method="post">
			<div id="grid_ptr_userdf" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_unq_col_sheet", "100%", "193px");</script>            
			</div>
			</form>
		</div>		
			
			
	</div>
	
<!-- </body> -->
<!-- </html> -->
