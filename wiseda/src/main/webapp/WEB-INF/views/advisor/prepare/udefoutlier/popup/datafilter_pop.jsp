<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="CD.TBL.INQ.1"/></title><!--코드테이블 조회-->

<script type="text/javascript">

$(document).ready(function(){

	$("#popExcelDown").hide();
	$("#popApply").show();

	$("#popAdd").click(function(){

		doAction("Add");
	});

	$("#popDelete").click(function(){

		doAction("Delete");
	});

	$("#popApply").click(function(){

		doAction("Apply");
	});

	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){

    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
	
});

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
	doAction("Search");
});

$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));        
   	// grid_sheet.SetExtendLastCol(1);         
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='BDQ.HEADER.DATAFILTER.POP1'/>";
		//No.|상태|선택|조건|변수|연산자|값
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,  SaveName:"ibsSeq",    Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,  SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",  Align:"Center", Edit:1},
                    {Type:"Combo",    Width:100, SaveName:"cndCd",     Align:"Left",   Edit:1},
                    {Type:"Combo",    Width:150, SaveName:"colPnm",    Align:"Left",   Edit:1},                   
                    {Type:"Combo",    Width:100, SaveName:"oprCd",     Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100, SaveName:"cndVal",    Align:"Left",   Edit:1},
                    {Type:"Text",	  Width:0, 	 SaveName:"cndSno",	   Align:"Left",   Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

        SetColProperty("cndCd", {ComboCode:"AND|OR", ComboText:"AND|OR"});
        SetColProperty("oprCd", {ComboCode:">=|>|=|<|<=", ComboText:">=|>|=|<|<="});
        SetColProperty("colPnm", ${colList});
        
        FitColWidth();         
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function doAction(action){

	switch(action){

		case "Add":

			grid_sheet.DataInsert(-1);
			break;
		case "Delete":

			//삭제할 대상이 없습니다. 
			if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {

				delCheckIBS(grid_sheet);
				
				var bRtn = delCheckIBS2(grid_sheet);
			 	
			 	if(bRtn) {
					var url = "<c:url value='/advisor/prepare/udefoutlier/datafilter/delDaseCndDvCnd.do' />";
					
					$("#frmUod #mdlJsonInf", parent.document).val(parent.myDiagram.model.toJson());
	
					var param = $("#frmUod, #frmDataFilter", parent.document).serialize(); 
					
					ibsDeleteJson = grid_sheet.GetSaveJson({StdCol:'ibsCheck'});
					
					IBSpostJson2(url, ibsDeleteJson, param, ibscallback);
			 	}
			}

			break;
		case "Search":

			var url = "<c:url value='/advisor/prepare/udefoutlier/datafilter/getUodcDataFilterColList.do' />";
			
			//var param = $("#frmUod, #frmDataFilter", parent.document).serialize(); 
			
			var param = $("#frmUod, #frmDataFilter", opener.document).serialize();
			
			grid_sheet.DoSearch(url, param); 		
			
			break;	
		case "Apply":
			
			if(! opener.valSaveCheckDataFilter()) return; 
			
			var url = "<c:url value="/advisor/prepare/udefoutlier/datafilter/regDaseCndDvCnd.do"/>"; 

			//alert(parent.myDiagram.model.toJson());

			//$("#frmUod #mdlJsonInf", parent.document).val(parent.myDiagram.model.toJson());

			//var param = $("#frmUod, #frmDataFilter", parent.document).serialize(); 
			
			$("#frmUod #mdlJsonInf", opener.document).val(opener.myDiagram.model.toJson());
			
			var param = $("#frmUod, #frmDataFilter", opener.document).serialize();
		
			ibsSaveJson = grid_sheet.GetSaveJson(1);   
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
						
			break;			
	}
}

function postProcessIBS(res){

	var udfOtlDtcId = $("#udfOtlDtcId", opener.document).val();
	var creCompId   = $("#frmDataFilter #creCompId", opener.document).val();

	//alert(udfOtlDtcId);

	opener.getAjaxDataFilter(udfOtlDtcId, creCompId); 
}

//delCheckIBS() 동작 후 삭제 되지 않은 행이 남은 경우 boolean 값 반환
function delCheckIBS2(ibsobj) {
	var bRtn = false;
	var iRow = ibsobj.FindCheckedRow("ibsCheck");
	var iRows = iRow.split("|");
	
	for(var i=0; i<iRows.length;i++ ) {
		if ("U" == ibsobj.GetCellValue(iRows[i], "ibsStatus")) {
			bRtn = true;
		}
	}
	
	return bRtn;
}



</script>
</head>

<body>
	<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt">조건설정</div><!-- 조건설정 -->
		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->  
	</div>
	<!-- 팝업 타이틀 끝 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 조회버튼영역  -->
    <div class="divLstBtn" style="display: none;">	 
         <div class="bt03">
		    <button class="btn_search" id="popAdd"     name="popAdd">추가</button> <!-- 추가 -->
         	<button class="btn_reset"  id="popDelete"  name="popDelete" >삭제</button> <!-- 삭제 -->
		    <button class="btn_apply"  id="popApply"   name="popApply">적용</button> <!-- 적용 -->
		</div>			
    </div>	
    <!-- 조회버튼영역  end -->
    
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>     
	</div>
	<!-- 그리드 입력 입력 -->
</body>

</html>