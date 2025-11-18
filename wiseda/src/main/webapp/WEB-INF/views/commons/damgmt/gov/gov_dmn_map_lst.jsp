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
// EnterkeyProcess("Search");

$(document).ready(function() {
		 // 엑셀업로드용 저장 Event Bind
        $("#btnSave").click(function(){
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
		}).show();
        
     // 삭제 Event Bind
        $("#btnDelete").click(function(){ 

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
        	}
        });
   	 
    	// 추가 Event Bind
        $("#btnNew").click(function(){ doAction("Add");  }); 
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
    
   	 	// 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 

		loadDetail();
		
		//파라미터 : (자동완성 대상 오브젝트, 검색할 도메인종류, 최대표시 갯수(default-10개))
// 		setautoComplete($("#frmSearch #symnLnm"), "SYMN");
		//파라미터 : (자동완성 대상 오브젝트, 검색할 도메인종류, 최대표시 갯수(default-10개))
// 		setautoComplete($("#frmSearch #sbswdLnm"), "SBSWD");
    }
    
    
    
);

$(window).on('load',function() {
	initGrid();
});


$(window).resize(
    
    function(){
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|선택|공통표준도메인ID|제정차수|공통표준도메인그룹명|공통표준도메인분류명|공통표준도메인명|공통표준도메인설명|데이터타입|데이터길이|데이터소수점길이|저장형식|표현형식|단위|허용값|기관표준도메인ID|기관표준도메인논리명|기관표준도메인물리명|등록유형코드|등록일시|등록사용자ID|등록사용자명", Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},   
                    
//                     {Type:"Combo",   Width:130,  SaveName:"stndAsrt",    Align:"Left", Edit:1, KeyField:1,UpdateEdit:0},
                    {Type:"Text",   Width:50,  SaveName:"govDmnId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"enactDgr",    Align:"Left", Edit:0, KeyField:1},
                    {Type:"Text",   Width:200,  SaveName:"govUppDmngLnm",    Align:"Center", Edit:0 , KeyField:1},
                    {Type:"Text",   Width:200,  SaveName:"govDmngLnm",    Align:"Center", Edit:0 , KeyField:1},
                    {Type:"Text",   Width:200,  SaveName:"govDmnLnm",    Align:"Center", Edit:0 , KeyField:1},
                    {Type:"Text",   Width:400,  SaveName:"objDescn",    Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",   Width:100,  SaveName:"dataType",    Align:"Center", Edit:1, KeyField:1},
                    {Type:"Text",   Width:100,  SaveName:"dataLen",    Align:"Right", Edit:1, KeyField:0},
                    {Type:"Text",   Width:100,  SaveName:"dataScal",    Align:"Right", Edit:1, KeyField:0},
                    {Type:"Text",   Width:100,  SaveName:"saveType",    Align:"Left", Edit:1, KeyField:0},
                    {Type:"Text",   Width:100,  SaveName:"examData",    Align:"Left", Edit:1, KeyField:0},
                    {Type:"Text",   Width:100,  SaveName:"unitType",    Align:"Left", Edit:1, KeyField:0},
                    {Type:"Text",   Width:100,  SaveName:"cdValRule",    Align:"Left", Edit:1, KeyField:0},
                    
                    {Type:"Text",   Width:200,  SaveName:"dmnId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"dmnLnm",    Align:"Center", Edit:1, KeyField:0},
                    {Type:"Text",   Width:200,  SaveName:"dmnPnm",    Align:"Center", Edit:0, KeyField:0},
                    
                    {Type:"Combo",   Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:40,  SaveName:"regDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:40,  SaveName:"regUserId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"regUserNm",    Align:"Left", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
//         SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});     	
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initDtlGrid() {
	initsubgrid_govdmnmapchange();
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/damgmt/gov/ajaxgrid/gov_dmn_map_dtl.do"/>', param, function(){
		if(!isBlankStr(param)) {
			initgrid_sub_govdmnmapchange();
			grid_sub_govdmnmapchange.DoSearch('<c:url value="/commons/damgmt/gov/ajaxgrid/govdmnmapchange_dtl.do" />', param);
		}
	});
	
	
}


		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
			var param = $("#frmSearch").serialize();
			loadDetail();
			$('#gov_dmn_map_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/commons/damgmt/gov/getGovDmnMapList.do" />", param);
        	break;
       
        case 'SaveRow': //단건 저장
    		//saction (I-입력, U-수정)
    		var urls = '<c:url value="/commons/damgmt/gov/saveGovDmnMapRow.do"/>';
    		var param = $('form[name=frmInput]').serialize();
    		ajax2Json(urls, param, ibscallback);
    		break;
    		
        case 'Save' : //엑셀 업로드용 저장
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(1); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/gov/saveGovDmnMapList.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;	
        	
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'공통표준도메인매핑조회'});
            
            break;
        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
		case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/gov/deleteGovDmnMapList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
            
        case 'Add' : //신규 추가
    		$('#gov_dmn_map_sel_title').html('도메인를 클릭하시면 상세정보를 조회합니다.');
    		loadDetail();
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
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var govDmnId = "&govDmnId="+grid_sheet.GetCellValue(row, "govDmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '공통표준 도메인명 :'+ param.govDmnLnm;
	$('#gov_dmn_map_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(govDmnId);

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
	if (grid_sheet.SearchRows() == 1){
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '공통표준도메인 : '+ param.govDmnLnm;
		$('#gov_dmn_map_sel_title').html(tmphtml);
		
		var govdmnId = "";
		govdmnId = grid_sheet.GetCellValue(1, "govDmnId");
		param = "govDmnId="+govdmnId;
		loadDetail(param);
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">공통표준 도메인 매핑 관리</div>
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="공통표준 도메인 조회"> <!-- 표준도메인조회 -->
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준도메인 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="govUppDmngLnm">공통표준 도메인 그룹명</label></th>
                                <td ><input type="text" id="govUppDmngLnm" class="wd300" name="govUppDmngLnm" /></td>
                                <th scope="row"><label for="govDmngLnm">공통표준 도메인 분류명</label></th>
                                <td ><input type="text" id="govDmngLnm" class="wd300" name="govDmngLnm" /></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="govDmnLnm">공통표준 도메인명</label></th>
                                <td ><input type="text" id="govDmnLnm" class="wd300" name="govDmnLnm" /></td>
                                <th scope="row"><label for="dmnLnm">기관표준 도메인명</label></th>
                                <td ><input type="text" id="dmnLnm" class="wd300" name="dmnLnm" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td colspan="3"><input type="text" id="objDescn" class="wd300" name="objDescn"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="gov_dmn_map_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">공통표준 도메인 등록</a></li>
	    <li><a href="#tabs-2"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
		<div id="tabs-2">
			<%@include file="govdmnmapchange_dtl.jsp" %>
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->

   
<%-- <form id="frmExcel" name="frmExcel" action="" method="post" > --%>
<!-- 	<input type="hidden" name="excelhtml" id="excelhtml"> -->
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>
