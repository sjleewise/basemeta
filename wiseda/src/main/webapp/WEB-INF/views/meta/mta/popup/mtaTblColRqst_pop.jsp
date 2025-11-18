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

var connTrgSchJson = ${codeMap.connTrgSch};
var selectedClmnRowIdx = 0;
var afterSaveOpenPopup = false;
var closePopup = false;

$(document).ready(function(){
	//$("form#frmInput #objDescn,#openDataLst").height("98px");
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	
	/*
	//폼검증
 	$("#frmInput").validate({
		rules: {
			mtaTblPnm	: "required",
			mtaTblLnm	: "required",
			tblVol		: "required",
			tblCreDt	: "required",
			subjNm		: "required",
			nopenRsn	: "required",
			objDescn	: "required"
		},
		messages: {
			mtaTblPnm	: requiremessage,
			mtaTblLnm	: requiremessage,
			tblVol		: requiremessage,
			tblCreDt	: requiremessage,
			subjNm		: requiremessage,
			nopenRsn	: requiremessage,
			objDescn	: requiremessage
		}
	});
	*/
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	
	// $("#tblCreDt").datepicker();  
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	//폼 저장 버튼 초기화...
	$('#frmPopTblInput #btnGridSave').click(function(event) {
		event.preventDefault();  //브라우저 기본 이벤트 제거...
			
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		//if(!$("#frmInput").valid()) return false;
		
		//필수입력 체크 
		if(!saveTblCheck()) return false;  	    
		
		saveTbl(); 
		
		//저장할래요? 확인창...
		//var message = "<s:message code="CNF.SAVE" />"; 
		//showMsgBox("CNF", message, saveTbl()); 
		
	});
	
	//폼 초기화 버튼 초기화...
	$('#frmPopTblInput #btnReset').click(function(event) {
		event.preventDefault();  //브라우저 기본 이벤트 제거...

		resetForm($("form#frmInput").find("readonly,disabled"));
		
	}).hide(); 


    $("#tblVol").keydown(function(event){

    	//숫자입력 체크 
        if(!numberCheck(event)) return false;         
    });

    /*   
    $("#frmInput #mtaTblLnm").keyup(function(event){
		
    	//숫자입력 체크 
    	if(!korengCheck(event)) return false;

    	var intext = $(this).val(); 
    	
    	///intext = korengCheck2(intext);       

    	$(this).val(intext); 
        
    });
    */
    
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close, #btnCloseBottom").click(function() {
    	//저장 
    	saveTbl();

    	closePopup = true; 
    	
    	//iframe 형태의 팝업일 경우
    	/* if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	} */
    });
    

    //저장
    $("#btnSend").click(function(){
    	if($("#frmPopTblInput #vrfCd option:selected").val() != "1") {
	    	//필수입력 체크 
			if(!saveTblCheck()) return false;  	   
	
			//컬럼 필수입력 체크 
	    	if(!saveColCheck()) return false;
	    	
	    	saveTbl();
    	} else {
    		
    		saveTbl();
    		
    		goSend(); 
    	}
    	
    }).show();
    
    $("#btnHelp").click(function(){
    	var tabIndex = $('#tabs').tabs('option', 'active');
    	var typeCd = "";
    	var width = 0;
    	var height = 0;
    	if(tabIndex == 0) {
        	typeCd = "tbl";
        	width = 990;
        	height = 821;
        } else if(tabIndex == 1) {
        	typeCd = "col";
        	width = 991;
        	height = 1097;
        }
//    	var param = "?tabIndex="+tabIndex; 
				
//		var vUrl = "<c:url value='/meta/mta/popup/mta_tbl_help.do' />"; 
				
	    //openLayerPop(vUrl, 600, 500, param);
	    
//		OpenWindow(vUrl+param, "tblHelp", 747, 600, "yes");

		var vUrl = "<c:url value='/meta/admin/popup/infosys_help.do?typCd=' />" + typeCd; 
	    
		OpenWindow(vUrl, "infosys_help", width, height, null); 
    });
	
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();

	$("#btnSearch").hide();

	$("#tabs").bind('tabsselect', function(event, ui) {
		if(ui.index == 2) {
			$('#btnHelp').hide();
			$('#btnHelp').parent().css('height', '28px');
		} else {
			$('#btnHelp').show();
			// 컬럼탭 일때 테이블정보 확인
			if(ui.index == 1) {
				if("${result.regTypCd}" != "D") {
					if(!saveTblCheck()) {
						$('#tabs').tabs("select", 0);
						return false;
					} 
				}
			}
		}
	});

	
});



