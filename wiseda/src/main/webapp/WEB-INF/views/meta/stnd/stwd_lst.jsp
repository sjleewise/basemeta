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
		setautoComplete($("#frmSearch #stwdLnm"), "STWD");
		
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
	loadDetail();
	
	//$( "#tabs" ).tabs();
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();

	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	if(!isBlankStr("${stwdId}")) {
		var param = "stwdId="+"${stwdId}";
		grid_sheet.DoSearch("<c:url value="/meta/stnd/getStndWordlist.do" />", param);
		
	}
	
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
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        var headers = [
                    {Text:"<s:message code='META.HEADER.STWD.LST'/>", Align:"Center"}
                ];
                //No.|상태|선택|표준단어ID|표준분류|표준단어논리명|표준단어물리명|영문의미|한자명|출처구분|표준단어구분|도메인여부|요청번호|요청일련번호|설명|버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:20,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"stwdId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
                    {Type:"Text",   Width:150,  SaveName:"stwdLnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:150,  SaveName:"stwdPnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:200,  SaveName:"engMean",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:80,  SaveName:"cchNm",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:50,  SaveName:"orgDs",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:80,  SaveName:"wdDcd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",   Width:80,  SaveName:"dmnYn",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"rqstNo",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"rqstSno",    Align:"Right", Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"objDescn",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Right", Edit:0, Hidden:1}, 
                    {Type:"Combo",   Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:30,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:20,  SaveName:"frsRqstUserId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Date",  Width:20,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:30,  SaveName:"rqstUserId",    Align:"Left", Edit:0, Hidden:1},          
                    {Type:"Date",   Width:30,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:30,  SaveName:"aprvUserId",    Align:"Left", Edit:0, Hidden:1}
                    
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
     	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	    SetColProperty("wdDcd", ${codeMap.wdDcdibs});
		SetColProperty("dmnYn", 	{ComboCode:"N|Y", ComboText:"N|Y"});
		
      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);

        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/stnd/ajaxgrid/stwdinfo_dtl.do"/>', param, function(){
		if(!isBlankStr(param)) {
			//그리드 초기화 한다.
			initDtlGrids();
			
			grid_sub_stwdchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdchange_dtl.do" />', param);
			grid_sub_stwdwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdwhereused_dtl.do" />', param);
			grid_sub_diccomparisonWord.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdcomparison_dtl.do" />', param);
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
			initDtlGrids();
			var param = $("#frmSearch").serialize();
			$('#program_sel_title').html('');
			loadDetail();
			grid_sub_stwdwhereused.RemoveAll();
			grid_sub_stwdchange.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getStndWordlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
	       //보여지는 컬럼들만 엑셀 다운로드          
	       var downColNms = "";

	       for(var i=0; i<=grid_sheet.LastCol();i++ ){
	     		if(grid_sheet.GetColHidden(i) != 1){
	     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	     		}
	     	}
          
            grid_sheet.Down2Excel({HiddenColumn:1,DownCols:downColNms, Merge:1, FileName:'표준단어조회'});
            
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
		
		//표준단어 변경이력 그리드 초기화
	 	initsubgrid_stwdchange();

		//표준단어 - where used 초기화
	 	initsubgrid_stwdwhereused();
		
	 	//사전비교 단어
	 	initsubgrid_diccomparisonWord();
		
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
	var stwdId = "&stwdId="+grid_sheet.GetCellValue(row, "stwdId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = "<s:message code='STRD.WORD.LGC.NM'/> : " + param.stwdLnm;  /*표준단어논리명*/
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(stwdId);
	
	
}


function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg,Response) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />",null,null,Response);
		return;
	}
	if(!isBlankStr("${stwdId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = "<s:message code='STRD.WORD.LGC.NM'/> : " + param.stwdLnm;  /*표준단어논리명*/
		$('#program_sel_title').html(tmphtml);
		
		
		var stwdId = "";
		stwdId = grid_sheet.GetCellValue(1, "stwdId");
		param = "stwdId="+stwdId;
		
		loadDetail(param);
// 		grid_sub_stwdchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdchange_dtl.do" />', param);
// 		grid_sub_stwdwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdwhereused_dtl.do" />', param);
	}
	FitColWidth();  
}




</script>
</head>

<body>

<div id="layer_div" >

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="STRD.WORD.INQ" /></div> <!-- 표준단어 조회 -->
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
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준단어 검색폼 -->
                   <colgroup>
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>          
                           <tr>
                           <th scope="row" ><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					         <td>
					        <select  id="stndAsrt" name="stndAsrt"  class="wd300">
 					                <option value=""><s:message code="WHL"/></option>
					                <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					        		  <option value="${code.codeCd}">${code.codeLnm}</option>
					        		</c:forEach> 
					        </select>
					        </td>
                                <th scope="row"><label for="stwdLnm"><s:message code="STRD.WORD.NM" /></label></th> <!-- 표준단어명 -->
                                <td><input type="text" id="stwdLnm" name="stwdLnm" class="wd200" /></td>
                                <th scope="row"><label for="engMean"><s:message code="ENSN.MEAN" /></label></th> <!-- 영문의미 -->
                                <td><input type="text" id="engMean" name="engMean" class="wd200" /></td>
                                <th scope="row"><label for="cchNm"><s:message code="CHCH"/></label></th> <!-- 한자 -->
                                <td><input type="text" id="cchNm" name="cchNm" class="wd200"/></td>
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
  						    	<th scope="row"><label for="orgDs"><s:message code="ORGN.DSTC"/></label></th> <!-- 출처구분 -->
                                <td><input type="text" id="orgDs" name="orgDs" class="wd95p"/></td>
                                
                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td><input type="text" id="objDescn" name="objDescn" class="wd95p"/></td>
                                
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="WORD.INFO" /></a></li> <!-- 단어정보 -->
	    <li><a href="#tabs-2"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	    <li><a href="#tabs-3">Where Used</a></li>
	    <li><a href="#tabs-4"><s:message code="DIC.COMPARISON" /></a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo1"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-2">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="stwdchange_dtl.jsp" %>
<!-- 			<div id="detailInfo2"></div> -->
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-3">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="stwdwhereused_dtl.jsp" %>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-4">
			<%@include file="dicComparisonWord_dtl.jsp" %>
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	</div>
   
<%-- <form id="frmstwdId" name="frmstwdId" action="" method="post" > --%>
<%-- 	<input type="text" name="stwdId" id="stwdId" value="${stwdId}" /> --%>
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>
