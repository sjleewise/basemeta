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
<title>DDL파티션-엑셀업로드</title>
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
    	var rows = grid_sheet.IsDataModified();
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
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DDLPART.XLS'/>";
        //No.|상태|선택|요청사유|요청구분|DDL테이블ID|DB접속대상명|DB스키마명|테이블물리명|테이블논리명|테이블스페이스|DDL파티션ID|파티션유형|파티션키|서브파티션유형|서브파티션키|적용요청구분|적용요청일자|설명
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Text",   Width:100,  SaveName:"rqstResn",	Align:"Left", 	 Edit:1, KeyField:0, Hidden:1},
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
                    
                    {Type:"Text",   Width:60,   SaveName:"ddlTblId",	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm"	,    Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",   Width:120,  SaveName:"dbSchPnm",    Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",   Width:140, SaveName:"ddlTblPnm",     Align:"Left",   Edit:1, KeyField:1}, 
                    {Type:"Text",   Width:100,  SaveName:"ddlTblLnm", 	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"tblSpacPnm"	,    Align:"Left",   Edit:0, Hidden:1, KeyField:0},
                    
                    {Type:"Text",   Width:40,  SaveName:"ddlPartId", 	 Align:"Left",   Edit:0, Hidden:1},

                    {Type:"Combo",   Width:60,  SaveName:"partTypCd", 	 Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",   Width:120,  SaveName:"partKey"	,    Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Combo",   Width:60,  SaveName:"subPartTypCd", 	 Align:"Left",   Edit:1},
                    {Type:"Text",   Width:120,  SaveName:"subPartKey"	,    Align:"Left",   Edit:1},
                    {Type:"Combo",  Width:80,  SaveName:"aplReqTypCd",  Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"aplReqdt",  Align:"Left",   Edit:1, Hidden:1 , KeyField:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:200,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		
		SetColProperty("partTypCd", ${codeMap.partTypCdibs});
		SetColProperty("subPartTypCd", ${codeMap.partTypCdibs});
		SetColProperty("aplReqTypCd", ${codeMap.aplReqTypCdibs});

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
        
//     case "New":        //동일레벨 추가...
//     	//현재행을 가져온다.
//     	var crow = grid_sheet.GetSelectRow();
//     	var clevel = grid_sheet.GetRowLevel(crow);
    	
//     	//선택된 행의 다음라인에 현재레벨로 추가한다.
//     	var nrow = grid_sheet.DataInsert(crow+1, clevel);
		
//     	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
//      	grid_sheet.SetCellValue(nrow, "menuLvl"	, grid_sheet.GetCellValue(crow, "menuLvl"));
//      	grid_sheet.SetCellValue(nrow, "menuOrdr"		, grid_sheet.GetRowLevel(nrow));
    
//         break;
            
            
//     	 case "NewLow":        //하위레벨추가...

//          	//현재행을 가져온다.
//          	var crow = grid_sheet.GetSelectRow();
//          	var clevel = grid_sheet.GetRowLevel(crow);
         	
//          	//선택행의 다음라인에 하위레벨로 추가한다.
//          	var nrow = grid_sheet.DataInsert();
         	
//          	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
         	
// 	    	grid_sheet.SetCellValue(nrow, "upperMenuNo"	, grid_sheet.GetCellValue(crow, "menuNo"));
// 	    	grid_sheet.SetCellValue(nrow, "menuOrdr"		, grid_sheet.GetRowLevel(nrow));
         	
         
//              break;
            
        case "Search":
        	
        	break;
        	
 	 	case 'Save' : //엑셀 일괄 저장
 	 		var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
// 	 	   	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
 	 	   	
 	 	   	//데이터 사이즈 확인...
 	 	   	// 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
    		// 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
			// Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
 	 	   	if (SaveJson.Code == "IBS000") return;
 	 		
 	 	  	if (SaveJson.Code == "IBS010") return;
 	 	   	
 	 	   	//alert(SaveJson.data.length); return;
 	 	   	var url = "<c:url value="/meta/ddl/regddlpartrqstlist.do"/>";
 	 		//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&ddlTrgDcd=D"; //개발단계
 	 	    IBSpostJson2(url, SaveJson, param, ibscallback);
			break; 
			
 	    case "LoadExcel":  //엑셀업로드
 	      
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch',ExtendParam:'excelCnt='+parent.parent.$("#mstFrm").find("input[name=excelCnt]").val()});
 	        
 	        break;
 	        
 	        
 	   case "Down2Excel":  //엑셀내려받기
     	 	grid_sheet.Down2Excel(    { HiddenColumn:1, Merge:1 }    );
            break;
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
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
    

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
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


function grid_sheet_OnLoadExcel(result,code,msg) {
	var excelCnt = "";
  	if ("${search.popType}" == "I") {
 		excelCnt = $("#mstFrm #excelCnt", parent.document).val();
 	} else {
 		excelCnt = $("#mstFrm #excelCnt", opener.document).val();
 	}
	if(code==1){
	}else{
		// ibmsg.xml msg 사용 msg
// 		showMsgBox("ERR", msg);
		showMsgBox("ERR", "엑셀업로드 가능 건수는 <font color=red>"+excelCnt+ "</font> 건 입니다.<br>일괄등록 엑셀 데이터 건수를 확인 하십시오.");
	}	
}

</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">엑셀업로드-DDL파티션</div>
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
            <div class="tb_basic"  style="display: none;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메뉴조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                            <th scope="row"><label for="stwdLnm">표준단어명</label></th>
                            <td>
                                <span class="input_file">
<!--                                 <input type="text" name="stwdLnm" id="stwdLnm" /> -->
                                </span>
                            </td>  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        </form>
       <div class="tb_comment">
       	<s:message  code='ETC.EXCEL' />
<!--        	<br>4. 인덱스유형은 NON-UNIQUE 또는 UNIQUE 만 입력가능합니다. -->
       </div>
		<div style="clear:both; height:10px;"><span></span></div>
    <!-- 조회버튼영역  -->
    <div class="divLstBtn" >	 
		<div class="bt03">
			<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp">엑셀 올리기</button>
			<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl">저장</button>
		</div>
		<div class="bt02">
			<button class="btn_excel_down" id="popExcelDown" name="popExcelDown">엑셀 내리기</button>                       
		</div>
	</div>
</div>       
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>

