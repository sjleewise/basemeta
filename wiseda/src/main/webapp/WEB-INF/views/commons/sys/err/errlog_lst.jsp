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
<title><s:message code="DEPT.REG" /></title> <!-- 부서 등록 -->

<script type="text/javascript">

//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
	    $("#divLstBtn").show();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
  
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

          
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
        //기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});
        
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	// 3: 1개월 
	setBetweenDtm( 3, $( "#searchBgnDe" ), $( "#searchEndDe" ));
	
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='ERR.LOG.LST'/>", Align:"Center"}
        
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                                         
                    {Type:"Text",   Width:40,  SaveName:"seq",    Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"pgmNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:700,  SaveName:"errLog",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Date",   Width:100,  SaveName:"errDtm",    Align:"Center", Edit:0, Hidden:0,Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:50,  SaveName:"userId",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:50,  SaveName:"userNm",    Align:"Center", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);

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
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/sys/err/geterrloglst.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
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
			

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
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
    
    $("#hdnRow").val(row);
    
    
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
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DEPT.MNG"/></div> <!-- 부서 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>       
                       <tr>                   
                            <th scope="row"><label for="pgmNm"><s:message code="PGM.NM" /></label></th><!-- 부서명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pgmNm" id="pgmNm" />
                                </span>
                                </td>  
                             <th scope="row"><label for="errLog"><s:message code="ERR.INFO" /></label></th><!-- 부서명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="errLog" id="errLog" />
                                </span>
                                </td>      
                       </tr>
                       <tr>
                             <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                             <td class="bd_none">
                              	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                <a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                <a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                <a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                <a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                <a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                             </td>
                             <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
   		   					 <td>
   		   						<input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/>
   		   					 </td>
                       </tr>
                       <tr>                   
                            <th scope="row"><label for="userId"><s:message code="USER.ID" /></label></th><!-- 사용자ID -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="userId" id="userId" />
                                </span>
                                </td>  
                             <th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th><!-- 사용자명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="userNm" id="userNm" />
                                </span>
                                </td>      
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />   
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>