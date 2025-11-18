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
package kr.wise.meta.ddl.web;

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
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlCol;
import kr.wise.meta.ddl.service.WaqDdlRel;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WaqPdmTbl;

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
 * 2. FileName  : DdlTblRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL 테이블 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 14. 오후 2:42:43
 * </PRE>
 */
@Controller("DdlTblRqstCtrl")
public class DdlTblRqstCtrl {

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
	private DdlTblRqstService ddlTblRqstService;

	@Inject
	private ApproveLineServie approveLineServie;


	static class WaqPdmTbls extends HashMap<String, ArrayList<WaqPdmTbl>> {}

	static class WaqDdlTbls extends HashMap<String, ArrayList<WaqDdlTbl>> {}

	static class WaqDdlRels extends HashMap<String, ArrayList<WaqDdlRel>> {}

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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//테이블변경유형코드
		codeMap.put("tblChgTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_CHG_TYP_CD")));
		codeMap.put("tblChgTypCd", cmcdCodeService.getCodeList("TBL_CHG_TYP_CD"));

		//관계유형코드
		codeMap.put("relTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REL_TYP_CD")));
		codeMap.put("relTypCd", cmcdCodeService.getCodeList("REL_TYP_CD"));
		//카디널리티유형코드
		codeMap.put("crdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CRD_TYP_CD")));
		codeMap.put("crdTypCd", cmcdCodeService.getCodeList("CRD_TYP_CD"));
		//Parent Optionality 유형코드
		codeMap.put("paOptTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PA_OPT_TYP_CD")));
		codeMap.put("paOptTypCd", cmcdCodeService.getCodeList("PA_OPT_TYP_CD"));
		
		//인덱스컬럼정렬순서
		codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));

		
		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
		String sysarea 		= UtilJson.convertJsonString(sysareatmp);
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));


		
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);
		
		
		

		return codeMap;
	}

    /** DDL테이블 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/ddl/ddltbl_rqst.do")
    public String goModelPdmTblRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DDL");
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

    	return "/meta/ddl/ddltbl_rqst";

    }

    /** DDL 테이블 요청 리스트 조회 @return insomnia */
    @RequestMapping("/meta/ddl/getddltblrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlTbl> getPdmTblRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqDdlTbl> list = ddlTblRqstService.getDdlTblRqstList(search);


		return new IBSheetListVO<WaqDdlTbl>(list, list.size());
	}

    /** DDL 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/ddl/ajaxgrid/ddltbl_rqst_dtl.do")
    public String getDdltblDetail(WaqDdlTbl searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDdlTbl resultvo =  ddlTblRqstService.getDdlTblRqstDetail(searchVo);
    		
    		if(searchVo.getRvwStsCd() != null && !searchVo.getRvwStsCd().equals(""))
    			resultvo.setRvwStsCd(searchVo.getRvwStsCd());
    		if(searchVo.getRvwConts() != null && !searchVo.getRvwConts().equals(""))
    			resultvo.setRvwConts(searchVo.getRvwConts());
    		
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/ddl/ddltbl_rqst_dtl";

    }

    /** DDL 테이블 추가용 팝업 (물리모델 조회 팝업) @return insomnia */
    @RequestMapping("/meta/ddl/popup/ddltbl_pop.do")
    public String goDdlTblPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		return "/meta/ddl/popup/ddltbl_pop";
	}
    
    /** DDL 이관 테이블 요청 리스트 조회 @return insomnia */
    @RequestMapping("/meta/ddl/getDdlTsfTblRqstList.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlTbl> getDdlTsfTblRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqDdlTbl> list = ddlTblRqstService.getDdlTsfTblRqstList(search);


		return new IBSheetListVO<WaqDdlTbl>(list, list.size());
	} 

    /** 물리모델 to DDL 테이블 @throws Exception insomnia */
    @RequestMapping("/meta/ddl/regWam2WaqDdlTbl.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

//		System.out.println("요청상태" + reqmst.getRqstDcd());
		ArrayList<WaqPdmTbl> list = data.get("data");

		int result = ddlTblRqstService.regWam2Waq(reqmst, list);

	    result += ddlTblRqstService.check(reqmst);

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

    @RequestMapping("/meta/model/delddltblrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlTblRqstList(@RequestBody WaqDdlTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlTbl> list = data.get("data");

		int result = ddlTblRqstService.delDdlTblRqst(reqmst, list);

		result += ddlTblRqstService.check(reqmst);

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

    @RequestMapping("/meta/ddl/regddltblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmTblRqstList(@RequestBody WaqDdlTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlTbl> list = data.get("data");
		int result = ddlTblRqstService.register(reqmst, list);

		result += ddlTblRqstService.check(reqmst);

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

    @RequestMapping("/meta/ddl/getddlcolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlCol> getPdmColRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqDdlCol> list = ddlTblRqstService.getDdlColRqstList(search);

		return new IBSheetListVO<WaqDdlCol>(list, list.size());
	}
    
    /** DDL 관계 요청리스트 조회 */
    @RequestMapping("/meta/ddl/getddlrelrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlRel> getDdlRelRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqDdlRel> list = ddlTblRqstService.getDdlRelRqstList(search);

		return new IBSheetListVO<WaqDdlRel>(list, list.size());
	}

    @RequestMapping("/meta/ddl/approveddltbl.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlTbl(@RequestBody WaqDdlTbls data, WaqMstr reqmst, Locale locale) throws Exception {

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


		ArrayList<WaqDdlTbl> list = data.get("data");

		int result  = ddlTblRqstService.approve(reqmst, list);


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

    /** DDL 관계 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/ddl/delddlrelrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlRelRqstList(@RequestBody WaqDdlRels data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlRel> list = data.get("data");

		int result = ddlTblRqstService.delDdlRelRqst(reqmst, list);

		result += ddlTblRqstService.check(reqmst);

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
    

    /** DDL to DDL이관 @throws Exception yeonho */
    @RequestMapping("/meta/ddl/regWam2WaqDdlTsfTbl.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2WaqDdlTsfTbl(@RequestBody WaqDdlTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);	

		ArrayList<WaqDdlTbl> list = data.get("data");
		
		int result = ddlTblRqstService.regTsfWam2Waq(reqmst, list); 
		
		
		result += ddlTblRqstService.check(reqmst);

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

    /** DDL to DDL이관 @throws Exception yeonho */
    @RequestMapping("/meta/ddl/regDdlTsfSaveRow.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regDdlTsfSaveRow(@RequestBody WaqDdlTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);	

		ArrayList<WaqDdlTbl> list = data.get("data");
		
		//int result = ddlTblRqstService.regTsfWam2Waq(reqmst, list);
		
		int result = ddlTblRqstService.registerTsf(reqmst, list);  
		
		
		result += ddlTblRqstService.check(reqmst);

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
