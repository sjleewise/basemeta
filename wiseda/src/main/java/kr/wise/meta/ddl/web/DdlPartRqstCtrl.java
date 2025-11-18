/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlPartRqstCtrl.java
 * 2. Package : kr.wise.meta.ddl.web
 * 3. Comment : DDL 파티션 요청 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2016. 12. 01.
 * 6. 변경이력 :
 */
package kr.wise.meta.ddl.web;

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

import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlPartRqstService;
import kr.wise.meta.ddl.service.DdlSeqRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlPartMain;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

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
 * 2. FileName  : DdlPartRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL 파티션 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2016. 12. 01. 
 * </PRE>
 */
@Controller
public class DdlPartRqstCtrl {

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

//	@Inject
//	private DdlIdxRqstService ddlIdxRqstService;

	@Inject
	private DdlPartRqstService ddlPartRqstService;
	@Inject
	private DdlTblRqstService ddlTblRqstService;
	@Inject
	private DdlIdxRqstService ddlIdxRqstService;

	@Inject
	private DdlSeqRqstService ddlSeqRqstService;

	@Inject
	private ApproveLineServie approveLineServie;


//	static class WaqDdlIdxCols extends HashMap<String, ArrayList<WaqDdlIdxCol>> {}
//	static class WaqDdlIdxs extends HashMap<String, ArrayList<WaqDdlIdx>> {}
	
	static class WaqDdlParts extends HashMap<String, ArrayList<WaqDdlPart>> {}
	static class WaqDdlPartMains extends HashMap<String, ArrayList<WaqDdlPartMain>> {} 
	static class WaqDdlPartSubs extends HashMap<String, ArrayList<WaqDdlPartSub>> {} 
//	static class WaqDdlMainParts extends HashMap<String, Object> {}
	

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		Map<String, Object> codeMap = new HashMap<String, Object>(); 

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
//		codeMap.put("tblChgTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_CHG_TYP_CD")));
//		codeMap.put("tblChgTypCd", cmcdCodeService.getCodeList("TBL_CHG_TYP_CD"));
		
		//파티션유형코드
//		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
//		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		codeMap.put("partTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PART_TYP_CD")));
		codeMap.put("partTypCd", cmcdCodeService.getCodeList("PART_TYP_CD"));
		
		//인덱스컬럼정렬순서
//		codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
//		codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));

