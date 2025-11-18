/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlEtcRqstCtrl.java
 * 2. Package : kr.wise.meta.ddletc.web
 * 3. Comment : DDL 인덱스 요청 컨트롤러...
 * 4. 작성자  : 
 * 5. 작성일  : 2014. 8. 6. 14:57:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 6.   :            : 신규 개발.
 */
package kr.wise.meta.ddletc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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

import kr.wise.meta.ddletc.service.DdlEtcRqstService;
import kr.wise.meta.ddletc.service.WaqDdlEtc;



/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlIdxRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL 인덱스 요청 컨트롤러...
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 6. 14:57:00
 * </PRE>
 */
@Controller("DdlEtcRqstCtrl")
public class DdlEtcRqstCtrl {

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
	private ApproveLineServie approveLineServie;
	
	@Inject
	private DdlEtcRqstService ddlEtcRqstService;


	

	static class WaqDdlEtcs extends HashMap<String, ArrayList<WaqDdlEtc>> {} 


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

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
		
		codeMap.put("etcObjDcd", cmcdCodeService.getCodeList("ETC_OBJ_DCD"));
		codeMap.put("etcObjDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ETC_OBJ_DCD")));
				
		
		
		return codeMap;
	}
	
	/** 기타오브젝트 엑셀업로드 팝업 */
    @RequestMapping("/meta/ddletc/popup/ddletc_xls.do")
    public String goDdlEtcXlsPop(@ModelAttribute("search") WaqDdlEtc search, Model model, Locale locale) {
    	//logger.debug("search:{}", search);
    	
    	return "/meta/ddletc/popup/ddletc_xls";
    }


	
	
    /** 기타오브젝트 요청서 입력 폼.... @throws Exception meta */
    @RequestMapping("/meta/ddletc/ddletc_rqst.do")
    public String goDdlIdxRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DET");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	

    	return "/meta/ddletc/ddletc_rqst";

    }

    /** 기타오브젝트 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/ddletc/ajaxgrid/ddletc_rqst_dtl.do")
    public String getDdlEtcDetail(WaqDdlEtc searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDdlEtc resultvo =  ddlEtcRqstService.getDdlEtcRqstDetail(searchVo); 
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U"); 
    	}

    	return "/meta/ddletc/ddletc_rqst_dtl";

    }

    @RequestMapping("/meta/ddletc/regDdlEctRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlEctRqstList(@RequestBody WaqDdlEtcs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlEtc> list = data.get("data");
		int result = ddlEtcRqstService.register(reqmst, list);

		result += ddlEtcRqstService.check(reqmst);

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
    
    @RequestMapping("/meta/ddletc/delDdlEtcRqstList.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlEtcRqstList(@RequestBody WaqDdlEtcs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlEtc> list = data.get("data");

		int result = ddlEtcRqstService.delDdlEtcRqst(reqmst, list);  

		result += ddlEtcRqstService.check(reqmst);

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
    
    /** DDL 기타오브젝스 요청 리스트 조회 @return skkim */
    @RequestMapping("/meta/ddletc/getDdlEtcRqstList.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlEtc> getPdmTblRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqDdlEtc> list = ddlEtcRqstService.getDdlEtcRqstList(search);


		return new IBSheetListVO<WaqDdlEtc>(list, list.size());
	}
    
    @RequestMapping("/meta/ddletc/approveddletc.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlEtc(@RequestBody WaqDdlEtcs data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqDdlEtc> list = data.get("data");

		int result  = ddlEtcRqstService.approve(reqmst, list);


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
    
    /** WAM에 있는 내용을 여러개 선택해서 waq에 처리하는 방법 @throws Exception insomnia */
	@RequestMapping("/meta/ddletc/regWam2WaqDdlEtc.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqDdlEtcs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqDdlEtc> list = data.get("data");

		int result = ddlEtcRqstService.regWam2Waq(reqmst, list); 

		result += ddlEtcRqstService.check(reqmst); 

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
