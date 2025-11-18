package kr.wise.dq.vrfcrule.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.menu.service.MenuStatVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.vrfcrule.service.VrfcruleService;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

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
 * 1. ClassName : 
 * 2. FileName  : MenuManageCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.web
 * 4. Comment  : 메뉴 관리 컨트롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 24. 오후 2:05:16
 * </PRE>
 */ 
@Controller
@RequestMapping("/dq/vrfcrule")

public class VrfcruleCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class VrfcruleVOs extends HashMap<String, ArrayList<VrfcruleVO>> { }
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private VrfcruleService vrfcruleService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 검증룰리스트 조회 폼 */
	@RequestMapping("/vrfcrule_lst.do")
	public String selectVrfc(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/vrfcrule/vrfcrule_lst";
	}
	
	/** 검증룰리스트 조회 -IBSheet json */
	@RequestMapping("/selectVrfcList.do")
	@ResponseBody
	public IBSheetListVO<VrfcruleVO> selectVrfcList(@ModelAttribute("searchVO") VrfcruleVO search) throws Exception {
		logger.debug("selectVrfcList");
		logger.debug("serach >>> " + search.toString());
		
		List<VrfcruleVO> list = vrfcruleService.selectVrfcList(search);
		
		return new IBSheetListVO<VrfcruleVO>(list, list.size()); 
	}
	
	/** 메뉴 저장 단건 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/saveVrfcRow.do")
    @ResponseBody
    public IBSResultVO<VrfcruleVO> saveVrfcRow(VrfcruleVO saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	logger.debug("saveVO.getVrfcRule >>> " + saveVO.getVrfcRule());
    	saveVO.setVrfcRule(saveVO.getVrfcRule().replaceAll("(\r\n|\r|\n|\n\r)", ""));
    	
    	int result = vrfcruleService.saveVrfc(saveVO, saction);

    	String resmsg ;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<VrfcruleVO>(saveVO, result, resmsg, action);
    }

	
	/** 메뉴 상세 정보 조회 */
	@RequestMapping("/selectVrfcDetail.do")
	public String selectVrfcDetail(String vrfcId, String saction, ModelMap model) {
		logger.debug(" {}", vrfcId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(vrfcId)) {

			VrfcruleVO result = vrfcruleService.selectVrfcDetail(vrfcId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		
		return "/dq/vrfcrule/vrfcrule_dtl";
	}
	
	/** 엑셀로 메뉴리스트 (여러건)등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/saveVrfcs.do")
	@ResponseBody
	public IBSResultVO<VrfcruleVO> regList(@RequestBody VrfcruleVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<VrfcruleVO> list = data.get("data");
		int result = vrfcruleService.regVrfc(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<VrfcruleVO>(result, resmsg, action);
	}
	
	
    /** 메뉴 삭제 for IBSheet
     * @throws Exception */
    @RequestMapping("/deleteVrfc.do")
    @ResponseBody
    public IBSResultVO<VrfcruleVO> deleteVrfc(@RequestBody VrfcruleVOs data, Locale locale) throws Exception {
    	
    	logger.debug("vrfcNo:{}", data);
    	ArrayList<VrfcruleVO> list = data.get("data");
    	int result = vrfcruleService.deleteVrfc(list);
    	
    	
    	String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else if (result == 0) {
			result = -1;
			resmsg = message.getMessage("ERR.CHKLINKEDDATA", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<VrfcruleVO>(result, resmsg, action);
    }
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));

		return codeMap;
	}
}
