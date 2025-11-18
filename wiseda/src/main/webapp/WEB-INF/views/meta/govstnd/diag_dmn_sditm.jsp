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
<title></title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSchByOnwer};
var connTrgSchJsonDmn = ${codeMap.devConnTrgSchByOwner} ; 
var sysareaJson = ${codeMap.sysareaOwner} ;	//시스템영역 코드 리스트 JSON...
var sysareaJson2 =${codeMap.sysareaibs};
var interval = "";
var selected = true;
EnterkeyProcess("Search");

$(document).ready(function() {
	//탭 요소 표시여부
	$("#sditmtab").click(function () { 
		
		if ($("#hidden1").css("display") == "none") { 
			$("#hidden1").css("display", "");
			$("#hidden2").css("display", "");
			$("#colspan").attr("colspan",0);
			//$("#hidden3").css("display", "");
			//$("#hidden4").css("display", "");
			selected = true;
		} 
		//alert(selected);
	}); 
	$("#dmntab").click(function () { 
		
		if ($("#hidden1").css("display") != "none") { 
			$("#hidden1").css("display", "none");
			$("#hidden2").css("display", "none");
			$("#colspan").attr("colspan",3);
			//$("#hidden3").css("display", "none");
			//$("#hidden4").css("display", "none"); 
			selected = false;
		}
		//alert(selected);
	});  	
	create_selectbox(sysareaJson, $("#sysAreaId"));
	
	
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ 
        	if (selected) doAction("Search");  
        	else doAction("SearchDmn"); 
        }).show();
        
    	//추가 Event Bind
//     	$("#btnTreeNew").show();
    	
        // 추가 Event Bind
//         $("#btnNew").click(function(){ 
//         	if (selected) doAction("New");  
//         	else doAction("NewDmn");   
//         }).show();
        
        // 저장 Event Bind
        $("#btnSave").click(function(){ 
//         	//저장할래요? 확인창...
//         	var message = "<s:message code="CNF.SAVE" />";
//         	if (selected) {
//         		var rowsSditm = grid_sheet.IsDataModified();
//             	if(!rowsSditm) {
//             		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
//             		return;
//             	} 
//         		//alert("sditm message window " + selected);
//         		showMsgBox("CNF", message, 'Save');
//         	} else {
//             	var rows = grid_sheet_Dmn.IsDataModified();
//             	if(!rows) {
//             		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
//             		return;
//             	}   
//             	//alert("dmn message window " + selected);
//             	showMsgBox("CNF", message, 'SaveDmn');		           		
//         	}
        }).hide();
        
        // 삭제 Event Bind
    	$("#btnDelete").click( function(){ 
// //alert("delbtn : " + selected);
//     		if (selected){ 
//     			//alert("btn_delete : sditmDelete" + selected);
//     			if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {  				
//         			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//         			//doAction("Delete");  
//     			}
//     		}
//         	else {
//         		//alert("btn_delete : dmnDelete" + selected);
//         		if(checkDelIBS (grid_sheet_Dmn, "<s:message code="ERR.CHKDEL" />")) {
//         			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteDmn');
//         			//doActionDmn("Delete");
//             	}
//         	}
    	}).hide();
  
        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ 
        	if (selected) doAction("LoadExcel");  
        	else doAction("LoadExcelDmn");  
        }).show();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ 
        	if (selected) doAction("Down2Excel");  
        	else doAction("Down2ExcelDmn");
        }).show();
        
        $("#btnCreStnd").click( function(){ 
        	doAction("CreStnd");  
        }).show();
        
    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
 //////////////////////////////////////////////////////////////////////////////
        
        var bscLvl = parseInt("${bscLvl}");
        var selectBoxId = "${selectBoxId}";
        var firstSelectBoxId = selectBoxId.split("|");
        
        
     	//divID,  selectbox건수, selectbox ID
        create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", "<s:message code='WHL' />"); //전체
        
         $("#stndAsrt").change(function() {
//         	$("#"+firstSelectBoxId[0] +" option:eq(0)").attr("selected", "selected");
        	double_selectStndAsrt(dmnginfotpJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
    	      $('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
    	    	  double_selectStndAsrt(dmnginfotpJson, $(this),$("#stndAsrt option:selected").val());
    	      });
        });

    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #dmnLnm"), "DMN");
    	
    	//기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});


     	$("#btnExcelDownSub").hide();
    	
    	double_select(connTrgSchJsonDmn, $("#frmSearch #dbConnTrgId"));
 	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
 	   		double_select(connTrgSchJsonDmn, $(this));
 	   	});
 	   	
	
});

