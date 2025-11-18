
<%
	/**
  * @Class Name : bbsNoticeRegist.jsp
  * @Description : bbsNoticeRegist 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.02.01   박정규              최초 생성
  *   2016.06.13   김연호              표준프레임워크 v3.6 개선
  *   2018.06.05   신용호              CK Editor V4.9.2 Upgrade
  *
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

<title>${bdMstr.bbsNm} - <s:message code="ARTICLE.WRITE" /></title> <!-- 글쓰기 -->
<script src="//cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
<script type="text/javascript">

var loadhtml = '<div class="process_loading">' + 
			'<div class="loading_img"><img src="<c:url value="/img/loading.gif"/>" alt="<s:message code="TRTT" />"></div>' + /* 처리중 */
			'<div class="loading_txt"><img src="<c:url value="/img/loading_txt.gif"/>" alt="<s:message code="TRTT.ING" />"></div>' + /* 처리중입니다. */
			'</div>';

			
var posblAtchFileSize = '${bdMstr.posblAtchFileSize}';
var posblAtchFileSizeInt = parseInt(posblAtchFileSize);		

$(document).ready(function() {
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#board").validate({
		rules: {
			nttSj		: "required"
		},
		messages: {
			nttSj		: requiremessage
		}
	});
	
	<c:if test="${bdMstr.bbsTyCode == 'BBST05' }">
		$('#prjId').val('${searchVO.prjId}');
		$('#reportType').val('${searchVO.reportType}');
	</c:if>

	//목록 버튼 클릭 이벤트....
	$('#btn_list').click(function(){
		select_noticeList();
	});
	
	//저장 버튼 클릭 이벤트....
	$('#btn_save').click(function(){
	
		//TODO : 폼검증한다. 나중에...
		checkForm(); return false;

	});

	//파일업로드 기능이 있을 경우 생성...
	<c:if test="${bdMstr.fileAtchPosblAt == 'Y'}">
		var maxFileNum = document.board.posblAtchFileNumber.value;
	    if(maxFileNum==null || maxFileNum==""){
	   	 	maxFileNum = 3;
	    }
	 	var multi_selector = new MultiSelector( document.getElementById( 'comFileList' ), maxFileNum );
	 	multi_selector.addElement( document.getElementById( 'comFileUploader' ) );
	</c:if>
	
