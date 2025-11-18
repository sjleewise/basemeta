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
<title><s:message code="BUBD.DTL.INFO" /></title> <!-- 게시판상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	var numbermessage = "<s:message code="VALID.NUMBER" />";
	

	
	
// 	  //공통코드 초기화 셋팅
//     setCodeSelect("COM004", "C", $("form[name=frmSearch] #bbsTyCode"));
	
	//조회 결과값 초기 셋팅
	$("#frmInput #bbsTyCode").val('${result.bbsTyCode}');
	$("#frmInput #bbsAttrbCode").val('${result.bbsAttrbCode}');
	$("#frmInput #tmplatId").val('${result.tmplatId}');
	$("#frmInput #replyPosblAt").val('${result.replyPosblAt}');
	$("#frmInput #fileAtchPosblAt").val('${result.fileAtchPosblAt}');
	$("#frmInput #useAt").val('${result.useAt}');
	$("#frmInput #cateCode").val('${result.cateCode}');
	$("#frmInput #menuCode").val('${result.menuCode}');
	
// 	alert('${result.useAt}');
// 	alert($("#frmInput #useAt}").val());
	
	//폼검증
	$("#frmInput").validate({
		rules: {
			bbsNm	: "required",
			bbsTyCode	: "required",
			bbsAttrbCode	: "required",
			tmplatId	: "required",
			replyPosblAt	: "required",
			fileAtchPosblAt : "required",
			posblAtchFileNumber:{required : true, number : true},
			useAt	: "required",
			cateCode	: "required",
			menuCode	: "required"
// 			frstRegisterId	: "required",
// 			frstRegisterPnttm	: "required"
// 			progrmStrePath 	: "required",
// 			url				: "required"
		},
		messages: {
			bbsNm	: requiremessage,
			bbsTyCode	: requiremessage,
			bbsAttrbCode	: requiremessage,
			tmplatId	: requiremessage,
			replyPosblAt	: requiremessage,
			fileAtchPosblAt	: requiremessage,
			posblAtchFileNumber:{required : requiremessage, number : numbermessage},
			useAt	: requiremessage,
			cateCode	: requiremessage,
			menuCode	: requiremessage
// 			frstRegisterId	: requiremessage,
// 			frstRegisterPnttm	: requiremessage
// 			progrmStrePath 	: requiremessage,
// 			url				: requiremessage
		}
	});
	
// 	$("input[posblAtchFileNumber=txtNUM]").css("ime-mode", "disabled").keypress(function(){//숫자만 입력
// 		if(event.keyCode < 48 || event.keyCode > 57){event.returnValue=false;}})
	
// 	$("#frmInput").validate({
// 		rules: {
// 			posblAtchFileNumber:"integer"
// 		},
// 		messages:{
// 			posblAtchFileNumber:"numbermessage"
// 		}
// 	});
	
	//alert("조회완료");
		$("input[name=bbsId]").attr('readonly', true);
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=bbsId]").attr('readonly', true);
// 	}
	
	
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnfrmReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmInput]")[0].reset();
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
	
	$("#frmInput input[name=bbsNm]").focus();


});
</script>
</head>
<body>
   <div class="stit"><s:message code="BUBD.DTL.INFO" /></div> <!-- 게시판 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" name="bbsId" class="wd300" value="${result.bbsId}" >
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th class="th_require"><s:message code="BUBD.NM" /></th> <!-- 게시판명 -->
        <td colspan="3">
        <input type="text" name="bbsNm" class="wd300" value="${result.bbsNm}" >
        </td>
      </tr>
      
      <tr>
      <th scope="row" class="th_require"><label for="bbsTyCode"><s:message code="BUBD.PTRN" /></label></th> <!-- 게시판 유형 -->
      <td colspan="3"><select id="bbsTyCode" class="" name="bbsTyCode" value = "${result.bbsTyCode}" >
      									<option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
                                        <c:forEach var="code" items="${codeMap.bbsTy }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>   
       </tr>
       
