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
<title><s:message code="MDEL.GAP.ANLY.INQ" /></title> <!-- 모델GAP분석 조회 -->

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;
//var headers ="";
//var headerInfo="";
var initGridYn2 = false;
$(document).ready(function() {
	
		//alert(sysareaJson[0].codeCd + ":" + sysareaJson[0].codeLnm);
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
		$("#grid_02").hide();
		$("#codeth2").hide();
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ 
        	doAction("Search");  
        	
        });
        
        $("#btnDelete").hide();
        
        $("#btnTreeNew").change(function(){}).hide();
                      
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
// 	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
// 	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
// 	   		double_select(connTrgSchJson, $(this));
// 	   	});
    	
	   	//$( "#tabs" ).tabs();
	   	
	   	
	   	$("#gapDcd").change(function(){
	   		grid_sheet.RemoveAll();
   			grid_sheet2.RemoveAll(); 
// 	   		if($("#gapDcd").val()=='MD'||$("#gapDcd").val()=='TR'){
// 	   			 headers = [
// 							   {Text:"No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|DB명|스키마명|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버|타겟서버"},
// 							   {Text:"No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|DB명|스키마명|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID", Align:"Center"}
// 						];
// 	   			 grid_sheet.SetMergeSheet(5);
// 				 headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
				
// 				 grid_sheet.InitHeaders(headers, headerInfo);
// 				   init_sheet(grid_sheet);   
// 	   		}else if($("#gapDcd").val()=='MT')
// 	   		{
	   			
// 	   		 headers = [
// 						   {Text:"No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|DB명|스키마명|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관|메타이관"},
// 						   {Text:"No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|DB명|스키마명|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID", Align:"Center"}
// 					];
			
// 			 headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
// 			 grid_sheet.SetMergeSheet(5);
// 			 grid_sheet.InitHeaders(headers, headerInfo);
// 			   init_sheet(grid_sheet);   
// 	   		}
	   		
	   	});
	   	
	   	$("#cdValTypCd").change(function(){
	   		if($("#cdValTypCd").val()=='SMP'){
	   			$("#grid_01").show();
	   			$("#grid_02").hide();
	   			$("#codeth1").show();
	   			$("#codeth2").hide();
	   			grid_sheet.RemoveAll();
	   			grid_sheet2.RemoveAll();
	   			
	   		}else if($("#cdValTypCd").val()=='CMP'){
	   			if(initGridYn2==false){
	   				initGrid2();
	   				initGridYn2 = true;
	   			}
	   			$("#grid_01").hide();
	   			$("#grid_02").show();
	   			$("#codeth1").hide();
	   			$("#codeth2").show();
	   			grid_sheet.RemoveAll();
	   			grid_sheet2.RemoveAll();
	   			grid_sheet2.FitColWidth();
	   		}                
	 	});
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	//$( "#layer_div" ).show();
});



