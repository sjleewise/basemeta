/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageTsfRqstCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 코드이관 요청 컨트롤러
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2014. 4. 15. 오후 2:18:13
 * 6. 변경이력 :
 *                 
 *                  */
package kr.wise.meta.stnd.web;

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
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.meta.stnd.service.CodeTsfRqstService;
import kr.wise.meta.stnd.service.MessageTsfRqstService;
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.meta.stnd.service.WamCdValTsf;
import kr.wise.meta.stnd.service.WamMsgTsf;
import kr.wise.meta.stnd.service.WaqCdVal;
import kr.wise.meta.stnd.service.WaqCdValTsf;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqMsgTsf;

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

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDmnRqstCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 15. 오후 2:18:13
 * </PRE>
 */
@Controller("MessageTsfRqstCtrl")
public class MessageTsfRqstCtrl {

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
	private MessageTsfRqstService messageTsfRqstService;
	
	@Inject
	private ApproveLineServie approveLineServie;


	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject CmvwDbService cmvwDbService;
	
	static class WaqMsgTsfs extends HashMap<String, ArrayList<WaqMsgTsf>> {}

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
		
		
		//메시지 유형코드
		codeMap.put("msgPtrnDvcd", cmcdCodeService.getCodeList("MSG_PTRN_DVCD"));
		codeMap.put("msgPtrnDvcdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MSG_PTRN_DVCD")));
		
		//메시지 등록요청용 업무구분코드
		codeMap.put("bizDivCd", cmcdCodeService.getCodeList("BIZ_DIV_CD"));
		codeMap.put("bizDivCdibs",UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DIV_CD")));


//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
/*		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		List<CodeListVo> dmng 	= codeListService.getCodeList("dmng");

		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
		codeMap.put("dmng", UtilJson.convertJsonString(dmng));
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dmng)));*/

       List<CodeListVo> connTrgSchIdCodeTsf = codeListService.getCodeList("connTrgSchIdCodeTsf");
       codeMap.put("connTrgSchIdCodeTsf", UtilJson.convertJsonString(connTrgSchIdCodeTsf));

		return codeMap;
	}


    /** 코드이관 요청서 .... @throws Exception */
    @RequestMapping("meta/stnd/messagetsf_rqst.do")
    public String goCodeTsfRqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("MST");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/stnd/messagetsf_rqst";

    }
    
     /** 이관할 메시지 검색  .... @throws Exception */
    @RequestMapping("/meta/stnd/popup/messagetsf_pop.do")
    public String goCodeTsfSearchpop(@ModelAttribute("search") WaqMstr reqmstr, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

    	logger.debug("운영db설정");
    	    WaaDbSch result = new WaaDbSch();
    	    result.setDdlTrgDcd("R");
          	List<WaaDbSch> list = cmvwDbService.getDbSchemaList(result);
        	result.setDbConnTrgId(list.get(0).getDbConnTrgId());
        	result.setDbConnTrgPnm(list.get(0).getDbConnTrgPnm());
        	result.setDbSchId(list.get(0).getDbSchId());
        	result.setDbSchPnm(list.get(0).getDbSchPnm());
    	
        	model.addAttribute("result", result);
    	return "/meta/stnd/popup/messagetsf_pop";

    }

   /**이관할 메시지 팝업 조회 -IBSheet json */

	@RequestMapping("/meta/stnd/getmessagetsflist_pop.do")
	@ResponseBody
	public IBSheetListVO<WaqMsgTsf> selectMessageList(WaqMsgTsf search, Model model, Locale locale) throws Exception {

		logger.debug("{}", search);

		List<WaqMsgTsf> list = messageTsfRqstService.selectMsgListTsf(search);
		return new IBSheetListVO<WaqMsgTsf>(list, list.size());
	}
	
	
	
	     /** 이관된 메시지 검색 팝업  .... @throws Exception */
    @RequestMapping("/meta/stnd/popup/messagetsfwam_pop.do")
    public String goMessageTsfWamSearchpop(@ModelAttribute("search") WaqMstr reqmstr, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);
        //db가 하나이기 때문에 default 설정
    	logger.debug("운영db설정");
    	    WaaDbSch result = new WaaDbSch();
    	    result.setDdlTrgDcd("R");
          	List<WaaDbSch> list = cmvwDbService.getDbSchemaList(result);
        	result.setDbConnTrgId(list.get(0).getDbConnTrgId());
        	result.setDbConnTrgPnm(list.get(0).getDbConnTrgPnm());
        	result.setDbSchId(list.get(0).getDbSchId());
        	result.setDbSchPnm(list.get(0).getDbSchPnm());
    	
        	model.addAttribute("result", result);
        	
    	return "/meta/stnd/popup/messagetsfwam_pop";

    }

   /**  메시지 이관 삭제용 팝업 조회 -IBSheet json */

	@RequestMapping("/meta/stnd/getmessagetsfwam_pop.do")
	@ResponseBody
	public IBSheetListVO<WaqMsgTsf> selectCdTsfWamList(WaqMsgTsf search, Model model, Locale locale) throws Exception {

		logger.debug("{}", search);

		List<WaqMsgTsf> list = messageTsfRqstService.selectMsgWamList(search);
		return new IBSheetListVO<WaqMsgTsf>(list, list.size());
	}


	/** 메시지 이관대상 추가..(팝업에서 호출함) @throws Exception insomnia */
	@RequestMapping("/meta/stnd/regMessageTsf.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regCodeTsflist(@RequestBody WaqMsgTsfs data, WaqMstr reqmst,Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		logger.debug("메시지이관등록");
		

		ArrayList<WaqMsgTsf> list = data.get("data");
        logger.debug("요청상태구분 : " + list.get(0).getRqstDcd());
        
        
        int result = messageTsfRqstService.regMessageTsf(reqmst, list);
		result += messageTsfRqstService.check(reqmst);

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
	
	/** 도메인 코드값(유효값) 요청서 조회 -IBSheet json */

	@RequestMapping("/meta/stnd/getmsgtsfrqstlist.do")
	@ResponseBody
	public IBSheetListVO<WaqMsgTsf> selectMsgTsfRqstList(WaqMstr reqmst) throws Exception {

		logger.debug("{}", reqmst);
		List<WaqMsgTsf> list = messageTsfRqstService.selectMsgTsfRqstList(reqmst);

		return new IBSheetListVO<WaqMsgTsf>(list, list.size());
	}

	    /** 메시지 요청서 상세정보 조회 @return  
     * @throws Exception */
    @RequestMapping("/meta/stnd/ajaxgrid/messagetsf_rqst_dtl.do")
    public String goMessageTsfRqstDetail(WaqMsgTsf searchVo, ModelMap model) throws Exception {
    	logger.debug("searhcVo:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	} else {
    		WaqMsgTsf result = messageTsfRqstService.getMsgTsfRqstDetail(searchVo);
    		model.addAttribute("result", result);
    		model.addAttribute("saction", "U");
    	}

    
    	return "/meta/stnd/messagetsf_rqst_dtl";
    }

    @RequestMapping("/meta/stnd/delmsgtsfrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delMsgRqstList(@RequestBody WaqMsgTsfs data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);

    	ArrayList<WaqMsgTsf> list = data.get("data");

    	int result = messageTsfRqstService.delCodeTsfRqstList(reqmst, list);
    	result += messageTsfRqstService.check(reqmst);

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
    
     //메시지 승인
    @RequestMapping("/meta/stnd/approvemsgtsf.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveCodeTsf (@RequestBody WaqMsgTsfs data, WaqMstr reqmst, Locale locale) throws Exception {

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

    	List<WaqMsgTsf> list = data.get("data");

    	int result = messageTsfRqstService.approve(reqmst, list);


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





