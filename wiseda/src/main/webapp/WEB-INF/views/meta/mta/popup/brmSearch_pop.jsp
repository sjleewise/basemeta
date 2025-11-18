<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title>업무분류체계 검색</title> <!-- 업무분류체계 검색 -->

<script type="text/javascript">
var loadhtml = '<div class="process_loading">' + 
				'<div class="loading_img"><img src="<c:url value="/img/loading.gif"/>" alt="<s:message code="TRTT" />"></div>' + /* 처리중 */
				'<div class="loading_txt"><img src="<c:url value="/img/loading_txt.gif"/>" alt="<s:message code="TRTT.ING" />"></div>' + /* 처리중입니다. */
				'</div>';
$(document).ready(function() {
// 	    event.preventDefault();  //브라우저 기본 이벤트 제거...
        //버튼 초기화...
    	$("#popReset").hide();
    	$("#popExcelDown").hide();

    	
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close, #btnCloseBottom").click(function() {
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        });

        $("#popSearch").click(function(){
                //doAction("BrmSearch");
            doAction("BrmTreeSearch"); 
        });

        //엔터키 입력 시 폼이  서브밋 되지 않도록 처리
        var submitAction = function(e){
            e.preventDefault();
            e.stopPropagation();
        }
        $('form').bind('submit',submitAction);
        
        $("input").keydown(function(){
//         	event.preventDefault();  //브라우저 기본 이벤트 제거...
            if(event.which ===13){
                doAction("Search");
            }
        });
        
        SboxSetLabelEvent();

        $('#brmId').change(function() {
			var url = '<c:url value="/meta/mta/popup/brmCodeData.do"/>';
			var param = {brmId : $(this).val()};

			$('#brmId2').html('<option value="">선택</option>');
			$('#brmId3').html('<option value="">선택</option>');
			$('#brmId2').val("");
			$('#brmId3').val("");

			ajax2Json(url, param, function(result) {
            	$.each(result, function(i, d) {
            		$('#brmId2').append('<option value="'+d.brmId+'">'+d.brmNm+'</option>');
                });
            });
            SboxSetLabelEvent();
        });
        $('#brmId2').change(function() {
			var url = '<c:url value="/meta/mta/popup/brmCodeData.do"/>';
			var param = {brmId : $(this).val()};

			$('#brmId3').html('<option value="">선택</option>');
			$('#brmId3').val("");
			
			ajax2Json(url, param, function(result) {
            	$.each(result, function(i, d) {
            		$('#brmId3').append('<option value="'+d.brmId+'">'+d.brmNm+'</option>');
                });
            });
            SboxSetLabelEvent();
        });
});

$(window).on('load',function() {
	//그리드 초기화 
	initApiBrmGrid();
	
	doAction("Search");  	
	
	//직접등록 
	//initBrmGrid();	
	
	initBrmTreeGrid();
	
});

$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
   	setibsheight($("#grid_01"));
});


function initApiBrmGrid()
{
    with(grid_api_brm){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.BRM.SEARCH.POP'/>";
		//업무분류체계ID|업무분류체계|업무분류체계명|상위주제영역ID
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Text",   Width:100,  SaveName:"brmId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150,  SaveName:"brmNm",   Align:"Left", Edit:0, Wrap:1},
                ];
                    
        InitColumns(cols);
        FitColWidth();
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_api_brm);    
    //===========================    
}





function initBrmGrid()
{
    with(grid_brm){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.BRM.SEARCH.POP'/>";
		//업무분류체계ID|업무분류체계|업무분류체계명|상위주제영역ID
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Text",   Width:100,  SaveName:"brmId",    	 Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150,  SaveName:"brmFullPath",  Align:"Left", Edit:0, Wrap:1},
                    {Type:"Text",   Width:100,  SaveName:"brmNm",        Align:"Left", Edit:0, Wrap:1,Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"upperBrmId",   Align:"Left", Edit:0, Wrap:1,Hidden:1}
                ];
                    
        InitColumns(cols);
        FitColWidth();
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_brm);    
    //===========================    
}


function initBrmTreeGrid()
{
    with(grid_brm){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.BRM.SEARCH.POP1'/>";
		//업무분류체계ID|업무분류체계|업무분류체계명|상위주제영역ID|BRM_LVL
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Text",   Width:100,  SaveName:"brmId",    	 Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"brmNm",        Align:"Left", Edit:0, Wrap:1, TreeCol:1}, 
                ];
                    
        InitColumns(cols);
        FitColWidth();
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_brm);    
    //===========================    
}


