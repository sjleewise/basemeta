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

var sysareaJson = ${codeMap.sysarea} ;
var connTrgSchJson = ${codeMap.connTrgSch} ;
var connTrgSchJsonBySubjSchMap = ${codeMap.connTrgSchJsonBySubjSchMap} ;

EnterkeyProcess("Search");
var initIdxTab = false;

$(document).ready(function() {
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
     
	$("#btnTreeNew").hide();
	$("#btnDelete").hide();
     
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
   	
   	$( "#tabs_part" ).tabs();
   	
   	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
   	setautoComplete($("#frmSearch #ddlTblPnm"), "DDLTBL");
   	setautoComplete($("#frmSearch #fullPath"), "SUBJ");
   	
   
   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});

    /*
   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select_upcode(connTrgSchJsonBySubjSchMap, $(this), "");
   	});
	*/
	
	$("#frmSearch #sysAreaId").change(function(){
		if($(this).val() == ""){
			double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
		}else{
    		double_select_upcode(connTrgSchJsonBySubjSchMap, $("#frmSearch #dbConnTrgId"), $(this).val());
		}
	});   	
   	
 // 시스템영역
	create_selectbox(sysareaJson, $("#sysAreaId"));
   	
   	
   	$('#subjSearchPop').button({   //주제영역 검색팝업 이벤트
	    icons: {
	        primary: "ui-icon-search"
	      },
	      text: false, 
	      create: function (event, ui) {
//	     	  $(this).addClass('search_button');
			  $(this).css({
				  'width': '18px',
				  'height': '18px',
				  'vertical-align': 'bottom'
				  });
	    	  
	      }
	    }).click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
		//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
		//$('div#popSearch').dialog("open");
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
			var popwin = OpenModal(url+"?"+param, "searchpop",  800, 600, "no");
			popwin.focus();
		
	}).parent().buttonset();
   	
	//DDL조회 Event Bind 아직안됨
	$("#btnDdlSearch").click(function(){
    	doAction("ShowDdl");
	}).hide();
	
	//프로젝트 검색 팝업 호출
	$("#prjSearchPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/projSearchPop.do' />", 800, 600, param);
	}).show(); 
    	
});

