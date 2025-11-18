/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelPdmCtrl.java
 * 2. Package : kr.wise.meta.model.controller
 * 3. Comment : 물리모델 컨트롤러...
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    	:  		: 신규 개발.
 */
package kr.wise.meta.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.model.service.AsisModelPdmService;
import kr.wise.meta.model.service.ModelPdmService;
import kr.wise.meta.model.service.WamPdmCol;
import kr.wise.meta.model.service.WamPdmRel;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.subjarea.service.WaaSubj;

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
import org.springframework.jndi.JndiObjectFactoryBean;
/**
 * <PRE>
 * 1. ClassName : ModelPdmCtrl
 * 2. Package  : kr.wise.meta.model.controller
 * 3. Comment  :
 * 4. 작성자   : 
 * 5. 작성일   : 
 * </PRE>
 */
@Controller
@RequestMapping("/meta/model/*")
public class AsisModelPdmCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private AsisModelPdmService asisModelPdmService;

	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;

	static class WaaSubjs extends HashMap<String, ArrayList<WaaSubj>> {}

	/**
	 * <PRE>
	 * 1. MethodName : initBinder
	 * 2. Comment    : 폼 데이터 바인딩시 value 값이 ""인 경우 Vo에 NULL로 바인딩 한다...
	 * 3. 작성자       : 
	 * 4. 작성일       :
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
	 * 3. 작성자       : 
	 * 4. 작성일       : 
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
		
		
		return codeMap;
	}


	@RequestMapping("popup/asispdmtblSearchPop.do")
	public String goAsisPdmTblPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);
		
//		if(!UtilString.isBlank(search.getPdmTblLnm())) {
//			model.addAttribute("pdmTblLnm" , search.getPdmTblLnm());
//
////			mv.addObject("subjLnm", search.getSubjLnm());
//		}
//		mv.setViewName("/meta/model/subjSearchPop");
		
//		return mv;
		
		return "/meta/model/popup/asispdmTblSearch_pop";
	}
	
	@RequestMapping("popup/asispdmcol_pop.do")
	public String goPdmColPop(@ModelAttribute("search") WamPdmCol search, Model model, Locale locale) {

		logger.debug("{}", search);
		return "/meta/model/popup/asispdmcol_pop";
	}


	@RequestMapping("/asispdmtbl_lst.do")
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

		return "/meta/model/asispdmTbl_lst";
	}

	@RequestMapping("/asispdmtblhist_lst.do")
	public String goPdmTblHist(@ModelAttribute WamPdmTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

		if(!UtilString.isBlank(search.getPdmTblLnm())) {
			model.addAttribute("search" , search);

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/meta/model/subjSearchPop");

//		return mv;

		return "/meta/model/asispdmtblhist_lst";
	}

	@RequestMapping("/asispdmtbl_ifm.do")
	public String goPdmTblTop30(@ModelAttribute WamPdmTbl search, Model model, Locale locale) {

		return "/meta/model/asispdmTbl_ifm";
	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmCollist
	 * 2. Comment    : 컬럼리스트 검색...
	 * 3. 작성자       : 
	 * 4. 작성일       : 
	 * </PRE>
	 *   @return IBSJsonSearch
	 *   @param search
	 *   @param locale
	 *   @return
	 */
	@RequestMapping("getasispdmcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getPdmCollist(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.getPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("getasisgappdmcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getGapPdmCollist(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.getGapPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("selectasisPdmColList.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> selectPdmColList(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.selectPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}

	@RequestMapping("getasiscoldtllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getColDtllist(@ModelAttribute WamPdmCol search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.getPdmColDtlList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}

	@RequestMapping("getasishistcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getColHistlist(@ModelAttribute WamPdmTbl search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.getPdmColHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	

	@RequestMapping("getasisstathistbllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getStatHisTbllist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getPdmStatHisTblList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmTbLlist
	 * 2. Comment    : 테이블 리스트 검새...
	 * 3. 작성자       : 
	 * 4. 작성일       : 
	 * </PRE>
	 *   @return IBSJsonSearch
	 *   @param search
	 *   @param locale
	 *   @return
	 */
	
	@RequestMapping("getasispdmtbllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getasisPdmTbLlist(@ModelAttribute WamPdmTbl search, Locale locale) {
		
		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getAsisPdmTblList(search);
		
//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);
		
		return new IBSheetListVO<WamPdmTbl>(list, list.size());
		
	}

	//테이블이력 조회(tblhist_dtl.jsp)
	@RequestMapping("getasishistbllist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLHistlist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getPdmTblHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}

	@RequestMapping("getasisidxcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmIdxCollist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getPdmIdxColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}

	@RequestMapping("getasispdmtblhist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLhist(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getPdmTblHist(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}


	@RequestMapping("getasispdmtblTop30.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLTop30(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getPdmTblTop30(search);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}
	
	/** 물리모델 관계 조회  */
	@RequestMapping("getasispdmrellist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelList(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = asisModelPdmService.getPdmRelList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** 물리모델 관계 이력조회  */
	@RequestMapping("getasispdmrelhistlist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelHistList(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = asisModelPdmService.getPdmRelHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** 물리모델 관계 조회(이력페이지)  */
	@RequestMapping("getasispdmrellistHistPage.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelListHistPage(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = asisModelPdmService.getPdmRelListHistPage(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** 물리모델 관계 이력조회(이력페이지)  */
	@RequestMapping("getasispdmrelhistlistHistPage.do")
	@ResponseBody
	public IBSheetListVO<WamPdmRel> getPdmRelHistListHistPage(@ModelAttribute WamPdmRel search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmRel> list = asisModelPdmService.getPdmRelHistListHistPage(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmRel>(list, list.size());

	}
	
	/** DDL 테이블 추가를 위한 물리모델 조회 */
	@RequestMapping("getasisPdmTblListForDdl.do")
	@ResponseBody
	public IBSheetListVO<WamPdmTbl> getPdmTbLlistForDdl(@ModelAttribute WamPdmTbl search, Locale locale) {

		logger.debug("{}", search);
		List<WamPdmTbl> list = asisModelPdmService.getPdmTblListForDdl(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmTbl>(list, list.size());

	}
	
	@RequestMapping("/asispdmtblcol_lst.do")
	public String goPdmTblColList(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		//주제영역 조회에서 더블클릭으로 subjLnm를 받아오는 경우가 있어서 || 뒤의 조건 추가함...(김연호)
		if(!UtilString.isBlank(search.getPdmTblLnm()) || !UtilString.isBlank(search.getSubjLnm())) {
			model.addAttribute("search" , search);

		}
		return "/meta/model/asispdmTblcol_lst";
	}

	@RequestMapping("getasishisttblcollist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getTblColHistlist(@ModelAttribute WamPdmCol search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.getPdmColHistListDtl(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
	
	@RequestMapping("getasispdmcolchglist.do")
	@ResponseBody
	public IBSheetListVO<WamPdmCol> getPdmColChgList(@ModelAttribute WamPdmCol search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamPdmCol> list = asisModelPdmService.getPdmColChgList(search);


		return new IBSheetListVO<WamPdmCol>(list, list.size());

	}
}
