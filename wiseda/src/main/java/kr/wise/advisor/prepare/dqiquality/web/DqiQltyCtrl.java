package kr.wise.advisor.prepare.dqiquality.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dqiquality.service.DqiQltyService;
import kr.wise.advisor.prepare.dqiquality.service.DqiQltyVo;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

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

@Controller
public class DqiQltyCtrl {
private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private DqiQltyService dqiQltyService;
	
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
		List<CodeListVo> schDbSchId = codelistService.getCodeList(CodeListAction.connTrgSchId);
		codeMap.put("dbSchId", UtilJson.convertJsonString(schDbSchId));
		
		//OD 알고리즘 코드
		List<CodeListVo> otlAlgCd = codelistService.getCodeList("OTL_ALG_ID");
		codeMap.put("otlAlgCd", otlAlgCd);
		
		List<CodeListVo> dqiCd = codelistService.getCodeList("OTL_DQI_MAP");
		codeMap.put("dqiCd", dqiCd);
		
		return codeMap;
	}
	
	/** bdq dqi 품질현황 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/dqiquality/dqiqlty_rqst.do")
	public String goDqiqualityRqst(@ModelAttribute("search") CommonVo search) {
		logger.debug("bdq dqi 품질현황 화면:{}", search);
		
		return "/advisor/prepare/dqiquality/dqiqlty_rqst";
	}
	
	/** bdq dqi 품질현황 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/dqiquality/getDqiQltyList.do")
	@ResponseBody
	public IBSheetListVO<DqiQltyVo> getDqiQltyList(DqiQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyList:{}", search);
		
		List<DqiQltyVo> dqiQltyList =  dqiQltyService.getDqiQltyList(search);
		
		return new IBSheetListVO<DqiQltyVo>(dqiQltyList,dqiQltyList.size());
	}
	
	/** bdq dqi 품질현황 화면 - bar chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/dqiquality/getDqiBarData.do")
	@ResponseBody
	public List<DqiQltyVo> getDqiQltyBar(DqiQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDqiQltyBar:{}", search);
		
		List<DqiQltyVo> dqiQltyBar =  dqiQltyService.getDqiQltyBar(search);
		
		return dqiQltyBar;
	}
	
	/** bdq dqi 품질현황 화면 - pie chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/dqiquality/getDqiPieData.do")
	@ResponseBody
	public List<DqiQltyVo> getDqiQltyPie(DqiQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDqiQltyPie:{}", search);
		
		List<DqiQltyVo> dqiQltyPie =  dqiQltyService.getDqiQltyPie(search);
		
		return dqiQltyPie;
	}
}
