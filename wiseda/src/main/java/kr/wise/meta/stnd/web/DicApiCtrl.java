package kr.wise.meta.stnd.web;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.DicApiService;
import kr.wise.meta.stnd.service.WaqDic;


/**
 * <PRE>
 * 1. ClassName : StndMainCtrl
 * 2. FileName  : StndMainCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 2014. 3. 25. 오전 10:01:51
 * </PRE>
 */

@Controller
public class DicApiCtrl{
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private DicApiService dicApiService;
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;
	
	@RequestMapping("/meta/stnd/dicApi_lst.do")
	public String doTest(ModelMap model) throws Exception{
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/dicApi_lst";
	}
	
	public static class WaqDics extends HashMap<String, ArrayList<WaqDic>> {}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String dicOrgn = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DIC_ORGN"));
	

		//공통코드 - IBSheet Combo Code용
		codeMap.put("dicOrgnibs", dicOrgn);
		codeMap.put("dicOrgn", cmcdCodeService.getCodeList("DIC_ORGN"));

		return codeMap;
	}
	@RequestMapping("/meta/stnd/delDicList.do")
    @ResponseBody
    public IBSResultVO<WaqDic> delDicList(@RequestBody WaqDics data, Locale locale) throws Exception {
    	logger.debug("remainDicList:{}", data);
    	List<WaqDic> list = data.get("data");
    	
    	//항목분할요청 ID
    	Map<String, String> resultMap = dicApiService.delDicList(list);
    	int result = Integer.parseInt(resultMap.get("result"));
    	String resmsg;
    	
    	if(result > 0 ){
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	return new IBSResultVO<WaqDic>(resultMap, result, resmsg, action);
    }
	
    @RequestMapping("/meta/stnd/getDicList.do")
    @ResponseBody
	public IBSheetListVO<WaqDic> getDicList(WaqDic data, Locale locale) {
		//사전 조회
    	logger.debug("division:{}", data);
    	
		List<WaqDic> list = dicApiService.getDicRqstList(data);
		
		return new IBSheetListVO<WaqDic>(list, list.size());
	}
	
	@RequestMapping("/meta/stnd/postKoreaApi.do")
	@ResponseBody 
	public IBSResultVO<WaqDic> kApi(@RequestBody WaqDics data, String param, Locale locale) throws Exception{
		logger.debug("dic_reqmst:{} \n dic_data:{}", param, data);
		
		List<WaqDic> list = null;
		String Korea = new String("K"); // 국립국어원
		String Naver = new String("N"); // 네이버 백과사전
		
		
		//사전 종류
		String dicType = data.get("data").get(0).getDicOrgn();
	
		
		if(dicType.equals(Korea)) {
			list = dicApiService.postKoreaApi(data);
		}
		else if(dicType.equals(Naver)) {
			list = dicApiService.postNaverApi(data);
		}
		
		
		Map<String, String> resultMap = dicApiService.regDic(list);
		int result = Integer.parseInt(resultMap.get("result"));
    	String resmsg;
    	
		if(result > 0 ){
    		result = 0;
    		resmsg = message.getMessage("REG.DIC", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("REG.NO.DIC", null, locale);
    	}
		String action = WiseMetaConfig.IBSAction.REG.getAction();
		
	
		
		return new IBSResultVO<WaqDic>(resultMap, result, resmsg, action);
		
	}
}