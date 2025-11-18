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
<title><s:message code="ITEM.STRC.EXAM"/></title> <!-- 항목 구조 검사 -->

<script type="text/javascript">


$(document).ready(function() {
	 
	
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
        $( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
	
     	//그리드 하단 영역 
    	//$( "#tabs" ).tabs();
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dmnLnm"), "DMN");
	setautoComplete($("#frmSearch #srcItemKnm"), "SDITM");
	
});

//엔터키 조회 처리한다.
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
						{Text:"<s:message code='META.HEADER.ITMSTRUCT.LST.1'/>"}
					,{Text:"<s:message code='META.HEADER.ITMSTRUCT.LST.2'/>", Align:"Center"}
						]
					;
					//NO.|소스|소스|소스|소스|소스|소스|비교|비교|비교|비교|비교|타겟|타겟|타겟|타겟
					//NO.|SRC_ITEM_ID|항목한글명|항목영문명|도메인명|도메인그룹|인포타입|동일비율(소스)|단어개수(소스)|동일단어수|단어개수(타겟)|동일비율(타겟)|TGT_ITEM_ID|항목한글명|항목영문명
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcItemId",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"srcItemKnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"srcItemEnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"domainKnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"dmngrpNm",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:100,  SaveName:"infotypeNm",		Align:"Left", Edit:0},		
						{Type:"Text",   Width:100,  SaveName:"srcTrmCnt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcSameRt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"samTrmCnt",		Align:"Right", Edit:0},	
						{Type:"Text",   Width:100,  SaveName:"tgtTrmCnt",		Align:"Right", Edit:0}, 
						{Type:"Text",   Width:100,  SaveName:"tgtSameRt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"tgtItemId",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"tgtItemKnm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"tgtItemEnm",		Align:"Left", Edit:0}
					];
			
        InitColumns(cols);
        
     //   SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
        
        //SetColHidden("rqstUserNm",1);

        FitColWidth();  
        
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
    	grid_sheet.DoSearch("<c:url value="/meta/symn/getItmStructure.do" />", param);
		break;
	case "Down2Excel": //엑셀내려받기
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'용어구조검사'});
		break;
	}
	
}



function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
		
		if(row < 1) return;
		
		
		//선택한 셀이 Edit 가능한 경우는 리턴...
		if(grid_sheet.GetColEditable(col)) return;

		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(row);
		//alert(param);
	//	var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="ITEM.KRN.NM"/> : ' + param.srcItemKnm + ' , <s:message code="TARG.ITEM.KRN.NM"/> : '+param.tgtItemKnm;
		//항목한글명, 타겟항목한글명
		$('#dmn_sel_title').html(tmphtml);
		
		//var param1 = "dmnId="+param.dmnId;
		var param1 = "srcItemId="+param.srcItemId+"&tgtItemId="+param.tgtItemId;
		//tabs에 데이터 상세 
		grid_sub.DoSearch('<c:url value="/meta/symn/ajaxgrid/getItmStruct_dtl.do"/>',param1);
		
}

</script>
</head>
<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="ITEM.STRC.EXAM"/></div><!-- 항목 구조 검사 -->
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
                   <caption><s:message code="ITEM.STRC.EXAM.INQ.FORM"/></caption><!-- 항목 구조 검사 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
	                           <th scope="row" ><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
						         <td>
						        <select  id="stndAsrt" name="stndAsrt"  class="wd300">
						                <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
						        		  <option value="${code.codeCd}">${code.codeLnm}</option>
						        		</c:forEach> 
						        </select>
                                <th scope="row"><label for="srcItemKnm"><s:message code="STRD.TERMS.NM" /></label></th><!-- 표준용어명 -->
                                <td><input type="text" id="srcItemKnm" name="srcItemKnm" class="wd200"/></td>
                                <th scope="row"><label for="dmnLnm"><s:message code="DMN.NM.ADD.2" /></label></th> <!-- 도메인명 -->
                                <td><input type="text" id="dmnLnm" name="dmnLnm" class="wd200"/></td>
                                <th scope="row"><label for="crlRt"><s:message code="BASE.RT.PER"/></label></th> <!-- 기준비율(%) -->
                                <td>
                                <select id="crlRt" class="wd100" name="crlRt">
                                	<option value="100">100</option>
									<option value="90" selected>90</option>
									<option value="80">80</option>
									<option value="70">70</option>
									<!-- 
									<option value="60">60</option>
									<option value="50">50</option>
									-->
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
		<li><a href="#tabs-1"><s:message code="ITEM.STRC.DTL.INFO"/></a></li> <!-- 항목 구조 상세정보 -->
	</ul>
	<div id="tabs-1">
		 <%@include file="itminit_dtl.jsp" %> 
	</div>
</div>
</div>

</body>
</html>