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

var initLnkUrl = "";
var totalStep = 4;
var STEP = 1;
var CONNTEST_YN = "N";
var IS_SAVE_SCH_END = false;

var dbmsTypeVersJson = ${codeMap.dbmsversibs};
//이건 어떤 방법으로 처리 해야 좋을까나?! ... 필수입력:inputId, 옵션:param
//lnkUrl 순서와 inputId는 맞아야 함 		
var dbmsJdbcJson= {"ALT" : [{dbmsVersCd:"", drvrNm: "Altibase.jdbc.driver.AltibaseDriver", lnkUrl: "jdbc:Altibase://{ip}:{port}/{sid}", inputId: ["ip","port","sid"]}
						  , {dbmsVersCd:"AL5", drvrNm: "Altibase5.jdbc.driver.AltibaseDriver", lnkUrl: "jdbc:Altibase://{ip}:{port}/{sid}", inputId: ["ip","port","sid"]}
					]
				, "CBR" : [{dbmsVersCd:"", drvrNm: "cubrid.jdbc.driver.CUBRIDDriver", lnkUrl: "jdbc:cubrid:{ip}:{port}:{dbName}:public::", inputId: ["ip","port","dbName"], param: ["charset"]}]
				, "ORA" : [{dbmsVersCd:"", drvrNm: "oracle.jdbc.driver.OracleDriver", lnkUrl: "jdbc:oracle:thin:@{ip}:{port}:{sid}", inputId: ["ip","port","sid"]}
					, {dbmsVersCd:"R08", drvrNm: "oracle8.jdbc.driver.OracleDriver", lnkUrl: "jdbc:oracle:thin:@{ip}:{port}:{sid}", inputId: ["ip","port","sid"]}
				 	]
				, "SYQ" : [{dbmsVersCd:"", drvrNm: "com.sybase.jdbc3.jdbc.SybDriver", lnkUrl: "jdbc:sybase:Tds:{ip}:{port}/{dbName}", inputId: ["ip","port","dbName"]}]
				, "SYA" : [{dbmsVersCd:"", drvrNm: "com.sybase.jdbc4.jdbc.SybDriver", lnkUrl: "jdbc:sybase:Tds:{ip}:{port}/{dbName}", inputId: ["ip","port","dbName"]}]
				, "DB2" : [{dbmsVersCd:"", drvrNm: "com.ibm.db2.jcc.DB2Driver", lnkUrl: "jdbc:db2://{ip}:{port}/{dbName}", inputId: ["ip","port","dbName"]}
					     , {dbmsVersCd:"DA4", drvrNm: "com.ibm.as400.access.AS400JDBCDriver", lnkUrl: "jdbc:as400://{ip}/{dbName}", inputId: ["ip","dbName"]}
					]
				, "MSA" : [{dbmsVersCd:"", drvrNm: "net.ucanaccess.jdbc.UcanaccessDriver", lnkUrl: "jdbc:ucanaccess:{mdbRoot}", inputId: ["mdbRoot"]}]
				, "MSQ" : [{dbmsVersCd:"", drvrNm: "com.microsoft.sqlserver.jdbc.SQLServerDriver", lnkUrl: "jdbc:sqlserver://{ip}:{port};DatabaseName={dbName}", inputId: ["ip","port","dbName"]}]
				, "MYS" : [{dbmsVersCd:"", drvrNm: "com.mysql.jdbc.Driver", lnkUrl: "jdbc:mysql://{ip}:{port}/{dbName}", inputId: ["ip","port","dbName"]}]
				, "POS" : [{dbmsVersCd:"", drvrNm: "org.postgresql.Driver", lnkUrl: "jdbc:postgresql://{ip}:{port}/{dbName}", inputId: ["ip","port","dbName"]}]
				, "TIB" : [{dbmsVersCd:"", drvrNm: "com.tmax.tibero.jdbc.TbDriver", lnkUrl: "jdbc:tibero:thin:@{ip}:{port}:{dbName}", inputId: ["ip","port","dbName"]}]
				, "MRA" : [{dbmsVersCd:"", drvrNm: "com.mysql.jdbc.Driver", lnkUrl: "jdbc:mysql://{ip}:{port}/{dbName}", inputId: ["ip","port","dbName"]}]
				, "MSA" : [{dbmsVersCd:"", drvrNm: "net.ucanaccess.jdbc.UcanaccessDriver", lnkUrl: "jdbc:ucanaccess://{mdbRoot}", inputId: ["mdbRoot"]}]
				, "HAN" : [{dbmsVersCd:"", drvrNm: "com.sap.db.jdbc.Driver", lnkUrl: "jdbc:sap://{ip}:{port}/?databaseName={dbName}&user=={userName}&password={password}", inputId: ["ip","port","dbName","userName","password"]}]
				};

