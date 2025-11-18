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
<title><s:message code="APMA.MNG" /></title> <!-- 결재자 관리 -->

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
                      
        $("#btnNew").click(function(){
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    doAction("Add");
        });
        
        // 추가 Event Bind
        $("#btnSubTreeNew").hide();
        
     	// 추가 Event Bind
        $("#btnSubDelete").hide();
        
        // 엑셀내리기 Event Bind
        $("#btnSubExcelDown").click( function(){ doAction("Down2Excel"); } );

       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
//         doAction("Search");
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
                    {Text:"<s:message code='COMMON.HEADER.APPROVEUSER.LST'/>", Align:"Center"} 
                    /* No.|상태|선택|결재그룹ID|만료시각|시작시각|결재그룹명|설명|버전|등록유형코드|작성일시|작성사용자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",   Width:80,  SaveName:"aprgId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",   Width:50,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",   Width:50,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:80,  SaveName:"aprgNm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:200,  SaveName:"objDescn",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:1},          
                    {Type:"Combo",  Width:30,  SaveName:"regTypCd",     Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:40,  SaveName:"writDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:40,  SaveName:"writUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"writUserNm",    Align:"Left", Edit:0, Hidden:0}
                    
                    /*  {Type:"Text",   Width:130,  SaveName:"userTelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"userHtelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"emailAddr",      Align:"Left",   Edit:1, Hidden:0},

                    {Type:"Combo",  Width:100,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:130,  SaveName:"writUserId",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"writUserNm",   Align:"Center", Edit:0} */
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("regTypCd", ${codeMap.regTypCdibs});
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
        

       

        case "Search":
        	var param = $('#frmSearch').serialize();

        	//결재그룹 선택 초기화...
        	$("input[name=aprgId]").val('');
        	$('#program_sel_title').html('');
        	
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/sys/aprg/aprgSelectlist.do" />', param);
        	
        	break;
        	
                	
		case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(grid_sub);
        	
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sub);
        	
        	var DelJson = grid_sub.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/damgmt/approve/approveuserDelete.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "SearchUser":
        	var param = $('#frmSubSearch').serialize();
        	grid_sub.DoSearch('<c:url value="/commons/damgmt/approve/approvegroupuserSub_lst.do" />', param);
        	
        	break;
       
        case "Add":
        	var url = "<c:url value='/commons/user/userPop.do' />";
			var param = $("form#frmAprgId").serialize(); //$("form#frmInput").serialize();
			if($("input[name=aprgId]").val() == '') {
				showMsgBox("ERR", "<s:message code='MSG.APRL.GRP.CHC' />"); /* 결재그룹을 먼저 선택하세요. */
				return;
			}
			

			console.log("param v");
			console.log(param);
			
			param = decodeURIComponent(param);
			console.log("decode param v");
			console.log(param);
			
			var popwin = OpenModal(url+"?"+param, "userpop",  800, 500, "no");
			popwin.focus();
			break;
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
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
			doAction("Search");

			
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

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
//$("#hdnRow").val(row);

	if(row < 1)	return;

		
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;

	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var aprgId = "&aprgId="+grid_sheet.GetCellValue(row, "aprgId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code='APRL.GRP.NM' /> : ' + param.aprgNm; /* 결재그룹명 */
	$('#program_sel_title').html(tmphtml);
	
	
	$("#frmSubSearch #aprgId").val(param.aprgId);

	$("#frmAprgId #aprgId").val(param.aprgId);
	$("#frmAprgId #aprgNm").val(param.aprgNm);
	
	//메뉴ID를 토대로 조회한다....
	var param1 = "aprgId="+param.aprgId;
	grid_sub.DoSearch('<c:url value="/commons/damgmt/approve/approvegroupuser_lst.do" />', param1);
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
	    <div class="menu_title"><s:message code="APMA.MNG" /></div> <!-- 결재자 관리 -->
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
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='APMA.INQ' />"> <!-- 결재자 조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                         <th scope="row"><label for="aprgId"><s:message code="APRL.GRP.NM" /></label></th> <!-- 결재그룹명 -->
                            <td>
                                <select id="aprgId"  name="aprgId">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.aprgId}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                            </td>

                       <!--     <th scope="row"><label for="usergId">사용자그룹명</label></th>
                                <td><select id="usergId" class="usergId" name="usergId">
                                        <option selected="" value="">-전체</option>
                                        <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>  -->  
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="USER.ADDT" /></a></li> <!-- 사용자 추가 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="approveuser_dtl.jsp" %>
<!-- 			<div id="detailInfo"></div> -->
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->

   
<%-- <form id="frmExcel" name="frmExcel" action="" method="post" > --%>
<!-- 	<input type="hidden" name="excelhtml" id="excelhtml"> -->
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>