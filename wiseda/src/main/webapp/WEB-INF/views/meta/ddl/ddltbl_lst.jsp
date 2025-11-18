<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set>
<c:set var="sitename"><s:message code="wiseda.site.name" /></c:set>

<html>
<head>
<title></title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
var interval = "";
var ddlTblId2 ="";
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
        

    	
//     	$( "#tabsTbl" ).tabs();
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #ddlTblLnm"), "DDLTBL");

    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
    	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	//PK값으로 검색
	if(!isBlankStr("${search.ddlTblId}")) {
		var param ="ddlTblId="+"${search.ddlTblId}";
		
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdllist.do" />", param);
	}
	
		
	/* var ddlTblLnm = "${search.ddlTblLnm}";
		param2 ="ddlTblLnm="+ddlTblLnm;
	if(ddlTblLnm != null && ddlTblLnm != "" && ddlTblLnm !="undefined"){
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdllist.do" />", param2);
	} */
		
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	
	
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	loadDetail();
// 	loadDetailCol();
	
	loadDetailScript();
	
	//$( "#layer_div" ).show();
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.DDLTBL.LST'/>", Align:"Center"}
						/* No.|DBMS명|DB스키마명|DDL테이블ID|DDL테이블물리명|DDL테이블논리명|테이블스페이스ID|물리모델테이블명|스크립트정보|요청번호|요청일련번호|파티션여부|암호화여부|설명|버전|등록유형코드|테이블변경코드|최초요청일시|최초요청자명|요청일시|요청자명|승인일시|승인자명 */
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:200,   SaveName:"dbConnTrgLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:200,   SaveName:"dbSchLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"ddlTblId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:250,   SaveName:"ddlTblPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:250,   SaveName:"ddlTblLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"tblSpacId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:250,   SaveName:"pdmTblLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"scrtInfo",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						
						{Type:"Combo",   Width:100,   SaveName:"partTblYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"encYn",	 	Align:"Center", Edit:0, Hidden:0},

						{Type:"Text",   Width:250,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"tblChgTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("encYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"});
		SetColProperty("partTblYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"});

        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
       // FitColWidth();  
        SetSheetHeight("250");
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
			
			grid_sub_ddltblchange.RemoveAll();
			grid_sub_ddltblcollist.RemoveAll();
			grid_sub_ddltblidxcol.RemoveAll();
			grid_sub_ddltblcolchange.RemoveAll();
			loadDetail();
			loadDetailCol();
// 			loadDetailScript();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdllist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'DDL테이블조회'});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}



//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		initsubgrid_ddltblcollist();
					
		initsubgrid_ddltblidxcol();   		
				
		initsubgrid_ddltblchange();
				
		initsubgrid_ddltblcolchange();
				
		//initsubgrid_ddlidxcolchange();
		
		<c:if test="${ pdmrelyn == 'Y'}">
		initsubgrid_ddltblcollist();
		initsubgrid_ddltblrelchange();
		</c:if>
		
		
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
	var ddlTbl = "&ddlTblId="+grid_sheet.GetCellValue(row, "ddlTblId");
	ddlTbl += "&rqstNo="+param.rqstNo;
   
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = 'DDL 테이블명 : ' + param.ddlTblLnm;
	$('#ddl_sel_title').html(tmphtml);
	
	initDtlGrids();		
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(ddlTbl);
// 	var param1 = "";
	
// 	param1 += "ddlTblId=" + param.ddlTblId;
// 	param1 += "&scrnDcd=" + "${search.scrnDcd}";
		
	//alert(param1); 
	
	//DDL 스크립트 
	loadDetailScript(ddlTbl);
	
}

//상세정보호출
function loadDetail(param) {
	
	
	$('div#detailInfo1').load('<c:url value="/meta/ddl/ajaxgrid/ddltblinfo_dtl.do"/>', param, function(){
		if(!isBlankStr(param)){
			
			grid_sub_ddltblchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblchange_dtl.do" />', param);
			grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />', param);
			grid_sub_ddltblidxcol.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblidxcol_dtl.do" />', param);
			grid_sub_ddltblcolchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcolchange_dtl.do" />', param);
// 			grid_sub_ddlidxcolchange.DoSearch('<c:url value="/meta/ddl/getDdlIdxColChange.do" />', param);
			
			<c:if test="${ pdmrelyn == 'Y'}">
			grid_sub_ddltblrellist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblrellist_dtl.do" />', param);
			grid_sub_ddltblrelchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblrelchange_dtl.do" />', param);
			</c:if>
		}
		//$('#tabs').show();
	});
}

//상세정보호출
function loadDetailScript(param) {
	
	
	$('div#detailInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){
				
			    
	});
}



