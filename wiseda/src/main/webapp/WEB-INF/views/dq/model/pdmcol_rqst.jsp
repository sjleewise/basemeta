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
        // 추가 Event Bind
        $("#btnColNew").click(function(){ 

			doAction("NewCol");
        });
        
        // 저장 Event Bind
        $("#btnColSave").click(function(){ 
        	//저장할 대상이 있는지 체크한다.
        	if(!col_sheet.IsDataModified()) {
        		//저장할 내역이 없습니다.
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'SaveCol');
  
        
        }).hide();

        // 삭제 Event Bind
        $("#btnColDelete").click(function(){ 
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
        	if(checkDelIBS (col_sheet, "<s:message code="ERR.CHKDEL" />")) {
        		//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteCol');
        	}
        	
        }); //doActionCol("Delete");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doActionCol("Down2Excel"); } );

        // 엑셀업로드 Event Bind
        $("#btnColExcelLoad").click( function(){ doActionCol("LoadExcel"); } );
        
        
        //컬럼 이동 버튼 Event Bind
        $("#btnColMoveTop").click(function(){
        	//맨위로 이동
        	dataMoveIBS(col_sheet, "TOP");
        }).next().click(function(){
        	//위로 이동
        	dataMoveIBS(col_sheet, "UP");
        }).next().click(function(){
        	//아래로 이동
        	dataMoveIBS(col_sheet, "DOWN");
        }).next().click(function(){
        	//맨 아래로 이동
        	dataMoveIBS(col_sheet, "BOTTOM");
        });
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initColGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, col_sheet);
	col_sheet.SetColHidden("rvwStsCd"	,1);
	col_sheet.SetColHidden("rvwConts"	,1);
	
	if(rqststep == "Q" || rqststep == "A") {
		$(".btn_move_top").parent().hide();
	}
	
	doActionCol("Search");
	
	//erwin 등록 버튼 숨긴다.
	if("${waqMstr.bizDcd}" == "R9P" || "${waqMstr.bizDcd}" == "R7P"){
		
		
		$("#divLstBtn").hide();
	}
	
});


$(window).resize(
    
    function(){
                
    	// col_sheet.SetExtendLastCol(1);    
    }
);


function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.PDMCOL.RQST.1'/>";
        	headtext += "<s:message code='META.HEADER.PDMCOL.RQST.2'/>";
        	headtext += "<s:message code='META.HEADER.PDMCOL.RQST.3'/>";
        	headtext += "<s:message code='META.HEADER.PDMCOL.RQST.4'/>";
        	headtext += "<s:message code='META.HEADER.PDMCOL.RQST.5'/>";
        	headtext += "<s:message code='META.HEADER.PDMCOL.RQST.6'/>";
		    headtext += "<s:message code='META.HEADER.PDMCOL.RQST.7'/>";

            //headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
            //headtext += "|주제영역|테이블(물리명)|컬럼ID|컬럼(물리명)|컬럼(논리명)|이전컬럼(물리명)";
            //headtext += "|테이블ID";
            //headtext += "|모델명|상위주제영역명|주제영역ID|주제영역명";
            //headtext += "|표준용어ID";
            //headtext += "|컬럼순서|데이터타입|길이|소수점길이|PK 여부|PK 순서|NOT NULL여부|DEFAULT 값";
            //headtext += "|암호화여부|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";

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
					
// 					{Type:"Text",    Width:100, SaveName:"fullPath"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100, SaveName:"pdmTblPnm"   ,Align:"Left",   Edit:0, KeyField:1},
					{Type:"Text",    Width:40,  SaveName:"pdmColId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"pdmColPnm"   ,Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"pdmColLnm"   ,Align:"Left",   Edit:0, KeyField:0},
                    {Type:"Text",    Width:40,  SaveName:"bfPdmColPnm" ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:40,  SaveName:"pdmTblId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:40,  SaveName:"mdlLnm"      ,Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",    Width:40,  SaveName:"uppSubjLnm"  ,Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",    Width:40,  SaveName:"subjId"      ,Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",    Width:100, SaveName:"subjLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:80,  SaveName:"sditmId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Int",     Width:60,  SaveName:"colOrd"      ,Align:"Center",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:80,  SaveName:"dataType"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:40,  SaveName:"dataLen"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:80,  SaveName:"dataScal"    ,Align:"Center",   Edit:0},
//                     {Type:"Combo",   Width:40,  SaveName:"pkYn"        ,Align:"Left",   Edit:0},
//                     {Type:"Int",     Width:60,  SaveName:"pkOrd"       ,Align:"Left",   Edit:0},
//                     {Type:"Combo",   Width:80,  SaveName:"nonulYn"     ,Align:"Left",   Edit:0},
//                     {Type:"Text",    Width:80,  SaveName:"defltVal"    ,Align:"Left",   Edit:0},
//                     {Type:"Combo",    Width:80,  SaveName:"encYn"    ,Align:"Center",   Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
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
	    
// 		SetColProperty("pkYn", 		{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 		SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 		SetColProperty("encYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		
        InitComboNoMatchText(1, "");
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
        //SetColHidden("arrUsrId",1);
      
        // FitColWidth();
        SetSheetHeight(400);
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}


//검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getPopData(jsondata) {
	
	//alert(jsondata.subjLnm);
}

