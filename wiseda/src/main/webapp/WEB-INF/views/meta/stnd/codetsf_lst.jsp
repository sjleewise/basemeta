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

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSchIdCodeTsf} ;

$(document).ready(function() {
		
// 		//$( "#tabs" ).tabs();
// 		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
//         $("#btnTreeNew").hide();
//         $("#btnSave").hide();
//         $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
    
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
// 		loadDetail();
		
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
		//setautoComplete($("#frmSearch #stwdLnm"), "STWD");
		
		//기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});
		
     	
    
		
      	double_select(connTrgSchJson, $("#frmSearch #tgtDbConnTrgId"));
	   	$('select', $("#frmSearch #tgtDbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	loadDetail();
	
	//$( "#tabs" ).tabs();
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();


	
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	
	//$( "#layer_div" ).show();
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var headtext  = "No.|<s:message code='META.HEADER.CODETSF.LST.1'/>";
		headtext += "|<s:message code='META.HEADER.CODETSF.LST.2'/>";
		headtext += "|<s:message code='META.HEADER.CODETSF.LST.3'/>";
		headtext += "|<s:message code='META.HEADER.CODETSF.LST.4'/>";
		headtext += "|<s:message code='META.HEADER.CODETSF.LST.5'/>";
		headtext += "|<s:message code='META.HEADER.CODETSF.LST.6'/>";

		//headtext  = "No.|상태";
		//headtext += "|타겟DB_ID|타겟DB명|타겟스키마ID|타겟스키마명";
		//headtext += "|도메인ID|도메인논리명|코드유형|대분류코드|코드ID|코드값|코드값명|상위코드ID|상위코드값";
		//headtext += "|표시순서|사용여부|기타1|기타1명|기타2|기타2명|기타3|기타3명|기타4|기타4명|기타5|기타5명";
		//headtext += "|적요1|적요2";
		//headtext += "|비고|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";

	
	     var headers = [
	     			{Text:headtext, Align:"Center"}
	     		];
	     
	     var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
	     
	     InitHeaders(headers, headerInfo); 
         
	     var cols = [						
	     			{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
	     			{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgId",   	Align:"Left", Edit:0,Hidden:1},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgPnm",   	Align:"Left", Edit:0,Hidden:0},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbSchId",   	Align:"Left", Edit:0,Hidden:1},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbSchPnm",   	Align:"Left", Edit:0,Hidden:0},
	     			{Type:"Text",   Width:100,   SaveName:"dmnId",	 	Align:"Left", Edit:0, KeyField:0, Hidden:1},
	     			{Type:"Text",   Width:100,   SaveName:"dmnLnm",	 	Align:"Left", Edit:0, KeyField:0, Hidden:0},
	     			{Type:"Combo",   Width:100,  SaveName:"cdValTypCd",   	Align:"Left", Edit:0},
	     			{Type:"Text",   Width:100,  SaveName:"dmnDscd",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"cdValId",   	Align:"Left", Edit:0,Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"cdVal",   	Align:"Left", Edit:0, KeyField:1},
	     			{Type:"Text",   Width:100,  SaveName:"cdValNm",   	Align:"Left", Edit:0, KeyField:1},
	     			{Type:"Text",   Width:60,  SaveName:"uppCdValId",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:100,  SaveName:"uppCdVal",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"dispOrd",   	Align:"Left", Edit:0},
	     			{Type:"Combo",  Width:80,  SaveName:"useYn",		Align:"Center", Edit:0},
	     			{Type:"Text",   Width:60,  SaveName:"vvNote1",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNoteNm1",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNote2",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNoteNm2",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNote3",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNoteNm3",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNote4",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNoteNm4",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNote5",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"vvNoteNm5",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"outlCntn1",   	Align:"Left", Edit:0, Hidden:1},
	     			{Type:"Text",   Width:60,  SaveName:"outlCntn2",   	Align:"Left", Edit:0, Hidden:1},
	 	    		{Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
	 	    		{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
	 	    		{Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
	 	    		{Type:"Text",   Width:50,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
	 	    		{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
	 	    		{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
	 	    	];
	 	    		
	            InitColumns(cols);
	            
	            SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
	            SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
	            SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	            SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
	            SetColProperty("useYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});
	            SetColProperty("cdValTypCd",${codeMap.cdValTypCdibs});
	            InitComboNoMatchText(1, "");
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/stnd/ajaxgrid/codetsf_dtl.do"/>', param, function(){
		if(!isBlankStr(param)) {
			//그리드 초기화 한다.
			initDtlGrids();
			
			grid_sub_codetsfchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/codetsfchange_dtl.do" />', param);

		}
		
		//$('#tabs').show();
	});
}


		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	//그리드 초기화 한다.
			//initDtlGrids();
			var param = $("#frmSearch").serialize();
			//alert(param);
			//$('#program_sel_title').html('');
			loadDetail();

			//grid_sub_stwdchange.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getCodeTsfWamList.do" />", param);

        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}


//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		//코드이관 변경이력 그리드 초기화
	 	initsubgrid_codetsfchange();
		
	 	chkinitdtlgrids = true;	
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
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	param +="&cdValId="+grid_sheet.GetCellValue(row, "cdValId");
	param +="&tgtDbConnTrgId="+grid_sheet.GetCellValue(row, "tgtDbConnTrgId");
	param +="&tgtDbSchId="+grid_sheet.GetCellValue(row, "tgtDbSchId");
	//var msgId = "&msgId="+grid_sheet.GetCellValue(row, "msgId");



	
	//ID를 토대로 조회한다....
	loadDetail(param);
	
	
}


function grid_sheet_OnSaveEnd(code, message) {

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

<div id="layer_div" >

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="CD.TFCT.INQ" /></div> <!-- 코드이관 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INQ' />"> <!-- 표준단어조회 -->
                   <caption><s:message code="CD.TFCT.INQ.FORM" /></caption> <!-- 코드이관 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                 <th scope="row"><label for="tgtDbConnTrgId"><s:message code="TARG.DB.SCHEMA.NM" /></label></th> <!-- 타겟 DB/스키마명 -->
							     <td>
							         <select id="tgtDbConnTrgId" class="wd100" name="tgtDbConnTrgId">
					                      <option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
					                 </select>
					                 <select id="tgtDbSchId" class="wd100" name="tgtDbSchId">
					                      <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                 </select>
                                 </td>                   		
                            <th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
                            <td>
                                <span class="input_file">
                                <input class="wd200" type="text" name="dmnLnm" id="dmnLnm" />
                                </span>
                            </td>  
                        </tr>
<!--                          <tr>                           -->
<!--                              <th scope="row"><label for="cdValTypCd">코드유형</label></th> -->
<!--                              <td colspan ="3"> -->
<%--                                  <span class="input_file"> --%>
<%--                                  <select name="cdValTypCd" id="cdValTypCd" /> --%>
<!--                                  <option value="">선택</option> -->
<!-- 					 		     <option value="O">단순코드</option> -->
<!-- 					 		     <option value="C">복잡코드</option> -->
<%--                                  </span> --%>
<!--                              </td>   -->
<!--                                  <th scope="row"><label for="dmnDscd">대분류코드</label></th> -->
<!--                              <td> -->
<%--                                  <span class="input_file"> --%>
<!--                                  <input class="wd200" type="text" name="dmnDscd" id="dmnDscd" /> -->
<%--                                  </span> --%>
<!--                              </td>   -->
<!--                         </tr> -->
                           
                        <tr>
                            	<th scope="row"><label for="cdVal"><s:message code="CD.VAL" /></label></th> <!-- 코드값 -->
                                <td ><input type="text" id="cdVal" name="cdVal" class="wd200"/></td>
                                <th scope="row"><label for="useYn"><s:message code="USE.YN" /></label></th> <!-- 사용여부 -->
                                <td><select id="useYn" name="useYn">
                                		<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
                                        <option value="Y">Y</option>
                                        <option value="N">N</option>
                                    </select></td>
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
      		                             <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td>
  						    </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
</div>
<div style="clear:both; height:10px;"><span></span></div>

       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />         

	<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<!-- <div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div> -->
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="CD.TFCT.INFO" /></a></li> <!-- 코드이관 정보 -->
	    <li><a href="#tabs-2"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo1"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-2">			
	       <%@include file="codetsfchange_dtl.jsp" %>
	  </div>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	</div>
   


</body>
</html>
