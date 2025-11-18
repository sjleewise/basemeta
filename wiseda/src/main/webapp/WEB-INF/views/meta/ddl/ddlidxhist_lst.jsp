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

var connTrgSchJson = ${codeMap.connTrgSch} ;
var interval = "";
var initIdxTab = false;
EnterkeyProcess("Search");

$(document).ready(function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
//         $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        

    	
    	$( "#tabsIdx" ).tabs();
    	
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
    	
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #ddlTblPnm"), "DDLTBL");

    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
    	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	loadDetail();
	loadDetailIdxCol();
	
	//loadDetailScript();
	$("#tabsIdx").show();
	//$( "#layer_div" ).show();
	
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.DDLIDXHIST.LST1'/>"
							/* No.|등록유형코드|버전|인덱스ID|인덱스명|인덱스명(논리)|DBMSID|DBMS명|스키마ID|DB스키마명|DDL테이블ID|DDL테이블명|DDL테이블명(논리)|인덱스스페이스ID|인덱스스페이스명| */
							+"<s:message code='META.HEADER.DDLIDXHIST.LST2'/>", Align:"Center"}
						/* PK인덱스여부|Unique인덱스여부|인덱스유형|스크립트정보|요청번호|요청일련번호|설명|최초요청일시|최초요청자명|요청일시|요청자명|승인일시|승인자명 */
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"ddlIdxId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"ddlIdxPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"ddlIdxLnm", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:110,   SaveName:"dbConnTrgId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:110,   SaveName:"dbConnTrgPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:110,   SaveName:"dbSchId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:110,   SaveName:"dbSchPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"ddlTblId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"ddlTblPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"ddlTblLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"idxSpacId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:200,   SaveName:"idxSpacPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"pkIdxYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"ukIdxYn",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"idxTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"scrtInfo",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:400,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:1},

						{Type:"Date",   Width:100,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("idxTypCd",	${codeMap.idxTypCdibs});
		SetColProperty("pkIdxYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("ukIdxYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
      	SetSheetHeight(250);
        // FitColWidth();  
        
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
        	
			var param = $("#frmSearch").serialize();
			$('#dmn_sel_title').html('');
			
// 			grid_sub_ddltblchange.RemoveAll();
			grid_sub_ddlidxcollist.RemoveAll();
			grid_sub_ddlidxcollist.SetColHidden("regTypCd",0);
			grid_sub_ddlidxcollist.SetColHidden("objVers",0);
// 			grid_sub_ddltblidxcol.RemoveAll();
// 			grid_sub_ddltblcolchange.RemoveAll();
			loadDetail();
			loadDetailIdxCol();
// 			loadDetailScript();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlIdxHistlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
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
	var ddlIdxId = "&ddlIdxId="+grid_sheet.GetCellValue(row, "ddlIdxId")+"&objVers="+grid_sheet.GetCellValue(row, "objVers");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DDL.IDEX.NM" /> : ' + param.ddlIdxPnm; //DDL 인덱스명
	$('#ddl_sel_title').html(tmphtml);
	if(initIdxTab==false){
		initIdxTab = true;
	    initsubgrid_ddlidxcolchange();
	}
	//메뉴ID를 토대로 조회한다....
	loadDetail(ddlIdxId);
// 	var param1 = "ddlTblId="+param.ddlTblId;
// 	var param1 = "ddlTblId="+param.ddlTblId + "&scrtInfo="+param.scrtInfo;
	
	//DDL 스크립트 
// 	loadDetailScript(param1);
	$("#frmInputDdlScript #ddlscript").val(param.scrtInfo);
	
	grid_sub_ddlidxcollist.DoSearch('<c:url value="/meta/ddl/getDdlIdxCollistHist.do" />', ddlIdxId);
	//grid_sub_ddlidxchange.DoSearch('<c:url value="/meta/ddl/getDdlIdxChangeHist.do" />', ddlIdxId);
	grid_sub_ddlidxcolchange.DoSearch('<c:url value="/meta/ddl/getDdlIdxColChangeHist.do" />', ddlIdxId);
}

//상세정보호출
function loadDetail(param) {
	$('div#detailIdxInfo1').load('<c:url value="/meta/ddl/ajaxgrid/ddlidxinfohist_dtl.do"/>', param, function(){
		if(param == null || param == "" || param =="undefined"){
			
		} else {
		}
		//$('#tabs').show();
	});
}

//상세정보호출
function loadDetailScript(param) {
	$('div#detailIdxInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
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
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DDL.IDEX.INQ" /></div> <!-- DDL 인덱스조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
							<td><select id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select id="dbSchId" class="" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select></td>
							<th scope="row"><label for="ddlIdxPnm"><s:message code="IDEX.NM" /></label></th> <!-- 인덱스명 -->
							<td><input type="text" id="ddlIdxPnm" name="ddlIdxPnm" class="wd200"/></td>
						</tr>
						<tr>
							<th scope="row"><label for="ddlTblPnm"><s:message code="TBL.NM" /></label></th> <!-- 테이블명 -->
							<td><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd200"/></td>
							<th scope="row"><label for="ddlColPnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
							<td><input type="text" id="ddlColPnm" name="ddlColPnm" class="wd200"/></td>
						</tr>
						<tr>
                               <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                               <td class="bd_none">
                               	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                               	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                               	<a href="#" class="tb_bt"><s:message code="DD7" /></a> <!-- 7일 -->
                               	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                               	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                               	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                               </td>
                               <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" readonly>  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}" readonly>
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
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabsIdx" style = "display:none;">
	  <ul>
	    <%-- <li><a href="#tabs-1"><s:message code="DDL.IDEX.DTL.INFO" /></a></li> --%> <!-- DDL인덱스 상세정보 -->
	    <li><a href="#tabs-1"><s:message code="DTL" /></a></li> <!-- 영문판용(한글버젼시 위 주석 사용) -->
	    <li><a href="#tabs-2"><s:message code="IDEX.CLMN.LST" /></a></li> <!-- 인덱스컬럼목록 -->
	    <li><a href="#tabs-3" id="colinfo"><s:message code="IDEX.CLMN.DTL.INFO" /></a></li> <!-- 인덱스컬럼 상세정보 -->
<!-- 	    <li><a href="#tabs-4">인덱스이력</a></li> -->
	    <li><a href="#tabs-5"><s:message code="IDEX.CLMN.HSTR" /></a></li> <!-- 인덱스컬럼이력 -->
<!--  	    <li><a href="#tabs-6">DDL</a></li>-->
	  </ul>
	  <div id="tabs-1">
			<div id="detailIdxInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddlidxcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailIdxInfo2"></div>
	  </div>
<!-- 	   <div id="tabs-4"> -->
<%-- 			<%@include file="ddlidxchange_dtl.jsp" %> --%>
<!-- 	  </div> -->
	   <div id="tabs-5">
			<%@include file="ddlidxcolchange_dtl.jsp" %>
	  </div>
<!-- 	   <div id="tabs-6">
 			<div id="detailIdxInfoScript"></div> -->
<%-- 			<%@include file="ddlscript_dtl.jsp" %> --%>
	  </div>
	 </div>
</body>
</html>