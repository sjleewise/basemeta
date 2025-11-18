package kr.wise.commons.handler;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.meta.model.service.GovVo;
import kr.wise.meta.model.service.WaaGovServerInfo;
import kr.wise.meta.result.service.MtResultVO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class PoiHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String type; // .xls, .xlsx
	private Workbook workbook = null; // excel

	private CellStyle titleStyle;
	private CellStyle headerStyle;
	private CellStyle listStyle;
	private CellStyle normalStyle;
	private CellStyle dataStyle;
	private CellStyle dataCenterStyle;
	private CellStyle resultStyle;

	// 생성자
	public PoiHandler(String type) {
		logger.debug("Poi Constructer");
		this.type = type;

		if (type.equals(".xls")) // excel2003이하
			workbook = new HSSFWorkbook();
		else if (type.equals(".xlsx")) // excel2007이상
			workbook = new XSSFWorkbook();
		else {
			workbook = new HSSFWorkbook();
			logger.debug(type + " 올바르지 않은 형식");
		}

		// /////////////////////////////////////////////////////////////////////////////////
		// 스타일 변수
		// title style
		titleStyle = workbook.createCellStyle(); // title 스타일 생성

		titleStyle.setAlignment((short) 2); // 가로 중앙 정렬
		titleStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬

		Font titleFont = workbook.createFont(); // title 폰트 생성
		titleFont.setFontHeightInPoints((short) 17); // 크기 17
		titleFont.setBold(true); // 굵은체
		titleFont.setFontName("맑은 고딕");
		titleStyle.setFont(titleFont); // 스타일에 폰트 적용

		// header style
		headerStyle = workbook.createCellStyle(); // header 스타일 생성
		headerStyle.setBorderBottom(CellStyle.BORDER_MEDIUM); // bottom 윤곽석
		headerStyle.setBorderLeft(CellStyle.BORDER_MEDIUM); // left 윤관석
		headerStyle.setBorderRight(CellStyle.BORDER_MEDIUM); // right 윤관석
		headerStyle.setBorderTop(CellStyle.BORDER_MEDIUM); // top 윤곽선
		headerStyle.setAlignment((short) 2); // 가로 중앙 정렬
		headerStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬
		headerStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index); // 배경색
																					// 지정
		headerStyle.setFillPattern(headerStyle.SOLID_FOREGROUND); // 배경 적용

		Font headerFont = workbook.createFont(); // header 폰트 생성
		headerFont.setFontHeightInPoints((short) 13); // 크기 15
		headerFont.setBold(true); // 굵은체
		headerFont.setColor(IndexedColors.WHITE.index); // 흰색 글씨
		headerFont.setFontName("맑은 고딕");
		headerStyle.setFont(headerFont); // 스타일에 폰트 적용

		// listStyle
		listStyle = workbook.createCellStyle(); // list 스타일 생성
		listStyle.setBorderBottom(CellStyle.BORDER_THIN); // bottom 윤곽석
		listStyle.setBorderLeft(CellStyle.BORDER_THIN); // left 윤관석
		listStyle.setBorderRight(CellStyle.BORDER_THIN); // right 윤관석
		listStyle.setBorderTop(CellStyle.BORDER_THIN); // top 윤곽선
		listStyle.setAlignment((short) 2); // 가로 중앙 정렬
		listStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬
		listStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); // 배경색
																				// 지정
		listStyle.setFillPattern(listStyle.SOLID_FOREGROUND); // 배경 적용

		Font listFont = workbook.createFont(); // list 폰트 생성
		listFont.setFontHeightInPoints((short) 11); // 크기 13
		listFont.setBold(true); // 굵은체
		listFont.setFontName("맑은 고딕");
		listStyle.setFont(listFont); // 스타일에 폰트 적용

		// normal style
		normalStyle = workbook.createCellStyle(); // normal 스타일 생성
		normalStyle.setAlignment((short) 1); // 가로 왼쪽 정렬
		normalStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬

		Font normalFont = workbook.createFont(); // normal 폰트 생성
		normalFont.setFontHeightInPoints((short) 11); // 크기 11
		normalFont.setFontName("맑은 고딕");
		normalStyle.setFont(normalFont); // 스타일에 폰트 적용

		// data style
		dataStyle = workbook.createCellStyle(); // data 스타일 생성
		dataStyle.setAlignment((short) 3); // 가로 오른쪽 정렬
		dataStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬

		Font dataFont = workbook.createFont(); // data 폰트 생성
		dataFont.setFontHeightInPoints((short) 11); // 크기 11
		dataFont.setFontName("맑은 고딕");
		dataStyle.setFont(dataFont); // 스타일에 폰트 적용

		// dataCenter style
		dataCenterStyle = workbook.createCellStyle(); // data 스타일 생성
		dataCenterStyle.setAlignment((short) 2); // 가로 오른쪽 정렬
		dataCenterStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬

		Font dataCenterFont = workbook.createFont(); // data 폰트 생성
		dataCenterFont.setFontHeightInPoints((short) 11); // 크기 11
		dataCenterFont.setFontName("맑은 고딕");
		dataCenterStyle.setFont(dataCenterFont); // 스타일에 폰트 적용

		// result style
		resultStyle = workbook.createCellStyle(); // result 스타일 생성
		resultStyle.setBorderBottom(CellStyle.BORDER_THIN); // bottom 윤곽석
		resultStyle.setBorderLeft(CellStyle.BORDER_THIN); // left 윤관석
		resultStyle.setBorderRight(CellStyle.BORDER_THIN); // right 윤관석
		resultStyle.setBorderTop(CellStyle.BORDER_THIN); // top 윤곽선
		resultStyle.setAlignment((short) 3); // 가로 오른쪽 정렬
		resultStyle.setVerticalAlignment((short) 1); // 세로 중앙 정렬
		resultStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index); // 배경색
																					// 지정
		resultStyle.setFillPattern(listStyle.SOLID_FOREGROUND); // 배경 적용

		Font resultFont = workbook.createFont(); // result 폰트 생성
		resultFont.setFontHeightInPoints((short) 12); // 크기 12
		resultFont.setBold(true); // 굵은체
		resultFont.setFontName("맑은 고딕");
		resultStyle.setFont(resultFont); // 스타일에 폰트 적용

	}

	// 실적조회
	public void createExcelResult(List<ResultVO> dataList, ResultVO term,
			List<ResultVO> tblList, List<ResultVO> dmnRule,
			List<ResultVO> execList, List<ResultVO> errList,
			List<ResultVO> dqiErrList) throws Exception {
		logger.debug("Poi createExcel started");

		if (dataList.size() == 0) // 없는경우
			return;

		createExcelResult_sheet1(dataList, term);
		createExcelResult_sheet2(tblList);
		// createExcelResult_sheet3(dmnRule);
		createExcelResult_sheet4(execList);
		createExcelResult_sheet5(errList);
//		for (int i = 0; i < 13; i++) {
		for (int i = 0; i < dqiErrList.size()  ; i++) {
			createExcelResult_sheet6(dqiErrList.get(i));
			createExcelResult_sheet7(dqiErrList.get(i));
		}
	}

	// 실적조회
	public void createExcelResult_sheet1(List<ResultVO> dataList, ResultVO term)
			throws Exception {
		logger.debug("Poi createExcel1 started");

		if (dataList.size() == 0) // 없는경우
			return;

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("진단결과종합현황");
		Row row = null;
		Cell cell = null;

		int index;
		// 행열 설정
		int rowNum = dataList.size() + 14; // 데이터 +정보를 나타내주는 행
		int colNum = 8;
		
		DefaultCategoryDataset chartData = new DefaultCategoryDataset();

		for(int i=0; i<dataList.size()-1; i++) {
			ResultVO tempVO = dataList.get(i);
			
			chartData.addValue(tempVO.getErrRate()==null||tempVO.getErrRate().equals("null")||tempVO.getErrRate().equals("")? 0:tempVO.getErrRate(),
					tempVO.getDqiLnm()==null ? "" : tempVO.getDqiLnm(),
					tempVO.getDqiLnm()==null ? "" : tempVO.getDqiLnm());
		}
//		chartData.addValue(21,"작대기명","X축");
//		chartData.addValue(31,"작대기명","X축");


		   JFreeChart BarChartObject=ChartFactory.createBarChart("현황그래프","품질지표","오류율",chartData,PlotOrientation.VERTICAL,true,true,false); 
		   
		   /* 그림크기 */              
		   int width=200; /* 가로 */
		   int height=480; /* 세로 */
		           
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();         
		   ChartUtilities.writeChartAsPNG(baos,BarChartObject,width,height);
		           
		   int my_picture_id = workbook.addPicture(baos.toByteArray(), workbook.PICTURE_TYPE_PNG);
		   baos.close();

		   /* 앵커라고 엑셀 어디에 박아놓을지 결정하는 것. */
		   HSSFClientAnchor anchor = new HSSFClientAnchor();
		   int col1=0,row1=rowNum; // 엑셀 12열, 2번째 칸에서 그림이 그려진다.
		   
		   // API참조할 것.
		   //(col1, row1, x1, y1, col2, row2, x2, y2)  
		   anchor.setAnchor((short)col1, row1, 0, 0,(short)(colNum+10), row1, 0, 0);
		   // 그림이 1,11 에서 18,28까지 그러진다.



		  anchor.setAnchorType(2);
		   // Creating HSSFPatriarch object
		   HSSFPatriarch patriarch=(HSSFPatriarch) sheet.createDrawingPatriarch();
		           
		   //Creating picture with anchor and index information
		   patriarch.createPicture(anchor,my_picture_id);
		   /* Graph 만들기. Finish.*/

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < colNum; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 3500);
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

		// //////////////////////////////////////////////////////////////
		// 값 입력
		// WiseDQ진단 종합 현황
		row = sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellValue("WISE DQ 진단결과종합현황");

		// 출력일
		row = sheet.getRow(1);
		cell = row.getCell(0);
		cell.setCellValue("출력일 : " + getTodayTime());

		// 진단 데이터베이스 기본 정보
		row = sheet.getRow(2);
		cell = row.getCell(0);
		cell.setCellValue("진단 데이터베이스 기본 정보");

		String[] tempList = { "진단기간", "DBMS명", "스키마명" };
		String[] tempValue = {
				term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(),
				term.getDbConnTrgLnm(), term.getDbSchLnm() };
		index = 0;
		for (int i = 3; i <= 5; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);
			cell.setCellValue(tempList[index]);

			// 값 설정
			cell = row.getCell(3);
			cell.setCellValue(tempValue[index++]);
		}

		String[] tempList2 = { "", "대상 테이블", "대상 컬럼", "진단규칙 설정", "", "진단규칙 실행" };
		String[] tempValue2 = { "진단규칙 설정 현황",
				makeComma(dataList.get(dataList.size() - 1).getTblCnt() + ""),
				makeComma(dataList.get(dataList.size() - 1).getColCnt() + ""),
				makeComma(term.getTotCnt() + ""), "",
				makeComma(term.getErrCnt() + "") };
		// index=0;
		for (int i = 0; i < tempList2.length; i++) {
			row = sheet.getRow(7);
			cell = row.getCell(i);
			cell.setCellValue(tempList2[i]);

			// 값 설정
			row = sheet.getRow(8);
			cell = row.getCell(i);
			cell.setCellValue(tempValue2[i]);
		}

		// 하단부 table header
		String[] tempHeader = { "분석영역", "검증유형", "테이블", "컬럼", "전체데이터", "오류데이터",
				"오류율(%)", "sigma" };
		row = sheet.getRow(10);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		index = 0;
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.getRow(i + 11);

			ResultVO tempVO = dataList.get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { tempVO.getUppDqiLnm(), tempVO.getDqiLnm(),
					tempVO.getTblCnt() + "", tempVO.getColCnt() + "",
					tempVO.getTotCnt() + "", tempVO.getErrCnt() + "",
					tempVO.getErrRate() + "", tempVO.getSigma() + ""};

			// if(i==9){
			// cell=row.getCell(0);
			// cell.setCellValue(tempKey[0]); //분석유형
			// }

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "0";

				if (j == 1 && tempCellValue.equals("0"))
					tempCellValue = "";

				cell.setCellValue(makeComma(tempCellValue));

				if (j == 0 || j == 1) {
					cell.setCellStyle(normalStyle);
				} else {
					cell.setCellStyle(dataStyle);
				}
			}
		}

		// //////////////////////////////////////////////////////////////
		// style 적용
		// WiseDQ 프로파일 조회 - titleStyle
		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(titleStyle);
		}

		// 출력일 = dataStyle
		row = sheet.getRow(1);
		cell = row.getCell(0);
		cell.setCellStyle(dataStyle);

		// 진단데이터베이스정보 - headerStyle
		row = sheet.getRow(2);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		// 진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for (int i = 3; i < 6; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < colNum; j++) {
				cell = row.getCell(j);
				if (j <= 2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}

		row = sheet.getRow(7);
		for (int i = 0; i < 7; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		row = sheet.getRow(8);
		cell = row.getCell(0);
		cell.setCellStyle(listStyle);
		for (int i = 1; i < 7; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(dataStyle);
		}

		// 분석유형, ... , SIGMA - headerStyle
		row = sheet.getRow(10);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		// //////////////////////////////////////////////////////////////
		// 셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colNum - 1)); // WiseDQ
																			// 프로파일
																			// 조회
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, colNum - 1)); // 출력일
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, colNum - 1)); // 진단
																			// 데이터베이스
																			// 기본
																			// 정보

		for (int i = 3; i < 6; i++) {
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2)); // 진단기간,
																		// DBMS명,
																		// 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i, i, 3, colNum - 1));// 값
		}

		sheet.addMergedRegion(new CellRangeAddress(7, 7, 3, 4));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 5, 7));
		sheet.addMergedRegion(new CellRangeAddress(8, 8, 3, 4));
		sheet.addMergedRegion(new CellRangeAddress(8, 8, 5, 7));

		sheet.addMergedRegion(new CellRangeAddress(11, 12, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(13, 18, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(19, 21, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(22, 23, 0, 0));

		// if(rowNum-2!=9)
		// sheet.addMergedRegion(new CellRangeAddress(9,rowNum-2,0,0)); //관계분석
		// sheet.addMergedRegion(new CellRangeAddress(rowNum-1,rowNum-1,0,6));
		// //값 진단 종합

		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}

	// 테이블목록
	public void createExcelResult_sheet2(List<ResultVO> dataList)
			throws Exception {
		logger.debug("Poi createExcel2 started");

		if (dataList.size() == 0) // 없는경우
			return;

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("테이블목록");
		Row row = null;
		Cell cell = null;

		// 행열 설정
		int rowNum = 0; // 데이터 +정보를 나타내주는 행
		if (dataList.size() + 1 > 65535)
			rowNum = 65535; // 데이터 +정보를 나타내주는 행
		else
			rowNum = dataList.size() + 1;
		int colNum = 7;

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < colNum; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);

		// //////////////////////////////////////////////////////////////
		// 하단부 table header
		String[] tempHeader = { "번호", "SCHEMA", "테이블", "테이블(한글)", "수집여부",
				"진단여부", "제외사유" };
		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		for (int i = 0; i < rowNum - 1; i++) {
			row = sheet.getRow(i + 1);

			ResultVO tempVO = dataList.get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { (i + 1) + "", tempVO.getDbSchPnm(),
					tempVO.getDbcTblNm(), tempVO.getDbcTblKorNm(),
					tempVO.getExpYn(), tempVO.getPrfYn(),
					tempVO.getExpRsnCntn() };

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "";

				cell.setCellValue(tempCellValue);
			}
		}

		// //////////////////////////////////////////////////////////////

		row = sheet.getRow(0);
		for (int i = 0; i < 7; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(listStyle);
		}

		// for(int i=0; i<dataList.size(); i++) {
		// row=sheet.getRow(i+1);
		// for(int j=0;j<7;i++){
		// cell=row.getCell(j);
		// cell.setCellStyle(normalStyle);
		// }
		// }
		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}

	// 도메인
	public void createExcelResult_sheet3(List<ResultVO> dataList)
			throws Exception {
		logger.debug("Poi createExcel3 started");

		if (dataList.size() == 0) // 없는경우
			return;

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("도메인규칙");
		Row row = null;
		Cell cell = null;

		// 행열 설정
		int rowNum = 0; // 데이터 +정보를 나타내주는 행
		if (dataList.size() + 1 > 65535)
			rowNum = 65535; // 데이터 +정보를 나타내주는 행
		else
			rowNum = dataList.size() + 1;
		int colNum = 11;

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < 12; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 6000);
		sheet.setColumnWidth(10, 6000);
		sheet.setColumnWidth(11, 6000);
		sheet.setColumnWidth(12, 3000);

		// //////////////////////////////////////////////////////////////
		// 하단부 table header
		String[] tempHeader = { "번호", "테이블명", "한글테이블명", "컬럼명", "한글컬럼명",
				"데이터타입", "검증유형", "검증형식", "도메인", "검증기준ID", "검증기준명" }; // ,"분류코드","제외데이터","제외데이터 구분자","도메인 검토내역","관련 검토기준","제외사유"};
		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		for (int i = 0; i < rowNum - 1; i++) {
			row = sheet.getRow(i + 1);

			ResultVO tempVO = dataList.get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { (i + 1) + "", tempVO.getDbcTblNm(),
					tempVO.getDbcTblKorNm(), tempVO.getDbcColNm(),
					tempVO.getDbcColKorNm(), tempVO.getDataType(),
					tempVO.getDqiLnm(), tempVO.getPrfKndCd(),
					tempVO.getPrfId(), tempVO.getPrfNm(), tempVO.getPrfTyp() };

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "";

				cell.setCellValue(tempCellValue);
			}
		}

		// //////////////////////////////////////////////////////////////
		// style 적용
		// WiseDQ 프로파일 조회 - titleStyle
		row = sheet.getRow(0);
		for (int i = 0; i <= 11; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(listStyle);
		}

		// 진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for (int i = 1; i < rowNum; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j <= 11; j++) {
				cell.setCellStyle(normalStyle);
			}
		}

		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}

	// 실행목록
	public void createExcelResult_sheet4(List<ResultVO> dataList)
			throws Exception {
		logger.debug("Poi createExcel4 started");

		if (dataList.size() == 0) // 없는경우
			return;

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("실행목록");
		Row row = null;
		Cell cell = null;

		// 행열 설정
		int rowNum = 0; // 데이터 +정보를 나타내주는 행
		if (dataList.size() + 1 > 65535)
			rowNum = 65535; // 데이터 +정보를 나타내주는 행
		else
			rowNum = dataList.size() + 1;
		int colNum = 8;

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < 9; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);

		// //////////////////////////////////////////////////////////////
		// 하단부 table header
		String[] tempHeader = { "번호", "테이블명", "컬럼명", "검증유형", "검증기준ID", "검증기준명",
				"검증형식", "실행여부" };

		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		for (int i = 0; i < rowNum - 1; i++) {
			row = sheet.getRow(i + 1);

			ResultVO tempVO = dataList.get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { (i + 1) + "", tempVO.getDbcTblNm(),
					tempVO.getDbcColNm(), tempVO.getPrfKndCd(),
					tempVO.getPrfId(), tempVO.getPrfNm(), tempVO.getPrfTyp(),
					tempVO.getPrfYn() };

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "";

				cell.setCellValue(tempCellValue);
			}
		}

		// //////////////////////////////////////////////////////////////
		// style 적용
		// WiseDQ 프로파일 조회 - titleStyle
		row = sheet.getRow(0);
		for (int i = 0; i <= 8; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(listStyle);
		}

		// 진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for (int i = 1; i < dataList.size() + 1; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j <= 8; j++) {
				cell.setCellStyle(normalStyle);
			}
		}

		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}

	// 에러목록
	public void createExcelResult_sheet5(List<ResultVO> dataList)
			throws Exception {
		logger.debug("Poi createExcel5 started");

		if (dataList.size() == 0) // 없는경우
			return;

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("오류목록");
		Row row = null;
		Cell cell = null;

		// 행열 설정
		int rowNum = 0; // 데이터 +정보를 나타내주는 행
		if (dataList.size() + 1 > 65535)
			rowNum = 65535; // 데이터 +정보를 나타내주는 행
		else
			rowNum = dataList.size() + 1;
		int colNum = 11;

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < 12; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 4000);

		// //////////////////////////////////////////////////////////////
		// 하단부 table header
		String[] tempHeader = { "번호", "검증유형", "검증형식", "테이블명", "테이블한글명", "컬럼명",
				"컬럼한글명", "전체건수", "오류건수", "오류율(%)", "오류추정건수 SQL" };

		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		for (int i = 0; i < rowNum - 1; i++) {
			row = sheet.getRow(i + 1);

			ResultVO tempVO = dataList.get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { (i + 1) + "", tempVO.getDqiLnm(),
					tempVO.getPrfKndCd(), tempVO.getDbcTblNm(),
					tempVO.getDbcTblKorNm(), tempVO.getDbcColNm(),
					tempVO.getDbcColKorNm(), tempVO.getTotCnt() + "",
					tempVO.getErrCnt() + "", tempVO.getErrRate() + "",
					tempVO.getErrSql() };

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "";

				cell.setCellValue(tempCellValue);
			}
		}

		// //////////////////////////////////////////////////////////////
		// style 적용
		// WiseDQ 프로파일 조회 - titleStyle
		row = sheet.getRow(0);
		for (int i = 0; i <= 11; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(listStyle);
		}

		// 진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for (int i = 1; i < dataList.size() + 1; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j <= 11; j++) {
				cell.setCellStyle(normalStyle);
			}
		}

		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}

	// 오류목록
	public void createExcelResult_sheet6(ResultVO resultVO) throws Exception {
		logger.debug("Poi createExcel " + resultVO.getDqiLnm() + " started");

		if (resultVO.getErrList().size() == 0) // 없는경우
			return;

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(resultVO.getDqiLnm());
		Row row = null;
		Cell cell = null;

		// 행열 설정
		int rowNum = 0; // 데이터 +정보를 나타내주는 행
		if (resultVO.getErrList().size() + 1 > 65535)
			rowNum = 65535; // 데이터 +정보를 나타내주는 행
		else
			rowNum = resultVO.getErrList().size() + 1;
		int colNum = 10;

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < 11; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 4000);

		// //////////////////////////////////////////////////////////////
		// 하단부 table header
		// String[] tempHeader =
		// {"번호","진단룰명","분석유형","진단테이블(영문)","진단테이블(한글)","진단컬럼(영문)","진단컬럼(한글)"
		// ,"전체건수","오류건수","오류율(%)","오류추정건수 SQL"};

		String[] tempHeader = { "번호", "검증유형", "검증형식", "진단테이블(영문)", "진단테이블(한글)",
				"진단컬럼(영문)", "진단컬럼(한글)", "전체건수", "오류건수", "오류율(%)" };

		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		for (int i = 0; i < rowNum - 1; i++) {
			row = sheet.getRow(i + 1);

			ResultDataVO tempVO = resultVO.getErrList().get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { (i + 1) + "", tempVO.getDqiLnm(),
					tempVO.getPrfKndCd(), tempVO.getDbcTblNm(),
					tempVO.getDbcTblKorNm(), tempVO.getDbcColNm(),
					tempVO.getDbcColKorNm(), tempVO.getAnaCnt() + "",
					tempVO.getErrCnt() + "", tempVO.getErrRate() + "",
					tempVO.getErrSql() };

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "";

				cell.setCellValue(tempCellValue);
			}
		}

		// //////////////////////////////////////////////////////////////
		// style 적용
		// WiseDQ 프로파일 조회 - titleStyle
		row = sheet.getRow(0);
		for (int i = 0; i <= 10; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(listStyle);
		}

		// 진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for (int i = 1; i < resultVO.getErrList().size() + 1; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j <= 10; j++) {
				cell.setCellStyle(normalStyle);
			}
		}

		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}

	// 오류데이터
	public void createExcelResult_sheet7(ResultVO resultVO) throws Exception {
		logger.debug("Poi createExcel " + resultVO.getDqiLnm()
				+ " err data list started");

		if (resultVO.getErrData().size() == 0) // 없는경우
			return;

		// int colCnt = resultVO.getColCnt().intValue();

		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(resultVO.getDqiLnm() + "_오류데이터");
		Row row = null;
		Cell cell = null;

		// 행열 설정
		int rowNum = 0;

		if (resultVO.getErrData().size() + 1 > 65535)
			rowNum = 65535; // 데이터 +정보를 나타내주는 행
		else
			rowNum = resultVO.getErrData().size() + 1;

		int colNum = 9;

		// 각 행열 대한 작업셀 생성
		for (int i = 0; i < rowNum; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < 10; j++) {
				cell = row.createCell(j);
			}
		}

		// 컬럼 width설정
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);

		// //////////////////////////////////////////////////////////////
		// 하단부 table header
		String[] tempHeader = { "번호", "진단유형", "진단룰명", "진단테이블(영문)", "진단테이블(한글)",
				"진단컬럼(영문)", "진단컬럼(한글)", "오류데이터", "오류건수" };

		row = sheet.getRow(0);
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}

		// 하단부 table data
		for (int i = 0; i < rowNum - 1; i++) {
			row = sheet.getRow(i + 1);

			ResultDataVO tempVO = resultVO.getErrData().get(i);

			// tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey = { (i + 1) + "", tempVO.getPrfNm(),
					tempVO.getDqiLnm(), tempVO.getDbcTblNm(),
					tempVO.getDbcTblKorNm(), tempVO.getDbcColNm(),
					tempVO.getDbcColKorNm(), tempVO.getColNm1(),
					tempVO.getErrCnt() + "" };

			for (int j = 0; j < colNum; j++) { // 데이터
				cell = row.getCell(j);
				String tempCellValue = tempKey[j];
				if (tempCellValue == null || tempCellValue.equals("null")
						|| tempCellValue.length() == 0) // null값을 갖는 값이 있다면
					tempCellValue = "";

				cell.setCellValue(tempCellValue);
			}
		}

		// //////////////////////////////////////////////////////////////
		// style 적용
		// WiseDQ 프로파일 조회 - titleStyle
		row = sheet.getRow(0);
		for (int i = 0; i <= 9; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(listStyle);
		}

		// 진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for (int i = 1; i < resultVO.getErrData().size() + 1; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j <= 9; j++) {
				cell.setCellStyle(normalStyle);
			}
		}

		// //////////////////////////////////////////////////////////////
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");

		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);
	}
	
	
	//표준준수
	public void createExcelStnd(ResultVO term, List<ResultVO> sditmTblList, List<ResultVO> sditmList ,List<ResultVO> dmnList) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createExcelStnd_sheet1(term);
		createSditmTblResult_sheet(sditmTblList, "(상세결과)표준용어준수(+도메인)");
		createSditmResult_sheet(sditmList, "(기준정보)표준용어");
		createDmnResult_sheet(dmnList, "(기준정보)표준도메인");
	}
	
	//표준준수 - meta
	public void createMetaExcelStnd(MtResultVO term, List<MtResultVO> sditmTblList, List<MtResultVO> sditmList ,List<MtResultVO> dmnList, String stndSort) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createMetaExcelStnd_sheet1(term);
		
		String sditmTblListStr = null;
		String sditmListStr = null;
		String dmnListStr = null;
		
		if(stndSort.equals("ORG")) {
			// 기관표준
			sditmTblListStr = "(상세결과)기관표준용어준수(+도메인)";
			sditmListStr = "(기준정보)기관표준용어";
			dmnListStr = "(기준정보)기관표준도메인";
		} else if(stndSort.equals("GOV")) {
			// 범정보표준
			sditmTblListStr = "(상세결과)범정부표준용어준수(+도메인)";
			sditmListStr = "(기준정보)범정부표준용어";
			dmnListStr = "(기준정보)범정부표준도메인";
		} else {
			// 개별표준
			sditmTblListStr = "(상세결과)분야별표준용어준수(+도메인)";
			sditmListStr = "(기준정보)분야별표준용어";
			dmnListStr = "(기준정보)분야별표준도메인";
		}
		
		createMetaSditmTblResult_sheet(sditmTblList, sditmTblListStr);
		createMetaSditmResult_sheet(sditmList, sditmListStr);
		createMetaDmnResult_sheet(dmnList, dmnListStr);
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
	public void createStructExcelResult(MtResultVO term, List<MtResultVO> dataList, List<MtResultVO> dbcTblList,
															  List<MtResultVO> dbcColList, List<MtResultVO> pdmTblList,
															  List<MtResultVO> pdmColList, List<MtResultVO> tblDdList, List<MtResultVO> colDdList,
															  List<MtResultVO> dqiErrList, String Model, String url) throws Exception{	
		logger.debug("Poi createExcel1 started");
		
		createExcelStructResult_sheet1(dataList, term, url);
		
		if(Model.equals("model")) {
			createStructTblExcelResult_sheet(dbcTblList, "(상세결과)물리DB기준 테이블 현행화", "SCHEMA");
			createStructColExcelResult_sheet(dbcColList, "(상세결과)물리DB기준 컬럼 현행화", "SCHEMA");
			createStructTblExcelResult_sheet(pdmTblList, "(상세결과)테이블정의서기준 테이블 현행화", "물리모델");
			createStructColExcelResult_sheet(pdmColList, "(상세결과)테이블정의서기준 컬럼 현행화", "물리모델");
		} else {
			createStructTblExcelResult_sheet(dbcTblList, "(상세결과)개별DB기준 테이블 현행화", "SCHEMA");
			createStructColExcelResult_sheet(dbcColList, "(상세결과)개별DB기준 컬럼 현행화", "SCHEMA");
			createStructTblExcelResult_sheet(pdmTblList, "(상세결과)테이블정의서기준 테이블 현행화", "개별모델");
			createStructColExcelResult_sheet(pdmColList, "(상세결과)테이블정의서기준 컬럼 현행화", "개별모델");
		}
		
		createStructTblDdExcelResult_sheet(tblDdList, "테이블정의서", "SCHEMA");
		createStructColDdExcelResult_sheet(colDdList, "컬럼정의서", "SCHEMA");
	}	

	public void createStructTblDdExcelResult_sheet(List<MtResultVO> dataList, String sheetName, String standard) {
		logger.debug("Poi createStructTblDdExcel started");
		
		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(2, 2000);
		sheet.setColumnWidth(3, 6000);
		
		// 하단부 table header
		String[] tempHeader = {"스키마", "테이블", "코멘트", "컬럼수", "표준위배컬럼수", "표준위배율(%)"};
		row = sheet.getRow(0);
		for(int i=0;i<colNum;i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
//		BigDecimal multi = new BigDecimal("100");
		
		if(dataList.size() != 0) {
			
			// 하단부 table data
			for(int i=0; i<rowNum-1; i++) {
				row = sheet.getRow(i+1);
				
				MtResultVO tempVO = dataList.get(i);
				
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
		
//		for(int i=0;i<dataList.size();i++) {
//			row=sheet.getRow(i+1);
//			for(int j=0;j<7;j++) {
//				cell=row.getCell(j);
//				cell.setCellStyle(normalStyle);
//			}
//		}
		
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 구조품질 진단 현황");
		
		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	public void createStructColDdExcelResult_sheet(List<MtResultVO> dataList, String sheetName, String standard) {
		logger.debug("Poi createStructTblDdExcel started");
		
		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(2, 2000);
		sheet.setColumnWidth(3, 6000);
		
		// 하단부 table header
		String[] tempHeader = {"스키마", "테이블", "컬럼", "코멘트", "데이터타입", "타입길이", "소수점길이", "NULL허용", "PK", "PK순번", "표준준수 점검내용"};
		row = sheet.getRow(0);
		for(int i=0;i<colNum;i++) {
			cell = row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		if(dataList.size() != 0) {
			
			// 하단부 table data
			for(int i=0; i<rowNum-1; i++) {
				row = sheet.getRow(i+1);
				
				MtResultVO tempVO = dataList.get(i);
				
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
		
//		for(int i=0;i<dataList.size();i++) {
//			row=sheet.getRow(i+1);
//			for(int j=0;j<7;j++) {
//				cell=row.getCell(j);
//				cell.setCellStyle(normalStyle);
//			}
//		}
		
		// 헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 구조품질 진단 현황");
		
		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}

	public void createExcelStructResult_sheet1(List<MtResultVO> dataList, MtResultVO term, String url) throws Exception {
		if(dataList.size() == 0) { // 없는 경우
			return;
		}
		
		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("진단결과종합현황");
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
		
		// url
		String [] str = url.split(":|@|/");
	    String port = "";
	    String dbIp = "";
	    String dbServiceName = "";
	    
		if("TIBERO".equals(str[1].toUpperCase()) || "TIB".equals(str[1].toUpperCase())){
			//"jdbc:tibero:thin:@{ip}:{port}:{db명}");
			String[] arr = url.split("[:]");
			dbIp = arr[3].substring(1);
			port = arr[4].substring(1);
			dbServiceName = arr[5];
		}else if("ORACLE".equals(str[1].toUpperCase()) || "ORA".equals(str[1].toUpperCase())){
			//"jdbc:oracle:thin:@{ip}:{port}:{sid}");
			String[] arr = url.split(":");
			dbIp = arr[3].substring(1);
			port = arr[4];
			dbServiceName = arr[5];
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
		}else if("MSSQL".equals(str[1].toUpperCase()) || "MSQ".equals(str[1].toUpperCase())){
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
		}else if("MARIA".equals(str[1].toUpperCase()) || "MRA".equals(str[1].toUpperCase())){
			//"jdbc:mariadb://{ip}:{port}/{dbName}");      
			String[] arr = url.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
		}		
		String[] tempList={"진단기간", "DBMS명", "스키마명", "DB종류", "DB아이피", "DB서비스명", "포트"};
		String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm(), str[1], dbIp, dbServiceName, port};
		
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
			
			MtResultVO tempVo = dataList.get(j);
			
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
	}
	// 테이블 목록
	public void createStructTblExcelResult_sheet(List<MtResultVO> dataList, String sheetName, String standard) throws Exception {
		logger.debug("Poi createStructTblExcel started");
		
		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(3, 6000);
		
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
				
				MtResultVO tempVO = dataList.get(i);
				
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
	}
	
	// 테이블 목록
	public void createStructColExcelResult_sheet(List<MtResultVO> dataList, String sheetName, String standard) throws Exception {
		logger.debug("Poi createStructColExcel started");
		
		// Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
				
				MtResultVO tempVO = dataList.get(i);
				
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
		sheet.setColumnWidth(1, 8000);
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
							, tblN.intValue()==0?"100%":(tblN.divide(tbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")
							, tbl.intValue()==0?"0%":(tbl.subtract(tblN).divide(tbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")};
		String[] tempValue3={"구조진단"
							, "물리DB 컬럼 불일치"
							, makeComma(col+"")
							, makeComma(colN+"")
							, colN.intValue()==0?"100%":(colN.divide(col, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")
							, col.intValue()==0?"0%":(col.subtract(colN).divide(col, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")};
		String[] tempValue4={"구조진단"
							, "모델정의서 테이블 불일치"
							, makeComma(pdmTbl+"")
							, makeComma(pdmTblN+"")
							, pdmTblN.intValue()==0?"100%":(pdmTblN.divide(pdmTbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")
							, pdmTbl.intValue()==0?"0%":(pdmTbl.subtract(pdmTblN).divide(pdmTbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")};
		String[] tempValue5={"구조진단"
							, "모델정의서 컬럼 불일치"
							, makeComma(pdmCol+"")
							, makeComma(pdmColN+"")
							, pdmColN.intValue()==0?"100%":(pdmColN.divide(pdmCol, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")
							, pdmCol.intValue()==0?"0%":(pdmCol.subtract(pdmColN).divide(pdmCol, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%")};

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

	//표준진단결과 표준용어준수
	public void createSditmTblResult_sheet(List<ResultVO> sditmTblList, String sheetName) throws Exception{	
		logger.debug("Poi createSditmTblResult_sheet started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(1, 5500);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 10000);
//			sheet.setColumnWidth(5, 4000);
//			sheet.setColumnWidth(6, 4000);
//			sheet.setColumnWidth(7, 3500);
//			sheet.setColumnWidth(8, 4000);
//			
//			sheet.setColumnWidth(9, 3500);
//			sheet.setColumnWidth(10, 4000);
//			sheet.setColumnWidth(11, 3500);
		
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
				
				ResultVO tmpVO = sditmTblList.get(i);
				
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
	}
	
	//표준진단결과 (기준정보)표준용어
	public void createSditmResult_sheet(List<ResultVO> dataList, String sheetName) throws Exception{	
		logger.debug("Poi createSditmResult_sheet started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(1, 4500);
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
				
				ResultVO tmpVO = dataList.get(i);
				
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
	}
	
	//표준진단결과 (기준정보)표준도메인
	public void createDmnResult_sheet(List<ResultVO> dataList, String sheetName) throws Exception{	
		logger.debug("Poi createDmnResult_sheet started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(4, 9000);
		
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
				
				ResultVO tmpVO = dataList.get(i);
				
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
	}
		
		
	//meta용 -------------------------------------------------
		
	//표준준수
	public void createMetaExcelStnd_sheet1(MtResultVO term) throws Exception{	
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
		
		logger.debug("term.getTotCnt() >>> " + term.getTotCnt());
		
		String[] tempList2={"분석영역", "검증룰명", "컬럼수", "불일치수", "불일치율", "적용율"};
		String[] tempValue2={"표준 진단"
							, "표준용어준수(+도메인)"
							, makeComma(term.getTotCnt()+"")
							, makeComma(term.getErrCnt()+"")
							, term.getTotCnt().equals(new BigDecimal("0"))?"0%":term.getErrCnt().divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, term.getTotCnt().equals(new BigDecimal("0"))?"0%":term.getTotCnt().subtract(term.getErrCnt()).divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		
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
	public void createMetaExcelModel(MtResultVO term, List<MtResultVO> qualStatus) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createMetaExcelModel_sheet1(term, qualStatus);
	}
	
	//구조품질
	public void createMetaExcelModel_sheet1(MtResultVO term, List<MtResultVO> qualStatus) throws Exception{	
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
		sheet.setColumnWidth(1, 8000);
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
		
//		BigDecimal multi = new BigDecimal("100");
//		BigDecimal tbl = new BigDecimal(tblCnt.trim()==null?"0":tblCnt.trim());
//		BigDecimal tblN = new BigDecimal(tblNcnt.trim()==null?"0":tblNcnt.trim());
//		BigDecimal col = new BigDecimal(colCnt.trim()==null?"0":colCnt.trim());
//		BigDecimal colN = new BigDecimal(colNcnt.trim()==null?"0":colNcnt.trim());
//		BigDecimal pdmTbl = new BigDecimal(pdmTblCnt.trim()==null?"0":pdmTblCnt.trim());
//		BigDecimal pdmTblN = new BigDecimal(pdmTblNcnt.trim()==null?"0":pdmTblNcnt.trim());
//		BigDecimal pdmCol = new BigDecimal(pdmColCnt.trim()==null?"0":pdmColCnt.trim());
//		BigDecimal pdmColN = new BigDecimal(pdmColNcnt.trim()==null?"0":pdmColNcnt.trim());
		
		
		String[] tempList2={"진단항목", "테이블/컬럼수", "불일치수", "불일치율", "현행화율", "최근진단일자"};
		
		String[] tempValue2={qualStatus.get(0).getItemNm()
							, qualStatus.get(0).getObjCnt()
							, qualStatus.get(0).getErrorCnt()
							, qualStatus.get(0).getGapRate()
							, qualStatus.get(0).getStndRate()
							, qualStatus.get(0).getResultDate()};
		
		String[] tempValue3={qualStatus.get(1).getItemNm()
							, qualStatus.get(1).getObjCnt()
							, qualStatus.get(1).getErrorCnt()
							, qualStatus.get(1).getGapRate()
							, qualStatus.get(1).getStndRate()
							, qualStatus.get(1).getResultDate()};
		String[] tempValue4={qualStatus.get(2).getItemNm()
							, qualStatus.get(2).getObjCnt()
							, qualStatus.get(2).getErrorCnt()
							, qualStatus.get(2).getGapRate()
							, qualStatus.get(2).getStndRate()
							, qualStatus.get(2).getResultDate()};
		String[] tempValue5={qualStatus.get(3).getItemNm()
							, qualStatus.get(3).getObjCnt()
							, qualStatus.get(3).getErrorCnt()
							, qualStatus.get(3).getGapRate()
							, qualStatus.get(3).getStndRate()
							, qualStatus.get(3).getResultDate()};

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
		
//			row=sheet.getRow(8);
//			cell=row.getCell(0);
//			cell.setCellStyle(listStyle);
//		for(int j=8; j<12; j++) {
//			row=sheet.getRow(j);
//			cell=row.getCell(0);
//			cell.setCellStyle(listStyle);
//			for(int i=1;i<colNum;i++){
//				cell=row.getCell(i);
//				cell.setCellStyle(dataStyle);
//			}
//		}

		////////////////////////////////////////////////////////////////
		//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

		for(int i=3;i<6;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간, DBMS명, 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}
		
//		sheet.addMergedRegion(new CellRangeAddress(8,11,0,0));

		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 구조 진단 종합 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}	

	//표준진단결과 표준용어준수
	public void createMetaSditmTblResult_sheet(List<MtResultVO> sditmTblList, String sheetName) throws Exception{	
		logger.debug("Poi createSditmTblResult_sheet started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(1, 5500);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 10000);
//				sheet.setColumnWidth(5, 4000);
//				sheet.setColumnWidth(6, 4000);
//				sheet.setColumnWidth(7, 3500);
//				sheet.setColumnWidth(8, 4000);
//				
//				sheet.setColumnWidth(9, 3500);
//				sheet.setColumnWidth(10, 4000);
//				sheet.setColumnWidth(11, 3500);
		
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
				
				MtResultVO tmpVO = sditmTblList.get(i);
				
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
	}
	
	//표준진단결과 (기준정보)표준용어
	public void createMetaSditmResult_sheet(List<MtResultVO> dataList, String sheetName) throws Exception{	
		logger.debug("Poi createSditmResult_sheet started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(1, 4500);
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
				
				MtResultVO tmpVO = dataList.get(i);
				
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
	}
	
	//표준진단결과 (기준정보)표준도메인
	public void createMetaDmnResult_sheet(List<MtResultVO> dataList, String sheetName) throws Exception{	
		logger.debug("Poi createDmnResult_sheet started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(sheetName);
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
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 10000);
		
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
				
				MtResultVO tmpVO = dataList.get(i);
				
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
	}

	// 범정부수동업로드템플릿
		public void createExcelGovList(List<GovVo> dataList, String fileNm) throws Exception {
			logger.debug("Poi createExcel started");

			if (dataList.size() == 0) { // 없는경우
				return;
		}
			
			this.createExcelGovList_sheet(dataList, fileNm);

		}
		
		//범정부수동업로드템플릿
		public void createExcelGovList_sheet(List<GovVo> dataList, String sheetName) throws Exception{	
			logger.debug("Poi createDmnResult_sheet started");
			
			//Sheet, Row, Cell 생성
			Sheet sheet = workbook.createSheet(sheetName);
			Row row= null;
			Cell cell=null;

			
			int index;
			//행열 설정
			int rowNum = dataList.size()+1;	//데이터 +정보를 나타내주는 행
			int colNum = 44;

			//각 행열 대한 작업셀 생성
			for(int i=0; i<rowNum; i++){
				row=sheet.createRow(i);
				for(int j=0;j<colNum;j++){
					cell=row.createCell(j);
				}
			}
			
			//컬럼 width설정(번호 빼고 동일 크기)
			sheet.setColumnWidth(0, 2000);
			for(int i = 1; i < colNum; i++){
				sheet.setColumnWidth(i, 4000);
			}
			
			String[] tempHeader={"ORG_NM", "INFO_SYS_NM", "USER_ID", "DB_CONN_TRG_PNM", "DB_CONN_TRG_LNM", "DB_DESCN", "APLY_BIZ_NM", "DBMS_TYP_CD"
								, "DBMS_VERS_CD", "OS_KND_CD", "OS_VER_NM", "CONST_DT", "TBL_CNT", "DATA_CPCT", "PDATA_EXPT_RSN", "DB_SCH_PNM"
								, "MTA_TBL_PNM", "MTA_TBL_LNM", "TBL_DESCN", "TBL_VOL", "TBL_TYP_NM", "DQ_DGNS_YN", "SUBJ_ID", "SUBJ_NM", "PRSV_TERM"
								, "OCCR_CYL", "OPEN_RSN_CD", "NOPEN_RSN", "NOPEN_DTL_REL_BSS", "OPEN_DATA_LST", "MTA_COL_PNM", "MTA_COL_LNM", "COL_DESCN"
								, "DATA_TYPE", "DATA_LEN", "DATA_FMT", "NONUL_YN", "PK_YN", "FK_YN", "CONST_CND", "OPEN_YN", "PRI_RSN", "PRSN_INFO_YN", "ENC_TRG_YN"
								};
			
			//style 적용
			//WiseDQ 프로파일 조회 - titleStyle
			row=sheet.getRow(0);
			for(int i=0;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellValue(tempHeader[i]);
			}
			
			// //////////////////////////////////////////////////////////////
			// 값 입력
			if(dataList.size() != 0) {
				
				for(int i=0; i<rowNum-1; i++) {
					row = sheet.getRow(i+1);
					
					GovVo tmpVO = dataList.get(i);
					
					String[] tmpKey = { tmpVO.getOrgNm(), tmpVO.getInfoSysNm(), tmpVO.getCrgUserNm(), tmpVO.getDbPnm(), tmpVO.getDbLnm()
										, tmpVO.getDbDescn(), tmpVO.getApplBz(), tmpVO.getDbmsTypCd(), tmpVO.getDbmsVersCd(), tmpVO.getOsKind()
										, tmpVO.getOsVers(), tmpVO.getEsblhDt(), tmpVO.getTblCnt(), tmpVO.getDataCapa(), tmpVO.getGthExcptRsn()
										, tmpVO.getSchPnm(), tmpVO.getTblPnm(), tmpVO.getTblLnm(), tmpVO.getTblDescn(), tmpVO.getTblVol()
										, tmpVO.getTblDiv(), tmpVO.getDqDgnsYn(), tmpVO.getSubjId(), tmpVO.getSubjNm(), tmpVO.getPrsvTerm()
										, tmpVO.getOccrCyl(), tmpVO.getOpenRsnCd(), tmpVO.getTblNopenRsn(), tmpVO.getNopenDtlRelBss(), tmpVO.getOpenDataLst()
										, tmpVO.getColPnm(), tmpVO.getColLnm(), tmpVO.getColDescn(), tmpVO.getDataType(), tmpVO.getDataLen()
										, tmpVO.getDataFormat(), tmpVO.getNonulYn(), tmpVO.getPkYn(), tmpVO.getFkYn(), tmpVO.getConstCnd()
										, tmpVO.getOpenYn(), tmpVO.getColNopenRsn(), tmpVO.getPrsnInfoYn(), tmpVO.getEncYn()
									};
					
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
			header.setLeft("범정부수동업로드템플릿");
			
			sheet.setFitToPage(true);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);
		}
		
		//파일 서버 전송
		public void sendExcel(String fileName, HttpServletResponse response, WaaGovServerInfo gInfo)
				throws Exception {
			logger.debug("Poi SendExcel started");
			
			Session session = null;
			Channel channel = null;
			ChannelSftp channelSftp = null;
			InputStream inputStream = null;
			FileOutputStream fileOut = null;
			//현재시간구하기
//			DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("yyMMdd_");
//			DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HHmmss");
//			String exlFileName = fileName + "_" + LocalDate.now().format(formatterD).toString() + LocalTime.now().format(formatterT).toString();
			logger.debug("filename : "+fileName);
			
			try {
				inputStream= PoiHandler.class.getClassLoader().getResourceAsStream("properties/configure.properties");

				Properties properties = new Properties();
				properties.load(inputStream);

				// /////////////SFTP 접속 설정
				String host = "";
				int port = 0;
				String username = "";
				String password = "";
				String sftpPath = "";
				
				if(gInfo == null) {
					host = properties.getProperty("sftp.host");
					port = Integer.parseInt(properties.getProperty("sftp.port"));
					username = properties.getProperty("sftp.username");
					password = properties.getProperty("sftp.password");
					sftpPath = properties.getProperty("stfp.path");
				} else {
					host = gInfo.getSvrIp();
					port = Integer.parseInt(gInfo.getPort());
					username = gInfo.getConnId();
					password = gInfo.getConnPwd();
					sftpPath = gInfo.getFtpPath();
				}
				
				String errMsg = "";
				
				logger.debug("SFTP PATH : "+ sftpPath);

				// /////////////SFTP 접속 설정 끝
				
				// /////////////파일 생성 및 출력
//				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//				response.setContentType("text/html;charset=UTF-8");
//				response.setHeader("Content-Disposition", "attachment; Filename="
//						+ URLEncoder.encode(fileName, "UTF-8") + type);
//				response.setHeader("Content-Disposition", "inline");
				//파일 임시 경로설정
				String tempPath = properties.getProperty("local.upload.dir");
				fileOut = new FileOutputStream(tempPath+"/"+fileName+type);
				workbook.write(fileOut);
				
//				OutputStream fileOut2 = response.getOutputStream();
//				
//				fileOut2.close();
//				response.getOutputStream().flush();
				
				StringBuffer sb = new StringBuffer();
				sb.append("<script type='text/javascript'>");
				sb.append("window.close();");
				sb.append("</script>");
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(sb);
				out.flush();
				
				// /////////////파일 생성 및 출력 끝
				
				// /////////////SFTP 파일 전송

				JSch jsch = new JSch();
				session = jsch.getSession(username, host, port);
				
				session.setPassword(password);
				
				Properties conf = new Properties();
				
				conf.put("StrictHostKeyChecking",  "no");
				session.setConfig(conf);
				
				session.connect();
				
				channel = session.openChannel("sftp");
				
				channelSftp = (ChannelSftp)channel;
				
				channelSftp.connect();
				
				logger.debug("local dir >>> " + tempPath+"/"+fileName+type);
				logger.debug("server dir >>> " + sftpPath+"/"+fileName+type);
				if(session != null){
					try {
						logger.debug("session is not null");
						channelSftp.put(tempPath+"/"+fileName+type, sftpPath+"/"+fileName+type);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				 
				int exitStatus = channel.getExitStatus();
				System.out.println("Exit Status : " + exitStatus);
				
				if(exitStatus > 0) {
					Exception e = new Exception(errMsg);
					throw e;
				}
				//로컬에 저장된 파일을 삭제
				//File f = new File(tempPath+exlFileName+type);
				//boolean fileDel = f.delete();
				
				//logger.debug("Temp File Delete:"+ fileDel);
				//로컬에 저장된 파일을 삭제 끝
				
			} catch(Exception e) {
				
				e.printStackTrace();
				throw e;
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
				if (fileOut != null) {
					fileOut.close();
//					response.getOutputStream().close();
				}
				if(channel != null) 
					channel.disconnect();
				if(session != null)
					session.disconnect();
				
			}
			// /////////////SFTP 파일 전송 끝
		}
		
	// 출력함수
	public void downExcel(String fileName, HttpServletResponse response)
			throws Exception {
		logger.debug("Poi DownExcel started");

		// 출력
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "attachment; Filename="
				+ URLEncoder.encode(fileName, "UTF-8") + type);
		OutputStream fileOut = response.getOutputStream();
		workbook.write(fileOut);

		fileOut.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	// 기타 필요한 함수
	// 기간출력함수
	public String findStartEndDate(List<WamPrfMstrCommonVO> dataList) {
		int max = 00000000;
		int min = 99999999;

		for (int i = 0; i < dataList.size(); i++) {
			String temp = dataList.get(i).getAnaStrDtm(); // 날짜시간
			if (temp != null && !temp.equals("")) {
				int tempDate = Integer.parseInt(temp.substring(0, 10).replace(
						"-", "")); // YYYYMMDD
				if (tempDate >= max)
					max = tempDate;
				if (tempDate <= min)
					min = tempDate;
			}
		}
		String minStr = min + "";
		String maxStr = max + "";

		if (max == 0) // 아무것도 안거친 경우
			return "";
		return minStr.substring(0, 4) + "/" + minStr.substring(4, 6) + "/"
				+ minStr.substring(6, 8) + " ~ " + maxStr.substring(0, 4) + "/"
				+ maxStr.substring(4, 6) + "/" + maxStr.substring(6, 8);
	}

	// 오늘 날짜 출력함수
	public String getTodayTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		return format.format(time);
	}

	// 날짜출력함수
	public String getDateFormat(String data) {
		if (data.equals(""))
			return "";
		return data.substring(0, data.length() - 2);
	}

	// 숫자3자리마다 , 생성함수
	public String makeComma(String str) {
		if (str == null || str.equals("") || str.equals("null")
				|| str.length() == 0)
			return "";

		String type = str.getClass().getName();
		DecimalFormat format = null;

		if (type.indexOf("String") == -1) {
			format = new DecimalFormat("###,###");
			return format.format(Long.parseLong(str));
		} else {
			return str;
		}
	}

	// sum함수
	public String sumOf(List<WamPrfMstrCommonVO> dataList, String key) {

		BigInteger sum = BigInteger.ZERO;
		int countNull = 0;
		for (int i = 0; i < dataList.size(); i++) {
			if (key.equals("anaCnt")) { // 분석총건수총합
				String tempValue = dataList.get(i).getAnaCnt();
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else {
					long temp = Long.parseLong(tempValue);
					sum = sum.add(BigInteger.valueOf(temp));
				}

			} else if (key.equals("esnErCnt")) { // 추정오류건수총합
				String tempValue = dataList.get(i).getEsnErCnt();
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else {
					long temp = Long.parseLong(tempValue);
					sum = sum.add(BigInteger.valueOf(temp));
				}
			} else if (key.equals("nullCnt")) { // 널건수 총합
				String tempValue = dataList.get(i).getNullCnt() + "";
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else {
					long temp = Long.parseLong(tempValue);
					sum = sum.add(BigInteger.valueOf(temp));
				}
			} else if (key.equals("spaceCnt")) { // 공백 건수 총합
				String tempValue = dataList.get(i).getSpaceCnt() + "";
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else {
					long temp = Long.parseLong(tempValue);
					sum = sum.add(BigInteger.valueOf(temp));
				}
			}

		}

		if (countNull == dataList.size())
			return "";

		return sum + "";
	}

	// avg함수
	public String avgOf(List<WamPrfMstrCommonVO> dataList, String key) {
		double sum = 0;
		int countNull = 0;
		for (int i = 0; i < dataList.size(); i++) {
			if (key.equals("colErrRate")) { // 추정오류율평균
				String tempValue = dataList.get(i).getColErrRate();
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			} else if (key.equals("sigma")) { // 시그마평균
				String tempValue = dataList.get(i).getSigma();
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			} else if (key.equals("minLen")) { // 최소길이평균
				String tempValue = dataList.get(i).getMinLen() + "";
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			} else if (key.equals("maxLen")) { // 최대길이평균
				String tempValue = dataList.get(i).getMaxLen() + "";
				if (tempValue == null || tempValue.equals("")
						|| tempValue.equals("null") || tempValue.length() == 0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}
		}
		if (countNull == dataList.size())
			return "";
		if (sum == 0)
			return "0";

		return String.format("%.2f", sum / (dataList.size() - countNull));
	}

	// 소수점 문자열 검사함수
	public String checkPointNum(String value) {
		String temp = value;
		if (value == null || value.length() == 0)
			return "";
		else if (value.charAt(0) == '.' && value.length() != 0)
			temp = "0" + value;
		return temp;
	}
}
