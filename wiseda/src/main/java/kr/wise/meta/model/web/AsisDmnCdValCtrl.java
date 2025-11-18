/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : AsisDmnCdValCtrl.java
 * 2. Package : kr.wise.meta.model.controller
 * 3. Comment : 물리모델 컨트롤러...
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    	:  		: 신규 개발.
 */
package kr.wise.meta.model.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.model.service.AsisDmnCdValService;
import kr.wise.meta.model.service.WamAsisDmnCdVal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * <PRE>
 * 1. ClassName : AsisDmnCdValCtrl
 * 2. Package  : kr.wise.meta.model.controller
 * 3. Comment  :
 * 4. 작성자   : 
 * 5. 작성일   : 
 * </PRE>
 */
@Controller
@RequestMapping("/meta/model/*")
public class AsisDmnCdValCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private AsisDmnCdValService asisDmnCdValService;

	private Map<String, Object> codeMap;


	/**
	 * <PRE>
	 * 1. MethodName : initBinder
	 * 2. Comment    : 폼 데이터 바인딩시 value 값이 ""인 경우 Vo에 NULL로 바인딩 한다...
	 * 3. 작성자       : 
	 * 4. 작성일       :
	 * </PRE>
	 *   @return void
	 *   @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 처리...
	 * 3. 작성자       : 
	 * 4. 작성일       : 
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		String regtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD"));

		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regtypcd", regtypcd);

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
		
		
		return codeMap;
	}


	@RequestMapping("popup/asisdmncdvalpop.do")
	public String goAsisPdmTblPop(@ModelAttribute("search") WamAsisDmnCdVal search, Model model, Locale locale) {

		logger.debug("{}", search);
		
		return "/meta/model/popup/asisdmncdval_pop";
	}
	
	/** 메시지 리스트 조회 */
	@RequestMapping("getasisdmncdvallist.do")
	@ResponseBody
	public IBSheetListVO<WamAsisDmnCdVal> getasisdmncdvallist(@ModelAttribute WamAsisDmnCdVal data, Locale locale) {
		
		logger.debug("reqvo:{}", data);
		
		List<WamAsisDmnCdVal> list = asisDmnCdValService.getAsisDmnCdValList(data);
		
		return new IBSheetListVO<WamAsisDmnCdVal>(list, list.size());
		
	}
	
	@RequestMapping("asisdmncdval_lst.do")
	public String goDmnCdValList(HttpSession session, @RequestParam(value="action",  required=false) String action, @RequestParam(value="objId", required=false) String dmnId, String linkFlag, Model model) {
		logger.debug("{}", action);
		logger.debug("linkFlag : {}",linkFlag);
		
		model.addAttribute("action", action);
		model.addAttribute("dmnId", dmnId);
		model.addAttribute("linkFlag",linkFlag);

		return "/meta/model/asisdmncdval_lst";
	}
	
		/** 메시지 상세정보 조회 */
	@RequestMapping("ajaxgrid/asisdmncdvalinfo_dtl.do")
	public String selectDmnCdValDetail(String dmnId, ModelMap model) {
		logger.debug(" {}", dmnId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dmnId)) {
			WamAsisDmnCdVal result = asisDmnCdValService.selectAsisDmnCdValDetail(dmnId);
			model.addAttribute("result", result);
		}
		return "/meta/model/asisdmncdvalinfo_dtl";
	}

	/** 메시지 변경이력 조회 -IBSheet json */
	@RequestMapping("ajaxgrid/masisdmncdvalchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamAsisDmnCdVal> selectDmnCdValChangeList(@ModelAttribute("searchVO") WamAsisDmnCdVal searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WamAsisDmnCdVal> list = asisDmnCdValService.selectAsisDmnCdValChangeList(dmnId);
		return new IBSheetListVO<WamAsisDmnCdVal>(list, list.size());
	}
}
