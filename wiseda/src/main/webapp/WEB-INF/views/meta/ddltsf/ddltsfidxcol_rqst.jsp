<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
        //imgConvert($('div.tab_navi a img'));
		

		
        // 추가 Event Bind
        $("#btnColNew").click(function(){ 
//         	doActionCol("New");
// 			doAction("NewCol");
        }).hide();
        
        // 저장 Event Bind
        $("#btnColSave").click(function(){ 
        	//저장할 대상이 있는지 체크한다.
        	if(!idxcol_sheet.IsDataModified()) {
        		//저장할 내역이 없습니다.
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'SaveCol');
//         	doActionCol("Save");  
        
        }).hide();

        // 삭제 Event Bind
        $("#btnColDelete").click(function(){ 
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
        	if(checkDelIBS (idxcol_sheet, "<s:message code="ERR.CHKDEL" />")) {
        		//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteIdxCol');
        	}
        	
        }); //doActionCol("Delete");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doActionIdxCol("Down2Excel"); } );

        // 엑셀업로드 Event Bind
        $("#btnColExcelLoad").click( function(){ doActionIdxCol("LoadExcel"); } ).hide();

        
        
    }
);

$(window).load(function() {
	//그리드 초기화 
	initTsfIdxColGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, idxcol_sheet);
	idxcol_sheet.SetColHidden("rvwStsCd"	,1);
	idxcol_sheet.SetColHidden("rvwConts"	,1);
	
	if(rqststep == "Q" || rqststep == "A") {
		$(".btn_move_top").parent().hide();
	}
	
// 	doActionIdxCol("Search");
	
	
});


$(window).resize(
    
    function(){
                
    	// col_sheet.SetExtendLastCol(1);    
    }
);


function initTsfIdxColGrid()
{
    
    with(idxcol_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
        	headtext += "|DDL컬럼ID|인덱스ID|인덱스(물리명)";
        	headtext += "|인덱스컬럼ID|인덱스컬럼(물리명)|인덱스컬럼(논리명)|인덱스컬럼순서|정렬순서";


        	//headtext += "|데이터타입|길이|소수점길이|PK 여부|PK 순서|NOT NULL여부|DEFAULT 값";
		    headtext += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";

	    var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,   SaveName:"rvwConts",  Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:60,   SaveName:"regTypCd",  Align:"Center", Edit:0},						
					{Type:"Combo",  Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0},
					
					//{Type:"Text",    Width:40,  SaveName:"ddlTblId"    ,Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",    Width:40,  SaveName:"ddlColId"    ,Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",    Width:40,  SaveName:"ddlIdxId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:120,  SaveName:"ddlIdxPnm"    ,Align:"Left",   Edit:0, Hidden:0},
                    
                    {Type:"Text",    Width:60, SaveName:"ddlIdxColId"   ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:130, SaveName:"ddlIdxColPnm"   ,Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:130, SaveName:"ddlIdxColLnm"   ,Align:"Left",   Edit:0, KeyField:1},
                    
                    {Type:"Int",     Width:80,  SaveName:"ddlIdxColOrd"      ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:80,  SaveName:"sortTyp"     ,Align:"Center",   Edit:0},
                    
//                     {Type:"Text",    Width:80,  SaveName:"dataType"    ,Align:"Left",   Edit:0},
//                     {Type:"Text",    Width:40,  SaveName:"dataLen"     ,Align:"Center",   Edit:0},
//                     {Type:"Text",    Width:80,  SaveName:"dataScal"    ,Align:"Center",   Edit:0},
//                     {Type:"Combo",   Width:40,  SaveName:"pkYn"        ,Align:"Left",   Edit:0},
//                     {Type:"Int",     Width:60,  SaveName:"pkOrd"       ,Align:"Left",   Edit:0},
//                     {Type:"Combo",   Width:80,  SaveName:"nonulYn"     ,Align:"Left",   Edit:0},
//                     {Type:"Text",    Width:80,  SaveName:"defltVal"    ,Align:"Left",   Edit:0},
                    
                    {Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		SetColProperty("sortTyp", 	${codeMap.idxColSrtOrdCdibs});
	    
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
        //SetColHidden("arrUsrId",1);
      
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(idxcol_sheet);    
    //===========================
   
}


//검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getPopData(jsondata) {
	
	//alert(jsondata.subjLnm);
}




//화면상의 모든 액션은 여기서 처리...
function doActionIdxCol(sAction)
{
        
    switch(sAction)
    {
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(col_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(idxcol_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
//         	var DelJson = col_sheet.GetSaveJson(0, "ibsCheck");
        	ibsSaveJson = idxcol_sheet.GetSaveJson(0, "ibsCheck");
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	
        	var param = "sAction=" + sAction;
        	var url = "<c:url value="/meta/ddltsf/delddltsfidxcolrqstlist.do"/>";
//         	$.postJSON(url, DelJson, ibscallbackCol);
            IBSpostJson(url, param, ibscallbackIdxCol);
        	break;
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#colfrmSearch').serialize();
        	var param = $('#mstFrm').serialize();
        	idxcol_sheet.DoSearch("<c:url value="/meta/ddl/getddlidxcolrqstlist.do" />", param);
//         	col_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SearchRow": //단일 조회...
        	//선택 행 조회
        	var crow = idxcol_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	
        	var param = $('#frmSearch').serialize();
        	idxcol_sheet.DoRowSearch(crow, "<c:url value="/meta/ddl/getpdmcolrqstinfo.do" />",  param ,0);
        break;
       
        case "Down2Excel":  //엑셀내려받기
            idxcol_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
function idxcol_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	
}

function idxcol_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(idxcol_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = idxcol_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '인덱스컬럼명 : ' + param.ddlIdxColPnm + ' [' + param.ddlIdxColLnm +']';
	$('#idxcol_sel_title').html(tmphtml);
	
	
	var param1  = "bizDtlCd=TSFIDXCOL";
	    param1 += "&rqstNo="+param.rqstNo;
	    param1 += "&rqstSno="+param.rqstSno;
	    param1 += "&rqstDtlSno="+param.rqstDtlSno;
	
	getRqstVrfLst(param1);
    
    
}


function idxcol_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function idxcol_sheet_OnSearchEnd(code, message) {
	//alert(col_sheet.GetDataBackColor()+":"+ col_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
// 		$('div#detailInfocol').empty();
// 		$('#btnColReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}


</script>

<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

        
         <!-- 버튼영역  -->
        <div class="divLstBtn" style="padding-right :10px">	 
            <div class="bt03">
				  <ul class="add_button_menu">
				    <li class="btn_new" id="btnIdxColNew"><a><span class="ui-icon ui-icon-pencil"></span>신규 추가</a></li>
<%-- 				    <li class="btn_chang_add" id="btnColChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span>변경대상 추가</a></li> --%>
				  </ul>         
			    <button class="btn_delete" id="btnIdxColDelete" 	name="btnIdxColDelete">삭제</button> 
<!-- 			    <button class="btn_check" id="btnCheck" 	name="btnCheck">검증</button>  -->
<!-- 			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst">등록</button>  -->
			</div>
			<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnColExcelLoad" name="btnColExcelLoad">엑셀 업로드</button>                        -->
	          <button class="btn_excel_down" id="btnIdxColExcelDown" name="btnIdxColExcelDown">엑셀 내리기</button>                       
	    	</div>
        </div>
         <!-- 버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("idxcol_sheet", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "col_sheetƒ", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="idxcol_sel_title">인덱스컬럼을 선택하세요.</div>
	</div>

<div style="clear:both; height:5px;"><span></span></div>

<!-- <div id="detailInfocol"></div> -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->