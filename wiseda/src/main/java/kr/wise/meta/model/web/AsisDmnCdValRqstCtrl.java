/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AsisDmnCdValRqstCtrl.java
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
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.model.service.AsisDmnCdValRqstService;
import kr.wise.meta.model.service.WaqAsisDmnCdVal;

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
 * 2. FileName  : AsisDmnCdValRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 물리모델 등록 요청 컨트롤러...
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
@Controller("AsisDmnCdValRqstCtrl")
public class AsisDmnCdValRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}
	
	private Map<String, Object> codeMap;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private AsisDmnCdValRqstService asisDmnCdValRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private MessageSource message;

	static class WaqAsisDmnCdVals extends HashMap<String, ArrayList<WaqAsisDmnCdVal>> {}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

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

		//목록성 코드(시스템영역 코드리스트)


		return codeMap;
	}

    /** 요청서 입력 폼.... @throws Exception  */
    @RequestMapping("/meta/model/asisdmncdval_rqst.do")
    public String goDmnCdValRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DCV");
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
       
    	return "/meta/model/asisdmncdval_rqst";
    }

    /** 요청 상세 정보 조회 @return */
    @RequestMapping("/meta/model/ajaxgrid/asisdmncdval_rqst_dtl.do")
    public String getdmncdvalDetail(WaqAsisDmnCdVal searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqAsisDmnCdVal resultvo =  asisDmnCdValRqstService.getDmnCdValRqstDetail(searchVo);
    		logger.debug("결과값 : " +resultvo.getRvwStsCd() );
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/model/asisdmncdval_rqst_dtl";

    }


    /** 요청 리스트 조회 @return  */
    @RequestMapping("/meta/model/getasisdmncdvalrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqAsisDmnCdVal> getDmnCdValRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqAsisDmnCdVal> list = asisDmnCdValRqstService.getDmnCdValRqstList(search);


		return new IBSheetListVO<WaqAsisDmnCdVal>(list, list.size());
	}

    /** 요청 리스트 등록... @throws Exception  */
    @RequestMapping("/meta/model/regasisdmncdvalrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDmnCdValRqstList(@RequestBody WaqAsisDmnCdVals data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqAsisDmnCdVal> list = data.get("data");
		
		int result = asisDmnCdValRqstService.register(reqmst, list);

		result += asisDmnCdValRqstService.check(reqmst);

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


    /** 요청 리스트 삭제.... @throws Exception  */
    @RequestMapping("/meta/model/delasisdmncdvalrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delDmnCdValRqstList(@RequestBody WaqAsisDmnCdVals data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqAsisDmnCdVal> list = data.get("data");

		int result = asisDmnCdValRqstService.delDmnCdValRqst(reqmst, list);

		result += asisDmnCdValRqstService.check(reqmst);

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

    /** 변경대상 요청 리스트 추가... @throws Exception */
    @RequestMapping("/meta/model/regWam2WaqAsisDmnCdVal.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqAsisDmnCdVals data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqAsisDmnCdVal> list = data.get("data");

		int result = asisDmnCdValRqstService.regWam2Waq(reqmst, list);

		result += asisDmnCdValRqstService.check(reqmst);

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

    /** 엑셀업로드 팝업창 호출... @return insomnia */
    @RequestMapping("/meta/model/popup/asisdmncdval_xls.do")
    public String goDmnCdValRqstxls(@ModelAttribute("search") WaqAsisDmnCdVal search) {

    	return "/meta/model/popup/asisdmncdval_xls";
    }

    /** 승인... @throws Exception  */
    @RequestMapping("/meta/model/approveAsisDmnCdVal.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveDmnCdVal(@RequestBody WaqAsisDmnCdVals data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqAsisDmnCdVal> list = data.get("data");

		int result  = asisDmnCdValRqstService.approve(reqmst, list);
        
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
//    
//
//    @RequestMapping("/meta/model/regasispdmcolxlsrqstlist.do")
//    @ResponseBody
//	public IBSResultVO<WaqMstr> regPdmColxlsRqstList(@RequestBody WaqPdmCols data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		logger.debug("reqmst:{}\ndata{}", reqmst, data);
//
//		List<WaqPdmCol> list = data.get("data");
//
//		String resmsg;
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//테이블명도 함께 받는다
//		HashMap<String, String> map = asisDmnCdValRqstService.regPdmxlsColList(reqmst, list);
//		
////		int result = asisDmnCdValRqstService.regPdmxlsColList(reqmst, list);
//		
//		int result = Integer.parseInt( map.get("result") );
//		String colKey = map.get("colKey");
//
//		//테이블이 존재하지 않음...
//		if(result == -999) {
//			result = -1;
//			resmsg = message.getMessage("ERR.NOTABLE", new String[] {colKey}, locale);
//
//			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//		}
//
//		result += asisDmnCdValRqstService.check(reqmst);
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
