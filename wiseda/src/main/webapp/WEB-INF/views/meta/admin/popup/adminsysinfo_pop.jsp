<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="INFO.SYS.REG" /></title> <!-- 정보시스템 등록 -->

<script type="text/javascript">
var saveChk = "N";


$(document).ready(function() {
	
	/* if($("#shdStrDtm").val() != "" && saveChk == "N"){	
		saveChk = "Y";
		//setselectbytext($("#frmInput #shdStrHr"), "${result.shdStrHr}");
		//setselectbytext($("#frmInput #shdStrMnt"), "${result.shdStrMnt}");
	} */
	
    $("#btnSaveBottom").click(function() {
        
        if($("#orgCd").val() == "") {
	    	showMsgBox("INF", "기관명을 입력하세요.");
			return false;
        } else if($("#infoSysNm").val() == ""){
	    	showMsgBox("INF", "<s:message code="INFO.SYS.NM.INPT" />");
			return false;
		}else if($("#constYy").val() == ""){
			showMsgBox("INF", "구축년도를 입력하세요.");
			return false;
		}else if($("#relLaw").val() == ""){
			showMsgBox("INF", "<s:message code="RECV.ORG.NM.INPT" />");
			return false;
		} else{
	    		saveChk = "Y";
	        	doAction("Save");
		}
    }).show();

    //팝업 닫기
    $("div.pop_tit_close, #btnCloseBottom").click(function(){
    	parent.closeLayerPop();
    });

    //SboxSetLabelEvent();

    $("#btnHelp").click(function(){
		var param = ""; 
				
		var vUrl = "<c:url value='/meta/admin/popup/infosys_help.do?typCd=info' />"; 
				
	    
		OpenWindow(vUrl, "infosys_help", 1000, 430, null);
	});

    
    
});

$(window).on('load',function() {
	//initGridPop();
	doAction("Search");
});


function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Save":
	        //저장 중복처리 방지 버튼 비활성화
        	$("#btnSaveBottom").attr("disabled", true);
        
        	if($('#infoSysCd').val() != null && $('#infoSysCd').val() != '') {
        		$('#ibsStatus').val("U");
            } else {
            	$('#ibsStatus').val("I");
            }
        	var param = $("#frmInput").serialize();
        	var saveJson = getform2IBSjson($("#frmInput"));
        	var url = "<c:url value="/meta/admin/regInfoSysList.do"/>";
        	//alert(JSON.stringify(saveJson));
			IBSpostJson2(url, saveJson, "", ibscallback2);

			//IBSpostJson(url, param, ibscallback);
        	break;
    }       
}
function ibscallback2(res) {
	var result = res.RESULT.CODE;

	if (result == 0) {
		if (postProcessIBS != null) {
			postProcessIBS(res);
		}
	} else if (result == 401) {
		// 권한이 없어요...
		showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	} else {
		// alsert("저장실패");
		// 공통메시지 팝업 : 실패 메세지...
		showMsgBox("ERR", res.RESULT.MESSAGE);

		if(res.action == "<%=WiseMetaConfig.IBSAction.REG%>") {
			$("#btnSaveBottom").attr("disabled", false);
		}
	}
}

function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			parent.postProcessIBS(res);
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
	}
	
}


</script>

</head>

<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
	<c:if test="${empty data.infoSysCd }">
   	 <div class="pop_tit_txt"><s:message code="INFO.SYS.REG" /></div> <!-- 정보시스템 등록 -->
    </c:if>
    <c:if test="${!empty data.infoSysCd }">
   	 <div class="pop_tit_txt"><s:message code="INFO.SYS.MOD" /></div> <!-- 정보시스템 수정 -->
    </c:if>
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
    
	  <!--  입력폼 -->
	  <div id="search_div">
        
        <form id="frmInput" name="frmInput" method="post">
        
        	<input type="hidden" id="ibsStatus" name="ibsStatus" class="wd98p" style="border:0;" value="I" />
        	<input type="hidden" style="background-color: #e2e2e2;" id="infoSysCd" name="infoSysCd" class="wd98p" style="border:0;" readonly value="<c:out value="${data.infoSysCd}"/>"/>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div id="conStep_01" style="display:block;">
					     <div class="tb_basic">
