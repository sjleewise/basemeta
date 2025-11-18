<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="DB.MS.DATA.TY.MNG" /></title> <!-- DBMS 데이터타입 관리 -->

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
var dbmsTypCdJson = ${codeMap.dbmstypvers} ; // var dbmsTypCdJson = ${codeMap.dbmsDataTypeDb} ;

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
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save"); 	
		}).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
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
     	double_select(dbmsTypCdJson, $("#frmSearch #dbmsTypCd"));
     	$('select', $("#frmSearch #dbmsTypCd").parent()).change(function(){
     		double_select(dbmsTypCdJson, $(this));
     	});
        
    }
);

$(window).on('load',function() {
// 	alert('window.load');
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
                    {Text:"<s:message code='COMMON.HEADER.DBMSDATATYPEMAP.LST'/>", Align:"Center"}
                    /* No.|상태|선택|데이터타입매핑룰ID|만료일시|시작일시|매핑룰명|논리데이터타입|DBMS유형|DBMS데이터타입|최소값|조건|최대값|설명|버전|등록유형코드|작성일시|작성사용자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:30,  SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:40,  SaveName:"dataTypeMapruId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:50,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",     Width:50,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",     Width:60,  SaveName:"mapruNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",    Width:60,  SaveName:"lgcDataType",    Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Combo",    Width:40,  SaveName:"dbmsTypCd",    Align:"Center", Edit:1, KeyField:1},
                    {Type:"Combo",    Width:40,  SaveName:"dbmsDataTypeId",    Align:"Center", Edit:1, KeyField:1},
                    {Type:"Text",     Width:30,  SaveName:"minVal",      Align:"Center", Edit:1, Hidden:0},
                    {Type:"Combo",    Width:30,  SaveName:"cndCd",       Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",     Width:30,  SaveName:"maxVal",      Align:"Center", Edit:1, Hidden:0},
                    {Type:"Combo",    Width:30,  SaveName:"lenUseYn",    Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",     Width:120, SaveName:"objDescn",    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:30,  SaveName:"objVers",     Align:"Center", Edit:0, Hidden:1},          
                    {Type:"Combo",    Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0}, 
                    {Type:"Date",     Width:40,  SaveName:"writDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",     Width:40,  SaveName:"writUserId",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"writUserNm",  Align:"Left", Edit:0, Hidden:0}

                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
        
        //공통코드 IBS셋팅 (코드명, 타입(LI,CI), 그리드명, saveName)
		<%--setComboIBS("dbmsDataType", "LI", grid_sheet, "dbmsDataTypeId");--%>
        
        <%--SetColProperty("dbmsDataTypeId", ${codeMap.dbmsDataTypeibs});--%>
	   	SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	   	SetColProperty("dbmsTypCd", ${codeMap.dbmsTypCdibs});
	   	SetColProperty("cndCd", ${codeMap.cndCdibs});
	   	SetColProperty("lgcDataType", ${codeMap.dataTypeibs});
	   	SetColProperty("lenUseYn", {ComboCode:"Y|N", 	ComboText:"예|아니요"});
	   	
        //SetColProperty("usergId", 	{ComboCode:"DA|AD|DB|UR|MR|DV", 	ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
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
        
	    case "New":        
	    
	    	var row = grid_sheet.DataInsert(-1);
	    	grid_sheet.SetCellValue(row, "dbmsTypCd", "");
	    	
	        break;
                                   
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
         	//setTreeCheckIBS(grid_sheet);
        	        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/damgmt/db/datatypemapDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/db/datatypemapReglist.do"/>';
        	
         	var param = "";

            IBSpostJson2(url, SaveJson, param, ibscallback);
             
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/db/datatypemapSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
                 
        	grid_sheet.LoadExcel({Mode:'HeaderMatch'});          
        
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
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
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
    
	if(row < 1) return;

}

function grid_sheet_OnChange(row, col, value, cellx, celly) {
	
	if(grid_sheet.GetCellProperty(row, col, "SaveName") == "dbmsTypCd") {
		$.each(${codeMap.dbmsDataType}, function(key, val) {
			
			if(key == value) {
				grid_sheet.CellComboItem(row, "dbmsDataTypeId", val);
				return;
			}
		});
	}

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    
}

function grid_sheet_OnSearchEnd(Code, Msg) { 
	
	if(Code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	
		var totalcnt = grid_sheet.SearchRows();
		
		for (var i=0; i<totalcnt; i++) {
			var tmpVal = grid_sheet.GetCellValue(i+1, "dbmsTypCd");
			$.each(${codeMap.dbmsDataType}, function(key, val) {
				
				if(key == tmpVal) {
					grid_sheet.CellComboItem(i+1, "dbmsDataTypeId", val);
					return;
				}
			});
		}
	}
}

function grid_sheet_OnLoadExcel(result) {

	
	var totalcnt = grid_sheet.RowCount("I");

	for (var i=0; i<totalcnt; i++) {

		var tmpVal = grid_sheet.GetCellValue(i+1, "dbmsTypCd");
		$.each(${codeMap.dbmsDataType}, function(key, val) {
			
			if(key == tmpVal) {

				grid_sheet.CellComboItem(i+1, "dbmsDataTypeId", val);

				grid_sheet.SetCellValue(i+1, "dbmsDataTypeId", grid_sheet.GetCellText(i+1, "dbmsDataTypeId"));

				return;
			}
		});
	}

	//grid_sheet.SetCellValue(totalcnt, "dbmsDataTypeId", grid_sheet.GetCellText(totalcnt, "dbmsDataTypeId"));
}



function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DBMS.DATA.TY.CONVERSION.MNG"/></div> <!-- DBMS 데이터타입 변환관리 -->
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
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>      
                   <tr>
                   			<th scope="row"><label for="mapruNm"><s:message code="MAPG.RULE.NM" /></label></th> <!-- 매핑룰명 -->
                            <td><input type="text" name="mapruNm" id="mapruNm" /></td>  
                   			<th scope="row"><label for="lgcDataType"><s:message code="LGC.DATA.TY" /></label></th> <!-- 논리데이터타입 -->
                            <td><select id="lgcDataType" class="wd150" name="lgcDataType">
                                        <option selected="" value="">----<s:message code="WHL" />----</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.dataType}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
                   </tr>
                   <tr>                    
		  					<th scope="row"><label for="dbmsDataTypeId"><s:message code="DB.MS.PTRN" /></label></th> <!-- DBMS유형 -->
		                            <td colspan="3">
		                                <span class="input_file">
											<select id="dbmsTypCd" class="" name="dbmsTypCd">
												<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
											</select>
											<select id="dbmsDataTypeId" class="" name="dbmsDataTypeId" style="display:none;">
												<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
										 	</select>
		                                </span>
		                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        <div class="tb_comment"></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>