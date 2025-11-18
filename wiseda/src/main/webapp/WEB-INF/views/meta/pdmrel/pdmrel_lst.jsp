<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set>

<html>
<head>
<title><s:message code="TBL.INQ"/></title> <!-- 테이블 조회 -->

<script type="text/javascript">

EnterkeyProcess("Search");

                       
$(document).ready(function() {
	
		//$( "#tabs" ).tabs();
	   
		
		// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
		
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        $('#subjSearchPop').click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
						
		    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
		    	var param = $("form#frmSearch").serialize(); 
				var popwin = OpenModal(url+"?"+param, "searchpop",  600, 400, "no");
				popwin.focus();
			
		});
        
       
       
        
    }
);

$(window).on('load',function() {
	
	
	//그리드 초기화 
	initGrid();
	

});


$(window).resize(
    
    function(){
                
    	setibsheight($("#grid_01"));
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.ASIS.PDM.REL.LST'/>";
		//No.|상태|관계ID|관계물리명|관계논리명|부모테이블주제영역ID|부모테이블주제영역명|부모테이블ID|부모테이블명|부모테이블컬럼ID|부모테이블컬럼명|자식테이블주제영역ID|자식테이블주제영역명|자식테이블ID|자식테이블명|자식테이블컬럼ID|자식테이블컬럼명|요청일시|요청자ID|요청자명|설명

        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
									
					{Type:"Text",    Width:100,  SaveName:"pdmRelId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:130,  SaveName:"pdmRelPnm"   , Align:"Left",   Edit:0},
                    {Type:"Text",    Width:130,  SaveName:"pdmRelLnm"   , Align:"Left",   Edit:0},                    
                    {Type:"Text",    Width:100,  SaveName:"paSubjId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:200,  SaveName:"paFullPath"  , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paTblId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"paTblPnm"    , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paColId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"paColPnm"    , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chSubjId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:200,  SaveName:"chFullPath"  , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chTblId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"chTblPnm"    , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chColId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"chColPnm"    , Align:"Left",   Edit:0, Hidden:0},
                     
                                       
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:80,   SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					
					
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});

		
        
        InitComboNoMatchText(1, "");
        
        
        // FitColWidth();
        
        SetExtendLastCol(1);       
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}



//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	
	$("#frmSearch #subjLnm").val(retjson.fullPath);
	
}



//주제영역 검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function returnSubjPop(ret) {
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#chFullPath").val(retjson.fullPath);  	
	
}


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	
        	//요청서 마스터 번호로 조회한다...        	
        	var param = $('#frmSearch').serialize();
        
        	grid_sheet.DoSearch("<c:url value="/meta/pdmrel/getWamPdmRelColList.do" />", param);
        	
        	break;
        	
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
function grid_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");
    
   

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

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PHYC.MDEL.INQ" /></div> <!-- 물리모델 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<div id="search_div">
<!-- 검색조건 입력폼 -->
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="pdmTblId" />
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:12%;" />
                   <col style="width:30%;" />
                   <col style="width:12%;" />  
                   <col style="width:20%;" />                   
                   <col style="width:12%;" />  
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           
                           <th scope="row"><label for="chFullPath">자식주제영역</label></th> <!-- 자식주제영역 -->
                            <td>
                                <span class="input_file">

                                	<input type="text" name="chFullPath" id="chFullPath" class="wd340"/>
                                	<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
		                            <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                            <th scope="row" ><label for="paTblPnm">부모테이블명</div> <!-- 부모테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="paTblPnm" id="paTblPnm"  />
                                </span>
                            </td>
                            
                            <th scope="row" ><label for="chTblPnm">자식테이블명</div> <!-- 자식테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="chTblPnm" id="chTblPnm"  />
                                </span>
                            </td>
                       </tr>
                       
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<!-- 조회버튼영역  -->
<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>             
	</div>
	<!-- 그리드 입력 입력 -->

</body>
</html>