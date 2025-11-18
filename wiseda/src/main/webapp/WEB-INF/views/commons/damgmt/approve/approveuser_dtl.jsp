<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">
//최근 선택 row

$(document).ready(function(){
	//그리드 초기화
	initsubgrid();
	$('button.btn_rqst_new').unbind('click');
		
	 // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("SearchUser");  }).find(".ui-button-text").text("<s:message code='WHL.INQ' />");; /* 전체조회 */
                  
    // 추가 Event Bind
    $("#btnTreeNew").click(function(){ doAction("Add");  });
    
 	// 추가 Event Bind
//     $("#btnNewLow").click(function(){ doAction("NewLow");  });

    // 저장 Event Bind
    $("#btnSave").hide();

    // 삭제 Event Bind
    $("#btnDelete").click(function(){ 

    	//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sub, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
 			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
        	//showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
    	}
    //	doAction("Delete");  
    });
    
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

    // 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide(); 
	

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			var param1 = "aprgId="+frmAprgId.aprgId.value;
			grid_sub.DoSearch('<c:url value="/commons/damgmt/approve/approvegroupuser_lst.do" />', param1);
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			
			
// 			doAction("Search");

			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub.FitColWidth();
});


function initsubgrid() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.APPROVEUSER.DTL'/>", Align:"Center"} 
                    /* No.|상태|선택|결재그룹명|사용자ID|사용자명|만료일자|시작일자|설명|버전|등록유형코드|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
	                {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
    	            {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",   Width:70,  SaveName:"aprgId",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:70,  SaveName:"userId",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:70,  SaveName:"userNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Date",   Width:100,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",   Width:100,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:200,  SaveName:"objDescn",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:50,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:60,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:60,  SaveName:"writDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:60,  SaveName:"writUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"writUserNm",    Align:"Left", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"아니요|예"});
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});
		SetColProperty("aprgId", ${codeMap.approvegroupibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//form 내용을 초기화 한다.....
		//doAction('Add');
		//$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    
    <form id="frmSubSearch" name="frmSubSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='APMA.INQ' />"> <!-- 결재자 조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                          <th scope="row"><label for="aprgId"><s:message code="APRL.GRP" /></label></th> <!-- 결재그룹 -->
                            <td>
                                <select id="aprgId"  name="aprgId">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.aprgId}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                            </td>
					
                          <th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th> <!-- 사용자명 -->
                                <td><input type="text" name="userNm" class="wd200" id="userNm" /></td> 
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        </form>
        <form id="frmAprgId" name="frmAprgId">
        <input type="hidden" id="aprgId" name="aprgId" value="" />
        <input type="hidden" id="aprgNm" name="aprgNm" value="" />
        </form>
	
	<div class="tb_basic" style="display: none;">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
<%--       <tr>
        <th>구분</th>
        <td>
        	<select id="cateCode" name="cateCode">
        		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
         	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
             <option value="${code.codeCd}">${code.codeLnm}</option>
             </c:forEach>
        	</select>
        </td>
        <th>게시물검색</th>
        <td>
	        <select  id="searchCnd" name="searchCnd" title="검색조건선택">
	        	<option value="0">제목</option>
	            <option value="1">내용</option>
	            <option value="2">작성자</option>
	        </select>
        <input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="검색어 입력" style="display: inline; width: 150px;">
        </td>
      </tr>
      <tr>
        <th class="bd_none">기간</th>
        <td class="bd_none">
        	<a href="#" class="tb_bt">1일</a>
            <a href="#" class="tb_bt">3일</a>
            <a href="#" class="tb_bt">7일</a>
            <a href="#" class="tb_bt">1개월</a>
            <a href="#" class="tb_bt">3개월</a>
            <a href="#" class="tb_bt">6개월</a>
        </td>
        <th>등록일</th>
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> --%>
      <tr>
      	<th><s:message code="PGM.NM" /></th> <!-- 프로그램명 -->
      	<td>
<%--       		<input type="text" name="searchWrd" value="${searchVO.searchWrd}"> --%>
      	</td>
      </tr>
    </table>
    </div>
    </form>
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
