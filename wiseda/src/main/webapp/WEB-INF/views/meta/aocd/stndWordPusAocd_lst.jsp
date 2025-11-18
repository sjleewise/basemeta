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
<title><s:message code="STRD.WORD.UTLZ.ANLY" /></title> <!-- 표준단어 활용도 분석 -->

<script type="text/javascript">



$(document).ready(function() {
	 
// 		initGrid();
		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
        $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
//         create_selectbox(bizdtlcdJson, $("#bizDtlCd"));
    /*     $( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
	 */
        
      //달력팝업 추가...
	 //	$( "#searchBgnDe" ).datepicker();
	//	$( "#searchEndDe" ).datepicker();
    	
		//그리드 하단 영역 
    	//$( "#tabs" ).tabs();
		
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류(업무상세코드 공통코드명), 최대표시 갯수(default-10개))
		setautoComplete($("#frmSearch #stndNm"), "STWD");
		
		//$( "#layer_div" ).show();
	
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	
	initGrid();
	
	//표준단어 - where used 초기화
	initsubgrid_stwdwhereused();
		
	
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,SizeMode:0};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
		var headers = [
						{Text:"<s:message code='META.ADD.EFFECT.2'/>"}
					,   {Text:"<s:message code='META.ADD.EFFECT.3'/>", Align:"Center"}
						]
					;
					//No.|표준단어명|표준단어ID|단어영문명|영문의미|설명|사용건수|사용건수|사용건수|사용건수|사용건수|비고
					//No.|표준단어명|표준단어ID|단어영문명|영문의미|설명|복합어|도메인|용어|테이블|합계|비고
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",   Width:50,  SaveName:"ibsSeq",			Align:"Right", Edit:0},
						{Type:"Text",   Width:200,  SaveName:"stwdLnm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"stwdId",			Align:"Left", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"stwdPnm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:200,  SaveName:"engMean",			Align:"Left", Edit:0},
						{Type:"Text",   Width:400,  SaveName:"objDescn",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"",				Align:"Center", Edit:0 ,Hidden:1},
						{Type:"Int",   Width:150,  SaveName:"domnNumCnt",		Align:"Center", Edit:0},
						{Type:"Int",   Width:150,  SaveName:"itemNumCnt",		Align:"Center", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"entyNumCnt",		Align:"Center", Edit:0, Hidden:1},
						{Type:"Int",   Width:150,  SaveName:"totlNumCnt",		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName:"reMark",		Align:"Left", Edit:0, Hidden:1},
					];
                    
        InitColumns(cols);
        
     //   SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
        
        //SetColHidden("rqstUserNm",1);
        
        //업무구분을 한글명칭으로
      //  SetColProperty("bizDcd", ${codeMap.bizDcdibs});

        //FitColWidth();  
        
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
    	grid_sheet.DoSearch("<c:url value="/meta/aocd/getStndWordPusAocd.do" />", param);
		break;
	case "Down2Excel": //엑셀내려받기
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'표준단어활용도분석'});
		break;
	}
	
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly){
	if(row<1) return;
	
	//더블클릭으로 해당 obj_id에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	var objId =grid_sheet.GetCellValue(row,"stwdId");
    window.open().location.href ="<c:url value="/meta/stnd/stwd_lst.do"/>"+"?objId="+objId;
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="STRD.WORD.NM" /> : ' + param.stwdLnm + ' , <s:message code="WORD.ENSN.NM" /> : '+param.stwdPnm; // 표준단어명,
	$('#dmn_sel_title').html(tmphtml);

	//상세정보를 보여주기 위한 데이터 
	var param1 = "stwdId="+param.stwdId;
	
	//tabs에 데이터 상세정보 전달 조회
	grid_sub_stwdwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdwhereused_dtl.do" />', param1);
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
// 		alert(grid_sheet.GetCellValue(1, "stwdLnm"));
// 		alert(grid_sheet.GetCellValue(2, "stwdLnm"));
// 		alert(grid_sheet.GetCellValue(3, "stwdLnm"));
// 		alert(grid_sheet.GetCellValue(4, "stwdLnm"));
	}
	
}


</script>


</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="STRD.WORD.UTLZ.ANLY" /></div> <!-- 표준단어 활용도 분석 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
                   <caption><s:message code="STRD.WORD.UTLZ.ANLY.INQ.FORM" /></caption> <!-- 표준단어 활용도 분석 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:30%;" />
                    <col style="width:10%;" />
                   <col style="width:30%;" /> 
                   </colgroup>
                   
                   <tbody>                 
                   			<tr>
                   				<th scope="row" ><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
						        <td colspan="3">
						        <select  id="stndAsrt" name="stndAsrt"  class="wd300" >
 					                <option value=""><s:message code="WHL"/></option>
					                <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					        		  <option value="${code.codeCd}">${code.codeLnm}</option>
					        		</c:forEach> 
						        </select>
                   			</tr>           
                            <tr>
                                <th scope="row"><label for="stndNm"><s:message code="STRD.WORD.NM" /></label></th> <!-- 표준단어명 -->
                                <td><input id="stndNm" name="stndNm" type="text" class="wd80" style="width:98%"/></td>
                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td><input id="objDescn" name="objDescn" type="text" class="wd80" style="width:98%" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="orderby"><s:message code="ARY.SQNC" /></label></th> <!-- 정렬순서 -->
                                <td>
	                                <select id="orderby" class="wd100" name="orderby" style="width:25%">
										<option value="1" selected><s:message code="T.USE.CCNT" /></option> <!-- 총 사용건수 -->
										<option value="2"><s:message code="DMN.USE.CCNT" /></option> <!-- 도메인 사용건수 -->
					  					<option value="3"><s:message code="TERMS.USE.CCNT" /></option> <!-- 용어 사용건수 -->
									</select>
                                </td>
                              	<th scope="row"><label for=""><s:message code="ARY.MTHD" /></label></th> <!-- 정렬방식 -->
                              	<td>
	                              	<select id="asc" class="wd100" name="asc">
										<option value="desc" selected><s:message code="DESC" /></option> <!-- 내림차순 -->
										<option value="asc"><s:message code="ASC" /></option> <!-- 오름차순 -->
									</select>
                              	</td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
         <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
          <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
         <!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

</div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 End -->


<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
	    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->

<div style="clear:both; height:5px;"><span></span></div>

<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">Where Used</a></li>
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="../stnd/stwdwhereused_dtl.jsp" %>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
</div>	
</div>


</body>
</html>