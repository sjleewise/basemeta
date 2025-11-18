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
<!-- <title>코드분류체계목록 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
        //imgConvert($('div.tab_navi a img'));
		
		//코드분류체계목록 이동 버튼 초기화
		   $(".btn_move_top").parent().hide();

		
        // 추가 Event Bind
        $("#btnCodeCfcNew").click(function(){ 
        	doActionCodeCfc("New");
        });
        
        // 저장 Event Bind
        $("#btnCodeCfcSave").click(function(){ 
        	//저장할 대상이 있는지 체크한다.
        	if(!codecfc_sheet.IsDataModified()) {
        		//저장할 내역이 없습니다.
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'SaveCodeCfcItem');
//         	doActionCodeCfc("Save");  
        
        }).show();

        // 삭제 Event Bind
        $("#btnCodeCfcDelete").click(function(){ 
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
        	if(checkDelIBS (codecfc_sheet, "<s:message code="ERR.CHKDEL" />")) {
        		//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteCol');
        	}
        	
        }); //doActionCodeCfc("Delete");  });
        
        // 재조회 버튼 Hide...
        $("#btnCodeCfcSearch").hide();
                
        // 엑셀내리기 Event Bind
        $("#btnCodeCfcExcelDown").click( function(){ doActionCodeCfc("Down2Excel"); } );

        // 엑셀업로드 Event Bind
        $("#btnCodeCfcExcelLoad").click( function(){ doActionCodeCfc("LoadExcel"); } );
        
        
        //코드분류체계목록 이동 버튼 Event Bind
        $("#btnCodeCfcMoveTop").click(function(){
        	//맨위로 이동
        	dataMoveIBS(codecfc_sheet, "TOP");
        }).next().click(function(){
        	//위로 이동
        	dataMoveIBS(codecfc_sheet, "UP");
        }).next().click(function(){
        	//아래로 이동
        	dataMoveIBS(codecfc_sheet, "DOWN");
        }).next().click(function(){
        	//맨 아래로 이동
        	dataMoveIBS(codecfc_sheet, "BOTTOM");
        });
    
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initCodeCfcGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, codecfc_sheet);
	codecfc_sheet.SetColHidden("rvwStsCd"	,1);
	codecfc_sheet.SetColHidden("rvwConts"	,1);
	
	if(rqststep == "Q" || rqststep == "A") {
		$(".btn_move_top").parent().hide();
	}
	
// 	doActionCodeCfc("Search");
	
	
});


$(window).resize(
    
    function(){
                
    	// codecfc_sheet.SetExtendLastCol(1);    
    }
);


function initCodeCfcGrid()
{
    
    with(codecfc_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.CODECFCSYS.ITEM.LST1'/>";
        /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과 */
        	headtext += "<s:message code='META.HEADER.CODECFCSYS.ITEM.LST2'/>";
        	/* |분류체계유형|분류체계명|분류체계항목순번|분류체계항목명|형식|자릿수|구분자|참조테이블|참조컬럼|참조ID */
		    headtext += "<s:message code='META.HEADER.CODECFCSYS.ITEM.LST3'/>";
		    /* |설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호 */

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
					{Type:"Combo",  Width:60,   SaveName:"regTypCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0},
					
					{Type:"Combo",   Width:150,   SaveName:"codeCfcSysCd",	 	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",    Width:100, SaveName:"codeCfcSysLnm"    ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemSeq"   ,Align:"Left",   Edit:1, KeyField:1, AcceptKeys:"N"},
					{Type:"Combo",    Width:100,  SaveName:"codeCfcSysItemCd"    ,Align:"Left",   Edit:1, Hidden:0 , KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemFrm"   ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemLen" ,Align:"Left",   Edit:1, Hidden:0, KeyField:1, AcceptKeys:"N"},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemSpt"    ,Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefTbl"      ,Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefCol"  ,Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefId"  ,Align:"Left",   Edit:1, Hidden:0},
                    
                    {Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:1},
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
		
		//분류체계유형
		SetColProperty("codeCfcSysCd", 	${codeMap.codeCfcSysCdibs});
		//분류체계항목
		SetColProperty("codeCfcSysItemCd", 	${codeMap.codeCfcSysItemCdibs});
	       
        InitComboNoMatchText(1, "");
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
      
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(codecfc_sheet);    
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
	var lrow = ibsobj.LastRow();
	
	switch(action){
	case "TOP":
		if(crow > 1)
		ibsobj.DataMove(1, crow);
		break;
	case "UP":
		if(crow > 1)
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
	
	for(var i=1; i<=lrow; i++) {
		ibsobj.SetCellValue(i, "colOrd", i);
		var pkyn = ibsobj.GetCellValue(i, "pkYn");
		if (pkyn == "Y" )
			ibsobj.SetCellValue(i, "pkOrd", i);
		else 
			ibsobj.SetCellValue(i, "pkOrd", "");
	}
	//PK 순서는 재조정 필요합니다....	

}



//화면상의 모든 액션은 여기서 처리...
function doActionCodeCfc(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
        	//코드분류체계의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
// 			var crow = grid_sheet.GetSelectRow();
// 			if(crow < 1) {
// 				showMsgBox("ERR", "<s:message code="ERR.TBLSEL" />");
// 				return;
// 			}
			codecfc_sheet.DataInsert(-1);
			
            break;
        

        case "NewChg":        //변경대상 추가...
        	//코드분류체계 검색 팝업을 오픈한다...
        	

            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(codecfc_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(codecfc_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
//         	var DelJson = codecfc_sheet.GetSaveJson(0, "ibsCheck");
        	ibsSaveJson = codecfc_sheet.GetSaveJson(0, "ibsCheck");
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	
        	var param = "sAction=" + sAction;
        	var url = "<c:url value="/meta/codecfcsys/delCodeCfcSysItemRqstList.do"/>";
//         	$.postJSON(url, DelJson, ibscallbackCol);
            IBSpostJson(url, param, ibscallbackCol);
        	break;
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = codecfc_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = codecfc_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = codecfc_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/model/regpdmcolrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallbackCol);
        	break;
        
        case "SaveRow":
        	//현재 선택행을 확인 후 1행 이상이면 저장 실행...
			var crow = codecfc_sheet.GetSelectRow();
			if(crow < 1) return;
//         	var nrow = codecfc_sheet.DataInsert();
			
			//폼 입력항목 검증... (이건 나중에...)
			
			//폼 내용을 시트에 셋팅...
			var istatus = codecfc_sheet.GetCellValue(crow, "ibsStatus");
        	form2ibsmapping(crow, $("form#frmCodeCfcItemInput"), codecfc_sheet);
        	if(istatus != "I") {codecfc_sheet.SetCellValue(crow, "ibsStatus", "U");}
        	
        	ibsSaveJson = getrowjsonIBS(crow, codecfc_sheet);
        	
        	var url = "<c:url value="/meta/model/regpdmcolrqstlist.do"/>";
        	var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallbackCol);
        	
        	break;
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#colfrmSearch').serialize();
        	var param = $('#mstFrm').serialize();
        	codecfc_sheet.DoSearch("<c:url value="/meta/codecfcsys/getcodecfcsysitemrqstlist.do" />", param);
//         	codecfc_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SearchRow": //단일 조회...
        	//선택 행 조회
        	var crow = codecfc_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	
        	var param = $('#frmSearch').serialize();
        	codecfc_sheet.DoRowSearch(crow, "<c:url value="/meta/model/getpdmcolrqstinfo.do" />",  param ,0);
        break;
       
        case "Down2Excel":  //엑셀내려받기
            codecfc_sheet.Down2Excel();
            break;
        case "LoadExcel":  //엑셀업로드
			var url = "<c:url value="/meta/codecfcsys/popup/codecfcsys_xls.do" />";
			
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
				doActionCodeCfc("Search");    		
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
	    	
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
	    		$("form#frmSearch input[name=rqstDtlSno]").val(res.ETC.rqstDtlSno);
				doActionCodeCfc("SearchRow");    		
	    	}
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" :
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
		
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#colfrmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
				doActionCodeCfc("Search");    		
	    	}
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
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
function codecfc_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function codecfc_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(codecfc_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = codecfc_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '코드분류체계목록명 : ' + param.codeCfcSysItemLnm ;
// 	$('#codecfcitem_sel_title').html(tmphtml);
	
	var param1  = "bizDtlCd=CCI";
	    param1 += "&rqstNo="+param.rqstNo;
	    param1 += "&rqstSno="+param.rqstSno;
	    param1 += "&rqstDtlSno="+param.rqstDtlSno;
	
	getRqstVrfLst(param1);
    
    
}

function codecfc_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) { 
}

