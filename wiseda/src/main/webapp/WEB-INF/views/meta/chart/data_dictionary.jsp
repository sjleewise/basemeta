<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>
<c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set>
<html>
<head>
<title>DATA_DICTIONARY</title>
<style class="cp-pen-styles">
body {
    overflow-x: hidden;
}
.circle {
  stroke:#182c4d;
  fill:#182c4d;
  z-index: 1;
}
.rect
{
  stroke:#b3c8d8;
  stroke-width:1;
  fill:#d4e3ee;
  z-index: 3;
}
.circleText
{
  fill: #e2e7f4;
  font-weight: 300;
  line-height: 1.2;
  font-family: 'Noto Sans KR', Noto Sans KR, Malgun Gothic, dotum, gulim, Verdana, sans-serif !important;
  letter-spacing: -0.01em;
}
.rectText
{
  fill: #475c78;
  font-weight: 500;
  line-height: 1.2;
  font-family: 'Noto Sans KR', Noto Sans KR, Malgun Gothic, dotum, gulim, Verdana, sans-serif !important;
  letter-spacing: -0.01em;
  z-index: 4;
}
line
{
  stroke:black;
  stroke-width:2;      
}
.table{
	stroke-width : 0;
}			
</style>
<script src='../../js/d3/d3.v3.min.js'></script>
<script>
var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
                
    	doAction("Search");  
  
});

function doAction(sAction)
{
        
    switch(sAction)
    {
        
                    
        case "Search":
        	drawERD();
        	break;
       

    }       
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmSearch #subjLnm").val(retjson.fullPath);
	
}

function drawERD(){
	 
	var data = JSON.parse('${data}');	// 데이터를 저장할 배열을 준비
	 
	var dataEntity = [ ];	// 데이터를 저장할 배열을 준비
	var dataRelation =[];
	 
	if(data==null){
		return;
	}
	 
	// 데이터 각각 넣기 	
	for(var i=0; i<data.MemberEntity.length; i++){	// 데이터 줄 수만큼 반복
		dataEntity.push(data.MemberEntity[i]);	 
	} 
	
	for(var i=0; i<data.MemberRelation.length; i++){	// 데이터 줄 수만큼 반복
		dataRelation.push(data.MemberRelation[i]);	 
	} 

	var w = $( window ).width();
	var h = $( window ).height();
	 
	var clear = d3.select("#erdDiv").html("");
	var svg = d3.select("#erdDiv").append("svg").attr("width", w).attr("height", h).style("margin-left","20px");

	var per = w/1920;//해상도에 따른 비율 조정
	 
	//관계선	
 	var relationGroup = svg.selectAll("svg.relationGroup")
		.data(dataRelation)
		.enter()
		.append("svg")
		.attr("id", function (d, i) {return "reation" + i;})
		.attr("x", 0)
		.attr("y", 0)
		.attr("class", "relation");
		
	var  relationMainGroup = relationGroup.append("g")
		.attr("id", function (d, i) {
		  return "r" + i + "RelataionGroupMain";
		});

	var  lineGroup = relationMainGroup.append("g")
		.attr("id", function (d, i) {
		  return "r" + i + "RelationLine";
		});
	
	var  terminateGroup = relationMainGroup.append("g")
		.attr("id", function (d, i) {
		  return "r" + i + "TerminatePoint";
		});


	var linkedLine = lineGroup.selectAll("g.linkedline")
		.data(function (d,i) {return d.Linked_Line;})
		.enter()
	    .append("line")
		.attr("x1",  function(d,i){	 
			return  d.x1*per;	 
		})
		.attr("y1",  function(d,i){	 
			return  d.y1*per; 
		})
		.attr("x2",  function(d,i){	
			return  d.x2*per; 
		})
		.attr("y2",  function(d,i){	 
			return  d.y2*per;  
		})
		.attr("stroke-dasharray",  function(d,i){	 
			var result  ="4"	
			if( d.Identifying == "2" ) {
				result = "0";
			}	
			return  result;  
		})
		.attr("z-index", -99)
		.attr("class", "rel_line"); 
	 
	var termBaseLine = terminateGroup.append("line")
		.attr("x1",  function(d,i){	 
			return  d.Terminate_Point.Base_Line.x1*per;	 
		})
		.attr("y1",  function(d,i){	 
			return d.Terminate_Point.Base_Line.y1*per;	 
		})
		.attr("x2",  function(d,i){	
			return d.Terminate_Point.Base_Line.x2*per; 
		})
		.attr("y2",  function(d,i){	 
			return d.Terminate_Point.Base_Line.y2*per;	 
		})
		.attr("class", "terminate_base");

	  	 
	var termOneBarLine = terminateGroup.append("line")
		.attr("x1",  function(d,i){	 
			return  d.Terminate_Point.One_Bar.x1*per;	 
		})
		.attr("y1",  function(d,i){	 
			return d.Terminate_Point.One_Bar.y1*per;	 
		})
		.attr("x2",  function(d,i){	
			return d.Terminate_Point.One_Bar.x2*per; 
		})
		.attr("y2",  function(d,i){	 
			return d.Terminate_Point.One_Bar.y2*per;	 
		})
		.attr("display",  function(d,i){	
			var result = "none"; 
			if (d.Identifying != "9"  &&  d.Cardinality != "-1" ) {
				result = "display";
			}
			return result;	 
		})
		.attr("class", "terminate_one");
	
	// 사각형
	var tableGroup = svg.selectAll("svg.tableGroup")
		.data(dataEntity)
		.enter()
		.append("svg")
		.attr("id", function (d, i) {return "tableGroup" + i;})
		.attr("x", function(d,i){	// 넓이를 지정. 두 번째의 파라미터에 함수를 지정
			return d.Top_X*per;	// 데이터 값을 그대로 넓이로 반환
		})
		.attr("y", function(d, i){	// Y 좌표를 지정함
			return  d.Top_Y*per;	// 표시 순서에 25를 곱해 위치를 계산
		})
		.attr("class", "table");
	 
	 var rect = tableGroup.selectAll("g.tableColumn")
		.data(function (d,i) {return d.Attribute})
		.enter()
		.append("rect")
		.attr("class", "rect")
		.attr("fill","white")
		.attr("width",120*per)
		.attr("height",40*per)
	 	.attr("x", 110*per)
		.attr("y", function (d, i) {
			var val = 40;
			return (val + i * 50)*per;
		})
		.on("click", function(d) {
			if(d.URL!=""&&typeof(d.URL)!="undefined"){
				location.href = '<c:url value="'+d.URL+'" />';
			}
		})
		.style("cursor",function(d){
			if(d.URL!=""&&typeof(d.URL)!="undefined"){
				return "pointer";
			}
		});
	 
	 tableGroup.selectAll("g.tableColumn")
		.data(function (d,i) {return d.Attribute})
		.enter()
		.append("text")
		.text(function (d) {
				  return d.Name;
			})
		.attr("x", 170*per)
		.attr("y", function (d, i) {
			var val = 63;
			return (val + i * 50)*per;
		})
		.on("click", function(d) {
			if(d.URL!=""&&typeof(d.URL)!="undefined"){
				location.href = '<c:url value="'+d.URL+'" />';
			}
		})
		.style("cursor",function(d){
			if(d.URL!=""&&typeof(d.URL)!="undefined"){
				return "pointer";
			}
		})
		.attr("text-anchor", "middle")
		.attr("class", "rectText")
		.style("font-size",13*per+"px");
	 
	 //원
	 var circle = tableGroup.append("circle")
		.attr("r", function(d,i){	 
			return 50*per;	 
		})
		.attr("cx", 50*per)
		.attr("cy", function(d){
			return (d.Attribute.length*25+25)*per;
		})
		.attr("class", "circle")
		.style("stroke-width", function(d) {
		    if (d.Name=="") {
		        return 0;
		    } else {
		        return 1;
		    }
		})
		.style("opacity", function(d) {
		    if (d.Name=="") {
		        return 0;
		    } else {
		        return 1;
		    }
		});
	 	
	 	tableGroup
		.append("text")
		.attr("x", 50*per)
		.attr("y", function(d){
				return (d.Attribute.length*25+30)*per;
			})
		.attr("text-anchor", "middle")
		.text(function (d,i) {
			return d.Name;
		})
		.attr("class", "circleText")
		.style("font-size",14*per+"px");
	
}

