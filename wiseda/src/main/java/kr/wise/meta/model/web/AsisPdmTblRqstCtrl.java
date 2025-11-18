/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AsisPdmTblRqstCtrl.java
 * 2. Package : kr.wise.meta.model.web
 * 3. Comment : 물리모델 등록 요청 컨트롤러...
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                     :. :            : 신규 개발.
 */
package kr.wise.meta.model.web;

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
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.model.service.AsisPdmTblRqstService;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmRel;
import kr.wise.meta.model.service.WaqPdmTbl;

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
 * 2. FileName  : AsisPdmTblRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 물리모델 등록 요청 컨트롤러...
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
@Controller("AsisPdmTblRqstCtrl")
public class AsisPdmTblRqstCtrl {

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
	private AsisPdmTblRqstService asisPdmTblRqstService;

	@Inject 
	private DdlTblRqstService ddlTblRqstService;
	
	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqPdmTbls extends HashMap<String, ArrayList<WaqPdmTbl>> {}

	static class WaqPdmCols extends HashMap<String, ArrayList<WaqPdmCol>> {}
	
	static class WaqPdmRels extends HashMap<String, ArrayList<WaqPdmRel>> {}

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

    /** 물리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/model/asispdmtbl_rqst.do")
    public String goModelPdmTblRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("ASP");
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
       
    	
     

    	return "/meta/model/asispdmtbl_rqst";

    }
    
   
   

    /** 물리모델 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/model/ajaxgrid/asispdmtbl_rqst_dtl.do")
    public String getpdmtblDetail(WaqPdmTbl searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqPdmTbl resultvo =  asisPdmTblRqstService.getPdmTblRqstDetail(searchVo);
    		logger.debug("결과값 : " +resultvo.getRvwStsCd() );
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/model/asispdmtbl_rqst_dtl";

    }


    /** 물리모델 테이블 요청 리스트 조회 @return insomnia */
    @RequestMapping("/meta/model/getasispdmtblrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqPdmTbl> getPdmTblRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqPdmTbl> list = asisPdmTblRqstService.getPdmTblRqstList(search);


		return new IBSheetListVO<WaqPdmTbl>(list, list.size());
	}

