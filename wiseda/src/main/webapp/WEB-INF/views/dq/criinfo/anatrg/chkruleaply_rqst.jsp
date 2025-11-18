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
<title><s:message code="DIAG.TRGT.DB.MS.INQ" /></title><!--진단대상DBMS 조회-->



<script type="text/javascript">

var interval = "";
var connTrgSchJson = ${codeMap.devConnTrgSch} ;
//엔터키 이벤트 처리
//EnterkeyProcess("Search");
$(document).ready(function() {
 

	
	//그리드 초기화 
	initGrid();
	$("#popSearch").click(function(){
		var url = "<c:url value='/dq/criinfo/anatrg/popup/vrfcrule_pop.do' />";
//     	var param =  "&popRqst=Y";
    	var param = "code=N&row=";
    	
//     	openLayerPop(url, 800, 600, param);
    	var popup = OpenWindow(url+"?"+param,"bizareaSearch","800","600","yes");
				  });  
	//그리드 사이즈 조절 초기화...		
	bindibsresize();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	//$( "#tabs" ).tabs();
	//그리드 초기화 
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

	$("#btnExcelLoad").click( function(){ doAction("Load2Excel"); } ).hide(); 
	
	
	$("#popApply").click(function(){
		var clicked = $("#vrfcNm").val();
		var clickedId = $("#vrfcId").val();
		var clickCdClsID = $("#cdClsId").val();
		
 		for(var i = 1; i <= grid_sheet.RowCount(); i++) {     
			if(grid_sheet.GetCellValue(i,"ibsCheck") ==1){
				grid_sheet.SetCellValue(i, "vrfcNm", clicked);		
				grid_sheet.SetCellValue(i, "vrfcId", clickedId);	
				grid_sheet.SetCellValue(i, "cdClsId", clickCdClsID);
			}
		} 
			doAction("Save");
		
//  	 	$.ajax({
// 			url : "<c:url value='/dq/criinfo/anatrg/popup/applyVrfcRule.do' />",
// 			data : {clicked : clicked},
// 			type : "GET",
// 			success : function(){
// 				console.log(clicked);
// 			},
// 			error : function(){
// 				console.log("에런가");
// 			}
// 		}) 
		
	
	
	}).show();
	
	$("#popDelete").click(function(){
 		for(var i = 1; i <= grid_sheet.RowCount(); i++) {     
			if(grid_sheet.GetCellValue(i,"ibsCheck") ==1){
				grid_sheet.SetCellValue(i, "vrfcNm", "");		
				grid_sheet.SetCellValue(i, "vrfcId", "");	
				grid_sheet.SetCellValue(i, "cdClsId", "");
			}
		} 
		
		
	}).show();
	
	//조회버튼 hidden
	$("#btnSave").click( function(){ doAction("Save"); }).show();
	$("#btnDelete").click( function(){ doAction("Delete"); }).show();

	$("#btnExec").click(function(){
		doAction("Exec");
	
	}).show();
	
	//tree 추가 버튼 hidden
	$("#btnTreeNew").hide();
	//상세 페이지
	loadDetail();
	
	//임시 메뉴목록 등장 함수
	var val = $("#dbConnTrgId option:selected").val();
	var trgId = ${codeMap.devConnTrgSch} ;
// 	$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == null) {
			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	}
	
	
	//======================================================
	 // 셀렉트 박스 초기화
	//======================================================
	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	});
	
// 	doAction("Search");
});

$(window).on('load',function() {

});


