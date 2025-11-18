<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="DMN.GRP.INQ" /></title> <!-- 도메인그룹 조회 -->

<script type="text/javascript" src='<c:url value="/js/ckeditor/ckeditor.js" />'></script>
<script type="text/javascript">

$(document).ready(function(){
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $(".BK_btn_x, #btn_pop_close").click(function(){
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    });
	
/*     var editor = CKEDITOR.instances["helpCtnt"];
    if(editor){editor.destroy(true);}
	CKEDITOR.replace( "helpCtnt", {readOnly:true, removePlugins:'toolbar'   */
		
 	    /* , filebrowserUploadUrl:'<c:url value="/js/ckeditor/upload.jsp?" />'
	    +'realUrl='+'<c:url value="/images/ckeditor/" />'
	    +'&realDir=/images/ckeditor/'  */ 
	//});
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setHelpContentHeight();
	// grid_sheet.SetExtendLastCol(1);    
});

$(window).on('load',function(){
	setHelpContentHeight();
});

function setHelpContentHeight () {
    var top = $("#help_content").offset().top;
    var bodyheight = $(document).height();
	$("#help_content").css('height', bodyheight-top-60);
}

</script>
<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="HELP.C"/> - <c:out value="${evalmst.scrNm}" /></div> <!-- 도움말 -->
    <div class="pop_tit_close"><a class="BK_btn_x">창닫기</a></div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<%-- 			<textarea class="ckeditor" id="helpCtnt" name="helpCtnt" accesskey="" style="width:96%; height:300px;">${evalmst.helpCtnt}</textarea> --%>

		<div class="BK_popup_text2" id="help_content" style="width:100%; height:400px; overflow:auto">
 			${evalmst.helpCtnt}
		</div>
	</div>
</div>
</body>
</html>