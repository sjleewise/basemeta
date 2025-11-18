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
<title><s:message code="DEPT.REG" /></title> <!-- 부서 등록 -->

<script type="text/javascript">

//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

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
            	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
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
//         create_selectbox(usergJson, $("#usergId"));
        
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DEPT.LST'/>", Align:"Center"}
                    /* No.|상태|선택|부서ID|만료일시|시작일시|부서명|상위부서ID|상위부서명|부서레벨|설명|객체버전|등록유형코드|작성일시|작성사용자ID|작성자명|전체부서명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"deptId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",   Width:50,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",   Width:50,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:140,  SaveName:"deptNm",    Align:"Left", Edit:1, Hidden:0, TreeCol:1, KeyField:1}, 
                    {Type:"Text",   Width:40,  SaveName:"uppDeptId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"uppDeptNm",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:30,  SaveName:"deptLvl",    Align:"Right", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Text",   Width:120,  SaveName:"objDescn",    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Right", Edit:0, Hidden:1},          
                    {Type:"Combo",  Width:30,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"<s:message code='COMBO.CUD' />"}, 
                    {Type:"Date",   Width:60,  SaveName:"writDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:40,  SaveName:"writUserId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"writUserNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"allDeptNm",    Align:"Left", Edit:0, Hidden:0}
                    
                    /*  {Type:"Text",   Width:130,  SaveName:"userTelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"userHtelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"emailAddr",      Align:"Left",   Edit:1, Hidden:0},

                                    {Type:"Combo",  Width:100,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:130,  SaveName:"writUserId",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"writUserNm",   Align:"Center", Edit:0} */
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	//SetColProperty("usergId", ${codeMap.usergp});
        //SetColProperty("usergId", 	{ComboCode:"DA|AD|DB|UR|MR|DV", 	ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
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
        
    case "New":        //동일레벨 추가...
    	//현재행을 가져온다.
    	var crow = grid_sheet.GetSelectRow();
    	var clevel = grid_sheet.GetRowLevel(crow);
    	
    	//선택된 행의 다음라인에 현재레벨로 추가한다.
    	var nrow = grid_sheet.DataInsert(crow+1, clevel);
    	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
     	grid_sheet.SetCellValue(nrow, "uppDeptId"	, grid_sheet.GetCellValue(crow, "uppDeptId"));
     	grid_sheet.SetCellValue(nrow, "uppDeptNm"	, grid_sheet.GetCellValue(crow, "uppDeptNm"));
     	grid_sheet.SetCellValue(nrow, "deptLvl"		, grid_sheet.GetRowLevel(nrow));
    
        break;
            
            
    	 case "NewLow":        //하위레벨추가...

         	//현재행을 가져온다.
         	var crow = grid_sheet.GetSelectRow();
         	var clevel = grid_sheet.GetRowLevel(crow);
         	
         	//선택행의 다음라인에 하위레벨로 추가한다.
         	var nrow = grid_sheet.DataInsert();
         	
         	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
         	
	    	grid_sheet.SetCellValue(nrow, "uppDeptId"	, grid_sheet.GetCellValue(crow, "deptId"));
	    	grid_sheet.SetCellValue(nrow, "uppDeptNm"	, grid_sheet.GetCellValue(crow, "deptNm"));
	    	grid_sheet.SetCellValue(nrow, "deptLvl"		, grid_sheet.GetRowLevel(nrow));
	    	
         	
         
             break;
             
             
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	setTreeCheckIBS(grid_sheet);
        	
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/sys/dept/deptDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/sys/dept/deptReglist.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/sys/dept/deptSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"부서관리"});
            
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
function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DEPT.MNG"/></div> <!-- 부서 관리 -->
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
                  </colgroup>
                   
                   <tbody>                          
                            <th scope="row"><label for="deptNm"><s:message code="DEPT.NM" /></label></th><!-- 부서명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="deptNm" id="deptNm" />
                                </span>
                                </td>  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />   
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
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