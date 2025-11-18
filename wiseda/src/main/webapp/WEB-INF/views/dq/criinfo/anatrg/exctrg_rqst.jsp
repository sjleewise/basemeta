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
//엔터키 이벤트 처리
EnterkeyProcess("Search");
var interval = "";
var connTrgSchJson = ${codeMap.connTrgDbmsSch};
$(document).ready(function() {
	//그리드 초기화 
// 	initGrid();
	
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
	//조회버튼 hidden
	$("#btnSave").click( function(){ doAction("Save"); }).show();
	
// 	$("#expRsnCntnApply").click(function(){ doAction("Apply");  }).show();
	
// 	$("#expRsnCntDelete").click(function(){ doAction("Delete");  }).show();
	
 	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); }).hide();
	
	//삭제버튼 
	$("#btnDelete").click( function(){ doAction("Delete"); }).show();
	
    // 추가 Event Bind
    $("#btnNew").click(function(){ doAction("New");  }).show();
	
	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	});
	
	//doAction("Search");
	
});

$(window).on('load',function() {
	initGrid();
});

$(window).resize(
    function(){

    	setibsheight($("#grid_01"));

    	//그리드 가로 스크롤 방지
    	//grid_sheet.FitColWidth();
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|DB접속상태|담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성사용자ID
        
        var headerText = "No.|상태|선택|제외기준룰ID|DBMS|스키마|포함관계|제외기준룰";
        //No.|상태|제외여부|DB_SCH_ID|DBMS명|스키마명|테이블명|테이블한글명|추가조건|제외사유
        
        var headers = [
                    {Text: headerText, Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:200,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                	{Type:"CheckBox", Width:60, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:200,  SaveName:"expRuleId",    	Align:"Left", Edit:0,Hidden:1,KeyField:0},                   
                    {Type:"Combo",     Width:200,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:1,KeyField:1},                   
                    {Type:"Combo",     Width:200,  SaveName:"dbSchId",    	Align:"Left", Edit:1,KeyField:1},                   
                	{Type:"Combo",     Width:200,  SaveName:"relation",    	Align:"Left", Edit:1,KeyField:1},
                    {Type:"Text",     Width:500,  SaveName:"excStndRule",      Align:"Left", Edit:1,KeyField:1}                
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        SetColProperty("relation",{ComboCode:"F|B", ComboText:"앞|뒤"});
        SetColProperty("dbConnTrgId", ${codeMap.ConnTrgDbmsibs2});

        //FitColWidth();
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
        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/getExpRuleTbl.do" />', param);
        	break;
       
        case "Save":
        	//TODO 공통으로 처리...
        	
         	//var SaveJson = grid_sheet.GetSaveJson(0,"ibscheck");  //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
         	var ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
   
        //	alert(SaveJson.data.length);
//         	for(var i=0; i<=SaveJson.data.length; i++){
        	
//         			if(grid_sheet.GetCellValue(i+1, "excStndRule") == ""){
// 	        			showMsgBox("ERR", "제외기준룰을 기입해주세요"); /*제외기중룰을 기입해주세요*/
// 	        			grid_sheet.SelectCell(i+1,"excStndRule", {"Focus" : 1});
// 	    				return;
//         			}
    
        		
//         	}
            var url = "<c:url value="/dq/criinfo/anatrg/regExpRuleTbl.do"/>";
        	var param = "";
        	IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        	break;
        	
//         case "Apply":
//         	//제외사유 일괄적용시 제외여부 + 제외사유 일괄적용 둘 다 체크되어야합니다.
//         	var expRsnCntn = $("#expRsnCntn").val();
        	
//         	var SaveJson = grid_sheet.GetSaveJson(0);

//         	if(SaveJson.data.length == 0 ) return;
        	
//    			for(var i = 0; i < SaveJson.data.length; i++) {
   				
//    				var row = SaveJson.data[i].ibsSeq;
   				
//    				if(grid_sheet.GetCellValue(row,"expYn") == "1" && grid_sheet.GetCellValue(row,"ibsCheck") == "1" ) {
// 					grid_sheet.SetCellValue(row, "expRsnCntn", expRsnCntn);
//    				}else if(grid_sheet.GetCellValue(row,"expYn") == "1" && grid_sheet.GetCellValue(row,"ibsCheck")!= "1" ){
//        				grid_sheet.SetCellValue(row, "expRsnCntn", "");
//    					var msg = "제외사유를 일괄적용하려면 제외사유 일괄적용 체크박스를 선택하세요.";
//        				msg += "</br>(일괄여부 미체크 Row : " + row + ")";
//        				showMsgBox("ERR", msg);
//    				}else if(grid_sheet.GetCellValue(row,"expYn") != "1" && grid_sheet.GetCellValue(row,"ibsCheck")== "1" ){
//        				grid_sheet.SetCellValue(row, "expRsnCntn", "");
//    					var msg = "제외대상 테이블의 경우 제외여부를 체크하셔야합니다.";
//        				msg += "</br>(제외여부 미체크 Row : " + row + ")";
//        				showMsgBox("ERR", msg);
//    				} 
   				
        			
//         	}
        	
//         	break;
        	
        case "Delete":
        	
			delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
		//	if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/delExpRule.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
			
        case "New":
        	var row = grid_sheet.DataInsert(-1);
    		grid_sheet.SetCellValue(row, "dbConnTrgId", null);
        	break;
    }       
}
 
//상세정보호출
// function loadDetail(param) {
// 	$('div#detailInfo').load('<c:url value="/dq/criinfo/ajaxgrid/selectAnaTrgDbmsDetail.do"/>', param, function(){
// 		//$('#tabs').show();
// 	});
// }

//이력조회
function getTrgDbmsHstLst(param) {
	grid_hst.DoSearch("<c:url value="/dq/criinfo/ajaxgrid/getTrgDbmsHstLst.do" />", param);
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sheet_OnChange(row, col, value, cellx, celly) {
	if(grid_sheet.GetCellProperty(row, col, "SaveName") == "dbConnTrgId") {
		grid_sheet.SetCellValue(row, "dbSchId", null);
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
		//	alert(key);
			//alert(val);
			if(key == value) {
				grid_sheet.CellComboItem(row, "dbSchId", val);
				return;
			}
		});
	}
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
// 	var param =  grid_sheet.GetRowJson(row);
// 	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");
// 	//caption 
// 	var tmphtml = ' <s:message code="DIAG.TRGT.DBMS.NM"/>'+ ' : ' + param.dbConnTrgLnm ; //진단대상DBMS명


// 	$('#anatrg_sel_title').html(tmphtml);
	
// 	//상세조회
// 	/* loadDetail(dbConnTrgId); */
	
// 	//이력조회
// 	/* getTrgDbmsHstLst(dbConnTrgId); */
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
	var totalcnt = grid_sheet.SearchRows();
	
	for (var i=1; i<totalcnt+1; i++) {
		var tmpVal = grid_sheet.GetCellValue(i, "dbConnTrgId");
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			
			if(key == tmpVal) {
				grid_sheet.CellComboItem(i, "dbSchId", val);
				return;
			}
		});
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
			//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
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
                     <th scope="row" class="th_require"><label for="dbConnTrgId"><s:message code="DB.MS"/></label></th><!--진단대상DBMS명-->
	                    <td>
				            <select id="dbConnTrgId" class="" name="dbConnTrgId">
				            <option value=""><s:message code="WHL" /></option><!--전체-->
				            </select>
				            <select id="dbSchId" class="" name="dbSchId">
				            <option value=""><s:message code="WHL" /></option><!--전체-->
				             </select>
				           </td>  
                           <th scope="row"><label for="excStndRule"><s:message code="EXC.STANDARD.RULE"/></label></th>
                           <td>
                           		<input type="text" name="excStndRule" id="excStndRule" />
                           </td>
                       </tr>
                    
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:20px;"><span></span></div>
            
           
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
          <div style="clear:both; height:5px;"><span></span></div>
         <!-- 조회버튼영역  -->
        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG"/></button> <!-- 저장 -->   
			    <button class="btn_rqst_new2" id="btnNew" 	name="btnNew"><s:message code="ADDT"/></button> <!-- 추가 -->   
			    <button class="btn_delete" id="btnDelete" name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
 	            <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad">엑셀 일괄등록</button>                     
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