/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlIdxRqstCtrl.java
 * 2. Package : kr.wise.meta.ddl.web
 * 3. Comment : DDL 인덱스 요청 컨트롤러...
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 6. 14:57:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 6.   :            : 신규 개발.
 */
package kr.wise.meta.ddl.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.cmm.service.FileManagerService;
import kr.wise.commons.cmm.service.FileManagerUtil;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlIdxCol;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlIdxRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL 인덱스 요청 컨트롤러...
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 6. 14:57:00
 * </PRE>
 */
@Controller("DdlIdxRqstCtrl")
public class DdlIdxRqstCtrl {

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
	private DdlIdxRqstService ddlIdxRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private CmvwDbService cmvwDbService;

	@Inject
    private FileManagerUtil fileManagerUtil;

	@Inject
	private FileManagerService  fileMngService;
	
	static class WaqDdlIdxCols extends HashMap<String, ArrayList<WaqDdlIdxCol>> {}

	static class WaqDdlIdxs extends HashMap<String, ArrayList<WaqDdlIdx>> {}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);
		
		//개발 진단대상/스키마 정보(double_select용 목록성코드)
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId"));
		codeMap.put("devConnTrgSch", devConnTrgSch); 

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
		
		//인덱스유형코드
		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		
		//인덱스컬럼정렬순서
		codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));


		//목록성 코드(시스템영역 코드리스트)
		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));


		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

	/** DDL 인덱스 엑셀업로드 팝업 @return yeonho */
    @RequestMapping("/meta/ddl/popup/ddlidx_xls.do")
    public String goDdlIdxXlsPop(@ModelAttribute("search") WaqDdlIdx search, Model model, Locale locale) {
    	logger.debug("search:{}", search);
    	
    	return "/meta/ddl/popup/ddlidx_xls";
    }
    
    /** DDL 인덱스컬럼 엑셀업로드 팝업 @return yeonho */
    @RequestMapping("/meta/ddl/popup/ddlidxcol_xls.do")
    public String goDdlIdxColXlsPop(@ModelAttribute("search") WaqDdlIdxCol search, Model model, Locale locale) {
    	logger.debug("search:{}", search);
    	
    	return "/meta/ddl/popup/ddlidxcol_xls";
    }
    
	
    /** DDL인덱스 요청서 입력 폼.... @throws Exception yeonho */
    @RequestMapping("/meta/ddl/ddlidx_rqst.do")
    public String goDdlIdxRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DDX");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//    	System.out.println("요청번호 : " + reqmst.getRqstNo());
