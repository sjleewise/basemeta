<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title>모델관리</title> 

<script type="text/javascript">
var connTrgSchJson = ${codeMap.connTrgSchId} ;
var allDbms = ${codeMap.allDbms};
var allDbmsSchId = ${codeMap.allDbmsSchId};
$(document).ready(function() {
	
	
	for(i=0; i<allDbms.length; i++) {
		$("#allDbms").append('<option value="' + allDbms[i].codeCd + '">' + allDbms[i].codeLnm + '</option>');
	}

	$("#allDbms").change(function() {
		$("#allDbmsSchId").find("option").remove().end();
		var val = $("#allDbms option:selected").val();

		$("#allDbmsSchId").append('<option value=""><s:message code="CHC" /></option> ');
		
		for(i=0; i<allDbmsSchId.length; i++) {
			if(allDbmsSchId[i].upcodeCd == val && val!="") {
				$("#allDbmsSchId").append('<option value="' + allDbmsSchId[i].codeCd + '">' + allDbmsSchId[i].codeLnm + '</option>');
			}
		}
	});	

	// 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  }).show();
        
    // 추가 Event Bind
    $("#btnNew").click(function(){ doAction("New");  }).hide();
     	
    // 저장 Event Bind
    $("#btnSave").click(function(){
    //var rows = grid_sheet.FindStatusRow("I|U|D");
    var rows = grid_sheet.IsDataModified();
    if(!rows) {
//    		alert("저장할 대상이 없습니다...");
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
   		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
   			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
       	}
   	});
               
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
      
    // 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 

    
  	//======================================================
    // 셀렉트 박스 초기화
    //======================================================
 	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
 	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
 		double_select(connTrgSchJson, $(this));
 	});
    
    
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #colLnm"), "GOVTBL");
	
	 $("#batchDelete").click( function(){ 
     	doAction("BatchDelete");  
     }).show();
	
}); 

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	initGrid();

});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
		var headers = [
						{Text:"NO.|상태|선택|검증결과|DBMS논리명|스키마논리명|테이블아이디|테이블물리명|테이블논리명|컬럼아이디|컬럼물리명|컬럼논리명|데이터타입|컬럼순서|주제영역풀패스|데이터길이|소수점|NOTNULL여부|PK여부|PK순서|FK여부|출처|작성자아이디|작성자|작성일시"}
						]
					;
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0, Hidden:0},
						{Type:"Status",   Width:50,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},//상태
						{Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},//선택
						{Type:"Text",   Width:150,  SaveName: "vrfRmk",		Align:"Center", Edit:0, Hidden:0},
