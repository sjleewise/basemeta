<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title><s:message code="TBL.INQ"/></title> <!-- 테이블 조회 -->

<script type="text/javascript">

EnterkeyProcess("Search");
var regtypcd = ${codeMap.regtypcd} ;

                       
$(document).ready(function() {
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        $("#btnReport").click(function(){
        	
        	doAction("Report");
        	  
        }).hide();
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(regtypcd, $("#regTypCd"));
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #pdmTblLnm"), "PDMTBL");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #pdmColLnm"), "PDMCOL");
        
        
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	//PK값으로 검색
	if(!isBlankStr("${search.pdmTblId}")) {
		var param ="pdmTblId="+"${search.pdmTblId}";
		grid_sheet.DoSearch("<c:url value="/dq/model/getpdmtbllist.do" />", param);
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
        
        var headtext = "<s:message code='META.HEADER.PDMTBL.LST'/>";

        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,  SaveName:"ibsSeq",			Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,  SaveName:"ibsStatus",		Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",		Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",     Width:150, SaveName:"dbConnTrgId",	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:150, SaveName:"dbConnTrgLnm",   Align:"Left", 	Edit:0},
                    {Type:"Text",     Width:150, SaveName:"dbConnTrgPnm",   Align:"Left", 	Edit:0}, 
                    {Type:"Text",     Width:150, SaveName:"dbSchId",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:150, SaveName:"dbSchLnm",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150, SaveName:"dbSchPnm",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150, SaveName:"pdmTblId",   	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:180, SaveName:"pdmTblPnm",		Align:"Left",   Edit:0}, 
                    {Type:"Text",     Width:250, SaveName:"pdmTblLnm",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:40,  SaveName:"crgUserId",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100, SaveName:"crgUserNm",		Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100, SaveName:"objDescn",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:40,  SaveName:"objVers",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:40,  SaveName:"regTypCd",		Align:"Center", Edit:0, Hidden:1},                        
                    {Type:"Date",     Width:100, SaveName:"rqstDtm",		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"rqstUserNm",		Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        
        InitComboNoMatchText(1, "");
        	    SetSheetHeight("250");
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}


//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		initColGrid();
		initTblHlGrid();
		initColHlGrid();
		
	 	chkinitdtlgrids = true;	
	}
	
}  

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	
	$("#frmSearch #subjLnm").val(retjson.fullPath);
	
}


//테이블 클릭시 처리하는 부분...
function tblClick(row) {
	initDtlGrids();

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	//IBSheect 내용을 Form에 저장...(row, $formobj, grid)
	
    ibs2formmapping(row, $("form#frmInput"), grid_sheet);
	
    $("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(1, "rqstDtm"));
    

    $("form#frmSearch input[name=pdmTblId]").val(grid_sheet.GetCellValue(row, "pdmTblId"));
	
    
    //선택행 셋팅..
    var tmptit = "<s:message code='CHC.TBL.NM'/> : " + param.pdmTblPnm + "["+ param.pdmTblLnm + "]"; //선택 테이블명
    $("#tbl_sle_title").html(tmptit);
    
    //컬럼리스트 조회...
    doActionCol("Search");
    doActionTblH("Search");

}

//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	
        	
        	//요청서 마스터 번호로 조회한다...
        	$('#frmSearch input[name=pdmTblId]').val('');
        	$('#frmSearch input[name=subjId]').val('');
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/dq/model/getpdmtbllist.do" />", param);
        	
        	break;
        	
        case "Report": //단일 조회...
        	
			if($("#subjLnm").val() == "") {
        		
				//주제영역을 입력하세요.
				showMsgBox("ERR","<s:message code='SUBJ.INPUT' />")
				return;
        	}
       
        	//var saveJson = grid_sheet.GetSaveJson(1);	

			//if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/model/prtXlsRptPdmTbl.do"/>";
			
			var param = $("#frmSearch").serialize();
			
			$("#frmSearch").attr("target","").attr("action",url).submit();
        	
			break;
			
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'데이터모델조회'});
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
    
    $("#hdnRow").val(row);
    
  //선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
    
    if(row < 1) return;
    
    tblClick(row);

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
		
		$('#btnfrmReset').click();
		
		if(!isBlankStr("${search.pdmTblId}")) {
			tblClick(1);
		}
		
		
			
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
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" ><label for="pdmTblLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" class="wd200" value="${search.pdmTblLnm}"/>
                                </span>
                            </td>
                           <th scope="row" ><label for="pdmColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmColLnm" id="pdmColLnm" class="wd200" value="${search.pdmColLnm}"/>
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
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1" ><a href="#tabs-1"><s:message code="TBL.DFNT.P" /></a></li><!-- 테이블 정의서 -->
	    <li id="tab-2" ><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
<%-- 	    <li id="tab-4" ><a href="#tabs-4"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 --> --%>
	    <li id="tab-6" ><a href="#tabs-6"><s:message code="TBL.HSTR" /></a></li> <!-- 테이블이력 -->
	    <li id="tab-5" ><a href="#tabs-5"><s:message code="CLMN.HSTR" /></a></li> <!-- 컬럼이력 -->
	  </ul>
	  <div id="tabs-1">
		<!-- 입력폼 시작 -->
	         <form name="frmInput" id="frmInput" method="post" >
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->

		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="pdmTblId" name="pdmTblId"/></span></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row"><label for="pdmTblPnm"><s:message code="TBL.NM.PHYC.NM" /></label></th> <!-- 테이블명(물리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd98p" readonly /></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                	<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                                    <td>
                                        <select id="regTypCd" class="" name="regTypCd"  disabled="disabled">
									     <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								        </select>
								</td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                 <div style="clear:both; height:10px;"><span></span></div>
					 <div class="stit"><s:message code="DEMD.DTL.INFO" /></div> <!-- 요청상세정보 -->
					 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="CLMN.NM1" /> <!-- 컬럼 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
<%-- 	                            <tr>
	                                <th scope="row" class=""><label for="frsRqstDtm"><s:message code="FRST.DEMD.DTTM" /></label></th> <!-- 최초요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRST.DMNT" /></label></th><!-- 최초요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr> --%>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DMNT" /></label></th> <!-- 요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
<%-- 	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserNm"><s:message code="APRR" /></label></th> <!-- 승인자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p" readonly/></span></td>
	                            </tr> --%>
	                        </tbody>
	                    </table>
	                </div>
<%-- 					<%@include file="../stnd/otherinfo.jsp" %> --%>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
	  </div>
	  
	  <div id="tabs-2">
	  <div id="test">
	  	<!-- 컬럼 목록 탭 -->
		<%@include file="pdmcol_lst.jsp" %>
	  </div>
		
	  </div>
	  
<%-- 	  <div id="tabs-4">
	  <!-- 컬럼 정보 탭 -->
		<%@include file="pdmcolinfo_dtl.jsp" %>
	  </div> --%>
	  <div id="tabs-6">
		<!-- 테이블 이력 탭 -->
		<%@include file="tblhist_dtl.jsp" %>
	  </div>	  

	  <div id="tabs-5">
		<!-- 컬럼 이력 탭 -->
		<%@include file="coltocolhist_dtl.jsp" %>
	  </div>
	  
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

</body>
</html>