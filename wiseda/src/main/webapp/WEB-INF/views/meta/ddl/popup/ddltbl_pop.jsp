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
<title>물리모델 테이블 검색</title>

<script type="text/javascript">

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
// 	$("#tabs").tabs();
	
                
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
	         if(checkDelIBS (grid_sheet, "<s:message code="REQ.NO.CHANG" />")) {
			 	doAction("Apply");
		     }
		 }).show();
        $("#popDelete").click(function(){ 
	         if(checkDelIBS (grid_sheet, "<s:message code="REQ.NO.DEL" />")) {
	 			doAction("DelRqst");
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
/*     create_selectbox(sysareaJson, $("#sysAreaId"));
    $("#sysAreaId").change(function(){
    	$("#btnSearch").click();
    }); */
    
    $('#btnSubjPop').click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
		//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
		//$('div#popSearch').dialog("open");
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
			var popwin = OpenModal(url+"?"+param, "searchpop",  1200, 600, "no");
			popwin.focus();
		
	});
    
    $("#btnDevDbmsPop").click(function(){
    	
    	if($("#subjLnm").val() == "") {
    		//주제영역을 입력하세요.
    		showMsgBox("ERR","<s:message code='SUBJ.INPUT' />");
    		return;
    	}
    	
    	var url = "<c:url value='/commons/damgmt/db/popup/devdbschema_pop.do' />";
    	
    	var param = $("#frmSearch").serialize();  
    	
    	var popwin = OpenModal(url+"?"+param, "searchpop",  600, 400, "no");
		popwin.focus();
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
    
    $("#rqstDcd").change(function(){
    	
    	grid_sheet.RemoveAll();
    });
    
});

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
	// doAction("Search");
});

EnterkeyProcess("Search");

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
        
        var headText = "";
        
        //headText += "No.|상태|선택|등록유형|주제영역ID|주제영역|DB_CONN_TRG_ID|DB_SCH_ID|DB명|스키마명|테이블ID|테이블(물리명)|테이블(논리명)|표준적용여부|담당사용자ID|모델승인일자|설명"; 
        
        //Num|Status|Select|Registration type|SubjectArea ID|SubjectArea|DBMS ID|SCHEMA ID|DBMS Name|Schema Name|PDM ID|Physical Name|Logical Name|Standard|Manager ID|Approval date|Description
        
        headText = "<s:message code='META.HEADER.DDLTBL.POP'/>";  
        //No.|상태|선택|등록유형|주제영역ID|주제영역|DB_CONN_TRG_ID|DB_SCH_ID|DB명|스키마명|테이블ID|테이블(물리명)|테이블(논리명)|표준적용여부|담당사용자ID|모델승인일자|설명
        
        var headers = [
	                    {Text: headText, Align:"Center"}   
	                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,   SaveName:"ibsSeq",         Align:"Center", Edit:0},
                    {Type:"Status",  Width:40,   SaveName:"ibsStatus",      Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50,   SaveName:"ibsCheck",       Align:"Center", Edit:1, Sort:0},
                    {Type:"Combo",   Width:70,   SaveName:"regTypCd",       Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"subjId"	,       Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"fullPath"	,   Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dbConnTrgId",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dbSchId"  ,      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dbConnTrgPnm",   Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dbSchPnm",       Align:"Left",   Edit:0},
                    {Type:"Text",    Width:80,   SaveName:"pdmTblId",   	Align:"Left",   Edit:0},
                    {Type:"Text",    Width:120,  SaveName:"pdmTblPnm",      Align:"Left",   Edit:0}, 
                    {Type:"Text",    Width:120,  SaveName:"pdmTblLnm", 	    Align:"Left",   Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"stdAplYn",       Align:"Center", Edit:0},
                    {Type:"Text",    Width:40,   SaveName:"crgUserId",      Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"aprvDtm",        Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",    Width:100,  SaveName:"objDescn",       Align:"Left",   Edit:0, Hidden:1},
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"아니요|예"});
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("subjId",1);
        SetColHidden("pdmTblId",1);
        SetColHidden("objDescn",1);
        
      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
        SetColHidden("crgUserId",1);
        
        
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
        	
        	if($("#subjLnm").val() == "") {
        		//주제영역을 입력하세요.
        		showMsgBox("ERR","<s:message code='SUBJ.INPUT' />");
        		return;
        	}
        	
			if($("#dbSchId").val() == "") {
        		
				//DB스키마명을 입력하세요.
        		showMsgBox("ERR","<s:message code='DBMS.SCH.INPUT' />");
        		return;
        	}
        	
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/getPdmTblListForDdl.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
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
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	
        	var url = "";
        	
        	param += "&rqstDcd="  + $("#rqstDcd").val();
        	param += "&dbSchId="  + $("#dbSchId").val();