/*  $(window).resize(
    function(){

    	setibsheight($("#grid_01"));

    	//그리드 가로 스크롤 방지
    	//grid_sheet.FitColWidth();
    }
);  */

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, VScrollMode:1};
        SetConfig(cfg);

        //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|DB접속상태|담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성사용자ID
        
        var headerText = "No.|상태|선택|RULE_REL_ID|DB_SCH_ID|DB_CONN_TRG_ID|DBMS명|스키마명|테이블명|테이블한글명|컬럼명|컬럼한글명|DataType|검증룰ID|검증룰|코드분류ID|shdJobId|shdKndCd|etcJobNm|shdJobNm|프로파일ID|진단상태|분석차수|분석차수|분석실행시간";
        
        var headers = [
                    {Text: headerText, Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",        Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status",   Width:80,   SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",         Align:"Center", Edit:1, Sort:0},                    
                    {Type:"Text",     Width:100,  SaveName:"ruleRelId",    	Align:"Left", Edit:0, Hidden:1},                    
                    {Type:"Text",     Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1},                   
                    {Type:"Text",     Width:180,  SaveName:"dbConnTrgId",  Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",     Width:180,  SaveName:"dbConnTrgPnm",  Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:180,  SaveName:"dbSchPnm",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:200,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:200,  SaveName:"dbcColKorNm",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,  SaveName:"dataType",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,  SaveName:"vrfcId",        Align:"Left", Edit:0, Hidden:1},  
                    {Type:"Popup",    Width:200,  SaveName:"vrfcNm",        Align:"Left", Edit:1},  
                    {Type:"Text",     Width:200,  SaveName:"cdClsId",       Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,  SaveName:"shdJobId",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdKndCd",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"etcJobNm",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdJobNm",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"prfId",         Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"anaStsCd",      Align:"Left", Edit:0, Hidden:0},                    
                    {Type:"Text",   	Width:50,   SaveName:"anaDgr",   		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"anaDgrNm",   		Align:"Center", Edit:0, Hidden:0},                   
                    {Type:"Text",     Width:10,   SaveName:"anaStrDtm",     Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}
                   
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}







function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	/* if($('#dbConnTrgId').val()=="" || $('#dbSchId').val()==""){
        		showMsgBox("ERR", "<s:message code="VALID.PRFREQUIRED" />", '');
        		break;
        	} */
        	
        	//initDtlGrids();
			if($("#dbConnTrgId").val() == ""){
				
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
				}
			if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "스키마정보를 입력하세요.");
				return;
				}

			var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/getCheckRuleTbl.do" />', param);
        	break;
       
        case "Save":

        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/criinfo/anatrg/regChkRuleAply.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        case "Delete":

        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/criinfo/anatrg/delChkRuleAply.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;       
        case "Exec":
           
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 

	        for(var i = 0; i < SaveJson.data.length ; i++){
	        	
				var bCheck = SaveJson.data[i].ibsCheck;
				var vrfcId = SaveJson.data[i].vrfcId;
				
	        	if(bCheck == 1 && vrfcId == ""){
					showMsgBox("ERR",'검증룰이 적용된 컬럼만 실행 가능합니다.');
					return;
	            }
	        }
			
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/criinfo/anatrg/execItmAna.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;             
        	
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "Load2Excel":  //엑셀올리기
        	grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:1});
            break;    
            
       
       
     
    }       
}
 
//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/ajaxgrid/selectAnaTrgDbmsDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
}


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
 function postProcessIBS(res) {
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");    		
			break;
		case "<%=WiseMetaConfig.IBSAction.REG%>" : 
			//execItmAna.do 후 처리
// 			showMsgBox2("INF", "처리중이니 잠시 기다려 주십시요.");
// 			showMsgBox("INF", "처리중이니 잠시 기다려 주십시요.");
// 			$("#btnMsgConf").bind("click", 
// 			    	function(){
// 			    	   doAction("Search");
// 				}
			
// 			);
			   //1row 진단실행분석시 3초 걸립니다. 그 이상 테스트 할 경우 늘려주세요...
// 			setTimeout(function() {
// 				  console.log('3초 후 SEARCH!');
// 				  doAction("Search");
// 				}, 3000);
			
// 			 doAction("Search");
			break;
		case "<%=WiseMetaConfig.IBSAction.DEL%>" : 
			doAction("Search");
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}	
} 


 function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	if(grid_sheet.CellSaveName(row, col) == "vrfcNm") return;
	
// 	getDataPattern(row);
} 

