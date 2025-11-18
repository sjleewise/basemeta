package kr.wise.meta.stnd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.CommStndService;

@Controller
public class CommStndTotCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CommStndService commStndService;


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
//	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);

		//공통코드 - IBSheet Combo Code용
		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
		//결재그룹 코드 리스트
//		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		//표준구분 멀티사전용
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));

		

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}
	
    @RequestMapping("/meta/stnd/commstndtot_lst.do")
	public String goCommStndTot(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);
    	
        
    	return "/meta/stnd/commstndtot_lst";
    	
	} 
    
    public void setEmptyToNull(HashMap<String, String> search){
    	for(String key: search.keySet()){
    		if(search.get(key).equals(""))
    			search.put(key, null);
    	}
    	
    }
	
    @RequestMapping("/meta/stnd/getcommsditmlist.do")
    @ResponseBody
    public IBSheetListVO<HashMap<String, String>> getCommSditmList(@RequestParam HashMap<String, String> search) throws Exception {
    	  setEmptyToNull(search);
	   	  logger.debug("search:{}", search);
	      List<HashMap<String, String>> list = commStndService.getCommSditmList(search); 
	      return new IBSheetListVO<HashMap<String, String>>(list, list.size());
		
	}
    
    @RequestMapping("/meta/stnd/getcommdmnlist.do")
    @ResponseBody
    public IBSheetListVO<HashMap<String, String>> getCommDmnList(@RequestParam HashMap<String, String> search) throws Exception {
    	setEmptyToNull(search);
    	logger.debug("search:{}", search);
        List<HashMap<String, String>> list = commStndService.getCommDmnList(search); 
		return new IBSheetListVO<HashMap<String, String>>(list, list.size());
		
	}
    
    @RequestMapping("/meta/stnd/getcommstwdlist.do")
    @ResponseBody
    public IBSheetListVO<HashMap<String, String>> getCommStwdList(@RequestParam HashMap<String, String> search) throws Exception {
    	setEmptyToNull(search);
   	    logger.debug("search:{}", search);
        List<HashMap<String, String>> list = commStndService.getCommStwdList(search); 
		return new IBSheetListVO<HashMap<String, String>>(list, list.size());
		
	}
	


}
