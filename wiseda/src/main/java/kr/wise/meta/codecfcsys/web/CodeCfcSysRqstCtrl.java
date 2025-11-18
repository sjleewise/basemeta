/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysRqstCtrl.java
 * 2. Package : kr.wise.meta.model.web
 * 3. Comment : 코드분류체계 등록 요청 컨트롤러...
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 1. 오후 4:22:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 1. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.CStreamGobbler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.codecfcsys.service.CodeCfcSysItemRqstService;
import kr.wise.meta.codecfcsys.service.CodeCfcSysRqstService;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItem;

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
 * 2. FileName  : CodeCfcSysRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 코드분류체계 등록 요청 컨트롤러...
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 7. 29. 오후 4:22:41
 * </PRE>
 */
@Controller("CodeCfcSysRqstCtrl")
public class CodeCfcSysRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;


	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private CodeCfcSysRqstService codeCfcSysRqstService;
	
	@Inject
	private CodeCfcSysItemRqstService codeCfcSysItemRqstService;
	
	@Inject
	private RequestApproveService requestApproveService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	static class WaqCodeCfcSysVO extends HashMap<String, ArrayList<WaqCodeCfcSys>> {}
	
	static class WaqCodeCfcSysItemVO extends HashMap<String, ArrayList<WaqCodeCfcSysItem>> {}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//공통코드 - IBSheet Combo Code용
		//검토상태코드
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
	
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
	
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));

		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
	
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		//코드분류체계유형
		List<CodeListVo> codeCfcSysCd = codeListService.getCodeList("clSystmTyCode");
		codeMap.put("codeCfcSysCd", codeCfcSysCd);
		codeMap.put("codeCfcSysCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(codeCfcSysCd)));
		
		//코드분류체계 항목
		List<CodeListVo> codeCfcSysItemCd = codeListService.getCodeList("clSystmTyIemCode");
		codeMap.put("codeCfcSysItemCd", codeCfcSysItemCd);
		codeMap.put("codeCfcSysItemCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(codeCfcSysItemCd)));
		
				
		return codeMap;
	}

    /** 코드분류체계 요청서 입력 폼.... @throws Exception meta */
    @RequestMapping("/meta/codecfcsys/codecfcsys_rqst.do")
    public String goCodeCfcSysRqstPage(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("CCS");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/codecfcsys/codecfcsys_rqst";

    }

    /** 코드분류체계 테이블 요청 상세 정보 조회 @return meta */
    @RequestMapping("/meta/codecfcsys/ajaxgrid/codecfcsys_dtl_rqst.do")
    public String getpdmtblDetail(WaqCodeCfcSysItem searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqCodeCfcSys resultVo =  codeCfcSysRqstService.getCodeCfcSysDetail(searchVo);
    		model.addAttribute("result", resultVo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/codecfcsys/codecfcsys_dtl";

    }


    /** 코드분류체계 테이블 요청 리스트 조회 @return meta */
    @RequestMapping("/meta/codecfcsys/getcodecfcsysrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqCodeCfcSys> getCodeCfcSysRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqCodeCfcSys> list = codeCfcSysRqstService.getRqstList(search);


		return new IBSheetListVO<WaqCodeCfcSys>(list, list.size());
	}

    /** 코드분류체계 항목 요청 리스트 등록... @throws Exception meta */
    @RequestMapping("/meta/codecfcsys/regCodeCfcSysItemRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regCodeCfcSysRqstList(@RequestBody WaqCodeCfcSysItemVO data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("\nreqmst:{} ", reqmst);
		logger.debug("\ndata:{}", data);
		
		ArrayList<WaqCodeCfcSysItem> list = data.get("data");

		//코드분류체계 기존정보 삭제 후 새로 등록되도록...
		codeCfcSysRqstService.deleteOldCodeCfcSysInfo(reqmst);
		codeCfcSysItemRqstService.deleteOldCodeCfcSysItemInfo(reqmst);
		
		int result = codeCfcSysItemRqstService.register(reqmst, list);

		result += codeCfcSysRqstService.check(reqmst);

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
    
    
  /** 코드분류체계 아이템 요청 리스트 조회... @return meta */
  @RequestMapping("/meta/codecfcsys/getcodecfcsysitemrqstlist.do")
  @ResponseBody
  public IBSheetListVO<WaqCodeCfcSysItem> getCodeCfcSysRqstItemList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqCodeCfcSysItem> list = codeCfcSysRqstService.getCodeCfcSysRqstItemList(search);

		return new IBSheetListVO<WaqCodeCfcSysItem>(list, list.size());
	}

  /** 코드분류체계 단건 (수정)저장... @throws Exception meta */
  @RequestMapping("/meta/codecfcsys/regCodeCfcSysRqst.do")
  @ResponseBody
	public IBSResultVO<WaqMstr> regCodeCfcSysRqst(@RequestBody WaqCodeCfcSysVO data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("\nreqmst:{} ", reqmst);
		logger.debug("\ndata:{}", data);
		
		ArrayList<WaqCodeCfcSys> list = data.get("data");
		
		
		int result = codeCfcSysRqstService.register(reqmst, list);
		
		
		result += codeCfcSysRqstService.check(reqmst);

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
  

    /** 코드분류체계 테이블 요청 리스트 삭제.... @throws Exception meta */
    @RequestMapping("/meta/codecfcsys/delCodeCfcSysRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delCodeCfcSysRqstList(@RequestBody WaqCodeCfcSysVO data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqCodeCfcSys> list = data.get("data");

		int result = codeCfcSysRqstService.delCodeCfcSysRqst(reqmst, list);
		result += codeCfcSysRqstService.check(reqmst);
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

    /** 코드분류체계 변경대상 요청 리스트 추가... @throws Exception meta */
    @RequestMapping("/meta/codecfcsys/regWam2WaqCodeCfcSys.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqCodeCfcSysVO data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqCodeCfcSys> list = data.get("data");

		int result = codeCfcSysRqstService.regWam2Waq(reqmst, list);

		result += codeCfcSysRqstService.check(reqmst);

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

    /** 코드분류체계 변경대상 팝업창 호출... @return meta */
    @RequestMapping("/meta/codecfcsys/popup/codecfcsys_pop.do")
    public String goPdmTblRqstxls(@ModelAttribute("search") WaqCodeCfcSys search) {

    	return "/meta/codecfcsys/popup/codecfcsys_pop";
    }

    
    /** 코드분류체계 테이블 승인... @throws Exception meta */
    @RequestMapping("/meta/codecfcsys/approveCodeCfcSys.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveCodeCfcSys(@RequestBody WaqCodeCfcSysVO data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqCodeCfcSys> list = data.get("data");

		int result  = codeCfcSysRqstService.approve(reqmst, list);

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
		
		//코드분류체계 개발DB 이관 SHELL 호출
//		if(reqmst.getRqstStepCd().equals("A") ){
//			codecfcsystsf("D");
//		}

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
    
    /**  코드분류체계 엑셀업로드 팝업 */
    @RequestMapping("/meta/codecfcsys/popup/codecfcsys_xls.do")
    public String gostndwordxls(@ModelAttribute("search") WaqCodeCfcSysItem search) {
    	return "/meta/codecfcsys/popup/codecfcsys_xls";
    }
    

    /** 코드분류체계 항목 삭제요청 - IBSheet 용...
	 * @throws Exception */
	@RequestMapping("/meta/codecfcsys/delCodeCfcSysItemRqstList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delCodeCfcSysItemRqstList(@RequestBody WaqCodeCfcSysItemVO data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqCodeCfcSysItem> list = data.get("data");

		int result = codeCfcSysItemRqstService.delCodeCfcSysItemRqstList(reqmst, list);
		result += codeCfcSysRqstService.check(reqmst);

		String resmsg ;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
	

	/** 
	  * 기술표준원 개발DB 코드분류체계 이관
	  * 사용법 ->CodeCfcSys.cmd DDL대상구분코드(D, T, R)
	  * 개발 테스트 운영 구분 
	  * 공통코드 DDL대상구분코드 와 동일
	  * 개발 : D
	  * 테스트 : T
	  * 운영 : R
	 */
    public void codecfcsystsf(String trgDcd) throws Exception{
		//코드분류체계이관 shell 경로
		String codecfcsyscmd = message.getMessage(WiseConfig.CODECFCSYS_CMD, null, Locale.getDefault()); 
		Process process = Runtime.getRuntime().exec(codecfcsyscmd + " "+ trgDcd);
		
		// 서버가 윈도우인 경우 버퍼 해소
		CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
		CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");

		out.start();
		error.start();

		int exitValue = process.waitFor();
		logger.debug("ExitValue : " + exitValue);
		
		if(exitValue != 0) {
			throw new Exception("Runtime error.("+exitValue+")");
		}
    }
    
}
