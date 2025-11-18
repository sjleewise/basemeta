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
<title></title>

<script type="text/javascript">


var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
		
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){
        	
        	doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
        $('#subjSearchPop').click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
			var popwin = OpenModal(url+"?"+param, "searchpop",  1200, 600, "no");
			popwin.focus();
		
	});
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
// 	loadDetail();
	
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
        
        SetMergeSheet(msHeaderOnly);
        //= "<s:message code='META.HEADER.ASIS.PDM.COL.LST'/>";
        var headtext  ="No.|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|As-Is컬럼|To-Be표준용어|To-Be표준용어|To-Be표준용어"
        var headtext2  = "No.|컬럼ID|주제영역|물리모델테이블ID|테이블논리명|테이블물리명|도메인ID|도메인명|도메인물리명|인포타입ID|인포타입명|컬럼명|컬럼한글명|컬럼순서|데이터타입|PK여부|PK순서|NOTNULL여부|DEFAULT값|암호화여부|등록유형|최초요청자ID|최초요청자|최초요청일시|요청자ID|요청자|요청일시|승인자ID|승인자|승인일시|버전|설명|표준용어ID|표준용어물리명|표준용어명"

        var headers= [{Text:headtext, Align:"Center"}, {Text:headtext2, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColId",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"fullPath",     Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmTblId",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"pdmTblLnm",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"pdmTblPnm",     Align:"Left",   Edit:0, Hidden:0},
                   
                    {Type:"Text",    Width:100,  SaveName:"dmnId",         Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dmnLnm",        Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dmnPnm",        Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"infotpId",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"infotpLnm",     Align:"Left",   Edit:0},
                    {Type:"Text",    Width:180,  SaveName:"pdmColPnm",     Align:"Left",   Edit:0},
                    {Type:"Text",    Width:180,  SaveName:"pdmColLnm",     Align:"Left",   Edit:0},
                    {Type:"Text",    Width:80,   SaveName:"colOrd",        Align:"Center",   Edit:0},

                    {Type:"Text",    Width:100,  SaveName:"dataType",       Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"pkYn",           Align:"Center",   Edit:0},
                    {Type:"Text",    Width:80,   SaveName:"pkOrd",          Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"nonulYn",        Align:"Center",   Edit:0},
                    {Type:"Text",    Width:80,   SaveName:"defltVal",       Align:"Center",   Edit:0},
                    {Type:"Text",    Width:80,   SaveName:"encYn",          Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Combo",   Width:50,   SaveName:"regTypCd",       Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserId",  Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserNm",  Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"frsRqstDtm",     Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserId",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserNm",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"rqstDtm",        Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserId",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserNm",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"aprvDtm",        Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:50,    SaveName:"objVers",       Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:200,  SaveName:"objDescn",       Align:"Left",   Edit:0},
                    
                    {Type:"Text",    Width:100,  SaveName:"sditmId",       Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"sditmPnm",      Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"sditmLnm",      Align:"Left",   Edit:0, Hidden:0}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"신규|변경|삭제"});
        SetColProperty("pkYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
        SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("dmnLnm",1);
        SetColHidden("infotpLnm",1);
        
//         SetExtendLastCol(1);    
        FitColWidth();
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
        	
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/getasispdmcollist.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;

			/*
        	if (isBlankStr($("#subjNm").val()) ) {
        		showMsgBox("ERR", "주제영역은 필수입력 항목입니다.");
        		return;
        	}
        	if (isBlankStr($("#pdmTblLnm").val()) && isBlankStr($("#pdmColLnm").val())) {
        		showMsgBox("ERR", "테이블 또는 용어명을 입력 후 조회하십시요. ");
        		return;
        	}
        	*/

        	
// 			var param = $("#frmSearch").serialize();
// 			grid_sheet.DoSearch("<c:url value="/meta/stnd/getasiscolvsitem.do" />", param);
// 			//loadDetail();
//         	break;


       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"AS-IS컬럼 vs TO-BE표준용어"});
            
            break;
        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
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
	
	if(grid_sheet.GetCellValue(row, "sditmId")!=''){
		var param = "objId="+grid_sheet.GetCellValue(row, "sditmId");
		//alert(param);
		window.open().location.href = "<c:url value="/meta/stnd/sditm_lst.do"/>"+"?"+ param;
	}
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//var sditmId = "&sditmId="+grid_sheet.GetCellValue(row, "sditmId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '표준용어논리명 : ' + param.sditmLnm;
	$('#sditm_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
// 	loadDetail(sditmId);
	
	
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/stnd/ajaxgrid/sditminfo_dtl.do"/>', param, function(){
		
		if(!isBlankStr(param)) {
			initDtlGrids();
			grid_sub_sditmchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmchange_dtl.do" />', param);
			grid_sub_sditminit.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditminit_dtl.do" />', param);
			grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmwhereused_dtl.do" />', param);	
		}
		
		//$('#tabs').show();
	});
}

function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	if(!isBlankStr("${sditmId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '표준용어논리명 : ' + param.sditmLnm +' [ 표준용어ID : ' + param.sditmId + ' ]';
		$('#sditm_sel_title').html(tmphtml);
		
		var sditmId = "";
		sditmId = grid_sheet.GetCellValue(1, "sditmId");
		param = "sditmId="+sditmId;
		
		loadDetail(param);
// 		grid_sub_sditmchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmchange_dtl.do" />', param);
// 		grid_sub_sditminit.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditminit_dtl.do" />', param);
// 		grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmwhereused_dtl.do" />', param);
	}
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	
	$("#frmSearch #subjLnm").val(retjson.fullPath);
	
}

</script>
</head>

<body>

 <div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">표준용어 조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준용어조회">
                   <caption>표준용어 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                   		<tr>
							<th scope="row"><label for="subjLnm">주제영역</label></th>
							<td><input type="text" id="subjLnm" name="subjLnm" class="wd200"/>
							  <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
							</td>
							<th scope="row"><label for="pdmTblLnm">테이블</label></th>
							<td><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd200"/></td>
						</tr>
						<tr>
							<th scope="row"><label for="sditmLnm">용어명(컬럼명)</label></th>
							<td><input type="text" id="pdmColLnm" name="pdmColLnm" class="wd200"/></td>
							<th scope="row"><label for="selectType">유형</label></th>
							<td>
								<select id="selectType" name="selectType">
									<option value="">전체</option>
									<option value="Y">매핑</option>
									<option value="N">비매핑</option>
								</select>
							</td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">검색조건 입력시 LIKE 검색으로 조회됩니다.</div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
 
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<!-- 
	<div class="selected_title_area">
		    <div class="selected_title" id="sditm_sel_title"> <span></span></div>
	</div>
	 -->
	
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	 </div>
</body>
</html>