		//적용요청구분코드
		codeMap.put("aplReqTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("APL_REQ_TYP_CD")));
		codeMap.put("aplReqTypCd", cmcdCodeService.getCodeList("APL_REQ_TYP_CD"));
				
		//목록성 코드(시스템영역 코드리스트)
		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));


		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		String connTrgSchTsf   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchIdTsf"));
		codeMap.put("connTrgSchTsf", connTrgSchTsf);
		String connTrgSchJsonBySubjSchMap = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchJsonBySubjSchMap"));
		codeMap.put("connTrgSchJsonBySubjSchMap", connTrgSchJsonBySubjSchMap);

		return codeMap;
	}
	
	
	/** DDL 파티션 변경대상 추가 팝업  */
    @RequestMapping("/meta/ddl/popup/ddlpart_pop.do")
    public String goDdlSeqPop(@ModelAttribute("search") WaqDdlPart search, Model model, Locale locale) {
    	//logger.debug("DDL 파티션 변경대상 추가 팝업:{}", search);
    	
    	return "/meta/ddl/popup/ddlpart_pop";
    }
    
	/** DDL 파티션 WAM 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getddlpartlist.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlPart> selectDdlSeqList(@ModelAttribute WaqDdlPart search) {
		//logger.debug("DDL 파티션 WAM 리스트 조회:{}", search);
		List<WaqDdlPart> list = ddlPartRqstService.getWamPartList(search);

		return new IBSheetListVO<WaqDdlPart>(list, list.size());
	}
	
	/** DDL 파티션 변경대상 추가 @throws Exception insomnia */
    @RequestMapping("/meta/ddl/regWam2WaqDdlPart.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqDdlParts data, WaqMstr reqmst, Locale locale) throws Exception {
		//logger.debug("DDL 파티션 변경대상 추가:{}", reqmst);
		
		ArrayList<WaqDdlPart> list = data.get("data");

		int result = ddlPartRqstService.regWam2Waq(reqmst, list);

		result += ddlPartRqstService.check(reqmst);

		String resmsg;

		if(result >= 0) {
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

	/** DDL 파티션 엑셀업로드 팝업 @return yeonho */
    @RequestMapping("/meta/ddl/popup/ddlpart_xls.do")
    public String goDdlPartXlsPop(@ModelAttribute("search") WaqDdlPart search, Model model, Locale locale) {
    	//logger.debug("DDL 파티션 엑셀업로드 팝업:{}", search);
    	
    	return "/meta/ddl/popup/ddlpart_xls";
    }
    
	
    /** DDL파티션 요청서 입력 폼.... @throws Exception yeonho */
    @RequestMapping("/meta/ddl/ddlpart_rqst.do")
    public String goDdlPartRqst(WaqMstr reqmst, ModelMap model) throws Exception {
    	//logger.debug("DDL파티션 요청서 입력 폼:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DDP");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

//    	//logger.debug("reqmst:{}", reqmst);
    	reqmst.setDdlTrgDcd("D"); //개발
    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/ddl/ddlpart_rqst";

    }

    /** DDL 파티션 요청 리스트 조회 @return yeonho */
    @RequestMapping("/meta/ddl/getddlpartrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlPart> getDdlPartRqstList(WaqMstr search) {

		//logger.debug("DDL 파티션 요청 리스트 조회:{}", search);

		List<WaqDdlPart> list = ddlPartRqstService.getDdlPartRqstList(search);


		return new IBSheetListVO<WaqDdlPart>(list, list.size());
	}

    
    /** DDL 파티션 요청 상세 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/ajaxgrid/ddlpart_rqst_dtl.do")
    public String getDdlPartDetail(WaqDdlPart searchVo, ModelMap model) {

    	//logger.debug("DDL 파티션 요청 상세 정보 조회:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		
    		model.addAttribute("saction", "I");
    		
    		//ibk신용정보 db가 개발은 1개이므로 db정보 default로 세팅
    		//우리은행에는 미적용(16.11.14 유성열)
//    		WaaDbSch search = new WaaDbSch();
//    		search.setTblSpacTypCd("I");
//    		search.setDdlTrgDcd("D");
//            List<WaaDbSch> resultVo = cmvwDbService.getDbSchemaList(search);
//            WaqDdlPart result = new WaqDdlPart();
//            result.setDbConnTrgId(resultVo.get(0).getDbConnTrgId());
//            result.setDbConnTrgPnm(resultVo.get(0).getDbConnTrgPnm());
//            result.setDbSchId(resultVo.get(0).getDbSchId());
//            result.setDbSchPnm(resultVo.get(0).getDbSchPnm());
//            result.setIdxSpacId(resultVo.get(0).getDbTblSpacId());
//            result.setIdxSpacPnm(resultVo.get(0).getDbTblSpacPnm());
//            
//            model.addAttribute("result", result);
            
    	} else {
    		WaqDdlPart resultvo =  ddlPartRqstService.getDdlPartRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/ddl/ddlpart_rqst_dtl";

    }
    
    /** DDL 주파티션 요청 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/getddlmainpartrqst.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlPartMain> getDdlPartMainRqstList(WaqMstr search) {
		//logger.debug("DDL 주파티션 요청 정보 조회:{}",search);

		List<WaqDdlPartMain> list = ddlPartRqstService.getDdlPartMainRqstList(search);

		return new IBSheetListVO<WaqDdlPartMain>(list, list.size());
	}

    /** DDL 서브파티션 요청 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/getddlsubpartrqst.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlPartSub> getDdlPartSubRqstList(WaqMstr search) {
    	//logger.debug("DDL 서브파티션 요청 정보 조회:{}",search);
    	
    	List<WaqDdlPartSub> list = ddlPartRqstService.getDdlPartSubRqstList(search);
    	
    	return new IBSheetListVO<WaqDdlPartSub>(list, list.size());
    }
    

    /** DDL 파티션 등록요청  */
    @RequestMapping("/meta/ddl/regddlpartrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmTblRqstList(@RequestBody WaqDdlParts data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("DDL 파티션 등록요청:{}", reqmst);
		ArrayList<WaqDdlPart> list = data.get("data");

		int result = ddlPartRqstService.register(reqmst, list);

		result += ddlPartRqstService.check(reqmst);
		
		/*
		//DDL이관일경우 인덱스, 파티션, 엔티티기반SEQ, 기타오브젝트 모두 검증
		if(reqmst.getBizDcd().equals("DTT") || reqmst.getBizDcd().equals("DTR")){
			result += ddlTblRqstService.check(reqmst);	//테이블 체크
			result += ddlPartRqstService.check(reqmst); //파티션 체크
			result += ddlIdxRqstService.check(reqmst); // GLOBAL 인덱스는 파티션 에 종속
			result += ddlSeqRqstService.check(reqmst);
			//종속관계없으니 제외
			result += ddlEtcObjRqstService.check(reqmst);
		}else{
			result += ddlPartRqstService.check(reqmst);
		}
		*/

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
    
    @RequestMapping("/meta/ddl/delddlpartrqst.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlPartRqst(@RequestBody WaqDdlParts data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("DDL 파티션 요청 삭제:{}", reqmst);
		ArrayList<WaqDdlPart> list = data.get("data");

		int result = ddlPartRqstService.delDdlPartRqst(reqmst, list);

		result += ddlPartRqstService.check(reqmst);

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

    /** DDL 주파티션 요청 리스트 등록... @throws Exception  */
    @RequestMapping("/meta/ddl/regddlmainpart.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlMainPartRqstList(@RequestBody WaqDdlPartMains data, WaqMstr reqmst,  WaqDdlPart waqpart, Locale locale) throws Exception {

		//logger.debug("DDL 주파티션 요청 리스트 등록:{}\n{}", reqmst, waqpart);
		
//		return null;

		List<WaqDdlPartMain> list = data.get("data");

		int result = ddlPartRqstService.regDdlMainPartList(reqmst, waqpart, list);
		result += ddlPartRqstService.check(reqmst);

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
    /** DDL 서브파티션 요청 리스트 등록... @throws Exception  */
    @RequestMapping("/meta/ddl/regddlsubpart.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regDdlSubPartRqstList(@RequestBody WaqDdlPartSubs data, WaqMstr reqmst,  WaqDdlPart waqpart, Locale locale) throws Exception {
    	
    	//logger.debug("DDL 서브파티션 요청 리스트 등록:{}\n{}", reqmst, waqpart);
    	
//		return null;
    	
    	List<WaqDdlPartSub> list = data.get("data");
    	
    	int result = ddlPartRqstService.regDdlSubPartList(reqmst, waqpart, list);
    	result += ddlPartRqstService.check(reqmst);
    	
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
    
    /** DDL컬럼 DDL파티션컬럼으로 등록  *//*
    @RequestMapping("/meta/ddl/regWamCol2WaqIdxCol.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWamCol2WaqIdxColList(@RequestBody WaqDdlPartCols data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata{}", reqmst, data);

		ArrayList<WaqDdlPartCol> list = data.get("data");

		int result = ddlIdxRqstService.regWamCol2WaqIdxCol(reqmst, list);
		result += ddlIdxRqstService.check(reqmst);

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
    
    *//** DDL파티션 주파티션 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/ddl/delddlmainpart.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlPartColRqstList(@RequestBody WaqDdlPartMains data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("DDL파티션 주파티션 요청 삭제:{}", reqmst);
		ArrayList<WaqDdlPartMain> list = data.get("data");

		int result = ddlPartRqstService.delDdlPartMainRqst(reqmst, list);

		result += ddlPartRqstService.check(reqmst);

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

    /** DDL파티션 서브파티션 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/ddl/delddlsubpart.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlPartSubRqst(@RequestBody WaqDdlPartSubs data, WaqMstr reqmst, Locale locale) throws Exception {
    	
    	//logger.debug("DDL파티션 서브파티션 요청 삭제:{}", reqmst);
    	ArrayList<WaqDdlPartSub> list = data.get("data");
    	
    	int result = ddlPartRqstService.delDdlPartSubRqst(reqmst, list);
    	
    	result += ddlPartRqstService.check(reqmst);
    	
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
    

    /** DDL파티션 승인  */
    @RequestMapping("/meta/ddl/approveddlpart.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlPart(@RequestBody WaqDdlParts data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("DDL파티션 승인:{}", reqmst);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}


		ArrayList<WaqDdlPart> list = data.get("data");

		int result  = ddlPartRqstService.approve(reqmst, list);


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
    
    /** DDL파티션 DDLTRGDCD업데이트 임시  */
    @RequestMapping("/meta/ddl/ddlTrgCdcUpdate.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> ddlTrgCdcUpdateTemp(WaqMstr reqmst, Locale locale) throws Exception {		
    	String resmsg;

		int result  = ddlPartRqstService.ddlTrgCdcUpdateTemp(reqmst);
		if(result > 0) {
			result = 0;
			resmsg = "변경되었습니다.";
		} else {
			result = -1;
			resmsg = "실패하였습니다.";
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
    
    /** DDL파티션 DDLTRGDCD업데이트 임시  */
    @RequestMapping("/meta/ddl/ddlTrgCdcUpdate2.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> ddlTrgCdcUpdateTemp2(WaqMstr reqmst, Locale locale) throws Exception {		
    	String resmsg;

		int result  = ddlPartRqstService.ddlTrgCdcUpdateTemp2(reqmst);
		if(result > 0) {
			result = 0;
			resmsg = "변경되었습니다.";
		} else {
			result = -1;
			resmsg = "실패하였습니다.";
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
    
    /** DDL파티션 DDLTRGDCD업데이트 임시  */
    @RequestMapping("/meta/ddl/ddlTrgCdcUpdate3.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> ddlTrgCdcUpdateTemp3(WaqMstr reqmst, Locale locale) throws Exception {		
    	String resmsg;

		int result  = ddlPartRqstService.ddlTrgCdcUpdateTemp3(reqmst);
		if(result > 0) {
			result = 0;
			resmsg = "변경되었습니다.";
		} else {
			result = -1;
			resmsg = "실패하였습니다.";
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}

    /** DDL파티션컬럼 엑셀업로드 요청 */
/*    @RequestMapping("/meta/ddl/regddlpartcolxlsrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlPartColxlsRqstList(@RequestBody WaqDdlPartCols data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqDdlPartCol> list = data.get("data");

		String resmsg;
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//테이블명도 함께 받는다
		HashMap<String, String> map = ddlIdxRqstService.regDdlPartxlsColList(reqmst, list);
		
//		int result = pdmTblRqstService.regPdmxlsColList(reqmst, list);
		
		int result = Integer.parseInt( map.get("result") );
		String colKey = map.get("colKey");

		//테이블이 존재하지 않음...
		if(result == -999) {
			result = -1;
			resmsg = message.getMessage("ERR.NOTABLE", new String[] {colKey}, locale);

			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}

		result += ddlIdxRqstService.check(reqmst);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}


		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

   }*/
    
    
    /** Range 파티션 생성 팝업 호출.... @return insomnia 
	 * @throws Exception */
	@RequestMapping(value="/meta/ddl/popup/ddlpartaddrange_pop.do")
	public String goDdlPartAddRangePop(@ModelAttribute("search") WaqDdlPart searchvo,  Model model) throws Exception {
		logger.debug("Range 파티션 생성 팝업 :{}", searchvo);
		
		//선택한 컬럼의 길이를 가져옴
		model.addAttribute("colLen",ddlPartRqstService.selectPartColumnLength(searchvo));

		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		/*WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}*/
		
		return "/meta/ddl/popup/ddlpartaddrange_pop";
	}

}
