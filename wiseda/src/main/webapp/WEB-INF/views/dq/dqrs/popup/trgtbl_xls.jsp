<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title><s:message code="EXCL.UPLOAD" /></title>

<script type="text/javascript">

$(document).ready(function() {
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	parent.closeLayerPop();
    });
	
	
	//엑셀 올리기 버튼 셋팅 및 클릭 이벤트 처리...
	$('#btnExcelUp').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doAction('LoadExcel');
	});
	
	//엑셀 저장 버튼 초기화...
	$('#btnSaveExl').click(function(event){
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sheet.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save");  
	});
	 // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
		
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
});


$(window).resize(
    
    function(){
    	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));       
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headerText = "No.|상태|선택|삭제|DBMS명|스키마명|테이블명|테이블한글명|추가조건|제외사유";
        //No.|상태|선택|삭제|DBMS명|스키마명|테이블명|테이블한글명|추가조건|제외사유
        
        var headers = [
                    {Text:headerText, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",      Width:30,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", Width:75,  SaveName:"expYn",         Align:"Center", Edit:1},                    
                    {Type:"DelCheck", Width:45,  SaveName:"ibsCheck",         Align:"Center", Edit:1},                    
                    {Type:"Combo",     Width:150,  SaveName:"dbConnTrgId",  Align:"Left", Edit:1},
                    {Type:"Combo",     Width:180,  SaveName:"dbSchId",      Align:"Left", Edit:1},
                    {Type:"Text",     Width:180,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:180,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"addCnd",   		Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"expRsnCntn",    Align:"Left", Edit:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("dbConnTrgId", ${codeMap.ConnTrgDbmsibs2});
        SetColProperty("expYn", 	{ComboCode:"0|1", ComboText:"N|Y"}); /* 아니요|예 */
        InitComboNoMatchText(1, "");
        
    	//히든 컬럼 설정...
     	SetColHidden("regTypCd"	,1); 
        
        
        // FitColWidth();
        
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
        	
        	break;
        	
 	 	case "Save" : //엑셀 일괄 저장
 	 	
 	 		var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

 	 	   	//데이터 사이즈 확인...
 	 	   	if (SaveJson.Code == "IBS000") return;
 	 	   	if (SaveJson.Code == "IBS010") return;
 	 	   	
 	 	 	for(var i = 1; i <= SaveJson.data.length; i++){
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
 	 	  
 	 	  $("div.pop_tit_close").click();
 	 		
			break;        
 	    case "LoadExcel":  //엑셀업로드
 	      
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
 	        
 	        break;

 	   case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel(    { HiddenColumn:1, Merge:1, FileName:'진단대상테이블관리 -엑셀업로드'}    );
			break;
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;

    }       
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
    

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}

function grid_sheet_OnChange(row, col, value, cellx, celly) {

		if(grid_sheet.GetCellProperty(row, col, "SaveName") == "dbConnTrgId") {
			
			grid_sheet.SetCellValue(row, "dbSchId", null);
			$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
				if(key == value) {
					grid_sheet.CellComboItem(row, "dbSchId", val);
					return;
				}
			});
		}
}

function grid_sheet_OnLoadExcel(result) {
	var totalcnt = grid_sheet.RowCount("I");
	for (var i=0; i<totalcnt; i++) {
		var tmpVal = grid_sheet.GetCellValue(i+1, "dbConnTrgId");
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			if(key == tmpVal) {
				grid_sheet.CellComboItem(i+1, "dbSchId", val);
				grid_sheet.SetCellText(i+1, "dbSchId", grid_sheet.GetCellText(i+1,"dbSchId"));
				return;
			}
		});
	}
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	console.log(res);
	switch(res.action) {
	
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" :
			if ("${anaTrgTblVO.popType}" == "I") {
				parent.postProcessIBS(res);
			}else{
				opener.postProcessIBS(res);
			}
			//팝업닫기
			$("div.pop_tit_close").click();
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}




</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="EXCL.UPLOAD" /></div> 
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic"  style="display: none;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"> <!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                            <th scope="row"><label for="stwdLnm"><s:message code="STRD.WORD.NM" /></label></th> <!-- 표준단어명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="stwdLnm" id="stwdLnm" />
                                </span>
                            </td>  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        </form>
       <div class="tb_comment"><s:message  code='ETC.EXCEL' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
    <!-- 조회버튼영역  -->
	
	<div class="divLstBtn" >	 
		<div class="bt03">
		<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp"><s:message code="EXCL.UPLOAD" /></button> <!-- 엑셀 올리기 -->
		<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl"><s:message code="STRG" /></button> <!-- 저장 -->
		</div>
		<div class="bt02">
			<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                        
		</div>
	</div>
	
</div>       
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>

