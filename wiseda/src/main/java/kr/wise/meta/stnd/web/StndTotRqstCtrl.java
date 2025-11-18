/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRequestCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준단어 등록요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 12:54:05
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.meta.stnd.web;

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
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.stnd.service.StndDmnRqstService;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.StndTotRqstService;
import kr.wise.meta.stnd.service.StndWordRqstService;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.service.WaqStndTot;
import kr.wise.meta.stnd.service.WaqStwd;

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
 * 2. FileName  : StndWordRequestCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  : 표준데이터 등록요청 (단어,도메인,항목 통합)
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 4. 오후 12:54:05
 * </PRE>
 */
@Controller
public class StndTotRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private RequestApproveService requestApproveService;
	
	private Map<String, Object> codeMap;

	@Inject
	private StndWordRqstService stndWordRqstService;

	@Inject
	private StndDmnRqstService stndDmnRqstService;

	@Inject
	private StndItemRqstService stndItemRqstService;

	@Inject
	private StndTotRqstService stndTotRqstService;

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


	static class WaqStwds extends HashMap<String, ArrayList<WaqStwd>> { }
	static class WaqDmns extends HashMap<String, ArrayList<WaqDmn>> {}
	static class WaqSditms extends HashMap<String, ArrayList<WaqSditm>> {}
	
	//static class WaqStnds extends HashMap<String, ArrayList<?>>{} 
	
	

	/** 표준데이터등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/meta/stnd/stndtot_rqst.do")
	public String goStndwordrqstForm(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

//    	logger.debug("reqmst:{}", reqmst);
    	
    	//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
		}

    	model.addAttribute("waqMstr", reqmst);

    	
    	//표준요청서의 항목을 체크하여 클릭될 탭을 설정
    	int wordYn = stndWordRqstService.checkExistsWaqWord(reqmst);
        int dmnYn = stndDmnRqstService.checkExistsWaqDmn(reqmst);
        int itemYn = stndItemRqstService.checkExistsWaqItem(reqmst);
        
        if(itemYn == 1){
           model.addAttribute("stndTabSel", itemYn);
        }else if(dmnYn == 2){
    	   model.addAttribute("stndTabSel", dmnYn);
    	}else if(wordYn ==3){
    		model.addAttribute("stndTabSel", wordYn);
    	}else{
    		model.addAttribute("stndTabSel", 1);
    	}
        
//        등록요청 취소 ( 결제가 한차례라도 되었을 경우 등록요청 취소 불가능) 
//        waa_apr_prc테이블에 등록요청번호로 로우가 있고, waq_mstr의 rqst_step_cd가 'A'나 'Q' 인 경우 등록요청 취소 불가능
//        waa_apr_prc테이블에 등록요청번호로 로우가 없고, waq_mstr의 rqst_step_cd가 'Q'인 경우 등록요청 취소 가능
//        필요한 것 
//        1. 로그인 한 사용자와 등록요청한 사용자와 같은 경우 등록요청취소 버튼 출력
//        2. 그 페이지에 필요한것은 요청번호, 로그인한 사용자, 등록요청자, 
//        3. 승인레벨 1인 경우만 체크, 승인레벨 1의 승인자가 빈 경우 - 등록요청중, 안빈 경우 - 승인프로세스타는중이거나 결제완료건.
//        4. 승인레벨 2는 체크할 필요가 없음. 만약 등록요청 취소하는경우 제거만 해주면됨.
//        5. 취소하는경우 'Q'도 'S'로 변경.
        
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
        
        model.addAttribute("codeMap",getcodeMap());
        
    	return "/meta/stnd/stndtot_rqst";
	} 
    
   


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
//	@ModelAttribute("codeMap")
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
		
		//표준구분 멀티사전용
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));

		

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}


	/** 표준단어 요청 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/meta/stnd/regStndTot.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStndWordRqstlist(@RequestBody WaqStwds data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqStwd> list = data.get("data");

		int result = stndWordRqstService.register(reqmst, list);
		
		String stwdDcd = UtilString.null2Blank(session.getAttribute("stwdDcd"));
		
		//동음의의어:H,이음동의어:A,둘다허용:T,유니크:U 
		reqmst.setStwdKeyDcd(stwdDcd); 

		stndWordRqstService.check(reqmst);

//		int result = stndWordRqstService.regStndWordRqstlist(list);
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
    @RequestMapping("/meta/stnd/regapproveStndWord.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regapproveStndWord(@RequestBody WaqStwds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		/*
		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}
		*/

		ArrayList<WaqStwd> list = data.get("data");

		int result = stndWordRqstService.regapprove(reqmst, list); 


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
		
		//승인요청 메일전송, 2019.02.11 
		//String reqURL = request.getRequestURI();
		//int tempResult = requestApproveService.sendMail(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

	/** 표준도메인 승인..... @throws Exception insomnia */
	@RequestMapping("/meta/stnd/regapproveStndDmn.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regapproveStndDmn(@RequestBody WaqDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		/*
		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}
		*/

		ArrayList<WaqDmn> list = data.get("data");

		int result  = stndDmnRqstService.regapprove(reqmst, list);

        
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

		//승인요청 메일전송, 2019.02.11 
		//String reqURL = request.getRequestURI();
		//int tempResult = requestApproveService.sendMail(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}

    @RequestMapping("/meta/stnd/regapproveStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regapproveStndItem (@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);
    	String resmsg;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		/*
		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}
		*/

    	List<WaqSditm> list = data.get("data");

    	int result = stndItemRqstService.regapprove(reqmst, list);


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

		//승인요청 메일전송, 2019.02.11 
		//String reqURL = request.getRequestURI();
		//int tempResult = requestApproveService.sendMail(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }

    @RequestMapping("/meta/stnd/approveStndTot.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveStndTot (WaqMstr reqmst, @RequestBody HashMap<String,Object> data, Locale locale) throws Exception {

    	logger.debug("reqmst:{}", reqmst);
    	String resmsg;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		/*
		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
	
		*/
		
		List<WaqStwd> lstWord  = (List<WaqStwd>) data.get("word");
		List<WaqDmn> lstDmn   = (List<WaqDmn>) data.get("dmn");
		List<WaqSditm> lstSditm = (List<WaqSditm>) data.get("item");   
		
		logger.debug("\n lstWord size:" + lstWord.size());
		logger.debug("\n lstDmn size:" + lstDmn.size());
		logger.debug("\n lstSditm size:" + lstSditm.size());

		int result = stndTotRqstService.aprvStndTot(reqmst, lstSditm, lstDmn, lstWord);  
				
    	//int result = stndTotRqstService.approve(reqmst, null);


		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		//승인요청 메일전송, 2019.02.11 
    	//String reqURL = request.getRequestURI();
    	//int tempResult = requestApproveService.sendMail(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }

	/** 표준데이터등록요청 화면 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/meta/stnd/unuseSditm_rqst.do")
	public String goUnuseItemrqstForm(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

//    	logger.debug("reqmst:{}", reqmst);
    	
    	model.addAttribute("waqMstr", reqmst);


//        등록요청 취소 ( 결제가 한차례라도 되었을 경우 등록요청 취소 불가능) 
//        waa_apr_prc테이블에 등록요청번호로 로우가 있고, waq_mstr의 rqst_step_cd가 'A'나 'Q' 인 경우 등록요청 취소 불가능
//        waa_apr_prc테이블에 등록요청번호로 로우가 없고, waq_mstr의 rqst_step_cd가 'Q'인 경우 등록요청 취소 가능
//        필요한 것 
//        1. 로그인 한 사용자와 등록요청한 사용자와 같은 경우 등록요청취소 버튼 출력
//        2. 그 페이지에 필요한것은 요청번호, 로그인한 사용자, 등록요청자, 
//        3. 승인레벨 1인 경우만 체크, 승인레벨 1의 승인자가 빈 경우 - 등록요청중, 안빈 경우 - 승인프로세스타는중이거나 결제완료건.
//        4. 승인레벨 2는 체크할 필요가 없음. 만약 등록요청 취소하는경우 제거만 해주면됨.
//        5. 취소하는경우 'Q'도 'S'로 변경.
        
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
        
        model.addAttribute("codeMap",getcodeMap());
        
    	return "/meta/stnd/unuseSditm_rqst";
	} 
}
