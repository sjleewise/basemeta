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
<title><s:message code="VRFC.INQ"/></title> <!-- 검증룰 조회 -->

<script type="text/javascript">

// var connTrgSchJson = ${codeMap.devConnTrgSch} ;

var interval = "";

$(document).ready(function() {
	
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
	      //버튼 초기화...
    /* 	$("button[id^=popReset]").hide();
    	$("button[id^=popExcelDown]").hide();
    	$("#popExcelDown]").hide(); */
    	
    	
        //그리드 초기화 
         initGrid();
        // 조회 Event Bind
         

          $("#btnSearch").click(function(){ doAction("Search");  });
        
          $("#popSearch").click(function(){ doAction("Search2");  });
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
        
     

    	

       
        
        //임시 메뉴목록 등장 함수
		var val = $("#dbConnTrgId option:selected").val();
		var trgId = ${codeMap.devConnTrgSch} ;
		//$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == null) {
				$("#frmSearch2 #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
		
		
		$("#frmSearch2 #dbConnTrgId").change(function() {
			$("#frmSearch2 #dbSchId").find("option").remove().end();
			var val = $("#dbConnTrgId option:selected").val();

			$("#frmSearch2 #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
			
			for(i=0; i<trgId.length; i++) {
				if(trgId[i].upcodeCd == val && val!="") {
					$("#frmSearch2 #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
				}
			}
		});      

});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
 	initGrid2();
	$(window).resize();
	if("${code}"=="Y"){
    	$("#ui-id-2").trigger("click");
    }
	$("#dbConnTrgId option[value=${search.dbConnTrgId}]").attr("selected",true); 
	
	doAction("Search");
//  	doAction("Search2");
	
});




$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
});

function only(chk){ 

    var obj = document.getElementsByName("vrfcTyp"); 

    for(var i=0; i<obj.length; i++){ 

        if(obj[i] != chk){ 

            obj[i].checked = false; 

        } 

    } 

} 

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='VRFC.RULE.HEADER.POP'/>", Align:"Center"}
                       /* No.|검증룰ID|검증룰명|검증유형|검증룰|검증룰설명 */
                   ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",    Width:30,  SaveName:"ibsSeq",     Align:"Center", Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"vrfcId",     Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"vrfcNm",     Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Combo",  Width:40,  SaveName:"vrfcTyp",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:100, SaveName:"vrfcRule",   Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:50,  SaveName:"vrfcDescn",  Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:50,  SaveName:"shdJobId",  Align:"Left", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
// 	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
		SetColProperty("vrfcTyp", ${codeMap.vrfcTypibs});
        
        InitComboNoMatchText(1, "");

      
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

 function initGrid2()
{
    
    with(grid_sheet2){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        var headerText = "No.|코드룰ID|코드룰명|코드유형|코드분류ID|코드분류명|코드값|코드명 ";
        
        var headers = [
                       {Text: headerText, Align:"Center"} 
                   ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1}; 
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",    Width:80,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
					{Type:"Text",   Width:150,  SaveName:"cdRuleId",    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"cdRuleNm",    Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",   Width:150,  SaveName:"cdTypCd",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:150,  SaveName:"codeClsId",   Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:150,  SaveName:"codeClsVal",  Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"codeId",      Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"codeVal",     Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"shdJobId",     Align:"Left", Edit:0, Hidden:1} 
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
// 	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	     SetColProperty("cdTypCd", {ComboText: '공통코드|목록성코드', ComboCode: 'CC|LC'});
		SetColProperty("vrfcTyp", ${codeMap.vrfcTypibs});
        
        InitComboNoMatchText(1, "");

      
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet2);    
    //===========================
   
} 

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
//         	var state = $("#ui-id-2").parent().hasClass('ui-state-active');
//         	if(state==true){
        		
//                 //$("#frmSearch2 #dbConnTrgId").val("OBJ_00000084397");
                
//             	var param = $('#frmSearch2').serialize();
    			
//     			var url = "<c:url value="/dq/criinfo/anatrg/popup/cdRuleSelectlist.do"/>";
     			
//     			console.log("여기하나");
//     			console.log(param);
    			
//     			grid_sheet.DoSearch(url, param);
                
//                 break;
	        	
//         	}else{ 
//         		var btn = $('#frmSearch #vrfcTyp').serialize();
//         		var param = $('#frmSearch').serialize();
// 	        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/popup/vrfcSelectlist.do" />', param);
//         	}
//         	break;

//         	var state = $("#ui-id-2").parent().hasClass('ui-state-active');
        	
//         	if(state==true){
        		
        		var btn = $('#frmSearch #vrfcTyp').serialize();
        		var param = $('#frmSearch').serialize();
	        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/popup/vrfcSelectlist.do" />', param);
	        	
//         	}
        	break;
        	
        case "Search2":
        	var param = $('#frmSearch2').serialize();
        	
			if($("#dbConnTrgId").val() == ""){
				
				showMsgBox("ERR", "DBMS명을 입력하세요.");
				return;
			}
			var url = "<c:url value="/dq/criinfo/anatrg/popup/cdRuleSelectlist.do"/>";
 			
			grid_sheet2.DoSearch(url, param);
        	break;
		
        
    }       
}
$(".pop_tit_close").click();