//그리드 시트 레코드 Move
function dataMoveIBS(ibsobj, action) {
	var crow = ibsobj.GetSelectRow();
	if(crow < 1) return false;
	var tblno =  ibsobj.GetCellValue(crow, "rqstSno");
	var lrow = ibsobj.LastRow();
	var frow = ibsobj.FindText("rqstSno", tblno+'');
	
	//테이블의 마지막 컬럼의 row 번호를 찾아 셋팅한다.
	for (var i=frow; i<=lrow;i++) {
		if (tblno != ibsobj.GetCellValue(i, "rqstSno")) {
			lrow = i-1;
			break;
		}
	}
	
	
	switch(action){
	case "TOP":
		if(crow > 1)
		ibsobj.DataMove(frow, crow);
		break;
	case "UP":
		if(crow > 1 && crow > frow)
		ibsobj.DataMove(crow-1, crow);
		break;
	case "DOWN":
		if(crow < lrow)
		ibsobj.DataMove(crow+2, crow);
		break;
	case "BOTTOM":
		if(crow < lrow)
		ibsobj.DataMove(lrow+1, crow);
		break;
	}
	
	//컬럼 순서를 재조정한다.
	var cntcolord = 1;
	for(var i=frow; i<=lrow; i++) {
		if(ibsobj.GetCellValue(i, "rqstDcd") == 'CU') {
			ibsobj.SetCellValue(i, "colOrd", i);
// 			var pkyn = ibsobj.GetCellValue(i, "pkYn");
// 			if (pkyn == "Y" )
// 				ibsobj.SetCellValue(i, "pkOrd", i);
// 			else 
// 				ibsobj.SetCellValue(i, "pkOrd", "");
			cntcolord++;
		}
	}
	
	//폼내용을 변경한다.
	crow = ibsobj.GetSelectRow();
// 	alert(ibsobj.GetCellValue(crow, "colOrd"));
	$("#frmColInput #colOrd").val(ibsobj.GetCellValue(crow, "colOrd"));
// 	$("#frmColInput #pkOrd").val(ibsobj.GetCellValue(crow, "pkOrd"));

}



//화면상의 모든 액션은 여기서 처리...
function doActionCol(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
        	//테이블의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
			var crow = grid_sheet.GetSelectRow();
			if(crow < 1) {
				showMsgBox("ERR", "<s:message code="ERR.TBLSEL" />");
				return;
			}
        	
        	//컬럼 입력폼을 불러온다.
        	loadDetailCol();
        
            break;

        case "NewChg":        //변경대상 추가...
        	//테이블 검색 팝업을 오픈한다...
        	

            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(col_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(col_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
//         	var DelJson = col_sheet.GetSaveJson(0, "ibsCheck");
        	ibsSaveJson = col_sheet.GetSaveJson(0, "ibsCheck");
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	
        	var param = "sAction=" + sAction;
        	var url = "<c:url value="/dq/model/delpdmcolrqstlist.do"/>";
//         	$.postJSON(url, DelJson, ibscallbackCol);
            IBSpostJson(url, param, ibscallbackCol);
        	break;
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = col_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = col_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = col_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/model/regpdmcolrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallbackCol);
        	break;
        
        case "SaveRow":
        	//현재 선택행을 확인 후 1행 이상이면 저장 실행...
			var crow = col_sheet.GetSelectRow();
			if(crow < 1) return;
//         	var nrow = col_sheet.DataInsert();
			
			//폼 입력항목 검증... (이건 나중에...)
			
			//폼 내용을 시트에 셋팅...
			var istatus = col_sheet.GetCellValue(crow, "ibsStatus");
        	form2ibsmapping(crow, $("form#frmColInput"), col_sheet);
        	if(istatus != "I") {col_sheet.SetCellValue(crow, "ibsStatus", "U");}
        	
        	ibsSaveJson = getrowjsonIBS(crow, col_sheet);
        	
        	var url = "<c:url value="/dq/model/regpdmcolrqstlist.do"/>";
        	var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallbackCol);
        	
        	break;
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#colfrmSearch').serialize();
        	var param = $('#mstFrm').serialize();
        	col_sheet.DoSearch("<c:url value="/dq/model/getpdmcolrqstlist.do" />", param);
//         	col_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SearchRow": //단일 조회...
        	//선택 행 조회
        	var crow = col_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	
        	var param = $('#frmSearch').serialize();
        	col_sheet.DoRowSearch(crow, "<c:url value="/dq/model/getpdmcolrqstinfo.do" />",  param ,0);
        break;
       
        case "Down2Excel":  //엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
			var url = "<c:url value="/dq/model/popup/pdmcol_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"pdmcolxls","800","600","yes");
			openLayerPop(url, 800, 600);
			
            break;
    }       
}
 
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function ibscallbackCol(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
		//공통메세지 팝업 : 성공 메세지...
    	showMsgBox("INF", res.RESULT.MESSAGE);
    
    	postProcessColIBS(res);
    	
    } else {
		//공통메시지 팝업 : 실패 메세지...
    	shotMsgBox("ERR", res.RESULT.MESSAGE);
    }
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessColIBS(res) {
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.ETC.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doActionCol("Search");    		
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
	    	
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
	    		$("form#frmSearch input[name=rqstDtlSno]").val(res.ETC.rqstDtlSno);
				doActionCol("SearchRow");    		
	    	}
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" :
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
		
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#colfrmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#colfrmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doActionCol("Search");    		
	    	}
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


