<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

var connTrgSchJson = ${codeMap.connTrgSch} ;
var connTrgRoleJson = ${codeMap.connTrgRole} ;

var interval = "";
EnterkeyProcess("Search");
$(document).ready(function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
//         $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        

    	
    	$( "#tabsGrt" ).tabs();
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	//setautoComplete($("#frmSearch #pdmTblPnm"), "DDLTBL");

    	double_select(connTrgSchJson, $("#frmSearch #grtorDbId"));
       	$('select', $("#frmSearch #grtorDbId").parent()).change(function(){
       		double_select(connTrgSchJson, $(this));
       	});
       	
       	double_select(connTrgRoleJson, $("#frmSearch #grtedDbId"));
       	$('select', $("#frmSearch #grtedDbId").parent()).change(function(){
       		double_select(connTrgRoleJson, $(this));
       	});
    	
});

$(window).load(function() {

		
	initGrid();
	
	
	loadDetail();
	
	var ddlGrtId ="";
 	ddlGrtId="${search.ddlGrtId}";
	param ="ddlGrtId="+ddlGrtId;
	if(ddlGrtId != null && ddlGrtId != "" && ddlGrtId !="undefined"){
		grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlGrtlist.do" />", param);
	}
	
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}	
	$("#tabsGrt").show();
	//$( "#layer_div" ).show();
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
		SetMergeSheet(5);
        
        var headtext2  = "No.";
	    	headtext2 += "|DDL권한ID|Grantor|Grantor|Grantor|Grantor";
	    	headtext2 += "|오브젝트ID|오브젝트명|오브젝트논리명|오브젝트유형";
	    	headtext2 += "|Granted to|Granted to|Granted to|Granted to";
	    	headtext2 += "|권한|권한|권한|권한|권한|SYNONYM여부";
	    	headtext2 += "|DBA처리|설명";
        
        var headtext  = "No.";
        	headtext += "|DDL권한ID|DB접속대상ID|DB접속대상물리명|DB사용자ID|DB사용자물리명";
        	headtext += "|오브젝트ID|오브젝트명|오브젝트논리명|오브젝트유형";
        	headtext += "|DB접속대상ID|DB접속대상물리명|ROLE ID|ROLE명";
        	headtext += "|SELECT|INSERT|UPDATE|DELETE|EXECUTE|SYNONYM여부";
        	headtext += "|DBA처리|설명";
      
        var headers = [
                    {Text:headtext2, Align:"Center"}
                    ,{Text:headtext, Align:"Center"}
                ];
        
			var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
						
						{Type:"Text",   Width:0,   SaveName:"ddlGrtId",	 Align:"Center",   Edit:0, Hidden:1},		
						{Type:"Text",   Width:100,   SaveName:"grtorDbId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtorDbPnm",	 Align:"Center",   Edit:0, Hidden:0, KeyField:0},		
						{Type:"Text",   Width:100,   SaveName:"grtorSchId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtorSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
						
						{Type:"Text",   Width:100,   SaveName:"ddlObjId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlObjPnm",	 Align:"Center",   Edit:0, KeyField:0},
						{Type:"Text",   Width:100,   SaveName:"ddlObjLnm",	 Align:"Center",   Edit:0, KeyField:0},
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
	                    {Type:"Combo",   Width:100,   SaveName:"prcTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
	                    {Type:"Text",   Width:100,   SaveName:"scrtInfo", 		Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);

        SetColProperty("ddlObjTypCd",	${codeMap.objDcdibs});
        SetColProperty("prcTypCd",	${codeMap.prcTypCdibs});

      	InitComboNoMatchText(1, "");
      	SetSheetHeight(350);
        FitColWidth();  
        
//        SetExtendLastCol(1);    
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
            /*	
        	if ( ( isBlankStr($("#frmSearch #dbConnTrgId").val(), 'O') 
           			|| isBlankStr($("#frmSearch #dbSchId").val(), 'O')
           		  )
           			&& isBlankStr($("#frmSearch #ddlObjPnm").val(), 'O') 
//            			&& isBlankStr($("#frmSearch #pdmTblPnm").val(), 'O') 
//            			&& isBlankStr($("#frmSearch #pdmColPnm").val(), 'O')           			
           		) {
           		showMsgBox("INF", "검색조건이 없습니다.<br>DBMS/스키마명,오브젝트명 중<br>최소 1개이상 검색조건을 입력후 조회하십시요.");
           		return;
           	}
        	*/
			var param = $("#frmSearch").serialize();
			$('#ddl_sel_title').html('');
			
			loadDetail();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlGrtlist.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
//             grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        	
        	//보여지는 컬럼들만 엑셀 다운로드          
            var downColNms = "";
           	for(var i=0; i<grid_sheet.LastCol()+1;i++ ){
           		if(grid_sheet.GetColHidden(i) != 1){
           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
           		}
           	}
           	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, DownCols:downColNms, FileName:"ddlGrtList.xls"});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
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
	
	if(row < 1) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var ddlGrt = "&ddlGrtId="+grid_sheet.GetCellValue(row, "ddlGrtId");
	ddlGrt += "&rqstNo="+param.rqstNo;

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '오브젝트 : ' + param.ddlObjPnm;
	$('#ddl_sel_title').html(tmphtml);
	
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(ddlGrt);
	
	grid_sub_ddlgrtchange.DoSearch('<c:url value="/meta/ddl/getDdlGrtChange.do" />', ddlGrt);
	
	var param1 = "ddlGrtId="+param.ddlGrtId;
	param1 += "&regTypCd="+param.regTypCd;
	param1 += "&rqstNo="+param.rqstNo;
	
	$('div#detailGrtInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param1, function(){});
// 	$('#frmInputDdlScript #ddlscript').val(param.scrtInfo);

}