</script>
</head>
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
        <div class="stit" style="display: none;"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post" >
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2" style="display: none;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역 조회"> <!-- 주제영역조회 -->
                   <caption>테이블 이름</caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:10%;">
                   <col style="width:30%;">
                   <col style="width:10%;">
                   <col style="width:17%;">
                   <col style="width:10%;">
                   <col style="width:17%;">
                   <col style="width:6%;">
                   </colgroup> 
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="subjLnm">주제영역명</label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340 ui-autocomplete-input" autocomplete="off"><span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
                                		<button class="btnDelPop" style="display: none;">삭제</button> <!-- 삭제 -->
		                                <button class="btnSearchPop" id="subjSearchPop">검색</button> <!-- 검색 -->
                                </span>
                            </td>
                            <th scope="row"><label for="veiwType">veiwType</label></th>
                            <td>
                                <select id="veiwType" class="" name="veiwType">
                                        <option value="default">default</option>
                                        <option value="none">none</option>
                                 </select>
                            </td>
                       		<th scope="row"><label for="logicalOrPhysical">논리모델/물리모델</label></th> <!-- 개인정보여부 -->
                            <td style="border-right: none;">
                                <select id="logicalOrPhysical" class="" name="logicalOrPhysical">
                                        <option value="Logical">논리모델</option>
                                        <option value="Physical">물리모델</option>
                                 </select>
                            </td>
                            <td>
	                             <!-- 조회버튼영역  -->
								<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
							</td>
                       </tr>
                   </tbody>
                 </table>
            </div>
            </fieldset>
        </form>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- d3 입력 입력 -->
	<div id="erdDiv" class="grid_01" style="min-height: 600px;"></div>
	<!-- d3 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
</div>
</body>
</html>