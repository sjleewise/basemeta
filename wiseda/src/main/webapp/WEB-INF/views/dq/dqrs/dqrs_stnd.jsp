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

var connTrgSchJson = ${codeMap.connTrgSch};
var connTrgSchJsonDmn = ${codeMap.devConnTrgSch} ;
var pubSditmConnTrgJson =${codeMap.pubSditmConnTrgId};
var pubDmnConnTrgJson = ${codeMap.pubDmnConnTrgId};
var allDbms = ${codeMap.allDbms};
var allDbmsSchId = ${codeMap.allDbmsSchId};

var interval = "";
var selected = "sditm";
EnterkeyProcess("Search");

$(document).ready(function() {
	
	$("#vrfRmk option[value='4']").css("display", "none");
	for(i=0; i<allDbms.length; i++) {
			$("#allDbms").append('<option value="' + allDbms[i].codeCd + '">' + allDbms[i].codeLnm + '</option>');
	}
	
		$("#allDbms").change(function() {
			$("#allDbmsSchId").find("option").remove().end();
			var val = $("#allDbms option:selected").val();

			$("#allDbmsSchId").append('<option value=""><s:message code="CHC" /></option> ');
			
			for(i=0; i<allDbmsSchId.length; i++) {
				if(allDbmsSchId[i].upcodeCd == val && val!="") {
					$("#allDbmsSchId").append('<option value="' + allDbmsSchId[i].codeCd + '">' + allDbmsSchId[i].codeLnm + '</option>');
				}
			}
		});	
	
	//탭 요소 표시여부
	$("#sditmtab").click(function () { 
			$("#batchDelDiv").css("display", "none"); 
			$("#hidden1").css("display", "");
			$("#hidden2").css("display", "");
			$("#colspan").attr("colspan",0);
			$("#dbSchId").css("display", "");
			$("#hidden3").css("display", "");
			$("#hidden4").css("display", "");
			$("#vrfRmk option[value='2']").css("display", "");
			$("#vrfRmk option[value='3']").css("display", "");
			$("#vrfRmk option[value='4']").css("display", "none");
			$("#vrfRmk option[value='5']").css("display", "");

		//======================================================
	    // 셀렉트 박스 초기화
	    //======================================================
	 	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	 	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	 		double_select(connTrgSchJson, $(this));
	 	});
	 		
		$("label[for='dbConnTrgId']").text('<s:message code="DB.MS.SCHEMA.NM" />');
		selected = "sditm";
	}); 
	$("#dmntab").click(function () { 
			$("#batchDelDiv").css("display", "none"); 
			$("#hidden1").css("display", "none");
			$("#hidden2").css("display", "none");
			$("#colspan").attr("colspan",0);
			$("#dbSchId").css("display", "");
			$("#hidden3").css("display", "");
			$("#hidden4").css("display", ""); 
			$("#vrfRmk option").prop("selected", false);
			$("#vrfRmk option[value='2']").css("display", "none");
			$("#vrfRmk option[value='3']").css("display", "none");
			$("#vrfRmk option[value='4']").css("display", "");
			$("#vrfRmk option[value='5']").css("display", "");
			
			//======================================================
		    // 셀렉트 박스 초기화
		    //======================================================
		 	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
		 	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		 		double_select(connTrgSchJson, $(this));
		 	});
		 	
		$("label[for='dbConnTrgId']").text('<s:message code="DB.MS.SCHEMA.NM" />');
		selected = "dmn";
	});  
	$("#pubDmntab").click(function () {
		
		$("#batchDelDiv").css("display", "none"); 
		$("#hidden1").css("display", "none");
		$("#hidden2").css("display", "none");
		$("#colspan").attr("colspan",0);
		$("#dbSchId").css("display", "none");
		$("#dbSchId").css("display", "none");
		$("#hidden3").css("display", "");
		$("#hidden4").css("display", ""); 
		$("#vrfRmk option").prop("selected", false);
		$("#vrfRmk option[value='2']").css("display", "none");
		$("#vrfRmk option[value='3']").css("display", "none");
		$("#vrfRmk option[value='4']").css("display", "");
		$("#vrfRmk option[value='5']").css("display", "none");
		$("label[for='dbConnTrgId']").text('표준분류명');
		
		//셀렉트박스 설정
		$("#frmSearch #dbConnTrgId").find("option").remove().end();
 		var trgId = pubDmnConnTrgJson ;
 		$("#frmSearch #dbConnTrgId").append('<option value="">선택</option>');
 		
 		for(i=0; i<trgId.length; i++) {
 			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
 		}
 		
			selected = "pubDmn";
		});
	$("#pubSditmtab").click(function () { 
		
		$("#batchDelDiv").css("display", "none"); 
		$("#hidden1").css("display", "");
		$("#hidden2").css("display", "");
		$("#colspan").attr("colspan",0);
		$("#dbSchId").css("display", "none");
		$("#hidden3").css("display", "");
		$("#hidden4").css("display", ""); 
		$("#vrfRmk option").prop("selected", false);
		$("#vrfRmk option[value='2']").css("display", "");
		$("#vrfRmk option[value='3']").css("display", "");
		$("#vrfRmk option[value='4']").css("display", "none");
		$("#vrfRmk option[value='5']").css("display", "none");
		$("label[for='dbConnTrgId']").text('표준분류명');
		
		//셀렉트박스 설정
		$("#frmSearch #dbConnTrgId").find("option").remove().end();
 		var trgId = pubSditmConnTrgJson ;
 		$("#frmSearch #dbConnTrgId").append('<option value="">선택</option>');
 		
 		for(i=0; i<trgId.length; i++) {
 			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
 		}
		
			
		selected = "pubSditm";
	});
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){
        	if (selected == "sditm") doAction("Search");  
        	else if (selected == "dmn") doAction("SearchDmn");
        	else if (selected == "pubSditm") doAction("SearchPubSditm");
        	else if (selected == "pubDmn") doAction("SearchPubDmn");
        }).show();
        
