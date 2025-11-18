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

var dmnginfotpJson = ${codeMap.dmnginfotp} ;
//도메인그룹 selectbox
var bscLvl = parseInt("${bscLvl}");
var selectBoxId = "${selectBoxId}";
var selectBoxNm = "${selectBoxNm}";
var firstSelectBoxId = selectBoxId.split("|");
var firstSelectBoxNm = selectBoxNm.split("|");

// var bizdtlcdJson = ${codeMap.bizdtlcd};

$(document).ready(function() {
	
// 		//$( "#tabs" ).tabs();
		//마우스 오버 이미지 초기화
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
        
//         create_selectbox(bizdtlcdJson, $("#bizDtlCd"));
        $( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
	
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #stndNm"), "STWD");
	
});
//엔터키 처리한다.
// EnterkeyProcess("Search");

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
// 	//$( "#tabs" ).tabs();
		
	var tmpstr = $("#stndNm").val();
	if(tmpstr != null && tmpstr != "" && tmpstr != "undefined") {
		doAction("Search");
	}
	
	$( "#tabsStwd" ).tabs().show();
	$( "#tabsDmn" ).tabs();
	$( "#tabsSditm" ).tabs();
	loadDetail("","STWD");
	
// 	//$( "#layer_div" ).show();

/* 	setTimeout(function(){
// 		alert("start");
		
		//표준단어 변경이력 그리드 초기화
	 	initsubgrid_stwdchange();

		//표준단어 - where used 초기화
	 	initsubgrid_stwdwhereused();

	 	//도메인 구성정보 그리드 초기화
	 	initsubgrid_dmninit();

	 	//도메인 유효값 그리드 초기화
	 	initsubgrid_dmnvalue();

	 	//도메인 변경 이력
	 	initsubgrid_dmnchange();

	 	//유효값 변경 이력 그리드 초기화
	 	initsubgrid_dmnvaluechange();

	 	//도메인 - where used  그리드 초기화
	 	initsubgrid_dmnwhereused();

	 	//표준용어 - 단어구성정보 그리드 초기화
	 	initsubgrid_sditminit();

	 	//표준용어 - 변경이력 그리드 초기화
	 	initsubgrid_sditmchange();

	 	//표준용어 - Where used 그리드 초기화
	 	initsubgrid_sditmwhereused();
		
	}, 100*1);
 */
});


$(window).resize(
    
    	// grid_sheet.SetExtendLastCol(1);    
);


function initGrid()
{
    
    with(grid_sheet){
    	
//     	var cfg = {SearchMode:1,Page:100};
    	var cfg = {SearchMode:2, Page:100, UseHeaderSortCancel:1};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.STND.LST'/>", Align:"Center"}
					];
					//No.|표준단어ID|표준분류|표준사전명|사전유형|표준사전논리명|표준사전물리명|등록자명|설명
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"stwdId",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"stndNm",		Align:"Left", Edit:0},
						{Type:"Combo",  Width:100,  SaveName:"bizDtlCd",	Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"stwdLnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"stwdPnm",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:100,  SaveName:"rqstUserNm",	Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"objDescn",	Align:"Left", Edit:0}
					];
                    
		
        InitColumns(cols);
        
        SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
        SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
        //SetColHidden("rqstUserNm",1);

        FitColWidth();  
        
        SetExtendLastCol(1);   
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
    	
//     alert(grid_sheet.Version());
   
}


		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getstndlist.do" />", param);
			
			//그리드 초기화 한다.
			initTotDicGrid();
			
// 페이징 처리
// 			param += "&onePageRow=1000";
// 			var info = {PageParam: "pageNum",  UseWaitImage:true, Param: param};
// 			grid_sheet.DoSearchPaging("<c:url value="/meta/stnd/getstndlist.do" />", info);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
      //보여지는 컬럼들만 엑셀 다운로드          
      var downColNms = "";
     	for(var i=0; i<=grid_sheet.LastCol();i++ ){
     		if(grid_sheet.GetColHidden(i) != 1){
     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
     		}
     	}

            grid_sheet.Down2Excel({HiddenColumn:1,DownCols:downColNms, Merge:1, FileName:'사전통합조회'});
            break;
        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}


