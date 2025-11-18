<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="STRD.DMN.ATMD.DIV" /></title> <!-- 표준도메인 자동분할 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

EnterkeyProcess("Search");


if (this.setIBEvents) {
    this.setIBEvents();
}

function setIBEvents() {
    window["grid_sheet_OnRowSearchEnd"] = function(row) {
        var col = 0;

        if (grid_sheet.GetCellValue(row, "dvOrgPnm") != "" && grid_sheet.GetCellValue(row, "dicAsmPnm") != "" && grid_sheet.GetCellValue(row, "dicAsmPnm") != grid_sheet.GetCellValue(row, "dvOrgPnm") ) {
        	grid_sheet.SetCellFontColor(row, "dicAsmPnm", "blue");
        	grid_sheet.SetCellFontColor(row, "dvOrgPnm", "red");
        }
    };
}

//var infotpIbs = ${codeMap.dmnginfotpibs};
$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
  
    // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  }).show();
    
	//추가 Event Bind
	$("#btnTreeNew").show();
	
    // 추가 Event Bind
    $("#btnNew").click(function(){ doAction("New");  }).show();
    
    // 저장 Event Bind
    $("#btnSave").click(function(){
		icons: { primary: "ui-icon-lightbulb" }
    	}).click(function(event){
    		
    		event.preventDefault();  //브라우저 기본 이벤트 제거...    		
    		//저장할래요? 확인창...
    		var message = "<s:message code='MSG.STRD.DMN.ATMD.DIV.EXCT' />"; //표준도메인 자동분할을 실행하시겠습니까?
    		showMsgBox("CNF", message, 'AutoDivision');	
	}).show().text("<s:message code='SPRT' />"); //자동분할
    
    //요청서작성 Event Bind
    $("#btn_rqst").click(function(){
    	var dataRows = grid_sheet.FindCheckedRow("ibsCheck"); //행 갯수 확인
    	if(dataRows == "") {
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVELIST" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code='MSG.STRD.DMN.LORQ.FLIN.DEMD.POSB.DATA.DEMD.STRD.ITEM.LORQ.FLIN' />";
		//표준도메인 요청서를 작성 시 요청가능한 데이터만 요청됩니다.<br>표준도메인 요청서를 작성하시겠습니까?
		showMsgBox("CNF", message, 'RqstWrite');	
    	//doAction("Save"); 	
	}).show();

    // 삭제 Event Bind
    $("#btnDelete").click(function(){
    	showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
    }).show();
    
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

    // 엑셀업로 Event Bind
     $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
    
     setCodeSelect("dvDmnRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));

});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
	//$( "#layer_div" ).show();
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
        
		var headtext  = "<s:message code='META.HEADER.STNDDMNDIV.LST.1'/>";
		headtext += "|<s:message code='META.HEADER.STNDDMNDIV.LST.2'/>";
		headtext += "|<s:message code='META.HEADER.STNDDMNDIV.LST.3'/>";

		//headtext  = "No.|상태|선택|요청구분|등록유형";
		//headtext += "|DV_RQST_NO|분할결과|표준분류|도메인논리명|도메인물리명|논리명기준구분|물리명기준구분|도메인그룹논리명|DMNG_ID|인포타입논리명|INFOTP_ID|데이터타입|길이|소수점|설명";
		//headtext += "|요청가능여부";

	var headers = [
				{Text:headtext, Align:"Center"}
			];
	
	var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
	
	InitHeaders(headers, headerInfo); 

	var cols = [						
				{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
				{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
				{Type:"CheckBox", Width:50, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0},
				{Type:"CheckBox", Width:70, SaveName:"delPartly", Align:"Center", Edit:1, Hidden:1},
				{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:0, Hidden:1},						
				{Type:"Combo",  Width:80,  SaveName:"regTypCd",	  Align:"Center", Edit:0, Hidden:1},						
				
				{Type:"Text",   Width:130,  SaveName:"dvRqstNo",	  Align:"Left", Edit:1, Hidden:1},	
				{Type:"Text",   Width:130,  SaveName:"dvRqstRes",  Align:"Left", Edit:0, KeyField:0},
				{Type:"Combo",  Width:100,  SaveName:"stndAsrt",   	Align:"Left", Edit:1, KeyField:1},
				{Type:"Text",   Width:100,  SaveName:"dvOrgLnm",  Align:"Left", Edit:1, KeyField:1},  
				{Type:"Text",   Width:100,  SaveName:"dvOrgPnm",  Align:"Left", Edit:1, KeyField:0},  
				
// 				{Type:"Text",   Width:150,  SaveName:"dicAsmLnm",	Align:"Left", Edit:1, KeyField:1},
				{Type:"Text",   Width:150,  SaveName:"dicAsmPnm",	Align:"Left", Edit:0}, 
				{Type:"Text",   Width:150,  SaveName:"dicAsmDsLnm",	Align:"Left", Edit:0},
				{Type:"Text",   Width:150,  SaveName:"dicAsmDsPnm",	Align:"Left", Edit:0},
								
				{Type:"Combo",  Width:100,  SaveName:"dmngLnm",	 	Align:"Left", Edit:1, Hidden:0, KeyField:1},
				{Type:"Text",   Width:100,  SaveName:"dmngId",	 	Align:"Left", Edit:1, Hidden:1},
				{Type:"Combo",  Width:100,  SaveName:"infotpLnm",	Align:"Left", Edit:1, Hidden:0, KeyField:1},
				{Type:"Text",   Width:100,  SaveName:"infotpId",	Align:"Left", Edit:1, Hidden:1},
				{Type:"Text",   Width:80,   SaveName:"dataType",	Align:"Left", Edit:0, Hidden:0},
				{Type:"Int",    Width:60,   SaveName:"dataLen",	 	Align:"Right", Edit:0, Hidden:0},
				{Type:"Int",    Width:60,   SaveName:"dataScal",	Align:"Right", Edit:0, Hidden:0}, 
				{Type:"Text",   Width:130,  SaveName:"dvObjDescn",	Align:"Left", Edit:1},					
				{Type:"Text",   Width:60,   SaveName:"regPosYn",	Align:"Left", Edit:1, Hidden:1},	
					
			];
				
	InitColumns(cols);
	
	//콤보 목록 설정
	SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
	SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
// 	SetColProperty("dmngLnm", 	${codeMap.dmngibs});
	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
	//SetColProperty("infotpLnm", ${codeMap.dmnginfotpibs});
	//SetColProperty("infotpLnm", ${codeMap.infotpibs});
	
// 	InitComboNoMatchText(1, "");
	SetSendComboData(0,"dmngLnm","Text");
	SetSendComboData(0,"infotpLnm","Text");
	//히든 컬럼 설정...
 	SetColHidden("regTypCd"	,1); 
        
//       	FitColWidth();  
        
        SetExtendLastCol(1);       
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function doAction(sAction, param)
{
	
    switch(sAction)
    {		    
	    case "New":
	    	var iRow = grid_sheet.DataInsert(0);
	    	
	    	var value0 = grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "stndAsrt");
	    	grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "dmngLnm", null);
       		$.each(${codeMap.dmngCodeListByStndAsrtIBS}, function(key, val) {
       			if(key == value0) {
       				grid_sheet.CellComboItem(grid_sheet.GetSelectRow(), "dmngLnm", val);
       				return;
       			}
       		});
       		
	    	var value = grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "dmngLnm");
	    	grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "infotpLnm", null);
       		$.each(${codeMap.infoTpCodeListIBS}, function(key, val) {
       			if(key == value) {
       				grid_sheet.CellComboItem(grid_sheet.GetSelectRow(), "infotpLnm", val);
       				return;
       			}
       		});
	    	break;
	    	
        case "Search":
        	var tmpstr = "";
        	if(param != null && param != "" && param != "undefined") {
        		tmpstr = "&dvRqstNo="+param + "&dvRqstDiv="+$("#dvRqstDiv").val();
        	}else{
        		if($("#dvRqstNo").val() == "") {
            		showMsgBox("ERR", "<s:message code='MSG.INQ.LST.CHC' />"); //조회목록을 먼저 선택하세요.
            		return; 
            	}
        		tmpstr = "&dvRqstNo="+ $("#dvRqstNo").val() + "&dvRqstDiv="+$("#dvRqstDiv").val();
        	}
        	
