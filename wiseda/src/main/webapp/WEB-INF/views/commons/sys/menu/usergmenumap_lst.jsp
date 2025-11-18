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
<title><s:message code="MENU.AUTH.MNG" /></title> <!-- 메뉴권한 관리 -->

<script type="text/javascript">

var interval = "";

$(document).ready(function() {
// 		alert("document.ready");
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
		//$( "#tabs" ).tabs();
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSubSearch").click(function(){ doAction("Search");  });
                      
        $("#btnSubTreeNew").hide();
        
        $("#btnSubDelete").hide();
        
        // 엑셀내리기 Event Bind
        $("#btnSubExcelDown").click( function(){ doAction("Down2Excel"); } );


    }
);

$(window).on('load',function() {
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
                       {Text:"<s:message code='COMMON.HEADER.USERGMENUMAP.LST'/>", Align:"Center"}
                       /* No.|사용자그룹ID|사용자그룹논리명|사용자그룹물리명|사용자그룹유형|설명|버전|등록유형|작성일시|작성자ID|작성자명 */
                   ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"usergId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150,  SaveName:"usergLnm",    Align:"Left", Edit:0},
                    {Type:"Text",   Width:150,  SaveName:"usergPnm",    Align:"Left", Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"usergTypCd",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:180,  SaveName:"objDescn",     Align:"Left", 	 Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"objVers",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:80,  SaveName:"regTypCd",     Align:"Center", Edit:0},                        
                    {Type:"Date",   Width:80,   SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"writUserNm",   Align:"Left", Edit:0},
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	   	SetColProperty("usergTypCd", ${codeMap.usergTypCdibs});

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
      
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
        

       

        case "Search":
        	
        	$("#frmUsergId #usergId").val("");
        	$('#userg_sel_title').html("");
        	grid_sub.RemoveAll();
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/usergSelectlist.do" />', param);
        	
        	break;
        	
        case "SearchMenu":
        	if ($('#frmUsergId #usergId').val() == null || $('#frmUsergId #usergId').val() == '') {
        		showMsgBox("ERR", "<s:message code='MSG.INQ.USER.GRP.CHC' />"); /* 조회할 사용자그룹을 먼저 선택하세요. */
        		return;
        	}
        	var param = $('#frmUsergId').serialize();
        	
//         	alert(param);
        	grid_sub.DoSearch('<c:url value="/commons/sys/menu/usergmenumap_dtl.do" />', param);
        	
        	break;

        case "Save":
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
//         	setTreeCheckIBS(grid_sub);
        	
// 			var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	var SaveJson = grid_sub.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/sys/menu/menuReglist.do"/>';

         	var param = $('#frmUsergId').serialize();
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
        	
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;

        case "Down2ExcelMenu":  //엑셀내려받기
            grid_sub.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
    }       
}
 




function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
//$("#hdnRow").val(row);

	if(row < 1)	return;

	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var usergId = "&usergId="+grid_sheet.GetCellValue(row, "usergId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="USER.GRP.NM" /> : ' + param.usergLnm; /* 사용자그룹명 */
	$('#userg_sel_title').html(tmphtml);
	
	$("#frmUsergId #usergId").val(param.usergId);
	
	//메뉴ID를 토대로 조회한다....
	doAction("SearchMenu");
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
// 	alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("SearchMenu");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("SearchMenu");

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("SearchMenu");
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




</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="MENU.AUTH.MNG" /></div> <!-- 메뉴권한 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="결재자 조회">
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                         <th scope="row"><label for="usergId"><s:message code="USER.GRP.NM" /></label></th> <!-- 사용자그룹명 -->
                            <td>
                                <select id="usergId"  name="usergId">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.usergroup}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
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
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="userg_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="MENU.AUTH.INFO" /></a></li> <!-- 메뉴권한 정보 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="usergmenumap_dtl.jsp" %>
<!-- 			<div id="detailInfo"></div> -->
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->


</body>
</html>