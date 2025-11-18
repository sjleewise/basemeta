	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title></title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
var interval = "";
var initTblGrid = false;
//var initIdxGrid = false;
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
        
		// 저장 Event Bind
		$("#btnSave").click(function(){
			//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save");
		}).show();
		
		//DDL조회 Event Bind
		$("#btnDelete").click(function(){
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code='MSG.INQ.DDL.CHC' />"); //조회할 DDL을 선택해 주세요.
        		return;
        	}
        	

        	doAction("ShowDdl");
		}).hide().find(".ui-button-text").text("<s:message code='DDL.INQ' />"); //DDL 조회
    	
// 		$( "#tabsTbl" ).hide();
// 		$( "#tabsIdx" ).hide();
		
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
	  //기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});

    	//주제영역 검색 팝업 호출
    	$("#subjSearchPop").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", 800, 600, param);
//     		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
//     		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
        });
    	
    	$("#subjSearchDel").click(function(){
    		$("#frmSearch #subjId").val("");
    		$("#frmSearch #fullPath").val("");
        }).show();
    	
     	loadDetail();
});

$(window).on('load',function() {
// 	alert('window.load');
			
	
		
	initGrid();
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	$( "#tabsTbl" ).tabs().show();
	
	//$( "#layer_div" ).show();
	
});


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmSearch #subjId").val(retjson.subjId);
	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.PCIMNG.LST'/>", Align:"Center"}
						/* No.|상태|등록유형|주제영역|DBMS ID|DBMS명|스키마ID|DB스키마명|테이블구분|오브젝트ID|테이블명|테이믈물리명|분리보관여부|테이블종류 */
					];
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Status", Width:50,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"fullPath",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:10,   SaveName:"dbConnTrgId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"dbConnTrgLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:10,   SaveName:"dbSchId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"dbSchLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:80,   SaveName:"objDcd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"objId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:250,   SaveName:"objLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:250,   SaveName:"objPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"CheckBox",   Width:90,   SaveName:"pciYn", 		Align:"Center", Edit:1, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"tblTypCd", 		Align:"Center", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("objDcd",	${codeMap.objDcdibs});
		SetColProperty("tblTypCd",	${codeMap.tblTypCdibs});
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
        FitColWidth();  
        
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

			grid_sheet.DoSearch("<c:url value="/meta/ddl/getPciYnList.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.w == 0) return;
        	
        	var url = '<c:url value="/meta/ddl/savePciYnPrc.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
       
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}
    
    
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
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

	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다... (현재는 DDL만)
	
	var param =  grid_sheet.GetRowJson(row);

	
		$( "#tabsTbl" ).tabs().show();
        if(!initTblGrid){
           initsubgrid_ddltblcollist();
           //initsubgrid_ddltblchange();
           initsubgrid_ddltblidxcol();
           //initsubgrid_ddltblcolchange();
           initTblGrid = true;
        }
		var ddlTblId = "&ddlTblId="+grid_sheet.GetCellValue(row, "objId");
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="DDL.TBL.NM" /> : ' + param.objLnm; //DDL 테이블명
		$('#ddl_sel_title').html(tmphtml);
		
		
		//메뉴ID를 토대로 조회한다....
		loadDetail(ddlTblId);
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


function loadDetail(param) {
		$('div#detailInfo1').load('<c:url value="/meta/ddl/ajaxgrid/ddltblinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
// 				loadDetailScript(null, 'TBL');
				//grid_sub_ddltblchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblchange_dtl.do" />', param);
				grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />', param);
				grid_sub_ddltblidxcol.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblidxcol_dtl.do" />', param);
				//grid_sub_ddltblcolchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcolchange_dtl.do" />', param);
			}
			//$('#tabs').show();
		});
}
</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DDL.TBL.DEMD.MNG" /></div> <!-- DDL테이블 요청관리 -->
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
						    <th scope="row"><label for="subjId"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                            <input type="hidden" id="subjId" name="subjId" readonly="readonly"/>
							<input type="text" class="wd60p" id="fullPath" name="fullPath" readonly="readonly"/>
						 	<button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					        <button class="btnDelPop" id="subjSearchDel" ><s:message code="DEL" /></button> <!-- 삭제 -->
                            </td>
							<th scope="row"><label for="objLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
							<td><input type="text" id="objLnm" name="objLnm" class="wd200" /></td>		
						</tr>
						<tr>
							<th scope="row"><label for="pciYn"><s:message code="SPRT.STGE.YN" /></label></th> <!-- 분리보관여부 -->
							<td><select id="pciYn" class="wd100" name="pciYn">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
								    <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
								</select></td>
							<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
							<td><select id="regTypCd" class="wd100" name="regTypCd">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select></td>
							
						</tr>
						<tr style="display:none">
                                         <th><s:message code="TRTT.DT" /></th><!-- 처리일자 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd100" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd100" /></td>
                                         <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                         	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                         	<a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                                         </td>
  						    </tr> 
						
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="tabsTbl" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DDL.TBL.DTL.INFO" /></a></li> <!-- DDL테이블 상세정보 -->
	    <li><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
	    <li><a href="#tabs-6"><s:message code="IDEX.CLMN" /></a></li> <!-- 인덱스컬럼 -->
	    <li><a href="#tabs-3" id="colinfo"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 -->
<!--    <li><a href="#tabs-5">테이블 이력</a></li>
	    <li><a href="#tabs-4">컬럼이력</a></li> -->
<!--  	    <li><a href="#tabs-7">DDL</a></li>-->
<!-- 	    <li><a href="#tabs-7">부가정보</a></li> -->
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddltblcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-6">
			<%@include file="ddltblidxcol_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailInfo2"></div>
	  </div>
	  <!-- 
	   <div id="tabs-5">
			<%@include file="ddltblchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddltblcolchange_dtl.jsp" %>
	  </div>-->
	<!--    <div id="tabs-7">
 			<div id="detailInfoScript"></div> -->
	  </div>
	 </div>
	 </div>
	 </div>
</body>
</html>