    /** 물리모델 테이블 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/model/regasispdmtblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmTblRqstList(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqPdmTbl> list = data.get("data");
		
		int result = asisPdmTblRqstService.register(reqmst, list);

		result += asisPdmTblRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}


    /** 물리모델 테이블 요청 리스트 삭제.... @throws Exception insomnia */
    @RequestMapping("/meta/model/delasispdmtblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delPdmTblRqstList(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqPdmTbl> list = data.get("data");

		int result = asisPdmTblRqstService.delPdmTblRqst(reqmst, list);

		result += asisPdmTblRqstService.check(reqmst);

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

    /** 물리모델 테이블 변경대상 요청 리스트 추가... @throws Exception insomnia */
    @RequestMapping("/meta/model/regWam2WaqAsisPdmTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqPdmTbl> list = data.get("data");

		int result = asisPdmTblRqstService.regWam2Waq(reqmst, list);

		
		
		result += asisPdmTblRqstService.check(reqmst);

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

    /** 물리모델 테이블 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/meta/model/popup/asispdmtbl_xls.do")
    public String goPdmTblRqstxls(@ModelAttribute("search") WaqPdmTbl search) {

    	return "/meta/model/popup/asispdmtbl_xls";
    }

    /** 물리모델 컬럼 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/meta/model/popup/asispdmcol_xls.do")
    public String goPdmColRqstxls(@ModelAttribute("search") WaqPdmCol search) {

    	return "/meta/model/popup/asispdmcol_xls";
    }

    @RequestMapping("/meta/model/ajaxgrid/asispdmcol_rqst_dtl.do")
    public String getPdmColRqstDetail(WaqPdmCol search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqPdmCol resultvo =  asisPdmTblRqstService.getPdmColRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}


    	return "/meta/model/asispdmcol_rqst_dtl";
    }


    /** 물리모델 컬럼 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/model/regasispdmcolrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmColRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqPdmCol> list = data.get("data");
//		System.out.println("세이브브이오리스트 : " + list.toString());
		int result = asisPdmTblRqstService.regPdmColList(reqmst, list);
		result += asisPdmTblRqstService.check(reqmst);

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

    /** 물리모델 컬럼 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/model/getasispdmcolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmCol> getPdmColRqstList(WaqMstr search,WaqPdmCol waqPdmCol) {
		logger.debug("search:{}",search);
		logger.debug("waqPdmCol:{}",waqPdmCol);
 
		List<WaqPdmCol> list = asisPdmTblRqstService.getPdmColRqstList(search);

		return new IBSheetListVO<WaqPdmCol>(list, list.size());
	}

    /** 물리모델 컬럼 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/model/delasispdmcolrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delPdmColRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqPdmCol> list = data.get("data");

		int result = asisPdmTblRqstService.delPdmColRqst(reqmst, list);

		result += asisPdmTblRqstService.check(reqmst);

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
    
    /** 물리모델 관계 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/model/getasispdmrelrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmRel> getPdmRelRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqPdmRel> list = asisPdmTblRqstService.getPdmRelRqstList(search);

		return new IBSheetListVO<WaqPdmRel>(list, list.size());
	}
    
    /** 물리모델 관계 요청 상세리스트 조회 */
    @RequestMapping("/meta/model/ajaxgrid/asispdmrel_rqst_dtl.do")
    public String getPdmRelRqstDetail(WaqPdmRel search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqPdmRel resultvo =  asisPdmTblRqstService.getPdmRelRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}


    	return "/meta/model/asispdmrel_rqst_dtl";
    }
    
    /** 물리모델 관계 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/model/regasispdmrelrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmRelRqstList(@RequestBody WaqPdmRels data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqPdmRel> list = data.get("data");

		int result = asisPdmTblRqstService.regPdmRelList(reqmst, list);
		result += asisPdmTblRqstService.check(reqmst);

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
    /** 물리모델 관계 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/model/delasispdmrelrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delPdmRelRqstList(@RequestBody WaqPdmRels data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqPdmRel> list = data.get("data");

		int result = asisPdmTblRqstService.delPdmRelRqst(reqmst, list);

		result += asisPdmTblRqstService.check(reqmst);

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
    
    /** 물리모델 컬럼 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/meta/model/popup/asispdmrel_xls.do")
    public String goPdmRelRqstxls(@ModelAttribute("search") WaqPdmRel search) {

    	return "/meta/model/popup/asispdmrel_xls";
    }
    
    

    /** 물리모델 테이블 승인... @throws Exception insomnia */
    @RequestMapping("/meta/model/approveasispdmtbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approvePdmTbl(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqPdmTbl> list = data.get("data");

		int result  = asisPdmTblRqstService.approve(reqmst, list);
		//ddl자동발행 승인
//		logger.debug("ddl자동발행 승인");
//        result += ddlTblRqstService.approve(reqmst,null);
//		logger.debug("ddl자동발행 승인완료");
        
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
    

    @RequestMapping("/meta/model/regasispdmcolxlsrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmColxlsRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqPdmCol> list = data.get("data");

		String resmsg;
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//테이블명도 함께 받는다
		HashMap<String, String> map = asisPdmTblRqstService.regPdmxlsColList(reqmst, list);
		
//		int result = asisPdmTblRqstService.regPdmxlsColList(reqmst, list);
		
		int result = Integer.parseInt( map.get("result") );
		String colKey = map.get("colKey");

		//테이블이 존재하지 않음...
		if(result == -999) {
			result = -1;
			resmsg = message.getMessage("ERR.NOTABLE", new String[] {colKey}, locale);

			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}

		result += asisPdmTblRqstService.check(reqmst);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}


		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

   }
    //물리모델 관계 엑셀업로드
    @RequestMapping("/meta/model/regasispdmrelxlsrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmRelxlsRqstList(@RequestBody WaqPdmRels data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqPdmRel> list = data.get("data");

		String resmsg;
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		int result = asisPdmTblRqstService.regPdmxlsRelList(reqmst, list);

		//테이블이 존재하지 않음...
		if(result == -999) {
			result = -1;
			resmsg = message.getMessage("ERR.NOTABLE", null, locale);

			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}

		result += asisPdmTblRqstService.check(reqmst);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}


		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

   }
    
}
