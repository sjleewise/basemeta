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

var chgRow = "";
var connTrgSchJson = ${codeMap.connTrgSch} ;

$(document).ready(function() {
	//그리드 초기화 
	initGrid();
	
	//그리드 사이즈 조절 초기화...		
	bindibsresize();

	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

	$("#btnExcelLoad").click( function(){ doAction("Load2Excel"); } ); 
	
	$("#expRsnCntnApply").click(function(){ doAction("Apply");  }).show();
	$("#expRsnCntDelete").click(function(){ doAction("Delete");  }).show();
	
	$("#popApply").click(function(){
		var clicked = $("#vrfcNm").val();
		var clickedId = $("#vrfcId").val();
		var clickCdClsID = $("#cdClsId").val();
 		for(var i = 1; i <= grid_sheet.RowCount(); i++) {     
			if(grid_sheet.GetCellValue(i,"ibsCheck") ==1){
				if(grid_sheet.GetCellValue(i,"colRmk") == '' || grid_sheet.GetCellValue(i,"colRmk") == null){
					grid_sheet.SetCellValue(i, "vrfcNm", clicked);		
					grid_sheet.SetCellValue(i, "vrfcId", clickedId);	
					grid_sheet.SetCellValue(i, "cdClsId", clickCdClsID);
				}
			}
		} 
			doAction("Save");
	
	}).show();
	
	$("#rmkApply").click(function(){
		var clicked = $("#colRmk").val();
 		for(var i = 1; i <= grid_sheet.RowCount(); i++) {     
			if(grid_sheet.GetCellValue(i,"ibsCheck") == 1 && grid_sheet.GetCellValue(i,"vrfcId") == ''){
				grid_sheet.SetCellValue(i, "colRmk", clicked);		
			}
		} 
			doAction("Save");
	
	}).show();
	
	
	$("#btnSave").click( function(){ doAction("Save"); }).show();

	
	//tree 추가 버튼 hidden
	$("#btnTreeNew").hide();
	
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

$(window).resize(
    function(){

    	setibsheight($("#grid_01"));
    }
);

function initGrid()
{
    
	//진단대상 컬럼 grid
   	with(grid_sheet){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           
           var headers = [
                       {Text:"No.|상태|선택|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명|Pk여부|Data Type|Null여부|제외여부|제외사유", Align:"Center"}
                   ];

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                   {Type:"Seq",      Width:30,   SaveName:"ibsSeq",        Align:"Center", Edit:0, Hidden:0}, 
                   {Type:"CheckBox",   Width:30,  SaveName:"ibsCheck",    	Align:"Left", Edit:1, Hidden:1}, 
                   {Type:"Status", Width:25,   SaveName:"ibsStatus",    	Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:0}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:0},                    
                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:0},
                   {Type:"Text",   Width:100,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                   {Type:"Text",   Width:100,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:70,   SaveName:"pkYn",    	Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,  SaveName:"dataType",    	Align:"Left", Edit:0},
                   {Type:"Text",   Width:40,   SaveName:"nullYn",    	Align:"Center", Edit:0, Hidden:0},
                   {Type:"Combo",   Width:40,   SaveName:"expYn",    	Align:"Center", Edit:1, Hidden:0},
                   {Type:"Text",   Width:120,   SaveName:"expRsnCntn",    	Align:"Left", Edit:1, Hidden:0},
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);

       InitComboNoMatchText(1, "");
       
       SetColProperty("expYn", 	{ComboCode:"N|Y", ComboText:"N|Y"}); /* 아니요|예 */
       
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       
       FitColWidth();
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
        	grid_sheet.DoSearch('<c:url value="/dq/dqrs/getExpCol.do" />', param);
        	break;
       
        case "Save":

        	//var SaveJson = grid_sheet.GetSaveJson({StdCol:"expYn"});
        	var SaveJson = grid_sheet.GetSaveJson();
        	if(SaveJson.data.length == 0 ){
        		showMsgBox("ERR", "제외여부 변경 내역이 없습니다.");
        		return;
        	}
        	
        	for(var i = 0; i < SaveJson.data.length; i++){
        		var expYn = SaveJson.data[i].expYn;
        		var expRsnCntn = SaveJson.data[i].expRsnCntn;
        		
        		if(expYn == 'Y' && expRsnCntn == ''){
        			showMsgBox("ERR", "제외사유를 입력하세요");
        			return;
        		}
        		
        		//제외여부가 N일 때 제외 사유만 저장 불가하도록
        		if((expYn == '' || expYn == 'N') && expRsnCntn != ''){
        			showMsgBox("ERR", "제외여부가 Y인 경우에만 제외사유를 입력할 수 있습니다.");
        			return;
        		}
        	}
        	
            var url = "<c:url value="/dq/dqrs/regExpCol.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        	
        case "Apply":
        	//제외사유 일괄적용시 제외여부 + 제외사유 일괄적용 둘 다 체크되어야합니다.
        	var expRsnCntn = $("#expRsnCntn").val();
        	
        	var SaveJson = grid_sheet.GetSaveJson();
        	if(SaveJson.data.length == 0 ) return;
        	
   			for(var i = 0; i < SaveJson.data.length; i++) {
   				
   				var row = SaveJson.data[i].ibsSeq;
   				if(grid_sheet.GetCellValue(row,"expYn") == "Y" && grid_sheet.GetCellValue(row,"ibsCheck") == "1" ) {
					grid_sheet.SetCellValue(row, "expRsnCntn", expRsnCntn);
   				}else if(grid_sheet.GetCellValue(row,"ibsCheck") == "1" && expRsnCntn == ''){
   					grid_sheet.SetCellValue(row, "expRsnCntn", expRsnCntn);
//       				showMsgBox("ERR", msg);
   				}else if(grid_sheet.GetCellValue(row,"expYn") != "Y" && grid_sheet.GetCellValue(row,"ibsCheck")== "1" ){
   					grid_sheet.SetCellValue(row, "expRsnCntn", expRsnCntn);
   					grid_sheet.SetCellValue(row,"expYn", "Y");
   				} 
   				
        			
        	}
   			
   			doAction("Save");
        	
        	break;             
        	
        case "Delete":
        	$("#expRsnCntn").val('');
        	
        	break;
        	
        case "Down2Excel":  //엑셀내려받기
 	    
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단대상컬럼제외관리'});
	    	
			break;
        case "Load2Excel":  //엑셀올리기
        	
        	grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:0, StartRow:'2'});
            break;    
            
       
       
     
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
			doAction("Search");    		
			break;
		case "<%=WiseMetaConfig.IBSAction.REG%>" : 
			
 			 doAction("Search");
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
	
	// 검증룰이 지정되어있는 컬럼은 컬럼의견 못쓰게. 컬럼의견이란 해당 컬럼을 진단대상에서 제외하는 이유를 기술하는 항목이기 때문
	for(var i = 1; i <= grid_sheet.RowCount(); i++) {     
		if(grid_sheet.GetCellValue(i,"vrfcId") != ''){
			grid_sheet.SetCellEditable(i, "colRmk", 0);		
		}
	}
}
 
 function grid_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) { 
	 if(grid_sheet.GetCellValue(Row,Col) == '' && grid_sheet.SaveNameCol("expRsnCntn") == Col){
		 grid_sheet.SetCellValue(Row,"expYn",'N');
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
       		<input type="hidden" id="downColNms" name="downColNms">
        	<input type="hidden" id="downColSz" name="downColSz">
        	<input type="hidden" id="downCode" name="downCode">
        	<input type="hidden" id="bizrulCtn" name="bizrulCtn">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
        	<div style="clear:both; height:5px;"><span></span></div>
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
                           </td>
                           
                           
                       </tr>
                        <tr>
                        	<th scope="row"><label for="dbcColNm">컬럼명</label></th>
                           <td>
                           		<input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
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
             </form>        	
        	<div style="clear:both; height:5px;"><span></span></div>
<!--              <div class="divLstBtn" style="display: none;">	  -->
<!-- 	            <div class="bt03"> -->
<%-- 				    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->                  --%>
<!-- 				</div> -->
<!-- 			</div> -->
             
<%--             <div style="clear:both; height:10px;"><span></span></div> --%>
<%--             <div style="clear:both; height:10px;"><span></span></div> --%>
            
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
<!--            		 </div> -->
<!--             </fieldset> -->
   
<%-- 		<div style="clear:both; height:10px;"><span></span></div> --%>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
            	<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			</div>
 			<div class="bt02">
  	          <!-- <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button> -->                        
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