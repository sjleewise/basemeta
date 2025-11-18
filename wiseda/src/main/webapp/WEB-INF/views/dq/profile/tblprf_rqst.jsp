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
var interval = "";

$(document).ready(function() {
	
	//그리드 초기화 
	initGrid();
	//그리드 사이즈 조절 초기화...		
 	bindibsresize();
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	$( "#tabs" ).tabs().click(function(event){
	    //event.preventDefault();	//브라우저 기본 이벤트 제거...
	});
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.PRFREQUIRED" />";
	//폼검증
	$("#frmAnaTrg").validate({
		rules: {
			dbConnTrgPnm		: "required",
			dbSchPnm		: "required",
			dbcTblNm 	: "required"
		},
		messages: {
			dbConnTrgPnm		: requiremessage,
			dbSchPnm		: requiremessage,
			dbcTblNm 	: requiremessage
		}
	});

	//프로파일 종류 테이블분석CD 강제 셋팅
	//$("#prfKndCd").val("PT01");
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });
    
    //div size 정의
//     divSize();
    
    //컬럼관련 진단대상 요소 hide
	$("form[name=frmAnaTrg] #colPrfTrLayer").hide();
    
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #schDbSchNm"), "DBSCH");
	setautoComplete($("#frmSearch #schDbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
	
	//일괄등록 버튼 Hide...
    $("#btnPrfSchRqst").click(function(){
    	doAction("PrfSchRqst");
    }).hide();	//시연때만 잠시 막아둠.. 화면이 깨지는데 못잡겠음;;
});



$(window).on('load',function() {
});

$(window).resize(function() {
		//div size 정의
		divSize();
});

EnterkeyProcess("SearchAnaTrgTbl");
	
function initGrid()
{
   
	//진단대상 테이블 grid
    with(grid_tbl){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.TBLPRF_RQST'/>", Align:"Center"}
                ];
        //No|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0}, 
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
// 	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
	init_sheet(grid_tbl);    
   
}	

function doAction(sAction, param)
{
        
    switch(sAction)
    {
       	/*진단대상 테이블 조회*/
        case "SearchAnaTrgTbl":
        	param = "";
        	param += "&tblColGb=PT";
        	param += "&"+$('form[name=frmSearch]').serialize();
        	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLst.do" />", param);
        	break;
        	
        case "DtlReset":
        	//테이블분석 텝 강제 클릭
    		//$("#tab-pt01 a").click();
    		//관계분석 form reset
			resetPT01();
			//중복분석 RESET
			resetPT02();
        	
    	    break;
        
        case "PrfSchRqst":
        	var url   = "<c:url value="/dq/profile/popup/prfschrqst_pop.do"/>";
        	var param = "" ;
        	var popup = OpenWindow(url+param,"SQL","1200","750","yes");

        	break;
        	
        case "Down2Excel":  //엑셀내려받기
            grid_tbl.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}


function divSize(){
	 // 진단대상 전체
//   $("#anatrg").attr("style","width:30%;float:left;");
  // 진단대상 조회조건
//   $("#searchTrg_div").attr("style","width:99%;height:100%;float:left;");
  
  // 프로파일 상세 전체
//   $("#tblprf").attr("style","width:69%;height:100%;float:left;");
  
  //프로파일 텝
//   $("#tabs").attr("style","width:99%;float:right;");
  
  //그리드 가로 스크롤 방지
	grid_tbl.FitColWidth();
	
	grid_prf.FitColWidth();
	
}

function loadTblDetail(param){
	ajax2Json('<c:url value="/dq/profile/getAnaTrgTblDetail.do"/>', param, setAnaTgtDtl);
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//프로파일정보 reset
   	resetPrfInfo("PT");
   	
    //텝 상세화면 RESET
   	doAction("DtlReset");
    
  //그리드 선택 데이터 변수 setting
	var param = "&dbConnTrgId="+grid_tbl.GetCellValue(row, "dbConnTrgId"); 
	     param += "&dbSchId="+grid_tbl.GetCellValue(row,"dbSchId");
	     param += "&dbcTblNm="+grid_tbl.GetCellValue(row,"dbcTblNm");
 	     param += "&dbcColNm="+$("form[name=frmSearch] input[name='schDbcColNm']").val();
 	     //테이블 컬럼 프로파일 구분
 	     param += "&tblColGb=PT";
    
   	loadTblDetail(param);
}

