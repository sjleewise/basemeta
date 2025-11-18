/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeTsfRqstCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 코드이관 요청 컨트롤러
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 11. 25. 
 * 6. 변경이력 :

 */
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
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.meta.stnd.service.WamCdValTsf;
import kr.wise.meta.stnd.service.WaqCdVal;
import kr.wise.meta.stnd.service.WaqCdValTsf;
import kr.wise.meta.stnd.service.WaqDmn;

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
@Controller("CodeTsfRqstCtrl")
public class CodeTsfRqstCtrl {

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
	private CodeTsfRqstService codeTsfRqstService;

	@Inject
	private ApproveLineServie approveLineServie;


	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CmvwDbService cmvwDbService;


	static class WaqDmns extends HashMap<String, ArrayList<WaqDmn>> {}

	static class WaqCdValTsfs extends HashMap<String, ArrayList<WaqCdValTsf>> {}


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
		//코드값유형코드
		codeMap.put("cdValTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD")));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		//코드값부여방식코드
		codeMap.put("cdValIvwCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD")));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));


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
    @RequestMapping("meta/stnd/codetsf_rqst.do")
    public String goCodeTsfRqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("CDT");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/stnd/codetsf_rqst";

    }
    
     /** 이관할 코드 검색  .... @throws Exception */
    @RequestMapping("/meta/stnd/popup/codetsf_pop.do")
    public String goCodeTsfSearchpop(@ModelAttribute("search") WaqMstr reqmstr, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);
    	    
    	//ibk는 운영db가 하나이므로 default로 db설정
//    	    logger.debug("운영db설정");
//    	    WaaDbSch result = new WaaDbSch();
//    	    result.setDdlTrgDcd("R");
//          	List<WaaDbSch> list = cmvwDbService.getDbSchemaList(result);
//        	result.setDbConnTrgId(list.get(0).getDbConnTrgId());
//        	result.setDbConnTrgPnm(list.get(0).getDbConnTrgPnm());
//        	result.setDbSchId(list.get(0).getDbSchId());
//        	result.setDbSchPnm(list.get(0).getDbSchPnm());
    	
//        	model.addAttribute("result", result);
        	
    	return "/meta/stnd/popup/codetsf_pop";

    }

   /** 도메인 코드값(유효값) 팝업 조회 -IBSheet json */

	@RequestMapping("/meta/stnd/getdmnvaluetsf_pop.do")
	@ResponseBody
	public IBSheetListVO<WamCdValTsf> selectDmnValueList(WamCdValTsf search, Model model, Locale locale) throws Exception {

		logger.debug("{}", search);

		List<WamCdValTsf> list = codeTsfRqstService.selectDmnListTsf(search);
		return new IBSheetListVO<WamCdValTsf>(list, list.size());
	}
	
	     /** 이관된 코드 검색 팝업  .... @throws Exception */
    @RequestMapping("/meta/stnd/popup/codetsfwam_pop.do")
    public String goCodeTsfWamSearchpop(@ModelAttribute("search") WaqMstr reqmstr, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

    	//ibk는 운영db가 하나이므로 default로 db설정
//    	    logger.debug("운영db설정");
//    	    WaaDbSch result = new WaaDbSch();
//    	    result.setDdlTrgDcd("R");
//          	List<WaaDbSch> list = cmvwDbService.getDbSchemaList(result);
//        	result.setDbConnTrgId(list.get(0).getDbConnTrgId());
//        	result.setDbConnTrgPnm(list.get(0).getDbConnTrgPnm());
//        	result.setDbSchId(list.get(0).getDbSchId());
//        	result.setDbSchPnm(list.get(0).getDbSchPnm());
//    	
//        	model.addAttribute("result", result);
    	
    	return "/meta/stnd/popup/codetsfwam_pop";

    }

   /** 코드값(유효값) 이관(wam)팝업 조회 -IBSheet json */

	@RequestMapping("/meta/stnd/getcdtsfwam_pop.do")
	@ResponseBody
	public IBSheetListVO<WamCdValTsf> selectCdTsfWamList(WamCdValTsf search, Model model, Locale locale) throws Exception {

		logger.debug("{}", search);

		List<WamCdValTsf> list = codeTsfRqstService.selectCdTsfWamList(search);
		return new IBSheetListVO<WamCdValTsf>(list, list.size());
	}


	/** 코드 이관대상 추가..(팝업에서 호출함) @throws Exception insomnia */
	@RequestMapping("/meta/stnd/regCodeTsf.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regCodeTsflist(@RequestBody WaqCdValTsfs data, WaqMstr reqmst,Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		logger.debug("코드이관등록");
		

		ArrayList<WaqCdValTsf> list = data.get("data");
        //logger.debug("요청상태구분 : " + list.get(0).getRqstDcd());
		int result = codeTsfRqstService.regCodeTsf(reqmst, list);

		result += codeTsfRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
     
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
        logger.debug("액션 : " + action);
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
	
	/** 도메인 코드값(유효값) 요청서 조회 -IBSheet json */

	@RequestMapping("/meta/stnd/getcodetsfrqstlist.do")
	@ResponseBody
	public IBSheetListVO<WaqCdValTsf> selectDmnValueRqstList(WaqMstr reqmst) throws Exception {

		logger.debug("{}", reqmst);
		List<WaqCdValTsf> list = codeTsfRqstService.selectDmnValueRqstList(reqmst);

		return new IBSheetListVO<WaqCdValTsf>(list, list.size());
	}

	    /** 코드이관 요청서 상세정보 조회 @return  
     * @throws Exception */
    @RequestMapping("/meta/stnd/ajaxgrid/codetsf_rqst_dtl.do")
    public String goCodeTsfRqstDetail(WaqCdValTsf searchVo, ModelMap model) throws Exception {
    	logger.debug("searhcVo:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	} else {
    		WaqCdValTsf result = codeTsfRqstService.getCodeTsfRqstDetail(searchVo);
    		model.addAttribute("result", result);
    		model.addAttribute("saction", "U");
    	}

    
    	return "/meta/stnd/codetsf_rqst_dtl";
    }

    @RequestMapping("/meta/stnd/delcodetsfrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delCodeTsfRqstList(@RequestBody WaqCdValTsfs data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);

    	ArrayList<WaqCdValTsf> list = data.get("data");

    	int result = codeTsfRqstService.delCodeTsfRqstList(reqmst, list);
    	result += codeTsfRqstService.check(reqmst);

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
    @RequestMapping("/meta/stnd/approvecodetsf.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveCodeTsf (@RequestBody WaqCdValTsfs data, WaqMstr reqmst, Locale locale) throws Exception {

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

    	List<WaqCdValTsf> list = data.get("data");

    	int result = codeTsfRqstService.approve(reqmst, list);


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





