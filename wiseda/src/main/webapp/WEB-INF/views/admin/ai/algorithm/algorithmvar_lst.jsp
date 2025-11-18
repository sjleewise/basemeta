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


$(document).ready(function() {
	
// 		alert("document.ready");

		$("#frmInput input[type=text]").attr("readonly", true);
	
        
        // 조회 Event Bind
        $("#btnSubSearch").click(function(){ doAction("SubSearch");  });
                      
        // 추가 Event Bind
        $("#btnSubNew").click(function(){ doAction("SubNew");  });

        // 저장 Event Bind
        $("#btnSubSave").click(function(){
        	//var rows = grid_sub.FindStatusRow("I|U|D");
        	var rows = grid_sub.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, "SubSave");	
        }).show();

        // 삭제 Event Bind
        $("#btnSubDelete").click(function(){ 
        	
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sub, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "선택한 알고리즘 파라미터를 삭제 하시겠습니까?", "SubDelete");
        	}
        //	doAction("Delete");  
        	
        });
        
        // 엑셀내리기 Event Bind
        $("#btnSubExcelDown").click( function(){ doAction("SubDown2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnSubExcelLoad").click( function(){ doAction("SubLoadExcel"); } );
      
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
	initSubGrid();

});


$(window).resize(
    
    function(){ 

    	setibsheight($("#grid_sub"));
         
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initSubGrid()
{
    
    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.ADMIN.ALG.DTL'/>", Align:"Center"}
                ];
        //No.|상태|선택|알고리즘ID|변수ID|파라미터명|파라미터물리명|데이터타입|디폴트값|파라미터값|최소값|최대값|표시순서|사용여부|파라미터설명|버전|등록유형|작성일시|작성자ID|작성자명
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:30,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:100,  SaveName:"algId",       Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:80,   SaveName:"algArgId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:150,  SaveName:"argLnm",      Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Text",     Width:80,   SaveName:"argPnm",      Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"argDataType", Align:"Center", Edit:1, KeyField:1},
                    {Type:"Text",     Width:100,  SaveName:"argDefltVal", Align:"Center", Edit:1, KeyField:1},
                    {Type:"Text",     Width:100,  SaveName:"argVal",      Align:"Center", Edit:1, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"minVal",      Align:"Center", Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"maxVal",      Align:"Center", Edit:1},
                    {Type:"Int",      Width:100,  SaveName:"dispOrd",     Align:"Center", Edit:1},
                    {Type:"Combo",    Width:100,  SaveName:"useYn",       Align:"Center", Edit:1, KeyField:1},
                    {Type:"Text",     Width:150,  SaveName:"objDescn",    Align:"Left", 	 Edit:1},
                    {Type:"Text",     Width:130,  SaveName:"objVers",     Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:130,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1},        
                    {Type:"Date",     Width:130,  SaveName:"writDtm",  	  Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",     Width:150,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:150,  SaveName:"writUserNm",   Align:"Left", Edit:0, Hidden:1}
                ];
        
        InitColumns(cols);
        
//         InitComboNoMatchText(1, "");
        
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("useYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
   
}






</script>

<!-- 검색조건 입력폼 -->
<div id="algorithm_info_div">
        <div class="stit">알고리즘 정보</div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        <form id="frmInput" name="frmInput" method="post">
        	<input type="hidden" name="algId" id="algId" >
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_read" >
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
                           <th scope="row"><label for="algTypNm">알고리즘 유형</label></th> 
                            <td colspan="3">
                                <input type="text" name="algTypNm" id="algTypNm" />
                            </td>
                       </tr>
                       <tr>                               
                           <th scope="row"><label for="algLnm">알고리즘명</label></th>
                            <td>
                                <input type="text" name="algLnm" id="algLnm" />
                            </td>
                           <th scope="row"><label for="algPnm">알고리즘(물리명)</label></th> 
                            <td>
                                <input type="text" name="algPnm" id="algPnm" />
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
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_sub">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>




