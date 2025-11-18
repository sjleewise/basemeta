



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
<title><s:message code="CLMN.MAPG.DFNT.P.INQ.2"/> </title> <!-- 컬럼 매핑정의서 조회 -->

<script type="text/javascript">


$(document).ready(function() {
	 
		//$( "#tabs" ).tabs();
		
		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
		setautoComplete($("#frmSearch #dmnLnm"), "DMN");
	
}); 

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	initGrid();
	//$( "#layer_div" ).show();
});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
		var headers = [
						{Text:"<s:message code='META.HEADER.COLMAP.LST1'/>"}
						/* NO.|매핑구분|매핑정의서ID|컬럼매핑구분|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|매핑조건|컬럼참조DB명|컬럼참조테이블명|조인조건|응용담당자명|전환담당자명|승인일시|요청일시|최초요청일시|승인사용자명|요청사용자명|최초요청사용자명|TBL_ID|OBJ_ID|FULLPATH|타겟테이블ID */
					,{Text:"<s:message code='META.HEADER.COLMAP.LST2'/>", Align:"Center"}
					/* NO.|매핑구분|매핑정의서ID|컬럼매핑구분|DB명|테이블명(물리명)|테이블명(논리명)|POSITION|컬럼명(물리명)|컬럼명(논리명)|데이터타입|PK여부|Null여부|코드도메인명|시스템명|업무명|DB명|테이블명(물리명)|테이블명(논리명)|컬럼명(물리명)|컬럼명(논리명)|데이터타입|Default값|컬럼설명|매핑조건|컬럼참조DB명|컬럼참조테이블명|조인조건|응용담당자명|전환담당자명|승인일시|요청일시|최초요청일시|승인사용자명|요청사용자명|최초요청사용자명|TBL_ID|OBJ_ID|FULLPATH|타겟테이블ID */
						]
					;
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",			Align:"Center", Edit:0},
						{Type:"Combo",  Width:70, 	SaveName:"mapDfType",		Align:"Left", Edit:0},
						{Type:"Text",   Width:130,  SaveName:"mapDfId",			Align:"Left", Edit:0},
						{Type:"Combo",  Width:130,  SaveName:"colMapType",		Align:"Left", Edit:0},
						
						{Type:"Text",   Width:100,  SaveName:"tgtDbPnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:130,  SaveName:"tgtTblPnm",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:130,  SaveName:"tgtTblLnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:70,  	SaveName:"colOrd",			Align:"Right", Edit:0},
						
						{Type:"Text",  Width:100, 	SaveName:"tgtColPnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"tgtColLnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"tgtDataType",		Align:"Left", Edit:0},	
						{Type:"Combo",   Width:60,  	SaveName:"tgtPkYn",			Align:"Left", Edit:0}, 
						{Type:"Combo",   Width:60,  	SaveName:"tgtNonullYn",		Align:"Left", Edit:0},
						{Type:"Text",   Width:130,  SaveName:"tgtDmnLnm",		Align:"Left", Edit:0},
						
						{Type:"Text",  Width:100, SaveName:"srcSysNm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:100, SaveName:"srcBizNm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:100, SaveName:"srcDbPnm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcTblPnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcTblLnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcColPnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcColLnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcDataType",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"defltVal",			Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcDescn",			Align:"Left", Edit:0},
						
						{Type:"Text",   Width:80,  SaveName:"mapCndNm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"colRefDbPnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"colRefTblPnm",		Align:"Left", Edit:0},
						
						{Type:"Text",   Width:80,  SaveName:"jnCndNm",			Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"appCrgpNm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"cnvsCrgpNm",		Align:"Left", Edit:0},
						
						{Type:"Date",   Width:80,  SaveName:"aprvDtm",			Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						{Type:"Date",   Width:80,  SaveName:"rqstDtm",			Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						{Type:"Date",   Width:80,  SaveName:"frsRqstDtm",		Align:"right", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						
						{Type:"Text",   Width:80,  SaveName:"aprvUserNm",		Align:"Left", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"rqstUserNm",		Align:"right", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"frsRqstUserNm",	Align:"right", Edit:0,Hidden:1},
						
						{Type:"Text",   Width:80,  SaveName:"fullPath",	Align:"right", Edit:0,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"pdmTblId",	Align:"right", Edit:0,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"tgtColId",	Align:"right", Edit:0,Hidden:1}
						
					];
                    
        InitColumns(cols);
        
        //매핑구분, 이행구분 콤보코드로 작성 
         SetColProperty("mapDfType", ${codeMap.mapDfTypCdibs});
         SetColProperty("colMapType",${codeMap.colMapTypCdibs});
         SetColProperty("tgtPkYn", 	{ComboCode:"N|Y", 	ComboText:"|PK"});
         SetColProperty("tgtNonullYn", 	{ComboCode:"N|Y", 	ComboText:"|NOTNULL"}); 
        //SetColHidden("rqstUserNm",1);

        //FitColWidth();  
        
        //SetExtendLastCol(1);    
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
    	grid_sheet.DoSearch("<c:url value="/meta/mapping/getColMap.do" />", param);
		break;
	case "Down2Excel": //엑셀내려받기
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'컬럼매핑정의서조회'});
		break;
	}
	
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	
	if(row < 1) return;
	
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="MAPG.DFNT.P.ID" /> : ' + param.mapDfId + ' <s:message code="TARG.TBL.NM" />,  : '+param.tgtTblPnm;
	//매핑정의서ID, 타겟테이블명
	$('#dmn_sel_title').html(tmphtml);
	
	//ibs2formmapping() 함수를 통해  sheet의 데이터와 폼의 데이터 id일치하면 뿌려줌
	ibs2formmapping(row, $("form#frmInput"), grid_sheet);
	
	$("form[name=frmInput] #frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
	$("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
	$("form[name=frmInput] #aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));
	$("form[name=frmInput] #colMapType").val(grid_sheet.GetCellText(row, "colMapType"));
	
	//컬럼목록 조회
 	$("form#frmSearch input[name=tgtColId]").val(grid_sheet.GetCellValue(row, "tgtColId"));
//	alert($("form#frmSearch input[name=tgtColId]").val());
	doActionCol("Search"); 
	
	/* 
	$("form[name=frmInput] #fullPath").val(grid_sheet.GetCellText(row, "fullPath"));
	$("form[name=frmInput] #pdmTblPnm").val(grid_sheet.GetCellText(row, "tgtTblPnm"));
	$("form[name=frmInput] #pdmTblLnm").val(grid_sheet.GetCellText(row, "tgtTblLnm"));
	
	$("form[name=frmInput] #tgtDbPnm").val(grid_sheet.GetCellText(row, "tgtDbPnm"));
	$("form[name=frmInput] #srcDbPnm").val(grid_sheet.GetCellText(row, "srcDbPnm"));
	
	$("form[name=frmInput] #tblMapType").val(grid_sheet.GetCellText(row, "tblMapType"));
	$("form[name=frmInput] #sltSkpCndNm").val(grid_sheet.GetCellText(row, "sltSkpCndNm"));
	$("form[name=frmInput] #jnCndNm").val(grid_sheet.GetCellText(row, "jnCndNm"));
	$("form[name=frmInput] #preFlfDescn").val(grid_sheet.GetCellText(row, "preFlfDescn"));
	$("form[name=frmInput] #refDescn").val(grid_sheet.GetCellText(row, "refDescn"));
	
	$("form[name=frmInput] #appCrgpNm").val(grid_sheet.GetCellText(row, "appCrgpNm"));
	$("form[name=frmInput] #cnvsCrgpNm").val(grid_sheet.GetCellText(row, "cnvsCrgpNm"));
	
	
	$("form[name=frmInput] #frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
	$("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
	$("form[name=frmInput] #aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));
	$("form[name=frmInput] #frsRqstUserId").val(grid_sheet.GetCellText(row, "frsRqstUserId"));
	$("form[name=frmInput] #rqstUserId").val(grid_sheet.GetCellText(row, "rqstUserId"));
	$("form[name=frmInput] #aprvUserId").val(grid_sheet.GetCellText(row, "aprvUserId"));
	 */
	
	//var param1 = "dmnId="+param.dmnId+"&tgtDmnId="+param.tgtDmnId;
	
	//tabs에 데이터 상세 
	//grid_sub.DoSearch('<c:url value="/meta/symn/ajaxgrid/getDmnStruct_dtl.do"/>',param1);
	
}



</script>


</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="CLMN.MAPG.DFNT.P.INQ.2"/></div><!-- 컬럼 매핑정의서 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code="DMN.GRP" />"> <!-- 도메인그룹 -->
                   <caption><s:message code="TBL.MAPG.DFNT.P.INQ.INQ.FORM"/></caption> <!-- 테이블 매핑정의서 조회 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for=""><s:message code="MAPG.DSTC" /></label></th> <!-- 매핑구분 -->
                           		<td>
		                        <select id="mapDfType" class="wd100" name="mapDfType">
		                        	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
		                        	<c:forEach var="code" items="${codeMap.mapDfTypCd}" varStatus="status">
									<option value="${code.codeCd}">${code.codeLnm} </option>	
									</c:forEach>
		                        </select>
	                            </td>
                                <th scope="row"><label for="mapDfId"><s:message code="MAPG.DFNT.P.ID" /></label></th> <!-- 매핑정의서ID -->
                                <td><input type="text" id="mapDfId" name="mapDfId"/></td>
                                <th scope="row"><label for="appCrgpNm"><s:message code="APPLI.CHG.R.NM" /></label></th> <!-- 응용담당자명 -->
                                <td><input type="text" id="appCrgpNm" name="appCrgpNm"/></td>
                                <th scope="row"><label for="cnvsCrgpNm"><s:message code="CNVR.CHG.R.NM" /></label></th> <!-- 전환담당자명 -->
                                <td><input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm"/></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="tgtDbPnm"><s:message code="TARG.DB.NM.2"/></label></th> <!-- 타겟(TOBE) DB명 -->
                                <td><input type="text" id="tgtDbPnm" name="tgtDbPnm"/></td>
                                <th scope="row"><label for="tgtTblPnm"><s:message code="TARG.TBL.NM"/></label></th> <!-- 타겟(TOBE) 테이블명 -->
                                <td><input type="text" id="tgtTblPnm" name="tgtTblPnm"/></td>
                                <th scope="row"><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.2"/></label></th> <!-- 소스(ASIS) DB명 -->
                                <td><input type="text" id="srcDbPnm" name="srcDbPnm"/></td>
                                <th scope="row"><label for="srcTblLnm"><s:message code="SOUR.TBL.NM.2"/></label></th> <!-- 소스(ASIS) 테이블명 -->
                                <td><input type="text" id="srcTblLnm" name="srcTblLnm"/></td>
                                <input type="hidden" name="tgtColId" />
                            </tr>
                            <tr>
                            	<th scope="row"><label for=""><s:message code="CLMN.MAPG.DSTC"/></label></th> <!-- 컬럼 매핑구분 -->
                           		<td>
		                        <select id="colMapType" class="wd100" name="colMapType">
		                        	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
		                        	<c:forEach var="code" items="${codeMap.colMapTypCd}" varStatus="status">
									<option value="${code.codeCd}">${code.codeLnm} </option>	
									</c:forEach>
		                        </select>
	                            </td>
	                            <th scope="row"><label for="tgtColLnm"><s:message code="TARG.CLMN.NM"/></label></th> <!-- 타겟(TOBE) 컬럼명 -->
                                <td><input type="text" id="tgtColLnm" name="tgtColLnm"/></td>
                                <th scope="row"><label for="srcColLnm"><s:message code="SOUR.CLMN.NM.2"/></label></th> <!-- 소스(ASIS) 컬럼명 -->
                                <td colspan="3"><input type="text" id="srcColLnm" name="srcColLnm"/></td>
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
<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
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
		<%-- <li><a href="#tabs-1"><s:message code="CLMN.MAPG.DTL.INFO"/></a></li> --%> <!-- 컬럼매핑 상세정보 -->
		<li><a href="#tabs-1"><s:message code="DTL.INFO"/></a></li> <!-- 영문판용(한글버젼시 위 주석 사용) -->
		<!-- <li><a href="#tabs-2">컬럼 목록</a></li> -->
	    <li><a href="#tabs-3"><s:message code="PHYC.CLMN.INFO"/></a></li> <!-- 물리 컬럼 정보 -->
	</ul>
	<div id="tabs-1">
		 <%@include file="list/colmapdf_lst.jsp" %> 
	</div>
	<div id="tabs-2" style="display: none;">
		 <%@include file="list/tblmapcol_lst.jsp" %> 
	</div>
	<div id="tabs-3">
	 	<%@include file="list/tblmapcolinfo_lst.jsp" %> 
	</div>
</div>
</div>

</body>
</html>