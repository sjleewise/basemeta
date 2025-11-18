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


var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;

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
        

    	
        $( "#tabs" ).hide();
		$( "#tabsIdx" ).hide();
    	
    	
    	//DB매핑 검색 팝업 호출
    	$("#searchPop").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/commons/damgmt/db/popup/dbmap_pop.do' />", 800, 600, param);
        });
    	//DB매핑 검색 팝업 호출
    	$("#searchPop2").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/commons/damgmt/db/popup/dbmap_pop.do' />", 800, 600, param);
        });
    	
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(connTrgSchJson, $("#frmSearch #srcDbConnTrgId"));
	   	$('select', $("#frmSearch #srcDbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
	   	double_select(connTrgSchJson, $("#frmSearch #tgtDbConnTrgId"));
	   	$('select', $("#frmSearch #tgtDbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #ddlTblLnm"), "DDLTSFTBL");

});

$(window).load(function() {
// 	alert('window.load');
	//PK값으로 검색
// 	var ddlTblId ="";
// 		ddlTblId="${search.ddlTblId}";
// 		param ="ddlTblId="+ddlTblId;
// 		if(ddlTblId != null && ddlTblId != "" && ddlTblId !="undefined"){
// 			grid_sheet.DoSearch("<c:url value="/meta/ddltsf/getDdllist.do" />", param);
// 	}
		
// 	var ddlTblLnm = "${search.ddlTblLnm}";
// 		param2 ="ddlTblLnm="+ddlTblLnm;
// 	if(ddlTblLnm != null && ddlTblLnm != "" && ddlTblLnm !="undefined"){
// 		grid_sheet.DoSearch("<c:url value="/meta/ddltsf/getDdllist.do" />", param2);
// 	}
		
	initGrid();
	var linkFlag = "";
// 	linkFlag= "${linkFlag}";
// 	if(linkFlag=="1"){
// 		doAction("Search");
// 	}
	
	loadDetail();
	
	loadDetailScript();
	$( "#tabs" ).tabs().show();
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"No.|이관단계|소스DBMS명|소스DB스키마명|타겟DBMS명|타겟DB스키마명|오브젝트구분|이관DDL오브젝트ID|이관DDL오브젝트물리명|이관DDL오브젝트논리명|테이블스페이스ID|물리모델테이블명|스크립트정보|요청번호|요청일련번호|설명|버전|등록유형코드|최초요청일시|최초요청자명|요청일시|요청자명|승인일시|승인자명", Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:100,   SaveName:"tsfStep", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"srcDbConnTrgPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"srcDbSchPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"tgtDbConnTrgPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"tgtDbSchPnm", 		Align:"Left", Edit:0, Hidden:0},
						 
						{Type:"Combo",   Width:80,   SaveName:"objDcd", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"objPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"objLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"tblSpacId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"pdmTblLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"scrtInfo",	 	Align:"Left", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:400,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Date",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:0}
					];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("objDcd",	${codeMap.objDcdibs});
        
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
			
			grid_sub_ddltblchange.RemoveAll();
			grid_sub_ddltblcollist.RemoveAll();
// 			grid_sub_ddltblidxcol.RemoveAll();
			grid_sub_ddltblcolchange.RemoveAll();
			loadDetail();
// 			loadDetailCol();
			loadDetailScript();
			grid_sheet.DoSearch("<c:url value="/meta/ddltsf/getDdlTsflist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}
    
//주제영역 팝업 리턴값 처리
function returnDbMapPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #srcDbConnTrgId").val(retjson.srcDbConnTrgId);
	$("#frmSearch #tgtDbConnTrgId").val(retjson.tgtDbConnTrgId);
	$("#frmSearch #srcDbConnTrgId").change();
	$("#frmSearch #tgtDbConnTrgId").change();
	$("#frmSearch #srcDbSchId").val(retjson.srcDbSchId);
	$("#frmSearch #tgtDbSchId").val(retjson.tgtDbSchId);
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
	
	if(param.objDcd == "TBL"){
		$( "#tabs" ).tabs().show();
        $( "#tabsIdx" ).hide();
		var ddlTblId = "&ddlTblId="+grid_sheet.GetCellValue(row, "objId");

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '이관 DDL 테이블명 : ' + param.objPnm;
		$('#ddl_sel_title').html(tmphtml);
		
		
		//메뉴ID를 토대로 조회한다....
		loadDetail(ddlTblId); 
		
		//DDL 스크립트 
//	 	loadDetailScript(param1);
		$("#frmInputDdlScript #ddlscript").val(param.scrtInfo);
	} else {
		$( "#tabsIdx" ).tabs().show();
        $( "#tabs" ).hide();
		var ddlIdxId = "&ddlIdxId="+grid_sheet.GetCellValue(row, "objId");

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '이관 DDL 인덱스명 : ' + param.objPnm;
		$('#ddl_sel_title').html(tmphtml);
		
		
		//메뉴ID를 토대로 조회한다....
		loadDetail(ddlIdxId, 'IDX');
// 		var param1 = "ddlIdxId="+param.objId;
		$("#detailIdxInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
	}
	
	
	
}

//상세정보호출
function loadDetail(param, objDcd) {
	if(objDcd != "IDX"){
		$('div#detailInfo1').load('<c:url value="/meta/ddltsf/ajaxgrid/ddltsftblinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
				//컬럼목록
				grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddltsf/ajaxgrid/ddlTsftblcollist_dtl.do" />', param);
				//테이블이력
				grid_sub_ddltblchange.DoSearch('<c:url value="/meta/ddltsf/ajaxgrid/ddltsftblchange_dtl.do" />', param);
	// 			grid_sub_ddltblidxcol.DoSearch('<c:url value="/meta/ddltsf/ajaxgrid/ddltblidxcol_dtl.do" />', param);
				//컬럼이력
				grid_sub_ddltblcolchange.DoSearch('<c:url value="/meta/ddltsf/ajaxgrid/ddltsftblcolchange_dtl.do" />', param);
			}
			//$('#tabs').show();
		});
	} else if(objDcd == "IDX") {
		$('div#detailIdxInfo1').load('<c:url value="/meta/ddltsf/ajaxgrid/ddltsfidxinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
				
				grid_sub_ddlidxcollist.DoSearch('<c:url value="/meta/ddltsf/getDdlTsfIdxCollist.do" />', param);
				grid_sub_ddlidxchange.DoSearch('<c:url value="/meta/ddltsf/getDdlTsfIdxChange.do" />', param);
				grid_sub_ddlidxcolchange.DoSearch('<c:url value="/meta/ddltsf/getDdlTsfIdxColChange.do" />', param);
			}
			//$('#tabs').show();
		});
	}
	
}

