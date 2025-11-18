package kr.wise.meta.ddl.web;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
//import kr.wise.meta.ddl.service.DdlEtcObjRqstService;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
//import kr.wise.meta.ddl.service.DdlPartRqstService;
import kr.wise.meta.ddl.service.DdlSeqRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WamDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlSeq;

/**
 * <PRE>
 * 1. ClassName : DdlSeqRqstCtrl
 * 2. FileName  : DdlSeqRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 02.
 * </PRE>
 */
@Controller("DdlSeqRqstCtrl")
public class DdlSeqRqstCtrl {

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

	//private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private DdlSeqRqstService ddlSeqRqstService;
	@Inject
	private DdlTblRqstService ddlTblRqstService;
	@Inject
	private DdlIdxRqstService ddlIdxRqstService;
//	@Inject
//	private DdlPartRqstService ddlPartRqstService;
//	@Inject
//	private DdlEtcObjRqstService ddlEtcObjRqstService; 

	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqDdlSeqs extends HashMap<String, ArrayList<WaqDdlSeq>> {}


		
	
	
	/** DDL시퀀스 요청서 입력 폼.... @throws Exception */
    @RequestMapping("/meta/ddl/ddlseq_rqst.do")
    public String goDdlseq_rqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	//logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DDS");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	//logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/ddl/ddlseq_rqst";

    }
    
    /** DDL 시퀀스 요청 리스트 조회 @return */
    @RequestMapping("/meta/ddl/getddlseqrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlSeq> getDdlSeqRqstList(WaqMstr search) {

		//logger.debug("search:{}", search);

		List<WaqDdlSeq> list = ddlSeqRqstService.getDdlSeqRqstList(search);


		return new IBSheetListVO<WaqDdlSeq>(list, list.size());
	}
    
  /** DDL 시퀀스 요청 상세 정보 조회 @return */
  @RequestMapping("/meta/ddl/ajaxgrid/ddlseq_rqst_dtl.do")
  public String getDdlSeqDetail(WaqDdlSeq searchVo, ModelMap model) {

  	//logger.debug("searchVO:{}", searchVo);

  	if(searchVo.getRqstSno() == null) {
  		model.addAttribute("saction", "I");

  	} else {
  		WaqDdlSeq resultvo =  ddlSeqRqstService.getDdlSeqRqstDetail(searchVo);
  		model.addAttribute("result", resultvo);
  		model.addAttribute("saction", "U");
  	}

  	return "/meta/ddl/ddlseq_rqst_dtl";

  }
  
	@RequestMapping("/meta/ddl/regddlseqrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regDdlSeqRqstList(@RequestBody WaqDdlSeqs data, WaqMstr reqmst, Locale locale) throws Exception {
	
		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlSeq> list = data.get("data");
	
		int result = ddlSeqRqstService.register(reqmst, list);
		String resmsg = null;
		String action = null;

		try {
				
			//DDL이관일경우 인덱스, 파티션, 엔티티기반SEQ, 기타오브젝트 모두 검증
			if(reqmst.getBizDcd().equals("DTT") || reqmst.getBizDcd().equals("DTR") ){
				result += ddlTblRqstService.check(reqmst);	//테이블 체크
				//result += ddlPartRqstService.check(reqmst); //파티션 체크
				result += ddlIdxRqstService.check(reqmst); // GLOBAL 인덱스는 파티션 에 종속
				result += ddlSeqRqstService.check(reqmst);
				//종속관계 없으니 제외
				//result += ddlEtcObjRqstService.check(reqmst);
			}else{
				result += ddlSeqRqstService.check(reqmst);
			}
			
			
		
			if(result > 0 ){
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
		
			action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
			//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
			reqmst = requestMstService.getrequestMst(reqmst);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}


	@RequestMapping("/meta/ddl/approveddlseq.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> approveDdlSeq(@RequestBody WaqDdlSeqs data, WaqMstr reqmst, Locale locale) throws Exception {
	
		//logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;
	
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
	
		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}
	
	
		ArrayList<WaqDdlSeq> list = data.get("data");
	
		int result  = ddlSeqRqstService.approve(reqmst, list);
	
	
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
	
	  /** 시퀀스 변경대상 추가 @throws Exception */
    @RequestMapping("/meta/ddl/regWam2WaqDdlSeq.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2WaqSeqlist(@RequestBody WaqDdlSeqs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqDdlSeq> list = data.get("data");

		int result = ddlSeqRqstService.regWam2Waq(reqmst, list);

		result += ddlSeqRqstService.check(reqmst);

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
    
    
  @RequestMapping("/meta/model/delddlseqrqstlist.do")
  @ResponseBody
  public IBSResultVO<WaqMstr> delDdlSeqRqstList(@RequestBody WaqDdlSeqs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlSeq> list = data.get("data");

		int result = ddlSeqRqstService.delDdlSeqRqst(reqmst, list);

		result += ddlSeqRqstService.check(reqmst);

		String resmsg;

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


  
  /** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		Map<String, Object> codeMap = new HashMap<String, Object>(); 

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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//시퀀스명명규칙유형
		codeMap.put("nmRlTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NM_RL_TYP_CD")));
		codeMap.put("nmRlTypCd", cmcdCodeService.getCodeList("NM_RL_TYP_CD"));
		//시퀀스사용용도유형
		codeMap.put("usTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("US_TYP_CD")));
		codeMap.put("usTypCd", cmcdCodeService.getCodeList("US_TYP_CD"));
		
		//적용요청구분코드
		codeMap.put("aplReqTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("APL_REQ_TYP_CD")));
		codeMap.put("aplReqTypCd", cmcdCodeService.getCodeList("APL_REQ_TYP_CD"));
		
		//클래스 코드
		codeMap.put("seqClasCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SEQ_CLAS_NM")));
		codeMap.put("seqClasCd", cmcdCodeService.getCodeList("SEQ_CLAS_NM"));
		//init_type 코드
		codeMap.put("seqInitCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SEQ_INLT_DSCD")));
		codeMap.put("seqInitCd", cmcdCodeService.getCodeList("SEQ_INLT_DSCD"));

		return codeMap;
	}
	
	/** DDL 시퀀스 엑셀업로드 팝업 */
    @RequestMapping("/meta/ddl/popup/ddlseq_xls.do")
    public String goDdlSeqXlsPop(@ModelAttribute("search") WaqDdlSeq search, Model model, Locale locale) {
    	//logger.debug("search:{}", search);
    	
    	return "/meta/ddl/popup/ddlseq_xls";
    }

    
    
    /** DDL 시퀀스 이관 관련 */
    
	/** DDL시퀀스 이관 요청서 입력 폼.... @throws Exception */
    @RequestMapping("/meta/ddltsf/ddltsfseq_rqst.do")
    public String goDdlTsfSeqRqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	//logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DTS");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	//logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/ddltsf/ddltsfseq_rqst";

    }
    
    
    /** DDL 시퀀스 이관 관련 */
    
    
	/** DDL 시퀀스 변경대상 추가 팝업  */
    @RequestMapping("/meta/ddltsf/popup/ddltsfseq_pop.do")
    public String goDdlSeqPop(@ModelAttribute("search") WamDdlSeq search, Model model, Locale locale) {
    	//logger.debug("search:{}", search);
    	
    	return "/meta/ddltsf/popup/ddltsfseq_pop";
    }
    
  	@RequestMapping("/meta/ddltsf/regddltsfseqrqstlist.do")
  	@ResponseBody
  	public IBSResultVO<WaqMstr> regDdlTsfSeqRqstList(@RequestBody WaqDdlSeqs data, WaqMstr reqmst, Locale locale) throws Exception {
  	
  		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
  		ArrayList<WaqDdlSeq> list = data.get("data");
  	
  		int result = ddlSeqRqstService.register(reqmst, list);
  		String resmsg = null;
  		String action = null;

  		try {
  				
  			//DDL이관일경우 인덱스, 파티션, 엔티티기반SEQ, 기타오브젝트 모두 검증
  			if(reqmst.getBizDcd().equals("DTT") || reqmst.getBizDcd().equals("DTS") ){
  				result += ddlTblRqstService.check(reqmst);	//테이블 체크
  				//result += ddlPartRqstService.check(reqmst); //파티션 체크
  				//result += ddlIdxRqstService.check(reqmst); // GLOBAL 인덱스는 파티션 에 종속
  				result += ddlSeqRqstService.check(reqmst);
  				//종속관계 없으니 제외
  				//result += ddlEtcObjRqstService.check(reqmst);
  			}else{
  				result += ddlSeqRqstService.check(reqmst);
  			}
  		
  			if(result > 0 ){
  				result = 0;
  				resmsg = message.getMessage("MSG.SAVE", null, locale);
  			} else {
  				result = -1;
  				resmsg = message.getMessage("ERR.SAVE", null, locale);
  			}
  		
  			action = WiseMetaConfig.RqstAction.REGISTER.getAction();
  		
  			//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
  			reqmst = requestMstService.getrequestMst(reqmst);
  		}catch(Exception e) {
  			e.printStackTrace();
  			logger.error(e.getMessage());
  		}
  		
  		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
  	}
    
    /** DDL 시퀀스 요청 리스트 조회 @return */
    @RequestMapping("/meta/ddltsf/getddltsfseqrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlSeq> getDdlTsfSeqRqstList(WaqMstr search) {
		//logger.debug("search:{}", search);
		List<WaqDdlSeq> list = ddlSeqRqstService.getDdlSeqRqstList(search);

		return new IBSheetListVO<WaqDdlSeq>(list, list.size());
	}
    
  /** DDL 시퀀스 요청 상세 정보 조회 @return */
  @RequestMapping("/meta/ddltsf/ajaxgrid/ddltsfseq_rqst_dtl.do")
  public String getDdlTsfSeqDetail(WaqDdlSeq searchVo, ModelMap model) {

  	//logger.debug("searchVO:{}", searchVo);

  	if(searchVo.getRqstSno() == null) {
  		model.addAttribute("saction", "I");

  	} else {
  		WaqDdlSeq resultvo =  ddlSeqRqstService.getDdlSeqRqstDetail(searchVo);
  		model.addAttribute("result", resultvo);
  		model.addAttribute("saction", "U");
  	}

  	return "/meta/ddltsf/ddltsfseq_rqst_dtl";
  }
  
	/** DDL 시퀀스 WAM 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddltsf/selectDdlTsfSeqListForRqst.do")
	@ResponseBody
	public IBSheetListVO<WamDdlSeq> selectDdlSeqList(@ModelAttribute WamDdlSeq search) {
		//logger.debug("DDL 시퀀스 WAM 리스트 조회:{}", search);
		List<WamDdlSeq> list = ddlSeqRqstService.selectDdlTsfSeqListForRqst(search);

		return new IBSheetListVO<WamDdlSeq>(list, list.size());
	}
	
	/** 시퀀스 변경대상 추가 @throws Exception */
	@RequestMapping("/meta/ddltsf/regWam2WaqDdlTsfSeq.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2WaqDdlTsfSeq(@RequestBody WaqDdlSeqs data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		ArrayList<WaqDdlSeq> list = data.get("data");
		int result = 0;
		try {
			result = ddlSeqRqstService.regTsfWam2Waq(reqmst, list);
			result += ddlSeqRqstService.check(reqmst);
		}catch(Exception e) {
			e.printStackTrace();
		}
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