<!-- 					     <div class="bt02"> -->
<%-- 							<a id="btnHelp" name="btnHelp"><img src="<c:url value="/images/icon_help.png"/>" /></a>		    --%>
<!-- 						</div> -->
						     <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
					             <caption>
					             <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
					             </caption>
					             <colgroup>
					             <col style="width:25%;" />
					             <col style="width:75%;" />
					             </colgroup>
					             <tbody>
					                 <tr>
					                     <th scope="row" class="th_require"><label for="orgCd"><s:message code="ORG.NM"/></label> </th> <!-- 기관명 -->
					                     <td>
					                     <c:choose>
					                     	<c:when test="${empty data.infoSysCd}">
					                     	<div class="sbox wd98p">
											<label class="sbox_label" for="orgCd"></label>
					                     	<select id="orgCd" name="orgCd" class="wd98p">
									            <option value=""><s:message code="CHC" /></option>
				                                <c:forEach var="code" items="${codeMap.orgCd}" varStatus="status" >
				                                       <option value="${code.codeCd}">${code.codeLnm}</option>
				                                </c:forEach>
			                                </select>
			                                </div>
					                     	</c:when>
					                     	<c:otherwise>
				                                <c:forEach var="code" items="${codeMap.orgCd}" varStatus="status" >
				                                    <c:if test="${data.orgCd eq code.codeCd}">
				                                    <input type="text" id="orgNm" name="orgNm" class="wd98p" readonly style="border:0;background-color: #e2e2e2;" value="<c:out value="${code.codeLnm}"/>" />
				                                    <input type="hidden" id="orgCd" name="orgCd" value="${data.orgCd}" />
				                                    </c:if>
				                                </c:forEach>
					                     	</c:otherwise>
					                     </c:choose>
	    		                         </td>
	    		                     </tr>
					                     <%-- <th scope="row"><label for="infoSysCd"><s:message code="INFO.SYS.CD" /></label></th> <!--정보시스템코드  --> --%>
									 <tr>	 
										 <th scope="row" class="th_require"><label for="infoSysNm"><s:message code="INFO.SYS.NM" /></label></th> <!--정보시스템명  -->
									     <td><input type="text" id="infoSysNm" name="infoSysNm" class="wd98p" <c:if test="${!empty data.infoSysNm}">readonly style="border:0;background-color: #e2e2e2;"</c:if> value="<c:out value="${data.infoSysNm}"/>" /></td>
			                       	 </tr>
					                
									<tr>
									<th scope="row" class="th_require"><label for="constYy"><s:message code="CONST.YEAR" /></label></th> <!--구축년도  --> 
									   <td>
									   	<div class="sbox wd30p">
										<label class="sbox_label" for="constYy"></label>
									   	<select id="constYy" name="constYy" class="wd30p" >
									   	<c:if test="${empty data.infoSysCd }">
									   		<option value="" selected><s:message code="CHC" /></option>
									   	</c:if>
									   	<c:if test="${!empty data.infoSysCd }">
									   		<option value=""><s:message code="CHC" /></option>
									   	</c:if>
									    	<c:forEach var = "i" begin= "0" end="30" step="1">
									   		<option value="${toYear-i}" <c:if test="${(toYear-i) eq data.constYy }">selected</c:if> >${toYear-i}</option>
									   		</c:forEach>
									   	</select>
									   	</div>
									   </td>
									</tr>
									   <!-- <td><input type="text" id="constYy" name="constYy" class="wd98p" style="border:0;" /></td> -->
									<tr>  
									   <th scope="row" class="th_require"><label for="relLaw"><s:message code="REL.LAW" /></label></th> <!--관련법령  -->
									   <td><%-- <input type="text" id="relLaw" name="relLaw" class="wd98p" style="border:0;" value="<c:out value="${data.relLaw}"/>" /> --%>
									   <textarea name="relLaw" id="relLaw" rows="5" style="width:98%;" <c:if test = "${empty data.relLaw }">placeholder="관련 법령을 입력하세요."</c:if> >${data.relLaw}</textarea>
									   </td>
							        </tr>
							        <c:if test="${!empty data.infoSysCd}">
							        <tr>
			                           <th scope="row"><label for="operDeptNm"><s:message code="OPER.DEPT.NM" /></label></th> <!--운영부서명  -->
									   <td><input type="text" id="operDeptNm" name="operDeptNm" class="wd98p" style="border:0;" value="<c:out value="${data.operDeptNm}"/>" /></td>
