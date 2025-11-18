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
        
        $("#stndAsrt").change(function() {
        	$("#"+firstSelectBoxId[0] +" option:eq(0)").attr("selected", "selected");
        	double_selectStndAsrt(dmnginfotpJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
    	      $('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
    	    	  double_selectStndAsrt(dmnginfotpJson, $(this),$("#stndAsrt option:selected").val());
    	      });
        });

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
	loadDetail();
	
	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	if(!isBlankStr("${dmnId}")) {
		var param = "dmnId="+"${dmnId}";
		grid_sheet.DoSearch("<c:url value="/dq/stnd/getDomainlist.do" />", param);
		
	}
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	
	//$( "#layer_div" ).show();
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.DMN.LST.1'/>|"
							+"<s:message code='META.HEADER.DMN.LST.2'/>|"
							+"<s:message code='META.HEADER.DMN.LST.3'/>|"
							+"<s:message code='META.HEADER.DMN.LST.4'/>", Align:"Center"}
					];
					//No.|표준분류|도메인ID|도메인논리명|도메인물리명|논리명기준구분|물리명기준구분|도메인그룹|인포타입|데이터타입|길이|소수점
					//상위도메인ID|주제영역ID|목록엔티티ID|목록엔티티물리명|목록엔티티논리명|목록어트리뷰트ID|목록어트리뷰트물리명
					//목록어트리뷰트논리명|코드값유형코드|코드값부여방식코드|대분류코드|표준용어자동생성여부|암호화여부|데이터형식|담당사용자ID|도메인출처구분
					//요청번호|요청일련번호|영문전체의미|설명|버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:130,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:130,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:60,   SaveName:"lnmCriDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:60,   SaveName:"pnmCriDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:130,   SaveName:"dmngId",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:130,   SaveName:"infotpId",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:130,   SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataScal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"uppDmnId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"subjId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstAttrId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstAttrPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstAttrLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"cdValTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:120,   SaveName:"cdValIvwCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"dmnDscd",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:140,   SaveName:"sditmAutoCrtYn",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",  Width:80,   SaveName:"encYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",  Width:80,   SaveName:"dupYn",   	Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"dataFrm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"crgUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"dmnOrgDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"dmnEngMean",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:500,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objVers",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:30,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"frsRqstUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:30,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"rqstUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:30,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:30,  SaveName:"aprvUserId",  Align:"Left", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
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
        	
			//그리드 초기화 한다.
			initDtlGrids();
			
			var param = $("#frmSearch").serialize();

			$('#dmn_sel_title').html('');
			loadDetail();
			grid_sub_dmnchange.RemoveAll();
			grid_sub_dmninit.RemoveAll();
			grid_sub_dmnvalue.RemoveAll();
			grid_sub_dmnwhereused.RemoveAll();
			grid_sub_dmnvaluechange.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/dq/stnd/getDomainlist.do" />", param);
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
		
		//도메인 구성정보 그리드 초기화
		initsubgrid_dmninit();
		//도메인 유효값 그리드 초기화
		initsubgrid_dmnvalue();
		//도메인 변경 이력
		initsubgrid_dmnchange();
		//유효값 변경 이력 그리드 초기화
		initsubgrid_dmnvaluechange();
		//도메인 - where used  그리드 초기화
		initsubgrid_dmnwhereused();
		//사전비교 도메인
	 	initsubgrid_diccomparisonDmn();
		
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
	var tmphtml = '<s:message code="DMN.LGC.NM" /> : ' + param.dmnLnm; /*도메인논리명*/
	$('#dmn_sel_title').html(tmphtml);
	
	
	
	var param1 = "dmnId="+param.dmnId;
	
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(dmnId);
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/dq/stnd/ajaxgrid/dmninfo_dtl.do"/>', param, function(){
		
		if(!isBlankStr(param)) {
			initDtlGrids();
			grid_sub_dmnchange.DoSearch('<c:url value="/dq/stnd/ajaxgrid/dmnchange_dtl.do" />', param);
			grid_sub_dmninit.DoSearch('<c:url value="/dq/stnd/ajaxgrid/dmninit_dtl.do" />', param);
			grid_sub_dmnvalue.DoSearch('<c:url value="/dq/stnd/ajaxgrid/dmnvalue_dtl.do" />', param);
			grid_sub_dmnvaluechange.DoSearch('<c:url value="/dq/stnd/ajaxgrid/dmnvaluechange_dtl.do" />', param);
			grid_sub_dmnwhereused.DoSearch('<c:url value="/dq/stnd/ajaxgrid/dmnwhereused_dtl.do" />', param);
			grid_sub_diccomparisonDmn.DoSearch('<c:url value="/dq/stnd/ajaxgrid/dmncomparison_dtl.do" />', param);
		}
			
		
		//$('#tabs').show();
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
	
	if(!isBlankStr("${dmnId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="DMN.LGC.NM" /> : ' + param.dmnLnm; /*도메인논리명*/
		$('#dmn_sel_title').html(tmphtml);
		
		var dmnId = "";
		dmnId = grid_sheet.GetCellValue(1, "dmnId");
		param = "dmnId="+dmnId;
		loadDetail(param);
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
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>

                            <th scope="row"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					        <td>
						        <select  id="stndAsrt" name="stndAsrt"  class="wd300">
	 					                <option value=""><s:message code="WHL"/></option>
						                <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
						        		  <option value="${code.codeCd}">${code.codeLnm}</option>
						        		</c:forEach> 
						        </select>
					        </td>
							<th scope="row"><label for="infotpId"><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 -->
							<td>
								<div id="selectBoxDiv"> <span></span></div>
<%-- 								<select id="dmngId" class="wd100" name="dmngId"> --%>
<!-- 									<option value="">전체</option> -->
<%-- 								</select> --%>
<%-- 								<select id="infotpId" class="wd100" name="infotpId"> --%>
<!-- 									<option value="">전체</option> -->
<%-- 							 	</select> --%>
							</td>
							<th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
							<td><input type="text" id="dmnLnm" name="dmnLnm" class="wd200"/></td>
							<th scope="row"><label for="cdValTypCd"><s:message code="CD.VAL.PTRN" /></label></th> <!-- 코드값유형 -->
                                <td><select id="cdValTypCd" name="cdValTypCd">
                                		<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.cdValTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
						</tr>
						<tr>
                            <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
							<td>
								<select id="dataType" name="dataType">
                            		<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
                                    <c:forEach var="code" items="${codeMap.dataTypeCd}" varStatus="status" >
                                    <option value="${code.codeCd}">${code.codeLnm}</option>
                                    </c:forEach>
                                </select>
                            
							</td>
						<th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
							<td >
							      <select id = "encYn" name="encYn">
							          <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
							          <option value="Y"><s:message code="MSG.YES" /></option>
							          <option value="N"><s:message code="MSG.NO" /></option>
							      </select>
							</td>
							
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
							<td colspan="7" ><input type="text" id="objDescn" name="objDescn" class="wd95p"/></td>
							
						
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
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
	    <li><a href="#tabs-1"><s:message code="DMN.INFO" /></a></li> <!-- 도메인정보 -->
<%-- 	    <li><a href="#tabs-1"><s:message code="INFO" /></a></li> <!-- 영문판용(한글버젼시 위 주석 사용) --> --%>
	    <li><a href="#tabs-2"><s:message code="DMN.CMPS" /></a></li> <!-- 도메인구성 -->
<%-- 	    <li><a href="#tabs-2"><s:message code="CONF" /></a></li> <!-- 영문판용(한글버젼시 위 주석 사용) --> --%>
	    <li><a href="#tabs-3"><s:message code="VLD.VAL" /></a></li> <!-- 유효값 -->
<!-- 	    <li><a href="#tabs-4">유효값관계</a></li> -->
	    <li><a href="#tabs-5"><s:message code="CHG.HSTR" /></a></li> <!-- 변경이력 -->
	    <li><a href="#tabs-6"><s:message code="VLD.VAL.CHG.HSTR" /></a></li> <!-- 유효값변경이력 -->
	    <li><a href="#tabs-7">Where Used</a></li>
	    <li><a href="#tabs-8"><s:message code="DIC.COMPARISON" /></a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="dmninit_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
			<%@include file="dmnvalue_dtl.jsp" %>
	  </div>
<!-- 	   <div id="tabs-4"> -->
<%-- 			<%@include file="dmnvaluerel_dtl.jsp" %> --%>
<!-- 	  </div> -->
	   <div id="tabs-5">
			<%@include file="dmnchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-6">
			<%@include file="dmnvaluechange_dtl.jsp" %>
	  </div>
	   <div id="tabs-7">
			<%@include file="dmnwhereused_dtl.jsp" %>
	  </div>
	  <div id="tabs-8">
			<%@include file="dicComparisonDmn_dtl.jsp" %>
	  </div>
	 </div>
</div>

</body>
</html>