//팝업 리턴값 제공
function returnPop(ret) {
// 	alert(JSON.stringify(ret));
	
	opener.frmSearch.subjLnm.value = ret;
	window.close();
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
   	
	var retjson = grid_sheet.GetRowJson(row);

	if("${code}"!="Y"){
		if ("${search.popType}" == "I") {
// 			alert(222);
 			parent.returnSubjPop(JSON.stringify(retjson), ${row});
		}
		//윈도우 형태의 팝업일 경우
		else {
			
			$("#cdClsId", opener.document).val();
		
			var vrfcNm = grid_sheet.GetCellValue(row, "vrfcNm");
			var vrfcId = grid_sheet.GetCellValue(row, "vrfcId");
				
			//윈도우오픈		
			$("#vrfcNm", opener.document).val(vrfcNm);
			$("#vrfcId", opener.document).val(vrfcId);
			window.close();
			
		} 
	}
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
}		 
				
	
	
/* 	var retjson ; 
	var sCls = "";
	
 	retjson = grid_sheet.GetRowJson(row);
 	sCls = "VR";
	
 	if("${code}"!="Y"){
	//iframe 형태의 팝업일 경우
		if ("${search.popType}" == "I") { 
			parent.returnSubjPop(JSON.stringify(retjson), ${row}, sCls);
			
 		} else {
 			opener.returnSubjPop(JSON.stringify(retjson), ${row}, sCls);
			window.close();
 		} 
 	}*/ 
	
		//팝업창 닫기 버튼 클릭....


function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
//     $("#hdnRow").val(row);
	if(row < 1) return;
	
	//선택한 상세정보를 가져온다...
// 	var param =  grid_sheet.GetRowJson(row);
// 	var subjLnm = "&subjLnm="+grid_sheet.GetCellValue(row, "subjLnm");
    
	//메뉴ID를 토대로 조회한다....
// 	loadDetail(subjLnm);
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}
function grid_sheet2_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var retjson = grid_sheet2.GetRowJson(row);

	var sCls = "CD";
	if("${code}"!="Y"){
		//iframe 형태의 팝업일 경우
		if ("${search.popType}" == "I") {
			parent.returnSubjPopCd(JSON.stringify(retjson), ${row});
			
		} else {
			var cdRuleNm = grid_sheet2.GetCellValue(row, "cdRuleNm");
			var cdRuleId = grid_sheet2.GetCellValue(row, "cdRuleId");
			var cdClsId = grid_sheet2.GetCellValue(row, "codeClsId");
			
			//윈도우오픈		
			$("#vrfcNm", opener.document).val(cdRuleNm);
			$("#vrfcId", opener.document).val(cdRuleId);
			$("#cdClsId", opener.document).val(cdClsId);
		}
	
		//팝업창 닫기 버튼 클릭....
		$(".pop_tit_close").click();	
	}
}

function grid_sheet2_OnClick(row, col, value, cellx, celly) {
    
//     $("#hdnRow").val(row);
	if(row < 1) return;
	
	//선택한 상세정보를 가져온다...
// 	var param =  grid_sheet.GetRowJson(row);
// 	var subjLnm = "&subjLnm="+grid_sheet.GetCellValue(row, "subjLnm");
    
	//메뉴ID를 토대로 조회한다....
// 	loadDetail(subjLnm);
}

