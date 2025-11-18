package kr.wise.advisor.prepare.udefoutlier.lof.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.lof.service.UodcLofService;
import kr.wise.advisor.prepare.udefoutlier.lof.service.WadUodcLof;
import kr.wise.advisor.prepare.udefoutlier.script.PythonScriptService;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UodcLofCtrl {
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
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper;
	
	@Inject
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper;
	
	@Inject
	private UodcLofService uodcLofService;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	
	static class WadUodcLofs extends HashMap<String, ArrayList<WadUodcLof>> {};
	
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
	
	@RequestMapping("/advisor/prepare/udefoutlier/lof/getUodcLofDetail.do")
	@ResponseBody
	public WadUodcLof getUodcLofDetail(WadUodcLof search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		WadUodcLof daseImpVo = uodcLofService.getUodcLofDetail(search);
		
		return daseImpVo; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/lof/getUodcLofColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcLof> getUodcLofColList(WadUodcLof search) {
		logger.debug("\n dbSchId:" + search.getDbSchId());
		
		List<WadUodcLof> list = uodcLofService.getUodcLofColList(search);
		
		return new IBSheetListVO<WadUodcLof>(list, list.size());
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/lof/regUodcLof.do") 
	@ResponseBody
	public IBSResultVO<WadUodcLof> regUodcLof(@RequestBody WadUodcLofs data, WadUodcLof mstData , Locale locale) throws Exception {  
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	logger.debug("\n anaDaseId:" + mstData.getAnaDaseId());
    	    	
    	List<WadUodcLof> list = data.get("data"); 
    	
    	//데이터임포트 등록 
    	result = uodcLofService.regUodcLof(list, mstData);    
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcLof>(result, resmsg, action);
	}
}
