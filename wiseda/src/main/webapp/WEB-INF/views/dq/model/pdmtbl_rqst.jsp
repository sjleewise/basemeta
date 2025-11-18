<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set>
<html>
<head>
<title><s:message code="PHYC.MDEL.REG"/></title> <!-- 물리모델 등록 -->
<script type="text/javascript">


//엔터키 이벤트 처리
// EnterkeyProcess("Search");

var interval = "";

$(document).ready(function() {
      // 조회 Event Bind
      $("#btnSearch").click(function(){ doAction("Search");  });
                    
      // 추가 Event Bind
      $("#btnNew").click(function(){ doAction("New");  });

      // 저장 Event Bind
      $("#btnSave").click(function(){
      	//var rows = tbl_sheet.FindStatusRow("I|U|D");
      	var rows = tbl_sheet.IsDataModified();
      	if(!rows) {
//       		alert("저장할 대상이 없습니다...");
      		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
      		return;
      	}
      	
      	//저장할래요? 확인창...
  		var message = "<s:message code="CNF.SAVE" />";
  		showMsgBox("CNF", message, 'Save');	
      	//doAction("Save"); 	
		}).show();

      // 삭제 Event Bind
      $("#btnDelete").click(function(){ 

      	//선택체크박스 확인 : 삭제할 대상이 없습니다..
  		if(checkDelIBS (tbl_sheet, "<s:message code="ERR.CHKDEL" />")) {
  			//삭제 확인 메시지
  			//alert("삭제하시겠어요?");
  			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//           	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
      	}
      //	doAction("Delete");  
      });
      
      // 엑셀내리기 Event Bind
      $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

      // 엑셀업로 Event Bind
       $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
     
      //======================================================
      // 셀렉트 박스 초기화
      //======================================================
      // 시스템영역
//       create_selectbox(usergJson, $("#usergId"));
      
       doAction("Search");
       
       $("#poiDown").hide();
  }
);

$(window).on('load',function() {
//	alert('window.load');
	initGrid();
	initGridCol();
	
});


$(window).resize(
);


