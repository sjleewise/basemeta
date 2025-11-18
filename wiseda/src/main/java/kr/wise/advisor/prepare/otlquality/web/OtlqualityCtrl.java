package kr.wise.advisor.prepare.otlquality.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.otlquality.service.OtlAlgQltyVo;
import kr.wise.advisor.prepare.otlquality.service.OtlQltyService;
import kr.wise.advisor.prepare.otlquality.service.OtlQltyVo;
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
public class OtlqualityCtrl {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private OtlQltyService otlQltyService;
	
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
		
		return codeMap;
	}
	
	/** bdq 이상값탐지 품질현황 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/otlquality_rqst.do")
	public String goOtlqualityRqst(@ModelAttribute("search") CommonVo search) {
		logger.debug("bdq 이상값탐지 품질현황 화면:{}", search);
		
		return "/advisor/prepare/otlquality/otlquality_rqst";
	}
	
	/** bdq 이상값탐지 품질현황 화면		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getDbmsQltyList.do")
	@ResponseBody
	public IBSheetListVO<OtlQltyVo> getDbmsQltyList(OtlQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyList:{}", search);
		
		List<OtlQltyVo> dbmsQltyList =  otlQltyService.getDbmsQltyList(search);
		
		return new IBSheetListVO<OtlQltyVo>(dbmsQltyList,dbmsQltyList.size());
	}
	
	/** bdq 이상값탐지 품질현황 화면 - dbms pie chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getDbmsPieData.do")
	@ResponseBody
	public List<OtlQltyVo> getDbmsQltyPie(OtlQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyPie:{}", search);
		
		List<OtlQltyVo> dbmsQltyPie =  otlQltyService.getDbmsQltyPie(search);
		
		return dbmsQltyPie;
	}
	
	/** bdq 이상값탐지 품질현황 화면 - tbl		 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getTblQltyList.do")
	@ResponseBody
	public IBSheetListVO<OtlQltyVo> getTblQltyList(OtlQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyList:{}", search);
		
		List<OtlQltyVo> tblQltyList =  otlQltyService.getTblQltyList(search);
		
		return new IBSheetListVO<OtlQltyVo>(tblQltyList,tblQltyList.size());
	}
	
	/** bdq 이상값탐지 품질현황 화면 - tbl pie chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getTblPieData.do")
	@ResponseBody
	public List<OtlQltyVo> getTblQltyPie(OtlQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyPie:{}", search);
		
		List<OtlQltyVo> tblQltyPie =  otlQltyService.getTblQltyPie(search);
		
		return tblQltyPie;
	}
	
	/** bdq 이상값탐지 품질현황 화면 - col	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getColQltyList.do")
	@ResponseBody
	public IBSheetListVO<OtlQltyVo> getColQltyList(OtlQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyList:{}", search);
		
		List<OtlQltyVo> colQltyList =  otlQltyService.getColQltyList(search);
		
		return new IBSheetListVO<OtlQltyVo>(colQltyList,colQltyList.size());
	}
	
	/** bdq 이상값탐지 품질현황 화면 - col pie chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getColPieData.do")
	@ResponseBody
	public List<OtlQltyVo> getColQltyPie(OtlQltyVo search) throws Exception {
		logger.debug("bdq 통계 getDbmsQltyPie:{}", search);
		
		List<OtlQltyVo> colQltyPie =  otlQltyService.getColQltyPie(search);
		
		return colQltyPie;
	}
	
	/** bdq 이상값탐지 품질현황 화면 - otl	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getOtlQltyList.do")
	@ResponseBody
	public IBSheetListVO<OtlAlgQltyVo> getOtlQltyList(OtlAlgQltyVo search) throws Exception {
		logger.debug("bdq 통계 getOtlQltyList:{}", search);
		
		List<OtlAlgQltyVo> otlQltyList =  otlQltyService.getOtlQltyList(search);
		
		return new IBSheetListVO<OtlAlgQltyVo>(otlQltyList,otlQltyList.size());
	}
	
	/** bdq 이상값탐지 품질현황 화면 - col pie chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getOtlBarData.do")
	@ResponseBody
	public List<OtlAlgQltyVo> getOtlQltyBar(OtlAlgQltyVo search) throws Exception {
		logger.debug("bdq 통계 getOtlQltyBar:{}", search);
		
		List<OtlAlgQltyVo> otlQltyBar =  otlQltyService.getOtlQltyBar(search);
		
		return otlQltyBar;
	}
	
	/** bdq 이상값탐지 품질현황 화면 - col pie chart 데이터 조회	 */
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/otlquality/getOtlPieData.do")
	@ResponseBody
	public List<OtlAlgQltyVo> getOtlQltyPie(OtlAlgQltyVo search) throws Exception {
		logger.debug("bdq 통계 getOtlQltyPie:{}", search);
		
		List<OtlAlgQltyVo> otlQltyPie =  otlQltyService.getOtlQltyPie(search);
		
		return otlQltyPie;
	}

}