$(document).ready(function() {
	
    	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function() {
        	popupClose();
        });

        $("#btnHelp").click(function() {
    		var url = "<c:url value='/meta/admin/popup/infosys_help.do?typCd=' />" + "db"; 
    		OpenWindow(url, "dbConnInfo_help", 990, 593, null);
        });

		$("#btnNextStep").click(function(event) {

			if(!validateStep() ) return false;

			if(STEP == 1) {

				if(!isBlankStr($("#frmDbInput #pdataExptRsn").val()) && $("#frmDbInput #pdataExptRsn").val() != "1") {
					
					//저장할래요? 확인창...
		    		var message = "수집제외사유 항목을 사유없음으로 선택 시 다음단계로 이동할 수 있습니다. DB기본정보만 등록하고 종료하겠습니까?";
		    		showMsgBox("CNF", message, 'subSaveDbInfo');

		    		return ;
				}
			}else if(STEP == 2 ) {

	 			if( ($("#ibsStatus").val() == 'I' &&  isBlankStr( $("#dbConnTrgId").val() ))
						|| ($("#ibsStatus").val() == "U" && CONNTEST_YN == "N")) {
					
					showMsgBox("ERR", "접속테스트를 하세요.");
					isValid = false;
					return isValid;
				}

 				if(CONNTEST_YN == "Y" && isBlankStr($("#dbLnkSts").text())) {

 					if(isBlankStr($("#frmDbInput #dbConnTrgId").val())) return;

					doAction("DbConnTestResult");
 		        	return ;
				}

				if(CONNTEST_YN == "Y" && !isBlankStr($("#dbLnkSts").text()) && $("#dbLnkSts").text() != "성공") {
					showMsgBox("ERR", "접속테스트 결과가 실패되었습니다. 접속테스트를 다시 하세요.");
					return false;
				}

				if($("#bfGpucFsvrId").val() != $("#gpucFsvrId").val()) {
		    		doAction("SaveGpucFsvrId");
				}
			}else if(STEP == 3) {
				//event.preventDefault();
	        	var rows = grid_dbsch.IsDataModified();
	        	
	        	if(!validateStep() && !rows) {
	        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
	        		return;
	        	}

	        	if(rows) {

					//스키마 중복체크
	        		var DupRow = grid_dbsch.ColValueDupRows("dbConnTrgId|dbSchPnm|dbSchLnm");
	        		
	    			if(DupRow != "") {
	        			showMsgBox("ERR", "<s:message code='MSG.SAME.SCHEMA.NM.EXIS' />"); 
	    				return;
	    			}
		        	
	        		//저장할래요? 확인창...
		    		//var message = "<s:message code="CNF.SAVE" />";
		    		//showMsgBox("CNF", message, 'SubSave');	
		    		doAction("DbSchSave");

		    		return ;
		        }
			}
			
			if(totalStep == STEP ) return false;
			STEP++;
			initBtnStep();
		});

		$("#btnPrevStep").click(function(event) {

			if(STEP == 1) return false;
			STEP--;
			initBtnStep();

			if(STEP == 3) {
				//초기화 
        		IS_SAVE_SCH_END = false;
			}
			
		});

    	SboxSetLabelEvent();
});

$(window).on('load',function() {
	
	initBtnStep();
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
});

function popupClose() {

	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.closeLayerPop();
	} else {
		window.close();
	}
}

/** .btn_search버튼 disabled 처리 */
function setBtnDisabled(btnObj, isDisabled) {

	var rmClassNm = isDisabled ? "btn_search" : "btn_noHover";
	var addClassNm = isDisabled ? "btn_noHover" : "btn_search";

	btnObj.attr("disabled", isDisabled);
	btnObj.removeClass(rmClassNm);
	if(!btnObj.hasClass(addClassNm)) {
		btnObj.addClass(addClassNm);
	}
}

