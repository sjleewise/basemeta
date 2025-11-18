<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="CD.TBL.INQ.1"/></title><!--코드테이블 조회-->


<script type="text/javascript">
	$(document).ready(	function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//그리드 초기화 
// 		initGrid();
		
		 //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
	    $("div.pop_tit_close").click(function(){
	    	//iframe 형태의 팝업일 경우
	    	if ("${metaDmnVO.popType}" == "I") {
	    		parent.closeLayerPop();
	    	} else {
	    		window.close();
	    	}
	    }); 
		 
	    $("#frmSearch input[type=text]").css("border-color","transparent").attr("readonly","true");
	});

	$(window).on('load',function() {
		//그리드 초기화 
		initGrid();
		$(window).resize();
		doAction("SearchMetaCdDmlLst");		
	});

	$(window).resize(function(){
        //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));
    	// grid_sheet.SetExtendLastCol(1);    
});

	function initGrid() {

		with (grid_code) {
			var cfg = {SearchMode : 2,Page : 100	};
			SetConfig(cfg);

			var headers = [ {Text : "<s:message code='DQ.HEADER.METADMN_POP'/>", Align : "Center"	} ];
			//선택|No|상태|유효값ID|유효값|유효값명|설명

			
			 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
	           InitHeaders(headers, headerInfo); 

	           var cols = [
	                   {Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
	   				   {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:0},
	   	               {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:70,  SaveName:"cdValId",    	Align:"Center", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:200,  SaveName:"cdVal",    	Align:"Left", Edit:0, Hidden:0}, 
	                   {Type:"Text",   Width:200,  SaveName:"cdValNm",    	Align:"Left", Edit:0, Hidden:0}, 
	                   {Type:"Text",   Width:300,  SaveName:"objDescn",    	Align:"Left", Edit:0},
	               ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			InitComboNoMatchText(1, "");

			FitColWidth();
			
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다    
			SetExtendLastCol(1);
		}

		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_code);
		//===========================
			
	}

	function doAction(sAction) {

		switch (sAction) {
			/*유효값 조회*/
	        case "SearchMetaCdDmlLst":
	        	//목록성 코드일경우 유효값 정보 조회 하지 않는다
	        	if("${metaDmnVO.cdValTypCd}" != "L"){
	        		var param = "&dmnId="+"${metaDmnVO.dmnId}"; 
		        	grid_code.DoSearch("<c:url value="/dq/profile/popup/metaCdDmnLst.do" />", param);
	        	}
	        	break;
		}
	}

	//조회 에러
	function grid_code_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
	 	}
		if(code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		} else {
			//조회 성공....
		}
	}
	
</script>
</head>