function showDDL() {
	
	var saveJson = grid_sheet.GetSaveJson(0);
	
	if(saveJson.data.length == 0) {
		
		//DDL체크박스에 체크하세요.
		showMsgBox("ERR", "DDL체크박스에 체크하세요.");   
		return;
	}
		
	var param = "?scrnDcd=WAM";  
	
	var url = '<c:url value="/meta/ddl/popup/ddlscript_pop.do"/>';
	OpenModal(url + param, "DdlScript", 800, 500);
}


function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
		if(!isBlankStr("${search.ddlTblId}")) {
			//선택한 상세정보를 가져온다...
			var param =  grid_sheet.GetRowJson(1);
			var ddlTblId = "&ddlTblId="+grid_sheet.GetCellValue(1, "ddlTblId");

			//선택한 그리드의 row 내용을 보여준다.....
			var tmphtml = 'DDL 테이블명 : ' + param.ddlTblLnm;
			$('#ddl_sel_title').html(tmphtml);
			
			initDtlGrids();
			
			//메뉴ID를 토대로 조회한다....
			loadDetail(ddlTblId);
			
			//DDL 스크립트 
//		 	loadDetailScript(param1);
			//$("#frmInputDdlScript #ddlscript").val(param.scrtInfo);
		} else {
			loadDetail();
		}
		
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DDL.TBL.INQ" /></div> <!-- DDL 테이블조회 -->
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
							<td><select class = "wd100" id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select class = "wd100" id="dbSchId" class="" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select></td>
							<th scope="row"><label for="subjId"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
							<td><select id="subjId" class="" name="subjId">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.subjLnm}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select></td>
						</tr>
						<tr>
							<th scope="row"><label for="ddlTblLnm"><s:message code="TBL.NM" /></label></th> <!-- 테이블명 -->
							<td><input type="text" id="ddlTblLnm" name="ddlTblLnm" class="wd200" /></td>
							<th scope="row"><label for="ddlColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 --></label></th>
							<td><input type="text" id="ddlColLnm" name="ddlColLnm" class="wd200" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="partTblYn"><s:message code="PARTITION.YN" /></label></th> <!-- 파티션여부 -->
							<td>
							    <select id="partTblYn" class="wd100" name="partTblYn">
							    <option value = ""><s:message code="CHC" /></option> <!-- 선택 -->
							    <option value = "N">아니요</option>
							    <option value = "Y">예</option>
							    </select>
							</td>
							<th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
							<td>
							   <select id="encYn" class="wd100" name="encYn">
							   <option value = ""><s:message code="CHC" /></option> <!-- 선택 -->
							    <option value = "N">아니요</option>
							    <option value = "Y">예</option>
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
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DDL.TBL.DTL.INFO" /></a></li> <!-- DDL테이블 상세정보 -->
	    <li><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
	    <li><a href="#tabs-5"><s:message code="IDEX.CLMN" /></a></li> <!-- 인덱스컬럼 -->
	    <li id="tab-3"><a href="#tabs-3" id="colinfo"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 -->
<!-- 	    <li><a href="#tabs-5" id="relinfo">관계정보</a></li> -->
	    <li><a href="#tabs-6"><s:message code="TBL.HSTR" /></a></li> <!-- 테이블이력 -->
	    <li><a href="#tabs-7"><s:message code="CLMN.HSTR" /></a></li> <!-- 컬럼이력 -->
<!--     <li><a href="#tabs-9">인덱스컬럼이력</a></li>-->	 
	    <li><a href="#tabs-10">DDL</a></li>
	    <c:if test="${ pdmrelyn == 'Y'}">
	    <li><a href="#tabs-4" ><s:message code="RLT.LST" /></a></li> <!-- display 보일때 tabs위치 제자리로 변경할것. --> <!-- 관계목록 -->
	    <li><a href="#tabs-8" ><s:message code="RLT.HSTR" /></a></li> <!-- 관계이력 -->
	    </c:if>
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddltblcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-5">
			<%@include file="ddltblidxcol_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailInfo2"></div>
	  </div>
	   <div id="tabs-6">
			<%@include file="ddltblchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-7">
			<%@include file="ddltblcolchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-10">
 			<div id="detailInfoScript"></div>
	  </div>
	  <c:if test="${ pdmrelyn == 'Y'}">
	  <div id="tabs-4">
			<%@include file="ddltblrellist_dtl.jsp" %>
	  </div>
	   <div id="tabs-8">
			<%@include file="ddltblrelchange_dtl.jsp" %>
	  </div>
	  </c:if>
	 </div>
</div>
</body>
</html>