//한글(완성형)이 포함되었는지 여부
function containKOR(str) {
	var chk = /[가-힣]/g;
	if(chk.test(str)) {
		return true;
	} else {
		return false;
	}
}

//공백 체크 
function checkSapce(val) { 
	
	var chk = /\s/g;  
	
	if(chk.test(val)) {
		return false;
	}
	
	return true;
} 


//특수문자 체크 
function checkExpChar(val) {  
	
	var chk = /[^가-힝a-zA-Z0-9]/g;  
	
	if(chk.test(val)) {
		return false;
	}
	
	return true;
}

// 문자열 바이트 체크 (문자열, 체크할 바이트 수)
function chkByteByStr(str, byte) {
	if(isBlankStr(byte)) {
		return false;
	} 
	var pattern = /[\u0000-\u007f]|([\u0080-\u07ff]|(.))/g;
	var chkByte = str.replace(pattern, "$&$1$2").length;
	if(chkByte < byte) {
		return true;
	} else {
		return false;
	}
}

//특수문자 체크 (허용할 문자 추가 체크)
function checkExpChar2(val, allow) {  
	
	// allow 는 제외하고 체크
	var temp = val.split(allow).join("").split(" ").join("");
	
	var chk = /[^가-힝a-zA-Z0-9]/g;  
	
	if(chk.test(temp)) {
		return false;
	}
	
	return true;
}
function goSend() {

	if($("#frmPopTblInput #vrfCd option:selected").val() != "1") {
		showMsgBox("ERR","검증상태가 등록가능 상태만 전송할 수 있습니다.");
		return; 
	}
	
	//강제로 승인상태로 변경 
	$("#frmPopTblInput #rvwStsCd").val("1");  
	     	    	
	var saveJson = getform2IBSjson($("#frmPopTblInput")); 
			
	var url = "<c:url value="/meta/mta/nonApproveMtaTbl.do"/>";
	
	var param = "";
	
	param += "&rqstNo="  + "${result.rqstNo}";
	param += "&rqstSno=" + "${result.rqstSno}";
	
	IBSpostJson2(url, saveJson, param, ibsPopCallback);
	 
}


//화면 로드시 공통 처리부분 여기에 추가한다.
$(window).on('load',function() {
	

	//==========테이블정보 조회=================
	var param = "";
	
	param += "rqstNo="   + "${result.rqstNo}"; 
	param += "&rqstSno=" + "${result.rqstSno}";
	
	loadDetail(param); 
	
	//===========================================
		
	//=========컬럼정보 조회========	
	initColGrid();
	
//	doAction("SearchCol");    
	//============================== 
		
	checkStwd("${result.mtaTblLnm}");
});

$(window).resize(function() {
	//그리드 가로 스크롤 방지
	//col_sheet.FitColWidth();
});

