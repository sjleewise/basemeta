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
<title>메타데이터GAP분석</title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();

        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
    }
);

$(window).on('load',function() {
	initGrid();
	loadDetail();
	$(window).resize();
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	//setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        SetMergeSheet(5);

        var headText1 = "<s:message code='META.HEADER.MTAGAP.LST.1'/>";
        //"No.|GAP상태|GAP유형|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타";
        var headText2 = "<s:message code='META.HEADER.MTAGAP.LST.2'/>";
        //"No.|GAP상태|GAP유형|기관|정보시스템|DBID|DB명|테이블ID|테이블영문명|테이블한글명|컬럼수|기관|정보시스템|DBID|DB명|스키마ID|스키마명|테이블영문명|테이블한글명|컬럼수";

        
        var headers = [
                    {Text:headText1, Align:"Center"},
                    {Text:headText2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq"        , Align:"Center", Edit:0},
                    //{Type:"CheckBox", Width:50,   SaveName:"ibsChk"        , Align:"Center", Edit:1},
                    {Type:"Combo",    Width:10,   SaveName:"gapStatus"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"gapConts"      , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Combo",     Width:90,  SaveName:"mtaOrgCd"     , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Combo",     Width:90,  SaveName:"mtaInfoSysCd" , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:90,  SaveName:"mtaDbConnTrgId"      , Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:90,  SaveName:"dbConnTrgPnm"      , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:100,  SaveName:"mtaTblId"     , Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:90,  SaveName:"mtaTblPnm"    , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:90,  SaveName:"mtaTblLnm"   , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width: 50,  SaveName:"mtaColCnt"    , Align:"Right",  Edit:0},
                    
                    {Type:"Combo",     Width:90,  SaveName:"dbcOrgCd"    , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Combo",     Width:90,  SaveName:"dbcInfoSysCd"    , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:90,  SaveName:"dbConnTrgId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:90,  SaveName:"dbConnTrgLnm", Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:90,  SaveName:"dbSchId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:90,  SaveName:"dbSchLnm"     , Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:90,  SaveName:"dbcTblNm"     , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:90,  SaveName:"dbcTblKorNm"     , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",     Width: 50,  SaveName:"dbcColCnt"    , Align:"Right",   Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */

		SetColProperty("dbcOrgCd", ${codeMap.orgCdibs});
		SetColProperty("mtaOrgCd", ${codeMap.orgCdibs});
		SetColProperty("mtaInfoSysCd", 	${codeMap.infoSysCdibs});
		SetColProperty("dbcInfoSysCd", 	${codeMap.infoSysCdibs});
        
        InitComboNoMatchText(1, "");
        FitColWidth();  
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//상세정보호출
function loadDetail(param) {
	if(!isBlankStr(param)) {
		//그리드 초기화 한다.
		initDtlGrids();
		grid_sub_sheet.DoSearch('<c:url value="/meta/mta/getMtaColGapList.do" />', param);
	}
}
		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":

        	$('#program_sel_title').html('');
        	//그리드 초기화 한다.
			initDtlGrids();
			var param = $("#frmSearch").serialize();
			loadDetail();
			grid_sub_sheet.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/meta/mta/getMtaGapList.do" />", param);
        	break;

        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;

    }       
}

//그리드 초기화 한다.
var chkinitgrid = false;
function initDtlGrids(){
	if (!chkinitgrid) {
		
		//컬럼정의서 그리드 초기화
	 	initsubgrid();
	 	chkinitgrid = true;	
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

	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//선택한 상세정보를 가져온다...
	//var param =  grid_sheet.GetRowJson(row);
	var param = "&tblNm=";
	var tblPnm = "";
	
	if(grid_sheet.GetCellValue(row, "mtaTblPnm") != ""){
		tblPnm = grid_sheet.GetCellValue(row, "mtaTblPnm");
		
	}else {
		tblPnm = grid_sheet.GetCellValue(row, "dbcTblNm");
	}
	
	//선택한 그리드의 테이블영문명을 내용을 보여준다.....
	var tmphtml = "<s:message code='CENM.TBL.PHYC.NM'/> : " + tblPnm;  /*테이블영문명*/
	$('#program_sel_title').html(tmphtml);

	param += tblPnm + "&dbSchId=" + grid_sheet.GetCellValue(row, "dbSchId") 
	+ "&dbcTblNm=" + grid_sheet.GetCellValue(row, "dbcTblNm")
	+ "&mtaTblId=" + grid_sheet.GetCellValue(row, "mtaTblId") ;
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(param);
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
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

	grid_sheet.SetExtendLastCol(1);  
	
	if(!isBlankStr("${mtaTblId}")) {
		
		var param = "&tblNm=";
		var tblPnm = "";
		
		if(grid_sheet.GetCellValue(row, "mtaTblPnm") != ""){
			tblPnm = grid_sheet.GetCellValue(row, "mtaTblPnm");
			
		}else {
			tblPnm = grid_sheet.GetCellValue(row, "dbcTblNm");
		}
		
		//선택한 그리드의 테이블영문명을 내용을 보여준다.....
		var tmphtml = "<s:message code='CENM.TBL.PHYC.NM'/> : " + tblPnm;  /*테이블영문명*/
		$('#program_sel_title').html(tmphtml);

		param += tblPnm + "&dbSchId=" + grid_sheet.GetCellValue(row, "dbSchId") 
		+ "&dbcTblNm=" + grid_sheet.GetCellValue(row, "dbcTblNm")
		+ "&mtaTblId=" + grid_sheet.GetCellValue(row, "mtaTblId") ;
		
		//메뉴ID를 토대로 조회한다....
		loadDetail(param);
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">컬럼정보</div>
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메타데이터GAP분석">
                   <caption>메타데이터GAP분석 검색폼</caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
      		                    <th scope="row"><label for="orgNm"><s:message code="ORG.NM" /></label></th>  <!-- 기관명 -->
                                <td><input type="text" id="orgNm" class="wd300" name="orgNm"/></td>
                                
                                <th scope="row"><label for="infoSysNm"><s:message code="INFO.SYS.NM" /></label></th>  <!-- 정보시스템명 -->
                                <td><input type="text" id="infoSysNm" class="wd300" name="infoSysNm"/></td>
                            	
                                <%-- <th scope="row" class="th_require"><label for="dbSchId"> DB명/스키마명</label></th>  <!-- DBMS/스키마명 -->
		                        <td>
					            <select id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value="">선택</option>
					            </select>
					            <select id="dbSchId" class="" name="dbSchId">
					             <option value="">선택</option>
					             </select>
					            </td> --%>
                            	<th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.NM" /></label></th>  <!-- DB명 -->
                                <td><input type="text" id="dbConnTrgLnm" class="wd300" name="dbConnTrgLnm"/></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="gapStatus"><s:message code="CENM.GAP.TYPE" /></label></th>  <!-- GAP유형 -->
                                <td>
									<select id="gapStatus" class="" name="gapStatus">
										<option value="">전체</option>
										<option value="NML">정상</option>
										<option value="GAP">GAP</option>
								 	</select>
								</td>
                                
                                <th scope="row"><label for="tblNm"><s:message code="TBL.NM" /></label></th>  <!-- 테이블명 -->
                                <td colspan="3"><input type="text" id="tblNm" class="wd300" name="tblNm"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
        </form>
</div>
<div style="clear:both; height:10px;"><span></span></div>

       <!-- 조회버튼영역  -->         
		<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" name="btnSearch" style="display:none"><s:message code="INQ"/></button> <!-- 조회 -->
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	

	<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
    
    <div style="clear:both; height:5px;"><span></span></div>
    
    <!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">컬럼정보</a></li> <!-- 컬럼정보 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="mtagap_dtl.jsp" %>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</div>

</body>
</html>