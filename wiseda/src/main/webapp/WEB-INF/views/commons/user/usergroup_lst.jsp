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
<title><s:message code="USER.GRP.MNG" /></title> <!-- 사용자그룹관리 -->

<script type="text/javascript">

//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";

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
      
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
});


$(window).resize(
    
    function(){
                
    	setibsheight($("#grid_01")); 
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.USERGROUP.LST'/>", Align:"Center"}
                    /* No.|상태|선택|사용자그룹ID|사용자그룹논리명|사용자그룹물리명|사용자그룹유형|객체설명|객체버전|등록유형|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"usergId",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:130,  SaveName:"usergLnm",    Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"usergPnm",    Align:"Left", Edit:1, KeyField:1},
                    {Type:"Combo",   Width:130,  SaveName:"usergTypCd",    Align:"Center", Edit:1,  Hidden:0},
                    {Type:"Text",   Width:150,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
                    {Type:"Text",   Width:40,  SaveName:"objVers",      Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Combo",  Width:80,  SaveName:"regTypCd",     Align:"Center", Edit:0},                        
                    {Type:"Date",   Width:80,   SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:80,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"writUserNm",   Align:"Left", Edit:0},
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	//SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	   	SetColProperty("usergTypCd", 	${codeMap.usergTypCdibs}); 
	   	SetColProperty("regTypCd", 		${codeMap.regTypCdibs});
	   	
//         SetColProperty("usergTypCd", 	{ComboCode:"DA|AD|DB|UR|MR|DV", ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        
        //콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
        
		//히든 컬럼 설정...
//         SetColHidden("ibsStatus", 1);
        SetColHidden("usergId", 1);
        SetColHidden("objVers", 1);
//         SetColHidden("regTypCd", 1);
        SetColHidden("writUserId", 1);
        SetColHidden("writUserNm", 1);
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
        
    	case "New":        //추가
        	//첫행에 추가...
        	grid_sheet.DataInsert(0);
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/userg_st.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");

        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/user/usergDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
           	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = "<c:url value="/commons/user/usergReglist.do"/>";
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/usergSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"사용자그룹 관리"});
            
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
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
			
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
	
	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
	$("#usrId").val(usrId);  
	   
    $("#frmInput").attr("action", url).submit();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
    $("#sltGb").val(    grid_sheet.GetCellValue(row,"arrRqstDcd"));
    $("#stwLnm").val(   grid_sheet.GetCellValue(row,"arrStwLnm"));
    $("#stwPnm").val(   grid_sheet.GetCellValue(row,"arrStwPnm"));
    $("#stwEfn").val(   grid_sheet.GetCellValue(row,"arrStwEfn"));
    $("#cchNm").val(    grid_sheet.GetCellValue(row,"arrCchNm"));
    $("#objDescn").val( grid_sheet.GetCellValue(row,"arrObjDescn"));
    
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
	    <div class="menu_title"><s:message code="USER.GRP.REG" /></div> <!-- 사용자그룹 등록 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.GRP.INQ' />"> <!-- 사용자그룹조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="usergLnm"><s:message code="USER.GRP.NM" /></label></th> <!-- 사용자그룹명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="usergLnm" id="usergLnm" />
                                </span>
                                </td>
                            <th scope="row"><label for="usergTypCd"><s:message code="USER.GRP.PTRN" /></label></th> <!-- 사용자그룹유형 -->
                                <td><select id="usergTypCd" class="usergTypCd" name="usergTypCd">
                                        <option value="">===<s:message code="WHL" />===</option> <!-- 전체 -->
					                	<c:forEach var="code" items="${codeMap.usergTypCd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
                                 </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />   
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<%-- 	<div class="selected_title_area">
		    <div class="selected_title">주제영역명 : <span></span></div>
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div class="sub_tab_area">
			<div class="stab">
	        	<div class="stab_108_over">탭제목1</div>
	            <div class="stab_108"><a href="#">탭제목2</a></div>
	        </div>
	</div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 입력폼 시작 -->
 	<div id="input_form_div">
	 <div class="stit">입력폼</div>
	 <div style="clear:both; height:5px;"><span></span></div>
	            <form action="">
                <fieldset>
                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
                <div class="tb_basic">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
                        <caption>
                        테이블 이름
                        </caption>
                        <colgroup>
                        <col style="width:15%;" />
                        <col style="width:35%;" />
                        <col style="width:15%;" />
                        <col style="width:35%;" />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th scope="row" class="th_require"><label for="id 와 반드시 같은 명이어야 함">입력항목</label></th>
                                <td colspan="3"><input type="text" id="label for 와 반드시 같은 명이어야 함" /></td>
                            </tr>
                            <tr>
                                <th scope="row" class="th_require"><label for="">입력항목</label></th>
                                <td colspan="3"><textarea id="" name="" accesskey=""></textarea></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">입력항목</label></th>
                                <td colspan="3"><textarea id="" name="" accesskey=""></textarea></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">첨부파일1</label></th>
                                <td colspan="3"><span class="input_file">
                                    <input type="file" id="" />
                                    </span></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">첨부파일2</label></th>
                                <td colspan="3"><span class="input_file">
                                    <input type="file" id="" />
                                    </span></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">입력항목</label></th>
                                <td><input type="text" id="" /></td>
                                <th scope="row"><label for="">입력항목</label></th>
                                <td><input type="text" id="" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">입력불가</label></th>
                                <td><span class="input_inactive"><input type="text" id="" /></span></td>
                                <th scope="row" class="th_require"><label for="">입력항목</label></th>
                                <td><input type="text" id="" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">체크박스</label></th>
                                <td><span class="input_check">
                                    <input type="checkbox" id="" /> 체크1 <input type="checkbox" id="" /> 체크2
                                    </span></td>
                                <th scope="row"><label for="">라디오버튼</label></th>
                                <td><span class="input_radio">
                                    <input type="radio" id="" /> 라디오1 <input type="radio" id="" /> 라디오2
                                    </span></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">셀렉트박스</label></th>
                                <td><select id="" class="" name="">
                                        <option selected="" value="">셀렉트박스</option>
                                        <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
                                        <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
                                    </select></td>
                                <th scope="row"><label for="">셀렉트박스</label></th>
                                <td><select id="" class="" name="">
                                        <option selected="" value="">셀렉트박스</option>
                                        <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
                                        <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
                                    </select></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">입력항목</label></th>
                                <td colspan="3"><select id="" class="" name="">
                                        <option selected="" value="">셀렉트박스</option>
                                        <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
                                        <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
                                    </select>
                                    <select id="" class="" name="">
                                        <option selected="" value="">셀렉트박스</option>
                                        <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
                                        <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
                                    </select>
                                    <select id="" class="" name="">
                                        <option selected="" value="">셀렉트박스</option>
                                        <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
                                        <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
                                    </select></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="">다운로드</label></th>
                                <td><span class="tb_down"><a href="#">download.zip</a></span></td>
                                <th scope="row"><label for="">텍스트</label></th>
                                <td>텍스트테스트</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                </fieldset>
            </form>
	</div> 
 --%>	<!-- 입력폼 끝 -->
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>

</body>
</html>