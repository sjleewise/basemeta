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
	$("#frmInputPT01").validate({
		rules: {
			objNm		: "required",  //관계분석명
			paTblDbConnTrgNm		: "required",
			paTblDbcSchNm		: "required",
			paTblDbcTblNm 	: "required",
			dqiLnm 	: "required",
		},
		messages: {
			objNm		: requiremessage,
			paTblDbConnTrgNm		: requiremessage,
			paTblDbcSchNm		: requiremessage,
			paTblDbcTblNm 	: requiremessage,
			dqiLnm 	: requiremessage,
		}
	});
	
	//사용자 정의 관계컬럼 초기화
	initRelGrid();
	
	//관계컬럼 버튼
	//그리드 추가
	$("div#div_rel #btnNew_PT01").click(function(){
		//event.preventDefault();	//브라우저 기본 이벤트 제거...
    	relTblColPopup();
	});
	//엑셀 업로드
	$("div#div_rel #btnExcelLoad_PT01").click(function(){
		grid_rel_col_sheet.LoadExcel({Mode:"HeaderMatch"});
	});
	//엑셀다운로드
	$("div#div_rel #btnExcelDown_PT01").click(function(){
		grid_rel_col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	}).hide();
	
	// 품질지표 조회
    $("#frmInputPT01 #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFPT01"; 
    		param += "&dqiIds="+$("#frmInputPT01 #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });	
	
});


$(window).on('load',function(){
});


$(window).resize( function(){
// 	grid_rel_col_sheet.FitColWidth();  
});

