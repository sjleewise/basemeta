<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title>유효값 검색</title>

<script type="text/javascript">

var dmnginfotpJson = ${codeMap.dmnginfotp} ;

var popRqst = "${search.popRqst}";
var uppDmnLnm = "${search.uppDmnLnm}";
var rqstNo = "${search.rqstNo}";
// var dmnLnm = "${search.dmnLnm}";

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  });
        
        if (popRqst == 'Y') {
	        // 적용 Event Bind
	        $("#popApply").click(function(){ 
	        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
					doAction("Apply");
		    	}
			}).show();
// 	        $("#popDelete").click(function(){ 
// 	        	if(checkDelIBS (grid_sheet, "<s:message code="REQ.NO.DEL" />")) {
// 					doAction("DelRqst");
// 		    	}
// 			}).show();
        }
        
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmSearch]")[0].reset();
		});
                
		// 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );
        
    	double_select(dmnginfotpJson, $("#dmngId"));
    	$('select', $("#dmngId").parent()).change(function(){
    		double_select(dmnginfotpJson, $(this));
    	});
    	
//     	//$( "#tabs" ).tabs();
    	
    	
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
        
        var bscLvl = parseInt("${bscLvl}");
        var selectBoxId = "${selectBoxId}";
        var firstSelectBoxId = selectBoxId.split("|");
        
     	//divID,  selectbox건수, selectbox ID
        create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", "전체");
        
    	double_select(dmnginfotpJson, $("#"+firstSelectBoxId[0]));
    	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
    		double_select(dmnginfotpJson, $(this));
    	});
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
        setautoComplete($("#frmSearch #dmnLnm"), "DMN");
    	
    	$("#frmSearch #dmnLnm").val(uppDmnLnm);
    	$("#frmSearch #rqstNo").val(rqstNo);
    	
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
	
	//alert("${search.dmnLnm}");
	if(!isBlankStr("${search.dmnLnm}")) {
		$("#frmSearch #dmnLnm").val("${search.dmnLnm}");
	}
// 	doAction('Search');
	
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
        
        var headtext = "<s:message code='META.HEADER.STND.CD.VAL.POP'/>";
        //No.|상태|선택|도메인ID|도메인논리명|도메인물리명|논리명기준구분|도메인그룹명|인포타입|데이터타입|데이터갈이|코드값ID|코드값|코드값명|적용일자|만료일자|상위도메인ID|주제영역ID|목록엔티티ID|목록엔티티물리명|목록엔티티논리명|목록어트리뷰트ID|목록어트리뷰트물리명|목록어트리뷰트논리명|코드값유형코드|코드값부여방식코드|표준용어자동생성여부|데이터형식|담당사용자ID|도메인출처구분|설명
		var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0, ColMerge:0},
		                {Type:"Status",   Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
		                {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Sort:0},
		                
						{Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:130,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:130,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:60,   SaveName:"lnmCriDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"dmngId",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"infotpId",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:130,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"cdValId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"cdVal",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Text",   Width:180,   SaveName:"cdValNm",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Text",  Width:120,  SaveName:"aplStrdDt",	Align:"Center", Edit:1, Format:"yyyy-MM-dd"},
						{Type:"Text",  Width:120,  SaveName:"aplEndDt",		Align:"Center", Edit:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:40,   SaveName:"uppDmnId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"subjId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstAttrId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstAttrPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstAttrLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"cdValTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:120,   SaveName:"cdValIvwCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:140,   SaveName:"sditmAutoCrtYn",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"dataFrm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"crgUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"dmnOrgDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:300,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},

					];
                    
        InitColumns(cols);
        
        //콤보코드 셋팅...
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
// 		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
		//SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
        
      	//히든 컬럼 설정...
     	SetColHidden("ibsStatus"	,1);      
     	SetColHidden("sditmAutoCrtYn"	,1);      
     	SetColHidden("cdValTypCd"	,1);      
     	SetColHidden("cdValIvwCd"	,1);      
     	SetColHidden("lstEntyLnm"	,1);      
      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
        
//       	FitColWidth();  
        
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
        	
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getDomainCdVal.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        	
        case "Apply": //적용버튼 액션...
			
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
        	//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&rqstDcd=CU";

        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.returnDmnCdvalPop(JSON.stringify(saveJson));
        	} else {
        		opener.returnDmnCdvalPop(JSON.stringify(saveJson));
        	}
        	
        	//팝업창 닫기 버튼 클릭....
        	$(".pop_tit_close").click();
        	
        
        	break;

       
        case "Down2Excel":  //엑셀내려받기
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}

//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function cdvalibscallback(res){
	var result = res.RESULT.CODE;
	if(result == 0) {
		doCdvalAction("Search");
		showMsgBox("INF", res.RESULT.MESSAGE);
	} else {
//	 	alsert("저장실패");
		showMsgBox("ERR", "<s:message code="MSG.TEST" />");
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
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
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
		parent.returnCdValPop(JSON.stringify(retjson),"${search.rqstSno}");
	} else {
		opener.returnCdValPop(JSON.stringify(retjson),"${search.rqstSno}");
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
	return;

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '도메인논리명 : ' + param.dmnLnm +' [ 도메인ID : ' + param.dmnId + ' ]';
	$('#dmn_sel_title').html(tmphtml);
	
}


function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
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
    <div class="pop_tit_txt">표준유효값 검색</div>
    <div class="pop_tit_close"><a>창닫기</a></div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
			<input type="hidden" id="rqstNo" name="rqstNo" />
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준용어조회">
                   <caption>표준용어 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:25%;" />
                   <col style="width:20%;" />
                   <col style="width:40%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="dmnLnm">도메인명</label></th>
							<td><input type="text" id="dmnLnm" name="dmnLnm" /></td>
							<th scope="row"><label for="infotpId">도메인그룹/인포타입</label></th>
							<td>
								<div id="selectBoxDiv"> <span></span></div>
<%-- 								<select id="dmngId" class="wd100" name="dmngId"> --%>
<!-- 									<option value="">전체</option> -->
<%-- 								</select> --%>
<%-- 								<select id="infotpId" class="wd100" name="infotpId"> --%>
<!-- 									<option value="">전체</option> -->
<%-- 							 	</select> --%>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="dataType">데이터타입</label></th>
							<td>
								<select id="dataType" class="wd100" name="dataType">
								<option value="">---전체---</option>
								<c:forEach var="code" items="${codeMap.dataTypeCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							 	</select>
							</td>
							<th scope="row"><label for="objDescn">설명</label></th>
							<td colspan="3"><input type="text" class="wd200" id="objDescn" name="objDescn" /></td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
<%--          <c:choose> --%>
<%--          <c:when test="${search.popRqst == 'Y'}"> --%>
<%--         	<div class="tb_comment"><s:message  code='ETC.RQST.POP' /></div> --%>
<%--          </c:when> --%>
<%--          <c:otherwise> --%>
        	<div class="tb_comment"><s:message  code='ETC.POP' /></div>
<%--          </c:otherwise> --%>
<%--          </c:choose> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
        <!-- 조회버튼영역  -->
<%-- 		 <c:choose> --%>
<%--          <c:when test="${search.popRqst == 'Y'}"> --%>
<%--         <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" /> --%>
<%--          </c:when> --%>
<%--          <c:otherwise> --%>
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
<%--          </c:otherwise> --%>
<%--          </c:choose> --%>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
</div>
</div>
</body>
</html>