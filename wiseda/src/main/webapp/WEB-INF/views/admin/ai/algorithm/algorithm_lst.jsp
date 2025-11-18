<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");


$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });

        // 저장 Event Bind
        $("#btnSave").click(function(){
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, "Save");	
        	//doAction("Save"); 
        }).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
        	
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "선택한 알고리즘에 속한 변수도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
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
    	/* double_select(dbmstypversJson, $("#dbmsTypCd"));
    	$('select', $("#dbmsTypCd").parent()).change(function(){
    		double_select(dbmstypversJson, $(this));
    	}); */
    	
    	//$( "#tabs" ).tabs();
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();

});


$(window).resize(
    
    function(){ 

    	setibsheight($("#grid_01"));
         
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.ADMIN.ALG.MST'/>", Align:"Center"}
                    /* No.|상태|선택|알고리즘ID|알고리즘유형|알고리즘논리명|알고리즘물리명|설명|버전|등록유형|작성일시|작성자ID|작성자명|분할분석수 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"algId",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",  Width:80,   SaveName:"algTypCd",   	 Align:"Center", Edit:1, KeyField:1},
                    {Type:"Text",   Width:150,  SaveName:"algLnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Text",   Width:80,  SaveName:"algPnm",   Align:"Left", Edit:1},
                    {Type:"Text",   Width:150,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
                    {Type:"Text",   Width:130,  SaveName:"objVers",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:130,  SaveName:"regTypCd",     Align:"Center", Edit:0, Hidden:1},        
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:150,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"writUserNm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Float",  Width:150,  SaveName:"algCnt",     Format:"#,###", Align:"Left", Edit:1, Hidden:0, KeyField:1}
                ];
        
        InitColumns(cols);
        
//         InitComboNoMatchText(1, "");
        
        SetColProperty("algTypCd", 	${codeMap.algTypCdibs});
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
// 		SetColProperty("metaMngYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();  
        
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
        	var row = grid_sheet.DataInsert(-1);
        
//         	grid_sheet.SetCellValue(row, "connTrgDrvrNm", "oracle.jdbc.driver.OracleDriver");
//         	grid_sheet.SetCellValue(row, "connTrgLnkUrl", "jdbc:oracle:thin:@{ip}:{port}:{sid}");
        	
        	grid_sub.RemoveAll();
        	$("#frmInput input").val("");
//         	$('#dbms_sel_title').html('');
        	

//         	$("#frmSearch #dbConnTrgId").val('');
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "SubNew":        //추가
        	//첫행에 추가...
        	if(isBlankStr($("#frmInput #algId").val())) {
        		showMsgBox("ERR", "선택한 알고리즘이 없습니다.<br>알고리즘을 먼저 선택하세요.");
        		return;
        	}
        	
        	var row = grid_sub.DataInsert(-1);
        	
         	grid_sub.SetCellValue(row, "algId", $('#frmInput #algId').val());
         	         	
            break;
            
        	
        case "Delete" :
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
			var delJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/admin/ai/algorithm/delalgorithm.do"/>";
// 			var param = $('form[name=frmInput]').serialize();
			var param = "";
			IBSpostJson2(url, delJson, param, ibscallback);

        	break;

        	
		case "SubDelete" :
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sub);
        	
        	//체크박스가 입력상태인 경우 삭제...
			if(!grid_sub.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
        	
			ibsSaveJson = grid_sub.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/admin/ai/algorithm/delalgorithmparam.do"/>";
// 			var param = $('form[name=frmInput]').serialize();
			var param = "";
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
        	break;
        	
        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	var url = "<c:url value="/admin/ai/algorithm/regalgorithmlist.do"/>";
         	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
            
            break;
            
        	
        case "SubSave" :
        	var SaveJson = grid_sub.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         ibsSaveJson = grid_sub.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0){
        		var message = "<s:message code="ERR.CHKSAVE" />";
				showMsgBox("ERR", message); 
        		return;
        	}
        	
        	var url = "<c:url value="/admin/ai/algorithm/regalgorithmparam.do"/>";
        	var param = $('#frmInput').serialize();
            IBSpostJson2(url, SaveJson, param, ibscallback);

        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/admin/ai/algorithm/getalgorithmlist.do" />', param);
        	
        	break;

        case "SubSearch":
        	var param = $('#frmInput').serialize();
        	//alert(param);
        	grid_sub.DoSearch('<c:url value="/admin/ai/algorithm/getalgorithmparam.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
            
        case "SubDown2Excel":  //엑셀내려받기
            
            
            grid_sub.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
            
            
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
        case "SubLoadExcel":  //엑셀업로
            
            
            grid_sub.LoadExcel({Mode:'HeaderMatch'});
            
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
			grid_sub.RemoveAll();
        	$("#frmInput input").val("");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("Search");
			grid_sub.RemoveAll();
        	$("#frmInput input").val("");
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("SubSearch");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		case "SCH" :  //스키마 저장/삭제 했을시...
			
			var param1 = "dbConnTrgId="+$('#frmSearch #dbConnTrgId').val();
			grid_sub.DoSearch('<c:url value="/commons/damgmt/db/dbconntrg_dtl.do" />', param1);
			
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
/* 	if(row < 1) return;
	
	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
	$("#usrId").val(usrId);  
	   
    $("#frmInput").attr("action", url).submit(); */
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
	//$("#hdnRow").val(row);

	if(row < 1)	return;

	
// 	return;
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
    //if(grid_sheet.GetCellValue(row, "ibsStatus")!="R") return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	$("#frmInput #algId").val(param.algId);
	$("#frmInput #algTypNm").val(grid_sheet.GetCellText(row,"algTypCd"));
	$("#frmInput #algLnm").val(param.algLnm);
	$("#frmInput #algPnm").val(param.algPnm);
	
	
	//알고리즘 ID 기준으로 파라미터 목록 조회
	var param1 = "algId="+param.algId;
	grid_sub.DoSearch('<c:url value="/admin/ai/algorithm/getalgorithmparam.do" />', param1);
}


function grid_sheet_OnChange(Row, Col, Value) {

	/* if(Col == 7) {
	        if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "ORA") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "oracle.jdbc.driver.OracleDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:oracle:thin:@{ip}:{port}:{sid}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "SYQ") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.sybase.jdbc3.jdbc.SybDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sybase:Tds:{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "SYA") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.sybase.jdbc3.jdbc.SybDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sybase:Tds:{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "DB2") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.ibm.as400.access.AS400JDBCDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:as400://{ip}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "MSQ") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sqlserver://{ip}:{port};DatabaseName={db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "ALT") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "Altibase.jdbc.driver.AltibaseDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:Altibase://{ip}:{port}/{sid}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "MYS") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.mysql.jdbc.Driver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:mysql://{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "TIB") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.tmax.tibero.jdbc.TbDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:tibero:thin:@{ip}:{port}:{db명}");
	        } else {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "");
	        }
	    } */
	}




function grid_sheet_OnSearchEnd(code, message) {
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
		grid_sheet.SetSelectRow(1);
		
		for(var i = 1; i <= grid_sheet.RowCount(); i++) {
			
			grid_sheet.SetCellEditable(i,"dbConnTrgPnm",false); 			
		}
	}
	
}



</script>

<div id="anatrg" style="width: 40%; float: left;">

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        <form id="frmSearch" name="frmSearch" method="post">
<%--         <input type="hidden" id="dbConnTrgId" name="dbConnTrgId" value="${record.dbConnTrgId}" /> --%>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='TARG.DB.MS.INQ' />"> <!-- 타겟DBMS 조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="algTypCd">알고리즘 유형</label></th> <!-- DBMS유형 -->
                            <td>
                                <span class="input_file">
									<select id="algTypCd" class="" name="algTypCd">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
										<c:forEach var="code" items="${codeMap.algTypCd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
									</select>
                                </span>
                            </td>
                           <th scope="row"><label for="algLnm">알고리즘명</label></th> <!-- DBMS명 -->
                            <td>
                                <input type="text" name="algLnm" id="algLnm" />
                            </td>
                       </tr>
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
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "350px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</div>
<div style="width: 1%;float: left;">&nbsp;</div>
<!-- 컬럼프로파일 상세 조회 -->
<div id="colprf" style="width:59%; float: left;">
	<%@include file="algorithmvar_lst.jsp" %>
</div>



