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
<title>등록요청조회</title>

<script type="text/javascript">

var interval = "";



$(window).resize(
	    
	    function(){
	                
	    	// grid_sheet.SetExtendLastCol(1);    
	    }
	);


// var ofBizType = ${codeMap.ofBizType} ;
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

     	//엔터시 조회
    	EnterkeyProcess("Search");
      
      
    }
    
    
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	
	doAction("Search");

	
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
	
});

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
			var param = $("#frmSearch").serialize();

			$('#rqst_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/meta/stnd/selectRqstToDoList.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;


        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;


    }       
}

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.RQSTTODO.LST1'/>", Align:"Center"}
                ];
        //No.|요청번호|요청구분|요청명|요청일자|승인일자|요청자|진행상태|결재진행레벨
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:20,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"rqstNo",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:50,  SaveName:"bizDcd",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:180,  SaveName:"rqstNm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Date",   Width:70,  SaveName:"rqstDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm"}, 
                    {Type:"Date",   Width:40,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:50,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Combo",   Width:50,  SaveName:"rqstStepCd",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:90,  SaveName:"aprvStepLvl",    Align:"Left", Edit:0}
                   
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("rqstStepCd", ${codeMap.rqstStepCdibs});
	    SetColProperty("bizDcd", ${codeMap.bizDcdibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
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
	var rqstNo = grid_sheet.GetCellValue(row, "rqstNo");
	var bizDcd = grid_sheet.GetCellValue(row, "bizDcd");
	
	window.open().location.href = "../../goRqstPage.do?rqstNo="+rqstNo + "&bizDcd=" + bizDcd;	
	
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
 <!-- 오른쪽 내용 시작 -->
<div class="right">
	<div class="stit">결재대상 조회</div>
	
	  
	<form id="frmSearch" name="frmSearch" method="post">


            
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="표준단어조회">
                   <caption>검색조건</caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:40%;" />
                   <col style="width:10%;" />
                   <col style="width:40%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="bizDcd">요청구분</label></th>
                                <td><select id="bizDcd" class="wd300" name="bizDcd">
                                       <option selected="" value="">전체</option>
					                	<c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select></td>
                                <th scope="row"><label for="rqstNm">요청명</label></th>
                                <td><input type="text" id="rqstNm" class="wd300" name="rqstNm"/></td>
                            </tr>
                            <tr>
                                         <th class="bd_none">기간</th>
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt">1일</a>
                                         	<a href="#" class="tb_bt">3일</a>
                                         	<a href="#" class="tb_bt">7일</a>
                                         	<a href="#" class="tb_bt">1개월</a>
                                         	<a href="#" class="tb_bt">3개월</a>
                                         	<a href="#" class="tb_bt">6개월</a>
                                         </td>
                                         <th>조회기간</th>
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td>
  						    </tr> 
                   </tbody>
                 </table>   
            
            
        <div class="tb_comment">- 더블클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 <span style="font-weight:bold; color:#444444;">Ctrl + C</span>를 사용하시면 됩니다.</div>
        </form>
	<div style="clear:both; height:10px;"><span></span></div>
       <!-- 조회버튼영역  -->         
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
	
	<div style="clear:both; height:5px;"><span></span></div>
	       <!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	
</div>
<!-- 오른쪽 내용 끝 -->

  
</body>
</html>