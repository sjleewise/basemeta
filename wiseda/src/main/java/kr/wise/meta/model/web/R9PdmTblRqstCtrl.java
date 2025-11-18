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
package kr.wise.meta.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.model.service.PdmTblRqstService;
import kr.wise.meta.model.service.R9PdmTblRqstService;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 물리모델 등록 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 1. 오후 4:22:41
 * </PRE>
 */
@Controller("R9PdmTblRqstCtrl")
public class R9PdmTblRqstCtrl {

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
	private RequestMstService requestMstService;

	@Inject
	private R9PdmTblRqstService r9pdmTblRqstService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	@Inject
	private PdmTblRqstService pdmTblRqstService;

	static class WaqPdmTbls extends HashMap<String, ArrayList<WaqPdmTbl>> {}

//	static class WaqPdmCols extends HashMap<String, ArrayList<WaqPdmCol>> {}

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
				
		
//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
			
		return codeMap;
	}

    /** 물리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/model/r9_pdmtbl_rqst.do")
    public String goModelPdmTblRqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
//    		String reqid = requestIdGnrService.getNextStringId();
//    		reqmst.setRqstNo(reqid);
//    		reqmst.setBizDcd("R9P");
////    		reqmst.setBizDtlCd("TBL");
//    		reqmst.setRqstStepCd("N");
    		
    		reqmst.setBizDcd("R9P");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    		
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	String returnstr = null;
    	
    	//String r9Dist = message.getMessage("r9_distributor", null, Locale.getDefault()); 
    	String r9Dist = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.R9_DISTRIBUTOR, null, Locale.getDefault());
    	
    	logger.debug("\r9Dist:" + r9Dist); 
    	
    	if(r9Dist.equals("SOFTVERK")){ 
    		
    		returnstr = "/meta/model/r9_pdmtbl_rqst_soft"; // 소프트베르크 모듈
    	}else{
			returnstr = "/meta/model/r9_pdmtbl_rqst"; // 제네시스 모듈    		
		}
    	
    	return returnstr;		

    }


    /** R9 모델마트 테이블 검색 팝업... @return insomnia 
     * @throws Exception */
    @RequestMapping("/meta/model/popup/r9_pdmtblSearchPop.do")
    public String goR9MartTblPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) throws Exception {
    	logger.debug("{}", search);
    	
    	WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("SUBJ");  
    	
    	int maxSubjLvl = data.getBscLvl(); 
    	
    	model.addAttribute("maxSubjLvl", maxSubjLvl);

    	return "/meta/model/popup/r9_pdmTblSearch_pop";
    }

    /** R9 모델마트 테이블 검색 팝업... 소프트베르크용 @return insomnia */
    @RequestMapping("/meta/model/popup/r9_pdmtblSearchPopSoft.do")
    public String goR9MartTblPopSoft(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
    	logger.debug("{}", search);
    	
    	return "/meta/model/popup/r9_pdmTblSearch_pop_soft";
    }

    /** R9 모델마트 테이블 조회 소프트베르크용 - IBSHEET JSON @return insomnia */
    @RequestMapping("/meta/model/getmart9tbllistsoft.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmTbl> getMart9TblListsoft(WaqPdmTbl search, Locale locale) throws Exception {
    	
		List<WaqPdmTbl> list = r9pdmTblRqstService.getMart9TblListSoft(search);
		
    	return new IBSheetListVO<WaqPdmTbl>(list, list.size());
    }

    /** R9 모델마트 테이블 조회 - IBSHEET JSON @return insomnia */
    @RequestMapping("/meta/model/getmart9tbllist.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmTbl> getMart9TblList(WaqPdmTbl search, Locale loclae) throws Exception {
    	logger.debug("{}", search);
    	
    	List<WaqPdmTbl> list = r9pdmTblRqstService.getMart9TblList(search);
//    	logger.debug("{}", list);
    	return new IBSheetListVO<WaqPdmTbl>(list, list.size());

    }

//    /** 물리모델 테이블 변경대상 요청 리스트 추가... @throws Exception insomnia */
    @RequestMapping("/meta/model/regMart9WaqPdmTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMart9Waqlist(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqPdmTbl> list = data.get("data");

		int result = r9pdmTblRqstService.regMart9Waq(reqmst, list);

		result += pdmTblRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
}