//         	var schParam = $("#frmSearch").serialize();
        	
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getDmnDvRqstList.do" />", tmpstr);
        	break;
        	
        case "AutoDivision":

			var prop = {KeyField:1};	
 	 		
 	 		grid_sheet.SetColProperty(0, "infotpLnm", prop);
            
 	 		var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
 	 		
 	 	   	//데이터 사이즈 확인...
 	 	   	if (SaveJson.Code == "IBS000") return; 
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
			//동일 표준도메인논리명 확인
// 			var DupRow = grid_sheet.ColValueDupRows("dvOrgLnm");
// 			if(DupRow != ""){
// 				var message = "<s:message code="ERR.EMPTY"  arguments="동일한 표준도메인논리명이 존재 합니다." />";
//     			showMsgBox("INF", message); 
// 				return;
// 			}
			
 	 	    var url = "<c:url value="/meta/stnd/regDmnAutoDiv.do"/>";

        	var param = "";

 	 	    IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        	
 	 	case "Delete" : 

 	 		var prop = {KeyField:0};	
 	 		
 	 		grid_sheet.SetColProperty(0, "infotpLnm", prop);
 	 	 	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/meta/stnd/delDmnAutoDiv.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
			break;   
			
        case "RqstWrite":
        
        	SaveJson = grid_sheet.GetSaveJson(1);
        	var url = "<c:url value="/meta/stnd/regStndDmnByDiv.do"/>";
        	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
			
 	    case "LoadExcel":  //엑셀업로드
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
 	        break;
 	        
        case "Down2Excel":  //엑셀내려받기
      //보여지는 컬럼들만 엑셀 다운로드          
	      var downColNms = "";
	     	for(var i=0; i<=grid_sheet.LastCol();i++ ){
	     		if(grid_sheet.GetColHidden(i) != 1){
	     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	     		}
	     	}
			grid_sheet.Down2Excel({HiddenColumn:1,DownCols:downColNms, Merge:1, FileName:'표준도메인자동분할'});
			
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
         //표준분류 변경
 	    if(grid_sheet.GetCellProperty(row, col, "SaveName") == "stndAsrt") {
   	    	grid_sheet.SetCellValue(row, "dmngLnm", null);
 
//    	    	alert(JSON.stringify(${codeMap.dmngCodeListByStndAsrtIBS}));
   	    	$.each(${codeMap.dmngCodeListByStndAsrtIBS}, function(key, val) {
//    	    		alert(key +"  "+ value +" " +val)
   	    		if(key == value) {
   	    			grid_sheet.CellComboItem(row, "dmngLnm", val);
   	    			return;
   	    		}
   	    	});
   	    }
 	    //도메인 그룹 변경
       	if(grid_sheet.GetCellProperty(row, col, "SaveName") == "dmngLnm") {
       		grid_sheet.SetCellValue(row, "infotpLnm", null);
       		$.each(${codeMap.infoTpCodeListIBS}, function(key, val) {
       			if(key == value) {
       				grid_sheet.CellComboItem(row, "infotpLnm", val);
       				return;
       			}
       		});
       	}
        		
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
	
	for (var i=0; i<totalcnt+1; i++) {
		
		var stndAsrt = grid_sheet.GetCellValue(i+1, "stndAsrt");

		$.each(${codeMap.dmngCodeListByStndAsrtIBS}, function(key, val) {
			if(key == stndAsrt) {
				grid_sheet.CellComboItem(i+1, "dmngLnm", val);
				grid_sheet.SetCellValue(i+1,"dmngLnm", grid_sheet.GetCellText(i+1, "dmngLnm"),0); //flag : 0  onchange 이벤트 없이 
				return;
			}
		});
		
		var tmpVal = grid_sheet.GetCellValue(i+1, "dmngLnm");
		$.each(${codeMap.infoTpCodeListIBS}, function(key, val) {
			
			if(key == tmpVal) {
				grid_sheet.CellComboItem(i+1, "infotpLnm", val);
				grid_sheet.SetCellValue(i+1,"infotpLnm", grid_sheet.GetCellText(i+1, "infotpLnm"));
				return;
			}
		});
	}
}

