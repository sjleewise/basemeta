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
<title>연계테스트</title>

<script type="text/javascript">

$(document).keypress(function(e) {
	  if(e.which == 13) {
		  //$("#dmnLnm").focus();
		  return false;
	  }
	});
	
var tmp ="";
$(document).ready(function(){
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){

		var checkTermNm = $("#frmInput #checkTermNm").val();
		var checkTermNmEn = $("#frmInput #checkTermNmEn").val();
		var searchVal = $("#frmInput #searchVal").val();
		var searchCon = $("#frmInput #searchCon").val();
		var searchModel = $("#frmInput #searchModelNm").val();
		var searchSubject = $("#frmInput #searchSubjectNm").val();
		
		var reqXml ="<?xml version='1.0' encoding='utf-8'?>\n<REQUEST>\n<ROW>\n";
		reqXml += "<CHECKTERMNM>"+checkTermNm+"</CHECKTERMNM>\n";
		reqXml += "<CHECKTERMNMEN>"+checkTermNmEn+"</CHECKTERMNMEN>\n";
		reqXml += "<SEARCHVAL>"+searchVal+"</SEARCHVAL>\n";
		reqXml += "<IN_MODELNM>"+searchModel+"</IN_MODELNM>\n";
		reqXml += "<IN_SUBJECTNM>"+searchSubject+"</IN_SUBJECTNM>\n";
		reqXml += "<SEARCHCON>"+searchCon+"</SEARCHCON>\n</ROW>\n</REQUEST>";

		tmp="item";
		alert(reqXml);
		
		searchItem(reqXml);
	});
	
	$('#btnGridSave2').click(function(event){

		var inEntityNm = $("#frmInput2 #inEntityNm").val();
		var inTableNm = $("#frmInput2 #inTableNm").val();
		var inColumnSeq = $("#frmInput2 #inColumnSeq").val();
		var inTermNm = $("#frmInput2 #inTermNm").val();
		var inTermNmEn = $("#frmInput2 #inTermNmEn").val();
		var inDomainNm = $("#frmInput2 #inDomainNm").val();
		var inLogicalDataType = $("#frmInput2 #inLogicalDataType").val();
		var inPhysicalDataType = $("#frmInput2 #inPhysicalDataType").val();
		var inAttributeid = $("#frmInput2 #inAttributeid").val();
		var inModelnm = $("#frmInput2 #inModelNm").val();
		var inSubjectnm = $("#frmInput2 #inSubjectNm").val();
		
		var reqXml ="<?xml version='1.0' encoding='utf-8'?>\n<REQUEST>\n<ROW>\n";
		reqXml += "<IN_ENTITYNM>"+inEntityNm+"</IN_ENTITYNM>\n";
		reqXml += "<IN_TABLENM>"+inTableNm+"</IN_TABLENM>\n";
		reqXml += "<IN_COLUMNSEQ>"+inColumnSeq+"</IN_COLUMNSEQ>\n";
		reqXml += "<IN_TERMNM>"+inTermNm+"</IN_TERMNM>\n";
		reqXml += "<IN_TERMNMEN>"+inTermNmEn+"</IN_TERMNMEN>\n";
		reqXml += "<IN_INFORTYPE>"+inDomainNm+"</IN_INFORTYPE>\n";
		// reqXml += "<IN_DOMAINNM>"+inDomainNm+"</IN_DOMAINNM>\n";
		reqXml += "<IN_LDATATYPE>"+inLogicalDataType+"</IN_LDATATYPE>\n";
		reqXml += "<IN_DATATYPE>"+inPhysicalDataType+"</IN_DATATYPE>\n";
		reqXml += "<IN_ATTRIBUTEID>"+inAttributeid+"</IN_ATTRIBUTEID>\n";
		reqXml += "<IN_MODELNM>"+inModelnm+"</IN_MODELNM>\n";
		reqXml += "<IN_SUBJECTNM>"+inSubjectnm+"</IN_SUBJECTNM>\n";
		reqXml += "</ROW>\n</REQUEST>";
		
		tmp="gap";
		alert(reqXml);
		
		searchItem(reqXml);
	});

});

