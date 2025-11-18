package kr.wise.dq.dqrs.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilExcel;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dqrs.handler.DqrsPoiHandler;
import kr.wise.dq.dqrs.handler.VrfcrulePoiHandler;
import kr.wise.dq.dqrs.service.DqrsDbConnTrgVO;
import kr.wise.dq.dqrs.service.DqrsPoiGapResult;
import kr.wise.dq.dqrs.service.DqrsPoiResult;
import kr.wise.dq.dqrs.service.DqrsResult;
import kr.wise.dq.dqrs.service.DqrsResultService;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DqrsResultCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private DqrsResultService dqrsResultService;
	
	static class DqrsDbConnTrgVOs extends HashMap<String, ArrayList<DqrsDbConnTrgVO>> { }

	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		String dbmsVersCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD"));
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);
		
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		codeMap.put("dbmsVersCdibs", dbmsVersCd);

		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("dbmsTypCd",cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsVersCd",cmcdCodeService.getCodeList("DBMS_VERS_CD"));

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);
				
		return codeMap;
	}
	
	/////////////품질진단 분석
	@RequestMapping("/dq/dqrs/dqrs_result.do")
	public String goDqrsResultPage() throws Exception {
		return "/dq/dqrs/dqrs_result";
	}
	
	@RequestMapping("/dq/dqrs/dqrsResult.do")
	@ResponseBody
	public IBSheetListVO<DqrsResult> getDqrsResult(DqrsResult search) {
		logger.debug("{}", search);
		
		List<DqrsResult> list = dqrsResultService.getDqrsResult(search);
		
		return new IBSheetListVO<DqrsResult>(list, list.size());
	}
	
	@RequestMapping("/dq/dqrs/poiDown.do")
	@ResponseBody
	public void prtXlsRptStwd(@RequestParam String dbmsId, @RequestParam String schId, DqrsPoiResult srchVo,  HttpServletRequest request, HttpServletResponse response, @RequestParam String maskingYn ) throws IOException, URISyntaxException { 
		logger.debug("dbmsId ="+dbmsId+" , schId=" +schId);
		srchVo.setDbSchId(schId);
		srchVo.setDbConnTrgId(dbmsId);
		srchVo.setMaskingYn(maskingYn);
		//데이터가 없는 경우 엑셀 다운로드 불가 하도록 처리
		DqrsPoiResult result = dqrsResultService.getAnaExecuteStatus(srchVo);
		if(result == null) {
			response.setContentType("text/html; charset=UTF-8");
			
    		PrintWriter out = response.getWriter();
    		 
    		out.println("<script> opener.showMsgBox(\"ERR\", \"내려받을 데이터가 없습니다.\");</script>");
    		out.println("<script> window.close(); </script>");
    		 
    		out.flush();
    		return;
		}
		
		String filePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ WiseConfig.EXCEL_RPT_PATH, null, Locale.getDefault());
		File srcFile = new File(filePath+"/VRFC_RULE_REPORT_RESULT_VALUE_sample.xlsx"); 
		
        FileInputStream is = new FileInputStream(srcFile);
