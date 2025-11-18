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
<title>추천용어 검색</title> 

<script type="text/javascript">

$(document).ready(function() {
// 	    event.preventDefault();  //브라우저 기본 이벤트 제거...
        //버튼 초기화...
    	$("#popReset").hide();
    	$("#popExcelDown").hide();

    	
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close, #btnCloseBottom").click(function() {
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        });

        $("#popSearch").click(function(){
                doAction("Search");
        });

        //엔터키 입력 시 폼이  서브밋 되지 않도록 처리
        var submitAction = function(e){
            e.preventDefault();
            e.stopPropagation();
        }
        $('form').bind('submit',submitAction);
        
        $("input").keydown(function(){
//         	event.preventDefault();  //브라우저 기본 이벤트 제거...
            if(event.which ===13){
                doAction("Search");
            }
        });
                        
        $('#selectKeyword').click(function() {
			var analKeyword = $('#analKeyword').val();
			/* if(isBlankStr(analKeyword)) {
				showMsgBox("ERR", "분석 추천 키워드가 없습니다.");
				return false;
			} */
			var recommKeyword = $("#recommKeyword").html();
			var retjson = new Object();
			retjson["TERM_NM"] = analKeyword;
			//iframe 형태의 팝업일 경우
			if ("${search.popType}" == "I") {
				parent.document.getElementById("mtaTblLnm").value = recommKeyword;
			} else {
				opener.returnRcmdtermPop(sditmLnm);
			}	
			//팝업창 닫기 버튼 클릭....
			$(".pop_tit_close").click();
        });
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	
	$("#sditmLnm").val("${search.keyword}"); 
	$("#orignKeyword").html("${search.keyword}");
	
	
	if($("#keyword").val() != null) {
	
		doAction("Search");
	}
 	
});

$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
   	setibsheight($("#grid_01"));
});


/* function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "PK|용어명|표준용어|영문명|영문약어명|용어설명|분류|연관어|주제영역|동의어|기관명|기관번호|등록일|상태|등록일자|등록자|수정일자|수정자|분류어여부";

        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
                        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Text",   Width:100,  SaveName:"DOCID",   	Align:"Left", Edit:0, Wrap:1, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"TERM_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"STD_TERM_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"ENG_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"ENG_ADD_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:0},
                    {Type:"Text",   Width:400,  SaveName:"TERM_DEF",   	Align:"Left", Edit:0, Wrap:1, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"BRM_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:0}, 
                    {Type:"Text",   Width:200,  SaveName:"RLTD_TERM",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"SUB_AREA",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"CLA_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"ORG_NM",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"ORG_NO",   	Align:"Left", Edit:0, Wrap:1, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"FST_REG_DATE",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"STATE",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"REG_DATE",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"REG_USER",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"MOD_DATE",   	Align:"Left", Edit:0, Wrap:1, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"MOD_USER",   	Align:"Left", Edit:0, Wrap:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"CLS_TERM_YN",   	Align:"Center", Edit:0, Wrap:1, Hidden:1}
                ];
                    
        InitColumns(cols);
//         FitColWidth();
//        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
} */




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.SDITM.LST'/>", Align:"Center"}
					];
					//No.|표준분류|표준용어ID|표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인ID|도메인논리명|도메인물리명|데이터타입|데이터길이|데이터소수점길이|도메인그룹|인포타입|인포타입변경여부|암호화여부|전체영문의미|설명|요청번호|요청일련번호|등록유형코드|버전|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"sditmId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:120,  SaveName:"sditmLnm",   	Align:"Left", Edit:0},
						{Type:"Text",   Width:120,  SaveName:"sditmPnm",   	Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"lnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"pnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"dmnId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:120,  SaveName:"dmnLnm",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,  SaveName:"dmnPnm",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName:"dataType",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"dataLen",   	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"dataScal",   	Align:"Right", Edit:0, Hidden:0},
						{Type:"Combo",  Width:100,  SaveName:"dmngId",   	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",  Width:100,  SaveName:"infotpId",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",  Width:100,  SaveName:"infotpChgYn", Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",  Width:80,  SaveName:"encYn",   	Align:"Center", Edit:0},
						{Type:"Text",   Width:200,  SaveName:"fullEngMean", Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"objDescn",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,  SaveName:"rqstNo",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"rqstSno",   	Align:"Right", Edit:0, Hidden:1},
						{Type:"Combo",  Width:80,   SaveName:"regTypCd",   	Align:"Center", Edit:0, Hidden:1},
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
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
		SetColProperty("infotpChgYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		//SetColProperty("persInfoCnvYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
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

           	if($("#sditmLnm").val()==''||$("#sditmLnm").val()==null){
        		showMsgBox("ERR","<s:message code='MSG.INQ.COND.INPT'/>");
        		return ;
            }
            //option : analyze(추천용어) or recommend(분류정보)
        	var param = $("#frmSearch").serialize();
        	param+="&option=analyze";
        	//alert(param);
//        	grid_sheet.DoSearch('<c:url value="/request/rcmdTermList.do" />', param);
			grid_sheet.DoSearch("<c:url value="/meta/mta/popup/getRcmdsditmlist.do" />", param);
			/* ajax2Json('<c:url value="/request/rcmdTermList2.do" />', param, function(result) {
				var jsonData = new Object();
				jsonData["DATA"] = result.list;
				jsonData["TOTAL"] = result.list.length;
				grid_sheet.LoadSearchData(jsonData);

				$('#orignKeyword').text("입력값 ("+$('#keyword').val()+")");
				var recommTxt = "";
				if(result.match) {
					recommTxt += "분석 추천값 ("+result.match+")";
				}
				if(result.unmatch) {
					if(recommTxt != "") {
						recommTxt += "<br>";
					}
					recommTxt += "미등록 용어 : "+result.unmatch;
				}
				$('#recommKeyword').html(recommTxt);
				$('#analKeyword').val(result.analKeyword);

				setTimeout(function() {
					grid_sheet.FitColWidth();
				}, 100);
				
			});
        	break; */
       
    }       
}

/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*//* 
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var retjson = grid_sheet.GetRowJson(row);
		
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
			parent.returnRcmdtermPop(retjson);
// 			parent.closeLayerPop();
		} else {
			opener.returnRcmdtermPop(retjson);
// 			window.close();
		}	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
}
 */

 
 function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	    
		if(row < 1) return;
		
		var param =  grid_sheet.GetRowJson(row);
		$("#recommKeyword").html(param.sditmLnm);
}
 
