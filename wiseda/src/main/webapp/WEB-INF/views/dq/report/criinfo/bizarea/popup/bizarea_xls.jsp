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
<title><s:message code="BZWR.TRRT.EX.UP" /></title><!-- 업무영역 엑셀업로드 -->
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
    		showMsgBox("ERR", "<s:message code='ERR.CHKSAVE' />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code='CNF.SAVE' />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save");  
	});
    
		
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_pop){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
   					{Text:"<s:message code='DQ.HEADER.BIZAREA.XLS'/>", Align:"Center"}
   				];
        
        //No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|업무영역명|레벨|상위업무영역명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호"

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",	Width:40,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
	                {Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},	
					{Type:"Combo",  Width:100,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:130,  SaveName:"bizAreaLnm",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:60,  SaveName:"bizAreaLvl",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:130,  SaveName:"uppBizAreaLnm",   	Align:"Left", Edit:0, KeyField:0}, 
					{Type:"Text",   Width:300,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Date",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0}
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
        SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("vrfCd", ${codeMap.vrfCdibs});
        //SetColProperty("usergId", 	{ComboCode:"DA|AD|DB|UR|MR|DV", 	ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        SetColHidden("ibsStatus"	,1);
        SetColHidden("stwdId"		,1);
        SetColHidden("objVers"		,1);
      
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

        	grid_pop.DoSearch('<c:url value="/meta/stnd/getStndWordlist.do" />', param);
        	
        	break;
        	
        case "Apply": //적용버튼 액션...
			
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
        	var retval = getibscheckjoin(grid_pop, "stwdId");
        	alert(retval);

        	var saveJson = grid_pop.GetSaveJson(0);
			
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS010") return;
			//if(saveJson.data.length == 0) return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	var param = $("#mstFrm", opener.document).serialize();
        	var url = "<c:url value="/meta/stnd/regWam2WaqStndword.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	
        
        	break;
        	
 	 	case 'Save' : //엑셀 일괄 저장
 	 		var SaveJson = grid_pop.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
// 	 	   	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
 	 	   	//데이터 사이즈 확인...
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
 	 	   	//alert(SaveJson.data.length); return;
 	 	    var url = "<c:url value="/dq/criinfo/bizarea/regVrfBizAreaList.do"/>";
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
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
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
	var tmphtml = '<s:message code="STRD.WORD.NM" />' + ' : ' + param.stwdLnm +' [ <s:message code="PHYC.NM" /> : ' + param.stwdPnm + ' ]'; //표준단어명 / 물리명
	$('#stwd_sel_title').html(tmphtml);
	

	

}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			
			
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
			if(!isBlankStr(res.resultVO.rqstNo)) {
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
				//팝업닫기
				$("div.pop_tit_close").click();
// 	    		alert(res.resultVO.rqstNo);
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("STWD");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("VrfSearch");    		
	    	} 
			
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
    <div class="pop_tit_txt"><s:message code="BZWR.TRRT.EX.UP2" /></div><!-- 엑셀업로드-업무영역 -->
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
            <div class="tb_basic"  style="display: none;">
                  
            </div>
            </fieldset>
            
            
        </form>
       <div class="tb_comment"><s:message  code='ETC.EXCEL' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		         <!-- 조회버튼영역  -->
	<div id="divXlsBtn" style="text-align: left;">
		<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp"><s:message code="EXCL.UP" /></button><!-- 엑셀 올리기 -->
		<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl"><s:message code="STRG" /></button><!--저장-->


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

