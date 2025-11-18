<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">


$(document).ready(function(){
	
	initGrid();
	initSubGrid();
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	
	//기간 버튼 클릭 체크
 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); 
	
	$("#btnTreeNew").hide();
	$("#btnDelete").hide();
	
	$("#btnSubTreeNew").hide();
	$("#btnSubDelete").hide();
	$("#btnSubSearch").hide();
	
	//조회 이벤트 처리
	$("#btnSearch").click(function(){ 
		doAction('Search'); 
	});
	
	//메뉴접속통계 엑셀다운...
	$("#btnExcelDown").click(function(){	
		doAction('Down2ExcelTot');
	});
	
	//사용자별 메뉴접속통계 엑셀다운...
	$("#btnSubExcelDown").click(function(){	
		doAction('Down2Excel');
	});
	

	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
});

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.MENUSTATLIST1'/>", Align:"Center"}
                    /* NO.|접속일자|메뉴번호(ID)|접속메뉴|프로그램파일명|접속수(합계) */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",   Width:70,  SaveName:"statsDate",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:70,  SaveName:"menuNo",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:130,  SaveName:"menuPath",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:70,  SaveName:"progrmFileNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:70,  SaveName:"statsCo",    Align:"Center", Edit:0, Hidden:0}
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	//SetColProperty("usergId", ${codeMap.usergp});
        //SetColProperty("usergId", 	{ComboCode:"DA|AD|DB|UR|MR|DV", 	ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function initSubGrid()
{
    
    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.MENUSTATLIST2'/>", Align:"Center"}
                    /* No.|접속일자|메뉴번호(ID)|접속메뉴|프로그램명|접속자명|접속ID|접속통계 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:150,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,  SaveName:"statsDate",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"menuNo",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:600,  SaveName:"menuPath",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:300,  SaveName:"progrmFileNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:300,  SaveName:"loginNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"loginId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:140,  SaveName:"statsCo",    Align:"Center", Edit:0, Hidden:0}
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	//SetColProperty("usergId", ${codeMap.usergp});
        //SetColProperty("usergId", 	{ComboCode:"DA|AD|DB|UR|MR|DV", 	ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
}


function doAction(action) {
	switch(action) {
	
	case 'Search' :	//조회
		var param = $('form[name=frmSearch]').serialize();
		grid_sheet.DoSearch('<c:url value="/commons/sys/menu/ajaxgrid/selectMenuStatTot.do" />', param);
		grid_sub.DoSearch('<c:url value="/commons/sys/menu/ajaxgrid/selectMenuStat.do" />', param);
		break;
	
	//메뉴접속통계 엑셀다운...
	case "Down2ExcelTot":  //엑셀내려받기
	    grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	    break;

	//사용자별 메뉴접속통계 엑셀다운...
	case "Down2Excel":  //엑셀내려받기
	    grid_sub.Down2Excel({HiddenColumn:1, Merge:1});
	    break;
	    
	}
    
}



</script>
</head>
<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="MENU.CONN.STAT" /></div> <!-- 메뉴 접속 통계 -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>

 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
	<form name="frmSearch" action ="" method="post">
	<div class="tb_basic2">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

      <tr>
      	<th><s:message code="TERM.DSTC" /></th> <!-- 기간구분 -->
      	<td>
	        <select  id="pdKind" name="pdKind" title="<s:message code='INQ.COND.CHC' />"> <!-- 검색조건선택 -->
	        	<option value="D"><s:message code="DALY" /></option> <!-- 일별 -->
	            <option value="M"><s:message code="MTHY" /></option> <!-- 월별 -->
	            <option value="Y"><s:message code="YRLY" /></option> <!-- 연도별 -->
	        </select>
      	</td>
      	<th><s:message code="CONN.USER.NM" /></th> <!-- 접속자명 -->
      	<td>
      		<input type="text" name="loginNm" value="${searchVO.loginNm}">
      	</td>
      </tr>
      <tr>
        <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
        <td class="bd_none">
        	<a href="#" class="tb_bt">1<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">3<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">7<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">1<s:message code="MN" /></a> <!-- 개월 -->
            <a href="#" class="tb_bt">3<s:message code="MN" /></a> <!-- 개월 -->
            <a href="#" class="tb_bt">6<s:message code="MN" /></a> <!-- 개월 -->
        </td>
        <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> 
    </table>
    </div>
    </form>
    
    
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
     <!-- 조회버튼영역  -->
     
     <div style="clear:both; height:5px;"><span></span></div>
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            

	</div>
   <!-- 그리드 입력 입력 -->
    <div style="clear:both; height:20px;"><span></span></div>
  	
  	<!-- 그리드 입력 입력 -->
  	<div class="stit" style="display: inline;"><s:message code="USER.MENU.CONN.STAT" /></div> <!-- 사용자별  메뉴접속통계 --> 
   	<tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> 
    <div style="clear:both; height:5px;"><span></span></div>
    <div id="grid_02" class="grid_01"> 
 	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "200px");</script>             

<!-- 	</div> -->
   <!-- 그리드 입력 입력 -->
   
    <div style="clear:both; height:10px;"><span></span></div>
    
</div>    
    
  	
    

</body>
</html>