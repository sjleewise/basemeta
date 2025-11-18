/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizruleCtrl.java
 * 2. Package : kr.wise.dq.bizrule.web
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 4. 29. 오전 9:53:28
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 4. 29. :            : 신규 개발.
 */
package kr.wise.dq.bizrule.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilExcel;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.bizrule.service.BizruleService;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : BizruleCtrl.java
 * 3. Package  : kr.wise.dq.bizrule.web
 * 4. Comment  :
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 4. 29. 오전 9:53:28
 * </PRE>
 */
@Controller
public class BizruleCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WamBrMstrVO extends HashMap<String, ArrayList<WamBrMstr>> { }

	private Map<String, Object> codeMap;

	@Inject
	BizruleService bizruleService;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	//데이터패턴 조회
	@Inject
	private DataPatternService DptrnService;

	@Inject
	private MessageSource message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);

		//검증join코드
		codeMap.put("tgtVrfJoinCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TGT_VRF_JOIN_CD")));
		codeMap.put("tgtVrfJoinCd", cmcdCodeService.getCodeList("TGT_VRF_JOIN_CD"));
		
		//실행차수
		List<CodeListVo> anaDgr = codelistService.getCodeList(CodeListAction.anaDgr);
		codeMap.put("anaDgrCd", anaDgr);
		
		// 업무규칙 차수
		List<CodeListVo> brAnaDgr = codelistService.getCodeList(CodeListAction.bizrAnaDgr);
		codeMap.put("brAnaDgrCd", brAnaDgr);
		
		
		return codeMap;
	}

	
	//업무규칙 조회 화면
	@RequestMapping("/dq/bizrule/bizrule_lst.do")
	public String formPage(@ModelAttribute("search") WamBrMstr search,String linkFlag,Model model){
		logger.debug("search : {}",search);
		logger.debug("linkFlag : {}", linkFlag);
		model.addAttribute("linkFlag", linkFlag);
		return "/dq/bizrule/bizrule_lst";
	}

	//업무규칙 조회
	@RequestMapping("/dq/bizrule/getBizruleList.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> selectList(@ModelAttribute WamBrMstr search){
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleService.getBizruleList(search);

		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}

	//검증대상DB 리스트 조회
	@RequestMapping("/dq/bizrule/getVrtDbList.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> selectvrtDbList(@ModelAttribute WamBrMstr search){
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleService.getvrtDbList(search);

		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}

	//검증테이블명 리스트 조회
	@RequestMapping("/dq/bizrule/getVrtTblList.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> selectvrtTblList(@ModelAttribute WamBrMstr search){
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleService.getvrtTblList(search);

		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}

	//검증컬럼명 리스트 조회
	@RequestMapping("/dq/bizrule/getVrtColList.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> selectvrtColList(@ModelAttribute WamBrMstr search){
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleService.getvrtColList(search);

		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}

	//업무규칙 조회
		@RequestMapping("/dq/bizrule/getBizruleListPop.do")
		@ResponseBody
		public IBSheetListVO<WamBrMstr> selectListPop(@ModelAttribute WamBrMstr search){
			logger.debug("{}", search);
			List<WamBrMstr> list = bizruleService.getBizruleListPop(search);

			return new IBSheetListVO<WamBrMstr>(list, list.size());
		}

	//업무규칙 상세 팝업
	@RequestMapping("/dq/bizrule/popup/bizrule_dtl_pop.do")
	public String bizDtlPopup(@ModelAttribute WamBrMstr searchVO,@ModelAttribute("search") DataPatternVO search, Model model) {

		logger.debug(" {}", searchVO);

		WamBrMstr result = bizruleService.getBizruleDtlList(searchVO);

		model.addAttribute("result", result);

		//데이터패턴 추가...
		int headerCnt = 0;
		String headerText = new String();
		//IBSHEET 컬럼 건수 조회
		DataPatternVO headerCntVO = DptrnService.DptrnHeaderInit(search);
		
		if( null !=headerCntVO ){
			headerCnt = headerCntVO.getColCnt()  ;
			
			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			//MARIA DB의 경우 || 로 문자열 결합 불가하여 CONCAT 새로 추가함.
			String headerColNmMaria = new String() ;
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;	
			StringBuffer tmpColNmMaria = new StringBuffer() ;
			
			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" );
					tmpColNmMaria.append(", COL_NM" + i + ", '|'");
				}
				headerColNm = tmpColNm.toString();
				headerColNmMaria = tmpColNmMaria.toString();
				search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));
				search.setHeaderTextColNmMaria("CONCAT(".concat(tmpColNmMaria.substring(1, headerColNmMaria.length()-5).concat(")")));
				
				//IBSHEET 헤더텍스트 조회
				DataPatternVO headerTextVO = DptrnService.DptrnHeaderText(search);
				headerText = headerTextVO.getHeaderText();
			}
			
			search.setColNm(colNm.toString());
			search.setColCnt(headerCnt);
			search.setHeaderText(headerText);
			
			model.addAttribute("headerVO", search);
		}
		
		return "/dq/bizrule/popup/bizrule_dtl_pop";
	}

	//업무규칙 기본정보 조회
	@RequestMapping("/dq/bizrule/ajaxgrid/bizrule_detail.do")
	public String bizDetail(@ModelAttribute WamBrMstr search, ModelMap model){

		model.addAttribute("result", search);

		return "/dq/bizrule/bizrule_detail";
	}

	//업무규칙 진단정보 조회
	@RequestMapping("/dq/bizrule/ajaxgrid/bizrule_dbDetail.do")
	public String bizDbDetail(@ModelAttribute WamBrMstr search, ModelMap model){

		model.addAttribute("result", search);

		return "/dq/bizrule/bizrule_dbDetail";
	}

	//업무규칙 비교검증정보 조회
	@RequestMapping("/dq/bizrule/ajaxgrid/bizrule_vrtDetail.do")
	public String bizVrfDetail(@ModelAttribute WamBrMstr search, ModelMap model){

		model.addAttribute("result", search);

		return "/dq/bizrule/bizrule_vrtDetail";
	}

	/* 이력조회 */
	@RequestMapping("/dq/bizrule/getBizRuleHstLst.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getBizAreaHstLst(String brId) {

		logger.debug("{}", brId);
		List<WamBrMstr> list = bizruleService.getBizRuleHstLst(brId);

		return new IBSheetListVO<WamBrMstr>(list, list.size());

	}

	@RequestMapping("/dq/bizrule/prtXlsRptBizrule.do")
	@ResponseBody
	public void prtXlsRptPdmTbl(WamBrMstr srchVo,  HttpServletRequest request, HttpServletResponse response ) throws IOException, URISyntaxException { 

		//logger.debug("searchVO:{}", search); 
		
		String filePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ WiseConfig.EXCEL_RPT_PATH, null, Locale.getDefault());
		filePath = filePath.trim();
		File srcFile = new File(filePath + "/bizrule_sample.xlsx"); 
				
		
        FileInputStream is = new FileInputStream(srcFile);
        
       
        //엑셀 처리 
        XSSFWorkbook workbook = createXlsxForBizrule(srcFile, srchVo); 
                
        //==========엑셀 다운======================
        // Set the content type.
 		String browser = UtilExcel.getBrowser(request); 
 		String fileName = "업무규칙.xlsx";
 		String encodedFilename = UtilExcel.getencodingfilename(browser, fileName);
 		
       
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(encodedFilename.getBytes("UTF-8"), "8859_1") + "\"");	
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        
        workbook.write(out);
        
        out.close(); 
        //=======================================
	}
	
	private XSSFWorkbook createXlsxForBizrule(File srcFile, WamBrMstr search) throws IOException{
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String userId = user.getUniqId();
		String userNm = user.getName();
		
		String curDate = UtilDate.str2Str(UtilDate.getCurrentDate(), '-');
		
		FileInputStream is = new FileInputStream(srcFile);
        
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        
        Sheet tblSht = workbook.getSheet("bizrule_list");
        
        //스타일 세팅
      	CellStyle style = setBsCellStyle(workbook, "L");
       
        Row row = null;
        Cell cell = null;
        
        //========업무규칙목록 excel 헤더 세팅=========
        row = tblSht.getRow(1);
        
        cell = row.getCell(2);
    	cell.setCellValue("");
    	cell.setCellStyle(style);
    	
    	cell = row.getCell(4);
    	cell.setCellValue(curDate);
    	cell.setCellStyle(style);
    	
    	cell = row.getCell(7);
    	cell.setCellValue(userNm);
    	cell.setCellStyle(style);
    	//============================
    	
    	List<WamBrMstr> list = bizruleService.getBizruleXlsxList(search);     
		
       
        int strRow = 3;
        int iNum = 1;        
        
        for(WamBrMstr srchVo : list) {
        	
        	row = tblSht.createRow(strRow);
        	
        	int iCell = 0;
        	        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(iNum);
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getBizAreaLnm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getBrNm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getDbConnTrgPnm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getDbcTblNm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getDbcColNm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(UtilString.null2Blank( srchVo.getAnaCnt()) );
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(UtilString.null2Blank( srchVo.getErCnt()) );
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(UtilString.null2Blank( srchVo.getErRate()) );
        	cell.setCellStyle(style);
        	
        	iNum++;   
        	strRow++;
        }
        
        //============컬럼 sheet 세팅 ==================
        createXlsxForBrDetail(workbook, list, search);
        		
		return workbook;
	}
	
	private XSSFWorkbook createXlsxForBrDetail(XSSFWorkbook workbook, List<WamBrMstr> list, WamBrMstr search) throws IOException{
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String userId = user.getUniqId();
		String userNm = user.getName();
		
		String curDate = UtilDate.str2Str(UtilDate.getCurrentDate(), '-');
		
		//스타일 세팅
		CellStyle style = setBsCellStyle(workbook, "L");
		
        //============컬럼 sheet 세팅 ==================
        
        //==BR 개수 만클 sheet 복제==         
		for(int i = 1; i < list.size() ; i++) {
        	
        	 workbook.cloneSheet(1);           	 
        }
		//=====================
        
        
        Row row = null;
        Cell cell = null;
                
        int colShtNo = 1; 
        
        Sheet colSht = null;
        
        for(WamBrMstr tblVo : list) {
        	
        	String brId = tblVo.getBrId(); 
        	String brNm = tblVo.getBrNm();
        	
        	
        	colSht = workbook.getSheetAt(colShtNo);
        	
        	//========테이블정의서 excel 헤더 세팅=========
        	//1행
            row = colSht.getRow(1);
            
            cell = row.getCell(2);
        	cell.setCellValue(tblVo.getBizAreaLnm());
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(4);
        	cell.setCellValue(curDate);
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(7);
        	cell.setCellValue(userNm);
        	cell.setCellStyle(style);
        	
        	//2행
        	row = colSht.getRow(2);
             
        	cell = row.getCell(2);
        	cell.setCellValue(tblVo.getBrNm());
        	cell.setCellStyle(style);
        	
        	//3행
         	row = colSht.getRow(3);
         	
         	cell = row.getCell(2);
        	cell.setCellValue(tblVo.getObjDescn());
        	cell.setCellStyle(style);
        	
        	
         	//4행
         	row = colSht.getRow(4);
         	
         	cell = row.getCell(2);
        	cell.setCellValue(tblVo.getDqiLnm());
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(4);
        	cell.setCellValue(tblVo.getCtqLnm());
        	cell.setCellStyle(style);
         	
        	cell = row.getCell(7);
        	cell.setCellValue(tblVo.getBrCrgpUserNm());
        	cell.setCellStyle(style);
        	
        	//5행
         	row = colSht.getRow(5);
         	
         	cell = row.getCell(2);
        	cell.setCellValue(tblVo.getDbConnTrgPnm());
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(4);
        	cell.setCellValue(tblVo.getDbcTblNm());
        	cell.setCellStyle(style);
        	
        	cell = row.getCell(6);
        	cell.setCellValue(tblVo.getDbcColNm());
        	cell.setCellStyle(style);
        	
        	//6행
         	row = colSht.getRow(6);
         	
         	cell = row.getCell(2);
        	cell.setCellValue(tblVo.getAnaSql());
        	cell.setCellStyle(style);
        	//============================
        	
         	//시트명 설정 아래 주석으로 하니 죽어라 안됨  skkim
         	workbook.getCTWorkbook().getSheets().getSheetArray(colShtNo).setName(brId); 
         	      	
        	//workbook.setSheetName(colShtNo, tblVo.getPdmTblPnm()); 
        	
         	
        	
        	colShtNo++;
        }
                
        //==============컬럼sheet 세팅 end================
        
        
		
		return workbook;
	}
	
	
	public CellStyle setBsCellStyle(XSSFWorkbook workbook, String align) {
        
		CellStyle style = workbook.createCellStyle(); 
		
		style.setWrapText(true);
		
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
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
