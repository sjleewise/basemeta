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

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;

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
      	
      	$("#poiDown").click( function() {
    		doAction("poiDown");
    	}).show();
      	
//       	doAction("Search");
    }
		
);

$(window).on('load',function() {
	initGrid();
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	// 3: 1개월 
	setBetweenDtm( 3, $( "#searchBgnDe" ), $( "#searchEndDe" ));
	
	doAction("Search");
});


$(window).resize(function(){ 
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,HeaderMergeMode:1};
        SetConfig(cfg);
        
        var headers = [
						/* {Text:"<s:message code='BDQ.HEADER.RSLT.LST'/>", Align:"Center"} */
                  /* No.|상위품질지표명|품질지표명|테이블수|컬럼수|전체건수|오류건수|오류율(%)|오류비중|달성도(%)|목표치(%) */
                  {Text:"No.|상위품질지표명|품질지표명|테이블수|컬럼수|전체건수|오류건수|오류율(%)|오류비중|달성도(%)|목표치(%)|분석시작일시|분석종료일시", Align:"Center"}
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:20,   SaveName:"ibsSeq",     Align:"Center",  Edit:0, ColMerge:0},
                    {Type:"Text",   Width:80,   SaveName:"uppDqiLnm",  Align:"Left",    Edit:0,    Hidden:0, ColMerge:1},   
                    {Type:"Text",   Width:80,   SaveName:"dqiLnm",     Align:"Left",    Edit:0,    Hidden:0, ColMerge:0},                     
                    {Type:"Int",    Width:80,   SaveName:"tblCnt",     Align:"Right",   Edit:0,    Hidden:0, ColMerge:0, Format:"#,###"}, 
                    {Type:"Int",    Width:80,   SaveName:"colCnt",     Align:"Right",   Edit:0,    Hidden:0, ColMerge:0, Format:"#,###"}, 
                    {Type:"Int",    Width:80,   SaveName:"totCnt",     Align:"Right",   Edit:0,    Hidden:0, ColMerge:0, Format:"#,###"}, 
                    {Type:"Int",    Width:80,   SaveName:"errCnt",     Align:"Right",   Edit:0,    Hidden:0, ColMerge:0, Format:"#,###"}, 
                    {Type:"Text",  Width:80,   SaveName:"errRate",    Align:"Right",   Edit:0,    Hidden:0, ColMerge:0}, 
                    {Type:"Text",  Width:80,   SaveName:"sumRate",    Align:"Right",   Edit:0,    Hidden:0, ColMerge:0},
                    {Type:"Text",  Width:80,   SaveName:"stndRate",    Align:"Right",   Edit:0,    Hidden:0, ColMerge:0},
                    {Type:"Text",  Width:80,   SaveName:"goal",    Align:"Right",   Edit:0,    Hidden:0, ColMerge:0}    
                ];
                    
        InitColumns(cols);
	           
        FitColWidth();  
        
        SetExtendLastCol(1);    

        SetRowSumable(grid_sheet.RowCount());
        
        SetMergeSheet(msPrevColumnMerge);
        
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

        	grid_sheet.DoSearch('<c:url value="/dq/result/resultList.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        
            break;
            
        case "poiDown":
        	//searchBox의 DBMS및 스키마명 선택된 것
        	var dbmsId=$('#dbConnTrgId').val();		//DBMS
        	var schId=$('#dbSchId').val();			//스키마명
        	var dbmsLnm=$('#dbConnTrgId').text();		//DBMS
        	var schLnm=$('#dbSchId').text();			//스키마명
        	var searchBgnDe=$("form[name=frmSearch] #searchBgnDe").val(); // 진단시작날짜
        	var searchEndDe=$("form[name=frmSearch] #searchEndDe").val(); // 진단시작날짜
        	
//         	if(!dbmsName || !schName){			//DBMS와 스키마명이 --선택-- 으로 되어있는 경우 종료
//         		showMsgBox("DBMS와 스키마명을 지정해주십시오.");
//         		return;
//         	}
        	
        	$('#dbmsId').val(dbmsId);		//선택된 DBMS의 값
        	$('#schId').val(schId);		//선택된 스키마의 값
        	$('#dbmsLnm').val(dbmsLnm);		//선택된 DBMS의 값
        	$('#schLnm').val(schLnm);		//선택된 스키마의 값
         	$('#searchBgnDe2').val(searchBgnDe); // 선택된 진단시작날짜
        	$('#searchEndDe2').val(searchEndDe); // 선택된 진단종료날짜
        	
//         	$("#poiForm").first().submit(function( event ) {
//        		  event.preventDefault();
       		  
// 			  var url ='<c:url value="/dq/result/poiDown.do" />';
        	
//         	  $("#poiForm").attr("action", url);
        	  
//        		  var data = $(this).serialize();
//        		  data += grid_sheet.GetSaveJson({AllSave:1});
       		  
//        		  $.post( url, data )
//        		  .done(function( data ) {
//        		    console.log('--->', data);
//        		  });
//        		});
        	
        	//hidden input을 가지고있는 poiForm
        	var myForm = document.poiForm;
/*         	var param = $('#frmSearch').serialize(); */
        	var url ='<c:url value="/dq/result/poiDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="poiForm";
        	myForm.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김
        	
        	break;
    }       
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
        
        <form name="poiForm">
			<input type="hidden" id ="dbmsId" name = "dbmsId" value="">
			<input type="hidden" id ="schId" name = "schId" value="">
			<input type="hidden" id ="dbmsLnm" name = "dbmsLnm" value="">
			<input type="hidden" id ="schLnm" name = "schLnm" value="">
			<input type="hidden" id ="searchBgnDe2" name = "searchBgnDe2" value="">
			<input type="hidden" id ="searchEndDe2" name = "searchEndDe2" value="">
		</form>
	
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
                           <th scope="row"><s:message code="INQ.TERM" /></th> <!--  -->
                           <td>
                           		<input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${search.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${search.searchEndDe}"/>
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
		     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "100%");</script>            
		</div>
		<!-- 그리드 입력 입력 End -->
	</div>
</body>
</html>