function validateStep() {

	var checkObjIdArr = [];
	var isValid = true;
	var msgText = "";
	
	switch(STEP) {
		case 1 :

			checkObjIdArr = [ "dbConnTrgLnm", "aplyBizNm", "dbmsTypCd", "dbmsVersCd", "osVerNm"
			                 <c:if test='${search.ibsStatus eq "U" and (result.cenmYn == null or result.cenmYn eq "N")}'>, "orgCd", "infoSysCd", "dbConnTrgPnm", "pdataExptRsn"</c:if> ];
					
			$.each(checkObjIdArr, function(idx, id) {
				if(isBlankStr($("#"+id).val())) {
					isValid = false;
					return isValid;
				}
			}); 

			if(!isValid) {
				showMsgBox("ERR", "필수 항목을 입력하지 않으면 다음단계로 넘어 갈 수 없습니다.");
				return;
			}

			if(!containKOR($("#dbConnTrgLnm").val())) {
				
				showMsgBox("ERR","논리DB명은 한글을 포함하여 입력하세요.");   
				isValid = false;
				return;
			}

			if($("#aplyBizNm").val().length < 10) {
				showMsgBox("ERR", "적용업무 10자 이상 상세하게 기재하세요.");
				isValid = false;
				return;
			}

			//구축일자 형식 
			if(!isBlankStr($("#constDt").val())) {
				
				var inputDt = $("#constDt").val().replace(/-/g,"");
				
				var validFormat = /^\d{4}\d{2}\d{2}$/;

				if(!validFormat.test(inputDt)) {
					showMsgBox("ERR", "날짜 형식이 올바르지 않습니다. YYYYMMDD");
					isValid = false;
					return;
				}else {

					//var df = inputDt.val().replace(/-/g,"");
					var inputYy = inputDt.substr(0, 4);
					var inputMm = (inputDt.substr(4, 1) == "0" ? inputDt.substr(5, 1) : inputDt.substr(4, 2))-1;
					var inputDd = inputDt.substr(6, 2);


					var resultDate = new Date(inputYy,inputMm,inputDd);

					if(resultDate.getFullYear() != inputYy) {
						showMsgBox("ERR", "날짜 형식이 올바르지 않습니다.");

						isValid = false;
						return;
					}

					if(resultDate.getMonth() != inputMm) {
						showMsgBox("ERR", "날짜 형식이 올바르지 않습니다. 월(01~12)");

						isValid = false;
						return;
					}
					if(addZero(resultDate.getDate()) != inputDd) {
						showMsgBox("ERR", "날짜 형식이 올바르지 않습니다. 일(01~31)");

						isValid = false;
						return;
					}
				}
			}
			
			break;
			
		case 2 :
			checkObjIdArr = ["connTrgLnkUrl","dbConnAcId","dbConnAcPwd"]; //,"gpucFsvrId" 행공센 


			$.each($("input[id^=connTrgLnkUrl_]:not(.param)"), function(idx, selObj) {
				if(isBlankStr(selObj.value)) {
				
					//selObj.focus();
					msgText = "연결URL입력정보("+selObj.name.toUpperCase()+")를 입력하세요.";
					isValid = false;
					return isValid;
				}
		     });

			if(!isValid) {
				showMsgBox("ERR", msgText);
				return;
			}

			$.each(checkObjIdArr, function(idx, id) {
				if(isBlankStr($("#"+id).val())) {
					msgText = "모든 정보를 입력하세요.";
					isValid = false;
					return isValid;
				}
			});

			if(!isValid) {
				showMsgBox("ERR", msgText);
				return;
			}
			
			break;
		case 3 :
			if(grid_dbsch.GetTotalRows() == 0) {
				showMsgBox("ERR", "테이블소유자 정보를 입력하세요.");
				isValid = false;
				return isValid;
			}

			break;
		case 4:
			checkObjIdArr = ["shdStrDtm", "shdStrHr", "shdStrMnt"];
			
			$.each(checkObjIdArr, function(idx, id) {
				if(isBlankStr($("#"+id).val())) {
					smsgText = "필수 항목을 입력하세요.";
					isValid = false;
					return isValid;
				}
			});

			if(!isValid) {
				showMsgBox("ERR", msgText);
				return;
			}
			break;
	}

	return isValid;
}

