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
package kr.wise.meta.model.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Date;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.handler.PoiHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilExcel;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.model.service.GovVo;
import kr.wise.meta.model.service.ModelPdmService;
import kr.wise.meta.model.service.PdmTblColDownVo;
import kr.wise.meta.model.service.WaaGovServerInfo;
import kr.wise.meta.model.service.WamPdmCol;
import kr.wise.meta.model.service.WamPdmRel;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.subjarea.service.WaaSubj;

import org.apache.poi.ss.usermodel.BorderStyle;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
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
@RequestMapping("/meta/model/*")
public class ModelPdmCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private ModelPdmService modelPdmService;
	
	@Inject
	private WaaDbConnTrgMapper waaDbConnTrgMapper;

	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;

	static class WaaSubjs extends HashMap<String, ArrayList<WaaSubj>> {}
	
	static class WaaGovServerInfos extends HashMap<String, ArrayList<WaaGovServerInfo>> {};

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

		//시스템영역 코드리스트 JSON
		List<CodeListVo> sysareatmp = codelistService.getCodeList("sysarea");
		String sysarea = UtilJson.convertJsonString(sysareatmp);
		String sysareaibs = UtilJson.convertJsonString(codelistService.getCodeListIBS(sysareatmp));
		

		String regtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD"));

//		codeMap.put("subjPdmTbl", UtilJson.convertJsonString(codelistService.getCodeList("subjPdmTbl")));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regtypcd", regtypcd);
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		//관계유형코드
		codeMap.put("relTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REL_TYP_CD")));
		codeMap.put("relTypCd", cmcdCodeService.getCodeList("REL_TYP_CD"));
		//카디널리티유형코드
		codeMap.put("crdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CRD_TYP_CD")));
		codeMap.put("crdTypCd", cmcdCodeService.getCodeList("CRD_TYP_CD"));
		//Parent Optionality 유형코드
		codeMap.put("paOptTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PA_OPT_TYP_CD")));
		codeMap.put("paOptTypCd", cmcdCodeService.getCodeList("PA_OPT_TYP_CD"));
		
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
		
		//범정부연계항목코드 
		//보존기간
		codeMap.put("prsvTermibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRSV_TERM")));
		codeMap.put("prsvterm", UtilJson.convertJsonString(cmcdCodeService.getCodeList("PRSV_TERM")));
		//공개/비공개여부
		codeMap.put("openRsnCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OPEN_RSN_CD")));
		codeMap.put("openrsncd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("OPEN_RSN_CD")));
		//비공개사유
		codeMap.put("nopenRsnibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NOPEN_RSN_CD")));
		codeMap.put("nopenrsn", UtilJson.convertJsonString(cmcdCodeService.getCodeList("NOPEN_RSN_CD")));
		//발생주기
		codeMap.put("occrCylibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OCCR_CYL")));
		codeMap.put("occrcyl", UtilJson.convertJsonString(cmcdCodeService.getCodeList("OCCR_CYL")));
		//테이블유형
		codeMap.put("tblTypNmibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_TYP_NM")));
		codeMap.put("tbltypnm", UtilJson.convertJsonString(cmcdCodeService.getCodeList("TBL_TYP_NM")));
		
		
		return codeMap;
	}


	@RequestMapping("popup/pdmtblSearchPop.do")
	public String goPdmTblPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

//		if(!UtilString.isBlank(search.getPdmTblLnm())) {
//			model.addAttribute("pdmTblLnm" , search.getPdmTblLnm());
//
////			mv.addObject("subjLnm", search.getSubjLnm());
//		}
//		mv.setViewName("/meta/model/subjSearchPop");

