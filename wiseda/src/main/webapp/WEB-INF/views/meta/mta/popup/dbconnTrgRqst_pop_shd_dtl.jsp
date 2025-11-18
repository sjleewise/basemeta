<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function() {

 	//바로수집버튼 클릭()
	$("#search_div_04 #btnBatchStart").click(function(event) {
		
		showMsgBox("CNF", "바로수집 하시겠습니까?", 'subSaveOneShd');
	});
    
  	//완료버튼 클릭(스케줄 등록)
	$('#btnComplete').click(function(event) {
		event.preventDefault();
		if(!validateStep()) return false;

		doAction("subSaveShd");
		return;
	});

	$("#frmShdDtlInput #shdTypCd").change(function(event) {
		var selVal = $(this).val();


		var checkValue = "";
		var preLen = "";
		var name = "";
		

		if($("#frmShdDtlInput #shdStrDtm").val() != getCurrDtm()) {
			$("#frmShdDtlInput #shdStrDtm").val(getCurrDtm());
		}
		$("form[name=frmShdDtlInput] input:checkbox").prop("checked", false);
		//$("#shdWklVal").val(""); //주기

		if(selVal == "W") {	// 매주
			$("#weekCd").show();
			//$("#shdWklVal").val("1");
			//checkArr(7, "${resultShd.shdWkl}", "schdWkl");
			checkArr(7, $("#shdWkl").val(), "schdWkl");
			
		}else if(selVal == "D") {		//매일
			$("#shdWkl").val("");
			$("#weekCd").hide();
			//$("#shdDly").val("00");
		}
	});

	if(isBlankStr($("#frmShdDtlInput #shdStrDtm").val())) {
		$("#frmShdDtlInput #shdStrDtm").val(getCurrDtm());
	}
});

$(window).on('load',function(){
	//달력팝업 추가...
	//$("#frmShdDtlInput #shdStrDtm").datepicker();
});

$(window).resize( function(){
	
});

