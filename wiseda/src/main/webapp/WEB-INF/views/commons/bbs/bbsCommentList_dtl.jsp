<%
 /**
  * @Class Name : bbsCommentList_dtl.jsp
  * @Description : 댓글
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.06.29   한성곤          최초 생성
  *
  *  @author 공통컴포넌트개발팀 한성곤
  *  @since 2009.06.29
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


<!-- 댓글 작성  -->
<script type="text/javascript">

$(document).ready(function(){
	
	  <c:if test="${result.bbsTyCode == 'BBST05' }">
	    $('#prjId').val('${result.prjId}');
	    $('#reportType').val('${result.reportType}');
	  </c:if>
});

function insert_commentList() {
	var form = document.getElementById("articleCommentVO");
	
	form.submit();
}

function updt_commentList() {
	var form = document.getElementById("articleCommentVO");
	
	form.modified.value = "true";
	form.action = "<c:url value='/commons/bbs/updateArticleComment.do'/>";
	form.submit();
}

function select_commentForupdt(commentNo) {
	var form = document.getElementById("articleCommentVO");
	
	form.commentNo.value = commentNo;
	form.action = "<c:url value='/commons/bbs/selectBoardArticle.do'/>";
	form.submit();
}

function delete_commentList(commentNo) {
	var form = document.getElementById("articleCommentVO");
	
	form.modified.value = "true";
	form.commentNo.value = commentNo;
	form.action = "<c:url value='/commons/bbs/deleteArticleComment.do'/>";
	form.submit();
}

/* 댓글페이징 */
function select_commentList(pageNo) {
	var form = document.getElementById("articleCommentVO");
	
	form.subPageIndex.value = pageNo;
	form.commentNo.value = '';
	form.action = "<c:url value='/commons/bbs/selectBoardArticle.do'/>";
	form.submit();
}
</script>

<div>
<!-- 댓글  -->
	<div class="stit"><s:message code="BBS.CMT" />&nbsp;<c:out value="${resultCnt}"/></div> <!-- 댓글 (개수) -->
 	<div style="clear:both; height:5px;"><span></span></div>
	<div class="reply" width="100%">
		<ul>
			<c:forEach var="result" items="${resultList}" varStatus="status">
			<li>
				<div class="top">
					<strong><c:out value="${result.wrterNm}" /></strong>
					<span class="bar">|</span>
					<span class="date"><c:out value="${result.frstRegisterPnttm}" /></span>
				</div>
				<p class="txt">
					<c:out value="${result.commentCn}"/>
				</p>
				<div class="bottom">
					<c:if test="${result.wrterId == sessionUniqId}">
						<span><a href="javascript:select_commentForupdt(${result.commentNo})"><s:message code="BTN.UPDATE" /> </a></span>&nbsp; <!-- 수정 -->
						<span><a href="javascript:delete_commentList(${result.commentNo})"><s:message code="BTN.DELETE" /></a></span>  <!-- 삭제 -->
					</c:if>

				</div>
			</li>
			</c:forEach>
<%--			<c:if test="${fn:length(resultList) == 0}"> --%>
			<c:if test="${resultCnt eq 0}">
			<li>
		  		<p class="txt"><s:message code="BBS.CMT.NODATA" /></p> <!-- 댓글이 없습니다. -->
	  		</li>
			 </c:if>
		</ul>
	</div>
	
	<!-- paging navigation -->
	<c:if test="${pageui != null and pageui != '' }">
		<div class="paging">
	  		${pageui}
        </div>
    </c:if>

	<div>
	<form:form name="cmtFrm" commandName="articleCommentVO" action="${pageContext.request.contextPath}/commons/bbs/insertArticleComment.do" method="post" onSubmit="insert_commentList(); return false;" style="float:left; clear:both;">
		<input type="hidden" name="subPageIndex" value="<c:out value='${searchVO.subPageIndex}'/>">
		<input type="hidden" name="subTotalPageCount" value="<c:out value='${searchVO.subTotalPageCount}'/>">
		<input type="hidden" name="subTotalRecordCount" value="<c:out value='${searchVO.subTotalRecordCount}'/>">
		<input type="hidden" name="commentNo" value="<c:out value='${searchVO.commentNo}'/>">
		<input type="hidden" name="modified" value="false">
		<input type="hidden" name="nttId" value="<c:out value="${result.nttId}" />">
		<input type="hidden" name="bbsId" value="<c:out value="${bdMstr.bbsId}" />">
		
		<input type="hidden" name="cateCode" value="<c:out value="${result.cateCode}" />">
		<input type="hidden" name="prjId" value="<c:out value="${result.prjId}" />">
		<input type="hidden" name="reportType" value="<c:out value="${result.reportType}" />">
		<div class="tb_basic2" >
		<table class="board_list top_line">
			<caption>${replyTitle } <s:message code="BBS.CMT.CONTENTS" /></caption>
			<colgroup>
				<col style="width: 16%;"><col style="width: ;">
			</colgroup>
			<tbody>
			<!-- 댓글 내용  -->
			<c:set var="title"><s:message code="BBS.CMT.CONTENTS"/> </c:set>
			<tr>
				<th><label for="commentCn">${title }</label></th>
				<td class="nopd">
					<form:textarea path="commentCn" title="${title} ${inputTxt}" class="re_txt wd92p"/>   
					<c:choose>
						<c:when test="${searchVO.commentNo == '' }">
							<span style="float:left;"><a href="javascript:insert_commentList(); " class="bt_gray re_btn"><s:message code="BBS.CMT.REGIST"/></a></span> <!-- 댓글 등록 -->
						</c:when>
						<c:otherwise>
							<span style="float:left;"><a href="javascript:updt_commentList(); " class="bt_gray re_btn"><s:message code="BBS.CMT.UPDATE"/></a></span> <!-- 댓글 수정 -->
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</tbody>
		</table>
		</div>
		<div style="clear:both; height:5px;"><span></span></div>
	</form:form>
	</div>
</div>
