<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
//         $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
    	
    	$( "#tabsSeq" ).tabs();
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	//setautoComplete($("#frmSearch #pdmTblPnm"), "DDLTBL");
    	setautoComplete($("#frmSearch #ddlSeqPnm"), "DDLSEQ");
    	

    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
		//DDL조회 Event Bind
		$("#btnDdlSearch").button({
		       icons: { primary: "ui-icon-refresh" }
		}).click(function(){
        	doAction("ShowDdl");
		}).hide();
		
		//프로젝트 검색 팝업 호출
		$("#prjSearchPop").click(function(){
			var param = ""; //$("form#frmInput").serialize();
			openLayerPop ("<c:url value='/meta/subjarea/popup/projSearchPop.do' />", 800, 600, param);
		}).show(); 
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
// 	   		$('#ddlTrgDcd').val('');
	   	});
// 		$('#ddlTrgDcd').change(function(){
// 			$("#frmSearch #dbConnTrgId").val('');
// 			$("#frmSearch #dbSchId").val('');
// 		});
});

$(window).load(function() {
		
	initGrid();
	loadDetail();
	loadDetailObjScript();

	var ddlSeqId ="";
 	ddlSeqId="${search.ddlSeqId}";
	param ="ddlSeqId="+ddlSeqId;
	if(ddlSeqId != null && ddlSeqId != "" && ddlSeqId !="undefined"){
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlSeqlist.do" />", param);
	}
		
	var ddlSeqLnm = "${search.ddlSeqLnm}";
		param2 ="ddlSeqLnm="+ddlSeqLnm;
	if(ddlSeqLnm != null && ddlSeqLnm != "" && ddlSeqLnm !="undefined"){
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlSeqlist.do" />", param2);
	}
		
	initGrid();
	var linkFlag = "";
	linkFlag= "<c:out value='${linkFlag}'/>";
	if(linkFlag=="1"){
		//doAction("Search");
	}	
	$("#tabsSeq").show();
	//$( "#layer_div" ).show();
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "No.";
    	headtext += "|SR번호|서브프로젝트번호|DDL시퀀스ID|시퀀스(물리명)|시퀀스(논리명)";
    	headtext += "|DB접속대상(물리명)|DB사용자(물리명)";
    	headtext += "|명명규칙|사용용도|테이블명|컬럼명|L1코드|L3코드|권한목록";
    	headtext += "|INCREMENT BY|START WITH|MIN VALUE|MAX VALUE|CYCLE여부|ORDER여부|CACHE SIZE";
    	headtext += "|패키지명|클래스명|초기화구분코드|초기화실패업무영향도";
    	headtext += "|objId|objDcd|DDL|스크립트정보|설명|요청사유|요청일시|요청자ID|요청번호|요청일련번호|요청자명|요청부서|적용요청일자|적용요청구분";
  
	    var headers = [
	                {Text:headtext, Align:"Center"}
	            ];
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:100,   SaveName:"srMngNo",	 Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"prjMngNo",	 Align:"Left",   Edit:0, Hidden:1},						
						{Type:"Text",   Width:0,   SaveName:"ddlSeqId",	 Align:"Center",   Edit:0, Hidden:1},		
						{Type:"Text",   Width:150,   SaveName:"ddlSeqPnm",	 Align:"Center",   Edit:0, KeyField:0},
						{Type:"Text",   Width:0,   SaveName:"ddlSeqLnm",	 Align:"Center",   Edit:0, Hidden:1},		
						{Type:"Text",   Width:150,   SaveName:"dbConnTrgPnm",	 Align:"Center",   Edit:0, KeyField:0},
						{Type:"Text",   Width:150,   SaveName:"dbSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
						
						{Type:"Combo",   Width:100,   SaveName:"nmRlTypCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"usTypCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"pdmTblPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"pdmColPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"l1cdPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"l3cdPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtLst",	 	 Align:"Center",   Edit:0, Hidden:1},
						
						{Type:"Text",   Width:100,   SaveName:"incby",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"strtwt",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"minval",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"maxval",	 Align:"Center",   Edit:0},
						
						{Type:"Combo",   Width:100,   SaveName:"cycYn",	 Align:"Center",   Edit:0},
						{Type:"Combo",   Width:100,   SaveName:"ordYn",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"cacheSz",	 Align:"Center",   Edit:0},
						
						{Type:"Text",   Width:100,    SaveName:"pckgNm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"seqClasCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"seqInitCd",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,    SaveName:"bizIfnc",	 Align:"Center",   Edit:0, Hidden:1},
						
						{Type:"Text",   Width:100,   SaveName:"objId",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"objDcd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"CheckBox",   Width:70,   SaveName:"ddl",	 	Align:"Center", Edit:1, Hidden:1},
	                    {Type:"Text",   Width:40,  SaveName:"scrtInfo",     Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",   Width:400,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
						{Type:"Text",   Width:200,  SaveName:"rqstResn",	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
						
	                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"rqstUserDept",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"aplReqdt",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"aplReqTypCd",	 	Align:"Center", Edit:0, Hidden:1}
	                ];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs} );


		SetColProperty("nmRlTypCd", 	${codeMap.nmRlTypCdibs});
		SetColProperty("usTypCd", 	${codeMap.usTypCdibs});
		SetColProperty("aplReqTypCd", 	${codeMap.aplReqTypCdibs});
		
		SetColProperty("cycYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("ordYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
		
		SetColProperty("seqClasCd", ${codeMap.seqClasCdibs});
		SetColProperty("seqInitCd", ${codeMap.seqInitCdibs});

      	InitComboNoMatchText(1, "");
      	SetSheetHeight(350);
//         FitColWidth();  
        
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
             	
         	if (  isBlankStr($("#frmSearch #dbConnTrgId").val(), 'O') 
           			&& isBlankStr($("#frmSearch #dbSchId").val(), 'O')
           			&& isBlankStr($("#frmSearch #ddlTrgDcd").val(), 'O')
           			&& isBlankStr($("#frmSearch #ddlSeqPnm").val(), 'O') 
           		) {
           		showMsgBox("INF", "검색조건이 없습니다.<br>DBMS/스키마명,시퀀스명 중<br>최소 1개이상 검색조건을 입력후 조회하십시요.");
           		return;
           	} 
			var param = $("#frmSearch").serialize();
			  
			$('#ddl_sel_title').html('');
			
			loadDetail();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlSeqlist.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
//             grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        	
        	//보여지는 컬럼들만 엑셀 다운로드          
            var downColNms = "";
           	for(var i=0; i<grid_sheet.LastCol();i++ ){
           		if(grid_sheet.GetColHidden(i) != 1){
           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
           		}
           	}
           	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, DownCols:downColNms, FileName:"ddlSeqList.xls"});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
        case "ShowDdl" :
        	var SelectJson = grid_sheet.GetSaveJson({StdCol:"ddl"});
			if (SelectJson.data.length > 500) {
				showMsgBox("ERR", "조회할 DDL는 500건 이상 선택할 수 없습니다.");
        		return;
			}
        	var tmpVal = 0;
			for(var i=0; i<SelectJson.data.length; i++) {
				if(SelectJson.data[i].ddl == '1'){
					tmpVal = 1;
					break;
				}
			}
			if (tmpVal == 0) {
				showMsgBox("ERR", "조회할 DDL을 선택해 주세요.");
        		return;
			}

			var url = '<c:url value="/meta/ddl/popup/ddlscript_pop.do"/>';
			OpenModal(url, "DdlScript", 800, 600);
        	break;            
    }       
}