function checkArr(preLen, oldStr, name) {
	
	var diff = preLen - oldStr.length;
	var filler = "0";

	for(var i=0 ; i < diff ; i++){
	  oldStr = filler + oldStr;
	}
	
	for(var oi=0; oi < preLen ; oi++) {
		
		if(oldStr.charAt(oi) == "1") {
			$("form[name=frmShdDtlInput] input:checkbox[id="+name+(preLen-oi)+"]").prop("checked", true);	
		}
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->

<!-- 검색조건 입력폼 -->
<div id="search_div_04" >       
    <%-- <div style="clear:both; height:10px;"><span></span></div> --%>
     <!-- 조회버튼영역  -->
	<div class="divLstBtn" style="display:none;">	 
           <div class="bt03">
               <button class="btn_search" id="btnBatchStart"  name="btnBatchStart">바로수집</button> <!-- 즉시실행  -->
		</div>
		<div class="bt02">
          <button name="btn" style="display:none;"></button> <!-- 삭제 -->                      
    	</div>
     	 	</div>	
<!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

<div class="tb_basic">
<form name="frmShdInput" id="frmShdInput" method="post" action="">
	<input type="hidden" id="shdIbsStatus" name="ibsStatus" value="${shdStatus}" />
	<input type="hidden" id="shdId" name="shdId" value="${resultShd.shdId}" />
	<input type="hidden" id="regTypCd" name="regTypCd" value="${resultShd.regTypCd}" />
	<input type="hidden" id="shdJobId" name="shdJobId" value="${resultShd.dbConnTrgId}"/>
	<input type="hidden" id="shdKndCd" name="shdKndCd" value="SC" />

     <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
            <caption>
            <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
            </caption>
            <colgroup>
            <col style="width:15%;" />
            <col style="width:35%;" />
            <col style="width:15%;" />
            <col style="width:35%;" />
            </colgroup>
            <tbody>
		   <%-- <tr style="display:none;">
	        <th class="th_require"><s:message code="SCDU.NM" /></th> <!-- 스케줄명 -->
	        <td colspan="3">
	        <input type="text" id="shdLnm" name="shdLnm" class="wd98p"/>
	        </td>
	      </tr>
	     <tr>
             <th scope="row" class="th_require"><label for="shdObjDescn"><s:message code="SCDU.TXT" /></label></th> <!-- 스케줄 설명 -->
             <td colspan="3"><textarea id="shdObjDescn" name="objDescn" accesskey="" class="wd98p">${resultShd.objDescn}</textarea></td>
          </tr> --%>
	      <tr>
	        <th><s:message code="SCDU.STOP.YN" /></th> <!-- 스케줄실행중지 -->
	        <td>
		        <div class="sbox wd20p">
				<label class="sbox_label" for="shdUseYn"></label>
		        <select id="shdUseYn" name="shdUseYn" class="wd20p">
		        
		        	<option value="Y" <c:if test="${resultShd.shdUseYn eq 'Y'}">selected</c:if>><s:message code="MSG.NO"/></option> <!-- 아니요 -->
		        	<option value="N" <c:if test="${resultShd.shdUseYn eq 'N'}">selected</c:if>><s:message code="MSG.YES" /></option> <!-- 예 -->
		        </select>
		        </div>
	        </td>
               </tbody>
              </table>
 </form>
             </div>
             
            <!--  -->
<div style="clear:both; height:5px;"><span></span></div>
<div class="stit"><s:message code="BTCH.DTL.INFO" /></div> <!-- 배치 상세정보 -->
<div style="clear:both; height:5px;"><span></span></div>
<div class="tb_basic">
<form name="frmShdDtlInput" id="frmShdDtlInput" method="post" action="">
	
	<table border="0" cellspacing="0" cellpadding="0"  summary="">
       <caption>
       <s:message code="BTCH.DTL.INFO" /><!-- 배치 상세정보 -->
       </caption>
       <colgroup>
            <col style="width:15%;" />
            <col style="width:35%;" />
            <col style="width:15%;" />
            <col style="width:35%;" />
       </colgroup>
      <tr>
        <th><s:message code="BTCH.FRMN" /></th> <!-- 배치형태 -->
        <td colspan="3">
		<div class="sbox wd20p">
		<label class="sbox_label" for="shdTypCd"></label>	        
       	<select id="shdTypCd"  name="shdTypCd" class="dtlInput wd20p" >
			<c:forEach var="code" items="${codeMap.shdTypeCd}" varStatus="status">
			<c:if test="${code.codeCd eq 'D' or code.codeCd eq 'W'}">
			<option value="${code.codeCd}" <c:if test="${code.codeCd eq resultShd.shdTypCd}">selected</c:if>>${code.codeLnm}</option>
			</c:if>
			</c:forEach>
		</select>
		</div>
       </td>
       </tr>
      <tr id="weekCd" style="display:none;">
        <th><s:message code="BTCH.DTL" /></th> <!-- 배치상세 -->
        <td colspan="3" >
      	  <div class="wr_mj_span">
       		 <%-- <span class="mj_span"><s:message code="EVERY" /> 
       		  <s:message code="EACHWEEK" /> </span> --%><!-- 매 --> <!-- 주 마다 -->
      		  <input type="hidden" id="shdDly" name="shdDly" value="00" />
       		  <input type="hidden" id="shdWklVal" name="shdWklVal" value="1"/>
       		  <input type="hidden" id="shdWkl" name="shdWkl" value="${resultShd.shdWkl}"/>
       		  
	      	 <span class="mj_span"><c:forEach var="code" items="${codeMap.shdWeekCd}" varStatus="status">
		     <span class="input_check" ><input type="checkbox" value="${code.codeCd}" id="schdWkl${status.count }" name="schdWkl"/>${code.codeLnm}</span>
			</c:forEach>
				<%-- <s:message code="TO.DD" />  --%></span><!-- 요일에 -->
			</div>
        </td>
      </tr>
      <tr>
        <th class="th_require"><s:message code="STRN.DD" /></th> <!-- 시작일 -->
        <td>
        	<span style="float:left; position:relative;margin-right:10px;">
        	<input type="text" id="shdStrDtm" name="shdStrDtm" class="wd100 d_readonly" readonly value="${resultShd.shdStrDtm}" />
        	</span>
			<div class="sbox tab_over_x">
			<label class="sbox_label" for="shdStrHr"></label>
        	<select id="shdStrHr"  name="shdStrHr" class="dtlInput">
				<c:forEach var="code" items="${codeMap.schdHourCd}" varStatus="status">
					<option value="${code.codeCd}" <c:if test="${code.codeCd eq resultShd.shdStrHr}">selected</c:if>>${code.codeLnm}</option>
				</c:forEach>
			</select>
			</div>
			<div class="s_txt">시</div>
			<input type="hidden" id="shdStrMnt" name="shdStrMnt" value="00"/>
			<%-- <div class="sbox">
			<label class="sbox_label" for="shdStrMnt"></label>
        	<select id="shdStrMnt"  name="shdStrMnt" class="dtlInput">
				<c:forEach var="code" items="${codeMap.schdMinCd}" varStatus="status">
				<option value="${code.codeCd}" <c:if test="${code.codeCd eq resultShd.shdStrMnt}">selected</c:if>>${code.codeLnm}</option>
				</c:forEach>
			</select>
			</div>
			<div class="s_txt">분</div> --%>
        </td>
      </tr>
       </table>
      </form>
      </div>
	
<!-- </body> -->
<!-- </html> -->