/* 단계별 버튼 및 div, 현재 단계 초기화 및 설정 */
function initBtnStep() {

	$("#divColBtn").removeClass("btn01 btn02 btn03");
	
	switch(STEP) {
		case 1 :

			$("#divColBtn #btnPrevStep").hide();
			$("#divColBtn #btnComplete").hide();
			$("#divColBtn #btnNextStep").show();
			
			$("#divColBtn").addClass("btn01");
		break;

		case 2 : 
			$("#divColBtn #btnPrevStep").show();
			$("#divColBtn #btnComplete").hide();
			$("#divColBtn #btnNextStep").show();

			$("#divColBtn").addClass("btn02");

			var dbmsTypNm = isBlankStr($("#dbmsTypCd").val()) ? "" : $("#dbmsTypCd").find('option:selected').text() ;
			var dbmsVersNm = isBlankStr($("#dbmsVersCd").val()) ? "" : $("#dbmsVersCd").find('option:selected').text() ;
				
			$("#dbmsTypNm").val(dbmsTypNm + " " + dbmsVersNm);

			if($("#frmDbInput #dbLnkSts").text() == "성공") {
				CONNTEST_YN = "Y";

				setBtnDisabled($("#btnSubConn"), true);
				
				//임시$("#dbConnAcId,#dbConnAcPwd").attr("readonly", true);
				//$("#gpucFsvrId").attr("disabled",true);
				
				//임시if(!$("#dbConnAcId,#dbConnAcPwd").hasClass("d_readonly")) {
				//임시	$("#dbConnAcId,#dbConnAcPwd").addClass("d_readonly");
				//임시}
			}
			
		break;

		case 3 : 
			
			$("#divColBtn #btnPrevStep").show();
			$("#divColBtn #btnComplete").hide();
			$("#divColBtn #btnNextStep").show();

			$("#divColBtn").addClass("btn02");

			//그리드 초기화
			//grid_dbsch.RemoveAll();
			if(typeof grid_dbsch != "undefined") {
				initdbschgrid();
				doAction("SubSearchSch");
			}
			
		break;

		case 4 : 
			$("#divColBtn #btnPrevStep").show();
			$("#divColBtn #btnComplete").show();
			$("#divColBtn #btnNextStep").hide();

			$("#divColBtn").addClass("btn02");

			$("#frmShdDtlInput #shdTypCd").change();

		break;
	}

	$(".dbconStep_nav ul li").removeClass("on");
	$(".dbconStep_nav ul li:eq("+(STEP-1)+")").addClass("on");

	$("div[id^=conStep_0]").hide();
	$("#conStep_0"+STEP).show();
}

