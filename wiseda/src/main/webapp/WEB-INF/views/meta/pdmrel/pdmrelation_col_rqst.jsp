<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
        //imgConvert($('div.tab_navi a img'));
		
		//$("#btnColExcelLoad").hide();// 엑셀올리기 숨김
		
		//컬럼 이동 버튼 초기화
		   /* $(".btn_move_top").button({
			    icons: {
			        primary: "ui-icon-arrowthickstop-1-n"//,
			      },
			      text: false
		   }).next().button({
			    icons: {
			        primary: "ui-icon-triangle-1-n"//,
			      },
			      text: false
			}).next().button({
			    icons: {
			        primary: "ui-icon-triangle-1-s"//,
			      },
			      text: false
			}).next().button({
			    icons: {
			        primary: "ui-icon-arrowthickstop-1-s"//,
			      },
			      text: false
			}).parent().buttonset(); */

		
        // 추가 Event Bind
        $("#btnColNew").click(function(){ 
        	
			doAction("NewCol");
        });
        
        // 저장 Event Bind
        $("#btnColSave").click(function(){ 
        	//저장할 대상이 있는지 체크한다.
        	if(!col_sheet.IsDataModified()) {
        		//저장할 내역이 없습니다.
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'SaveCol');
  
        
        }).show();

        // 삭제 Event Bind
        $("#btnColDelete").click(function(){ 
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
        	if(checkDelIBS (col_sheet, "<s:message code="ERR.CHKDEL" />")) {
        		//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteCol');
        	}
        	
        }); //doActionCol("Delete");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doActionCol("Down2Excel"); } );

        // 엑셀업로드 Event Bind
        $("#btnColExcelLoad").click( function(){ doActionCol("LoadExcel"); } ).hide();
        
        
        //컬럼 이동 버튼 Event Bind
        $("#btnColMoveTop").click(function(){
        	//맨위로 이동
        	dataMoveIBS(col_sheet, "TOP");
        }).next().click(function(){
        	//위로 이동
        	dataMoveIBS(col_sheet, "UP");
        }).next().click(function(){
        	//아래로 이동
        	dataMoveIBS(col_sheet, "DOWN");
        }).next().click(function(){
        	//맨 아래로 이동
        	dataMoveIBS(col_sheet, "BOTTOM");
        });
        
        /*
        $("#btnColRqstNew").hide();
        $("#btnColDelete").hide();
        
        $("#btnColMoveTop").hide();
        $("#btnColMoveUp").hide();
        $("#btnColMoveDown").hide();
        $("#btnColMoveBottom").hide();*/
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initColGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, col_sheet);
	col_sheet.SetColHidden("rvwStsCd"	,1);
	col_sheet.SetColHidden("rvwConts"	,1);
	
	if(rqststep == "Q" || rqststep == "A") {
		$(".btn_move_top").parent().hide();
	}
	
	doActionCol("Search");
	
	//erwin 등록 버튼 숨긴다.
	if("${waqMstr.bizDcd}" == "R9P" || "${waqMstr.bizDcd}" == "R7P"){
		
		
		$("#divLstBtn").hide();
	}
	
});


$(window).resize(
    
    function(){
                
    	// col_sheet.SetExtendLastCol(1);    
    }
);


function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.ASIS.PDM.REL.COL.RQST'/>";
		//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|관계ID|관계물리명|관계논리명|부모테이블주제영역ID|부모테이블주제영역명|부모테이블ID|부모테이블명|부모테이블컬럼ID|부모테이블컬럼명|자식테이블주제영역ID|자식테이블주제영역명|자식테이블ID|자식테이블명|자식테이블컬럼ID|자식테이블컬럼명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호|이전관계명|이전부모테이블명|이전부모테이블컬럼명|이전자식테이블명|이전자식테이블컬럼명

        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,   SaveName:"rvwConts",  Align:"Left",   Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:60,   SaveName:"regTypCd",  Align:"Center", Edit:0},						
					{Type:"Combo",  Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0},
					
					{Type:"Text",    Width:100,  SaveName:"pdmRelId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"pdmRelPnm"   , Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:100,  SaveName:"pdmRelLnm"   , Align:"Left",   Edit:0},                    
                    {Type:"Text",    Width:100,  SaveName:"paSubjId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paFullPath"   , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paTblId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paTblPnm"   , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paColId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Popup",   Width:100,  SaveName:"paColPnm"   , Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chSubjId"   , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chFullPath" , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chTblId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chTblPnm"   , Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"chColId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Popup",   Width:100,  SaveName:"chColPnm"   , Align:"Left",   Edit:1, Hidden:0},
                     
                    
                    {Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlDtlSno",  Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Text",    Width:100, SaveName:"bfPdmRelPnm"   ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:100,  SaveName:"bfPaEntyPnm"  ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"bfPaAttrPnm"     ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:100,  SaveName:"bfChEntyPnm"  ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"bfChAttrPnm"     ,Align:"Left",   Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});

		
        
        InitComboNoMatchText(1, "");
        
        
        // FitColWidth();
        
        SetExtendLastCol(1);     
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}




