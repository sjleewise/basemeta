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
<title><s:message code="STRD.TERMS.INQ"/></title> <!-- 표준용어 검색 -->

<script type="text/javascript">


var popRqst = "${search.popRqst}";

$(document).ready(function() {
      
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  });
        
        
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
			$("form[name=frmSearch]")[0].reset();
		});
                
		// 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );
        
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #sditmLnm"), "SDITM");
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #dmnLnm"), "DMN");
    	
    	
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

$(window).on('load',function() {
	initGrid();
	$(window).resize();
	
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
});

EnterkeyProcess('Search');

function initGrid()
{
    
    with(grid_sheet){
    	
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headtext  = "<s:message code='META.HEADER.STNDITEM.POP.1'/>";
		headtext += "<s:message code='META.HEADER.STNDITEM.POP.2'/>";
		headtext += "<s:message code='META.HEADER.STNDITEM.POP.3'/>";

		//headtext  = "No.|상태|선택";
		//headtext += "|표준분류|표준용어ID|표준용어논리명|표준용어물리명|논리명기준구분|도메인|도메인그룹|인포타입|인포타입명|데이터타입|길이|소수점|암호화여부";
		//headtext += "|설명";

		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:40,   SaveName:"ibsSeq",	 Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck", Align:"Center", Edit:1, Hidden:0, Sort:0},
			        {Type:"Combo",   Width:80,  SaveName:"stndAsrt",   	Align:"Center", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"sditmId",  Align:"Left", Edit:0 },
					{Type:"Text",   Width:100,  SaveName:"sditmLnm",  Align:"Left", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"sditmPnm",  Align:"Left", Edit:0, KeyField:1}, 
					{Type:"Text",   Width:100,  SaveName:"lnmCriDs",  Align:"Left", Edit:0}, 
					{Type:"Text",   Width:60,   SaveName:"dmnLnm", 	  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dmngLnm",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:60,   SaveName:"infotpId",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"infotpLnm", Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"dataType",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dataLen",	  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dataScal",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:60,  SaveName:"encYn",	  Align:"Center", Edit:0},			
					{Type:"Combo",  Width:60,  SaveName:"dupYn",	  Align:"Center", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0}
				];
                    
        InitColumns(cols);
        
        //콤보코드 셋팅...
// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
        SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("persInfoCnvYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		
      	InitComboNoMatchText(1, "");
        
      	//히든 컬럼 설정...
     	SetColHidden("sditmId"		,1);      
     	SetColHidden("ibsStatus"	,1);      
     	SetColHidden("lnmCriDs"		,1);      
      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
        
//       	FitColWidth();  
        
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
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getsditmlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
       
        case "Down2Excel":  //엑셀내려받기
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}



//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
				$("#btnMsgConf").one("click", function() {
					$("div.pop_tit_close").click();
	    		}); 		
	    	} 
			
// 			opener.doAction("Search");
			
			break;
	
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
		
			
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			
			
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
		parent.returnItemPop(JSON.stringify(retjson));
	} else {
		opener.returnItemPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
	return;

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	
	
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

function grid_sheet_OnSaveEnd(code, message) {

}



</script>
</head>

<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="STRD.TERMS.INQ"/></div> <!-- 표준용어 검색 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                        <tr>
                        <th scope="row" ><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					    <td colspan="3">
					    <select  id="stndAsrt" name="stndAsrt"  class="wd200">
					            <option value=""><s:message code="WHL"/></option>
					            <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					    		  <option value="${code.codeCd}">${code.codeLnm}</option>
					    		</c:forEach>
					    </select>
                        </tr>
						<tr>
							<th scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS.NM" /></label></th> <!-- 표준용어명 -->
							<td><input type="text" id="sditmLnm" name="sditmLnm" value="${search.sditmLnm }" /></td>
							<th scope="row"><label for="dnmLnm"><s:message code="STRD.DMN"/></label></th> <!-- 표준도메인 -->
							<td><input type="text" id="dmnLnm" name="dmnLnm" value="${search.dmnLnm }" /></td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
         <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        	<div class="tb_comment"><s:message  code='ETC.RQST.POP' /></div>
         </c:when>
         <c:otherwise>
        	<div class="tb_comment"><s:message  code='ETC.POP' /></div>
         </c:otherwise>
         </c:choose>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		 <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
         </c:when>
         <c:otherwise>
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:otherwise>
         </c:choose>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
</div>
</div>
</body>
</html>