//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
	 switch(sAction)
	 {    

	 	case "subSaveDbInfo":        //dB정보만 등록
    	 	var saveJson = getform2IBSjson($("#frmDbInput"));
        	
        	var url = "<c:url value="/meta/mta/popup/regDbConnTrgInfo.do"/>";
         	var param = "";
         	
         	IBSpostJson2(url, saveJson, param, ibscallback);
         break;
	     
	     case "subSaveDbConn":        //dB정보 저장... CONNTEST

	    	 	<c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>
	    	 	$("#frmDbInput #orgCd,#infoSysCd,#pdataExptRsn").attr("disabled", false);
	    	 	//2019.03.07 전수조사자료이고 수집제외사유가 빈값이면 사유없음 처리 
	    	 	if(isBlankStr($("#pdataExptRsn").val())) {
	    	 		$("#pdataExptRsn").val("1");
		    	}
	    	 	</c:if>
	    	 	var saveJson = getform2IBSjson($("#frmDbInput"));
	    	 	<c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>
	    	 	$("#frmDbInput #orgCd,#infoSysCd,#pdataExptRsn").attr("disabled", true);
	    	 	</c:if>

	        	var url = "<c:url value="/meta/mta/popup/regDbConnAndTest.do"/>";
	         	var param = "";
	         	
	         	IBSpostJson2(url, saveJson, param, ibscallback);

	       	break;

	    case "DbConnTestResult": //DBLNKSTS
		    	//메뉴ID를 토대로 조회한다....
	        	var param = "dbConnTrgId="+$("#frmDbInput #dbConnTrgId").val();  
	        	var url   = '<c:url value="/meta/mta/popup/dbLnkStsTestResult.do"/>';
	        	
	        	IBSpostJson(url, param, callbackDbConnTestResult);
		    break; 

       	case "SaveGpucFsvrId": //DBLNKSTS
	    	//메뉴ID를 토대로 조회한다....
        	var param = "gpucFsvrId="+$("#frmDbInput #gpucFsvrId").val() + "&dbConnTrgId="+$("#frmDbInput #dbConnTrgId").val();  
        	var url   = '<c:url value="/meta/mta/popup/saveGpucFsvrId.do"/>';
        	
        	IBSpostJson(url, param, callbackSaveResult);
	    break; 
		    
			//스케줄정보등록
	     case "subSaveShd":

		     	var shdTypeCd = $("#frmShdDtlInput #shdTypCd").val();
	    	 	//매주 check 값 코드화 
  		    	var schWeekOm = "";
  		    	
  		    	if(shdTypeCd == "W") {

  		    		var schdWklCheckArr = $("form[name=frmShdDtlInput] input:checkbox[name=schdWkl]");
  	  		    	
  					schWeekOm = Number("0");
  					for(var i=0; i<schdWklCheckArr.length; i++) {
  	  					
  						if( $("#"+schdWklCheckArr[i].id).is(":checked") ) {
  							schWeekOm += Number(schdWklCheckArr[i].value);
  						}
  					}
  		    	}

  		    	$("#frmShdDtlInput #shdWkl").val(schWeekOm);
   		    	
	    	 	var saveJson = getform2IBSjson($("#frmShdInput"));
	        	
	        	var url = "<c:url value="/meta/mta/dbconntrg/popup/ajaxgrid/insertSchedule.do"/>";
	        	
	         	var param = $("#frmShdDtlInput").serialize()
	         		param += "&orgCd="+$('#orgCd').val()+"&dbConnTrgId="+$('#dbConnTrgId').val();
	         			//param += "&shdMny="+schMonOm+"&shdWkl="+schWeekOm;
         			//param += "&shdWkl="+schWeekOm;
	         			
	         	IBSpostJson2(url, saveJson, param, ibscallback);
	    		
		     break;

		     //스케줄 즉시실행()
    	case "subSaveOneShd":

    			setBtnDisabled($("#btnBatchStart"), true);
    				

				var saveJson = getform2IBSjson($("#frmShdInput"));
				var url = "<c:url value="/meta/mta/dbconntrg/popup/ajaxgrid/regOneSchedule.do"/>";

				var param = $("#frmShdInput").serialize()
	 	         	param += "&orgCd="+$('#orgCd').val()+"&dbConnTrgId="+$('#dbConnTrgId').val();
	 	         	
	 	         			
				IBSpostJson2(url, saveJson, param, ibscallback);
    	    		
			break;

	     case "SubSearchSch":

	    	 	var param1 = "dbConnTrgId="+$('#dbConnTrgId').val();
				grid_dbsch.DoSearch('<c:url value="/commons/damgmt/db/dbconntrg_dtl.do" />', param1);
				
		     break;

	     case "saveDbConn" :

	    		//TODO 공통으로 처리...
	        	var SaveJson = getform2IBSjson($("#frmDbInput"));
	        	//데이터 사이즈 확인...
	        	if(SaveJson.data.length == 0) return;
	        	
	        	var url = "<c:url value="/commons/damgmt/db/regconntrglist.do"/>";
	         	var param = "";
	            IBSpostJson2(url, SaveJson, param, ibscallback);

	    		/* //저장할래요? 확인창...
	    		var message = "<s:message code="CNF.SAVE" />";

	    		showMsgBox("CNF", message, 'SaveDbConn'); */
		     break;
			     
         	case "DbSchNew":        //추가
	        	var row = grid_dbsch.DataInsert(-1);
	        	
	         	grid_dbsch.SetCellValue(row, "dbConnTrgId", $('#frmDbInput #dbConnTrgId').val());
	         	grid_dbsch.SetCellValue(row, "dbConnTrgPnm",$("#frmDbInput #dbConnTrgPnm").val());
	         	grid_dbsch.SetCellValue(row, "dbConnTrgLnm",$("#frmDbInput #dbConnTrgLnm").val());
	         	grid_dbsch.SetCellValue(row, "ddlTrgYn", "Y");
	         	         	
	            break;

         	case "DbSchSave" : //SCH
             	
            	var SaveJson = grid_dbsch.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

            	//데이터 사이즈 확인...
            	if(SaveJson.data.length == 0) {

            		var message = "<s:message code="ERR.CHKSAVE" />";
    				showMsgBox("ERR", message); 
            		return;
            	}else {
                	
       				if(grid_dbsch.FindText("dbSchPnm", "", 0, -1) > 0) {
       					showMsgBox("ERR", "테이블소유자를 입력하세요.");
       					return;
       				}
                }
            	ibsSaveJson = SaveJson;
            	var url = "<c:url value="/commons/damgmt/db/regSchList.do"/>";
             	var param = "";
                //IBSpostJson2(url, SaveJson, param, ibscallback);
                
                IBSpostJson(url, param, ibscallback);
            	break;	            
	            
         	case "DbSchDelete" : //DELSCH
         		//체크된 행 중에 입력상태인 경우 시트에서 제거...
         		var checkedRowCnt = grid_dbsch.CheckedRows("ibsCheck");
            	delCheckIBS(grid_dbsch);

            	//체크박스가 입력상태인 경우 삭제...
    			if(!grid_dbsch.CheckedRows("ibsCheck")) {
    				//삭제할 대상이 없습니다...
    				if(checkedRowCnt == 0) {
	    				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
        			}
    				return;
    			}

    			ibsSaveJson = grid_dbsch.GetSaveJson(0, "ibsCheck");
    			
    			var url = "<c:url value="/commons/damgmt/db/delSchList.do"/>";
    			var param = "";
    			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
    			
            	break;        
	 }
}


