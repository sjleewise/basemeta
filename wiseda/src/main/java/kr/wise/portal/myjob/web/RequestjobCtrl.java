package kr.wise.portal.myjob.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.portal.myjob.service.RequestJobService;
import kr.wise.portal.myjob.service.WaqReqMst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/portal/myjob")
public class RequestjobCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private RequestJobService reqJobService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	private Map<String, Object> codeMap = new HashMap<String, Object>();;
	
	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
//		codeMap = new HashMap<String, Object>();
		
		//시스템영역 코드리스트 JSON 
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String ofBizType 		= UtilJson.convertJsonString(codeListService.getComCodeList(WiseConfig.META, "BIZ_TYPE")); 
//		String requestStatus 	= UtilJson.convertJsonString(codeListService.getComCodeList(WiseConfig.META, "STATUS_CODE")); 
//		String sysareaibs 	= UtilJson.convertJsonString(codelistService.getCodeListIBS("sysarea"));
//		codeMap.put("ofBizType", ofBizType);
		//codeMap.put("ofBizType", codeListService.getComCodeList(WiseConfig.META, "RQST_BIZ_TYPE"));
		//codeMap.put("requestStatus", codeListService.getComCodeList(WiseConfig.META, "STATUS_CODE"));
//		codeMap.put("sysareaibs", sysareaibs);
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
//				codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드
		
		return codeMap;
	}
	
	/** 내 요청목록 화면 조회 */
	/** yeonho */
    @RequestMapping("/request.do")
	public String LoadRqstMyPage() {
		return "/portal/myjob/requestjob";
	}
	

}
