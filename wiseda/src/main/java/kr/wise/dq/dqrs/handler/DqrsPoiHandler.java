package kr.wise.dq.dqrs.handler;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import kr.wise.dq.dqrs.service.DqrsPoiGapResult;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.result.service.ResultVO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DqrsPoiHandler {
	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private String type;				//.xls, .xlsx
	private Workbook workbook=null;		//excel

	private CellStyle titleStyle;
	private CellStyle headerStyle;
	private CellStyle listStyle;
	private CellStyle normalStyle;
	private CellStyle dataStyle;
	private CellStyle dataCenterStyle;
	private CellStyle resultStyle;
	private CellStyle dqiNtcNm;
	private CellStyle dqiNtCnt;


	//생성자
	public DqrsPoiHandler(String type) {
		logger.debug("Poi Constructer");
		this.type=type;

		if(type.equals("1")) {				//excel2003이하
//			type = ".xls";
			workbook = new HSSFWorkbook();	//.xls
		} else if(type.equals("2")) {		//excel2007이상
//			type = ".xlsx";
			workbook = new XSSFWorkbook();	//.xlsx
		} else if(type.equals("3")) {
//			type = ".xlsx";
			workbook = new SXSSFWorkbook(-1);	//.xlsx
		} else{
			logger.debug(type + " 올바르지 않은 형식");
		}

		///////////////////////////////////////////////////////////////////////////////////
		//스타일 변수
		//title style
		titleStyle=workbook.createCellStyle();	//title 스타일 생성

		titleStyle.setAlignment((short)2);					//가로 중앙 정렬
		titleStyle.setVerticalAlignment((short)1);			//세로 중앙 정렬

		Font titleFont = workbook.createFont();				//title 폰트 생성
		titleFont.setFontHeightInPoints((short)17);			//크기 17
//		titleFont.setBold(true);							//굵은체
		titleFont.setFontName("맑은 고딕");
		titleStyle.setFont(titleFont);						//스타일에 폰트 적용


		//header style
		headerStyle=workbook.createCellStyle();			//header 스타일 생성
		headerStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);		//bottom 윤곽석
		headerStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);			//left 윤관석
		headerStyle.setBorderRight(CellStyle.BORDER_MEDIUM);		//right 윤관석
		headerStyle.setBorderTop(CellStyle.BORDER_MEDIUM);			//top 윤곽선
		headerStyle.setAlignment((short)2);							//가로 중앙 정렬
		headerStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬
		headerStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index);	//배경색 지정
		headerStyle.setFillPattern(headerStyle.SOLID_FOREGROUND);	//배경 적용

		Font headerFont=workbook.createFont();						//header 폰트 생성
		headerFont.setFontHeightInPoints((short)13);				//크기 15
//		headerFont.setBold(true);									//굵은체
		headerFont.setColor(IndexedColors.WHITE.index); 			//흰색 글씨
		headerFont.setFontName("맑은 고딕");
		headerStyle.setFont(headerFont);							//스타일에 폰트 적용


		//listStyle
		listStyle=workbook.createCellStyle();				//list 스타일 생성
		listStyle.setBorderBottom(CellStyle.BORDER_THIN);			//bottom 윤곽석
		listStyle.setBorderLeft(CellStyle.BORDER_THIN);				//left 윤관석
		listStyle.setBorderRight(CellStyle.BORDER_THIN);			//right 윤관석
		listStyle.setBorderTop(CellStyle.BORDER_THIN);				//top 윤곽선
		listStyle.setAlignment((short)2);							//가로 중앙 정렬
		listStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬
		listStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);		//배경색 지정
		listStyle.setFillPattern(listStyle.SOLID_FOREGROUND); 		//배경 적용

		Font listFont=workbook.createFont();						//list 폰트 생성
		listFont.setFontHeightInPoints((short)11); 					//크기 13
//		listFont.setBold(true); 									//굵은체
		listFont.setFontName("맑은 고딕");
		listStyle.setFont(listFont);								//스타일에 폰트 적용


		//normal style
		normalStyle=workbook.createCellStyle();			//normal 스타일 생성
		normalStyle.setAlignment((short)1);							//가로 왼쪽 정렬
		normalStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬

		Font normalFont=workbook.createFont();						//normal 폰트 생성
		normalFont.setFontHeightInPoints((short)11); 				//크기 11
		normalFont.setFontName("맑은 고딕"); 							
		normalStyle.setFont(normalFont); 							//스타일에 폰트 적용


		//data style
		dataStyle=workbook.createCellStyle();				//data 스타일 생성
		dataStyle.setAlignment((short)3);							//가로 오른쪽 정렬
		dataStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬

		Font dataFont=workbook.createFont();						//data 폰트 생성
		dataFont.setFontHeightInPoints((short)11); 					//크기 11
		dataFont.setFontName("맑은 고딕"); 							
		dataStyle.setFont(dataFont); 								//스타일에 폰트 적용

		//dataCenter style
		dataCenterStyle=workbook.createCellStyle();				//data 스타일 생성
		dataCenterStyle.setAlignment((short)2);							//가로 오른쪽 정렬
		dataCenterStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬

		Font dataCenterFont=workbook.createFont();						//data 폰트 생성
		dataCenterFont.setFontHeightInPoints((short)11); 					//크기 11
		dataCenterFont.setFontName("맑은 고딕"); 							
		dataCenterStyle.setFont(dataCenterFont); 								//스타일에 폰트 적용


		//result style
		resultStyle=workbook.createCellStyle();			//result 스타일 생성
		resultStyle.setBorderBottom(CellStyle.BORDER_THIN);		//bottom 윤곽석
		resultStyle.setBorderLeft(CellStyle.BORDER_THIN);			//left 윤관석
		resultStyle.setBorderRight(CellStyle.BORDER_THIN);		//right 윤관석
		resultStyle.setBorderTop(CellStyle.BORDER_THIN);			//top 윤곽선
		resultStyle.setAlignment((short)3);							//가로 오른쪽 정렬
		resultStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬
		resultStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);		//배경색 지정
		resultStyle.setFillPattern(listStyle.SOLID_FOREGROUND); 		//배경 적용

		Font resultFont=workbook.createFont();						//result 폰트 생성
		resultFont.setFontHeightInPoints((short)12); 				//크기 12
