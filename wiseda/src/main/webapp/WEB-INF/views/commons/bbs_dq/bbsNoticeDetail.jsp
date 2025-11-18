<%
 /**
  * @Class Name : bbsNoticeDetail.jsp
  * @Description : bbsNoticeDetail 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.02.01   박정규              최초 생성
  *   2016.06.13   김연호              표준프레임워크 v3.6 개선
  *  @author 공통서비스팀 
  *  @since 2009.02.01
  *  @version 1.0
  *  @see
  *  
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>${bdMstr.bbsNm} <s:message code="ARTICLE.WRITE" /></title> <!-- 글쓰기 -->
<script src="//cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	  <c:if test="${result.bbsTyCode == 'BBST05' }">
	    $('#prjId').val('${result.prjId}');
	    $('#reportType').val('${result.reportType}');
	  </c:if>
	  
		//목록 버튼 클릭 이벤트....
		$('#btn_list').click(function(){
			select_noticeList(1);
		});
		
		//저장 버튼 클릭 이벤트....
		$('#btn_update').click(function(){
			
			document.frm.action = "<c:url value='/commons/bbs_dq/forUpdateBoardArticle.do'/>";
			document.frm.submit();
		});
		
		//게시물 삭제 이벤트...
		$('#btn_delete').click(function(){
			
			//확인창에서 확인 후 처리...
			checkForm(); return false;
		});
		
		//답글 작성 이벤트...
		$('#btn_apply').click(function(){
			document.frm.action = "<c:url value='/commons/bbs_dq/addReplyBoardArticle.do'/>";
			document.frm.submit();
		});
		
		CKEDITOR.replace( 'nttCn', {readOnly:true, toolbarStartupExpanded:false});
		
});

function checkForm() {
	var msg = "<s:message code='CNF.DEL' />";
	showMsgBox("CNF", msg, 'DELETE');
	return false;
}

// 삭제 처리
 function doAction(action) {
	switch(action) {
	case 'DELETE':
		document.frm.action = "<c:url value='/commons/bbs_dq/deleteBoardArticle.do'/>";
		document.frm.submit();
		break;
	}
}

// 게시물 목록으로 이동
function select_noticeList(pageNo) {
	document.frm.pageIndex.value = pageNo;
	document.frm.cateCode.value = "";
	document.frm.action = "<c:url value='/commons/bbs_dq/selectBoardList.do'/>";
	document.frm.submit();
}
</script>

</head>
<body>
	<div id="content">
        <div style="clear:both; height:5px;"><span></span></div>
        <div class = "right">
<%--        <div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home">  &gt; ${brdMstrVO.bbsNm}</div> --%>
            <div class="stit"><s:message code="ARTICLE.WRITE" /></div> <!-- 글쓰기 -->
            <div style="clear:both; height:5px;"><span></span></div>
            
            <form name="frm" method="post" action="" >
				<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>">
				<input type="hidden" name="bbsId" value="<c:out value='${bdMstr.bbsId}'/>" >
				<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" >
				<input type="hidden" name="cateCode"  value="<c:out value="${result.cateCode}"/>" />
				<input type="hidden" name="parnts" value="<c:out value='${result.parnts}'/>" >
				<input type="hidden" name="sortOrdr" value="<c:out value='${result.sortOrdr}'/>" >
				<input type="hidden" name="replyLc" value="<c:out value='${result.replyLc}'/>" >
				<input type="hidden" name="nttSj" value="<c:out value='${result.nttSj}'/>" >
				<input type="hidden" name="prjId" value="<c:out value='${result.prjId}'/>" >
				<input type="hidden" name="reportType" value="<c:out value='${result.reportType}'/>" >
				
            	<table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
					<caption><s:message code="INPT.FORM" /> </caption> <!-- 입력폼 -->
					<colgroup>
						<col style="width:12%;">
                    	<col style="width:28%;">
                    	<col style="width:12%;">
                    	<col style="width:28%;">
                    	<col style="width:10%;">
                    	<col style="width:10%;">
					</colgroup>
					<tbody>
					<c:if test="${result.bbsTyCode == 'BBST05' }">
             			<tr>
                			<th><s:message code="PRJT" /></th> <!-- 프로젝트 -->
                			<td >
                				<select id="prjId" name="prjId" title="<s:message code='PRJT.CHC' />" disabled="disabled"> <!-- 프로젝트선택 -->
                					<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                				<c:forEach var="code" items="${codeMap.prjCode}" varStatus="status">
	                    				<option value="${code.codeCd}">${code.codeLnm}</option>
	                    			</c:forEach>
                				</select>
                			</td>
                			<th><s:message code="CALC.PTRN" /></th> <!-- 산출물유형 -->
                			<td>
                				<select id="reportType" name="reportType" title="<s:message code='CALC.PTRN' />" disabled="disabled"> <!-- 산출물유형 -->
                					<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                				<c:forEach var="code" items="${codeMap.reportType}" varStatus="status">
	                    				<option value="${code.codeCd}">${code.codeLnm}</option>
	                    			</c:forEach>
                				</select>
                			</td>
              			</tr>
              		</c:if>
					<tr>
						<th><s:message code="BBS.SUBJECT" /></th> <!-- 제목 -->
						<td colspan="3" class="left"><c:out value="${result.nttSj}"/></td>
						<th><s:message code="DSTC" /></th> <!-- 구분 -->
                		<td ><c:out value="${result.cateNm}" /></td>
					</tr>
					<tr>
						<th><s:message code="DFTM" /></th> <!-- 작성자 -->
						<td>	    
                			<c:choose>
			    				<c:when test="${result.ntcrNm == null || result.ntcrNm == ''}">
			    					<c:out value="${result.frstRegisterNm}" />
			    				</c:when>
			    				<c:otherwise>
			    					<c:out value="${result.ntcrNm}" />
			    				</c:otherwise>
			    			</c:choose>
	    				</td>
						<th><s:message code="FLIN.DTTM" /></th> <!-- 작성시각 -->
						<td class="left"><c:out value="${result.frstRegisterPnttm}"/></td> 
						<th><s:message code="HIT" /></th> <!-- 조회수 -->
						<td class="left"><c:out value="${result.inqireCo}"/></td> 
					</tr>
					<tr>
						<th class="vtop"><s:message code="BBS.CONTENTS" /></th> <!-- 내용 -->
<%--                 <td colspan="3"><div id="bbs_cn"><c:out value="${result.nttCn}" escapeXml="false" /></div></td> --%>
                		<td colspan="5">
                			<textarea  id="nttCn" name="nttCn" accesskey="" style="width:96%; height:200px;">${result.nttCn}</textarea>
                		</td>
              		</tr>
					<c:if test="${not empty result.atchFileId}">
						<tr>
							<th class="bd_none"><s:message code="ATFL" /></th> <!-- 첨부파일 -->
							<td class="bd_none" colspan="5">
								<c:import url="/commons/fms/selectFileInfs.do" charEncoding="utf-8">
									<c:param name="param_atchFileId" value="${result.atchFileId}" />
								</c:import>
							</td>
						</tr>
	  				</c:if>
					</tbody>
				</table>
			</form>
			<!-- 하단 버튼 -->
			<ul class="bt">
            	<c:if test="${result.frstRegisterId == sessionUniqId}">
            		<li id="btn_update"><a class="bt_gray"><s:message code="BTN.UPDATE"/></a></li> <!-- 수정 -->
            		<li id="btn_delete"><a class="bt_gray"><s:message code="BTN.DELETE"/></a></li> <!-- 삭제 -->
            	</c:if>
            	<c:if test="${result.replyPosblAt == 'Y'}">
            		<li id="btn_apply"><a class="bt_gray"><s:message code="BTN.APPLY"/></a></li> <!-- 답글작성 -->
            	</c:if>
            	<li id="btn_list"><a class="bt_gray"><s:message code="BTN.LIST"/></a></li> <!-- 목록 -->
            </ul>
            
		</div><div style="clear:both;"></div>
	</div>
	
	<!-- 댓글 -->
	<c:if test="${useComment == 'true'}">
<%--		<%@include file="/commons/bbs_dq/bbsCommentList.jsp" %> --%>
		<c:import url="/commons/bbs_dq/selectArticleCommentList.do" charEncoding="utf-8"></c:import>
	</c:if>
</body>
</html>
