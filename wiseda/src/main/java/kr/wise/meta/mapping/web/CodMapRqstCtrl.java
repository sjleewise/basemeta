/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodMapRqstCtrl.java
 * 2. Package : kr.wise.meta.mapping.web
 * 3. Comment : 코드매핑정의서 등록요청
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 31. 14:55:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열  : 2014. 7. 31. :            : 신규 개발.
 */
package kr.wise.meta.mapping.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.mapping.service.CodMapRqstService;
import kr.wise.meta.mapping.service.WamCodMap;
import kr.wise.meta.mapping.service.WaqCodMap;

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
 * 2. FileName  : CodMapRqstCtrl.java
 * 3. Package  : kr.wise.meta.mapping.web
 * 4. Comment  : 코드매핑정의서 등록요청
 * 5. 작성자   : 유성열
 * 6. 작성일   :  2014. 7. 31. 14:55:00
 * </PRE>
 */
@Controller("CodMapRqstCtrl")
public class CodMapRqstCtrl {

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
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private CodMapRqstService codMapRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqCodMaps extends HashMap<String, ArrayList<WaqCodMap>> {}

	
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
		
		//매핑정의서유형
		codeMap.put("mapDfTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MAP_DF_TYP_CD")));
		codeMap.put("mapDfTypCd", cmcdCodeService.getCodeList("MAP_DF_TYP_CD"));
		
		//코드전환유형
		codeMap.put("cdCnvsTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_CNVS_TYPE")));
		codeMap.put("cdCnvsTypCd", cmcdCodeService.getCodeList("CD_CNVS_TYPE"));
		
		//코드매핑유형
		codeMap.put("codMapTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_MAP_TYPE")));
		codeMap.put("codMapTypCd", cmcdCodeService.getCodeList("CD_MAP_TYPE"));

		//코드값유형
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		codeMap.put("cdValTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD")));
		
		//코드값부여방식
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		codeMap.put("cdValIvwCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD")));
		
		
		//주제영역
		List<CodeListVo> subj = codeListService.getCodeList("subj");
		codeMap.put("subj", subj);
		codeMap.put("subjibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(subj)));
		
		
		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
		String sysarea 		= UtilJson.convertJsonString(sysareatmp);
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

    /** 코드 매핑정의서 등록 요청서 입력 폼.... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/codmap_rqst.do")
    public String goCodMapRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DMP");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	
    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//        System.out.println("요청번호 : " + reqmst.getRqstNo());
//        System.out.println("요청구분 : " + reqmst.getBizDcd());
//        System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        int mapcount = approveLineServie.checkRequst(mapvo); 
//        System.out.println("카운트 : " + mapcount);
        
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
	model.addAttribute("rqstbizdcd",reqmst.getBizDcd());

    	return "/meta/mapping/codmap_rqst";

    }

    /** 코드 매핑정의서 등록 요청 상세 정보 조회 @return 유성열 */
    @RequestMapping("/meta/mapping/ajaxgrid/codmap_rqst_dtl.do")
    public String getTblMapDetail(WaqCodMap searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqCodMap resultvo =  codMapRqstService.getCodMapRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}
    	
    	logger.debug("model:{}", model);

    	return "/meta/mapping/codmap_rqst_dtl";

    }
    
    
    /** 코드매핑정의서 요청 리스트 조회 @return 유성열 */
    @RequestMapping("/meta/mapping/getcodmaprqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqCodMap> getCodMapRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqCodMap> list = codMapRqstService.getCodMapRqstList(search);
		
		logger.debug("list:{}", list);

		return new IBSheetListVO<WaqCodMap>(list, list.size());
	}
    
    
    
    /** 코드 매핑정의서 요청 리스트 등록... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/regcodmaprqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regcodmaprqstlist(@RequestBody WaqCodMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqCodMap> list = data.get("data");

		//int result = 0;
		int result = codMapRqstService.register(reqmst, list);

		result += codMapRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
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



    
    
    /** 코드매핑정의서 타겟코드도메인 리스트 조회 @return 유성열 */
    @RequestMapping("/meta/mapping/getTgtCdDmnList.do")
    @ResponseBody
	public IBSheetListVO<WaqCodMap> getTgtCdDmnList(WaqCodMap search) {

		logger.debug("search:{}", search);

		List<WaqCodMap> list = codMapRqstService.getTgtCdDmnList(search);

		return new IBSheetListVO<WaqCodMap>(list, list.size());
	}
    
	/** 코드매핑정의서 타겟코드도메인 팝업 화면 */
	@RequestMapping("/meta/mapping/popup/tgtCdDmnSearchPop.do")
	public String goTgtCdDmnSearchPop(@ModelAttribute("search") WaqCodMap search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mapping/popup/tgtCdDmnSearchPop";
	}



    /** 코드매핑정의서 요청 리스트 삭제.... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/delcodmaprqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delCodMapRqstList(@RequestBody WaqCodMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqCodMap> list = data.get("data");

		int result = codMapRqstService.delCodMapRqst(reqmst, list);

		result += codMapRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

    /** 코드매핑정의서 변경대상 요청 리스트 추가... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/regWam2WaqCodMap.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqCodMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqCodMap> list = data.get("data");

		int result = codMapRqstService.regWam2Waq(reqmst, list);

		result += codMapRqstService.check(reqmst);
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		logger.debug("결과메시지 : {} / result : {}", resmsg, result);

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}

    /** 코드매핑정의서 엑셀업로드 팝업창 호출... @return 유성열 */
    @RequestMapping("/meta/mapping/popup/codmap_xls.do")
    public String goCodMapRqstxls(@ModelAttribute("search") WaqCodMap search) {

    	return "/meta/mapping/popup/codmap_xls";
    }
    
    /** 코드매핑정의서 변경대상추가 팝업창 호출... @return 유성열 */
	@RequestMapping("/meta/mapping/popup/codMapSearchPop.do")
	public String goCodMapPop(@ModelAttribute("search") WamCodMap search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mapping/popup/codMapSearch_pop";
	}
	
	
	/** 코드매핑정의서 변경대상리스트 조회... @return 유성열 */
	@RequestMapping("/meta/mapping/popup/getcodmaplist.do")
	@ResponseBody
	public IBSheetListVO<WamCodMap> getCodMaplist(@ModelAttribute WamCodMap search, Locale locale) {

		logger.debug("{}", search);
		List<WamCodMap> list = codMapRqstService.getCodMapList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamCodMap>(list, list.size());

	}
	

    /** 코드매핑정의서 승인... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/approvecodmap.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveCodMap(@RequestBody WaqCodMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}

		ArrayList<WaqCodMap> list = data.get("data");

		int result  = codMapRqstService.approve(reqmst, list);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}


}