// 						{Type:"Text",   Width:150,  SaveName: "sysAreaPnm",		Align:"left", Edit:0, Hidden:0, KeyField:1},
						{Type:"Combo",   Width:150,  SaveName: "dbConnTrgId",		Align:"left", Edit:1, Hidden:0, KeyField:1},
						{Type:"Combo",   Width:150,  SaveName: "dbSchId",		Align:"left", Edit:1, Hidden:0, KeyField:1},
						{Type:"Text",   Width:150,  SaveName: "tblId",		Align:"left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName: "tblPnm",		Align:"left", Edit:0, Hidden:0, KeyField:1},
						{Type:"Text",   Width:150,  SaveName: "tblLnm",		Align:"left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName: "colId",		Align:"left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName: "colPnm",		Align:"left", Edit:0, Hidden:0, KeyField:1},
						{Type:"Text",   Width:150,  SaveName: "colLnm",		Align:"left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,  SaveName: "dataType",		Align:"left", Edit:0, Hidden:0, KeyField:1},
						{Type:"Text",   Width:100,  SaveName: "colOrd",		Align:"Center", Edit:0, Hidden:1, KeyField:0},
						{Type:"Text",   Width:100,  SaveName: "subjAllPath",		Align:"right", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName: "dataLen",		Align:"right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName: "scal",		Align:"right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName: "nullYn",		Align:"Center", Edit:0, Hidden:0, KeyField:1},
						{Type:"Text",   Width:100,  SaveName: "pkYn",		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName: "pkOrd",		Align:"right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName: "fkYn",		Align:"right", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName: "dicOrgn",		Align:"right", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:100,  SaveName:"writUserId",			Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"writUserNm",			Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,  SaveName:"writDtm",		Align:"right", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						
						
					];
                    
        InitColumns(cols);
        
        //매핑구분, 이행구분 콤보코드로 작성 
        //SetColHidden("rqstUserNm",1);
         SetColProperty("dbConnTrgId", ${codeMap.ConnTrgDbmsibs2});
//         SetColProperty("dbSchId", ${codeMap.dbSchLnmOnlyibs2});
         SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
         SetColProperty("fkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
         SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
         InitComboNoMatchText(1, "");
//         FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function doAction(sAction)
{
	switch(sAction)
	{
	
	case "New":        //추가
    	//첫행에 추가...
    	grid_sheet.DataInsert(0);
        break;
        
	case "Search":
		
		if($('#dbConnTrgId').val() == '' || $('#dbSchId').val() == '') {
			showMsgBox("ERR", "DBMS/스키마명을 선택 해 주세요.");
     		return;
		}
		
		var param = $('#frmSearch').serialize();
    	grid_sheet.DoSearch("<c:url value="/dq/dqrs/getDqrsTblLst.do" />", param);
		break;
  		
      case "Save" : //엑셀 업로드용 저장
      	//TODO 공통으로 처리...
       	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

      	//데이터 사이즈 확인...
      	if(SaveJson.data.length == 0) return;
      	
      	var url = '<c:url value="/dq/dqrs/saveDqrsTbl.do"/>';
      	
       	var param = "";
       	
          IBSpostJson2(url, SaveJson, param, ibscallback);
          
      	break;	
      	
      case "Delete" :
	    	
      	var url = '<c:url value="/dq/dqrs/delDqrsTbl.do"/>';
      	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	        delCheckIBS(grid_sheet);
       	
      	var param = "";
      	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
      	if(DelJson.data.length == 0) {return;}
    		
      	IBSpostJson2(url, DelJson, param, ibscallback);
      
      	break;
      	
	case "Down2Excel": //엑셀내려받기
		var downData = 'ibsSeq|dbConnTrgId|dbSchId|tblPnm|tblLnm|colPnm|colLnm|dataType|colOrd|objDescn|precision|dataLen|scal|nullYn|pkYn|pkOrd|';
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1,
			DownCols:downData , FileName:'테이블매핑정의서조회'});
		break;
		
	 case "LoadExcel":  //엑셀업로드
         grid_sheet.LoadExcel({Mode:'HeaderMatch'});
     
         break;
         
	 case "BatchDelete"	:
     	
     	var dbId  = $("#allDbms option:selected").val();
     	var schId = $("#allDbmsSchId option:selected").val();
     	if(dbId=='' || schId ==''){
     		showMsgBox("ERR", "DBMS/스키마명을 선택 해 주세요.");
     		return;
     	}
     	
     	var url ='<c:url value="/dq/dqrs/batchDeleteDiagSditmList.do"/>';
     	 var json = {
     			"allDbms" 	   : dbId,
     			"allDbmsSchId" : schId,
     			"delType"      : "tbl"
     	}; 
     	IBSpostJson2(url, json,"", ibscallback);    
     	break;
	}
	
}

function postProcessIBS(res) {
	
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
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	
	if(row < 1) return;
	
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '테이블명 : ' + param.tblPnm + ' , 컬럼명: '+param.colPnm+ ' , FULL PATH: '+param.subjAllPath;
	//매핑정의서ID, 타겟테이블명
	$('#gov_tbl_title').html(tmphtml);
	
	//ibs2formmapping() 함수를 통해  sheet의 데이터와 폼의 데이터 id일치하면 뿌려줌
	ibs2formmapping(row, $("form#frmInput"), grid_sheet);
}

function grid_sheet_OnChange(row, col, value, cellx, celly) {
	if(grid_sheet.GetCellProperty(row, col, "SaveName") == "dbConnTrgId") {
		grid_sheet.SetCellValue(row, "dbSchId", null);
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
		//	alert(key);
			//alert(val);
			if(key == value) {
				grid_sheet.CellComboItem(row, "dbSchId", val);
				return;
			}
		});
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	var totalcnt = grid_sheet.SearchRows();
	
	for (var i=1; i<totalcnt+1; i++) {
		var tmpVal = grid_sheet.GetCellValue(i, "dbConnTrgId");
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			
			if(key == tmpVal) {
				grid_sheet.CellComboItem(i, "dbSchId", val);
				return;
			}
		});
	}
}

