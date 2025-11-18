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
<title><s:message code="BZWR.RULE.INQ" /></title><!--업무규칙 조회-->


<script type="text/javascript">

var interval = "";

//page 구분
var pageFlag = "";
EnterkeyProcess("Search");
$(document).ready(function() {
		//page 구분
		pageFlag = "${sflag}";
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		//탭 초기화....
		////$( "#tabs" ).tabs();
        //그리드 초기화 
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  });
        // 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } ).hide();

      //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
    	       	//iframe 형태의 팝업일 경우
    	       	if ("${search.popType}" == "I") {
    	       		parent.closeLayerPop();
    	       	} else {
    	       		window.close();
    	       	}
        });
        
        //상세 페이지
       // loadDetail();
        
        initButton(pageFlag);
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	
	$("#dqiLnm").focus();
	//doAction("Search");
});


$(window).resize(
    function(){
    	//그리드 가로 스크롤 방지
    	grid_pop.FitColWidth();
    }
);

function initButton(sflag) {
	if (sflag == "CHG") { //업무영역 등록요청의 변경대상 추가용 팝업인 경우 초기화버튼, 적용버튼을 지정한다.
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmSearch]")[0].reset();
		});
      
		//적용 버튼 이벤트 처리
		$("#popApply").click(function(){
// 	 		alert("적용버튼");
			//선택체크박스 확인 : 적용할 대상이 없습니다..
			if(checkDelIBS (grid_pop, "<s:message code='ERR.APPLY' />")) {
				doAction("Apply");
	    	}
		}).show();
		
	} else { //그외의 경우는 초기화 및 적용버튼을 숨긴다.
		 // 초기화버튼 Hide
        $("#popReset").click(function(){}).hide();
        // 적용버튼 Hide
        $("#popApply").click(function(){}).hide();
		
	}
		
}

function initGridCol(sflag) {
	if (sflag == "CHG") { //변경대상 추가용일시 선택컬럼 표시
		
        grid_pop.SetColHidden("ibsCheck",0);
        grid_pop.SetColProperty("ibsCheck",{Edit:1});
        
	} else {
		
        grid_pop.SetColHidden("ibsCheck",1);
	}
}



function initGrid()
{
    
    with(grid_pop){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.BIZRULE.POP'/>", Align:"Center"}
                ];
        //No.|상태|선택|업무규칙ID|업무규칙명|DQI명|CTQ명|설명|버전|등록유형|요청일시|요청자ID|요청자명


        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"brId",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:400,  SaveName:"brNm",   	Align:"Left", Edit:0},
                    {Type:"Text",   Width:150,   SaveName:"dqiLnm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:70,   SaveName:"ctqLnm", 	Align:"Center", Edit:0},
                    {Type:"Text",   Width:150,  SaveName:"objDescn",    Align:"Left", 	Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"objVers",     Align:"Left",   Edit:0},
                    {Type:"Combo",  Width:40,  SaveName:"regTypCd",    Align:"Center", Edit:0},                        
                    {Type:"Text",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstUserNm",  Align:"Center", Edit:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW' />|<s:message code='CHG' />|<s:message code='DEL' />"}); /*"신규|변경|삭제"*/

	 
	     //변경대상 추가용인지 확인하여 다중선택이 가능/불가능으로 선택해준다...
        initGridCol(pageFlag);
	     
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("brId",1);
        SetColHidden("objVers",1);
        SetColHidden("regTypCd",1);
        SetColHidden("rqstDtm",1);
        SetColHidden("rqstUserId",1);
        SetColHidden("rqstUserNm",1);
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_pop);    
    //===========================
   
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_pop.DoSearch("<c:url value="/dq/bizrule/getBizruleListPop.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_pop.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
            
		case "Apply": //적용버튼 액션...
			
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
//         	var retval = getibscheckjoin(grid_pop, "stwdId");
//         	alert(retval);

        	var saveJson = grid_pop.GetSaveJson(0, "ibsCheck");
			
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
        	var url = "<c:url value="/dq/bizrule/regWam2WaqBizrule.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	
        
        	break;
    }       
}




function grid_pop_OnDblClick(row, col, value, cellx, celly) {
	 //main 화면 그리드 셋팅
	 if(row < 1) return;
	 

	//변경대상 추가시
// 	 if(pageFlag == "CHG") {
// 		 if (grid_pop.GetCellValue(row, "ibsCheck") =="0") {
// 		 	grid_pop.SetCellValue(row, "ibsCheck", "1");
// 		 } else {
// 			grid_pop.SetCellValue(row, "ibsCheck", "0");
// 		 }
// 		 return;
// 	 }
	
// 	window.close();
}

function grid_pop_OnClick(row, col, value, cellx, celly) {
	/*
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
	var param =  grid_pop.GetRowJson(row);
	var dqiId = "&dqiId="+grid_pop.GetCellValue(row, "dqiId");
	//caption 
	var tmphtml = '<s:message code="DATA.QLTY.INDC.NM"/>' + ':'' + param.dqiLnm ; <!--데이터품질지표명-->
	$('#dqi_sel_title').html(tmphtml);
	
	//상세조회
	//loadDetail(dqiId);
	*/
}

function grid_pop_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
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
				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("SDITM");
	    		
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


</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="BZWR.RULE.INQ" /></div><!--업무규칙 조회-->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
		<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DATA.QLTY.INDC.INQ'/>"><!--데이터품질지표조회-->
                   <caption><s:message code="DATA.QLTY.INDC"/></caption><!--데이터품질지표-->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="brNm"><s:message code="BZWR.RGR.NM"/></label></th><!--업무규칙명-->
                           <td colspan="3">
                               <input type="text" name="brNm" id="brNm" />
                           </td>
                           
                           
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<!--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div>  -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "400px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display:none ;">
		    <div class="selected_title" id="dqi_sel_title"><s:message code="BZWR.RGR.NM"/>: <span></span></div><!--업무규칙명--> 
	</div>
<!-- 
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DATA.QLTY.INDC.DETAIL"/></a></li>데이터품질지표 상세정보




	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo"></div>
	  </div>
	 </div>
 -->
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>