//     	추가 Event Bind
    	$("#btnNew").hide();
        
        // 저장 Event Bind
        $("#btnSave").click(function(){ 
        	//저장할래요? 확인창...
        	var message = "<s:message code="CNF.SAVE" />";
        	if (selected == "sditm") {
        		var rowsSditm = grid_sheet.IsDataModified();
            	if(!rowsSditm) {
            		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
            		return;
            	} 

        		showMsgBox("CNF", message, 'Save');
        	} else if(selected == "dmn"){
            	var rows = grid_sheet_Dmn.IsDataModified();
            	if(!rows) {
            		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
            		return;
            	}   

            	showMsgBox("CNF", message, 'SaveDmn');		           		
        	} else if(selected == "pubSditm"){
            	var rows = grid_sheet_pubSditm.IsDataModified();
            	if(!rows) {
            		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
            		return;
            	}   

            	showMsgBox("CNF", message, 'SavePubSditm');		           		
        	} else if(selected == "pubDmn"){
            	var rows = grid_sheet_pubDmn.IsDataModified();
            	if(!rows) {
            		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
            		return;
            	}   

            	showMsgBox("CNF", message, 'SavePubDmn');		           		
        	}
        }).show();
        
        // 삭제 Event Bind
    	$("#btnDelete").click( function(){ 

    		if (selected == "sditm"){ 
    			if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {  				
        			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
    			}
    		}
        	else if(selected == "dmn"){
        		if(checkDelIBS (grid_sheet_Dmn, "<s:message code="ERR.CHKDEL" />")) {
        			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteDmn');
            	}
        	}
        	else if(selected == "pubSditm"){
        		if(checkDelIBS (grid_sheet_pubSditm, "<s:message code="ERR.CHKDEL" />")) {
        			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeletePubSditm');
            	}
        	}
        	else if(selected == "pubDmn"){
        		if(checkDelIBS (grid_sheet_pubDmn, "<s:message code="ERR.CHKDEL" />")) {
        			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeletePubDmn');
            	}
        	}
    	}).show();
  
        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ 
        	if (selected == "sditm") doAction("LoadExcel");  
        	else if (selected == "dmn") doAction("LoadExcelDmn");
        	else if (selected == "pubSditm") doAction("LoadExcelPubSditm");  
        	else if (selected == "pubDmn") doAction("LoadExcelPubDmn");
        }).show();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ 
        	if (selected == "sditm") doAction("Down2Excel");  
        	else if (selected == "dmn") doAction("Down2ExcelDmn");
        	else if (selected == "pubSditm") doAction("Down2ExcelPubSditm");  
        	else if (selected == "pubDmn") doAction("Down2ExcelPubDmn");
        }).show();
        
        $("#btnCreStnd").click( function(){ 
        	doAction("CreStnd");  
        }).show();
        
        $("#batchDelete").click( function(){ 
        	doAction("BatchDelete");  
        }).show();
 	   	
        if(selected == "sditm" || selected == "dmn"){
     		//임시 메뉴목록 등장 함수
     		var val = $("#dbConnTrgId option:selected").val();
     		var trgId = connTrgSchJson ;
     		
     		for(i=0; i<trgId.length; i++) {
     			if((val != null && val != "" && val != 'undefined' && trgId[i].upcodeCd == val)
     					|| (val == null || val == "" || val == 'undefined')) {
     				$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
     			}
     		}
     		
     		
     		$("#frmSearch #dbConnTrgId").change(function() {
     			$("#frmSearch #dbSchId").find("option").remove().end();
     			var val = $("#dbConnTrgId option:selected").val();

     			$("#frmSearch #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
     			
     			for(i=0; i<trgId.length; i++) {
     				if(trgId[i].upcodeCd == val && val!="") {
     					$("#frmSearch #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
     				}
     			}
     		});	
     	} else if(selected == "pubSditm"){
     		//공통인 경우 공통코드
     		$("#frmSearch #dbConnTrgId").find("option").remove().end();
     		var trgId = pubSditmConnTrgJson ;
     		$("#frmSearch #dbConnTrgId").append('<option value="">선택</option>');
     		
     		for(i=0; i<trgId.length; i++) {
     			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
     		}
     	} else if(selected == "pubDmn"){
     		//공통인 경우 공통코드
     		$("#frmSearch #dbConnTrgId").find("option").remove().end();
     		var trgId = pubDmnConnTrgJson ;
     		$("#frmSearch #dbConnTrgId").append('<option value="">선택</option>');
     		
     		for(i=0; i<trgId.length; i++) {
     			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
     		}
     	}
        
        var bscLvl = parseInt("${bscLvl}");
        var selectBoxId = "${selectBoxId}";
        var firstSelectBoxId = selectBoxId.split("|");
        
        
     	//divID,  selectbox건수, selectbox ID
        create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", "<s:message code='WHL' />"); //전체
        
         $("#stndAsrt").change(function() {
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

    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});


     	$("#btnExcelDownSub").hide();
    	
     	$("#batchDelDiv").hide();
     	
     	$("#sditmtab").trigger("click");
});

