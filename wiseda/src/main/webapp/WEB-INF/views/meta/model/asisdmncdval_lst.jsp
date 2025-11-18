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
	if(!isBlankStr("${dmnId}")) {
		var param = "dmnId="+"${dmnId}";
		grid_sheet.DoSearch("<c:url value="/meta/model/getasisdmncdvallist.do" />", param);
		
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
                    {Text:"<s:message code='META.HEADER.ASIS.DMN.CD.VAL.LST'/>", Align:"Center"}
                ];
        //No.|상태|선택|ASIS유효값ID|시스템명|시스템구분|업무구분|ASISDB명|테이블명|컬럼명|코드명|참조테이블명|참조컬럼명|코드값|표시순번|코드값설명|응용팀|KB담당자|SI담당자|전환팀|전환담당자|최종수정일자|비고1|비고2|비고3|비고4|요청번호|요청일련번호|설명|버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    Width:20,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
                    
					{Type:"Text",   Width:100,  SaveName:"dmnId",  Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"asisSystemNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"asisDit",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"asisGroupNm",  Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"asisDbNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"asisTblNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"asisColNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"codeNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"dfdColNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"dfdColValue",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"codeValue",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"codeDispNum",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"objDescn",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"appTeamNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"appChrgNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"appSiNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"tsfTeamNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"tsfChrgNm",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:130,  SaveName:"lastChgDt",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
// 					{Type:"Text",   Width:100,  SaveName:"lastChgDt",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"addNote1",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"addNote2",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"addNote3",  Align:"Left", Edit:0, KeyField:0},
					{Type:"Text",   Width:100,  SaveName:"addNote4",  Align:"Left", Edit:0, KeyField:0},
					
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
	$('div#detailInfo1').load('<c:url value="/meta/model/ajaxgrid/asisdmncdvalinfo_dtl.do"/>', param, function(){
		if(!isBlankStr(param)) {
			//그리드 초기화 한다.
			//initDtlGrids();
			
			grid_sub_messagechange.DoSearch('<c:url value="/meta/model/ajaxgrid/asisdmncdvalchange_dtl.do" />', param);
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
			grid_sheet.DoSearch("<c:url value="/meta/model/getasisdmncdvallist.do" />", param);
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
	var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '코드값 : ' + param.codeValue;
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(dmnId);
	
	
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
	if(!isBlankStr("${dmnId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '메시지코드 : ' + param.msgCd;
		$('#program_sel_title').html(tmphtml);
		
		
		var dmnId = "";
		dmnId = grid_sheet.GetCellValue(1, "dmnId");
		param = "dmnId="+dmnId;
		
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
<!-- 						<tr> -->
<!-- 							<th scope="row" ><label for="asisSystemNm">시스템명</label></th> -->
<!-- 							<td> -->
<!-- 								<input type="text" id="asisSystemNm" name="asisSystemNm" class="wd200"   /> -->
<!-- 							</td> -->
<!-- 							<th scope="row" ><label for="asisDit">시스템구분</label></th> -->
<!-- 							<td> -->
<!-- 								<input type="text" id="asisDit" name="asisDit" class="wd200"   /> -->
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
							<th scope="row" ><label for="asisDbNm">ASISDB명</label></th>
							<td>
								<input type="text" id="asisDbNm" name="asisDbNm" class="wd200"   />
							</td>
							<th scope="row" ><label for="asisTblNm">테이블명</label></th>
							<td>
								<input type="text" id="asisTblNm" name="asisTblNm" class="wd200"  />
							</td>
						</tr>
						<tr>
							<th scope="row" ><label for="asisColNm">컬럼명</label></th>
							<td>
								<input type="text" id="asisColNm" name="asisColNm" class="wd200"   />
							</td>
							<th scope="row" ><label for="codeValue">코드값</label></th>
							<td>
								<input type="text" id="codeValue" name="codeValue" class="wd200"   />
							</td>
						</tr>
						<tr>
							<th scope="row" ><label for="appTeamNm">응용팀</label></th>
							<td>
								<input type="text" id="appTeamNm" name="appTeamNm" class="wd200"  />
							</td>
							<th scope="row" ><label for="appChrgNm">KB담당자 &nbsp/&nbsp SI담당자</label></th>
							<td>
								<input type="text" id="appChrgNm" name="appChrgNm" class="wd200"   />
								&nbsp/&nbsp
								<input type="text" id="appSiNm" name="appSiNm" class="wd200"   />
							</td>
						</tr>
						<tr>
							<th scope="row" ><label for="tsfTeamNm">전환팀</label></th>
							<td>
								<input type="text" id="tsfTeamNm" name="tsfTeamNm" class="wd200"   />
							</td>
							<th scope="row" ><label for="tsfChrgNm">전환담당자</label></th>
							<td >
								<input type="text" id="tsfChrgNm" name="tsfChrgNm" class="wd200"  />
							</td>
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
	    <li><a href="#tabs-1">유효값 정보</a></li>
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
			<%@include file="asisdmncdvalchange_dtl.jsp" %>
<!-- 			<div id="detailInfo2"></div> -->
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</body>
</html>
