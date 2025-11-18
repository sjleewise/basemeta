<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title>업무규칙조회 기본정보</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
	//업무영역 검색 팝업
	$("#btnSearchBizAreaPop").click(function(){
		var url = "<c:url value='/dq/criinfo/bizarea/popup/bizarea_pop.do' />";
    	var param = "sflag=BRDTL"; 
    	//openLayerPop(url, 800, 600, param);
    	var popup = OpenWindow(url+"?"+param,"bizareaSearch","800","600","yes");
				  });
	    	  
	//품질지표 검색 팝업
	$("#btnSearchDqiPop").click(function(){
		var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=BRDTL"; 
    	//openLayerPop(url, 800, 600, param);
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
				  });
	    	  
	//중요정보항목 검색 팝업
	$("#btnSearchCtqPop").click(function(){
		var url = "<c:url value='/dq/criinfo/ctq/popup/ctq_pop.do' />";
    	var param = "sflag=BRDTL"; 
//     	openLayerPop("<c:url value='/dq/criinfo/ctq/popup/ctq_pop.do' />", 800, 600, param);
    	var popup = OpenWindow(url+"?"+param,"ctqSearch","800","600","yes");
				  });
	    	  
	//담당자 검색 팝업 호출
	$("#userPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
	$("#useYn").val("${result.useYn }");
	
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
});


//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmDetail #brCrgpUserId").val(retjson.userId);
	$("#frmDetail #brCrgpUserNm").val(retjson.userNm);
}


</script>
<!-- </head> -->
<!-- <body> -->
	  	<form name="frmDetail" id="frmDetail" method="post" action="">
			<div style="clear:both; height:5px;"><span></span></div>
			<div id="valid_bizrule" style="display: none;">
			<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
			<div style="clear:both; height:10px;"><span></span></div>
			</div>
			<div class="tb_basic">
			
				<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" />
	  			<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" />
				<input type="hidden" id="bizAreaId" name="bizAreaId" />
				<input type="hidden" id="dqiId" name="dqiId" />
				<input type="hidden" id="ctqId" name="ctqId" />
				<input type="hidden" id="brCrgpUserId" name="brCrgpUserId" />
			
				
				<div class="stit"><s:message code="BZWR.RGR.BSIC.INFO"/></div><!-- 업무규칙 기본정보 -->
				<table border="0" cellspacing="0" cellpadding="0"  summary="">
		        <caption><s:message code="BZWR.RGR.BSIC.INFO"/></caption><!-- 업무규칙 기본정보 -->
		        <colgroup>
		            <col style="width:12%;">
		            <col style="width:38%;">
		            <col style="width:12%;">
		            <col style="width:38%;">
		        </colgroup>
			      <tr>
			        <th><s:message code="BZWR.RGR.NM"/></th><!-- 업무규칙명 -->
			        <td>
                    	<input type="text" class="wd60p" id="brNm" name="brNm" value="${result.brNm }" readonly/>
			        </td>
			        <th rowspan="8" scope="row"><s:message code="CONTENT.TXT" /></th><!-- 설명 -->
			        <td rowspan="8">
			        <textarea style="height:140px;width:99%;"  id="objDescn" name="objDescn" accesskey="" readonly>${result.objDescn }</textarea>
			        </td>
			      </tr>
			      <tr>
			        <th><s:message code="BZWR.TRRT.NM" /></th><!-- 업무영역명 -->
			       <td>
			        	<input type="text" class="wd60p" id="bizAreaLnm" name="bizAreaLnm"  value="${result.bizAreaLnm }" readonly/>
			        	<button class="btnSearchPop" id="btnSearchBizAreaPop" name="btnSearchBizAreaPop"><s:message code="SRCH" /></button><!-- 검색 -->
			        </td>
			      </tr>
			      
			      <tr>
			        <th><s:message code="QLTY.INDC.NM"/></th><!-- 품질지표명 -->
			        <td>
						<input type="text" class="wd60p" id="dqiLnm" name="dqiLnm" value="${result.dqiLnm }" readonly/>
			        	<button class="btnSearchPop" id="btnSearchDqiPop" name="btnSearchDqiPop"><s:message code="SRCH" /></button><!-- 검색 -->
			        </td>
			      </tr>
			      <tr>
			        <th><s:message code="IMCE.INFO.ITEM.NM"/></th><!-- 중요정보항목명 -->
			        <td>
			        <input type="text" class="wd60p" id="ctqLnm" name="ctqLnm" value="${result.ctqLnm }" readonly/>
			        	<button class="btnSearchPop" id="btnSearchCtqPop" name="btnSearchCtqPop"><s:message code="SRCH" /></button><!-- 검색 -->
			        </td>
			      </tr>
			      <tr>
			        <th><s:message code="BZWR.RGR.CHGR"/></th><!-- 업무규칙담당자 -->
			         <td>
			        	<input type="hidden" class="" id="brCrgpUserId" name="brCrgpUserId" value="${result.brCrgpUserId}" readonly/>
			        	<input type="text" class="wd60p" id="brCrgpUserNm" name="brCrgpUserNm" value="${result.brCrgpUserNm}" readonly/>
			        	<button class="btnSearchPop" id="userPop" name="userPop"><s:message code="SRCH" /></button><!-- 검색 -->
			        </td>
			      </tr>
			      <tr>
			        <th><s:message code="USE.YN"/></th><!-- 사용여부 -->
			        <td>
						<select id="useYn" name="useYn" class="input" value="${result.useYn }" disabled="disabled">
				        	<option value="Y"><s:message code="MSG.YES"/></option><!-- 예 -->
				        	<option value="N"><s:message code="MSG.NO" /></option><!-- 아니오 -->
			        	</select>
			        </td>
			      </tr>
			      <tr>
			       <th>목표치</th><!-- 목표치 -->
			        <td>
                    	<input type="text" class="wd60p" id="goal" name="goal" value="${result.goal }" readonly/>&nbsp;&nbsp;&nbsp;(%)
			        </td>
			      </tr>
			      <tr>
			       <th><s:message code="weight.val"/></th><!-- 가중치 -->
			        <td>
                    	<input type="text" class="wd60p" id="weight" name="weight" value="${result.weight }" readonly/>
			        </td>
			      </tr>
			      </table>
			</div>
			    </form>
			<div style="clear:both; height:10px;"><span></span></div>
			<div id="otherinfo" style="display:none;"><%@include file="../../meta/stnd/otherinfo.jsp" %></div>

			<div style="clear:both; height:10px;"><span></span></div>
			<div id="buttonRqst" style="display: none;">
			<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
			</div>
<!-- </body> -->
<!-- </html> -->