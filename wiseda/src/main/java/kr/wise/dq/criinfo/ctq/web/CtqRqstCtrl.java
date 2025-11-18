package kr.wise.dq.criinfo.ctq.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.cmm.service.MessengerService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.RequestVrfDtlsService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.criinfo.ctq.service.CtqRqstService;
import kr.wise.dq.criinfo.ctq.service.WamCtqVO;
import kr.wise.dq.criinfo.ctq.service.WaqCtqVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
 * 2. FileName  : CtqRqstCtrl.java
 * 3. Package  : kr.wise.dq.criinfo.ctq.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 10. 오전 11:41:09
 * </PRE>
 */ 
@Controller
public class CtqRqstCtrl {

	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;
	
	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WaqCtqVOs extends HashMap<String, ArrayList<WaqCtqVO>> { }
	
	@Inject
	private RequestVrfDtlsService requestVrfDtlsService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CtqRqstService ctqRqstService;
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
	private RequestMstService requestMstService;
	
	/* 결재라인조회*/
	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private MessengerService mService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/dq/criinfo/ctq/ctq_rqst.do")
	public String formpage(WaqMstr reqmst, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("CTQ");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	
    	
    	//결재라인 결재레벨 조회
    	//결재레벨 미존재 시 검증 후 등록 버튼 활성화
    	WaaRqstBizApr aprSearch = new WaaRqstBizApr();
    	aprSearch.setBizDcd(reqmst.getBizDcd());
    	List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(aprSearch);
    	logger.debug("list SIZE : ", list.size());
    	reqmst.setAprLvl(list.size());

    	model.addAttribute("waqMstr", reqmst);
    	
		return "/dq/criinfo/ctq/ctq_rqst";
	}
	
	
	 /** 중요정보항목 엑셀 등록 팝업 화면 호출 */
    @RequestMapping("/dq/criinfo/ctq/popup/ctq_xls.do")
    public String ctqxls(@ModelAttribute("search")WamCtqVO search) {
    	logger.debug("WaqMstr {} : ", search);
    	
    	return  "/dq/criinfo/ctq/popup/ctq_xls";
    }
	
	@RequestMapping("/dq/criinfo/ctq/getVrfCtqListIBS.do")
	@ResponseBody
	public IBSheetListVO<WaqCtqVO> getVrfCtqListIBS(@ModelAttribute WaqMstr search) {
		logger.debug("WaqMstr {} : ", search);
		List<WaqCtqVO> list = ctqRqstService.getVrfCtqListIBS(search);
		
		return new IBSheetListVO<WaqCtqVO>(list, list.size());
	}
	
	/** 데이터품질지표 상세내용 조회 */
    /** meta */
    @RequestMapping("/dq/criinfo/ctq/ajaxgrid/ctq_rqst_dtl.do")
    public String getDqiRqstDtl (WaqCtqVO searchVO, ModelMap model) {

    	logger.debug("searchVO:{}", searchVO);

    	if (searchVO.getRqstSno() == null){
    		model.addAttribute("saction", "I");
    	} else {
    		WaqCtqVO resultVO = ctqRqstService.getCtqRqstDetail(searchVO); 
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}

    	return "/dq/criinfo/ctq/ctq_rqst_dtl";
    }
	
	/** 중요정보항목 리스트 저장 -  결과는 IBSResult Json 리턴 */	
	@RequestMapping("/dq/criinfo/ctq/regVrfCtqList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> register( @RequestBody WaqCtqVOs saveVOs,  @ModelAttribute WaqMstr reqmst,  Locale locale) throws Exception {
		logger.debug("data : {}", saveVOs);
		
		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		logger.debug("USER : {}", user);
		//검증사용자ID 셋팅
		reqmst.setWritUserId(user.getId());
		//요청사용자ID 셋팅
		//결재로직 개발 완료 후 추가 개발 요건 발생
		reqmst.setRqstUserId(user.getId());
				
		//등록 결과를 맵으로 받는다.
		//result : 등록 결과
		//rqstNo : 요청서 번호
		int result = ctqRqstService.register(reqmst, saveVOs.get("data"));
		
		
		//검증로직
		if(result > 0){
			ctqRqstService.check(reqmst);
		}
		
		String resultMsg = null;
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resultMsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);
				
		return new IBSResultVO<WaqMstr>(reqmst, result, resultMsg, action);
	}
	
		
	@RequestMapping("/dq/criinfo/ctq/delVrfCtqList.do")
	@ResponseBody
	public IBSResultVO<WaqCtqVO> delVrfCtqList(@RequestBody WaqCtqVOs delVOs, @ModelAttribute WaqMstr mstr, Locale locale) {
		
		logger.debug("data : {}\nsearch : {}", delVOs);
		Map<String, String> resultMap = ctqRqstService.delRegCtqList(delVOs.get("data"), mstr);
		int result = Integer.parseInt(resultMap.get("result"));
		
		//검증로직
		if(result > 0){
			ctqRqstService.check(mstr);
		}
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		String resultMsg = null;
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			resultMsg = message.getMessage("ERR.DEL", null, locale);
		}
    	
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	return new IBSResultVO<WaqCtqVO>(resultMap, result, resultMsg, action);
    }
	
	/** 중요정보항목 요청서 결재처리... @return meta
     * @throws Exception */
    @RequestMapping("/dq/criinfo/ctq/approveCtq.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveDqi(@RequestBody WaqCtqVOs data, WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//결재라인이 없을경우는 자동결재가 처리되야하므로 결재자인지 확인하는 단계를 생략한다...
		if(reqmst.getAprLvl() != 0) {
			//결재자인지 확인한다.
			Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
			if(!checkapprove) {
				resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
				return new IBSResultVO<WaqMstr>(-1, resmsg, null);
			}
		}

		ArrayList<WaqCtqVO> list = data.get("data");

		int result = ctqRqstService.approve(reqmst, list);

		//stndWordRqstServie.check(reqmst);

//		int result = stndWordRqstServie.regStndWordRqstlist(list);


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
		
		if(result == 0) {
    		mService.sendMsg(reqmst.getAprvUserId(), reqmst.getRqstUserId(), reqmst.getRqstNm() + "결재처리되었습니다.", "", "kindName");
    	}

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
    /** 중요정보항목 변경대상 추가..(팝업에서 호출함)
	 * meta
	 */
	@RequestMapping("/dq/criinfo/ctq/regWam2WaqCtq.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2WaqlistCtq(@RequestBody WaqCtqVOs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqCtqVO> list = data.get("data");
		int result = ctqRqstService.regWam2Waq(reqmst, list);
		ctqRqstService.check(reqmst);

//		int result = stndWordRqstServie.regStndWordRqstlist(list);
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
    
	
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		codeMap = new HashMap<String, Object>();
		
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
//						codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		return codeMap;
	}
	
}
