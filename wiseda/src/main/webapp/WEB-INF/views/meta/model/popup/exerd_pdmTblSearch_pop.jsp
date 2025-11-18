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
<title>ERWIN7 테이블 검색</title>

<script type="text/javascript">

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
	
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();
    }
    
    
    
    
  //폼 초기화 버튼 초기화...
	$('#popReset').button({
	       icons: {
	          primary: "ui-icon-power"
	        }
	}).click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmSearch]")[0].reset();
	});
                  
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //======================================================
    // 셀렉트 박스 초기화
    //======================================================
    // 시스템영역
    create_selectbox(sysareaJson, $("#sysAreaId"));
    $("#sysAreaId").change(function(){
    	$("#btnSearch").click();
    });
    
    
    
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
    
    $('#subjSearchPop').button({   //주제영역 검색팝업 이벤트
	    icons: {
	        primary: "ui-icon-search"
	      },
	      text: false, 
	      create: function (event, ui) {
//	     	  $(this).addClass('search_button');
			  $(this).css({
				  'width': '18px',
				  'height': '18px',
				  'vertical-align': 'bottom'
				  });
	    	  
	      }
	    }).click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
		//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
		//$('div#popSearch').dialog("open");
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
	    	
	    	param += "&lecyDcd=TO";  
	    	
			// var popwin = OpenModal(url+"?"+param, "subjR7Pop",  600, 400, "no");  
			
			openLayerPop(url, 750, 550, param);
			
// 			popwin.focus();
		
	}).parent().buttonset();
    
  //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    //setautoComplete($("#frmSearch #pdmTblLnm"), "PDMTBL");
    
});
//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	
	
	//그리드 초기화
	initGrid();
	// $(window).resize();
	//doAction("Search");
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
        
        var headtext = "<s:message code='META.HEADER.R9.PDMTBLSEARCH.POP'/>";
        //No.|상태|선택|요청구분|주제영역ID|주제영역|테이블ID|테이블(물리명)|테이블(논리명)|표준적용여부|담당사용자ID|설명|category_id|model_id|subjectArea_id|entity_id|category_name|model_name|subjectArea_name|entity_name|entity_name_physical|entity_name_logical

        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",    Align:"Center", Edit:1},
                    {Type:"Combo",    Width:50,   SaveName:"rqstDcd",     Align:"Center",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"subjId",      Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"fullPath",    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"exerdPdmTblId",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:80,   SaveName:"pdmTblId",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:120,  SaveName:"pdmTblPnm",   Align:"Left",   Edit:0}, 
                    {Type:"Text",     Width:120,  SaveName:"pdmTblLnm",   Align:"Left",   Edit:0},
                    {Type:"Combo",    Width:60,   SaveName:"stdAplYn"	,    Align:"Center",   Edit:0},
                    {Type:"Text",     Width:40,   SaveName:"crgUserId"	,    Align:"Left",   Edit:0},
//                     {Type:"Text",   Width:40,  SaveName:"crgUserNm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"objDescn",       Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"category_id",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"model_id",         Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"subjectArea_id",   Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"entity_id",        Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"category_name",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"model_name",       Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"subjectArea_name", Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"entity_name",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"entity_name_physical", Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"entity_name_logical",  Align:"Left",   Edit:0, Hidden:1},
                    
//                     {Type:"Text",   Width:40,  SaveName:"objVers"   ,    Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Combo",  Width:40,  SaveName:"regTypCd",      Align:"Center", Edit:0, Hidden:1},                        
//                     {Type:"Text",   Width:60,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"frsRqstUserId", Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"rqstDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
	    SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"아니요|예"});
	    SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs}); 
	     
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("subjId",1);
//         SetColHidden("pdmTblId",1);
        SetColHidden("objDescn",1);
      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
        SetColHidden("crgUserId",1);
        
        SetColHidden("crgUserId",1);
        
        FitColWidth();
        
//         SetExtendLastCol(1);    
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
        	
        	if($("#subjLnm").val() == ""){
        		//주제영역을 입력하세요.
        		showMsgBox("ERR","<s:message code="SUBJ.INPUT" />");  
        		return;
        	}
        	
        	var param = $('#frmSearch').serialize();
        	
        	if($("#rqstDcd").val() == "CU") {

//         		grid_sheet.DoSearch("<c:url value="/meta/model/getmart9tbllist.do" />", param);
        		grid_sheet.DoSearch("<c:url value="/meta/model/getexerdtbllist.do" />", param)
        	}else{
        		grid_sheet.DoSearch("<c:url value="/meta/model/getpdmtbllist.do" />", param);
        	}
        	

        	break;
       
            
        case "Apply":
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
//         	var retval = getibscheckjoin(grid_pop, "stwdId");
//         	alert(retval);

        	var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			//2. 데이
