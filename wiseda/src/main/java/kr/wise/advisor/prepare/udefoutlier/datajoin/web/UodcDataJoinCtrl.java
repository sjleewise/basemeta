package kr.wise.advisor.prepare.udefoutlier.datajoin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCnd;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.UodcDaseJnService;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJn;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnCol;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
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
 * 1. ClassName : UodcDataJoinCtrl
 * 2. FileName  : UodcDataJoinCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.udefoutlier.datajoin.web
 * 4. Comment  : data join 컨트롤러
 * 5. 작성자   : 김부식
 * 6. 작성일   : 2018. 4. 16. 오후 2:29:40
 * </PRE>
 */
@Controller
public class UodcDataJoinCtrl {
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
	private UodcDaseJnService uodcDaseJnService;
	
	@Inject
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper;
	
	@Inject
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper;
	
	static class WadUodcDaseJnCols extends HashMap<String, ArrayList<WadUodcDaseJnCol>> {};
	static class WadUodcDaseJns extends HashMap<String, ArrayList<WadUodcDaseJn>> {};
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String leftAnaVarId   = UtilJson.convertJsonString(codeListService.getCodeList("anaVarId"));
		codeMap.put("leftAnaVarId", leftAnaVarId);
		
		return codeMap;
	}
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datajoin/getUodcDaseJnDetail.do")
	@ResponseBody
	public WadUodcDaseJn getUodcDaseJnDetail(WadUodcDaseJn search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		WadUodcDaseJn daseImpVo =  uodcDaseJnService.getUodcDaseJnDetail(search);    
		
		return daseImpVo; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datajoin/getUodcJoinColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcDaseJnCol> getUodcJoinColList(WadUodcDaseJn search) {
		logger.debug("\n dbSchId:" + search.getDbSchId());
		
		List<WadUodcDaseJnCol> list = uodcDaseJnService.getUodcJoinColList(search);
		
		return new IBSheetListVO<WadUodcDaseJnCol>(list, list.size());
	}
	
	/** 데이터조인 팝업
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/udefoutlier/popup/datajoin_pop.do") 
	public String goDataJoinPop(@ModelAttribute("search") WadUodcDaseJn search, ModelMap model) { 
		logger.debug("이상값 탐지 요청화면:{}", search);
		logger.debug("udfOtlDtcId >>>> {}", search.getUdfOtlDtcId());
		logger.debug("creCompId >>>> {}", search.getCreCompId());
		
		WadUodcAnaDase param = new WadUodcAnaDase();
		
		param.setUdfOtlDtcId(search.getUdfOtlDtcId());
		param.setCreCompId(search.getCreCompId());
		
		List<WadUodcAnaDase> list = wadUodcAnaDaseMapper.selectAnaDaseIdByFk(param); 
		
		ComboIBSVo comboIbs = new ComboIBSVo();
		
		String comboCode = "";
		String comboText = "";
		
		for(WadUodcAnaDase vo: list){
			logger.debug("\n colPnm:" + vo.getAnaDaseNm());
			
			comboCode += "|" + UtilString.null2Blank(vo.getAnaDaseId());
			comboText += "|" + UtilString.null2Blank(vo.getAnaDaseNm());
			
			WadUodcDaseCndDv colParam = new WadUodcDaseCndDv();
			colParam.setAnaDaseId(vo.getAnaDaseId());
		}
		
		if(!comboCode.equals("")) {
		
			comboIbs.ComboCode = comboCode.substring(1);
			comboIbs.ComboText = comboText.substring(1);
		}
						
		model.addAttribute("colList",  UtilJson.convertJsonString(comboIbs));
		
		return "/advisor/prepare/udefoutlier/popup/datajoin_pop";
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datajoin/getSubSelBox.do")
	@ResponseBody
	public List<WadUodcAnaDaseCol> getSubSelBox(WadUodcDaseJnCol search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		List<WadUodcAnaDaseCol> daseImpVo =  uodcDaseJnService.getSubSelBox(search);
		
		return daseImpVo; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datajoin/delDaseJnCol.do") 
	@ResponseBody
	public IBSResultVO<WadUodcDaseJnCol> delDaseJnCol(@RequestBody WadUodcDaseJnCols data, WadUodcDaseJn mstData , Locale locale) throws Exception {  
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	
    	List<WadUodcDaseJnCol> list = data.get("data"); 
    	
    	logger.debug("\n list >>>>> " + list.get(0).getJnColSno());
    	
    	result = uodcDaseJnService.delDaseJnCol(list, mstData);
    
    	String resmsg;
    	
    	if(result >= 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction(); 

    	return new IBSResultVO<WadUodcDaseJnCol>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datajoin/regUodcJoin.do") 
	@ResponseBody
	public IBSResultVO<WadUodcDaseJn> regUodcJoin(@RequestBody WadUodcDaseJnCols data, WadUodcDaseJn mstData , Locale locale) throws Exception {  
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	logger.debug("\n anaDaseId:" + mstData.getAnaDaseId());
    	    	
    	List<WadUodcDaseJnCol> list = data.get("data"); 
    	
    	//데이터임포트 등록 
    	result = uodcDaseJnService.regUodcJoin(list, mstData);    
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcDaseJn>(result, resmsg, action);
	}
}