//		resultFont.setBold(true);									//굵은체
		resultFont.setFontName("맑은 고딕"); 							
		resultStyle.setFont(resultFont); 							//스타일에 폰트 적용

		
		//업무규칙 폰트
		Font dqiNtcNmFont = workbook.createFont();
		dqiNtcNmFont.setFontName("맑은 고딕");
		dqiNtcNmFont.setFontHeight((short)500);
		dqiNtcNmFont.setColor(IndexedColors.DARK_BLUE.getIndex());
		dqiNtcNmFont.setBold(true);
		
		//업무규칙 내용 폰트
		Font dqiNtcCntFont = workbook.createFont();
		dqiNtcCntFont.setFontName("맑은 고딕");
		dqiNtcCntFont.setFontHeight((short)200);
		dqiNtcCntFont.setColor(IndexedColors.BROWN.getIndex());
		
		//업무규칙 스타일
		dqiNtcNm = workbook.createCellStyle();
		dqiNtcNm.setAlignment(HorizontalAlignment.CENTER);
		dqiNtcNm.setVerticalAlignment(VerticalAlignment.CENTER);
		dqiNtcNm.setFont(dqiNtcNmFont);
		
		//업무규칙 내용 스타일
		dqiNtCnt = workbook.createCellStyle();
		dqiNtCnt.setAlignment(HorizontalAlignment.LEFT);
		dqiNtCnt.setVerticalAlignment(VerticalAlignment.CENTER);
		dqiNtCnt.setWrapText(true);
		dqiNtCnt.setFont(dqiNtcCntFont);
	}
	
	//표준준수
	public void createExcelStnd(ResultVO term) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createExcelStnd_sheet1(term);
	}
	
	//표준준수
	public void createExcelStnd_sheet1(ResultVO term) throws Exception{	
		logger.debug("Poi createExcel1 started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("표준품질현황");
		Row row= null;
		Cell cell=null;

		
		int index;
		//행열 설정
		int rowNum= 10;	//데이터 +정보를 나타내주는 행
		int colNum=6;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.createCell(j);
			}
		}
		
		//컬럼 width설정
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 4000);
		
		sheet.setColumnWidth(9, 3500);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 3500);

		
		////////////////////////////////////////////////////////////////
		//값 입력
		//WiseDQ진단 종합 현황 
		row=sheet.getRow(0);
		cell=row.getCell(0);
		cell.setCellValue("WISE DQ 표준진단종합현황");
		
		//출력일
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellValue("출력일 : " + getTodayTime());
		
		//진단 데이터베이스 기본 정보
		row=sheet.getRow(2);
		cell=row.getCell(0);
		cell.setCellValue("진단 데이터베이스 기본 정보");
		

		String[] tempList={"진단기간", "DBMS명", "스키마명"};
		String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm()};
		index=0;
		for(int i=3;i<=5;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempList[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempValue[index++]);
		}
		
		BigDecimal multi = new BigDecimal("100");
		
		String[] tempList2={"분석영역", "검증룰명", "컬럼수", "불일치수", "불일치율", "적용율"};
		String[] tempValue2={"표준 진단"
							, "표준용어준수(+도메인)"
							, makeComma(term.getTotCnt()+"")
							, makeComma(term.getErrCnt()+"")
							, term.getErrCnt().divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, term.getTotCnt().subtract(term.getErrCnt()).divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		
		for(int i=0;i<tempList2.length;i++){
			row=sheet.getRow(7);
			cell=row.getCell(i);
			cell.setCellValue(tempList2[i]);

			//값 설정
			row=sheet.getRow(8);
			cell=row.getCell(i);
			cell.setCellValue(tempValue2[i]);
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(titleStyle);
		}

		//출력일 = dataStyle
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellStyle(dataStyle);

		//진단데이터베이스정보 - headerStyle
		row=sheet.getRow(2);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=3;i<6;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}
		
		row=sheet.getRow(7);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}