function grid_sheet_OnLoadExcel(result) {
	var totalcnt = grid_sheet.RowCount("I");
	
	for (var i=0; i<totalcnt+1; i++) {
		
		var stndAsrt = grid_sheet.GetCellValue(i+1, "stndAsrt");

		$.each(${codeMap.dmngCodeListByStndAsrtIBS}, function(key, val) {
			if(key == stndAsrt) {
				grid_sheet.CellComboItem(i+1, "dmngLnm", val);
				grid_sheet.SetCellValue(i+1,"dmngLnm", grid_sheet.GetCellText(i+1, "dmngLnm"),0); //flag : 0  onchange 이벤트 없이 
				return;
			}
		});
		
		var tmpVal = grid_sheet.GetCellValue(i+1, "dmngLnm");
		$.each(${codeMap.infoTpCodeListIBS}, function(key, val) {
			
			if(key == tmpVal) {
				grid_sheet.CellComboItem(i+1, "infotpLnm", val);
				grid_sheet.SetCellValue(i+1,"infotpLnm", grid_sheet.GetCellText(i+1, "infotpLnm"));
				return;
			}
		});
	}
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	switch(res.action) {
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			
			var rqstNo = res.resultVO.rqstNo;
			var bizDcd = res.resultVO.bizDcd;
			
			setCodeSelect("dvDmnRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));
			doAction("Search", rqstNo);
			
			var url = "../../goRqstPage.do?rqstNo="+rqstNo+"&bizDcd="+bizDcd; 
			window.open().location.href=url;
			
			break;
	
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.ETC.dvrqstno)) {
				setCodeSelect("dvDmnRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));
				doAction("Search", res.ETC.dvrqstno);    		
	    	} 
			break;
			
		//항목분할 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			if(!isBlankStr(res.ETC.dvrqstno)) {
				setCodeSelect("dvDmnRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));
				doAction("Search", res.ETC.dvrqstno);    		
	    	} 
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
	    	
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="BFHD.INTG.INQ" /></div> <!-- 사전통합 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INQ' />"> <!-- 표준단어조회 -->
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준단어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="dvRqstNo"><s:message code="INQ.LST" /></label></th> <!-- 조회목록 -->
								<td>
									<span id="selectDvRqstNo"><select id="dvRqstNo"  name="dvRqstNo" class="wd400">
									<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									</select></span>
								</td>
                                <th scope="row"><label for="dvRqstDiv"><s:message code="DIV.DSTC" /></label></th> <!-- 분할구분 -->
								<td>
									<select id="dvRqstDiv"  name="dvRqstDiv" >
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value="ASC"><s:message code="MIN.LNGT" /></option> <!-- 최소길이 -->
									<option value="DESC" selected><s:message code="MAX.LNGT" /></option> <!-- 최대길이 -->
									</select>
								</td>								
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.EXIS.ENSN.ABRV.DISPLAY.DATA.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 존재하는 영문약어는 빨간색으로 표시됩니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
        
        
		<div style="clear:both; height:10px;"><span></span></div>
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonStndDiv.jsp" />
	</div>

	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>
</div>


</body>
</html>

