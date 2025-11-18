/**
===============================================================================
프로그램  명  : IBSheet 기본 환경 설정 Javascript
프로그램개요  : IBSheet의 환경을 설정한다.
                아래 함수는 IBSheet 기본 속성을 설정하기 전에 호출하여 사용한다.
                아래 함수는 ConfigXml의 비확장성을 보완하기 위해 사용한다.
작   성   자  : 아이비 리더스 이경희
작   성   일  : 2004.05.17
===============================================================================
수정자/수정일 :
수정사유/내역 :
===============================================================================
*/

/**
 * 메인 IBSheet의 환경을 설정한다.
 * param : sheet_obj IBSheet Object ID
 * return : 없음
 */
function init_sheet(sheet_obj)
{ 
    with (sheet_obj)
    {    
	    SetHeaderBackColor       ("#608bbe");  //해더행 배경색
	    SetDataBackColor         ("255,255,255");  //데이터행 배경색(홀수)
		SetDataAlternateBackColor ("250,250,250");  //데이터행 배경색(짝수)
		SetSelectBackColor       ("2783cb");  //선택행 배경색    
		SetSubSumBackColor       ("214,243,214");  //소계행 배경색
		SetCumulateBackColor     ("241,241,241");  //누계행 배경색
		SetSumBackColor          ("255,255,153");  //합계행 배경색
		
		SetHeaderFontColor       ("255,255,255");     //해더행 글자색
		
		//SetHeaderFontBold        (true);     //해더행 글자색
		
	    SetDataFontColor         ("85,85,85");     //데이터행 글자색
	    SetSumFontColor          ("85,85,85");     //합계행 글자색
	    
	    SetSheetFontName         ("");        //글자체, Default:굴림체
	    SetHeaderRowHeight       (30    );                //해더 행 높이, Default:26
	    SetSheetFontSize         (13    );                //글자크기, Default:9
	    SetDataRowHeight         (24    );                //데이터 행 높이, Default:20
	    SetFocusAfterProcess     (0);	     			  //프로세스 이후 해당 시트로 포커스를 줄지 안줄지 여부(0:포커스 이동 없음)
	  
	    
	    SelectHighLight       = true;                  //Default:true, 하일라이트 여부(SelectBackColor적용위함)
	    
	    /*
	    InLineColor           = RgbColor(192,213,225);  //안쪽선색
	    OutLineColor          = RgbColor(192,213,225);   //바깥쪽선색
	    EditableColorDiff     = false;                  //Default:false, Edit 가능/불가능구분 여부
	    
	    EditableColor         = RgbColor(255,255,0);    //Default:255,255,255, 흰색 Edit 가능 데이터 배경색
	    UnEditableColor       = RgbColor(192,192,92);   //Default:239,235,239, 회색 Edit 불가능 데이터 배경색
	    SelectHighLight       = true;                  //Default:true, 하일라이트 여부(SelectBackColor적용위함)
	    SelectFontBold        = true;                   //Default:false, 선택행 볼드여부
	    MultiSelection        = false;                   //Default:false, 다중 선택 여부
	    
	    FocusAfterProcess     = true;                   //Default:true, 조회후 포커스 뺏기여부
	    CountFontBold         = true;                   //Default:true, 건수글자볼드여부
	    DateFormatChar        = "-";                    //Default:., 날짜구분자,(-/.)
	    ScrollTrack           = false;                   //Default:false, 스크롤과 데이타 같이이동
	    RequestTimeOut        = 1200;                    //Default:60초, 응답대기시간,초단위
	    ShowSortArrow         = false;                  //Default:false, 소트 화살표 표시여부
	    ShowButtonImage       = 3;                      //Default:0(Focus 있을때 팝업 이미지 표시), 3(Edit 가능시 팝업/콤보이미지 표시), 데이터 타입이 dtPopup, dtPopupEdit, dtCombo, dtComboEdit 일때 이미지 표시종류
	    EditEnterBehavior     = "down"                  //Default:tab, 편집중 Enter행동자
	    EnterBehavior         = "tab";                  //Default:Edit, Enter행동자
	    HeadFlat              = false;                  //Default:3D 해더평면여부
	    WaitImageVisible      = true;                  //Default:true, 대기이미지 표시여부
	    DataAutoTrim          = false;                  //Default:true, 데이타 양쪽공백 트림여부
	    MultiLineText         = false;                  //Default:true, Shift+Enter 또는 Ctrl+Enter 이용 다중라인 입력가능여부
	    ToolTipOption		  = "width:320";
	    */
	    		
		SetEditable(1);	
		//편집 불가능한 셀을 구분하지 않음
		SetEditableColorDiff (0);

	    
	    SetSelectionMode(1);	//행 단위 선택
	    
	    SetCountFormat("선택 SELECTDATAROW행 / 총 ROWCOUNT건");
	    SetCountPosition(3);
	    SetComboOpenMode(1);
	    
//	    alert(Version());
		
    }
    
     
}