//접속테스트결과 callback
function callbackDbConnTestResult(res) {
	var result = res.RESULT.CODE;
	
	if (result == 0) {
		$("#dbLnkSts").text(res.RESULT.MESSAGE);
	} else if (result == 401) {
		
		// 권한이 없어요...
		showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	} else {
		 alsert("저장실패");
		// 공통메시지 팝업 : 실패 메세지...
		showMsgBox("ERR", res.RESULT.MESSAGE);
	}
}

function callbackSaveResult(res) {
	var result = res.RESULT.CODE;
	
	if (result == 0) {

	} else if (result == 401) {
		// 권한이 없어요...
		showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	} else {
		 alsert("저장실패");
		// 공통메시지 팝업 : 실패 메세지...
		showMsgBox("ERR", res.RESULT.MESSAGE);
	}
}

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {

		case "DBREG": //DB기본정보만 저장

			// 공통메세지 팝업 : 성공 메세지...
			showMsgBox("INF", res.RESULT.MESSAGE);
			
			parent.doAction("Search");
			$("div.pop_tit_close").click();
			//$("#btnNextStep").hide();
		break;

		//접속테스트 후 처리...
		case "<%=WiseMetaConfig.IBSAction.CONNTEST%>" :

			// 공통메세지 팝업 : 성공 메세지...
			//showMsgBox("INF", res.RESULT.MESSAGE);
			$("#divMsgPopup").remove();
			
			if(res.resultVO != null ) {
				
				if(!isBlankStr(res.resultVO.dbConnTrgId) ) {
					$("#dbConnTrgId").val(res.resultVO.dbConnTrgId);
					if($("#ibsStatus").val() == "I" && isBlankStr($("#objVers").val())) {
						$("#objVers").val(1);
					}
					 
					if($("#frmShdInput #shdIbsStatus").val() == "I" ) {
						$("#frmShdInput #shdJobId").val(res.resultVO.dbConnTrgId);
					}
					
					getDbConnTestResult();
				}
			}
			
		break;

		case "SCH":
			IS_SAVE_SCH_END = true;
			doAction("SubSearchSch");
		break;

		case "DELSCH" :
			IS_SAVE_SCH_END = false;
			doAction("SubSearchSch");
			
		break;

		/* 스케줄등록 후 */
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			if(res.resultVO != null ) {

				$("#divMsgPopup").remove();
				// 공통메세지 팝업 : 성공 메세지...
				//showMsgBox("INF", "접속대상 DB정보 등록이 완료되었습니다. 스케줄 수집 상태를 확인하고 메타데이터를 등록하세요.");

				if(isBlankStr($("#frmShdInput #shdId").val()) && $("#frmShdInput #shdIbsStatus").val() == "I") {

					$("#frmShdInput #regTypCd").val("U");
					$("#frmShdInput #shdIbsStatus").val("U");
					$("#frmShdInput #shdId").val(res.resultVO.shdId);
				}

				parent.returnDbConnTrgRqstPop();
				//$("div.pop_tit_close").click();
				popupClose();
			}
			
		break;

		//배치1회성
		case "ONEREG" : 
			// 공통메세지 팝업 : 성공 메세지...
			showMsgBox("INF", res.RESULT.MESSAGE);
		break;
		default : 
			// 아무 작업도 하지 않는다...
			break;		
	}	
}
</script>

