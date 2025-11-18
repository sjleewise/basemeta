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
<title></title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
var interval = "";
var initTblGrid = false;
//var initIdxGrid = false;
EnterkeyProcess("Search");

$(document).ready(function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      

        $("#btnNew").click(function(){ $("#btnReset").click();  });
        
        $("#tabsTbl").hide();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
		// 저장 Event Bind
		$("#btnSave").click(function(){
		}).hide();
		
		//DDL조회 Event Bind
		$("#btnDelete").click(function(){
        	
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
        	}

		}).show();	
		
		$("#btnExcelLoad").hide();
});

$(window).on('load',function() {
	initGrid();
	$( "#tabsTbl" ).tabs().show();
	loadDetail("");
	doAction("Search");
	
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.TBLMST.LST'/>", Align:"Center"}
						/* No.|상태|선택|등록유형|주제영역|주테이블ID|주테이블 물리명|주테이블 논리명|보조테이블 주제영역|보조테이블 ID|보조테이블 물리명|보조테이블 논리명|스키마명|오브젝트ID|설명 */
					];
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Status", Width:50,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
	                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:1, Sort:0},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"subjLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Popup",   Width:100,   SaveName:"mstDdlTblId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Popup",   Width:100,   SaveName:"mstDdlTblPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"mstDdlTblLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"histSubjLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Popup",   Width:100,   SaveName:"histDdlTblId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Popup",   Width:100,   SaveName:"histDdlTblPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"histDdlTblLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dbSchId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"objId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:250,   SaveName:"objDescn", 		Align:"Left", Edit:0, Hidden:0}
					];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});

      	InitComboNoMatchText(1, "");
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
        case "New":        //추가
    	//첫행에 추가...
    	    
    	                    
        break;
        
        case "AddWam": //기존 테이블 추가
			var url   = "<c:url value="/meta/ddl/popup/ddltbl_pop.do"/>";
			var param = "popRqst=Y";
// 			var popup = OpenWindow(url+param,"pdmtbl_ddlrqst","800","600","yes");
			openLayerPop(url, 800, 600, param);
		
		break;
		
        case "Search":
        	
			var param = $("#frmSearch").serialize();

			grid_sheet.DoSearch("<c:url value="/meta/ddl/getTblMstList.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SaveRow" :
        	//TODO 공통으로 처리...
        	var saveJson = getform2IBSjson($("#frmInput"));
			if (saveJson.Code == "IBS000") return;
		
			if($("#frmInput #mstDdlTblLnm").val() == $("#frmInput #histDdlTblLnm").val()){
				showMsgBox("ERR", "<s:message code='MSG.MAIN.TBL.ASSI.TBL.SAME' />"); //주테이블과 보조테이블이 동일합니다
				return;
			}
			var url = "<c:url value="/meta/ddl/regTblMstList.do"/>";
			var param = "";//$('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);

        	break;	
        case "Delete" : 	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	
        	var url = "<c:url value="/meta/ddl/delTblMstList.do"/>";
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
       
        	break;
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}
    
    
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
						
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {

	
	if(row < 1) return;	

	if(grid_sheet.GetColEditable(col)) return;
	
	var param =  grid_sheet.GetRowJson(row);

	
		$( "#tabsTbl" ).tabs().show();
        
		var objId = "&objId="+grid_sheet.GetCellValue(row, "objId");
		
		
		
		//메뉴ID를 토대로 조회한다....
		loadDetail(objId);
}




function grid_sheet_OnSaveEnd(code, message) {

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
	$("#btnReset").click();
}


function loadDetail(param) {
		$('div#detailInfo1').load('<c:url value="/meta/ddl/ajaxgrid/tblmst_dtl.do"/>', param, function(){

		});
}
</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="BAUP.TBL.SYNC.MNG" /></div> <!-- 백업테이블동기화관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="mstDdlTblLnm"><s:message code="MAIN.TBL.NM" /></label></th><!-- 주테이블명 -->
							<td><input type="text" id="mstDdlTblLnm" name="mstDdlTblLnm" class="wd200" /></td>		
							<th scope="row"><label for="histDdlTblLnm"><s:message code="ASSI.TBL.NM" /></label></th><!-- 보조테이블명 -->
					        <td><input type="text" id="histDdlTblLnm" name="histDdlTblLnm" class="wd200" /></td>

						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="tabsTbl" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DTL.INFO" /></a></li> <!-- 상세정보 -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  </div>
	 </div>
	 </div>
</body>
</html>