//         	param += "&dbSchPnm=" + $("#dbSchPnm").val();
        	param += "&dbConnTrgPnm=" + $("#dbConnTrgPnm").val(); 
        	
        	url = "<c:url value="/meta/ddl/regWam2WaqDdlTbl.do" />";
        	
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	break;
 	      case "DelRqst": //삭제요청...
 	        	var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
 				
 				if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
 													   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
 													   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
 	        	var param = "";
 	        	if ("${search.popType}" == "I") {
 	        		param = $("#mstFrm", parent.document).serialize();
 	        	} else {
 	        		param = $("#mstFrm", opener.document).serialize();
 	        	}
 	        	param += "&rqstDcd=DD";
 	        	var url = "<c:url value="/meta/ddl/regWam2WaqDdlTbl.do" />";
 				IBSpostJson2(url, saveJson, param, ibscallback);
// 	 			showMsgBox("PRC", "<s:message code="REQ.PRC.LOAD" />");
 	        
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
	var subjId   = retjson.subjId;
		
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	$("#frmSearch #subjLnm").val(fullPath);
	$("#frmSearch #subjId").val(subjId); 
			
	$("#dbConnTrgPnm").val("");
	$("#dbSchPnm").val("");
	$("#dbSchId").val("");
	
	//개발 DBMS 조회 
	getDevDbSch(subjId);
}


function returnDbSchemaPop(ret){
	
	var retjson = jQuery.parseJSON(ret);
		
	$("#frmSearch #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmSearch #dbSchPnm").val(retjson.dbSchPnm);
	$("#frmSearch #dbSchId").val(retjson.dbSchId);
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
				//팝업닫기
// 				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("DMN");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("Search");    		
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


function getDevDbSch(subjId) {
	
	var url = "<c:url value="/commons/damgmt/db/getdevsubjdbschemalist.do" />";
	
	var param = "subjId=" + subjId;  
		
	getComboData(url, true, $("#dbSchId"), param);
}

//================================================
//소스DB스키마 조회
//================================================
function getComboData(url, bSel, obj, param) {
			
	url = url + "?" + param;
	
	var dbConnTrgP = "";
	//alert(url); 
	
	$.ajax({
		url: url,
		async: false,
		type: "POST",
		data: "",
		dataType: 'json',
		success: function (data) {
			
			if(data){
				 
				//alert(JSON.stringify(data) );
								
				obj.find("option").remove().end(); 
				 
				if(bSel){
				  obj.append("<option value=\"\"><s:message code="CHC" /></option>");	//선택					 
				}
				 
				$.each(data.DATA, function(i, map){
				 
				   var dbmsPnm = map.dbConnTrgPnm + "." + map.dbSchPnm;
				   dbConnTrgP = map.dbConnTrgPnm;
					
				   obj.append("<option value="+ map.dbSchId +">"+ dbmsPnm +"</option>");					   						   
				});	
								
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
			
			
		}
	});
	
	$("#frmSearch #dbConnTrgPnm").val(dbConnTrgP);
	
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
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	
        
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:30%;" />
                   <col style="width:15%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>
                   		<tr>                   			
                   			<th scope="row" class="th_require"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td colspan=3>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" style="width:80%;" value="${search.subjLnm}"/>
                                <input type="hidden" name="subjId" id="subjId" />
                                
                                <button class="btnDelPop" >삭제</button>
	                            <button class="btnSearchPop" id="btnSubjPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                            
                   		</tr>
                       <tr>                               
                            <th scope="row" class="th_require"><label for="rqstDcd"><s:message code="DEMD.DSTC" /></label></th>  <!-- 요청구분 -->
                            <td>
                                <select id="rqstDcd" name="rqstDcd" class="wd100">
                                    <c:forEach var="code" items="${codeMap.rqstDcd}" varStatus="status">
				                    <option value="${code.codeCd}">${code.codeLnm}</option>
				                    </c:forEach>			                
                                 </select>
                            </td>
                   			
                            <th scope="row" class="th_require"><label for="dbSchPnm"><s:message code="DEV.DB.MS.NM" /></th> <!-- 개발DBMS -->
                            <td>
                                <select name="dbSchId" id="dbSchId" style="width:80%;">
                                	
                                </select>
                                <!-- 
                                <span class="input_file">
                                <input type="text" name="dbConnTrgPnm" id="dbConnTrgPnm" style="width:35%"  />
                                <input type="text" name="dbSchPnm" id="dbSchPnm" style="width:40%"  />
                                <button class="btnSearchPop" id="btnDevDbmsPop">검색</button>
                                <input type="hidden" name="dbSchId" id="dbSchId"  />
                                </span>
                                 -->
                                 <input type="hidden" name="dbConnTrgPnm" id="dbConnTrgPnm" />
                            </td>
                            
                       </tr>
                       <tr>                                                          
                            <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM" /></label></th>  <!-- 테이블명 -->
                            <td colspan=3>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" style="width:95%;" value="${search.pdmTblPnm}"/>
                                </span>
                            </td>
                            
                       </tr>
                       
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
         <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        	<div class="tb_comment"><s:message  code='ETC.RQST.POP' /></div>
         </c:when>
         <c:otherwise>
        	<div class="tb_comment"><s:message  code='ETC.POP' /></div>
         </c:otherwise>
         </c:choose>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<c:choose>
         <c:when test="${search.popRqst == 'Y'}">
         <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:when>
         <c:otherwise>
         <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:otherwise>
         </c:choose>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
    
</div>
</body>
</html>