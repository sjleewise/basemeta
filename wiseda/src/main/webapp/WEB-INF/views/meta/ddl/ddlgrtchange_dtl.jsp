<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">
//최근 선택 row

$(document).ready(function(){

	//그리드 초기화
//	initsubgrid_ddlgrtchange();
		
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_ddlgrtchange.FitColWidth();
});


function initsubgrid_ddlgrtchange() {

    with(grid_sub_ddlgrtchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
		SetMergeSheet(5);
        
        var headtext2  = "No.|만료일시|시작일시";
	    	headtext2 += "|DDL권한ID|Grantor|Grantor|Grantor|Grantor";
	    	headtext2 += "|오브젝트ID|오브젝트명|오브젝트유형";
	    	headtext2 += "|Granted to|Granted to|Granted to|Granted to";
	    	headtext2 += "|권한|권한|권한|권한|권한|SYNONYM여부";
	    	headtext2 += "|설명|버전|등록유형|요청일시|요청자ID|요청자명|요청번호|요청일련번호|승인일시|승인자명";
        
        var headtext  = "No.|만료일시|시작일시";
        	headtext += "|DDL권한ID|DB접속대상ID|DB접속대상물리명|DB사용자ID|DB사용자물리명";
        	headtext += "|오브젝트ID|오브젝트명|오브젝트유형";
        	headtext += "|DB접속대상ID|DB접속대상물리명|ROLE ID|ROLE명";
        	headtext += "|SELECT|INSERT|UPDATE|DELETE|EXECUTE|SYNONYM여부";
        	headtext += "|설명|버전|등록유형|요청일시|요청자ID|요청자명|요청번호|요청일련번호|승인일시|승인자명";
      
        var headers = [
                    {Text:headtext2, Align:"Center"}
                    ,{Text:headtext, Align:"Center"}
                ];
    	       
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Date",   Width:100,   SaveName:"expDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Date",   Width:100,   SaveName:"strDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						
						{Type:"Text",   Width:0,   SaveName:"ddlGrtId",	 Align:"Center",   Edit:0, Hidden:1},		
						{Type:"Text",   Width:100,   SaveName:"grtorDbId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtorDbPnm",	 Align:"Center",   Edit:0, Hidden:0, KeyField:0},		
						{Type:"Text",   Width:100,   SaveName:"grtorSchId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtorSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
						
						{Type:"Text",   Width:100,   SaveName:"ddlObjId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlObjPnm",	 Align:"Center",   Edit:0, KeyField:0},
						{Type:"Combo",   Width:100,   SaveName:"ddlObjTypCd",	 Align:"Center",   Edit:0, KeyField:0},
						
						{Type:"Text",   Width:100,   SaveName:"grtedDbId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtedDbPnm",	 Align:"Center",   Edit:0, Hidden:0, KeyField:0},		
						{Type:"Text",   Width:100,   SaveName:"grtedSchId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtedSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
						
						{Type:"CheckBox",   Width:50,   SaveName:"selectYn",	 Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
						{Type:"CheckBox",   Width:50,   SaveName:"insertYn",	 Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
						{Type:"CheckBox",   Width:50,   SaveName:"updateYn",	 Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
	                    {Type:"CheckBox",   Width:50,   SaveName:"deleteYn",     Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
	                    {Type:"CheckBox",   Width:50,   SaveName:"executeYn",    Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
	                    {Type:"Text",   Width:50,   SaveName:"synonymYn",     Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
						
						{Type:"Text",   Width:40,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:0},
						{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:0}
					
                ];
                    
        InitColumns(cols);
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("ddlObjTypCd",	${codeMap.objDcdibs});		
		
      	//콤보 목록 설정...

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");

      	SetSheetHeight(250);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddlgrtchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddlgrtchange.ShowDebugMsg(-1);	
    	
}

$(window).load(function() {
	initsubgrid_ddlgrtchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_ddlgrtchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_ddlgrtchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_ddlgrtchange_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    

    <div style="clear:both; height:10px;"><span></span></div>
    
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_ddlgrtchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