function grid_sheet_OnLoadExcel(result) {
	var totalcnt = grid_sheet.RowCount("I");
	for (var i=0; i<totalcnt; i++) {
		var tmpVal = grid_sheet.GetCellValue(i+1, "dbConnTrgId");

		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			if(key == tmpVal) {
				grid_sheet.CellComboItem(i+1, "dbSchId", val);
				grid_sheet.SetCellText(i+1, "dbSchId", grid_sheet.GetCellText(i+1,"dbSchId"));
				return;
			}
		});
	}
}

</script>


</head>

<body>

<div id="layer_div" >   
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">모델관리</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <input type="hidden" name="pdmTblId" id="pdmTblId" />
            
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code="DMN.GRP" />"> <!-- 도메인그룹 -->
                   <caption><s:message code="TBL.MAPG.DFNT.P.INQ.INQ.FORM"/></caption><!-- 테이블 매핑정의서 조회 검색폼 -->
                   <colgroup>
                    <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                         <tr>
								<th scope="row" class="th_require"><label for="dbConnTrgId">DBMS명/스키마물리명</label></th>
								<!-- DB물리명/스키마물리명 -->
								<td><select class="wd100" id="dbConnTrgId" class=""
									name="dbConnTrgId">
										<option value=""><s:message code="CHC" /></option>
										<!-- 선택 -->
								</select> <select class="wd100" id="dbSchId" class="" name="dbSchId">
										<option value=""><s:message code="CHC" /></option>
										<!-- 선택 -->
								</select></td>
								<th id="hidden3" style="display:'';" scope="row"><label for="">검증결과</label></th> 
							<td id="hidden4" style="display:'';" colspan="3" class="wd200">
								<select class="wd200" id="vrfRmk" name="vrfRmk">
									<option id ="" value=""><s:message code="CHC" /></option> 
									<option id ="1" value="1">정상</option>
									<option id ="2" value="2">진단대상DB미존재</option>
									<option id ="3" value="3">테이블,컬럼명 중복</option>
					            </select>

							</tr>
                            <tr>
                                <th scope="row"><label for="tblPnm">테이블명</label></th>
								<td><input type="text" id="tblPnm" name="tblPnm" class="wd200" /></td>
                                <th scope="row"><label for="colPnm">컬럼명</label></th>
								<td><input type="text" id="colPnm" name="colPnm" class="wd200" /></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <div class="tb_comment">- 등록시점에 따라 도메인명, 진단대상DB정보 변동으로 검증 결과가 다를 수 있습니다.</div>
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
	
            </div> 
             <div style="clear:both; height:10px;"><span></span></div>
               
         <!-- 조회버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

</div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "450px");</script>            
</div>
<!-- 그리드 입력 입력 End -->


<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area"> -->
<%-- 	    <div class="selected_title" id="gov_tbl_title"> <span></span></div> --%>
<!-- </div> -->
<!-- <!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End--> 

<%-- 	<div style="clear:both; height:10px;"><span></span></div> --%>
        
<!--         		    <div class="stit">DBMS 일괄삭제</div>검색조건 -->
            
<%--             <legend><s:message code="FOREWORD" /></legend><!--머리말--> --%>
<!--              <fieldset> -->
<!--             <div class="tb_basic2"> -->
<%--                 <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회--> --%>

<%--                    <caption>DBMS 일괄삭제</caption><!--진단대상DBMS--> --%>

<%--                    <colgroup> --%>
<%--                    <col style="width:20%;" /> --%>
<%--                    <col style="width:80%;" /> --%>
                 
<%--                    </colgroup> --%>

<!-- 							<tbody> -->
							
<!-- 								<tr> -->
<%-- 									<th scope="row" class ="th"><label for=""><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 --> --%>
<!-- 									<td id="colspan"> -->
<%-- 										<select class = "wd100" id="allDbms" name="" style ="width: 170px;"> --%>
<%-- 											<option value=""><s:message code="CHC" /></option> <!-- 선택 --> --%>
<%-- 					            		</select> --%>
<%-- 					            		<select class = "wd100" id="allDbmsSchId" name=""> --%>
<%-- 					            			<option value=""><s:message code="CHC" /></option> <!-- 선택 --> --%>
<%-- 					           			 </select> --%>
<!-- 					           			 <button  class="btn_search" id="batchDelete" name="batchDelete"> -->
<!-- 									 		일괄삭제 -->
<!-- 										</button> -->
<!-- 					       			 </td> -->
									
<!-- 								</tr> -->

<!-- 							</tbody> -->
<!-- 						</table>    -->
<!--             </div> -->
            
<!--             </fieldset> -->

</body>
</html>