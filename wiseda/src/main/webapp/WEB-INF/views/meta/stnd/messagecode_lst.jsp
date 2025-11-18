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
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
    
		//기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});

    }
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	loadDetail();
	
	//$( "#tabs" ).tabs();
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();

	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	if(!isBlankStr("${actnMsgId}")) {
		var param = "actnMsgId="+"${actnMsgId}";
		grid_sheet.DoSearch("<c:url value="/meta/stnd/getMessageCodeList.do" />", param);
		
	}
	
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
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
                    {Text:"<s:message code='META.HEADER.MESSAGE.CODE.LST'/>", Align:"Center"}
                ];
        //No.|상태|선택|조치코드ID|조치코드|언어구분코드|조치코드사용여부|조치코드삭제여부|조치코드적용일자|조치내용|요청번호|요청일련번호|설명|버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    Width:20,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
                    
                    {Type:"Text",   Width:40,   SaveName:"actnMsgId",    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"actnCd",  Align:"Left", Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"errTlgLanDscd",  Align:"Left", Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"actnUseYn",  Align:"Left", Edit:0}, 
					{Type:"Combo",  Width:100,  SaveName:"actnDelYn",  Align:"Left", Edit:0}, 
					{Type:"Text",   Width:130,  SaveName:"actnCdAplDt",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:200,  SaveName:"actnMsgCtnt",  Align:"Left", Edit:0}, 
					
                    {Type:"Text",   Width:40,  SaveName:"rqstNo",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"rqstSno",    Align:"Right", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"objDescn",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Right", Edit:0, Hidden:1}, 
                    {Type:"Combo",   Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:80,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:20,  SaveName:"frsRqstUserId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Date",  Width:20,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:30,  SaveName:"rqstUserId",    Align:"Left", Edit:0, Hidden:1},          
                    {Type:"Date",   Width:30,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:30,  SaveName:"aprvUserId",    Align:"Left", Edit:0, Hidden:1}
                    
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});

		SetColProperty("errTlgLanDscd",	${codeMap.errTlgLanDscdibs});
		SetColProperty("actnUseYn",	 {ComboCode:"N|Y", ComboText:"미사용|사용"});
		SetColProperty("actnDelYn",	 {ComboCode:"N|Y", ComboText:"미삭제|삭제"});
		
      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);

        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/stnd/ajaxgrid/messagecode_dtl.do"/>', param, function(){
		if(!isBlankStr(param)) {
			//그리드 초기화 한다.
			//initDtlGrids();
			
			grid_sub_messagechange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/messagecodechange_dtl.do" />', param);
		}
		
		//$('#tabs').show();
	});
}


		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	//그리드 초기화 한다.
			//initDtlGrids();
			var param = $("#frmSearch").serialize();
			$('#program_sel_title').html('');
			loadDetail();
			//grid_sub_stwdwhereused.RemoveAll();
			//grid_sub_stwdchange.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getMessageCodeList.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
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
		
		//표준단어 변경이력 그리드 초기화
	 	initsubgrid_stwdchange();

		//표준단어 - where used 초기화
	 	initsubgrid_stwdwhereused();
		
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
	var actnMsgId = "&actnMsgId="+grid_sheet.GetCellValue(row, "actnMsgId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '메시지코드명 : ' + param.msgCd;
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(actnMsgId);
	
	
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
	if(!isBlankStr("${actnMsgId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '메시지코드 : ' + param.msgCd;
		$('#program_sel_title').html(tmphtml);
		
		
		var actnMsgId = "";
		actnMsgId = grid_sheet.GetCellValue(1, "actnMsgId");
		param = "actnMsgId="+actnMsgId;
		
		loadDetail(param);
// 		grid_sub_stwdchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdchange_dtl.do" />', param);
// 		grid_sub_stwdwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/stwdwhereused_dtl.do" />', param);
	}
}




</script>
</head>

<body>

<div id="layer_div" >

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">채널오류메시지 조회</div>
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준단어조회">
                   <caption>메시지 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
						    <th scope="row"><label for="actnCd ">조치코드</label></th>
						     <td><input type="text" id="actnCd" name="actnCd" class="wd95p"/></td>
							<th scope="row"><label for="errTlgLanDscd">오류전문언어구분코드</label></th>
							<td>
								<select id="errTlgLanDscd" name="errTlgLanDscd">
								  <option value="">선택</option>
								<c:forEach var="code" items="${codeMap.errTlgLanDscd}" varStatus="status">
								  <option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
						    <th scope="row"><label for="actnMsgCtnt">조치내용</label></th>
						    <td colspan="3"><input type="text" id="actnMsgCtnt" name="actnMsgCtnt" class="wd95p"/></td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
</div>
<div style="clear:both; height:10px;"><span></span></div>

       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />         

	<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">메시지코드 정보</a></li>
	    <li><a href="#tabs-2">변경이력</a></li>
<!-- 	    <li><a href="#tabs-3">Where Used</a></li> -->
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo1"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-2">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="messagecodechange_dtl.jsp" %>
<!-- 			<div id="detailInfo2"></div> -->
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</body>
</html>
