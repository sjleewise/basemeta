package kr.wise.advisor.prepare.domain.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.DataSetNstndService;
import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarNstnd;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.util.UtilJson;

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



/**
 * <PRE>
 * 1. ClassName : DomainPredictView
 * 2. FileName  : DomainPredictView.java
 * 3. Package  : kr.wise.advisor.prepare.domain.web
 * 4. Comment  : 도메인 판별 뷰 컨트롤러로 화면처리 및 공통코드 처리를 처리한다.
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 15. 오후 1:27:34
 * </PRE>
 */ 
@Controller
public class DomainPredictView { 
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private DataSetService dataSetService;
	
	@Inject
	private DataSetNstndService dataSetNstndService;
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
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
		codeMap.put("connTrgDbmsCd", connTrgDbms);
				
		//스키마
//		List<CodeListVo> schDbSchNm = codelistService.getCodeList(CodeListAction.connTrgSch);
//		codeMap.put("schDbSchNm", UtilJson.convertJsonString(schDbSchNm));
		
		//진단대상, 스키마 Double Select
		String connTrgSch = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("schDbSchId", connTrgSch);
		
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		
		return codeMap;
	}
	
	/** 도메인 판별 요청 화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/domain/dmnPredict_rqst.do")
	public String goDomainPredict(@ModelAttribute SearchVO search) {
		logger.debug("도메인 판별 요청화면");
		
		
		return "/advisor/prepare/domain/dmnPredict_rqst";
	}
	/** 도메인 판별 결과 화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/domain/dmnclass_rqst.do")
	public String goDmnClass(@ModelAttribute("search") CommonVo search) {
		logger.debug("도메인 판별 요청:[{}]", search.getRqstNo());
		
		
		return "/advisor/prepare/domain/dmnclass_rqst";
	}
	
	/** 비표준 도메인 판별 결과 화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/domain/dmnclass_nstnd_rqst.do")
	public String goDmnClassNstnd(@ModelAttribute("search") CommonVo search) {
		logger.debug("도메인 판별 요청:[{}]", search.getRqstNo());
		
		return "/advisor/prepare/domain/dmnclass_nstnd_rqst";
	}
	
	/** 도메인 판별 결과 화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/domain/dmnpredict_lst.do")
	public String goDmnPredictResult(@ModelAttribute("search") CommonVo search) {
		logger.debug("도메인 판별 결과화면:[{}]", search.getRqstNo());
		
		
		return "/advisor/prepare/domain/dmnpredict_lst";
	}

	@RequestMapping("/advisor/prepare/domain/getdmnfeature_dtl.do")
	public String getDmnFeatureDtl(@ModelAttribute("search") WadAnaVar search, Model model) {
		logger.debug("도메인판별 파생변수 상세정보:[{}]", search.getAnlVarId());
		
		WadAnaVar result = dataSetService.getAnlVarDtl(search);
		
		model.addAttribute("dmnfeatureVo", result);
		
		return "/advisor/prepare/domain/dmnfeature_dtl";
	}
	
	@RequestMapping("/advisor/prepare/domain/getnstnddmnfeature_dtl.do")
	public String getNstndDmnFeatureDtl(@ModelAttribute("search") WadAnaVarNstnd search, Model model) {
		logger.debug("도메인판별 파생변수 상세정보:[{}]", search.getAnlVarId());
		
		WadAnaVarNstnd result = dataSetNstndService.getAnlVarNstndDtl(search);
		
		model.addAttribute("nstnddmnfeatureVo", result);
		
		return "/advisor/prepare/domain/nstnddmnfeature_dtl";
	}
	
}
