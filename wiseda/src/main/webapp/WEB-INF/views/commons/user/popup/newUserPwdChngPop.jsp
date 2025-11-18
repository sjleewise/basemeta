<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<html>
<head>
<title><s:message code="USER.INFO.CHG" /></title> <!-- 사용자 정보변경 -->
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rsa.js" />'></script>
<script type="text/javascript">

var interval = "";
// var usergJson = ${codeMap.userglist} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
// 		alert("document.ready");
	    //alert(usergTypCd);
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//버튼 초기화...
		//$("#btnTreeNew, #btnSave, #btnDelete").hide();
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
       // $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
       // $("#btnNew").click(function(){ doAction("New");  }); 

        // 저장 Event Bind
         $("#btnSave").click(function(){ doAction("Save");  }); 

        // 삭제 Event Bind
         //$("#btnDelete").click(function(){ doAction("Delete");  }); 
        
         // 엑셀내리기 Event Bind
        //$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
       // $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );  
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//alert("15.10.29");
        	parent.closeLayerPop();
        	//iframe 형태의 팝업일 경우
        	/* if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	} */
        	
        });
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
      //  setautoComplete($("#frmSearch #userNm"), "USRNM");
        
      //폼 저장 버튼 초기화...
    	$('#btnGridSave').button({
    	       icons: {
    	          primary: "ui-icon-disk"
    	        }
    	}).click(function(event){
    		event.preventDefault();  //브라우저 기본 이벤트 제거...
    		
    		if($("#frmSearch #bfPassword").val() == '') {
    	        alert("<s:message code='MSG.ACHN.PWD.INPT' />"); /* 변경후 비밀번호를 입력하세요. */
    	        
        		$("#frmSearch #bfPassword").val('');
        		$("#frmSearch #afPassword").val('');
        		
    	        return false;
    	    }
    		if($("#frmSearch #afPassword").val() == '') {
    	        alert("<s:message code='MSG.PWD.CNFR.INPT' />"); /* 비밀번호 확인을 입력하세요. */
    	        
        		$("#frmSearch #bfPassword").val('');
        		$("#frmSearch #afPassword").val('');
        		
    	        return false;
    	    }
    		
    		if($("#frmSearch #bfPassword").val() != $("#frmSearch #afPassword").val()) {
    	        alert("<s:message code='MSG.PWD.NOT.MATH' />"); /* 비밀번호가 다릅니다. */
    	        
        		$("#frmSearch #bfPassword").val('');
        		$("#frmSearch #afPassword").val('');
        		
    	        return false;
    	    }
    		
    		//문자, 숫자, 특수문자를 포함한 9자리 이상여부 체크
    		 if(!isValidPassword($("#frmSearch #bfPassword").val())) {
   		    	alert("<s:message code='MSG.PWD.ENSN.LTRS' />"); /* 비밀번호는 영문자, 숫자, 특수문자를 포함한 9자 이상입니다. */
   		        return false;
   		    }

    	    $('#securedUsername').val($('#frmSearch #userId').val());
    	    $('#securedPassword').val($('#frmSearch #afPassword').val());
     	     //alert($('#securedUsername').val());
     	     //alert($('#securedPassword').val());
    	    
     	    				
    	     //$("#secureFrm").attr("action", "<c:url value='/commons/user/usrInfoChng.do'/>").submit();
    	     
    	     //ajax로 변경 이상익 20151211 callback function 아래쪽에 신규생성
    	     var param =  $("#secureFrm").serialize();
    	     var url  = "<c:url value='/commons/user/usrInfoChng.do'/>";
    	     ajax2Json(url, param, callback);
    	     
    	     //alert("수정되었습니다.");     //function으로 변경
    	     //parent.closeLayerPop();
    	     
    	     
    		/* //IBSheet 저장용 JSON 전역변수 초기화
    		ibsSaveJson = null;
    		
    		//변경한 시트 단건 내용을 저장...
//     		alert("단건저장");
    		//폼 검증...
    		//if(!$("#frmSearch").valid()) return false;
    		showMsgBox("INF", res.RESULT.MESSAGE);
    		//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'SaveRow'); */
    				
    		
    		
    	});
    	//폼 초기화 버튼 초기화...
    	$('#btnReset').button({
    	       icons: {
    	          primary: "ui-icon-power"
    	        }
    	}).click(function(event){
    		event.preventDefault();  //브라우저 기본 이벤트 제거...

    		$("#frmSearch #bfPassword").val('');
    		$("#frmSearch #afPassword").val('');
    		
    	});
       
    }
);


