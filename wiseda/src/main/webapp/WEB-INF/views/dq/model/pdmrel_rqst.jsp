<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="PHYC.MDEL.RLT.REG.DTL.INFO" /></title> <!-- 물리모델관계등록상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	
	
	//======================================================
    // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
    //======================================================
    //imgConvert($('div.tab_navi a img'));
	
		
    // 추가 Event Bind
    $("#btnRelNew").click(function(){ 
//     	doActionCol("New");
		doAction("NewRel");
    });
    
    // 저장 Event Bind
    $("#btnRelSave").click(function(){ 
    	//저장할 대상이 있는지 체크한다.
    	if(!rel_sheet.IsDataModified()) {
    		//저장할 내역이 없습니다.
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRel');
//     	doActionCol("Save");  
    
    }).hide();

    // 삭제 Event Bind
    $("#btnRelDelete").click(function(){ 
    	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    	if(checkDelIBS (rel_sheet, "<s:message code="ERR.CHKDEL" />")) {
    		//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteRel');
    	}
    	
    }); //doActionCol("Delete");  });
    
            
    // 엑셀내리기 Event Bind
    $("#btnRelExcelDown").click( function(){ doActionRel("Down2Excel"); } );

    // 엑셀업로드 Event Bind
    $("#btnRelExcelLoad").click( function(){ doActionRel("LoadExcel"); } );
    
    
    

});

$(window).on('load',function() {
	//그리드 초기화 
	initRelGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, rel_sheet);
	rel_sheet.SetColHidden("rvwStsCd"	,1);
	rel_sheet.SetColHidden("rvwConts"	,1);
	
	if(rqststep == "Q" || rqststep == "A") {
		$(".btn_move_top").parent().hide();
	}
	
	doActionRel("Search");
// 	loadDetailRel();
});