//		return mv;

		return "/meta/model/popup/pdmTblSearch_pop";
	}
	
	@RequestMapping("popup/pdmcol_pop.do")
	public String goPdmColPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {

		logger.debug("{}", search);
		return "/meta/model/popup/pdmcol_pop";
	}


	@RequestMapping("/pdmtbl_lst.do")
	public String goPdmTblList(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		//주제영역 조회에서 더블클릭으로 subjLnm를 받아오는 경우가 있어서 || 뒤의 조건 추가함...(김연호)
		if(!UtilString.isBlank(search.getPdmTblLnm()) || !UtilString.isBlank(search.getSubjLnm())) {
			model.addAttribute("search" , search);

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/meta/model/subjSearchPop");

//		return mv;

		return "/meta/model/pdmTbl_lst";
	}

	@RequestMapping("/pdmtblhist_lst.do")
	public String goPdmTblHist(@ModelAttribute WamPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

		if(!UtilString.isBlank(search.getPdmTblLnm())) {
			model.addAttribute("search" , search);

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/meta/model/subjSearchPop");

//		return mv;

		return "/meta/model/pdmtblhist_lst";
	}

	@RequestMapping("/pdmtbl_ifm.do")
	public String goPdmTblTop30(@ModelAttribute WamPdmTbl search, Model model, Locale locale) {

		return "/meta/model/pdmTbl_ifm";
	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmCollist
	 * 2. Comment    : 컬럼리스트 검색...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 27.
	 * </PRE>
	 *   @return IBSJsonSearch
	 *   @param search
	 *   @param locale
	 *   @return
	 */
	@RequestMapping("getpdmcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getPdmCollist(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("getgappdmcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getGapPdmCollist(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getGapPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	/** @param search
	/** @param locale
	/** @return yeonho */
	@RequestMapping("selectPdmColList.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> selectPdmColList(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.selectPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}

	@RequestMapping("getcoldtllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getColDtllist(@ModelAttribute WamPdmCol search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getPdmColDtlList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}

	@RequestMapping("gethistcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getColHistlist(@ModelAttribute WamPdmTbl search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getPdmColHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	

	@RequestMapping("getstathistbllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getStatHisTbllist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = modelPdmService.getPdmStatHisTblList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmTbLlist
	 * 2. Comment    : 테이블 리스트 검새...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 27.
	 * </PRE>
	 *   @return IBSJsonSearch
	 *   @param search
	 *   @param locale
	 *   @return
	 */
	@RequestMapping("getpdmtbllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLlist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		search.setUserId(((LoginVO)UserDetailHelper.getAuthenticatedUser()).getUniqId());
		List<WamPdmTbl> list = modelPdmService.getPdmTblList(search);
		
//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}
	
	//테이블이력 조회(tblhist_dtl.jsp)
	@RequestMapping("gethistbllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLHistlist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = modelPdmService.getPdmTblHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}

	@RequestMapping("getidxcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmIdxCollist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = modelPdmService.getPdmIdxColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}

	@RequestMapping("getpdmtblhist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLhist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = modelPdmService.getPdmTblHist(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}


	@RequestMapping("getpdmtblTop30.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLTop30(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = modelPdmService.getPdmTblTop30(search);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}
	
	/** 물리모델 관계 조회 yeonho */
	@RequestMapping("getpdmrellist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelList(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = modelPdmService.getPdmRelList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** 물리모델 관계 이력조회 yeonho */
	@RequestMapping("getpdmrelhistlist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelHistList(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = modelPdmService.getPdmRelHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** 물리모델 관계 조회(이력페이지) yeonho */
	@RequestMapping("getpdmrellistHistPage.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelListHistPage(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = modelPdmService.getPdmRelListHistPage(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** 물리모델 관계 이력조회(이력페이지) yeonho */
	@RequestMapping("getpdmrelhistlistHistPage.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelHistListHistPage(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = modelPdmService.getPdmRelHistListHistPage(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** DDL 테이블 추가를 위한 물리모델 조회 */
	@RequestMapping("getPdmTblListForDdl.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLlistForDdl(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = modelPdmService.getPdmTblListForDdl(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}
	
	@RequestMapping("/pdmtblcol_lst.do")
	public String goPdmTblColList(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		//주제영역 조회에서 더블클릭으로 subjLnm를 받아오는 경우가 있어서 || 뒤의 조건 추가함...(김연호)
		if(!UtilString.isBlank(search.getPdmTblLnm()) || !UtilString.isBlank(search.getSubjLnm())) {
			model.addAttribute("search" , search);

		}
		return "/meta/model/pdmTblcol_lst";
	}

	@RequestMapping("gethisttblcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getTblColHistlist(@ModelAttribute WamPdmCol search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getPdmColHistListDtl(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("getpdmcolchglist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getPdmColChgList(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getPdmColChgList(search);


		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("/colnonstnd_lst.do")
	public String goColNonStndList(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		
		return "/meta/model/colnonstnd_lst";
	}

	//비표준 컬럼분석 조회
	@RequestMapping("getcolnonstndlist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getColNonStndlist(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = modelPdmService.getColNonStndlist(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("/gov_lst.do")
	public String goGovList(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

		
		return "/meta/model/gov_lst";
	}
	
	//비표준 컬럼분석 조회
		@RequestMapping("/getGovList.do")
		@ResponseBody
		public IBSheetListVO<GovVo> getGovList(@ModelAttribute GovVo search, Locale locale) {
			logger.debug("searchVO:{}", search);
			
			List<GovVo> list = modelPdmService.getGovList(search);

			return new IBSheetListVO<GovVo>(list, list.size());

		}
	
	@RequestMapping("/meta/model/prtXlsRptPdmTbl.do")
	@ResponseBody
	public void prtXlsRptPdmTbl(@ModelAttribute WamPdmTbl search,  HttpServletRequest request, HttpServletResponse response ) throws IOException, URISyntaxException { 

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
	
	private XSSFWorkbook createXlsxForPdmTbl(File srcFile, WamPdmTbl search) throws IOException{
		
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
    	cell.setCellValue(search.getSubjLnm());
    	cell.setCellStyle(style);
    	
    	cell = row.getCell(4);
    	cell.setCellValue(curDate);
    	cell.setCellStyle(style);
    	
    	cell = row.getCell(7);
    	cell.setCellValue(userNm);
    	cell.setCellStyle(style);
    	//============================
    	
    	List<WamPdmTbl> list = modelPdmService.getPdmTblList(search);
		
       
        int strRow = 3;
        int iNum = 1;        
        
        for(WamPdmTbl srchVo : list) {
        	
        	row = tblSht.createRow(strRow);
        	
        	int iCell = 0;
        	        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(iNum);
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getFullPath());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getPdmTblPnm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getPdmTblLnm());
        	cell.setCellStyle(style);
        	
        	cell = row.createCell(iCell++);
        	cell.setCellValue(srchVo.getColCnt());
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
	
	private XSSFWorkbook createXlsxForPdmCol(XSSFWorkbook workbook, List<WamPdmTbl> list, WamPdmTbl search) throws IOException{
		
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
        
        for(WamPdmTbl tblVo : list) {
        	
        	String pdmTblId = tblVo.getPdmTblId(); 
        	String pdmTblPnm = tblVo.getPdmTblPnm();
        	
        	
        	colSht = workbook.getSheetAt(colShtNo);
        	
        	//========테이블정의서 excel 헤더 세팅=========
        	//1행
            row = colSht.getRow(1);
            
            cell = row.getCell(2);
        	cell.setCellValue(search.getSubjLnm());
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
         	WamPdmCol srchcol = new WamPdmCol(); 
         	
         	srchcol.setPdmTblId(pdmTblId);
         	         	
         	List<WamPdmCol> coLst = modelPdmService.getPdmColList(srchcol);
         	
         	int strColRow = 5;
            int iColNum = 1;         
            
         	
         	for(WamPdmCol colVo : coLst) {
         		
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
	
	@RequestMapping("poiDown.do")
	@ResponseBody
    public void poiDown (HttpServletResponse response, @ModelAttribute GovVo search) throws Exception{
    	logger.debug("poiDown clicked");
    	String excelType = ".xlsx"; //.xlsx와 .xls 중 택 1
    	//String excelType = ".xls";
    	
    	//db list query
    	WaaDbConnTrgVO dbParam = new WaaDbConnTrgVO();
    	List<WaaDbConnTrgVO> dbList = waaDbConnTrgMapper.selectList(dbParam);
    	WaaGovServerInfo gInfo = modelPdmService.getGovServerInfo();
    	
//    	if(gInfo == null) {
//    		logger.debug("gInfo is null");
//    		StringBuffer sb = new StringBuffer();
//			sb.append("<script type='text/javascript'>");
//			sb.append("window.close();");
//			sb.append("</script>");
//			
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println(sb);
//			out.flush();
//    	}

    	logger.debug("gInfo is not null");
//    	logger.debug("gInfo LnkSts >>> " + gInfo.getLnkSts());
    	
    	//연계서버접속정보가 null인 경우는 configure.properties 파일에 정의 되어 있는 정보로 파일을 전송한다.
    	//연계서버접속정보가 null이 아닌 경우는 접속테스트 결과가 '접속 성공'인 경우만 파일을 전송한다.
    	if(gInfo == null || (gInfo != null && gInfo.getLnkSts() != null && gInfo.getLnkSts().equals("접속 성공"))) {
			if((search.getInfoSysNm() == null || search.getInfoSysNm().equals(""))
			  && (search.getDbPnm() == null || search.getDbPnm().equals(""))) {
		    	for(WaaDbConnTrgVO dbListVo : dbList) {
		    		
		    		logger.debug("dbConnTrgPnm >>> " + dbListVo.getDbConnTrgPnm());
		    		
		    		if(search.getDbPnm() == null || search.getDbPnm().equals("")){
		    			search.setDbPnm(dbListVo.getDbConnTrgPnm());
		    		}
		    	//db list param
			    	List<GovVo> list = modelPdmService.getGovListForSendFile(search);	//조회 목록
			    	
			    	DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("yyyyMMdd");
					DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HHmmssSSS");
					
					String fileNm = "M";
					
					if(list != null && list.size() > 0) {
						PoiHandler poiHandler = new PoiHandler(excelType);
						
						fileNm += list.get(0).getOrgCd() + "_" + LocalDate.now().format(formatterD).toString();
	//					fileNm += "6280000_" + LocalDate.now().format(formatterD).toString();
						fileNm += LocalTime.now().format(formatterT).toString() + "_I";
			    	
						poiHandler.createExcelGovList(list, fileNm);
				
						poiHandler.sendExcel(fileNm, response, gInfo);
					} 
					
					search.setDbPnm("");
		    	}
			} else {
				List<GovVo> list = modelPdmService.getGovListForSendFile(search);	//조회 목록
		    	
		    	DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("yyyyMMdd");
				DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HHmmssSSS");
				
				String fileNm = "M";
				
				
				if(list != null && list.size() > 0) {
					PoiHandler poiHandler = new PoiHandler(excelType);
					
					fileNm += list.get(0).getOrgCd() + "_" + LocalDate.now().format(formatterD).toString();
	//				fileNm += "6280000_" + LocalDate.now().format(formatterD).toString();
					fileNm += LocalTime.now().format(formatterT).toString() + "_I";
		    	
					poiHandler.createExcelGovList(list, fileNm);
					
					poiHandler.sendExcel(fileNm, response, gInfo);
				}
			}
    	} else {
    		StringBuffer sb = new StringBuffer();
			sb.append("<script type='text/javascript'>");
			sb.append("window.close();");
			sb.append("</script>");
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(sb);
			out.flush();
    	}
    }
	
	@RequestMapping("popup/gov_send_pop.do")
	public String goGovSend(@ModelAttribute GovVo data, Model model, Locale locale) {
		logger.debug("{}", data);
		
		GovVo result = modelPdmService.getOrgNm();
//		result.setConstYy(data.getConstYy());
		model.addAttribute("data", result); 
		
		WaaGovServerInfo result2 = modelPdmService.getGovServerInfo();
		model.addAttribute("data2",result2);
		
		return "/meta/model/popup/govsend_pop";
	}
	
	/** 범정부메타전송 정보 등록  - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/model/regGovServerInfo.do")
	@ResponseBody
	public IBSResultVO<WaaGovServerInfo> regGovServerInfo(@RequestBody WaaGovServerInfos data, Locale locale) throws Exception {
		logger.info("/meta/model/regGovServerInfo.do started...");
		logger.debug("#regGovServerInfo data:{}", data);
		
		String resultMsg = "";
		ArrayList<WaaGovServerInfo> list = data.get("data");
		
		int result = modelPdmService.regGovServerInfo(list);
				
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.SAVE", null, locale);
		}else { 
			result = -1;
			resultMsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		HashMap<String, String> resLnkSts = new HashMap<String, String>();
		
		resLnkSts.put("newLnkSts","");
		resLnkSts.put("newLnkStsCnts","");
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();

		return new IBSResultVO<WaaGovServerInfo>(resLnkSts, result, resultMsg, action);
	}
	
	@RequestMapping("/meta/model/connTestGovServer.do")
	@ResponseBody
	public IBSResultVO<WaaGovServerInfo> connTestGovServer(Locale locale) throws Exception {
		String resultMsg = "";

		WaaGovServerInfo gInfo = modelPdmService.getGovServerInfo();
		
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		// /////////////SFTP 접속 설정
		String host = "";
		int port = 0;
		String username = "";
		String password = "";
		String sftpPath = "";
		String errMsg = "";
		String resMsg = "";
		String resErrMsg = "";
		
		if(gInfo == null) {
			resErrMsg = "연계서버 접속 정보가 없습니다.";
		} else {
			host = gInfo.getSvrIp();
			port = new Integer(gInfo.getPort());
			username = gInfo.getConnId();
			password = gInfo.getConnPwd();
			sftpPath = gInfo.getFtpPath();
			errMsg = "";
			resMsg = "";
			resErrMsg = "";
			
			logger.debug("SFTP PATH : "+ sftpPath);
	
			try {
				// /////////////SFTP 접속 설정 끝
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
				
				if(session != null){
					try {
						logger.debug("session is not null");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				 
				int exitStatus = channel.getExitStatus();
				System.out.println("Exit Status : " + exitStatus);
				
				if(exitStatus > 0) {
					Exception e = new Exception(errMsg);
					resErrMsg = e.toString();
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				resErrMsg = e.toString();
			} finally {
				if(channel != null) 
					channel.disconnect();
				if(session != null)
					session.disconnect();
			}
		}
		
		if(resErrMsg == null || resErrMsg.equals("")) {
			resMsg = "접속 성공";
		} else {
			resMsg = "접속 실패";
		}
		
		int result = modelPdmService.connTestGovServer(resMsg, resErrMsg);
				
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.SAVE", null, locale);
		}else { 
			result = -1;
			resultMsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
//		model.addAttribute("newLnkSts",resMsg);
//		model.addAttribute("newLnkStsCnts",resErrMsg);
		
		HashMap<String, String> resLnkSts = new HashMap<String, String>();
		
		resLnkSts.put("newLnkSts",resMsg);
		resLnkSts.put("newLnkStsCnts",resErrMsg);
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();

		return new IBSResultVO<WaaGovServerInfo>(resLnkSts, result, resultMsg, action);
	}
	
	//감사원 테이블정의서 다운로드
	@RequestMapping("/meta/model/prtBaiXlsRptPdmTbl.do")
	@ResponseBody
	public void prtBaiXlsRptPdmTbl(@ModelAttribute PdmTblColDownVo search,  HttpServletRequest request, HttpServletResponse response ) throws IOException, URISyntaxException { 

		logger.debug("searchVO:{}", search);
		
		String filePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ WiseConfig.EXCEL_RPT_PATH, null, Locale.getDefault());
			
		File srcFile = new File(filePath + "/PAIS_PDM_TBL_DEFINITION_SAMPLE.xlsx"); 
				
		
        FileInputStream is = new FileInputStream(srcFile);
        
        //엑셀 처리 
        XSSFWorkbook workbook = createXlsxForBaiPdmTbl(srcFile, search);
                
        //==========엑셀 다운======================
        // Set the content type.
 		String browser = UtilExcel.getBrowser(request); 
 		String fileName = "테이블정의서_"+UtilDate.getCurrentDate()+".xlsx";
 		String encodedFilename = UtilExcel.getencodingfilename(browser, fileName);
 		
       
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(encodedFilename.getBytes("UTF-8"), "8859_1") + "\"");	
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        
        workbook.write(out);
        
        out.close(); 
        //=======================================
	}

	private XSSFWorkbook createXlsxForBaiPdmTbl(File srcFile, PdmTblColDownVo search) throws IOException {
		logger.debug("search" + search);
		
		FileInputStream is = new FileInputStream(srcFile);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		
		CellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		
		Sheet tblSht0 = workbook.getSheet("표지");
		Sheet tblSht1 = workbook.getSheet("테이블정의서");
		
		Row row = null;
		Cell cell = null;
		
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String curDate = dateForm.format(calendar.getTime());
		
		int strRow = 1;
		int no = 1;
		
		//표지
		tblSht0.getRow(9).getCell(2).setCellValue(curDate);
		
		//테이블정의서
		List<PdmTblColDownVo> result0 = modelPdmService.getPdmTblForDownBaiExl(search);
		
		for(PdmTblColDownVo srchVo : result0) {
			int iCell = 0;
			
			row = tblSht1.createRow(strRow);
			//NO
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(no));
			cell.setCellStyle(cellStyle);
			
			//DB_CONN_TRG_PNM
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getDbConnTrgPnm()));
			cell.setCellStyle(cellStyle);
			
			//DB_CONN_ANA_ID
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getDbConnAcId()));
			cell.setCellStyle(cellStyle);
			
			//PDM_TBL_LNM
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getPdmTblLnm()));
			cell.setCellStyle(cellStyle);
			
			//PDM_TBL_PNM
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getPdmTblPnm()));
			cell.setCellStyle(cellStyle);
			
			//공백
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(""));
			cell.setCellStyle(cellStyle);
			
			//FULL_PATH
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getFullPath()));
			cell.setCellStyle(cellStyle);
			
			//OBJ_DESCN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getObjDescn()));
			cell.setCellStyle(cellStyle);
			
			//공백
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(""));
			cell.setCellStyle(cellStyle);
			
			strRow++;
			no++;
		}
		
		return workbook;
	}
	
	//감사원 컬럼정의서 다운로드
	@RequestMapping("/meta/model/prtBaiXlsRptPdmCol.do")
	@ResponseBody
	public void prtBaiXlsRptPdmCol(@ModelAttribute PdmTblColDownVo search,  HttpServletRequest request, HttpServletResponse response ) throws IOException, URISyntaxException { 
	
		logger.debug("searchVO:{}", search);
		
		String filePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ WiseConfig.EXCEL_RPT_PATH, null, Locale.getDefault());
			
		File srcFile = new File(filePath + "/PAIS_PDM_COL_DEFINITION_SAMPLE.xlsx"); 
				
		
		    FileInputStream is = new FileInputStream(srcFile);
		    
		    //엑셀 처리 
		    XSSFWorkbook workbook = createXlsxForBaiPdmCol(srcFile, search);
		            
		    //==========엑셀 다운======================
		    // Set the content type.
			String browser = UtilExcel.getBrowser(request); 
			String fileName = "컬럼정의서_"+UtilDate.getCurrentDate()+".xlsx";
			String encodedFilename = UtilExcel.getencodingfilename(browser, fileName);
			
		   
		    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(encodedFilename.getBytes("UTF-8"), "8859_1") + "\"");	
		    
		    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		    
		    workbook.write(out);
		    
		    out.close(); 
		    //=======================================
	}
	
	private XSSFWorkbook createXlsxForBaiPdmCol (File srcFile, PdmTblColDownVo search) throws IOException {
		// TODO Auto-generated method stub
		logger.debug("search" + search);
		
		FileInputStream is = new FileInputStream(srcFile);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		
		CellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		
		Sheet tblSht0 = workbook.getSheet("표지");
		Sheet tblSht1 = workbook.getSheet("컬럼정의서");
		
		Row row = null;
		Cell cell = null;
		
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String curDate = dateForm.format(calendar.getTime());
		
		int strRow = 1;
		int no = 1;
		
		//표지
		tblSht0.getRow(9).getCell(2).setCellValue(curDate);
		
		//테이블정의서
		List<PdmTblColDownVo> result0 = modelPdmService.getPdmColForDownBaiExl(search);
		
		for(PdmTblColDownVo srchVo : result0) {
			int iCell = 0;
			
			row = tblSht1.createRow(strRow);
			//NO
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(no));
			cell.setCellStyle(cellStyle);
			
			//PDM_TBL_PNM
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getPdmTblPnm()));
			cell.setCellStyle(cellStyle);
			
			//PDM_COL_LNM
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getPdmColLnm()));
			cell.setCellStyle(cellStyle);
			
			//PDM_COL_PNM
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getPdmColPnm()));
			cell.setCellStyle(cellStyle);
			
			//OBJ_DESCN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getObjDescn()));
			cell.setCellStyle(cellStyle);
			
			//FULL_PATH
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getFullPath()));
			cell.setCellStyle(cellStyle);
			
			//공백
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(""));
			cell.setCellStyle(cellStyle);
			
			//DATA_TYPE
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getDataType()));
			cell.setCellStyle(cellStyle);
			
			//DATA_LEN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getDataLen()));
			cell.setCellStyle(cellStyle);
			
			//NONUL_YN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getNonulYn()));
			cell.setCellStyle(cellStyle);
			
			//PK_YN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getPkYn()));
			cell.setCellStyle(cellStyle);
			
			//AK_YN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getAkYn()));
			cell.setCellStyle(cellStyle);
			
			//FK_YN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getFkYn()));
			cell.setCellStyle(cellStyle);
			
			//공백
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(""));
			cell.setCellStyle(cellStyle);
			
			//공백
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(""));
			cell.setCellStyle(cellStyle);
			
			//ENC_YN
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(srchVo.getEncYn()));
			cell.setCellStyle(cellStyle);
			
			//공백
			cell = row.createCell(iCell++);
			cell.setCellValue(UtilString.null2Blank(""));
			cell.setCellStyle(cellStyle);
			
			strRow++;
			no++;
		}
		
		return workbook;
	}
}
