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
<title><s:message code="SUBJ.TRRT.INQ" /></title> <!-- 주제영역조회 -->

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
		//alert(sysareaJson[0].codeCd + ":" + sysareaJson[0].codeLnm);
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
        
        $("#sysAreaId").change(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ 
        	  doAction("New");
        	
        });

        // 추가 Event Bind
        $("#btnNewLow").click(function(){ doAction("NewLow");  });

        // 저장 Event Bind
        $("#btnSave").click(function(){ 
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save");
        	
        }).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL.SUBJ" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete"); 
        }); //doAction("Delete");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(sysareaJson, $("#sysAreaId"));
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:1,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.SUB.LST'/>", Align:"Center"}
                    /* No.|상태|선택|시스템영역|주제영역ID|주제영역논리명|주제영역물리명|주제영역약어|상위주제영역ID|상위주제영역명|주제영역레벨|표준적용여부|레거시구분코드|구현물리모델수|설명|버전|등록유형|작성일시|작성자ID|작성자명|전체경로 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:1, Sort:0},
                    {Type:"Combo",    Width:170,   SaveName:"sysAreaId",    Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",     Width:100,   SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:200,   SaveName:"subjLnm",   	Align:"Left", Edit:1, KeyField:1, TreeCol:1},
                    {Type:"Text",     Width:120,   SaveName:"subjPnm",   	Align:"Left", Edit:1, KeyField:0}, 
                    {Type:"Text",     Width:90,    SaveName:"subjAbrNm", 	Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"uppSubjId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"uppSubjNm", 	Align:"Left", Edit:0},
                    {Type:"Text",     Width:80,    SaveName:"subjLvl", 	    Align:"Center", Edit:0, Hidden:0},                    
                    {Type:"Combo",    Width:100,   SaveName:"dbmsTypCd",    Align:"Center", Edit:1},
                    {Type:"Combo",    Width:80,    SaveName:"stdAplYn",   	Align:"Center", Edit:1},
                    {Type:"Combo",    Width:100,   SaveName:"stndAsrt",     Align:"Center", Edit:1},
                    {Type:"Combo",    Width:90,    SaveName:"lecyDcd",   	Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",     Width:180,   SaveName:"reaTblCnt",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:280,   SaveName:"objDescn",     Align:"Left", 	Edit:1},
                    {Type:"Text",     Width:40,    SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:40,    SaveName:"regTypCd",     Align:"Center", Edit:0, Hidden:1},                        
                    {Type:"Date",     Width:150,   SaveName:"writDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",     Width:50,    SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"writUserNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:300,   SaveName:"fullPath",     Align:"Left", Edit:0}
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
	    SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	    SetColProperty("stdAplYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("lecyDcd", ${codeMap.lecyDcdibs});
	    SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"}); /* 신규|변경|삭제 */
	    SetColProperty("dbmsTypCd", ${codeMap.dbmsTypCdibs});
	    SetColProperty("stndAsrt", ${codeMap.stndAsrtibs});
        
        InitComboNoMatchText(1, "");
        
        
      
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function checkDelIBS () {
	//체크박스 확인...
	if(!grid_sheet.CheckedRows("ibsCheck")) {
		//삭제할 대상이 없습니다...
		showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
		return;
	}
	
	showMsgBox("CNF", "<s:message code='MSG.CHC.LWRK.LVL.DEL' />", "Delete"); /* 선택한 행의 하위 레벨도 모두 삭제됩니다.<br>삭제 하시겠습니까? */
	
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "New":        //동일레벨 추가...
        	//현재행을 가져온다.
        	var crow = grid_sheet.GetSelectRow();
        	var clevel = grid_sheet.GetRowLevel(crow);
        	
        	//선택된 행의 다음라인에 현재레벨로 추가한다.
        	var nrow = grid_sheet.DataInsert(crow+1, clevel);
        	
        	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
        	grid_sheet.SetCellValue(nrow, "sysAreaId"	, grid_sheet.GetCellValue(crow, "sysAreaId"));
        	grid_sheet.SetCellValue(nrow, "uppSubjNm"	, grid_sheet.GetCellValue(crow, "uppSubjNm"));
        	grid_sheet.SetCellValue(nrow, "uppSubjId"	, grid_sheet.GetCellValue(crow, "uppSubjId"));
        	grid_sheet.SetCellValue(nrow, "subjLvl"		, grid_sheet.GetRowLevel(nrow)+1);
        	grid_sheet.SetCellValue(nrow, "stdAplYn"    , "Y");
        
            break;

        case "NewLow":        //하위레벨추가...

        	//현재행을 가져온다.
        	var crow = grid_sheet.GetSelectRow();
        	var clevel = grid_sheet.GetRowLevel(nrow);
        	//선택행의 다음라인에 하위레벨로 추가한다.
        	var nrow = grid_sheet.DataInsert();
        	        	
        	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
        	
        	grid_sheet.SetCellValue(nrow, "uppSubjId"	, grid_sheet.GetCellValue(crow, "subjId"));
        	grid_sheet.SetCellValue(nrow, "sysAreaId"	, grid_sheet.GetCellValue(crow, "sysAreaId"));
        	grid_sheet.SetCellValue(nrow, "uppSubjNm"	, grid_sheet.GetCellValue(crow, "subjLnm"));
        	grid_sheet.SetCellValue(nrow, "subjLvl"		, grid_sheet.GetRowLevel(nrow)+1);
        	
        
            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	setTreeCheckIBS(grid_sheet);

        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	
        	//주제영역에 테이블 갯수가 1개 이상일 경우 삭제 불가
        	var tblCnt = deleteCheck(DelJson);
        	if(tblCnt != 1) {
        		showMsgBox("ERR", tblCnt+" <s:message code='MSG.SUBJ.TRRT.PHYC.MDEL.EXIT' />"); /* 주제영역에 속한 물리모델이 존재합니다.<br>삭제할 수 없습니다. */
        		return;
        	}
        	
        	
        	var url = "<c:url value="/meta/subjarea/delsubjlist.do"/>";
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	console.log(SaveJson);
            var url = "<c:url value="/meta/subjarea/regsubjlist.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/subjarea/getsubjlist.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"주제영역 관리"});
            break;
            
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Append:1, Mode:'HeaderMatch'});
            break;
    }       
}
 
function deleteCheck(DelJson){
	
	for(var i=0; i<DelJson.data.length; i++) {
		if(DelJson.data[i].reaTblCnt == null || DelJson.data[i].reaTblCnt == '' || DelJson.data[i].reaTblCnt == '0'){
		
		} else {
			return DelJson.data[i].fullPath;  //삭제 불가일 경우 0 리턴
		}
	}
	return 1;
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
// 			alert (res.resultVO.progrmFileNm);
			//전체 내용을 다시 조회 할 경우 사용...
			//doAction("Search"); return;  
			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
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
    
    $("#hdnRow").val(row);
    
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
		grid_sheet.SetSelectRow(1);
		
		for(var i = 1; i <= grid_sheet.RowCount(); i++) {
			
			grid_sheet.SetCellEditable(i,"subjLnm",false); 			
		}
	}
	
}




</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SUBJ.TRRT.MNG" /></div> <!-- 주제영역 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td>
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                 </select>
                            </td>
                           <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /> </label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" />
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title">주제영역명 : <span></span></div>
	</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>