//		System.out.println("요청구분 : " + reqmst.getBizDcds());
//    	System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        int mapcount = approveLineServie.checkRequst(mapvo); 
// 	    System.out.println("카운트 : " + mapcount);
        
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
	model.addAttribute("rqstbizdcd",reqmst.getBizDcd());

    	return "/meta/ddl/ddlidx_rqst";

    }

    /** DDL 인덱스 요청 리스트 조회 @return yeonho */
    @RequestMapping("/meta/ddl/getddlidxrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlIdx> getDdlIdxRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqDdlIdx> list = ddlIdxRqstService.getDdlIdxRqstList(search);


		return new IBSheetListVO<WaqDdlIdx>(list, list.size());
	}

    /** DDL 인덱스 요청 상세 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/ajaxgrid/ddlidx_rqst_dtl.do")
    public String getDdlIdxDetail(WaqDdlIdx searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
//    		
//    		//ibk신용정보 db가 개발은 1개이므로 db정보 default로 세팅
//    		WaaDbSch search = new WaaDbSch();
//    		search.setTblSpacTypCd("I");
//    		search.setDdlTrgDcd("D");
//            List<WaaDbSch> resultVo = cmvwDbService.getDbSchemaList(search);
//            WaqDdlIdx result = new WaqDdlIdx();
//            result.setDbConnTrgId(resultVo.get(0).getDbConnTrgId());
//            result.setDbConnTrgPnm(resultVo.get(0).getDbConnTrgPnm());
//            result.setDbSchId(resultVo.get(0).getDbSchId());
//            result.setDbSchPnm(resultVo.get(0).getDbSchPnm());
//            result.setIdxSpacId(resultVo.get(0).getDbTblSpacId());
//            result.setIdxSpacPnm(resultVo.get(0).getDbTblSpacPnm());
//            
//            model.addAttribute("result", result);
//            
    	} else {
    		WaqDdlIdx resultvo =  ddlIdxRqstService.getDdlIdxRqstDetail(searchVo);
    		
    		if(searchVo.getRvwStsCd() != null && !searchVo.getRvwStsCd().equals(""))
    			resultvo.setRvwStsCd(searchVo.getRvwStsCd());
    		if(searchVo.getRvwConts() != null && !searchVo.getRvwConts().equals(""))
    			resultvo.setRvwConts(searchVo.getRvwConts());
    		
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/ddl/ddlidx_rqst_dtl";

    }
    
    /** DDL 인덱스 컬럼 요청 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/getddlidxcolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlIdxCol> getDdlIdxColRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WaqDdlIdxCol> list = ddlIdxRqstService.getDdlIdxColRqstList(search);

		return new IBSheetListVO<WaqDdlIdxCol>(list, list.size());
	}
    
    /** DDL 인덱스 컬럼 요청 상세 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/ajaxgrid/ddlidxcol_rqst_dtl.do")
    public String getDdlIdxColRqstDetail(WaqDdlIdxCol search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDdlIdxCol resultvo =  ddlIdxRqstService.getDdlIdxColRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}


    	return "/meta/ddl/ddlidxcol_rqst_dtl";
    }
     



    /** 인덱스 변경대상 추가 @throws Exception yeonho */
    @RequestMapping("/meta/ddl/regWam2WaqDdlIdx.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqDdlIdxs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqDdlIdx> list = data.get("data");

		int result = ddlIdxRqstService.regWam2Waq(reqmst, list);

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

    @RequestMapping("/meta/ddl/delddlidxrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlIdxRqstList(@RequestBody WaqDdlIdxs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlIdx> list = data.get("data");

		int result = ddlIdxRqstService.delDdlIdxRqst(reqmst, list);

		result += ddlIdxRqstService.check(reqmst);

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

    /** DDL 인덱스 등록요청 yeonho */
    @RequestMapping("/meta/ddl/regddlidxrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlIdxRqstList(@RequestBody WaqDdlIdxs data, WaqMstr reqmst, Locale locale
			/*, MultipartHttpServletRequest multiRequest*/) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlIdx> list = data.get("data");

	    List<FileVO> fileTmp = null;
	    String atchFileId = "";
//		Map<String, MultipartFile> files = multiRequest.getFileMap();
//	    if (!files.isEmpty()) {
//			fileTmp = fileManagerUtil.parseFileInf(files, "BBS_", 0, "", "");
//			atchFileId = fileMngService.insertFileInfs(fileTmp);
//			logger.debug("fileid[{}]", atchFileId);
//			list.get(0).setAtchFileId("atchFileId");
//	    }
//	    logger.debug("\n\n\natchFileId : {}\n\n\n",atchFileId);
		int result = ddlIdxRqstService.register(reqmst, list);

		result += ddlIdxRqstService.check(reqmst);

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

    /** DDL인덱스 컬럼 요청 리스트 등록... @throws Exception yeonho */
    @RequestMapping("/meta/ddl/regddlidxcolrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlIdxColRqstList(@RequestBody WaqDdlIdxCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqDdlIdxCol> list = data.get("data");

		int result = ddlIdxRqstService.regDdlIdxColList(reqmst, list);
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
    
    /** DDL인덱스 컬럼 요청 리스트 삭제... @throws Exception insomnia */
    @RequestMapping("/meta/ddl/delddlidxcolrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlIdxColRqstList(@RequestBody WaqDdlIdxCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDdlIdxCol> list = data.get("data");

		int result = ddlIdxRqstService.delDdlIdxColRqst(reqmst, list);

		result += ddlIdxRqstService.check(reqmst);

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
    

    /** DDL인덱스 승인 yeonho */
    @RequestMapping("/meta/ddl/approveddlidx.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlIdx(@RequestBody WaqDdlIdxs data, WaqMstr reqmst, Locale locale) throws Exception {

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


		ArrayList<WaqDdlIdx> list = data.get("data");

		int result  = ddlIdxRqstService.approve(reqmst, list);


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

    /** DDL인덱스컬럼 엑셀업로드 요청 */
    @RequestMapping("/meta/ddl/regddlidxcolxlsrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regDdlIdxColxlsRqstList(@RequestBody WaqDdlIdxCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		ArrayList<WaqDdlIdxCol> list = data.get("data");
	    
		
		//int result = ddlIdxRqstService.register(reqmst, list);
		//테이블명도 함께 받는다
		HashMap<String, String> map = ddlIdxRqstService.regDdlIdxxlsColList(reqmst, list);
		int result = Integer.parseInt(map.get("result"));
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
    
    /** DDL 인덱스컬럼조회 팝업 @return yeonho */
    @RequestMapping("/meta/ddl/popup/ddlidxcol_pop.do")
    public String goDdlIdxColPop(@ModelAttribute("search") WamDdlIdx search, Model model, Locale locale) {
    	logger.debug("search:{}", search); 
    	 
    	return "/meta/ddl/popup/ddlidxcol_pop";
    } 

    
	/** 이관요청대상 DDL인덱스 목록 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/ddl/getDdlTsfIdxListForRqst.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlIdx> selectDdlTsfIdxListForRqst(WaqDdlIdx search) throws Exception {
		logger.debug("{}", search);
		List<WaqDdlIdx> list = ddlIdxRqstService.selectDdlTsfIdxListForRqst(search);
		return new IBSheetListVO<WaqDdlIdx>(list, list.size());    
		
	}

    /** DDL to DDL이관 @throws Exception */
    @RequestMapping("/meta/ddltsf/regWam2WaqDdlTsfIdx.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2WaqDdlTsfIdx(@RequestBody WaqDdlIdxs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);	

		ArrayList<WaqDdlIdx> list = data.get("data");
		
		int result = ddlIdxRqstService.regTsfWam2Waq(reqmst, list); 
		
		
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
}