$(window).on('load',function() {
	initGrid();
	loadDetail();
// 	loadDetailIdxCol();
	loadDetailObjScript();
/*
	var ddlIdxId ="";
	ddlIdxId="${search.ddlIdxId}";
	param ="ddlIdxId="+ddlIdxId;
	if(ddlIdxId != null && ddlIdxId != "" && ddlIdxId !="undefined"){
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlIdxlist.do" />", param);
	}
		
	var ddlIdxLnm = "${search.ddlIdxLnm}";
		param2 ="ddlIdxLnm="+ddlIdxLnm;
	if(ddlIdxLnm != null && ddlIdxLnm != "" && ddlIdxLnm !="undefined"){
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlIdxlist.do" />", param2);
	}
	*/
		
	var linkFlag = "";
	linkFlag= "<c:out value='${linkFlag}'/>";
	if(linkFlag=="1"){
		doAction("Search");
	}	
	$("#tabs_part").show();
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DDLPART.LST'/>";
        //No.|시스템영역아이디|시스템영역명|주제영역명|DDL테이블ID|DB접속대상명|DB스키마명|테이블물리명|테이블논리명|테이블스페이스|SR번호|서브프로젝트번호|DDL파티션ID|파티션유형|파티션키|서브파티션유형|서브파티션키|스크립트정보|요청번호|요청일련번호|전일배치CUD|objId|objDcd|DDL|설명|버전|등록유형코드|요청사유|최초요청일시|최초요청자명|요청일시|승인일시|승인자명|요청자명|요청부서|적용요청일자|적용요청구분
		var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						
						{Type:"Text",   Width:100,  SaveName:"sysAreaId", 	 Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"sysAreaLnm", 	 Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"fullPath",	 Align:"Left", Edit:0},
						
						{Type:"Text",   Width:60,   SaveName:"ddlTblId",	 Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm", Align:"Left",   Edit:0},
						{Type:"Text",   Width:120,  SaveName:"dbSchPnm",     Align:"Left",   Edit:0},
						{Type:"Text",   Width:140,  SaveName:"ddlTblPnm",    Align:"Left",   Edit:0}, 
						{Type:"Text",   Width:100,  SaveName:"ddlTblLnm", 	 Align:"Left",   Edit:0, Hidden:0},
						{Type:"Text",   Width:120,  SaveName:"tblSpacPnm",   Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"srMngNo",	     Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",   Width:100,  SaveName:"prjMngNo",	 Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"ddlPartId", 	 Align:"Left",   Edit:0, Hidden:1},
						{Type:"Combo",  Width:60,   SaveName:"partTypCd", 	 Align:"Left",   Edit:0, KeyField:0},
						{Type:"Text",   Width:120,  SaveName:"partKey"	,    Align:"Left",   Edit:0, KeyField:0},
						{Type:"Combo",  Width:60,   SaveName:"subPartTypCd", Align:"Left",   Edit:0},
						{Type:"Text",   Width:120,  SaveName:"subPartKey",    Align:"Left",   Edit:0}, 
						{Type:"Text",   Width:100,  SaveName:"scrtInfo",	 	Align:"Left", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",  Width:80,   SaveName:"cudYn",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"objId",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"objDcd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"CheckBox",   Width:70,   SaveName:"ddl",	 	Align:"Center", Edit:1, Hidden:1}, 
						{Type:"Text",   Width:400,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,    SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",  Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:200,   SaveName:"rqstResn",	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						
						{Type:"Date",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,   SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"rqstUserDept",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"aplReqdt",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",  Width:100,   SaveName:"aplReqTypCd",	 	Align:"Center", Edit:0, Hidden:1}
						];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("partTypCd", ${codeMap.partTypCdibs});
		SetColProperty("subPartTypCd", ${codeMap.partTypCdibs});
		SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
		SetColProperty("cudYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("aplReqTypCd", 	${codeMap.aplReqTypCdibs});
		
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
      	SetSheetHeight(250);
      	FitColWidth();  
        
        //SetExtendLastCol(1);    
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
             	
  /*       	if ( ( isBlankStr($("#frmSearch #dbConnTrgId").val(), 'O') || isBlankStr($("#frmSearch #dbSchId").val(), 'O'))
           			&& isBlankStr($("#frmSearch #ddlTblPnm").val(), 'O') 
           		) {
           		showMsgBox("INF", "검색조건이 없습니다.<br>DBMS/스키마명,테이블명 중<br>최소 1개이상 검색조건을 입력후 조회하십시요.");
           		return;
           	} */
        	
			$('#dmn_sel_title').html('');
			loadDetail();
			
			var param = $("#frmSearch").serialize();
		     	param += "&ddlTrgDcd=D"; //개발단계
		     	
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlPartlist.do" />", param);

        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
        	var downColNms = "";
        	for(var i=0; i<=grid_sheet.LastCol();i++ ){
        		if(grid_sheet.GetColHidden(i) != 1){
        			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
        		}
        	}
        	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, DownCols:downColNms, FileName:"DDL파티션조회"});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;

        case "ShowDdl" :
        	var SelectJson = grid_sheet.GetSaveJson({StdCol:"ddl"});
        	
			if (SelectJson.data.length > 1000) {
				showMsgBox("ERR", "조회할 DDL는 천건 이상 선택할 수 없습니다.");
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
    

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #fullPath").val(retjson.subjLnm);
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
	
	var url = "<c:url value='/meta/ddl/popup/ddlpartlst_pop.do' />";
	var ddlPartId = "ddlPartId="+grid_sheet.GetCellValue(row, "ddlPartId");
	var popwin = OpenModal(url+"?"+ddlPartId, "ddlPartDetailPop",  900, 600, "yes");
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var ddlPart = "&ddlPartId="+grid_sheet.GetCellValue(row, "ddlPartId");
	ddlPart += "&rqstNo="+param.rqstNo;

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = 'DDL 테이블명 : ' + param.ddlTblPnm;
	$('#ddl_sel_title').html(tmphtml);
	
	//상세조회
	loadDetail(ddlPart);
	
	//DDL 스크립트
	var param1 = "ddlObjId=" + param.ddlPartId + "&ddlObjType=DDP";
	
	loadDetailObjScript(param1);
	
	$("#frmInputDdlScript #ddlscript").val(param.scrtInfo);
	
	part_main_sheet.DoSearch('<c:url value="/meta/ddl/getddlmainpartlst.do" />', ddlPart);
	
	sub_sheet.DoSearch('<c:url value="/meta/ddl/getddlsubpartlst.do" />', ddlPart);
	
	part_hist_sheet.DoSearch('<c:url value="/meta/ddl/getddlparthistlst.do" />', ddlPart);
}

//상세정보호출
function loadDetail(param) {
	
	$('div#detailPartInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlpartinfo_dtl.do"/>', param, function(){
		if(param == null || param == "" || param =="undefined"){
		} else {
		}
	});
}

//상세정보호출
function loadDetailObjScript(param) {
	$('div#detailObjInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlScriptObj_dtl.do"/>', param, function(){});
// 	$('div#detailObjInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
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
	    <div class="menu_title">DDL 파티션조회</div>
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="DDL파티션조회">
                   <caption>검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>   
                    
                   	<tr>
						<th scope="row"><label for="dbConnTrgId">DBMS/스키마명</label></th>
						<td>
							<select id="dbConnTrgId" class="" name="dbConnTrgId">
							<option value="">선택</option>
							</select>
							<select id="dbSchId" class="" name="dbSchId">
							<option value="">선택</option>
							</select>
						</td>
						
						<th scope="row"><label for="ddlTblPnm">테이블명</label></th>
						<td  colspan="3"><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd200" /></td>
					</tr>
					
					<tr>
						<th scope="row"><label for="partTypCd">파티션 유형</label></th>
						<td>
							<select id="partTypCd" name="partTypCd" >
							<option value="">---선택---</option>
							<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
							<option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
							</select>
						</td>
						<th scope="row"><label for="partKey">파티션키</label></th>
						<td><input type="text" id="partKey" name="partKey" class="wd200" /></td>
					</tr>
                   
					<tr>
						<th scope="row" ><label for="subPartTypCd">서브파티션 유형</label></th>
						<td>
							<select id="subPartTypCd" name="subPartTypCd" >
							<option value="">---선택---</option>
							<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
							<option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
							</select>
						</td>
						<th scope="row" ><label for="subPartKey">서브파티션키</label></th>
						<td><input type="text" id="subPartKey" name="subPartKey" class="wd200" /></td>
					</tr>
					
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

	<div id="tabs_part" style="display:none;">
	  <ul>
	    <li><a href="#tabs-1">DDL파티션 상세정보</a></li>
	    <li><a href="#tabs-2">주파티션목록</a></li>
	    <li><a href="#tabs-3">서브파티션목록</a></li>
	    <li><a href="#tabs-4">파티션이력</a></li>
	    <li><a href="#tabs-5">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailPartInfo"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddlpartmain_list.jsp" %>
	  </div>
	  <div id="tabs-3">
			<%@include file="ddlpartsub_list.jsp" %>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddlparthist_list.jsp" %>
	  </div>
	   <div id="tabs-5">
 			<div id="detailObjInfoScript"></div>
	  </div>
	 </div>
</div>
</body>
</html>