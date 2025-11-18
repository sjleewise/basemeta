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
		initGrid();
		
		//그리드 사이즈 조절 초기화...		
		bindibsresize();
		
		// 조회 Event Bind
		$("#btnSearch").click(function() {
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("SearchAnaTrgCol");
		});
		// 조회 Event Bind
		$("#btnReturn").click(function() {
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("ReturnCol");
		}).show();
		
		//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
	    $("div.pop_tit_close").click(function(){
	    	//iframe 형태의 팝업일 경우
	    	if ("${searchVO.popType}" == "I") {
	    		parent.closeLayerPop();
	    	} else {
	    		window.close();
	    	}
	    });
		
	  //조회조건 readonly
		if("${searchVO.dbConnTrgPnm }" != ""){
			$("form[name=frmSearch] #schDbConnTrgLdm").css("border-color","transparent").attr("readonly",true);
		}
		if("${searchVO.dbSchPnm }" != ""){
			$("form[name=frmSearch] #schDbSchNm").css("border-color","transparent").attr("readonly",true);
		}
		if("${searchVO.dbcTblNm }" != ""){
			$("form[name=frmSearch] #schDbcTblNm").css("border-color","transparent").attr("readonly",true);
		}
		
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
		setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
	});
	

	//엔터키 처리한다.
	EnterkeyProcess("SearchAnaTrgCol");

	$(window).on('load',function() {
	});

	$(window).resize(function() {
		//그리드 가로 스크롤 방지
		grid_col.FitColWidth();
	});

	function initGrid() {

		with (grid_col) {
			var cfg = {SearchMode : 2,Page : 100	};
			SetConfig(cfg);

			var headers = [ {Text : "<s:message code='DQ.HEADER.ANATRGCOL.POP'/>", Align : "Center"	} ];
//선택|No|상태|Position|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명|컬럼명|컬럼한글명|Pk여부|Data Type|Null여부|Default

			 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
	           InitHeaders(headers, headerInfo); 

	           var cols = [
	                   {Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
	   				   {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:0},
	   	               {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:70,  SaveName:"ord",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
	                   {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:70,  SaveName:"pkYn",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:80,  SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:0},
	                   {Type:"Text",   Width:80,  SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:0}
	               ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			InitComboNoMatchText(1, "");

			FitColWidth();
			
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다    
			SetExtendLastCol(1);
		}

		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_col);
		//===========================
			
	}

	function doAction(sAction, param) {

		switch (sAction) {
		
			/*진단대상 테이블 조회*/
	        case "SearchAnaTrgCol":
	        	param = "";
	        	param = $('form[name=frmSearch]').serialize();
	        	grid_col.DoSearch("<c:url value="/dq/criinfo/anatrg/getAnaTrgColLst.do" />", param);
	        	break;
	        	
	        case "ReturnCol":
	        	var retjson = grid_col.GetSaveJson(0);

	        	//iframe 형태의 팝업일 경우
	    		if ("${searchVO.popType}" == "I") {
	    			parent.returnAnaColPop(retjson);
// 	    			parent.returnAnaColPop(JSON.stringify(retjson));
	    		} else {
	    			opener.returnAnaColPop(JSON.stringify(retjson));
	    		}
	    		
	        	//중복컬럼 셋팅
	    		//ibsSheet2SheetStatus(grid_col ,   parent.grid_unq_col_sheet, "ibsStatus"); 
	    		
	    		//팝업창 닫기 버튼 클릭....
	    		$(".pop_tit_close").click();
	    		
	        	break;
		}
	}

	function grid_col_OnDblClick(row, col, value, cellx, celly) {
		if (row < 1)	return;
		
		var retjson = grid_col.GetRowJson(row);
		
		//iframe 형태의 팝업일 경우
		if ("${searchVO.popType}" == "I") {
			parent.returnAnatblPop(JSON.stringify(retjson));
		} else {
			opener.returnAnatblPop(JSON.stringify(retjson));
		}
		//팝업창 닫기 버튼 클릭....
		$(".pop_tit_close").click();
		
	}

	
	//조회 에러
	function grid_col_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}
	}
	
</script>
</head>

<body>
	<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt"><s:message code="CLMN.INQ.1"/></div><!--컬럼 조회-->
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
					<!-- 자식테이블정보 -->
					<div class="stit"><s:message code="CLMN"/> <s:message code="INQ.COND2" /></div><!--검색조건--><!--컬럼-->
					<div style="clear: both; height: 5px;"><span></span></div>
					<div class="tb_basic2">
					<table width="99%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='CLMN.INQ.2'/>">
						<caption></caption>
						<colgroup>
							<col style="width: 30%;" />
							<col style="width: 70%;" />
						</colgroup>

						<tbody>
							<tr>
								<th scope="row"><label for="schDbConnTrgLdm"><s:message code="TBL.DIAG.TRGT.NM"/></label></th><!--테이블진단대상명-->

								<td>
									<input type="text" name="schDbConnTrgLdm"	id="schDbConnTrgLdm" class="wd95p" value="${searchVO.dbConnTrgPnm }"	/>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="schDbSchNm"><s:message code="TBL.NM.KRN.NM"/></label></th><!--테이블명(한글명)-->

								<td><input type="text" name="schDbSchNm" id="schDbSchNm"	class="wd95p" value="${searchVO.dbSchPnm }"  /></td>
							</tr>
							<tr>
								<th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM.KRN.NM"/></label></th><!--테이블명(한글명)-->

								<td><input type="text" name="schDbcTblNm" 	id="schDbcTblNm" class="wd95p" value="${searchVO.dbcTblNm }" /></td>
							</tr>
							<tr>
								<th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM.KRN.NM"/></label></th><!--컬럼명(한글명)-->
								<td><input type="text" name="schDbcColNm" 	id="schDbcColNm"  class="wd95p" value="${searchVO.dbcColNm }"  /></td>
							</tr>
						</tbody>
					</table>
					</div>
				</form>
				</fieldset>
			</div>
			
			<!-- 조회버튼영역  -->
			<div style="clear: both; height: 10px;"><span></span></div>
		    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button><!--조회-->
 
		    <!-- 반영 버튼 추가 -->
			<button class="btn_save" id="btnReturn" name="btnReturn"><s:message code="RFLC"/></button><!--반영-->
			<div style="clear: both; height: 5px;"><span></span></div>
		
			<div id="grid_01" class="grid_01">
				<script type="text/javascript">createIBSheet("grid_col", "100%", "370px");</script>
			</div>
			
			<div style="clear: both; height: 5px;"><span></span></div>
			
		</div>
	</div>
</body>
</html>