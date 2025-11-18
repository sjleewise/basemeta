package kr.wise.meta.app.web;

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
import org.springframework.web.bind.annotation.RequestParam;
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
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.app.service.AppPrgmItemService;
import kr.wise.meta.app.service.WamAppPrgm;
import kr.wise.meta.app.service.WaqAppPrgm;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.app.web.AppStndItemRqstCtrl.WaqAppSditms;
import kr.wise.meta.model.service.WaqPdmCol;

@Controller
public class AppPrgmItemCtrl{

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codeListService;

	@Inject	
	private CmcdCodeService cmcdCodeService;

	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private AppPrgmItemService appPrgmItemService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private UserService userService;
	
	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	static class WaqAppPrgms extends HashMap<String, ArrayList<WaqAppPrgm>> {}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//공통 코드(요청구분 코드리스트)
		codeMap.put("appPrgmDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("APP_PRGM_DCD")));
		codeMap.put("appPrgmDcd", cmcdCodeService.getCodeList("APP_PRGM_DCD"));
		
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
		return codeMap;
	}

	@RequestMapping("/meta/app/appprgmitem_lst.do")
	public String goAppPrgmItemList(HttpSession session, @RequestParam(value="stndNm", required=false) String stndNm, Model model) throws Exception  {
		logger.debug("{}", stndNm);
		
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
		}
				
		model.addAttribute("stndNm", stndNm);
		return "/meta/app/appprgmitem_lst";
	}
	@RequestMapping("/meta/app/getPrgmItemlist.do")
	@ResponseBody
	public IBSheetListVO<WamAppPrgm> getAppPrgmItemlist(@ModelAttribute WamAppPrgm data, Locale locale) {
		
		logger.debug("reqvo:{}", data);
		
		List<WamAppPrgm> list = appPrgmItemService.getPrgmItemList(data);
		
		
//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);
		
		return new IBSheetListVO<WamAppPrgm>(list, list.size());
		
	}
	
	/** APP용어 상세정보 조회 */
	@RequestMapping("/meta/app/ajaxgrid/appprgmitem_dtl.do")
	public String selectAppPrgmInfoDetail(String appPrgmId, ModelMap model) {
		logger.debug("appPrgmId : {}", appPrgmId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(appPrgmId)) {

			WamAppPrgm result = appPrgmItemService.selectAppPrgmDetail(appPrgmId);
			model.addAttribute("result", result);
		}
		return "/meta/app/appprgmitem_dtl";
	}
	
	/** 단어 변경이력 조회 -IBSheet json */
	@RequestMapping("/meta/app/ajaxgrid/appprgmitemchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamAppPrgm> selectMenuList(@ModelAttribute("searchVO") WamAppPrgm searchVO, String appPrgmId) throws Exception {

		logger.debug("{}", appPrgmId);
		List<WamAppPrgm> list = appPrgmItemService.selectAppPrgmChangeList(appPrgmId);
		return new IBSheetListVO<WamAppPrgm>(list, list.size());
	}
	
	/** APP용어 등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return  */
    @RequestMapping("/meta/app/appprgmitem_rqst.do")
	public String goStndwordrqstForm(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
  //  	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("APM");
    		reqmst.setBizDtlCd("APM");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

		return "/meta/app/appprgmitem_rqst";
	}
    
    @RequestMapping("/meta/app/ajaxgrid/appprgmitem_rqst_dtl.do")
    public String getAppPrgmDetail(WaqAppPrgm searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqAppPrgm resultvo =  appPrgmItemService.getAppPrgmRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    		logger.debug("여기여기여기:{}",resultvo.toString());
    	}

    	return "/meta/app/appprgmitem_rqst_dtl";

    }
    
    @RequestMapping("/meta/app/regappprgmitemrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regAppPrgmItemRqstList(@RequestBody WaqAppPrgms data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqAppPrgm> list = data.get("data");
//		reqmst = requestMstService.getBizInfoInit(reqmst);
		
		int result = appPrgmItemService.register(reqmst, list);

		result += appPrgmItemService.check(reqmst);

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
    
    @RequestMapping("/meta/app/getappprgmitemrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqAppPrgm> getAppPrgmItemRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqAppPrgm> list = appPrgmItemService.getAppPrgmItemRqstList(search);


		return new IBSheetListVO<WaqAppPrgm>(list, list.size());
	}
    
    /** APP용어 요청 리스트 삭제.... @throws Exception insomnia */
    @RequestMapping("/meta/app/delappprgmitemrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delAppPrgmItemRqstList(@RequestBody WaqAppPrgms data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqAppPrgm> list = data.get("data");

		WaaBizInfo bz = requestMstService.getBizInfo(reqmst);
		reqmst.setBizInfo(bz);
		
		int result = appPrgmItemService.delAppPrgmItemRqst(reqmst, list);
		
		if(!appPrgmItemService.checkEmptyRqst(reqmst)) {
			result += appPrgmItemService.check(reqmst);
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
    
    /** APP용어 승인... @throws Exception insomnia */
    @RequestMapping("/meta/app/approveappprgmitem.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approve(@RequestBody WaqAppPrgms data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqAppPrgm> list = data.get("data");
		int result  = appPrgmItemService.approve(reqmst, list);
        
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
    
	@RequestMapping("/meta/app/popup/appprgmitemSearchPop.do")
	public String goAppPrgmItemPop(@ModelAttribute("search") WaqAppPrgm search, Model model, Locale locale) {
		logger.debug("{}", search);
		return "/meta/app/popup/appPrgmItemSearch_pop";
	}
	
    @RequestMapping("/meta/app/regWam2WaqAppPrgmItem.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqAppPrgms data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqAppPrgm> list = data.get("data");

		WaaBizInfo bz = requestMstService.getBizInfo(reqmst);
		reqmst.setBizInfo(bz);
		
		int result = appPrgmItemService.regWam2Waq(reqmst, list);

		result += appPrgmItemService.check(reqmst);

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
    
    /** 엑셀업로드 팝업창 호출... @return*/
    @RequestMapping("/meta/app/popup/appprgmitem_xls.do")
    public String goPdmTblColRqstxls(@ModelAttribute("search") WaqPdmCol search) {

    	return "/meta/app/popup/appprgmitem_xls"; 
    }
      
}