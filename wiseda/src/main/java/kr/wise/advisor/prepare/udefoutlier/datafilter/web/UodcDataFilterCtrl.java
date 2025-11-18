/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutlierDetectCtrl.java
 * 2. Package : kr.wise.advisor.prepare.outlier.web
 * 3. Comment : 테이상값 탐지 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:21:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.udefoutlier.datafilter.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.UodcDaseCndDvService;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCnd;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : UodcColDaseDvCtrl
 * 2. FileName  : UodcColDaseDvCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.udefoutlier.daserowdv.web
 * 4. Comment  : SubsetCol 분할 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:21:16
 * </PRE>
 */
@Controller
public class UodcDataFilterCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService; 
	
	@Inject
    private EgovIdGnrService uodIdGnrService;  
	
	@Inject
	private UodcDaseCndDvService uodcDaseCndDvService;       

	@Inject
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper;   
	
	static class WadUodcDaseCndDvCnds extends HashMap<String, ArrayList<WadUodcDaseCndDvCnd>> {};

	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		
		return codeMap;
	}
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 데이터필터 팝업
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/udefoutlier/popup/datafilter_pop.do") 
	public String goDataFilterPop(@ModelAttribute("search") WadUodcDaseCndDv search, ModelMap model) { 
		logger.debug("이상값 탐지 요청화면:{}", search);
		
		List<WadUodcAnaDaseCol> list = wadUodcAnaDaseColMapper.selectAnaDaseColList(search); 
		
		ComboIBSVo comboIbs = new ComboIBSVo();
		
		String comboCode = "";
		String comboText = "";
		
		for(WadUodcAnaDaseCol vo: list){
			
			logger.debug("\n colPnm:" + vo.getColPnm());
			
			comboCode += "|" + UtilString.null2Blank(vo.getColPnm());
			comboText += "|" + UtilString.null2Blank(vo.getColPnm());
		}
		
		comboIbs.ComboCode = comboCode.substring(1);
		comboIbs.ComboText = comboText.substring(1);
						
		model.addAttribute("colList",  UtilJson.convertJsonString(comboIbs)); 
		
		return "/advisor/prepare/udefoutlier/popup/datafilter_pop";
	}
	
	
	@RequestMapping("/advisor/prepare/udefoutlier/datafilter/getUodcDaseCndDvDetail.do")
	@ResponseBody
	public WadUodcDaseCndDv getUodcColDaseDvDetail(WadUodcDaseCndDv search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		WadUodcDaseCndDv daseImpVo =  uodcDaseCndDvService.getUodcDaseCndDvDetail(search);    
		
		return daseImpVo; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datafilter/getUodcDataFilterColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcDaseCndDvCnd> getUodcDataFilterColList(WadUodcDaseCndDv search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		List<WadUodcDaseCndDvCnd> list =  uodcDaseCndDvService.getUodcDataFilterColList(search);     
		
		return new IBSheetListVO<WadUodcDaseCndDvCnd>(list, list.size()); 
	}
	
	
	
	@RequestMapping("/advisor/prepare/udefoutlier/datafilter/regDaseCndDv.do") 
	@ResponseBody
	public IBSResultVO<WadUodcDaseCndDv> regDaseCndDv(WadUodcDaseCndDv mstData , Locale locale) throws Exception {  
		
		//logger.debug("division:{}", data);
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	logger.debug("\n srcAnaDaseId:" + mstData.getSrcAnaDaseId()); 
    	    	
    	
    	//데이터임포트 등록 
    	result = uodcDaseCndDvService.regDaseCndDv( mstData);     
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcDaseCndDv>(result, resmsg, action);
	}

	@RequestMapping("/advisor/prepare/udefoutlier/datafilter/regDaseCndDvCnd.do") 
	@ResponseBody
	public IBSResultVO<WadUodcDaseCndDv> regDaseCndDvCnd(@RequestBody WadUodcDaseCndDvCnds data, WadUodcDaseCndDv mstData , Locale locale) throws Exception {  
		
		//logger.debug("division:{}", data);
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	logger.debug("\n srcAnaDaseId:" + mstData.getSrcAnaDaseId());
    	    	
    	List<WadUodcDaseCndDvCnd> list = data.get("data"); 
    	
    	//데이터임포트 등록 
    	result = uodcDaseCndDvService.regDaseCndDvCnd(list, mstData);    
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcDaseCndDv>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datafilter/delDaseCndDvCnd.do") 
	@ResponseBody
	public IBSResultVO<WadUodcDaseCndDv> delDaseCndDvCnd(@RequestBody WadUodcDaseCndDvCnds data, WadUodcDaseCndDv mstData , Locale locale) throws Exception {  
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	logger.debug("\n srcAnaDaseId:" + mstData.getSrcAnaDaseId());
    	
    	List<WadUodcDaseCndDvCnd> list = data.get("data"); 
    	
    	//데이터임포트 등록 
    	result = uodcDaseCndDvService.delDaseCndDvCnd(list, mstData);
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcDaseCndDv>(result, resmsg, action);
	}
	
}