function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.MTACOL.RQST'/>";

	    var headers = [
                    {Text:headtext, Align:"Center"}

                    //No.|ibs상태|선택|검토상태|검토내용|요청구분|상태|등록유형|검증결과|테이블명|컬럼ID|컬럼명|컬럼명(한글)|테이블ID|컬럼설명|연관엔티티명|연관속성명|컬럼순서|데이터타입|데이터길이|소수점길이|데이터포맷
                    //|NOT NULL여부|PK정보|PK순서|FK정보|DEFAULT 값|제약조건|개방여부|개인정보여부|암호화여부|비공개사유|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	  Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status",   Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:1, Sort:0},
					{Type:"Combo",   Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",    Width:80,   SaveName:"rvwConts",  Align:"Left",   Edit:0, Hidden:1},						
					{Type:"Combo",   Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:1, Hidden:0},
					{Type:"Combo",   Width:70,   SaveName:"regStatus",  Align:"Center", Edit:0, Hidden:0},				
					{Type:"Combo",   Width:70,   SaveName:"regTypCd",  Align:"Center", Edit:0, Hidden:0},	 					
					{Type:"Combo",   Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0, Hidden:0},
					
                    {Type:"Text",    Width:110, SaveName:"mtaTblPnm"   ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:40,  SaveName:"mtaColId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:200, SaveName:"mtaColPnm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:200, SaveName:"mtaColLnm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:40,  SaveName:"mtaTblId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"objDescnCol",	Align:"Left", 	Edit:0, Hidden:1},

                    {Type:"Text",    Width:80,  SaveName:"colRelEntyNm"   , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:80,  SaveName:"colRelAttrNm"   , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Int",     Width:60,  SaveName:"colOrd"         , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:120, SaveName:"dataType"       , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:60,  SaveName:"dataLen"        , Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:80,  SaveName:"dataScal"       , Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:110, SaveName:"dataFmt"       , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100, SaveName:"nonulYn"       , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Combo",   Width:80,  SaveName:"pkYn"           , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Int",     Width:50,  SaveName:"pkOrd"          , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"fkYn"           , Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"defltVal"      , Align:"Left",   Edit:0, Hidden:1},
                  
                    {Type:"Text",    Width:120,  SaveName:"constCnd"   ,Align:"Left",   Edit:0, Hidden:1},

                    {Type:"Combo",   Width:100,  SaveName:"openYn"       , Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"priRsn"       , Align:"Left",     Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"prsnInfoYn"   , Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"encTrgYn"     , Align:"Center",   Edit:0},
                    
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",      Align:"Center", Edit:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",     Align:"Center", Edit:0},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

        var ynComboData = {ComboCode:"N|Y", ComboText: "<s:message code='COMBO.NO.YES'/>"};
        
	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
	    
		SetColProperty("pkYn", 		 ynComboData); /* 아니요|예 */
// 		SetColProperty("fkYn", 		 ynComboData); /* 아니요|예 */

		SetColProperty("nonulYn", 	 ynComboData); /* 아니요|예 */
		
		 var openComboData = {ComboCode:"N|Y", ComboText: "비공개|공개"};	        
		
		SetColProperty("openYn", 	 openComboData); /* 아니요|예 */
		SetColProperty("prsnInfoYn", ynComboData); /* 아니요|예 */
		SetColProperty("encTrgYn", 	 ynComboData); /* 아니요|예 */		
		SetColProperty("regStatus",  {ComboCode:"ERR|OK", ComboText:"미완료|완료"} );
		
		//===============비공개사유 세팅================
		var jsonPriRsn = ${codeMap.nopenRsnibs}; 	
			
		var vPriRsnCd   =  jsonPriRsn.ComboCode;  
		var vPriRsnCdNm = jsonPriRsn.ComboText;  
		
	    SetColProperty("priRsn", {ComboCode: vPriRsnCd, ComboText: vPriRsnCdNm}); //비공개사유 
		//================================================
	    
        InitComboNoMatchText(1, "");
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
        //SetColHidden("arrUsrId",1);
        /*
        SetColEditable("mtaColLnm",true);
        SetColEditable("objDescnCol",true);
        SetColEditable("openYn",true);
        SetColEditable("prsnInfoYn",true);
        SetColEditable("encTrgYn",true);
        SetColEditable("priRsn",true);
      */
        FitColWidth();
        //SetSheetHeight(250);
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}

//비공개사유 팝업 리턴값 처리
/* function returnNopenRstPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #nopenRsn").val(retjson.codeCd);
	$("#frmInput #nopenRsnCdNm").val(retjson.codeLnm);
	
} */

function setTblInfo(param, send) {
	
	var vUrl ="<c:url value="/meta/mta/popup/getMtaTblInfoDetail.do"/>";   
	
	$.ajax({
		type : "POST",
		url : vUrl,
		dataType : "json",
		// async: false,
		data : param,
		success : function(res) {

			//alert(res.vrfCd);
			
			$("#frmPopTblInput #vrfCd").val(res.vrfCd);
			$("#frmPopTblInput #regTypCd").val(res.regTypCd); 			
			$("#frmPopTblInput #regTblStatus").val(res.regTblStatus);

			if(send) {
				goSend();
			}
		},
		error : function(res) {

			// alert(res.data);
		}
	});	
}

function loadDetail(param) {	
	
	/*
	$('div#detailInfo').load('<c:url value="/meta/mta/popup/ajax/mtatbl_rqst_dtl_pop.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
		    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
		    alert( msg + xhr.status + " " + xhr.statusText );
		  }else{
						  
			  
		 }
	});
	*/
}


function saveTbl() {
		
	//비공개사유 
	$("#nopenRsn").val(getNopenRsnChk());
	
	var saveJson = getform2IBSjson($("#frmPopTblInput")); 
	
	
	var ibsSaveJson = col_sheet.GetSaveJson(0);	 
		
	//필수입력 누락
	if (ibsSaveJson.Code == "IBS010") {
	
		$("#tabs").tabs({active: 1});
		return;
	}	
	
	var data = new Object(); 
	
	data.dataTbl = saveJson.data;
	data.dataCol = ibsSaveJson.data;
		
	if(saveJson.data.length == 0) return;
	
	var url = "<c:url value="/meta/mta/regMtaTblColRqstList.do"/>";
	
	var param = "";
	
	param += "&rqstNo="  + "${result.rqstNo}";
	param += "&rqstSno=" + "${result.rqstSno}";
	
	//alert(param);
		
	IBSpostJson2(url, data, param, ibsPopCallback);  
}

function getNopenRsnChk() {
	
	var chkVal = "";
	
	$(":checkbox[name='chkNopenRsn']:checked").each(function(i, e){ 
		
		if(chkVal == ""){
			
			chkVal += e.value;
		}else{
			chkVal += "|" + e.value;
		}
	});
	
	return chkVal;
}

//================================================
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
//================================================
function ibsPopCallback(res) {
	var result = res.RESULT.CODE;
		
	if (result == 0) {
		 //alert(res.RESULT.MESSAGE);

		// 공통메세지 팝업 : 성공 메세지...
//		showMsgBox("INF", res.RESULT.MESSAGE);
		if (!isBlankStr(cnfNextFunc)) {
			eval(cnfNextFunc);
			return;
		}
		// alert(postProcessIBS);
		if (postProcessPopIBS != null) {
			postProcessPopIBS(res);
		}
	} else if (result == 401) {
		// 권한이 없어요...
		showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	} else {
		// alsert("저장실패");
		// 공통메시지 팝업 : 실패 메세지...
		showMsgBox("ERR", res.RESULT.MESSAGE);
	}

	if(closePopup) {

		doAction("Close");
	}
}


function saveTblCheck() {   
		
	if($("#frmPopTblInput #mtaTblLnm").val() == "") {
		
		showMsgBox("ERR","테이블한글명을 입력하세요.");   
		return false;
	}
	
	var tblLnm = $("#frmPopTblInput #mtaTblLnm").val();
	
	 if(!containKOR(tblLnm)) {
		
		showMsgBox("ERR","테이블한글명은 한글을 포함하여 입력하세요.");     
		return false;
	}
		
/* 	if(!checkSapce(tblLnm)) { 
		
		showMsgBox("ERR","테이블한글명은 공백을 입력할 수 없습니다..");   
		return false;
	}  2019.03.25 DB에서 저장할때 TRIM 처리로 변경 */
		
	 if(!checkExpChar2(tblLnm, "_")) {  
		showMsgBox("ERR","테이블한글명은 특수문자를 입력할 수 없습니다.");   
		return false;
	}  	
	
	if($("#frmPopTblInput #objDescn").val().trim() == "") {
		
		showMsgBox("ERR","테이블설명을 입력하세요.");
		return false;
	}

 	if(chkByteByStr($('#objDescn').val(), 30)) {
		showMsgBox("ERR", "<s:message code="META.INST.MESSAGE2"/>");
		$('#objDescn').focus();
    	return false;
	} 
	
	/* if($("#frmPopTblInput #subjNm").val() == "") {
		
		showMsgBox("ERR","BRM분류명을 입력하세요.");
		return false;
	} */

	// 삭제상태일때 체크 안함
	if("${result.regTypCd}" != "D" && $("#frmPopTblInput #openRsnCd").val() == "") {
		
		showMsgBox("ERR","공개여부를 입력하세요.");
		return false;
	}
	
	//========비공개,부분공개 사유 입력 체크======= 
	if($("#openRsnCd").val() == "02" || $("#openRsnCd").val() == "03") {
	
		var chkCnt = 0;
		
		$(":checkbox:input[name='chkNopenRsn']").each(function(i,e){
		
			if(e.checked == true) {
				chkCnt++;
			}
		});
		
		if(chkCnt == 0) {
			
			showMsgBox("ERR","비공개사유를 입력하세요.");
			return false;
		} 		

		if($('#nopenDtlRelBss').val() == "") {
			showMsgBox("ERR","상세관련근거를 입력하세요.");
			return false;
		}
	}

	// 삭제상태일때 체크 안함
	if("${result.regTypCd}" != "D" && $("#dqDgnsYn").val() == "") {
		showMsgBox("ERR","품질진단여부를 입력하세요.");
		return false;
	}
	
	//==============================================
		
	
	/*
	if($("#frmPopTblInput #tblVol").val() == "") {
		
		showMsgBox("ERR","테이블볼륨을 입력하세요.");
		return false;
	}
	*/
	/* 
	if($("#frmPopTblInput #tblCreDt").val() == "") {
		
		showMsgBox("ERR","테이블생성일을 입력하세요.");
		return false;
	}
		 
	if($("#frmPopTblInput #nopenRsn").val() == "") {
		
		showMsgBox("ERR","비공개사유를 입력하세요.");
		return false;
	}
	*/
			
	return true;
}



function saveColCheck() { 

	var openRsnCd = $('#frmPopTblInput #openRsnCd').val();
	for(var i = 1; i <= col_sheet.RowCount() ; i++) {
		
		var mtaColPnm   = col_sheet.GetCellValue(i, "mtaColPnm");
		var mtaColLnm   = col_sheet.GetCellValue(i, "mtaColLnm");
		var dataType    = col_sheet.GetCellValue(i, "dataType");
		var prsnInfoYn  = col_sheet.GetCellValue(i, "prsnInfoYn");
		var openYn      = col_sheet.GetCellValue(i, "openYn");
		var encTrgYn    = col_sheet.GetCellValue(i, "encTrgYn");
		var priRsn      = col_sheet.GetCellValue(i, "priRsn");
		var objDescnCol = col_sheet.GetCellValue(i, "objDescnCol");

		var numStr = i + "번 행 컬럼정보의 ";
		
		if(mtaColPnm == "") {
			
			showMsgBox("ERR", numStr + "컬럼영문명을 입력하세요.");
			
			$("#tabs").tabs("select",1);
			return false;
		}
		
		if(mtaColLnm == "") {
			
			showMsgBox("ERR", numStr + "컬럼한글명을 입력하세요.");
			$("#tabs").tabs("select",1);
			return false;
		}
		
		if(!containKOR(mtaColLnm)) {
			
			showMsgBox("ERR", numStr + "컬럼한글명은 한글을 포함하여 입력하세요.");
			$("#tabs").tabs("select",1);
			return false;
		}
				
/* 		if(!checkSapce(mtaColLnm)) { 
			
			showMsgBox("ERR", numStr + "컬럼한글명은 공백을 입력할 수 없습니다.");
			$("#tabs").tabs("select",1);
			return false;
		} 2019.03.25 DB에서 저장할때 TRIM 처리로 변경 */		
		
		if(!checkExpChar2(mtaColLnm, "_")) {
			
			showMsgBox("ERR", numStr + "컬럼한글명은 특수문자를 입력할 수 없습니다.");
			$("#tabs").tabs("select",1);
			return false;
		}		
						
		if(dataType == "") {
			
			showMsgBox("ERR", numStr + "데이터타입을 입력하세요.");
			$("#tabs").tabs("select",1);
			return false;
		}
		
		if(prsnInfoYn == "") {
			
			showMsgBox("ERR", numStr + "개인정보여부를 입력하세요.");			
			$("#tabs").tabs("select",1);
			return false;
		}
			
		if( openYn == "") {
			
			showMsgBox("ERR", numStr + "개방여부를 입력하세요.");		
			$("#tabs").tabs("select",1);
			return false;
		}
		
		if(encTrgYn == "") {
			
			showMsgBox("ERR", numStr + "암호화여부를 입력하세요.");
			
			$("#tabs").tabs("select",1);
			return false;
		}

		// 컬럼의 비공개사유 '아니오' 이고 테이블의 공개여부가 부분공개 이면 
		if(openYn == "N" && openRsnCd == "03") {
			if(priRsn == "") {
				
				showMsgBox("ERR", numStr + "비공개사유를 입력하세요.");
				
				$("#tabs").tabs("select",1);
				return false;
			}
		}
		
		
		/* 
		if( objDescnCol == "") { 
			
			showMsgBox("ERR","컬럼설명을 입력하세요.");
			return false;
		}
		*/ 	
	}
			
	return true;
}




//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {        
        case "SearchCol":	//요청서 재조회...
        	
        	var param = "";
                	
        	param += "rqstNo="   + "${result.rqstNo}"; 
        	param += "&rqstSno=" + "${result.rqstSno}";
        	param += "&bizDcd=MTA";
		        	
        	col_sheet.DoSearch("<c:url value="/meta/mta/getmtacolrqstlist.do" />", param);
        	        	
        	param += "&bizDtlCd=TBL";
        	
        	//검증결과 조회
        	getRqstVrfLst(param);

        	<c:if test="${sessionScope.loginVO.isSysAdminYn == 'Y' and search.gapStsCd == 'U'}">
        	//2019.03.06 추가
        	if(typeof grid_change != "undefined") {
        		grid_change.RemoveAll();
        	}
        	
        	getRqstChg(param);
        	</c:if>
        	
        	break;
        case "Close":
        	
        	//iframe 형태의 팝업일 경우
	    	if ("${search.popType}" == "I") { 
	    		parent.closeLayerPop();
	    	} else {
	    		window.close();
	    	}			
        	
        	break;
        	              
    }       
}

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessPopIBS(res) {

	//alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
// 		showMsgBox("INF", res.RESULT.MESSAGE);
	}

	//alert(res.action);
	
	switch(res.action) {
		
		//요청서 저장 및 검증
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...

	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
	    		
	    		var param = "";
	    		
	    		param += "&rqstNo="  + "${result.rqstNo}";
	    		param += "&rqstSno=" + "${result.rqstSno}";
	    			    		
	    	    //loadDetail(param);
	    	    
	    	    setTblInfo(param, true); 

	    	    doAction("SearchCol"); 
				
	    		parent.doAction("Search");
	    	}
			
			break;
			
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>": 
			
			showMsgBox("INF", "중앙메타시스템으로 전송이 완료되었습니다.","Close");
			
			break;
						
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}	
}





