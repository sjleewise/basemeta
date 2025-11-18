<!DOCTYPE html>
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
<title><s:message code="MENU.MNG" /></title> <!-- 메뉴 관리 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;
var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
		//저장버튼 hidden
		$("#btnSave").hide();
		//삭제버튼 hidden
		$("#btnDelete").hide();
		//tree 추가 버튼 hidden
		$("#btnTreeNew").hide();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
        
        
         double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
     	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
     		double_select(connTrgSchJson, $(this));
     	});
     	
     	doAction("Search");
        
     	$("#poiDown").hide();
    }
		
);

$(window).on('load',function() {
	initGrid();
	loadDetail();
});


$(window).resize(function(){ 
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,VScrollMode:1};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='VRFC.RULE.ERR.HEADER'/>", Align:"Center"}
                  /* No.|상태|선택|DB ID|DB 물리명|스키마ID|스키마 물리명|테이블명|컬럼명|한글컬럼명|프로파일ID|프로파일종류|최근분석차수|프로파일 시작시간|프로파일 건수|오류건수|오류율|검증룰ID|검증룰명|검증룰 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:20,     SaveName:"ibsSeq",       Align:"Center",   Edit:0},
                    {Type:"Status",   Width:20,     SaveName:"ibsStatus",    Align:"Center",   Edit:0,    Hidden:1},
                    {Type:"CheckBox", Width:20,     SaveName:"ibsCheck",     Align:"Center",   Edit:1,    Hidden:1},
                    {Type:"Text",     Width:80,     SaveName:"dbConnTrgId",  Align:"Center",   Edit:0,    Hidden:1}, 
                    {Type:"Text",     Width:40,     SaveName:"dbConnTrgPnm", Align:"Left",     Edit:0,    Hidden:0}, 
                    {Type:"Text",     Width:20,     SaveName:"dbSchId",      Align:"Center",   Edit:0,    Hidden:1},   
                    {Type:"Text",     Width:40,     SaveName:"dbSchPnm",     Align:"Left",     Edit:0},
                    {Type:"Text",     Width:60,     SaveName:"dbcTblNm",     Align:"Left",     Edit:0},
                    {Type:"Text",     Width:80,     SaveName:"dbcColNm",     Align:"Left",     Edit:0},
                    {Type:"Text",     Width:80,     SaveName:"dbcColKorNm",     Align:"Left",     Edit:0},
                    {Type:"Text",     Width:40,     SaveName:"prfId",        Align:"Center",   Edit:0,    Hidden:1}, 
                    {Type:"Text",     Width:40,     SaveName:"prfKndCd",     Align:"Center",   Edit:0,    Hidden:1}, 
                    {Type:"Text",     Width:40,     SaveName:"anaDgr",       Align:"Center",   Edit:0,    Hidden:1}, 
                    {Type:"Date",     Width:80,     SaveName:"anaStrDtm",    Align:"Center",   Edit:0,    Hidden:0,      Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Int",   	  Width:50,     SaveName:"anaCnt",       Align:"Right",    Edit:0,    Hidden:0, Format:"#,###"}, 
                    {Type:"Int",   	  Width:50,     SaveName:"esnErCnt",     Align:"Right",    Edit:0,    Hidden:0, Format:"#,###"}, 
                    {Type:"Text",     Width:50,     SaveName:"erRate",       Align:"Right",   Edit:0,    Hidden:0}, 
                    {Type:"Text",     Width:20,     SaveName:"vrfcId",       Align:"Center",   Edit:0,    Hidden:1},
                    {Type:"Text",     Width:100,    SaveName:"vrfcNm",       Align:"Left",     Edit:0},
                    {Type:"Text",     Width:100,    SaveName:"vrfcRule",     Align:"Left",     Edit:0,    Hidden:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	/* SetColProperty("regTypCd", ${codeMap.regTypCdibs}); */

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

        	grid_sheet.DoSearch('<c:url value="/dq/vrfcrule/selectVrfcErrList.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        
            break;
    }       
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/vrfcrule/selectVrfcErrDetail.do"/>', param);
}

 
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
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
	
	var param ="prfId="+grid_sheet.GetCellValue(row, "prfId");
	 param +="&anaStrDtmS="+grid_sheet.GetCellValue(row, "anaStrDtm");
	 param += "&objId="+grid_sheet.GetCellValue(row, "prfId");
    param += "&objDate="+grid_sheet.GetCellValue(row, "anaStrDtm");
    
	if(grid_sheet.GetCellValue(row, "prfKndCd")=="BR"){
		param += "&objIdCol=BR_ID";		  
	     param += "&objResTbl=WAM_BR_RESULT";
	     param += "&objErrTbl=WAM_BR_ERR_DATA";
	     param += "&erDataSnoCol=ER_DATA_SNO";
	     param += "&objGb="+grid_sheet.GetCellValue(row, "PRF_KND_CD");     
	     param += "&erDataSno="+0;
	}else{
	     param += "&objIdCol=PRF_ID";		  
	     param += "&objResTbl=WAM_PRF_RESULT";
	     param += "&objErrTbl=WAM_PRF_ERR_DATA";
	     param += "&erDataSnoCol=ESN_ER_DATA_SNO";
	     param += "&objGb="+grid_sheet.GetCellValue(row, "PRF_KND_CD");     
	     param += "&erDataSno="+0;
	}
	
	
	
    //테이블 컬럼 프로파일 구분
	vrfcDtlPop(param);	

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var vrfcId = "&vrfcId="+grid_sheet.GetCellValue(row, "vrfcId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="VRFC.NM" /> : ' + param.vrfcNm; /* 메뉴명 */
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(vrfcId);

}