//	CKEDITOR.replace('nttCn', ckeditor_config);
		
	// 캘린더
	$.datepicker.setDefaults($.datepicker.regional['ko'])
	$("#ntceBgnde").datepicker({
		dateFormat : 'yy-mm-dd',
		showOn : 'button',
		buttonImage : '<c:url value='/img/bu_icon_carlendar.gif'/>',
		buttonImageOnly : true

		,
		showMonthAfterYear : true,
		showOtherMonths : true,
		selectOtherMonths : true

		,
		changeMonth : true // 월선택 select box 표시 (기본은 false)
		,
		changeYear : true // 년선택 selectbox 표시 (기본은 false)
		,
		showButtonPanel : true
	// 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
	$("#ntceEndde").datepicker({
		dateFormat : 'yy-mm-dd',
		showOn : 'button',
		buttonImage : '<c:url value='/img/bu_icon_carlendar.gif'/>',
		buttonImageOnly : true
		,
		showMonthAfterYear : true,
		showOtherMonths : true,
		selectOtherMonths : true
		,
		changeMonth : true // 월선택 select box 표시 (기본은 false)
		,
		changeYear : true // 년선택 selectbox 표시 (기본은 false)
		,
		showButtonPanel : true
		// 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
	
	
	// 첨부파일제한사이즈 여부에 따른 코멘트 설정
	if(posblAtchFileSize == ""){
		$("#comments1").text(" * 첨부파일 제한 사이즈:  1.86 GB ").val();
	}else{
		var posblAtchFileSizeMb = Math.round(posblAtchFileSizeInt / 1024 / 1024 * 100) / 100;
// 		console.log(posblAtchFileSizeMb);
		$("#comments1").text(" * 첨부파일 제한 사이즈:  " + posblAtchFileSizeMb + " MB ").val();
	}
	
});

function checkForm() {
		if($('#nttSj').val() == "") {
			var msg = "<s:message code='BBS.REQUIRED' arguments='제목' />"; /* 제목 */
			showMsgBox("ERR", msg); $('#nttSj').focus(); return false;
		}
		
		var msg = "<s:message code='CNF.SAVE' />";
		showMsgBox("CNF", msg, 'SAVE');
		return false;
		
}



function doAction(action) {
	
	switch(action) {
	case 'SAVE':
		//return;
		document.board.action = "<c:url value='/commons/bbs/insertBoardArticle.do'/>";
		document.board.submit();
		//처리중 이미지 호출...
		processLoading(loadhtml);
		break;
		
	}i
}

//게시물 목록으로 이동
function select_noticeList() {
	document.board.action = "<c:url value='/commons/bbs/selectBoardList.do'/>";
	document.board.submit();
}

</script>
</head>
<body>
        <div>
        <div style="clear:both; height:5px;"><span></span></div>
            <div class="stit"><s:message code="ARTICLE.WRITE" /></div> <!-- 글쓰기 -->
            <div style="clear:both; height:5px;"><span></span></div>
            <form:form commandName="board" name="board" method="post" enctype="multipart/form-data" >
	            <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<input type="hidden" name="bbsId" value="<c:out value='${articleVO.bbsId}'/>" />
				<input type="hidden" name="bbsAttrbCode" value="<c:out value='${bdMstr.bbsAttrbCode}'/>" />
				<input type="hidden" name="bbsTyCode" value="<c:out value='${bdMstr.bbsTyCode}'/>" />
				<input type="hidden" name="replyPosblAt" value="<c:out value='${bdMstr.replyPosblAt}'/>" />
				<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${bdMstr.fileAtchPosblAt}'/>" />
				<input type="hidden" name="posblAtchFileNumber" value="<c:out value='${bdMstr.posblAtchFileNumber}'/>" />
				<input type="hidden" name="posblAtchFileSize" value="<c:out value='${bdMstr.posblAtchFileSize}'/>" />
				<input type="hidden" name="tmplatId" value="<c:out value='${bdMstr.tmplatId}'/>" />
				<input type="hidden" name="authFlag" value="<c:out value='${bdMstr.authFlag}'/>" />
            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
                <caption>
                <s:message code="INPT.FORM" /> <!-- 입력폼 -->
                </caption>
                <colgroup>
                    <col style="width:10%;">
                    <col style="width:40%;">
                    <col style="width:10%;">
                    <col style="width:40%;">
                </colgroup>
              <c:if test="${bdMstr.bbsTyCode == 'BBST05' }">
              <tr>
                <th><s:message code="PRJT" /></th> <!-- 프로젝트 -->
                <td >
                <select id="prjId" name="prjId" title="<s:message code='PRJT.CHC' />"> <!-- 프로젝트선택 -->
                	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.prjCode}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                </select>
                </td>
                <th><s:message code="CALC.PTRN" /></th> <!-- 산출물유형 -->
                <td>
                <select id="reportType" name="reportType" title="<s:message code='CALC.PTRN' />"> <!-- 산출물유형 -->
                	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.reportType}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                </select>
                </td>
              </tr>
              </c:if>
              <tr>
              	<th><s:message code="DSTC" /></th> <!-- 구분 -->
                <td colspan="3">
                	<select id="cateCode" name="cateCode">
                		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                	</select>
                </td>
              </tr>
              <tr>
                <th><s:message code="BBS.SUBJECT" /></th>
                <td colspan="3"><input id="nttSj" name="nttSj" type="text"></td>
              </tr>
              <tr>
                <th><s:message code="BBS.CONTENTS" /></th>
                <td colspan="3"><textarea class="ckeditor" id="nttCn" name="nttCn" accesskey="" style="width:96%; height:200px;"></textarea></td>
              </tr>
              <c:if test="${bdMstr.fileAtchPosblAt == 'Y'}">
              <tr>
                <th class="bd_none"><s:message code="FILE.ATTACH" /></th>
                <td colspan="3" class="bd_none">
                	 <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
					    <tr>
					        <td><input name="file_1" id="comFileUploader" type="file" title="<s:message code='ATFL.INPT' />	"/></td> <!-- 첨부파일입력 -->
						
					    </tr>
					    <tr>
					        <td>
					        	<div  id="comFileList"></div>
						        <div class="tb_comment" id="comments1"></div>
					        	<div class="tb_comment" id="comments2">
					        		* 첨부가능 확장자: hwp, doc, ppt, xls, pdf, jpg, pptx, docx, xlsx, txt, zip, rar, jpeg, gif, png, erwin, fcu, fuc, cfu, cuf, exe</div>
					        </td>
					    </tr>
		   	        </table>
                </td>
              </tr>
              </c:if>
            </table>
            </form:form>
            <ul class="bt">
            	<li id="btn_list"><a  class="bt_gray"><s:message code="BTN.LIST"/></a></li>
            <c:if test="${bdMstr.authFlag == 'Y'}">
                <li id="btn_save"><a  class="bt_gray"><s:message code="BTN.SAVE"/></a></li>
            </c:if>
            </ul> 
        </div>
</body>
</html>
