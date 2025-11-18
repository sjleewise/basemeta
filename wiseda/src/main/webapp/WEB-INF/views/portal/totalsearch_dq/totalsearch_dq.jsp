<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>품질관리</title>
<script type="text/javascript">

$(document).ready(function(){
	
	
		$('#searchNm').focus()	;
		// 조회 Event Bind
 		$(".nsearch_sch a").click(function(event){
 			event.preventDefault();
 			if($.trim($('form[name="search"] input[name="searchNm"]').val()) == "") {
 				showMsgBox("ERR", "<s:message code='MSG.INQ.COND.INPT'/>");
 				return false;
 			}
 			if($.trim($('form[name="search"] input[name="searchNm"]').val()).length < 2) {
 				showMsgBox("ERR", "<s:message code='MSG.INQ.COND.2WORD.INPT'/>");
 				return false;
 			}
 			$('form[name="search"] input[name="searchNm"]').val($.trim($('form[name="search"] input[name="searchNm"]').val()));
 			$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
 		});
	
});



$(window).resize(function(){
                
        // Window Resize 시  컬럼  Width%
//         var interval = "5|10|10|10|15|15|35";
        
//         chgSize(mySheet, interval);
});

$(document).keypress(function(e) {
	  if(e.which == 13) {
	    // enter pressed
//   alert("you pressed enter key");
		  $(".search_bt").click();
	  }
	});


function moreTot() {
	$('form[name="search"] input[name="bfSearchNm"]').val('${result.searchNm}');
	$('form[name="search"] input[name="categ1Cd"]').val('');
	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
}

function moreStnd() {
	$('form[name="search"] input[name="bfSearchNm"]').val('${result.searchNm}');
	$('form[name="search"] input[name="categ1Cd"]').val('1');
	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
}

function moreTbl() {
	$('form[name="search"] input[name="bfSearchNm"]').val('${result.searchNm}');
	$('form[name="search"] input[name="categ1Cd"]').val('2');
	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
}

function moreDq() {
	$('form[name="search"] input[name="bfSearchNm"]').val('${result.searchNm}');
	$('form[name="search"] input[name="categ1Cd"]').val('3');
	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
}

function moreSubj() {
	$('form[name="search"] input[name="bfSearchNm"]').val('${result.searchNm}');
	$('form[name="search"] input[name="categ1Cd"]').val('4');
	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
}

function moreBbs() {
	$('form[name="search"] input[name="bfSearchNm"]').val('${result.searchNm}');
	$('form[name="search"] input[name="categ1Cd"]').val('5');
	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
}

function openDetail(categ1cd, categ2cd, objId, objId2) {
	var url = "";
	if(categ1cd == "1") {
		switch(categ2cd)
	    {
			case "1" : url += "../../meta/stnd/stwd_lst.do?objId="+objId; //표준단어
			           break;
			case "2" : url += "../../meta/stnd/symn_lst.do?objId="+objId; //유사어
	           break;
			case "3" : url += "../../meta/stnd/dmn_lst.do?objId="+objId; //도메인
	           break;
			case "4" : url += "../../meta/stnd/sditm_lst.do?objId="+objId; //표준항목
	           break;

	    }
// 		var openWin = this.window.open(url, "detail", 'toolbar=no, location=no, directories=no, status=yes, menubar=no'
// 				+', scrollbars=no, resizable=yes, copyhistory=no,width=1000px, height=600px, left=200, top=100');
		var openWin = window.open().location.href = url;
		openWin.focus();
	} else if(categ1cd == "2") {
		switch(categ2cd)
	    {
			case "1" : url += "../../meta/model/pdmtbl_lst.do?pdmTblId="+objId; //물리테이블
			           break;
			case "2" : url += "../../meta/dbc/dbctbl_lst.do?dbSchId="+objId2+"&dbcTblNm="+objId; //DBC테이블
	           break;
			case "3" : url += "../../meta/ddl/ddltbl_lst.do?ddlTblId=" + objId;

	    }
		var openWin = this.window.open().location.href = url;

		openWin.focus();
	} else if(categ1cd == "3") {
		url = "<%=WiseConfig.URL_DQ%>";
		switch(categ2cd)
	    {
			case "1" : url += "../../dq/bizrule/bizrule_lst.do?brId="+objId; //업무규칙
			           break;
			case "2" : url += "../../dq/profile/profile_list.do?prfId="+objId+"&prfKndCd="+objId2; //프로파일
	           break;
			case "3" : url += "../../dq/criinfo/bizarea/bizarea_lst.do?bizAreaId="+objId; //업무영역
				break;
			case "4" : url += "../../dq/criinfo/dqi/dqi_lst.do?dqiId="+objId;  //DQI
				break;
			case "5" : url += "../../dq/criinfo/ctq/ctq_lst.do?ctqId="+objId; //CTQ
				break;
			case "6" : url += "../../dq/impv/impl_lst.do?anaJobId="+objId+"&anaStrDtmLink="+objId2; //개선활동
				break;
			case "7" : url += "../../dq/impv/imrsl_lst.do?anaJobId="+objId+"&anaStrDtmLink="+objId2; //개선결과
				break;
	    }
		var openWin = this.window.open().location.href = url;

		openWin.focus();
	} else if(categ1cd == "4") {   //주제영역
		url = "<%=WiseConfig.URL_META%>";
		switch(categ2cd)
	    {
			case "1" : url += "../../meta/model/metasubj_lst.do?subjId="+objId; //주제영역
			           break;
		
	    }
		var openWin = this.window.open().location.href = url;

		openWin.focus();
	} else if(categ1cd == "5") { //게시판
		$('form[name="search"] input[name="bbsId"]').val(categ2cd);
		$('form[name="search"] input[name="nttId"]').val(objId);
		$('form[name="search"]').attr('target','_blank');
		$('form[name="search"]').attr('action', '<c:url value="/commons/bbs/selectBoardArticle.do"/>').submit();
	}
};

