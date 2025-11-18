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
		
		loadDetail();
		
    }
    
    
    
);

$(window).on('load',function() {
	initGrid();

});


$(window).resize(
    
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|선택|공통표준용어ID|차수|공통표준용어명|공통표준용어영문약어명|공통표준도메인ID|공통표준도메인명|저장 형식|표현 형식|허용값|행정표준코드명|소관기관명|공통표준용어설명|기관표준용어ID|기관표준용어논리명|기관표준단어물리명|등록유형코드|등록일시|등록사용자ID|등록사용자명", Align:"Center"}
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:80,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:80,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:100,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},   
                    
//                     {Type:"Combo",   Width:130,  SaveName:"stndAsrt",    Align:"Left", Edit:1, KeyField:1,UpdateEdit:0},
                    {Type:"Text",   Width:100,  SaveName:"govSditmId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:120,  SaveName:"enactDgr",    Align:"Left", Edit:0, KeyField:1},
                    {Type:"Text",   Width:300,  SaveName:"govSditmLnm",    Align:"Center", Edit:0 , KeyField:1},
                    {Type:"Text",   Width:340,  SaveName:"govSditmPnm",    Align:"Center", Edit:0, KeyField:1},
                    
                    {Type:"Text",   Width:200,  SaveName:"govDmnId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:200,  SaveName:"govDmnLnm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:200,  SaveName:"saveType",     Align:"Center", Edit:1},
                    {Type:"Text",   Width:200,  SaveName:"examData",     Align:"Center", Edit:1},
                    {Type:"Text",   Width:200,  SaveName:"cdValRule",      Align:"Center",   Edit:1},
                    {Type:"Text",   Width:200,  SaveName:"govStndCd",      Align:"Center",   Edit:1},
                    {Type:"Text",   Width:200,  SaveName:"govOrgNm",      Align:"Center",   Edit:1},
                    {Type:"Text",   Width:300,  SaveName:"objDescn",    Align:"Left", Edit:1, KeyField:1}, 
                    
                    {Type:"Text",   Width:200,  SaveName:"sditmId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:300,  SaveName:"sditmLnm",    Align:"Center", Edit:1, KeyField:0},
                    {Type:"Text",   Width:300,  SaveName:"sditmPnm",    Align:"Center", Edit:1, KeyField:0},
                    
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
	initgrid_sub_govsditmmapchange();
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/damgmt/gov/ajaxgrid/gov_sditm_map_dtl.do"/>', param, function(){
		if(!isBlankStr(param)) {
			initDtlGrid();
			grid_sub_govsditmmapchange.DoSearch('<c:url value="/commons/damgmt/gov/ajaxgrid/govsditmmapchange_dtl.do" />', param);
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
			$('#gov_sditm_map_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/commons/damgmt/gov/getGovSditmMapList.do" />", param);
        	break;
       
        case 'SaveRow': //단건 저장
    		//saction (I-입력, U-수정)
    		var urls = '<c:url value="/commons/damgmt/gov/saveGovSditmMapRow.do"/>';
    		var param = $('form[name=frmInput]').serialize();
    		ajax2Json(urls, param, ibscallback);
    		break;
    		
        case 'Save' : //엑셀 업로드용 저장
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(1); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/gov/saveGovSditmMapList.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;	
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'공통표준용어매핑조회'});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
		case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/gov/deleteGovSditmMapList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
            
        case 'Add' : //신규 추가
    		$('#gov_sditm_map_sel_title').html('용어를 클릭하시면 상세정보를 조회합니다.'); /* 용어를 클릭하시면 상세정보를 조회합니다. */
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
	var govSditmId = "&govSditmId="+grid_sheet.GetCellValue(row, "govSditmId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '공통표준 용어 논리명 :'+ param.govSditmLnm; /* 공통표준 용어 논리명 : */
	$('#gov_sditm_map_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(govSditmId);

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
		var tmphtml = '공통표준용어 : '+ param.govSditmLnm; /* 공통표준용어논리명 : */
		$('#gov_sditm_map_sel_title').html(tmphtml);
		
		var govsditmId = "";
		govsditmId = grid_sheet.GetCellValue(1, "govSditmId");
		param = "govSditmId="+govsditmId;
		loadDetail(param);
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">공통용어 단어 매핑 관리</div> <!-- 공통용어 단어 매핑 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="공통표준 용어 조회"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="govSditmLnm">공통표준 용어명</label></th> <!-- 공통표준 용어명 -->
                                <td colspan = "3"><input type="text" id="govSditmLnm" class="wd300" name="govSditmLnm" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="sditmLnm">기관표준 용어명</label></th> <!-- 기관표준 용어명 -->
                                <td colspan = "3"><input type="text" id="sditmLnm" class="wd300" name="sditmLnm" /></td>
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
		    <div class="selected_title" id="gov_sditm_map_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">공통표준 용어 등록</a></li>
	    <li><a href="#tabs-2"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
		<div id="tabs-2">
			<%@include file="govsditmmapchange_dtl.jsp" %>
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
