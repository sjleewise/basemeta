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
<title><s:message code="PHYC.MDEL.TBL.INQ" /></title> <!-- 물리모델 테이블 검색 -->

<script type="text/javascript">

var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
// 	$("#tabs").tabs();
	
                
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();
    }
    
    
  //폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmSearch]")[0].reset();
	});
                  
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //======================================================
    // 셀렉트 박스 초기화
    //======================================================
    // 시스템영역
    
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
    
});

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
	doAction("Search");
});

EnterkeyProcess("Search");

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
        SetMergeSheet(5);
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBMAP.POP1'/>"},
                    /* No.|소스(Source)|소스(Source)|소스(Source)|타겟(Target)|타겟(Target)|타겟(Target)|DDL이관여부|설명|version|등록유형|등록일시|작성일시|작성자|소스DB|소스DB|타겟DB|타겟DB|소스DB|타겟DB */
                    {Text:"<s:message code='COMMON.HEADER.DBMAP.POP2'/>", Align:"Center"}
                    /* No.|DB스키마명|DDL대상|스키마명|DB스키마명|DDL대상|스키마명|DDL이관여부|설명|version|등록유형|등록일시|작성일시|작성자|소스DB ID|소스DB접속대상명|타겟DB ID|타겟DB접속대상명|DDL대상|DDL대상 */
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Combo",   Width:180,  SaveName:"srcDbSchId",    	Align:"Center", Edit:0}, 
                    
                    {Type:"Combo",   Width:70,  SaveName:"srcDdlTrgDcd",    	Align:"Center", Edit:0 }, 
                    {Type:"Text",   Width:100,  SaveName:"srcDbSchPnm",    	Align:"Center", Edit:0}, 
                    //{Type:"Text",   Width:100,  SaveName:"srcDbConnTrgPnm", Align:"Center", Edit:0}, 
                    {Type:"Combo",   Width:180,  SaveName:"tgtDbSchId",    	Align:"Center", Edit:0}, 
                    {Type:"Combo",   Width:70,  SaveName:"tgtDdlTrgDcd",    	Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"tgtDbSchPnm",    	Align:"Center", Edit:0}, 
                  //  {Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgPnm", Align:"Center", Edit:0}, 
//                     {Type:"Text",   Width:100,  SaveName:"expDtm",    	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},	             
                    {Type:"Combo",   Width:100,  SaveName:"ddlTsfYn",    	Align:"Center", Edit:0},
                    {Type:"Text",   Width:330,  SaveName:"objDescn",    	Align:"Left", Edit:0, Hidden:1},
                     {Type:"Text",   Width:100,  SaveName:"objVers",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,  SaveName:"regTypCd",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"strDtm",    	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",   Width:120,  SaveName:"writDtm",    	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",   Width:70,  SaveName:"writUserNm",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:180,  SaveName:"srcDbConnTrgId",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:180,  SaveName:"srcDbConnTrgPnm",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:180,  SaveName:"tgtDbConnTrgId",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:180,  SaveName:"tgtDbConnTrgPnm",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:180,  SaveName:"srcDdlTrgDcdNm",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:180,  SaveName:"tgtDdlTrgDcdNm",    	Align:"Center", Edit:0, Hidden:1}, 
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("srcDbSchId", 	${codeMap.dbSchibs});
	    SetColProperty("tgtDbSchId", 	${codeMap.dbSchibs});
	    SetColProperty("srcDdlTrgDcd", 	${codeMap.ddlTrgDcdibs});
	    SetColProperty("tgtDdlTrgDcd", 	${codeMap.ddlTrgDcdibs});
	    SetColProperty("ddlTsfYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"});
        
        InitComboNoMatchText(1, "");
        

        
        
        FitColWidth();
        
//         SetExtendLastCol(1);    
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
        	var param = "";
        	grid_sheet.DoSearch("<c:url value="/commons/damgmt/db/getdbmaplist.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
            
        case "Apply":
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
//         	var retval = getibscheckjoin(grid_pop, "stwdId");
//         	alert(retval);

        	var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			//2. 데이
// 			alert(saveJson.Code); 처리대상 행이 없는 경우 리턴한다.
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			//if(saveJson.data.length == 0) return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	var url = "<c:url value="/meta/ddl/regWam2WaqDdlTbl.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
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
	
	var retjson = grid_sheet.GetRowJson(row);	
	
	//요청서용 팝업일 경우.....
	if (popRqst == 'Y') {
// 		//체크박스 선택/해제 토글 기능.....
// 		var cellchk = grid_sheet.GetCellValue(row, "ibsCheck");
// 		if(cellchk == '0') {
// 			grid_sheet.SetCellValue(row, "ibsCheck", 1);
// 		} else {
// 			grid_sheet.SetCellValue(row, "ibsCheck", 0);
// 		}
		
// 		return;
	}
	
	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.returnDbMapPop(JSON.stringify(retjson));
	} else {
		opener.returnDbMapPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
    return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;
    
    
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
	} else {
		
	}
	
}




</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="DB.MAPG.INQ" /></div> <!-- DB매핑 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>