//진단대상 테이블 조회 오류
function grid_tbl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		resetPrfInfo("PT");
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="TBL.PROF.MNG" /></div><!--테이블프로파일 관리-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 진단대상 검색 -->
<div id="anatrg" style="width: 30%; float: left;" >
	<div id="searchTrg_div" >
	        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	        <div style="clear:both; height:5px;"><span></span></div>
	        
	        <form id="frmSearch" name="frmSearch" method="post">
	            <fieldset>
	            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	            <div class="tb_basic2" >
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='TBL.PROF.MNG'/>"><!-- /*테이블프로파일관리*/ -->

	                   <caption><s:message code="TBL.PROF.MNG"/></caption><!--테이블프로파일관리-->

	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   </colgroup>
	                   
	                   <tbody>                            
	                       <tr>                               
	                           <th scope="row"><label for="schDbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->


	                           <td>
	                           		<select id="schDbConnTrgId"  name="schDbConnTrgId" class="wd98p">
	                           		<option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
	                           		</select>
	                           </td>
	                       </tr>
	                       	<tr>                               
	                           <th scope="row"><label for="schDbSchNm"><s:message code="SCHEMA.NM" /></label></th><!--스키마명-->

	                           <td>
	                               <input type="text" name="schDbSchNm" id="schDbSchNm" class="wd98p"/>
	                           </td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm" class="wd98p"/>
	                       		</td>
	                       </tr>
	                       <tr>                               
	                           <th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                           <td>
	                               <input type="text" name="schDbcColNm" id="schDbcColNm" class="wd98p" />
	                           </td>
	                       </tr>
	                       
<!-- 	                       <tr>                                -->
<%-- 	                           <th scope="row"><label for="metaAsscAnly"><s:message code="META.LNKD.ANLY" /></label></th><!--메타연계--> --%>

<!-- 	                           <td> -->
<%-- 	                               <select id="metaAsscAnly" name="metaAsscAnly"> --%>
<%-- 	                               	   <option value=""><s:message code="WHL" /></option> <!-- 전체 --> --%>
<!-- 	                               	   <option value="Y">Y</option> 	                               	   	                               	    -->
<%-- 	                               </select> --%>
<!-- 	                           </td> -->
<!-- 	                       </tr> -->
	                       
	                       <tr>                               
	                           <th scope="row"><label for="schRegYn"><s:message code="REG.YN" /></label></th><!--등록여부-->
	                           <td>
	                           		<select id="schRegYn"  name="schRegYn">
	                           			<option value=""><s:message code="WHL" /></option><!--전체-->

	                           			<option value="Y">Y</option>
	                           			<option value="N">N</option>
	                           		</select>
	                           </td>
	                       </tr>
	                   </tbody>
	                 </table>   
	            </div>
	            </fieldset>
	            
	        </form>
	</div>
	
    <!-- 조회버튼영역  -->
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtn" style="text-align: left;">
		<button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch"><s:message code="INQ"/><!--조회-->
 
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_tbl", "99%", "480px");</script>            
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	
	
</div>
<div style="width: 1%; float: left;">&nbsp;</div>
<!-- 테이블프로파일 상세 조회 -->
<div id="tblprf" style="width: 69%; float:left; ">
	<!-- 진단대상 상세 -->
	<div id="tgtDtl">
		<%@include file="dtl/prf_dtl.jsp" %>
	</div>
	<!--  진단대상 상세 끝 -->

	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 버튼영역  -->
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" />
	<div style="clear:both; height:5px;"><span></span></div>       
	
	
	<!-- 프로파일 종류별 텝 -->
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-pt01"><a href="#tabs-pt01"><s:message code="RLT"/></a></li><!--관계분석-->
	    <li id="tab-pt02"><a href="#tabs-pt02"><s:message code="DUP"/></a></li><!--중복분석-->

	  </ul>
	  <div id="tabs-pt01">
	  	<div id="PT01_div">
	  		<%@include file="dtl/tblrel_dtl.jsp" %>
	  	</div>
	  </div>
	  <div id="tabs-pt02">
	  	<div id="PT02_div">
	  		<%@include file="dtl/tblunq_dtl.jsp" %>
	  	</div>
	  </div>
	<!-- 프로파일 종류별 텝 끝 -->
	</div>
</div>

</body>
</html>