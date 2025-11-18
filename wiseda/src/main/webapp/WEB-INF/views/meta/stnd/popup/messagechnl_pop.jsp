<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>채널오류코드검색</title>
<script type="text/javascript">

var popRqst = "${search.popRqst}";

EnterkeyProcess('Search');
$(document).ready(function() {
	$(window).focus();
// 		alert("document.ready");
	
       //조회 이벤트 처리
		$("#popSearch").click(function(){ 
			doAction('Search'); 
		});
		//적용 버튼 이벤트 처리
		if (popRqst == 'Y') {
	        // 적용 Event Bind
	        $("#popApply").click(function(){ 
	        	if(checkDelIBS (grid_pop, "<s:message code="REQ.NO.CHANG" />")) {
					doAction("Apply");
		    	}
			}).show();
	        $("#popDelete").click(function(){ 
	        	if(checkDelIBS (grid_pop, "<s:message code="REQ.NO.DEL" />")) {
					doAction("DelRqst");
		    	}
			}).show();
        }
	
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmPopSearch]")[0].reset();
		}).show();
                      
        
        // 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
        
    }
    
		
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
});


$(window).resize(
    
    function(){
    	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));        
    	// grid_pop.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_pop){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.MESSAGE.CHNL.POP'/>";			
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Sort:0},
                    
                    {Type:"Text",   Width:40,   SaveName:"chnlMsgId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,   SaveName:"stdErrMsgId",    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"errCd",  Align:"Left", Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"errTlgLanDscd",  Align:"Left", Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"errTpCd",  Align:"Left", Edit:0}, 
					{Type:"Text",   Width:200,  SaveName:"chnlErrMsgCts",  Align:"Left", Edit:0}, 
					{Type:"Text",   Width:100,  SaveName:"chnlErrCd",  Align:"Left", Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"errCdUsYn",  Align:"Left", Edit:0}, 
					{Type:"Combo",  Width:100,  SaveName:"chnlTpCd",  Align:"Left", Edit:0}, 
					{Type:"Combo",  Width:100,  SaveName:"chnlDtlsClcd",  Align:"Left", Edit:0}, 
					{Type:"Text",   Width:130,  SaveName:"errCdAplDt",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"actnCd",  Align:"Left", Edit:0},
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	  
		SetColProperty("errTlgLanDscd",	${codeMap.errTlgLanDscdibs});
		SetColProperty("errTpCd",	${codeMap.errTpCdibs});
		SetColProperty("errCdUsYn",	${codeMap.errCdUsYnibs});
		SetColProperty("chnlTpCd",	${codeMap.chnlTpCdibs});
		SetColProperty("chnlDtlsClcd",	${codeMap.chnlDtlsClcdibs});
        //InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        SetColHidden("ibsStatus"	,1);
      
//         FitColWidth();  
        
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
        	//alert(param);

        	grid_pop.DoSearch('<c:url value="/meta/stnd/getMessageChnlList.do" />', param);
        	
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
        	//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&rqstDcd=CU";
        	var url = "<c:url value="/meta/stnd/regWam2WaqMessageChnl.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
			//처리중입니다. 메세지
// 			showMsgBox("PRC", "<s:message code="REQ.PRC.LOAD" />");
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        
        	break;
        	
 	     case "DelRqst": //삭제요청...
        	var saveJson = grid_pop.GetSaveJson(0, "ibsCheck");
			
			//2. 데이
// 			alert(saveJson.Code); 처리대상 행이 없는 경우 리턴한다.
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&rqstDcd=DD";
        	var url = "<c:url value="/meta/stnd/regWam2WaqMessageChnl.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
			//처리중입니다. 메세지
// 			showMsgBox("PRC", "<s:message code="REQ.PRC.LOAD" />");
 	        
 	      	break;
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;
    	
    	case "Down2Excel":  //엑셀내려받기
    		
    		grid_pop.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;

    }       
}


// //팝업 리턴값 제공
function returnPop(ret) {
// 	alert(JSON.stringify(ret));
	opener.frmInput.stwdId.value = ret;
	window.close();
}






/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_pop_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
    
	var retjson = grid_pop.GetRowJson(row);
	
	//요청서용 팝업일 경우.....
	if (popRqst == 'Y') {
		//체크박스 선택/해제 토글 기능.....
		var cellchk = grid_pop.GetCellValue(row, "ibsCheck");
		if(cellchk == '0') {
			grid_pop.SetCellValue(row, "ibsCheck", 1);
		} else {
			grid_pop.SetCellValue(row, "ibsCheck", 0);
		}
		
		return;
	}
    
}

function grid_pop_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_pop.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_pop.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//선택한 상세정보를 가져온다...
	var param =  grid_pop.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '표준단어명 : ' + param.stwdLnm +' [ 물리명 : ' + param.stwdPnm + ' ]';
	$('#stwd_sel_title').html(tmphtml);
	
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
    <div class="pop_tit_txt">메시지 검색</div>
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
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메뉴조회">
                   <caption></caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                            <th scope="row"><label for="stdErrCd">오류코드</label></th>
                            <td>
                                <span class="input_file">
                                <input type="text" name="stdErrCd" id="stdErrCd" />
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
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
         </c:when>
         <c:otherwise>
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:otherwise>
         </c:choose>   
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="stwd_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>