function initRelGrid()
{
    
    with(rel_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.PDMREL.RQST.1'/>";
	    	headtext += "<s:message code='META.HEADER.PDMREL.RQST.2'/>";
	    	headtext += "<s:message code='META.HEADER.PDMREL.RQST.3'/>";
	    	headtext += "<s:message code='META.HEADER.PDMREL.RQST.4'/>";
	    	headtext += "<s:message code='META.HEADER.PDMREL.RQST.5'/>";
		    headtext += "<s:message code='META.HEADER.PDMREL.RQST.6'/>";
		    headtext += "<s:message code='META.HEADER.PDMREL.RQST.7'/>";

		    //headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
	    	//headtext += "|관계ID|관계물리명|관계논리명";
	    	//headtext += "|관계유형코드|카디널리티유형코드|Parent Optionality유형코드";
	    	//headtext += "|부모엔터티주제영역ID|부모엔터티주제영역명|부모엔터티ID|부모엔터티명|부모엔터티속성ID|부모엔터티속성명";
	    	//headtext += "|자식엔터티주제영역ID|자식엔터티주제영역명|자식엔터티ID|자식엔터티명|자식엔터티속성ID|자식엔터티속성명";
		    //headtext += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";
		    //headtext += "|이전관계명|이전부모엔터티명|이전부모엔터티속성명|이전자식엔터티명|이전자식엔터티속성명";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,   SaveName:"rvwConts",  Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:60,   SaveName:"regTypCd",  Align:"Center", Edit:0},						
					{Type:"Combo",  Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0},
					
					{Type:"Text",    Width:100, SaveName:"pdmRelId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"pdmRelPnm"   ,Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"pdmRelLnm"   ,Align:"Left",   Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"relTypCd"        ,Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Combo",   Width:100,  SaveName:"crdTypCd"        ,Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Combo",   Width:100,  SaveName:"paOptTypCd"        ,Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:100,  SaveName:"paSubjId" ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paSubjPnm"    ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paEntyId"      ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paEntyPnm"  ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paAttrId"      ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"paAttrPnm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chSubjId" ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chSubjPnm"    ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chEntyId"      ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chEntyPnm"  ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chAttrId"      ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"chAttrPnm"     ,Align:"Left",   Edit:0, Hidden:0},

                    
                    {Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Text",    Width:100, SaveName:"bfPdmRelPnm"   ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:100,  SaveName:"bfPaEntyPnm"  ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"bfPaAttrPnm"     ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:100,  SaveName:"bfChEntyPnm"  ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"bfChAttrPnm"     ,Align:"Left",   Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});

		SetColProperty("relTypCd", 	${codeMap.relTypCdibs});
		SetColProperty("crdTypCd", 	${codeMap.crdTypCdibs});
		SetColProperty("paOptTypCd", 	${codeMap.paOptTypCdibs});
		
		
        
        InitComboNoMatchText(1, "");
        

        
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(rel_sheet);    
    //===========================
   
}


//화면상의 모든 액션은 여기서 처리...
function doActionRel(sAction)
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
        	loadDetailRel();
        
            break;

        case "NewChg":        //변경대상 추가...
        	//테이블 검색 팝업을 오픈한다...
        	

            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(rel_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(rel_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
//         	var DelJson = rel_sheet.GetSaveJson(0, "ibsCheck");
        	ibsSaveJson = rel_sheet.GetSaveJson(0, "ibsCheck");
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	
        	var param = "sAction=" + sAction;
        	var url = "<c:url value="/meta/model/delpdmcolrqstlist.do"/>";
//         	$.postJSON(url, DelJson, ibscallbackCol);
            IBSpostJson(url, param, ibscallbackCol);
        	break;
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = rel_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = rel_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = rel_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/model/regpdmcolrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallbackCol);
        	break;
        
        case "SaveRow":
        	//현재 선택행을 확인 후 1행 이상이면 저장 실행...
			var crow = rel_sheet.GetSelectRow();
			if(crow < 1) return;
//         	var nrow = rel_sheet.DataInsert();
			
			//폼 입력항목 검증... (이건 나중에...)
			
			//폼 내용을 시트에 셋팅...
			var istatus = rel_sheet.GetCellValue(crow, "ibsStatus");
        	form2ibsmapping(crow, $("form#frmColInput"), rel_sheet);
        	if(istatus != "I") {rel_sheet.SetCellValue(crow, "ibsStatus", "U");}
        	
//         	alert(rel_sheet.GetCellValue(crow, "rqstDcd"));
			
			//현재 선택행 Json 생성...
//         	var temp = rel_sheet.GetRowJson(crow);
//         	var tmparr = new Array();
//         	tmparr.push(temp);
        	
        	ibsSaveJson = getrowjsonIBS(crow, rel_sheet);
        	
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
        	rel_sheet.DoSearch("<c:url value="/meta/model/getpdmrelrqstlist.do" />", param);
//         	rel_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SearchRow": //단일 조회...
        	//선택 행 조회
        	var crow = rel_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	
        	var param = $('#frmSearch').serialize();
        	rel_sheet.DoRowSearch(crow, "<c:url value="/meta/model/getpdmcolrqstinfo.do" />",  param ,0);
        break;
       
        case "Down2Excel":  //엑셀내려받기
            rel_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
        	var crow = grid_sheet.GetSelectRow();
			if(crow < 1) {
				showMsgBox("ERR", "<s:message code="ERR.TBLSEL" />");
				return;
			}
			var url = "<c:url value="/meta/model/popup/pdmrel_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"pdmcolxls","800","600","yes");
			openLayerPop(url, 800, 600);
			
            break;
    }       
}



//상세정보호출
function loadDetailRel(param) {
	$('div#detailInfoRel').load('<c:url value="/meta/model/ajaxgrid/pdmrel_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
		  
		  //신규일 경우 선택한 테이블의 rqstsno를 업데이트 한다.
		  if ($("#frmRelInput #ibsStatus").val() == "I") {
			  var crow = grid_sheet.GetSelectRow();
			  if(crow > 0) {
				  $("#frmRelInput #rqstSno").val(grid_sheet.GetCellValue(crow, "rqstSno"));
				  $("#frmRelInput #chSubjPnm").val(grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "fullPath"));
				  $("#frmRelInput #chEntyPnm").val(grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "pdmTblPnm"));
			  }
		  }
		  
		  
	});
}

function rel_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	var param =  rel_sheet.GetRowJson(row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno + "&subInfo=REL";
	$("#tabs #relinfo").click();
	grid_changecol2.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1, 'REL');
	}
	
}