$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(5);
		var headers = [
						   {Text:"<s:message code='META.HEADER.CODEGAP.LST1'/>"},
						   /* No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟 */
						   {Text:"<s:message code='META.HEADER.CODEGAP.LST2'/>", Align:"Center"}
						   /* No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|DB명|스키마명|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID|DB명|스키마명|대분류코드|중분류코드|소분류코드|대분류코드명|중분류코드명|소분류코드명|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID */
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0, ColMerge:0},
			            {Type:"Combo",   Width:150,    SaveName:"gapStatus", 		Align:"Center", Edit:0, Hidden:0},
			            {Type:"Text",   Width:150,    SaveName:"gapConts", 		Align:"Left", Edit:0, Hidden:0},
			            {Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},  
 				        {Type:"Text",   Width:120,  SaveName:"dmnLnm" , 		Align:"Left", Edit:0, Hidden:0},            
 				        {Type:"Text",   Width:120,  SaveName:"dmnPnm" , 		Align:"Left", Edit:0, Hidden:1},
 				        {Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm" , 		Align:"Left", Edit:0, Hidden:1},            
 				        {Type:"Text",   Width:120,  SaveName:"dbSchPnm" , 		Align:"Left", Edit:0, Hidden:1},
 						{Type:"Text",   Width:100,  SaveName:"lccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"mccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"sccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"lclsNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"mdcdNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"sclsNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"useYn"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"dispOrd"   , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc1"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm1"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc2"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm2"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc3"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm3"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc4"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm4"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc5"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm5"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"rmrkCntn"  , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"outlCntn1" , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"outlCntn2" , 		Align:"Left", Edit:0, Hidden:0},                    
 				        {Type:"Text",   Width:60,   SaveName:"cdValId" , 		Align:"Left", Edit:0, Hidden:1},
 				        {Type:"Text",   Width:120,  SaveName:"tgtDbConnTrgPnm" , 		Align:"Left", Edit:0, Hidden:0},            
 				        {Type:"Text",   Width:120,  SaveName:"tgtDbSchPnm" , 		Align:"Left", Edit:0, Hidden:0},
 						{Type:"Text",   Width:100,  SaveName:"tgtLccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtMccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtSccd"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtLclsNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtMdcdNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtSclsNm"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtUseYn"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtDispOrd"   , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc1"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm1"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc2"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm2"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc3"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm3"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc4"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm4"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc5"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm5"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtRmrkCntn"  , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtOutlCntn1" , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtOutlCntn2" , 		Align:"Left", Edit:0, Hidden:0},                    
 				        {Type:"Text",   Width:60,   SaveName:"tgtCdValId" , 		Align:"Left", Edit:0, Hidden:1}           

					];
                    
        InitColumns(cols);
//         SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
// 		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
// 		SetColProperty("infotpId",	${codeMap.infotpibs});
// 		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
         FitColWidth();  
        //SetExtendLastCol(1);  
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}
function initGrid2()
{
    
    with(grid_sheet2){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(5);
		var headers = [
						   {Text:"<s:message code='META.HEADER.CODEGAP.LST.3'/>"}, 
						   {Text:"<s:message code='META.HEADER.CODEGAP.LST.4'/>", Align:"Center"}
				 	];
				 	//No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|소스|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟|타겟
				 	//No.|상태|GAP내용|도메인ID|도메인논리명|도메인물리명|DB명|스키마명|코드|상위코드|코드명|레벨|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID|DB명|스키마명|코드|상위코드|코드명|레벨|사용여부|표시순서|기타1|기타명1|기타2|기타명2|기타3|기타명3|기타4|기타명4|기타5|기타명5|비고|적요1|적요2|코드ID
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0, ColMerge:0},
			            {Type:"Combo",   Width:150,    SaveName:"gapStatus", 		Align:"Center", Edit:0, Hidden:0},
			            {Type:"Text",   Width:150,    SaveName:"gapConts", 		Align:"Left", Edit:0, Hidden:0},
			            {Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},  
 				        {Type:"Text",   Width:120,  SaveName:"dmnLnm" , 		Align:"Left", Edit:0, Hidden:0},            
 				        {Type:"Text",   Width:120,  SaveName:"dmnPnm" , 		Align:"Left", Edit:0, Hidden:1},
 				        {Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm" , 		Align:"Left", Edit:0, Hidden:1},            
 				        {Type:"Text",   Width:120,  SaveName:"dbSchPnm" , 		Align:"Left", Edit:0, Hidden:1},
 						{Type:"Text",   Width:100,   SaveName:"cdVal"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"uppCdVal"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,   SaveName:"cdValNm"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"codeLevel"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"useYn"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"dispOrd"   , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc1"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm1"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc2"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm2"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc3"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm3"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc4"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm4"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etc5"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"etcNm5"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"rmrkCntn"  , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"outlCntn1" , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"outlCntn2" , 		Align:"Left", Edit:0, Hidden:0},                    
 				        {Type:"Text",   Width:60,   SaveName:"cdValId" , 		Align:"Left", Edit:0, Hidden:1},
 				        {Type:"Text",   Width:120,  SaveName:"tgtDbConnTrgPnm" , 		Align:"Left", Edit:0, Hidden:0},            
 				        {Type:"Text",   Width:120,  SaveName:"tgtDbSchPnm" , 		Align:"Left", Edit:0, Hidden:0},
 						{Type:"Text",   Width:100,  SaveName:"tgtCdVal"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtUppCdVal"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:100,  SaveName:"tgtCdValNm"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtCodeLevel"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtUseYn"     , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtDispOrd"   , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc1"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm1"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc2"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm2"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc3"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm3"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc4"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm4"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtc5"      , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtEtcNm5"    , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtRmrkCntn"  , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtOutlCntn1" , 		Align:"Left", Edit:0, Hidden:0},        
 				        {Type:"Text",   Width:60,   SaveName:"tgtOutlCntn2" , 		Align:"Left", Edit:0, Hidden:0},                    
 				        {Type:"Text",   Width:60,   SaveName:"tgtCdValId" , 		Align:"Left", Edit:0, Hidden:1}           

					];
                    
        InitColumns(cols);
        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
         FitColWidth();  
        //SetExtendLastCol(1);  
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet2);    
    //===========================
   
}