//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function ibscallbackCol(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
		//공통메세지 팝업 : 성공 메세지...
    	showMsgBox("INF", res.RESULT.MESSAGE);
    
    	postProcessColIBS(res); 
    	
    } else {
		//공통메시지 팝업 : 실패 메세지...
    	shotMsgBox("ERR", res.RESULT.MESSAGE);
    }
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessColIBS(res) {
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			
		
			doAction("SearchCol");    		
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
	    	
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
	    		$("form#frmSearch input[name=rqstDtlSno]").val(res.ETC.rqstDtlSno);
				doActionCol("SearchRow");    		
	    	}
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" :
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
		
			
			if(!isBlankStr(res.resultVO.rqstNo)) {

	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}

	    		doAction("SearchCol");    		
	    	} 
						
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
function col_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	var param =  col_sheet.GetRowJson(row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno   + "&searchObj=" + param.pdmTblPnm + "&subInfo=COL";
	$("#tabs #colinfo").click();
	grid_changecol.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1, 'COL');
	}
	
}

function col_sheet_OnClick(row, col, value, cellx, celly) {
    	
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(col_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = col_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="CLMN.NM" /> : ' + param.pdmRelPnm + ' [' + param.paColPnm +']'; //컬럼명
	$('#col_sel_title').html(tmphtml);
	
	//loadDetailCol(param);
	
	var param1  = "bizDtlCd=PRELC";
	
    param1 += "&rqstNo="     + param.rqstNo;
    param1 += "&rqstSno="    + param.rqstSno;
    param1 += "&rqstDtlSno=" + param.rqstDtlSno;
    param1 += "&rqstDtlDtlSno=" + param.rqstDtlDtlSno;

    //alert(param1);
    
	getRqstVrfLst(param1);
        
}

function col_sheet_OnPopupClick(Row, Col) { 
	

	var colName = col_sheet.ColSaveName(Col);
	
	//alert(colName);
	
	if(colName == "paColPnm") {
		
		var param = "sFlag=PDMREL";
		
		param += "&subjLnm="   + col_sheet.GetCellValue(Row,"paFullPath");
		param += "&pdmTblLnm=" + col_sheet.GetCellValue(Row,"paTblPnm");
		
		$("#frmRelInput #subjFlag").val("CPA");

		openLayerPop ("<c:url value='/meta/model/popup/pdmcol_pop.do' />", 800, 600, param);
		
	}else if(colName == "chColPnm") {
				
		var param = "sFlag=PDMREL";
		
		param += "&subjLnm="   + col_sheet.GetCellValue(Row,"chFullPath");
		param += "&pdmTblLnm=" + col_sheet.GetCellValue(Row,"chTblPnm");

		$("#frmRelInput #subjFlag").val("CCH");
		openLayerPop ("<c:url value='/meta/model/popup/pdmcol_pop.do' />", 800, 600, param);
	}
	
}



function col_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
        alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
        alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		$('div#detailInfocol').empty();
// 		$('#btnColReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}
</script>

<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

        
         <!-- 버튼영역  -->
        <div class="divLstBtn" id="divLstBtn"  style="padding-right :10px">	 
            <div class="bt03">
<!-- 			    <button class="btn_search" id="btnSearch" 	name="btnSearch">재조회</button>  -->
                <button class="btn_rqst_new" id="btnColRqstNew" name="btnColRqstNew"><s:message code="ADDT" /></button>  <!-- 추가 -->                                                        
				  <ul class="add_button_menu">
				    <li class="btn_new" id="btnColNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li><!-- 신규 추가 -->
<%-- 				    <li class="btn_chang_add" id="btnColChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span>변경대상 추가</a></li> --%>
				    <li class="btn_excel_load" id="btnColExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 올리기 -->
				  </ul>         
			    <button class="btn_save" id="btnColSave" 	name="btnColSave"><s:message code="STRG" /></button> <!-- 저장 -->
			    <button class="btn_delete" id="btnColDelete" 	name="btnColDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
<!-- 			    <button class="btn_check" id="btnCheck" 	name="btnCheck">검증</button>  -->
<!-- 			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst">등록</button>  -->
			</div>
           
			<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnColExcelLoad" name="btnColExcelLoad">엑셀 업로드</button>                        -->
	          <button class="btn_excel_down" id="btnColExcelDown" name="btnColExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                        
	    	</div>
        </div>
         <!-- 버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("col_sheet", "100%", "350px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "col_sheetƒ", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="col_sel_title"><s:message code="CLMN.CHC.1"/></div> <!-- 컬럼을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>

<div id="detailInfocol"></div>

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->