function getInfoSys(){
	
	var ajaxUrl = "<c:url value="/meta/govstnd/getInfoSys.do" />";
	
	$.ajax({
		url: ajaxUrl,
		async: false,
		type: "POST",
		contentType: 'application/json',
		dataType: 'json',
		
		success: function(jsonData){
			
			$("#frmSearch #infoSysCd").find("option").remove().end();
			
			$("#frmSearch #infoSysCd").append("<option value=\"\"><s:message code='WHL' /></option>");
			
			$.each(jsonData, function(index,item){
				var infoSysCd = item.INFO_SYS_CD;
				var infoSysNm = item.INFO_SYS_NM;
				
// 				console.log(infoSysNm);
				
				$("#frmSearch #infoSysCd").append("<option value="+ infoSysCd +">"+ infoSysNm +"</option>");
			});
		},
		error: function (jqXHR, textStatus, errorThrown) {
			
		}
	});
}

function doAction(sAction)
{
        
    switch(sAction)
    {
    	case "New" :
    		
    		grid_sheet.DataInsert(0);
    		grid_sheet.SetCellValue(1, "excYn", "N");
    		
    		break;
    		
		case "Search" :
			
			if($("#dbConnTrgId").val() == "" || $("#dbSchId").val() == ""){ //DBMS/스키마명 조회조건 필수처리.
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
			}	
			
			var url = '<c:url value="/meta/govstnd/getdiagSditmList.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet.DoSearch(url, param);
			
			break;
			
        case "Down2Excel" :  //엑셀내려받기
		    //보여지는 컬럼들만 엑셀 다운로드          
		    var downColNms = "";
		      
	     	for(var i=0; i<=grid_sheet.LastCol();i++ ){
	     		if(grid_sheet.GetColHidden(i) != 1){
	     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	     		}
	     	}
	     	
            //grid_sheet.Down2Excel({HiddenColumn:1,DownCols:downColNms, Merge:1});
            
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단표준용어조회'});
            
            break;
            
        case "LoadExcel" :  //엑셀업로
        	
            grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:0});
        
            break;
            
        case "Save" :
        	
        	var saveJsonSditm = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(saveJsonSditm.Code == "IBS010") {
        		showMsgBox("INF", "<s:message code="REQUIRED.INPT.ITEM" />");
        		return;
        	}
        	
        	if(saveJsonSditm.data.length == 0) {
        		saveJsonSditm = grid_sheet.GetSaveJson(0);
        	}
        	
        	var url = '<c:url value="/meta/govstnd/addDiagSditmList.do" />';
        	var param = '';
        	
        	
        	IBSpostJson2(url, saveJsonSditm, param, ibscallback);
        	
        	break;
        	
        case "Delete" :
        	
        	var url ='<c:url value="/meta/govstnd/deleteDiagSditmList.do" />';
        	var param = "";
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	
        	break;
        	
 /////////////////////////////////////////////////////////////////////////////////////    
 
        case "NewDmn":        //추가
	    	//첫행에 추가...
	    	grid_sheet_Dmn.DataInsert(0);
	    	grid_sheet_Dmn.SetCellValue(1, "excYn", "N");
	        break;
    	
        case "DeleteDmn" :
        	//alert("doAction : dmnDelete");
        	var urlDmn = '<c:url value="/meta/govstnd/delDiagDmnList.do"/>';
        	var paramDmn = "";
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	        delCheckIBS(grid_sheet_Dmn);
         	     	
	    	var DelJsonDmn = grid_sheet_Dmn.GetSaveJson(0, "ibsCheck");
	    	
        	if(DelJsonDmn.data.length == 0) {return;}
      		
        	IBSpostJson2(urlDmn, DelJsonDmn, paramDmn, ibscallback);
        
        	break;
        	
        	
        case "SearchDmn":
        	
        	if($("#dbConnTrgId").val() == "" || $("#dbSchId").val() == ""){ //DBMS/스키마명 조회조건 필수처리.
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
			}
        	
			initDtlGrids();
			var paramDmn = $("#frmSearch").serialize();
			grid_sheet_Dmn.DoSearch("<c:url value="/meta/govstnd/getDiagDmnList.do" />", paramDmn);
			
			
        	break;
        	
        case "SaveDmn" :
           	
        	//TODO 공통으로 처리...
        	var saveJson = grid_sheet_Dmn.GetSaveJson(0, "ibsCheck"); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
			
        	if(saveJson.Code == "IBS010") {
        		showMsgBox("INF", "<s:message code="REQUIRED.INPT.ITEM" />");
        		return;
        		}
        	//데이터 사이즈 확인...
        	if(saveJson.data.length == 0) {
        		saveJson = grid_sheet_Dmn.GetSaveJson(0);
        		//alert("length " + saveJson.data.length);
        	}
 
        	var urlDmn = "<c:url value="/meta/govstnd/save_diag_dmn_lst.do"/>";       	
         	var param = "";
         	 
            IBSpostJson2(urlDmn, saveJson, param, ibscallback);
        	break;	
        	  
        case "Down2ExcelDmn":  //엑셀내려받기
        
            grid_sheet_Dmn.Down2Excel({HiddenColumn:1, Merge:1, FileName:'도메인조회'});
            
            break;
            
        case "LoadExcelDmn":  //엑셀업로드
        	
            grid_sheet_Dmn.LoadExcel({Mode:'HeaderMatch'});