//상세정보호출
function loadDetail(param) {
	$('div#detailGrtInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlgrtinfo_dtl.do"/>', param, function(){
		if(param == null || param == "" || param =="undefined"){
			
		} else {
		}
		//$('#tabsGrt').show();
	});
}


function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//form 내용을 초기화 한다.....
		//doAction('Add');
		//$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">DDL 오브젝트조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준용어조회">
                   <caption>표준용어 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="grtorSchPnm">Grantor to<br>DBMS/스키마</label></th>
                            <td>
                                 <select id="grtorDbId" class="" name="grtorDbId">
					             	<option value="">선택</option>
					             </select>
					             <select id="grtorSchId" class="" name="grtorSchId">
					             	<option value="">선택</option>
					             </select>
                            </td>
                            
                            <th scope="row"><label for="grtedSchPnm">Granted to<br>DBMS/ROLE</label></th>
                            <td>
                                 <select id="grtedDbId" class="" name="grtedDbId">
					             	<option value="">선택</option>
					             </select>
					             <select id="grtedSchId" class="" name="grtedSchId">
					             	<option value="">선택</option>
					             </select>
                            </td>
						</tr>
						<tr>
							<th scope="row"><label for="ddlObjPnm">오브젝트명</label></th> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlObjPnm" id="ddlObjPnm" value="${search.ddlObjPnm}"/>
                                </span>
                            </td>
                           	<th scope="row"><label for="ddlObjTypCd">오브젝트구분</label></th>
							<td colspan="3">
							<select id="ddlObjTypCd" class="wd100" name="ddlObjTypCd">
<%-- 								    <option value=""><s:message code="COMBO.ALL" /></option> <!-- 전체 --> --%>
								    <c:forEach var="code" items="${codeMap.objDcd}" varStatus="status">
								    <c:if test="${code.codeCd == 'TBL'}" >
							    		<option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:if>
								    </c:forEach>
								</select>
							</td>
                   		</tr>
						
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 <span style="font-weight:bold; color:#444444;">Ctrl + C</span>를 사용하시면 됩니다.</div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabsGrt" style="display:none;">
	  <ul>
	    <li><a href="#tabs-1">DDL오브젝트 상세정보</a></li>
	    <li><a href="#tabs-2">오브젝트이력</a></li>
	    <li><a href="#tabs-3">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailGrtInfo"></div>
	  </div>

	   <div id="tabs-2">
			<%@include file="ddlgrtchange_dtl.jsp" %>
	  </div>
	  
	  <div id="tabs-3">
 			<div id="detailGrtInfoScript"></div>
	  </div>
	  
	  
	 </div>
</div>
</body>
</html>