function getInfoSys(){
	
	var ajaxUrl = "<c:url value="/meta/dqrs/stnd/getInfoSys.do" />";
	
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
			
			var url = '<c:url value="/dq/dqrs/getDqrsSditmLst.do" />';
			var param = $('#frmSearch').serialize();
			
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
        	
        	var url = '<c:url value="/dq/dqrs/saveDqrsSditmLst.do" />';
        	var param = $('#frmSearch').serialize();
        	IBSpostJson2(url, saveJsonSditm, param, ibscallback);
        	
        	break;
        	
        case "Delete" :
        	
        	var url ='<c:url value="/dq/dqrs/delDqrsSditmLst.do" />';
        	var param = "";
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson({AllSave:0, stdCol:"ibsCheck", ValidKeyField:0});
        	
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
        	var urlDmn = '<c:url value="/dq/dqrs/delDqrsDmnLst.do"/>';
        	var paramDmn = "";
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	        delCheckIBS(grid_sheet_Dmn);
         	
	    	var DelJsonDmn = grid_sheet_Dmn.GetSaveJson({AllSave:0, stdCol:"ibsCheck", ValidKeyField:0});
	    	
        	if(DelJsonDmn.data.length == 0) {return;}
      		
        	IBSpostJson2(urlDmn, DelJsonDmn, paramDmn, ibscallback);
        
        	break;
        	
        	
        case "SearchDmn":
        	
			initDtlGrids();
			var paramDmn = $("#frmSearch").serialize();
			grid_sheet_Dmn.DoSearch("<c:url value="/dq/dqrs/getDqrsDmnLst.do" />", paramDmn);
			
			
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
        	}
        	
        	var urlDmn = "<c:url value="/dq/dqrs/saveDqrsDmnLst.do"/>";       	
