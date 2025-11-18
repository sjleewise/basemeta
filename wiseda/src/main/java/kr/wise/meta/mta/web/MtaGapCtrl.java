/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaGapCtrl.java
 * 2. Package : kr.wise.meta.mta.web
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.08.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.08. :            : 신규 개발.
 */
package kr.wise.meta.mta.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.mta.service.MtaGapService;
import kr.wise.meta.mta.service.MtaGapVO;

@Controller("MtaGapCtrl")
public class MtaGapCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource messageSource;
	
	@Inject
	private CmcdCodeService cmcdCodeService; 
	
	@Inject
	private CodeListService codeListService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MtaGapService mtaGapService;
	
	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		codeMap.put("connTrgSch", UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2")));
		codeMap.put("connTrgSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("connTrgSchId2")));
		
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId2"));
		codeMap.put("devConnTrgSch", devConnTrgSch);

		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		//시스템영역 코드리스트 JSON
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
		String sysarea = UtilJson.convertJsonString(sysareatmp);
		String sysareaibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));
		
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs); 

        codeMap.put("orgCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("orgCd")));
		codeMap.put("orgCd", codeListService.getCodeList("orgCd"));
		
		codeMap.put("infoSysCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("infoSysCd")));
		codeMap.put("infoSysCd", codeListService.getCodeList("infoSysCd"));

		return codeMap;
	}
	
	
	/** 메타데이터 GAP 분석 조회 페이지 호출 */
	@RequestMapping("/meta/mta/mtagap_lst.do")
	public String goMtaGapLst() {
		
		return "/meta/mta/mtagap_lst";
	}
	
	/** GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/mta/getMtaGapList.do")
	@ResponseBody
	public IBSheetListVO<MtaGapVO> getMtaGapList(@ModelAttribute MtaGapVO search) {
		logger.debug("{}", search);
		List<MtaGapVO> list = mtaGapService.getMtaGapAnalyze(search);
		
		return new IBSheetListVO<MtaGapVO>(list, list.size());
	}
	
	/** 메타데이터GAP분석 컬럼 목록 조회 - IBSheet JSON */
	@RequestMapping("/meta/mta/getMtaColGapList.do")
	@ResponseBody
	public IBSheetListVO<MtaGapVO> getMtaColGapList(@ModelAttribute("searchVO") MtaGapVO search, String tblNm) {

		List<MtaGapVO> list = mtaGapService.getMtaColGapList(search);

		return new IBSheetListVO<MtaGapVO>(list, list.size());
	}
	
	@RequestMapping("/meta/mta/popup/mtagaptbl_pop.do")
	public String goMtaGapTblPop(MtaGapVO search, ModelMap model) {
		
		model.addAttribute("search", search);
		
		return "/meta/mta/popup/mtagaptbl_pop";
	}
}