<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">접속대상DB관리-<c:choose><c:when test="${search.ibsStatus eq 'U'}">수정</c:when><c:otherwise>등록</c:otherwise></c:choose> </div>
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
    	
		<div class="dbconStep_nav"> 
			<ul class="step04">
				<li class="on">DB기본정보</li> <!-- class="on" 추가-->
				<li>접속정보</li>
				<li>DB식별자(스키마)</li>
				<li class="last">스케줄정보</li>
			</ul>
		</div>
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="bt02">
			<a id="btnHelp"><img src="<c:url value="/images/icon_help.png"/>" /></a>		   
	    </div>
		<div>
			 <div style="clear:both; height:10px;"><span></span></div>
			 <!-- <div class="stit">DB기본정보</div> --> <!-- DB기본정보 -->
			 
		     <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
		     	<form name="frmDbInput" id="frmDbInput" method="post" >
				<input type="hidden" id="ibsStatus" name="ibsStatus" value="${search.ibsStatus}"/>
				<input type="hidden" id="dbConnTrgId" name="dbConnTrgId" value="${search.dbConnTrgId}"/>
				<input type="hidden" id="objVers" name="objVers" value="${result.objVers}"/>
				
				<input type="hidden" id="cenmYn" name="cenmYn" value="${result.cenmYn}"/>
				
				<fieldset>
		     	<!-- 01 -->
		     	 <div id="conStep_01" style="display:block;">
				     <%@include file="dbconnTrgRqst_pop_dtl.jsp"%>
				</div>
				<!-- 02 -->
				<div id="conStep_02" style="display:none;">
				     <%@include file="dbconnTrgRqst_pop_conn_dtl.jsp"%> 
				</div>
				</fieldset>
				</form>
				
				<!-- 03 -->
				<div id="conStep_03" class="conStep">
						<%@include file="dbconnTrgRqst_pop_dbsch_dtl.jsp" %>
				</div>
				
				<!-- 04 -->
				<div id="conStep_04" style="display:none;">
					<%@include file="dbconnTrgRqst_pop_shd_dtl.jsp" %>
				</div> <!-- conStep_04 end -->
				
				<!-- 05 -->
				<div id="conStep_05" style="display:none;">
				</div>
		</div>		
    </div>
    <div style="clear:both; height:25px;"><span></span></div>
	<div id="divColBtn" class="pop_divColBtn" style="text-align: center;"> <%-- <span class="blind"> --%>
		<button class="btnPrevStep" id="btnPrevStep" name="btnPrevStep" type="button" style="display:none;"><s:message code="BTN.PREV.STEP"/></button><!-- 이전단계  -->
		<button class="btnComplete" id="btnComplete" name="btnComplete" type="button" style="display:none;"><s:message code="BTN.CMPL"/></button><!-- 완료 -->
		<button class="btnNextStep" id="btnNextStep" name="btnNextStep" type="button" style="display:none;"><s:message code="BTN.NEXT.STEP"/></button> <!-- 다음단계 -->
	</div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
