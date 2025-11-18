<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="DDL.TBL.DTL.INFO" /></title> <!-- DDL테이블 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		
		var requiremessage = "<s:message code="VALID.REQUIRED" />";
		
		//폼검증
		$("#frmInput").validate({
			rules: {
				mstDdlTblLnm		: "required",
				histDdlTblLnm		: "required",


			},
			messages: {
				mstDdlTblLnm		: requiremessage,
				histDdlTblLnm		: requiremessage,
			}
		});
		
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#divInputBtn").show();
	
		initSearchButton();
		//폼 저장 버튼 초기화...
		$('#btnGridSave').click(function(event){
			event.preventDefault();  //브라우저 기본 이벤트 제거...
			
			//IBSheet 저장용 JSON 전역변수 초기화
			ibsSaveJson = null;
			
			//변경한 시트 단건 내용을 저장...
//	 		alert("단건저장");
			//폼 검증...
			if(!$("#frmInput").valid()) return false;
			
			//저장할래요? 확인창...
			var message = "<s:message code="CNF.SAVE" />";
			showMsgBox("CNF", message, 'SaveRow');
			
		});
		//폼 초기화 버튼 초기화...
		$('#btnReset').click(function(event){
			event.preventDefault();  //브라우저 기본 이벤트 제거...
			//alert("폼 초기화");
			$("form[name=frmInput]")[0].reset();
			resetForm($("form#frmInput"));
			/* var row = grid_sheet.GetSelectRow();
			if(row < 1) {
				$("form#frmInput")[0].reset();
			    //선택행 셋팅..
			    var tmptit = "테이블을 선택하세요.";
			    $("#tbl_sle_title").html(tmptit);
			} else {
				tblClick(row);
			} */
			
		});
		
		//주테이블 팝업호출
		$("#mstDdlTblPop").click(function(){
			var param = "&popRqst=Y&mstDcd=Y"; //$("form#frmInput").serialize();
	    	openLayerPop("<c:url value='/meta/ddl/popup/ddltblmst_pop.do' />", 800, 600, param);
	    });
		//보조테이블 호출
		$("#HistDdlTblPop").click(function(){
			var param = "&popRqst=Y&mstDcd=N"; //$("form#frmInput").serialize();
	    	openLayerPop("<c:url value='/meta/ddl/popup/ddltblmst_pop.do' />", 800, 600, param);
	    });
	
		
	});
	
function returnMstTblPop(ret) {
	 	//alert(ret);
		var retjson = jQuery.parseJSON(ret);
		
		$("#frmInput #dbSchId").val(retjson.dbSchId);
		$("#frmInput #mstDdlTblPnm").val(retjson.ddlTblPnm);
		$("#frmInput #mstDdlTblLnm").val(retjson.ddlTblLnm);
		$("#frmInput #mstDdlTblId").val(retjson.ddlTblId);
}
function returnHistTblPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmInput #dbSchId").val(retjson.dbSchId);
	$("#frmInput #histDdlTblPnm").val(retjson.ddlTblPnm);
	$("#frmInput #histDdlTblLnm").val(retjson.ddlTblLnm);
	$("#frmInput #histDdlTblId").val(retjson.ddlTblId);
}

</script>
</head>
<body>
   <div class="stit"><s:message code="DTL.INFO" /></title> <!-- 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	<input type ="hidden" id="objId" name="objId" value="${result.objId}">
   	<input type ="hidden" id="mstDdlTblId" name="mstDdlTblId" value="${result.mstDdlTblId}">
   	<input type ="hidden" id="histDdlTblId" name="histDdlTblId" value="${result.histDdlTblId}">
   	<input type ="hidden" id="dbSchId" name="dbSchId" value="${result.dbSchId}">
   	<input type ="hidden" id="regTypCd" name="regTypCd" value="">
  	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /><!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

      <tbody>                   
                            <tr>
                     	        <th scope="row"><label for="mstDdlTblPnm"><s:message code="TBL.PHYC.LGC.NM" /></label></th><!-- 테이블 물리/논리명 -->
                                <td ><input type="text" id="mstDdlTblPnm" name="mstDdlTblPnm" class="wd200" value="${result.mstDdlTblPnm}" readonly/>
                                <input type="text" id="mstDdlTblLnm" name="mstDdlTblLnm" class="wd200" value="${result.mstDdlTblLnm}" readonly />
                                <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						        <button class="btnSearchPop" id="mstDdlTblPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						        </td>
                           	</tr>
                           	<tr>
                           	<th scope="row"><label for="histDdlTblPnm"><s:message code="SPRT.STGE.TBL.PHYC.LGC.NM" /></label></th><!-- 분리보관 테이블 물리/논리명 -->
                                <td ><input type="text" id="histDdlTblPnm" name="histDdlTblPnm" class="wd200" value="${result.histDdlTblPnm}" readonly />
                                     <input type="text" id="histDdlTblLnm" name="histDdlTblLnm" class="wd200" value="${result.histDdlTblLnm}" readonly/>
                                <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						        <button class="btnSearchPop" id="HistDdlTblPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						        </td>
                            </tr>
                            
                            <tr>
                           		<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" >${result.objDescn}</textarea></td>
                                
                            </tr>
                   </tbody>
    </table>
    </div>
    
    </form>
    
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>

	<!-- 입력폼 버튼... --> 
 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />

</body>
</html>