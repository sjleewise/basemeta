
	//============================================
	// 다단계 결재 요청을 한다.
	//============================================
	function doRequest(param)
	{
		/*
		// 1. 등록요청을 하기 전에 그리드에 변경된 사항이 있는지 확인
	 	if (!chkIsDataModified()) {
	 		
	 		alert("등록요청에 변경된 내역이 있습니다. 먼저 작성완료를 하신후 등록요청 하시기 바랍니다.");
	 		return;
	 	}

	 	// 2. 등록가능이 하나라도 있는지 확인
	 	if (!chkRequestAvailable()) {
	 		
	 		alert("등록가능한 내역이 없습니다.");
	 		return;
	 	}
	 	*/
		
		var url = containerPath+"/commons/damgmt/approve/popup/approveSubmit_pop.do"; 
//		alert(url);
		openLayerPop(url, 600, 360, param);

	}
	
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	function setDispRqstMainButton(rqststepcd, objgrid)
	{		
		var rqststep = rqststepcd;
		
		switch(rqststep) {
		
		//작성전...
		case 'N': 
			//현재 등록요청 상태를 보여준다.
			$('div.npop_process div').removeClass().addClass('npop_process_01');
			
			break;
		//임시저장 : 저장 및 검증 완료
		case 'S':
			$('div.npop_process div').removeClass('npop_process_01').addClass('npop_process_02');
			
			//등록요청 버튼을 보여준다....
			$("#btnRegRqst").show();
			break;
		//등록요청 : 결재 요청 완료
		case 'Q':
			$('div.npop_process div').removeClass('npop_process_02').addClass('npop_process_03');
			
			//검증결과 탭을 숨긴다.
			$("#tabs-rqstvrf").hide();
			
			//추가, 저장, 삭제 버튼 숨기기.....
			$("button.btn_rqst_new, button.btn_save, button.btn_delete").hide();
			
			//검토처리 버튼 보여주기....
//			checkApproveYn($("#mstFrm"));
			
			//검토상태를 보여주면서 수정 가능하도록 처리...
			if(objgrid != null) {
				objgrid.SetColHidden("rvwStsCd"	,0);
				objgrid.SetColHidden("rvwConts"	,0);
				objgrid.SetColHidden("ibsCheck"	,1);
				objgrid.SetColHidden("rqstDcd"	,1);
				objgrid.SetColHidden("vrfCd"	,1);
				
				objgrid.SetColProperty("rvwStsCd", {Edit:1}	);
				objgrid.SetColProperty("rvwConts", {Edit:1}	);
			}
			break;
		//결재처리 : 승인이 완료된 상태
		case 'A':
			
			$('div.npop_process div').removeClass('npop_process_03').addClass('npop_process_04');
			
			//검증결과 탭을 보여준다.
//			$("#tabs-rqstvrf").show();
//			$("[id$='-rqstvrf']").hide();
			$("[id$='-rqstchg']").hide();
			
			//추가, 저장, 삭제 버튼 숨기기.....
			$("button.btn_rqst_new, button.btn_save, button.btn_delete").hide();
			
			//신규버튼 보여주기.....
			$("#btnBlank").show();
			
			
			//검토처리 버튼 보여주기....
//	 		$("#btnReqApprove").show();
			
			//검토상태를 보여주면서 수정 가능하도록 처리...
			if(objgrid != null) {
				objgrid.SetColHidden("rvwStsCd"	,0);
				objgrid.SetColHidden("rvwConts"	,0);
				objgrid.SetColHidden("ibsCheck"	,1);
				objgrid.SetColHidden("rqstDcd"	,1);
				objgrid.SetColHidden("vrfCd"	,1);
			}
			
//	 		objgrid.SetColProperty("rvwStsCd", {Edit:1}	);
			break;
			
		}
		
//		if(objgrid != null) {
////			objgrid.FitColWidth(); 
//		}
	}
	
	//등록요청일 경우 현재 결제레벨의 결재자인지 확인다한다.
	function checkApproveYn (mstfrm) {
		var rqststep = mstfrm.find("#rqstStepCd").val();
		
		if (rqststep != "Q") {
			return false;
		}
		$("#btnGridSave, #btnReset").hide();
		var url = containerPath+"/commons/damgmt/approve/getapproveYnbyUser.do";
		var param = mstfrm.serializeArray();
		$.getJSON(url, param, function(data){
//			alert(data);
			if(data == 'Y') {
				$("#btnReqApprove").parent().show();
			} else {
				$("#btnReqApprove").parent().hide();
			}
		});
	}
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	function setDispLstButton(step)
	{		
		//alert('1');
		//요청서 단계에 따라 저장,초기화 버튼 숨기기...
		var rqststep = step;
//	 	alert(rqststep);
		switch(rqststep) {
		
		//작성전...
		case 'N': 
			break;
		//임시저장 : 저장 및 검증 완료
		case 'S':
			break;
		//등록요청 : 결재 요청 완료
		case 'Q':
//	 		alert("qqqqq");
			//저장, 초기화 버튼 숨기기.....
//			$("#btnGridSave, #btnReset").hide();
			//검토결과 보여주기
			$(".reviewStatus").show();
			//저장, 초기화 버튼 숨기기.....
			$("button.btn_frm_save, button.btn_frm_reset").hide();
			break;
		//결재처리 : 승인이 완료된 상태
		case 'A':
			//검토결과 보여주기
			$(".reviewStatus").show();
			//저장, 초기화 버튼 숨기기.....
			$("button.btn_frm_save, button.btn_frm_reset").hide();
			break;
		}
	}

	

	 
	//============================================
	// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
	//============================================
	/**
	 * @param sheetName
	 * @param colArrRvwStsCd
	 * @returns
	 */
	function chkRvwStsCd(sheetName, colArrRvwStsCd)
	{
		var bCheck = -1;
		//공통상세코드명 가져오기 (코드, 타입(C,L), 상세코드)
//		var stsnm = getCodeName("RVW_STS_CD", "C", "0");
		//var stsnm = "검토전";
		
//		alert(stsnm);
		//var bCheck = sheetName.FindText(colArrRvwStsCd, stsnm, 0);
		
		for(var i = sheetName.HeaderRows(); i <= sheetName.RowCount(); i++)
		{
			if(sheetName.GetCellValue(i, colArrRvwStsCd ) == '0' || 
			   sheetName.GetCellValue(i, colArrRvwStsCd ) == '')
			{
				//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
				return bCheck = 1;
			}
		}
		
//		alert(bCheck);
		return bCheck;
	}
	
	function chkSheetDataModified(sheetName){
		
		var bRtn = true;
		
		if(!sheetName.IsDataModified()){
    		//alert("저장할 내역이 없습니다.");
    		bRtn = false;
    	};
    	
    	return bRtn;
	}
	
	//반려가 있으면 반려사유 입력하도록 함.
	function chkRvwCont(sheetName, colArrRvwStsCd, colArrRvwCont)
	{
		//공통상세코드명 가져오기 (코드, 타입(C,L), 상세코드)
		//var stsnm = getCodeName("RVW_STS_CD", "C", "2");
		var headrow = sheetName.HeaderRows(); 
		var datarow = sheetName.RowCount();
		
		for(var i = headrow; i < datarow+headrow; i++)
		{
			if (sheetName.GetCellValue(i, colArrRvwStsCd) == "2" && 
				sheetName.GetCellValue(i, colArrRvwCont) == "")
			{
				//alert(i + "열에 검토내용이 없습니다. 반려일 경우 검토내용을 적어주시기 바랍니다.");
				return i;
			}
		}
		return -1; 
	}
	
	//============================================
	// 전체반려시 검토상태와 검토내용 업데이트 
	//============================================
	function doAllReject (sheetName, val, val2) {
		var headrow = sheetName.HeaderRows(); 
		var datarow = sheetName.RowCount();
		for(var i = headrow; i < datarow+headrow; i++) {
			sheetName.SetCellValue(i, "rvwStsCd", val);
			sheetName.SetCellValue(i, "rvwConts", val2);
		}
	}

	//============================================
	// 전체승인/변경 으로 변경 (100건 이상처리시)
	//============================================
	function doAllApprove(sheetName, val) {

		var strPros = "";

		/*
		//전체반려가 아니고 반려 경우 별도처리(예. 엔티티+속성)
		if( btnAllReject.src.indexOf("images/btn_return.gif") != -1 ) {
			doAction("Reject");
			return;
		}
		*/

		if(val == "1")
			strPros = "승인";
		else
			strPros = "반려";
	
		var headrow = sheetName.HeaderRows(); 
		var datarow = sheetName.RowCount();
		
		for(var i = headrow; i < datarow+headrow; i++) {
			sheetName.SetCellValue(i, "rvwStsCd", val);
		}
		
		
		/*
		if(termGrid.ToTalRows > 100)
		{
			if(confirm("처리될 데이터가 많습니다. 전체목록" + strPros + "체크버튼을 이용하여 일괄적으로 적용하시겠습니까?\n'취소'를 누르면 처리구분을 바꾸는데 시간이 걸릴 수 있습니다."))
			{
				if(val == "1")
				{
					chkProsApprove.checked = true;
					chkProsReject.checked = false;
					doApproveCheck(frmInfo, 1);
				}
				else
				{
					chkProsApprove.checked = false;
					chkProsReject.checked = true;
					doApproveCheck(frmInfo, 2);
				}
			}
			else
			{
				//buttonClose();//승인처리시에 모든버튼 비활성화(다건처리시에 사용자의 오작동을 막기위해서)
				// '취소'선택시 체크를 없앤다.
				chkProsApprove.checked = false;
				chkProsReject.checked = false;

				// 루프를 돌려 모든 목록의 승인여부값을 바꾼다.
				for (var i=1;i<termGrid.ToTalRows+termGrid.HeaderRows;i++)
				{
					if(termGrid.RowEditable(i) == true){
						termGrid.CellValue(i,colArrReviewStatus) =  val;
					}
				}
			}
		}
		else
		{
			//buttonClose();//승인처리시에 모든버튼 비활성화(다건처리시에 사용자의 오작동을 막기위해서)
			for (var i=1;i<termGrid.ToTalRows+termGrid.HeaderRows;i++)
			{
				if(termGrid.RowEditable(i) == true){
					if(termGrid.CellValue(i,colStatus) == 'R'){
						termGrid.CellValue(i,colArrReviewStatus) =  val;
						termGrid.CellValue(i,colStatus) =  'R';
					}else{
						termGrid.CellValue(i,colArrReviewStatus) =  val;
					}
				}
			}
		}
		*/
		
	}
	
	
	//============================================
	// 전체승인/변경 으로 변경 (100건 이상처리시)
	// HeaderRows()가 2인 경우 사용
	//============================================
	function doAllApprove2Header(sheetName, val)
	{

		var strPros = "";

		if(val == "1")
			strPros = "승인";
		else
			strPros = "반려";
	
		for(var i = sheetName.HeaderRows(); i <= sheetName.RowCount()+1; i++)
		{
			sheetName.SetCellValue(i, "rvwStsCd", val);
		}
		
	}
	
	
	//전체반려 사유 입력 메세지 박스 
	function rejectMsgBox(msgType, msg, action)
	{
		//메세지 팝업용 div 초기화 후 진행....
		$("#divMsgPopup").remove();
		
		var sHtml = "";		
		
		sHtml += '<div id="divMsgPopup">                                                            ';
		sHtml += '<div class="pop_wrap">                                                             ';
		sHtml += '    <div class="pop_top" style="display:block;"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->           ';
		sHtml += '        <!-- 팝업 타이틀 시작 -->                                                     ';
		sHtml += '        <div id="divMsgTitle" class="pop_tit_01">첫번째 타이틀</div>                  ';
		
		if(gLangDcd == "en") { 		
			//Eglish
			sHtml += '        <div id="btnMsgBoxClose" class="pop_close"><a><span>X</span> Close</a></div> ';
		}else{
			//Korean
			sHtml += '        <div id="btnMsgBoxClose" class="pop_close"><a><span>X</span> 닫기</a></div> ';	
		}
				
		sHtml += '        <!-- 팝업 타이틀 끝 -->                                                        ';
		sHtml += '    </div>                                                                         ';
		sHtml += '        <!-- 팝업 내용 시작 -->                                                       ';
		sHtml += '       <div class="pop_container">                                                  ';
		sHtml += '            <div class="pop_cont">                                               ';
		sHtml += '            <div id="divMsgCont" style="text-align: center;"></div>               ';
		sHtml += '            <div id="divRejCont"><textarea class="wd98p" rows="3" name="rejectConts" id="rejectConts" ></textarea></div>';
		sHtml += '            <div id="divRegError" style="display:none; color:red;">반려사유를 입력하세요.</div>';
		sHtml += '            <div class="pop_bt03_57" style="margin-top: 15px;">                 ';
		
		if(gLangDcd == "en") { 		
			//Eglish
			sHtml += '                <a id="btnMsgConf">Confirm</a>                                       ';
			sHtml += '                <a id="btnMsgCancle">Cancel</a>                                     ';
		}else{
			//Korean
			sHtml += '                <a id="btnMsgConf">확인</a>                                       ';
			sHtml += '                <a id="btnMsgCancle">취소</a>                                     ';	
		}		
				
		sHtml += '            </div> ';
		sHtml += '            <div id="progressbar"></div> ';
//		sHtml += '            <div style="text-align: center; margin-top: 15px;">                 ';
//		sHtml += '                <button id="btnMsgConf" class="bt_gray">확인</button>            ';
//		sHtml += '                <button id="btnMsgCancle" class="bt_gray">취소</button>          ';
//		sHtml += '            </div>                                                              ';
		sHtml += '        </div>                                                                  ';
		sHtml += '        <!-- 팝업 내용 시작 -->                                                     ';
		sHtml += '    </div>                                                                      ';
		sHtml += '</div>                                                                          ';
		sHtml += '</div>                                                                          ';
		
		$("body").append(sHtml);
	
		//========메세지 내용 셋팅=======================
		
		$("#divMsgCont").html(msg);
		$("#divMsgCont").css("font-size","13px");
		//마스터에 있는 반려 사유 입력
//		alert($("#mstFrm #rvwConts").val());
		$("#divRejCont #rejectConts").val($("#mstFrm #rvwConts").val());
		
		//===========메세지 종류==========
		var digtit = "";
//		$("#divMsgTitle").html("전체반려");
//		digtit = "전체반려";
		$("#divMsgTitle").html("Reject all");
		digtit = "Reject all";
		
		//$("#btnMsgCancle").show(); //취소 버튼 활성화
			
		
		//========팝업 호출=======================
		$("#divMsgPopup").dialog(
	            {
	               modal: true,
	               draggable: true,
	               resizable: false,	              
	               width: 400,
	               position: { my: "center top", at: "center top+200", of: "body" },
	               title: digtit, 
	               //height: auto,
//	               istitle: false,
	               open: function(event, ui) {  	            	    
	            	   $(".ui-dialog-titlebar-close", $(this).parent()).hide();
	            	   $(".ui-dialog-titlebar", $(this).parent()).hide();  
//	            	   $(".ui-dialog-content").removeClass();
	            	   $(this).removeClass("ui-dialog-content ui-widget-content");
	               },
	               close: function( event, ui ) {
	            	   //alert("colse");
	            	   $(this).remove();
	               }
	            }
	        ); 
		 
			//이렇게 하면 페이지 스크롤이 안되잔아...
			//$('body').css('overflow','hidden');
		
		
		//=======닫기버튼 Event Bind=======
		$("#btnMsgBoxClose, #btnMsgCancle").bind("click", function(){
	    	   		    	
	    	   $("#divMsgPopup").dialog("close");   
//	    	   $("#divMsgPopup").dialog("destroy");
	    	   $("#divMsgPopup").remove();
	    });
		
		//=======확인버튼 Event Bind=======
		$("#btnMsgConf").bind("click", function(){
			
			//alert($("#divRejCont #rejectConts").val());
			if (isBlankStr($("#divRejCont #rejectConts").val())) {
				//내용을 입력하세요.
				//alert("반려사유를 입력하세요.");
				$("#divRegError").show();
				return;
			}

			//마스터 반려 및 반려사유를 셋팅한다.  
			$("#mstFrm #rvwStsCd").val("2");
			$("#mstFrm #rvwConts").val($("#divRejCont #rejectConts").val());
	    	   		    	
	    	   $("#divMsgPopup").dialog("close");   
	    	   $("#divMsgPopup").remove();
//	    	   $("#divMsgPopup").dialog("destroy");
	    	   if(typeof action == "string") { doAction(action); }
	    		else if (typeof action == "function") {action();}
	    });
				
	}
	
	

	//전체승인/전체반려 메시지 박스 
	function approveMsgBox(msgType, msg, action)
	{
		
		//메세지 팝업용 div 초기화 후 진행....
		$("#divMsgPopup").remove();
		
		var sHtml = "";		
		
		sHtml += '<div id="divMsgPopup">                                                            ';
		sHtml += '<div class="pop_wrap">                                                             ';
		sHtml += '    <div class="pop_top" style="display:block;"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->           ';
		sHtml += '        <!-- 팝업 타이틀 시작 -->                                                     ';
		sHtml += '        <div id="divMsgTitle" class="pop_tit_01">첫번째 타이틀</div>                  ';
		
		if(gLangDcd == "en") { 
			//Eglish
			sHtml += '        <div id="btnMsgBoxClose" class="pop_close"><a><span>X</span>Close</a></div> ';
		}else{
			//Korean
			sHtml += '        <div id="btnMsgBoxClose" class="pop_close"><a><span>X</span> 닫기</a></div> ';
		}
						
		sHtml += '        <!-- 팝업 타이틀 끝 -->                                                        ';
		sHtml += '    </div>                                                                         ';
		sHtml += '        <!-- 팝업 내용 시작 -->                                                       ';
		sHtml += '       <div class="pop_container">                                                  ';
		sHtml += '            <div class="pop_cont">                                               ';
		sHtml += '            <div id="divMsgCont" style="text-align: left;"></div>               ';
		sHtml += '            <div class="pop_bt03_57" style="margin-top: 15px;">                 ';
		
		if(gLangDcd == "en") { 
			//Eglish
			sHtml += '                <a id="btnMsgallappr">Approve</a>                                       ';
			sHtml += '                <a id="btnMsgallrejt">Reject</a>                                       ';
			sHtml += '                <a id="btnMsgCancle">Cancel</a>                                     ';
		}else{
			//Korean
			sHtml += '                <a id="btnMsgallappr">전체승인</a>                                       ';
			sHtml += '                <a id="btnMsgallrejt">전체반려</a>                                       ';
			sHtml += '                <a id="btnMsgCancle">취소</a>                                     ';
		}
				
		sHtml += '            </div> ';
		sHtml += '            <div id="progressbar"></div> ';
//		sHtml += '            <div style="text-align: center; margin-top: 15px;">                 ';
//		sHtml += '                <button id="btnMsgConf" class="bt_gray">확인</button>            ';
//		sHtml += '                <button id="btnMsgCancle" class="bt_gray">취소</button>          ';
//		sHtml += '            </div>                                                              ';
		sHtml += '        </div>                                                                  ';
		sHtml += '        <!-- 팝업 내용 시작 -->                                                     ';
		sHtml += '    </div>                                                                      ';
		sHtml += '</div>                                                                          ';
		sHtml += '</div>                                                                          ';
		
		$("body").append(sHtml);
		
		//========메세지 내용 셋팅=======================
		
		$("#divMsgCont").html(msg);
		$("#divMsgCont").css("font-size","13px");
		
		//===========메세지 종류==========
		var digtit = "";
		$("#divMsgTitle").html("Confirm");
		digtit = "Confirm";
		//$("#btnMsgCancle").show(); //취소 버튼 활성화
		
		//========팝업 호출=======================
		$("#divMsgPopup").dialog(
				{
					modal: true,
					draggable: true,
					resizable: false,	              
					width: 400,
					position: { my: "center top", at: "center top+200", of: "body" },
					title: digtit, 
					//height: auto,
//	               istitle: false,
					open: function(event, ui) {  	            	    
						$(".ui-dialog-titlebar-close", $(this).parent()).hide();
						$(".ui-dialog-titlebar", $(this).parent()).hide();  
//	            	   $(".ui-dialog-content").removeClass();
						$(this).removeClass("ui-dialog-content ui-widget-content");
					},
					close: function( event, ui ) {
						//alert("colse");
						$(this).remove();
					}
				}
		); 
		
		//이렇게 하면 페이지 스크롤이 안되잔아...
		//$('body').css('overflow','hidden');
		
		//=======닫기버튼 Event Bind=======
		$("#btnMsgBoxClose, #btnMsgCancle").bind("click", function(){
			
			$("#divMsgPopup").dialog("close");   
//	    	   $("#divMsgPopup").dialog("destroy");
			$("#divMsgPopup").remove();
		});
		
		//=======전체승인버튼 Event Bind=======
		$("#btnMsgallappr").bind("click", function(){
			
			$("#divMsgPopup").dialog("close");   
			$("#divMsgPopup").remove();
			
			$("#btnAllApprove").click();
		});

		//=======전체반려버튼 Event Bind=======
		$("#btnMsgallrejt").bind("click", function(){
			
			$("#divMsgPopup").dialog("close");   
			$("#divMsgPopup").remove();
			
			$("#btnAllReject").click();
		});
		
	}
	 
	//그리드의 특정 컬럼의 값이 몇개인지 카운트한다. 없을 경우 0을 리턴한다. (그리드명, saveName, value)
	function countGridValue(gridobj, saveName, cellVal) {
		var cntval = 0;
		var cntinit = gridobj.HeaderRows();
		var cnttot  = gridobj.RowCount()+gridobj.HeaderRows();
		for(var i=cntinit; i<cnttot; i++) {
			if (gridobj.GetCellValue(i, saveName) == cellVal) cntval++;
			
		}
		
		return cntval;
	}
	 
	//전체승인 및 반려 선택시 내용 업데이트...
	function chgallapprove (gridobj) {

		
		//전체승인 및 반려를 선택했는지 확인하자....
		var mstrvwsts =  $("#mstFrm #rvwStsCd").val();
		var mstrvwCont=  $("#mstFrm #rvwConts").val();
		
		
		
		if ("1" == mstrvwsts ){
//				alert("전체승인");
			doAllApprove(gridobj, "1");
//				return;
		} else if ("2" == mstrvwsts) {
//				alert("전체반려");
			
			doAllReject(gridobj, "2", mstrvwCont);
//				return;
		}
	}	 
	
	
	//등록요청일 경우 현재 결제레벨의 결재자인지 확인다한다.
	function disableBtnRqstUser ( rqstUserId, sessionRqstUserId) {
		var rqststep = $("#mstFrm").find("#rqstStepCd").val();
		
		if(rqstUserId == "") return;
		
		if (rqststep == "S" || rqststep == "N") {
						
			if(rqstUserId != sessionRqstUserId) {
						
				$("#btnRegRqst").hide();  		  		
				$("#btnMartSch").hide();
				$("#btnSave").hide();				
				$("#btnDelete").hide();				
				$("#btnRqstNew").hide();
				
				$("#btnGridSave").hide();
				$("#btnReset").hide();
				
				$("#btnCdvalRqstNew").hide();
				$("#btnCdvalSave").hide();
				$("#btnCdvalDelete").hide();
				$("#btnCdvalAllDelete").hide();
				$("#btnSecGridSave").hide();
			}else{
				
				$("#btnRegRqst").show();
				$("#btnRegRqst", parent.document).show(); 
			}			
		}
		
	}	
	
	//============================================
	// 요청단계별 sr번호/프로젝트번호, CUD여부 버튼 ... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	//============================================
	function setDispRqstSrMngNoButton(rqststepcd)
	{		
		var rqststep = rqststepcd;
		switch(rqststep) {
		//작성전...
		case 'N': 
			$("#prjSearchPop").show(); 
			$("#prjbtnDelPop").show(); 
			$("#prjAllApply").hide(); 
			$("#aplReqDtAllApply").hide(); 
			$("#cudAllApply").hide(); 
			break;
		//임시저장 : 저장 및 검증 완료
		case 'S':
			$("#prjSearchPop").show(); 
			$("#prjbtnDelPop").show(); 
			$("#prjAllApply").show(); 
			$("#aplReqDtAllApply").show();
			$("#cudAllApply").hide(); 
			break;
		//등록요청 : 결재 요청 완료
		case 'Q':
			$("#prjSearchPop").hide(); 
			$("#prjbtnDelPop").hide(); 
			$("#prjAllApply").hide(); 
			$("#aplReqDtAllApply").hide();
			$("#cudAllApply").hide(); 
			break;
		//결재처리 : 승인이 완료된 상태
		case 'A':
			$("#prjSearchPop").hide(); 
			$("#prjbtnDelPop").hide(); 
			$("#prjAllApply").hide(); 
			$("#aplReqDtAllApply").hide();
			$("#cudAllApply").hide(); 
			break;
		}
	}
	 