var chkinittotgrid = false;
//그리드 초기화 한다.
function initTotDicGrid(){
	if (!chkinittotgrid) {
		
		//표준단어 변경이력 그리드 초기화
	 	initsubgrid_stwdchange();

		//표준단어 - where used 초기화
	 	initsubgrid_stwdwhereused();

	 	//도메인 구성정보 그리드 초기화
	 	initsubgrid_dmninit();

	 	//도메인 유효값 그리드 초기화
	 	initsubgrid_dmnvalue();

	 	//도메인 변경 이력
	 	initsubgrid_dmnchange();

	 	//유효값 변경 이력 그리드 초기화
	 	initsubgrid_dmnvaluechange();

	 	//도메인 - where used  그리드 초기화
	 	initsubgrid_dmnwhereused();

	 	//표준용어 - 단어구성정보 그리드 초기화
	 	initsubgrid_sditminit();

	 	//표준용어 - 변경이력 그리드 초기화
	 	initsubgrid_sditmchange();

	 	//표준용어 - Where used 그리드 초기화
	 	initsubgrid_sditmwhereused();
		
	 	//사전비교 단어
	 	initsubgrid_diccomparisonWord();
	 	
		//사전비교 도메인
	 	initsubgrid_diccomparisonDmn();
		
		//사전비교 용어
	 	initsubgrid_diccomparisonSditm();
	 	
		chkinittotgrid = true;	
	}
	
}

//도메인 명에 따라 해당하는 도메인 그룹, 인포정보를 셋팅한다.
function setDomainInfoinit () {
	if(isBlankStr($("#frmInput #dmnLnm").val())) return;
	var vUrl = "<c:url value='/meta/stnd/getdmninfojson.do'/>";
	var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
		param += "&dmnPnm="+$("#frmInput #dmnPnm").val();

	ajax2Json(vUrl, param, function(data){
		
	    for(var i=0; i<firstSelectBoxId.length; i++){
		    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), data[firstSelectBoxNm[i]]);
			$("#frmInput #"+firstSelectBoxId[i]).change();
	    }
		
		setselectbytext($("#frmInput #infotpId"), data.infotpLnm);
		$("#frmInput #infotpId").change();
	});
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
	

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="STRD.BFHD.NM" /> : ' + param.stwdLnm +' [ <s:message code="BFHD.PTRN" /> : ' + param.bizDtlCd + ' ]'; //표준사전명, 사전유형
	$('#stnd_sel_title').html(tmphtml);
	
	
	if(grid_sheet.GetCellValue(row, "bizDtlCd") == 'STWD'){ //표준단어
		$( "#tabsStwd" ).show();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
        var stwdId = "&stwdId="+grid_sheet.GetCellValue(row, "stwdId");
		loadDetail(stwdId, 'STWD');
		
		
	} else if (grid_sheet.GetCellValue(row, "bizDtlCd") == 'DMN'){ //도메인
		$( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).show();
        $( "#tabsSditm" ).hide();
        var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "stwdId");
		loadDetail(dmnId, 'DMN');
		
		
	} else if (grid_sheet.GetCellValue(row, "bizDtlCd") == 'SDITM'){ //표준용어
		$( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).show();
        var sditmId = "&sditmId="+grid_sheet.GetCellValue(row, "stwdId");
		loadDetail(sditmId, 'SDITM');
		
		
	}
	
}

//상세정보호출
function loadDetail(param, param1) {
	if(param1 == 'STWD') {
		$('div#stwdDetailInfo').load('<c:url value="/meta/stnd/ajaxgrid/stwdinfo_dtl.do"/>', param, function(){
			
			if(param == null || param == "" || param =="undefined") {
				
			} else {
				grid_sub_stwdchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdchange_dtl.do" />', param);
				grid_sub_stwdwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdwhereused_dtl.do" />', param);
				grid_sub_diccomparisonWord.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdcomparison_dtl.do" />', param);
			}
	 		
			//$('#tabs').show();
			
		});
	} else if(param1 == 'DMN') {
		$('div#dmnDetailInfo').load('<c:url value="/meta/stnd/ajaxgrid/dmninfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param == "undefined") {
				
			} else {
				grid_sub_dmnchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmnchange_dtl.do" />', param);	
				grid_sub_dmninit.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmninit_dtl.do" />', param);
				grid_sub_dmnvalue.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmnvalue_dtl.do" />', param);
				grid_sub_dmnvaluechange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmnvaluechange_dtl.do" />', param);
				grid_sub_dmnwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmnwhereused_dtl.do" />', param);
				grid_sub_diccomparisonDmn.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmncomparison_dtl.do" />', param);
			}
			
			//$('#tabs').show();
			
		});
	} else if(param1 == 'SDITM') {
		$('div#sditmDetailInfo').load('<c:url value="/meta/stnd/ajaxgrid/sditminfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param == "undefined") {
				
			} else {
				grid_sub_sditmchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmchange_dtl.do" />', param);
				grid_sub_sditminit.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditminit_dtl.do" />', param);
				grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmwhereused_dtl.do" />', param);
				grid_sub_diccomparisonSditm.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmcomparison_dtl.do" />', param);
			}
			
			//$('#tabs').show();
			
		});
	}
	

}


