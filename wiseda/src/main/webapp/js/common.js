
	/* 팝업으로 열기 */
	function OpenWindow(target, wName, wWidth, wHeight, wScroll){
	  var xpos = ( screen.width -  wWidth  ) / 2; 
	  var ypos = ( screen.height - wHeight )  / 2;  
	  
	  //윈도우 이름이 틀리면 새창으로 팝업이 열리고, 같으면 이름이 같이 창이 Replace된다.
	  if (wName == null) wName = "";
	
	  var splitParam = target.split('&');
//		console.log(splitParam);
		
		var url = splitParam[0].split('?');
		
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", url[0]);
		form.setAttribute("target", wName);
		
		if(url[1].indexOf('=') > -1) {
			var input = document.createElement('input');
			input.type = 'hidden';
			input.name = url[1].split('=')[0];
			input.value = url[1].split('=')[1];
			
			form.appendChild(input);
		}
		
		for(var i=1; i<splitParam.length; i++) {
			var splitParam2 = splitParam[i].split('=');
			
			var input = document.createElement('input');
			input.type = 'hidden';
			input.name = splitParam2[0];
			input.value = splitParam2[1];
			
			form.appendChild(input);
		}
		
		
		document.body.appendChild(form);
		
		//윈도우 이름이 틀리면 새창으로 팝업이 열리고, 같으면 이름이 같이 창이 Replace된다.
		if (wName == null) wName = "";
		
		popupWindow = window.open(target,wName,'toolbar=no,status=no,top='+ ypos +',left='+ xpos +',width=' + wWidth + ',height=' + wHeight +',directories=no,scrollbars=' + wScroll + ',location=no,resizable=yes,border=0,menubar=no,status=yes');
		
		form.submit();
		document.body.removeChild(form);
		
	  
	 
	  /*
	  if (popupWindow != null) {
	   
		popupWindow.resizeTo(wWidth, wHeight);
	    popupWindow.focus();
	  }	
	  */  
	  popupWindow.focus();

	  return popupWindow;
	}
	
	/* 모달 팝업으로 열기 */
	function OpenModal(target, wName, wWidth, wHeight, wScroll){
		
		//var xpos = ( screen.width -  wWidth  ) / 2; 
		//var ypos = ( screen.height - wHeight )  / 2;
		
		
		//듀얼모니터에서 windows.open 팝업위치 조정 
		//var winWidth  = document.body.clientWidth;  // 현재창의 너비
		//var winHeight = document.body.clientHeight; // 현재창의 높이
		
		var winWidth  = $(window).innerWidth();  // 현재창의 너비
		var winHeight = $(window).innerHeight(); // 현재창의 높이
		
		//alert(winWidth);
		
		//alert( window.screenX );
		
		//var winX      = window.screenX || window.screenLeft || 0;// 현재창의 x좌표
		//var winY      = window.screenY || window.screenTop  || 0; // 현재창의 y좌표
		
		var winX = window.screenLeft;	// 현재창의 x좌표
		var winY = window.screenTop;	// 현재창의 y좌표
			
		var xpos = winX + (winWidth  - wWidth) / 2;
		var ypos = winY + (winHeight - wHeight) / 2; 
		
		var splitParam = target.split('&');
//		console.log(splitParam);
		
		var url = splitParam[0].split('?');
		
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", splitParam[0].split('?')[0]);
		form.setAttribute("target", wName);
		
		if(url[1].indexOf('=') > -1) {
			var input = document.createElement('input');
			input.type = 'hidden';
			input.name = url[1].split('=')[0];
			input.value = url[1].split('=')[1];
			
			form.appendChild(input);
		}
		
		for(var i=1; i<splitParam.length; i++) {
			var splitParam2 = splitParam[i].split('=');
			
			var input = document.createElement('input');
			input.type = 'hidden';
			input.name = splitParam2[0];
			input.value = splitParam2[1];
			
			form.appendChild(input);
		}
		
		
		document.body.appendChild(form);
		
		//윈도우 이름이 틀리면 새창으로 팝업이 열리고, 같으면 이름이 같이 창이 Replace된다.
		if (wName == null) wName = "";
		
		popupWindow = window.open(target,wName,'top='+ ypos +',left='+ xpos +',width=' + wWidth + ',height=' + wHeight +',directories=no,scrollbars=' + wScroll + ',location=no,resizable=yes,border=0,menubar=no,status=no,toolbar=no,modal=yes');
//		popupWindow = window.open(target,wName,'center=yes; width=' + wWidth + ';height=' + wHeight +';directories=no;scrollbars=' + wScroll + ';location=no;resizable=yes;border=0;menubar=no;status=no;toolbar=no;modal=yes;');
		
		form.submit();
		document.body.removeChild(form);
		
		/*
	  if (popupWindow != null) {
	   
		popupWindow.resizeTo(wWidth, wHeight);
	    popupWindow.focus();
	  }	
		 */  
		
		return popupWindow;
	}
	
	 //그리드 자동사이즈 조절, Parameter(그리드명, 그리드 TD id명(String), 하단여백, 간격) 
	 function chgSize(gridName, TdId, bottomPadding, interval)
	 {		
	 	if(interval == ""){
			gridName.FitColWidth();
	 	}else{
	 		gridName.FitColWidth(interval);	 		
	 	}
	 	
	 	gridName.SetExtendLastCol(1);	
	 		 	
	 	var tmpHeight = document.body.offsetHeight - document.getElementById(TdId).offsetParent.offsetTop - 12;
	  
	 	tmpHeight -= bottomPadding;
	    if(tmpHeight < 150) tmpHeight = 150;
	  
	    document.getElementById(TdId).height = tmpHeight ;	    
	 }
	 
	//탭 콘텐츠 영역  자동사이즈 조절, Parameter(그리드명, TAB TD id명(String), 하단여백, 간격) 
	 function chgTabContSize(tabName, TdId, bottomPadding, interval)
	 {
		
        // 탬 사이즈를 100px 로 초기화. 사이즈가 커지지만 하고, 줄어들지 않는 오류를 초기화로 보완
		tabName.SetWidth("300px");
		// tabName.SetHeight("300px");
	    
	    
	    // var tabHeight = calcTabHeightSize('tabHeight','120');
		
		var tabHeight = initTabHeightSize('tabHeight','150');
	    
	    // 다시 100% 맞춤 
	    tabName.SetWidth("100%");
	    tabName.SetHeight(tabHeight);
	    

		// tabName.SetWidth("100%"); 
		 
	    /*
	    var tmpHeight = document.body.offsetHeight - document.getElementById(TdId).offsetParent.offsetTop - 12;
	
	    tmpHeight -= bottomPadding;
	    if(tmpHeight < 150) tmpHeight = 150;
	    	    
	    document.getElementById(TdId).height = tmpHeight;
	    */
	 }
	 
	 function calcTabHeightSize(TdId, bottomPadding)
	 {      
	    var tmpHeight = document.body.offsetHeight - document.getElementById(TdId).offsetParent.offsetTop - 12;
	    	    
	    tmpHeight -= bottomPadding;
	    	   
	    if(tmpHeight < 150) tmpHeight = 150;
	   
	    tmpHeight = tmpHeight + "px";
	    
	    return tmpHeight;
	 }
	 
	 function initTabHeightSize(TdId, bottomPadding)
	 {      
	    // var tmpHeight = document.body.offsetHeight - document.getElementById(TdId).offsetParent.offsetTop - 12;
		
		var tmpHeight = window.screen.height - bottomPadding;
	    	    
	    tmpHeight -= bottomPadding;
	    	   
	    if(tmpHeight < 150) tmpHeight = 150;
	   
	    tmpHeight = tmpHeight + "px";
	    
	    return tmpHeight;
	 }
	 
	 function calcTabWidthSize(bottomPadding)
	 {     		    		
		var tabWidth = window.screen.width - bottomPadding;
			   
	    return tabWidth;
	 }
	 
	 //=======IBSheet combo 공통코드 조회=====	 
	 // param1: 코드 명
	 function getSheetComboCode(vCodeName){
		 
		 var arrCode = new Array(2);
		 
		 var sCdVal = "";
		 var sCdNm  = "";
		 
		 var vUrl = WEB_PATH + "/egmd/cmcd/cmcdcode_lst.do/selectCmcdCodeList";
		 
		 var param = "codeName=" + vCodeName;
		 
		 $.ajax({
			  type: "POST",			  
			  url: vUrl,
			  dataType: "json",
			  //async: false,
			  data: replacerXssParam(param),
			  success: function(res){
			      				
				 $.each(res, function(i, map){
					  
					if(i == 0){
						
						sCdVal = map.VALUE;	
					  	sCdNm  = map.VALUE_KNM;
					}else{
						sCdVal += "|" + map.VALUE;	
					  	sCdNm  += "|" + map.VALUE_KNM;
					}
					  						   
				 });
				  												 
			  },
			  error: function(res){
				  
				 // alert(res.data);
			  }
		 });
		 
		 arrCode[0] = sCdVal;
		 arrCode[1] = sCdNm;

		 return arrCode;
	 }	
	 
	 //=======select box 공통코드 조회=====
	 // param1: 셀렉트박스명
	 // param2: 코드 명
     // param3: <opeion value=""></option> 추가 여부 
	 function getSelectBoxCodeList(obj, vCodeName, bSel){
	
		 var vUrl = WEB_PATH + "/egmd/cmcd/cmcdcode_lst.do/selectCmcdCodeList";
		 
		 var param = "codeName=" + vCodeName;
		 
		 $.ajax({
			  type: "POST",			  
			  url: vUrl,
			  dataType: "json",
			  //async: false,
			  data: replacerXssParam(param),
			  success: function(res){
			     
				 obj.find("option").remove().end(); 
				 
				 if(bSel){
					 obj.append("<option value=\"\"></option>");						 
				 }
				  
				 $.each(res, function(i, map){
					 
					 obj.append("<option value="+ map.VALUE +">"+ map.VALUE_KNM +"</option>");					   						   
				 });
				  												 
			  },
			  error: function(res){
				  
				 // alert(res.data);
			  }
		 });		
	 }
	 
	 
	 function addOption(obj, val, txt) {
	    if (obj) {
	        var NewOption = new Option();
	        NewOption.value = val;
	        NewOption.text = txt;
	        //obj.add(NewOption);
	        
	        obj.append("<option value="+ val +">"+ txt +"</option>");
	    } 
	    return true;
	}
	 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//select박스에 option을 하나 제거 하는 합수
	//인자 :  select박스 객체
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	function removeOption(obj) {
	    if (obj) {
	        for(i=1 ; i<obj.length; i++){
	                    if(obj[i].selected == true){
	                        obj.options[i] = null;
	                    }
	        }
	    }
	    return true;
	} 
	
	
	var saveNmovejson = {func:null, param:null, url:null};
	
	var cnfNextFunc = "";
	function showMsgBox(msgType, msg, action, nexturl,Response)
	{

		if(Response!= null && Response!='undefined'){
			var json = JSON.parse(Response);
			msg += "<br>"+json.message;
		}
		
		/*if(isBlankStr(topsize)) {
			topsize = "center top+200";
		} else {
			topsize = "center top+"+topsize;
		}*/
		
		var bodyht = $("body").height();
//		alert(bodyht);
		if (bodyht < 500) topsize = "center top+20";
		else topsize = "center top+200";
		
		//메세지 팝업용 div 초기화 후 진행....
		$("#divMsgPopup").remove();
		
		var sHtml = "";		
		sHtml += '<div id="divMsgPopup">                                                            ';
//		sHtml += '<div class="pop_wrap">                                                             ';
//		sHtml += '       <div class="pop_container">                                                  ';
//		sHtml += '            <div class="pop_cont">                                               ';
		sHtml += '            <div id="divMsgCont" style="text-align: center; padding:20px 10px 0 10px"></div>               ';
		sHtml += '            <div class="pop_bt03_57" style="margin: 15px 0;">                 ';
		
		
		if(gLangDcd == "en") { 		
			//Eglish
			sHtml += '                <button class="da_default_btn" id="btnMsgConf">Confirm</button>                                       ';
			sHtml += '                <button class="da_default_btn" id="btnMsgCancle">Cancel</button>                                     ';
		}else{
			//Korean
			sHtml += '                <button class="da_default_btn" id="btnMsgConf">확인</button>                                       ';
			sHtml += '                <button class="da_default_btn" id="btnMsgCancle">취소</button>                                     ';	
		}
				
		sHtml += '            </div> ';
		sHtml += '            <div id="progressbar" style="display:none; text-align: center;"><img src="'+containerPath+'/images/loading/loading11.gif" alt="처리중"></div> ';
//		sHtml += '        </div>                                                                  ';
//		sHtml += '    </div>                                                                      ';
//		sHtml += '</div>                                                                          ';
		sHtml += '</div>  ';
		
		$("body").append(sHtml);
	
		
		
		//========메세지 내용 셋팅=======================
		
		$("#divMsgCont").html(msg);
//		$("#divMsgCont").css("font-size","13px");
		
		
		
		//===========메세지 종류==========
		var digtit = "";
		var escyn = true;	//esc close 기능
		switch(msgType){
		
		case "INF" : 
			$("#divMsgTitle").html("Information");
			digtit = "Information";
			$("#btnMsgCancle").hide(); //취소 버튼 비활성화
			if (action == "0") { 
				$("#btnMsgConf").hide(); //확인 버튼 비활성화
//					$("#progressbar" ).progressbar({
//					      value: 30
//					});
			}
			break;
		case "PRC" : 
			$("#divMsgTitle").html("Processing");
			digtit = "Processing";
			$("#btnMsgCancle").hide(); //취소 버튼 비활성화
			$("#btnMsgConf").hide(); //확인 버튼 비활성화
			$("#progressbar" ).show();
			escyn = false;
//			$("#progressbar" ).progressbar({
//				value: false
//			});
			break;
		case "ERR" : 
			$("#divMsgTitle").html("Error");
			digtit = "Error";
			$("#btnMsgCancle").hide(); //취소 버튼 비활성화
			break;	
		case "CNF" :
			$("#divMsgTitle").html("Confirm");
			digtit = "Confirm";
			$("#btnMsgCancle").show(); //취소 버튼 활성화
			break;
		case "SNF" :
			$("#divMsgTitle").html("Confirm");
			digtit = "Confirm";
			$("#btnMsgConf").text('저장 후 이동');
			$("#btnMsgCancle").text('이동').show(); //취소 버튼 활성화
			break;
		}
		
		
		//========팝업 호출=======================
		$("#divMsgPopup").dialog(              
	            {
	               modal: true,
	               draggable: true,
	               resizable: false,	              
	               width: 400,
	               position: { my: "center top", at: topsize, of: "body" },
	               title: digtit,
	               closeOnEscape: false,
	               //height: auto,
//	               istitle: false,
	               open: function(event, ui) {  	            	    
	            	   if ("PRC" == msgType) $(".ui-dialog-titlebar-close").hide();
//	            	   $(".ui-dialog-titlebar-close", $(this).parent()).hide();
	            	   $(".ui-dialog-titlebar", $(this).parent()).css('height', '30px');
	            	   $(".ui-dialog-title").css({'font-size':'13px', 'line-height':2});
//	            	   $(".ui-dialog-content").removeClass();
//	            	   $(this).removeClass("ui-dialog-content ui-widget-content");
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
		$("#btnMsgBoxClose, #btnMsgCancle").bind("click", 
			    
	    	function(){
	    	   		    	
	    	   $("#divMsgPopup").dialog("close");   
//	    	   $("#divMsgPopup").dialog("destroy");
	    	   $("#divMsgPopup").remove();
	    	   if (msgType == "SNF") {
	    		   cnfNextFunc = nexturl;
	    		   eval(cnfNextFunc);
	    	   }
	        }
	    );
		
		//=======확인버튼 Event Bind=======
		$("#btnMsgConf").bind("click", 
			    
	    	function(){
	    	   		    	
	    	   $("#divMsgPopup").dialog("close");   
	    	   $("#divMsgPopup").remove();
//	    	   $("#divMsgPopup").dialog("destroy");
	    	   if(msgType == "CNF") {
	    		   if(typeof action == "string") { doAction(action); }
	    		   else if (typeof action == "function") {action();}
	    	   } else if (msgType == "SNF") {
	    		   cnfNextFunc = nexturl;
	    		   if(typeof action == "string") { doAction(action); }
	    		   else if (typeof action == "function") {action();}
	    	   }
	    		   
	    	   //doAction("Delete");
	        }
	    );
				
	}
	
	//페이지 처리시 처리중 이미지 모달 창으로 호출한다.
	function processLoading(loadhtml) {
//		var loadhtml = '<div class="process_loading">' + 
//		'<div class="loading_img"><img src="<c:url value="/img/loading.gif"/>" alt="처리중"></div>' +
//	    '<div class="loading_txt"><img src="<c:url value="/img/loading_txt.gif"/>" alt="처리중입니다."></div>' +
//		'</div>';
//		alert(loadhtml);
		$("body").append(loadhtml);
		
		$("div.process_loading").dialog(              
	            {
	               modal: true,
	               draggable: false,
	               resizable: false,	              
	               width: 200,
	               position: { my: "center", at: "center", of: "body" },
	               //height: 200,
	               istitle: false,
//	                closeOnEscape: false,
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
	}

	//엑셀 업로드 모달 창으로 호출한다.
	function openLayerPop(url, wd,  ht, param) {
		
		if (isBlankStr(param)) {
			url = url+"?popType=I";
		} else {
			url = url+"?popType=I&" + param;
		}
		
		$("div#excel_pop").remove();
		
		var layerhtml = "<div id='excel_pop' style='display: none;'>" +
		  				"<iframe src='' frameborder='0' height='0' width='0'  scrolling='auto' ></iframe>" +
		  				"</div>";
		
		$("body").append(layerhtml);
		
		$("div#excel_pop").dialog({
					modal: true,
					draggable: true,
					resizable: false,
					width: wd,
					position: { my: "center", at: "center", of: "body" },
					height: eval(ht)+60,
					istitle: false,
//	                closeOnEscape: false,
					open: function(event, ui) {						
						$(".ui-dialog-titlebar-close", $(this).parent()).hide();
						$(".ui-dialog-titlebar", $(this).parent()).hide();
//						$(this).removeClass();
						$(this).removeClass("ui-dialog-content ui-widget-content");
//						$(".ui-dialog-content").removeClass();
					},
					close: function( event, ui ) {
						//alert("colse");
//						$('div#excel_pop iframe').attr("src", "");
						$('iframe', this).attr('src', '');
						$(this).remove();
					}
				}
		); 
		
		$('div#excel_pop iframe').attr({
			src: url,
			width: wd,
			height:ht
	});
	}
	
	//상단에 모달창 띄움
    function openLayerPop2(url, wd,  ht, param) {
		
		if (isBlankStr(param)) {
			url = url+"?popType=I";
		} else {
			url = url+"?popType=I&" + param;
		}
		
		
		var layerhtml = "<div id='excel_pop' style='display: none;'>" +
		  				"<iframe src='' frameborder='0' height='0' width='0'  scrolling='no' ></iframe>" +
		  				"</div>";
		
		$("body").append(layerhtml);
		
		$("div#excel_pop").dialog(
				{
					modal: true,
					draggable: false,
					resizable: false,	              
					width: wd,
					position: { my: "center", at: "center", of: "body" },
					height: ht,
					istitle: false,
//	                closeOnEscape: false,
					open: function(event, ui) {						
						$(".ui-dialog-titlebar-close", $(this).parent()).hide();
						$(".ui-dialog-titlebar", $(this).parent()).hide();  
//						$(this).removeClass();
						$(this).removeClass("ui-dialog-content ui-widget-content");
//						$(".ui-dialog-content").removeClass();
					},
					close: function( event, ui ) {
						//alert("colse");
//						$('div#excel_pop iframe').attr("src", "");
						$('iframe', this).attr('src', '');
						$(this).remove();
					}
				}
		); 
		
		$('div#excel_pop iframe').attr({
			src: url,
			width: wd,
			height:ht
	});
	}
	
	//레이어드 검색 팝업창 클로즈...
	 function closeLayerPop() {
//		 $('div#excel_pop iframe').attr("src", "");
		 $('div#excel_pop').dialog("close");
	 }
	
	
	//================================================
	// IBSheet 그리드 내용을 JSON으로 변경하여 ajax 처리한다.
	// 저장 결과는 callback 함수를 정의하여 처리한다.
	//================================================
	var ibsSaveJson = null; //IBS 그리드 시트 저장용 JSON 변수
	function IBSpostJson(urls, param, callback) {
		var ajaxurl = urls;
		if(param != null && param != "") {
			ajaxurl = urls + "?" +param;
		}
		
		 $.ajax({
	      url: ajaxurl,
	      async: false,
	      type: "POST",
	      data: JSON.stringify(ibsSaveJson, replacerXss),
	      contentType: 'application/json',
	      dataType: 'json', 
	      success: callback
//	      error: function (jqXHR, textStatus, errorThrown) {
//	          //alert('에러발생...' + textStatus);
//	          var res = {RESULT : {CODE:-1, MESSAGE:'시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.'}};
//				ibscallback(res);
//	      }
	  });
		
	}

	function IBSpostJson2(urls, savejson, param, callback, syncyn) {
		var ajaxurl = urls;
		if(!isBlankStr(param)) {
			ajaxurl = urls + "?" +param;
		}
		
		
		
		var syn = true;
		if (syncyn) syn = syncyn;
		
		$.ajax({
			url: ajaxurl,
			async: syn,
			type: "POST",
			data: JSON.stringify(savejson, replacerXss),
			contentType: 'application/json',
			dataType: 'json',
			beforeSend: function () {
								
				// 처리중이니 잠시 기다려 주십시요.
				showMsgBox("PRC", gMSG_PRC_WAIT);
				
				
			},
			success: callback
//			error: function (jqXHR, textStatus, errorThrown) {
//				//alert('에러발생...' + textStatus);
//				if (jqXHR.status == 403) {
//					location.href = containerPath + "/"; 
//				}
//				var res = {RESULT : {CODE:-1, MESSAGE:'시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.'}};
//				ibscallback(res);
//			}
		});
		
	}

	//================================================
	// IBSheet 그리드 내용을 JSON으로 변경하여 ajax 처리한다.
	// 저장 결과는 callback 함수를 정의하여 처리한다.
	//================================================
	function ajax2Json(urls, param, callback, syncyn) {
		var ajaxurl = urls;
		var syn = true;
		if (syncyn) syn = syncyn;
		
//		alert(syn);
//		if(param != null && param != "") {
//			ajaxurl = urls + "?" +param;
//		}
//		showMsgBox("PRC", '처리중이니 잠시 기다려 주십시요.');
		
//		setTimeout(function(){
			$.ajax({
				url: ajaxurl,
				async: syn,
				type: "POST",
				data: replacerXssParam(param),
				dataType: 'json',
			beforeSend: function () {
				//처리중입니다. 메세지
								
				// 처리중이니 잠시 기다려 주십시요.
				showMsgBox("PRC", gMSG_PRC_WAIT);
				
			},
			complete: function () {
				 $("#divMsgPopup").dialog("close");   
//		    	   $("#divMsgPopup").remove();
			},
				success: callback
//				error: function (jqXHR, textStatus, errorThrown) {
//					//alert('에러발생...' + textStatus);
//					var res = {RESULT : {CODE:-1, MESSAGE:'시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.'}};
//					ibscallback(res);
//				}
			});
//		}, 10);
		
		
	}
	
	//ajax 셋팅..
	$.ajaxSetup({
//		beforeSend: function () {
//			//처리중입니다. 메세지
//			showMsgBox("PRC", '처리중이니 잠시 기다려 주십시요.');
//		},
		error: function (jqXHR, textStatus, errorThrown) {
			//alert('에러발생...' + textStatus);
			var res ;
			//에러메시지 추가 200429
			var json;
			var errMsg ="";
			//CustomSimpleMappingExceptionResolver 통해서 들어올 경우 
			if(jqXHR.status == 499 &&jqXHR.responseText !=null && jqXHR.responseText !='' ) {
				json = JSON.parse(jqXHR.responseText);
				errMsg = "<br>"+json.message; 
			}
			if (jqXHR.status == 401) {
				
				//로그인 정보가 없습니다.<br>로그인 화면으로 이동하시겠습니까?
				res = {RESULT : {CODE:jqXHR.status, MESSAGE: gMSG_LOGIN_INFO_NOT_EXISTS}};
				
			} else {
								
				//시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.
				res = {RESULT : {CODE:-1, MESSAGE: gMSG_SYS_NO_USE + errMsg }};				
			}
			
			ibscallback(res);
		}
	});
	
	
	
	//================================================
	//IBS 공통코드 리스트를 Combo로 셋팅한다. - ajax로 처리
	//================================================
	function setComboIBS(code, typ, ibsobj, savenm) {
		//url의 컨테이너 명 하드코딩됨...
		var url = containerPath+"/commons/code/getComboIbs.do";
    	var param = {codenm:code, type:typ};
		
    	$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			success: function (data) {
				if(data){
	    			ibsobj.SetColProperty(savenm, data);
	    		}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				
				var res  = "";
				
				//시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.
				res = {RESULT : {CODE:-1, MESSAGE: gMSG_SYS_NO_USE}};		
				
				ibscallback(res);
			}
		});
	}

	//================================================
	//공통코드 리스트를 selectbox로 셋팅한다. - ajax로 처리
	//================================================
	function setCodeSelect(code, typ, selobj) {
		//url의 컨테이너 명 하드코딩됨...
		var url = containerPath+"/commons/code/getComboIbs.do";
		var param = {codenm:code, type:typ};
		
    	$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			success: function (data) {
				//alert(data.COM_DCD);
				if(data){
					create_selectbox(data, selobj);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				
				var res  = "";

				//시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.
				res = {RESULT : {CODE:-1, MESSAGE: gMSG_SYS_NO_USE}};		
				
				ibscallback(res);
			}
		});
		
	}
	
	
	
	//================================================
	//공통코드 코드명을 가져온다 - ajax로 처리
	// (코드, 코드타입)
	//================================================
	function getCodeName (code, typ, codecd){
		//url의 컨테이너 명 하드코딩됨... wiseda
		var url = containerPath+"/commons/code/getDetailCodeName.do";
		var param = {codenm:code, type:typ, dtlcode:codecd};
		
		var result ="";
		
    	$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			success: function (data) {
				//alert(data.COM_DCD);
				if(data){
					result = data.codeLnm;
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				
				var res  = "";

				//시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.
				res = {RESULT : {CODE:-1, MESSAGE: gMSG_SYS_NO_USE}};		
				
				ibscallback(res);
			}
		});
		
		return result;
	}
	
	
	//============================================
	// 그리드 셀내용 일괄 적용
	//============================================
	function setGridCellAll (sheetName, saveNm, cellVal) {
		var headrow = sheetName.HeaderRows(); 
		var datarow = sheetName.RowCount();
		for(var i = headrow; i < datarow+headrow; i++) {
			sheetName.SetCellValue(i, saveNm, cellVal);
		}
	}

	//================================================
	//IBS 선택한 row를 getsavejson 형태로 제공...
	//================================================
	function getrowjsonIBS(row, ibsobj){
		//현재 선택행 Json 생성...
		var temp = ibsobj.GetRowJson(row);
		var tmparr = new Array();
		tmparr.push(temp);
		
		return {"data": tmparr};
	}

	//================================================
	//form 내용을 IBSheet 저장 JSON 형태로 제공...
	//================================================
	function getform2IBSjson(frmname){
		//현재 선택행 Json 생성...
		
		var temp = frmname.serializeObject();
		var tmparr = new Array();
		tmparr.push(temp);
		
		return {"data": tmparr};
	}
	
	//================================================
	//form 내용을 저장 JSON 형태로 제공...
	//================================================
	$.fn.serializeObject = function()
	{
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};

	//================================================
	//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
	//================================================
	function ibscallback(res){
	    var result = res.RESULT.CODE;
	    if(result == 0) {
//	     	alert(res.RESULT.MESSAGE);
	    	
			//공통메세지 팝업 : 성공 메세지...
	    	showMsgBox("INF", res.RESULT.MESSAGE);
	    	if (!isBlankStr(cnfNextFunc)) {
	    		eval(cnfNextFunc);
	    		return;
	    	}
	    	//alert(postProcessIBS);
	    	if (postProcessIBS != null) {
	    		postProcessIBS(res);
	    	}
	    } else if (result == 401) {
	    	//권한이 없어요...
	    	showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	    } else {
//	     	alsert("저장실패");
			//공통메시지 팝업 : 실패 메세지...
	    	showMsgBox("ERR", res.RESULT.MESSAGE);
	    }
	}
	
	function gologinform() {
//		setTimeout(function(){
		if (parent && parent != self ) {parent.document.location.href = containerPath + "/loginform.do";}
		else if (opener && opener != self ) {opener.document.location.href = containerPath + "/loginform.do"; window.close();}
		else {location.href = containerPath + "/loginform.do";} 
//	}, 3000);
	}
	
	/*==============================================================
	 * 마우스 오버시 이미지 전환
	 * 대상은 이미지 태그만 가능
	 * 마우스 오버시 이미지파일.gif -> 이미지파일_.gif (마우스 아웃시 반대로..) 
	 ================================================================*/
	 function imgConvert(obj) {
		 obj.css('cursor', 'pointer').mouseover(function(){
				$(this).attr('src', $(this).attr('src').replace('.gif', '_.gif'));
			}).mouseout(function(){
				$(this).attr('src', $(this).attr('src').replace('_.gif', '.gif'));
		 });
	 }
	 
	//============================================
	// 셀렉트 박스 셋팅 (jsonlist를 셀렉트 박스 생성)
	//============================================
	function create_selectbox (jsonlist, selobj) {
//	 		alert(code+":");
		var combosel = selobj; //셋팅한 셀렉트 박스
		//최초값 셋팅
		var inittext = combosel.children(':first').text();
		if(inittext == null || inittext == "" || inittext == undefined) {
			inittext = "선택하세요";
		}
		var txtsel = '<option value="">'+inittext+'</option>';
		var cnt = jsonlist.length;
		
		//셀렉트의 초기화
		combosel.html(txtsel);
		
		//하위 셀렉트 박스의  셋팅
		for(i=0; i< cnt; i++){
			combosel.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
		}
		//셀렉트 옵션이 하나인 경우 바로 선택
		selectBoxSetup(combosel);
		//$(window).resize();
		
	}
	
	
//	create_selectbox2(생성할 divId, 생성할 갯수, 생성할 아이디, 첫칸 빈칸표시여부, readOnly여부)
	function create_selectbox2(divId, selectBoxCnt, selectBoxId, blankText, readOnly){
		var arrSelectBoxId = selectBoxId.split("|");
		var selectBoxHtml = "";
		for (var i=0; i<selectBoxCnt; i++){
			if(readOnly == true) {
				selectBoxHtml += "<select id='"+arrSelectBoxId[i]+"' class='wd30p' name='"+arrSelectBoxId[i]+"' disabled=disabled>";
			} else {
				selectBoxHtml += "<select id='"+arrSelectBoxId[i]+"' class='wd30p' name='"+arrSelectBoxId[i]+"'>";				
			}
			if(blankText == null){
				selectBoxHtml += "<option value=''></option>";
			} else {
				selectBoxHtml += "<option value=''>"+blankText+"</option>";
			}
			
			selectBoxHtml += "</select>";
			;
		}
		 divId.append(selectBoxHtml);
	}
	
	//============================================
	// IBS Combo 셋팅 (jsonlist를 ComboCode, ComboText 생성...)
	//============================================
	function create_ibscombo(jsonlist) {
		
	} 
	
	//============================================
	/*
	 * iframe 컨텐츠 내용에 따라 사이즈 자동 조정...
	 * 검색 팝업시 iframe 내의 body 가로/세로 사이즈 계산 후 iframe적용 ...
	 * 사이즈 계산시점은 iframe의 내용이 load 완료 후 한다.
	 */
	//============================================
	 function resizeFrame(iframeObj){
		  var innerBody = $(iframeObj)[0].contentWindow.document.body;
		  var innerHeight = innerBody.scrollHeight + (innerBody.offsetHeight - innerBody.clientHeight);
		  var innerWidth = innerBody.scrollWidth + (innerBody.offsetWidth - innerBody.clientWidth);
	 	  //alert(innerHeight +":"+innerWidth); 
	// 	  return;
		    if(innerHeight>0 && innerWidth>0){
		        $(iframeObj).css({
			        'height' : innerHeight,
		       		'width'  : innerWidth
		        });
		        $(iframeObj).parent().dialog("option", "width", innerWidth);
		        $(iframeObj).parent().dialog("option", "height", innerHeight);
		        $(iframeObj).parent().dialog("option", "position", "center");
		    }
		    
		}
	 
	 //============================================
	 // IBS내용을 폼에 매핑하여 저장 : (row, $form, gridname)
	 // 폼과 시트는 동일한 변수명으로 되어 있어야 한
	 // 라디오 및 체크박스는 "|" 로 연결하여 저장...
	 //============================================
	 function ibs2formmapping(row, frmobj, ibsgrid){		
	    //IBSheect 내용을 Form에 저장...
		 
	    var tempJson = ibsgrid.GetRowJson(row);
//	     alert(tempJson['pdmTblPnm']); 
//	     var formelement = $("form#frmInput").find("[name=pdmTblPnm]");
//	     alert(tempJson[formelement[0].name]);
//	     return;
	    
//	    	var formelement = $("form#frmInput").find("[name="+key+"]");
	   	var tmpform = frmobj;
	   	
	   	if(tmpform.length < 1) { return;}
	   	
	   	for(var i=0;i<tmpform[0].elements.length;i++) {
	   		var formelement = tmpform[0].elements[i];
		   	switch(formelement.type) {
		   		case undefined:
		    	case "button":
		        case "reset":
		        case "submit":
		        case "fieldset":
		          break;
		        case "radio":
		        case "checkbox":
		        	var chkval = tempJson[formelement.name];
		        	chkformsetup($(formelement), chkval);
		        	break;
		        case "select-one":
		        	$(formelement).val(tempJson[formelement.name]);
		        	$(formelement).change();
		        	break;
		        case "select-multiple":
		        	alert("멀티 셀렉트는 아직 정의되지 않음... 이걸 쓰는 경우가 있을때 작성");
		        	break;
		        default :
		        	var preval = $(formelement).val();
		        	var tmpval = tempJson[formelement.name];
		        	
//		        	alert(formelement.name+":"+$(formelement).val());
		        	//값이 없는 경우는 기존 값을 셋팅한다... 
		        	//폼에 속한 태그는 id, name 값이 반드시 있어야 함...
		        	if(tmpval === undefined) {
		        		$(formelement).val(preval);
		        	} else {
		        		$(formelement).val(tmpval);
		        	}
		        	
//		        	if(formelement.name == "frsRqstDtm" || formelement.name == "rqstDtm" || formelement.name == "aprvDtm" || formelement.name == "strDtm" || formelement.name == "expDtm"){
//		        		var chkval = tempJson[formelement.name];
////		        		chkDate($(formelement), chkval);
//		        		$(formelement).val(chkDate(chkval));
//		        	}
		        	break;
		   	}
	   	}
	 }
	 //============================================
	 // function ibs2formmapping(row, frmobj, ibsgrid)
	 // 사용시 날짜 정보 형태 변환
	 //============================================
		function chkDate(chkval){
			
			if(isBlankStr(chkval)) return;
			
//			var input = chkval.value.replace(/-/g,"");
			var input = chkval;
			var inputYear = input.substr(0,4);
			var inputMonth = input.substr(4,2);
			var inputDate = input.substr(6,2);
//			var resultDate = new Date(inputYear, inputMonth, inputDate);
			chkval = inputYear + "-" + inputMonth + "-" + inputDate;
//			if(resultDate.getFullYear() != inputYear ||
//				resultDate.getMonth() != inputMonth ||
//				resultDate.getDate() != inputDate){
//				chkval = "";
//			}else{
//				chkval = inputYear + "-" + inputMonth + "-" + inputDate;
//				chkval = resultDate;
//			}
			return chkval;
		}
	 //============================================
	 // JSON 내용을 폼에 매핑하여 저장 : ($form, json)
	 // json.name과 formelemetn.name은 동일한 변수명으로 되어 있어야 한
	 // 라디오 및 체크박스는 "|" 로 연결하여 저장...
	 //============================================
	 function json2formmapping(frmobj, tempJson) {
		 var tmpform = frmobj;
	   	if(tmpform.length < 1) { return;}
	   	
	   	for(var i=0;i<tmpform[0].elements.length;i++) {
	   		var formelement = tmpform[0].elements[i];
		   	switch(formelement.type) {
		   		case undefined:
		    	case "button":
		        case "reset":
		        case "submit":
		        case "fieldset":
		          break;
		        case "radio":
		        case "checkbox":
		        	var chkval = tempJson[formelement.name];
		        	chkformsetup($(formelement), chkval);
		        	break;
		        case "select-one":
		        	$(formelement).val(tempJson[formelement.name]);
		        	break;
		        case "select-multiple":
		        	alert("멀티 셀렉트는 아직 정의되지 않음... 이걸 쓰는 경우가 있을때 작성");
		        	break;
		        default :
		        	var preval = $(formelement).val();
		        	var tmpval = tempJson[formelement.name];
//			        	alert(formelement.name+":"+$(formelement).val());
		        	//값이 없는 경우는 기존 값을 셋팅한다... 
		        	//폼에 속한 태그는 id, name 값이 반드시 있어야 함...
		        	if(tmpval === undefined) {
		        		$(formelement).val(preval);
		        	} else {
		        		$(formelement).val(tmpval);
		        	}
		        	break;
		        	//alert(formelement.name+": "+formelement.value);
		   	}
	   	}
		 
	 }
	 
	 //============================================
	 // 폼 내용을 IBS에 매핑하여 저장 : (row, $form, gridname)
	 // 폼과 시트는 동일한 변수명으로 되어 있어야 한
	 // 라디오 및 체크박스는 "|" 로 연결하여 저장...
	 //============================================
	 function form2ibsmapping(row, tmpform, ibsgrid){		
//	 	var tmpform = $("form#frmInput");
	   	if(tmpform.length < 1) { return;}
	   	
	   	var prevelement = ""; //체크박스나 멀티 셀렉트의 경우 이전 값을 셋팅한다...
	   	for(var i=0;i<tmpform[0].elements.length;i++) {
	   		var formelement = tmpform[0].elements[i];
		   	switch(formelement.type) {
		   		case undefined:
		    	case "button":
		        case "reset":
		        case "submit":
		        case "fieldset":
		          break;
		        case "radio":
		        case "checkbox":
		        	if(prevelement != formelement.name) {
			        	var chkval = chkvaljoin($(formelement));
//				        	var chkval = chkvaljoinbyname($("form#frmInput"), formelement.name);
			        	prevelement = formelement.name;
			        	ibsgrid.SetCellValue(row, formelement.name, chkval);
		        	}
		        	break;
		        case "select-one":
		        	ibsgrid.SetCellValue(row, formelement.name, $(formelement).val());
		        	break;
		        case "select-multiple":
		        	alert("멀티 셀렉트는 아직 정의되지 않음... 이걸 쓰는 경우가 있을때 작성");
		        	break;
		        default :
		        	ibsgrid.SetCellValue(row, formelement.name, $(formelement).val());
		   	}
	   	}
	 }
	 
	 //============================================
	 //체크박스 내용을 '|' 문자로 조인하여 리턴... 
	 //단일폼의 경우 사용 가능... 다른폼에 같은 name의 인풋 박스 존재시 같이 변경되지 않을까???? 
	 //============================================
	 function chkvaljoinbyname(frmobj, tagnm){
	 	var chktemp = new Array();
	 	$('input[name='+tagnm+']:checked', frmobj).each(function(){
	 		chktemp.push($(this).val());
	 	});
//	  	alert(chktemp.join('|'));
	 	return chktemp.join('|');
	 	
	 }
	 //============================================
	 //체크박스 내용을 '|' 문자로 조인하여 리턴...
	 //============================================
	 function chkvaljoin(selobj){
		 var chktemp = new Array();
		 
		 var frmname = selobj[0].form.name;
		 var tagnm = selobj.attr('name');
		 $("form[name="+frmname+"] input[name="+tagnm+"]:checked").each(function(){
		 		chktemp.push($(this).val());
		 });
//	  	alert(chktemp.join('|'));
		 return chktemp.join('|');
		 
	 }
	 
	//============================================
	//체크박스 텍스트를 내용을 '|' 문자로 조인하여 리턴...
	//============================================
	function chkTextjoin(selobj, jchr){
		 var joinchr = "|";
		 if(!isBlankStr(jchr)) joinchr = jchr;
		 
		 var chktemp = new Array();
		 
		 var frmname = selobj[0].form.name;
		 var tagnm = selobj.attr('name');
		 $("form[name="+frmname+"] input[name="+tagnm+"]:checked").each(function(){
		 		chktemp.push($(this).next().text());
		 });
//	 	alert(chktemp.join(joinchr));
		 return chktemp.join(joinchr);
		 
	}

	 //============================================
	 //라디오 및 체크박스 폼셋팅 : '|' 문자로 조인된 chkval를 각 체크 value와 비교하여 셋팅한다.
	 //============================================
	 function chkformsetupbyname(tagnm, chkval) {
		 
		 if(isBlankStr(chkval)) return;
	 	var chktemp = chkval.split('|');
	 	var cnt  = chktemp.length;

	 	$('input[name='+tagnm+']').each(function(){
	 		$(this).attr('checked', false);
//	 		$(this).prop("checked", false); 
	 	});
	 	for(var i=0; i<cnt; i++) {
	 		$('input[name='+tagnm+'][value='+chktemp[i]+']').attr('checked', true);
//	 		$('input[name='+tagnm+'][value='+chktemp[i]+']').prop('checked', true);
	 	}
	 }
	 //============================================
	 //라디오 및 체크박스 폼셋팅 : '|' 문자로 조인된 chkval를 각 체크 value와 비교하여 셋팅한다.
	 //============================================
	 function chkformsetup(selobj, chkval) {
		 
		 if(isBlankStr(chkval)) return;
		 
		 var chktemp = chkval.split('|');
		 var cnt  = chktemp.length;
		 
		 selobj.each(function(){
			 $(this).attr('checked', false);
			 for(var i=0; i<cnt; i++) {
				 //alert(selobj.val());
				 if(selobj.val() == chktemp[i]) selobj.prop('checked', true);
//			 selobj.find("[value="+chktemp[i]+"]").attr('checked', true);
			 }
		 });
	 }
	 
	 //============================================
	 //라디오 및 체크박스 RESET
	 //============================================
	 function chkformreset(selobj) {
		 selobj.each(function(){
			 $(this).attr('checked', false);
		 });
	 }

	 //============================================
 	 //셀렉트 박스 셋팅 : option text 에 따라 셋팅...
	 //============================================
	 function setselectbytext(selobj, optiontext) {
	 	selobj.children().filter(function(){
	 		return $(this).text() == optiontext; 
	 	}).attr('selected', true);
	 }

	 //============================================
	 // 스트링 체크 (null/undefined/"" 일 경우 true
	 //============================================
	 function isBlankStr (tmpstr) {
		 if(tmpstr == null || tmpstr == undefined || tmpstr == "" )
			 return true;
		 else return false;
	 }

	 //============================================
	// 기본 버튼 셋팅... (이건 테스트 용도입니다)...
	//============================================
	 function setButtonMain() {
		 
		 $('#btnpop').button({
		      icons: {
		          primary: "ui-icon-gear",
		          secondary: "ui-icon-triangle-1-s"
		        },
		        text: false
		      }).click(function(){
	 		
	 		//var msg = '<s:message code='MSG.TEST' arguments="단어, 도메인, 항목" />';
			//showMsgBox("INF", msg);
			
			$('div#popSearch').dialog("open");
			//resizeFrame($('div#popSearch iframe'));
	 	  
	   });
	 }
	 
	 /*=======================================================================
	 기   능 : 윈도우 resize시 셀렉트 박스 width 조정
	 =======================================================================*/	
	 function resize_select(subselect) {
	 	var parentsel = subselect.parent(); //셀렉트 부모
	 	var totwidth = parentsel.width(); //셀렉트 부모 width 값
	 	var cntsel = $('select', parentsel).length; //셀렉트 갯수
	 	
	 	var selwidth = totwidth / cntsel - 10;
	 	
	 	$('select', parentsel).css('width', selwidth+'px');
	 	
	 }

	 function resize_selectTD(subselect) {
	 	var parentsel = subselect.parent(); //셀렉트 부모
	 	var totwidth = parentsel.width(); //셀렉트 부모 width 값
	 	var cntsel = $('select', parentsel).length; //셀렉트 갯수
	 	
	 	var selwidth = totwidth - 10;
	 	
	 	$('select', parentsel).css('width', selwidth+'px');
	 	
	 }

	 /*=======================================================================
	 기   능 : 더블셀렉트V1.1  (json 객체, 셀렉트id)
	 =======================================================================*/
	 function double_select(jsonlist, subid) {
	 	var tmpwidth;		//셀렉트 width 값
	 	var subselect = subid; //현재셀렉트 박스
	 	var code = subselect.val(); //셀렉트 값
	 	
	 	var parentsel = subselect.parent(); //셀렉트 부모
	 	var totsel = $('select', parentsel); //전체셀렉트 박스
	 	
	 	var cntsel = totsel.length; //셀렉트 갯수
	 	var idxsel = totsel.index(subselect); //현재 셀렉트 순번
	 	
	 	if ((cntsel-1) == idxsel) return; //최종 셀렉트인 경우 리턴
	 	
	 	var combosel = totsel.eq(idxsel + 1); //하위 셀릭트 박스
//	 	alert(subselect.children(':first').text());
	 	var txtsel = '<option value="">'+subselect.children(':first').text()+'</option>';
	 	var cnt = jsonlist.length;
	 	
	 	//하위 셀렉트의 초기화
//	 	$('select:gt('+idxsel+')\'', parentsel).html(txtsel);
	 	parentsel.find('select:gt('+idxsel+')').html(txtsel);
	 	
	 	//현재 코드가 없을경우 하위 셀렉트를 초기화 후 리턴
	 	if(code == "") {
	 		//최상위 셀렉트 박스의 경우 초기화
	 		if (idxsel == 0) {
	 			subselect.html(txtsel);
	 			for(var i=0; i < cnt; i++) {
	 				if(cnt > 1 && jsonlist[i].upcodeCd == null)
	 				subselect.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
	 			}
	 		}
	 		//$(window).resize();
	 		return;
	 	}
	 	
	 	//하위 셀렉트 박스의  셋팅
	 	for(var i=0; i< cnt; i++){
	 		if(cnt > 1 && jsonlist[i].upcodeCd == code) {
	 			combosel.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
	 		}
	 	}
	 	//$(window).resize();
	 }

	 function double_selectTD(jsonlist, subid, parentId) {
	 	var tmpwidth;		//셀렉트 width 값
	 	var subselect = subid; //현재셀렉트 박스
	 	var code = subselect.val(); //셀렉트 값
	 	
	 	var parentsel = parentId; //셀렉트 부모
	 	var totsel = $('select', parentsel); //전체셀렉트 박스
	 	
	 	var cntsel = totsel.length; //셀렉트 갯수

	 	var idxsel = totsel.index(subselect); //현재 셀렉트 순번
	 	
	 	if ((cntsel-1) == idxsel) return; //최종 셀렉트인 경우 리턴
	 	
	 	var combosel = totsel.eq(idxsel + 1); //하위 셀릭트 박스
	 	
	 	var txtsel = '<option value=""></option>';
	 	var cnt = jsonlist.length;
	 	
	 	//하위 셀렉트의 초기화
	 	$('select:gt('+idxsel+')', parentsel).html(txtsel);
	 	
	 	//현재 코드가 없을경우 하위 셀렉트를 초기화 후 리턴
	 	if(code == "") {
	 		//최상위 셀렉트 박스의 경우 초기화
	 		if (idxsel == 0) {
	 			subselect.html(txtsel);
	 			for(i=0; i < cnt; i++) {
	 				if(cnt > 1 && jsonlist[i].upcodeCd == null)
	 				subselect.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
	 			}
	 		}
	 		
//	 		$(window).resize();
	 		return;
	 	}
	 	
	 	//하위 셀렉트 박스의  셋팅
	 	for(i=0; i< cnt; i++){
	 		if(cnt > 1 && jsonlist[i].upcodeCd == code) {
	 			combosel.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
	 		}
	 	}
//	 	$(window).resize();
	 }

	 
	 
	 /*=======================================================================
	 기   능 : 레이어드 검색 팝업 초기화...
	 =======================================================================*/
	 function initSearchPop2() {
		 var htmlPop = "<div id='popSearch' style='display: none;'>" +
//		 "<iframe src='' frameborder='0' height='0' width='0'  scrolling='no' ></iframe>" +
		 "</div>";
		 
		 $('body').append(htmlPop);
	 }
	 /*=======================================================================
	 기   능 : 레이어드 검색 팝업 오플...
	 =======================================================================*/
	 function openSearchPop2(url, param) { 
//		 event.preventDefault();	//브라우저 기본 이벤트 제거...
		 //url = url + "?" + param;
		 //$('div#popSearch iframe').attr('src', url);
		 $('div#popSearch').load(url, param);
		 $('div#popSearch').show();
      	 //$('div#popSearch').dialog("open");
	 }
	 
	 
	 /*=======================================================================
	 기   능 : iframe 형태 검색 팝업 초기화...
	 =======================================================================*/
	 function initSearchPop() {
		 
		 var htmlPop = "<div id='popSearch' style='display: none;'>" +
		 			  "<iframe src='' frameborder='0' height='0' width='0'  scrolling='no' ></iframe>" +
		 			  "</div>";
		 
		 $('body').append(htmlPop);
		 
		 $('div#popSearch').dialog({
				autoOpen: false,
			  draggable: false,
			  resizable: false,	          
//			    height: '500px',
//			    width: '500px',
			    modal: true,
			    open: function(event, ui) {
//			   	  $(this).load("<c:url value='/meta/test/pop/testpop.do' />");
//					$(this).hide();
					$(".ui-dialog-titlebar-close", $(this).parent()).hide();
			  	   	$(".ui-dialog-titlebar", $(this).parent()).hide();  
//			  	   	$(".ui-dialog-content").removeClass();
			  	   	$(this).removeClass("ui-dialog-content ui-widget-content");
//			 			$('iframe', this).attr('src', "<c:url value='/meta/test/pop/testpop.do' />")
					$('iframe', this).load(function(){ 
						resizeFrame($(this));
						$(this).parent().show();
					});
			    }, 
				  close: function(event, ui) {
					$('iframe', this).attr('src', '').css({'width': '0px', 'height':'0px'});
					$(this).css({'width': '0px', 'height':'0px'});
					  
				  } 
			});
		 
	 }
	 
	 
	//레이어드 검색 팝업창 오픈...
	 function openSearchPop(url, param) { 
//		 event.preventDefault();	//브라우저 기본 이벤트 제거...
		 url = url + "?" + param;
		 $('div#popSearch iframe').attr('src', url);
      	 $('div#popSearch').dialog("open");
	 }
	 
	 //레이어드 검색 팝업창 클로즈...
	 function closeSearchPop() {
		 $('div#popSearch').dialog("close");
	 }
	 
	 /*=======================================================================
	 기   능 : 폼내의 검색버튼 초기화  
	 =======================================================================*/
	 function initSearchButton() {
		$('.btnDelPop').click(function(event){
		    	//alert("click event");
		    	event.preventDefault();  //브라우저 기본 이벤트 제거...
		    	//$("input", $(this).parent()).val('');
//		    	$(this).parent().children().val('');
			
		}).hide();
		
	 $('.btnSearchPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		  
		});
	 $('.btnEtcPop').click(function(event){
		    	event.preventDefault();  //브라우저 기본 이벤트 제거...
		});
		 
			
	 }
	 
	 //엔터키 처리 함수
	 function EnterkeyProcess(action) {
		   
		 //엔터키 이벤트 처리
		   $(document).keydown(function(event) {
		   	  if (event.keyCode == '13') {
		   	     event.preventDefault();
		   	     //alert("엔터키");
//		   	     alert(typeof action);
		   	     if(typeof action == "string") { doAction(action); }
		   	     else if (typeof action == "function") {action();}
		   	     
		   	   }
		   });
	 }
	 
	 //자동완성 기능 적용...
	 //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	 function setautoComplete(autoobj, type, maxrow) {
		//autocomplete test
		var mrow = 10;
		if (!isBlankStr(maxrow)) mrow = maxrow;
		
		var url = containerPath+"/commons/autocom/getTermList.do";
