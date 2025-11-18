
package kr.wise.meta.dmngrqst.web;

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

import kr.wise.meta.dmngrqst.service.WaqDmng;
import kr.wise.meta.dmngrqst.service.WaqInfoType;
import kr.wise.meta.dmngrqst.service.DmngRqstService;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DmngRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 물리모델 등록 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 1. 오후 4:22:41
 * </PRE>
 */
@Controller("DmngRqstCtrl")
public class DmngRqstCtrl {

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
	private DmngRqstService dmngRqstService;
	
	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqDmngs extends HashMap<String, ArrayList<WaqDmng>> {}

	static class WaqInfoTypes extends HashMap<String, ArrayList<WaqInfoType>> {}
	
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
		
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regtypcd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD")));
	
		//표준구분
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

		return codeMap;
	}

    /** 물리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/dmngrqst/dmng_rqst.do")
    public String goModelDmngRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DMG");
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
       
    	
     

    	return "/meta/dmngrqst/dmng_rqst";

    }
    
   
   

    /** 물리모델 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/dmngrqst/ajaxgrid/dmng_rqst_dtl.do")
    public String getdmngDetail(WaqDmng searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDmng resultvo =  dmngRqstService.getDmngRqstDetail(searchVo);
    		logger.debug("결과값 : " +resultvo.getRvwStsCd() );
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/dmngrqst/dmng_rqst_dtl";

    }


    /** 물리모델 테이블 요청 리스트 조회 @return insomnia */
    @RequestMapping("/meta/dmngrqst/getdmngrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDmng> getDmngRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqDmng> list = dmngRqstService.getDmngRqstList(search);


		return new IBSheetListVO<WaqDmng>(list, list.size());
	}

    /** 물리모델 테이블 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/dmngrqst/regdmngrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDmngRqstList(@RequestBody WaqDmngs data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDmng> list = data.get("data");
		
		int result = dmngRqstService.register(reqmst, list);

		result += dmngRqstService.check(reqmst);

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
    @RequestMapping("/meta/dmngrqst/deldmngrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delDmngRqstList(@RequestBody WaqDmngs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDmng> list = data.get("data");

		int result = dmngRqstService.delDmngRqst(reqmst, list);
		
		if(!dmngRqstService.checkEmptyRqst(reqmst)) {
			result += dmngRqstService.check(reqmst);
		}
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
    @RequestMapping("/meta/dmngrqst/regWam2WaqDmng.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqDmngs data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqDmng> list = data.get("data");

		int result = dmngRqstService.regWam2Waq(reqmst, list);

		
		
		result += dmngRqstService.check(reqmst);

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
    @RequestMapping("/meta/dmngrqst/popup/dmng_xls.do")
    public String goDmngRqstxls(@ModelAttribute("search") WaqDmng search) {

    	return "/meta/dmngrqst/popup/dmng_xls";
    }

    /** 인포타입 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/meta/dmngrqst/popup/infotype_xls.do")
    public String goInfoTypeRqstxls(@ModelAttribute("search") WaqInfoType search) {

    	return "/meta/dmngrqst/popup/infotype_xls";
    }
    
    /** 물리모델 테이블컬럼 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/meta/dmngrqst/popup/dmngcol_xls.do")
    public String goDmngColRqstxls(@ModelAttribute("search") WaqInfoType search) {

    	return "/meta/dmngrqst/popup/dmngcol_xls"; 
    }


    @RequestMapping("/meta/dmngrqst/ajaxgrid/infotype_rqst_dtl.do")
    public String getInfoTypeRqstDetail(WaqInfoType search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqInfoType resultvo =  dmngRqstService.getInfoTypeRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}


    	return "/meta/dmngrqst/infotype_rqst_dtl";
    }


    /** 인포타입 요청 리스트 등록... @throws Exception insomnia */
    @RequestMapping("/meta/dmngrqst/reginfotyperqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regInfoTypeRqstList(@RequestBody WaqInfoTypes data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqInfoType> list = data.get("data");
//		System.out.println("세이브브이오리스트 : " + list.toString());
		int result = dmngRqstService.regInfoTypeList(reqmst, list);
		result += dmngRqstService.check(reqmst);

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

    /** 인포타입 요청 리스트 조회... @return insomnia */
    @RequestMapping("/meta/dmngrqst/getinfotyperqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqInfoType> getInfoTypeRqstList(WaqMstr search,WaqInfoType waqInfoType) {
		logger.debug("search:{}",search);
		logger.debug("waqInfoType:{}",waqInfoType);
 
		List<WaqInfoType> list = dmngRqstService.getInfoTypeRqstList(search);

		return new IBSheetListVO<WaqInfoType>(list, list.size());
	}

    /** 인포타입 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/dmngrqst/delinfotyperqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delInfoTypeRqstList(@RequestBody WaqInfoTypes data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqInfoType> list = data.get("data");

		int result = dmngRqstService.delInfoTypeRqst(reqmst, list);

		result += dmngRqstService.check(reqmst);

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
    
    /** 물리모델 테이블 승인... @throws Exception insomnia */
    @RequestMapping("/meta/dmngrqst/approvedmng.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveDmng(@RequestBody WaqDmngs data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqDmng> list = data.get("data");

		int result  = dmngRqstService.approve(reqmst, list);
        
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
    

    @RequestMapping("/meta/dmngrqst/reginfotypexlsrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regInfoTypexlsRqstList(@RequestBody WaqInfoTypes data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqInfoType> list = data.get("data");

		String resmsg;
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//테이블명도 함께 받는다
		HashMap<String, String> map = dmngRqstService.regInfoTypexlsList(reqmst, list);
		
//		int result = dmngRqstService.regPdmxlsColList(reqmst, list);
		
		int result = Integer.parseInt( map.get("result") );
		String colKey = map.get("colKey");

		//테이블이 존재하지 않음...
		if(result == -999) {
			result = -1;
			resmsg = message.getMessage("ERR.NOTABLE", new String[] {colKey}, locale);

			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}

		result += dmngRqstService.check(reqmst);


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
    
    // 엑셀 테이블 컬럼 일괄 저장 
//    @RequestMapping("/meta/dmngrqst/regDmngColXlsRqstList.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regDmngColXlsRqstList(@RequestBody WaeInfoTypes data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata{}", reqmst, data);
//
//		List<WaeInfoType> list = data.get("data");
//
//		String resmsg;
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		int result = 0;
//		
//		//테이블 컬럼 일괄 저장
//		result = dmngRqstService.regPdmXlsTblColList(reqmst, list); 
//				
//		//검증 
//		result += dmngRqstService.check(reqmst);
//
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//   }
    
}