function initRelGrid()
{
   
	with(grid_rel_col_sheet){
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headers = [
					{Text:"<s:message code='DQ.HEADER.TBLREL_DTL'/>"
// 						   +"객체설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호"
						   , Align:"Center"}
				];
		//삭제|No.|상태|요청구분|등록유형|검증결과|검토상태코드|"+"자식테이블DB접속대상ID|자식테이블DBC스키마ID|자식테이블DBC테이블명|자식컬럼명|자식컬럼한글명|

		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
	                    {Type:"DelCheck", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
						{Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
		                {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",  Width:100,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",		Align:"Center", Edit:0, Hidden:1},	
						
						{Type:"Text",   Width:200,  SaveName:"chTblDbConnTrgId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcSchId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcTblNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcColNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcColKorNm",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						
						{Type:"Text",   Width:200,  SaveName:"paTblDbConnTrgId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcSchId",   	Align:"Left", Edit:0, KeyField:0 ,Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcTblNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcColNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcColKorNm",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},

// 						{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0,  Hidden:1},
// 						{Type:"Date",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
// 						{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
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
// 		SetColHidden("rqstDtm",0);
// 		SetColHidden("rqstUserId",1);
// 		SetColHidden("rqstUserNm",1);
// 		SetColHidden("rqstNo",1);
// 		SetColHidden("rqstSno",1);
	  
		FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_rel_col_sheet);	
	//===========================
	
	//grid_sheet.SetColHidden(Col, Hidden)
   
}	

//DQI 팝업 리턴값 처리
function returnDqiPopPT01 (ret) {
	//초기화
	$("#frmInputPT01 #dqiLnm").val("");
	$("#frmInputPT01 #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInputPT01 #dqiLnm").val() != null && $("#frmInputPT01 #dqiLnm").val() != "undefined" && $("#frmInputPT01 #dqiLnm").val() != "" ){
			$("#frmInputPT01 #dqiLnm").val($("#frmInputPT01 #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInputPT01 #dqiId").val($("#frmInputPT01 #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInputPT01 #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInputPT01 #dqiId").val(parsejson.dqiId);
		}
	}
}

function resetPT01(){
	//프로파일 상세정보 초기화
	$("form[name=frmInputPT01]")[0].reset();
	//관계컬럼 그리드 초기화
	grid_rel_col_sheet.RemoveAll();
}

function savePT01(){
	//관계분석컬럼
// 	var ibsSaveJson =grid_rel_col_sheet.GetSaveJson(0);
	/* if($("form[name=frmAnaTrg] #regTypCd").val() == "D"){
		ibsSaveJson = grid_rel_col_sheet.GetSaveJson(1);
	} */
	
	var ibsSaveJson = grid_rel_col_sheet.GetSaveJson(1);
	
	//form 체크
 	if(!$("form[name=frmInputPT01]").valid()) return false; 
	
	
	//관계분석 삭제 일 경우 관계컬럼 전체 삭제
	if($("form[name=frmAnaTrg] #regTypCd").val() != "D"){
		
		//관계컬럼 입력 확인
		if(grid_rel_col_sheet.GetTotalRows() == ""){ 
			var message = "<s:message code='ERR.EMPTY' arguments="<s:message code='RLT.CLMN.INPT'/>" />";/*관계컬럼을 입력하십시오.*/
			showMsgBox("INF", message); 
			return;
		}
		
		//동일 관계컬럼 확인
		var DupRow = grid_rel_col_sheet.ColValueDupRows("chTblDbConnTrgId|chTblDbcSchId|chTblDbcTblNm|chTblDbcColNm|paTblDbConnTrgId|paTblDbcSchId|paTblDbcTblNm|paTblDbcColNm");
		if(DupRow != ""){
			var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='SAME.RLT.CLMN.EXIS'/>" />"; /*동일한 관계컬럼이 존재 합니다.*/
			showMsgBox("INF", message); 
			return;
		}
		
		//사용자정의 관계컬럼 그리드 삭제 선택일경우 삭제대상 건수 와 전체건수 비교
		if(grid_rel_col_sheet.FindStatusRow("D") != ""){
			var arrDelRows = grid_rel_col_sheet.FindStatusRow("D").split(";").length ;
			if( arrDelRows == grid_rel_col_sheet.GetTotalRows()){
				var message = "<s:message code="DEL.TRGT.RLT.CLMN.CCNT.TCNT.SAME" />";/*삭제대상 관계컬럼 건수와 총건수가 동일합니다.*/

				showMsgBox("INF", message); 
				return;
			}
		}
	}
	
	//저장
	var param =   "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
		 param += "&"+$("form[name=frmInputPT01]").serialize(); //관계분석 정보
		 
	var urls = '<c:url value="/dq/profile/registerProfilePT01RelCol.do"/>';
	IBSpostJson2(urls, ibsSaveJson, param, ibscallback);
}

function setDtlPT01(data){
	//관계분석 정보 form 셋팅
	json2formmapping($("form[name=frmInputPT01]"), data.resultVO);
	//관계컬럼  조회
	var param = "&prfId="+data.resultVO.prfId; 
	//탭안에 아이비시트
	grid_rel_col_sheet.DoSearch("<c:url value="/dq/profile/getPrfPT01RelColLst.do" />", param);
}

/* 부모테이블조회  */
function relTblColPopup(){
	if($("form[name=frmAnaTrg] #dbConnTrgId").val() == ""){
		var message = "<s:message code="VALID.PRFREQUIRED" />";
		showMsgBox("INF", message); 
		return;
	}
  	var url = '<c:url value="/dq/profile/popup/anatrgreltblcol_pop.do"/>';
	var param = $("form[name=frmAnaTrg]").serialize(); 
	     param += "&"+$("form[name=frmInputPT01]").serialize(); 
	
// 	var popup = OpenWindow(url+"?"+param, "REL_TBL_COL", "1000", "800", "auto");
	openLayerPop(url ,1000 ,800,  param);
} 

function returnRelColPop(ret){
	var retjson = jQuery.parseJSON(ret);
	
	$("form[name=frmInputPT01] #paTblDbConnTrgId").val(retjson.schPaDbConnTrgId) ;
	$("form[name=frmInputPT01] #paTblDbcSchId").val(retjson.schPaDbSchId) ;
	
	$("form[name=frmInputPT01] #paTblDbConnTrgNm").val(retjson.schPaDbConnTrgLdm) ;
	$("form[name=frmInputPT01] #paTblDbcSchNm").val(retjson.schPaDbSchNm) ;
	$("form[name=frmInputPT01] #paTblDbcTblNm").val(retjson.schPaDbcTblNm) ;
	$("form[name=frmInputPT01] #paTblDbcTblKorNm").val(retjson.schPaDbcTblKorNm) ;
}


/* 그리드 function  */
function grid_rel_col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
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
	<div id="searchPT01_div" >
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="RLT.ANLY.DTL.INFO"/></div><!--관계분석 상세정보-->
		<div style="clear:both; height:5px;"><span></span></div>
		
		<form id="frmInputPT01" name="frmInputPT01" method="post">
		 <input type="hidden"  class="" name="paTblDbConnTrgId" id="paTblDbConnTrgId" />
		 <input type="hidden"  class="" name="paTblDbcSchId" id="paTblDbcSchId" />
		 
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='RLT.ANLY'/>"> <!--관계분석-->
			   <caption><s:message code="RLT.ANLY"/></caption><!--관계분석-->
			   <colgroup>
			   <col style="width:14%;" />
			   <col style="width:14%;" />
			   <col style="width:34%;" />
			   <col style="width:14%;" />
			   <col style="width:24%;" />
			   </colgroup>
			       <tbody>   
			       	   <tr>                               
			               <th scope="row" class="th_require"><label for="objNm"><s:message code="RLT.ANLY.NM"/></label></th><!--관계분석명-->
			               <td colspan="4">
			                   <input type="text"  class="wd50p" name="objNm" id="objNm" />
			               </td>
			           </tr>
			       	   <tr>                               
			               <th scope="row"><label for="adtCndSql"><s:message code="CHILD.ADDT.COND"/></label></th><!--자식추가조건-->

			               <td colspan="4">
			                   <input type="text"  class="wd98p" name="adtCndSql" id="adtCndSql" />
			               </td>
			           </tr>
			           <tr>
			           		<th rowspan="3" scope="row" ><s:message code="PRNT.TBL"/></th><!--부모테이블-->
			           		 <th scope="row" class="th_require"><label for="paTblDbConnTrgNm"><s:message code="DB.MS"/></label></th><!--부모진단대상명-->
				             <td>
				                   <input type="text"  name="paTblDbConnTrgNm" id="paTblDbConnTrgNm" class="b0wd98" readonly/>
				             </td>
				             <th scope="row" class="th_require"><label for=""><s:message code="SCHEMA.NM"/></label></th><!--부모스키마명-->
				               <td>
				                   <input type="text" name="paTblDbcSchNm" id="paTblDbcSchNm" class="b0wd98" readonly/>
				               </td>
			           </tr>
			       	   <tr>                               
			               <th scope="row" class="th_require"><label for="paTblDbcTblNm"><s:message code="TBL.NM"/></label></th><!--부모테이블명-->
			               <td>
			                   <input type="text" name="paTblDbcTblNm" id="paTblDbcTblNm"  class="b0wd98" readonly/>
			               </td>
			               <th scope="row"><label for=""><s:message code="TBL.KRN.NM"/></label></th><!--부모테이블한글명-->
			               <td>
			                   <input type="text"  name="paTblDbcTblKorNm" id="paTblDbcTblKorNm" class="b0wd98" readonly/>
			               </td>
			           </tr>
			       	   <tr>                               
			               <th scope="row"><label for=""><s:message code="PRNT.ADDT.COND"/></label></th><!--부모추가조건-->
			               <td colspan="4">
			                   <input type="text"  class="wd98p" name="paTblAdtCndSql" id="paTblAdtCndSql" />
			               </td>
			           </tr>
			           
					<tr>
		               <th scope="row" class="th_require"><label for="dqiSearch"><s:message code="QLTY.INDC"/></label></th><!--품질지표-->
		               <td colspan="4">
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
			
		<div id="div_rel" name="div_rel">
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="stit"><s:message code="RLT.CLMN.DTL.INFO"/></div><!--관계컬럼 상세정보-->
			<div style="clear:both; height:5px;"><span></span></div>
			<!-- 추가 엑셀내리기  -->
			<div class="divLstBtn" >	 
            <div class="bt03">
                <button class="btn_rqst_new" id="btnNew_PT01" name="btnNew_PT01"><s:message code="ADDT" /></button><!--추가-->
        
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown_PT01" name="btnExcelDown_PT01"><s:message code="EXCL.DOWNLOAD" /></button><!--엑셀 내리기-->
	    	</div>
        </div>
			<!-- 검색조건 입력폼 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<form id="frmInputPT01_userdf" name="frmInputPT01_userdf" method="post">
			<div id="grid_ptr_userdf" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_rel_col_sheet", "100%", "120px");</script>            
			</div>
			</form>
		</div>		
	</div>
	
<!-- </body> -->
<!-- </html> -->
