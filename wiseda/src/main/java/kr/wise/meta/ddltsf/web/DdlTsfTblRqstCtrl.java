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
package kr.wise.meta.ddltsf.web;

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
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlTbl;

import kr.wise.meta.ddltsf.service.DdlTsfRqstService;
import kr.wise.meta.ddltsf.service.WaaDbMapVo;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;
import kr.wise.meta.ddltsf.service.WaqDdlTsfCol;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxCol;
import kr.wise.meta.ddltsf.service.WaqDdlTsfRel;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTbl;

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
@Controller("DdlTsfTblRqstCtrl")
public class DdlTsfTblRqstCtrl {

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
	private DdlTsfRqstService ddlTsfRqstService;

	@Inject
	private ApproveLineServie approveLineServie;



	static class WaqDdlTbls extends HashMap<String, ArrayList<WaqDdlTbl>> {}

	static class WaqDdlTsfTbls extends HashMap<String, ArrayList<WaqDdlTsfTbl>> {}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);
		//테이블스페이스 정보(double_select용 목록성코드)
		String tblSpac   = UtilJson.convertJsonString(codeListService.getCodeList("tblSpac"));
		codeMap.put("tblSpac", tblSpac);
		
		
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
		
		//인덱스유형코드
		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		
		//인덱스컬럼정렬순서
		//codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		//codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));
		
		//오브젝트구분코드
		codeMap.put("objDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OBJ_DCD")));
		codeMap.put("objDcd", cmcdCodeService.getCodeList("OBJ_DCD"));
		//목록성 코드(시스템영역 코드리스트)
//		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
//		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));


//		codeMap.put("sysarea", sysarea);
//		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

    /** DDL테이블 요청서 입력 폼.... @throws Exception yeonho */
    @RequestMapping("/meta/ddltsf/ddltsftbl_rqst.do")
    public String goModelPdmTblRqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DTT");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/ddltsf/ddltsftbl_rqst";

    }

    /** DDL 이관 요청 리스트 조회 @return yeonho */
    @RequestMapping("/meta/ddltsf/getddltsftblrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlTsfTbl> getDdlTsfTblRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqDdlTsfTbl> list = ddlTsfRqstService.getDdlTsfTblRqstList(search);


		return new IBSheetListVO<WaqDdlTsfTbl>(list, list.size());
	}

    /** DDL 테이블 요청 상세 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddltsf/ddltsftbl_rqst_dtl.do")
    public String getDdltblDetail(WaqDdlTsfTbl searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDdlTsfTbl resultvo =  ddlTsfRqstService.getDdlTsfTblRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/ddltsf/ddltsftbl_rqst_dtl";

    }
    
//    /** DDL 인덱스 요청 상세 정보 조회 @return yeonho */
//    @RequestMapping("/meta/ddltsf/ddltsftbl_rqst_dtl_idx.do")
//    public String getDdlIdxDetail(WaqDdlTsfTbl searchVo, ModelMap model) {
//
//    	logger.debug("searchVO:{}", searchVo);
//
//    	if(searchVo.getRqstSno() == null) {
//    		model.addAttribute("saction", "I");
//
//    	} else {
//    		WaqDdlTsfTbl resultvo =  ddlTsfRqstService.getDdlTsfIdxRqstDetail(searchVo);
//    		model.addAttribute("result", resultvo);
//    		model.addAttribute("saction", "U");
//    	}
//
//    	return "/meta/ddltsf/ddltsftbl_rqst_dtl";
//
//    }
    

    /** DDL 이관 대상 추가용 팝업 (DDL 이관대상 조회 팝업) @return yeonho */
    @RequestMapping("/meta/ddltsf/popup/ddltsftbl_pop.do")
    public String goDdlTblPop(@ModelAttribute("search") WaaDbMapVo search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		return "/meta/ddltsf/popup/ddltsftbl_pop";
	}
    
    /** DDL 이관 대상 추가용 팝업 (DDL 이관대상 조회 팝업) @return yeonho */
    @RequestMapping("/meta/ddltsf/popup/ddltsfidx_pop.do")
    public String goDdlTsfIdxPop(@ModelAttribute("search") WaaDbMapVo search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		return "/meta/ddltsf/popup/ddltsfidx_pop";
	}

    /** DDL 이관 대상 조회 - IBSheet JSON @return yeonho */
    @RequestMapping("/meta/ddltsf/getDdlTsfTblList.do")
    @ResponseBody
    public IBSheetListVO<WamDdlTsfObj> getDdlObjectList(@ModelAttribute("search") WaaDbMapVo search,  Locale locale) {

    	logger.debug("search:{}", search);
    	List<WamDdlTsfObj> list = ddlTsfRqstService.getDdlTsfList(search);

    	return new IBSheetListVO<WamDdlTsfObj>(list, list.size());
    }

    /** 팜업에서 DDL to DDL이관 @throws Exception yeonho */
    @RequestMapping("/meta/ddltsf/regWam2WaqDdlTsfTbl.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqDdlTsfTbls data, WaqDdlTsfTbl dbmsInfo, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		logger.debug("dbmsInfo:{}", dbmsInfo);

		ArrayList<WaqDdlTsfTbl> list = data.get("data");

		int result = ddlTsfRqstService.regWam2Waq(reqmst, list, dbmsInfo);

		result += ddlTsfRqstService.check(reqmst);

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
    
    

    @RequestMapping("/meta/ddltsf/delddltsftblrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlTsfTblRqstList(@RequestBody WaqDdlTsfTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlTsfTbl> list = data.get("data");

		int result = ddlTsfRqstService.delDdlTsfTblRqst(reqmst, list);

		result += ddlTsfRqstService.check(reqmst);

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

    @RequestMapping("/meta/ddltsf/regddltsftblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlTsfTblRqstList(@RequestBody WaqDdlTsfTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlTsfTbl> list = data.get("data");

		int result = ddlTsfRqstService.register(reqmst, list);

		result += ddlTsfRqstService.check(reqmst);

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

    /** DDL 이관컬럼 요청리스트 조회 */
    /** yeonho */
    @RequestMapping("/meta/ddltsf/getddltsfcolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlTsfCol> getDdlTsfColRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqDdlTsfCol> list = ddlTsfRqstService.getDdlTsfColRqstList(search);

		return new IBSheetListVO<WaqDdlTsfCol>(list, list.size());
	}

    @RequestMapping("/meta/ddltsf/approveddltsftbl.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlTsfTbl(@RequestBody WaqDdlTsfTbls data, WaqMstr reqmst, Locale locale) throws Exception {

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


		ArrayList<WaqDdlTsfTbl> list = data.get("data");

		int result  = ddlTsfRqstService.approve(reqmst, list);


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

    /** DDL 이관관계 요청리스트 조회 */
    /** yeonho */
    @RequestMapping("/meta/ddltsf/getddltsfrelrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlTsfRel> getDdlTsfRelRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqDdlTsfRel> list = ddlTsfRqstService.getDdlTsfRelRqstList(search);

		return new IBSheetListVO<WaqDdlTsfRel>(list, list.size());
	}
    
    /** DDL 이관 인덱스컬럼 요청리스트 조회 */
    /** yeonho */
    @RequestMapping("/meta/ddltsf/getddltsfidxcolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlTsfIdxCol> getDdlTsfIdxColRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqDdlTsfIdxCol> list = ddlTsfRqstService.getDdlTsfIdxColRqstList(search);

		return new IBSheetListVO<WaqDdlTsfIdxCol>(list, list.size());
	}


}
