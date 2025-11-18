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

$(document).ready(function() {
		//$( "#tabs" ).tabs();
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
//         $("#btnTreeNew").hide();
//         $("#btnSave").hide();
//         $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

    	//$( "#tabs" ).tabs();
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #appSditmLnm"), "SDITM");
    	
    	//기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});
		
     	//달력팝업 추가...
     	$( "#searchBgnDe" ).datepicker();
    	$( "#searchEndDe" ).datepicker();
    	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	loadDetail();
	
	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	if(!isBlankStr("${appSditmId}")) {
		var param = "appSditmId="+"${appSditmId}";
		grid_sheet.DoSearch("<c:url value="/meta/app/getsditmlist.do" />", param);
	} 
	
	var linkFlag = "";
	linkFlag = "${linkFlag}";
	if(linkFlag == '1') {
		doAction("Search");
	}
	
	
	//$( "#layer_div" ).show();
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.APPSDITM.LST'/>", Align:"Center"}
						/* No.|비표준용어ID|비표준용어논리명|비표준용어물리명|논리명기준구분|물리명기준구분|전체영문의미|설명|요청번호|요청일련번호|등록유형코드|버전|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID */
					];
			//
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"appSditmId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:300,  SaveName:"appSditmLnm",   	Align:"Left", Edit:0},
						{Type:"Text",   Width:300,  SaveName:"appSditmPnm",   	Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"lnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"pnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:500,  SaveName:"fullEngMean",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:500,  SaveName:"objDescn",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,  SaveName:"rqstNo",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"rqstSno",   	Align:"Right", Edit:0, Hidden:1},
						{Type:"Combo",   Width:80,  SaveName:"regTypCd",   	Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"objVers",   	Align:"Right", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,  SaveName:"frsRqstDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:100,  SaveName:"frsRqstUserId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,  SaveName:"rqstDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:100,  SaveName:"rqstUserId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,  SaveName:"aprvDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,  SaveName:"aprvUserId",   	Align:"Left", Edit:0, Hidden:1}
						
					];
                    
        InitColumns(cols);
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});

      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);
      
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
        	
        	//그리드 초기화 한다.
			initDtlGrids();
        	
			var param = $("#frmSearch").serialize();
			loadDetail();
			$('#app_sditm_sel_title').html('');
// 			grid_sub_sditmwhereused.RemoveAll();
// 			grid_sub_sditmchange.RemoveAll();
// 			grid_sub_sditminit.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/meta/app/getsditmlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'전문용어조회'});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}

//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		//APP항목 - 단어구성정보 그리드 초기화
// 		initsubgrid_sditminit();
		//APP항목 - 변경이력 그리드 초기화
// 		initsubgrid_sditmchange();
		//APP항목 - Where used 그리드 초기화
// 		initsubgrid_sditmwhereused();
		
	 	chkinitdtlgrids = true;	
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
	var appSditmId = "&appSditmId="+grid_sheet.GetCellValue(row, "appSditmId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="APP.ITEM.LGC.NM" /> : ' + param.appSditmLnm;  //APP항목논리명
	$('#app_sditm_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(appSditmId);
	
	
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/app/ajaxgrid/appsditminfo_dtl.do"/>', param, function(){
		
		if(!isBlankStr(param)) {
			initDtlGrids();
// 			grid_sub_sditmchange.DoSearch('<c:url value="/meta/app/ajaxgrid/sditmchange_dtl.do" />', param);
// 			grid_sub_sditminit.DoSearch('<c:url value="/meta/app/ajaxgrid/sditminit_dtl.do" />', param);
// 			grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/app/ajaxgrid/sditmwhereused_dtl.do" />', param);	
		}
		
		//$('#tabs').show();
	});
}

function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	if(!isBlankStr("${appSditmId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="APP.ITEM.LGC.NM" /> : ' + param.appSditmLnm +' [ <s:message code="APP.ITEM.ID" /> : ' + param.appSditmId + ' ]';
		//APP항목논리명, APP항목ID
		$('#app_sditm_sel_title').html(tmphtml);
		
		var appSditmId = "";
		appSditmId = grid_sheet.GetCellValue(1, "appSditmId");
		param = "appSditmId="+appSditmId;
		
		loadDetail(param);
// 		grid_sub_sditmchange.DoSearch('<c:url value="/meta/app/ajaxgrid/sditmchange_dtl.do" />', param);
// 		grid_sub_sditminit.DoSearch('<c:url value="/meta/app/ajaxgrid/sditminit_dtl.do" />', param);
// 		grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/app/ajaxgrid/sditmwhereused_dtl.do" />', param);
	}
}

</script>
</head>

<body>

 <div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="APP.ITEM.INQ" /></div> <!-- APP항목 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='APP.ITEM.INQ' />"><!-- APP항목조회 -->
                   <caption><s:message code="APP.ITEM.INQ.FORM" /></caption> <!-- APP항목 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                   		<tr>
							<th scope="row"><label for="appSditmLnm"><s:message code="APP.ITEM.NM" /></label></th> <!-- APP항목명 -->
							<td colspan="3"><input type="text" id="appSditmLnm" name="appSditmLnm" class="wd200"/></td>
						</tr>
						<tr>
                                         <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                         	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                         	<a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                                         </td>
                                         <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td>
  						    </tr>
  						    
  						   <tr>
							<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
							<td colspan="3"><input type="text" id="objDescn" name="objDescn" class="wd98p" /></td>
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
		    <div class="selected_title" id="app_sditm_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="ITEM.INFO" /></a></li> <!-- 항목정보 -->
<!-- 	    <li><a href="#tabs-2">항목구성</a></li> -->
<!-- 	    <li><a href="#tabs-3">변경이력</a></li> -->
<!-- 	    <li><a href="#tabs-4">종속성</a></li> -->
<!-- 	    <li><a href="#tabs-5">Where Used</a></li> -->
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
<!-- 	  <div id="tabs-2"> -->
<%-- 			<%@include file="appsditminit_dtl.jsp" %> --%>
<!-- 	  </div> -->
<!-- 	  <div id="tabs-3"> -->
<%-- 			<%@include file="appsditmchange_dtl.jsp" %> --%>
<!-- 	  </div> -->
<!-- 	   <div id="tabs-4"> -->
<%-- 			<%@include file="sditmdeploy_dtl.jsp" %> --%>
<!-- 	  </div> -->
<!-- 	   <div id="tabs-5"> -->
<%-- 			<%@include file="appsditmwhereused_dtl.jsp" %> --%>
<!-- 	  </div> -->
	 </div>
	 </div>
</body>
</html>