function getDataPattern(row){
 	var objId = grid_sheet.GetCellValue(row, "prfId");
 	var anaStrDtm = grid_sheet.GetCellValue(row, "anaStrDtm");
	
	if(objId == ""){
		showMsgBox("ERR", "컬럼분석을 완료한 데이터를 선택하십시오."); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	
 	var param = "?objId="+objId;
	     param += "&objDate="+ anaStrDtm;
	     param += "&objIdCol=PRF_ID";		  
         param += "&objResTbl=WAM_PRF_RESULT";
	     param += "&objErrTbl=WAM_PRF_ERR_DATA";
	     param += "&erDataSnoCol=ESN_ER_DATA_SNO";
         param += "&objGb=PC01";        
         param += "&erDataSno="+0;
         
    var url = '<c:url value="/dq/report/popup/datapattern_pop.do" />';
 	var popup = OpenWindow(url+param, "DATA_PATTERN", "800", "600", "yes"); 
}


 function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
	var param =  grid_sheet.GetRowJson(row);
	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");
	//caption 
	var tmphtml = ' <s:message code="DIAG.TRGT.DBMS.NM"/>'+ ' : ' + param.dbConnTrgLnm ; //진단대상DBMS명


	$('#anatrg_sel_title').html(tmphtml);
	
	//상세조회
	loadDetail(dbConnTrgId);
	
} 

 function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}
 
//검증룰 팝업 리턴값 처리
// function returnSubjPop (ret, row, cls) {
// // 	alert(ret);
	
// 	var retjson = jQuery.parseJSON(ret); 

// 	if(cls == "CD"){

// 		var vrfcNm = retjson.cdRuleNm + " " + retjson.codeClsVal;   

// 		//alert(vrfcNm);  
		
// 		grid_sheet.SetCellValue(row, "vrfcId",  retjson.cdRuleId); 
// 		grid_sheet.SetCellValue(row, "vrfcNm",  vrfcNm);

		
// 	}else{
// 		grid_sheet.SetCellValue(row, "vrfcId", retjson.vrfcId);
// 		grid_sheet.SetCellValue(row, "vrfcNm", retjson.vrfcNm);

// 	}			
// }

// //검증룰 팝업 리턴값 처리
function returnSubjPop (ret, row) {
 

	
	var retjson = jQuery.parseJSON(ret);
	
	grid_sheet.SetCellValue(row, "vrfcId", "");
	grid_sheet.SetCellValue(row, "vrfcNm", "");
	grid_sheet.SetCellValue(row, "cdClsId", "");
	
	grid_sheet.SetCellValue(row, "vrfcId", retjson.vrfcId);
	grid_sheet.SetCellValue(row, "vrfcNm", retjson.vrfcNm);
// 	$("#frmSearch #subjLnm").val(retjson.subjLnm);
// 	$("#frmSearch #fullPath").val(retjson.fullPath);
//  검증룰 적용과 동시에 저장
// 	doAction("Save");
}

function returnSubjPopCd (ret, row) {
 
	var retjson = jQuery.parseJSON(ret);
	
	grid_sheet.SetCellValue(row, "vrfcId", "");
	grid_sheet.SetCellValue(row, "vrfcNm", "");
	grid_sheet.SetCellValue(row, "cdClsId", "");

	grid_sheet.SetCellValue(row, "cdClsId", retjson.codeClsId);
	grid_sheet.SetCellValue(row, "vrfcId", retjson.cdRuleId);
	grid_sheet.SetCellValue(row, "vrfcNm", retjson.cdRuleNm);
	
	
}

 function grid_sheet_OnPopupClick(Row,Col) {
	var param = "code=N&row=" +Row;
	param +=  "&vrfcNm="+grid_sheet.GetCellValue(Row, "vrfcNm");

	//사용자 검색 팝업 오픈

	if ("vrfcNm" == grid_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/dq/criinfo/anatrg/popup/vrfcrule_pop.do" />';
		openLayerPop(url, 800, 700, param);
	}
}


 

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DIAG.TRGT.DB.MS.INQ" /></div><!--진단대상DBMS 조회-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->

                   <caption><s:message code="DIAG.TRGT.DBMS"/></caption><!--진단대상DBMS-->

                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
