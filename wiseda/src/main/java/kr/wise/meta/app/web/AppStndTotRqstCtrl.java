/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndWordRequestCtrl.java
 * 2. Package : kr.wise.meta.app.web
 * 3. Comment : App표준단어 등록요청 컨트롤러
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
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.app.service.AppStndItemRqstService;
import kr.wise.meta.app.service.AppStndTotRqstService;
import kr.wise.meta.app.service.AppStndWordRqstServie;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.app.service.WaqAppStwd;
import kr.wise.meta.app.web.AppStndItemRqstCtrl.WaqAppSditms;
import kr.wise.meta.app.web.AppStndWordRqstCtrl.WaqAppStwds;

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
 * 2. FileName  : AppStndWordRequestCtrl.java
 * 3. Package  : kr.wise.meta.app.web
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
@Controller
public class AppStndTotRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private AppStndWordRqstServie appStndWordRqstServie;

	@Inject
	private AppStndItemRqstService appStndItemRqstService;

	@Inject
	private AppStndTotRqstService appStndTotRqstService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;


	static class WaqStwds extends HashMap<String, ArrayList<WaqAppStwd>> { }
	static class WaqSditms extends HashMap<String, ArrayList<WaqAppSditm>> {}


	/** 표준데이터등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/meta/app/appstndtot_rqst.do")
	public String goStndwordrqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("ADC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);
    	
    	//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
//		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
//		logger.debug("기본정보레벨 조회 : {}", data);
//		if (data != null) {
//			model.addAttribute("bscLvl", data.getBscLvl());
//			model.addAttribute("selectBoxId", data.getSelectBoxId());
//			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
//		}

    	model.addAttribute("waqMstr", reqmst);

		return "/meta/app/appstndtot_rqst";
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

		return codeMap;
	}


	/** 표준단어 요청 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/meta/app/regStndTot.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStndWordRqstlist(@RequestBody WaqAppStwds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqAppStwd> list = data.get("data");

		int result = appStndWordRqstServie.register(reqmst, list);

		appStndWordRqstServie.check(reqmst);

//		int result = appStndWordRqstServie.regStndWordRqstlist(list);
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



    /** 표준단어 요청서 결재처리... @return insomnia
     * @throws Exception */
    @RequestMapping("/meta/app/regapproveStndWord.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regapproveStndWord(@RequestBody WaqAppStwds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		
	     WaaRqstBizApr aprSearch = new WaaRqstBizApr();
	     aprSearch.setBizDcd(reqmst.getBizDcd());
	     List<WaaRqstBizApr> aprlist = approveLineServie.getapprovelinelist(aprSearch); // 결재선검색
	     reqmst.setAprLvl(aprlist.size());
	     Boolean check = false;
	     if(aprlist.size() > 0){
	      check = true;
	     }
	     
		if(!checkapprove) {
			 if(check){ 
				resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
				return new IBSResultVO<WaqMstr>(-1, resmsg, null);
			 }
		}

		ArrayList<WaqAppStwd> list = data.get("data");

		int result = appStndWordRqstServie.approve(reqmst, list);


		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}


    @RequestMapping("/meta/app/regapproveStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regapproveStndItem (@RequestBody WaqAppSditms data, WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);
    	String resmsg;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
	     WaaRqstBizApr aprSearch = new WaaRqstBizApr();
	     aprSearch.setBizDcd(reqmst.getBizDcd());
	     List<WaaRqstBizApr> aprlist = approveLineServie.getapprovelinelist(aprSearch); // 결재선검색
	     reqmst.setAprLvl(aprlist.size());
	     Boolean check = false;
	     if(aprlist.size() > 0){
	      check = true;
	     }
	     
		if(!checkapprove) {
			 if(check){ 
				resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
				return new IBSResultVO<WaqMstr>(-1, resmsg, null);
			 }
		}

    	List<WaqAppSditm> list = data.get("data");

    	int result = appStndItemRqstService.approve(reqmst, list);


		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }

    @RequestMapping("/meta/app/approveStndTot.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveStndTot (WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{}", reqmst);
    	String resmsg;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		
	     WaaRqstBizApr aprSearch = new WaaRqstBizApr();
	     aprSearch.setBizDcd(reqmst.getBizDcd());
	     List<WaaRqstBizApr> aprlist = approveLineServie.getapprovelinelist(aprSearch); // 결재선검색
	     reqmst.setAprLvl(aprlist.size());
	     Boolean check = false;
	     if(aprlist.size() > 0){
	      check = true;
	     }
//	     else{
//	    	 reqmst.setAprvStatus("1");
//	     }
	     
		if(!checkapprove) {
			 if(check){ 
				resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
				return new IBSResultVO<WaqMstr>(-1, resmsg, null);
			 }
		}

    	int result = appStndTotRqstService.approve(reqmst, null);


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


}
