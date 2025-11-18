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
<title><s:message code="DMN.GRP.MNG" /></title> <!-- 도메인그룹 관리 -->

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";

$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });
        
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
//     			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
            	showMsgBox("CNF", "<s:message code='MSG.CHC.LWRK.LVL.DEL' />", "Delete");
        	}
        //	doAction("Delete");  
        	
        });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
     // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 

        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        //create_selectbox(usergJson, $("#usergId"));
        //펼치기 접기
        $("#btnSpread").click( function(){ grid_sheet.ShowTreeLevel(-1);} );
        $("#btnFold").click( function(){ grid_sheet.ShowTreeLevel(0);} );
         
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	
	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	var dmngId = "";
	dmngId = "${dmngId}";
	param = "dmngId="+dmngId;
	if(dmngId != null && dmngId != "" && dmngId != "undefined") {
		grid_sheet.DoSearch("<c:url value="/commons/damgmt/dmnginfo/dmngSelectlist.do" />", param);
		
	} 
	
	//도메인그룹의 기본정보레벨, SELECT BOX ID 확인
// 	var bscLvl = "${bscLvl}";
// 	var selectBoxId = "${selectBoxId}";
// 	alert(bscLvl);
// 	alert(selectBoxId);
	
});


$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));        
    	// grid_sheet.SetExtendLastCol(1);    
    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DMNG.LST'/>", Align:"Center"}
                    /* No.|상태|선택|도메인그룹ID|만료일시|시작일시|도메인그룹논리명|도메인그룹물리명|상위도메인그룹ID|상위도메인그룹명|도메인그룹레벨|코드도메인여부|인포타입변경가능여부|설명|버전|작성일자|작성사용자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",         Align:"Center", Edit:0},
                    {Type:"Status",   Width:60,   SaveName:"ibsStatus",      Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",       Align:"Center", Edit:1, Hidden:0, Sort:0},    
                    {Type:"Text",     Width:130,  SaveName:"dmngId",         Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",     Width:130,  SaveName:"expDtm",         Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",     Width:130,  SaveName:"strDtm",         Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Combo",    Width:130,  SaveName:"stndAsrt",       Align:"Center", Edit:1, KeyField:1,UpdateEdit:0},
                    {Type:"Text",     Width:130,  SaveName:"dmngLnm",        Align:"Left",   Edit:1, KeyField:1, TreeCol:1}, 
                    {Type:"Text",     Width:130,  SaveName:"dmngPnm",        Align:"Left",   Edit:1, KeyField:0},
                    {Type:"Popup",    Width:130,  SaveName:"uppDmngId",      Align:"Center", Edit:0, Hidden:1},    
                    {Type:"Popup",    Width:130,  SaveName:"uppDmngLnm",     Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"dmngLvl",        Align:"Center", Edit:0, Hidden:0},
                    {Type:"Combo",    Width:110,  SaveName:"cdDmnYn",        Align:"Center",   Edit:1, Hidden:0, KeyField:0},
                    {Type:"Combo",    Width:110,  SaveName:"infotpChgCanYn", Align:"Center",   Edit:1, Hidden:1, KeyField:0},
                    {Type:"Text",     Width:500,  SaveName:"objDescn",       Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"objVers",        Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Date",     Width:130,  SaveName:"writDtm",        Align:"Center",   Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",     Width:130,  SaveName:"writUserId",     Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"writUserNm", 	 Align:"Left", 	Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
        //표준분류
        SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
        SetColProperty("cdDmnYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("infotpChgCanYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
      
		InitComboNoMatchText(1, "");
        // FitColWidth();  
        
//         GetTreeActionMode(1);
        
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
        
	    case "New":        //동일레벨 추가...
	    	//현재행을 가져온다.
	    	var crow = grid_sheet.GetSelectRow();
	    	var clevel = grid_sheet.GetRowLevel(crow);
	    	
	    	//선택된 행의 다음라인에 현재레벨로 추가한다.
	    	var nrow = grid_sheet.DataInsert(crow+1, clevel);
			
	    	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
	     	grid_sheet.SetCellValue(nrow, "uppDmngId"	, grid_sheet.GetCellValue(crow, "uppDmngId"));
	     	grid_sheet.SetCellValue(nrow, "uppDmngLnm"	, grid_sheet.GetCellValue(crow, "uppDmngLnm"));
	    	grid_sheet.SetCellValue(nrow, "stndAsrt"	, grid_sheet.GetCellValue(crow, "stndAsrt"));
	     	grid_sheet.SetCellValue(nrow, "dmngLvl"		, grid_sheet.GetRowLevel(nrow) + 1); 
	    
	        break;
            
            
    	 case "NewLow":        //하위레벨추가...

         	//현재행을 가져온다.
         	var crow   = grid_sheet.GetSelectRow();
         	var clevel = grid_sheet.GetRowLevel(crow);
         	
         	//선택행의 다음라인에 하위레벨로 추가한다.
         	var nrow = grid_sheet.DataInsert();
         	
         	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
         	
	    	grid_sheet.SetCellValue(nrow, "uppDmngId"	, grid_sheet.GetCellValue(crow, "dmngId"));
	    	grid_sheet.SetCellValue(nrow, "uppDmngLnm"	, grid_sheet.GetCellValue(crow, "dmngLnm"));
	    	grid_sheet.SetCellValue(nrow, "stndAsrt"	, grid_sheet.GetCellValue(crow, "stndAsrt"));
	    	grid_sheet.SetCellValue(nrow, "dmngLvl"		, grid_sheet.GetRowLevel(nrow) + 1);
         	
         
             break;
            
        case "Delete" :
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(grid_sheet);
        	        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");

        	//json sort 합수 하위레벨로   desc 정렬 한다.
        	sortJson(DelJson.data, "dmngLvl", "int", false);  

        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/dmnginfo/dmngDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
        	var SaveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	//도메인그룹 레벨확인... 서비스Impl에서 구현함...
//         	var bscLvl = "${bscLvl}";
//         	for(var i=0; i<SaveJson.data.length; i++) {
//         		if(SaveJson.data[i].dmngLvl >= bscLvl) {
//         			showMsgBox("ERR", "도메인그룹 레벨은 '" + bscLvl + "'을(를) 넘을수 없습니다.<br> 도메인그룹 레벨은 기본정보레벨관리 페이지에서 변경하세요.");
//             		return;
//         		}
//         	}
        	
            var url = "<c:url value="/commons/damgmt/dmnginfo/dmngReglist.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/dmnginfo/dmngSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
	        //보여지는 컬럼들만 엑셀 다운로드          
	        var downColNms = "";
	       	for(var i=0; i<=grid_sheet.LastCol();i++ ){
	       		if(grid_sheet.GetColHidden(i) != 1){
	       			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	       		}
	       	}


            grid_sheet.Down2Excel({HiddenColumn:1,  DownCols:downColNms, Merge:1, FileName:"도메인그룹 관리"});
            break;
        case "LoadExcel":  //엑셀업로
        
          
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


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret, row) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	grid_sheet.SetCellValue(row, "uppDmngId", retjson.dmngId);
	grid_sheet.SetCellValue(row, "uppDmngLnm", retjson.dmngLnm);
// 	$("#frmSearch #subjLnm").val(retjson.subjLnm);
// 	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}

function grid_sheet_OnPopupClick(Row,Col) {
	
	//Format이 날짜인 경우 달력 팝업을 오픈한다.
// 	grid_sheet.ShowCalendar();
	var param = "row=" +Row;
	if(grid_sheet.GetCellValue(Row,"dmngLvl")=='0'){
		showMsgBox("INF","<s:message code='MSG.DMN.GRP.TOP' />")
		return ;
	}
	//사용자 검색 팝업 오픈
	if ("uppDmngLnm" == grid_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/commons/damgmt/dmnginfo/popup/dmnglst_pop.do" />';
		openLayerPop(url, 1100, 700, param);
	}
}


function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    if(row < 1) return;

}


//체크  이벤트가 발생한다.
function grid_sheet_OnChange(Row, Col) {

// 	 var vChk = grid_sheet.GetCellValue(Row,"ibsCheck") ;
	
// 	 var dmngId = grid_sheet.GetCellValue(Row,"dmngId"); 
 	 
//  	 for(var i = 1; i <= grid_sheet.RowCount(); i++) {
 		 
//  		 var uppDmngId =  grid_sheet.GetCellValue(i,"uppDmngId");
 		 
//  		 if(uppDmngId == dmngId){ 
 			 
//  			grid_sheet.SetCellValue(i,"ibsCheck", vChk); 
//  		 }  		 
//  	 } 	      
}

function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DMN.GRP.MNG" /></div> <!-- 도메인그룹 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                    <tbody>
                         <tr>       
                           <th scope="row" class="th_require"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					         <td>
					         <select  id="stndAsrt" name="stndAsrt"  class="wd300">
					                 <option value=""><s:message code="WHL"/></option>
					                 <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					         		  <option value="${code.codeCd}">${code.codeLnm}</option>
					         		</c:forEach>
					         </select>
					         </td>
                          <th scope="row"><label for="dmngLnm"><s:message code="DMN.GRP.NM" /></label></th> <!-- 도메인그룹명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="dmngLnm" id="dmngLnm" />
                                </span>
                             </td>  
                   </tbody> 
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />   
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
</div>  
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>