function grid_sheet_OnSaveEnd(code, message) {

}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code='BFHD.INTG.INQ' /></div><!-- 사전통합 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BFHD.INTG.INQ' />"> <!-- 사전통합조회 -->
                   <caption><s:message code="BFHD.INTG.INQ.FORM" /></caption> <!-- 사전통합 검색폼 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   </colgroup>
                   
                   <tbody>      
                            <tr>
                               <th scope="row"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					        <td>
					        <select  id="stndAsrt" name="stndAsrt"  class="wd300">
 					                <option value=""><s:message code="WHL"/></option>
					                <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					        		  <option value="${code.codeCd}">${code.codeLnm}</option>
					        		</c:forEach> 
					        </select>
					        </td>
					         <th scope="row"><label for="bizDtlCd"><s:message code="BFHD.PTRN" /></label></th> <!-- 사전유형 -->
                                <td>
                                <select id="bizDtlCd" class="" name="bizDtlCd">
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value="STWD"><s:message code="STRD.WORD" /></option> <!-- 표준단어 -->
									<option value="DMN"><s:message code="DMN" /></option> <!-- 도메인 -->
									<option value="SDITM"><s:message code="STRD.TERMS" /></option> <!-- 표준용어 -->
								</select>
								</td>
                            </tr>                      
                            <tr>
                               
                                <th scope="row"><label for="stndNm"><s:message code="STRD.BFHD.NM" /></label></th> <!-- 표준사전명 -->
                                <td><input type="text" id="stndNm" name="stndNm" class="wd200" value="${stndNm}" /></td>
                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td><input type="text" id="objDescn" name="objDescn" class="wd200"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="stnd_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabsStwd" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="WORD.INFO" /></a></li> <!-- 단어정보 -->
	    <li><a href="#tabs-2"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	    <li><a href="#tabs-3">Where Used</a></li>
	    <li><a href="#tabs-4"><s:message code="DIC.COMPARISON" /></a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="stwdDetailInfo"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="stwdchange_dtl.jsp" %>
<!-- 			<div id="detailInfo2"></div> -->
	  </div>
	  <div id="tabs-3">
			<%@include file="stwdwhereused_dtl.jsp" %>
<!-- 			<div id="detailInfo3"></div> -->

	  </div>
	  <div id="tabs-4">
			<%@include file="dicComparisonWord_dtl.jsp" %>
	  </div>
	 </div>

	 <div id="tabsDmn" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DMN.INFO" /></a></li> <!-- 도메인정보 -->
	    <li><a href="#tabs-2"><s:message code="DMN.CMPS" /></a></li> <!-- 도메인구성 -->
	    <li><a href="#tabs-3"><s:message code="VLD.VAL" /></a></li> <!-- 유효값 -->
	    <li><a href="#tabs-5"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	    <li><a href="#tabs-6"><s:message code="VLD.VAL.CHG.HSTR" /></a></li> <!-- 유효값변경이력 -->
	    <li><a href="#tabs-7">Where Used</a></li>
	    <li><a href="#tabs-8"><s:message code="DIC.COMPARISON" /></a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="dmnDetailInfo"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="dmninit_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
			<%@include file="dmnvalue_dtl.jsp" %>	
	  </div>
	   <div id="tabs-5">
			<%@include file="dmnchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-6">
			<%@include file="dmnvaluechange_dtl.jsp" %>
	  </div>
	   <div id="tabs-7">
			<%@include file="dmnwhereused_dtl.jsp" %>
	  </div>
	   <div id="tabs-8">
			<%@include file="dicComparisonDmn_dtl.jsp" %>
	  </div>
	 </div>
	 <div id="tabsSditm" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="TERMS.INFO" /></a></li> <!-- 용어정보 -->
	    <li><a href="#tabs-2"><s:message code="TERMS.CMPS" /></a></li> <!-- 용어구성 -->
	    <li><a href="#tabs-3"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	    <li><a href="#tabs-5">WHERE USED</a></li>
	    <li><a href="#tabs-6"><s:message code="DIC.COMPARISON" /></a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="sditmDetailInfo"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="sditminit_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
			<%@include file="sditmchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-5">
			<%@include file="sditmwhereused_dtl.jsp" %>
	  </div>
	  <div id="tabs-6">
			<%@include file="dicComparisonSditm_dtl.jsp" %>
	  </div>
	 </div>

	<!-- 선택 레코드의 내용을 탭처리 END -->
	</div>
</body>
</html>