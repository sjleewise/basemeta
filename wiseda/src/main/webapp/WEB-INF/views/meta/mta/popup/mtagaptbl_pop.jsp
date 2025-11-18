<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title></title>

<script type="text/javascript">

$(document).ready(function() {
	$(window).focus();
	
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
    
  //폼 초기화 버튼 초기화...
  	$("#popReset").hide();
  
/* 	$('#popReset').click(function(event){
		event.preventDefault();
		$("#mtaColPnm").val("");
	});
           */        
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){

    	//iframe 형태의 팝업일 경우
    	//if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	//} else {
    //		window.close();
    	//}
    	
    });
    
  //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    //setautoComplete($("#frmSearch #mtaTblLnm"), "MTATBL");

/*     double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});
   	 */

    doAction("Search");
});
//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	loadDetail();
	//$(window).resize();
});


$(window).resize(function(){
		 //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
		setibsheight($("#grid_02"));        
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
                    {Type:"Seq",      Width:40,   SaveName:"ibsSeq"        , Align:"Center", Edit:0},
                    //{Type:"CheckBox", Width:50,   SaveName:"ibsChk"        , Align:"Center", Edit:1},
                    {Type:"Combo",    Width:10,   SaveName:"gapStatus"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"gapConts"      , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Combo",     Width:90,  SaveName:"mtaOrgCd"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",     Width:90,  SaveName:"mtaInfoSysCd" , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",     Width:90,  SaveName:"mtaDbConnTrgId"      , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:90,  SaveName:"dbConnTrgPnm"      , Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:100,  SaveName:"mtaTblId"     , Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:90,  SaveName:"mtaTblPnm"    , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:90,  SaveName:"mtaTblLnm"   , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width: 50,  SaveName:"mtaColCnt"    , Align:"Right",  Edit:0},
                    
                    {Type:"Combo",     Width:90,  SaveName:"dbcOrgCd"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",     Width:90,  SaveName:"dbcInfoSysCd"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",     Width:90,  SaveName:"dbConnTrgId"    , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:90,  SaveName:"dbConnTrgLnm", Align:"Left",   Edit:0, Hidden:1},
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

		SetColProperty("mtaDbConnTrgId", 	${codeMap.connTrgSchibs});
		SetColProperty("dbConnTrgId", 	${codeMap.connTrgSchibs});
        
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

        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/meta/mta/getMtaGapList.do" />', param);

        	break;
        	
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
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
	
	
	
}

//상세정보호출
function loadDetail(selectRowJsonData) {
	if(!isBlankStr(selectRowJsonData)) {

		var param = "&tblNm=";
		var tblPnm = "";
		
		if(selectRowJsonData.mtaTblPnm != ""){
			tblPnm = selectRowJsonData.mtaTblPnm;
			
		}else {
			tblPnm = selectRowJsonData.dbcTblNm;
		}
		
		//선택한 그리드의 테이블영문명을 내용을 보여준다.....
		var tmphtml = "<s:message code='CENM.TBL.PHYC.NM'/> : " + tblPnm;  /*테이블영문명*/
		$('#program_sel_title').html(tmphtml);
		
		param += tblPnm + "&dbSchId=" + selectRowJsonData.dbSchId
		+ "&dbcTblNm=" + selectRowJsonData.dbcTblNm
		+ "&mtaTblId=" + selectRowJsonData.mtaTblId ;

		//컬럼 그리드 조회 한다.
		grid_sub_sheet.DoSearch('<c:url value="/meta/mta/getMtaColGapList.do" />', param);
	}
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var selectRowJsonData =  grid_sheet.GetRowJson(row);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(selectRowJsonData);
    
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
	} else {

		var clickRowIdx = 2;
		grid_sheet.SetSelectRow(clickRowIdx);

		$("#orgNm").val(grid_sheet.GetCellText(clickRowIdx, "dbcOrgCd"));
		$("#infoSysNm").val(grid_sheet.GetCellText(clickRowIdx, "dbcInfoSysCd"));

		var selectRowJsonData =  grid_sheet.GetRowJson(clickRowIdx);
		
		loadDetail(selectRowJsonData);
	}
	
}
</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->

	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">메타정보변경상세팝업 [테이블명:${search.tblNm}]</div> <!-- 테이블 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

	<!-- 팝업 내용 시작 -->
	<div class="pop_content">
		<!-- 검색조건 입력폼 -->
		<div id="search_div">
		        <%-- <div class="stit"><s:message code="INQ.COND2" /></div>  --%><!-- 검색조건 -->
		        <div style="clear:both; height:5px;"><span></span></div>
		        
		        <form id="frmSearch" name="frmSearch" method="post">
		        	<input type="hidden" name="orgCd" id="orgCd" value="${search.orgCd}"/>
			     	<input type="hidden" name="infoSysCd" id="infoSysCd" value="${search.infoSysCd}"/>
			     	<input type="hidden" name="dbConnTrgId" id="dbConnTrgId" value="${search.dbConnTrgId}"/>
			     	<input type="hidden" name="dbSchId" id="dbSchId" value="${search.dbSchId}"/>
			     	<input type="hidden" name="tblNm" id="tblNm" value="${search.tblNm}"/>
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
		                        <th scope="row">기관명</th>
		                         <td>
		                             <span class="input_file">
		                             <input type="text" id="orgNm" readonly value=""/>
		                             </span>
		                         </td>
		                        <th scope="row">정보시스템명</th>
		                         <td>
		                             <span class="input_file">
		                             <input type="text" id="infoSysNm" readonly value=""/>
		                             </span>
		                         </td>
							 </tr>
		                   </tbody>
		                 </table>   
		            </div>
		            </fieldset>
		            
		        <div class="tb_comment"><%-- <s:message  code='ETC.POP' /> --%></div>
		        </form>
				<div style="clear:both; height:10px;"><span></span></div>
		        
		         <!-- 조회버튼영역  -->
		        <%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" /> --%>
		</div>	
		<!-- search_div 끝  -->
		<div style="clear:both; height:5px;"><span></span></div>
	
		
		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "130px");</script>            
		</div>
		
		<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
		<div class="selected_title_area">
			    <div class="selected_title" id="program_sel_title"> <span></span></div>
		</div>
	
		<div style="clear:both; height:5px;"><span></span></div>
		
		<div id="tabs">
		  <ul>
		    <li><a href="#tabs-1">컬럼정보</a></li> <!-- 컬럼정보 -->
		  </ul>
		  
		  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="mtagapcol_pop.jsp" %>
			<!-- 	상세정보 ajax 로드시 이용 END -->
		  </div>
		</div>
	</div>
	<!-- 팝업내용 끝  -->
</div>
    <!-- 팝업 내용 끝 -->
</body>
</html>