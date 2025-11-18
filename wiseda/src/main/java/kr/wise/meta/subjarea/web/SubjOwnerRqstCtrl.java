/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTblRqstCtrl.java
 * 2. Package : kr.wise.meta.ddl.web
 * 3. Comment : DDL 테이블 요청 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 14. 오후 2:42:43
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 14. :            : 신규 개발.
 */
package kr.wise.meta.subjarea.web;

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
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
//import kr.wise.commons.util.UtilApi;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.tblrel.service.ProfilePT01Service;
import kr.wise.meta.subjarea.service.SubjOwnerRqstService;
import kr.wise.meta.subjarea.service.WaaSubj;
//import kr.wise.softverk.PassManager;
//import kr.wise.sysinf.erwin.service.MartUserVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * 2. FileName  : SubjOwnerRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : 기타오브젝트 요청 컨트롤러...
 * 5. 작성자   : ssh
 * 6. 작성일   : 2017. 02. 10. 오후 2:42:43
 * </PRE>
 */
@Controller("SubjOwnerRqstCtrl")
public class SubjOwnerRqstCtrl {

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
	private SubjOwnerRqstService subjOwnerRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private RequestApproveService requestApproveService;	

//	@Inject 
//	private UtilApi UtilApi;
//	
	static class WaaSubjs extends HashMap<String, ArrayList<WaaSubj>> {}
	
	
	// erwin 권한 전달
	@Inject
	private ProfilePT01Service pt01Service;		


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		Map<String, Object> codeMap = new HashMap<String, Object>(); 

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
		
		return codeMap;
	}

    /** 주제영역 권한 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/subjarea/subjowner_rqst.do")
    public String goDdlEtcRqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	//logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("SOW");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	//logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/subjarea/subjowner_rqst";

    }

    
    @RequestMapping("/meta/subjarea/subjownerrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlEtcRqstList(@RequestBody WaaSubjs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaaSubj> list = data.get("data");

		int result = subjOwnerRqstService.register(reqmst, list);

		result += subjOwnerRqstService.check(reqmst);

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
    
    
    /** 요청 리스트 조회 */
    @RequestMapping("/meta/subjarea/getsubjownerrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaaSubj> getSubjOwnerRqstList(WaqMstr search) {
		//logger.debug("search:{}", search);
		List<WaaSubj> list = subjOwnerRqstService.getSubjOwnerRqstList(search); 
		return new IBSheetListVO<WaaSubj>(list, list.size());
	}
    
   
    @RequestMapping("/meta/subjarea/delsubjownerrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delSubjOwnerRqstList(@RequestBody WaaSubjs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaaSubj> list = data.get("data");

		int result = subjOwnerRqstService.delSubjOwnerRqstList(reqmst, list);
		result += subjOwnerRqstService.check(reqmst);
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

    /***
     * 승인처리
     * @param data
     * @param reqmst
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping("/meta/subjarea/approveSubjOwer.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlEtc(@RequestBody WaaSubjs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String rqstStepCd = reqmst.getRqstStepCd(); //
		String bizDcd = reqmst.getBizDcd(); // 업무구분?(SOW:주제영역권한관리)

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}

		ArrayList<WaaSubj> list = data.get("data");
		int result  = subjOwnerRqstService.approve(reqmst, list);

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
		
		// 주제영역권한요청, 처리완료시 erwin에 권한정보 넘김, 2019.03.12, hoon		
//		if("SOW".equals(bizDcd) && result==0){
//		    for (int i = 0; i < list.size(); i++) {
//		    	WaaSubj resultWaaSubj =  list.get(0); //
//		    	// 주제영역 전체경로(ebay>PublicationSystemSample_2018R1_TEST>Book)
//
//		    	String resultSubjLnm = UtilString.null2Blank(resultWaaSubj.getSubjLnm()); 
//		    	String resultRegTypCd = UtilString.null2Blank(resultWaaSubj.getRegTypCd()); // 등록유형, I/U/D
//		    	String resultRvwStsCd = resultWaaSubj.getRvwStsCd();	//    RVW_STS_CD 1:승인, 2:반려, 3:부분승인
//                         
//		    	logger.debug("resultRvwStsCd="+ resultRvwStsCd +" resultRegTypCd="+ resultRegTypCd +" / resultSubjLnm="+resultSubjLnm +"["+ resultSubjLnm.replaceAll(">", "/") +"]");
//		    	
//		    	// 
//		    	if(!"".equals(resultSubjLnm) && !"".equals(resultRegTypCd) && "1".equals(resultRvwStsCd)){
//		    		String cResult = "";
//					MartUserVO martUserVO = new MartUserVO();
//					martUserVO.setUsername("ebaykorea\\"+resultWaaSubj.getRqstUserId());
//					martUserVO.setUsertype("1");
//					martUserVO.setSubjectpath(UtilApi.getSubjectpath(resultSubjLnm)); 	// 주제영역 경로		    	
//					martUserVO.setRolename("Modeler");
//					martUserVO.setcResult("");		
//					
//					if("D".equals(resultRegTypCd)){
//						// 권한제거
//						cResult = pt01Service.delProfileassignment(martUserVO);	
//					}else{
//						// 권한추가
//						cResult = pt01Service.addProfileassignment(martUserVO);
//					}		
//					logger.debug("cResult : "+ cResult);
//		    	}
//		    }			
//		}
		
		
		// mail 발송
    	//결재승인/반려 메일전송, 2019.02.11 
    	//String reqURL = request.getRequestURI();
//    	//int tempResult = requestApproveService.sendMail(reqmst);		
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
}
