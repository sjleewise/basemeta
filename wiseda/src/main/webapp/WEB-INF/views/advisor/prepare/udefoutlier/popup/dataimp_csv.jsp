<!DOCTYPE html>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">

$(document).ready(function() {
	
	//엑셀 올리기 버튼 셋팅 및 클릭 이벤트 처리...
	$('#btnExcelUp').click(function(event){
		//event.preventDefault();  //브라우저 기본 이벤트 제거...
		doCsvAction('LoadText');
	});
	
	//엑셀 저장 버튼 초기화...
	$('#btnSaveExl').click(function(event){
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_csv.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code='ERR.CHKSAVE' />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code='CNF.SAVE' />";
		showMsgBox("CNF", message, 'Save');	
    	//doCsvAction("Save");  
	});
    
		
});

$(window).on('load',function() {
	initCsvGrid();
	
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	//setibsheight($("#grid_01"));
});


function initCsvGrid()
{
    
    with(grid_csv){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
   					{Text:"<s:message code='BDQ.HEADER.DATAIMP.RQST.DTL1'/>", Align:"Center"}
   				];
        
        //컬럼명|컬럼한글명|데이터타입

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [
					{Type:"Text",     Width:150, SaveName:"colPnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:150, SaveName:"colLnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:80,  SaveName:"dataType",  Align:"Left", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
      
        FitColWidth();  
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_csv);    
    //===========================
}

function doCsvAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	var param = $('#frmSearch').serialize();

        	grid_pop.DoSearch('<c:url value="/meta/stnd/getStndWordlist.do" />', param);
        	
        	break;
        	
        case "Apply": //적용버튼 액션...
			
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
        	var retval = getibscheckjoin(grid_pop, "stwdId");
        	alert(retval);

        	var saveJson = grid_pop.GetSaveJson(0);
			
			//2. 필수입력 누락인 경우
			if (saveJson.Code == "IBS010") return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	var param = $("#mstFrm", opener.document).serialize();
        	var url = "<c:url value="/meta/stnd/regWam2WaqStndword.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
			
        	break;
        	
 	 	case 'Save' : //엑셀 일괄 저장
 	 		var SaveJson = grid_pop.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
 	 	   	//데이터 사이즈 확인...
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
 	 	    var url = "<c:url value="/dq/criinfo/bizarea/regVrfBizAreaList.do"/>";
	 	 	if ("${search.popType}" == "I") {
	      		param = $("#mstFrm", parent.document).serialize();
	      	} else {
	      		param = $("#mstFrm", opener.document).serialize();
	      	}
 	 	    IBSpostJson2(url, SaveJson, param, ibscallback);
			break;        
 	    case "LoadText":  //엑셀업로드
 	    	grid_csv.LoadText({Mode:'NoHeader',Deli:",",FileExt:'csv'});
 	        
 	        break;
    }       
}

</script>

<!-- 검색조건 입력폼 -->
<div id="search_div">
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		         <!-- 조회버튼영역  -->
	<div id="divXlsBtn" style="text-align: left;">
		<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp"><s:message code="EXCL.UP" /></button><!-- 엑셀 올리기 -->
		<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl"><s:message code="STRG" /></button><!--저장-->


	</div>  
</div>       
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_csv", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

