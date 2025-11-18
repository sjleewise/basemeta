/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : TextMatchView.java
 * 2. Package : kr.wise.advisor.prepare.textcluster.web
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 8. 오후 4:20:56
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 8. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.textcluster.web;

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

import kr.wise.advisor.prepare.outlier.service.OutlierService;
import kr.wise.advisor.prepare.summary.service.SummaryService;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : TextMatchView.java
 * 3. Package  : kr.wise.advisor.prepare.textcluster.web
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 8. 오후 4:20:56
 * </PRE>
 */
@Controller
public class TextMatchView {

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
		
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);
		codeMap.put("connTrgDbmsCd", connTrgDbms);
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch); 
		
		//스키마
		List<CodeListVo> schDbSchNm = codelistService.getCodeList(CodeListAction.connTrgSch);
		codeMap.put("schDbSchNm", UtilJson.convertJsonString(schDbSchNm));
		
		//OD 알고리즘 코드
//		List<CodeListVo> otlAlgCd = codelistService.getCodeList("OTL_ALG_ID");
//		codeMap.put("otlAlgCd", otlAlgCd);
		
		//알고리즘 유형
//		codeMap.put("algTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ALG_TYP_CD")));
		
		return codeMap;
	}
	
	/** 데이터매칭 요청화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/textcluster/textmatch_rqst.do")
	public String goTextMatch(@ModelAttribute("search") CommonVo search) {
		logger.debug("데이터매칭 요청화면:{}", search);
		
		return "/advisor/prepare/textcluster/textmatch_rqst";
	}

	/** 데이터매칭 추가 팝업 
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/textcluster/popup/textmatchtblcol_pop.do")
	public String goPopTextMatch(@ModelAttribute("dataMtcTblVO") WadDataMtcTbl search, ModelMap model) {
		logger.debug("데이터매칭 추가 팝업:{}", search); 
		 
		return "/advisor/prepare/textcluster/popup/textmatchtblcol_pop";   
	}

	/** 텍스트클러스터 요청화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/textcluster/textclust_rqst.do")
	public String goTextCluster(@ModelAttribute("search") CommonVo search) {
		logger.debug("텍스트클러스터 요청화면:{}", search);
		
		return "/advisor/prepare/textcluster/textclust_rqst";
	}
}