function returnBrmInfoPop(brmFullPath, brmId, upperBrmId){	

	//alert(JSON.stringify(data));
	
	$("#frmInput #subjNm").val(brmFullPath);     //주제영역전체경로  
 	$("#frmInput #subjId").val(brmId);           //주제영역id
 	$("#frmInput #uppSubjId").val(upperBrmId);   //주제영역id
	
	/*
	var sData = jQuery.parseJSON(data);    
	
	$("#frmInput #subjNm").val(sData.subj_path); //주제영역전체경로 
 	$("#frmInput #subjId").val(sData.subj_id);   //주제영역id
 	$("#frmInput #uppSubjId").val(sData.upp_subj_id);   //주제영역id
 	*/  
 	
}

function numberCheck(event){

	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	if( (keyID >= 48 && keyID <= 57) ||
		(keyID >= 96 && keyID <= 105) || keyID == 8		
	    ){

	    return true;
	}else{
		return false;
	}	
}


function korengCheck2(intext){

	//var deny = /[`~!@#$%^&*()_+-={}[]|\\\'\":;/\?]/gi ;
	
	var deny = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
	
	if(deny.test(intext)) {

		intext = intext.replace(deny,"");
		
	    return intext; 
	}else{
		return intext;
	}		
}


function korengCheck(event){

	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//alert(keyID);

	if(keyID == 8) { 
		return true;

	}else if( keyID == 16){ 

		return false;
		
	}else if( keyID >= 48 && keyID <= 57){

		return true;
	}else if( keyID == 189 || keyID == 187){

		return false;
	}else if( (keyID < 65) || (keyID > 122 && keyID <= 127) ){

	    return false;
	}else{
		return true;
	}		
}


