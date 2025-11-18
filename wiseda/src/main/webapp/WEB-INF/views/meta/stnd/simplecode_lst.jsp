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

var dmnginfotpJson = ${codeMap.dmnginfotp} ;

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
        
        
        var bscLvl = parseInt("${bscLvl}");
        var selectBoxId = "${selectBoxId}";
        var firstSelectBoxId = selectBoxId.split("|");
        
     	//divID,  selectbox건수, selectbox ID
        create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", "<s:message code='WHL' />"); //전체
        
    	double_select(dmnginfotpJson, $("#"+firstSelectBoxId[0]));
    	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
    		double_select(dmnginfotpJson, $(this));
    	});
    	
   		//코드도메인으로 도메인그룹 기본선택
    	if("${codeDmngId}" != "") {
        	$("#"+firstSelectBoxId[0]).val("${codeDmngId}");
        	$("#"+firstSelectBoxId[0]).change();
        	$("#"+firstSelectBoxId[0]).attr("disabled", true);
    	}
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #dmnLnm"), "DMN");
    	
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
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        //ColMerge의 기본값은 1임...
        //SetMergeSheet(msPrevColumnMerge);
        //SetMergeSheet(msPrevColumnMerge);
		var headers = [
						{Text:"<s:message code='META.HEADER.SIMPLECODE.LST'/>", Align:"Center"}
					];
                    //No.|도메인ID|도메인논리명|도메인물리명|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0, ColMerge:0},
			            {Type:"Text",   Width:40,    SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},  
 				        {Type:"Text",   Width:120,   SaveName:"dmnLnm" , 		Align:"Left", Edit:0, Hidden:0},            
 				        {Type:"Text",   Width:120,   SaveName:"dmnPnm" , 		Align:"Left", Edit:0, Hidden:0},
 						{Type:"Text",   Width:100,   SaveName:"lccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"mccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"sccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"lclsNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"mdcfNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"sclsNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"useYn"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"dispOrd"   , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc1"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm1"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc2"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm2"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc3"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm3"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc4"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm4"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc5"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm5"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"rmrkCntn"  , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"outlCntn1" , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"outlCntn2" , 		Align:"Left", Edit:0, Hidden:0},                    
 				        {Type:"Text",   Width:60,   SaveName:"cdValId" , 		Align:"Left", Edit:0, Hidden:1}           

					];
                    
        InitColumns(cols);
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
         FitColWidth();  
        //SetExtendLastCol(1);  
        grid_sheet.SetSheetHeight(400);
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

			//$('#dmn_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getSimpleCodeList.do" />", param);
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
	var tmphtml = ''<s:message code="CD.DMN.NM" /> : ' : ' + param.dmnLnm;/*코드도메인명*/
	//$('#dmn_sel_title').html(tmphtml);
	
	
	
	var param1 = "dmnId="+param.dmnId;
	
	
	//메뉴ID를 토대로 조회한다....
	//loadDetail(dmnId);
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
	
	if (grid_sheet.SearchRows() == 1){
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = ''<s:message code="CD.DMN.NM" /> : ' : ' + param.dmnLnm;/*코드도메인명*/
		//$('#dmn_sel_title').html(tmphtml);
		
		var dmnId = "";
		dmnId = grid_sheet.GetCellValue(1, "dmnId");
		param = "dmnId="+dmnId;
	
	
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
                   <col style="width:11%;" />
                   <col style="width:22%;" />
                   <col style="width:11%;" />
                   <col style="width:22%;" />
                   <col style="width:11%;" />
                   <col style="width:22%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
							<td><input type="text" id="dmnLnm" name="dmnLnm" class="wd200"/></td>
								<th scope="row"><label for="cdVal"><s:message code="L.M.S.CL.CD" /></label></th> <!-- (대/중/소)분류코드 -->
							<td><input type="text" id="cdVal" name="cdVal" class="wd200"/></td>
							<th scope="row"><label for="cdValNm"><s:message code="L.M.S.CLS.NM" /></label></th> <!-- (대/중/소)분류명 -->
							<td><input type="text" id="cdValNm" name="cdValNm" class="wd200"/></td>
						</tr>
					
					<!-- 	<tr>
                                         <th class="bd_none">기간</th> 
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt">1일</a> 
                                         	<a href="#" class="tb_bt">3일</a> 
                                         	<a href="#" class="tb_bt" id="seven">7일</a> 
                                         	<a href="#" class="tb_bt">1개월</a> 
                                         	<a href="#" class="tb_bt">3개월</a> 
                                         	<a href="#" class="tb_bt">6개월</a> 
                                         </td>
                                         <th>조회기간</th> 
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td>
  						    </tr> -->
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DATA.CLMN.CHC.CTRL.C.USE" /></div>
        <!-- 데이터를 복사하시려면 복사할 컬럼을 선택하시고 Ctrl + C를 사용하시면 됩니다. -->
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
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	</div>
</body>
</html>