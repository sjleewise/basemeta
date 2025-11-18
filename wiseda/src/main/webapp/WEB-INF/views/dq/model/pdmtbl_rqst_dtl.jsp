<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<script type="text/javascript">


//엔터키 이벤트 처리
// EnterkeyProcess("Search");

var interval = "";

$(document).ready(function() {
      // 조회 Event Bind
      $("#btnSubSearch").click(function(){ doActionCol("Search");  });
                    
      // 추가 Event Bind
      $("#btnSubNew").click(function(){ doActionCol("New");  });

      // 저장 Event Bind
      $("#btnSubSave").click(function(){
      	//var rows = col_sheet.FindStatusRow("I|U|D");
      	var rows = col_sheet.IsDataModified();
      	if(!rows) {
//       		alert("저장할 대상이 없습니다...");
      		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
      		return;
      	}
      	
      	//저장할래요? 확인창...
  		var message = "<s:message code="CNF.SAVE" />";
  		showMsgBox("CNF", message, doActionCol('SaveCol'));	
		}).show();

      // 삭제 Event Bind
      $("#btnSubDelete").click(function(){ 

      	//선택체크박스 확인 : 삭제할 대상이 없습니다..
  		if(checkDelIBS (col_sheet, "<s:message code="ERR.CHKDEL" />")) {
  			//삭제 확인 메시지
  			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteCol');
      	}
      });
      
      // 엑셀내리기 Event Bind
      $("#btnSubExcelLoad").click( function(){ doActionCol("LoadExcel"); } );

      // 엑셀업로 Event Bind
       $("#btnSubExcelDown").click( function(){ doActionCol("Down2Excel"); } ); 
     
  }
);

$(window).on('load',function() {
//	alert('window.load');
	initGridCol();
});


$(window).resize(
);