function checkStwd(chkTerm) {
	
	var vUrl ="<c:url value="/meta/stnd/api/request/getWamStwdList.do"/>";   
	
	param = "stwdLnm=" + chkTerm;
	
	$.ajax({
		type : "POST",
		url : vUrl,
		dataType : "json",
		async: false,
		data : param,
		success : function(res) {
			
			if(res.DATA.length == 0){
				$("#frmPopTblInput #divStnd").html("비표준");
				$("#frmPopTblInput #divStnd").css("color","red");				
			}else{
				$("#frmPopTblInput #divStnd").html("표준");
			} 
		},
		error : function(res) {

			// alert(res.data);
		}
	});	
}


/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function col_sheet_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
		
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	
	// 등록유형이 삭제가 아닌 경우에만 작동
	/* if("${result.regTypCd}" != "D") {

		selectedClmnRowIdx = row;

		var rowJson =  col_sheet.GetRowJson(row); 
		
		var url = "<c:url value='/meta/mta/popup/mtaColRqstPop.do'/>";
		
		var param = "";
		
		param += "&rqstNo="     + rowJson.rqstNo ;
		param += "&rqstSno="    + rowJson.rqstSno ; 
		param += "&rqstDtlSno=" + rowJson.rqstDtlSno;
		param += "&searchObj="  + rowJson.mtaTblPnm;
		param += "&subInfo=COL"; 	
		
		openLayerPop(url, 1000, 580, param); 
	} */
	afterSaveOpenPopup = false;
	selectedClmnRowIdx = row;
	openColDetailPopup(row);
}

