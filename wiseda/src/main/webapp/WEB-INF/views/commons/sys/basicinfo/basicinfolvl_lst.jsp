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
<title><s:message code="BSIC.INFO.LVL.MNG" /></title> <!-- 기본정보 레벨 관리 -->

<script type="text/javascript">

//var interval = "";
//var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

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
       
        	
        });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
     // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 

        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        //create_selectbox(usergJson, $("#usergId"));
        //create_selectbox(datatpJson, $("#dataType"));
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.BASICINFOVL.LST'/>", Align:"Center"}
                    /* No.|상태|선택|기본정보명|기본정보레벨|만료일시|시작일시|SELECT BOX ID|SELECT BOX NM|설명|버전|등록유형|작성일시|작성사용자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",   Width:60,  SaveName:"bscId",    Align:"Left", Edit:1, Hidden:0, KeyField:1, UpdateEdit:0},
                    {Type:"Combo",   Width:60,  SaveName:"bscLvl",    Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Date",   Width:60,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",   Width:60,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:60,  SaveName:"selectBoxId",    Align:"Left", Edit:1},
                    {Type:"Text",   Width:60,  SaveName:"selectBoxNm",    Align:"Left", Edit:1},
                    {Type:"Text",   Width:100,  SaveName:"objDescn",      Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",   Width:60,  SaveName:"objVers",      Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",  Width:20,  SaveName:"regTypCd",     Align:"Center", Edit:0},
                    {Type:"Date",   Width:60,  SaveName:"writDtm",      Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:60,  SaveName:"writUserId",      Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"writUserNm",      Align:"Left", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
        SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        SetColProperty("bscId", ${codeMap.bscLvlCdibs});
        SetColProperty("bscLvl", {ComboCode:"1|2|3|4|5", 	ComboText:"1|2|3|4|5"})
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
                                
            break;
            
        case "Delete" :

        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/sysmgmt/basicinfo/basicInfoLvlDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
           	
        	ibsSaveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
        	//SELECT BOX ID의 갯수와 기준정보레벨의 값이 일치하는지 검사한다...
        	var validCheck = selectBoxIdCheck(ibsSaveJson);
        	if (validCheck != 0) {
        		showMsgBox("ERR", validCheck + "<s:message code='MSG.DEF.LEV' />"); /* 행의 기본정보레벨과 SELECT BOX값이 일치하지 않습니다.<br>SELECT BOX 값은 '|'로 연결하세요. */
        		return;
        	}
        		
            var url = "<c:url value="/commons/sysmgmt/basicinfo/basicInfoLvlReglist.do"/>";
        	var param = "";
            IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/sysmgmt/basicinfo/basicInfoLvlList.do" />', param);
        	
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

function selectBoxIdCheck(ibsSaveJson) {
    //Select Box Id값 체크 함수...
    //'|'로 구분한 갯수가 기준정보레벨의 수와 일치하는지 확인하여 다를경우 return...
	var tmpSelectBoxId = "";
	var tmpSelectBoxNm = "";
	for(var i=0; i<ibsSaveJson.data.length; i++){
		tmpSelectBoxId = ibsSaveJson.data[i].selectBoxId.split("|");
		tmpSelectBoxNm = ibsSaveJson.data[i].selectBoxNm.split("|");
		if(ibsSaveJson.data[i].selectBoxId != "" && tmpSelectBoxId.length != ibsSaveJson.data[i].bscLvl) {
			return ibsSaveJson.data[i].ibsSeq;
		}
		if(ibsSaveJson.data[i].selectBoxNm != "" && tmpSelectBoxNm.length != ibsSaveJson.data[i].bscLvl) {
			return ibsSaveJson.data[i].ibsSeq;
		}
	}
	return 0;

 
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
	    <div class="menu_title"><s:message code="BSIC.INFO.LVL.MNG" /></div> <!-- 기본정보 레벨관리 -->
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
                          <th scope="row"><label for="criInfoId"><s:message code="BSIC.INFO.NM" /></label></th> <!-- 기본정보명 -->
                            <td colspan="3">
                            <select id="bscId"  name="bscId">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.bscLvlCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select> 
								</td>
					</tr>
                   </tbody> 
                 </table>   
            </div>
            </fieldset> 
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