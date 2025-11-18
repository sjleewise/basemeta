<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
                
        double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
       	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
       		double_select(connTrgSchJson, $(this));
       	});
      
});

$(window).on('load',function() {

	initGrid();
	loadDetail();
	
	
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        var headtext  = "<s:message code='META.HEADER.DDLETC.LST'/>";
        //No.|상태|선택|기타오브젝트ID|DB명|스키마명|기타오브젝트구분|기타오브젝트물리명|기타오브젝트논리명
        
		var headers = [
						{Text: headtext, Align:"Center"}
					];
				
			
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
		
		InitHeaders(headers, headerInfo); 

		var cols = [
					{Type:"Seq",	Width:80,   SaveName:"ibsSeq",	     Align:"Center", Edit:0},
					{Type:"Status", Width:80,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:80, SaveName:"ibsCheck",     Align:"Center", Edit:1, Hidden:1, Sort:0},
					{Type:"Text",   Width:120,  SaveName:"ddlEtcId",     Align:"Left", Edit:0, Hidden:1 },
					{Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm", Align:"Left", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"dbSchPnm",     Align:"Left", Edit:0},
					{Type:"Combo",  Width:120,  SaveName:"etcObjDcd",    Align:"Center", Edit:0},
					{Type:"Text",   Width:250,  SaveName:"ddlEtcPnm",    Align:"Left", Edit:0},
					{Type:"Text",   Width:250,  SaveName:"ddlEtcLnm",    Align:"Left", Edit:0}, 
					{Type:"Text",   Width:250,  SaveName:"rqstNo",       Align:"Left", Edit:0, Hidden:1},			
				];
                   
        InitColumns(cols);
       
        //콤보코드 셋팅...
		SetColProperty("etcObjDcd",	${codeMap.etcObjDcdibs});		
        
      	InitComboNoMatchText(1, "");
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
        	
			
			var param = $("#frmSearch").serialize();

			$('#dmn_sel_title').html('');
			loadDetail();
			
			grid_sheet.DoSearch("<c:url value="/meta/ddletc/getDdlEtcList.do" />", param);
			
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
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
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var ddlEtc = "&ddlEtcId="+grid_sheet.GetCellValue(row, "ddlEtcId");
	ddlEtc += "&rqstNo="+param.rqstNo;

	
	
	var param1 = "ddlEtcId="+param.ddlEtcId;
	
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(ddlEtc); 
	
	//DDL 스크립트
	loadDetailScript(ddlEtc);
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/ddletc/ajaxgrid/ddletcinfo_dtl.do"/>', param, function(){
		
		if(!isBlankStr(param)) {
			
		}
		
	});
}


//상세정보호출
function loadDetailScript(param) {
	
	
	$('div#detailEtcInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){
				
			    
	});
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
	}
		
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="STRD.DMN.INQ" /></div> <!-- 표준도메인 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="dbSchId">DB스키마</label></th> <!-- DB스키마 -->
							<td>
								<select id="dbConnTrgId" class="" name="dbConnTrgId">
							    	<option value="">선택</option> <!-- 전체 -->
							    </select>
							    <select id="dbSchId" class="" name="dbSchId">
							    	<option value="">선택</option> <!-- 전체 -->
							    </select>
							</td>
							
							<th scope="row"><label for="etcObjDcd">오브젝트구분</label></th> <!-- 오브젝트구분 -->
							<td>
								<select id="etcObjDcd" name="etcObjDcd">
									<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									<c:forEach var="code" items="${codeMap.etcObjDcd}" varStatus="status">
									  <option value="${code.codeCd}">${code.codeLnm}</option>
									</c:forEach>
							 	</select>
							</td>
						</tr>
						<tr>							
							<th scope="row"><label for="ddlEtcPnm">기타오브젝트명</label></th> <!-- 기타오브젝트명 -->
							<td colspan="3"><input type="text" id="ddlEtcPnm" name="ddlEtcPnm" value="${search.ddlEtcPnm }" /></td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
<%--         <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
</div>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	  <ul>	   
	    <li><a href="#tabs-1">기타오브젝트정보</a></li>  
	    <li><a href="#tabs-2">DDL</a></li> 
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<div id="detailEtcInfoScript"></div>
	  </div>
	  
	 </div>
</div>

</body>
</html>