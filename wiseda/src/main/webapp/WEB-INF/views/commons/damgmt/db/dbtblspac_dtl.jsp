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
<title><s:message code="APL.RULE" /></title> <!-- DB테이블스페이스 상세정보 -->
<script type="text/javascript">
var connTrgSchJson = ${codeMap.connTrgSch} ;

$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			dbTblSpacPnm			: "required",
			dbConnTrgId			: "required",
			dbSchId			: "required",
			tblSpacTypCd		: "required"
		},
		messages: {
			dbTblSpacPnm			: requiremessage,
			dbConnTrgId			: requiremessage,
			dbSchId             :requiremessage,
			tblSpacTypCd		: requiremessage,
		}
	});
	

	
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
	
	$("#frmInput input[name=dbTblSpacPnm]").focus();

  //	    double_select(connTrgSchJson, $("#frmInput #dbConnTrgId"));
//     	$('select', $("#frmInput #dbConnTrgId").parent()).change(function(){
//     		double_select(connTrgSchJson, $(this));
//     	});
   	


  //조회 결과값 초기 셋팅
	$("#frmInput #dbConnTrgId").val('${result.dbConnTrgId}');
	$("#frmInput #tblSpacTypCd").val('${result.tblSpacTypCd}');
	$("#frmInput #ddlDisplay").val('${result.ddlDisplay}');
	$("#frmInput #defaultUsage").val('${result.defaultUsage}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	
	//double_select(connTrgSchJson, $("#frmInput #dbConnTrgId"));

	$("#frmInput #dbSchId").val('${result.dbSchId}');
	

});

$(window).on('load',function() {



});

</script>
</head>
<body>
   <div class="stit"><s:message code="APL.RULE" /></div> <!-- DB테이블스페이스 상세정보 --> 
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="dbTblSpacId" name="dbTblSpacId" value="${result.dbTblSpacId}" >
   
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="TBL.SPACE" /> <!-- 테이블스페이스 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th class="th_require"><s:message code="TBL.SPACE" /></th> <!-- 테이블스페이스물리명 -->
        <td>
        <input type="text" name="dbTblSpacPnm" class="wd240" value="${result.dbTblSpacPnm}" >
        </td>
        <th><s:message code="LGC.NM" /></th> <!-- 테이블스페이스논리명 -->
        <td>
        <input type="text" name="dbTblSpacLnm" class="wd240" value="${result.dbTblSpacLnm}" >
        </td>
      </tr>
      <tr>
          <th><s:message code="DB.MS.NM" /></th> <!-- DBMS명 -->
        <td>
        <select id="dbConnTrgId" class="wd240" name="dbConnTrgId">
                                       <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                	<c:forEach var="code" items="${codeMap.connTrgDbms}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
        </td>
       
        <th class="th_require"><s:message code="TBL.SPACE.PTRN" /></th> <!-- 테이블스페이스유형 -->
        <td>
        <select id="tblSpacTypCd" class="wd240" name="tblSpacTypCd">
                                       <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                	<c:forEach var="code" items="${codeMap.tblSpacTypCd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select> 
        </td>
      </tr>
      <tr>
        <th><s:message code="DDL.DISPLAY.YN" /></th> <!-- DDL표시여부 -->
        <td>
        <select id="ddlDisplay" class="wd240" name="ddlDisplay">
					                	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
										<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
                                    </select>
        </td>
        <th><s:message code="BSIC.TBL.SPACE.YN" /></th> <!-- 디폴트테이블스페이스여부 -->
        <td>
        <select id="defaultUsage" class="wd240" name="defaultUsage">
					                	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
										<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
                                    </select>
        </td>
      </tr>
      <tr>
        <th><s:message code="DATA.FILE" /></th> <!-- 데이터파일 -->
        <td>
        <input type="text" name="dbTblSpacDatafile" class="wd240" value="${result.dbTblSpacDatafile}" >
        </td>
        <th><s:message code="TBL.SPACE.GRP" /></th> <!-- 테이블스페이스그룹 -->
        <td>
        <input type="text" name="dbTblSpacGroup" class="wd240" value="${result.dbTblSpacGroup}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="FIRT.SIZE" /></th> <!-- 초기사이즈 -->
        <td>
        <input type="text" name="dbTblSpacInitSize" class="wd240" value="${result.dbTblSpacInitSize}" >
        </td>
        <th><s:message code="NXT.SIZE" /></th> <!-- 다음사이즈 -->
        <td>
        <input type="text" name="dbTblSpacNextSize" class="wd240" value="${result.dbTblSpacNextSize}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="MAX.SIZE" /></th> <!-- 최대사이즈 -->
        <td>
        <input type="text" name="dbTblSpacMaxSize" class="wd240" value="${result.dbTblSpacMaxSize}" >
        </td>
        <th><s:message code="WARNING.SIZE" /></th> <!-- 경고사이즈 -->
        <td>
        <input type="text" name="dbTblSpacWrn" class="wd240" value="${result.dbTblSpacWrn}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="LIMI.SIZE" /></th> <!-- 한계사이즈 -->
        <td>
        <input type="text" name="dbTblSpacMgn" class="wd240" value="${result.dbTblSpacMgn}" >
        </td>
        <th><s:message code="REG.PTRN" /></th> <!-- 등록유형 -->
        <td>
        <select id="regTypCd" class="wd240" name="regTypCd" disabled="disabled">
					                	<c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select> 
        </td>
      </tr>
      <tr>
        <th><s:message code="TBL.SPACE.SCRIPT" /></th> <!-- 테이블스페이스스크립트 -->
        <td colspan="3">
        <textarea name="dbTblSpacScript" class="wd98p" >${result.dbTblSpacScript}</textarea>
        </td>
      </tr>
      <tr>
        <th><s:message code="CONTENT.TXT" /></th> <!-- 설명 -->
        <td colspan="3">
        <textarea name="objDescn" class="wd98p" >${result.objDescn}</textarea>
        </td>
      </tr>
      <tr>
        
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div style="text-align: center;">
		<button class="btn_search" id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_search" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>