//		 	var param = {searchType:"STWD", maxRows: 10, startTerm: request.term};
		autoobj.autocomplete({
			minLength:2,
			source:function(request, response) {
				$.getJSON(url, {searchType:type, maxRows: mrow, searchTerm: request.term}, function(data, status, xhr){
//		 				alert("autocom result:" + status);
					if(data != null) {
						response(data);
					}
				});
			} 
		});
	 }
	 
	 //천단위 , 추가
	 function addThousandCommas(num) {
	    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
	 
//***********************************************************************************
//***********************************************************************************
//	 jqgrid 전용 함수....
//***********************************************************************************
//***********************************************************************************
	 //헤더 체크박스 선택
	 function checkBox(e) {
		  e = e||event;/* get IE event ( not passed ) */
		  e.stopPropagation? e.stopPropagation() : e.cancelBubble = true;
	}	 
	 
	 
//***********************************************************************************
//***********************************************************************************
//	 IBSheet 전용 함수....
//***********************************************************************************
//***********************************************************************************
	 
	 //그리드의 중복행들을 ","으로 조인하여 리턴한다. 없을 경우 -1
	 function getibsdupjoin(ibsnm, savenm) {
		 return ibsmn.ColValueDupRows(savenm);
	 }
	 
	 //IBSheet 체크 Row의 특정 컬럼내용을 join해서 리턴한다.
	 function getibscheckjoin(ibsnm, savenm, joinchr, checknm) {
		 var joinchar = "|";
		 var chksavenm = "ibsCheck";
		 
		 if (!isBlankStr(joinchr)) joinchr = joinchr;
		 if (!isBlankStr(checknm)) checknm = checknm;
		 
		 var sRow = ibsnm.FindCheckedRow(chksavenm);
    	//받은 결과를 배열로 생성한다.
    	var arrRow = sRow.split("|");
//    	var tmpkey = "";
    	var tmparr = new Array();
    	for(idx=0; idx<arrRow.length; idx++){ 
    		//alert(arrRow[idx]);
//    		tmpkey += ibsnm.GetCellValue(arrRow[idx], savenm) +"|";
    		tmparr.push(ibsnm.GetCellValue(arrRow[idx], savenm));
		}
    	return tmparr.join(joinchar);
	 }
	 
	 //IBSheet 특정 컬럼에 해당하는 모든 row의 값을 조인하여 리턴한다.
	 function getibsrowjoin (sheetName, savenm, joinc) {
		var joinchar = ",";
		var tmparr = new Array();
		
		if (!isBlankStr(joinc)) joinchar = joinc;
		
		var headrow = sheetName.HeaderRows(); 
		var datarow = sheetName.RowCount();
		
		for(var i = headrow; i < datarow+headrow; i++)	{
			tmparr.push(sheetName.GetCellValue(i, savenm));
		}
		
		return tmparr.join(joinchar);
	 }
	 
	//검증결과 상세조회
	 function getRqstVrfLst(param) {
		//alert(grid_vrf); 
		 if (typeof grid_vrf != 'undefined')
//		if (grid_vrf != null && grid_vrf != undefined)
	 	grid_vrf.DoSearch(containerPath+"/commons/rqstmst/getRqstVrfLst.do", param);
	 }
	 
	 //변경항목 상세조회
	 function getRqstChg(param, subInfo) {
	 
		 if(subInfo == "COL") {
			 if (typeof grid_changecol != 'undefined'){
				 param += "&subInfo="+subInfo
				 grid_changecol.DoSearch(containerPath+"/commons/rqstmst/getRqstChangeLst.do", param);
			 }
		 }else if(subInfo == "REL") {
			 if (typeof grid_changecol2 != 'undefined')
			 grid_changecol2.DoSearch(containerPath+"/commons/rqstmst/getRqstChangeLst.do", param); 
		 } else {
			 if (typeof grid_change != 'undefined')
				 grid_change.DoSearch(containerPath+"/commons/rqstmst/getRqstChangeLst.do", param);
		 }
	 }
	 
	//트리레벨의 경우 하위 레벨도 모두 삭제 하기 위해 체크 처리하도록 한다
	 function setTreeCheckIBS(ibsobj) {
	 	
	 	var sRow = ibsobj.FindCheckedRow("ibsCheck");
	 	 
	 	//자바 스크립트 배열로 만들기
	 	var arr = sRow.split("|");
	 	for (var i=0; i<arr.length; i++) {
	 	    //alert(arr[i] + " 행이 선택되었음");
	 		// 체크된 행의 모든 자식행 확인
	 		var childRows = ibsobj.GetChildRows(arr[i]);
	 		var childArr = childRows.split("|");
	 		for(var j=0; j<childArr.length; j++) {
	 			// OnChange 이벤트 발생하지 않음
	 			ibsobj.SetCellValue(childArr[j], "ibsCheck" , 1, 0);
	 		}
	 	
	 	}
	 }
	 //체크 상태인 리스트 중 입력상태인 경우 시트에서 제거...
	 function delCheckIBS(ibsobj) {
		var bRtn = false;
	 	var iRow = ibsobj.FindCheckedRow("ibsCheck");
	 	var iRows = iRow.split("|");
	 	var delRows = "";
	 	
	 	for(var i=0; i<iRows.length;i++ ) {
	 		if ("I" == ibsobj.GetCellValue(iRows[i], "ibsStatus")) {
	 			//ibsobj.RowDelete(iRows[i], 0); //해당 Row 삭제...
	 			delRows += iRows[i] + "|";
	 			bRtn = true;
	 		}
	 	}
	 	
//	 	alert(delRows);
	 	// 3, 7, 10번 행 삭제하기	"3|7|10"
	 	ibsobj.RowDelete(delRows, 0); //해당 Row 삭제...
	 	
	 	
	 	return bRtn;
	 }
	 
	//삭제요청시 체크박스 확인...
	//삭제대상이 없을 경우 경고창...
	//삭제대상이 있을 경우 확인창... ("예" 선택시 사용할 액션명 지정 Delete)
	function checkDelIBS (sheetobj, message) {
		//체크박스 확인...
		if(!sheetobj.CheckedRows("ibsCheck")) {
			//삭제할 대상이 없습니다...
			showMsgBox("ERR", message);
			return false;
		}
		
		return true;
		
	}
	
	//그리드 하단에서 드래그로 높이 조정
	function bindibsresize() {
		$( ".grid_01" ).resizable({
			minHeight: 150,
			handles: "s",
//	 		animate: true,
			helper: "ui-resizable-helper",
//	 		resize: function( event, ui ) {
				//alert(ui.size.width+":"+ui.size.height);
//	 			$(this).children("div:first").css('height', ui.size.height);
				//$(this).children("div:first").css('width', $(this).width);
				//$("#DIV_grid_sheet").css('height', ui.size.height);
				
//	 		},
			create: function( event, ui ) {
				$("div.ui-resizable-handle").css('z-index', '0');
			},
			stop: function(event, ui ){
				$(this).children("div:first").css('height', ui.size.height);
			}
			
		});
	}
	
	//그리드 높이 조정 : 그리드 현재 위치에서 페이지 최하단까지
	function setibsheight(selobj) {
		var gridtop = selobj.offset().top;
		var bodyheight = $(document).height();
//		var footerh = $("div.BK_footer").outerHeight(true)+10;
		var footerh = $("#btn_close").parent().outerHeight(true)+20;
		
//		alert(footerh);
		var tmpheight = 60;
		if (footerh > 60) {
			tmpheight = footerh;
		}
		
//	 	alert(tmpheight);
		
//	 	alert(bodyheight-gridtop-60);
		
		selobj.children("div:first").css('height', bodyheight-gridtop-tmpheight);
	}
	 
   /*=======================================================================
     기  능 : 날짜 선택 - 달력 오픈 & 날짜 선택시 입력
   =======================================================================*/   
	$.datepicker.setDefaults($.datepicker.regional['ko']);
	$.datepicker.setDefaults({
			   dateFormat: 'yy-mm-dd',
			   monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			   monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			   dayNames: ["일", "월", "화", "수", "목", "금", "토" ],
			   dayNamesMin: ["일", "월", "화", "수", "목", "금", "토" ],
			   dayNamesShort: ["일", "월", "화", "수", "목", "금", "토" ],
//			   monthNames: ['Janury','February','March','April','May','June','July','August','September','October','November','December'],
//			   monthNamesShort: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
//			   dayNames: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
//			   dayNamesMin: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
//			   dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
			   changeMonth: true,
			   changeYear: true,
//			   showOn: 'both',
			   showOn: 'button',
			   buttonImage: containerPath+'/img/icon_05.gif',
//   			   buttonText: 'Calendar',
   			buttonText: '날짜선택',
   			   buttonImageOnly: true,
			   showMonthAfterYear: true 
   	});
  /*=======================================================================
    기  능 : 1일, 3일, 7일, 1개월, 3개월, 6개월 버튼 클릭시 오늘 날짜를 기준으로 시작일과 마지막 날짜 셋팅
   사용법 : setBetweenDtm( idx[버튼인덱스], $( "#ntceBgnde" )[시작일자 input], $( "#ntceEndde" )[종료일자 input]);
  =======================================================================*/   
	function setBetweenDtm( idx, objStdDt, objEndDt) {
		//날짜 초기화
		objStdDt.val(''); objEndDt.val('');
		
		//오늘 날짜를 구해서 EndDate에 셋팅한다.
		var curDt = new Date();
		objEndDt.val(converDateString(curDt));
		
		var StdDt= new Date();
		
		switch(idx) {
		case 0: //하루전
			StdDt.setDate(curDt.getDate());
		break;
		case 1: //3일전
			StdDt.setDate(curDt.getDate() - 2);
		break;
		case 2: //7일전
			StdDt.setDate(curDt.getDate() - 6);
		break;
		case 3: //1개월
			StdDt.setMonth(StdDt.getMonth() - 1);
			StdDt.setDate(StdDt.getDate() + 1);
		break;
		case 4: //3개월
			StdDt.setMonth(StdDt.getMonth() - 3);
			StdDt.setDate(StdDt.getDate() + 1);
		break;
		case 5: //6개월
			StdDt.setMonth(StdDt.getMonth() - 6);
			StdDt.setDate(StdDt.getDate() + 1);
		break;
		}
		
		objStdDt.val(converDateString(StdDt));
		
	}

	function converDateString(dt){
		return dt.getFullYear() + "-" + addZero(eval(dt.getMonth()+1)) + "-" + addZero(dt.getDate());
	}

	function addZero(i){
		var rtn = i + 100;
		return rtn.toString().substring(1,3);
	}
	
	
	 //시간 계산 : 시작날짜와 마지막 날짜 사이의 00일 00시간 00분을 계산하여 리턴한다.
	 function calbetweendate(startdate, enddate) {
		 
		 if(startdate == null || enddate == null) return;
		 
		 //날짜 포맷 : "YYYY-MM-DD HH:MM:SS" 
		 var dateString = startdate.split(" ")[0];
		 var dateArray = dateString.split("-");
		 var timeString = startdate.split(" ")[1];
		 var timeArray = timeString.split(":"); 
		 //시작날짜
		 var strtime = new Date(dateArray[0], Number(dateArray[1])-1, dateArray[2], timeArray[0], timeArray[1], timeArray[2]);

		 dateString = enddate.split(" ")[0];
		 dateArray = dateString.split("-");
		 timeString = enddate.split(" ")[1];
		 timeArray = timeString.split(":"); 
	     //종료날짜
		 var endtime = new Date(dateArray[0], Number(dateArray[1])-1, dateArray[2], timeArray[0], timeArray[1], timeArray[2]);
//	 	 alert(strtime + ":"+endtime); 
		 
		 
		 var msecPerMinute = 1000 * 60;
		 var msecPerHour   = msecPerMinute * 60;
		 var msecPerDay    = msecPerHour * 24;
		 
		 var intervalsec = endtime.getTime() - strtime.getTime();
		 
		 var days = Math.floor(intervalsec / msecPerDay);
		 intervalsec = intervalsec - (days * msecPerDay);
		 
		 var hours = Math.floor(intervalsec / msecPerHour);
		 intervalsec = intervalsec - (hours * msecPerHour);
		 
		 var minutes = Math.floor(intervalsec / msecPerMinute);
		 intervalsec = intervalsec - (minutes * msecPerMinute);
		 
		 var seconds = Math.floor(intervalsec / 1000);
		 
		 var strret = "";
		 
		 if(days > 0) 	 strret += days + "일 " ;
		 if(hours > 0)   strret += hours + "시간 ";
	 	 if(minutes > 0) strret += minutes +"분 ";
	 	 
	 	 if (strret == "") strret = "1분 이내";
			 
		 return strret;
	 }
	 

	
	 /**
	 * 2개 Sheet에서 데이터 이동하기 - STATUS I U D 데이터만 이동하기
	 * fromSheet, toSheet layout 동일해아 함
	 * @param : fromSheet - 이동 원본 Sheet의 Object id
	 * @param : parent.opener.grid_rel_col_sheet   - 이동 대상 Sheet의 Object id
	 * @param : statusCol    - 상태 컬럼명
	 * @return : 없음
	 * @version : 2.4.0.0
	 * @sample
	 *  ibsSheet2SheetStatus(mySheet1, mySheet2, "ibsStatus");
	 */
	function ibsSheet2SheetStatus(fromSheet, toSheet, statusCol)  {
		//데이터 행의 개수 확인
		var toRow = toSheet.RowCount();
		  
		for (var ir = fromSheet.RowCount(); ir>= 1; ir--) {
			// IBSHEET 상태가 조회 (R) 이면 continue
			if(fromSheet.GetCellValue(ir, statusCol) == "R" ) continue;
			//데이터 행 추가
			toRow = toSheet.DataInsert(toRow);
			//데이터 옮기기
			for (var ic = 0; ic<=fromSheet.LastCol(); ic++) {
				toSheet.SetCellValue(toRow, ic, fromSheet.GetCellValue(ir,ic));
			}
		    //원본에서 지움
		    fromSheet.RowDelete(ir, false);
		    toRow-- ;
		}
	}
		
	//============================================
	 // form 초기화
	 //============================================
	 function resetForm(frmobj){		
	   	var tmpform = frmobj;
	   	if(tmpform.length < 1) { return;}
	   	
	   	for(var i=0;i<tmpform[0].elements.length;i++) {
	   		var formelement = tmpform[0].elements[i];

		   	switch(formelement.type) {
		   		case undefined:
		    	case "button":
		        case "reset":
		        case "submit":
		        case "fieldset":
		          break;
		        case "radio":
		        	chkformreset($(formelement));
		        	break;
		        case "checkbox":
		        	chkformreset($(formelement));
		        	break;
		        case "select-one":
		        	$(formelement).val("");
		        	//jquery 버전업 하면서 select박스 값을 빈값으로 둘 경우 1번째 박스가 선택이 안되서 추가
		        	var selbxopt = $(formelement).find("option");
		    		 if(selbxopt.length > 1 && selbxopt.eq(0).val()!=""){
//		    			 alert(selbxopt.eq(0).val());
		    			 selbxopt.eq(0).prop("selected","true").change();
		    		 }
		        	break;
		        case "select-multiple":
		        	alert("멀티 셀렉트는 아직 정의되지 않음... 이걸 쓰는 경우가 있을때 작성");
		        	break;
		        case "hidden":
		        	$(formelement).val("");
		        	break;
		        default :
	        		$(formelement).val("");
		        	break;
		   	}
		   	
			$("#ibsStatus").val("I");
	   	}
	 }
		
//	 function (y){
//		 var A,B,C,D,E;
//		 E=this.EndEdit(1);
//		 if(E==-1)return;
//		 B={};
//		 C=this.GetRowByIndex(y);
//		 if(!C)return B;
//		 
//		 for(A in this.Cols){
//			 D=this.Cols[A]["SaveName"];
//			 if(D){B[D]=this.GetCellValue(C,A)}
//			 if(this["MainCol"]==A&&this.Cols[A]["LevelSaveName"]){
//				 B[this.Cols[A]["LevelSaveName"]]=C["Level"]
//			 }
//		 }
//		 return B
//		 }
	 
	 // IBSheet의 높이를 맞게 조정한다.
	 function fitSheetHeightIB(ibObj){
			// 사이즈 수정
		 	var rowHCnt = ibObj.HeaderRows();
			var headerH = ibObj.GetHeaderRowHeight() * rowHCnt;
			var dataH = 0;
			var rowCnt = ibObj.RowCount();
			if(rowCnt > 10)return;
			
			for(var i = rowHCnt; i < rowHCnt+rowCnt; i++){
				if("0" == ibObj.GetRowHidden(i)){
					dataH += ibObj.GetRowHeight(i);
				} 
			}
			dataH += 2;
			
			// 스크롤 여부 
			var sheetWidth = ibObj.GetSheetWidth();
			var dataWidth = 0;
			for(var i = 0; i < ibObj.LastCol()+1; i++){
				if("0" == ibObj.GetColHidden(i)){
					dataWidth += ibObj.GetColWidth(2);
				}
			}
			
			// 스크롤 부분추가
			if(sheetWidth < dataWidth){
				dataH += 20;
			}
			
			// IBSheet 최소 높이가 84 미만이면 에러발생함 
			if(dataH <= 45){
				dataH = 45;
			}

			dataH += headerH;
			
			ibObj.SetSheetHeight(dataH);
	 }
	 
	 // 종합, 평균 스타일 정의
	 function sumRowStyle(ibObj, str){
		 
		 if(str == null || str == undefined || str == ""){
			 str = "evitNm";
		 }
		 
			var cntrow = ibObj.RowCount();
			if(cntrow > 1){
				var evitNm = ibObj.GetCellValue(cntrow, str);
				if(evitNm == "종합등급"){
					ibObj.SetRowBackColor(cntrow,"#f8ebb0");
					ibObj.SetRangeFontBold(cntrow, 0, cntrow, ibObj.LastCol(), 1);
				}
			}
	 }
	 
	 
	 function setibsTabHeight(selobj, padHeight) { 
			
			var gridtop    = 0;
			var bodyheight = 0;
			
			if(padHeight == undefined) padHeight = 100;
			
			if($("#tabs").offset() == undefined) {  
				
				if($("#tabsStwd").offset() == undefined) {
					
				}else{
					gridtop = $("#tabsStwd").offset().top;
				}
				
			}else{
			
				//gridtop = $("#tabs", parent.document).offset().top + 400; 
				
				gridtop = $("#tabs", parent.document).position().top + 200; 
			}
			                		
			bodyheight = $(window).height();     
			
		 	//alert( bodyheight +":"+  gridtop );   
		 	//alert(bodyheight - gridtop); 
			
			var chdHeight =  bodyheight - gridtop;
			
			if(chdHeight < 0) chdHeight = 200; 
					 
			selobj.children("div:first").css('height', chdHeight + padHeight);
		 	
		 	//selobj.children("div:first").css('height', gridtop + padHeight); 
			
		}
	 
	 
	 function floatCheck(val, key) {
		var validation = /^([0])[\.]?([0-9])*?$/;
		
		if(!validation.test(val)) {
			return val.replace(key, '');
		} else {
			return val;
		}
	 }
	 
	 function numberCheck(val, key) {
		var validation = /^([0-9])*?$/;
		
		if(!validation.test(val)) {
			return val.replace(key, '');
		} else {
			return val;
		}
	 }
	 
	//============================================
	//문자열 바이트사이즈 계산 후 리턴한다...
	//============================================
	 function chechkByteSize(strval) {
	 	var charCode = null;
	 	var strsize = 0;
	 	for(i=0;i<strval.length;i++) {
	 		charCode = strval.charAt(i);
	 		
	 		if (escape(charCode).length > 4) strsize += 2;
	 		else strsize += 1;
	 		
	 		/* charCode = strval.charAt(i).charCodeAt(0);
	 		if(charCode <= 0x00007F) strsize += 1;
	 		else if(charCode <= 0x0007FF) strsize += 2;
	 		else if(charCode <= 0x00FFFF) strsize += 3;
	 		else strsize += 4; */
	 	}
	 	
	 	return strsize;
	 }
	 
	//적용요청일자 체크 현재일자보다 작을경우 반려만 가능
		function approveChkAplRegDt(approveSaveJson, gridId, aqdAlwYn){
			if(aqdAlwYn=='Y'){
				
			}else{
				//언디파인드, 널값이 들어왔을 경우 디폴트 N처리
				return false;
			}
			
//			var vpreDay = getCrtDay();
			var vpreDay = getSysdate("YYYY-MM-DD");
//			alert(vpreDay);
			var vCnt = 0;
			var vAplReqdt = "";
			var vRqstSno = "";
			var vRow = "";
			for(var i=0; i<approveSaveJson.data.length; i++){
				vAplReqdt = approveSaveJson.data[i].aplReqdt;
				if(vAplReqdt.length == 8){
//					vpreDay = getCrtDay2(); 
					vpreDay = getCrtDay2("YYYYMMDD"); 
				}
//				alert(vAplReqdt);
				//검토상태 반려 제외
				if(approveSaveJson.data[i].rvwStsCd != "2"){
					//적용요청일자 체크 현재일자보다 작을경우 반려만 가능
					if(vAplReqdt < vpreDay){
							vRqstSno = approveSaveJson.data[i].rqstSno;
							//그리드 type int 일경우 못 찾음.. text 로 변경
							vRow = gridId.FindText("rqstSno", vRqstSno);
							gridId.SetCellValue(vRow, "rvwStsCd", "2");
							gridId.SetCellValue(vRow, "rvwConts", "적용요청일자가 현재일자보다 작습니다.");
							vCnt ++;
						}
					}
			}
			if(vCnt > 0){
				return true;
			}
		 }


	//원하는 폼요소를 readonly 시키거나 해제함
	//frmList는 구분자를 | 사용하여 폼id를 나열, readonlyYn : true or false	
	function formElementChgReadOnly(frmList,readOnlyYn){
		var frmArr = frmList.split("|");
		
		for(var i=0; i< frmArr.length ; i++){
			var frmEmnt = $("#"+frmArr[i]);
			switch(frmEmnt.prop('type')){
			case "select-one" : 
//				$("#"+frmArr[i] +"> option").prop("disabled", readOnlyYn);
				$("#"+frmArr[i] ).prop("disabled", readOnlyYn);
				break;
			default :
				frmEmnt.attr("readonly", readOnlyYn);
				break;
			}
		}
	}
	
	 /*=======================================================================
	 기   능 : 더블셀렉트(표준분류용) V1.0  (json 객체, 셀렉트id, 표준분류) 이베이코리아
	 =======================================================================*/
	 function double_selectStndAsrt(jsonlist, subid, stndAsrt) {
	 	var tmpwidth;		//셀렉트 width 값
	 	var subselect = subid; //현재셀렉트 박스
	 	var code = subselect.val(); //셀렉트 값
	 	
	 	var parentsel = subselect.parent(); //셀렉트 부모
	 	var totsel = $('select', parentsel); //전체셀렉트 박스
	 	
	 	var cntsel = totsel.length; //셀렉트 갯수
	 	var idxsel = totsel.index(subselect); //현재 셀렉트 순번
	 	
	 	if ((cntsel-1) == idxsel) return; //최종 셀렉트인 경우 리턴
	 	
	 	var combosel = totsel.eq(idxsel + 1); //하위 셀릭트 박스
//	 	alert(subselect.children(':first').text());
	 	var txtsel = '<option value="">'+subselect.children(':first').text()+'</option>';
	 	var cnt = jsonlist.length;

	 	//하위 셀렉트의 초기화
//	 	$('select:gt('+idxsel+')\'', parentsel).html(txtsel);
	 	parentsel.find('select:gt('+idxsel+')').html(txtsel);
	 	
	 	//현재 코드가 없을경우 하위 셀렉트를 초기화 후 리턴
	 	if(code == "") {
	 		//최상위 셀렉트 박스의 경우 초기화
	 		if (idxsel == 0) {
	 			subselect.html(txtsel);
	 			for(var i=0; i < cnt; i++) {
	 				if(cnt > 1 && jsonlist[i].upcodeCd == null && jsonlist[i].stndAsrt == stndAsrt)
	 				subselect.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
	 			}
	 		}
	 		//$(window).resize();
	 		return;
	 	}
	 	
	 	
	 	//하위 셀렉트 박스의  셋팅
	 	for(var i=0; i< cnt; i++){
	 		
	 		if(cnt > 1 && jsonlist[i].upcodeCd == code) {
	 			
	 			combosel.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codeLnm+"</option>");
	 		}
	 	}
	 	
	 	//상위셀렉트와 하위셀렉트가 1:1인 경우 하위셀렉트 바로 선택
	 	var comboselopt = combosel.find("option");
	 	if(comboselopt.length==2){
	 		comboselopt.eq(1).attr("selected", "true");
	 		comboselopt.eq(1).change();  //201130 추가
	 	}
	 	//$(window).resize();
	 }
	 
	 function double_select_PNM(jsonlist, subid) {
		 	var tmpwidth;		//셀렉트 width 값
		 	var subselect = subid; //현재셀렉트 박스
		 	var code = subselect.val(); //셀렉트 값
		 	
		 	var parentsel = subselect.parent(); //셀렉트 부모
		 	var totsel = $('select', parentsel); //전체셀렉트 박스
		 	
		 	var cntsel = totsel.length; //셀렉트 갯수
		 	var idxsel = totsel.index(subselect); //현재 셀렉트 순번
		 	
		 	if ((cntsel-1) == idxsel) return; //최종 셀렉트인 경우 리턴
		 	
		 	var combosel = totsel.eq(idxsel + 1); //하위 셀릭트 박스
//		 	alert(subselect.children(':first').text());
		 	var txtsel = '<option value="">'+subselect.children(':first').text()+'</option>';
		 	var cnt = jsonlist.length;
		 	
		 	//하위 셀렉트의 초기화
//		 	$('select:gt('+idxsel+')\'', parentsel).html(txtsel);
		 	parentsel.find('select:gt('+idxsel+')').html(txtsel);
		 	
		 	//현재 코드가 없을경우 하위 셀렉트를 초기화 후 리턴
		 	if(code == "") {
		 		//최상위 셀렉트 박스의 경우 초기화
		 		if (idxsel == 0) {
		 			subselect.html(txtsel);
		 			for(var i=0; i < cnt; i++) {
		 				if(cnt > 1 && jsonlist[i].upcodeCd == null)
		 				subselect.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codePnm+"</option>");
		 			}
		 		}
		 		//$(window).resize();
		 		return;
		 	}
		 	
		 	//하위 셀렉트 박스의  셋팅
		 	for(var i=0; i< cnt; i++){
		 		if(cnt > 1 && jsonlist[i].upcodeCd == code) {
		 			combosel.append("<option value='"+jsonlist[i].codeCd+"'>"+jsonlist[i].codePnm+"</option>");
		 		}
		 	}
		 	//$(window).resize();
		 }
	 
	 
	 function IBSpostJsonString(urls, savejson, param, callback, syncyn) {
			var ajaxurl = urls;
			if(!isBlankStr(param)) {
				ajaxurl = urls + "?" +param;
			}
			
			
			
			var syn = true;
			if (syncyn) syn = syncyn;
			
			$.ajax({
				url: ajaxurl,
				async: syn,
				type: "POST",
				data: JSON.stringify(savejson, replacerXss),
				contentType: 'text/plain',
				dataType: 'json',
				beforeSend: function () {
									
					// 처리중이니 잠시 기다려 주십시요.
					showMsgBox("PRC", gMSG_PRC_WAIT);
					
					
				},
				success: callback
//				error: function (jqXHR, textStatus, errorThrown) {
//					//alert('에러발생...' + textStatus);
//					if (jqXHR.status == 403) {
//						location.href = containerPath + "/"; 
//					}
//					var res = {RESULT : {CODE:-1, MESSAGE:'시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.'}};
//					ibscallback(res);
//				}
			});
			
		}
	 
	 
	 //JSON SORT 
	 function sortJson(element, prop, propType, asc) {

	 	  switch (propType) {
	 	    case "int":
	 	      element = element.sort(function (a, b) {
	 	        if (asc) {
	 	          return (parseInt(a[prop]) > parseInt(b[prop])) ? 1 : ((parseInt(a[prop]) < parseInt(b[prop])) ? -1 : 0);
	 	        } else {
	 	          return (parseInt(b[prop]) > parseInt(a[prop])) ? 1 : ((parseInt(b[prop]) < parseInt(a[prop])) ? -1 : 0);
	 	        }
	 	      });
	 	      break;
	 	    default:
	 	      element = element.sort(function (a, b) {
	 	        if (asc) {
	 	          return (a[prop].toLowerCase() > b[prop].toLowerCase()) ? 1 : ((a[prop].toLowerCase() < b[prop].toLowerCase()) ? -1 : 0);
	 	        } else {
	 	          return (b[prop].toLowerCase() > a[prop].toLowerCase()) ? 1 : ((b[prop].toLowerCase() < a[prop].toLowerCase()) ? -1 : 0);
	 	        }
	 	      });
	 	  }
	  }
	 
	 function replacerXss(key, value) {
		 if(typeof value === "string") {
			 value = value.replace(/(\<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)\>|\<\/(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)\>)/g, "")
                          .replace(/((a|A)(l|L)(e|E)(r|R)(t|T))/g, "<!-- alert -->");
		 }
		 
		 return value;
	 }
	 
	 function replacerXssParam(param) {
		 if(typeof value === "string") {
			 param = param.replace(/(\<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)\>|\<\/(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)\>)/g, "")
			              .replace(/((a|A)(l|L)(e|E)(r|R)(t|T))/g, "<!-- alert -->");
		 }
		 
		 return param;
	 }
	  
	 //셀렉트박스 하나인 경우 바로 선택 .change 하위 존재시
	 function selectBoxSetup(selbx){
		 var selbxopt = selbx.find("option");
		 if(selbxopt.length==2){
			 selbxopt.eq(1).prop("selected","true").change();
		 }
		 
	 }
	