//프로젝트 팝업 리턴값 처리
function returnPrjAllPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #prjMngNo").val(retjson.prjMngNo);
	$("#frmSearch #srMngNo").val(retjson.srMngNo);
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
	var url = "<c:url value='/meta/ddl/popup/ddlseqlst_pop.do' />";
	var ddlSeqId = "ddlSeqId="+grid_sheet.GetCellValue(row, "ddlSeqId");
	var popwin = OpenModal(url+"?"+ddlSeqId, "ddlSeqDetailPop",  900, 700, "yes");
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var ddlSeq = "&ddlSeqId="+grid_sheet.GetCellValue(row, "ddlSeqId");
	ddlSeq += "&rqstNo="+param.rqstNo;

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = 'DDL 시퀀스명 : ' + param.ddlSeqPnm;
	$('#ddl_sel_title').html(tmphtml);
	
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(ddlSeq);
	
	
	//DDL 스크립트 
	var param1 = "ddlObjId="+param.ddlSeqId + "&ddlObjType=SEQ";
	loadDetailObjScript(param1);
	
	$("#frmInputDdlScript #ddlscript").val(param.scrtInfo);
	
	grid_sub_ddlseqchange.DoSearch('<c:url value="/meta/ddl/getDdlSeqChange.do" />', ddlSeq);
}

//상세정보호출
function loadDetail(param) {
	$('div#detailSeqInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlseqinfo_dtl.do"/>', param, function(){
		if(param == null || param == "" || param =="undefined"){
			
		} else {
		}
		//$('#tabsSeq').show();
	});
}

//상세정보호출
function loadDetailObjScript(param) {
	$('div#detailObjInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlScriptObj_dtl.do"/>', param, function(){});
}

function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//form 내용을 초기화 한다.....
		//doAction('Add');
		//$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">DDL 시퀀스조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="DDL시퀀스조회">
                   <caption>표준용어 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row" class="th_require"><label for="dbConnTrgId">DBMS/스키마명</label></th>
							<td><select id="dbConnTrgId" name="dbConnTrgId" class="wd100">
					             <option value="">선택</option>
					            </select>
					            <select id="dbSchId"  name="dbSchId" class="wd100">
					             <option value="">선택</option>
					             </select>
					        </td>
                            <th scope="row"><label for="ddlTrgDcd">DDL대상구분</label></th>
                            <td><select id="ddlTrgDcd" name="ddlTrgDcd">
                         		<option value="">전체</option>
                         		<option value="D">개발</option>
                         		<option value="T">검증</option>
                         		<option value="R">운영</option>
                                </select>
                            </td>
						</tr>
						<tr>
							<th scope="row"><label for="ddlSeqPnm">시퀀스명</label></th>
							<td colspan="3"><input type="text" id="ddlSeqPnm" name="ddlSeqPnm" class="wd200" /></td>
						</tr>
<!-- 						<tr>
							<th scope="row"><label for="pdmTblPnm">테이블명</label></th>
							<td><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd200" /></td>
							<th scope="row"><label for="pdmColPnm">컬럼명</label></th>
							<td><input type="text" id="pdmColPnm" name="pdmColPnm" class="wd200" /></td>
						</tr> -->
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div> 
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
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
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabsSeq" style="display:none;">
	  <ul>
	    <li><a href="#tabs-1">DDL시퀀스 상세정보</a></li>
	    <li><a href="#tabs-2">시퀀스이력</a></li>
	    <li><a href="#tabs-3">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailSeqInfo"></div>
	  </div>

	   <div id="tabs-2">
			<%@include file="ddlseqchange_dtl.jsp" %>
	  </div>

	   <div id="tabs-3">
 			<div id="detailObjInfoScript"></div>
<%-- 			<%@include file="ddlobjscript_dtl.jsp" %> --%>
	  </div>
	 </div>
</div>
</body>
</html>