//             grid_sheet_Dmn.SetCellValue(row, "excYn", "Y");
            break;
            
        case "CreStnd":  //표준용어/도메인 자동생성
        	
	        if($("#dbConnTrgId").val() == "" || $("#dbSchId").val() == ""){ //DBMS/스키마명 조회조건 필수처리.
				
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
			}
        
        	var urlDmn = "<c:url value="/meta/govstnd/autoCreStnd.do"/>";       	
        	var param = $("#frmSearch").serialize();
//          	alert(param);
            IBSpostJson2(urlDmn, saveJson, param, ibscallback);
            
            break;
    }       
}


function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			doAction("SearchDmn");
			doAction("Search");
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("SearchDmn");
			doAction("Search");		
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("SearchDmn");
			doAction("Search");		
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

</script>
</head>

<body>

<!-- 검색조건 입력폼 -->
<div id="search_div">
<div class="stit"><s:message code="INQ.COND2" /></div> <!-- 조회 목록 -->

        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                     
                   		<tr>
<%--                    			<th scope="row"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 --> --%>
<!-- 								<td> -->
<%-- 								<select id="sysAreaId" class="" name="sysAreaId"> --%>
<%-- 									<option value=""><s:message code="WHL" /></option> <!-- 전체 --> --%>
<%-- 								</select> --%>
<!-- 								</td> -->
							<th scope="row" class ="th_require"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
							<td id="colspan">
								<select class = "wd100" id="dbConnTrgId" name="dbConnTrgId">
									<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select class = "wd100" id="dbSchId" name="dbSchId">
					            	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					        </td>
                   		    <th id="hidden1" style="display:'';" scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS.NM" /></label></th> <!-- 표준용어명 -->
							<td id="hidden2" style="display:'';"><input type="text" id="sditmLnm" name="sditmLnm" class="wd200"/></td>
						</tr>
						<tr>
							<th scope="row"><label for="dmnNm"><s:message code="DMN.NM.ADD.2" /></label></th><!-- 도메인명 -->
							<td>
								<input type="text" id="dmnNm" name="dmnNm" class="wd200" />
							</td>							
							<th scope="row"><label for="dataType">데이터타입</label></th> <!-- 데이터타입 -->
							<td><input type="text" id="dataType" name="dataType" class="wd200"/></td>
							<!-- 검증결과 -->
							<!-- 
							<th id="hidden3" style="display:'';" scope="row"><label for="">검증결과</label></th> 
							<td id="hidden4" style="display:'';">
								<select class="wd100" id="vrfRmk" name="vrfRmk">
									<option value=""><s:message code="CHC" /></option> 
									<option value="정상">정상</option>
									<option value="도메인미존재">도메인미존재</option>
									<option value="표준용어명중복">표준용어명중복</option>
					            </select>
					             -->
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>

<div id="layer_div" >

<div style="clear:both; height:5px;"><span></span></div>
 
 <div id="tabs">
	<ul>
		<li><a href="#tabs-1" id="sditmtab">표준용어</a></li> <!-- 표준용어 상세정보 -->
		<li><a href="#tabs-2" id="dmntab">도메인</a></li> <!-- 표준용어 상세정보 -->
	</ul>

	<div id="tabs-1" value="sditm"> 
		<%@include file="../../meta/govstnd/diag_sditm_lst.jsp" %> 
	</div>
	<div id="tabs-2" value="dmn"> 
		<%@include file="../../meta/govstnd/diag_dmn_lst.jsp" %> 
	</div>

 </div>
 
</div>
</body>
</html>