function grid_sheet2_OnSearchEnd(code, message, stCode, stMsg) {
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
	    <div class="pop_tit_txt"><s:message code="VRFC.INQ"/></div> <!-- 부서 검색 -->
	    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
	    <!-- 팝업 타이틀 끝 -->
	
	    <!-- 팝업 내용 시작 -->
	    <div class="pop_content">
	    	<div id="tabs">
				  <ul>
				    <li><a href="#tabs-1"><s:message code="VRFC.RULE" /></a></li> <!-- 검증룰 -->
				    <li><a href="#tabs-2"><s:message code="CD" /></a></li> <!-- 코드 -->
				  </ul>
			<!-- 검색조건 입력폼 -->
			<div id="tabs-1">
			        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
			        <div style="clear:both; height:5px;"><span></span></div>
			        
			        <form id="frmSearch" name="frmSearch" method="post">
			            <fieldset>
			            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
			            <div class="tb_basic2">
			                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메뉴조회">
			                   <caption><s:message code="INQ.COND2" /></caption> <!-- 검색조건 -->
			                   <colgroup>
			                   <col style="width:10%;" />
			                   <col style="width:40%;" />
			                   <col style="width:20%;" />
			                   <col style="width:30%;" />
			                  </colgroup>
			                   
			                   <tbody>
								   <tr>
								   		<th scope="row" class=""><label for="vrfcTyp"><s:message code="VRFC.TYP" /></label></th> <!-- 검증유형 -->
			                            <td><!-- <input type="text" name="vrfcTyp" id="vrfcTyp" /> -->
			                            		   
		                                     
								                	<c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status" >
								                		<c:if test="${code.codeLnm!='코드'}">
								                    		<input type="checkbox"  id="vrfcTyp" name="vrfcTyp" value="${code.codeCd}" onclick="only(this);" >${code.codeLnm}</button>
<%-- 								                    		<button type="checkbox"  id="vrfcTyp" name="vrfcTyp" value="${code.codeCd}" class="btnSearchPop" >${code.codeLnm}</button> --%>
								                    	</c:if>	
								                    </c:forEach>
								                    
					
								                    
								                    
			                              <%--   </select>  --%>
			                   
			                             </td>
			                            
								   		<th scope="row" class=""><label for="vrfcNm"><s:message code="VRFC.NM" /></label></th> <!-- 검증룰명 -->
			                            <td><input type="text" name="vrfcNm" id="vrfcNm"/></td>
			                       </tr>
			                   </tbody>
			                 </table>   
			            </div>
			            </fieldset>
			            
			<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
			        </form>
					<div style="clear:both; height:10px;"><span></span></div>
			        
			         <!-- 조회버튼영역  -->
					<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" /> --%>
					<div><button class="btn_search" id="btnSearch" 	name="btnSearch">조회</button> <!--조회 -->
					</div>
			
			<div style="clear:both; height:5px;"><span></span></div>
			        
				<!-- 그리드 입력 입력 -->
				<div id="grid_01" class="grid_01">
				     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "350px");</script>            
				</div>
				<!-- 그리드 입력 입력 -->
			
			<div style="clear:both; height:5px;"><span></span></div>
			
				<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
				<div class="selected_title_area" style="display: none;" >
					    <div class="selected_title"><s:message code="SUBJ.TRRT.NM" /> : <span></span></div> <!-- 주제영역명 -->
				</div>
			
			<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
			<div style="clear:both; height:5px;"><span></span></div>
		</div>
		
<!-- 		201118 주석처리 -->
		<div id="tabs-2">
			        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
			        <div style="clear:both; height:5px;"><span></span></div>
			        
			        <form id="frmSearch2" name="frmSearch2" method="post">
			            <fieldset>
			            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
			            <div class="tb_basic2">
			                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메뉴조회">
			                   <caption><s:message code="INQ.COND2" /></caption> <!-- 검색조건 -->
			                   <colgroup>
			                   <col style="width:15%;" />
			                   <col style="width:35;" />
			                   <col style="width:15%;" />
			                   <col style="width:35;" />
			                  </colgroup>
			                   
			                   <tbody>
								   <tr>
								   		<th scope="row" class="th_require"><label for="dbConnTrgId">DBMS</label></th> <!-- 검증유형 -->
			                            <td colspan="3">
			                            	 <select id="dbConnTrgId"  name="dbConnTrgId">
											    <option value=""><s:message code="WHL" /></option><!--전체-->
											 </select>
								             <select id="dbSchId" class="" name="dbSchId" style="display:none;">
								             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								             </select>
			                             </td>			                            								   	
			                       </tr>
								   <tr>
								   		<th scope="row" class=""><label for="codeClsId">코드분류ID</label></th> <!-- 검증유형 -->
			                            <td>
			                            	<input type="text" name="codeClsId" value="">
			                             </td>			                            								   	
								   		<th scope="row" class=""><label for="codeClsVal">코드분류명</label></th> <!-- 검증유형 -->
			                            <td>
			                            	<input type="text" name="codeClsVal" value="">
			                             </td>			                            								   	
			                       </tr>
			                   </tbody>
			                 </table>   
			            </div>
			            </fieldset>
			            
			
			        </form>
					<div style="clear:both; height:10px;"><span></span></div>
			        
<!-- 			         조회버튼영역  -->
					<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
			
			<div style="clear:both; height:5px;"><span></span></div>
			        
<!-- 				그리드 입력 입력 -->
				<div id="grid_01" class="grid_01">
				     <script type="text/javascript">createIBSheet("grid_sheet2", "100%", "350px");</script>            
				</div>
<!-- 				그리드 입력 입력 -->
			
			<div style="clear:both; height:5px;"><span></span></div>
			
<!-- 				그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
				<div class="selected_title_area" style="display: none;" >
					    <div class="selected_title"><s:message code="SUBJ.TRRT.NM" /> : <span></span></div> 
				</div>
			
<!--  			그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록  -->
			<div style="clear:both; height:5px;"><span></span></div>
		</div>
		
	</div>		
</div>		
<%-- <%= application.getRealPath("/") %> --%>
</div>
</body>
</html>