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
    		var message = "<s:message code='STRD.TERMS.ATMD.DIV.EXCT'/>"; //표준용어 자동분할을 실행하시겠습니까?
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
		var message = "<s:message code='STRD.TERMS.LORQ.FLIN.DEMD.POSB.DATA.DEMD.STRD.TERMS.LORQ.FLIN'/>"; //표준용어 요청서를 작성 시 요청가능한 데이터만 요청됩니다.<br>표준용어 요청서를 작성하시겠습니까?
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
    
     setCodeSelect("dvRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));

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
        
		var headtext  = "<s:message code='META.HEADER.STNDITEMDIV.LST.1'/>";
		headtext += "|<s:message code='META.HEADER.STNDITEMDIV.LST.2'/>";
		headtext += "|<s:message code='META.HEADER.STNDITEMDIV.LST.3'/>";

		//headtext  = "No.|상태|선택|요청구분|등록유형";
		//headtext += "|DV_RQST_NO|분할결과|표준분류|표준용어논리명|표준용어물리명|표준용어분할논리명|표준용어분할물리명|논리명기준구분|물리명기준구분|도메인논리명|도메인물리명|도메인그룹|인포타입논리명|데이터타입|길이|소수점|암호화여부|설명";
		//headtext += "|요청가능여부";

	var headers = [
				{Text:headtext, Align:"Center"}
			];
	
	var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
	
	InitHeaders(headers, headerInfo); 

	var cols = [						
				{Type:"Seq",      Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
				{Type:"Status",   Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
				{Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
				{Type:"Combo",    Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:0, Hidden:1},						
				{Type:"Combo",    Width:80,   SaveName:"regTypCd",	  Align:"Center", Edit:0, Hidden:1},						
				
				{Type:"Text",     Width:130,  SaveName:"dvRqstNo",	  Align:"Left", Edit:1, Hidden:1},	
				{Type:"Text",     Width:150,  SaveName:"dvRqstRes",  Align:"Left", Edit:0, KeyField:0},
				{Type:"Combo",    Width:100,  SaveName:"stndAsrt",   	Align:"Left", Edit:1, KeyField:1},
				{Type:"Text",     Width:180,  SaveName:"dvOrgLnm",  Align:"Left", Edit:1, KeyField:1},				
				{Type:"Text",     Width:100,  SaveName:"dvOrgPnm",  Align:"Left", Edit:1, KeyField:0},				
				{Type:"Text",     Width:100,  SaveName:"dicAsmLnm",  Align:"Left", Edit:0, KeyField:0},
				{Type:"Text",     Width:100,  SaveName:"dicAsmPnm",  Align:"Left", Edit:0, KeyField:0}, 
				{Type:"Text",     Width:100,  SaveName:"dicAsmDsLnm",  Align:"Left", Edit:0, KeyField:0}, 
				{Type:"Text",     Width:100,  SaveName:"dicAsmDsPnm",  Align:"Left", Edit:0, KeyField:0}, 
				{Type:"Text",     Width:80,   SaveName:"dmnLnm", 	  Align:"Left", Edit:0, Hidden:0},
				{Type:"Text",     Width:80,   SaveName:"dmnPnm", 	  Align:"Left", Edit:0, Hidden:0},
				{Type:"Text",     Width:80,   SaveName:"dmngLnm",   Align:"Left", Edit:0, Hidden:0},
				{Type:"Text",     Width:80,   SaveName:"infotpLnm", Align:"Left", Edit:1, Hidden:0},
				{Type:"Text",     Width:80,   SaveName:"dataType",  Align:"Left", Edit:0, Hidden:0},
				{Type:"Int",      Width:80,   SaveName:"dataLen",	  Align:"Rigth", Edit:0, Hidden:0},
				{Type:"Int",      Width:80,   SaveName:"dataScal",  Align:"Rigth", Edit:0, Hidden:0},
				{Type:"Combo",    Width:80,   SaveName:"dvEncYn",	  Align:"Center", Edit:1},		
				{Type:"Text",     Width:130,  SaveName:"dvObjDescn",	  Align:"Left", Edit:1, KeyField:1},	
				{Type:"Text",     Width:60,   SaveName:"regPosYn",	  Align:"Left", Edit:1, Hidden:1},	
					
			];
				
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
		
		SetColProperty("dvEncYn", 	{ComboCode:"N|Y", ComboText:"N|Y", DefaultValue:"N"});
		//SetColProperty("persInfoCnvYn", 	{ComboCode:"N|Y", ComboText:"N|Y", DefaultValue:"N"});
		
		InitComboNoMatchText(1, "");
		
		//히든 컬럼 설정...
	 	SetColHidden("regTypCd"	,1); 
        
        
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
	    	break;
	    	
        case "Search":
        	var tmpstr = "";
        	
        	if(param != null && param != "" && param != "undefined") {
        		tmpstr = "&dvRqstNo="+ param + "&dvRqstDiv="+$("#dvRqstDiv").val();
        		
        		$("#dvRqstNo").val(param); 
        	}else{
        		
        		if($("#dvRqstNo").val() == "") {
            		showMsgBox("ERR", "조회목록을 먼저 선택하세요.");
            		return;
            	}        		
        	}
        	
        	var param = "";
        	
        	param += $("#frmSearch").serialize();        	         	
        	
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getItemDvRqstList.do" />", param);
        	break;
        	
        case "AutoDivision":
 	 		var SaveJson = grid_sheet.GetSaveJson(1); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
 	 		
 	 	   	//데이터 사이즈 확인...
 	 	   	if (SaveJson.Code == "IBS000") return; 
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
			//동일 표준용어논리명 확인
// 			var DupRow = grid_sheet.ColValueDupRows("dvOrgLnm");
// 			if(DupRow != ""){
// 				var message = "<s:message code="ERR.EMPTY"  arguments="동일한 표준용어논리명이 존재 합니다." />";
//     			showMsgBox("INF", message); 
// 				return;
// 			}
 	 	    var url = "<c:url value="/meta/stnd/regItemAutoDiv.do"/>";
 	 	    var param = $("#frmSearch").serialize();
 	 	    IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        	
 	 	case "Delete" : 
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/meta/stnd/delItemAutoDiv.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
			break;   
			
        case "RqstWrite":
        
        	var SaveJson = grid_sheet.GetSaveJson(1);
			
        	//데이터 사이즈 확인...
 	 	   	if (SaveJson.Code == "IBS000") return; 
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
        	var url = "<c:url value="/meta/stnd/regStndItemByDiv.do"/>";
        	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
			
 	    case "LoadExcel":  //엑셀업로드
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
 	        break;
 	        
        case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel(    { HiddenColumn:1 , Merge:1, FileName:'표준용어자동분할.xlsx'}    );
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



//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	switch(res.action) {
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			
			var rqstNo = res.resultVO.rqstNo;
			var bizDcd = res.resultVO.bizDcd;
			
			setCodeSelect("dvRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));
			doAction("Search", rqstNo);
			
			var url = "../../goRqstPage.do?rqstNo="+rqstNo+"&bizDcd="+bizDcd; 
			window.open().location.href=url;
			
			break;
	
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.ETC.dvrqstno)) {
				setCodeSelect("dvRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));
				
				doAction("Search", res.ETC.dvrqstno);    										
	    	} 
			break;
			
		//항목분할 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			if(!isBlankStr(res.ETC.dvrqstno)) {
				setCodeSelect("dvRqstNo", "L", $("form[name=frmSearch] #dvRqstNo"));
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
	    <div class="menu_title"><s:message code="ITEM.ATMD.DIV" /></div> <!-- 항목 자동분할 -->
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
                   <col style="width:11%;" />
                   <col style="width:30%;" />
                   <col style="width:11%;" />
                   <col style="width:15%;" />                   
                   <col style="width:11%;" />
                   <col style="width:*;" />
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
									<option value="ASC"><s:message code="MIN.LNGT" /></option> <!-- 최소길이 -->
									<option value="DESC" selected="selected"><s:message code="MAX.LNGT" /></option> <!-- 최대길이 -->
								</select>
							</td>
					
							<th scope="row"><label for="dvRqstDiv"><s:message code="DIV.DSTC.DMN" /></label></th> <!-- 분할구분(도메인) -->
							<td>
							    <select id="dvRqstDivDmn"  name="dvRqstDivDmn" >									
							       <option value="ASC"><s:message code="MIN.LNGT" /></option> <!-- 최소길이 -->
								   <option value="DESC" selected="selected"><s:message code="MAX.LNGT" /></option> <!-- 최대길이 -->
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
	<!-- 그리드 입력 입력 End -->
   	
</div>


</body>
</html>