function loadDetailScript(param, objDcd) {
	
// 	if(objDcd == 'TBL'){
		
		$('div#detailInfoScript').load('<c:url value="/meta/ddltsf/ajaxgrid/ddltsfscript_dtl.do"/>', param, function(){
		});
// 	} else if(objDcd == 'IDX'){
		$('div#detailIdxInfoScript').load('<c:url value="/meta/ddltsf/ajaxgrid/ddltsfscript_dtl.do"/>', param, function(){});
// 	}
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
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">이관 DDL테이블조회</div>
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="이관DDL조회">
                   <caption>이관DDL 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>   
                   	    <tr>                               
                           <th scope="row"><label for="srcDbSchId">소스DB정보</label></th>
                            <td>
                            <select id="srcDbConnTrgId" class="" name="srcDbConnTrgId">
				             <option value=""><s:message code="CHC" /></option>
				            </select>
				            <select id="srcDbSchId" class="" name="srcDbSchId">
				             <option value=""><s:message code="CHC" /></option>
				             </select>
						 	<button class="btnDelPop" >삭제</button>
						 	<button class="btnSearchPop" id="searchPop">검색</button>
                            </td>
                           
                            <th scope="row" class=""><label for="tgtDbSchId">타겟DB정보</label></th>
	                      <td>
				            <select id="tgtDbConnTrgId" class="" name="tgtDbConnTrgId">
				             <option value=""><s:message code="CHC" /></option>
				            </select>
				            <select id="tgtDbSchId" class="" name="tgtDbSchId">
				             <option value=""><s:message code="CHC" /></option>
				             </select>
				             <button class="btnDelPop" >삭제</button>
						 	<button class="btnSearchPop" id="searchPop2">검색</button>
				           </td>
                        </tr>                         
						<tr>
							<th scope="row"><label for="ddlTblLnm">이관 DDL오브젝트명</label></th>
							<td colspan="3"><input type="text" id="ddlTblLnm" name="ddlTblLnm" /></td>
						</tr>
						
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 <span style="font-weight:bold; color:#444444;">Ctrl + C</span>를 사용하시면 됩니다.</div>
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

	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">이관 DDL테이블 상세정보</a></li>
	    <li><a href="#tabs-2">컬럼목록</a></li>
	    <li><a href="#tabs-3" id="colinfo">컬럼정보</a></li>
	    <li><a href="#tabs-4">컬럼이력</a></li>
	    <li><a href="#tabs-5">테이블 이력</a></li>
<!-- 	    <li><a href="#tabs-6">인덱스컬럼</a></li> -->
	    <li><a href="#tabs-7">DDL</a></li>
<!-- 	    <li><a href="#tabs-7">부가정보</a></li> -->
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddltsftblcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailInfo2"></div>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddltsftblcolchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-5">
			<%@include file="ddltsftblchange_dtl.jsp" %>
	  </div>
<!-- 	   <div id="tabs-6"> -->
<%-- 			<%@include file="ddltsftblidxcol_dtl.jsp" %> --%>
<!-- 	  </div> -->
	   <div id="tabs-7">
 			<div id="detailInfoScript"></div>
	  </div>
	  </div>
	 </div>
	 <div id="tabsIdx">
	  <ul>
	    <li><a href="#tabs-1">이관 DDL인덱스 상세정보</a></li>
	    <li><a href="#tabs-2">인덱스컬럼목록</a></li>
	    <li><a href="#tabs-3" id="colinfo">인덱스컬럼 상세정보</a></li>
	    <li><a href="#tabs-4">인덱스이력</a></li>
	    <li><a href="#tabs-5">인덱스컬럼이력</a></li>
	    <li><a href="#tabs-6">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailIdxInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="../ddl/ddlidxcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailIdxInfo2"></div>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddltsfidxchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-5">
			<%@include file="../ddl/ddlidxcolchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-6">
 			<div id="detailIdxInfoScript"></div>
<%-- 			<%@include file="ddlscript_dtl.jsp" %> --%>
	  </div>
	 </div>

</body>
</html>