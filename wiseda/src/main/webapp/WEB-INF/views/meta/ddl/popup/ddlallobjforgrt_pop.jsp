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
<title>DDL 오브젝트 검색</title>

<script type="text/javascript">

var popRqst = "${search.popRqst}";
var connTrgSchJson = ${codeMap.connTrgSch} ;

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
	$('#popReset').button({
	       icons: {
	          primary: "ui-icon-power"
	        }
	}).click(function(event){
		event.preventDefault();
		$("form[name=frmSearch]")[0].reset();
	});
  
	   
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );
    
    double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});

	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();        		    		
    	} else {
    		window.close();
    	}
    	
    });
    
});

$(window).load(function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
	
	//doAction("Search");
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
        
        var headtext = "No.|상태|선택|DBMS명|DBMS논리명|스키마명|스키마논리명|오브젝트ID|오브젝트명|오브젝트논리명|오브젝트유형|요청자명|객체설명";

        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",         Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,   SaveName:"ibsStatus",      Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",       Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",     Width:80,   SaveName:"dbConnTrgPnm",   Align:"Left",   Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"dbConnTrgLnm",   Align:"Left",   Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"dbSchPnm",   	 Align:"Left",   Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"dbSchLnm",   	 Align:"Left",   Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"objId",   	 Align:"Left",   Edit:0},
                    {Type:"Text",     Width:120,  SaveName:"objPnm",      Align:"Left",   Edit:0}, 
                    {Type:"Text",     Width:120,  SaveName:"objLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Combo",     Width:80,   SaveName:"objDcd",   	 Align:"Left",   Edit:0},
                    {Type:"Text",     Width:40,   SaveName:"rqstUserNm"	,    Align:"Left",   Edit:0},
//                     {Type:"Text",   Width:40,  SaveName:"crgUserNm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"objDescn"  ,     Align:"Left",   Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"});
        
        InitComboNoMatchText(1, "");
        
        SetColProperty("objDcd",	${codeMap.objDcdibs});
        
        SetColHidden("ibsStatus",1);
        SetColHidden("objId",1);
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
        	
        	if($("#dbSchId").val() == ""){
        		
        		showMsgBox("ERR","DB스키마정보를 입력하세요.");
        		return;
        	}
        	
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlallobjforgrtlist.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
            
        case "Apply":
        	
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
		parent.returnDdlTblPop(JSON.stringify(retjson));
	} else {
		opener.returnDdlTblPop(JSON.stringify(retjson));
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
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
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
     <div class="pop_tit_txt">오브젝트 검색</div> <!-- 테이블 검색 -->
    <div class="pop_tit_close"><a>창닫기</a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:40%;" />
                   <col style="width:15%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
							<th scope="row" class="th_require"><label for="dbSchPnm">DBMS</label></th>  <!-- 개발DBMS -->
                            <td>
                                 <select id="dbConnTrgId" class="" name="dbConnTrgId">
					             	<option value="">선택</option>
					             </select>
					             <select id="dbSchId" class="" name="dbSchId">
					             	<option value="">선택</option>
					             </select>
                            </td>
                            
                           <th scope="row"><label for="objLnm">오브젝트명</label></th> <!-- 테이블명 -->
                            <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="objLnm" id="objLnm" value="${search.objLnm}"/>
                                </span>
                            </td>
                           
                            
                       </tr>
                       <tr>
                       		<th scope="row"><label for="objDcd">오브젝트구분</label></th>
							<td colspan="3">
							<select id="objDcd" class="wd100" name="objDcd">
<%-- 								    <option value=""><s:message code="COMBO.ALL" /></option> <!-- 전체 --> --%>
								    <c:forEach var="code" items="${codeMap.objDcd}" varStatus="status">
								    <c:if test="${code.codeCd == 'TBL'}" >
				    					<option value="${code.codeCd}">${code.codeLnm}</option>	
								    </c:if>
								    </c:forEach>
								</select>
							</td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.POP' /></div> --%>
        </form>
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