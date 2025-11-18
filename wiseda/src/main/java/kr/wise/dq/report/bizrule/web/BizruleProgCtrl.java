package kr.wise.dq.report.bizrule.web;

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
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.report.bizrule.service.BizruleProgService;
import kr.wise.dq.report.profile.service.ProgChartVO;

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

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleProgCtrl.java
 * 3. Package  : kr.wise.dq.report.bizrule.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 11. 오전 9:48:53
 * </PRE>
 */ 
@Controller
public class BizruleProgCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private MessageSource message;

	@Inject
	private BizruleProgService bizruleProgService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 업무규칙 품질추이 폼 */
	@RequestMapping("/dq/report/bizrule/bizruleprog_lst.do")
	public String formPage() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/bizrule/bizruleprog_lst";
	}
	
	/** 업무영역별 품질추이 폼 */
	@RequestMapping("/dq/report/bizrule/bizareaprog_lst.do")
	public String formPageBizArea() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/bizrule/bizareaprog_lst";
	}
	
	/** 진단대상별 품질현황 폼 */
	@RequestMapping("/dq/report/bizrule/bizrule_tab.do")
	public String goBizruleTab(Model model) throws Exception {
		model.addAttribute("codeMap", getcodeMap());
		return "/dq/report/bizrule/bizrule_tab";
	}
	
	/** dqi별 품질추이 폼 */
	@RequestMapping("/dq/report/bizrule/dqiprog_lst.do")
	public String formPageDqi() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/bizrule/dqiprog_lst";
	}
	
	/** dqi별 품질추이 폼 */
	@RequestMapping("/dq/report/bizrule/dqiquality_lst.do")
	public String formPageDqiQuality() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/bizrule/dqiquality_lst";
	}
	
	/** ctq별 품질추이 폼 */
	@RequestMapping("/dq/report/bizrule/ctqprog_lst.do")
	public String formPageCtq() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/bizrule/ctqprog_lst";
	}
	
	/** 업무규칙 품질추이 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/bizrule/getBizruleProgQuality.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getBizruleProgQuality(@ModelAttribute WamBrMstr search) {
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleProgService.getBizruleProgQuality(search);
		
		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}
	
	/** 업무영역별 품질현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/bizrule/getBizareaProgQuality.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getBizareaProgQuality(@ModelAttribute WamBrMstr search) {
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleProgService.getBizareaProgQuality(search);
		
		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}
	
	/** DQI별 품질현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/bizrule/getDqiProgQuality.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getDqiProgQuality(@ModelAttribute WamBrMstr search) {
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleProgService.getDqiProgQuality(search);
		
		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}
	
	/** DQI별 품질현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/bizrule/getDqiQuality.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getDqiQuality(@ModelAttribute WamBrMstr search) {
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleProgService.getDqiQuality(search);
		
		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}
	
	/** CTQ별 품질현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/bizrule/getCtqProgQuality.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getCtqProgQuality(@ModelAttribute WamBrMstr search) {
		logger.debug("{}", search);
		List<WamBrMstr> list = bizruleProgService.getCtqProgQuality(search);
		
		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}
	
	/** 진단대상별 품질현황 폼 */
	@RequestMapping("/dq/report/bizrule/bizrulequality_lst.do")
	public String formPageQuality() throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/report/bizrule/bizrulequality_lst";
	}
	
	/** 진단대상별 품질현황 조회 - IBSheet JSON */
	@RequestMapping("/dq/report/bizrule/getBizruleQuality.do")
	@ResponseBody
	public IBSheetListVO<ProgChartVO> getBizruleQuality(@ModelAttribute ProgChartVO search) {
		logger.debug("{}", search);
		List<ProgChartVO> list = bizruleProgService.getBizruleQuality(search);
		
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