function doAction(sAction)
{
    switch(sAction)
    {
    	    
        case "Search":
			/*
           	if($("#keyword").val()==''||$("#keyword").val()==null){
        		showMsgBox("INF","<s:message code='MSG.INQ.COND.INPT'/>");
        		return ;
            }
            */
            
            //option : analyze(추천용어) or recommend(분류정보)
        	//var param = $("#frmSearch").serialize();

            //var keyword = "${search.keyword}";  

     		var param = "";
     		
     		grid_brm.DoSearch("<c:url value="/meta/mta/popup/getBrmInfoVoList.do" />", param);

        	break;
    		/* param += "option=recommend"; 
    		param += "&type=6";
    		param += "&se=C";
    		param += "&keyword=" + keyword; 
    		
        	grid_api_brm.DoSearch('<c:url value="/request/brmList.do" />', param); */

        	break;
        	
        case "BrmSearch":
        	
			if($("#brmFullPath").val() == "") {
				showMsgBox("ERR","검색어를 입력하세요.");				
				return;
			}
	    	
	        var param = "brmFullPath=" + $("#brmFullPath").val();
	
	    	grid_brm.DoSearch('<c:url value="/meta/stnd/api/request/getBrmRqstList.do" />', param);
	    	
	        break;
    	
        case "BrmTreeSearch":
        	/*
        	if($("#brmId").val() == ""){
				showMsgBox("ERR","분야을 입력하세요.");        		
        		return;
        	}*/
            if(isBlankStr($('#brmId3').val())) {
            	showMsgBox("ERR","<s:message code='META.INST.MESSAGE3'/>");
                return false;
            }        	
			        	
	        var param = "";
	        
	        param += "brmId=" + $("#brmId3").val(); 
	        
	    	grid_brm.DoSearch('<c:url value="/meta/stnd/api/request/getBrmRqstList.do" />', param);
	    	
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
function grid_api_brm_OnDblClick(row, col, value, cellx, celly) {
    
	//if(row < 1) return;
	
	var retjson = grid_brm.GetRowJson(row);

	var brmId       = retjson.brmId;
	var brmNm  = retjson.brmNm;
	alert(brmId);
	alert(brmNm);
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		//parent.returnBrmInfoPop(JSON.stringify(retjson));
		alert("!");
		parent.document.getElementById("subjNm").value = recommKeyword;
		parent.closeLayerPop();
	} else {
		alert("@");
		opener.returnBrmInfoPop(brmFullPath, brmId, upperBrmId); 
		window.close();
	}
	
	//팝업창 닫기 버튼 클릭....
	//$(".pop_tit_close").click();
}

function grid_api_brm_OnClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
}

function grid_api_brm_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_brm_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var retjson = grid_brm.GetRowJson(row);

	var brmId       = retjson.brmId;
	var brmNm  = retjson.brmNm;

	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		//parent.returnBrmInfoPop(JSON.stringify(retjson));

		parent.document.getElementById("subjNm").value = brmNm;
		parent.closeLayerPop();
	} else {
		opener.returnBrmInfoPop(brmFullPath, brmId, upperBrmId); 
		window.close();
	}
	
	//팝업창 닫기 버튼 클릭....
	//$(".pop_tit_close").click();
}

function grid_brm_OnSearchEnd(code, message, stCode, stMsg) {
	
	grid_brm.ShowTreeLevel(0, 1);
	
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

</script>

</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">업무분류체계 검색</div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
</div>
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
	<!-- 검색조건 입력폼 -->
	<div id="search_div">
        
        <form id="frmSearch" name="frmSearch" method="post">
            <input type="hidden" name="option" id="option" value="${search.option}">
            <input type="hidden" name="type" id="type" value="${search.type}">
            <input type="hidden" name="se" id="se" value="C">   <!-- 로컬중앙구분코드 -->
            
        </form>
               
	</div>
	
	<div class="stit">자동추천</div>
	
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01" style="display:none;">
	     <script type="text/javascript">createIBSheet("grid_api_brm", "100%", "210px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	<%-- 
	<div style="clear:both; height:5px;"><span></span></div>
	
	 --%>
	 
         
<%--      <!-- 버튼 -->    
     <div style="clear:both; height:5px;"><span></span></div>     
     <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
	 <div style="clear:both; height:5px;"><span></span></div>
	 <!-- 버튼 --> --%>
	
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_brm", "100%", "450px");</script>             
	</div>
	<!-- 그리드 입력 입력 -->
	
     <div style="clear:both; height:10px;"><span></span></div>     
	<div id="" style="text-align: center;">
    	<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>           
    </div>
	
	
	</div>
</body>
</html>