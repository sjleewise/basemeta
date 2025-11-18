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

$(window).on('load',function() {
// 	$( "#tabs" ).show();
});


$(window).resize(
);


function doAction(sAction)
{
        
    switch(sAction)
    {
    }       
}

function setTabByPrfKndCd(){
	var prfKndCd = "${prfKndCd}";
	//컬럼분석
	if(prfKndCd == "PC01"){
		//오류건수SQL
		$("#tab-2").css("display", "none");
		//분석SQL
		$("#tab-3").css("display", "none");
		//패턴SQL
		$("#tab-4").css("display", "none");
		//오류건수SQL
		$("#tab-5").css("display", "");
		//분석SQL
		$("#tab-6").css("display", "");
		//패턴SQL
		$("#tab-7").css("display", "");
		
		$("#tab-8").css("display", "");
		
		//스페이스건수
		$("#tab-9").css("display", "");
		
	}
}
</script>

<div id="tabs">
	<ul>
		<li id="tab-1"><a href="#tabs-totalcntsql"><s:message code="TOT.CCNT.SQL"/></a></li><!--분석건수SQL-->

		<li id="tab-2"><a href="#tabs-errcountsql"><s:message code="EROR.CCNT.SQL"/></a></li><!--오류건수SQL-->

		<li id="tab-3" style="display:none;"><a href="#tabs-errdatasql"><s:message code="ANLY.SQL" /></a></li><!--분석SQL-->

		<li id="tab-4"><a href="#tabs-patternsql"><s:message code="ERR.DATA.SQL"/></a></li><!--패턴SQL-->




		<li id="tab-5" style="display:none;"><a href="#tabs-crdanasql"><s:message code="DATA.FRQC.ANLY.SQL"/></a></li><!--데이터빈도분석SQL-->
		<li id="tab-6" style="display:none;"><a href="#tabs-nullcountsql"><s:message code="NULL.ANLY.SQL"/></a></li><!--NULL분석SQL-->
		<li id="tab-9" style="display:none;"><a href="#tabs-spacecountsql"><s:message code="SPACE.CCNT.SQL"/></a></li><!--스페이스건수SQL-->
		<li id="tab-7" style="display:none;"><a href="#tabs-valmaxminsql"><s:message code="MAX.MIN.VAL.SQL"/></a></li><!--최대최소값SQL-->
		<li id="tab-8" style="display:none;"><a href="#tabs-lenmaxminsql"><s:message code="MAX.MIN.LNGT.SQL"/></a></li><!--최대최소길이SQL-->

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
	
	<div id="tabs-crdanasql">
		<textarea  class="wd99p">${sqlVO.errorPattern}</textarea>
	</div>
	
	<div id="tabs-nullcountsql">
		<textarea  class="wd99p">${sqlVO.nullCountSql}</textarea>
	</div>
	
	<div id="tabs-spacecountsql">
		<textarea  class="wd99p">${sqlVO.spaceCountSql}</textarea>
	</div>
	
	<div id="tabs-valmaxminsql">
		<textarea  class="wd99p">${sqlVO.minMaxSql}</textarea>
	</div>
	
	<div id="tabs-lenmaxminsql">
		<textarea  class="wd99p" >${sqlVO.minMaxLenSql}</textarea>
	</div>
	
</div>
	 