function doAction(sAction)
{
        
    switch(sAction)
    {
        
                    
        case "Search":
        	var param = $('#frmSearch').serialize();

        	if($("#cdValTypCd").val() =="SMP"){
        	    if($("#gapDcd").val() =="MD"){
        	       grid_sheet.DoSearch("<c:url value="/meta/gap/getCodeGapMetaDev.do" />", param);
        	    }else if($("#gapDcd").val() =="TR"){
        	       grid_sheet.DoSearch("<c:url value="/meta/gap/getCodeGapMetaReal.do" />", param);
        	    }else if($("#gapDcd").val() =="MT"){
        	       grid_sheet.DoSearch("<c:url value="/meta/gap/getCodeGapMetaTsf.do" />", param);
        	    }else if($("#gapDcd").val() =="DR"){
        	       grid_sheet.DoSearch("<c:url value="/meta/gap/getCodeGapDevReal.do" />", param);
        	    }
        	}else if($("#cdValTypCd").val() =="CMP"){
        		if($("#gapDcd").val() =="MD"){
         	       grid_sheet2.DoSearch("<c:url value="/meta/gap/getCmpCodeGapMetaDev.do" />", param);
         	    }else if($("#gapDcd").val() =="TR"){
         	       grid_sheet2.DoSearch("<c:url value="/meta/gap/getCmpCodeGapMetaReal.do" />", param);
         	    }else if($("#gapDcd").val() =="MT"){
         	       grid_sheet2.DoSearch("<c:url value="/meta/gap/getCmpCodeGapMetaTsf.do" />", param);
         	    }else if($("#gapDcd").val() =="DR"){
         	       grid_sheet2.DoSearch("<c:url value="/meta/gap/getCmpCodeGapDevReal.do" />", param);
         	    }
        	}    

        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2ExcelBuffer(true);  
            grid_sheet2.Down2ExcelBuffer(true);
            if($("#cdValTypCd").val() =="SMP"){
        	   if(grid_sheet.SearchRows()>0){
        	   	grid_sheet.Down2Excel({FileName:'smpcodegap_list',SheetName:'<s:message code="SMPL.CD.GAP.ANLY" />', HiddenColumn:1, Merge:1}); /*단순코드GAP분석*/
        	   }
        	}else if($("#cdValTypCd").val() =="CMP"){
        	   if(grid_sheet2.SearchRows()>0){
        	   	grid_sheet.Down2Excel({FileName:'cmpcodegap_list',SheetName:'<s:message code="COEX.CD.GAP.ANLY" />', HiddenColumn:1, Merge:1}); /*복잡코드GAP분석*/
        	   }
        	}

        	grid_sheet.Down2ExcelBuffer(false);   
        	grid_sheet2.Down2ExcelBuffer(false);
            break;
    }       
}
 



//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
			
			
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
    
	//if(row < 1) return;
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
// 	var subjLnm = grid_sheet.GetCellValue(row, "subjLnm");	
	
// 	window.open().location.href = "../model/pdmtbl_lst.do?subjLnm="+subjLnm;
	
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
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
function grid_sheet2_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}


// function grid_sub_meta_OnSearchEnd(code, message, stCode, stMsg) {
// 	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
// 	if(code < 0) {
// 		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
// 		return;
// 	}
// }