//상세정보호출
function loadDetailCol(param) {
	$('div#detailInfocol').load('<c:url value="/dq/model/ajaxgrid/pdmcol_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
		  
		  //신규일 경우 선택한 테이블의 rqstsno를 업데이트 한다.
		  if ($("#frmColInput #ibsStatus").val() == "I") {
			  var crow = grid_sheet.GetSelectRow();
			  if(crow > 0) {
				  $("#frmColInput #rqstSno").val(grid_sheet.GetCellValue(crow, "rqstSno"));
			  }
		  }
		  
		  
	});
}

/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function col_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	var param =  col_sheet.GetRowJson(row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno   + "&searchObj=" + param.pdmTblPnm + "&subInfo=COL";
	$("#tabs #colinfo").click();
	grid_changecol.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1, 'COL');
	}
	
}

function col_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(col_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = col_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="CLMN.NM" /> : ' + param.pdmColPnm + ' [' + param.pdmColLnm +']'; //컬럼명
	$('#col_sel_title').html(tmphtml);
	
	loadDetailCol(param);
	
	var param1  = "bizDtlCd=COL";
	    param1 += "&rqstNo="+param.rqstNo;
	    param1 += "&rqstSno="+param.rqstSno;
	    param1 += "&rqstDtlSno="+param.rqstDtlSno;
	
	getRqstVrfLst(param1);
    
    
}


function col_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
        alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
        alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		$('div#detailInfocol').empty();
		
	}
	
}
</script>

<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

        
         <!-- 버튼영역  -->
        <div class="divLstBtn" id="divLstBtn"  style="padding-right :10px">	 
            <div class="bt03">
<!-- 			    <button class="btn_search" id="btnSearch" 	name="btnSearch">재조회</button>  -->
                <button class="btn_rqst_new" id="btnColRqstNew" name="btnColRqstNew"><s:message code="ADDT" /></button>  <!-- 추가 -->                                                        
				  <ul class="add_button_menu">
				    <li class="btn_new" id="btnColNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li><!-- 신규 추가 -->
<%-- 				    <li class="btn_chang_add" id="btnColChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span>변경대상 추가</a></li> --%>
				    <li class="btn_excel_load" id="btnColExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 올리기 -->
				  </ul>         
			    <button class="btn_save" id="btnColSave" 	name="btnColSave"><s:message code="STRG" /></button> <!-- 저장 -->
			    <button class="btn_delete" id="btnColDelete" 	name="btnColDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
			</div>
			
			<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnColExcelLoad" name="btnColExcelLoad">엑셀 업로드</button>                        -->
	          <button class="btn_excel_down" id="btnColExcelDown" name="btnColExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                        
	    	</div>
        </div>
         <!-- 버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("col_sheet", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="col_sel_title"><s:message code="CLMN.CHC.1"/></div> <!-- 컬럼을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>

<div id="detailInfocol"></div>

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->