function callback(){
	alert("<s:message code='MSG.REVS.LOIN' />"); /* 수정되었습니다. 다시 로그인 해주세요. */
    parent.logout();
}

function doAction(sAction)
{

    switch(sAction)
    {		    
              	
        case "Save" :
           	//TODO 공통으로 처리...

        	/* var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/cmvw/user/userReglist.do"/>";
//         	var param = "commDcdNm=test";
            IBSpostJson(url, param, ibscallback); */
        	break; 
              

    }       
}


//문자, 숫자, 특수문자를 포함한 9자리 이상여부
function isValidPassword(pw){
	var check = /^(?=^.{9,}$)(?=.*\d)(?=.*[a-zA-Z])/;
	
	// 문자+숫자+9자리이상 여부
	if(!check.test(pw)){
		return false;
	}
	
	// 특수문자 여부
	if(!(new RegExp(/[^a-z|^0-9]/gi)).test(pw) && !(new RegExp(/[\^]/)).test(pw)){
		return false;
	}
	
	return true;
}
 


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/



function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}


</script>
</head>

<body>

<div class="pop_tit" > <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="PWD.CHG" /></div> <!-- 비밀번호 변경 -->
    <div class="pop_tit_close"><a href="#"><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- <div class="pop_tit_close"><a href="#">창닫기</a></div> -->
	  <div class="pop_content">


<!-- 메뉴 메인 제목 -->
<%-- <div style="clear:both; height:5px;"><span></span></div> --%>

<!-- 검색조건 입력폼 -->
<div id="search_div"> 
        <div class="stit"><s:message code="USER.INFO"/></div> <!-- 사용자정보 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INFO.CHG' />"> <!-- 사용자정보변경 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                  </colgroup>
                   
                   <tbody> 
                   		<tr>                         
                     	<th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th> <!-- 사용자명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="userNm" id="userNm" value="${loginVO.name}" style="width:98%" readonly/>
                                </span>
                            </td>  

                         <th scope="row"><label for="usergId"><s:message code="USER.ID" /></label></th> <!-- 사용자ID -->
                         	<td>
                                <span class="input_file">
                                <input type="text" name="userId" id="userId" value="${loginVO.id}" style="width:98%" readonly/>
                                </span>
                            </td>
                         </tr>
                         <tr>
                         <th scope="row"><label for="bfPassword"><s:message code="ACHN.PWD" /></label></th> <!-- 변경후비밀번호 -->
                            <td>
                                <span class="input_file">
                                <input type="password" name="bfPassword" id="bfPassword" style="width:98%" />
                                </span>
                            </td>  
                         <th scope="row"><label for="afPassword"><s:message code="PWD.CNFR" /></label></th> <!-- 비밀번호확인 -->
                         	<td>
                                <span class="input_file">
                                <input type="password" name="afPassword" id="afPassword" style="width:98%" />
                                </span>
                            </td>
                         </tr>                         
                    </tbody>
                 </table>   
            
            </fieldset>
			<!-- 문자, 숫자, 특수문자를 포함한 9자리 이상여부 -->
            <div class="tb_comment">
             <br/><s:message code="MSG.LOIN.USER.USE.PWD.CHG" /><br/><br/> <!-- 처음 로그인하는 사용자의 경우 사용할 비밀번호를 변경하여야 합니다. -->
          - <s:message code="MSG.PWD.ENSN.SET" /><br/> <!-- 비밀번호는 영문자, 숫자, 특수문자(!@#$%^&*?등)를 각 1자이상 포함하여 9자리 이상으로 설정합니다. -->
		</div>
           </form>  
           <form id="secureFrm" name="secureFrm" method="post">
				<input type="hidden" name="securedUsername" id="securedUsername" /> 	
				<input type="hidden" name="securedPassword" id="securedPassword" /> 	
			</form>       
         <!-- 조회버튼영역  -->
         <div style="clear:both; height:20px;"><span></span></div>
         <page:applyDecorator id="buttonRqstInput" name="buttonRqstInput" />

</div>
</body>
</html>