function searchItem(reqXml){
	
	 var vUrl = "/wiseda/intf/StndTermLst.do";
	 
//	 var param = "trdGb="+tmp+"&reqXml="+encodeURI(encodeURIComponent(reqXml));
	 var param = "trdGb="+tmp+"&reqXml="+encodeURI(reqXml);
	 
	 $.ajax({
		  type: "POST",			  
		  url: vUrl,
		  dataType: "xml",
		  //async: false,
		  data: replacerXssParam(param),
		  success: function(res){		     
			alert($(res).text());	
			//alert(new XMLSerializer().serializerToString(res));				
			console.log(res);
		  },
		  error: function(res){
			  alert("다시 시도하여 주시기 바랍니다.");
			 // alert(res.data);
		  }
	 });		
}


</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div_item" style="width:500px">
	<form id="frmInput" name="frmInput" method="post">
	<div style="clear:both; height:10px;"><span></span></div>
	<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit">표준조회</div>
		<div style="clear:both; height:5px;"><span></span></div>
	<legend>표준조회</legend>
	<div class="tb_basic" id="tb_item" >
		<table border="0" cellspacing="0" cellpadding="0" summary="표준조회">
			<caption>표준조회 정보입력</caption>
			<colgroup>
			<col style="width:40%;" />
			<col style="width:60%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require"><label for="checkTermNm">표준용어명 검색여부</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="checkTermNm" name="checkTermNm" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="checkTermNmEn">표준영문용어명 검색여부</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="checkTermNmEn" name="checkTermNmEn" value="" class="wd200" />
					</td>
				</tr><tr>
					<th scope="row" class="th_require"><label for="searchVal">검색값</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="searchVal" name="searchVal" value="조회단어" class="wd200" />
					</td>
				</tr><tr>
					<th scope="row" class="th_require"><label for="searchCon">검색종류</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="searchCon" name="searchCon" value="A:%%, F:뒤%, B:앞%, E:=" class="wd200" />
					</td>
				</tr>
 				<tr>
					<th scope="row" class="th_require"><label for="searchModelNm">모델명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="searchModelNm" name="searchModelNm" value="" class="wd200" />
					</td>
				</tr> 
 				<tr>
					<th scope="row" class="th_require"><label for="searchSubjectNm">주제영역명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="searchSubjectNm" name="searchSubjectNm" value="" class="wd200" />
					</td>
				</tr> 
			</tbody>
		</table>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divInputBtn" style="text-align: center;">
           <button class="btn_frm_save" type="button" id="btnGridSave" name="btnGridSave">조회</button> 
    </div>
	</form>
</div>
<div id="input_form_div_gap" style="width:500px">
	<form id="frmInput2" name="frmInput2" method="post">
	<div style="clear:both; height:10px;"><span></span></div>
	<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit">GAP조회</div>
		<div style="clear:both; height:5px;"><span></span></div>
	<legend>GAP조회</legend>
	<div class="tb_basic" id="tb_item" >
		<table border="0" cellspacing="0" cellpadding="0" summary="GAP조회">
			<caption>GAP조회 정보입력</caption>
			<colgroup>
			<col style="width:40%;" />
			<col style="width:60%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require"><label for="inEntityNm">모델엔티티명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inEntityNm" name="inEntityNm" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inTableNm">모델테이블명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inTableNm" name="inTableNm" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inColumnSeq">모델컬럼순서</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inColumnSeq" name="inColumnSeq" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inTermNm">모델속성명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inTermNm" name="inTermNm" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inTermNmEn">모델컬럼명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inTermNmEn" name="inTermNmEn" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inDomainNm">모델속성도메인</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inDomainNm" name="inDomainNm" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inLogicalDataType">모델논리데이터타입</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inLogicalDataType" name="inLogicalDataType" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inPhysicalDataType">모델데이터타입</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inPhysicalDataType" name="inPhysicalDataType" value="" class="wd200" />
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="inAttributeid">모델속성ID</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inAttributeid" name="inAttributeid" value="" class="wd200" />
					</td>
				</tr>
 				<tr>
					<th scope="row" class="th_require"><label for="inModelNm">모델명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inModelNm" name="inModelNm" value="회원사업_통합회원" class="wd200" />
					</td>
				</tr> 
 				<tr>
					<th scope="row" class="th_require"><label for="inSubjectNm">주제영역명</label></th>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" id="inSubjectNm" name="inSubjectNm" value="TEST" class="wd200" />
					</td>
				</tr> 
			</tbody>
		</table>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divInputBtn" style="text-align: center;">
           <button class="btn_frm_save" type="button" id="btnGridSave2" name="btnGridSave2">조회</button> 
    </div>
	</form>
</div>
</body>
</html>
