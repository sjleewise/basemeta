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
<title><s:message code="CD.CLS.SYST.EXCL.UPLOAD" /></title> <!-- 코드분류체계 엑셀업로드 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function() {
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
	
	
	//엑셀 올리기 버튼 셋팅 및 클릭 이벤트 처리...
	$('#btnExcelUp').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doAction('LoadExcel');
	});
	
	//엑셀 저장 버튼 초기화...
	$('#btnSaveExl').click(function(event){
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_pop.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save");  
	});
    
	 // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );
		
});

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
        
        var headtext = "<s:message code='META.HEADER.CODECFCSYS.XLS'/>";
        /* No.|상태|삭제|요청구분|분류체계유형|분류체계명|분류체계항목순번|분류체계항목명|형식|자릿수|구분자|참조테이블|참조컬럼|설명 */
//         	headtext += "|요청번호|요청일련번호|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID";			
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"DelCheck", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1},
                    {Type:"Combo",  Width:70,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:1},
                    {Type:"Combo",   Width:100,   SaveName:"codeCfcSysCd",	 	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",    Width:100, SaveName:"codeCfcSysLnm"    ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",    Width:120, SaveName:"codeCfcSysItemSeq"   ,Align:"Left",   Edit:1, KeyField:1, AcceptKeys:"N"},
					{Type:"Combo",    Width:100,  SaveName:"codeCfcSysItemCd"    ,Align:"Left",   Edit:1, Hidden:0 , KeyField:1},
//                     {Type:"Combo",    Width:100, SaveName:"codeCfcSysItemCdDesc"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemFrm"   ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",    Width:80,  SaveName:"codeCfcSysItemLen" ,Align:"Left",   Edit:1, Hidden:0, KeyField:1, AcceptKeys:"N"},
                    {Type:"Text",    Width:80,  SaveName:"codeCfcSysItemSpt"    ,Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefTbl"      ,Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefCol"  ,Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"objDescn",    Align:"Left", Edit:1}, 
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("codeCfcSysCd", 	${codeMap.codeCfcSysCdibs});
	    
		grid_pop.SetColProperty("codeCfcSysItemCd", 	${codeMap.codeCfcSysItemCdibs});
// 		grid_pop.SetColProperty("codeCfcSysItemCdDesc", 	${codeMap.codeCfcSysItemCdibs});

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        SetColHidden("ibsStatus"	,1);
      
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
        
 	 	case 'Save' : //엑셀 일괄 저장
 	 		var SaveJson = grid_pop.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
// 	 	   	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
 	 	   	//데이터 사이즈 확인...
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
 	 	   	//alert(SaveJson.data.length); return;
 	 	    var url = "<c:url value="/meta/codecfcsys/regCodeCfcSysItemRqstList.do"/>";
 	 		//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
 	 	    IBSpostJson2(url, SaveJson, param, ibscallback);
			break;  
			
 	    case "LoadExcel":  //엑셀업로드
 	    	grid_pop.LoadExcel({Mode:'HeaderMatch'});
 	        break;
 	       
        case "Down2Excel":  //엑셀내려받기
        	grid_pop.Down2Excel(    { HiddenColumn:1, Merge:1 }    );
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
function grid_pop_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
    
}

function grid_pop_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

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


function grid_pop_OnChange(Row, Col, Value, OldValue, RaiseFlag) { 
	//SaveName 이 아니라 index
	//코드분류체계유형 
	if(Col == "4"){
// 		grid_pop.SetCellValue(Row, "codeCfcSysItemCd", null);
// 		var codeVal = grid_pop.GetCellValue(Row, Col);
		//KS규격 분류코드 체계
		if("KS" == codeVal){
// 			grid_pop.CellComboItem(Row, "codeCfcSysItemCd", 	${codeMap.ksCodeCfcSysItemCdibs});
// 			grid_pop.CellComboItem(Row, "codeCfcSysItemCdDesc", 	${codeMap.ksCodeCfcSysItemCdDesc});
		}
		//인증 분류코드 체계
		if("CRTFC" == codeVal){
// 			grid_pop.CellComboItem(Row, "codeCfcSysItemCd", 	${codeMap.CrtfcCodeCfcSysItemCdibs});
// 			grid_pop.CellComboItem(Row, "codeCfcSysItemCdDesc", 	${codeMap.CrtfcCodeCfcSysItemCdDesc});
		}

		//코드분류체계항목 설명 셋팅
		//grid_pop.SetCellValue(Row, "codeCfcSysItemCdDesc",codeVal );
	}
	
	//코드분류체계항목
	if(Col == "7"){
// 		var codeVal = grid_pop.GetCellValue(Row, Col);
		//코드분류체계항목 설명 셋팅
// 		grid_pop.SetCellValue(Row, "codeCfcSysItemCdDesc",codeVal );
	}
}



</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="EXCL.UPLOAD.-.CD.CLS.SYST.ITEM" /></div> <!-- 엑셀업로드-코드분류체계 항목 -->
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
            <div class="tb_basic"  style="display: none;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"> <!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                            <th scope="row"><label for="codeCfcSysLnm"><s:message code="CD.CLS.SYST.NM" /></label></th> <!-- 코드분류체계명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="codeCfcSysLnm" id="codeCfcSysLnm" />
                                </span>
                            </td>  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        </form>
       <div class="tb_comment"><s:message  code='ETC.EXCEL' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
   
	
	<div class="divLstBtn" >	 
		<div class="bt03">
			<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp"><s:message code="EXCL.UP" /></button> <!-- 엑셀 올리기 -->
			<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl"><s:message code="STRG" /></button> <!-- 저장 -->
		</div>
		<div class="bt02">
			<button class="btn_excel_down" id="popExcelDown" name="popExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
		</div>
	</div>
          
</div>       
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>