function openColDetailPopup(row) {

	if("${result.regTypCd}" != "D") {

		var rowJson =  col_sheet.GetRowJson(row); 
		
		var url = "<c:url value='/meta/mta/popup/mtaColRqstPop.do'/>";
		
		var param = "";
		
		param += "&rqstNo="     + rowJson.rqstNo ;
		param += "&rqstSno="    + rowJson.rqstSno ; 
		param += "&rqstDtlSno=" + rowJson.rqstDtlSno;
//		param += "&searchObj="  + rowJson.mtaTblPnm;
		param += "&subInfo=COL"; 	
		
		openLayerPop(url, 1000, 600, param);
	}
}

function moveColInfo(rqstDtlSno) {

	afterSaveOpenPopup = true;

	var currRowIdx = col_sheet.FindText("rqstDtlSno", rqstDtlSno, 0, -1);
	var lastRowIdx = col_sheet.GetTotalRows();

	if(currRowIdx < 1) return;

	if(currRowIdx == lastRowIdx) {
		selectedClmnRowIdx = 1;
	}else {
		selectedClmnRowIdx = currRowIdx+1;
	}

	doAction("SearchCol");
}

function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {

		if(afterSaveOpenPopup) {

			if(selectedClmnRowIdx > 0) {
				col_sheet.SetSelectRow(selectedClmnRowIdx);

				var row = col_sheet.GetSelectRow();

				openColDetailPopup(row);
				
			}

		afterSaveOpenPopup = false;
			
		}
	}
}

