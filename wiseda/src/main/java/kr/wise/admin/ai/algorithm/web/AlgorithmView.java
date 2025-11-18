/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : AlgorithmView.java
 * 2. Package : kr.wise.admin.ai.algorithm.web
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 23. 오후 2:13:17
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 23. :            : 신규 개발.
 */
package kr.wise.admin.ai.algorithm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.util.UtilJson;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AlgorithmView.java
 * 3. Package  : kr.wise.admin.ai.algorithm.web
 * 4. Comment  : 알고리즘 관리 뷰 컨트롤러... 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 23. 오후 2:13:17
 * </PRE>
 */
@Controller
public class AlgorithmView {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		codeMap.put("algTypCd", cmcdCodeService.getCodeList("ALG_TYP_CD"));
		codeMap.put("algTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ALG_TYP_CD")));
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		
		return codeMap;
	}
	
	@RequestMapping("/admin/ai/algorithm/algorithm_lst.do")
	public String goAlgorithmMgmt(@ModelAttribute SearchVO search) {
		logger.debug("알고리즘 관리화면으로 이동");
		
		
		return "/admin/ai/algorithm/algorithm_lst";
	}

}
