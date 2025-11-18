package kr.wise.advisor.prepare.summary.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.domain.service.DomainPredictService;
import kr.wise.advisor.prepare.summary.service.SummaryService;
import kr.wise.advisor.prepare.summary.service.WadVarSum;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.util.UtilJson;



/**
 * <PRE>
 * 1. ClassName : SummaryDsView
 * 2. FileName  : SummaryDsView.java
 * 3. Package  : kr.wise.advisor.prepare.summary.web
 * 4. Comment  : 데이터셋 탐색에 이용되는 뷰 컨트롤러....
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 15. 오후 1:27:34
 * </PRE>
 */ 
@Controller
public class SummaryDsView { 
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private SummaryService summaryService;
	
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
				
		return codeMap;
	}
	
	
	/** 데이터셋 summary 조회화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/summary/summaryds_lst.do")
	public String goSummaryDataSet(@ModelAttribute("search") CommonVo search) {
		logger.debug("데이터셋 summary 조회화면:{}", search);
		
		return "/advisor/prepare/summary/summaryds_lst";
	}

	@RequestMapping("/advisor/prepare/summary/getsummary_dtl.do")
	public String getSummaryDtl(@ModelAttribute("search") WadAnaVar search, Model model) {
		logger.debug("컬럼(변수) summary 상세정보:[{}]", search.getAnlVarId());
		
		WadVarSum result = summaryService.getSummaryDtl(search);
		
		model.addAttribute("summaryresVo", result);
		
		return "/advisor/prepare/summary/summary_dtl";
	}
	
//	/advisor/prepare/summary/regSummaryDataset.do
	
}
