/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ImRslRqstCtrl.java
 * 2. Package : kr.wise.dq.impv.web
 * 3. Comment :
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 10. 오후 3:22:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 10. :            : 신규 개발.
 */
package kr.wise.dq.impv.web;

import java.util.ArrayList;
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
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.impv.service.ImRslRqstService;
import kr.wise.dq.impv.service.WaqImActMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ImRslRqstCtrl.java
 * 3. Package  : kr.wise.dq.impv.web
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 10. 오후 3:22:20
 * </PRE>
 */
@Controller
public class ImRslRqstCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

//	IBSJsonSearch ibsresult = new IBSJsonSearch();
//	IBSResult ibsres = new IBSResult();

	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WaqImActMstrs extends HashMap<String, ArrayList<WaqImActMstr>> { }

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;

	@Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ImRslRqstService imRslRqstService;

	/* 결재라인조회*/
	@Inject
	private ApproveLineServie approveLineServie;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


	/** 개선결과 조회 - IBSheet */
	/** meta */
	@RequestMapping("/dq/impv/getImRsl.do")
	@ResponseBody
	public IBSheetListVO<WaqImActMstr> selectRslList(@ModelAttribute WaqImActMstr search){
		logger.debug("{}", search);
		List<WaqImActMstr> list = imRslRqstService.getRslList(search);
		return new IBSheetListVO<WaqImActMstr>(list, list.size());
	}



	/** 개선결과 등록요청 화면 */
	@RequestMapping("/dq/impv/imrsl_rqst.do")
	public String formpage(WaqMstr reqmst, ModelMap model) throws Exception {
		logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("IMR");
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
		return "/dq/impv/imrsl_rqst";
	}

	/** 개선결과 코드 조회 */
	/** meta */
	@RequestMapping("/dq/impv/impl_rqst_imRslCd.do")
	@ResponseBody
	public IBSheetListVO<CodeListVo> selectimRslCd() {
		logger.debug("개선계획 코드 조회");
		List<CodeListVo> list = cmcdCodeService.getCodeList("IM_RSL_CD");

		return new IBSheetListVO<CodeListVo>(list, list.size());
	}

	/** 개선불가 코드 조회 */
	/** meta */
	@RequestMapping("/dq/impv/impl_rqst_imIlCd.do")
	@ResponseBody
	public IBSheetListVO<CodeListVo> selectimIlCd() {
		logger.debug("개선불가 코드 조회");
		List<CodeListVo> list = cmcdCodeService.getCodeList("IM_IL_CD");

		return new IBSheetListVO<CodeListVo>(list, list.size());
	}

	/** 개선결과 입력대상항목 조회 - IBSheet */
	/** meta */
	@RequestMapping("/dq/impv/getCanImRsl.do")
	@ResponseBody
	public IBSheetListVO<WaqImActMstr> selectList(@ModelAttribute WaqImActMstr search){
		logger.debug("{}", search);
		List<WaqImActMstr> list = null;
		
		list = imRslRqstService.getImRslList(search);
		return new IBSheetListVO<WaqImActMstr>(list, list.size());
	}

	/** 개선결과 등록요청 -  결과는 IBSResult Json 리턴 */
	/**meta */
	@RequestMapping("/dq/impv/regImRslRqstList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> register( @RequestBody WaqImActMstrs saveVOs, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("data : {}", saveVOs);

		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		logger.debug("USER : {}", user);
		//검증사용자ID 셋팅
		reqmst.setWritUserId(user.getUniqId());
		//요청사용자ID 셋팅
		//결재로직 개발 완료 후 추가 개발 요건 발생
		reqmst.setRqstUserId(user.getUniqId());

		//등록 결과를 맵으로 받는다.
		//result : 등록 결과
		//rqstNo : 요청서 번호
		int result = imRslRqstService.register(reqmst, saveVOs.get("data"));
		//int result = Integer.parseInt(resultMap.get("result"));

		//검증로직
		if(result > 0){
			imRslRqstService.check(reqmst);
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

	/** 개선계획 등록요청건 조회 */
	/** @param search
	/** @return meta */
	@RequestMapping("/dq/impv/getVrfImRslListIBS.do")
	@ResponseBody
	public IBSheetListVO<WaqImActMstr> getVrfImPlListIBS(@ModelAttribute WaqMstr search) {
		logger.debug("searchVO {} : ", search);
		List<WaqImActMstr> list = imRslRqstService.getVrfImRslListIBS(search);

		return new IBSheetListVO<WaqImActMstr>(list, list.size());
	}

	 /** 개선결과 등록요청서 결재처리... @return meta
     * @throws Exception */
    @RequestMapping("/dq/impv/approveImRsl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveImRsl(@RequestBody WaqImActMstrs data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqImActMstr> list = data.get("data");

		int result = imRslRqstService.approve(reqmst, list);

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

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

	/* 이력조회 */
	@RequestMapping("/dq/impv/getImplHstLst.do")
	@ResponseBody
	public IBSheetListVO<WaqImActMstr> getImplHstLst(String anaJobId, String anaStrDtm) {

		logger.debug("anaJobId:{}", anaJobId);
		logger.debug("anaStrDtm:{}", anaStrDtm);
		
		List<WaqImActMstr> list = imRslRqstService.getImplHstLst(anaJobId, anaStrDtm);

		return new IBSheetListVO<WaqImActMstr>(list, list.size());
	}

	/* 이력조회 */
	@RequestMapping("/dq/impv/getImrslHstLst.do")
	@ResponseBody
	public IBSheetListVO<WaqImActMstr> getImrslHstLst(String anaJobId, String anaStrDtm) {

		logger.debug("anaJobId:{}", anaJobId);
		logger.debug("anaStrDtm:{}", anaStrDtm);
		
		List<WaqImActMstr> list = imRslRqstService.getImrslHstLst(anaJobId, anaStrDtm);

		return new IBSheetListVO<WaqImActMstr>(list, list.size());
	}

	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);

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

		//실행차수
		List<CodeListVo> anaDgr = codelistService.getCodeList(CodeListAction.anaDgr);
		codeMap.put("anaDgrCd", anaDgr);
		
		List<CodeListVo> brAnaDgr = codelistService.getCodeList(CodeListAction.bizrAnaDgr);
		codeMap.put("brAnaDgrCd", brAnaDgr);
		
		logger.debug("brAnaDgr:{}",brAnaDgr.toString());
		
		return codeMap;
	}

}
