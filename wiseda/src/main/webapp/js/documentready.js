//화면 로드시 공통 처리부분 여기에 추가한다.
 $(document).ready(function(){
	 
		//======================================================
		//메뉴 초기화
		//======================================================
		$(".menu_subject").hide();
//		$(".m_tit_tit, .ntit_tit").html($(".menu_title").html());
		//alert($(".location").text());
//		$(".m_tit_location, .ntit_loca").html($(".location").text());

	 //버튼 초기화...
	 
		//적용...(검색 팝업에서만 사용한다... 평소에는 숨겨놓자..)
		$('button.btn_apply').hide();
		//추가 버튼...
		$('button.btn_rqst_new').click(function() {
			//var menu = $("#addTreeMenu").show();
//			var menu = $("#addTreeMenu").show().position({
			var menu = $(this).next().show().position({
			my: "left top",
            at: "left bottom+2",
            of: this
					});
			$(document).one( "click", function() {
	            menu.hide();
	        });
	        return false;
		
		});
		//저장 버튼...
		$('button.btn_save').hide();
		//등록요청 버튼...
		$('button.btn_reg_rqst').hide();
		//결재처리 옆 아래 화살표 버튼
//		$('.btn_req_appr').hide();
		$('.btn_sel_appr').click(function(){
			var menu = $(this).parent().next().show().position({
				my: "left top",
	            at: "left bottom+2",
	            of: $(this).prev()
						});
				$(document).one( "click", function() {
		            menu.hide();
		        });
		        return false;
		}).parent().hide();
		
		
		
		
		//추가 버튼 메뉴...
		$("ul.add_button_menu").hide().menu({
			//position: { my: "left top", at: "left bottom"},
			create: function( event, ui ) {
				//alert($(".ui-menu").css("width"));
				$(this).css({
					"position" : "absolute",
					"width"    : "120px",
					"z-index"  : "1100"
				});
			}
			
		});
		//전체승인 반려 버튼 메뉴...
		$("ul.appr_button_menu").hide().menu({
			//position: { my: "left top", at: "left bottom"},
			create: function( event, ui ) {
				//alert($(".ui-menu").css("width"));
				$(this).css({
					"position" : "absolute",
					"width"    : "100px",
					"z-index"  : "1100"
				});
			}
		
		});
		//신규요청서 버튼... (우측 삼각형 제거)
		/*$('button.btn_rqst_new2').unbind('click').click(function() {
			
			var menu = $(this).next().show().position({
			my: "left top",
            at: "left bottom+2",
            of: this
					});
			$(document).one( "click", function() {
	            menu.hide();
	        });
	        return false;
		
		});*/
		
	 /*=======================================================================
		 기   능 : 폼내의 검색버튼 초기화  
	=======================================================================*/
	initSearchButton();
			
	//그리드 사이즈 조절 초기화...		
	bindibsresize();
	
//	EnterkeyProcess();

 });
 
 //화면 로드시 공통 처리부분 여기에 추가한다.
 $(window).on('load',function() {
	//상단 버튼을 보여준다.
//	$(".divLstBtn").find('.ui-icon').hide();
	$(".divLstBtn").show();

	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();
	 
	// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
	//마우스 오버 이미지 초기화
//	imgConvert($('div.tab_navi a img'));
	
	//탭 초기화....
	$("#tabs").tabs().show();
 });