<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title></title>

<script type="text/javascript">
var popRqst = "${search.popRqst}";

$(document).ready(function() {
	initSearchButton();
	
    $("#btnHelp").click(function(){
    	var tabIndex = $('#tabs').tabs('option', 'active');
    	var typeCd = "";
    	var width = 0;
    	var height = 0;
    	if(tabIndex == 0) {
        	typeCd = "tbl";
        	width = 990;
        	height = 821;
        } else if(tabIndex == 1) {
        	typeCd = "col";
        	width = 991;
        	height = 1097;
        }

		var vUrl = "<c:url value='/meta/admin/popup/infosys_help.do?typCd=' />" + typeCd; 
	    
		OpenWindow(vUrl, "infosys_help", width, height, null); 
    });

	//=========컬럼정보 조회========	
//	initColGrid();
	initGrid();
	
	doAction("SearchCol");    
	
	//============================== 	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close, #btnCloseBottom").click(function() {
   		parent.closeLayerPop();
    });
    
	<c:if test="${not empty result.nopenRsn}">
	var nopenRsn = "${result.nopenRsn}";
	arrNopenRsn = nopenRsn.split("|");
	var nopenRsnHtml = '';
	for(var i = 0; i < arrNopenRsn.length; i++) {
	<c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status" >
		if("${code.codeCd}" == arrNopenRsn[i]) {
			nopenRsnHtml += '<c:out value="${code.codeLnm}"/><br/>'; 
		}
	</c:forEach>	
	}
	/* <c:if test="${not empty result.nopenDtlRelBss}">
	nopenRsnHtml += '<c:out value="${result.nopenDtlRelBss}"/>';
	</c:if> */
	$('#trNopenRsn td div').html(nopenRsnHtml);
	$('#trNopenRsn').show();
	$('#trNopenDtiRsn').show();
	</c:if>

	$("#tabs").bind('tabsselect', function(event, ui) {
		if(ui.index == 1) {
			setTimeout(function() {col_sheet.FitColWidth();}, 100);
		}
	});
});
//엔터키 처리한다.
EnterkeyProcess("Search");



