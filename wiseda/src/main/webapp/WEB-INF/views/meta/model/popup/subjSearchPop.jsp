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
<title><s:message code='SUBJ.TRRT.INQ' /></title> <!-- 주제영역조회 -->

<script type="text/javascript">

var interval = "";
// var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
		//alert(sysareaJson[0].codeCd + ":" + sysareaJson[0].codeLnm);
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
	      //버튼 초기화...
    	$("#popReset").hide();
    	$("#popExcelDown").hide();
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  });
        
        $("#sysAreaId").change(function(){ doAction("Search");  });
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
                      

        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(sysareaJson, $("#sysAreaId"));
        setCodeSelect("sysarea", "L", $("form[name=frmSearch] #sysAreaId"));
//         setCodeSelect("uppSubjId", "L", $("form[name=frmSearch] #subjId"));

      
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
        
        var headtext  = "<s:message code='META.HEADER.SUBJSEARCHPOP.1'/>";
		headtext += "<s:message code='META.HEADER.SUBJSEARCHPOP.2'/>";
		headtext += "<s:message code='META.HEADER.SUBJSEARCHPOP.3'/>";

        //headtext  = "No.|상태|선택";
        //headtext += "|시스템영역|주제영역ID|주제영역논리명|주제영역물리명|주제영역약어|상위주제영역ID|주제영역레벨|표준적용여부|레거시코드여부|표준분류";
        //headtext += "|설명|버전|전체경로|등록유형|작성일시|작성자ID|작성자명";
		
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",    Align:"Center", Edit:0, Sort:0},
                    {Type:"Combo",    Width:80,   SaveName:"sysAreaId",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"subjId",      Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:180,  SaveName:"subjLnm",     Align:"Left", Edit:0, KeyField:1, TreeCol:1},
                    {Type:"Text",     Width:100,  SaveName:"subjPnm",     Align:"Left", Edit:0, KeyField:1}, 
                    {Type:"Text",     Width:60,   SaveName:"subjAbrNm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"uppSubjId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:80,   SaveName:"subjLvl", 	  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",    Width:80,   SaveName:"stdAplYn",    Align:"Center", Edit:0},
                    {Type:"Combo",    Width:80,   SaveName:"stndAsrt",    Align:"Center", Edit:0},
                    {Type:"Combo",    Width:60,   SaveName:"lecyDcd",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:120,  SaveName:"objDescn",    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:40,   SaveName:"objVers",     Align:"Left",   Edit:0},
                    {Type:"Text",     Width:400,   SaveName:"fullPath",    Align:"Left",   Edit:0},
                    {Type:"Combo",    Width:40,   SaveName:"regTypCd",    Align:"Center", Edit:0},                        
                    {Type:"Date",     Width:80,   SaveName:"writDtm",  	  Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",     Width:50,   SaveName:"writUserId",  Align:"Center", Edit:0},
                    {Type:"Text",     Width:60,   SaveName:"writUserNm",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
	    SetColProperty("sysAreaId", ${codeMap.sysareaibs});
	    SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("lecyDcd", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
	    SetColProperty("stndAsrt",  ${codeMap.stndAsrtibs});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("ibsCheck",1);
        SetColHidden("objVers",1);
        SetColHidden("regTypCd",1);
        SetColHidden("writDtm",1);
        SetColHidden("writUserId",1);
        SetColHidden("writUserNm",1);
      
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
        	grid_sheet.DoSearch('<c:url value="/meta/subjarea/getsubjlist.do" />', param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
    }       
}

//팝업 리턴값 제공
function returnPop(ret) {
// 	alert(JSON.stringify(ret));
	
	opener.frmSearch.subjLnm.value = ret;
	window.close();
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
	
	//물리모델요청 페이지에서 관계에서도 주제영역을 사용하기 때문에 분기를 태움...
	
	if ("${sFlag}" == "PDMREL") {
		//iframe 형태의 팝업일 경우
		if ("${search.popType}" == "I") {
			
			parent.returnSubjPopRel(JSON.stringify(retjson));
			
//	 		parent.closeLayerPop();
		} else {
			opener.returnSubjPopRel(JSON.stringify(retjson));
//	 		window.close();
		}
	} else {
		
		//iframe 형태의 팝업일 경우
		if ("${search.popType}" == "I") {
			
			parent.returnSubjPop(JSON.stringify(retjson));
			
	// 		parent.closeLayerPop();
		} else {
			opener.returnSubjPop(JSON.stringify(retjson));
	// 		window.close();
		}
	}
	
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
	
//     var subjLnm = grid_sheet.GetCellValue(row, "subjLnm");
//     returnPop(subjLnm);
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
//     $("#hdnRow").val(row);
	if(row < 1) return;
	
	//선택한 상세정보를 가져온다...
// 	var param =  grid_sheet.GetRowJson(row);
// 	var subjLnm = "&subjLnm="+grid_sheet.GetCellValue(row, "subjLnm");
    
	//메뉴ID를 토대로 조회한다....
// 	loadDetail(subjLnm);
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
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="SUBJ.TRRT.INQ" /></div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"> <!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" ><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td>
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                 </select>
                            </td>
                           <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
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
            
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "260px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;" >
		    <div class="selected_title"><s:message code="SUBJ.TRRT.NM" /> : <span></span></div> <!-- 주제영역명 -->
	</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div style="clear:both; height:5px;"><span></span></div>
</div>
<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>