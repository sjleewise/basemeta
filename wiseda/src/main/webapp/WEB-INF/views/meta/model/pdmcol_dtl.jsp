<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//검색조건 display none
// 		$("div#col_search_div").hide();
// 		$("form#frmColInput").hide();
 		$("#btnColExcelDown").hide();
 		
 	// 범정부 사용 여부 세션 값
 		var govYn = "<%=session.getAttribute("govYn")%>";
 		
 		// 범정부 사용 여부 N일때 숨기기
 	 	if ("${govYn}" == "N") {
 	 		$("#gov3").hide(); // 범정부 중앙에타 연계항목 (컬럼)
 	 		$("#gov4").hide(); // 범정부 중앙에타 연계항목 테이블 (컬럼)
 	 	}
 		
 		// 범정부 사용 여부 Y일때 보이기
 	 	if ("${govYn}" == "Y") {
 	 		$("#gov3").show();
 	 		$("#gov4").show();
 	 	}
		
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initColDtlGrid();
	
});


$(window).resize();


function initColDtlGrid()
{
    
    with(coldtl_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.PDMCOL.DTL'/>";
        	//No.|컬럼ID|시작일시|만료일시|물리모델테이블ID|표준용어ID|컬럼명|컬럼한글명|컬럼순서|상태|"
            //+ "데이터타입|길이|소수점|PK여부|PK순서|NOTNULL여부|DEFAULT값|요청번호|요청일련번호|요청상세일련번호|"
            //+ "최초요청일시|최초요청자ID|요청일시|요청자ID|승인일시|승인자ID|버전|설명
         // 범정부 항목 
        if ("${govYn}" == "Y") {
			headtext += "<s:message code='META.HEADER.PDMCOL.RQST.8'/>"; //범정부 중앙메타 연계항목
			//headtext += "|공개/비공개여부|개인정보여부|제약조건";
	    }
 
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColId"     ,Align:"Left",   Edit:0},
                    {Type:"Date",    Width:100,  SaveName:"strDtm"     ,Align:"Left",   Edit:0},
                    {Type:"Date",    Width:100,  SaveName:"expDtm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmTblId"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"sditmId"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColLnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"colOrd"     ,Align:"Left",   Edit:0},
                    {Type:"Combo",    Width:100,  SaveName:"regTypCd"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dataType"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dataLen"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dataScal"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pkYn"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pkOrd"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"nonulYn"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"defltVal"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstNo"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstSno"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstDtlSno"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstDtm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserId"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"RqstDtm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"RqstUserId"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"aprvDtm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserId"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"objVers"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"objDescn"     ,Align:"Left",   Edit:0}
                ];
        
     // 범정부 항목
        if ("${govYn}" == "Y") {
        	cols.push(
        			//범정부연계항목
					{Type:"Combo",    Width:80,  SaveName:"openYn"    ,Align:"Center",   Edit:0},
					{Type:"Combo",    Width:80,  SaveName:"prsnInfoYn" ,Align:"Center",   Edit:0},
					{Type:"Text",     Width:80,  SaveName:"constCnd" ,Align:"Center",   Edit:0}
				);
        }
                    
        InitColumns(cols);
        
        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
//         SetColProperty("regTypCd", 	${codeMap.regtypcd});

        if ("${govYn}" == "Y") {
        	//범정부 연계 항목 
    		SetColProperty("openYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
    		SetColProperty("prsnInfoYn",{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        }
        InitComboNoMatchText(1, "");
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(coldtl_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doActionColDtl(sAction)
{
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#frmSearch').serialize();
        	var param = $('#frmSearch').serialize();
        	coldtl_sheet.DoSearch("<c:url value="/meta/model/gethistcollist.do" />", param);
//         	coldtl_sheet.DoSearchScript("testJsonlist");
}
 


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function col_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	return;
}

function coldtl_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
}

</script>
<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

<div id="col_search_div">
<!-- 검색조건 입력폼 -->
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" id="rqstNo" name="rqstNo" />
        	<input type="hidden" id="rqstSno" name="rqstSno" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>
	                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
        
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("coldtl_sheet", "100%", "300px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "coldtl_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->