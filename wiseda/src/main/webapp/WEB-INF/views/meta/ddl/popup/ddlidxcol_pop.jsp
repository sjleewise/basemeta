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
<title>DDL 인덱스 검색</title>

<script type="text/javascript">

var popRqst = "${search.popRqst}";
var connTrgSchJson = ${codeMap.devConnTrgSch} ;

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
        	
        	doAction("Apply");
        	
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
	}).hide();
  
	
    double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	}); 
               
   	$("#moveRight").click(function(){
   		
   		doMoveRight();   		
   	});
   	
	$("#moveLeft").click(function(){
   		
		doMoveLeft();
   	});
	
	$("#btnMoveUp").click(function(){
   				
		doMoveUp();
   	});
	
	$("#btnMoveDown").click(function(){
   		
		doMoveDown();
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
    
});

$(window).on('load',function() {
		
	//그리드 초기화
	initGrid();
	initIdxColGrid();
	
	if("${search.ddlTblId}" != ""){
		
		doAction("Search");
		
		setInitDdlIdxCol(); 
	}
	
	$(window).resize();
 		
	
});

EnterkeyProcess("Search");

$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	setibsheight($("#grid_02"));        
    	// grid_sheet.SetExtendLastCol(1);    
    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        //headText = "No.|상태|선택|컬럼순서|PK여부|컬럼(물리명)|컬럼(논리명)";
        //headText = "Num|State|Choice|Order|PK|DDL column (physical name)|DDL column (logical name)";
        
        var headText = "";
        
        headText = "<s:message code='META.HEADER.DDLIDXCOL.POP1' />";
        //No.|상태|선택|컬럼순서|PK여부|컬럼(물리명)|컬럼(논리명)

        
        var headers = [
                    {Text: headText, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",  Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50,   SaveName:"ibsCheck",     Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",    Width:60,   SaveName:"colOrd",       Align:"Center", Edit:0},
                    {Type:"Text",    Width:60,   SaveName:"pkYn",         Align:"Center", Edit:0},
                    {Type:"Text",    Width:120,  SaveName:"ddlColPnm",    Align:"Left",   Edit:0},
                    {Type:"Text",    Width:120,  SaveName:"ddlColLnm",    Align:"Left",   Edit:0},                   
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        
        
        FitColWidth();
        
//         SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================   
}