//        System.out.println("파일경로 path is : "+srcFile );
        
        //엑셀 처리 
        XSSFWorkbook workbook = createXlsx(srcFile, srchVo); 
                
        //==========엑셀 다운======================
 		String browser = UtilExcel.getBrowser(request); 
 		String fileName = "WISEDQ_값진단결과보고서.xlsx";
 		String encodedFilename = UtilExcel.getencodingfilename(browser, fileName);
 		
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(encodedFilename.getBytes("UTF-8"), "8859_1") + "\"");	
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        workbook.write(out);
        out.close(); 
        //=======================================
	}

	private XSSFWorkbook createXlsx(File srcFile, DqrsPoiResult search) throws IOException{
		DqrsDbConnTrgVO search2 = new DqrsDbConnTrgVO();
		search2.setSysAreaId(search.getSysAreaId());
		search2.setDbSchId(search.getDbSchId());
		search2.setDbConnTrgId(search.getDbConnTrgId());
		
		LocalDateTime now = LocalDateTime.now();
		String curDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//		String stndAsrt = search.getStndAsrt();
		
		FileInputStream is = new FileInputStream(srcFile);
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        
        //진단대상 DB정보
        DqrsDbConnTrgVO dbConnTrg = dqrsResultService.getByDbms(search2);
        
        XSSFSheet tblSht0 = workbook.getSheet("(진단결과)값진단결과");
        XSSFSheet tblSht1 = workbook.getSheet("(테이블선정)진단대상테이블");
        XSSFSheet tblSht2 = workbook.getSheet("(룰설정)도메인");
        XSSFSheet tblSht3 = workbook.getSheet("(룰설정)참조무결성");
//        Sheet tblSht4 = workbook.getSheet("(룰설정)필수값완전성");
//        Sheet tblSht5 = workbook.getSheet("(룰설정)데이터중복");
        XSSFSheet tblSht6 = workbook.getSheet("(룰설정)업무규칙");
        XSSFSheet tblSht7 = workbook.getSheet("(진단실행)진단항목실행정보");
        XSSFSheet tblSht8 = workbook.getSheet("(진단실행)진단항목오류정보");
        
//      	CellStyle style = VrfcrulePoiHandler.setBsCellStyle(workbook, "L");
        Row row = null;
//        Cell cell = null;

        //출력일
        tblSht0.getRow(1).getCell(5).setCellValue("출력일 : "+curDate);
        //진단기간
//        tblSht0.getRow(3).getCell(2).setCellValue(search.getSearchBgnDe()+" ~ "+search.getSearchEndDe());
        //기관명  
        tblSht0.getRow(3).getCell(2).setCellValue(message.getMessage(message.getMessage("mode", null, Locale.getDefault())+".ORG_NM", null, Locale.getDefault()));
        //시스템
        tblSht0.getRow(4).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"infoSysNm"));
        //DB명, DB서비스명
        tblSht0.getRow(5).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbConnTrgLnm"));
        tblSht0.getRow(5).getCell(5).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbServiceName"));
        //DB종류, 버전
        tblSht0.getRow(6).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbmsType"));
        tblSht0.getRow(6).getCell(5).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbmsVer"));
        //DB아이피, DB포트
        if(search.getMaskingYn().equals("N")) {
        	tblSht0.getRow(7).getCell(2).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"dbIp"));	
        }else{
        	String ip = VrfcrulePoiHandler.getData(dbConnTrg,"dbIp");
        	tblSht0.getRow(7).getCell(2).setCellValue(ipMasking(ip));
        }
        
        tblSht0.getRow(7).getCell(5).setCellValue(VrfcrulePoiHandler.getData(dbConnTrg,"port"));
    	
        //진단결과
        //1.진단대상 테이블 현황
        logger.debug("========진단대상 테이블 현황 시작");
        DqrsPoiResult result1 = dqrsResultService.getTargetTableStatus(search);
        tblSht0.getRow(10).getCell(2).setCellValue(result1.getTrgTblCnt());
        tblSht0.getRow(10).getCell(5).setCellValue(result1.getTrgTblRt());
        tblSht0.getRow(11).getCell(2).setCellValue(result1.getTrgExcCnt());
        tblSht0.getRow(11).getCell(5).setCellValue(result1.getTrgExcRt());
        tblSht0.getRow(12).getCell(2).setCellValue(result1.getNtCllcCnt());
        tblSht0.getRow(12).getCell(5).setCellValue(result1.getNtCllcRt());
		
        //2.진단실행상태
        logger.debug("========진단실행상태 시작");
        DqrsPoiResult result2 = dqrsResultService.getAnaExecuteStatus(search);
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
        
        logger.debug("========진단결과 시작");
        List<DqrsPoiResult> result3 = dqrsResultService.getValAnaResultList(search);
        for(DqrsPoiResult srchVo : result3) {
        	
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
        logger.debug("========진단대상테이블 시작");
        List<DqrsPoiResult> list1 = dqrsResultService.getTargetTableList(search);
        
        for(DqrsPoiResult srchVo : list1) {

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
        logger.debug("========도메인규칙 시작");
    	List<DqrsPoiResult> list2 = dqrsResultService.getDmnList(search);
        
        for(DqrsPoiResult srchVo : list2) {

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
        	// 2022.06.17 수집데이터 마스킹처리 분기처리 기능 추가
        	if(search.getMaskingYn().equals("N")) {
        		row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDataPtr()));	
        	}else {
        		row.createCell(iCell++).setCellValue(dataMasking(UtilString.null2Blank(srchVo.getDataPtr())));
        	}
        	// 의견
        	row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getDbcColOpn()));
        	
        	strRow2++;
        }
        
        //3.(룰설정)참조무결성
        int strRow3 = 1;
        logger.debug("========참조무결성 시작");
    	List<DqrsPoiResult> list3 = dqrsResultService.getRefIgList(search);
        
        for(DqrsPoiResult srchVo : list3) {
        	
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
//    	List<DqrsPoiResult> list4 = dqrsResultService.getReqValList(search);
//        
//        for(DqrsPoiResult srchVo : list4) {
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
//        List<DqrsPoiResult> list5 = dqrsResultService.getDataRdndList(search);
//        
//        for(DqrsPoiResult srchVo : list5) {
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
        logger.debug("========업무규칙 시작");
        List<DqrsPoiResult> list6 = dqrsResultService.getBrList(search);
        
        for(DqrsPoiResult srchVo : list6) {
        	
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
        logger.debug("========진단항목실행정보 시작");
        List<DqrsPoiResult> list7 = dqrsResultService.getExcInfoList(search);
        
        for(DqrsPoiResult srchVo : list7) {
        	
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
        logger.debug("========진단항목오류정보 시작");
        List<DqrsPoiResult> list8 = dqrsResultService.getExcErrInfoList(search);
        
        for(DqrsPoiResult srchVo : list8) {
        	
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
        	// 2022.06.17 오류데이터 마스킹처리 분기처리 기능 추가
        	if(search.getMaskingYn().equals("N")) {
        		row.createCell(iCell++).setCellValue(UtilString.null2Blank(srchVo.getErrData()));
        	}else {
        		row.createCell(iCell++).setCellValue(dataMasking(UtilString.null2Blank(srchVo.getErrData())));
        	}
        	
        	strRow8++;
        }
        
        //============컬럼 sheet 세팅 ==================
//        createXlsxForBrDetail(workbook, list, search);
        
      //============sheet lock; XSSFSheet에서 지원하는 기능 ==================
        tblSht0.enableLocking();
        tblSht1.enableLocking();
        tblSht2.enableLocking();
        tblSht3.enableLocking();
		//tblSht4.enableLocking();
		//tblSht5.enableLocking();
        tblSht6.enableLocking();
        tblSht7.enableLocking();
        tblSht8.enableLocking();
        		
		return workbook;
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
	
	//2022.06.17 품질평가용 자료 내려받기 data 마스킹 처리 기능 추가
	public static String dataMasking(String str) {
		
		String[] arrayData   = str.split(",");
		String maskingData   = "";
		String replaceString = "";
		if(!str.isEmpty()){
			if(arrayData.length > 1 ) {
				for(int i =0; i < arrayData.length;i++) {
					maskingData = arrayData[i];
					
					if(maskingData.length() == 1) {
						replaceString = replaceString + "*";
					}else if(maskingData.length() == 2) {
						replaceString = replaceString + maskingData.replaceAll("(?<=.{1}).", "*");
					}else if(maskingData.length() == 3) {
						replaceString = replaceString + maskingData.replaceAll("(?<=.{2}).", "*");
					}else {
						replaceString = replaceString + maskingData.replaceAll("(?<=.{3}).", "*");
					}
					
					if(i+1 < arrayData.length) {
						replaceString = replaceString + "," ;
					}
				}
			}else {
				if(str.length() == 1) {
					replaceString = "*";
				}else if(str.length() == 2) {
					replaceString = str.replaceAll("(?<=.{1}).", "*");
				}else if(str.length() == 3) {
					replaceString = str.replaceAll("(?<=.{2}).", "*");
				}else {
					replaceString = str.replaceAll("(?<=.{3}).", "*");
				}
			}
		}
			
		return replaceString;
	}

	//////////// 표준/구조품질 레포팅
	@RequestMapping("/dq/dqrs/gapPoiDown.do")
    public void gapPoiDown (HttpServletResponse response, @RequestParam String dbmsId, @RequestParam String schId
	           , @RequestParam String dbmsLnm, @RequestParam String schLnm, @RequestParam String poiFlag
	           , @RequestParam String gap, @RequestParam String tot
	           , @RequestParam String tblCnt, @RequestParam String tblNcnt
	           , @RequestParam String colCnt, @RequestParam String colNcnt
	           , @RequestParam String pdmColCnt, @RequestParam String pdmColNcnt
	           , @RequestParam String pdmTblCnt, @RequestParam String pdmTblNcnt
	           , @RequestParam(value="stndSort", required=false) String stndSort
	           , @RequestParam(value="asrt", required=false) String asrt
	            , @RequestParam String maskingYn
	            , @RequestParam(value="sditmDbConnTrgId", required=false) String sditmDbConnTrgId) throws Exception{
		
		logger.debug("gapPoiDown:{}",dbmsId);
		logger.debug("gapPoiDown:{}",schId);
		logger.debug("tot:{}", tot);
		logger.debug("gap:{}", gap);
		
    	switch(poiFlag) {
	    	case "stnd":
	    		stndDown(response, dbmsId, schId, dbmsLnm, schLnm, gap, tot, stndSort, asrt, maskingYn, sditmDbConnTrgId);
	    		break;
	    	case "model":
	    		modelDown(response, dbmsId, schId, dbmsLnm, schLnm, poiFlag, maskingYn);
	    		break;
	    	case "govModel":
	    		govModelDown(response, dbmsId, schId, dbmsLnm, schLnm, poiFlag, maskingYn, sditmDbConnTrgId);
	    		break;
    	}
	}
	
	private void stndDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String gap, String tot, String stndSort, String stndAsrt , String maskingYn, String sditmDbConnTrgId) throws Exception {
		//list를 추출하기 위한 vo
		
		logger.debug("stndSrot:{}", stndSort);
		logger.debug("stndAsrt:{}", stndAsrt);
		
		DqrsPoiGapResult search = new DqrsPoiGapResult();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);
		search.setSditmDbConnTrgId(sditmDbConnTrgId);
		
		String url = dqrsResultService.getDbConnTrgURL(search);
		
		logger.debug("url:{}", url);
	
		String excelType = "2"; //.xlsx
		DqrsPoiHandler poiHandler = new DqrsPoiHandler(excelType);
		
		DqrsPoiGapResult term = null;
		DqrsPoiGapResult cnt = null;
		List<DqrsPoiGapResult> sditmTblList = null;
		List<DqrsPoiGapResult> sditmList = null;
		List<DqrsPoiGapResult> dmnList = null;
		
		search.setStndType(stndSort);
		search.setStndAsrt(stndAsrt);
		
		logger.debug("search:{}", search.toString());

		cnt = dqrsResultService.getGovCnt(search);
		term = dqrsResultService.getStndTerm(search);
		sditmTblList = dqrsResultService.getGovSditmTblList(search);
		sditmList = dqrsResultService.getGovSditmList(search);
		dmnList = dqrsResultService.getGovDmnList(search);
		
		logger.debug("cnt:{}", cnt.getTotCnt());
		logger.debug("cnt:{}", cnt.getErrCnt());
		
		term.setTotCnt(cnt.getTotCnt());
		term.setErrCnt(cnt.getErrCnt());
		term.setMaskingYn(maskingYn);
		if(dbmsId == null || dbmsId.equals("")) {
			term.setDbConnTrgLnm("전체");
			term.setDbSchLnm("전체");
		} else {
			term.setDbConnTrgLnm(dbmsLnm);
			term.setDbSchLnm(schLnm);
		}
		
		poiHandler.createMetaExcelStnd(term, sditmTblList, sditmList, dmnList, stndSort, url);
		poiHandler.downExcel("WISEDQ 표준진단종합현황", response);
	}
	
	private void modelDown(HttpServletResponse response, String dbmsId, String schId, String dbmsLnm, String schLnm, String poiFlag, String maskingYn) throws Exception {
		
		logger.debug("dbmsLnm:", dbmsLnm);
		logger.debug("dbmsId:", dbmsId);
		logger.debug("schId:", schId);
		logger.debug("schLnm:", schLnm);
		
		//list를 추출하기 위한 vo
		DqrsPoiGapResult search = new DqrsPoiGapResult();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);

		String excelType = "1"; //.xls
		
		DqrsPoiHandler poiHandler = new DqrsPoiHandler(excelType);
	
		DqrsPoiGapResult term = dqrsResultService.getModelTerm(search);
		String url = dqrsResultService.getDbConnTrgURL(search);
		
		logger.debug("url:{}", url);
		
		List<DqrsPoiGapResult> dataList = null; // 종합결과 조회 목록
		List<DqrsPoiGapResult> dbcTblList = null; // 물리DB기준 테이블 현행화 조회 목록
		List<DqrsPoiGapResult> dbcColList = null; // 물리DB기준 컬럼 현행화 조회 목록
		List<DqrsPoiGapResult> pdmTblList = null; // 테이블정의서 기준 테이블 현행화 조회 목록
		List<DqrsPoiGapResult> pdmColList = null; // 컬럼정의서 기준 컬럼 현행화 조회 목록
		List<DqrsPoiGapResult> tblDbList = null; // 테이블정의서
		List<DqrsPoiGapResult> colDbList = null; // 컬럼정의서
		
		dataList=dqrsResultService.getStructResultList(search); // 종합결과 조회 목록
		dbcTblList=dqrsResultService.getDbcTblList(search);
		dbcColList=dqrsResultService.getDbcColList(search);
		pdmTblList=dqrsResultService.getPdmTblList(search);
		pdmColList=dqrsResultService.getPdmColList(search);
		tblDbList=dqrsResultService.getTblDbList(search);
		colDbList=dqrsResultService.getColDbList(search);
		
		if(dbmsId == null || dbmsId.equals("")) {
			term.setDbConnTrgLnm("전체");
			term.setDbSchLnm("전체");
		} else {
			term.setDbConnTrgLnm(dbmsLnm);
			term.setDbSchLnm(schLnm);
		}
		
		logger.debug("term:", term);
		
		List<DqrsPoiGapResult> dqiErrList = null;
		
		dqiErrList = new ArrayList<DqrsPoiGapResult>();
	
		poiHandler.createStructExcelResult(term, dataList, dbcTblList, dbcColList, pdmTblList, pdmColList, tblDbList, colDbList, dqiErrList, poiFlag, url);
		poiHandler.downExcel("WISEDQ 구조진단종합현황", response);
	}	
	
	private void govModelDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String poiFlag, String maskingYn, String sditmDbConnTrgId) throws Exception {
		//list를 추출하기 위한 vo
		DqrsPoiGapResult search = new DqrsPoiGapResult();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);
		search.setSditmDbConnTrgId(sditmDbConnTrgId);

		String excelType = "2"; //.xls
		
		DqrsPoiHandler poiHandler = new DqrsPoiHandler(excelType);
	
		DqrsPoiGapResult term = dqrsResultService.getModelTerm(search);
		String url = dqrsResultService.getDbConnTrgURL(search);
		
		logger.debug("url:{}", url);
		
		List<DqrsPoiGapResult> dataList = null; // 종합결과 조회 목록
		List<DqrsPoiGapResult> dbcTblList = null; // 물리DB기준 테이블 현행화 조회 목록
		List<DqrsPoiGapResult> dbcColList = null; // 물리DB기준 컬럼 현행화 조회 목록
		List<DqrsPoiGapResult> pdmTblList = null; // 테이블정의서 기준 테이블 현행화 조회 목록
		List<DqrsPoiGapResult> pdmColList = null; // 컬럼정의서 기준 컬럼 현행화 조회 목록
		List<DqrsPoiGapResult> tblDbList = null; // 테이블정의서
		List<DqrsPoiGapResult> colDbList = null; // 컬럼정의서
		
		dataList=dqrsResultService.getGovStructResultList(search); // 종합결과 조회 목록
		dbcTblList=dqrsResultService.getGovDbcTblList(search);
		dbcColList=dqrsResultService.getGovDbcColList(search);
		pdmTblList=dqrsResultService.getGovTblList(search);
		pdmColList=dqrsResultService.getGovColList(search);
		tblDbList=dqrsResultService.getGovTblDbList(search);
		colDbList=dqrsResultService.getGovColDbList(search);
		
		if(dbmsId == null || dbmsId.equals("")) {
			term.setDbConnTrgLnm("전체");
			term.setDbSchLnm("전체");
		} else {
			term.setDbConnTrgLnm(dbmsLnm);
			term.setDbSchLnm(schLnm);
		}
		term.setMaskingYn(maskingYn);
		List<DqrsPoiGapResult> dqiErrList = null;
		
		dqiErrList = new ArrayList<DqrsPoiGapResult>();
	
		poiHandler.createStructExcelResult(term, dataList, dbcTblList, dbcColList, pdmTblList, pdmColList, tblDbList, colDbList, dqiErrList, poiFlag, url);
		poiHandler.downExcel("WISEDQ 구조진단종합현황", response);
	}
	
	@RequestMapping("/dq/dqrs/dqrs_info_sys.do")
	public String goDqrsInfoSysPage() throws Exception {
		return "/dq/dqrs/dqrs_info_sys";
	}
	
	@RequestMapping("/dq/dqrs/getInfoSys.do")
	@ResponseBody
	public IBSheetListVO<DqrsDbConnTrgVO> getInfoSys(DqrsDbConnTrgVO search) {
		logger.debug("{}", search);
		
		List<DqrsDbConnTrgVO> list = dqrsResultService.getInfoSys(search);
		
		return new IBSheetListVO<DqrsDbConnTrgVO>(list, list.size());
	}
	
	@RequestMapping("/dq/dqrs/updateInfoSys.do")
	@ResponseBody
	public IBSResultVO<DqrsDbConnTrgVO> updateInfoSys(@RequestBody DqrsDbConnTrgVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<DqrsDbConnTrgVO> list = data.get("data");
		
		int result = dqrsResultService.updateInfoSys(list);  
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<DqrsDbConnTrgVO>(result, resmsg, action);

	}
}
