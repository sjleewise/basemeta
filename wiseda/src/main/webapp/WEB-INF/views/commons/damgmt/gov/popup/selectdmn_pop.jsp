	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title><s:message code="DMN.INQ"/></title> <!-- 도메인 검색 -->

<script type="text/javascript">

var dmnginfotpJson = ${codeMap.dmnginfotp} ;

$(document).ready(function() {
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  });
        
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
			$("form[name=frmSearch]")[0].reset();
		});
                
		// 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );
        
    	double_select(dmnginfotpJson, $("#dmngId"));
    	$('select', $("#dmngId").parent()).change(function(){
    		double_select(dmnginfotpJson, $(this));
    	});
    	
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	parent.closeLayerPop();
        	
        });
        
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
    	
    	
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	initGrid();
	$(window).resize();

	if(!isBlankStr("${search.dmnLnm}")) {
		$("#frmSearch #dmnLnm").val("${search.dmnLnm}");
	}
	
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01")); 
});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "No.|<s:message code='META.HEADER.STNDDMN.POP.1'/>";
            headtext += "|<s:message code='META.HEADER.STNDDMN.POP.2'/>";
            headtext += "|<s:message code='META.HEADER.STNDDMN.POP.3'/>";
            headtext += "|<s:message code='META.HEADER.STNDDMN.POP.4'/>";

            //headtext = "No.|상태|선택";
            //headtext += "|도메인ID|표준분류|도메인논리명|도메인물리명|도메인그룹|인포타입|상위도메인ID";
            //headtext += "|코드값유형|코드값부여방식|목록엔티티|표준용어자동생성여부|암호화여부|데이터형식|담당사용자ID|도메인출처구분";
            //headtext += "|설명";
        
		var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	  Width:40,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
		                {Type:"Status",   Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
		                {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Sort:0, Hidden:1},
						{Type:"Text",     Width:40,   SaveName:"dmnId", 		Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Left", Edit:0},
						{Type:"Text",     Width:120,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",     Width:120,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0},
// 						{Type:"Text",     Width:60,   SaveName:"lnmCriDs",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",    Width:100,   SaveName:"dmngId",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",    Width:100,   SaveName:"infotpId",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",     Width:40,   SaveName:"uppDmnId",	 	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",     Width:40,   SaveName:"subjId",	 	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",     Width:40,   SaveName:"lstEntyId",	 	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",     Width:40,   SaveName:"lstEntyPnm",	Align:"Center", Edit:0},
						{Type:"Combo",    Width:100,   SaveName:"cdValTypCd",	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",    Width:120,   SaveName:"cdValIvwCd",	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",     Width:100,   SaveName:"lstEntyLnm",	Align:"Center", Edit:0},
						{Type:"Combo",    Width:140,   SaveName:"sditmAutoCrtYn",Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",    Width:140,   SaveName:"encYn"         ,lign:"Center", Edit:0, Hidden:1},
						{Type:"Text",     Width:40,   SaveName:"dataFrm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",     Width:40,   SaveName:"crgUserId",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",     Width:40,   SaveName:"dmnOrgDs",	 	Align:"Left", Edit:0, Hidden:1},
// 						{Type:"Text",     Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Text",     Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",     Width:150,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
// 						{Type:"Text",     Width:40,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
// 						{Type:"Date",   Width:30,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
// 						{Type:"Text",   Width:30,   SaveName:"frsRqstUserId",	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Date",   Width:30,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
// 						{Type:"Text",   Width:30,   SaveName:"rqstUserId",	 	Align:"Center", Edit:0, Hidden:1},
// 						{Type:"Date",   Width:30,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
// 						{Type:"Text",   Width:30,  SaveName:"aprvUserId",  Align:"Center", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);
        
        //콤보코드 셋팅...
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
// 		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		//SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
        
      	//히든 컬럼 설정...
     	SetColHidden("ibsStatus"	,1);      
     	SetColHidden("sditmAutoCrtYn"	,1);      
     	SetColHidden("cdValTypCd"	,1);      
     	SetColHidden("cdValIvwCd"	,1);      
     	SetColHidden("lstEntyLnm"	,1);      
        
//       	FitColWidth();  
        
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
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getDomainlist.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}



//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			
			break;
	
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			
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
	
	var retjson = grid_sheet.GetRowJson(row);

	parent.returnDmnVal(JSON.stringify(retjson));
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
	return;

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DMN.LGC.NM" /> : ' + param.dmnLnm +' [ <s:message code="DMN.ID" /> : ' + param.dmnId + ' ]'; //도메인논리명, 도메인ID
	$('#dmn_sel_title').html(tmphtml);
	
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
	} else {
		
	}
	
}

</script>
</head>

<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="STRD.DMN.INQ"/></div> <!-- 표준도메인 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준도메인조회"> 
                   <caption>표준도메인검색폼</caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:23%;" />
                   <col style="width:10%;" />
                   <col style="width:23%;" />
                   <col style="width:10%;" />
                   <col style="width:23%;" />
                   </colgroup>
                   
                   <tbody>  
                      <tr>
                        <th scope="row" ><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					    <td >
					    <select  id="stndAsrt" name="stndAsrt"  class="wd200">
					            <option value=""><s:message code="WHL"/></option>
					            <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					    		  <option value="${code.codeCd}">${code.codeLnm}</option>
					    		</c:forEach>
					    </select>
							
							<th scope="row"><label for="infotpId"><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 -->
							<td colspan="3">
								<div id="selectBoxDiv"> <span></span></div>
							</td>
						</tr>
						<tr>
						<th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
							<td><input type="text" id="dmnLnm" name="dmnLnm" /></td>
							<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
							<td>
								<select id="dataType" class="wd100" name="dataType">
								<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
								<c:forEach var="code" items="${codeMap.dataTypeCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							 	</select>
							</td>
							<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
							<td colspan="3"><input type="text" class="wd200" id="objDescn" name="objDescn" /></td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        	<div class="tb_comment"><s:message  code='ETC.POP' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
        <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
</div>
</div>
</body>
</html>