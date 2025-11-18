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
<title>DDL 파티션 검색</title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
var popRqst = "${search.popRqst}";
var rqstNo = "${search.rqstNo}";
var sysareaJson = ${codeMap.sysarea};	//시스템영역 코드 리스트 JSON...
var connTrgSchJsonBySubjSchMap = ${codeMap.connTrgSchJsonBySubjSchMap};

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
  
	// 시스템영역
	create_selectbox(sysareaJson, $("#sysAreaId"));
	
	$("#frmSearch #sysAreaId").change(function(){
		if($(this).val() == ""){
			double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
		}else{
    		double_select_upcode(connTrgSchJsonBySubjSchMap, $("#frmSearch #dbConnTrgId"), $(this).val());
		}
	});
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

	
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
    
    $("#subjSearchPop").button({   // $('.btnSearchPop').button({ //주제영역 검색팝업 이벤트
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
		
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
			var popwin = OpenModal(url+"?"+param, "searchpop",  800, 600, "no");
		    	
			popwin.focus();
		
	}).parent().buttonset();    
    
    setautoComplete($("#frmSearch #ddlTblPnm"), "DDL");
    setautoComplete($("#frmSearch #fullPath"), "SUBJ");
    
    
});

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
// 	doAction("Search");

	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});
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
        
        var headtext  = "<s:message code='META.HEADER.DDLPART.POP'/>";
        //No.|상태|선택|시스템영역|주제영역|DDL테이블ID|DB접속대상명|DB스키마명|테이블물리명|테이블논리명|테이블스페이스|DDL파티션ID|파티션유형|파티션키|서브파티션유형|서브파티션키|설명
        	
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:100,  SaveName:"sysAreaId",    Align:"Center",   Edit:0},
					{Type:"Text",     Width:150,  SaveName:"fullPath",    Align:"Left",   Edit:0},
// 					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
// 					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
// 					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
// 					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
// 					{Type:"Combo",  Width:100,  SaveName:"vrfCd",	Align:"Center", Edit:0},
                    
                    {Type:"Text",   Width:60,   SaveName:"ddlTblId",	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm"	,    Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",   Width:120,  SaveName:"dbSchPnm",    Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",   Width:140, SaveName:"ddlTblPnm",     Align:"Left",   Edit:0, KeyField:1}, 
                    {Type:"Text",   Width:100,  SaveName:"ddlTblLnm", 	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"tblSpacPnm"	,    Align:"Left",   Edit:0, Hidden:1, KeyField:0},
                    
                    {Type:"Text",   Width:40,  SaveName:"ddlPartId", 	 Align:"Left",   Edit:0, Hidden:1},

                    {Type:"Combo",   Width:60,  SaveName:"partTypCd", 	 Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",   Width:120,  SaveName:"partKey"	,    Align:"Left",   Edit:0, KeyField:0},
                    {Type:"Combo",   Width:60,  SaveName:"subPartTypCd", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"subPartKey"	,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0, Hidden:1},
                    
//                     {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
// 					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
// 					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
//                     {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0}, 
// 					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("partTypCd", ${codeMap.partTypCdibs});
		SetColProperty("subPartTypCd", ${codeMap.partTypCdibs});
		SetColProperty("aplReqTypCd", 	${codeMap.aplReqTypCdibs});
		SetColProperty("sysAreaId", 	${codeMap.sysareaibs});

//         InitComboNoMatchText(1, "");
        
        // FitColWidth();
        
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
        	
//            	if($("#frmSearch #fullPath").val() == "") {
//         		showMsgBox("ERR","주제영역을 입력하세요.");
//         		return;
//         	}
           	
        	/* if ( isBlankStr($("#frmSearch #ddlTblPnm").val(), 'O')&&isBlankStr($("#frmSearch #dbSchId").val(), 'O')) {
           		showMsgBox("INF", "검색조건이 없습니다.<br>검색조건 입력 후 조회하십시요.");
           		return;
           	} */
        	
        	var param = $('#frmSearch').serialize();
        	    param += "&ddlTrgDcd="+"${search.ddlTrgDcd}";
        	grid_sheet.DoSearch("<c:url value="/meta/ddl/getddlpartlist.do" />", param);
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
 	        	param += "&rqstDcd=CU";
 	        	param += "&ddlTrgDcd="+"${search.ddlTrgDcd}"; //개발
        	var url = "<c:url value="/meta/ddl/regWam2WaqDdlPart.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
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
 	        	param += "&ddlTrgDcd="+"${search.ddlTrgDcd}"; //개발
//  	        	param += "&ddlTrgDcd=D"; //개발단계
 	        	var url = "<c:url value="/meta/ddl/regWam2WaqDdlPart.do" />";
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
    //alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmSearch #fullPath").val(retjson.fullPath);
	
	var subjId = retjson.subjId; 	  
	
// 	//개발스키마 조회
// 	getDevDbSchList(subjId);
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
		parent.returnDdlTblPop(JSON.stringify(retjson));
	//	parent.returnDbSchemaPop(JSON.stringify(retjson));
	} else {
		opener.returnDdlTblPop(JSON.stringify(retjson));
		//opener.returnDbSchemaPop(JSON.stringify(retjson));
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


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
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
				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("STWD");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("Search");    		
	    	} 
			
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

</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">파티션 검색</div>
    <div class="pop_tit_close"><a>창닫기</a></div>
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>
                   		<tr>
						<th scope="row"><label for="sysAreaId">시스템영역명</label></th>
                           <td colspan="5">
                               <select id="sysAreaId" class="" name="sysAreaId">
                                       <option value="">전체</option>
                                </select>
                           </td>
					    </tr>
                       <tr>                               
                   			<th scope="row"  class=""><label for="fullPath">주제영역명</label></th>
                            <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="fullPath" id="fullPath" value="" style="width:85%" />
                                </span>
                                <button class="btnDelPop" >삭제</button>
		                        <button class="btnSearchPop" id="subjSearchPop">검색</button
                            </td>
                            
                   		</tr>            
                       <tr>                               
                            <th scope="row"><label for="dbConnTrgId">DBMS/스키마명</label></th>
							<td ><select class = "wd120" id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value="">선택</option>
					            </select>
					            <select class = "wd120" id="dbSchId" class="" name="dbSchId">
					             <option value="">선택</option>
					             </select>
					        </td>
                     
                     
                           <th scope="row"><label for="ddlTblPnm">DDL테이블명</label></th>
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlTblPnm" id="ddlTblPnm" value="${search.ddlTblPnm}"/>
                                </span>
                            </td>
                           
                            
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        	<div class="tb_comment"><s:message  code='ETC.AUTO.POP' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
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