function initGrid()
{
  
  with(tbl_sheet){
  	
  	var cfg = {SearchMode:2,Page:100};
      SetConfig(cfg);
      
      var headers = [
                  {Text:"<s:message code='DQ.HEADER.PDM.TBL.LST'/>", Align:"Center"}
                  /* No.|상태|선택|DBID|데이터베이스(물리명)|데이터베이스(논리명)|스키마ID|스키마(물리명)|스키마(논리명)|테이블ID|테이블(물리명)|테이블(논리명)
                  |만료일시|시작일시|담당자ID|담당자명|설명|버전|등록유형|요청일시|요청사용자ID */
              ];
      
      var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
      
      InitHeaders(headers, headerInfo); 
      

      var cols = [                        
                  {Type:"Seq",      Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                  {Type:"Status",   Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                  {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",     Align:"Center", Edit:1, Hidden:0, Sort:0},
                  {Type:"Combo",     Width:130,  SaveName:"dbConnTrgId",       Align:"Left", Edit:1, Hidden:0},
                  {Type:"Combo",     Width:130,  SaveName:"dbSchId",       Align:"Left", Edit:1, Hidden:0},
                  {Type:"Text",     Width:130,  SaveName:"pdmTblId",       Align:"Left", Edit:0, Hidden:1},
                  {Type:"Text",     Width:130,  SaveName:"pdmTblPnm",       Align:"Left", Edit:1, Hidden:0, KeyField:1},
                  {Type:"Text",     Width:130,  SaveName:"pdmTblLnm",       Align:"Left", Edit:1, Hidden:0, KeyField:1},
                  {Type:"Date",     Width:130,  SaveName:"expDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                  {Type:"Date",     Width:130,  SaveName:"strDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                  {Type:"Text",     Width:130,  SaveName:"crgUserId",       Align:"Left", Edit:1, KeyField:0, Hidden:1}, 
                  {Type:"Text",    Width:130,  SaveName:"crgUserNm",      Align:"Left", Edit:1, KeyField:0, Hidden:1},
                  {Type:"Text",     Width:230,  SaveName:"objDescn",      Align:"Left",   Edit:1, Hidden:0},
                  {Type:"Text",     Width:130,  SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
                  {Type:"Combo",    Width:80,   SaveName:"regTypCd",      Align:"Center",   Edit:0, Hidden:0},
                  {Type:"Text",     Width:130,  SaveName:"rqstDtm",      Align:"Center",   Edit:0, Hidden:1},
                  {Type:"Text",     Width:130,  SaveName:"rqstUserId",      Align:"Center",   Edit:0, Hidden:1}

                  
              ];
                  
      InitColumns(cols);
	     
      //콤보 목록 설정...
      SetColProperty("regTypCd", ${codeMap.regTypCdibs});
      SetColProperty("dbConnTrgId", ${codeMap.dbConnTrgibs});
      SetColProperty("dbSchId", ${codeMap.connTrgSchibs});
      

      InitComboNoMatchText(1, "");
    
      FitColWidth();  
      
      SetExtendLastCol(1);    
  }
  
  //==시트설정 후 아래에 와야함=== 
  init_sheet(tbl_sheet);    
  //===========================
}

function doAction(sAction)
{
      
  switch(sAction)
  {		    
      
  	case "New":        //추가
      	//첫행에 추가...
      	tbl_sheet.DataInsert(0);
                      
          break;
          
      case "Delete" :
      	//체크된 행 중에 입력상태인 경우 시트에서 제거...
      	delCheckIBS(tbl_sheet);
      	
      	var DelJson = tbl_sheet.GetSaveJson(0, "ibsCheck");
      	var url = '<c:url value="/dq/model/delpdmtblrqstlist.do"/>';
      	var param = "";
      	IBSpostJson2(url, DelJson, param, ibscallback);
      	break;
      	
      case "DeleteCol" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(col_sheet);
        	
        	var DelJson = col_sheet.GetSaveJson(0, "ibsCheck");
        	var url = '<c:url value="/dq/model/delpdmcolrqstlist.do"/>';
        	var param = "";
        	console.log(DelJson);
        	console.log(url);
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
      	
      case "Save" :
      	//TODO 공통으로 처리...
      	var SaveJson = tbl_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
      	//데이터 사이즈 확인...
      	if(SaveJson.data.length == 0) return;
      	
      	var url = "<c:url value="/dq/model/regpdmtblrqstlist.do"/>";
       	var param = "";
           IBSpostJson2(url, SaveJson, param, ibscallback);
      	break;
          
      case "Search":
      	var param = $('#frmSearch').serialize();
      	
      	tbl_sheet.DoSearch('<c:url value="/dq/model/getpdmtblrqstlist.do" />', param);
      	
      	break;
      	
      case "ColSearch":
   	  	var row = tbl_sheet.GetSelectRow();
   	  	 
       	var param = "pdmTblId=" + tbl_sheet.GetCellValue(row, 'pdmTblId');
       	col_sheet.DoSearch('<c:url value="/dq/model/getpdmcolrqstlist.do"/>', param);
       	
       	break;
     
      case "Down2Excel":  //엑셀내려받기
      
          tbl_sheet.Down2Excel({HiddenColumn:1, Merge:1});
          
          break;
      case "LoadExcel":  //엑셀업로
      
          tbl_sheet.LoadExcel({Mode:'HeaderMatch'});
          
          break;
  }       
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			
			break;
		case "regCol":
			doAction("ColSearch");
			
			break;
			
		case "delCol":
			doAction("ColSearch");
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}



/*
  row : 행의 index
  col : 컬럼의 index
  value : 해당 셀의 value
  x : x좌표
  y : y좌표
*/
function tbl_sheet_OnDblClick(row, col, value, cellx, celly) {
  
	if(row < 1) return;

}

function tbl_sheet_OnClick(row, col, value, cellx, celly) {
	var colName = tbl_sheet.ColSaveName(0,col);
	if(colName == "ibsCheck" || colName == "dbConnTrgId" || colName == "dbSchId")
		return;
	doAction("ColSearch");
}

function tbl_sheet_OnPopupClick(Row,Col) {
	var param = "row=" +Row;
	//사용자 검색 팝업 오픈
	if ("deptNm" == tbl_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/commons/user/popup/deptlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
}

function tbl_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}

}


function tbl_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		showMsgBox("INF", "<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		showMsgBox("INF", "<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}



</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">물리모델 관리</div> <!-- 사용자 등록 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
      <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
      <div style="clear:both; height:5px;"><span></span></div>
      
      <form id="frmSearch" name="frmSearch" method="post">
          <fieldset>
          <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
          <div class="tb_basic2">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="물리모델 조회">
                 <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                 <colgroup>
                 <col style="width:15%;" />
                 <col style="width:35%;" />
                 <col style="width:15%;" />
                 <col style="width:35%;" />
                </colgroup>
                 
                 <tbody>                          
                          <th scope="row"><label for="pdmTblPnm">테이블명</label></th> <!-- 사용자명 -->
                          <td>
                              <span class="input_file">
                              	<input type="text" name="pdmTblPnm" id="pdmTblPnm" />
                              </span>
                          </td>
                 </tbody>
               </table>   
          </div>
          </fieldset>
      <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
      <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
      </form>
		<div style="clear:both; height:10px;"><span></span></div>
 
      <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> 
</div>        

	<div style="clear:both; height:5px;"><span></span></div>
      
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("tbl_sheet", "100%", "180px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->
	
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">컬럼 목록</a></li> 
	  </ul>
	  <div id="tabs-1">
			<%@include file="pdmtbl_rqst_dtl.jsp" %>
	  </div>
	 </div>


</body>
</html>