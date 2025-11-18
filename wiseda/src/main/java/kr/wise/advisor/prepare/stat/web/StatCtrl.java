package kr.wise.advisor.prepare.stat.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.stat.service.StatService;
import kr.wise.advisor.prepare.stat.service.StatVo;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatCtrl {
private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private StatService statService;
	
	private Map<String, Object> codeMap;
	
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
		List<CodeListVo> schDbSchNm = codelistService.getCodeList(CodeListAction.connTrgSch);
		codeMap.put("schDbSchNm", UtilJson.convertJsonString(schDbSchNm));
		
		return codeMap;
	}
	
	/** bdq 통계 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/stat_rqst.do")
	public String goStatRqst(@ModelAttribute("search") CommonVo search) {
		logger.debug("bdq 통계 조회화면:{}", search);
		
		return "/advisor/prepare/stat/stat_rqst";
	}
	
	/** bdq 통계 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/getProfStatList.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getProfStatList(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getProfStatList:{}", search);
		
		List<StatVo> profList =  statService.getProfStatList(search);
		
		return new IBSheetListVO<StatVo>(profList,profList.size());
	}
	
	/** bdq 통계 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/getBrStatList.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getBrStatList(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getBrStatList:{}", search);
		
		List<StatVo> brList =  statService.getBrStatList(search);
		
		return new IBSheetListVO<StatVo>(brList, brList.size());
	}
	
	/** bdq 통계 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/getOtlStatList.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getOtlStatList(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getOtlStatList:{}", search);
		
		List<StatVo> otlList =  statService.getOtlStatList(search);
		
		return new IBSheetListVO<StatVo>(otlList, otlList.size());
	}
	
	/** bdq 통계 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/getClstStatList.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getClstStatList(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getClstStatList:{}", search);
		
		List<StatVo> clstList =  statService.getClstStatList(search);
		
		return new IBSheetListVO<StatVo>(clstList, clstList.size());
	}
	
	/** bdq 통계 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/getMtchStatList.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getMtchStatList(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getMtchStatList:{}", search);
		
		List<StatVo> mtchList =  statService.getMtchStatList(search);
		
		return new IBSheetListVO<StatVo>(mtchList, mtchList.size());
	}
	
	/** bdq 통계 화면 popup	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/prof_pop.do")
	public String getProfStatPop(@ModelAttribute("search") StatVo search, Model model, Locale locale) throws Exception {
		logger.debug("bdq 통계 getOtlStatPop:{}", search);
		
		model.addAttribute("type", "prof");
		model.addAttribute("search", search);
		
		return "/advisor/prepare/stat/popup/stat_pop";
	}
	
	/** bdq 통계 화면 popup	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/br_pop.do")
	public String getBrStatPop(@ModelAttribute("search") StatVo search, Model model, Locale locale) {
		logger.debug("bdq 통계 getOtlStatPop:{}", search);
		
		model.addAttribute("type", "br");
		model.addAttribute("search", search);
		
		return "/advisor/prepare/stat/popup/stat_pop";
	}
	
	/** bdq 통계 화면 popup  */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/otl_pop.do")
	public String getOtlStatPop(@ModelAttribute("search") StatVo search, Model model, Locale locale) {
		logger.debug("bdq 통계 getOtlStatPop:{}", search);
		
		model.addAttribute("type", "otl");
		model.addAttribute("search", search);
		
		return "/advisor/prepare/stat/popup/stat_pop";
	}
	
	/** bdq 통계 화면 popup  */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/clst_pop.do")
	public String getClstStatPop(@ModelAttribute("search") StatVo search, Model model, Locale locale) {
		logger.debug("bdq 통계 getClstStatPop:{}", search);
		
		model.addAttribute("type", "clst");
		model.addAttribute("search", search);
		
		return "/advisor/prepare/stat/popup/stat_pop";
	}
	
	/** bdq 통계 화면 popup  */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/mtch_pop.do")
	public String getMtchStatPop(@ModelAttribute("search") StatVo search, Model model, Locale locale) {
		logger.debug("bdq 통계 getMtchStatPop:{}", search);
		
		model.addAttribute("type", "mtch");
		model.addAttribute("search", search);
		
		return "/advisor/prepare/stat/popup/stat_pop";
	}
	
	/** bdq 통계 화면 popup	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/prof_dtl.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getProfStatDtl(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getProfStatDtl:{}", search);
		logger.debug("bdq 통계 getProfStatDtl prfId:{}", search.getPrfId());
		
		List<StatVo> otlList =  statService.getProfStatDtl(search);
		
		return new IBSheetListVO<StatVo>(otlList, otlList.size());
	}
	
	/** bdq 통계 화면 popup	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/br_dtl.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getBrStatDtl(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getBrStatDtl:{}", search);
		logger.debug("bdq 통계 getProfStatDtl brId:{}", search.getBrId());
		
		List<StatVo> otlList =  statService.getBrStatDtl(search);
		
		return new IBSheetListVO<StatVo>(otlList, otlList.size());
	}
	
	/** bdq 통계 화면 popup  */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/otl_dtl.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getOtlStatDtl(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getOtlStatDtl:{}", search);
		logger.debug("bdq 통계 getProfStatDtl otlId:{}", search.getOtlDtcId());
		
		List<StatVo> otlList =  statService.getOtlStatDtl(search);
		
		return new IBSheetListVO<StatVo>(otlList, otlList.size());
	}
	/** bdq 통계 화면 popup  */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/clst_dtl.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getClstStatDtl(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getClstStatDtl:{}", search);
		logger.debug("bdq 통계 getClstStatDtl mtcId:{}", search.getMtcId());
		
		List<StatVo> clstList =  statService.getClstStatDtl(search);
		
		return new IBSheetListVO<StatVo>(clstList, clstList.size());
	}
	
	/** bdq 통계 화면 popup  */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/stat/popup/mtch_dtl.do")
	@ResponseBody
	public IBSheetListVO<StatVo> getMtchStatDtl(@ModelAttribute("search") StatVo search) {
		logger.debug("bdq 통계 getMtchStatDtl:{}", search);
		logger.debug("bdq 통계 getMtchStatDtl mtcId:{}", search.getMtcId());
		
		List<StatVo> mtchList =  statService.getMtchStatDtl(search);
		
		return new IBSheetListVO<StatVo>(mtchList, mtchList.size());
	}
}