<!--       <tr> -->
<!--         <th>게시판 유형</th> -->
<!--         <td colspan="3"> -->
<%--          <select style="" name="bbsTyCode" class="wd300" value="${result.bbsTyCode}" >  --%>
<%--          <option selected="" value="">${result.bbsTyCode}</option> --%>
<!--         <option value = "BBST01">일반게시판(BBST01)</option> -->
<!--         <option value = "BBST02">익명게시판(BBST02)</option> -->
<!--         <option value = "BBST03">공지게시판(BBST03)</option> -->
<!--         <option value = "BBST04">방명록(BBST04)</option> -->
<!--         <option value = "BBST05">산출물(BBST05)</option> -->
<%--         </select> --%>
<!--         </td> -->
<!--       </tr> -->
      
      <tr>
      <th scope="row" class="th_require"><label for="bbsAttrbCode"><s:message code="BUBD.ATRB" /></label></th> <!-- 게시판 속성 -->
      <td colspan="3"><select id="bbsAttrbCode" class="" name="bbsAttrbCode">
      									<option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
                                        <c:forEach var="code" items="${codeMap.bbsAttr }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>   
       </tr>
      
 <tr>
      <th scope="row" class="th_require"><label for="tmplatId"><s:message code="TMPL.PTRN" /></label></th> <!-- 템플릿 유형 -->
      <td colspan="3"><select id="tmplatId" class="" name="tmplatId">
      									<option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
                                        <c:forEach var="code" items="${codeMap.bbsTmplT }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>   
       </tr>
       
<!--       <tr> -->
<!--         <th>템플릿 유형</th> -->
<!--         <td colspan="3"> -->
<%--         <select style="" name="tmplatId" class="wd300" value="${result.tmplatId}" >  --%>
<%--         <option selected="" value="">${result.tmplatId}</option> --%>
<!--         <option value = "TMPLAT_BOARD_DEFAULT">게시판템플릿(TMPLAT_BOARD_DEFAULT)</option> -->
<!--         <option value = "TMPLAT_CMNTY_DEFAULT">커뮤니티템플릿(TMPLAT_CMNTY_DEFAULT)</option> -->
<!--         <option value = "TMPLAT_CLUB__DEFAULT">동호회템플릿(TMPLAT_CLUB__DEFAULT)</option> -->
<%--         </select> --%>
<!--         </td> -->
<!--       </tr> -->
      
      <tr>
        <th><s:message code="BUBD.TXT" /></th> <!-- 게시판설명 -->
        <td colspan="3">
        <input type="text" name="bbsIntrcn" class="wd300" value="${result.bbsIntrcn}" >
        </td>
      </tr>
 
   <tr>
        <th class="th_require"><s:message code="RPLY.POSB.YN" /></th> <!-- 답변 가능 여부 -->
        <td colspan="3">
        <select  id="replyPosblAt"  name="replyPosblAt" class=""  >
        <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
        <option value = "Y"><s:message code="USE" /></option> <!-- 사용 -->
        <option value = "N"><s:message code="NO.USE" /></option> <!-- 비사용 -->
        </select>
        </td>
      </tr>

    <tr>
        <th class="th_require"><s:message code="FILE.ATCM.YN" /></th> <!-- 파일 첨부 여부 -->
        <td colspan="3">
        <select id="fileAtchPosblAt" name="fileAtchPosblAt" class="" >
        <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
        <option value = "Y"><s:message code="USE" /></option> <!-- 사용 -->
        <option value = "N"><s:message code="NO.USE" /></option> <!-- 비사용 -->
        </select>
        </td>
      </tr>
      
      
      
      <tr>
        <th class="th_require"><s:message code="ATFL.POSB.CNT" /></th> <!-- 첨부파일 가능 개수 -->
        <td colspan="3">
        <input type="text" name="posblAtchFileNumber" class="wd300" value="${result.posblAtchFileNumber}" >
        </td>
      </tr>
      
      
 
      <tr>
        <th><s:message code="ATFL.SIZE" /></th> <!-- 첨부파일 사이즈 -->
        <td colspan="3">
        <input type="text" name="posblAtchFileSize" class="wd300" value="${result.posblAtchFileSize}" >
        </td>
      </tr>
      
     <tr>
        <th class="th_require"><s:message code="BUBD.USE.YN" /></th> <!-- 게시판 사용 여부 -->
        <td colspan="3">
        <select id="useAt" name="useAt" class=""  >
        <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
        <option value = "Y"><s:message code="USE" /></option> <!-- 사용 -->
        <option value = "N"><s:message code="NO.USE" /></option> <!-- 비사용 -->
        </select>
        </td>
      </tr>
      
