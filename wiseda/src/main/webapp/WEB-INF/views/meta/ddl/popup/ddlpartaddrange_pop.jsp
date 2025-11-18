<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title>Range 범위 추가</title>

<script type="text/javascript">

var popRqst = "${search.popRqst}";
var colLen = "${colLen}";

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//$("#popApply").button("option", "label", "유효값 추가");
		$("#popApply").text("적용");
      
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  }).hide();
        
        if (popRqst == 'Y') {
	        // 적용 Event Bind
	        $("#popApply").click(function(){ 
					doAction("Apply");
			}).show();
	        $("#popDelete").click(function(){ 
	        	/* if(checkDelIBS (grid_sheet, "<s:message code="REQ.NO.DEL" />")) {
					doAction("DelRqst");
		    	} */
			}).hide(); 
        }
        
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmSearch]")[0].reset();
		});
        
    	
//     	//$( "#tabs" ).tabs();
    	
    	
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });

     // 엑셀내리기 Event Bind
       $("#popExcelDown").click( function(){ doAction("Down2Excel"); } ).hide();
    	
    	
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).load(function() {
// 	alert('window.load');
//	initGrid();
	//$(window).resize();
	//페이지 호출시 처리할 액션...
	
	
	
	//doAction('Search');
	
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	//setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});



		 
function doAction(sAction)
{
        
    switch(sAction)
    {

        	
        	
        case "Apply": //적용버튼 액션...
			
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
//         	var retval = getibscheckjoin(grid_pop, "stwdId");
//         	alert(retval);

			var rangeType = $("#frmSearch #rangeType").val();
			var strTxt = $("#frmSearch #strTxt").val();
			var endTxt = $("#frmSearch #endTxt").val();
			var colLen = $("#frmSearch #colLen").val();
			
			strTxt = strTxt.replace("-","").replace("-","");
			endTxt = endTxt.replace("-","").replace("-","");
			//alert(strTxt + "/"+endTxt);
			//var listDate = [];
			//getDateRange(strTxt,endTxt,listDate);
			var reg = /^[0-9]+$/g;
			var regrst = reg.test(strTxt+endTxt);
        	
			if(rangeType=="YYYY"){
				//alert("년도");
				
				if(strTxt.length < 4||endTxt.length < 4){
					alert("시작값, 종료값을 정확히 입력해주세요.\n예)2018");
					return;
				}
				if(!regrst){
					alert("시작값과 종료일은 숫자만 입력 가능합니다.\n예)2018");
					return;
				}
				
				strTxt = strTxt.substring(0,4);
				endTxt = endTxt.substring(0,4);
				for(var i=strTxt;i<=endTxt;i++){
					var name = "P"+i;
					var value = i;
					value++;
					
					var returnVal = value+"0101";
					if(colLen>0){
						returnVal = returnVal.substring(0,colLen);
					}
					parent.returnPopAddRange(name,returnVal);
					
				}
			}else if(rangeType=="YYYYMM"){
				if(strTxt.length < 6||endTxt.length < 6){
					alert("시작값, 종료값을 정확히 입력해주세요.\n예)201801");
					return;
				}
				if(!regrst){
					alert("시작값과 종료일은 숫자만 입력 가능합니다.\n예)201801");
					return;
				}
				//alert("년월");
				strTxt = strTxt.substring(0,6);
				endTxt = endTxt.substring(0,6);
				//getListMonthDiff(strTxt,endTxt);
				
				//while 돌려서 하자 그냥...
				while(strTxt<endTxt+1){
					name = "P"+strTxt;
					
					year = strTxt.substring(0,4);
					month = strTxt.substring(4,6);
					
					month++;
					
					if(month>12){
						month = "1";
						year++;
					}
					
					if(month<10){
						month = "0"+month;
					}
					
					strTxt = year+month;
					value = strTxt;
					
					var returnVal = value+"01";
					if(colLen>0){
						returnVal = returnVal.substring(0,colLen);
					}
					
					parent.returnPopAddRange(name,returnVal);
				}
				
			}else if(rangeType=="YYYYMMDD"){
				if(strTxt.length < 8||endTxt.length < 8){
					alert("시작값, 종료값을 정확히 입력해주세요.\n예)20180101");
					return;
				}
				if(!regrst){
					alert("시작값과 종료일은 숫자만 입력 가능합니다.\n예)20180101");
					return;
				}
				//alert("년월일");
				strTxt = strTxt.substring(0,8);
				endTxt = endTxt.substring(0,8);
				
				//getListDateDiff(strTxt,endTxt);
				//while 돌려서 하자 그냥...
				
				
				while(strTxt<endTxt+1){
					name = "P"+strTxt;
					
					year = parseInt(strTxt.substring(0,4));
					month = parseInt(strTxt.substring(4,6));
					day	= parseInt(strTxt.substring(6,8));
					day++;
					
					if(month==4||month==6||month==9||month==11){
						if(day>30){
							month++;
							day="1";
						}
					}else if(month==2){
						if(year%4==0 && year%100!=0 || year%400==0){
							if(day>29){
								month++;
								day="1";
							}
						}else{
							if(day>28){
								month++;
								day="1";
							}
						}
					}else{
						if(day>31){
							month++;
							day="1";
						}
					}
					
					if(month>12){
						month = "1";
						year++;
					}
					
					if(month<10){
						month = "0"+month;
					}
					
					if(day<10){
						day = "0"+day;
					}
					
					strTxt = year+""+month+""+day;
					value = strTxt;
					
					parent.returnPopAddRange(name,value);
				}
			}else if(rangeType=="YYYYQ"){
				//alert(strTxt.substring(5,6));
				if(strTxt.length < 6
					||endTxt.length < 6
					||strTxt.substring(4,5)!='Q'
					||endTxt.substring(4,5)!='Q'
					){
					alert("시작값, 종료값을 정확히 입력해주세요.\n예)2018Q1,2018Q4");
					return;
				}
				
				if(!(strTxt.substring(5,6)=='1'||strTxt.substring(5,6)=='2'||strTxt.substring(5,6)=='3'||strTxt.substring(5,6)=='4')
					||!(endTxt.substring(5,6)=='1'||endTxt.substring(5,6)=='2'||endTxt.substring(5,6)=='3'||endTxt.substring(5,6)=='4')
					){
					alert("분기는 1~4 사이 값을 입력해주세요.\n예)2018Q1,2018Q4");
					return;
				}
				
				strTxt = strTxt.substring(0,4)+strTxt.substring(5,6);
				endTxt = endTxt.substring(0,4)+endTxt.substring(5,6);
				//getListMonthDiff(strTxt,endTxt);
				//alert(strTxt + "/" + endTxt)
				
				while(strTxt<endTxt+1){
					name = "P"+strTxt.substring(0,4)+"Q"+strTxt.substring(4,5);
					
					year = strTxt.substring(0,4);
					qt = strTxt.substring(4,5);
					month = "";
					
					if(qt=="1"){
						month = "04";
					}else if(qt=="2"){
						month = "07";
					}else if(qt=="3"){
						month = "10";
					}else if(qt=="4"){
						month = "01";
					}
					
					qt++;
					
					if(qt>4){
						qt = "1";
						year++;
					}
					
					strTxt = year+qt;
					value = year+month;
					
					var returnVal = value+"01";
					if(colLen>0){
						returnVal = returnVal.substring(0,colLen);
					}
					
					parent.returnPopAddRange(name,returnVal);
				}
				
			}
			
			//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        
        	break;

    }       
}

function getListDateDiff(sDate,eDate){
	
	var toDate = eDate;
	var fromDate = sDate;
	
//	toDate = toDate.replace("-","");
//	fromDate = fromDate.replace("-","");
	
	var diffDate = getDateDiff(toDate,fromDate);
	if(toDate.length<8)return 9999;
	if(fromDate.length<8)return 9999;
	
	//종료년월일
	var eDateYear = parseInt(toDate.substring(0,4),10);
	var eDateMonth = parseInt(toDate.substring(4,6),10);
	if(eDateMonth<10){
		eDateMonth = "0"+eDateMonth;
	}
	
	var eDateDate = parseInt(toDate.substring(6,8),10);
	if(eDateDate<10){
		eDateDate = "0"+eDateDate;
	}

	//시작년월일
	var sDateYear = parseInt(fromDate.substring(0,4),10);
	var sDateMonth = parseInt(fromDate.substring(4,6),10);
	if(sDateMonth<10){
		sDateMonth = "0"+sDateMonth;
	}
	
	var sDateDate = parseInt(fromDate.substring(6,8),10);
	if(sDateDate<10){
		sDateDate = "0"+sDateDate;
	}
	
	//시작년월일
	var obj = new Date();
	obj.setYear(sDateYear);
	obj.setMonth(sDateMonth);
	obj.setDate(sDateDate);
	
	var ret = "";
	var name = "";
	var value = "";
	
	for(var i=0;i<diffDate;i++){
		
		var temp = getObjectTimeShift(obj,i);
		name = "P"+convDateChr(temp);
		
		var temp2 = getObjectTimeShift(obj,i+1);
		value = convDateChr(temp2);
		
		//ret = ret + value
		//alert(name + "/" + value);
		parent.returnPopAddRange(name,value);
	}
	
	return ret;
}

function convDateChr(temp)
{
	var tYear;
	var tMonth;
	var tDate;
	
	tYear = temp.getFullYear();
	tMonth = temp.getMonth();
	
	if(temp.getMonth()==0){
		tMonth = "1";
	}else{
		tMonth = tMonth+1;
	}
	if(tMonth<10){
		tMonth = "0" + tMonth;
	}
	
	
	tDate = temp.getDate();
	if(temp.getDate()<10){
		tDate = "0"+tDate;
	}
	
	value = ""+tYear+""+tMonth+""+tDate+"";
	
	return value
}

function getObjectTimeShift(pDateObj,str){
	var date = new Date(pDateObj);
	date.setMonth(date.getMonth()-1);
	//date.setTime(pDateObj.getTime() + ((str)*24*60*60*1000));
	date.setDate(pDateObj.getDate()+str);
	return date;
}


function getDateDiff(eDate,sDate){
	
//	eDate = eDate.replace("-","");
//	sDate = sDate.replace("-","");
	
	if(eDate.length<8)return 9999;
	if(sDate.length<8)return 9999;
	
//	var eDateYear = parseInt(eDate.substring(2,4),10);
	var eDateYear = parseInt(eDate.substring(0,4),10);
	var eDateMonth = parseInt(eDate.substring(4,6),10);
	var eDateDate = parseInt(eDate.substring(6,8),10);
	
//	var sDateYear = parseInt(sDate.substring(2,4),10);
	var sDateYear = parseInt(sDate.substring(0,4),10);
	var sDateMonth = parseInt(sDate.substring(4,6),10);
	var sDateDate = parseInt(sDate.substring(6,8),10);
	
	var eDate = new Date(eDateYear,eDateMonth-1,eDateDate);
	var sDate = new Date(sDateYear,sDateMonth-1,sDateDate);
	
	var differ = (((((eDate - sDate)/1000)/60)/60)/24)+1;
	
	return differ;
}

/*
function getListMonthDiff(sDate,eDate){
	
	var toDate = eDate;
	var fromDate = sDate;
	
//	toDate = toDate.replace("-","");
//	fromDate = fromDate.replace("-","");
	
	var diffDate = getMonthDiff(toDate,fromDate);
	
	if(toDate.length<6)return 9999;
	if(fromDate.length<6)return 9999;
	
	//종료년월일
	var eDateYear = parseInt(toDate.substring(0,4),10);
	var eDateMonth = parseInt(toDate.substring(4,6),10);
	if(eDateMonth<10){
		eDateMonth = "0"+eDateMonth;
	}
	var eDateDate = 15;
	
	//시작년월일
	var sDateYear = parseInt(fromDate.substring(0,4),10);
	var sDateMonth = parseInt(fromDate.substring(4,6),10);
	sDateMonth = sDateMonth - 1;
	if(sDateMonth<10){
		sDateMonth = "0"+sDateMonth;
	}
	var sDateDate = 15;
	
	//시작년월일
	var obj = new Date();
	obj.setYear(sDateYear);
	obj.setMonth(sDateMonth);
	obj.setDate(sDateDate);
	
	var ret = "";
	var name = "";
	var value = "";
	
	for(var i=0;i<diffDate;i++){
		
		var temp = getObjectMonthShift(obj,i);
		name = "P"+convMonthChr(temp);
		
		var temp2 = getObjectMonthShift(obj,i+1);
		value = convMonthChr(temp2);
		
		//ret = ret + value
		//alert(name + "/" + value);
		parent.returnPopAddRange(name,value);
	}
	
	return ret;
}

function getMonthDiff(eDate,sDate){
	
//	eDate = eDate.replace("-","");
//	sDate = sDate.replace("-","");
	
	if(eDate.length<6)return 9999;
	if(sDate.length<6)return 9999;
	
	var eDateYear = parseInt(eDate.substring(0,4),10);
	var eDateMonth = parseInt(eDate.substring(4,6),10);
	
	var sDateYear = parseInt(sDate.substring(0,4),10);
	var sDateMonth = parseInt(sDate.substring(4,6),10);
	
	var eDate = new Date(eDateYear,eDateMonth-1,15);
	var sDate = new Date(sDateYear,sDateMonth-1,15);
	
	var differ = ((((((eDate - sDate)/1000)/60)/60)/24)+1)/30;
	differ = Math.round(differ) + 1;
	
	return differ;
}

function getObjectMonthShift(pDateObj,str){
	var date = new Date(pDateObj);
	date.setMonth(date.getMonth()-1);
	date.setMonth(pDateObj.getMonth()+str);
	return date;
}

function convMonthChr(temp)
{
	var tYear;
	var tMonth;
	
	tYear = temp.getFullYear();
	tMonth = temp.getMonth();
	
	if(temp.getMonth()==0){
		tMonth = "1";
	}else{
		tMonth = tMonth+1;
	}
	if(tMonth<10){
		tMonth = "0" + tMonth;
	}
	
	value = ""+tYear+""+tMonth+"";
	
	return value
}
*/
</script>
</head>

<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">Range 범위 추가</div>
    <div class="pop_tit_close"><a>창닫기</a></div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">추가 범위</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" id="colLen" name="colLen" value="${colLen}">
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
<!-- 						<tr>
							<th scope="row"><label for="ddlTblPnm">테이블명</label></th>
							<td colspan="3"><input type="text" id="ddlTblPnm" name="ddlTblPnm" /></td>
						</tr>	 -->
						<tr>
							<th scope="row"><label for="rangeType">RANGE유형</label></th>
							<td colspan="3">
								<select id="rangeType" class="wd200" name="rangeType">
									<option value="YYYY">년도(YYYY)</option>
									<option value="YYYYMM" selected>년월(YYYYMM)</option>
									<option value="YYYYMMDD">년월일(YYYYMMDD)</option>
									<option value="YYYYQ">분기(YYYYQ1~4)</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="strTxt">시작값</label></th>
							<td><input type="text" id="strTxt" name="strTxt" /></td>
							<th scope="row"><label for="endTxt">종료값</label></th>
							<td><input type="text" id="endTxt" name="endTxt" /></td>
						</tr>
						<%-- <tr>
							<th scope="row"><label for="dataType">데이터타입</label></th>
							<td>
								<select id="dataType" class="wd100" name="dataType">
								<option value="">---전체---</option>
								<c:forEach var="code" items="${codeMap.dataTypeCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							 	</select>
							</td>
							<th scope="row"><label for="objDescn">설명</label></th>
							<td colspan="3"><input type="text" class="wd200" id="objDescn" name="objDescn" /></td>
						</tr> --%>
                   </tbody>
                 </table>   
            </div>
            </fieldset>

        	<div class="tb_comment"> * 시작값과 종료값은 '-' 없이 입력해 주시기 바랍니다.</div>
			<div class="tb_comment"> * 년월일의 경우 적용시간이 오래 걸리니 적용 버튼 클릭 후</div>
			<div class="tb_comment">&nbsp;&nbsp;&nbsp;&nbsp;팝업창이 자동으로 사라질때 까지 기다려 주시기 바랍니다.</div>
			<div class="tb_comment"> * 년월일의 경우 되도록 2년 이하로 나누어서 적용 해주세요.</div>
			<div class="tb_comment"><font color="red"> * 범위 추가 후 저장 버튼을 클릭해야 요청서가 저장 됩니다.</font></div>

        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
        <!-- 조회버튼영역  -->
		 <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
         </c:when>
         <c:otherwise>
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:otherwise>
         </c:choose>
</div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
</div>
</div>
</body>
</html>