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
package kr.wise.meta.ldm.web;

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

import kr.wise.meta.ldm.service.WamLdmAttr;
import kr.wise.meta.ldm.service.ModelLdmService;
import kr.wise.meta.ldm.service.WamLdmEnty;
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
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 22.
 * </PRE>
 */
@Controller("ModelLdmCtrl")
public class ModelLdmCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private ModelLdmService modelLdmService;

	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;

	static class WaaSubjs extends HashMap<String, ArrayList<WaaSubj>> {}

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
		
		
		return codeMap;
	}


	@RequestMapping("/meta/ldm/popup/ldmEntySearch_pop.do")
	public String goPdmTblPop(@ModelAttribute("search") WamLdmEnty search, Model model, Locale locale) {
		logger.debug("{}", search);


		return "/meta/ldm/popup/ldmEntySearch_pop";
	}
	
	@RequestMapping("/meta/ldm/popup/pdmcol_pop.do")
	public String goPdmColPop(@ModelAttribute("search") WamLdmEnty search, Model model, Locale locale) {

		logger.debug("{}", search);
		return "/meta/ldm/popup/pdmcol_pop";
	}


	@RequestMapping("/meta/ldm/ldmenty_lst.do")
	public String goPdmTblList(@ModelAttribute("search") WamLdmEnty search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		//주제영역 조회에서 더블클릭으로 subjLnm를 받아오는 경우가 있어서 || 뒤의 조건 추가함...(김연호)
		if(!UtilString.isBlank(search.getLdmEntyLnm()) || !UtilString.isBlank(search.getSubjLnm())) {
			model.addAttribute("search" , search);

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/meta/ldm/subjSearchPop");

//		return mv;

		return "/meta/ldm/ldmenty_lst";
	}

	@RequestMapping("/meta/ldm/pdmtblhist_lst.do")
	public String goPdmTblHist(@ModelAttribute WamLdmEnty search, Model model, Locale locale) {
		logger.debug("{}", search);

		if(!UtilString.isBlank(search.getLdmEntyLnm())) {
			model.addAttribute("search" , search);

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/meta/ldm/subjSearchPop");

//		return mv;

		return "/meta/ldm/pdmtblhist_lst";
	}

	@RequestMapping("/meta/ldm/pdmtbl_ifm.do")
	public String goPdmTblTop30(@ModelAttribute WamLdmEnty search, Model model, Locale locale) {

		return "/meta/ldm/pdmTbl_ifm";
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
	@RequestMapping("/meta/ldm/getLdmEntyList.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getLdmEntyList(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getLdmEntyList(search); 

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

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
	@RequestMapping("/meta/ldm/getLdmAttrList.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getLdmAttrList(@ModelAttribute WamLdmAttr search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.getLdmAttrList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}
	
	@RequestMapping("/meta/ldm/getgappdmcollist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getGapPdmCollist(@ModelAttribute WamLdmAttr search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.getGapPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}
	
	/** @param search
	/** @param locale
	/** @return meta */
	@RequestMapping("/meta/ldm/selectPdmColList.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> selectPdmColList(@ModelAttribute WamLdmAttr search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.selectPdmColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}

	@RequestMapping("/meta/ldm/getcoldtllist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getColDtllist(@ModelAttribute WamLdmAttr search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.getPdmColDtlList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}

	@RequestMapping("/meta/ldm/getHistAttrList.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getColHistlist(@ModelAttribute WamLdmEnty search, Locale locale) {
		logger.debug("searchVO:{}", search);

		List<WamLdmAttr> list = modelLdmService.getLdmAttrHistList(search);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}
	

	@RequestMapping("/meta/ldm/getstathistbllist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getStatHisTbllist(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getPdmStatHisTblList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

	}

	
	//테이블이력 조회(tblhist_dtl.jsp)
	@RequestMapping("/meta/ldm/getHistEntyList.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getHistEntyList(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getLdmEntyHistList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

	}

	@RequestMapping("/meta/ldm/getidxcollist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getPdmIdxCollist(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getPdmIdxColList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

	}

	@RequestMapping("/meta/ldm/getpdmtblhist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getPdmTbLhist(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getPdmTblHist(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

	}


	@RequestMapping("/meta/ldm/getpdmtblTop30.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getPdmTbLTop30(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getPdmTblTop30(search);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

	}
	
	
	/** DDL 테이블 추가를 위한 물리모델 조회 */
	@RequestMapping("/meta/ldm/getPdmTblListForDdl.do")
	@ResponseBody
	public IBSheetListVO<WamLdmEnty> getPdmTbLlistForDdl(@ModelAttribute WamLdmEnty search, Locale locale) {

		logger.debug("{}", search);
		List<WamLdmEnty> list = modelLdmService.getPdmTblListForDdl(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmEnty>(list, list.size());

	}
	
	@RequestMapping("/meta/ldm/pdmtblcol_lst.do")
	public String goPdmTblColList(@ModelAttribute("search") WamLdmEnty search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		//주제영역 조회에서 더블클릭으로 subjLnm를 받아오는 경우가 있어서 || 뒤의 조건 추가함...(김연호)
		if(!UtilString.isBlank(search.getLdmEntyLnm()) || !UtilString.isBlank(search.getSubjLnm())) {
			model.addAttribute("search" , search);

		}
		return "/meta/ldm/pdmTblcol_lst";
	}

	@RequestMapping("/meta/ldm/gethisttblcollist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getTblColHistlist(@ModelAttribute WamLdmAttr search, Locale locale) {
		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.getPdmColHistListDtl(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}
	
	@RequestMapping("/meta/ldm/getpdmcolchglist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getPdmColChgList(@ModelAttribute WamLdmAttr search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.getPdmColChgList(search);


		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}
	
	@RequestMapping("/meta/ldm/colnonstnd_lst.do")
	public String goColNonStndList(@ModelAttribute("search") WamLdmEnty search, Model model, Locale locale,String linkFlag) {
		logger.debug("{}", search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);

		
		return "/meta/ldm/colnonstnd_lst";
	}

	//비표준 컬럼분석 조회
	@RequestMapping("/meta/ldm/getcolnonstndlist.do")
	@ResponseBody
	public IBSheetListVO<WamLdmAttr> getColNonStndlist(@ModelAttribute WamLdmAttr search, Locale locale) {

		logger.debug("searchVO:{}", search);
		List<WamLdmAttr> list = modelLdmService.getColNonStndlist(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamLdmAttr>(list, list.size());

	}
}
