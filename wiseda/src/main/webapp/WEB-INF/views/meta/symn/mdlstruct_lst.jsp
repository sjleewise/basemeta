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
<title><s:message code="MDEL.STRC.EXAM"/></title> <!-- 모델 구조 검사 -->

<script type="text/javascript">


$(document).ready(function() {

	 
        $("#btnTreeNew").hide();
        $("#btnSave").hide();
        $("#btnDelete").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 상세엑셀내리기 Event Bind
        $("#btnExcelDownSub").click( function(){ doAction("Down2ExcelSub"); } );
        
        $( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
	
      	
    	//주제영역 검색 팝업 호출
    	$("#subjSearchPop").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", 800, 600, param);
//     		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
//     		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
        });
		
});

//엔터키 조회 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {

	initGrid();

});

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
	
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #srcObjId").val(retjson.subjId);
	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
		var headers = [
						{Text:"<s:message code='META.HEADER.MDLSTRUCT.LST.1'/>"}
					,{Text:"<s:message code='META.HEADER.MDLSTRUCT.LST.2'/>", Align:"Center"}
						]
					;
					//NO.|소스|소스|소스|소스|소스|소스|비교|비교|비교|비교|비교|타겟|타겟|타겟|타겟|타겟|타겟|타겟
					//NO.|SRC_OBJ_ID|모델|상위주제영역|주제영역|엔티티명|테이블명|동일비율(소스)|컬럼개수(소스)|동일컬럼수|컬럼개수(타겟)|동일비율(타겟)|TGT_OBJ_ID|모델|상위주제영역|주제영역|엔티티명|테이블명
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"srcObjId",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"srcDatemodelNm",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"srcPrimarySubjNm",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"srcSubjNm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"srcTblKnm",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:150,  SaveName:"srcTblEnm",		Align:"Left", Edit:0},	
						{Type:"Text",   Width:80,  SaveName:"srcColCnt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"srcSameRt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"sameColCnt",		Align:"Right", Edit:0},	
						{Type:"Text",   Width:80,  SaveName:"tgtColCnt",		Align:"Right", Edit:0}, 
						{Type:"Text",   Width:80,  SaveName:"tgtSameRt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"tgtObjId",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"tgtDatemodelNm",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"tgtPrimarySubjNm",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"tgtSubjNm",		Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"tgtTblKnm",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:150,  SaveName:"tgtTblEnm",		Align:"Left", Edit:0}
					];
			
        InitColumns(cols);
        
   
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
	    	grid_sheet.DoSearch("<c:url value="/meta/symn/getMdlStructuer.do" />", param);
			break;
			
		case "Down2Excel": //엑셀내려받기
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'모델구조검사'});
			break;

		case "Down2ExcelSub": //상세엑셀내려받기
			grid_sub.Down2Excel({HiddenColumn:1, Merge:1, FileName:'모델구조검사'});
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
	//var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml  = '<s:message code="SOUR" /> : ' + param.srcSubjNm + ' > '+param.srcTblKnm; //소스
	var tmphtml2 = ' -> <s:message code="TARG" /> : ' + param.tgtSubjNm + ' > '+param.tgtTblKnm; //타겟
	$('#dmn_sel_title').html(tmphtml + tmphtml2);
	//$('#dmn_sel_title2').html(tmphtml2);
	//var param1 = "dmnId="+param.dmnId; 
	var param1 = "srcObjId="+param.srcObjId+"&tgtObjId="+param.tgtObjId;
	//tabs에 데이터 상세 
	grid_sub.DoSearch('<c:url value="/meta/symn/ajaxgrid/getMdlStruct_dtl.do"/>',param1);
	
}

</script>
</head>
<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="MDEL.STRC.EXAM"/></div><!-- 모델 구조 검사 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MDEL.STRC'/>"> <!-- 모델 구조 -->
                   <caption><s:message code="MDEL.STRC.EXAM.INQ.FORM"/></caption><!-- 모델 구조 검사 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:5%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for=""><s:message code="SUBJ.TRRT" /></label></th> <!-- 주제영역 -->
                           		<td>
		                            <input type="hidden" id="srcObjId" name="srcObjId" readonly="readonly"/>
									<input type="text" class="wd60p" id="fullPath" name="fullPath" readonly="readonly"/>
								 	<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
								 	<button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
	                            </td>
                                <th scope="row"><label for="srcTblEnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                                <td><input type="text" id="srcTblEnm" name="srcTblEnm" class="wd200"/></td>
                                <th scope="row"><label for="colNm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                                <td><input type="text" id="colNm" name="colNm"/></td>
                                <th scope="row"><label for="crlRt"><s:message code="BASE.RT.PER"/></label> <!-- 기준비율(%) -->
                                <td>
                                <select id="crlRt" class="wd100" name="crlRt">
                                	<option value="100">100</option>
									<option value="90" selected>90</option>
									<option value="80">80</option>
									<option value="70">70</option>
									<option value="60">60</option>
									<option value="50">50</option>
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
<!-- <div class="selected_title_area"> -->
<%-- 	    <div class="selected_title" id="dmn_sel_title2"> <span></span></div> --%>
<!-- </div> -->
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->

<div style="clear:both; height:5px;"><span></span></div>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:message code="MDEL.STRC.DTL.INFO"/></a></li> <!-- 모델 구조 상세정보 -->
		<div style="float:right;">
			<button class="btn_excel_down" id="btnExcelDownSub" name="btnExcelDownSub"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->  
		</div>
	</ul>
			
	<div id="tabs-1">
		 <%@include file="mdlinit_dtl.jsp" %> 
	</div>
</div>
</div>

</body>
</html>