function initGridCol()
{
  
  with(col_sheet){
  	
  	var cfg = {SearchMode:2,Page:100};
      SetConfig(cfg);
      
      var headers = [
                  {Text:"<s:message code='DQ.HEADER.PDM.COL.LST'/>", Align:"Center"}
                  /* No.|상태|선택|DBID|데이터베이스(물리명)|데이터베이스(논리명)|스키마ID|스키마(물리명)|스키마(논리명)|테이블ID|테이블(물리명)|테이블(논리명)
                  |만료일시|시작일시|담당자ID|담당자명|설명|버전|등록유형|요청일시|요청사용자ID */
              ];
      
      var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
      
      InitHeaders(headers, headerInfo); 
      

      var cols = [                        
                  {Type:"Seq",      Width:60,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                  {Type:"Status",   Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
                  {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                  {Type:"Combo",    Width:130,  SaveName:"dbConnTrgId",	Align:"Left", Edit:0, Hidden:0},
                  {Type:"Combo",    Width:130,  SaveName:"dbSchId",     Align:"Left", Edit:0, Hidden:0},
                  {Type:"Text",     Width:130,  SaveName:"pdmTblId",    Align:"Left", Edit:1, Hidden:1},
                  {Type:"Text",     Width:130,  SaveName:"pdmTblPnm",   Align:"Left", Edit:0, Hidden:0, KeyField:1},
                  {Type:"Text",     Width:130,  SaveName:"pdmTblLnm",   Align:"Left", Edit:0, Hidden:0, KeyField:1},
                  {Type:"Text",    Width:100, SaveName:"pdmColId",		Align:"Left",   Edit:1, Hidden:1},
                  {Type:"Text",    Width:150, SaveName:"pdmColSno",		Align:"Left",   Edit:1, Hidden:1},
                  {Type:"Text",    Width:150, SaveName:"pdmColPnm",		Align:"Left",   Edit:1},
                  {Type:"Text",    Width:150, SaveName:"pdmColLnm",		Align:"Left",   Edit:1},
                  {Type:"Text",    Width:50,  SaveName:"colOrd",		Align:"Center", Edit:1},
                  {Type:"Text",    Width:100, SaveName:"dataType",		Align:"Center", Edit:1},
                  {Type:"Text",    Width:50,  SaveName:"dataLen",		Align:"Center", Edit:1},
                  {Type:"Text",    Width:50,  SaveName:"dataScal",		Align:"Center", Edit:1},
                  {Type:"Combo",   Width:50,  SaveName:"pkYn",			Align:"Center", Edit:1},
                  {Type:"Text",    Width:50,  SaveName:"pkOrd",			Align:"Center", Edit:1},
                  {Type:"Combo",   Width:100, SaveName:"nonulYn",		Align:"Center", Edit:1},
                  {Type:"Text",    Width:100, SaveName:"defltVal",		Align:"Center", Edit:1},
                  {Type:"Date",     Width:130,  SaveName:"expDtm",      Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                  {Type:"Date",     Width:130,  SaveName:"strDtm",      Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                  {Type:"Text",     Width:130,  SaveName:"crgUserId",   Align:"Left", Edit:1, KeyField:0, Hidden:1}, 
                  {Type:"Text",     Width:130,  SaveName:"crgUserNm",   Align:"Left", Edit:1, KeyField:0, Hidden:1},
                  {Type:"Text",     Width:230,  SaveName:"objDescn",    Align:"Left",   Edit:1, Hidden:0},
                  {Type:"Text",     Width:130,  SaveName:"objVers",     Align:"Right",   Edit:0, Hidden:1},
                  {Type:"Combo",    Width:80,   SaveName:"regTypCd",    Align:"Center",   Edit:0, Hidden:0},
                  {Type:"Text",     Width:130,  SaveName:"rqstDtm",     Align:"Center",   Edit:0, Hidden:1},
                  {Type:"Text",     Width:130,  SaveName:"rqstUserId",  Align:"Center",   Edit:0, Hidden:1}

                  
              ];
                  
      InitColumns(cols);
	     
      //콤보 목록 설정...
      SetColProperty("regTypCd", ${codeMap.regTypCdibs});
      SetColProperty("dbConnTrgId", ${codeMap.dbConnTrgibs});
      SetColProperty("dbSchId", ${codeMap.connTrgSchibs});

	  SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	  SetColProperty("nonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
      

      InitComboNoMatchText(1, "");
    
      FitColWidth();  
      
      SetExtendLastCol(1);    
  }
  
  //==시트설정 후 아래에 와야함=== 
  init_sheet(col_sheet);    
  //===========================
}

function doActionCol(sAction)
{
      
  switch(sAction)
  {		    
      
  	case "New":        //추가
      	//첫행에 추가...
      	var row = col_sheet.DataInsert(0);
  		var tbl = tbl_sheet.GetSelectRow();
  		col_sheet.SetCellValue(row, "dbConnTrgId", tbl_sheet.GetCellValue(tbl,"dbConnTrgId"));
  		col_sheet.SetCellValue(row, "dbSchId", tbl_sheet.GetCellValue(tbl,"dbSchId"));
  		col_sheet.SetCellValue(row, "pdmTblPnm", tbl_sheet.GetCellValue(tbl,"pdmTblPnm"));
  		col_sheet.SetCellValue(row, "pdmTblLnm", tbl_sheet.GetCellValue(tbl,"pdmTblLnm"));
          break;
          
//       case "DeleteCol" :
//       	//체크된 행 중에 입력상태인 경우 시트에서 제거...
//       	delCheckIBS(col_sheet);
      	
//       	var DelJson = col_sheet.GetSaveJson(0, "ibsCheck");
//       	var url = '<c:url value="/dq/model/delpdmcolrqstlist.do"/>';
//       	var param = "";
//       	console.log(DelJson);
//       	console.log(url);
//       	IBSpostJson2(url, DelJson, param, ibscallback);
//       	break;
      	
      case "SaveCol" :
      	//TODO 공통으로 처리...
      	var SaveJson = col_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
      	//데이터 사이즈 확인...
      	if(SaveJson.data.length == 0) return;
      	
      	var url = "<c:url value="/dq/model/regpdmcolrqstlist.do"/>";
       	var param = "";
        IBSpostJson2(url, SaveJson, param, ibscallback);
      	break;
          
      case "Search":
//       	var param = $('#frmSearch').serialize();
      	var param = "pdmTblId=" + tbl_sheet.GetCellValue(tbl_sheet.GetSelectRow(), 'pdmTblId');
      	col_sheet.DoSearch('<c:url value="/dq/model/getpdmcolrqstlist.do" />', param);
      	
      	break;
     
      case "Down2Excel":  //엑셀내려받기
      
          col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
          
          break;
      case "LoadExcel":  //엑셀업로
      
          col_sheet.LoadExcel({Mode:'HeaderMatch'});
          
          break;
  }       
}

function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}

}


function col_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		showMsgBox("INF", "<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		showMsgBox("INF", "<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}



</script>

<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
 
      <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> 
</div>        

	<div style="clear:both; height:5px;"><span></span></div>
      
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_02">
	     <script type="text/javascript">createIBSheet("col_sheet", "100%", "220px");</script>            

	</div>

