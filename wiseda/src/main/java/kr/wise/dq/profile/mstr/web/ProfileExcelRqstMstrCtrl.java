/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileCtrl.java
 * 2. Package : kr.wise.dq.criinfo.profile.web
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:29:21
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.profile.mstr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.cmm.service.MessengerService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.ProfileExcelRqstMstrService;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.dq.profile.tblunq.service.WaqPrfUnqColVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProfileExcelRqstMstrCtrl.java
 * 3. Package  : kr.wise.dq.profile.web
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:29:21
 * </PRE>
 */
@Controller
public class ProfileExcelRqstMstrCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	
	//프로파일 MSTR
	//static class WaqPrfMstrVOs extends HashMap<String, ArrayList<WaqPrfMstrVO>> { }
	
	//컬럼분석
	static class WaqPrfColAnaVOs extends HashMap<String, ArrayList<WaqPrfColAnaVO>> { }
	
	//유효값분석
	static class WaqPrfEfvaUserDfVOs extends HashMap<String, ArrayList<WaqPrfEfvaUserDfVO>> { }
	
	//날짜형식 분석
	static class WaqPrfDtfrmAnaVOs extends HashMap<String, ArrayList<WaqPrfDtfrmAnaVO>> { }
	
	//범위 분석
	static class WaqPrfRngAnaVOs extends HashMap<String, ArrayList<WaqPrfRngAnaVO>> { }
	
	//유효패턴 분석
	static class WaqPrfPtrAnaVOs extends HashMap<String, ArrayList<WaqPrfPtrAnaVO>> { }
	
	//관계분석
	static class WaqPrfRelColVOs extends HashMap<String, ArrayList<WaqPrfRelColVO>> { }
	
	//중복분석
	static class WaqPrfUnqColVOs extends HashMap<String, ArrayList<WaqPrfUnqColVO>> { }
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;
	
	/* RQST_NO 채번*/
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
	private ProfileExcelRqstMstrService prfExlRqstMstrService; 
	
	@Inject
	private MessengerService mService;
	
	//프로파일 마스터
	@Inject
	private ProfileMstrService profileMstrService;
	
	@Inject
	private ProfileMstrCtrl profileMstrCtrl;
	
	
	//프로파일 엑셀등록  화면이동
	@RequestMapping("/dq/profile/prfexl_rqst.do")
	public String prfExcelRqstForm(WaqMstr reqmst, ModelMap model) throws Exception{
    	
		logger.debug(" reqmst {} "+reqmst);
		
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("PRF");
    		reqmst.setBizDtlCd("PT01");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//BIZ_DTL_CD 이걸 가져와야 하네...
//    		if(reqmst.getBizDtlCd() == null || reqmst.getBizDtlCd().isEmpty()) {
			if(UtilString.isNull(reqmst.getBizDtlCd()) || reqmst.getBizDtlCd().equals("null")) {
    			String prfKndCd = prfExlRqstMstrService.getPrfKndCd(reqmst.getRqstNo());
        		reqmst.setBizDtlCd(prfKndCd);
    		}
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

    	return "/dq/profile/prfexl_rqst";
	}
	
	//프로파일 엑셀등록  화면이동
	@RequestMapping("/dq/profile/ajaxgrid/getRqstForm.do")
	@ResponseBody
	public WaqMstr getRqstForm(@ModelAttribute("search") WaqMstr reqmst) throws Exception{
		
		//요청번호가 없을 경우 요청번호를 먼저 채번한다.
		String reqid = requestIdGnrService.getNextStringId();
		reqmst.setRqstNo(reqid);
		reqmst.setBizDcd("PRF");
		reqmst.setRqstNm("");
		reqmst.setBizDtlCd(reqmst.getBizDtlCd());
		reqmst.setRqstStepCd("N");
		
		//결재라인 결재레벨 조회
		//결재레벨 미존재 시 검증 후 등록 버튼 활성화
		WaaRqstBizApr aprSearch = new WaaRqstBizApr();
		aprSearch.setBizDcd(reqmst.getBizDcd());
		List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(aprSearch);
		logger.debug("list SIZE : ", list.size());
		reqmst.setAprLvl(list.size());
		
		return reqmst;
		
	}
	