//         	var param = $('#frmSearch').serialize();

            IBSpostJson2(urlDmn, saveJson, param, ibscallback);
        	break;	
        	  
        case "Down2ExcelDmn":  //엑셀내려받기
        
            grid_sheet_Dmn.Down2Excel({HiddenColumn:1, Merge:1, FileName:'도메인조회'});
            
            break;
            
        case "LoadExcelDmn":  //엑셀업로드
        	
            grid_sheet_Dmn.LoadExcel({Mode:'HeaderMatch'});
            break;
            
        case "CreStnd":  //표준용어/도메인 자동생성
        
        	var urlDmn = "<c:url value="/dq/govstnd/autoCreStnd.do"/>";       	
        	var param = $("#frmSearch").serialize();
        	
            IBSpostJson2(urlDmn, saveJson, param, ibscallback);
            
            break;
		/////////////////////////////////////////////////////////////////////////////////////공통표준용어
		case "NewPubSditm" :
    		
			grid_sheet_pubSditm.DataInsert(0);
			grid_sheet_pubSditm.SetCellValue(1, "excYn", "N");
    		
    		break;
    		
		case "SearchPubSditm" :
			
			var url = '<c:url value="/dq/dqrs/getDqrsPubSditmLst.do" />';
			var param = $('#frmSearch').serialize();
			
			grid_sheet_pubSditm.DoSearch(url, param);
			
			break;
			
        case "Down2ExcelPubSditm" :  //엑셀내려받기
		    //보여지는 컬럼들만 엑셀 다운로드          
		    var downColNms = "";
		      
	     	for(var i=0; i<=grid_sheet_pubSditm.LastCol();i++ ){
	     		if(grid_sheet_pubSditm.GetColHidden(i) != 1){
	     			downColNms += grid_sheet_pubSditm.ColSaveName(0,i)+ "|";
	     		}
	     	}
            
            grid_sheet_pubSditm.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단표준용어조회'});
            
            break;
            
        case "LoadExcelPubSditm" :  //엑셀업로
        	
        	grid_sheet_pubSditm.LoadExcel({Mode:'HeaderMatch', Append:0});
        
            break;
            
        case "SavePubSditm" :
        	
        	var saveJsonPubSditm = grid_sheet_pubSditm.GetSaveJson(0, "ibsCheck");
        	
        	if(saveJsonPubSditm.Code == "IBS010") {
        		showMsgBox("INF", "<s:message code="REQUIRED.INPT.ITEM" />");
        		return;
        	}
        	
        	if(saveJsonPubSditm.data.length == 0) {
        		saveJsonPubSditm = grid_sheet_pubSditm.GetSaveJson(0);
        	}
        	
        	var pubSditmUrl ='<c:url value="/dq/dqrs/saveDqrsSditmLst.do" />';	
         	var param = '';