<%--                    <col style="width:10%;" /> --%>
<%--                    <col style="width:23;" /> --%>
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="dbConnTrgId"><s:message code="DIAG.TRGT.DBMS.NM"/></label><%-- <img src='<c:url value="/images/th_require.gif" />'/> --%></th><!--진단대상DBMS명-->
                           <td>
                              <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
								</select>
								<select id="dbSchId" class="" name="dbSchId">
					             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select>
                           </td>
                           <th scope="row"><label for="dbcTblNm">테이블명</label></th>
                           <td>
                           		<input type="text" name="dbcTblNm" id="dbcTblNm" />
                           		<select id="selectNm" name="selectNm" class="wd100">
                           			<option value="include">포함</option>
                           			<option value="all">완전일치</option>
                           			<option value="front">앞 일치</option>
                           			<option value="back">뒤 일치</option>
                           		</select>
                           </td>
                           
                           
                       </tr>
                        <tr>
                        	<th scope="row"><label for="dbcColNm">컬럼명</label></th>
                           <td>
                           		<input type="text" name="dbcColNm" id="dbcColNm" />
                           			<select id="selectCol" name="selectCol" class="wd100">
                           			<option value="include">포함</option>
                           			<option value="all">완전일치</option>
                           			<option value="front">앞 일치</option>
                           			<option value="back">뒤 일치</option>
                           		</select>
                           </td>
                                                                          
                           <th scope="row"><label for="dataType">데이터타입</label></th>
                           <td >
                           		<input type="text" name="dataType" id="dataType" />
                           </td>                  
                       </tr>
                       
                       <tr>                                              
                           <th scope="row"><label for="regYn">검증룰등록여부</label></th>
                           <td>
                           		<select id="regYn" name="regYn" class="wd100">
                           			<option value="">전체</option> 
                           			<option value="Y">Y</option>
                           			<option value="N">N</option>
                           		</select>
                           </td>    
                           <th scope="row"><label for="vrfcTyp">검증분류</label></th>
                           <td >
                           		<select id="vrfcTyp" name="vrfcTyp" >
                           		   <option value="">전체</option> 
                         		   <c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status" >
                                   <option value="${code.codeCd}">${code.codeLnm}</option>
                                   </c:forEach>
                           		</select>
                           </td>                          
                       </tr>
                       
                   </tbody>
                 </table>   
            </div>
            </fieldset>
             </form>
             
            <div style="clear:both; height:10px;"><span></span></div>
            <div style="clear:both; height:10px;"><span></span></div>
            
            <div class="stit">검증룰 일괄지정</div><!--검색조건-->
            
                   
            
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
             <fieldset>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->

                   <caption>검증룰</caption><!--진단대상DBMS-->

                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:80%;" />
                 
                   </colgroup>

							<tbody>
								<tr>
									<th scope="row"><label for="vrfcRuleNm"><s:message
												code="ANATRG" /></label>
									<%-- <img src='<c:url value="/images/th_require.gif" />'/> --%></th>
									<!--진단대상DBMS명-->
									<td>
									<input type="text" name="vrfcNm" id="vrfcNm" />
									<input type="hidden" name="vrfcId" id="vrfcId"  />
									<input type="hidden" name="cdClsId" id="cdClsId"  />

										
											<!-- 검색 -->
											<button  class="btn_search" id="popSearch" name="popSearch">
												<s:message code="SRCH" />
											</button>
											<!-- 적용 -->
											<button class="btn_apply" id="popApply" name="popApply">
												<s:message code="APL" />
											</button>
											&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
											<!-- 삭제 -->
											<button class="btn_delete" id="popDelete" name="popDelete">
												<s:message code="INON" />
											</button>
										
											
										</td>

								</tr>

							</tbody>
						</table>   
            </div>
            
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
   
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->                 
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
			    <button class="btn_save"   id="btnExec" 	name="btnExec">진단항목실행</button> <!-- 저장 -->    
			</div>
			<div class="bt02"> 
 	          <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                       
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
         <!-- 조회버튼영역  -->

</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "350px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	
</body>
</html>