function initIdxColGrid()
{
    
    with(grid_idxcol){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headText = "";
        
        //headText = "No.|상태|선택|인덱스컬럼(물리명)|인덱스컬럼(논리명)|정렬유형";
        //headText = "Num|State|Choice|DDL table (physical name)|DDL table (logical name)|Sorting type";
        
        headText = "<s:message code='META.HEADER.DDLIDXCOL.POP2'/>";  
        //No.|상태|선택|인덱스컬럼(물리명)|인덱스컬럼(논리명)|정렬유형
                      
        var headers = [
                    {Text: headText, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50,  SaveName:"ibsCheck",     Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:120,  SaveName:"ddlColPnm",    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"ddlColLnm",    Align:"Left",   Edit:0},
                    {Type:"Combo",  Width:90,   SaveName:"sortTyp",       Align:"Center", Edit:1},   
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
 	    SetColProperty("sortTyp", {ComboCode:"ASC|DESC", ComboText:"ASC|DESC"});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);         
        SetColHidden("ddlColLnm",1);
        
        FitColWidth();
        
//         SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_idxcol);    
    
       
    //===========================   
}



function doAction(sAction)
{
        
    switch(sAction)
    {
             
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlIdxForColList.do" />", param);
            
        	break;
       
            
        case "Apply":
        	
        	var ddlIdxColAsm = ""; 
        	
        	for(var i = 1; i <= grid_idxcol.RowCount(); i++) {
        		
        		var ddlIdxColPnm = grid_idxcol.GetCellValue(i,"ddlColPnm");
        		var sortTyp      = grid_idxcol.GetCellText(i,"sortTyp");
        		
        		if(i > 1){
        			
        			ddlIdxColAsm +=  ";"; 
        		}
        		
        		ddlIdxColAsm +=  ddlIdxColPnm + "," + sortTyp;
        	}
        	
        	//alert(ddlIdxColAsm);
        	
        	parent.returnDdlIdxCol(ddlIdxColAsm);
        	
        	//팝업창 닫기 버튼 클릭....
        	$(".pop_tit_close").click();
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}
 

function doMoveRight() {
	
	for(var i = 1;  i <= grid_sheet.RowCount(); i++) {
		
		var ddlColPnm = grid_sheet.GetCellValue(i,"ddlColPnm");
		var ddlColLnm = grid_sheet.GetCellValue(i,"ddlColLnm");
		
		if(grid_sheet.GetCellValue(i, "ibsCheck") == "1"){
			
			var findRow = grid_idxcol.FindText("ddlColPnm",ddlColPnm);
			
			if(findRow > 0) {
				
				//이미 추가된 컬럼이 있습니다.
				showMsgBox("ERR","<s:message code='AL.ADD.COL' />"); 
				return; //기존에 있으면 pass
			}
			
			var row = grid_idxcol.DataInsert(-1);
			
			grid_idxcol.SetCellValue(row,"ddlColPnm", ddlColPnm);
			grid_idxcol.SetCellValue(row,"ddlColLnm", ddlColLnm);
			
			grid_sheet.SetCellValue(i, "ibsCheck",0); 
		}
		
	}	
}


function doMoveLeft() {
	
	for(var i = grid_idxcol.RowCount();  i > 0; i--) {
				
		if(grid_idxcol.GetCellValue(i, "ibsCheck") == "1"){
			
			grid_idxcol.RowDelete(i, false);
		}
		
	}
	
}


function doMoveUp() {
	
	var befCol = grid_idxcol.GetSelectRow();
	
	var aftCol = befCol - 1; 
	
	//alert(befCol);
	
	if(aftCol > 0) {
	
		grid_idxcol.DataMove(aftCol, befCol);
	} 	
}

function doMoveDown() {
	
	var befCol = grid_idxcol.GetSelectRow();
	
	var aftCol = befCol + 2;  
		
	if(aftCol > 0) {
	
		grid_idxcol.DataMove(aftCol, befCol);
	} 		
}


function setInitDdlIdxCol(){
		
	var ddlIdxColAsm =  $("#ddlIdxColAsm", parent.document).val();
    		
	if(ddlIdxColAsm == "" || ddlIdxColAsm == undefined) return;
	
	var arrDdlIdxColAsm = ddlIdxColAsm.split(";");		 
	
	grid_idxcol.RemoveAll();
	
	for(var i = 0; i < arrDdlIdxColAsm.length; i++) {
		
		var arrTemp = arrDdlIdxColAsm[i].split(",");
		
		var ddlIdxColPnm = arrTemp[0];
		var sortTyp      = arrTemp[1];
	
		
		var row =  grid_idxcol.DataInsert(-1);
								
		grid_idxcol.SetCellValue(row, "ddlColPnm", ddlIdxColPnm);
		grid_idxcol.SetCellValue(row, "sortTyp", sortTyp); 		
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
    <div class="pop_tit_txt"><s:message code="IDEX.COL.INQ" /></div>  <!-- 인덱스 검색 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="DDL인덱스조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="dbSchPnm"><s:message code="DEV.DB.MS.NM" /></label></th> <!-- 개발DBMS -->
                           <td>
                                 <span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd200" value="${search.dbConnTrgPnm}" readonly="readonly"/></span>
								<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm" class="wd200" value="${search.dbSchPnm}" readonly="readonly"/></span>
                           </td>   
                            
                           <th scope="row"><label for="ddlTblPnm"><s:message code="TBL.NM" /></label></th> <!-- 테이블명 -->
                           <td>
                                <span class="input_inactive">
                                <input type="text" name="ddlTblPnm" id="ddlTblPnm" value="${search.ddlTblPnm}"/>
                                <input type="hidden" name="ddlTblId" id="ddlTblId" value="${search.ddlTblId}"/>
                                
                                </span>
                           </td>                                                                                         
                       </tr>                       
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.POP' /></div> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
        <!-- 조회버튼영역  -->       
        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="popSearch"  name="popSearch"><s:message code="INQ"/></button> <!-- 조회 -->
            	<button class="btn_reset"  id="popReset"   name="popReset" ><s:message code="INON" /></button> <!-- 초기화 -->
			    <button class="btn_apply"  id="popApply"   name="popApply"><s:message code="APL" /></button> <!-- 적용 -->
			</div>
			<div class="bt02">
			  	<button class="btn_move_up"   id="btnMoveUp">위로 이동</button> <!-- 위로 이동 -->
				<button class="btn_move_down" id="btnMoveDown">아래로 이동</button> <!-- 아래로 이동 -->
			    &nbsp;&nbsp;&nbsp;
	          	<button class="btn_excel_down" id="popExcelDown" name="popExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                      
	    	</div>
        </div>	
        <!-- 조회버튼영역 end -->
        
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<table width="100%">
		<tr>
			<td style="width:50%">
				<div id="grid_01" class="grid_01">
				     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
				</div>
			</td>
			<td valign="middle"  style="width:5%;text-align:center;">
				<a><img id="moveRight" style="cursor:hand;" src="<c:url value="/images/tab_move_right_.gif"/>" /></a><br><br>
				<a><img id="moveLeft" style="cursor:hand;" src="<c:url value="/images/tab_move_left_.gif"/>" /></a>   
			</td>
			<td style="width:*">
				<div id="grid_02" class="grid_01">
				     <script type="text/javascript">createIBSheet("grid_idxcol", "100%", "400px");</script>            
				</div>
			</td>			
		</tr>
	</table>
	
	
	
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>