//        	var param = $('#frmSearch').serialize();

        	IBSpostJson2(pubSditmUrl, saveJsonPubSditm, param, ibscallback);
        	
        	break;
        	
        case "DeletePubSditm" :
        	
        	var url ='<c:url value="/dq/dqrs/delDqrsSditmLst.do" />';
        	var param = "";
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet_pubSditm);
        	
        	var DelJsonPubSditm = grid_sheet_pubSditm.GetSaveJson({AllSave:0, stdCol:"ibsCheck", ValidKeyField:0});
        	
        	if(DelJsonPubSditm.data.length == 0) return;
        	
        	IBSpostJson2(url, DelJsonPubSditm, param, ibscallback);
        	
        	break;
        	
 	/////////////////////////////////////////////////////////////////////////////////////  공통 도메인
 	
 		case "NewPubDmn" :
    		
			grid_sheet_pubDmn.DataInsert(0);
			grid_sheet_pubDmn.SetCellValue(1, "excYn", "N");
    		
    		break;
    		
		case "SearchPubDmn" :

			var url = '<c:url value="/dq/dqrs/getDqrsPubDmnLst.do" />';
			var param = $('#frmSearch').serialize();
			
			grid_sheet_pubDmn.DoSearch(url, param);
			
			break;
			
        case "Down2ExcelPubDmn" :  //엑셀내려받기
		    //보여지는 컬럼들만 엑셀 다운로드          
		    var downColNms = "";
		      
	     	for(var i=0; i<=grid_sheet_pubDmn.LastCol();i++ ){
	     		if(grid_sheet_pubDmn.GetColHidden(i) != 1){
	     			downColNms += grid_sheet_pubDmn.ColSaveName(0,i)+ "|";
	     		}
	     	}
	     	
            
	     	grid_sheet_pubDmn.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단도메인조회'});
            
            break;
            
        case "LoadExcelPubDmn" :  //엑셀업로
        	
        	grid_sheet_pubDmn.LoadExcel({Mode:'HeaderMatch', Append:0});
        
            break;
            
        case "SavePubDmn" :
        	
        	var saveJsonPubDmn = grid_sheet_pubDmn.GetSaveJson(0, "ibsCheck");
        	
        	if(saveJsonPubDmn.Code == "IBS010") {
        		showMsgBox("INF", "<s:message code="REQUIRED.INPT.ITEM" />");
        		return;
        	}
        	
        	if(saveJsonPubDmn.data.length == 0) {
        		saveJsonPubDmn = grid_sheet_pubDmn.GetSaveJson(0);
        	}

        	var pubDmnUrl ="<c:url value="/dq/dqrs/saveDqrsDmnLst.do"/>";   	
         	var param = '';
//        	var param = $('#frmSearch').serialize();

        	IBSpostJson2(pubDmnUrl, saveJsonPubDmn, param, ibscallback);
        	
        	break;
        	
        case "DeletePubDmn" :
        	
        	var pubDmnUrl ='<c:url value="/dq/dqrs/delDqrsDmnLst.do"/>';
        	var param = "";
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet_pubDmn);
        	
        	var DelJsonPubDmn = grid_sheet_pubDmn.GetSaveJson({AllSave:0, stdCol:"ibsCheck", ValidKeyField:0});
        	
        	if(DelJsonPubDmn.data.length == 0) return;
        	
        	IBSpostJson2(pubDmnUrl, DelJsonPubDmn, param, ibscallback);
        	
        	break;
        case "BatchDelete"	:
        	
        	var dbId  = $("#allDbms option:selected").val();
        	var schId = $("#allDbmsSchId option:selected").val();
        	if(dbId=='' || schId ==''){
        		showMsgBox("ERR", "DBMS/스키마명을 선택 해 주세요.");
        		return;
        	}
        	
        	var url ='<c:url value="/dq/dqrs/batchDelDqrsSditmLst.do"/>';
        	 var json = {
        			"allDbms" 	   : dbId,
        			"allDbmsSchId" : schId,
        			"delType"      : "dmnSditm"
        	}; 
        	IBSpostJson2(url, json,"", ibscallback);
        	break;
    }       
}


