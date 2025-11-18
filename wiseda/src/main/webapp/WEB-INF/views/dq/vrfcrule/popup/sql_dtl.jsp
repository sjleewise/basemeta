<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">

$(document).ready(function() {
	//탭 초기화....
	$( "#tabs" ).tabs().click(function(event){
	    event.preventDefault();	//브라우저 기본 이벤트 제거...
	});
	
	$(".wd99p").attr("readonly",true).attr("rows", "35").css("border-color","transparent").css("font-family", "굴림체").css("font-size","12px");
	$( "#tabs" ).show();
});

</script>

<div id="tabs">
	<ul>
		<li id="tab-1"><a href="#tabs-totalcntsql"><s:message code="TOT.CCNT.SQL"/></a></li><!--분석건수SQL-->

		<li id="tab-2"><a href="#tabs-errcountsql"><s:message code="EROR.CCNT.SQL"/></a></li><!--오류건수SQL-->

		<li id="tab-3" style="display:none;"><a href="#tabs-errdatasql"><s:message code="ANLY.SQL" /></a></li><!--분석SQL-->

		<li id="tab-4"><a href="#tabs-patternsql"><s:message code="ERR.DATA.SQL"/></a></li><!--패턴SQL-->

	</ul>
  
	 <div id="tabs-totalcntsql">
		<textarea class="wd99p">${sqlVO.totalCount} </textarea>	
	 </div>
 
	<div id="tabs-errcountsql">
		<textarea class="wd99p">${sqlVO.errorCount}</textarea>	
	</div>
	
	<div id="tabs-errdatasql">
		<textarea  class="wd99p">${sqlVO.errorData}</textarea>	
	</div>
	
	<div id="tabs-patternsql">
		<textarea  class="wd99p">${sqlVO.errorPattern}</textarea>	
	</div>
	
</div>
	 