function initGrid() 
{
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.MTACOL.RQST'/>";

	    var headers = [
                    {Text:headtext, Align:"Center"}

                   /*  No.|ibs상태|선택|검토상태|검토내용|요청구분|입력상태|등록유형|검증결과|테이블명|컬럼ID|컬럼영문명|컬럼한글명|테이블ID|컬럼설명|연관엔티티명|연관속성명|컬럼순서|데이터타입|데이터길이|소수점길이|데이터포맷(샘플값)
                   |NOTNULL여부|PK정보|PK순서|FK정보|DEFAULT 값|제약조건|공개/비공개여부|개인정보여부|암호화여부|비공개사유|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	  Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status",   Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:1, Sort:0},
					{Type:"Text",   Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",    Width:80,   SaveName:"rvwConts",  Align:"Left",   Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:1, Hidden:1},
					{Type:"Text",   Width:70,   SaveName:"regStatus",  Align:"Center", Edit:0, Hidden:1},				
					{Type:"Text",   Width:70,   SaveName:"regTypCd",  Align:"Center", Edit:0, Hidden:1},	 					
					{Type:"Text",   Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0, Hidden:1},
					
                    {Type:"Text",    Width:110, SaveName:"mtaTblPnm"   ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:40,  SaveName:"mtaColId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"mtaColPnm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150, SaveName:"mtaColLnm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:40,  SaveName:"mtaTblId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"objDescnCol",	Align:"Left", 	Edit:0, Hidden:1},

                    {Type:"Text",    Width:80,  SaveName:"colRelEntyNm"   , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:80,  SaveName:"colRelAttrNm"   , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Int",     Width:60,  SaveName:"colOrd"         , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:120, SaveName:"dataType"       , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:60,  SaveName:"dataLen"        , Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:80,  SaveName:"dataScal"       , Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:110, SaveName:"dataFmt"       , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100, SaveName:"nonulYn"       , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"pkYn"           , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Int",     Width:50,  SaveName:"pkOrd"          , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"fkYn"           , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"defltVal"      , Align:"Left",   Edit:0, Hidden:1},
                  
                    {Type:"Text",    Width:120,  SaveName:"constCnd"   ,Align:"Left",   Edit:0, Hidden:1},

                    {Type:"Combo",   Width:90,  SaveName:"openYn"       , Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:400,  SaveName:"priRsn"       , Align:"Left",     Edit:0},
                    {Type:"Combo",   Width:70,  SaveName:"prsnInfoYn"   , Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:70,  SaveName:"encTrgYn"     , Align:"Center",   Edit:0},
                    
                    
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",      Align:"Center", Edit:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",     Align:"Center", Edit:0},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

        var ynComboData = {ComboCode:"N|Y", ComboText: "<s:message code='COMBO.NO.YES'/>"};
        

		SetColProperty("nonulYn", 	 ynComboData); /* 아니요|예 */
		
		 var openComboData = {ComboCode:"N|Y", ComboText: "비공개|공개"};	        
		
		SetColProperty("openYn", 	 openComboData); /* 아니요|예 */
		SetColProperty("prsnInfoYn", ynComboData); /* 아니요|예 */
		SetColProperty("encTrgYn", 	 ynComboData); /* 아니요|예 */		
		SetColProperty("regStatus",  {ComboCode:"ERR|OK", ComboText:"미완료|완료"} );
		
		//===============비공개사유 세팅================
		var jsonPriRsn = ${codeMap.nopenRsnibs}; 	
			
		var vPriRsnCd   =  jsonPriRsn.ComboCode;  
		var vPriRsnCdNm = jsonPriRsn.ComboText;  
		
	    SetColProperty("priRsn", {ComboCode: vPriRsnCd, ComboText: vPriRsnCdNm}); //비공개사유 
		//================================================
	    
        InitComboNoMatchText(1, "");
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
        FitColWidth();
//        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
}
$(window).on('load',function() {	
});
$(window).resize(function(){

});
//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
      
  switch(sAction)
  {        
      case "SearchCol":	//요청서 재조회...
      	
      	var param = "mtaTblId=${result.mtaTblId}";
			        	
      	col_sheet.DoSearch("<c:url value="/meta/mta/ajaxgrid/mtacol_lst.do" />", param);
      	
      	break;
  }       
}
function col_sheet_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
		
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	
	// 등록유형이 삭제가 아닌 경우에만 작동
	/* if("${result.regTypCd}" != "D") {

		selectedClmnRowIdx = row;

		var rowJson =  col_sheet.GetRowJson(row); 
		
		var url = "<c:url value='/meta/mta/popup/mtaColRqstPop.do'/>";
		
		var param = "";
		
		param += "&rqstNo="     + rowJson.rqstNo ;
		param += "&rqstSno="    + rowJson.rqstSno ; 
		param += "&rqstDtlSno=" + rowJson.rqstDtlSno;
		param += "&searchObj="  + rowJson.mtaTblPnm;
		param += "&subInfo=COL"; 	
		
		openLayerPop(url, 1000, 580, param); 
	} */
	afterSaveOpenPopup = false;
	selectedClmnRowIdx = row;
 	openColDetailPopup(row);
}
function openColDetailPopup(row) {

	if("${result.regTypCd}" != "D") {

		var rowJson =  col_sheet.GetRowJson(row); 
		
		var url = "<c:url value='/meta/mta/popup/mtaColViewPop.do'/>";
		
		var param = "";
		
		param += "&rqstNo="     + rowJson.rqstNo ;
		param += "&rqstSno="    + rowJson.rqstSno ; 
		param += "&rqstDtlSno=" + rowJson.rqstDtlSno;
//		param += "&searchObj="  + rowJson.mtaTblPnm;
		param += "&subInfo=COL"; 	
		openLayerPop(url, 1000, 600, param);
	}
}
function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	setTimeout(function() {col_sheet.FitColWidth();}, 500);
}
</script>
<style type="text/css">
#tabs-1 .tb_basic tr td {padding-left : 8px;} 
</style>
</head>
<body>
<div class="pop_tit">
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">테이블|컬럼정보</div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
	   <%--  <div class="bt02">
	    	<a id="btnHelp"><img src="<c:url value="/images/icon_help.png"/>" /></a>		   
	    </div> --%>
	    
	    <div style="clear:both; height:5px;"><span></span></div>
	    
	    <div id="tabs" style="display: none;clear:both;">
		  <ul>
		    <li><a href="#tabs-1" id="tblinfo">테이블정보</a></li>
		    <li><a href="#tabs-2" id="collist">컬럼정보</a></li>
		  </ul>
	
			<div id="tabs-1" style="margin: 10px 0 10px 0;height:370px;">
				<div class="tb_read">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />">
						<!-- 테이블 서머리입니다. -->
						<caption>
							<s:message code="TBL.NM1" />
						</caption>
						<colgroup>
							<col style="width: 15%;" />
							<col style="width: 35%;" />
							<col style="width: 15%;" />
							<col style="width: 35%;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="dbConnTrgId">DB명</label></th>
								<td><input type="text" id="dbConnTrgId" name="dbConnTrgId" class="wd95p" value="${result.dbConnTrgPnm }" readonly /></td>
								<th scope="row"><label for="dbSchId">테이블소유자</label></th>
								<td><input type="text" id="dbSchId" name="dbSchId" class="wd95p" value="${result.dbSchPnm }" readonly /></td>
							</tr>
							<tr>
								<th scope="row"><label for="mtaTblPnm">테이블영문명</label>
								<td><input type="text" id="mtaTblPnm" name="mtaTblPnm" class="wd95p" value="${result.mtaTblPnm }" readonly /></td>
								<th scope="row"><label for="mtaTblLnm">테이블한글명</label></th>
								<td><input type="text" id="mtaTblLnm" name="mtaTblLnm" class="wd95p" value="${result.mtaTblLnm }" readonly /></td>
							</tr>
							<tr>
								<th scope="row"><label for="objDescn">테이블<s:message code="CONTENT.TXT" /></label></th>
								<td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" style="height:50px;" readonly>${result.objDescn }</textarea></td>
							</tr>
							<tr>
								<th scope="row"><label for="subjNm">업무분류체계</label></th>
								<td colspan="3"><input type="text" id="subjNm" name="subjNm" class="wd95p" value="${result.subjNm }" readonly /></td>
							</tr>
							<tr>
								<th scope="row"><label for="openRsnCd">공개/비공개여부</label></th>
								<td>
									<c:forEach var="code" items="${codeMap.openRsnCd}" varStatus="status">
										<c:if test="${result.openRsnCd == code.codeCd}">
											<input type="text" id="openRsnCd" name="openRsnCd" class="wd95p" value="${code.codeLnm} " readonly />
										</c:if>
									</c:forEach>
								</td>
								<th scope="row"><label for="tblTypNm">테이블유형</label></th>
								<td><input type="text" id="tblTypNm" name="tblTypNm" class="wd95p" value="${result.tblTypNm} " readonly /></td>
							</tr>
							<tr id="trNopenRsn" style="display:none;">
								<th scope="row"><label for="nopenRsnCd">비공개사유</label></th>
								<td colspan="3"><div style="min-height:40px;line-height:40px;"></div></td>
							</tr>
							<tr id="trNopenDtiRsn" style="display:none;">
								<th scope="row"><label for="nopenDtlRelBss">관련상세근거</label></th>
								<td colspan="3"><input type="text" id="nopenDtlRelBss" name="nopenDtlRelBss" class="wd95p" value="${result.nopenDtlRelBss} " readonly /></td>
							</tr>
							<tr>
								<th scope="row"><label for="dqDgnsYn">품질진단여부</label></th>
								<td>
									<c:if test="${result.dqDgnsYn == 'Y' }">
									<s:message code="MSG.YES" />
									</c:if>
									<c:if test="${result.dqDgnsYn == 'N' }">
									<s:message code="MSG.NO" />
									</c:if>
								</td>
								<th scope="row"><label for="occrCyl">발생주기</label></th>
								<td><input type="text" id="occrCyl" name="occrCyl" class="wd95p" value="${result.occrCyl }" readonly /></td>
							</tr>
							<tr>
								<th scope="row"><label for="prsvTerm">보존기간</label></th>
								<td><input type="text" id="prsvTerm" name="prsvTerm" class="wd95p" value="${result.prsvTerm }" readonly /></td>
								<th scope="row"><label for="tblVol">테이블볼륨(ROW수)</label></th>
								<td><input type="text" id="tblVol" name="tblVol" class="wd95p" value="${result.tblVol }" readonly /></td>
							</tr>
							<tr>
								<th scope="row"><label for="openDataLst">개방데이터목록</label></th>
								<!-- 설명 -->
								<td colspan="3"><textarea id="openDataLst" name="openDataLst" class="wd98p" style="height:50px;" readonly>${result.openDataLst }</textarea></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="tb_comment"> ※ MS-SQL(2000), CUBRID, Sysbase, MS-ACCESS는 DB카달로그에 테이블볼륨(ROW수) 정보가 없습니다.</div>
			</div>
			<!-- 컬럼 목록 탭 -->
			<div id="tabs-2" style="margin: 10px 0 10px 0;height:370px;">
				<!-- 그리드 입력 입력 -->
				<div id="grid_02" class="grid_01">
					<script type="text/javascript">createIBSheet("col_sheet", "100%", "380px");</script>
				</div>
				<!-- 그리드 입력 입력 -->
			</div>
			<!-- 그리드 입력 입력 -->
			<div style="clear:both; height:120px;"><span></span></div>	
			<div id="" style="text-align: center;">
		    	<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>           
		    </div>
		</div>
	</div>
</div>
</body>
</html>