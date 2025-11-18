<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:set var="favmenuYn"><s:message code="wiseda.fav.menu.yn" /></c:set>
<script type="text/javascript">

<c:if test="${favmenuYn == 'Y' }">
$(document).ready(function() {
$.ajaxSetup({ cache: false });

$('#mymenufooter').change(function(){
	$(location).attr('href','/wiseda/'+$('#mymenufooter option:selected').val());
});
});

$(window).on('load',function() {
getFavoriteListForFooter();
});
function getFavoriteListForFooter(){
	//인기검색어 스크롤 텍스트 처리하도록 처리
		$.getJSON('<c:url value="/meta/sitemap/favoriteList.json"/>', function(data){
		if(data ==  null) return;
		$("select[name='mymenufooter'] option").remove();
		    $("#mymenufooter").append("<option value='' selected>----[<s:message code='CHC.FAVO.MENU' />]----</option>"); /* 선택한 즐겨찾기 메뉴 */
		for(var i=0; i<data.length; i++){
		    $("#mymenufooter").append("<option value='"+data[i].filePath +"'>"+ data[i].fullPath+"</option>");
		}
		});	
}
</c:if>
</script>
<!-- footer 시작 -->
<div class="n4m_footer">
<c:if test="${favmenuYn == 'Y' }">
 <select id="mymenufooter" name ="mymenufooter">
						
	 </select> 
</c:if>	 
Copyright(C) 2018 WISEITECH Co.,Ltd. All rights reserved.</div>
<!-- footer 끝 -->	

