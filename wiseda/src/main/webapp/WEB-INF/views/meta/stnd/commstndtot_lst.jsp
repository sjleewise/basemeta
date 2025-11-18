<!DOCTYPE html>
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
<title><s:message code="STRD.DATA.DEMD" /></title> <!-- 표준데이터요청 -->
<script type="text/javascript">
	$(document).ready(function() {

		doAction("SditmSearch");

        // 조회 Event Bind
        $("#btnSearch1").click(function(){ doAction("SditmSearch");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown1").click( function(){ doAction("SditmDown2Excel"); } ).show();

        $("#btnSearch2").click(function(){ doAction("DmnSearch");  });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown2").click( function(){ doAction("DmnDown2Excel"); } ).show();

        $("#btnSearch3").click(function(){ doAction("StwdSearch");  });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown3").click( function(){ doAction("StwdDown2Excel"); } ).show();
	    	
	});

	 //엔터키 이벤트 처리
	   $(document).keydown(function(event) {
	   	  if (event.keyCode == '13') {
	   	     event.preventDefault();
//	   	     alert(typeof action);
	 		if($("#tabNm").val() == "SDITM"){
				doAction("SditmSearch");
			}else if($("#tabNm").val() == "DMN"){
				doAction("DmnSearch");
			}else if($("#tabNm").val() == "STWD"){
				doAction("StwdSearch");
			}
	   	     
	   	   }
	   });
	
	$(window).load(function() {
			
		initGrid();
		initGrid2();
		initGrid3();
	});
	
	function initGrid()
	{
	    
	    with(grid_sditm){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        //merge 설정
	        SetMergeSheet(0);
	        
			var headtext  = "<s:message code='META.HEADER.STNDITEM.RQST.IFM.1'/>";
	//  		headtext += "<s:message code='META.HEADER.STNDITEM.RQST.IFM.2'/>";
			headtext += "<s:message code='META.HEADER.STNDITEM.RQST.IFM.4'/>";
	 		headtext += "<s:message code='META.HEADER.STNDITEM.RQST.IFM.3'/>";
	
	
	
	// 		headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|";
	// 		headtext += "용어명|영문용어명|표준용어설명|도메인명|업무분야|오너쉽기관|행정표준코드";
	// 		headtext += "|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
	
	// 		headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|";
	// 		headtext += "표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인논리명|도메인물리명|도메인그룹|인포타입ID|인포타입논리명|데이터타입|길이|암호화여부|개인정보여부";
	// 		headtext += "설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
			
			var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
	
			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
						{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
						{Type:"CheckBox", Width:45, SaveName:"ibsCheck",  Align:"Center", Edit:0, Hidden:0, Sort:0, Hidden:1},
						{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	  Align:"Center", Edit:0, Hidden:1},						
						{Type:"Text",   Width:80,  SaveName:"rvwConts",	  Align:"Left", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:0, ColMerge:0,Hidden:1},						
						{Type:"Combo",  Width:80,  SaveName:"regTypCd",	  Align:"Center", Edit:0, ColMerge:0,Hidden:1},						
						{Type:"Combo",  Width:120,  SaveName:"vrfCd",	  Align:"Center", Edit:0, ColMerge:0,Hidden:1},
	//		  		기관명	용어명	용어 설명	영문약어명	도메인명	허용값	저장형식	표현형식	단위	행정표준코드명	소관기관명	등록일자					
						{Type:"Text",   Width:15,  SaveName:"sditmId",     Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:15,  SaveName:"dbNm",     Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"orgNm",     Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:130,  SaveName:"sditmLnm",     Align:"Center", Edit:0, KeyField:0},
						{Type:"Text",   Width:300,  SaveName:"objDescn",	 Align:"Left", 	 Edit:0, KeyField:0, ColMerge:0},
						{Type:"Text",   Width:150,  SaveName:"sditmPnm",     Align:"Center", Edit:0, KeyField:0},
						{Type:"Text",   Width:100,  SaveName:"infotpLnm",	 Align:"Center", Edit:0, Hidden:0, KeyField:0}, //도메인명
						
						{Type:"Text",       Width:120,  SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:1, KeyField:0},
						{Type:"Int",       Width:100,  SaveName:"dataLen",	 	    Align:"Center", Edit:0, Hidden:1, KeyField:0},
						{Type:"Int",       Width:100,  SaveName:"dataScal",	 	Align:"Center", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:200,  SaveName:"alwVal",      Align:"Center", Edit:0},   // 허용값
	                    {Type:"Text",   Width:100,  SaveName:"saveFrm", 	 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
					    {Type:"Text",   Width:100,  SaveName:"exprsnFrm",	 	Align:"Center", Edit:0, Hidden:0},
					    {Type:"Text",   Width:100,  SaveName:"unit",    	 	Align:"Center", Edit:0, Hidden:0},
						
						{Type:"Text",   Width:100,  SaveName:"admnStndCd", 	      Align:"Center", Edit:0, Hidden:0, ColMerge:0},  //행정표준코드명
						{Type:"Text",   Width:100,  SaveName:"ownrOrg",      Align:"Center", Edit:0},  // 소관기관명
						{Type:"Text",   Width:130,  SaveName:"rqstDtm",  	  Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1, ColMerge:0},
						{Type:"Text",   Width:150,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, ColMerge:0, Hidden:1},
						{Type:"Text",   Width:60,   SaveName:"rqstNo",        Align:"Center", Edit:0, Hidden:1, ColMerge:0}, 
						{Type:"Int",    Width:60,   SaveName:"rqstSno",       Align:"Center", Edit:0, Hidden:1, ColMerge:0}
					];
						
			InitColumns(cols);
			
			//콤보 목록 설정
			SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
			SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
			SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
			SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
	//		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
			//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
			
	// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
			//SetColProperty("infotpId",	${codeMap.infotpibs});
	// 		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	// 		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	// 		SetColProperty("persInfoYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
			
			InitComboNoMatchText(1, "");
			
		  
			FitColWidth();  
		    //SetSheetHeight(200);
			SetExtendLastCol(1);	
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(grid_sditm);    
	    //===========================
	   
	}
	
	function initGrid2()
	{
	    
	    with(grid_dmn){
	    	
	     	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
			var headtext  = "<s:message code='META.HEADER.STNDDMN.RQST.IFM.1'/>";
	// 		headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.2'/>";
	// 		headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.3'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.5'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.4'/>";
			
			//headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
			//headtext += "|도메인그룹명|도메인분류명|도메인명|도메인설명|데이터타입|데이터길이|소수점|저장형식|표현형식|단위|허용값|행정표준코드";
			//headtext += "|담당자ID|담당자명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
			
			//headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
			//headtext += "|대분류코드|도메인논리명|도메인물리명|논리명기준구분|물리명기준구분|도메인그룹ID|도메인그룹|인포타입ID|인포타입|데이터타입|길이|소수점|암호화여부";
			//headtext += "|용어자동생성여부|모델|상위주제영역|주제영역|주제영역ID|코드값유형|코드값부여방식|데이터형식|목록엔티티논리명|목록엔티티물리명|목록어트리뷰트논리명|목록어트리뷰트물리명|부모도메인|최소값|최대값";
			//headtext += "|담당자ID|담당자명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
	
			var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
		
			var cols = [						
						{Type:"Seq",		Width:50,   SaveName:"ibsSeq",	    Align:"Center", Edit:0},
						{Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
						{Type:"CheckBox", 	Width:45,   SaveName:"ibsCheck",    Align:"Center", Edit:0, Hidden:0, Sort:0, Hidden:1},
						{Type:"Combo",      Width:80,   SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
						{Type:"Text",       Width:80,   SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
						{Type:"Combo",      Width:80,   SaveName:"rqstDcd",	    Align:"Center", Edit:0, KeyField:0,Hidden:1},						
						{Type:"Combo",      Width:80,   SaveName:"regTypCd",	Align:"Center", Edit:0,Hidden:1},						
						{Type:"Combo",      Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0,Hidden:1},							
						
						{Type:"Text",       Width:10,  SaveName:"dmnId",	 	    Align:"Left", Edit:0, Hidden:1, KeyField:0},
						{Type:"Text",   Width:15,  SaveName:"dbNm",     Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",       Width:100,  SaveName:"orgNm",	 	    Align:"Center", Edit:0, Hidden:1, KeyField:0}, //도메인그룹명
						{Type:"Text",       Width:100,  SaveName:"dmngLnm",	 	    Align:"Center", Edit:0, Hidden:0, KeyField:0}, //도메인그룹명
						{Type:"Text",       Width:100,  SaveName:"dmnLnm",   	    Align:"Center", Edit:0, KeyField:0}, //도메인분류명
		// 				{Type:"Text",       Width:180,  SaveName:"dmnPnm",   	    Align:"Left", Edit:0, KeyField:0, Hidden:0}, 
						{Type:"Text",       Width:100,  SaveName:"infotpLnm",	 	Align:"Center", Edit:0, Hidden:0, KeyField:0}, //도메인명
						{Type:"Text",       Width:500,  SaveName:"objDescn",	    Align:"Left", Edit:0, KeyField:0},
						{Type:"Text",       Width:120,  SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Int",        Width:50,  SaveName:"dataLen",	 	    Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Int",       Width:50,  SaveName:"dataScal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",       Width:100,  SaveName:"saveFrm", 	 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Text",       Width:100,  SaveName:"exprsnFrm",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",       Width:100,  SaveName:"unit",    	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",       Width:200,  SaveName:"cdVal",   	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",       Width:100,  SaveName:"admnStndCd",	 	Align:"Center", Edit:0, Hidden:0},
						                    
						{Type:"Text",       Width:60,   SaveName:"crgUserId",	Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",       Width:60,   SaveName:"crgUserNm",	Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",       Width:60,   SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
						{Type:"Text",       Width:60,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",       Width:60,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1}, 
						{Type:"Text",       Width:60,   SaveName:"rqstNo",      Align:"Center", Edit:0, Hidden:1}, 
						{Type:"Int",        Width:60,   SaveName:"rqstSno",     Align:"Center", Edit:0, Hidden:1}
					];
						
			InitColumns(cols);
			
			//콤보 목록 설정
			SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
			SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
			SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
			SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
			
		// 	SetColProperty("dmngId", 	${codeMap.dmngibs});
		// 	SetColProperty("infotpId",	${codeMap.infotpibs});
		// 	SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		// 	SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		// 	SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		// 	SetColProperty("encYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		// 	SetColProperty("dupYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		// 	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
			
		//		SetSendComboData(0,"dmngLnm", "Text");
			//SetSendComboData(0,"infotpLnm", "Text");
			
			InitComboNoMatchText(1, "");
			
		// 	SetColHidden("dmngId"	,1);
		// 	SetColHidden("infotpId"	,1);
		// 	SetColHidden("mdlLnm"	,1);
		// 	SetColHidden("uppSubjLnm",1);
		// 	SetColHidden("subjId"	,1);
		// 	SetColHidden("rqstNo"	,1);
		// 	SetColHidden("rqstSno"	,1);
		// 	SetColHidden("fullPath"	,1);
		// 	SetColHidden("dataScal"	,null);
			
		
			
		  
				FitColWidth();  
		    //SetSheetHeight(200);
			SetExtendLastCol(1);	
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(grid_dmn);    
	    //===========================
	   
	}
	
	
	function initGrid3()
	{
	    
	    with(grid_stwd){
	    	
			var cfg = {SearchMode:2,Page:100};
			SetConfig(cfg);
			
			var headers = [
						{Text:"<s:message code='META.HEADER.STNDWORD.RQST'/>", Align:"Center"}
					];
					//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|단어명|영문약어명|영문단어명|단어설명|형식단어여부|도메인분류명|이음동의어 및 금칙어 목록|요청일시|요청자ID|요청자명|요청번호|요청일련번호
					//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|표준단어논리명|표준단어물리명|영문의미|한자|출처구분|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
	
			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	    Align:"Center", Edit:0},
						{Type:"Status", Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
						{Type:"CheckBox", Width:50, SaveName:"ibsCheck",    Align:"Center", Edit:0, Hidden:0, Sort:0, Hidden:1},
						{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
						{Type:"Text",   Width:100,  SaveName:"rvwConts",	Align:"Left",   Edit:0, Hidden:1},						
						{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	    Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:80,   SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},
						
						{Type:"Text",   Width:150,  SaveName:"stwdId",   	Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:15,  SaveName:"dbNm",     Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"orgNm",   	Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"stwdLnm",   	Align:"Center", Edit:0, KeyField:0},
						{Type:"Text",   Width:80,  SaveName:"stwdPnm",   	Align:"Center", Edit:0, KeyField:0}, 
						{Type:"Text",   Width:100,  SaveName:"engMean", 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Text",   Width:800,  SaveName:"objDescn",	Align:"Left", Edit:0, KeyField:0},
						{Type:"Combo",  Width:100,   SaveName:"dmnYn",	 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Text",   Width:100,  SaveName:"dmnLnm",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:200,  SaveName:"symnLnm",	    Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName:"fbdnLnm",	    Align:"Center", Edit:0, Hidden:0},
						
						{Type:"Text",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
						{Type:"Text",   Width:60,  SaveName:"rqstUserId",   Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:60,  SaveName:"rqstUserNm",   Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:60,  SaveName:"rqstNo",       Align:"Center", Edit:0, Hidden:1}, 
						{Type:"Int",   Width:60,   SaveName:"rqstSno",      Align:"Center", Edit:0, Hidden:1}
					];
						
			InitColumns(cols);
			
			//콤보 목록 설정
			SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
			SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
			SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
			SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
			
			SetColProperty("dmnYn", 	{ComboCode:"N|Y", ComboText:"N|Y"}); /* 아니요|예 */
			
			
			
	// 		InitComboNoMatchText(1, "");
			
			SetColHidden("rqstUserId",1);
		  
			FitColWidth();  
			
			SetExtendLastCol(1);	
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(grid_stwd);    
	    //===========================
	   
	}
	
	function doAction(sAction)
	{
	        
	    switch(sAction)
	    {
	        case "SditmSearch":
	       		var param = $("#frmSearch1").serialize();
	       		
	//        		if($("#frmSearch #adAcnt")==''){
	//        			showMsgBox("INF","ID를 입력하여 주십시오.");
	//        			return;
	//        		}
				grid_sditm.DoSearch("<c:url value="/dq/stnd/getcommsditmlist.do" />", param);
				
	        	break;

	        case "DmnSearch":
       		var param = $("#frmSearch2").serialize();
       		
//        		if($("#frmSearch #adAcnt")==''){
//        			showMsgBox("INF","ID를 입력하여 주십시오.");
//        			return;
//        		}
			grid_dmn.DoSearch("<c:url value="/dq/stnd/getcommdmnlist.do" />", param);
			
        	break;

	        case "StwdSearch":
       		var param = $("#frmSearch3").serialize();
       		
//        		if($("#frmSearch #adAcnt")==''){
//        			showMsgBox("INF","ID를 입력하여 주십시오.");
//        			return;
//        		}
			grid_stwd.DoSearch("<c:url value="/dq/stnd/getcommstwdlist.do" />", param);
			
        	break;
	       
	        case "SditmDown2Excel":  //엑셀내려받기
	        
	//             grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	        	
// 	        	//보여지는 컬럼들만 엑셀 다운로드          
// 	            var downColNms = "";
// 	           	for(var i=0; i<grid_sheet.LastCol();i++ ){
// 	           		if(grid_sheet.GetColHidden(i) != 1){
// 	           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
// 	           		}
// 	           	}
// 	            var downColNms2 = "";
// 	       	    for(var i=0; i<grid_sheet2.LastCol();i++ ){
// 	       		  if(grid_sheet2.GetColHidden(i) != 1){
// 	       			  downColNms2 += grid_sheet2.ColSaveName(0,i)+ "|";
// 	       		  }
// 	       	    }
// 	       	 var downColNms3 = "";
// 	    	    for(var i=0; i<grid_sheet3.LastCol();i++ ){
// 	    		  if(grid_sheet3.GetColHidden(i) != 1){
// 	    			  downColNms3 += grid_sheet3.ColSaveName(0,i)+ "|";
// 	    		  }
// 	    	    }
	           	grid_sditm.Down2Excel({HiddenColumn:1, Merge:1, FileName:"공통표준용어.xls"});
	            break;

	        case "DmnDown2Excel":

	//             grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	        	
// 	        	//보여지는 컬럼들만 엑셀 다운로드          
// 	            var downColNms = "";
// 	           	for(var i=0; i<grid_sheet.LastCol();i++ ){
// 	           		if(grid_sheet.GetColHidden(i) != 1){
// 	           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
// 	           		}
// 	           	}
// 	            var downColNms2 = "";
// 	       	    for(var i=0; i<grid_sheet2.LastCol();i++ ){
// 	       		  if(grid_sheet2.GetColHidden(i) != 1){
// 	       			  downColNms2 += grid_sheet2.ColSaveName(0,i)+ "|";
// 	       		  }
// 	       	    }
// 	       	 var downColNms3 = "";
// 	    	    for(var i=0; i<grid_sheet3.LastCol();i++ ){
// 	    		  if(grid_sheet3.GetColHidden(i) != 1){
// 	    			  downColNms3 += grid_sheet3.ColSaveName(0,i)+ "|";
// 	    		  }
// 	    	    }
	        	grid_dmn.Down2Excel({HiddenColumn:1, Merge:1, FileName:"공통표준도메인.xls"});
	            break;			        
	            
	        case "StwdDown2Excel":          

	    	//             grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        	
//	        	//보여지는 컬럼들만 엑셀 다운로드          
//	            var downColNms = "";
//	           	for(var i=0; i<grid_sheet.LastCol();i++ ){
//	           		if(grid_sheet.GetColHidden(i) != 1){
//	           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
//	           		}
//	           	}
//	            var downColNms2 = "";
//	       	    for(var i=0; i<grid_sheet2.LastCol();i++ ){
//	       		  if(grid_sheet2.GetColHidden(i) != 1){
//	       			  downColNms2 += grid_sheet2.ColSaveName(0,i)+ "|";
//	       		  }
//	       	    }
//	       	 var downColNms3 = "";
//	    	    for(var i=0; i<grid_sheet3.LastCol();i++ ){
//	    		  if(grid_sheet3.GetColHidden(i) != 1){
//	    			  downColNms3 += grid_sheet3.ColSaveName(0,i)+ "|";
//	    		  }
//	    	    }
        	grid_stwd.Down2Excel({HiddenColumn:1, Merge:1, FileName:"공통표준단어.xls"});
            break;		        
	     
	    }       
	}
	        	
	function tabClick(obj){
		var nm = $(obj).attr('id');
		$("#tabNm").val(nm);
 		if($("#tabNm").val() == "SDITM"){
			doAction("SditmSearch");
		}else if($("#tabNm").val() == "DMN"){
			doAction("DmnSearch");
		}else if($("#tabNm").val() == "STWD"){
			doAction("StwdSearch");
		}
	}
	/*
	row : 행의 index
	col : 컬럼의 index
	value : 해당 셀의 value
	x : x좌표
	y : y좌표
	*/
	function grid_sditm_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
		if(code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		} else {
			
		}
	}

	function grid_dmn_OnSearchEnd(code, message) {
		//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
			if(code < 0) {
				showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
				return;
			} else {
			}
		}

	function grid_stwd_OnSearchEnd(code, message) {
		//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
			if(code < 0) {
				showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
				return;
			} else {
				
			}
		}
</script>
</head>
<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"></div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<div style="clear:both; height:5px;"><span></span></div>
<input id="tabNm" name="tabNm" type="hidden" value="SDITM"/>
	<div id="tabs">
	  <ul>
	    <li id="tab-SDITM"><a id="SDITM" onclick="tabClick(this)" href="#tabs-SDITM"><s:message code="STRD.TERMS"/></a></li><!--표준용어-->
	    <li id="tab-DMN"><a id="DMN" onclick="tabClick(this)" href="#tabs-DMN"><s:message code="DMN"/></a></li><!-- 도메인-->
	    <li id="tab-STWD"><a id="STWD" onclick="tabClick(this)" href="#tabs-STWD"><s:message code="STRD.WORD"/></a></li><!--표준단어-->
	  </ul>
	  <div id="tabs-SDITM">
			<div id="detailInfoSDITM">
		<div id="search_div1">
		<!--         <div class="stit">검색조건</div> -->
		         <!-- 조회버튼영역  -->
		        <button class="btn_search" id="btnSearch1" 	name="btnSearch1"><s:message code="INQ"/></button> <!-- 조회 -->
				<div class="bt02"><button class="btn_excel_down" id="btnExcelDown1" name="btnExcelDown1"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->                       
		        <div style="clear:both; height:5px;"><span></span></div>
		          <form id="frmSearch1" name="frmSearch1" method="post">
		            <fieldset>
		            <legend>머리말</legend>
		            <div class="tb_basic2">
		                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="공통표준용어조회">
		                   <caption>용어검색조건</caption>
		                   <colgroup>
		                   <col style="width:10%;" />
		                   <col style="width:23%;" />
		                   <col style="width:10%;" />
		                   <col style="width:23%;" />
		                   <col style="width:10%;" />
		                   <col style="width:23%;" />	
		                   </colgroup>
		                   
		                   <tbody>                            
								<tr>
								<th>용어명</th>
								<td><input class="wd200" id="sditmLnm" name="sditmLnm"/></td>
								<th>영문약어명</th>
								<td><input class="wd200" id="sditmPnm" name="sditmPnm"/></td>
								<th>도메인명</th>
								<td><input class="wd200" id="infotpLnm" name="infotpLnm"/></td>						
								</tr>
		                   </tbody>
		                 </table>   
		            </div>
		            </fieldset>
		         
		        </form>
		        </div>
		<div style="clear:both; height:5px;"><span></span></div>
					<div class="grid_01" id="grid_01">
		
			     <script type="text/javascript">createIBSheet("grid_sditm", "100%", "500px");</script>            
			
			<div style="clear:both; height:10px;"><span></span></div>        
		</div>
			</div>
	  </div>
	  <div id="tabs-DMN">
			<div id="detailInfoDMN">
			<div id="search_div2">
			<!--         <div class="stit">검색조건</div> -->
			         <!-- 조회버튼영역  -->
			        <button class="btn_search" id="btnSearch2" 	name="btnSearch2"><s:message code="INQ"/></button> <!-- 조회 -->
					<div class="bt02"><button class="btn_excel_down" id="btnExcelDown2" name="btnExcelDown2"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->                               <div style="clear:both; height:5px;"><span></span></div>
			          <form id="frmSearch2" name="frmSearch2" method="post">
			            <fieldset>
			            <legend>머리말</legend>
			            <div class="tb_basic2">
			                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="공통표준용어조회">
			                   <caption>도메인검색조건</caption>
			                   <colgroup>
			                   <col style="width:10%;" />
			                   <col style="width:23%;" />
			                   <col style="width:10%;" />
			                   <col style="width:23%;" />
			                   <col style="width:10%;" />
			                   <col style="width:23%;" />	
			                   </colgroup>
			                   
			                   <tbody>                            
									<tr>
									<th>도메인 그룹명</th>
									<td><input class="wd200" id="dmngLnm" name="dmngLnm"/></td>
									<th>도메인 분류명</th>
									<td><input class="wd200" id="dmnLnm" name="dmnLnm"/></td>
									<th>도메인명</th>
									<td><input class="wd200" id="infotpLnm" name="infotpLnm"/></td>						
									</tr>
			                   </tbody>
			                 </table>   
			            </div>
			            </fieldset>
			         
			        </form>
			</div>    
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="grid_01" id="grid_02">
			    
				     <script type="text/javascript">createIBSheet("grid_dmn", "100%", "500px");</script>         
		    </div>   
			</div>
	  </div>
	  <div id="tabs-STWD">
			<div id="detailInfoSTWD">
		  <div id="search_div3">
		<!--         <div class="stit">검색조건</div> -->
		         <!-- 조회버튼영역  -->
		        <button class="btn_search" id="btnSearch3" 	name="btnSearch3"><s:message code="INQ"/></button> <!-- 조회 -->
				<div class="bt02"><button class="btn_excel_down" id="btnExcelDown3" name="btnExcelDown3"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->                               <div style="clear:both; height:5px;"><span></span></div>
		          <form id="frmSearch3" name="frmSearch3" method="post">
		            <fieldset>
		            <legend>머리말</legend>
		            <div class="tb_basic2">
		                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="공통표준용어조회">
		                   <caption>단어검색조건</caption>
		                   <colgroup>
		                   <col style="width:10%;" />
		                   <col style="width:23%;" />
		                   <col style="width:10%;" />
		                   <col style="width:23%;" />
		                   <col style="width:10%;" />
		                   <col style="width:23%;" />		                   
		                   </colgroup>
		                   
		                   <tbody>
		                   		<tr>                            
								<th>단어명</th>
								<td><input class="wd200" id="stwdLnm" name="stwdLnm"/></td>
								<th>영문약어명</th>
								<td><input class="wd200" id="stwdPnm" name="stwdPnm"/></td>		
								<th>형식단어여부</th>
								<td>
								<select id="dmnYn" name="dmnYn">
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
								</td>				
								</tr>
		                   </tbody>
		                 </table>   
		            </div>
		            </fieldset>
		         
		        </form>
		</div>
		 <div style="clear:both; height:5px;"><span></span></div>
			<div class="grid_01" id="grid_03">
		    
			     <script type="text/javascript">createIBSheet("grid_stwd", "100%", "500px");</script>            
			</div>
			</div>
	  </div>	  
	 </div>




	<!-- 그리드 입력 입력 End -->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	
</div>
</body>
</html>