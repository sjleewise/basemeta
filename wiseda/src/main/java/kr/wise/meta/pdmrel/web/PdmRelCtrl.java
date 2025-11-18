/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstCtrl.java
 * 2. Package : kr.wise.meta.model.web
 * 3. Comment : 물리모델 등록 요청 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 1. 오후 4:22:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 1. :            : 신규 개발.
 */
package kr.wise.meta.pdmrel.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.pdmrel.service.PdmRelService;
import kr.wise.meta.pdmrel.service.WamPdmRelMst;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmRelCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 물리모델 관계 조회  컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 1. 오후 4:22:41
 * </PRE>
 */
@Controller("pdmRelCtrl")
public class PdmRelCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;
   
	@Inject
	private PdmRelService pdmRelService;

	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
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
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//관계유형코드
		codeMap.put("relTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REL_TYP_CD")));
		codeMap.put("relTypCd", cmcdCodeService.getCodeList("REL_TYP_CD"));
		//카디널리티유형코드
		codeMap.put("crdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CRD_TYP_CD")));
		codeMap.put("crdTypCd", cmcdCodeService.getCodeList("CRD_TYP_CD"));
		//Parent Optionality 유형코드
		codeMap.put("paOptTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PA_OPT_TYP_CD")));
		codeMap.put("paOptTypCd", cmcdCodeService.getCodeList("PA_OPT_TYP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));


		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

    //모델관계 변경대상 조회
	@RequestMapping("/meta/pdmrel/popup/pdmrel_pop.do")
	public String goPdmRelPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {

		logger.debug("{}", search);
		return "/meta/pdmrel/popup/pdmrel_pop";
	}

	/** 물리모델 관계 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/pdmrel/getWamPdmRelList.do")
    @ResponseBody
    public IBSheetListVO<WamPdmRelMst> getWamPdmRelList(WamPdmRelMst search) {
		logger.debug("search:{}",search);

		List<WamPdmRelMst> list = pdmRelService.getWamPdmRelList(search); 

		return new IBSheetListVO<WamPdmRelMst>(list, list.size());
	}
    
    //모델관계  조회
  	@RequestMapping("/meta/pdmrel/pdmrel_lst.do") 
  	public String goPdmRelList(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {

  		logger.debug("{}", search);
  		
  		return "/meta/pdmrel/pdmrel_lst";
  	}
  	
  	/** 물리모델 관계 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/pdmrel/getWamPdmRelColList.do")
    @ResponseBody
    public IBSheetListVO<WamPdmRelMst> getWamPdmRelColList(WamPdmRelMst search) {
		logger.debug("search:{}",search);

		List<WamPdmRelMst> list = pdmRelService.getWamPdmRelColList(search);  

		return new IBSheetListVO<WamPdmRelMst>(list, list.size());
	}
    
}
