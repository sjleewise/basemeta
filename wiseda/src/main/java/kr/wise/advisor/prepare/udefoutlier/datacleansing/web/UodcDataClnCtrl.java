package kr.wise.advisor.prepare.udefoutlier.datacleansing.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.UodcDataClnService;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataCln;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnCol;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
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
public class UodcDataClnCtrl {
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
	private UodcDataClnService uodcDataClnService;
	
	static class WadUodcDataClns extends HashMap<String, ArrayList<WadUodcDataCln>> {};
	static class WadUodcDataClnCols extends HashMap<String, ArrayList<WadUodcDataClnCol>> {};
	
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
	
	@RequestMapping("/advisor/prepare/udefoutlier/datacln/getDataClnDetail.do")
	@ResponseBody
	public WadUodcDataCln getDataClnDetail(WadUodcDataCln search) {
		
		logger.debug("\n getDataClnDetail search:" + search);  
		
		WadUodcDataCln daseImpVo =  uodcDataClnService.getDataClnDetail(search); 
		
		return daseImpVo; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datacln/getUodcDataClnColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcAnaDaseCol> getUodcDataClnColList(WadUodcDataClnCol search) {
		
		logger.debug("\n getUodcDataClnColList search:" + search); 
		
		List<WadUodcAnaDaseCol> list =  uodcDataClnService.getUodcDataClnColList(search);
		
		return new IBSheetListVO<WadUodcAnaDaseCol>(list, list.size()); 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/datacln/regUodcDataCln.do") 
	@ResponseBody
	public IBSResultVO<WadUodcDataCln> regUodcDataCln(@RequestBody WadUodcDataClnCols data, WadUodcDataCln mstData , Locale locale) throws Exception {  
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	logger.debug("\n anaDaseId:" + mstData.getAnaDaseId());
    	    	
    	List<WadUodcDataClnCol> list = data.get("data"); 
    	
    	//데이터임포트 등록 
    	result = uodcDataClnService.regUodcDataCln(list, mstData);
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcDataCln>(result, resmsg, action);
	}
}