<%-- 									   <td><input type="text" id="operDeptNm" name="operDeptNm" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${data.operDeptNm}"/>" /></td> --%>
									</tr>
							        <tr>
			                           <th scope="row"><label for="crgUserNm"><s:message code="CHG.R.NM" /></label></th> <!--담당자명  -->
									   <td><input type="text" id="crgUserNm" name="crgUserNm" class="wd98p" style="border:0;" value="<c:out value="${data.crgUserNm}"/>" /></td>
<%-- 									   <td><input type="text" id="crgUserNm" name="crgUserNm" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${data.crgUserNm}"/>" /></td> --%>
									</tr>
									<tr>   
									   <th scope="row"><label for="crgTelNo"><s:message code="TLNO" /></label></th> <!-- 전화번호 -->
									   <td><input type="text" id="crgTelNo" name="crgTelNo" class="wd98p" style="border:0;" value="<c:out value="${data.crgTelNo}"/>" /></td>
<%-- 									   <td><input type="text" id="crgTelNo" name="crgTelNo" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${data.crgTelNo}"/>" /></td> --%>
							        </tr>
							        <tr>   
									   <th scope="row"><label for="crgEmailAddr"><s:message code="USER_EMAIL" /></label></th> <!--이메일  -->
									   <td><input type="text" id="crgEmailAddr" name="crgEmailAddr" class="wd98p" style="border:0;" value="<c:out value="${data.crgEmailAddr}"/>" /></td>
<%-- 									   <td><input type="text" id="crgEmailAddr" name="crgEmailAddr" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${data.crgEmailAddr}"/>" /></td> --%>
							        </tr>
							        </c:if>
							        <c:if test="${empty data.infoSysCd}">
							        <tr>
			                           <th scope="row"><label for="operDeptNm"><s:message code="OPER.DEPT.NM" /></label></th> <!--운영부서명  -->
									   <td><input type="text" id="operDeptNm" name="operDeptNm" class="wd98p" style="border:0;" value="<c:out value="${sessionScope.loginVO.orgNm}"/>" /></td>
<%-- 									   <td><input type="text" id="operDeptNm" name="operDeptNm" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${sessionScope.loginVO.orgNm}"/>" /></td> --%>
									</tr>
							        <tr>
			                           <th scope="row"><label for="crgUserNm"><s:message code="CHG.R.NM" /></label></th> <!--담당자명  -->
									   <td><input type="text" id="crgUserNm" name="crgUserNm" class="wd98p" style="border:0;" value="<c:out value="${sessionScope.loginVO.name}"/>" /></td>
<%-- 									   <td><input type="text" id="crgUserNm" name="crgUserNm" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${sessionScope.loginVO.name}"/>" /></td> --%>
									</tr>
									<tr>   
									   <th scope="row"><label for="crgTelNo"><s:message code="TLNO" /></label></th> <!-- 전화번호  -->
									   <td><input type="text" id="crgTelNo" name="crgTelNo" class="wd98p" style="border:0;" value="<c:out value="${sessionScope.loginVO.userHtelno}"/>" /></td>
<%-- 									   <td><input type="text" id="crgTelNo" name="crgTelNo" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${sessionScope.loginVO.userHtelno}"/>" /></td> --%>
							        </tr>
							        <tr>   
									   <th scope="row"><label for="crgEmailAddr"><s:message code="USER_EMAIL" /></label></th> <!--이메일  -->
									   <td><input type="text" id="crgEmailAddr" name="crgEmailAddr" class="wd98p" style="border:0;" value="<c:out value="${sessionScope.loginVO.email}"/>" /></td>
<%-- 									   <td><input type="text" id="crgEmailAddr" name="crgEmailAddr" class="wd98p" style="border:0;background-color: #e2e2e2;" readonly value="<c:out value="${sessionScope.loginVO.email}"/>" /></td> --%>
							        </tr>
							        </c:if>
				                 </tbody>
			                 </table>
					     </div>
					</div>
            </fieldset>
        </form>
            
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop3.jsp" /> --%>
	  </div>
    <!-- 팝업 내용 끝 -->
    <div style="clear:both; height:5px;"><span></span></div>		
	<div id="" style="text-align: center;">
    	<button class="btn_frm_save btn_colse" id="btnSaveBottom" type="button">저장</button>    
    	<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>        
    </div>
	</div>
</div>
</body>
</html>