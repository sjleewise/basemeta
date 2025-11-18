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
package kr.wise.advisor.prepare.udefoutlier.coldasedv.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Param;
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

import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.UodcColDaseDvService;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDv;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvCol;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.WadUodcDaseRowDv;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.WapDvCanAsm;

/**
 * <PRE>
 * 1. ClassName : UodcColDaseDvCtrl
 * 2. FileName  : UodcColDaseDvCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.udefoutlier.coldasedv.web
 * 4. Comment  : SubsetCol 분할 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:21:16
 * </PRE>
 */
@Controller
public class UodcColDaseDvCtrl {
	
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
	private UodcColDaseDvService uodcColDaseDvService;     
	
	
	static class WadUodcColDaseDvCols extends HashMap<String, ArrayList<WadUodcColDaseDvCol>> {}
	
	
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
	
	
	@RequestMapping("/advisor/prepare/udefoutlier/coldasedv_rqst_dtl.do")	
	public String getColDaseDv(WadUodcColDaseDv search,  ModelMap model) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		WadUodcColDaseDv daseImpVo =  uodcColDaseDvService.getUodcColDaseDvDetail(search); 
				
		if(daseImpVo == null) {
    		model.addAttribute("saction", "I");

    	} else {    		
    		model.addAttribute("resultColDv", daseImpVo);
    		model.addAttribute("saction", "U");
    	}
		
		return "/advisor/prepare/udefoutlier/coldasedv_rqst_dtl"; 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/coldasedv/getUodcColDaseDvDetail.do")
	@ResponseBody
	public WadUodcColDaseDv getUodcColDaseDvDetail(WadUodcColDaseDv search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());  
		
		WadUodcColDaseDv daseImpVo =  uodcColDaseDvService.getUodcColDaseDvDetail(search); 
		
		return daseImpVo; 
	}
	
		
	@RequestMapping("/advisor/prepare/udefoutlier/coldasedv/getUodcColDaseDvColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcColDaseDv> getUodcColDaseDvColList(WadUodcColDaseDv search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId()); 
		
		List<WadUodcColDaseDv> list =  uodcColDaseDvService.getUodcColDaseDvColList(search); 
		
		return new IBSheetListVO<WadUodcColDaseDv>(list, list.size()); 
	}
	
	 
	 
	@RequestMapping("/advisor/prepare/udefoutlier/coldasedv/regColDaseDvlist.do")
	@ResponseBody
	public IBSResultVO<WadUodcColDaseDv> regColDaseDvlist(@RequestBody WadUodcColDaseDvCols data,  WadUodcColDaseDv mstData , Locale locale) throws Exception { 
		
		//logger.debug("division:{}", data);
		int result ;
		
    	List<WadUodcColDaseDvCol> list = data.get("data"); 
    	
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId()); 
    	
    	//데이터임포트 등록 
    	result = uodcColDaseDvService.regColDaseDvlist(list, mstData);   
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcColDaseDv>(result, resmsg, action);
	}
	

	
}
