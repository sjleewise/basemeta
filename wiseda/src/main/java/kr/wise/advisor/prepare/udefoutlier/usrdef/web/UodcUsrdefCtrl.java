package kr.wise.advisor.prepare.udefoutlier.usrdef.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.isolationforest.service.WadUodcIsfr;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
import kr.wise.advisor.prepare.udefoutlier.usrdef.service.UodcUsrdefService;
import kr.wise.advisor.prepare.udefoutlier.usrdef.service.WadUodcUsrdef;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
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
public class UodcUsrdefCtrl {
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
	private UodcUsrdefService uodcUsrdefService;
	
	static class WadUodcUsrdefs extends HashMap<String, ArrayList<WadUodcUsrdef>> {};
	
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
	
	@RequestMapping("/advisor/prepare/udefoutlier/usrdef/getDataUsrDef.do")
	@ResponseBody
	public WadUodcUsrdef getDataUsrDef(WadUodcUsrdef search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		WadUodcUsrdef usrdefVo = uodcUsrdefService.getDataUsrDef(search);
		
		return usrdefVo; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/usrdef/regUodcUsrDef.do") 
	@ResponseBody
	public String regUodcUsrDef(WadUodcUsrdef mstData , Locale locale) throws Exception {  
		int result ;
	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId());
    	//logger.debug("\n anaDaseId:" + mstData.getAnaDaseId());
    	
    	//데이터임포트 등록 
    	result = uodcUsrdefService.regUodcUsrDef(mstData);    
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);  
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		//String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return resmsg;
	}
}
