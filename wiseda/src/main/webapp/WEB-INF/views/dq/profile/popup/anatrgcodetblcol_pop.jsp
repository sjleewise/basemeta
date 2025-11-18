<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		//탭 초기화....
		////$( "#tabs" ).tabs();
		//그리드 초기화 
		// 조회 Event Bind
		$("#btnSearch").click(function() {
			doAction("SearchAnaTrgTbl");
		});
		// 엑셀내리기 Event Bind
		$("#btnExcelDown").click(function() {
			doAction("Down2Excel");
		}).hide();
		//조회버튼 hidden
		$("#btnSave").hide();
		//삭제버튼 hidden
		$("#btnDelete").hide();
		//tree 추가 버튼 hidden
		$("#btnTreeNew").hide();

		//선택버턴
		$("#btnReturn").click(function(event) {
			event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("CdColReturn");
		}).show();

		$("form[name=frmSearch] #schDbConnTrgLdm").css("border-color","transparent").css("width", "99%");
		$("form[name=frmInput] #cdTblDbcColNm").css("border-color","transparent").css("width", "99%");
		$("form[name=frmInput] #cdTblCdIdColNm").css("border-color","transparent").css("width", "99%");
		
		//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
	    $("div.pop_tit_close").click(function(){
	    	//iframe 형태의 팝업일 경우
	    	if ("${anaTrgTblVO.popType}" == "I") {
	    		parent.closeLayerPop();
	    	} else {
	    		window.close();
	    	}
	    });
	});

	//엔터키 처리한다.
	EnterkeyProcess("SearchAnaTrgTbl");
	
	$(window).on('load',function() {
		//그리드 초기화 
		initGrid();
		//그리드 사이즈 조절 초기화...		
		bindibsresize();

		//opener 이용 조회 조건 setting
	});

	$(window).resize(function() {
		//그리드 가로 스크롤 방지
		grid_tbl.FitColWidth();
		
		grid_col.FitColWidth();
		
	});

	function initGrid() {

		with (grid_tbl) {
			var cfg = {
				SearchMode : 2,
				Page : 100
			};
			SetConfig(cfg);

			var headers = [ {
				Text : "<s:message code='DQ.HEADER.ANATRGCODETBLCOL_POP'/>",
				Align : "Center"
			} ];
			//No|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명


			var headerInfo = {
				Sort : 1,
				ColMove : 0,
				ColResize : 1,
				HeaderCheck : 1
			};

			InitHeaders(headers, headerInfo);

			var cols = [ {
				Type : "Seq",
				Width : 50,
				SaveName : "ibsSeq",
				Align : "Center",
				Edit : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "subjId",
				Align : "Left",
				Edit : 0,
				Hidden : 1
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "subjLnm",
				Align : "Left",
				Edit : 0,
				Hidden : 1
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbConnTrgId",
				Align : "Left",
				Edit : 0,
				Hidden : 1
			}, {
				Type : "Text",
				Width : 80,
				SaveName : "dbConnTrgPnm",
				Align : "Left",
				Edit : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbSchId",
				Align : "Left",
				Edit : 0,
				Hidden : 1
			}, {
				Type : "Text",
				Width : 80,
				SaveName : "dbSchPnm",
				Align : "Left",
				Edit : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbcTblNm",
				Align : "Left",
				Edit : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbcTblKorNm",
				Align : "Left",
				Edit : 0
			}, ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			//콤보 목록 설정...
			// 	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});

			InitComboNoMatchText(1, "");

			FitColWidth();
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다    

			SetExtendLastCol(1);
		}

		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_tbl);
		//===========================

		//진단대상 컬럼 grid
		with (grid_col) {

			var cfg = {
				SearchMode : 2,
				Page : 100
			};
			SetConfig(cfg);

			var headers = [ {
				Text : "<s:message code='DQ.HEADER.ANATRGCODETBLCOL_POP2'/>",
				Align : "Center"
			} ];
			//Position|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명|Pk여부|Data Type|Null여부|Default


			 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
	           InitHeaders(headers, headerInfo); 

	           var cols = [                        
	                   {Type:"Text",   Width:70,  SaveName:"ord",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
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

			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			//        SetExtendLastCol(1); 

			FitColWidth();

		}

		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_col);
		//===========================

	}

	function doAction(sAction, param) {

		switch (sAction) {
		/*진단대상 테이블 조회*/
		case "SearchAnaTrgTbl":
			param = "";
			param = $('form[name=frmSearch]').serialize();
			grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLstNotRedline.do" />", param);
			break;

		/*진단대상 컬럼 조회*/
		case "SearchAnaTrgCol":
			grid_col.DoSearch("<c:url value="/dq/profile/getPrfColLstNotRedline.do" />", param);
			break;

		case "CdColReturn":
			if ($("form[name=frmInput] #cdTblDbcColNm").val() == "") {
				var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='MSG.CODE.VAL.NM.INPUT'/>" />";
				showMsgBox("ERR", message); 
				return;
			}
			//iframe 형태의 팝업일 경우
			if ("${anaTrgTblVO.popType}" == "I") {
				//코드테이블 상세정보 reset
				parent.frmInputPC02_cdtbl.reset();
				parent.frmInputPC02_cdtbl.cdTblDbConnTrgLdm.value = $("form[name=frmInput] #cdTblDbConnTrgLdm").val();
				parent.frmInputPC02_cdtbl.cdTblDbConnTrgId.value = $("form[name=frmInput] #cdTblDbConnTrgId").val();
				parent.frmInputPC02_cdtbl.cdTblDbSchLdm.value = $("form[name=frmInput] #cdTblDbSchLdm").val();
				parent.frmInputPC02_cdtbl.cdTblDbSchId.value = $("form[name=frmInput] #cdTblDbSchId").val();
				parent.frmInputPC02_cdtbl.cdTblDbcTblNm.value = $("form[name=frmInput] #cdTblDbcTblNm").val();
				parent.frmInputPC02_cdtbl.cdTblDbcTblKorNm.value = $("form[name=frmInput] #cdTblDbcTblKorNm").val();
				parent.frmInputPC02_cdtbl.cdTblDbcColNm.value = $("form[name=frmInput] #cdTblDbcColNm").val();
				parent.frmInputPC02_cdtbl.cdTblDbcColKorNm.value =$("form[name=frmInput] #cdTblDbcColKorNm").val();
				parent.frmInputPC02_cdtbl.cdTblCdIdColNm.value = $("form[name=frmInput] #cdTblCdIdColNm").val();
				parent.frmInputPC02_cdtbl.cdTblCdIdColKorNm.value = $("form[name=frmInput] #cdTblCdIdColKorNm").val();
			} else {
// 				opener.
			}
			$('div.pop_tit_close').click();
			
			break;

		}
	}

	function grid_tbl_OnDblClick(row, col, value, cellx, celly) {
		if (row < 1)
			return;
	}

	function grid_tbl_OnClick(row, col, value, cellx, celly) {
		if (row < 1)
			return;
		//그리드 선택 데이터 변수 setting
		var param = grid_tbl.GetRowJson(row);
		//caption 
		var tmphtml = ' <s:message code="TBL.NM" />' +  ' : ' + param.dbcTblNm; /*<!--테이블명-->*/
		$('#bizarea_sel_title').html(tmphtml);

		//그리드 선택 데이터 변수 setting
		var param = "&schDbConnTrgId="+ grid_tbl.GetCellValue(row, "dbConnTrgId");
		param += "&schDbSchId=" + grid_tbl.GetCellValue(row, "dbSchId");
		param += "&schDbcTblNm=" + grid_tbl.GetCellValue(row, "dbcTblNm");
		param += "&schDbcColNm="	+ $("form[name=frmSearch] input[name='schDbcColNm']").val();

		//컬럼조회
		doAction("SearchAnaTrgCol", param);
	}

	function grid_tbl_OnSearchEnd(code, message, stCode, stMsg) {
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

	function grid_col_OnClick(row, col, value, cellx, celly) {
		if (row < 1)
			return;
		//그리드 선택 데이터 변수 setting
		var param = grid_col.GetRowJson(row);

		var radio = $("form[name=frmInput] input:radio:checked").val();

		if (radio == "VAL") {
			$("form[name=frmInput] #cdTblDbcColNm").val(param.dbcColNm);
			$("form[name=frmInput] #cdTblDbcColKorNm").val(param.dbcColKorNm);
		} else if (radio == "CD") {
			$("form[name=frmInput] #cdTblCdIdColNm").val(param.dbcColNm);
			$("form[name=frmInput] #cdTblCdIdColKorNm").val(param.dbcColKorNm);
		}
		
		$("form[name=frmInput] #cdTblDbConnTrgLdm").val(param.dbConnTrgPnm);
		$("form[name=frmInput] #cdTblDbConnTrgId").val(param.dbConnTrgId);
		$("form[name=frmInput] #cdTblDbSchLdm").val(param.dbSchPnm);
		$("form[name=frmInput] #cdTblDbSchId").val(param.dbSchId);
		$("form[name=frmInput] #cdTblDbcTblNm").val(param.dbcTblNm);
		$("form[name=frmInput] #cdTblDbcTblKorNm").val(param.dbcTblKorNm);
	}
</script>
</head>

<body>
	<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt"><s:message code="CD.TBL.INQ.1"/></div><!--코드테이블 조회-->

		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
		<!-- 팝업 타이틀 끝 -->

		<!-- 팝업 내용 시작 -->
		<div class="pop_content">
			<!-- 검색조건 입력폼 -->
			<div id="search_div">
				<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
				<div style="clear: both; height: 5px;">
					<span></span>
				</div>

				<form id="frmSearch" name="frmSearch" method="post">
					<fieldset>
						<legend><s:message code="FOREWORD" /></legend><!--머리말-->
						<div class="tb_basic">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								summary="<s:message code='CD.TBL.INQ.2'/>"><!-- /*코드테이블조회*/
 -->
								<caption></caption>
								<colgroup>
									<col style="width: 25%;" />
									<col style="width: 25%;" />
									<col style="width: 25%;" />
									<col style="width: 25%;" />
								</colgroup>

								<tbody>
									<tr>
										<th scope="row"><label for="schDbConnTrgLdm"><s:message code="CD.TBL.DIAG.TRGT.NM"/></label></th><!--코드테이블진단대상명-->

										<td>${anaTrgTblVO.dbConnTrgPnm } <input type="hidden" name="schDbConnTrgId" id="schDbConnTrgId" 	value="${anaTrgTblVO.dbConnTrgId }" /></td>
										<th scope="row"><label for="schDbSchNm"><s:message code="CD.TBL.SCHEMA.NM"/></label></th><!--코드테이블스키마명-->

										<td><input type="text" name="schDbSchNm" id="schDbSchNm"	value="${anaTrgTblVO.dbSchPnm }" /></td>
									</tr>
									<tr>
										<th scope="row"><label for="schDbcTblNm"><s:message code="CD.TBL.NM"/></label></th><!--코드테이블명-->
										<td><input type="text" name="schDbcTblNm"
											id="schDbcTblNm"  /></td>
										<th scope="row"><label for="schDbcColNm"><s:message code="CD.VAL.CLMN.NM"/></label></th><!--코드값컬럼명-->

										<td><input type="text" name="schDbcColNm"
											id="schDbcColNm" /> <%-- value="${anaTrgTblVO.dbcColNm }" --%>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</fieldset>

					<!--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div>  -->
				</form>
				<div style="clear: both; height: 10px;">
					<span></span>
				</div>

				<!-- 조회버튼영역  -->
				<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
			</div>
			<div style="clear: both; height: 5px;">
				<span></span>
			</div>

			<!-- 그리드 입력 입력 -->
			<div id="grid_01" class="grid_01">
				<script type="text/javascript">
					createIBSheet("grid_tbl", "100%", "180px");
				</script>
			</div>
			<div style="clear: both; height: 5px;">
				<span></span>
			</div>
			<div class="selected_title_area" style="display:;">
				<div class="selected_title" id="bizarea_sel_title">
					<s:message code="TBL.NM" /> : <span></span><!--테이블명-->

				</div>
			</div>
			<div style="clear: both; height: 5px;">
				<span></span>
			</div>
			<div id="grid_02" class="grid_01">
				<script type="text/javascript">
					createIBSheet("grid_col", "100%", "250px");
				</script>
			</div>

			<div id="div_cddtl" name="div_cddtl">
				<div style="clear: both; height: 10px;">
					<span></span>
				</div>
				<div class="stit"><s:message code="CD.CLMN.DTL.INFO"/></div><!--코드컬럼 상세정보-->

				<div style="clear: both; height: 5px;">
					<span></span>
				</div>

				<!-- 선택 버튼 추가 -->
				<button class="btn_save" id="btnReturn" name="btnReturn"><s:message code="CD.CLMN.RFLC"/></button><!--코드컬럼반영-->

				<div style="clear: both; height: 5px;">
					<span></span>
				</div>
				
				
				<form id="frmInput" name="frmInput" method="post">
					<input type="hidden" name="cdTblDbConnTrgLdm" id="cdTblDbConnTrgLdm"  />
					<input type="hidden" name="cdTblDbConnTrgId" id="cdTblDbConnTrgId"  />
					<input type="hidden" name="cdTblDbSchLdm" id="cdTblDbSchLdm"  />
					<input type="hidden" name="cdTblDbSchId" id="cdTblDbSchId"  />
					<input type="hidden" name="cdTblDbcTblNm" id="cdTblDbcTblNm"  />
					<input type="hidden" name="cdTblDbcTblKorNm" id="cdTblDbcTblKorNm"  />
					<input type="hidden" name="cdTblDbcColKorNm" id="cdTblDbcColKorNm"  />
					<input type="hidden" name="cdTblCdIdColKorNm" id="cdTblCdIdColKorNm"  />
				
					<fieldset>
						<legend><s:message code="FOREWORD" /></legend><!--머리말-->
						<div class="tb_basic">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								summary="<s:message code='CD.CLMN.DTL.INFO'/>"><!-- /*코드컬럼 상세정보*/ -->
								<caption><s:message code="CD.CLMN.DTL.INFO"/></caption><!-- /*코드컬럼 상세정보*/-->
								<colgroup>
									<col style="width: 30%;" />
									<col style="width: 70%;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row"><input type="radio" name="rdoSetMode"	id="rdoSetMode" value="VAL" title="<s:message code='CD.VAL.CLMN.NM'/>"	style="width: 20px; background: #F6FAEF url(../images/th_require.gif) no-repeat 100% 0%; border: #ffffff 0 solid;" 	checked /> <s:message code="CD.VAL.CLMN.NM"/></th> <!--코드값컬럼명-->

										<td><input type="text" name="cdTblDbcColNm"	id="cdTblDbcColNm" class="readonly" readonly /></td>
									</tr>
									<tr>
										<th scope="row"><input type="radio" name="rdoSetMode"
											id="rdoSetMode" value="CD" title="<s:message code='CD.ID.CLMN.NM'/>" 
											style="width: 20px; background: #F6FAEF url(../images/th_require.gif) no-repeat 100% 0%; border: #ffffff 0 solid;" /><!-- /*코드ID컬럼명*/ -->
											<s:message code="CD.ID.CLMN.NM"/></th><!--코드ID컬럼명-->

										<td><input type="text" name="cdTblCdIdColNm" 	id="cdTblCdIdColNm" class="readonly" readonly /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</fieldset>
				</form>
			</div>

			<!-- 그리드 입력 입력 -->
			<div style="clear: both; height: 5px;">
				<span></span>
			</div>
		</div>
	</div>
	<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>