function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
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
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">추천용어 검색</div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div style="clear:both; height:5px;"><span></span></div>
          <form id="frmSearch" name="frmSearch" method="post">

            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary=""> <!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:*;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                            <th scope="row"><label for="sditmLnm"><s:message code="INQ.WORD" /></label></th> <!-- 검색키워드 -->
                            <td>                               
                                <input type="text" name="sditmLnm" id="sditmLnm" style="width:70%;" />      
                                <button class="btn_frm_save" type="button" id="popSearch" name="popSearch" style="display: inline-block; float:right;">조회</button>                          
                            </td>  
                       </tr>
                   </tbody>
                 </table>   
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                 <colgroup>
	                   <col style="width:40%;" />
	                   <col style="width:5%;" />
	                   <col style="width:40%;" />
	                   <col style="*" />
	                  </colgroup>
                 	<tbody>
                       <tr>
                       		<td style="background-color: #088eb1; color: #FFFFFF; padding-left:5px; word-break:break-all;" id="orignKeyword"></td>
                       		<td align="center"><img src="<c:url value="/images/m_pop_arrow.png"/>" style="width:24px; height:24px;"/></td>
                       		<td style="background-color: #66b5bc; color: #FFFFFF; padding-left:5px; word-break:break-all;" id="recommKeyword"></td>
                       		<td>
                                <button class="btn_frm_save" type="button" id="selectKeyword" name="selectKeyword" style="display: inline-block; float:right;">입력</button>
                                <input type="hidden" name="analKeyword" id="analKeyword"/>  
                       		</td>
                       </tr>
                   </tbody>
                 </table>
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        <!-- 조회버튼영역  -->
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="tb_comment"></div>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "260px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="" style="text-align: center;">
    	<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>           
    </div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div style="clear:both; height:5px;"><span></span></div>
	</div>
</body>
</html>