//프로파일 상세 팝업
function vrfcDtlPop(param){
  	var url = '<c:url value="/dq/vrfcrule/popup/vrfcdtl_pop.do"/>';
  	param = encodeURI(param);
  	
	//var popwin = openLayerPop(url, 1200, 800,param); 

	var popUp = OpenWindow(url + "?" + param, "prf", 1200, 800, "true"); 
} 

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="MENU.MNG" /></div> <!-- 메뉴 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메뉴조회">
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                       <tr>                               
                           <th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->
                           <td>
							<select id="dbConnTrgId"  name="dbConnTrgId">
							    <option value=""><s:message code="WHL" /></option><!--전체-->
							</select>
				             <select id="dbSchId" class="" name="dbSchId">
				             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				             </select>
                           </td>
                           
                           <th scope="row"><label for="dbcTblNm">검증분류</label></th>	<!--테이블명-->

                       		<td>
                       			<select name="vrfcTyp" id="vrfcTyp">
                       				<option value=""><s:message code="WHL" /></option><!--전체-->
                       				<c:forEach var="code" items="${codeMap.vrfcTyp }" varStatus="status" >
	                                <option value="${code.codeCd }">${code.codeLnm}</option>
	                                </c:forEach>
	                                <option value="PT01">참조무결성</option>
                                   	<option value="QB">업무규칙</option>
                       			</select>
                       		</td>
                       </tr>
                       <tr>
                       		<th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

                       		<td>
                       			<input type="text" name="dbcTblNm" id="dbcTblNm" class="wd98p"/>
                       		</td>
                       		<th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th>	<!--컬럼명-->

                       		<td>
                       			<input type="text" name="dbcColNm" id="dbcColNm" class="wd98p"/>
                       		</td>
                       </tr>  
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "180px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   <!--  -->
<!--	<div style="clear:both; height:5px;"><span></span></div>                              -->
<!--                                                                                          -->
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->                                         
<!--	<div class="selected_title_area">                                                     -->
<!--		    <div class="selected_title" id="program_sel_title"> <span></span></div>       -->
<!--	</div>                                                                                -->
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->                                      
<!--	<div style="clear:both; height:5px;"><span></span></div>                              -->
<!--                                                                                          -->
	<!-- 선택 레코드의 내용을 탭처리... -->                                                           
<!--	<div id="tabs">                                                                       -->
<!--	  <ul>                                                                                -->
<!--	    <li><a href="#tabs-1"><s:message code="VRFC.DTL.INFO" /></a></li> <!-- 메뉴 상세정보 -->
<!--	  </ul>                                                                               -->
<!--	  <div id="tabs-1">                                                                   -->
			<!-- 	상세정보 ajax 로드시 이용 -->                                                  
<!--			<div id="detailInfo"></div>                                                   -->
			<!-- 	상세정보 ajax 로드시 이용 END -->                                              
<!--	  </div>                                                                              -->
<!--	 </div>                                                                               -->
	<!-- 선택 레코드의 내용을 탭처리 END -->                                                          
<!--                                                                                          -->
</body>
</html>
