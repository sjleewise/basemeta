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
<title></title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      

        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
    	
        $("#btnDelete").hide();
        $("#btnTreeNew").hide();

        
      //기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});
      
      //기간 7일 default 선택 
    //  $("#seven").click();
   	 	
    }
    
    
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	$(window).resize();
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	//5 : 6개월
	setBetweenDtm( 5, $( "#searchBgnDe" ), $( "#searchEndDe" ));
	
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.RQSTTODO.LST'/>", Align:"Center"}
                ];
                //No.|요청번호|요청구분|요청명|요청일자|승인일자|요청자|진행상태|결재진행레벨|비고
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq"      , Align:"Center", Edit:0},
                    {Type:"Text",   Width:160,  SaveName:"rqstNo"      , Align:"Center", Edit:0, Hidden:0},
                    {Type:"Combo",  Width:130,  SaveName:"bizDcd"      , Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:500,  SaveName:"rqstNm"      , Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Date",   Width:150,  SaveName:"rqstDtm"     , Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm"}, 
                    {Type:"Date",   Width:150,  SaveName:"aprvDtm"     , Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm"}, 
                    {Type:"Text",   Width:150,  SaveName:"rqstUserNm"  , Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Combo",  Width:150,  SaveName:"rqstStepCd"  , Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:150,  SaveName:"aprvStepLvl" , Align:"Left",   Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"reMark"      , Align:"Left",   Edit:0}
                   
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("rqstStepCd", ${codeMap.rqstStepCdibs});
	    SetColProperty("bizDcd", ${codeMap.bizDcdibs});
        //SetColHidden("rqstUserNm",1);

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
        case "Search":
        	
			var param = $("#frmSearch").serialize();

			if( $("#aprvDcd").val() == '2'){
				grid_sheet.DoSearch("<c:url value="/meta/stnd/selectRqstResultList.do" />", param);
			}else{
				grid_sheet.DoSearch("<c:url value="/meta/stnd/selectRqstToDoList.do" />", param);
			}
			
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;


        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'내결제목록조회'});
            
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
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	var rqstNo = grid_sheet.GetCellValue(row, "rqstNo");
	var bizDcd = grid_sheet.GetCellValue(row, "bizDcd");
	
	//window.open().location.href = "../../goRqstPage.do?rqstNo="+rqstNo + "&bizDcd=" + bizDcd;	
	window.location.href = "../../goRqstPage.do?rqstNo="+rqstNo + "&bizDcd=" + bizDcd;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DEMD.NM" /> : ' + param.rqstNm; /*요청명*/
	$('#rqst_sel_title').html(tmphtml);
	


}


function grid_sheet_OnSaveEnd(code, message) {

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
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="MY.APRL.LST.INQ" /></div> <!-- 내 결재목록 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INQ' />"> <!-- 표준단어조회 -->
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준단어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="bizDcd"><s:message code="BZWR.DSTC" /></label></th><!--  업무구분 -->
                                <td><select id="bizDcd" class="wd300" name="bizDcd">
                                       <option selected="" value=""><s:message code="WHL" /></option> <!-- 전체 -->
					                	<c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
                                </td>
                                                                
                                <th scope="row"><label for="aprvDcd"><s:message code="APRV.DV" /></label></th> <!-- 승인구분 -->
                                <td>
                                	<select id="aprvDcd" class="wd300" name="apvDcd">
                                		<option selected value="1"><s:message code="APRL.TRGT" /></option> <!--결재대상 -->
                                		<option  value="2"><s:message code="APRL.CMPL" /></option> <!-- 결재 완료 -->
                               		</select>
								</td>
                            </tr>
                            <tr>
                                <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                <td class="bd_none">
                                	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                	<a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                                </td>
                                
                                <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		                    <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td>
  						    </tr> 
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.DATA.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 더블클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
    
	
</div>
</div>


</body>
</html>
