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
var connTrgSchJson = ${codeMap.connTrgSch} ;

//엔터키 이벤트 처리
EnterkeyProcess("Search");
var interval = "";

$(document).ready(function() {
	//그리드 초기화 
	initGrid();
	
	//그리드 사이즈 조절 초기화...		
	bindibsresize();

	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	//조회버튼 hidden
	$("#btnSave").click( function(){ doAction("Save"); }).show();
	
	$("#expRsnCntnApply").click(function(){ doAction("Apply");  }).show();
	
	$("#expRsnCntDelete").click(function(){ doAction("Delete");  }).show();
	
	// 엑셀업로 Event Bind
    $("#btnNew").click( function(){ doAction("LoadExcel"); } );
	
	//삭제버튼 hidden
	$("#btnDelete").hide();
	//tree 추가 버튼 hidden
	$("#btnTreeNew").hide();
	//상세 페이지
	loadDetail();
	

	//======================================================
    // 셀렉트 박스 초기화
    //======================================================
 	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
 	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
 		double_select(connTrgSchJson, $(this));
 	});
	
});

$(window).on('load',function() {

});

EnterkeyProcess("Search");

$(window).resize(
    function(){

    	setibsheight($("#grid_01"));
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        var headerText = "No.|상태|제외여부|사유적용|DB_SCH_ID|DBMS명|스키마명|테이블명|테이블한글명|추가조건|제외사유";
        
        var headers = [
                    {Text: headerText, Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:30,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"expYn",         Align:"Center", Edit:1, Sort:0},                    
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",      Align:"Center", Edit:1, Sort:0,Hidden:1},                   
                    {Type:"Text",     Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1},                   
                    {Type:"Text",     Width:150,  SaveName:"dbConnTrgPnm",  Align:"Left", Edit:0},
                    {Type:"Text",     Width:180,  SaveName:"dbSchPnm",      Align:"Left", Edit:0},
                    {Type:"Text",     Width:180,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:180,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"addCnd",   		Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"expRsnCntn",    Align:"Left", Edit:1} 
                   
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

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
        	
			if($("#dbConnTrgId").val() == ""){
				
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
			}
			if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "스키마정보를 입력하세요.");
				return;
			}
			
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/dq/dqrs/getTrgTbl.do" />', param);
        	break;
       
        case "Save":

        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
        	for(var i = 1; i <= grid_sheet.GetTotalRows(); i++){
        		if(grid_sheet.GetCellValue(i,"expYn") == "0" && grid_sheet.GetCellValue(i,"expRsnCntn") != "") {
        			var msg = "제외여부를 체크해야 합니다.";
    				msg += "</br>(제외여부 미적용 Row : " + i + ")"
    				showMsgBox("ERR", msg);
    				return;
        		}
        		if(grid_sheet.GetCellValue(i,"expYn") == "1" && grid_sheet.GetCellValue(i,"expRsnCntn") == "") {
        			var msg = "제외대상 테이블은 제외사유를 입력해야 합니다.";
        				msg += "</br>(제외사유 미입력 Row : " + i + ")"
        			showMsgBox("ERR", msg);
        			return;
        			
        		}
        	}
        	
            var url = "<c:url value="/dq/dqrs/regTrgTbl.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단대상테이블관리'});
            break;
            
        case "LoadExcel":
 	    	var url = "<c:url value="/dq/dqrs/popup/trgtbl_xls.do" />";
			
			openLayerPop(url, 1200, 600);
        	break;
        	
        case "Apply":
        	//제외사유 일괄적용시 제외여부 + 제외사유 일괄적용 둘 다 체크되어야합니다.
        	var expRsnCntn = $("#expRsnCntn").val();
        	
        	var SaveJson = grid_sheet.GetSaveJson(0);

        	if(SaveJson.data.length == 0 ) return;
        	
   			for(var i = 0; i < SaveJson.data.length; i++) {
   				
   				var row = SaveJson.data[i].ibsSeq;
   				
   				if(grid_sheet.GetCellValue(row,"expYn") == "1" && grid_sheet.GetCellValue(row,"ibsCheck") == "1" ) {
					grid_sheet.SetCellValue(row, "expRsnCntn", expRsnCntn);
   					if(expRsnCntn == ""){ //일괄 적용할 제외사유가 공백일 경우 제외여부 해제
   						grid_sheet.SetCellValue(row,"expYn", "0");
   					}
   				}else if(grid_sheet.GetCellValue(row,"expYn") == "1" && grid_sheet.GetCellValue(row,"ibsCheck")!= "1" ){

   				}else if(grid_sheet.GetCellValue(row,"expYn") != "1" && grid_sheet.GetCellValue(row,"ibsCheck")== "1" ){
   					grid_sheet.SetCellValue(row, "expRsnCntn", expRsnCntn);
   					grid_sheet.SetCellValue(row,"expYn", "1");
   				} 
   				
        			
        	}
   			
   			doAction("Save");
        	
        	break;
        	
        case "Delete":
        	$("#expRsnCntn").val('');
        	
        	break;
    }       
}
 
