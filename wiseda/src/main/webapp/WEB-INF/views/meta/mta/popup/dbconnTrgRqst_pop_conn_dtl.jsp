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

	// 접속테스트 Event Bind
    $("#btnSubConn").click(function(event) {
    	event.preventDefault();

    	$("input[id^=connTrgLnkUrl_]").focusout();

    	if($("#ibsStatus").val() == "I" && !isBlankStr($("#dbConnTrgId").val()) && $("#dbConnTrgId").val() == "성공") return false;
    	
    	if(!validateStep()) return false;
    	
    	$("#frmDbInput #dbLnkSts").text("");
    	$("#frmDbInput #dbLnkStsLoading,#dbLnkStsLoadingMsg").hide();
    	setBtnDisabled($("#btnSubConn"), true);


    	//if($("#ibsStatus").val() == "I" && !isBlankStr($("#dbConnTrgId").val())) {
        	//재접속 수행 시
    	//	getDbConnTestResult();
       // }else {
    	doAction("subSaveDbConn");
       // }
    	
    	return ;
    });

	//SboxSetLabelEvent();
});

$(window).on('load',function() {
});

$(window).resize( function(){
	
});

function getDbConnTestResult() {

	CONNTEST_YN = "Y";
	$("#frmDbInput #dbLnkStsLoading,#dbLnkStsLoadingMsg").show();

	var count = 0;
	var timerId = 0;
	timerId = setInterval(function() {
		
		count++;
		doAction("DbConnTestResult");

		if(count <= 6) { //10초단위 새로고침 최대 60초까지 

			if(!isBlankStr($("#frmDbInput #dbLnkSts").text())) {
				if($("#frmDbInput #dbLnkSts").text() == "성공") {
					//CONNTEST_YN = "Y";
				}else {
					CONNTEST_YN = "N";
					setBtnDisabled($("#btnSubConn"), false);
				}
				$("#frmDbInput #dbLnkStsLoading,#dbLnkStsLoadingMsg").hide();
				clearInterval(timerId);
			}else {

				if(count == 6) {
					CONNTEST_YN = "N";
					setBtnDisabled($("#btnSubConn"), false);
					$("#frmDbInput #dbLnkStsLoading,#dbLnkStsLoadingMsg").hide();
		        	clearInterval(timerId);
				}
			}
		}
	}, 10000 );
}
</script>

<!-- </head> -->
<!-- <body>     -->
<div class="tb_basic">
	<!-- 검색조건 입력폼 -->
	<div id="search_div_02" >       
	    <%-- <div style="clear:both; height:10px;"><span></span></div> --%>
	     <!-- 조회버튼영역  -->
		<div class="divLstBtn">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSubConn" name="btnSubConn"><s:message code="CONN.TEST" /></button> <!-- 접속테스트 -->
			    &nbsp;&nbsp;&nbsp;<img id="dbLnkStsLoading" src="<c:url value="/images/loading/loading2.gif"/>" alt="접속테스트중입니다" style="display:none;"/><span id="dbLnkStsLoadingMsg" style="display:none;">&nbsp;접속테스트중입니다. 네트워크 상태에 따라 지연될 수 있습니다.</span><span id="dbLnkSts">${result.dbLnkSts}</span>
			</div>
      	 	</div>	
	<!-- 조회버튼영역  -->
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
     
	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
		<caption>
		<s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
		</caption>
		<colgroup>
			<col style="width:15%;" />
			<col style="width:45%;" />
			<col style="width:15%;" />
			<col style="width:25%;" />
		</colgroup>
		<tbody>
		<tr id="dbmsTypNm_tr">
			<th scope="row"><label for="dbmsTypNm"><s:message code="DBMS.KIND.VERS" /></label></th>
			<td colspan="3"><input type="text" id="dbmsTypNm" name="dbmsTypNm" class="wd98p d_readonly" value="${result.dbmsTypNm}" readonly />
			</td>
		</tr>
		<!-- 입력시 입력 -->
		<tr>
			<th scope="row"><label for="connTrgLnkUrl"><s:message code="CNCT.URL" /></label></th>
			<td><input type="text" id="connTrgLnkUrl" name="connTrgLnkUrl" class="wd98p d_readonly" value="${result.connTrgLnkUrl}" readonly />
			</td>
			<th scope="row" class="th_require"><label for="dbConnAcId"><s:message code="DB.CONN.ACNT.ID" /></label></th>
			<td ><input type="text" id="dbConnAcId" name="dbConnAcId"
				class="wd98p" value="${result.dbConnAcId}"/></td>
		</tr>
		<tr>
			<th scope="row"><label for="connTrgDrvrNm"><s:message code="DRIVER.NM" /></label></th>
			<td><input type="text" id="connTrgDrvrNm" name="connTrgDrvrNm" class="wd98p d_readonly" value="${result.connTrgDrvrNm}" readonly />
			</td>
			<th scope="row" class="th_require"><label for="dbConnAcPwd"><s:message code="DB.CONN.ACNT.PWD" /></label></th>
			<td ><input type="password" id="dbConnAcPwd" name="dbConnAcPwd"
				class="wd98p" value="${result.dbConnAcPwd}"/></td>
		</tr>
		<tr>
	        <th scope="row"><label for="gpucFsvrId">행공센FTP명</label> </th> <!-- 기관명 -->
	        <td colspan="3">
	          	<div class="sbox wd50p">
				<label class="sbox_label" for="gpucFsvrId"></label>
				<select id="gpucFsvrId" class="" name="gpucFsvrId">
					<option value=""><s:message code="CHC" /></option>
					<c:forEach var="code" items="${codeMap.gpucFsvrId}" varStatus="status">
						<option value="${code.codeCd}" <c:if test='${search.ibsStatus eq "U" and code.codeCd eq result.gpucFsvrId}'>selected</c:if>>${code.codeLnm}</option>
					</c:forEach>
				</select>
				</div>
				<input type="hidden" id="bfGpucFsvrId" name="bfGpucFsvrId" value="${result.gpucFsvrId}"/>	
			</td>
		</tr>
	</tbody>
	</table>
</div>
<!-- </body> -->
<!-- </html> -->