function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			doAction("SearchDmn");
			doAction("SearchPubDmn");
			doAction("Search");
			doAction("SearchPubSditm");
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("SearchDmn");
			doAction("SearchPubDmn");
			doAction("Search");
			doAction("SearchPubSditm");
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("SearchDmn");
			doAction("SearchPubDmn");
			doAction("Search");		
			doAction("SearchPubSditm");
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
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                     
                   		<tr>
							<th scope="row" class ="th"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
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
							<th scope="row"><label for="dmnNm"><s:message code="DMN.NM.ADD.2" /></label></th><!-- 도메인명 -->
							<td colspan="3">
								<input type="text" id="dmnNm" name="dmnNm" class="wd200" />
							</td>							
						</tr>
						<tr>
							<th scope="row"><label for="dataType">데이터타입</label></th> <!-- 데이터타입 -->
							<td ><input type="text" id="dataType" name="dataType" class="wd200"/></td>
							<!-- 검증결과 -->
							
							<th id="hidden3" style="display:'';" scope="row"><label for="">검증결과</label></th> 
							<td id="hidden4" style="display:'';" colspan="3" >
								<select class="wd200" id="vrfRmk" name="vrfRmk">
									<option id ="" value=""><s:message code="CHC" /></option> 
									<option id ="1" value="1">정상</option>
									<option id ="2" value="2">도메인미존재</option>
									<option id ="3" value="3">표준용어명중복</option>
									<option id ="5" value="4" >도메인명 중복</option>
									<option id ="4" value="5">진단대상DB미존재</option>
					            </select>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            <div style="clear:both; height:5px;"><span></span></div>
          <div class="tb_comment">- 등록시점에 따라 도메인명, 진단대상DB정보 변동으로 검증 결과가 다를 수 있습니다.</div> 
        
        </form>
       
        	
               <div style="clear:both; height:10px;"><span></span></div>
             
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
        <div style="clear:both; height:10px;"><span></span></div>
</div>

<div id="layer_div" >

<div style="clear:both; height:5px;"><span></span></div>
 
 <div id="tabs">
	<ul>
		<li><a href="#tabs-1" id="sditmtab">표준용어</a></li> <!-- 표준용어 상세정보 -->
		<li><a href="#tabs-2" id="dmntab">도메인</a></li> <!-- 표준용어 상세정보 -->
		<li><a href="#tabs-3" id="pubSditmtab">공통표준용어</a></li> <!-- 공통표준용어 상세정보 -->
		<li><a href="#tabs-4" id="pubDmntab">공통도메인</a></li> <!-- 공통도메인 상세정보 -->
	</ul>

	<div id="tabs-1" value="sditm"> 
		<%@include file="../../dq/dqrs/dqrs_sditm_lst.jsp" %> 
	</div>
	<div id="tabs-2" value="dmn"> 
		<%@include file="../../dq/dqrs/dqrs_dmn_lst.jsp" %> 
	</div>
	<div id="tabs-3" value="pubSditm"> 
		<%@include file="../../dq/dqrs/dqrs_pubsditm_lst.jsp" %> 
	</div>
	<div id="tabs-4" value="pubDmn"> 
		<%@include file="../../dq/dqrs/dqrs_pubdmn_lst.jsp" %> 
	</div>

 </div>
 <div style="clear:both; height:5px;"><span></span></div>
</div>
<div style="clear:both; height:550px;"><span></span></div>
        	 <div id="batchDelDiv">
		    <div class="stit">DBMS 일괄삭제</div><!--검색조건-->
            
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
             <fieldset>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->

                   <caption>DBMS 일괄삭제</caption><!--진단대상DBMS-->

                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:80%;" />
                 
                   </colgroup>

							<tbody>
							
								<tr>
									<th scope="row" class ="th"><label for=""><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
									<td id="colspan">
										<select class = "wd100" id="allDbms" name="" style ="width: 170px;">
											<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            		</select>
					            		<select class = "wd100" id="allDbmsSchId" name="">
					            			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					           			 </select>
					           			 <button  class="btn_search" id="batchDelete" name="batchDelete">
									 		일괄삭제
										</button>
					       			 </td>
									
								</tr>

							</tbody>
						</table>   
            </div>
            
            </fieldset>
             </div> 
</body>
</html>