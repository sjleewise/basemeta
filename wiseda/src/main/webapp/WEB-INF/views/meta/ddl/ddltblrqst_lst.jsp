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

var connTrgSchJson = ${codeMap.connTrgSch} ;
var interval = "";
var initTblGrid = false;
var initIdxGrid = false;
var initSeqGrid = false;
var initEtcGrid = false;
var initPartGrid = false;
var initGrtGrid = false;
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
		
		//DDL조회 Event Bind
		$("#btnDDL").click(function(){
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code='MSG.INQ.DDL.CHC' />"); //조회할 DDL을 선택해 주세요.
        		return;
        	}
        	doAction("ShowDdl");
		}).show().text("<s:message code='DDL.INQ'/>"); //DDL 조회
    	
// 		$( "#tabsTbl" ).hide();
// 		$( "#tabsIdx" ).hide();
		
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
	  //기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});

	  loadDetail(null, 'TBL');
	  loadDetail(null, 'IDX');
	  loadDetailCol();
	  loadDetailIdxCol();
});

$(window).on('load',function() {
// 	alert('window.load');
			
	
		
	initGrid();
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	$( "#tabsTbl" ).tabs().show();
	
	//$( "#layer_div" ).show();
	
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.DDLTBLRQST.LST'/>", Align:"Center"}
						/* No.|상태|등록유형|DBMS ID|DBMS명|스키마ID|DB스키마명|오브젝트구분|오브젝트ID|오브젝트명|오브젝트물리명|테이블종류|암호화여부|DDL|처리완료|처리일자|처리DBAID|처리DBA명|요청사항|스크립트정보|테이블변경타입코드 */
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Status", Width:50,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:10,   SaveName:"dbConnTrgId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"dbConnTrgLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:10,   SaveName:"dbSchId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:120,   SaveName:"dbSchLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:80,   SaveName:"objDcd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"objLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"objPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:70,   SaveName:"tblTypCd", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:70,   SaveName:"encYn", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"CheckBox",   Width:70,   SaveName:"ddl",	 	Align:"Center", Edit:1, Hidden:0},
						{Type:"CheckBox",   Width:90,   SaveName:"prcTypCd", 		Align:"Center", Edit:1, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"prcDt", 		Align:"Center", Edit:0, Hidden:0, Format:"YmdHms"},
						{Type:"Text",   Width:10,   SaveName:"prcDbaId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"prcDbaNm", 		Align:"Left", Edit:0, Hidden:0},

						{Type:"Text",   Width:100,   SaveName:"userRqstCntn", 		Align:"Left", Edit:0, Hidden:1},

						{Type:"Text",   Width:100,   SaveName:"scrtInfo", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"tblChgTypCd", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",     Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
						{Type:"Int",      Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Int",      Width:60,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"expDtmStr", 		Align:"Center", Edit:0, Hidden:1, Format:"YmdHms"}
					];
                    
        InitColumns(cols);

		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("objDcd",	${codeMap.objDcdibs});
		SetColProperty("tblTypCd",	${codeMap.tblTypCdibs});
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("pciYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
        // FitColWidth();  
        
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

			grid_sub_ddltblchange.RemoveAll();
			grid_sub_ddltblcollist.RemoveAll();
// 			grid_sub_ddltblidxcol.RemoveAll();
			grid_sub_ddltblcolchange.RemoveAll();
			loadDetail();
			loadDetailCol();
			loadDetailScript();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlRqstlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.w == 0) return;
        	
        	var url = '<c:url value="/meta/ddl/saveDdlTblRqstPrc.do"/>';
        	//alert(JSON.stringify(SaveJson));
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
       
        case "ShowDdl" :
        	var SelectJson = grid_sheet.GetSaveJson({StdCol:"ddl"});

			if (SelectJson.length == 0) {
				showMsgBox("ERR", "<s:message code='MSG.INQ.DDL.CHC' />"); //조회할 DDL을 선택해 주세요.
        		return;
			}
			var objDcd = SelectJson.data[0].objDcd;
			var objId = SelectJson.data[0].objId;
        	for(var i=1; i<SelectJson.data.length; i++) {
        		objDcd += "|" + SelectJson.data[i].objDcd;
        		objId += "|" + SelectJson.data[i].objId;
			}
			
// 			var url = '<c:url value="/meta/ddl/popup/ddlscript_pop.do"/>';
// 			OpenModal(url, "DdlScript", 800, 500);
			
			
			var param = "?scrnDcd=DDLPRC";
				param += "&objDcd="+objDcd;
				param += "&objId="+objId;
			var url = '<c:url value="/meta/ddl/popup/ddlscript_pop.do"/>';
			OpenModal(encodeURI(url + param), "DdlScript", 800, 600);
			
        	break;
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'DDL요청관리'});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
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
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다... (현재는 DDL만)
	
	var param =  grid_sheet.GetRowJson(row);

	if(param.objDcd == "TBL"){
        $( "#tabsIdx" ).hide();
        $( "#tabsSeq" ).hide();
        $( "#tabsEtc" ).hide();
        $( "#tabsPart" ).hide();
        $( "#tabsGrt" ).hide();
		$( "#tabsTbl" ).tabs().show();
        if(!initTblGrid){
           initsubgrid_ddltblcollist();
           initsubgrid_ddltblchange();
//            initsubgrid_ddltblidxcol();
           initsubgrid_ddltblcolchange();
           initTblGrid = true;
        }
        
		var ddlTbl = "&ddlTblId="+grid_sheet.GetCellValue(row, "objId");
		ddlTbl += "&rqstNo="+param.rqstNo;
		
		//메뉴ID를 토대로 조회한다....
		loadDetail(ddlTbl, 'TBL');
		

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="DDL.TBL.NM" /> : ' + param.objLnm; //DDL 테이블명
		$('#ddl_sel_title').html(tmphtml);
		
		
		$("#detailInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
		loadDetailScript(ddlTbl, 'TBL');
	} else if(param.objDcd == "IDX"){
        $( "#tabsTbl" ).hide();
        $( "#tabsSeq" ).hide();
        $( "#tabsEtc" ).hide();
        $( "#tabsPart" ).hide();
        $( "#tabsGrt" ).hide();
		$( "#tabsIdx" ).tabs().show();
        //DDDD
        if(!initIdxGrid){
           initsubgrid_ddlidxcollist();
    	   initsubgrid_ddlidxchange();
    	   initsubgrid_ddlidxcolchange();
    	   initIdxGrid =true;
        }
		var ddlIdx = "&ddlIdxId="+grid_sheet.GetCellValue(row, "objId");
		ddlIdx += "&rqstNo="+param.rqstNo;

		//메뉴ID를 토대로 조회한다....
		loadDetail(ddlIdx, 'IDX');

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="DDL.IDEX.NM" /> : ' + param.objLnm; //DDL 인덱스명
		$('#ddl_sel_title').html(tmphtml);
		
		var param1 = "ddlIdxId="+param.objId;
		param1 += "&regTypCd="+param.regTypCd;
		param1 += "&rqstNo="+param.rqstNo;
		$("#detailIdxInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
		loadDetailScript(param1, 'IDX');
	} else if(param.objDcd == "SEQ"){
        $( "#tabsTbl" ).hide();
        $( "#tabsIdx" ).hide();
        $( "#tabsEtc" ).hide();
        $( "#tabsPart" ).hide();
        $( "#tabsGrt" ).hide();
		$( "#tabsSeq" ).tabs().show();
		if(!initSeqGrid){
	    	   initsubgrid_ddlseqchange();
	    	   initSeqGrid =true;
	        }
			var ddlSeq = "&ddlSeqId="+grid_sheet.GetCellValue(row, "objId");
			ddlSeq += "&rqstNo="+param.rqstNo;
			
			//메뉴ID를 토대로 조회한다....
 			loadDetail(ddlSeq, 'SEQ');

			//선택한 그리드의 row 내용을 보여준다.....
			var tmphtml = 'DDL 시퀀스명 : ' + param.objPnm; //DDL 시퀀스명
			$('#ddl_sel_title').html(tmphtml);
			
			var param1 = "ddlSeqId="+param.objId;
			param1 += "&regTypCd="+param.regTypCd;
			param1 += "&rqstNo="+param.rqstNo;
 			$("#detailSeqInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
			loadDetailScript(param1, 'SEQ');
	} else if(param.objDcd == "ETC"){
        $( "#tabsTbl" ).hide();
        $( "#tabsIdx" ).hide();
        $( "#tabsSeq" ).hide();
        $( "#tabsPart" ).hide();
        $( "#tabsGrt" ).hide();
		$( "#tabsEtc" ).tabs().show();
		if(!initEtcGrid){
	    	   initEtcGrid =true;
	        }
			var ddlEtc = "&ddlEtcId="+grid_sheet.GetCellValue(row, "objId");
			ddlEtc += "&rqstNo="+param.rqstNo;

			//선택한 그리드의 row 내용을 보여준다.....
			var tmphtml = 'DDL 기타오브젝트명 : ' + param.objLnm; //DDL 인덱스명
			$('#ddl_sel_title').html(tmphtml);
			
			//메뉴ID를 토대로 조회한다....
 			loadDetail(ddlEtc, 'ETC');
 			var param1 = "ddlEtcId="+param.objId;
 			param1 += "&regTypCd="+param.regTypCd;
 			param1 += "&rqstNo="+param.rqstNo;
 			$("#detailEtcInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
 			loadDetailScript(param1, 'ETC');
	} else if(param.objDcd == "DDP"){
        $( "#tabsTbl" ).hide();
        $( "#tabsIdx" ).hide();
        $( "#tabsSeq" ).hide();
        $( "#tabsEtc" ).hide();
        $( "#tabsGrt" ).hide();
		$( "#tabsPart" ).tabs().show();
        //DDDD
        if(!initPartGrid){
           initPartMainGrid();
           initPartSubGrid();
           initPartHistGrid();
    	   initPartGrid =true;
        }
		var ddlPart = "&ddlPartId="+grid_sheet.GetCellValue(row, "objId");
		ddlPart += "&rqstNo="+param.rqstNo;

		//메뉴ID를 토대로 조회한다....
		loadDetail(ddlPart, 'DDP');
		
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="DDL.IDEX.NM" /> : ' + param.objLnm; //DDL 인덱스명
		$('#ddl_sel_title').html(tmphtml);
		
		
		var param1 = "ddlPartId="+param.objId;
		param1 += "&regTypCd="+param.regTypCd;
		param1 += "&rqstNo="+param.rqstNo;
		$("#detailPartInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
		loadDetailScript(param1, 'DDP');
	} else if(param.objDcd == "GRT"){
		$( "#tabsTbl" ).hide();
        $( "#tabsIdx" ).hide();
        $( "#tabsSeq" ).hide();
		$( "#tabsEtc" ).hide();
		$( "#tabsPart" ).hide();
        $( "#tabsGrt" ).tabs().show();
       
        if(!initGrtGrid){
    		initGrtGrid =true;
        }

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = 'DDL 오브젝트 : ' + param.objPnm;
		$('#ddl_sel_title').html(tmphtml);
		
		var ddlGrt = "ddlGrtId="+param.objId;
		ddlGrt += "&rqstNo="+param.rqstNo;
		
		//메뉴ID를 토대로 조회한다....
     	loadDetail(ddlGrt, 'GRT');
		
		var param1 = "ddlGrtId="+param.objId;
		param1 += "&regTypCd="+param.regTypCd;
		param1 += "&rqstNo="+param.rqstNo;
		$("#detailGrtInfoScript #frmInputDdlScript #ddlscript").val(param.scrtInfo);
		loadDetailScript(param1, 'GRT');
	}
	
	

}


//상세정보호출
function loadDetail(param, objDcd) {
	if(objDcd == 'TBL') {
		$('div#detailInfo1').load('<c:url value="/meta/ddl/ajaxgrid/ddltblinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
// 				loadDetailScript(null, 'TBL');
				grid_sub_ddltblchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblchange_dtl.do" />', param);
// 				grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />', param);
				grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl_forwah.do" />', param);
// 				grid_sub_ddltblidxcol.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblidxcol_dtl.do" />', param);
				grid_sub_ddltblcolchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcolchange_dtl.do" />', param);
			}
			//$('#tabs').show();
		});
	} else if (objDcd == 'IDX') {
		$('div#detailIdxInfo1').load('<c:url value="/meta/ddl/ajaxgrid/ddlidxinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
// 				loadDetailScript(null, 'IDX');
				grid_sub_ddlidxcollist.DoSearch('<c:url value="/meta/ddl/getDdlIdxCollist.do" />', param);
				grid_sub_ddlidxchange.DoSearch('<c:url value="/meta/ddl/getDdlIdxChange.do" />', param);
				grid_sub_ddlidxcolchange.DoSearch('<c:url value="/meta/ddl/getDdlIdxColChange.do" />', param);
			}
			//$('#tabs').show();
		});
	} else if (objDcd == 'SEQ') {
		$('div#detailSeqInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlseqinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
				grid_sub_ddlseqchange.DoSearch('<c:url value="/meta/ddl/getDdlSeqChange.do" />', param);
			}
		});
	} else if (objDcd == 'ETC') {
		$('div#detailEtcInfo').load('<c:url value="/meta/ddletc/ajaxgrid/ddletcinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
				
			}
			
		});
	} else if (objDcd == 'DDP') {
		$('div#detailPartInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlpartinfo_dtl.do"/>', param, function(){
			if(param == null || param == "" || param =="undefined"){
				
			} else {
				part_main_sheet.DoSearch('<c:url value="/meta/ddl/getddlmainpartlst.do" />', param);
				sub_sheet.DoSearch('<c:url value="/meta/ddl/getddlsubpartlst.do" />', param);
				part_hist_sheet.DoSearch('<c:url value="/meta/ddl/getddlparthistlst.do" />', param);
			}
		});
	} else if (objDcd == 'GRT') {
	      $('div#detailGrtInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlgrtinfo_dtl.do"/>', param, function(){
	         if(param == null || param == "" || param =="undefined"){
	            
	         } else {
	            grid_sub_ddlgrtchange.DoSearch('<c:url value="/meta/ddl/getDdlGrtChange.do" />', param);
	         }
	      });
	   }
}

