package kr.wise.dq.report.vrfcrule.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.report.vrfcrule.service.VrfcruleProgChartVO;
import kr.wise.dq.report.vrfcrule.service.VrfcruleProgService;

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
import org.springframework.web.servlet.ModelAndView;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProfileProgCtrl.java
 * 3. Package  : kr.wise.dq.report.profile.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 11. 오전 9:48:36
 * </PRE>
 */ 
@Controller
public class VrfcruleProgCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private MessageSource message;

	@Inject
	private VrfcruleProgService vrfcruleProgService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 검증룰 품질추이 폼 */
	@RequestMapping("/dq/report/vrfcrule/vrfcruleprog_lst.do")
	public String formPage() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/vrfcrule/vrfcruleprog_lst";
	}
	
	/** 검증룰 품질추이 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/vrfcrule/getVrfcruleProgQuality.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> selectList(@ModelAttribute WamPrfMstrCommonVO search) {
		logger.debug("{}", search);
		//ANA_DGR 분석차수가 NULL이 아닌경우만 나오게  
		List<WamPrfMstrCommonVO> list = vrfcruleProgService.getVrfcruleProgQuality(search);
		
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());
	}
	
	/** 진단대상별 품질현황 폼 */
	@RequestMapping("/dq/report/vrfcrule/vrfcruletot_lst.do")
	public String govrfcruleTot(Model model) throws Exception {
		model.addAttribute("codeMap", getcodeMap());
		return "/dq/report/vrfcrule/vrfcruletot_lst";
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//진단대상
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);

		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
//		String connTrgSchId   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
//		codeMap.put("connTrgSchId", connTrgSchId);
		
		//프로파일 종류코드
		codeMap.put("prfKndCd",cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
		
		//실행차수
		List<CodeListVo> anaDgr = codeListService.getCodeList(CodeListAction.anaDgr);
		codeMap.put("anaDgrCd", anaDgr);
		//검증룰 종류코드
		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
		return codeMap;
	}
	
}