<!--       <tr> -->
<!--         <th>최초등록 ID</th> -->
<!--         <td colspan="3"> -->
<%--         <input type="text" name="frstRegisterId" class="wd300" value="${result.frstRegisterId}" > --%>
<!--         </td> -->
<!--       </tr> -->
<!--       <tr> -->
<!--         <th>등록 일자</th> -->
<!--         <td colspan="3"> -->
<%--         <input type="text" name="frstRegisterPnttm" class="wd300" value="${result.frstRegisterPnttm}" > --%>
<!--         </td> -->
<!--       </tr> -->
<!--       <tr> -->
<!--         <th>수정자 ID</th> -->
<!--         <td colspan="3"> -->
<%--         <input type="text" name="lastUpdUsrId" class="wd300" value="${result.lastUpdUsrId}" > --%>
<!--         </td> -->
<!--       </tr> -->
<!--       <tr> -->
<!--         <th>수정 일자</th> -->
<!--         <td colspan="3"> -->
<%--         <input type="text" name="lastUpdtPnttm" class="wd300" value="${result.lastUpdtPnttm}" > --%>
<!--         </td> -->
<!--       </tr> -->

<!--       <tr> -->
<!--        <th scope="row"><label for="cateCode">업무 구분</label></th> -->
<%--        <td colspan="3"><select id="cateCode" class="cateCode" name="cateCode"> --%>
<!--                                         <option selected="" value="">=============</option> -->
<%--                                         <c:forEach var="code" items="${codeMap.cateCode }" varStatus="status" > --%>
<%--                                         <option value="${code.codeCd }">${code.codeLnm}</option> --%>
<%--                                         </c:forEach> --%>
<%--                                     </select> --%>
<!--                                     </td>   -->
<!--       </tr> -->
      
      <tr>
       <th scope="row" class="th_require"><label for="cateCode"><s:message code="BULI.DSTC" /></label></th> <!-- 게시물 구분 -->
       <td colspan="3"><select id="cateCode" class="" name="cateCode">
                                       <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                	<c:forEach var="code" items="${codeMap.bbscateCod}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
                                 </td>
      </tr>
      
      <tr>
       <th scope="row" class="th_require"><label for="menuCode"><s:message code="MENU.DSTC" /></label></th> <!-- 메뉴 구분 -->
       <td colspan="3"><select id="menuCode" class="" name="menuCode">
                                       <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                	<c:forEach var="code" items="${codeMap.bbsmenuCode}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
                                 </td>
      </tr>
      
      
      <tr>
        <th><s:message code="BRD"/> url</th> <!-- 게시판 -->
        <td colspan="3">
        <input type="text" name="bbsUrl" class="wd400" value="${result.bbsUrl}" >
        <a href="<c:url value="${result.bbsUrl}" />"><s:message code="BUBD.GO.SHIFT.CLICK" /></a> <!-- 게시판 가기(shift + 클릭) -->
        </td>
      </tr>
      
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button class="btn_save" id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_reset" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>