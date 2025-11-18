<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title><s:message code="DDL.TBL.SRH" /></title> <!-- DDL 테이블 검색 -->

<script type="text/javascript">

var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	////imgConvert($('div.tab_navi a img'));
	
// 	$("#tabs").tabs();
	
                
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code='ERR.APPLY' />")) {
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
// 	doAction("Search");
	if($("#ddlTblPnm").val() != '') {
		$("#ddlTblPnm").attr("readOnly", true);
	}
	
	//페이지 호출시 처리할 액션...
	doAction('Search');

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
        
        var headtext = "<s:message code='META.HEADER.DDLCOL.AUTO.POP'/>";
        	/* No.|상태|선택|주제영역ID|주제영역|테이블ID|DDL테이블(물리명)|DDL테이블(논리명)|컬럼ID|DDL컬럼(물리명)|DDL컬럼(논리명)|요청자명|객체설명| */
// 	        +"객체버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50, SaveName:"ibsCheck",      Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId"	,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:150,  SaveName:"fullPath"	,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:80, SaveName:"ddlTblId",   	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120, SaveName:"ddlTblPnm",     Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"ddlTblLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:80, SaveName:"ddlColId",   	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120, SaveName:"ddlColPnm",     Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"ddlColLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"rqstUserNm"	,    Align:"Left",   Edit:0},
//                     {Type:"Text",   Width:40,  SaveName:"crgUserNm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        
        InitComboNoMatchText(1, "<s:message code='ITEM.NO' />"); //항목없음
        
        SetColHidden("ibsStatus",1);
        SetColHidden("subjId",1);
        SetColHidden("fullPath",1);
        SetColHidden("ddlTblId",1);
        SetColHidden("ddlColId",1);
        SetColHidden("ddlTblLnm",1);
        SetColHidden("objDescn",1);
      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
        SetColHidden("rqstUserNm",1);
        
        
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
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />", param);
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
            grid_sheet.Down2Excel({HiddenColumn:1});
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
		//체크박스 선택/해제 토글 기능.....
		var cellchk = grid_sheet.GetCellValue(row, "ibsCheck");
		if(cellchk == '0') {
			grid_sheet.SetCellValue(row, "ibsCheck", 1);
		} else {
			grid_sheet.SetCellValue(row, "ibsCheck", 0);
		}
		
		return;
	}
	
	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.returnDdlColAutoPop(JSON.stringify(retjson));
	} else {
		opener.returnDdlColAutoPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	//$(".pop_tit_close").click();
	
    return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;
    
    
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code='CNF.LOGIN' />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code='ERR.SEARCH' />");
		return;
	} else {
		
	}
	
}

//============================================
// 트리형 시트에서 명칭 검색 (나중에 공통으로 처리...)
//============================================
function searchSubjInfo(subjnm) {
	if(subjnm) {
        alert("<s:message code='MSG.SYS.NM.SYS.NM.INPT.INQ' />"); //시스템명이 없습니다.\n시스템명을 입력 후 조회하십시요.
		$('#txtGridSearchNm').focus();
		return ;
	}
	var findrow = 0;
	var firstfrow = 0;
	var cntfind = 0;
	var totrow = grid_sheet.TotalRows;
	grid_sheet.ShowTreeLevel(0,1); //Tree 모두 접기...
	//grid_sheet.ColFontColor("arrSysKnm") = grid_sheet.RgbColor(0,0,0);
	while(findrow != -1 && findrow < totrow) {
		findrow = grid_sheet.FindText("arrSysKnm", subjnm, findrow+1, 2, false);
		//alert(findrow);
		if(findrow > 0) {
			var prow = findrow;
			var plvl = grid_sheet.RowLevel(prow);
			//grid_sheet.CellFontColor(prow, "arrSysKnm") = grid_sheet.RgbColor(0,0,255);
			while (prow) {
				prow--;
				if (grid_sheet.RowLevel(prow) < plvl) {
					//grid_sheet.RowExpanded(prow) = true;
//						grid_sheet.CellFontColor(prow, "arrSysKnm") = grid_sheet.RgbColor(0,0,255);
					plvl = grid_sheet.RowLevel(prow);
				}
				if(plvl == 0) break;
			}
			cntfind++;
			if(cntfind == 1) firstfrow = findrow;
		}
	}
//		alert(cntfind);
	grid_sheet.SelectCell(firstfrow, "arrSysKnm");
	$('#txtGridSearchNm').focus();
}



</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="DDL.CLMN.INQ" /></div> <!-- DDL컬럼 검색 -->
    <div class="pop_tit_close"><a href="#"><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
<!--                            <th scope="row" class="th_require"><label for="sysAreaId">시스템영역</label></th> -->
<!--                             <td> -->
<%--                                 <select id="sysAreaId" class="" name="sysAreaId"> --%>
<!--                                         <option value="">전체</option> -->
<%--                                  </select> --%>
<!--                             </td> -->
                           <th scope="row"><label for="ddlTblPnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlTblPnm" id="ddlTblPnm" value="${search.ddlTblPnm}"/>
                                </span>
                            </td>
                           <th scope="row"><label for="ddlColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlColLnm" id="ddlColLnm" value="${search.ddlColLnm}"/>
                                </span>
                            </td>
                           
                            
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.POP' /></div> --%>
             <div class="tb_comment"> * <s:message code="MSG.LORQ.CLMN.REG" /></div> <!-- 더블클릭하면 요청서에 컬럼이 등록됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <page:applyDecorator id="buttonpop" name="buttonpop" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "95%");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>