<body>
	<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt"><s:message code="META.DOMAIN.VAL.SERARCH1"/></div><!-- 메타도메인/유효값 조회 -->
		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
		<!-- 팝업 타이틀 끝 -->

		<!-- 팝업 내용 시작 -->
		<div class="pop_content">
			<!-- 검색조건 입력폼 -->
			<div id="search_div">
				<fieldset>
				<legend><s:message code="FOREWORD" /></legend><!--머리말-->
				<form id="frmSearch" name="frmSearch" method="post">
				
				<div class="stit"><s:message code="DIAG.TRGT.CLMN"/></div><!--진단대상컬럼-->


				<div style="clear: both; height: 5px;"><span></span></div>
				<div class="tb_basic">
				<table width="99%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='DIAG.TRGT.CLMN'/>"><!-- /*진단대상컬럼*/ -->
						<caption></caption>
						<colgroup>
							<col style="width: 20%;" />
							<col style="width: 30%;" />
							<col style="width: 20%;" />
							<col style="width: 30%;" />
						</colgroup>

						<tbody>
							<tr>
								<th scope="row"><s:message code="DIAG.TRGT.CLMN.NM"/></th><!--진단대상컬럼명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dbcColNm}"	/></td>
								<th scope="row"><s:message code="DIAG.TRGT.CLMN.KRN.NM"/></th><!--진단대상컬럼한글명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dbcColKorNm}"	/></td>
							</tr>
						</tbody>
					</table>
					</div>
					<div style="clear: both; height: 10px;"><span></span></div>
					<div class="stit"><s:message code='STRD.ITEM.DTL.INFO' /><s:message code='STRD.ITEM.DTL.INFO' /></div><!-- /*표준항목 상세정보*/
 -->
					<div style="clear: both; height: 5px;"><span></span></div>
					<div class="tb_basic">
					<table width="99%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='STRD.ITEM.INQ' />"><!-- /*표준항목조회*/
 -->
						<caption></caption>
						<colgroup>
							<col style="width: 20%;" />
							<col style="width: 30%;" />
							<col style="width: 20%;" />
							<col style="width: 30%;" />
						</colgroup>

						<tbody>
							<tr>
								<th scope="row"><s:message code="STRD.ITEM.PHYC.NM" /></th><!--표준항목물리명-->

								<td><input type="text"  class="wd98p"  value="${metaDmnVO.sditmPnm}"/></td>
								<th scope="row"><s:message code="STRD.ITEM.LGC.NM" /></th><!--표준항목논리명-->

								<td><input type="text"  class="wd98p"  value="${metaDmnVO.sditmLnm}"/></td>
							</tr>
							
							<tr>
								<th scope="row"><s:message code="DMN.PHYC.NM" /></th><!--도메인물리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dmnPnm}"/></td>
								<th scope="row"><s:message code="DMN.LGC.NM" /></th><!--도메인논리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dmnLnm}"/></td>
							</tr>
							
							<tr>
								<th scope="row"><s:message code="CD.DMN.TY"/></th><!--코드도메인유형-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.cdValTypNm}"/></td>
								<th scope="row"><s:message code="VLD.VAL.EXIS.YN"/></th><!--유효값존재여부-->

								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dmnLnm}"/></td>
							</tr>
							
							<tr>
								<th scope="row"><s:message code="LST.TBL.PHYC.NM"/></th><!--목록성테이블물리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.lstEntyPnm}"/></td>
								<th scope="row"><s:message code="LST.TBL.LGC.NM"/></th><!--목록성테이블논리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.lstEntyLnm}"/></td>
							</tr>
							
							<tr>
								<th scope="row"><s:message code="LST.CLMN.PHYC.NM"/></th><!--목록성컬럼물리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.pdmColPnm}"/></td>
								<th scope="row"><s:message code="LST.CLMN.LGC.NM"/></th><!--목록성컬럼논리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.pdmColLnm}"/></td>
							</tr>
						</tbody>
					</table>
					</div>
					
					<div style="clear: both; height: 10px;"><span></span></div>
					<div class="stit"><s:message code="DMN.GRP.DTL.INFO"/></div><!--도메인그룹 상세정보-->
					<div style="clear: both; height: 5px;"><span></span></div>
					<div class="tb_basic">
					<table width="99%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='DMN.GRP.INQ'/>"><!--도메인그룹조회-->
						<caption></caption>
						<colgroup>
							<col style="width: 20%;" />
							<col style="width: 30%;" />
							<col style="width: 20%;" />
							<col style="width: 30%;" />
						</colgroup>

						<tbody>
							<tr>
								<th scope="row"><s:message code="DMN.GRP.PHYC.NM" /></th><!--도메인그룹물리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dmngPnm}"/></td>
								<th scope="row"><s:message code="DMN.GRP.LGC.NM" /></th><!--도메인그룹논리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dmngLnm}"/></td>
							</tr>
							<tr>
								<th scope="row"><s:message code="INFO.TY.PHYC.NM"/></th><!--인포타입물리명-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.infotpLnm}"/></td>
								<th scope="row"><s:message code="DATA.TY" /></th><!--데이터타입-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dataType}"/></td>
							</tr>
							<tr>
								<th scope="row"><s:message code="DATA.LNGT" /></th><!--데이터길이-->
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dataLen}"/></td>
								<th scope="row"><s:message code="FPOINT" /></th><!-- 소수점 -->								
								<td><input type="text"  class="wd98p"  value="${metaDmnVO.dataScal}"/></td>
							</tr>
						</tbody>
					</table>
					</div>
				</form>
				</fieldset>
			</div>
			
			<div style="clear: both; height: 10px;"><span></span></div>
			<div class="stit"><s:message code="CD.DMN.VLD.VAL.DTL.INFO"/></div><!--코드도메인유효값 상세정보-->
			<div style="clear: both; height: 5px;"><span></span></div>
		
			<div id="grid_01" class="grid_01">
				<script type="text/javascript">createIBSheet("grid_code", "100%", "320px");</script>
			</div>
			
			<div style="clear: both; height: 5px;"><span></span></div>
			
		</div>
	</div>
</body>
</html>