/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndDmnRqstCtrl.java
 * 2. Package : kr.wise.meta.app.web
 * 3. Comment : App표준항목 요청 컨트롤러
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.app.service.AppStndItemRqstService;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanDic;

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

@Controller("AppStdnItemRqstCtrl")
public class AppStndItemRqstCtrl {

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
	private InfotpService infotpService;

	@Inject
	private AppStndItemRqstService appStndItemRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CodeListService codelistService;

	static class WaqAppSditms extends HashMap<String, ArrayList<WaqAppSditm>> {}
	


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
		//데이터타입
		codeMap.put("dataTypeibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DATA_TYPE")));
		codeMap.put("dataType", cmcdCodeService.getCodeList("DATA_TYPE"));
		
		//목록성 코드(시스템영역 코드리스트)
//		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
//		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));
//		String dmng 		= UtilJson.convertJsonString(codeListService.getCodeList("dmng"));
//		String dmngibs 		= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
//		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
//		String dmnginfotp 	= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
//		String infotpinfolst = UtilJson.convertJsonString(infotpService.getInfoTypeList());


//		codeMap.put("sysarea", sysarea);
//		codeMap.put("sysareaibs", sysareaibs);
//		codeMap.put("dmng", dmng);
//		codeMap.put("dmngibs", dmngibs);
//		codeMap.put("infotpibs", infotpibs);
//		codeMap.put("dmnginfotp", dmnginfotp);
//		codeMap.put("infotpinfolst", infotpinfolst);

		return codeMap;
	}
	
	
	
    //표준항목 분할
    @RequestMapping("/meta/app/stnditemdiv_lst.do")
    public String gostnditemxlspage(@ModelAttribute("search") WaqAppSditm search) {

    	return "/meta/app/stnditemdiv_lst";
    }
    
    /** 표준항목 요청서 입력 폼.... @throws Exception  */
    @RequestMapping("/meta/app/stnditem_rqst.do")
    public String goStndItemRqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("ADC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/app/stnditem_rqst";

    }

    /** 표준항목 요청서 입력 폼.... @throws Exception  */
    @RequestMapping("/meta/app/appstnditem_rqst_ifm.do")
    public String goStndItemRqstIframe(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("API");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/app/appstnditem_rqst_ifm";

    }
    
    /** 표준항목 요청서 입력 폼.... @throws Exception  */
    @RequestMapping("/meta/app/appstnditem_rqst.do")
    public String goStndItemRqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);
    	
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("API");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}
    	
    	logger.debug("reqmst:{}", reqmst);
    	
    	model.addAttribute("waqMstr", reqmst);
    	
    	return "/meta/app/appstnditem_rqst";
    	
    }


    /** 표준항목 요청서 상세정보 조회 @return  
     * @throws Exception */
    @RequestMapping("/meta/app/ajaxgrid/stnditem_rqst_dtl.do")
    public String goStndItemDetail(WaqAppSditm searchVo, ModelMap model) throws Exception {
    	logger.debug("searhcVo:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	} else {
    		WaqAppSditm result = appStndItemRqstService.getStndItemRqstDetail(searchVo);
    		model.addAttribute("result", result);
    		model.addAttribute("saction", "U");
    	}

    	//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
		}
    	
    	return "/meta/app/stnditem_rqst_dtl";
    }

    /** 표준항목 자동분할 팝업 호출.... @return  */
    @RequestMapping("/meta/app/popup/appstnditemdivision_pop.do")
    public String goitemdivisionpop(@ModelAttribute("search") WaqAppSditm search, Model model, Locale locale) {
    	logger.debug("search:{}", search);

    	return "/meta/app/popup/appstnditemdivision_pop";
    }

    @RequestMapping("/meta/app/popup/appstnditem_xls.do")
    public String gostnditemxls(@ModelAttribute("search") WaqAppSditm search) {

    	return "/meta/app/popup/appstnditem_xls";
    }


    /** 표준항목 요청 리스트 조회.... @return  */
    @RequestMapping("/meta/app/getitemrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqAppSditm> getAppStndItemRqstList (WaqMstr search) {
    	logger.debug("search:{}", search);

    	List<WaqAppSditm> list = appStndItemRqstService.getStndItemRqstList(search);

    	return new IBSheetListVO<WaqAppSditm>(list, list.size());
    }

    /** 표준항목 분리 기능 호출 @return  */
    @RequestMapping("/meta/app/getsditmdivjson.do")
    @ResponseBody
    public WaqAppSditm getItemdivision(WaqAppSditm search) {

    	WaqAppSditm result = appStndItemRqstService.getItemWordInfo(search);

    	return result;
    }

    /** 표준항목 리스트 등록 @throws Exception  */
    @RequestMapping("/meta/app/regitemrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regStndItemRqstList(@RequestBody WaqAppSditms data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqAppSditm> list = data.get("data");

		int result = appStndItemRqstService.register(reqmst, list);

		result += appStndItemRqstService.check(reqmst);

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

    @RequestMapping("/meta/app/regWam2WaqStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2WaqList(@RequestBody WaqAppSditms data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);
    	List<WaqAppSditm> list = data.get("data");

    	int result = appStndItemRqstService.regWam2Waq(reqmst, list);
    	result += appStndItemRqstService.check(reqmst);

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

    @RequestMapping("/meta/app/delSditmrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delStndItemRqstList(@RequestBody WaqAppSditms data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);

    	ArrayList<WaqAppSditm> list = data.get("data");

    	int result = appStndItemRqstService.delStndItemRqstList(reqmst, list);
    	result += appStndItemRqstService.check(reqmst);

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

    @RequestMapping("/meta/app/approveStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveStndItem (@RequestBody WaqAppSditms data, WaqMstr reqmst, Locale locale) throws Exception {

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

    	List<WaqAppSditm> list = data.get("data");

    	int result = appStndItemRqstService.approve(reqmst, list);


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


    @RequestMapping("/meta/app/regitemdivision.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regItemDivision(@RequestBody WaqAppSditms data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);

    	List<WaqAppSditm> list = data.get("data");

    	int result = appStndItemRqstService.regitemdivision(reqmst, list);

    	result += appStndItemRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("REQ.DIV.ITEM", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.DIV.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }
    
    /** 표준항목 자동분할 기능 처리... @return  */
    @RequestMapping("/meta/app/getsditmdvcanlist.do")
    @ResponseBody
    public IBSheetListVO<WapDvCanAsm> getItemDivisionList(WapDvCanDic data, Locale locale) throws Exception {
    	logger.debug("division:{}", data);

    	List<WapDvCanAsm> list = appStndItemRqstService.getItemDivisionList(data);

    	return new IBSheetListVO<WapDvCanAsm>(list, list.size());

    }
    
    /** 표준항목 자동분할 조회...  */
    @RequestMapping("/meta/app/getItemDivList.do")
    @ResponseBody
    public IBSheetListVO<WapDvCanAsm> getItemDivList(WapDvCanDic data, Locale locale) {
    	logger.debug("division:{}", data);
    	
    	List<WapDvCanAsm> list = appStndItemRqstService.getItemDivList(data);
    	
    	return new IBSheetListVO<WapDvCanAsm>(list, list.size());
    	
    }

}
