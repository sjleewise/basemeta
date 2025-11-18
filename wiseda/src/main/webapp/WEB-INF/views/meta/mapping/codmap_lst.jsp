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
<title><s:message code="CD.MAPG.DFNT.P.INQ2"/> </title> <!-- 코드 매핑정의서 조회 -->

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
// 	alert('window.load');
	initGrid();
	
	//도메인 유효값 그리드 초기화
	initsubgrid_dmnvalue();
	
	loadDetail();
	//$( "#layer_div" ).show();
});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
		var headers = [
						{Text:"<s:message code='META.HEADER.CODMAP.LST1'/>"}
						/* NO.|매핑구분|코드전환구분|코드매핑구분|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|응용담당자명|전환담당자명|승인일시|요청일시|최초요청일시|승인자명|요청자명|최초요청자명|colMapId|타겟도메인ID */
					,{Text:"<s:message code='META.HEADER.CODMAP.LST2'/>", Align:"Center"}
					/* NO.|매핑구분|코드전환구분|코드매핑구분|코드도메인명|코드값|코드값명|시스템명|업무명|DB명|테이블물리명|테이블논리명|컬럼물리명|컬럼논리명|코드|코드명|상위코드|상위코드명|응용담당자명|전환담당자명|승인일시|요청일시|최초요청일시|승인자명|요청자명|최초요청자명|colMapId|타겟도메인ID */
						]
					;
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",			Align:"Center", Edit:0},
						{Type:"Combo",  Width:70, 	SaveName:"mapDfType",		Align:"Left", Edit:0},
						{Type:"Combo",   Width:70,  SaveName:"cdCnvsType",			Align:"Left", Edit:0},
						{Type:"Combo",  Width:70,  SaveName:"cdMapType",		Align:"Left", Edit:0},
						
						{Type:"Text",   Width:100,  SaveName:"tgtDmnLnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:130,  SaveName:"tgtCdVal",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:130,  SaveName:"tgtCdValNm",		Align:"Left", Edit:0},


						{Type:"Text",   Width:100,  SaveName:"srcSysNm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcBizNm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcDbPnm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcTblPnm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcTblLnm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcColPnm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcColLnm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcCdVal",	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,  SaveName:"srcCdValNm",	Align:"Center", Edit:0, Hidden:0},				
						{Type:"Text",   Width:80,  SaveName:"srcUppCdVal",	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,  SaveName:"srcUppCdValNm",	Align:"Center", Edit:0, Hidden:0},				
					
						{Type:"Text",   Width:100,  SaveName:"appCrgpNm", 	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,  SaveName:"cnvsCrgpNm", 	 Align:"Center",   Edit:0},
						
						
						{Type:"Date",   Width:80,  SaveName:"aprvDtm",	 Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						{Type:"Date",   Width:80,  SaveName:"rqstDtm",	 Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						{Type:"Date",   Width:80,  SaveName:"frsRqstDtm",	 Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						
						{Type:"Text",   Width:80,  SaveName:"aprvUserNm",		Align:"Left", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:60,  	SaveName:"rqstUserNm",		Align:"Left", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"frsRqstUserNm",	Align:"Left", Edit:0,Hidden:1},
						
						{Type:"Text",   Width:60,  	SaveName:"colMapId",		Align:"Left", Edit:0,Hidden:1},
						{Type:"Text",   Width:60,  	SaveName:"tgtDmnId",		Align:"Left", Edit:0,Hidden:1}
					];
                    
        InitColumns(cols);
        
        //매핑구분, 이행구분 콤보코드로 작성 
          SetColProperty("mapDfType", ${codeMap.mapDfTypCdibs});
          SetColProperty("cdCnvsType", ${codeMap.cdCnvsTypeibs});
          SetColProperty("cdMapType", ${codeMap.cdMapTypeibs});
          
        //SetColHidden("rqstUserNm",1);

//         FitColWidth();  
        
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
		$('#dmn_sel_title').html('');
		var param = $('#frmSearch').serialize();
    	grid_sheet.DoSearch("<c:url value="/meta/mapping/getCodeMap.do" />", param);
		break;
	case "Down2Excel": //엑셀내려받기
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'코드매핑정의서조회'});
		break;
	}
	
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "tgtDmnId");
	//alert(dmnId);
	//var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="TARG.DMN.NM"/> : ' + param.tgtDmnLnm; /*타겟(TOBE)도메인명*/
	$('#dmn_sel_title').html(tmphtml);

	//컬럼목록 조회
 	$("form#frmSearch input[name=tgtDmnId]").val(grid_sheet.GetCellValue(row, "tgtDmnId"));
	
	
 	ibs2formmapping(row, $("form#frmInput"), grid_sheet);	
	$("form[name=frmInput] #frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
	$("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
	$("form[name=frmInput] #aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));
	
	loadDetail(dmnId);
	
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/stnd/ajaxgrid/dmninfo_dtl.do"/>', param, function(){
		
		if(param == null || param == "" || param =="undefined") {
			
		} else {
			grid_sub_dmnvalue.DoSearch('<c:url value="/meta/stnd/ajaxgrid/dmnvalue_dtl.do" />', param);
		}
	});
}