function codecfc_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function codecfc_sheet_OnSearchEnd(code, message, stCode, stMsg) {
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

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>
        
<!-- 버튼영역  -->
<div class="divLstBtn" style="padding-right :10px">	 
	<div class="bt03">
		<button class="btn_search" id="btnCodeCfcSearch" 	name="btnCodeCfcSearch"><s:message code="RE.INQ" /></button> <!--재조회-->
		<button class="btn_rqst_new" id="btnCodeCfcRqstNew" name="btnCodeCfcRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
		<ul class="add_button_menu">
		<li class="btn_new" id="btnCodeCfcNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
<%-- 		<li class="btn_chang_add" id="btnCodeCfcChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span>변경대상 추가</a></li> --%>
		<li class="btn_excel_load" id="btnCodeCfcExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UP" /></a></li> <!-- 엑셀 올리기 -->
		</ul>         
		<button class="btn_save" id="btnCodeCfcSave" 	name="btnCodeCfcSave"><s:message code="STRG" /></button>  <!-- 저장 -->
		<button class="btn_delete" id="btnCodeCfcDelete" 	name="btnCodeCfcDelete"><s:message code="DEL" /></button>  <!-- 삭제 -->
	</div>
	<div class="bt03" style="padding-left: 10px;">
		<button class="btn_move_top"   id="btnCodeCfcMoveTop"><s:message code="TOP.UPPER.MOVE" /></button> <!-- 맨 위로 이동 -->
		<button class="btn_move_up"   id="btnCodeCfcMoveUp"><s:message code="UPPER.MOVE" /></button> <!--위로 이동-->
		<button class="btn_move_down" id="btnCodeCfcMoveDown"><s:message code="UNDER.MOVE" /></button> <!--아래로 이동-->
		<button class="btn_move_bottom" id="btnCodeCfcMoveBottom"><s:message code="TOP.UNDER.MOVE" /></button> <!--맨 아래로 이동-->
	</div>
	<div class="bt02">
		<button class="btn_excel_down" id="btnCodeCfcExcelDown" name="btnCodeCfcExcelDown"><s:message code="EXCL.DOWNLOAD" /></button><!-- 엑셀 내리기 -->                       
	</div>
</div>
<!-- 버튼영역  -->

<div style="clear:both; height:5px;"><span></span></div>
       
<!-- 그리드 입력 입력 -->
<div id="grid_02" class="grid_01">
	<script type="text/javascript">createIBSheet("codecfc_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area"> -->
<!-- 	<div class="selected_title" id="codecfcitem_sel_title">코드분류체계목록을 선택하세요.</div> -->
<!-- </div> -->

<div style="clear:both; height:5px;"><span></span></div>


<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->