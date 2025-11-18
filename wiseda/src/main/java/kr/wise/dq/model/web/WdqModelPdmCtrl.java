/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelPdmCtrl.java
 * 2. Package : kr.wise.meta.model.controller
 * 3. Comment : 물리모델 컨트롤러...
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 22. 오전 12:59:08
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 22. 		: 신규 개발.
 */
package kr.wise.dq.model.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilExcel;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.model.service.WdqModelPdmService;
import kr.wise.dq.model.service.WdqmPdmCol;
import kr.wise.dq.model.service.WdqmPdmTbl;
/**
 * <PRE>
 * 1. ClassName : ModelPdmCtrl
 * 2. Package  : kr.wise.meta.model.controller
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 22.
 * </PRE>
 */
@Controller
@RequestMapping("/dq/model/*")
public class WdqModelPdmCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private WdqModelPdmService wdqModelPdmService;

	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;


	@Inject
	private CodeListService codeListService;
	/**
	 * <PRE>
	 * 1. MethodName : initBinder
	 * 2. Comment    : 폼 데이터 바인딩시 value 값이 ""인 경우 Vo에 NULL로 바인딩 한다...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 27.
	 * </PRE>
	 *   @return void
	 *   @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 처리...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 27.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		String regtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regtypcd", regtypcd);
		
		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));

		//진단대상(DB_CONN_TRG_ID)
		codeMap.put("dbConnTrgibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("connTrgDbms")));
		
		//스키마
		codeMap.put("connTrgSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("getConnTrgSchIdMdl")));
		return codeMap;
	}


	@RequestMapping("popup/pdmtblSearchPop.do")
	public String goPdmTblPop(@ModelAttribute("search") WdqmPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

//		if(!UtilString.isBlank(search.getPdmTblLnm())) {
//			model.addAttribute("pdmTblLnm" , search.getPdmTblLnm());
//
////			mv.addObject("subjLnm", search.getSubjLnm());
//		}
//		mv.setViewName("/dq/model/subjSearchPop");

//		return mv;

		return "/dq/model/popup/pdmTblSearch_pop";
	}
	
	@RequestMapping("popup/pdmcol_pop.do")
	public String goPdmColPop(@ModelAttribute("search") WdqmPdmTbl search, Model model, Locale locale) {

		logger.debug("{}", search);
		return "/dq/model/popup/pdmcol_pop";
	}


	@RequestMapping("/pdmtbl_lst.do")
	public String goPdmTblList(@ModelAttribute("search") WdqmPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		model.addAttribute("search" , search);
		
		return "/dq/model/pdmTbl_lst";
	}

	@RequestMapping("/pdmtblhist_lst.do")
	public String goPdmTblHist(@ModelAttribute WdqmPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

		if(!UtilString.isBlank(search.getPdmTblLnm())) {
			model.addAttribute("search" , search);

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/dq/model/subjSearchPop");

//		return mv;

		return "/dq/model/pdmtblhist_lst";
	}

	@RequestMapping("/pdmtbl_ifm.do")
	public String goPdmTblTop30(@ModelAttribute WdqmPdmTbl search, Model model, Locale locale) {

		return "/dq/model/pdmTbl_ifm";
	}

	@RequestMapping("getpdmcollist.do")
	@ResponseBody
	public IBSheetListVO<WdqmPdmCol> getPdmCollist(@ModelAttribute WdqmPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WdqmPdmCol> list = null;
		try {
				list = wdqModelPdmService.getPdmColList(search);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new IBSheetListVO<WdqmPdmCol>(list, list.size());

	}

	@RequestMapping("gethistcollist.do")
	@ResponseBody
	public IBSheetListVO<WdqmPdmCol> getColHistlist(@ModelAttribute WdqmPdmTbl search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WdqmPdmCol> list = wdqModelPdmService.getPdmColHistList(search);

		return new IBSheetListVO<WdqmPdmCol>(list, list.size());

	}
	
	@RequestMapping("getpdmtbllist.do")
	@ResponseBody
	public IBSheetListVO<WdqmPdmTbl> getPdmTbLlist(@ModelAttribute WdqmPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WdqmPdmTbl> list = wdqModelPdmService.getPdmTblList(search);

		return new IBSheetListVO<WdqmPdmTbl>(list, list.size());

	}
	
	//테이블이력 조회(tblhist_dtl.jsp)
	@RequestMapping("gethistbllist.do")
	@ResponseBody
	public IBSheetListVO<WdqmPdmTbl> getPdmTbLHistlist(@ModelAttribute WdqmPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WdqmPdmTbl> list = wdqModelPdmService.getPdmTblHistList(search);

		return new IBSheetListVO<WdqmPdmTbl>(list, list.size());

	}

	@RequestMapping("getpdmtblhist.do")
	@ResponseBody
	public IBSheetListVO<WdqmPdmTbl> getPdmTbLhist(@ModelAttribute WdqmPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WdqmPdmTbl> list = wdqModelPdmService.getPdmTblHist(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmPdmTbl>(list, list.size());

	}
	
	@RequestMapping("/pdmtblcol_lst.do")
	public String goPdmTblColList(@ModelAttribute("search") WdqmPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		//주제영역 조회에서 더블클릭으로 subjLnm를 받아오는 경우가 있어서 || 뒤의 조건 추가함...(김연호)
		if(!UtilString.isBlank(search.getPdmTblLnm()) ) {
			model.addAttribute("search" , search);

		}
		return "/dq/model/pdmTblcol_lst";
	}
	@RequestMapping("/colnonstnd_lst.do")
	public String goColNonStndList(@ModelAttribute("search") WdqmPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		
		return "/dq/model/colnonstnd_lst";
	}
	
	@RequestMapping("/dq/model/prtXlsRptPdmTbl.do")
	@ResponseBody
	public void prtXlsRptPdmTbl(@ModelAttribute WdqmPdmTbl search,  HttpServletRequest request, HttpServletResponse response ) throws IOException, URISyntaxException { 

		logger.debug("searchVO:{}", search);
		
		String filePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ WiseConfig.EXCEL_RPT_PATH, null, Locale.getDefault());
			
		File srcFile = new File(filePath + "/table_sample.xlsx"); 
				
		
        FileInputStream is = new FileInputStream(srcFile);
        
        //엑셀 처리 
        XSSFWorkbook workbook = createXlsxForPdmTbl(srcFile, search);
                
        //==========엑셀 다운======================
        // Set the content type.
 		String browser = UtilExcel.getBrowser(request); 
 		String fileName = "테이블정의서.xlsx";
 		String encodedFilename = UtilExcel.getencodingfilename(browser, fileName);
 		
       
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(encodedFilename.getBytes("UTF-8"), "8859_1") + "\"");	
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        
        workbook.write(out);
        
        out.close(); 
        //=======================================
	}
	
	private XSSFWorkbook createXlsxForPdmTbl(File srcFile, WdqmPdmTbl search) throws IOException{
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String userId = user.getUniqId();
		String userNm = user.getName();
		
		String curDate = UtilDate.str2Str(UtilDate.getCurrentDate(), '-');
		
		FileInputStream is = new FileInputStream(srcFile);
        
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        
        Sheet tblSht = workbook.getSheet("table_list");
        
        //스타일 세팅
      	CellStyle style = setBsCellStyle(workbook, "L");
       
        Row row = null;
        Cell cell = null;
        
        //========테이블목록 excel 헤더 세팅=========
        row = tblSht.getRow(1);
        
        cell = row.getCell(2);
    	cell.setCellStyle(style);
    	
    	cell = row.getCell(4);
    	cell.setCellValue(curDate);
    	cell.setCellStyle(style);
    	
    	cell = row.getCell(7);
    	cell.setCellValue(userNm);
    	cell.setCellStyle(style);
    	//============================
    	
    	List<WdqmPdmTbl> list = wdqModelPdmService.getPdmTblList(search);
		
       
        int strRow = 3;
        int iNum = 1;        
        
        for(WdqmPdmTbl srchVo : list) {
        	
        	row = tblSht.createRow(strRow);
        	
        	int iCell = 0;
        	        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(iNum);
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getPdmTblPnm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getPdmTblLnm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        //	cell.setCellValue(srchVo.getColCnt());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);        	
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);        	
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);        	
        	cell.setCellStyle(style);
        	
        	iNum++;   
        	strRow++;
        }
        
        //============컬럼 sheet 세팅 ==================
        createXlsxForPdmCol(workbook, list, search);
        		
		return workbook;
	}
	
	private XSSFWorkbook createXlsxForPdmCol(XSSFWorkbook workbook, List<WdqmPdmTbl> list, WdqmPdmTbl search) throws IOException{
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String userId = user.getUniqId();
		String userNm = user.getName();
		
		String curDate = UtilDate.str2Str(UtilDate.getCurrentDate(), '-');
		
		//스타일 세팅
		CellStyle style = setBsCellStyle(workbook, "L");
		
        //============컬럼 sheet 세팅 ==================
        
        //==테이블 개수 만클 sheet 복제==         
		for(int i = 1; i < list.size() ; i++) {
        	
        	 workbook.cloneSheet(1);           	 
        }
		//=====================
        
        
        Row row = null;
        Cell cell = null;
                
        int colShtNo = 1; 
        
        Sheet colSht = null;
        
        for(WdqmPdmTbl tblVo : list) {
        	
        	String pdmTblId = tblVo.getPdmTblId(); 
        	String pdmTblPnm = tblVo.getPdmTblPnm();
        	
        	
        	colSht = workbook.getSheetAt(colShtNo);
        	
        	//========테이블정의서 excel 헤더 세팅=========
        	//1행
            row = colSht.getRow(1);
            
            cell = row.getCell(2);
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(4);
        	cell.setCellValue(curDate);
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(6);
        	cell.setCellValue(userNm);
        	cell.setCellStyle(style);
        	
        	//2행
        	row = colSht.getRow(2);
             
            cell = row.getCell(2);
         	cell.setCellValue(tblVo.getPdmTblPnm());
         	cell.setCellStyle(style);
         	
         	cell = row.getCell(4);
         	cell.setCellValue(tblVo.getPdmTblLnm());
         	cell.setCellStyle(style);
         	
         	//3행
         	row = colSht.getRow(3);
         	
         	cell = row.getCell(2);
         	cell.setCellValue(tblVo.getObjDescn());
         	cell.setCellStyle(style);
         	
        	//============================
        	
         	//시트명 설정 아래 주석으로 하니 죽어라 안됨  skkim
         	workbook.getCTWorkbook().getSheets().getSheetArray(colShtNo).setName(pdmTblPnm); 
         	      	
        	//workbook.setSheetName(colShtNo, tblVo.getPdmTblPnm()); 
        	
         	//컬럼리스트 세팅 
         	WdqmPdmCol srchcol = new WdqmPdmCol(); 
         	
         	srchcol.setPdmTblId(pdmTblId);
         	         	
         	List<WdqmPdmCol> coLst = wdqModelPdmService.getPdmColList(srchcol);
         	
         	int strColRow = 5;
            int iColNum = 1;         
            
         	
         	for(WdqmPdmCol colVo : coLst) {
         		
         		row = colSht.createRow(strColRow);
            	
            	int iCell = 0;
            	        	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(iColNum);
            	cell.setCellStyle(style);
            	            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(colVo.getPdmColPnm());
            	cell.setCellStyle(style);
            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(colVo.getPdmColLnm());
            	cell.setCellStyle(style);
            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(UtilString.null2Blank(colVo.getDataType()));
            	cell.setCellStyle(style);
            	            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(UtilString.null2Blank(colVo.getNonulYn()));
            	cell.setCellStyle(style);
            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(UtilString.null2Blank(colVo.getPkYn()));
            	cell.setCellStyle(style);
            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(UtilString.null2Blank(colVo.getDefltVal()));
            	cell.setCellStyle(style);
            	
            	cell = row.createCell(iCell++);
            	cell.setCellValue(UtilString.null2Blank(colVo.getObjDescn()));
            	cell.setCellStyle(style);
            	            	
            	iColNum++;   
            	strColRow++;
         	}
         	
        	
        	colShtNo++;
        }
                
        //==============컬럼sheet 세팅 end================
        
        
		
		return workbook;
	}
	
	
	public CellStyle setBsCellStyle(XSSFWorkbook workbook, String align) {
        
		CellStyle style = workbook.createCellStyle(); 
		
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		
		if(align.equals("L")) {
			
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}else if(align.equals("C")) {
			style.setAlignment(CellStyle.ALIGN_CENTER);
		}else if(align.equals("R")) {
			style.setAlignment(CellStyle.ALIGN_RIGHT);	
		}
		
		XSSFFont font = workbook.createFont();
		
		font.setFontName("굴림체");	
		font.setFontHeightInPoints((short)11); 

		style.setFont(font);
		
						
        return style;
    }
	
}
