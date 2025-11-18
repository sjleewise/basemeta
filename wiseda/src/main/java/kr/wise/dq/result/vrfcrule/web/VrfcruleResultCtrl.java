package kr.wise.dq.result.vrfcrule.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.handler.VrfcrulePoiHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilExcel;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;
import kr.wise.dq.report.service.DataPatternVO;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.dq.result.vrfcrule.service.VrfcruleResultService;
import kr.wise.dq.result.vrfcrule.service.VrfcruleResultVO;
import kr.wise.dq.result.vrfcrule.service.impl.VrfcruleResultServiceImpl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ValAnaResultCtrl.java
 * 3. Package  : kr.wise.dq.report.sdq.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2021. 11. 01
 * </PRE>
 */ 
@Controller
public class VrfcruleResultCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private MessageSource message;

	@Inject	
	private AnaTrgService anaTrgService;
	
	@Inject
	private VrfcruleResultService vrfcruleResultService;
	
	@Inject
	private ResultService resultService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 검증룰 종합현황 폼 */
	@RequestMapping("/dq/vrfcrule/vrfcrule_result_lst.do")
	public String formPage() throws Exception {
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {

    	}
		return "/dq/vrfcrule/vrfcrule_result_lst";
	}
	
	
	/** 검증룰 종합현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/vrfcrule/vrfcrule_resultList.do")
	@ResponseBody
	public IBSheetListVO<ResultVO> selectList(@ModelAttribute ResultVO search) {
		logger.debug("{}", search);
		List<ResultVO> list = vrfcruleResultService.getVrfcruleResultList(search);
		
		return new IBSheetListVO<ResultVO>(list, list.size());
	}
		
	@RequestMapping("/dq/vrfcrule/poiDown.do")
	@ResponseBody
	public void prtXlsRptStwd(@RequestParam String sysId, @RequestParam String dbmsId, @RequestParam String schId, VrfcruleResultVO srchVo,  HttpServletRequest request, HttpServletResponse response ) throws IOException, URISyntaxException { 
		logger.debug("sysAreaId="+sysId+", dbmsId ="+dbmsId+" , schId=" +schId);
		srchVo.setSysAreaId(sysId);
		srchVo.setDbSchId(schId);
		srchVo.setDbConnTrgId(dbmsId);
		
		String filePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ WiseConfig.EXCEL_RPT_PATH, null, Locale.getDefault());
		File srcFile = new File(filePath+"/VRFC_RULE_REPORT_RESULT_VALUE_sample.xlsx"); 
        FileInputStream is = new FileInputStream(srcFile);
        System.out.println("파일경로 path is : "+srcFile );
        
        //엑셀 처리 
        XSSFWorkbook workbook = createXlsx(srcFile, srchVo); 
                
        //==========엑셀 다운======================
 		String browser = UtilExcel.getBrowser(request); 
 		String fileName = "WISEDQ 값진단결과보고서.xlsx";
 		String encodedFilename = UtilExcel.getencodingfilename(browser, fileName);
 		
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(encodedFilename.getBytes("UTF-8"), "8859_1") + "\"");	
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        workbook.write(out);
        out.close(); 
        //=======================================
	}

	private XSSFWorkbook createXlsx(File srcFile, VrfcruleResultVO search) throws IOException{
		logger.debug("search "+search);
		WaaDbConnTrgVO search2 = new WaaDbConnTrgVO();
		search2.setSysAreaId(search.getSysAreaId());
		search2.setDbSchId(search.getDbSchId());
		search2.setDbConnTrgId(search.getDbConnTrgId());
		logger.debug("dbmsId "+search2.getDbConnTrgId()+" schId" +search2.getDbSchId());
		
		LocalDateTime now = LocalDateTime.now();
		String curDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String stndAsrt = search.getStndAsrt();
		
		FileInputStream is = new FileInputStream(srcFile);
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        
        //진단대상 DB정보
        WaaDbConnTrgVO dbConnTrg = anaTrgService.getByDbms(search2);
        
        Sheet tblSht0 = workbook.getSheet("(진단결과)값진단결과");
        Sheet tblSht1 = workbook.getSheet("(테이블선정)진단대상테이블");
        Sheet tblSht2 = workbook.getSheet("(룰설정)도메인");
        Sheet tblSht3 = workbook.getSheet("(룰설정)참조무결성");
//        Sheet tblSht4 = workbook.getSheet("(룰설정)필수값완전성");
//        Sheet tblSht5 = workbook.getSheet("(룰설정)데이터중복");
        Sheet tblSht6 = workbook.getSheet("(룰설정)업무규칙");
        Sheet tblSht7 = workbook.getSheet("(진단실행)진단항목실행정보");
        Sheet tblSht8 = workbook.getSheet("(진단실행)진단항목오류정보");
        
      	CellStyle style = VrfcrulePoiHandler.setBsCellStyle(workbook, "L");
        Row row = null;
        Cell cell = null;

        //출력일
        tblSht0.getRow(1).getCell(5).setCellValue("출력일 : "+curDate);
        //진단기간
//        tblSht0.getRow(3).getCell(2).setCellValue(search.getSearchBgnDe()+" ~ "+search.getSearchEndDe());
        //기관명  
//        tblSht0.getRow(3).getCell(2).setCellValue("행정안전부");
        tblSht0.getRow(3).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"orgNm"));
        //시스템
        tblSht0.getRow(4).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"infoSysNm"));
        //DB명, DB서비스명
        tblSht0.getRow(5).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbConnTrgLnm"));
        tblSht0.getRow(5).getCell(5).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbServiceName"));
        //DB종류, 버전
        tblSht0.getRow(6).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbmsType"));
        tblSht0.getRow(6).getCell(5).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbmsVer"));
        //DB아이피, DB포트
        tblSht0.getRow(7).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbIp"));
        tblSht0.getRow(7).getCell(5).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"port"));
    	
        //진단결과
        //1.진단대상 테이블 현황
        VrfcruleResultVO result1 = vrfcruleResultService.getTargetTableStatus(search);
        tblSht0.getRow(10).getCell(2).setCellValue(result1.getTrgTblCnt());
        tblSht0.getRow(10).getCell(5).setCellValue(result1.getTrgTblRt());
        tblSht0.getRow(11).getCell(2).setCellValue(result1.getTrgExcCnt());
        tblSht0.getRow(11).getCell(5).setCellValue(result1.getTrgExcRt());
        tblSht0.getRow(12).getCell(2).setCellValue(result1.getNtCllcCnt());
        tblSht0.getRow(12).getCell(5).setCellValue(result1.getNtCllcRt());
		
        //2.진단실행상태
        VrfcruleResultVO result2 = vrfcruleResultService.getAnaExecuteStatus(search);
        tblSht0.getRow(15).getCell(2).setCellValue(result2.getAnaCmlCnt());
        tblSht0.getRow(15).getCell(5).setCellValue(result2.getAnaCmlRt());
        tblSht0.getRow(16).getCell(2).setCellValue(result2.getAnaFalCnt());
        tblSht0.getRow(16).getCell(5).setCellValue(result2.getAnaFalRt());
        tblSht0.getRow(17).getCell(2).setCellValue(result2.getAnaRnnCnt());
        tblSht0.getRow(17).getCell(5).setCellValue(result2.getAnaRnnRt());
        
        //3.값진단결과
        int strRow0 = 21;
        long sumAnaCnt = 0;
        long sumErCnt = 0;
        
        CellStyle styleTh = VrfcrulePoiHandler.setValAnaResultThCellStyle(workbook, "C", true);
        CellStyle styleTdR = VrfcrulePoiHandler.setValAnaResultTdCellStyle(workbook, "R", true);
        CellStyle styleTdC = VrfcrulePoiHandler.setValAnaResultTdCellStyle(workbook, "C", true);
        DecimalFormat df = new DecimalFormat("#,###,###,###,###,###,###");
        
        List<VrfcruleResultVO> result3 = vrfcruleResultService.getValAnaResultList(search);
        for(VrfcruleResultVO srchVo : result3) {
        	
        	int iCell = 1;
        	row = tblSht0.createRow(strRow0);
        	row.setHeight( (short) 600);
        	
        	// 진단항목
        	row.createCell(iCell).setCellStyle(styleTh);
        	row.getCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndCd()));
        	// 진단대상구분
        	row.createCell(iCell).setCellStyle(styleTdR);
        	row.getCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAnaTrgCol() + "\n" + srchVo.getAnaErrCol()));
        	// 진단건수
        	long anaCnt = Long.parseLong(srchVo.getAnaCnt() != null ? srchVo.getAnaCnt() : "0");
        	sumAnaCnt += anaCnt;
        	row.createCell(iCell).setCellStyle(styleTdR);
        	row.getCell(iCell++).setCellValue(UtilString.null2Blank(df.format(anaCnt)));
        	// 오류건수
        	long erCnt = Long.parseLong(srchVo.getErCnt() != null ? srchVo.getErCnt() : "0");
        	sumErCnt += erCnt;
        	row.createCell(iCell).setCellStyle(styleTdR);
        	row.getCell(iCell++).setCellValue(UtilString.null2Blank(df.format(erCnt)));
        	// 오류율
        	row.createCell(iCell).setCellStyle(styleTdR);
        	row.getCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getErrRt()));
        	// 최근진단일자
        	row.createCell(iCell).setCellStyle(styleTdC);
        	row.getCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getStrAnaStrDtm()));
        	
        	strRow0++;
        }
        
        int jCell = 1;
        row = tblSht0.createRow(strRow0);
        row.setHeight( (short) 600);
        row.createCell(jCell).setCellStyle(styleTh);
		row.getCell(jCell++).setCellValue("");
		row.createCell(jCell).setCellStyle(styleTh);
		row.getCell(jCell++).setCellValue("");
        row.createCell(jCell).setCellStyle(styleTh);
		row.getCell(jCell++).setCellValue(UtilString.null2Blank(df.format(sumAnaCnt)));
        row.createCell(jCell).setCellStyle(styleTh);
		row.getCell(jCell++).setCellValue(UtilString.null2Blank(df.format(sumErCnt)));
        row.createCell(jCell).setCellStyle(styleTh);
        
        double totalRate = 0;
        if(sumAnaCnt != 0 && sumErCnt != 0) {
        	totalRate = (double) sumErCnt / (double) sumAnaCnt;
        }
		row.getCell(jCell++).setCellValue(UtilString.null2Blank(new DecimalFormat("##0.0000%").format(totalRate)));
		row.createCell(jCell).setCellStyle(styleTh);
		row.getCell(jCell++).setCellValue("");
        
        //1.(테이블선정)진단대상테이블
        int strRow1 = 1;
        List<VrfcruleResultVO> list1 = vrfcruleResultService.getTargetTableList(search);
        
        for(VrfcruleResultVO srchVo : list1) {

        	int iCell = 0;
        	row = tblSht1.createRow(strRow1);
        	// 테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
        	// 상태
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAnaTrgStatus()));
        	// 수집일자 
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getStrAnaDtm()));
        	// 범위조건
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAnaTrgRangeCon()));
        	//의견
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDescn()));
        	
        	strRow1++;
        }

        
    	//2.(룰설정)도메인
        int strRow2 = 1;
    	List<VrfcruleResultVO> list2 = vrfcruleResultService.getDmnList(search);
        
        for(VrfcruleResultVO srchVo : list2) {

        	int iCell = 0;
        	row = tblSht2.createRow(strRow2);
        	
        	// 테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
        	// 컬럼
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColNm()));
        	// 데이터타입
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDataType()));
        	// 검증룰명
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndNm()));
        	// 도메인
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndCd()));
        	// 검증형식
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getVrfFrm()));
        	// 오류제외데이터
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getErrExcData()));
        	// 검증룰 생성 및 변경
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getStrAprvDtm()));
        	// 수집데이터
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDataPtr()));
        	// 의견
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColOpn()));
        	
        	strRow2++;
        }
        
        //3.(룰설정)참조무결성
        int strRow3 = 1;
    	List<VrfcruleResultVO> list3 = vrfcruleResultService.getRefIgList(search);
        
        for(VrfcruleResultVO srchVo : list3) {
        	
        	int iCell = 0;
        	row = tblSht3.createRow(strRow3);
        	
        	// 진단테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getChTblDbcTblNm()));
        	// 진단컬럼명
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getChTblDbcColNm()));
        	// 조인순서
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getRelColSno()));
        	// 참조컬럼
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getPaTblDbcColNm()));
        	// 참조테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getPaTblDbcTblNm()));
        	// 진단테이블 추가조건SQL
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getPaTblAdtCndSql()));
        	
        	strRow3++;
        }

//    	//4.(룰설정)필수값완전성
//        int strRow4 = 1;
//    	List<VrfcruleResultVO> list4 = vrfcruleResultService.getReqValList(search);
//        
//        for(VrfcruleResultVO srchVo : list4) {
//        	
//        	int iCell = 0;
//        	row = tblSht4.createRow(strRow4);
//        	
//        	// 진단대상여부
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAonlYn()));
//        	// 테이블
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
//        	// 컬럼
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColNm()));
//        	// 한글컬럼명
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColKorNm()));
//        	// 데이터타입
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDataType()));
//        	// 데이터길이
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDataLen()));
//        	
//        	strRow4++;
//        }

    	//5.(룰설정)데이터중복
//        int strRow5 = 1;
//        List<VrfcruleResultVO> list5 = vrfcruleResultService.getDataRdndList(search);
//        
//        for(VrfcruleResultVO srchVo : list5) {
//        	
//        	int iCell = 0;
//        	row = tblSht5.createRow(strRow5);
//        	
//        	//테이블
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
//        	// 컬럼
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColNm()));
//        	// 한글컬럼명
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColKorNm()));
//        	// 물리PK순서
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getColSeqPnm()));
//        	// 가상PK순서
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getColSeqLnm()));
//        	
//        	strRow5++;
//        }

    	//6.(룰설정)업무규칙
        int strRow6 = 1;
        List<VrfcruleResultVO> list6 = vrfcruleResultService.getBrList(search);
        
        for(VrfcruleResultVO srchVo : list6) {
        	
        	int iCell = 0;
        	row = tblSht6.createRow(strRow6);
        	
        	// 분석영역
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getBizAreaLnm()));
        	// 업무규칙ID
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getBrId()));
        	// 업무규칙명
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getBrNm()));
        	// 설명
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getObjDescn()));
        	// 테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
        	// 대상 전체건수 SQL
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getCntSql()));
        	// 오류 데이터 추출 SQL
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAnaSql()));
        	// 오류데이터별 오류건수 SQL
//        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getErCntSql()));
        	
        	strRow6++;
        }
        
        //7.(진단실행)진단항목실행정보
        int strRow7 = 1;
        List<VrfcruleResultVO> list7 = vrfcruleResultService.getExcInfoList(search);
        
        for(VrfcruleResultVO srchVo : list7) {
        	
        	int iCell = 0;
        	row = tblSht7.createRow(strRow7);
        	// 테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
        	// 컬럼
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColNm()));
        	// 검증룰
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndCd()));
        	// 룰명
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndNm()));
        	// 실행상태
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAnaStsTxt()));
        	// 실행일시
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getStrAnaStrDtm()));
        	
        	strRow7++;
        }
        
        //8.(진단실행)진단항목오류정보
        int strRow8 = 1;
        List<VrfcruleResultVO> list8 = vrfcruleResultService.getExcErrInfoList(search);
        
        for(VrfcruleResultVO srchVo : list8) {
        	
        	int iCell = 0;
        	row = tblSht8.createRow(strRow8);
        	
        	// 테이블
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcTblNm()));
        	// 컬럼
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColNm()));
        	// 도메인
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndCd()));
        	// 룰명
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getKndNm()));
        	// 전체건수
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getAnaCnt()));
        	// 오류건수
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getEsnErCnt()));
        	// 오류율
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getColErrRate()));
        	// 오류데이터 추출 SQL
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getErrSql()));
        	// 오류데이터
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getErrData()));
        	
        	
        	strRow8++;
        }
        
        //============컬럼 sheet 세팅 ==================
//        createXlsxForBrDetail(workbook, list, search);
        		
		return workbook;
	}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
//		codeMap.put("regTypCdibs", regTypCd);
//		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
//		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
		
		//프로파일 종류코드
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
		
		//진단대상
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsCd", connTrgDbms);		
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		
		//스키마
		List<CodeListVo> schDbSchNm = codeListService.getCodeList(CodeListAction.connTrgSch);
		
		codeMap.put("schDbSchNm", schDbSchNm);
		codeMap.put("schDbSchNmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(schDbSchNm)));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
//		//진단대상/스키마 정보(double_select용 목록성코드)
//		String connTrgSchByOnwer   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchIdByOwner"));
//		codeMap.put("connTrgSchByOnwer", connTrgSchByOnwer);
//		
		List<CodeListVo> sysarea = codeListService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		
		return codeMap;
	}
	
}