//		
		row=sheet.getRow(8);
		cell=row.getCell(0);
		cell.setCellStyle(listStyle);
		for(int i=1;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(dataStyle);
		}

		logger.debug("end style");
		////////////////////////////////////////////////////////////////
		//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

		for(int i=3;i<6;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간, DBMS명, 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}

		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 표준 진단 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//구조품질
	public void createExcelModel(ResultVO term, String tblCnt, String tblNcnt, String colCnt, String colNcnt, String pdmColCnt, String pdmColNcnt, String pdmTblCnt, String pdmTblNcnt) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createExcelModel_sheet1(term, tblCnt, tblNcnt, colCnt, colNcnt, pdmColCnt, pdmColNcnt, pdmTblCnt, pdmTblNcnt);
	}
	
	//구조품질
	public void createExcelModel_sheet1(ResultVO term, String tblCnt, String tblNcnt, String colCnt, String colNcnt, String pdmColCnt, String pdmColNcnt, String pdmTblCnt, String pdmTblNcnt) throws Exception{	
		logger.debug("Poi createExcel1 started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("진단결과종합현황");
		Row row= null;
		Cell cell=null;

		int index;
		//행열 설정
		int rowNum= 16;	//데이터 +정보를 나타내주는 행
		int colNum=6;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 3000);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 3000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 3000);
		sheet.setColumnWidth(15, 3000);


		////////////////////////////////////////////////////////////////
		//값 입력
		//WiseDQ진단 종합 현황 
		row=sheet.getRow(0);
		cell=row.getCell(0);
		cell.setCellValue("WISE DQ 구조 진단 종합 현황");

		//출력일
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellValue("출력일 : " + getTodayTime());


		//진단 데이터베이스 기본 정보
		row=sheet.getRow(2);
		cell=row.getCell(0);
		cell.setCellValue("진단 데이터베이스 기본 정보");


		String[] tempList={"진단기간", "DBMS명", "스키마명"};
		String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm()};
		index=0;
		for(int i=3;i<=5;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempList[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempValue[index++]);
		}
		
		BigDecimal multi = new BigDecimal("100");
		BigDecimal tbl = new BigDecimal(tblCnt.trim()==null?"0":tblCnt.trim());
		BigDecimal tblN = new BigDecimal(tblNcnt.trim()==null?"0":tblNcnt.trim());
		BigDecimal col = new BigDecimal(colCnt.trim()==null?"0":colCnt.trim());
		BigDecimal colN = new BigDecimal(colNcnt.trim()==null?"0":colNcnt.trim());
		BigDecimal pdmTbl = new BigDecimal(pdmTblCnt.trim()==null?"0":pdmTblCnt.trim());
		BigDecimal pdmTblN = new BigDecimal(pdmTblNcnt.trim()==null?"0":pdmTblNcnt.trim());
		BigDecimal pdmCol = new BigDecimal(pdmColCnt.trim()==null?"0":pdmColCnt.trim());
		BigDecimal pdmColN = new BigDecimal(pdmColNcnt.trim()==null?"0":pdmColNcnt.trim());
		
		String[] tempList2={"분석영역", "검증룰명", "테이블/컬럼수", "불일치수", "불일치율", "현행화율"};
		String[] tempValue2={"구조진단"
							, "물리DB 테이블 불일치"
							, makeComma(tbl+"")
							, makeComma(tblN+"")
							, tblN.divide(tbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, tbl.subtract(tblN).divide(tbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		String[] tempValue3={"구조진단"
							, "물리DB 컬럼 불일치"
							, makeComma(col+"")
							, makeComma(colN+"")
							, colN.divide(col, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, col.subtract(colN).divide(col, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		String[] tempValue4={"구조진단"
							, "모델정의서 테이블 불일치"
							, makeComma(pdmTbl+"")
							, makeComma(pdmTblN+"")
							, pdmTblN.divide(pdmTbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, pdmTbl.subtract(pdmTblN).divide(pdmTbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		String[] tempValue5={"구조진단"
							, "모델정의서 컬럼 불일치"
							, makeComma(pdmCol+"")
							, makeComma(pdmColN+"")
							, pdmColN.divide(pdmCol, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, pdmCol.subtract(pdmColN).divide(pdmCol, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};

		for(int i=0;i<tempList2.length;i++){
			row=sheet.getRow(7);
			cell=row.getCell(i);
			cell.setCellValue(tempList2[i]);

			//값 설정
			row=sheet.getRow(8);
			cell=row.getCell(i);
			cell.setCellValue(tempValue2[i]);
			
			//값 설정
			row=sheet.getRow(9);
			cell=row.getCell(i);
			cell.setCellValue(tempValue3[i]);
			
			//값 설정
			row=sheet.getRow(10);
			cell=row.getCell(i);
			cell.setCellValue(tempValue4[i]);
			
			//값 설정
			row=sheet.getRow(11);
			cell=row.getCell(i);
			cell.setCellValue(tempValue5[i]);
		}


		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(titleStyle);
		}

		//출력일 = dataStyle
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellStyle(dataStyle);

		//진단데이터베이스정보 - headerStyle
		row=sheet.getRow(2);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=3;i<6;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}
		
		row=sheet.getRow(7);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}
		
//		row=sheet.getRow(8);
//		cell=row.getCell(0);
//		cell.setCellStyle(listStyle);
		for(int j=8; j<12; j++) {
			row=sheet.getRow(j);
			cell=row.getCell(0);
			cell.setCellStyle(listStyle);
			for(int i=1;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(dataStyle);
			}
		}

		////////////////////////////////////////////////////////////////
		//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

		for(int i=3;i<6;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간, DBMS명, 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}
		
		sheet.addMergedRegion(new CellRangeAddress(8,11,0,0));

		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 구조 진단 종합 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//출력함수
	public void downExcel (String fileName,HttpServletResponse response) throws Exception{
		logger.debug("Poi DownExcel started");

		if(this.type.equals("1")) {
			this.type = ".xls";
		} else {
			this.type = ".xlsx";
		}
		//출력
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "attachment; Filename="+URLEncoder.encode(fileName,"UTF-8")+ this.type);
		OutputStream fileOut  = response.getOutputStream();
		workbook.write(fileOut);

		fileOut.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	//기타 필요한 함수
	//기간출력함수
	public String findStartEndDate(List<WamPrfMstrCommonVO> dataList){
		int max=00000000;
		int min=99999999;

		for(int i=0;i<dataList.size();i++){
			String temp = dataList.get(i).getAnaStrDtm();	//날짜시간
			if(temp!=null&&!temp.equals("")){
				int tempDate = Integer.parseInt(temp.substring(0,10).replace("-", ""));	//YYYYMMDD
				if(tempDate >= max)
					max=tempDate;
				if(tempDate<=min)
					min=tempDate;
			}
		}
		String minStr=min+"";
		String maxStr=max+"";
		
		if(max == 0)	//아무것도 안거친 경우
			return "";
		return minStr.substring(0,4)+"/"+minStr.substring(4,6)+"/"+minStr.substring(6,8)
				+" ~ "+ maxStr.substring(0,4)+"/"+maxStr.substring(4,6)+"/"+maxStr.substring(6,8);
	}

	//오늘 날짜 출력함수
	public String getTodayTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		return format.format(time);
	}

	//날짜출력함수
	public String getDateFormat(String data){
		if(data.equals(""))
			return "";
		return data.substring(0,data.length()-2);
	}

	//숫자3자리마다 , 생성함수
	public String makeComma(String str){
		if(str ==null || str.equals("") || str.equals("null") || str.length()==0)
			return "";

		String type = str.getClass().getName();
		DecimalFormat format = null;

		if(type.indexOf("String") == -1) {
			format = new DecimalFormat("###,###");
			return format.format(Long.parseLong(str));
		} else {
			return str;
		}
	}

	//sum함수
	public String sumOf(List<WamPrfMstrCommonVO> dataList, String key){
		
		BigInteger sum =BigInteger.ZERO;
		int countNull=0;
		for(int i=0;i<dataList.size();i++){
			if(key.equals("anaCnt")) {	//분석총건수총합
				String tempValue = dataList.get(i).getAnaCnt();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}

			}else if(key.equals("esnErCnt")){	//추정오류건수총합
				String tempValue = dataList.get(i).getEsnErCnt();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}
			}else if(key.equals("nullCnt")){ //널건수 총합
				String tempValue = dataList.get(i).getNullCnt()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}
			}else if(key.equals("spaceCnt")) {	//공백 건수 총합
				String tempValue = dataList.get(i).getSpaceCnt()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}
			}
			
		}

		if(countNull==dataList.size())
			return "";

		return sum+"";
	}


	//avg함수
	public String avgOf(List<WamPrfMstrCommonVO> dataList, String key){
		double sum=0;
		int countNull=0;
		for(int i=0;i<dataList.size();i++){
			if(key.equals("colErrRate")) {	//추정오류율평균
				String tempValue = dataList.get(i).getColErrRate();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}
			else if(key.equals("sigma")){	//시그마평균
				String tempValue = dataList.get(i).getSigma();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}else if(key.equals("minLen")){	//최소길이평균
				String tempValue = dataList.get(i).getMinLen()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}else if(key.equals("maxLen")){	//최대길이평균
				String tempValue = dataList.get(i).getMaxLen()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}
		}
		if(countNull==dataList.size())
			return "";
		if(sum==0)
			return "0";

		return String.format("%.2f", sum/(dataList.size()-countNull));
	}

	//소수점 문자열 검사함수
	public String checkPointNum(String value){
		String temp=value;
		if(value == null || value.length()==0)
			return "";
		else if(value.charAt(0)=='.'&&value.length()!=0)
			temp="0"+value;
		return temp;
	}
	
	public void createMetaExcelStnd(DqrsPoiGapResult term, List<DqrsPoiGapResult> sditmTblList, List<DqrsPoiGapResult> sditmList ,List<DqrsPoiGapResult> dmnList, String stndSort, String url) throws Exception{	
		logger.debug("Poi createExcel started");

		createMetaExcelStnd_sheet1(term, url);
		
		String sditmTblListStr = null;
		String sditmListStr = null;
		String dmnListStr = null;
		
		// 개별표준
		sditmTblListStr = "(상세결과)표준용어준수(+도메인)";
		sditmListStr = "(기준정보)표준용어";
		dmnListStr = "(기준정보)표준도메인";
		
		createMetaSditmTblResult_sheet(sditmTblList, sditmTblListStr);
		createMetaSditmResult_sheet(sditmList, sditmListStr);
		createMetaDmnResult_sheet(dmnList, dmnListStr);
	}
	
	//표준준수
		public void createMetaExcelStnd_sheet1(DqrsPoiGapResult term, String url) throws Exception{	
			logger.debug("Poi createExcel1 started");

			//Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet("표준품질현황");
			Row row= null;
			Cell cell=null;

			
			int index;
			//행열 설정
			int rowNum= 13;	//데이터 +정보를 나타내주는 행
			int colNum=6;
			
			//각 행열 대한 작업셀 생성
			for(int i=0;i<rowNum;i++){
				row=sheet.createRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.createCell(j);
				}
			}
			
			//컬럼 width설정
			sheet.setColumnWidth(0, 4500);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 3500);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 3500);
			sheet.setColumnWidth(8, 4000);
			
			sheet.setColumnWidth(9, 3500);
			sheet.setColumnWidth(10, 4000);
			sheet.setColumnWidth(11, 3500);

			
			////////////////////////////////////////////////////////////////
			//값 입력
			//WiseDQ진단 종합 현황 
			row=sheet.getRow(0);
			cell=row.getCell(0);
			cell.setCellValue("WISE DQ 표준진단종합현황");
			
			//출력일
			row=sheet.getRow(1);
			cell=row.getCell(0);
			cell.setCellValue("출력일 : " + getTodayTime());
			
			//진단 데이터베이스 기본 정보
			row=sheet.getRow(2);
			cell=row.getCell(0);
			cell.setCellValue("진단 데이터베이스 기본 정보");
			
			//진단대상 DB정보
			Map<String,Object> dbmsInfo = selectDbmsInfo(url);
			String dbmsType = (String) dbmsInfo.get("dbmsType");
			String dbIp = (String) dbmsInfo.get("dbIp");
			String dbServiceName = (String) dbmsInfo.get("dbServiceName");
			String port = (String) dbmsInfo.get("port");
			//IP 마스킹 처리
			if(term.getMaskingYn().equals("Y")) {
				dbIp = ipMasking(dbIp);
			}

			String[] tempList={"진단기간", "DBMS명", "스키마명", "DB종류", "DB아이피", "DB서비스명", "포트"};
			String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm(), dbmsType, dbIp, dbServiceName, port};
			index=0;
			for(int i=3;i<=9;i++){
				row=sheet.getRow(i);
				cell=row.getCell(0);
				cell.setCellValue(tempList[index]);

				//값 설정
				cell=row.getCell(3);
				cell.setCellValue(tempValue[index++]);
			}
			
			BigDecimal multi = new BigDecimal("100");
			
			logger.debug("term.getTotCnt() >>> " + term.getTotCnt());
			
			String[] tempList2={"분석영역", "검증룰명", "컬럼수", "불일치수", "불일치율", "적용율"};
			String[] tempValue2={"표준 진단"
								, "표준용어준수(+도메인)"
								, makeComma(term.getTotCnt()+"")
								, makeComma(term.getErrCnt()+"")
								, term.getTotCnt().equals(new BigDecimal("0"))?"0%":term.getErrCnt().divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
								, term.getTotCnt().equals(new BigDecimal("0"))?"0%":term.getTotCnt().subtract(term.getErrCnt()).divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
			
			for(int i=0;i<tempList2.length;i++){
				row=sheet.getRow(11);
				cell=row.getCell(i);
				cell.setCellValue(tempList2[i]);

				//값 설정
				row=sheet.getRow(12);
				cell=row.getCell(i);
				cell.setCellValue(tempValue2[i]);
			}

			////////////////////////////////////////////////////////////////
			//style 적용
			//WiseDQ 프로파일 조회 - titleStyle
			row=sheet.getRow(0);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(titleStyle);
			}

			//출력일 = dataStyle
			row=sheet.getRow(1);
			cell=row.getCell(0);
			cell.setCellStyle(dataStyle);

			//진단데이터베이스정보 - headerStyle
			row=sheet.getRow(2);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(headerStyle);
			}

			//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
			for(int i=3;i<=9;i++){
				row=sheet.getRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.getCell(j);
					if(j<=2)
						cell.setCellStyle(listStyle);
					else
						cell.setCellStyle(normalStyle);
				}
			}
			
			row=sheet.getRow(11);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(headerStyle);
			}
