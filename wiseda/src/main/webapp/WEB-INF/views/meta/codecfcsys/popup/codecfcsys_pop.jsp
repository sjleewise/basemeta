<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="CD.CLS.SYST.SRH" /></title> <!-- 코드분류체계 검색 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

EnterkeyProcess('Search');
$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
				
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
// 		//$( "#tabs" ).tabs();
                    
        //그리드 초기화 
//         initGrid();
       //조회 이벤트 처리
		$("#popSearch").click(function(){ 
			doAction('Search'); 
		});
		//적용 버튼 이벤트 처리
		$("#popApply").click(function(){
// 	 		alert("적용버튼");
			//선택체크박스 확인 : 적용할 대상이 없습니다..
			if(checkDelIBS (grid_pop, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();
	
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmPopSearch]")[0].reset();
		}).hide();
                      
        
        // 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         //$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
    }
    
		
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
	doAction('Search');
});


$(window).resize(
    
    function(){
    	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
//     	setibsheight($("#grid_01"));        
    	// grid_pop.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_pop){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.CODECFCSYS.POP'/>";
        /* No.|상태|선택|코드분류체계ID|코드분류체계유형|코드분류체계명|코드분류체계포맷 */
//         	headtext += "|요청번호|요청일련번호|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID";			
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"codeCfcSysId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:40,  SaveName:"codeCfcSysCd",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:40,  SaveName:"codeCfcSysLnm",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:60,  SaveName:"codeCfcSysFrm",    Align:"Left", Edit:0}
                    
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("codeCfcSysCd", ${codeMap.codeCfcSysCdibs});

        //InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
      
        FitColWidth();  
        
//         SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_pop);    
    //===========================
}

function doAction(sAction)
{
        
    switch(sAction)
    {		    
        
//     case "New":        //동일레벨 추가...
//     	//현재행을 가져온다.
//     	var crow = grid_pop.GetSelectRow();
//     	var clevel = grid_pop.GetRowLevel(crow);
    	
//     	//선택된 행의 다음라인에 현재레벨로 추가한다.
//     	var nrow = grid_pop.DataInsert(crow+1, clevel);
		
//     	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
//      	grid_pop.SetCellValue(nrow, "menuLvl"	, grid_pop.GetCellValue(crow, "menuLvl"));
//      	grid_pop.SetCellValue(nrow, "menuOrdr"		, grid_pop.GetRowLevel(nrow));
    
//         break;
            
            
//     	 case "NewLow":        //하위레벨추가...

//          	//현재행을 가져온다.
//          	var crow = grid_pop.GetSelectRow();
//          	var clevel = grid_pop.GetRowLevel(crow);
         	
//          	//선택행의 다음라인에 하위레벨로 추가한다.
//          	var nrow = grid_pop.DataInsert();
         	
//          	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
         	
// 	    	grid_pop.SetCellValue(nrow, "upperMenuNo"	, grid_pop.GetCellValue(crow, "menuNo"));
// 	    	grid_pop.SetCellValue(nrow, "menuOrdr"		, grid_pop.GetRowLevel(nrow));
         	
         
//              break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);

        	grid_pop.DoSearch('<c:url value="/meta/codecfcsys/getCodeCfcSysList.do" />', param);
        	
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
        	var url = "<c:url value="/meta/codecfcsys/regWam2WaqCodeCfcSys.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	
        
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
    
//     var stwdId = grid_pop.GetCellValue(row, "stwdId");
//     returnPop(stwdId);
    
//     ibs2formmapping(row, $("form#frmInput", opener.document), grid_pop);
	
// 	window.close();

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

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_pop.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="CD.CLS.SYST.NM" /> : ' + param.codeCfcSysLnm; /*코드분류체계명*/
	$('#codecfcsys_sel_title').html(tmphtml);
	

	

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
    <div class="pop_tit_txt"><s:message code="CD.CLS.SYST.SRH" /></div> <!-- 코드분류체계 검색 --> 
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
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"><!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>
                                <th scope="row"><label for="codeCfcSysCd"><s:message code="CD.CLS.SYST.PTRN" /></label></th> <!-- 코드분류체계유형 -->
                                <td><select id="codeCfcSysCd" name="codeCfcSysCd">
                                		<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.codeCfcSysCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
                                <th scope="row"><label for="codeCfcSysLnm"><s:message code="CD.CLS.SYST.NM" /></label></th> <!-- 코드분류체계명 -->
                                <td><input type="text" id="codeCfcSysLnm" name="codeCfcSysLnm" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="codeCfcSysFrm"><s:message code="CD.CLS.SYST.FORMAT" /></label></th> <!-- 코드분류체계포맷 -->
                                <td><input type="text" id="codeCfcSysFrm" name="codeCfcSysFrm"/></td>
                                <th scope="row"><label for="crgUserNm"><s:message code="CHG.R.NM" /></label></th> <!-- 담당자명 -->
                                <td><input type="text" id="crgUserNm" name="crgUserNm"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
       
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "350px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="codecfcsys_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

   
<%-- <form id="frmExcel" name="frmExcel" action="" method="post" > --%>
<!-- 	<input type="hidden" name="excelhtml" id="excelhtml"> -->
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>