//상세정보호출
function loadDetailScript(param, objDcd) {
	
	if(objDcd == 'TBL'){
		$('div#detailInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){
		});
	} else if(objDcd == 'IDX'){
		$('div#detailIdxInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
	} else if(objDcd == 'SEQ'){
		$('div#detailSetInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
	} else if(objDcd == 'ETC'){
		$('div#detailEtcInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
	} else if(objDcd == 'DDP'){
		$('div#detailPartInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
	} else if(objDcd == 'GRT'){
		$('div#detailGrtInfoScript').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_dtl.do"/>', param, function(){});
	}
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
	    <div class="menu_title"><s:message code="DDL.TBL.DEMD.MNG" /></div> <!-- DDL테이블 요청관리 -->
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
							<th scope="row"><label for="objLnm"><s:message code="OBJ.NM" /></label></th> <!-- 오브젝트명 -->
							<td><input type="text" id="objLnm" name="objLnm" class="wd200" /></td>
							<th scope="row"><label for="objDcd"><s:message code="OBJ.DSTC" /></label></th> <!-- 오브젝트구분 -->
							<td>
							<select id="objDcd" class="wd100" name="objDcd">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.objDcd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
							</td>
							
						</tr>
						<tr>
							<th scope="row"><label for="dbSchId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
							<td><select id="dbConnTrgId" class="wd100" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select id="dbSchId" class="wd100" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select></td>
							<th scope="row"><label for="prcTypCd"><s:message code="TRTT.PTRN" /></label></th> <!-- 처리유형 -->
							<td><select id="prcTypCd" class="wd100" name="prcTypCd">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.prcTypCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select></td>
							
						</tr>
						<tr>
							<th scope="row"><label for="tblTypCd"><s:message code="TBL.KIND" /></label></th> <!-- 테이블종류 -->
							<td><select id="tblTypCd" class="wd100" name="tblTypCd">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.tblTypCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select></td>
							<th scope="row"><label for="regTypCd"><s:message code="OBJ.REG.PTRN" /></label></th> <!-- 오브젝트등록유형 -->
							<td><select id="regTypCd" class="wd100" name="regTypCd">
								    <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								    <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select></td>
							
						</tr>
						<tr>
                                         <th><s:message code="TRTT.DT" /></th> <!-- 처리일자 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd100" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd100" /></td>
                                         <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                         	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                         	<a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
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
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabsTbl" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DDL.TBL.DTL.INFO" /></a></li> <!-- DDL테이블 상세정보 -->
	    <li><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
<%-- 	    <li><a href="#tabs-6"><s:message code="IDEX.CLMN" /></a></li> <!-- 인덱스컬럼 --> --%>
	    <li><a href="#tabs-3" id="colinfo"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 -->
	    <li><a href="#tabs-5"><s:message code="TBL.HSTR" /></a></li> <!-- 테이블 이력 -->
	    <li><a href="#tabs-4"><s:message code="CLMN.HSTR" /></a></li> <!-- 컬럼이력 -->
 	    <li><a href="#tabs-7">DDL</a></li>
<!-- 	    <li><a href="#tabs-7">부가정보</a></li> -->
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddltblcollist_dtl.jsp" %>
	  </div>
<!-- 	  <div id="tabs-6"> -->
<%-- 			<%@include file="ddltblidxcol_dtl.jsp" %> --%>
<!-- 	  </div> -->
	  <div id="tabs-3">
 			<div id="detailInfo2"></div>
	  </div>
	   <div id="tabs-5">
			<%@include file="ddltblchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddltblcolchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-7">
 			<div id="detailInfoScript"></div>
	  </div>
	 </div>
	<div id="tabsIdx" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DDL.IDEX.DTL.INFO" /></a></li> <!-- DDL인덱스 상세정보 -->
	    <li><a href="#tabs-2"><s:message code="IDEX.CLMN.LST" /></a></li> <!-- 인덱스컬럼목록 -->
	    <li><a href="#tabs-3" id="colinfo"><s:message code="IDEX.CLMN.DTL.INFO" /></a></li> <!-- 인덱스컬럼 상세정보 -->
	    <li><a href="#tabs-4"><s:message code="IDEX.HSTR" /></a></li> <!-- 인덱스이력 -->
	    <li><a href="#tabs-5"><s:message code="IDEX.CLMN.HSTR" /></a></li> <!-- 인덱스컬럼이력 -->
	    <li><a href="#tabs-6">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailIdxInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddlidxcollist_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
 			<div id="detailIdxInfo2"></div>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddlidxchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-5">
			<%@include file="ddlidxcolchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-6">
 			<div id="detailIdxInfoScript"></div>
	   </div>
	 </div>
	<div id="tabsSeq" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1">DDL시퀀스 상세정보</a></li>
	    <li><a href="#tabs-2">시퀀스이력</a></li>
	    <li><a href="#tabs-3">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailSeqInfo"></div>
	  </div>

	   <div id="tabs-2">
			<%@include file="ddlseqchange_dtl.jsp" %>
	  </div>

	   <div id="tabs-3">
 			<div id="detailSetInfoScript"></div>
	  </div>
	</div>
	<div id="tabsEtc" style="display: none;">
	  <ul>	   
	    <li><a href="#tabs-1">기타오브젝트정보</a></li>   
	    <li><a href="#tabs-2">DDL</a></li>
	  </ul>
	  	  <div id="tabs-1">
			<div id="detailEtcInfo"></div>
	  </div>
	  <div id="tabs-2">
			<div id="detailEtcInfoScript"></div>
	  </div>
	  
	 </div>
	 
	<div id="tabsPart" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1">DDL파티션 상세정보</a></li>
	    <li><a href="#tabs-2">주파티션목록</a></li>
	    <li><a href="#tabs-3">서브파티션목록</a></li>
	    <li><a href="#tabs-4">파티션이력</a></li>
	    <li><a href="#tabs-5">DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="detailPartInfo"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="ddlpartmain_list.jsp" %>
	  </div>
	  <div id="tabs-3">
			<%@include file="ddlpartsub_list.jsp" %>
	  </div>
	   <div id="tabs-4">
			<%@include file="ddlparthist_list.jsp" %>
	  </div>
	   <div id="tabs-5">
 			<div id="detailPartInfoScript"></div>
	  </div>
	</div>
	 
	 <div id="tabsGrt" style="display:none;">
		  <ul>
		    <li><a href="#tabs-1">DDL권한 상세정보</a></li>
		    <li><a href="#tabs-2">권한이력</a></li>
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
</body>
</html>