//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/ajaxgrid/selectAnaTrgDbmsDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
}

//이력조회
function getTrgDbmsHstLst(param) {
	grid_hst.DoSearch("<c:url value="/dq/criinfo/ajaxgrid/getTrgDbmsHstLst.do" />", param);
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
	var param =  grid_sheet.GetRowJson(row);
	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");
	//caption 
	var tmphtml = ' <s:message code="DIAG.TRGT.DBMS.NM"/>'+ ' : ' + param.dbConnTrgLnm ; //진단대상DBMS명

	$('#anatrg_sel_title').html(tmphtml);
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
			$("#expRsnCntn").val('');
				doAction("Search");    		
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
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
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class ="th_require"><label for="dbConnTrgId"><s:message code="DIAG.TRGT.DBMS.NM"/></label></th><!--진단대상DBMS명-->

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
                           </td>
                       </tr>
                       <tr>                               
                           <th scope="row"><label for="expYn">제외대상여부</label></th><!--제외대상여부-->
                           <td colspan="3">
                              <select id="expYn"  name="expYn">
								    <option value = ""><s:message code="CHC" /></option> <!-- 선택 -->
								    <option value="Y">Y</option>
								    <option value="N">N</option>
								</select>
                           </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:20px;"><span></span></div>
            
<!--             <div class="stit">제외사유 일괄지정</div>검색조건 -->
<%--             <legend><s:message code="FOREWORD" /></legend><!--머리말--> --%>
<!--              <fieldset> -->
<!--             <div class="tb_basic2"> -->
<%--                 <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회--> --%>
<%--                    <caption>제외사유</caption><!--진단대상DBMS--> --%>
<%--                    <colgroup> --%>
<%--                    <col style="width:20%;" /> --%>
<%--                    <col style="width:80%;" /> --%>
<%--                    </colgroup> --%>
<!-- 							<tbody> -->
<!-- 								<tr> -->
<!-- 									<th scope="row"><label for="expRsnCntn">제외사유</label> -->
<!-- 									<td> -->
<!-- 									<input type="text" name="expRsnCntn" id="expRsnCntn" /> -->
<!-- 										&nbsp; -->
<!-- 											적용 -->
<!-- 											<button class="btn_apply" id="expRsnCntnApply" name="expRsnCntnApply"> -->
<%-- 												<s:message code="APL" /> --%>
<!-- 											</button> -->
<!-- 											&nbsp; -->
<!-- 											삭제 -->
<!-- 											<button class="btn_delete" id="expRsnCntDelete" name="expRsnCntDelete"> -->
<%-- 												<s:message code="DEL" /> --%>
<!-- 											</button> -->
<!-- 										</td> -->
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table>    -->
<!-- 						<div class="tb_comment">- 제외사유를 일괄지정하려면 제외여부와 제외사유 일괄적용 모두 체크되어야합니다.</div> -->
<!--            		 </div> -->
<!--             </fieldset> -->
<%--           <div style="clear:both; height:20px;"><span></span></div> --%>
         <!-- 조회버튼영역  -->
        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 -->   
<%--  	            <button class="btn_rqst_new" id="btnNew" name="btnNew"><s:message code="EXCL.UPLOAD" /></button><!--엑셀업로드--> --%>
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	
</body>
</html>