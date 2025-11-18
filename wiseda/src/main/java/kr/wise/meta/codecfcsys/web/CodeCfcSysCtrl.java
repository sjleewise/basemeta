/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysCtrl.java
 * 2. Package : kr.wise.meta.codecfcsys.web
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 7. 오후 4:32:17
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 7. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.codecfcsys.service.CodeCfcSysService;
import kr.wise.meta.codecfcsys.service.WamCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysCtrl.java
 * 3. Package  : kr.wise.meta.codecfcsys.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 7. 오후 4:32:17
 * </PRE>
 */
@Controller
public class CodeCfcSysCtrl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;


	@Inject
	private MessageSource message;
	
	@Inject
	private CodeCfcSysService codeCfcSysService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	static class WaqCodeCfcSysVO extends HashMap<String, ArrayList<WaqCodeCfcSys>> {}
	
	static class WaqCodeCfcSysItemVO extends HashMap<String, ArrayList<WaqCodeCfcSysItem>> {}

	
	
	/** 코드분류체계 조회페이지 폼.... @throws Exception meta */
    @RequestMapping("/meta/codecfcsys/codecfcsys_lst.do")
    public String goCodeCfcSysPage() throws Exception {
    	
    	return "/meta/codecfcsys/codecfcsys_lst";

    }
    
    /** 코드분류체계 리스트 조회 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/codecfcsys/getCodeCfcSysList.do")
	@ResponseBody
	public IBSheetListVO<WamCodeCfcSys> selectList(@ModelAttribute WamCodeCfcSys search) throws Exception {
		logger.debug("{}", search);
		
		
		List<WamCodeCfcSys> list = codeCfcSysService.getCodeCfcSysList(search);
		
		return new IBSheetListVO<WamCodeCfcSys>(list, list.size());
	}
    
	/** 코드분류체계 항목 리스트 조회 @return meta */
    @RequestMapping("/meta/codecfcsys/getCodeCfcSysItemList.do")
    @ResponseBody
	public IBSheetListVO<WaqCodeCfcSysItem> getCodeCfcSysRqstList(@ModelAttribute WaqCodeCfcSysItem search) {

		logger.debug("search:{}", search);

		List<WaqCodeCfcSysItem> list = codeCfcSysService.getCodeCfcSysItemList(search);


		return new IBSheetListVO<WaqCodeCfcSysItem>(list, list.size());
	}
    
    /** 코드분류체계 테이블 상세 정보 조회 @return shshin */
    @RequestMapping("/meta/codecfcsys/ajaxgrid/codecfcsys_dtl.do")
    public String getCodeCfcSysDetail(String codeCfcSysId, ModelMap model) {

    	logger.debug("codeCfcSysId:{}", codeCfcSysId);

    	if(codeCfcSysId == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WamCodeCfcSys resultVo =  codeCfcSysService.getCodeCfcSysDetail(codeCfcSysId);
    		model.addAttribute("result", resultVo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/codecfcsys/codecfcsys_dtl";

    }
    
    /** 코드분류체계 변경이력 리스트 조회 @return meta */
    @RequestMapping("/meta/codecfcsys/getCodeCfcSysHistList.do")
    @ResponseBody
	public IBSheetListVO<WaqCodeCfcSys> getCodeCfcSysHistList(@ModelAttribute WaqCodeCfcSys search) {

		logger.debug("search:{}", search);

		List<WaqCodeCfcSys> list = codeCfcSysService.getCodeCfcSysHistList(search);


		return new IBSheetListVO<WaqCodeCfcSys>(list, list.size());
	}
    
    /** 코드분류체계 항목 변경이력 리스트 조회 @return meta */
    @RequestMapping("/meta/codecfcsys/getCodeCfcSysItemHistList.do")
    @ResponseBody
	public IBSheetListVO<WaqCodeCfcSysItem> getCodeCfcSysItemHistList(@ModelAttribute WaqCodeCfcSysItem search) {

		logger.debug("search:{}", search);

		List<WaqCodeCfcSysItem> list = codeCfcSysService.getCodeCfcSysItemHistList(search);


		return new IBSheetListVO<WaqCodeCfcSysItem>(list, list.size());
	}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//공통코드 - IBSheet Combo Code용
	
	
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));

		//코드분류체계유형
		List<CodeListVo> codesyscd = codeListService.getCodeList("clSystmTyCode");
		codeMap.put("codeCfcSysCd", codesyscd);
		codeMap.put("codeCfcSysCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(codesyscd)));
		
		//코드분류체계 항목
		List<CodeListVo> clSystmTyIemCode = codeListService.getCodeList("clSystmTyIemCode");
		codeMap.put("codeCfcSysItemCd", clSystmTyIemCode);
		codeMap.put("codeCfcSysItemCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(clSystmTyIemCode)));
		

		return codeMap;
	}
	
	
}