</script>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

</head>
<body>

    <!-- 왼쪽메뉴가 없는 서브 컨테이너 시작 -->
    <div class="nsearch">
<%--         <div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt='home'> &gt; 통합검색</div> --%>
        <form name="search">
        <div class="nsearch_bar">
        	<div class="nsearch_sch"><input type="text" id="searchNm" name="searchNm" value="${result.searchNm}" placeholder="<s:message code='MSG.INQ.WORD.INPT'/>"> <a href="#"><img src="../../images/nm_search_bt.gif" onMouseOver="this.src='../../images/nm_search_bt_.gif'" onMouseOut="this.src='../../images/nm_search_bt.gif'" alt="검색"></a></div>
        	<input type="hidden" name="categ1Cd">
            <input type="hidden" name="bfSearchNm">
            <input type="hidden" name="bbsId">
            <input type="hidden" name="nttId">
        </div>
        </form>
        <div class="nsearch_stit">Integrated search result</div>
        <div class="nsearch_result">“<span>${result.searchNm}</span>”, integrated search gives a result. (total <span>${result.totCnt}</span>)</div>
        <ul class="nsearch_tab">
        
        	<li <c:if test="${result.categ1Cd == ''}">class="nsearch_tab_select"</c:if>><a href="javascript:moreTot()"><s:message code='WHL'/></a></li>
            <li <c:if test="${result.categ1Cd == '1'}">class="nsearch_tab_select"</c:if>><a href="javascript:moreStnd()"><s:message code='STRD.DATA'/></a></li>
            <li <c:if test="${result.categ1Cd == '2'}">class="nsearch_tab_select"</c:if>><a href="javascript:moreTbl()"><s:message code='PHYC.MDEL'/></a></li>
            <li <c:if test="${result.categ1Cd == '3'}">class="nsearch_tab_select"</c:if>><a href="javascript:moreDq()"><s:message code='DATA.QLTY.SYS'/></a></li>
            <li <c:if test="${result.categ1Cd == '4'}">class="nsearch_tab_select"</c:if>><a href="javascript:moreSubj()"><s:message code='SUBJ.TRRT.NM'/></a></li>
            <li <c:if test="${result.categ1Cd == '5'}">class="nsearch_tab_select"</c:if>><a href="javascript:moreBbs()">Board</a></li>
        </ul>
        
        <c:if test="${result.categ1Cd == ''}">
        <div class="nsearch_txt_01"><s:message code='STRD.DATA'/>(<s:message code='SUM'/> <span>${result.stndCnt}</span>)<c:if test="${result.stndCnt > 2}"><a href="javascript:moreStnd();" class="nsearch_more"><span>+</span> more</a></c:if></div>
        <c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${resultList.categ1Cd == '1'}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:if>
        </c:forEach>
        
        <div class="nsearch_line"><span></span></div><!-- 구분선 -->
        
        <div class="nsearch_txt_01"><s:message code='PHYC.MDEL'/>(<s:message code='SUM'/> <span>${result.tblCnt}</span>) <c:if test="${result.tblCnt > 2}"><a href="javascript:moreTbl();" class="nsearch_more"><span>+</span> more</a></c:if></div>
        <c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${resultList.categ1Cd == '2'}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}','${resultList.regId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:if>
        </c:forEach>
        
        <div class="nsearch_line"><span></span></div><!-- 구분선 -->
        
        <div class="nsearch_txt_01"><s:message code='DATA.QLTY.SYS'/>(<s:message code='SUM'/> <span>${result.dqCnt}</span>) <c:if test="${result.dqCnt > 2}"><a href="javascript:moreDq();" class="nsearch_more"><span>+</span> more</a></c:if></div>
		<c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${resultList.categ1Cd == '3'}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}', '${resultList.info1}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:if>
        </c:forEach>
                
        <div class="nsearch_line"><span></span></div><!-- 구분선 -->
        
        <div class="nsearch_txt_01"><s:message code='SUBJ.TRRT.NM'/>(<s:message code='SUM'/> <span>${result.subjCnt}</span>) <c:if test="${result.subjCnt > 2}"><a href="javascript:moreSubj();" class="nsearch_more"><span>+</span> more</a></c:if></div>
		<c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${resultList.categ1Cd == '4'}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:if>
        </c:forEach>
        
        <div class="nsearch_line"><span></span></div><!-- 구분선 -->
        
        <div class="nsearch_txt_01">Board(<s:message code='SUM'/> <span>${result.bbsCnt}</span>) <c:if test="${result.bbsCnt > 2}"><a href="javascript:moreBbs();" class="nsearch_more"><span>+</span> more</a></c:if></div>
		<c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${resultList.categ1Cd == '5'}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:if>
        </c:forEach>
        </c:if>
        
        <c:if test="${result.categ1Cd == '1'}">
        <div class="nsearch_txt_01"><s:message code='STRD.DATA'/>(<s:message code='SUM'/> <span>${result.stndCnt}</span>)</div>
        <c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:forEach>
        </c:if>
        <c:if test="${result.categ1Cd == '2'}">
        <div class="nsearch_txt_01"><s:message code='PHYC.MDEL'/>(<s:message code='SUM'/> <span>${result.tblCnt}</span>)</div>
        <c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}','${resultList.regId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:forEach>
        </c:if>
        
        <c:if test="${result.categ1Cd == '3'}">
        <div class="nsearch_txt_01"><s:message code='DATA.QLTY.SYS'/>(<s:message code='SUM'/> <span>${result.dqCnt}</span>)</div>
		<c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}','${resultList.info1}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:forEach>
        </c:if>

		<c:if test="${result.categ1Cd == '4'}">
        <div class="nsearch_txt_01"><s:message code='SUBJ.TRRT.NM'/>(<s:message code='SUM'/> <span>${result.dqCnt}</span>)</div>
		<c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:forEach>
        </c:if>

        <c:if test="${result.categ1Cd == '5'}">
        <div class="nsearch_txt_01">Board(Total <span>${result.bbsCnt}</span>)</div>
		<c:set var="categ2Cd" value="0"/>
        <c:forEach var="resultList" items="${schList}">
        <c:if test="${categ2Cd != resultList.categ2Cd}"><div class="nsearch_txt_02">${resultList.categ2Nm}</div><c:set var="categ2Cd" value="${resultList.categ2Cd}"/></c:if>
        <div class="nsearch_txt_03"><a href="javascript:openDetail('${resultList.categ1Cd}','${resultList.categ2Cd}','${resultList.objId}');" class="nsearch_txt_03_a">${resultList.objKnm}<c:if test="${resultList.objEnm != null and resultList.objEnm != ''}">(${resultList.objEnm})</c:if></a></div>
        <div class="nsearch_txt_04">${resultList.shotDesc}</div>
        <div class="nsearch_txt_05">
        	<div>${resultList.info1}</div>
            <div><s:message code='DEMD.DTTM'/> : <fmt:formatDate value="${resultList.regDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            <div class="bg_none"><c:if test="${resultList.objEnm != null and resultList.objEnm != ''}"><s:message code='DMNT'/> : ${resultList.regMan}</c:if></div>
        </div>
        </c:forEach>
        </c:if>
        <div class="ht40"><span></span></div><!-- footer와 서브 컨텐츠 사이 하단 여백 -->
    </div>
    <!-- 왼쪽메뉴가 없는 서브 컨테이너 끝 -->
       
</body>
</html>