function rel_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(rel_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = rel_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="RLT.NM" /> : ' + param.pdmRelPnm + ' [' + param.pdmRelLnm +']'; //관계명
	$('#rel_sel_title').html(tmphtml);
	
	//선택한 시트 열번호를 저장한다...
	$("#sheetRow #sheetRow").val(row);
	
	
	//값이 변경된게 있을경우 ibs2wformmapping이 실행되도록 한다.
	if(rel_sheet.GetCellValue(row, "ibsStatus") == 'R') {
		
		loadDetailRel(param);
	} else {
		
	 	ibs2formmapping(row, $("#frmRelInput"), rel_sheet);
	}
	
	var param1  = "bizDtlCd=REL";
	    param1 += "&rqstNo="+param.rqstNo;
	    param1 += "&rqstSno="+param.rqstSno;
	    param1 += "&rqstDtlSno="+param.rqstDtlSno;
	    
	    
	
	getRqstVrfLst(param1);
    
    
}

//주제영역 팝업 리턴값 처리...
function returnSubjPopRel(ret){
	var retjson = jQuery.parseJSON(ret);
	if($("#frmRelInput #subjFlag").val() == "PA") {
		$("#frmRelInput #paSubjPnm").val(retjson.fullPath);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CH") {
		$("#frmRelInput #chSubjPnm").val(retjson.fullPath);
		
	} 
}
//엔터티 팝업 리턴값 처리...
function returnPdmtblPop(ret){
	var retjson = jQuery.parseJSON(ret);
	if($("#frmRelInput #subjFlag").val() == "PA") {
		
		$("#frmRelInput #paSubjPnm").val(retjson.fullPath);
		$("#frmRelInput #paEntyPnm").val(retjson.pdmTblPnm);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CH") {
		$("#frmRelInput #chSubjPnm").val(retjson.fullPath);
		$("#frmRelInput #chEntyPnm").val(retjson.pdmTblPnm);
		
	} 
}

//속성명 팝업 리턴값 처리...
function returnPdmColPop(ret){
	var retjson = jQuery.parseJSON(ret);
	if($("#frmRelInput #subjFlag").val() == "PA") {
		$("#frmRelInput #paSubjPnm").val(retjson.subjLnm);
		$("#frmRelInput #paEntyPnm").val(retjson.pdmTblPnm);
		$("#frmRelInput #paAttrPnm").val(retjson.pdmColPnm);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CH") {
		$("#frmRelInput #chAttrPnm").val(retjson.pdmColPnm);
		
	} 
}


</script>
</head>
<body>
<div style="clear:both; height:5px;"><span></span></div>
	<!-- 버튼영역  -->
        <div class="divLstBtn" style="padding-right :10px">	 
            <div class="bt03">
<!-- 			    <button class="btn_search" id="btnSearch" 	name="btnSearch">재조회</button>  -->
                <button class="btn_rqst_new" id="btnRelRqstNew" name="btnRelRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu">
				    <li class="btn_new" id="btnRelNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li><!-- 신규 추가 -->
<%-- 				    <li class="btn_chang_add" id="btnColChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span>변경대상 추가</a></li> --%>
				    <li class="btn_excel_load" id="btnRelExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UP" /></a></li> <!-- 엑셀 올리기 -->
				  </ul>         
			    <button class="btn_save" id="btnRelSave" 	name="btnRelSave"><s:message code="STRG" /></button> <!-- 저장 -->
			    <button class="btn_delete" id="btnRelDelete" 	name="btnRelDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
<!-- 			    <button class="btn_check" id="btnCheck" 	name="btnCheck">검증</button>  -->
<!-- 			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst">등록</button>  -->
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnRelExcelDown" name="btnRelExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                        
	    	</div>
        </div>
         <!-- 버튼영역  -->
	
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("rel_sheet", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="rel_sel_title"><s:message code="MSG.RLT.CHC" /></div> <!-- 관계를 선택하세요. -->
	</div>
<div style="clear:both; height:5px;"><span></span></div>

<div id="detailInfoRel"></div>
<form id="sheetRow" name="sheetRow">
<input type="hidden" id="sheetRow" name="sheetRow" style="disabled:disabled;" />
</form>
</body>
</html>