/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function col_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param = col_sheet.GetRowJson(row);
			
	var param1 = "";
	
	param1  = "bizDtlCd=COL";
    param1 += "&rqstNo="    + param.rqstNo;
    param1 += "&rqstSno="   + param.rqstSno;
    param1 += "&rqstDtlSno="+ param.rqstDtlSno;
    
    //alert(param1); 

	getRqstVrfLst(param1); 
	
	<c:if test="${sessionScope.loginVO.isSysAdminYn == 'Y' and search.gapStsCd == 'U'}">
	//2019.03.06 추가
	param1 += "&bizDcd=MTA";
	param1 += "&subInfo=COL";
	
	
	if(typeof grid_changecol != "undefined") {
		grid_changecol.RemoveAll();
	}

	getRqstChg(param1, "COL");
	</c:if>
}

</script>


<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">테이블|컬럼정보 등록/수정</div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
	    
	    <div class="bt02">
	    	<!-- <a id="btnHelp"><img src="<c:url value="/images/icon_help.png"/>" /></a>  -->		   
	    </div>
	    
	    <div style="clear:both; height:5px;"><span></span></div>
	    
	    <div id="tabs" style="display: none;">
		  <ul>
		    <li><a href="#tabs-1" id="tblinfo">테이블정보</a></li>
		    <li><a href="#tabs-2" id="collist">컬럼정보</a></li>
		    <li><a href="#tabs-3" id="vrflist">검증결과</a></li>
		   	<c:if test="${sessionScope.loginVO.isSysAdminYn == 'Y' and search.gapStsCd == 'U'}">
			    <li><a href="#tabs-4"><s:message code="TBL.CHG.ITEM" /></a></li> <!-- 테이블변경항목 -->
			    <li><a href="#tabs-5" id="colChgItem"><s:message code="CLMN.CHG.ITEM" /></a></li> <!-- 컬럼변경항목 -->
	    	</c:if> 	  	   
		  </ul>
		  
		  <div id="tabs-1" style="margin: 10px 0 10px 0;"> 
		  	  <%@include file="mtatbl_rqst_dtl_pop.jsp" %>
		  	  <div style="clear:both; height:10px;"><span></span></div>
		  	  <div class="tb_comment g_ico g_ico1">&nbsp;<span class="ico_ga">&nbsp;</span> 보유DB에서 메타데이터를 자동수집하는 항목입니다. (MS-SQL(2000), CUBRID, Sysbase, MS-ACCESS는 DB카달로그에 테이블볼륨(ROW수) 정보가 없습니다.)</div>
		  	  <div style="clear:both; height:10px;"><span></span></div> 
		  </div>
		  	  
		  <!-- 컬럼 목록 탭 -->
		  <div id="tabs-2"> 
		  	<c:if test="${sessionScope.loginVO.isSysAdminYn == 'Y' and search.gapStsCd == 'U'}">
				<div class="tb_comment"><span style="">!시스템관리자기능!</span> - 클릭시 컬럼변경항목으로 이동합니다. - 더블클릭시 컬럼상세정보팝업이 실행합니다.</div>
				<div style="clear:both; height:5px;"><span></span></div>
		  	</c:if>
			<!-- 그리드 입력 입력 -->
			<div id="grid_02" class="grid_01">
			     <script type="text/javascript">createIBSheet("col_sheet", "100%", "380px");</script>                       
			</div>
			<!-- 그리드 입력 입력 --> 
		  </div>
		  <div id="tabs-3">
			 <%@include file="../../../commons/rqstmst/rqstvrf_lst.jsp" %>
		  </div>
		  <c:if test="${sessionScope.loginVO.isSysAdminYn == 'Y' and search.gapStsCd == 'U'}">
			  <div id="tabs-4">
				<%@include file="../../../commons/rqstmst/rqstChange_lst.jsp" %>
			  </div>
			  <div id="tabs-5">
				<%@include file="../../../commons/rqstmst/rqstChangeCol_lst.jsp" %>
			  </div>
		  </c:if>
		</div> 
	     
	    <!-- 버튼시작 -->
	    <div style="clear:both; height:370px;"><span></span></div>
	    <div style="clear:both; height:10px;"><span></span></div>
	    <div style="clear:both; height:10px;"><span></span></div>
	    <div style="clear:both; height:10px;"><span></span></div>
	    <div id="divTblEnd" style="clear:both; height:20px;"><span></span></div>
	    <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstMtaPop.jsp" /> 
	    <!-- 버튼 끝 -->
	    
	</div>
	
</div>
</body>


