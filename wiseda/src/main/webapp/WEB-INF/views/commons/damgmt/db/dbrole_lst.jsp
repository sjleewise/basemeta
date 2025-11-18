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
<title></title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      

		 // 엑셀업로드용 저장 Event Bind
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
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        });
   	 
    	// 추가 Event Bind
        $("#btnNew").click(function(){ doAction("Add");  }); 
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
    
   	 	// 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
		

    }
    
    
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	
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
                    {Text:"<s:message code='COMMON.HEADER.DBROLE.LST'/>", Align:"Center"}
                    /* No.|상태|선택|Role ID|만료일자|시작일자|권한명|권한명(논리)|DB접속대상명|설명|버전|등록유형|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:40,   SaveName:"roleId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",     Width:40,   SaveName:"expDtm",    Align:"Left", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",     Width:40,   SaveName:"strDtm",    Align:"Left", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",     Width:70,   SaveName:"rolePnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Text",     Width:70,   SaveName:"roleLnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Combo",    Width:60,   SaveName:"dbConnTrgId",    Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Text",     Width:100,  SaveName:"objDescn",    Align:"Left", Edit:1, Hidden:0},          
                    {Type:"Text",     Width:30,   SaveName:"objVers",    Align:"Left", Edit:0, Hidden:1},          
                    {Type:"Combo",    Width:40,   SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:0},          
                    {Type:"Date",     Width:60,   SaveName:"writDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},          
                    {Type:"Text",     Width:40,   SaveName:"writUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:50,   SaveName:"writUserNm",    Align:"Left", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	    SetColProperty("dbConnTrgId", ${codeMap.connTrgDbmsibs});
        //SetColHidden("rqstUserNm",1);

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
        case "Search":
        	
			var param = $("#frmSearch").serialize();
			$('#role_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/commons/damgmt/db/getDbRoleList.do" />", param);
        	break;
       
    		
        case 'Save' : //엑셀 업로드용 저장
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/db/regDbRoleList.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;	
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
		case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
//         	setTreeCheckIBS(grid_sheet);
        	
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/damgmt/db/delDbRoleList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
            
        case 'Add' : //신규 추가
    		$('#role_sel_title').html('');
    		grid_sheet.DataInsert(-1);
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
			doAction("Search");

			
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
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
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
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">DB ROLE <s:message code="MNG" /></div> <!-- 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='INQ.FORM' />"> <!-- 검색폼 -->
                   <caption><s:message code="INQ.FORM" /></caption> <!-- 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS.NM" /></label></th> <!-- DBMS명 -->
                                <td><select id="dbConnTrgId" class="wd300" name="dbConnTrgId">
                                       <option selected="" value=""><s:message code="WHL" /></option> <!-- 전체 -->
					                	<c:forEach var="code" items="${codeMap.connTrgDbms}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select></td>
                                <th scope="row"><label for="roleLnm"><s:message code="ROLE.NM" /></label></th> <!-- ROLE명 -->
                                <td><input type="text" id="roleLnm" class="wd300" name="roleLnm" /></td>
                            </tr>
                            
                   </tbody>
                 </table>   
            </div>
            </fieldset>
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="role_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

</div>

</body>
</html>