//				
			row=sheet.getRow(12);
			cell=row.getCell(0);
			cell.setCellStyle(listStyle);
			for(int i=1;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(dataStyle);
			}

			logger.debug("end style");
			////////////////////////////////////////////////////////////////
			//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

			for(int i=3;i<=9;i++){
				sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간, DBMS명, 스키마명
				sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
			}

			////////////////////////////////////////////////////////////////
			//헤더설정 및 프린트옵션 설정
			Header header = sheet.getHeader();
			header.setLeft("WISE DQ 표준 진단 현황");


			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			
		     //============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			 sheet.enableLocking();
		}
		
		//표준진단결과 표준용어준수
		public void createMetaSditmTblResult_sheet(List<DqrsPoiGapResult> sditmTblList, String sheetName) throws Exception{	
			logger.debug("Poi createSditmTblResult_sheet started");

			//Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row= null;
			Cell cell=null;

			
			int index;
			//행열 설정
			int rowNum = sditmTblList.size()+1;	//데이터 +정보를 나타내주는 행
			int colNum = 5;

			//각 행열 대한 작업셀 생성
			for(int i=0; i<rowNum; i++){
				row=sheet.createRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.createCell(j);
				}
			}
			
			//컬럼 width설정
			sheet.setColumnWidth(0, 5500);
			sheet.setColumnWidth(1, 8500);
			sheet.setColumnWidth(2, 8000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 14000);
			
			String[] tempHeader={"컬럼", "테이블", "데이터타입", "상태", "진단내용"};
			
			//style 적용
			//WiseDQ 프로파일 조회 - titleStyle
			row=sheet.getRow(0);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}

			if(sditmTblList.size() != 0) {
				
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tmpVO = sditmTblList.get(i);
					
					String[] tmpKey = { tmpVO.getPdmColPnm(), tmpVO.getPdmTblPnm(), tmpVO.getDataType(), tmpVO.getGapStatus().equals("표준을 준수하였습니다.")?"일치":"불일치", tmpVO.getGapStatus()};
					
					for(int j=0; j<colNum; j++) {
						cell = row.getCell(j);
						String tmpCellVal = tmpKey[j];
						if(tmpCellVal == null || tmpCellVal.equals("null") || tmpCellVal.length()==0) {
							tmpCellVal = "";
						}
						
						cell.setCellValue(tmpCellVal);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0; i<colNum; i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
			Header header = sheet.getHeader();
			header.setLeft("표준진단결과");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			 sheet.enableLocking();
		}
		
		
		//표준진단결과 (기준정보)표준용어
		public void createMetaSditmResult_sheet(List<DqrsPoiGapResult> dataList, String sheetName) throws Exception{	
			logger.debug("Poi createSditmResult_sheet started");

			//Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row= null;
			Cell cell=null;

			
			int index;
			//행열 설정
			int rowNum = dataList.size()+1;	//데이터 +정보를 나타내주는 행
			int colNum = 5;

			//각 행열 대한 작업셀 생성
			for(int i=0; i<rowNum; i++){
				row=sheet.createRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.createCell(j);
				}
			}
			
			//컬럼 width설정
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 10000);
			sheet.setColumnWidth(4, 4500);
			
			String[] tempHeader={"번호", "용어명", "영문약어명", "용어설명", "도메인"};
			
			//style 적용
			//WiseDQ 프로파일 조회 - titleStyle
			row=sheet.getRow(0);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}

			if(dataList.size() != 0) {
				
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tmpVO = dataList.get(i);
					
					String[] tmpKey = { Integer.toString(i+1), tmpVO.getSditmLnm(), tmpVO.getSditmPnm(), tmpVO.getObjDescn(), tmpVO.getInfotpLnm()};
					
					for(int j=0; j<colNum; j++) {
						cell = row.getCell(j);
						String tmpCellVal = tmpKey[j];
						if(tmpCellVal == null || tmpCellVal.equals("null") || tmpCellVal.length()==0) {
							tmpCellVal = "";
						}
						
						cell.setCellValue(tmpCellVal);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0; i<colNum; i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
			Header header = sheet.getHeader();
			header.setLeft("표준진단결과");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			sheet.enableLocking();
		}
		
		//표준진단결과 (기준정보)표준도메인
		public void createMetaDmnResult_sheet(List<DqrsPoiGapResult> dataList, String sheetName) throws Exception{	
			logger.debug("Poi createDmnResult_sheet started");

			//Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row= null;
			Cell cell=null;

			
			int index;
			//행열 설정
			int rowNum = dataList.size()+1;	//데이터 +정보를 나타내주는 행
			int colNum = 6;

			//각 행열 대한 작업셀 생성
			for(int i=0; i<rowNum; i++){
				row=sheet.createRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.createCell(j);
				}
			}
			
			//컬럼 width설정
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 8000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 14000);
			
			String[] tempHeader={"번호", "도메인명", "데이터타입", "전체자리수", "소수점이하 자리수", "설명"};
			
			//style 적용
			//WiseDQ 프로파일 조회 - titleStyle
			row=sheet.getRow(0);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}

			if(dataList.size() != 0) {
				
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tmpVO = dataList.get(i);
					
					String[] tmpKey = { Integer.toString(i+1), tmpVO.getDmnLnm(), tmpVO.getDataType(), tmpVO.getDataLen()
										, tmpVO.getDataScal(), tmpVO.getObjDescn()};
					
					for(int j=0; j<colNum; j++) {
						cell = row.getCell(j);
						String tmpCellVal = tmpKey[j];
						if(tmpCellVal == null || tmpCellVal.equals("null") || tmpCellVal.length()==0) {
							tmpCellVal = "";
						}
						
						cell.setCellValue(tmpCellVal);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0; i<colNum; i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
			Header header = sheet.getHeader();
			header.setLeft("표준진단결과");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			sheet.enableLocking();
		}
		
		//구조품질
		public void createStructExcelResult(DqrsPoiGapResult term, List<DqrsPoiGapResult> dataList, List<DqrsPoiGapResult> dbcTblList,
																  List<DqrsPoiGapResult> dbcColList, List<DqrsPoiGapResult> pdmTblList,
																  List<DqrsPoiGapResult> pdmColList, List<DqrsPoiGapResult> tblDdList, List<DqrsPoiGapResult> colDdList,
																  List<DqrsPoiGapResult> dqiErrList, String Model, String url) throws Exception{	
			logger.debug("Poi createExcel1 started");
			
			createExcelStructResult_sheet1(dataList, term, url);
			
			if(Model.equals("model")) {
				createStructTblExcelResult_sheet(dbcTblList, "(상세결과)물리DB기준 테이블 현행화", "SCHEMA");
				createStructColExcelResult_sheet(dbcColList, "(상세결과)물리DB기준 컬럼 현행화", "SCHEMA");
				createStructTblExcelResult_sheet(pdmTblList, "(상세결과)테이블정의서기준 테이블 현행화", "물리모델");
				createStructColExcelResult_sheet(pdmColList, "(상세결과)테이블정의서기준 컬럼 현행화", "물리모델");
			} else {
				createStructTblExcelResult_sheet(dbcTblList, "(상세결과)물리DB기준 테이블 현행화", "SCHEMA");
				createStructColExcelResult_sheet(dbcColList, "(상세결과)물리DB기준 컬럼 현행화", "SCHEMA");
				createStructTblExcelResult_sheet(pdmTblList, "(상세결과)테이블정의서기준 테이블 현행화", "개별모델");
				createStructColExcelResult_sheet(pdmColList, "(상세결과)테이블정의서기준 컬럼 현행화", "개별모델");
			}
			
			createStructTblDdExcelResult_sheet(tblDdList, "테이블정의서", "SCHEMA");
			createStructColDdExcelResult_sheet(colDdList, "컬럼정의서", "SCHEMA");
		}	
		
		public void createStructColDdExcelResult_sheet(List<DqrsPoiGapResult> dataList, String sheetName, String standard) {
			logger.debug("Poi createStructTblDdExcel started");
			
			// Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row = null;
			Cell cell = null;
			
			// 행열 설정
			int rowNum = 0; // 데이터 + 정보를 나타내주는 행
			if(dataList.size() + 1 > 65535) {
				rowNum = 65535; // 데이터 + 정보를 나타내주는 행
			} else {
				rowNum = dataList.size() + 1;
			}
			
			int colNum = 11;
			
			// 각 행열에 대한 작업셀 생성
			for(int i=0;i<rowNum;i++) {
				row = sheet .createRow(i);
				for(int j=0;j<colNum;j++) {
					cell = row.createCell(j);
				}
			}
			
			// 컬럼 width설정
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 8000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 8000);
			sheet.setColumnWidth(5, 6000);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 5000);
			sheet.setColumnWidth(8, 5000);
			sheet.setColumnWidth(9, 5000);
			sheet.setColumnWidth(10, 30000);
			
			// 하단부 table header
			String[] tempHeader = {"스키마", "테이블", "컬럼", "컬럼한글명", "데이터타입", "타입길이", "소수점길이", "NULL허용", "PK", "PK순번", "표준준수 점검내용"};
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}
			
			if(dataList.size() != 0) {
				
				// 하단부 table data
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tempVO = dataList.get(i);
					
					// tempHeader에 해당하는 값들을 tempKey에 저장
					String[] tempKey = {tempVO.getDbSchPnm(), tempVO.getPdmTblPnm(), tempVO.getPdmColPnm(), tempVO.getPdmColLnm()
										, tempVO.getDataType(), tempVO.getDataLen(), tempVO.getDataScal(), tempVO.getNoNullYn()
										, tempVO.getPkYn(), tempVO.getPkOrd(), tempVO.getDetailStatus()};
					
					for(int j=0;j<colNum;j++) { // 데이터
						cell = row.getCell(j);
						String tempCellValue = tempKey[j];
						if(tempCellValue == null || tempCellValue.equals("null") || tempCellValue.length() ==0) {
							tempCellValue = "";
						}
						cell.setCellValue(tempCellValue);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
//			for(int i=0;i<dataList.size();i++) {
//				row=sheet.getRow(i+1);
//				for(int j=0;j<7;j++) {
//					cell=row.getCell(j);
//					cell.setCellStyle(normalStyle);
//				}
//			}
			
			// 헤더설정 및 프린트옵션 설정
			Header header = sheet.getHeader();
			header.setLeft("WISE DQ 구조품질 진단 현황");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			 sheet.enableLocking();
		}
		
		public void createStructTblDdExcelResult_sheet(List<DqrsPoiGapResult> dataList, String sheetName, String standard) {
			logger.debug("Poi createStructTblDdExcel started");
			
			// Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row = null;
			Cell cell = null;
			
			// 행열 설정
			int rowNum = 0; // 데이터 + 정보를 나타내주는 행
			if(dataList.size() + 1 > 65535) {
				rowNum = 65535; // 데이터 + 정보를 나타내주는 행
			} else {
				rowNum = dataList.size() + 1;
			}
			
			int colNum = 6;
			
			// 각 행열에 대한 작업셀 생성
			for(int i=0;i<rowNum;i++) {
				row = sheet .createRow(i);
				for(int j=0;j<colNum;j++) {
					cell = row.createCell(j);
				}
			}
			
			// 컬럼 width설정
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 8000);
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 6000);
			
			// 하단부 table header
			String[] tempHeader = {"스키마", "테이블", "테이블한글명", "컬럼수", "표준위배컬럼수", "표준위배율(%)"};
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}
			