</script>


</head>

<body>
<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="CD.MAPG.DFNT.P.INQ2"/></div><!-- 코드 매핑정의서 조회 -->
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
                   <caption><s:message code="CD.MAPG.DFNT.P.INQ.INQ.FORM"/></caption> <!-- 코드 매핑정의서 조회 검색폼 -->
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
                            	<th scope="row"><label for="cdCnvsType"><s:message code="CD.CNVR.DSTC" /></label></th> <!-- 코드전환구분 -->
                           		<td>
		                        <select id="cdCnvsType" class="wd100" name="cdCnvsType">
		                        	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
		                        	 <c:forEach var="code" items="${codeMap.cdCnvsType}" varStatus="status">
									<option value="${code.codeCd}">${code.codeLnm} </option>	
									</c:forEach> 
		                        </select>
	                            </td>
                                <th scope="row"><label for="tgtDmnLnm"><s:message code="TARG.CD.DMN.NM.2"/></label></th> <!-- 타겟(TOBE) 코드도메인명 -->
                                <td><input type="text" id="tgtDmnLnm" name="tgtDmnLnm"/></td>
                                <th scope="row"><label for="tgtCdVal"><s:message code="TARG.VLD.VAL"/></label></th> <!-- 타겟(TOBE) 유효값 -->
                                <td><input type="text" id="tgtCdVal" name="tgtCdVal"/></td>
                                <th scope="row"><label for="tgtCdValNm"><s:message code="TARG.VLD.VAL.NM"/></label></th> <!-- 타겟(TOBE) 유효값명 -->
                                <td><input type="text" id="tgtCdValNm" name="tgtCdValNm"/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="cdMapType"><s:message code="CD.MAPG.DSTC" /></label></th> <!-- 코드매핑구분 -->
                           		<td>
		                        <select id="cdMapType" class="wd100" name="cdMapType">
		                        	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
		                        	 <c:forEach var="code" items="${codeMap.cdMapType}" varStatus="status">
									<option value="${code.codeCd}">${code.codeLnm} </option>	
									</c:forEach> 
		                        </select>
	                            </td>
                            	<th scope="row"><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.2"/></label></th> <!-- 소스(ASIS) DB명 -->
                                <td><input type="text" id="srcDbPnm" name="srcDbPnm"/></td>
                                <th scope="row"><label for="srcTblLnm"><s:message code="SOUR.TBL.NM.2"/></label></th> <!-- 소스(ASIS) 테이블명 -->
                                <td><input type="text" id="srcTblLnm" name="srcTblLnm"/></td>
                                <th scope="row"><label for="srcColLnm"><s:message code="SOUR.CLMN.NM.2"/></label></th> <!-- 소스(ASIS) 컬럼명 -->
                                <td><input type="text" id="srcColLnm" name="srcColLnm"/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="srcCdVal"><s:message code="SOUR.CD.VAL"/></label></th> <!-- 소스(ASIS) 코드값 -->
                                <td><input type="text" id="srcCdVal" name="srcCdVal"/></td>
                                <th scope="row"><label for="srcCdValNm"><s:message code="SOUR.CD.NM"/></label></th> <!-- 소스(ASIS) 코드명 -->
                                <td><input type="text" id="srcCdValNm" name="srcCdValNm"/></td>
                                <th scope="row"><label for="frsRqstUserId"><s:message code="DMNT.NM" /></label></th> <!-- 요청자명 -->
                                <td colspan="3"><input type="text" id="frsRqstUserId" name="frsRqstUserId"/>
                                	<input type="hidden" id="tgtDmnId" name="tgtDmnId"/>
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
	     <%-- <li><a href="#tabs-3"><s:message code="CD.MAPG.DTL.INFO"/></a></li> --%> <!-- 코드매핑 상세정보 -->
	     <li><a href="#tabs-3"><s:message code="DTL.INFO"/></a></li> <!-- 영문판용(한글버젼시 위 주석 사용) -->
	     <li><a href="#tabs-1"><s:message code="TARG.DMN.INFO"/></a></li> <!-- 타겟도메인정보 -->
	     <li><a href="#tabs-2"><s:message code="TARG.DMN.VLD.VAL"/></a></li> <!-- 타겟도메인유효값 -->
	</ul>
	<div id="tabs-3">
			<%@include file="list/codmap_dtl_lst.jsp" %>
	</div>  
	<div id="tabs-1">
			<div id="detailInfo1"></div>
	</div>
	<div id="tabs-2">
			<%@include file="../stnd/dmnvalue_dtl.jsp" %>
	</div>  
</div>
</div>

</body>
</html>