/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColMapRqstCtrl.java
 * 2. Package : kr.wise.meta.mapping.web
 * 3. Comment : 컬럼매핑정의서 등록요청
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 24. 19:33:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열  : 2014. 7. 24. :            : 신규 개발.
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
import kr.wise.meta.mapping.service.ColMapRqstService;
import kr.wise.meta.mapping.service.WamColMap;
import kr.wise.meta.mapping.service.WamTblMap;
import kr.wise.meta.mapping.service.WaqColMap;




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
 * 2. FileName  : ColMapRqstCtrl.java
 * 3. Package  : kr.wise.meta.mapping.web
 * 4. Comment  : 컬럼매핑정의서 등록요청
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 24. 19:33:00
 * </PRE>
 */
@Controller("ColMapRqstCtrl")
public class ColMapRqstCtrl {

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
	private ColMapRqstService colMapRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqColMaps extends HashMap<String, ArrayList<WaqColMap>> {}

	
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
		
		
		//타겟이행유형
		codeMap.put("tgtFlfTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TGT_FLF_TYP_CD")));
		codeMap.put("tgtFlfTypCd", cmcdCodeService.getCodeList("TGT_FLF_TYP_CD"));
		
		//테이블매핑유형
		codeMap.put("tblMapTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_MAP_TYP_CD")));
		codeMap.put("tblMapTypCd", cmcdCodeService.getCodeList("TBL_MAP_TYP_CD"));
		
		//컬럼매핑유형
		codeMap.put("colMapTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("COL_MAP_TYP_CD")));
		codeMap.put("colMapTypCd", cmcdCodeService.getCodeList("COL_MAP_TYP_CD"));

		//주제영역
		codeMap.put("subj", codeListService.getCodeList("subj"));
		codeMap.put("subjibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("subj")));
		
		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
		String sysarea 		= UtilJson.convertJsonString(sysareatmp);
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));

		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

    /** 컬럼 매핑정의서 등록 요청서 입력 폼.... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/colmap_rqst.do")
    public String goColMapRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
//    		String reqid = requestIdGnrService.getNextStringId();
//    		reqmst.setRqstNo(reqid);
//    		reqmst.setBizDcd("CMP");
////    		reqmst.setBizDtlCd("TBLMAP");
//    		reqmst.setRqstStepCd("N");
    		reqmst.setBizDcd("CMP");
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

    	return "/meta/mapping/colmap_rqst";

    }

    /** 컬럼 매핑정의서 등록 요청 상세 정보 조회 @return 유성열 */
    @RequestMapping("/meta/mapping/ajaxgrid/colmap_rqst_dtl.do")
    public String getColMapDetail(WaqColMap searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqColMap resultvo =  colMapRqstService.getColMapRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}
    	
    	logger.debug("model:{}", model);

    	return "/meta/mapping/colmap_rqst_dtl";

    }
    
    /** 타겟테이블매핑 팝업창 호출... @return 유성열 */
	@RequestMapping("/meta/mapping/popup/tgtTblMapSearch_pop.do")
	public String goTgtTblMapPop(@ModelAttribute("search") WamTblMap search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mapping/popup/tgtTblMapSearch_pop";
	}
	
    
    /** 컬럼 매핑정의서 요청 리스트 등록... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/regcolmaprqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regcolmaprqstlist(@RequestBody WaqColMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqColMap> list = data.get("data");

		//int result = 0;
		int result = colMapRqstService.register(reqmst, list);

		//기본적인 체크 로직만 존재. 차후 추가 요망.
		result += colMapRqstService.check(reqmst);

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


    /** 컬럼매핑정의서 요청 리스트 조회 @return 유성열 */
    @RequestMapping("/meta/mapping/getcolmaprqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqColMap> getColMapRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqColMap> list = colMapRqstService.getColMapRqstList(search);
		
		logger.debug("list:{}", list);

		return new IBSheetListVO<WaqColMap>(list, list.size());
	}
    
    
    /** 컬럼매핑정의서 타겟컬럼 리스트 조회 @return 유성열 */
    @RequestMapping("/meta/mapping/getTgtColList.do")
    @ResponseBody
	public IBSheetListVO<WaqColMap> getTgtColList(WaqColMap search) {

		logger.debug("search:{}", search);

		List<WaqColMap> list = colMapRqstService.getTgtColList(search);

		return new IBSheetListVO<WaqColMap>(list, list.size());
	}
    
	/** 컬럼매핑정의서 타겟테이블 팝업 화면 */
	@RequestMapping("/meta/mapping/popup/tgtColSearchPop.do")
	public String goTgtColSearchPop(@ModelAttribute("search") WaqColMap search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mapping/popup/tgtColSearchPop";
	}



    /** 테이블매핑정의서 요청 리스트 삭제.... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/delcolmaprqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delColMapRqstList(@RequestBody WaqColMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqColMap> list = data.get("data");

		int result = colMapRqstService.delColMapRqst(reqmst, list);

		result += colMapRqstService.check(reqmst);

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

    /** 컬럼매핑정의서 변경대상 요청 리스트 추가... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/regWam2WaqColMap.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqColMaps data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqColMap> list = data.get("data");
		
		int result = colMapRqstService.regWam2Waq(reqmst, list);

		result += colMapRqstService.check(reqmst);

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

    /** 컬럼매핑정의서 엑셀업로드 팝업창 호출... @return 유성열 */
    @RequestMapping("/meta/mapping/popup/colmap_xls.do")
    public String goColMapRqstxls(@ModelAttribute("search") WaqColMap search) {

    	return "/meta/mapping/popup/colmap_xls";
    }
    
    /** 컬럼매핑정의서 변경대상추가 팝업창 호출... @return 유성열 */
	@RequestMapping("/meta/mapping/popup/colMapSearchPop.do")
	public String goColMapPop(@ModelAttribute("search") WamColMap search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mapping/popup/colMapSearch_pop";
	}
	
	
	/** 컬럼매핑정의서 변경대상리스트 조회... @return 유성열 */
	@RequestMapping("/meta/mapping/popup/getcolmaplist.do")
	@ResponseBody
	public IBSheetListVO<WamTblMap> getColMaplist(@ModelAttribute WamTblMap search, Locale locale) {

		logger.debug("{}", search);
		List<WamTblMap> list = colMapRqstService.getColMapList(search);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamTblMap>(list, list.size());

	}
	

    /** 컬럼매핑정의서 승인... @throws Exception 유성열 */
    @RequestMapping("/meta/mapping/approvecolmap.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveColMap(@RequestBody WaqColMaps data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqColMap> list = data.get("data");

		int result  = colMapRqstService.approve(reqmst, list);


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
