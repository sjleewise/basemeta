package kr.wise.dq.report.impv.web;

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
import kr.wise.dq.report.impv.service.ImRslProgService;
import kr.wise.dq.report.profile.service.ProgChartVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ImRslProgCtrl.java
 * 3. Package  : kr.wise.dq.report.impv.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 17. 오후 5:01:03
 * </PRE>
 */ 
@Controller
public class ImRslProgCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private MessageSource message;

	@Inject
	private ImRslProgService imRslProgService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 개선결과 현황 폼 */
	@RequestMapping("/dq/report/impv/imrslprog_lst.do")
	public String formPage() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/impv/imrslprog_lst";
	}
	
	/** 개선결과 현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/impv/getImRslProgQuality.do")
	@ResponseBody
	public IBSheetListVO<ProgChartVO> getImRslProgQuality(@ModelAttribute ProgChartVO search) {
		logger.debug("{}", search);
		List<ProgChartVO> list = imRslProgService.getImRslProgQuality(search);
		
		return new IBSheetListVO<ProgChartVO>(list, list.size());
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
		
		//실행차수
		List<CodeListVo> anaDgr = codeListService.getCodeList(CodeListAction.anaDgr);
		codeMap.put("anaDgrCd", anaDgr);
		List<CodeListVo> brAnaDgr = codeListService.getCodeList(CodeListAction.bizrAnaDgr);
		codeMap.put("brAnaDgrCd", brAnaDgr);
		
		return codeMap;
	}
	
}