// function grid_sub_dbconntrg_OnSearchEnd(code, message, stCode, stMsg) {
// 	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
// 	if(code < 0) {
// 		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
// 		return;
// 	}
// }






</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SUBJ.TRRT.INQ" /></div> <!-- 주제영역 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <input type="hidden" id="bpdmRow" name="bpdmRow" readonly="readonly"/>
            <input type="hidden" id="bddlRow" name="bddlRow" readonly="readonly"/>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody> 
                   <tr>
                         <th scope="row" class=""><label for="gapDcd"><s:message code="GAP.COMPARE.TRGT.SOUR.TARG" /></label></th> <!-- GAP비교대상(소스/타겟) -->
				           <td>
				            <select id="gapDcd" class="" name="gapDcd">
				             <option value="MD"><s:message code="META.VS.DEV.DB" /></option> <!-- 메타 VS 개발DB -->
				             <option value="TR"><s:message code="META.TFCT.VS.OPR.DB" /></option> <!-- 메타이관 VS 운영DB -->
				             <option value="MT"><s:message code="META.VS.META.TFCT" /></option> <!-- 메타 VS 메타이관 -->
<!-- 				             <option value="DR">개발DB VS 운영DB</option> -->
				            </select>
				           </td>
                          <th scope="row" class=""><label for="cdValTypCd"><s:message code="CD.PTRN" /></label></th> <!-- 코드유형 -->
	                      <td>
				            <select id="cdValTypCd" class="" name="cdValTypCd">
				             <option value="SMP"><s:message code="SMPL.CD" /></option> <!-- 단순코드 -->
				             <option value="CMP"><s:message code="COEX.CD" /></option> <!-- 복잡코드 -->
				            </select>
				           </td>
				          
                   </tr>                           
                       <tr>                               
                           <th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="dmnLnm" id="dmnLnm" class="wd200" />
                                </span>
                            </td>
<!--                             <th scope="row" class=""><label for="dbSchId">DB/스키마정보</label></th> -->
<!-- 	                      <td> -->
<%-- 				            <select id="dbConnTrgId" class="" name="dbConnTrgId"> --%>
<!-- 				             <option value=""><s:message code="CHC" /></option> 선택 --> 
<%-- 				            </select> --%>
<%-- 				            <select id="dbSchId" class="" name="dbSchId"> --%>
<!-- 				             <option value=""><s:message code="CHC" /></option>  선택 --> 
<%-- 				             </select> --%>
<!-- 				           </td> -->
                           <th scope="row"><label for="gapStatus"><s:message code="STS" /></label></th> <!-- 상태 -->
                            <td>
                                <select id="gapStatus" class="" name="gapStatus"> 
                                        <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
                                        <option value="NML"><s:message code="NROM" /></option> <!-- 정상 -->
                                        <option value="GAP">GAP</option> 
                                 </select>
                            </td>
                            </tr>
                            <tr>
                             <th scope="row" id = "codeth1"><label for="codePnm"><s:message code="L.M.S.CLCD"/></label></th> <!-- 대/중/소  분류코드 -->
                            <th scope="row" id = "codeth2" style="display:none;"><label for="codePnm"><s:message code="COEX.CD" /></label></th> <!-- 복잡코드 -->
                            <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="codePnm" id="codePnm" class="wd200" />
                                </span>
                            </td>
                            </tr>
                           
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "700px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet2", "100%", "700px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title"><s:message code="SUBJ.TRRT.NM" /> : <span></span></div> <!-- 주제영역명 -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

<!-- 	 선택 레코드의 내용을 탭처리... --> 
<!-- 	<div id="tabs"> -->
<!-- 	  <ul> -->
<!-- 	    <li><a href="#tabs-1">상세정보</a></li> -->
	    
<!-- 	  </ul> -->
<!-- 	  <div id="tabs-1"> -->
<!-- 			 	상세정보 ajax 로드시 이용 --> 
<%-- 			<%@include file="codegap_dtl.jsp" %> --%>
			
<!-- 				상세정보 ajax 로드시 이용 END --> 
<!-- 	  </div> -->
	  
<!-- 	 </div> -->
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>