//			BigDecimal multi = new BigDecimal("100");
			
			if(dataList.size() != 0) {
				
				// 하단부 table data
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tempVO = dataList.get(i);
					
					// tempHeader에 해당하는 값들을 tempKey에 저장
					String[] tempKey = {tempVO.getDbSchPnm(), tempVO.getPdmTblPnm(), tempVO.getPdmTblLnm(), makeComma(tempVO.getColCnt()+""), makeComma(tempVO.getErrCnt()+""), makeComma(tempVO.getGapRate()+"")};
					
					for(int j=0;j<colNum;j++) { // 데이터
						cell = row.getCell(j);
						String tempCellValue = tempKey[j];
						if(tempCellValue == null || tempCellValue.equals("null") || tempCellValue.length() ==0) {
							tempCellValue = "";
						}
						cell.setCellValue(tempCellValue);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
//			for(int i=0;i<dataList.size();i++) {
//				row=sheet.getRow(i+1);
//				for(int j=0;j<7;j++) {
//					cell=row.getCell(j);
//					cell.setCellStyle(normalStyle);
//				}
//			}
			
			// 헤더설정 및 프린트옵션 설정
			Header header = sheet.getHeader();
			header.setLeft("WISE DQ 구조품질 진단 현황");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능으로 형변환 필요 ==================
			 sheet.enableLocking();
		}
		
		public void createExcelStructResult_sheet1(List<DqrsPoiGapResult> dataList, DqrsPoiGapResult term, String url) throws Exception {
			if(dataList.size() == 0) { // 없는 경우
				return;
			}
			
			//Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet("진단결과종합현황");
			Row row= null;
			Cell cell=null;

			int index;
			//행열 설정
			int rowNum = dataList.size() + 18;	//데이터 +정보를 나타내주는 행
			int colNum = 7;

			//각 행열 대한 작업셀 생성
			for(int i=0;i<rowNum;i++){
				row=sheet.createRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.createCell(j);
				}
			}

			//컬럼 width설정
			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 5000);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 3500);
			sheet.setColumnWidth(8, 4000);
			sheet.setColumnWidth(9, 4000);
			sheet.setColumnWidth(10, 3000);
			sheet.setColumnWidth(11, 3000);
			sheet.setColumnWidth(12, 3000);
			sheet.setColumnWidth(13, 3000);
			sheet.setColumnWidth(14, 3000);
			sheet.setColumnWidth(15, 3000);

			////////////////////////////////////////////////////////////////
			//일괄 높이 지정
			for(int i=1;i<rowNum;i++) {
				row = sheet.getRow(i);
				row.setHeight((short) 500);
			}
			
			//값 입력
			//WiseDQ진단 종합 현황 
			row=sheet.getRow(0);
			row.setHeight((short) 1000);
			cell=row.getCell(1);
			cell.setCellValue("WISE DQ 구조 진단 종합 현황");

			//출력일
			row=sheet.getRow(1);
			cell=row.getCell(1);
			cell.setCellValue("출력일 : " + getTodayTime());

			//진단 데이터베이스 기본 정보
			row=sheet.getRow(2);
			cell=row.getCell(1);
			cell.setCellValue("진단 데이터베이스 기본 정보");
			
			Map<String,Object> dbmsInfo = selectDbmsInfo(url);
			String dbmsType = (String) dbmsInfo.get("dbmsType");
			String dbIp = (String) dbmsInfo.get("dbIp");
			String dbServiceName = (String) dbmsInfo.get("dbServiceName");
			String port = (String) dbmsInfo.get("port");
			//IP 마스킹 처리
			if(term.getMaskingYn().equals("Y")) {
				dbIp = ipMasking(dbIp);
			}
			String[] tempList={"진단기간", "DBMS명", "스키마명", "DB종류", "DB아이피", "DB서비스명", "포트"};
			String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm(), dbmsType, dbIp, dbServiceName, port};

			logger.debug("term:{}", term.getAnaStrDtm() + " , " + term.getAnaEndDtm()+ " , "+ term.getDbConnTrgLnm()+ " , "+ term.getDbSchLnm() + " , " + term.getDbConnTrgId());
			
			index=0;

			for(int i=3;i<=9;i++){
				row=sheet.getRow(i);
				cell=row.getCell(1);
				cell.setCellValue(tempList[index]);

				//값 설정
				cell=row.getCell(2);
				cell.setCellValue(tempValue[index++]);
			}
			
			////////////////////////////////////////////////////////////////
			//style 적용
			//WiseDQ 프로파일 조회 - titleStyle
			row=sheet.getRow(0);
			for(int i=1;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(titleStyle);
			}
			
			//출력일 = dataStyle
			row=sheet.getRow(1);
			for(int i=1;i<colNum; i++) {
				cell = row.getCell(i);
				cell.setCellStyle(dataStyle);
			}
			
			//진단데이터베이스정보 - headerStyle
			row=sheet.getRow(2);
			for(int i=1;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(headerStyle);
			}

			//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
			for(int i=3;i<=9;i++) {
				row = sheet.getRow(i);
				for(int j=1;j<colNum;j++) {
					cell = row.getCell(j);
					if(j==1) {
						cell.setCellStyle(listStyle);
					} else {
						cell.setCellStyle(normalStyle);
					}
				}
			}
			
			// 표준 진단 결과
			row = sheet.getRow(13);
			cell = row.getCell(1);
			cell.setCellValue("구조 진단 결과");
			for(int i=1;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
			// 진단항목, 컬럼수, 불일치수, 불일치율(%), 적용율(%), 진단일자
			row = sheet.getRow(14);
			String[] resultList = {"진단항목", "테이블/컬럼수", "불일치수", "불일치율(%)", "적용율(%)", "진단일자"};
			for(int i=1;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellValue(resultList[i-1]);
				cell.setCellStyle(headerStyle);
			}
			// 값 부분 : 진단항목, 컬럼수, 불일치수, 불일치율(%), 적용율(%), 진단일자
			
			for(int j=0;j<4;j++) {
				row = sheet.getRow(15+j);
				
				DqrsPoiGapResult tempVo = dataList.get(j);
				
				String[] resultVal = {String.valueOf(tempVo.getItemNm()),String.valueOf(tempVo.getTblCnt()),String.valueOf(tempVo.getErrCnt()),String.valueOf(tempVo.getGapRate()),
						String.valueOf(tempVo.getStndRate()),String.valueOf(tempVo.getResultDate())};
				
				for(int i=1;i<colNum;i++) {
					cell = row.getCell(i);
					
					String tempCellValue = resultVal[i-1];
					
					if(tempCellValue == null || tempCellValue.equals("null") || tempCellValue.length() == 0) {
						tempCellValue = "";
					}
					cell.setCellValue(resultVal[i-1]);
					
					Font normal = workbook.createFont();
					normal.setFontHeightInPoints((short) 11);
					normal.setFontName("맑은 고딕");
					normalStyle.setFont(normal);
					cell.setCellStyle(normalStyle);
				}
			}
			
			////////////////////////////////////////////////////////////////
			//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
			sheet.addMergedRegion(new CellRangeAddress(0,0,1,colNum-1));	//WiseDQ 프로파일 조회
			sheet.addMergedRegion(new CellRangeAddress(1,1,1,colNum-1));	//출력일	
			sheet.addMergedRegion(new CellRangeAddress(2,2,1,colNum-1));	//진단 데이터베이스 기본 정보

			for(int i=3;i<=9;i++){ 
				sheet.addMergedRegion(new CellRangeAddress(i,i,2,colNum-1));
			}
					
			sheet.addMergedRegion(new CellRangeAddress(13,13,1,colNum-1));

			////////////////////////////////////////////////////////////////
			//헤더설정 및 프린트옵션 설정
			Header header = sheet.getHeader();
			header.setLeft("WISE DQ 구조 진단 종합 현황");
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			 sheet.enableLocking();
		}
		
		//dbms정보용 공통 메소드(표준, 구조진단에서 사용)
		public Map<String,Object> selectDbmsInfo(String url){
			Map<String,Object> dbmsInfo = new HashMap<String, Object>();
			// url
			String [] str = url.split(":|@|/");
			String dbmsType = "";
		    String port = "";
		    String dbIp = "";
		    String dbServiceName = "";
		    
			if("TIBERO".equals(str[1].toUpperCase()) || "TIB".equals(str[1].toUpperCase())){
				//"jdbc:tibero:thin:@{ip}:{port}:{db명}");
				String[] arr = url.split(":");
				dbIp = arr[3].substring(1);
				port = arr[4];
				dbServiceName = arr[5];
			}else if("ORACLE".equals(str[1].toUpperCase()) || "ORA".equals(str[1].toUpperCase())){
				//"jdbc:oracle:thin:@{ip}:{port}:{sid}");
				String[] arr = url.split(":|/");
				dbIp = arr[3].substring(1);
				if(arr.length == 6){
					port = arr[4];
					dbServiceName = arr[5];				
				}else{
					port = arr[4].split("/")[0];
					dbServiceName = arr[4].split("/")[1];
				}
			}else if("SYBASEIQ".equals(str[1].toUpperCase()) || "SYQ".equals(str[1].toUpperCase())){
			    //"jdbc:sybase:Tds:{ip}:{port}/{db명}");
				String[] arr = url.split(":");
				dbIp = arr[3];
				port = arr[4].split("[/]")[0];
				dbServiceName = arr[4].split("[/]")[1];
			}else if("SYBASEASE".equals(str[1].toUpperCase()) || "SYA".equals(str[1].toUpperCase())){
				//"jdbc:sybase:Tds:{ip}:{port}/{db명}");
				String[] arr = url.split(":");
				dbIp = arr[3];
				port = arr[4].split("[/]")[0];
				dbServiceName = arr[4].split("[/]")[1];
			}else if("DB2".equals(str[1].toUpperCase()) || "DB2".equals(str[1].toUpperCase())){
				//"jdbc:as400://{ip}/{db명}");
				String[] arr = url.split(":");
				dbIp = arr[2].split("[/]")[0].substring(2);
				dbServiceName = arr[2].split("[/]")[1];
			}else if("UNIVERSAL DATABASE".equals(str[1].toUpperCase()) || "UDB".equals(str[1].toUpperCase())){
				//       "jdbc:db2://{ip}:{port}/{db명}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1];
			}else if("MSSQL".equals(str[1].toUpperCase()) || "MSQ".equals(str[1].toUpperCase()) || "SQLSERVER".equals(str[1].toUpperCase())){
				//"jdbc:sqlserver://{ip}:{port};DatabaseName={db명}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split(";")[0];
				dbServiceName = arr[3].split(";")[1].substring(13);
			}else if("ALTIBASE".equals(str[1].toUpperCase()) || "ALT".equals(str[1].toUpperCase())){
				//"jdbc:Altibase://{ip}:{port}/{sid}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1];
			}else if("TERADATA".equals(str[1].toUpperCase()) || "TER".equals(str[1].toUpperCase())){
				//"jdbc:teradata://{ip}/CHARSET=ASCII,CLIENT_CHARSET=cp949");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
			}else if("MYSQL".equals(str[1].toUpperCase()) || "MYS".equals(str[1].toUpperCase())){
				//"jdbc:mysql://{ip}:{port}/{db명}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1];
			}else if("INFORMIX".equals(str[1].toUpperCase()) || "IFX".equals(str[1].toUpperCase())){
				//"jdbc:informix-sqli://{host}:{port}/{db명}:INFORMIXSERVER={인스턴스명};user:{유져명};password={패스워드}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1];
			}else if("MONGODB".equals(str[1].toUpperCase()) || "MDB".equals(str[1].toUpperCase())){
				//"mongodb://{ip}:{port}/{db명}");
				String[] arr = url.split(":");
				dbIp = arr[1].substring(2);
				port = arr[2].split("[/]")[0];
				dbServiceName = arr[2].split("[/]")[1];
			}else if("HIVE".equals(str[1].toUpperCase()) || "HIV".equals(str[1].toUpperCase())){
				//"jdbc:hive2://{ip}:{port}/{db명};auth=noSasl");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1].split(";")[0];
			}else if("CUBRID".equals(str[1].toUpperCase()) || "CBR".equals(str[1].toUpperCase())){
				//"jdbc:cubrid:{ip}:{port}:{db명}:public::");
				String[] arr = url.split(":");
				dbIp = arr[2];
				port = arr[3];
				dbServiceName = arr[4];
			}else if("POSTGRESQL".equals(str[1].toUpperCase()) || "POS".equals(str[1].toUpperCase())){
				//"jdbc:postgresql://{ip}:{port}/{dbName}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1];
			}else if("MARIA".equals(str[1].toUpperCase()) || "MRA".equals(str[1].toUpperCase()) || "MARIADB".equals(str[1].toUpperCase())){
				//"jdbc:mariadb://{ip}:{port}/{dbName}");
				String[] arr = url.split(":");
				dbIp = arr[2].substring(2);
				port = arr[3].split("[/]")[0];
				dbServiceName = arr[3].split("[/]")[1];
			}	
			dbmsInfo.put("dbmsType",str[1]);
			dbmsInfo.put("dbIp",dbIp);
			dbmsInfo.put("dbServiceName", dbServiceName);
			dbmsInfo.put("port", port);
			return dbmsInfo;
		}
		
		// 테이블 목록
		public void createStructTblExcelResult_sheet(List<DqrsPoiGapResult> dataList, String sheetName, String standard) throws Exception {
			logger.debug("Poi createStructTblExcel started");
			
			// Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row = null;
			Cell cell = null;
			
			// 행열 설정
			int rowNum = 0; // 데이터 + 정보를 나타내주는 행
			if(dataList.size() + 1 > 65535) {
				rowNum = 65535; // 데이터 + 정보를 나타내주는 행
			} else {
				rowNum = dataList.size() + 1;
			}
			
			int colNum = 4;
			
			// 각 행열에 대한 작업셀 생성
			for(int i=0;i<rowNum;i++) {
				row = sheet .createRow(i);
				for(int j=0;j<colNum;j++) {
					cell = row.createCell(j);
				}
			}
			
			// 컬럼 width설정
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 2000);
			sheet.setColumnWidth(3, 14000);
			
			// 하단부 table header
			String[] tempHeader = {"기준", "테이블", "상태", "진단내용"};
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}
			
			if(dataList.size() != 0) {
				
				// 하단부 table data
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tempVO = dataList.get(i);
					
					// tempHeader에 해당하는 값들을 tempKey에 저장
					String[] tempKey = {standard, tempVO.getTblNm(), tempVO.getState(), tempVO.getState().equals("일치")? "물리DB 기준으로 물리모델을 진단하였습니다. 일치합니다.":tempVO.getDetailStatus()};
					
					for(int j=0;j<colNum;j++) { // 데이터
						cell = row.getCell(j);
						String tempCellValue = tempKey[j];
						if(tempCellValue == null || tempCellValue.equals("null") || tempCellValue.length() ==0) {
							tempCellValue = "";
						}
						cell.setCellValue(tempCellValue);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
			
//				for(int i=0;i<dataList.size();i++) {
//					row=sheet.getRow(i+1);
//					for(int j=0;j<7;j++) {
//						cell=row.getCell(j);
//						cell.setCellStyle(normalStyle);
//					}
//				}
			
			// 헤더설정 및 프린트옵션 설정
			Header header = sheet.getHeader();
			header.setLeft("WISE DQ 구조품질 진단 현황");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			 sheet.enableLocking();
		}
		
		// 테이블 목록
		public void createStructColExcelResult_sheet(List<DqrsPoiGapResult> dataList, String sheetName, String standard) throws Exception {
			logger.debug("Poi createStructColExcel started");
			
			// Sheet, Row, Cell 생성
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);
			Row row = null;
			Cell cell = null;
			
			// 행열 설정
			int rowNum = 0; // 데이터 + 정보를 나타내주는 행
			if(dataList.size() + 1 > 65535) {
				rowNum = 65535; // 데이터 + 정보를 나타내주는 행
			} else {
				rowNum = dataList.size() + 1;
			}
			
			int colNum = 6;
			
			// 각 행열에 대한 작업셀 생성
			for(int i=0;i<rowNum;i++) {
				row = sheet .createRow(i);
				for(int j=0;j<colNum;j++) {
					cell = row.createCell(j);
				}
			}
			
			// 컬럼 width설정
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 5000);
			
			// 하단부 table header
			String[] tempHeader = {"기준", "테이블", "컬럼", "데이터타입", "상태", "진단내용"};
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}
			
			if(dataList.size() != 0) {
				
				// 하단부 table data
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					DqrsPoiGapResult tempVO = dataList.get(i);
					
					// tempHeader에 해당하는 값들을 tempKey에 저장
					String[] tempKey = {standard, tempVO.getTblNm(), tempVO.getColNm(), tempVO.getDataType(), tempVO.getState(), tempVO.getState().equals("일치")? "물리DB 기준으로 물리모델을 진단하였습니다. 일치합니다.":tempVO.getDetailStatus()};
					
					for(int j=0;j<colNum;j++) { // 데이터
						cell = row.getCell(j);
						String tempCellValue = tempKey[j];
						if(tempCellValue == null || tempCellValue.equals("null") || tempCellValue.length() ==0) {
							tempCellValue = "";
						}
						cell.setCellValue(tempCellValue);
					}
				}
			}
			
			row = sheet.getRow(0);
			for(int i=0;i<colNum;i++) {
				cell = row.getCell(i);
				cell.setCellStyle(listStyle);
			}
//				
			// 헤더설정 및 프린트옵션 설정
			Header header = sheet.getHeader();
			header.setLeft("WISE DQ 구조품질 진단 현황");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			
			//============sheet lock; XSSFSheet에서 지원하는 기능 ==================
			 sheet.enableLocking();
		}
		
		
		//2022.06.16 IP 마스킹 처리 기능 추가
		public static String ipMasking(String str) {
	        String replaceString = str;
	         
	        Matcher matcher = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$").matcher(str);
	         
	        if(matcher.matches()) {
	            replaceString = "";
	             
	            for(int i=1;i<=matcher.groupCount();i++) {
	                String replaceTarget = matcher.group(i);
	                if(i == 1 || i == 2) {
	                    char[] c = new char[replaceTarget.length()];
	                    Arrays.fill(c, '*');
	                     
	                    replaceString = replaceString + String.valueOf(c);
	                } else {
	                    replaceString = replaceString + replaceTarget;
	                }
	                if(i < matcher.groupCount()) {
	                    replaceString =replaceString + ".";
	                }
	            }
	        }
	         
	        return replaceString;
	    }

}