// 			alert(saveJson.Code); 처리대상 행이 없는 경우 리턴한다.
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			//if(saveJson.data.length == 0) return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	var param = "";
        	
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	}else{
        		param = $("#mstFrm", opener.document).serialize();	
        	}	
        	
        	var url = "";
        	
        	if($("#rqstDcd").val() == "CU"){
        		//url = "<c:url value="/meta/model/regMart2WaqPdmTbl.do" />";        		        		
        		
//         		url = "<c:url value="/meta/model/regMart9WaqPdmTbl.do" />";
        		url = "<c:url value="/meta/model/regExerd2WaqPdmTbl.do" />";
        	}else{
        		param += "&rqstDcd=DD";
    	        url = "<c:url value="/meta/model/regWam2WaqPdmTbl.do" />";        		
        	}
        	                	
			//alert(saveJson);
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {  
//	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//var fullPath = retjson.sysAreaLnm + ">" + retjson.fullPath;
	
	var fullPath = retjson.fullPath;
	
	var subjId = retjson.subjId;

	var arrFullPath = fullPath.split(">");

	var subjLvl = arrFullPath.length -1;

	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	
	if(${maxSubjLvl} != subjLvl){
		alert("주제영역을 "+ "${maxSubjLvl + 1}" +"단계로 선택해주세요.");
	}else{
		$("#frmSearch #subjLnm").val(fullPath);	
		$("#frmSearch #subjId").val(subjId);
	}	
	
} 
 

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
				if ("${search.popType}" == "I") {
					
					json2formmapping ($("#mstFrm", parent.document), res.resultVO);
		    		
		    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
		    		$("#mstFrm #bizDtlCd", parent.document).val(res.resultVO.bizInfo.bizDtlCd);
		    		
		    		if ($("#mstFrm #rqstStepCd", parent.document).val() == "S")  {
		    			$("#btnRegRqst", parent.document).show();
		    		}

		    		parent.doAction("Search");    	
				}else{
					
					json2formmapping ($("#mstFrm", opener.document), res.resultVO);
		    		
		    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
		    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
		    		
		    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
		    			$("#btnRegRqst", opener.document).show();
		    		}

					opener.doAction("Search");    	
				}

	    			
	    	} 
			
// 			opener.doAction("Search");
			
			break;
	
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var retjson = grid_sheet.GetRowJson(row);	
	
	//요청서용 팝업일 경우.....
	if (popRqst == 'Y') {
		//체크박스 선택/해제 토글 기능.....
		var cellchk = grid_sheet.GetCellValue(row, "ibsCheck");
		if(cellchk == '0') {
			grid_sheet.SetCellValue(row, "ibsCheck", 1);
		} else {
			grid_sheet.SetCellValue(row, "ibsCheck", 0);
		}
		
		return;
	}
	
	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.returnPdmtblPop(JSON.stringify(retjson));
	} else {
		opener.returnPdmtblPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
    return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;
    
    
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
	}
	
}

//============================================
// 트리형 시트에서 명칭 검색 (나중에 공통으로 처리...)
//============================================
function searchSubjInfo(subjnm) {
	if(subjnm) {
		alert("시스템명이 없습니다.\n시스템명을 입력 후 조회하십시요.");
		$('#txtGridSearchNm').focus();
		return ;
	}
	var findrow = 0;
	var firstfrow = 0;
	var cntfind = 0;
	var totrow = grid_sheet.TotalRows;
	grid_sheet.ShowTreeLevel(0,1); //Tree 모두 접기...
	//grid_sheet.ColFontColor("arrSysKnm") = grid_sheet.RgbColor(0,0,0);
	while(findrow != -1 && findrow < totrow) {
		findrow = grid_sheet.FindText("arrSysKnm", subjnm, findrow+1, 2, false);
		//alert(findrow);
		if(findrow > 0) {
			var prow = findrow;
			var plvl = grid_sheet.RowLevel(prow);
			//grid_sheet.CellFontColor(prow, "arrSysKnm") = grid_sheet.RgbColor(0,0,255);
			while (prow) {
				prow--;
				if (grid_sheet.RowLevel(prow) < plvl) {
					//grid_sheet.RowExpanded(prow) = true;
//						grid_sheet.CellFontColor(prow, "arrSysKnm") = grid_sheet.RgbColor(0,0,255);
					plvl = grid_sheet.RowLevel(prow);
				}
				if(plvl == 0) break;
			}
			cntfind++;
			if(cntfind == 1) firstfrow = findrow;
		}
	}
//		alert(cntfind);
	grid_sheet.SelectCell(firstfrow, "arrSysKnm");
	$('#txtGridSearchNm').focus();
}



</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="TBL.INQ" /></div> <!-- 테이블 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!--    		<div class="stab">
           	<div class="stab_108_over">요청서목록</div>
               <div class="stab_108"><a href="#">요청서목록</a></div>
               <ul class="bt02">
                   <li class="bt02_50"><a href="#">엑셀읽기</a></li>
                   <li class="bt02_50"><a href="#">엑셀저장</a></li>
               </ul>
           </div> -->
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 --> 
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        <input type="hidden" id="subjId" name="subjId" value=""/>
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:17%;" />
                   <col style="width:15%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr> 
                           <th scope="row" class="th_require"><label for="rqstDcd"><s:message code="DEMD.DSTC" /></label></th>  <!-- 요청구분 -->                       
						   <td>
                                <select id="rqstDcd"  name="rqstDcd">
								    <c:forEach var="code" items="${codeMap.rqstDcd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                           </td>     
                                
                           <th scope="row" class="th_require"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th>  <!-- 주제영역명 -->                       
						   <td>
                                <span class="input_file"> 
                                	<input type="text" name="subjLnm" id="subjLnm" style="width:80%;" />
                               		<button class="btnDelPop" >삭제</button>
	                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                           </td>                          
                       </tr>
                       <tr>                                  
                           <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM" /></label></th>   <!-- 테이블명 -->    
                           <td colspan=3>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" style="width:95%" value="${search.pdmTblPnm}"/>
                                </span>
                           </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.POP' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>