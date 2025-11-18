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
        
    	//$( "#tabs" ).tabs();
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
    	
    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
    	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	//PK값으로 검색
	var dbSchId ="";
	var dbcTblNm ="";
	dbSchId="${search.dbSchId}";
	dbcTblNm="${search.dbcTblNm}";
	param ="dbSchId="+dbSchId;
	param +="&dbcTblNm="+dbcTblNm;
	if(( dbSchId != null && dbSchId != "" && dbSchId !="undefined" ) || (dbcTblNm != null && dbcTblNm != "" && dbcTblNm !="undefined")){
		grid_sheet.DoSearch("<c:url value="/meta/dbc/getDbclist.do" />", param);
	}
	

	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	loadDetail();
	loadDetailCol();
	
	//$( "#layer_div" ).show();
		
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.DBCTBL.LST'/>", Align:"Center"}
						/* No.|DB스키마ID|DB접속대상명|DB스키마명|주제영역ID|주제영역명|DBC테이블명|DBC테이블한글명|DB접속대상ID|DDL테이블ID|DDL테이블명|물리모델테이블ID|물리모델테이블명|DEPENDENCY CNT|생성일시|등록유형 */
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:120,   SaveName:"dbSchId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"dbConnTrgLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dbSchLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"subjId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"subjLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:240,   SaveName:"dbcTblNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dbcTblKorNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbConnTrgId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlTblId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"ddlTblLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"pdmTblId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"pdmTblLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"depcCnt",	 	Align:"Center", Edit:0, Hidden:0},
						
						{Type:"Date",   Width:150,   SaveName:"crtDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Combo",   Width:120,   SaveName:"regTyp",	 	Align:"Center", Edit:0, Hidden:0}
						
					];
                    
        InitColumns(cols);

		SetColProperty("regTyp",	${codeMap.regTypCdibs});
        
        //SetColHidden("rqstUserNm",1);
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
			
			//각 시트 초기화....
			grid_sub_dbctblcollist.RemoveAll();
			grid_sub_dbctblidxcol.RemoveAll();
			grid_sub_dpnd.RemoveAll();
			loadDetail();
			loadDetailCol();

			
			
			grid_sheet.DoSearch("<c:url value="/meta/dbc/getDbclist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'DBC테이블조회'});
            
            break;

    }       
}
    

function grid_sheet_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;

	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var pk = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId") + "&dbcTblNm=" + grid_sheet.GetCellValue(row, "dbcTblNm");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DB.C.TBL.NM" /> : ' + param.dbcTblNm +' [ <s:message code="SCHEMA.NM" /> : ' + param.dbSchLnm + ' ]'; //DBC 테이블명, 스키마명
	$('#dbc_sel_title').html(tmphtml);
	
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(pk);
	

	

}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/dbc/ajaxgrid/dbctblinfo_dtl.do"/>', param, function(){
		if(param == null || param == "" || param =="undefined"){
			
		} else {
			
			grid_sub_dbctblcollist.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbctblcollist_noncolor_dtl.do" />', param);
			grid_sub_dbctblidxcol.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbctblidxcol_dtl.do" />', param);
			grid_sub_dpnd.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbcdpnd_dtl.do" />', param);	//dependency
		}
		//$('#tabs').show();
	});
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
	    <div class="menu_title"><s:message code="DB.C.TBL.INQ" /></div> <!-- DBC 테이블조회 -->
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
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
							<td><select id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select id="dbSchId" class="" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select></td>
							<th scope="row"><label for="subjId"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
							<td colspan="3"><select id="subjId" class="" name="subjId">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.subjLnm}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select></td>
						</tr>
						<tr>
							<th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th> <!-- 테이블명 -->
							<td><input type="text" id="dbcTblNm" name="dbcTblNm" class="wd200"/></td>
							<th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
							<td><input type="text" id="dbcColNm" name="dbcColNm" class="wd200"/></td>
							<th scope="row"><label for="subjLnm"><s:message code="DPNDCY.YN" /></label></th><!-- DEPENDENCY존재여부 -->
							<td>
                                	<select id="depcYn"  name="depcYn" class="wd100" >
									<option value="" selected><s:message code="WHL" /></option><!-- 전체 -->
									<option value="Y">Y</option>
									<option value="N">N</option>
									</select>  
                            </td>
						</tr>
						
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
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
		    <div class="selected_title" id="dbc_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DB.C.TBL.DTL.INFO" /></a></li> <!-- DBC테이블 상세정보 -->
	    <li><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
	    <li><a href="#tabs-4"><s:message code="IDEX.CLMN" /></a></li> <!-- 인덱스컬럼 -->
	    <li><a href="#tabs-3" id="colinfo"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 -->
		<li><a href="#tabs-5" id="dependency">DEPENDENCY</a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="dbctblcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailInfo2"></div>
	  </div>
	   <div id="tabs-4">
			<%@include file="dbctblidxcol_dtl.jsp" %>
	  </div>
	  <div id="tabs-5">
			<%@include file="dbcdependency_dtl.jsp" %>
	  </div>
	 </div>
</div>
</body>
</html>