//	//프로파일 엑셀등록  화면이동
	@RequestMapping("/dq/govPrf/ajaxgrid/getRqstForm.do")
	@ResponseBody
	public WaqMstr getGovRqstForm(@ModelAttribute("search") WaqMstr reqmst) throws Exception{
		
		//요청번호가 없을 경우 요청번호를 먼저 채번한다.
		String reqid = requestIdGnrService.getNextStringId();
		reqmst.setRqstNo(reqid);
		reqmst.setBizDcd("DRR");
		reqmst.setRqstNm("");
		reqmst.setBizDtlCd(reqmst.getBizDtlCd());
		reqmst.setRqstStepCd("N");
		
		//결재라인 결재레벨 조회
		//결재레벨 미존재 시 검증 후 등록 버튼 활성화
		WaaRqstBizApr aprSearch = new WaaRqstBizApr();
		aprSearch.setBizDcd(reqmst.getBizDcd());
		List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(aprSearch);
		logger.debug("list SIZE : ", list.size());
		reqmst.setAprLvl(list.size());
		
		return reqmst;
		
	}
	
	/*컬럼분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPC01Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPC01Lst(WaqMstr reqmst, @RequestBody WaqPrfColAnaVOs pc01VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
		logger.debug(" pc01VOs {} " + pc01VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfColAnaVO> list = pc01VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	
	/*유효값분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPC02Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPC02Lst(WaqMstr reqmst, @RequestBody WaqPrfEfvaUserDfVOs pc02VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
		logger.debug(" pc02VOs {} " + pc02VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfEfvaUserDfVO> list = pc02VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	
	/*날짜분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPC03Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPC03Lst(WaqMstr reqmst, @RequestBody WaqPrfDtfrmAnaVOs pc03VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
//		logger.debug(" pc03VOs {} " + pc03VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfDtfrmAnaVO> list = pc03VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	
	/*범위분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPC04Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPC04Lst(WaqMstr reqmst, @RequestBody WaqPrfRngAnaVOs pc04VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
		logger.debug(" pc04VOs {} " + pc04VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfRngAnaVO> list = pc04VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	
	/*유효패턴분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPC05Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPC05Lst(WaqMstr reqmst, @RequestBody WaqPrfPtrAnaVOs pc05VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
		logger.debug(" pc05VOs {} " + pc05VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfPtrAnaVO> list = pc05VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	
	/*관계분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPT01Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPT01Lst(WaqMstr reqmst, @RequestBody WaqPrfRelColVOs pt01VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
		logger.debug(" pt01VOs {} " + pt01VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfRelColVO> list = pt01VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	/*중복분석 엑셀 일괄등록 */
	@RequestMapping("/dq/profile/regExlPT02Lst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regExlPT02Lst(WaqMstr reqmst, @RequestBody WaqPrfUnqColVOs pt02VOs, Locale locale ) throws Exception{
		
		logger.debug(" reqmst {} " + reqmst);
		logger.debug(" pt02VOs {} " + pt02VOs.get("data"));
		
		int result = 0;
		
		ArrayList<WaqPrfUnqColVO> list = pt02VOs.get("data");
		
		result = registerPorfile(reqmst, list);
		
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
	
	/*프로파일분석 WAQ 등록 및 검증 */
	public int registerPorfile(WaqMstr reqmst,  List<?> reglist ) throws Exception{
		int result = 0;
		//컬럼분석 정보 저장
		result = prfExlRqstMstrService.register(reqmst, reglist);
		//검증로직
		prfExlRqstMstrService.check(reqmst);
		
		return result;
	}
	
	/*컬럼분석 엑셀 삭제 */
	@RequestMapping("/dq/profile/delPrfExlLst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delPrfExlLst(@RequestParam("joinkey") String checkjoin, WaqMstr reqmst, Locale locale ) throws Exception{
		
		logger.debug("reqmst:{} \ndelKey:{}", reqmst, checkjoin);
		
		int result = prfExlRqstMstrService.delPrfExlLst(reqmst, checkjoin);
		//검증로직
		prfExlRqstMstrService.check(reqmst);
		
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
	
	/** 컬럼분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPC01.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approvePrfPC01(@RequestBody WaqPrfColAnaVOs pc01VOs, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, pc01VOs);
		ArrayList<WaqPrfColAnaVO> list = pc01VOs.get("data");
		IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
		return iBSResultVO;
		
	}
    
    /** 유효값분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPC02.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePrfPC02(@RequestBody WaqPrfEfvaUserDfVOs pc02VOs, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, pc02VOs);
    	ArrayList<WaqPrfEfvaUserDfVO> list = pc02VOs.get("data");
    	IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
    	return iBSResultVO;
    }
    
    /** 날짜형식분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPC03.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePrfPC03(@RequestBody WaqPrfDtfrmAnaVOs pc03VOs, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, pc03VOs);
    	ArrayList<WaqPrfDtfrmAnaVO> list = pc03VOs.get("data");
    	IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
    	return iBSResultVO;
    }
    
    /** 범위분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPC04.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePrfPC04(@RequestBody WaqPrfRngAnaVOs pc04VOs, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, pc04VOs);
    	ArrayList<WaqPrfRngAnaVO> list = pc04VOs.get("data");
    	IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
    	return iBSResultVO;
    }
    
    /** 유효패턴분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPC05.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePrfPC05(@RequestBody WaqPrfPtrAnaVOs pc05VOs, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, pc05VOs);
    	ArrayList<WaqPrfPtrAnaVO> list = pc05VOs.get("data");
    	IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
    	return iBSResultVO;
    }
    
    /** 관계분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPT01.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePrfPT01(@RequestBody WaqPrfRelColVOs pt01VOs, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, pt01VOs);
    	ArrayList<WaqPrfRelColVO> list = pt01VOs.get("data");
    	IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
    	return iBSResultVO;
    }
    
    /** 중복분석 요청서 결재처리 */
    @RequestMapping("/dq/profile/approvePrfPT02.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approvePrfPT02(@RequestBody WaqPrfUnqColVOs pt02VOs, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, pt02VOs);
    	ArrayList<WaqPrfUnqColVO> list = pt02VOs.get("data");
    	IBSResultVO<WaqMstr> iBSResultVO = approveProfile(reqmst, list, locale);
    	return iBSResultVO;
    }
    
    
    /** 프로파일 요청서 결재처리... 
     * @throws Exception */
    public IBSResultVO<WaqMstr> approveProfile(WaqMstr reqmst, List<?> prflist, Locale locale) throws Exception {
    	
    	logger.debug("reqmst:{} \ndata:{}", reqmst, prflist);
    	String prfKndCd = reqmst.getBizDtlCd();
    	
    	ArrayList<?> list = null;
    	
    	//컬럼분석
    	if(prfKndCd.equals("PC01")){
    		list = (ArrayList<WaqPrfColAnaVO>) prflist;
    	}
    	//유효값분석
    	else if(prfKndCd.equals("PC02")){
    		list = (ArrayList<WaqPrfEfvaUserDfVO>) prflist;
    	}
    	//날짜형식분석
    	else if(prfKndCd.equals("PC03")){
    		list = (ArrayList<WaqPrfDtfrmAnaVO>) prflist;
    	}
    	//범위분석
    	else if(prfKndCd.equals("PC04")){
    		list = (ArrayList<WaqPrfRngAnaVO>) prflist;
    	}
    	//유효패턴분석
    	else if(prfKndCd.equals("PC05")){
    		list = (ArrayList<WaqPrfPtrAnaVO>) prflist;
    	}
    	//관계분석
    	else if(prfKndCd.equals("PT01")){
    		list = (ArrayList<WaqPrfRelColVO>) prflist;
    	}
    	//중복분석
    	else if(prfKndCd.equals("PT02")){
    		list = (ArrayList<WaqPrfUnqColVO>) prflist;
    	}
    	
    	logger.debug("list:{} \ndata:{}", list);
    	
    	int result = prfExlRqstMstrService.approve(reqmst, list);
    	
//    	if(result > 0) {
//    		WamPrfMstrVO prfmstrVO = null;
//    		SqlGeneratorVO sqlVO = null;
//    		
//    		for (WamPrfMstrVO saveVo : (ArrayList<WamPrfMstrVO>)list) {
//    			if(!saveVo.getPrfKndCd().equals("PC01")) {
//    				prfmstrVO = profileMstrService.getSqlGenDbmsInfoByPrfId(saveVo.getPrfId());
//    				sqlVO = profileMstrCtrl.getSqlGen(prfmstrVO);
//    				saveVo.setErrCntSql(sqlVO.getErrorPattern());
//    				result += profileMstrService.saveErrCntSql(saveVo);
//    			}
//    		}
//    	}
    	
    	String resmsg;
    	
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
    
    //220223 데이터규칙 등록  화면이동
  	@RequestMapping("/dq/govPrf/govPrfexl_rqst.do")
  	public String govPrfExcelRqstForm(WaqMstr reqmst, ModelMap model) throws Exception{
      	
  		logger.debug(" reqmst {} "+reqmst);
  		
      	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
      	if (!StringUtils.hasText(reqmst.getRqstNo())){
      		reqmst.setBizDcd("DRR");
      		reqmst.setBizDtlCd("PT01");
      		reqmst = requestMstService.getBizInfoInit(reqmst);
      	} else {
      		//BIZ_DTL_CD 이걸 가져와야 하네...
      		String prfKndCd = prfExlRqstMstrService.getPrfKndCd(reqmst.getRqstNo());
      		reqmst.setBizDtlCd(prfKndCd);
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

      	return "/dq/govPrf/govPrfexl_rqst";
  	}
    
    /** 컬럼분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPC01ExlLst.do")
	@ResponseBody
	public IBSheetListVO<WaqPrfColAnaVO> getPrfPC01ExlLst(@ModelAttribute WaqMstr search) {
		logger.debug("searchVO:{}", search);
		List<WaqPrfColAnaVO> list = null;
		list = prfExlRqstMstrService.getPrfExlPc01Lst(search);
		return new IBSheetListVO<WaqPrfColAnaVO>(list, list.size());
	}
	
    /** 유효값분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPC02ExlLst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfEfvaUserDfVO> getPrfPC02ExlLst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfEfvaUserDfVO> list = prfExlRqstMstrService.getPrfExlPc02Lst(search);
    	return new IBSheetListVO<WaqPrfEfvaUserDfVO>(list, list.size());
    }
    
    /** 날짜형식분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPC03ExlLst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfDtfrmAnaVO> getPrfPC03ExlLst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfDtfrmAnaVO> list = null;
		list = prfExlRqstMstrService.getPrfExlPc03Lst(search);
    	return new IBSheetListVO<WaqPrfDtfrmAnaVO>(list, list.size());
    }
    
    /** 범위분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPC04ExlLst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfRngAnaVO> getPrfPC04ExlLst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfRngAnaVO> list = null;
    	list = prfExlRqstMstrService.getPrfExlPc04Lst(search);
    	return new IBSheetListVO<WaqPrfRngAnaVO>(list, list.size());
    }
    
    /** 유효패턴분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPC05ExlLst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfPtrAnaVO> getPrfPC05ExlLst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfPtrAnaVO> list = null;
    	list = prfExlRqstMstrService.getPrfExlPc05Lst(search);
    	return new IBSheetListVO<WaqPrfPtrAnaVO>(list, list.size());
    }
    
    /** 관계분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPT01ExlLst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfRelColVO> getPrfPT01ExlLst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfRelColVO> list = null;
    	list = prfExlRqstMstrService.getPrfExlPt01Lst(search);
    	return new IBSheetListVO<WaqPrfRelColVO>(list, list.size());
    }
    
    /** 중복분석 요청 리스트 조회 - IBSheet JSON  */
    @RequestMapping("/dq/profile/getPrfPT02ExlLst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfUnqColVO> getPrfPT02ExlLst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfUnqColVO> list = null;
    	list = prfExlRqstMstrService.getPrfExlPt02Lst(search);
    	return new IBSheetListVO<WaqPrfUnqColVO>(list, list.size());
    }
    
    /** 메타연동 조회 - IBSheet JSON  */
	//범위분석 메타 연동 대상 조회
    @RequestMapping("/dq/profile/getPrfMetaSrchPc04Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfRngAnaVO> getPrfMetaSrchPc04Lst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfRngAnaVO> list = null;
    	list = prfExlRqstMstrService.getPrfMetaSrchPc04Lst(search);
    	return new IBSheetListVO<WaqPrfRngAnaVO>(list, list.size());
    }
    
	//날짜분석 메타 연동 대상  조회
    @RequestMapping("/dq/profile/getPrfMetaSrchPc03Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfDtfrmAnaVO> getPrfMetaSrchPc03Lst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfDtfrmAnaVO> list = null;
    	list = prfExlRqstMstrService.getPrfMetaSrchPc03Lst(search);
    	return new IBSheetListVO<WaqPrfDtfrmAnaVO>(list, list.size());
    }
    
	//유효값분석 메타 연동 대상  조회
    @RequestMapping("/dq/profile/getPrfMetaSrchPc02Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfEfvaUserDfVO> getPrfMetaSrchPc02Lst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
	    		
    	List<WaqPrfEfvaUserDfVO> list = null;
    	list = prfExlRqstMstrService.getPrfMetaSrchPc02Lst(search);
    	return new IBSheetListVO<WaqPrfEfvaUserDfVO>(list, list.size());
    }
    
	//관계분석 메타 연동 대상  조회
    @RequestMapping("/dq/profile/getPrfMetaSrchPt01Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfRelColVO> getPrfMetaSrchPt01Lst(@ModelAttribute WaqMstr search) {
    	logger.debug("searchVO:{}", search);
	    		
    	List<WaqPrfRelColVO> list = null;
    	list = prfExlRqstMstrService.getPrfMetaSrchPt01Lst(search);
    	return new IBSheetListVO<WaqPrfRelColVO>(list, list.size());
    }
    /** 메타연동 조회 - IBSheet JSON  */
    
  //범위분석 메타 연동 대상 조회
//    @RequestMapping("/dq/profile/getPrfWatPc04Lst.do")
//    @ResponseBody
//    public IBSheetListVO<WaqPrfRngAnaVO> getPrfWatPc04Lst(@ModelAttribute WaqMstr search,@RequestParam("dbcColNm") String dbcColNm,@RequestParam("paramVal") String paramVal) {
//    	logger.debug("searchVO:{}", search);
//    	List<WaqPrfRngAnaVO> list = null;
//    	list = prfExlRqstMstrService.getPrfWatPc04Lst(search, dbcColNm, paramVal);
//    	return new IBSheetListVO<WaqPrfRngAnaVO>(list, list.size());
//    }
    
	//날짜분석 메타 연동 대상  조회
    @RequestMapping("/dq/profile/getPrfWatPc03Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfDtfrmAnaVO> getPrfWatPc03Lst(@ModelAttribute WaqMstr search,@RequestParam("dbConnTrgId") String dbConnTrgId,@RequestParam("dbSchId") String dbSchId,@RequestParam("dbcColNm") String dbcColNm,@RequestParam("paramVal") String paramVal) {
    	logger.debug("searchVO:{}", search);
    	List<WaqPrfDtfrmAnaVO> list = null;
    	if(paramVal.toUpperCase().indexOf("Y") != -1 
    			|| paramVal.toUpperCase().indexOf("M") != -1 
    			|| paramVal.toUpperCase().indexOf("D") != -1) {
    		paramVal = paramVal.length() + "";
    	} else if(paramVal == null || paramVal.equals("")) {
    		return null;
    	}
    	
    	list = prfExlRqstMstrService.getPrfWatPc03Lst(dbConnTrgId, dbSchId, dbcColNm, paramVal);
    	return new IBSheetListVO<WaqPrfDtfrmAnaVO>(list, list.size());
    }
    
	//유효값분석 메타 연동 대상  조회
    @RequestMapping("/dq/profile/getPrfWatPc02Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfEfvaUserDfVO> getPrfWatPc02Lst(@ModelAttribute WaqMstr search,@RequestParam("dbConnTrgId") String dbConnTrgId,@RequestParam("dbSchId") String dbSchId,@RequestParam("dbcColNm") String dbcColNm,@RequestParam("paramVal") String paramVal) {
    	logger.debug("searchVO:{}", search);
	    		
    	List<WaqPrfEfvaUserDfVO> list = null;
    	list = prfExlRqstMstrService.getPrfWatPc02Lst(dbConnTrgId, dbSchId, dbcColNm, paramVal);
    	return new IBSheetListVO<WaqPrfEfvaUserDfVO>(list, list.size());
    }
    
  //유효값분석 메타 연동 대상  조회
    @RequestMapping("/dq/profile/getPrfWatPc05Lst.do")
    @ResponseBody
    public IBSheetListVO<WaqPrfEfvaUserDfVO> getPrfWatPc05Lst(@ModelAttribute WaqMstr search,@RequestParam("dbConnTrgId") String dbConnTrgId,@RequestParam("dbSchId") String dbSchId,@RequestParam("dbcColNm") String dbcColNm,@RequestParam("paramVal") String paramVal) {
    	logger.debug("searchVO:{}", search);
	    		
    	List<WaqPrfEfvaUserDfVO> list = null;
    	list = prfExlRqstMstrService.getPrfWatPc05Lst(dbConnTrgId, dbSchId, dbcColNm, paramVal);
    	return new IBSheetListVO<WaqPrfEfvaUserDfVO>(list, list.size());
    }
    
    
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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드
		//프로파일종류코드
		codeMap.put("prfKndCd",cmcdCodeService.getCodeList("PRF_KND_CD"));
		//유효값유형
		codeMap.put("efvaAnaKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("EFVA_ANA_KND_CD")));
		//날짜분석유형코드
		codeMap.put("dateFrmCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DATE_FRM_CD")));
		//범위분석코드
		codeMap.put("rngOprCdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RNG_OPR_CD")));
		//범위분석 연결자코드
		codeMap.put("rngCncibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RNG_CNC_CD")));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		

		return codeMap;
	}
}
