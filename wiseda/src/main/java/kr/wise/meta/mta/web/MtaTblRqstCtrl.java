/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaTblRqstCtrl.java
 * 2. Package : kr.wise.meta.mta.web
 * 3. Comment : 메타데이터 등록요청 컨트롤러
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12. 오후 4:31:42
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.web;

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
//import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.net.CommNetApiUtil;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.esb.EsbConfig;
import kr.wise.esb.send.service.EsbFilesendVO;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.mta.service.BrmInfoVo;
import kr.wise.meta.mta.service.MtaTblRqstService;
import kr.wise.meta.mta.service.WamMtaCol;
import kr.wise.meta.mta.service.WamMtaTbl;
import kr.wise.meta.mta.service.WaqMtaCol;
import kr.wise.meta.mta.service.WaqMtaTbl;
import kr.wise.meta.mta.service.WaqMtaTblMapper;
import kr.wise.meta.stnd.service.StndSditmService;
import kr.wise.meta.stnd.service.WaaBrmDetail;
import kr.wise.meta.stnd.service.WamSditm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller("MtaTblRqstCtrl")
public class MtaTblRqstCtrl  {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private MessageSource message;

    //@Inject
	//private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private MtaTblRqstService mtaTblRqstService;
	
	@Inject
	private ApproveLineServie approveLineServie;
	
/*	@Inject
	private EsbFilesendService esbFilesendService;*/
	
	@Inject 
	private WaqMtaTblMapper waqMtaTblMapper; 
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
	private StndSditmService stndSditmService;

	private Map<String, Object> codeMap;

	static class WaqMtaTbls extends HashMap<String, ArrayList<WaqMtaTbl>> {}
	static class WaqMtaCols extends HashMap<String, ArrayList<WaqMtaCol>> {}
	static class WamMtaTbls extends HashMap<String, ArrayList<WamMtaTbl>> {}
	static class WamMtaCols extends HashMap<String, ArrayList<WamMtaCol>> {}

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

		//비공개사유 
		codeMap.put("nopenRsnibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NOPEN_RSN_CD")));
		
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		//DB스키마명(IBSheet용)
		codeMap.put("connTrgSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("connTrgSchId")));
		
		//기관코드 (IBSheet용)
        codeMap.put("orgCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("orgCd")));
        
        //codeMap.put("infoSysCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("infoSysCd")));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		//목록성 코드(시스템영역 코드리스트)
		
		codeMap.put("sysarea", UtilJson.convertJsonString(codeListService.getCodeList("sysarea")));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea")));

		//==== 기관별 코드 리스트 Start===== 
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String ssOrgCd = UtilString.null2Blank(user.getOrgCd());
		
		//List<CodeListVo> orgList = codeListService.getOrgCdEachList(ssOrgCd); 
		
		List<CodeListVo> orgList = codeListService.getAuthOrgCdList(user);  
		codeMap.put("orgCd", orgList);
		
		//List<CodeListVo> infoSysList = codeListService.getInfoSysCdEachList(ssOrgCd); 
		//codeMap.put("infoSysCd", infoSysList);
		//==== 기관별 코드 리스트 End=====
		
		//GAP상태코드
		codeMap.put("gapStsCd", cmcdCodeService.getCodeList("GAP_STS_CD"));
		codeMap.put("gapStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("GAP_STS_CD")));
		
		//테이블유형코드
		codeMap.put("tblTypCd", cmcdCodeService.getCodeList("TBL_TYP_CD"));
		codeMap.put("tblTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_TYP_CD")));
		
		//테이블수집구분코드 
		codeMap.put("tblClltDcd", cmcdCodeService.getCodeList("TBL_CLLT_DCD"));
		codeMap.put("tblClltDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_CLLT_DCD")));

		//보존기간 
		codeMap.put("prsvTerm", cmcdCodeService.getCodeList("PRSV_TERM"));
		codeMap.put("prsvTermibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRSV_TERM")));
		
		//보존기간 
		codeMap.put("occrCyl", cmcdCodeService.getCodeList("OCCR_CYL"));
		codeMap.put("occrCylibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OCCR_CYL")));

		//공개사유 
		codeMap.put("openRsnCd", cmcdCodeService.getCodeList("OPEN_RSN_CD"));
		codeMap.put("openRsnCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OPEN_RSN_CD")));

		//비공개사유
		codeMap.put("nopenRsnCd", cmcdCodeService.getCodeList("NOPEN_RSN_CD"));
/*		List<CodeListVo> brmCd = codeListService.getCodeList("brmCd");
		//UtilJson.convertJsonString(codeListService.getCodeList("brmCd"))
		codeMap.put("brmCd", brmCd);*/
		//List<CodeListVo> infoSysList = codeListService.getInfoSysCdEachList(ssOrgCd); 
		
		return codeMap;
	}

	/** 메타데이터 테이블 요청서 입력 화면 이동 */
	@RequestMapping("/meta/mta/mtatbl_rqst.do")
	public String goMtaTblRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);
    	
    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())) {
    		reqmst.setBizDcd("MTA");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
        System.out.println("요청번호 : " + reqmst.getRqstNo());
        System.out.println("요청구분 : " + reqmst.getBizDcd());
        System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
        
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        
        int mapcount = approveLineServie.checkRequst(mapvo); 
        System.out.println("카운트 : " + mapcount);
        
       
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
        model.addAttribute("rqstbizdcd",reqmst.getBizDcd()); 
        
        //============임시저장건수 체크==================
        reqmst.setWritUserId(userid); 
        
        //임시저장건수 체크
        HashMap<String,String> tempmap = mtaTblRqstService.getTempRqstCnt(reqmst);
        
        model.addAttribute("tempRqstNo", tempmap);
        //===========================================
        
        return "/meta/mta/mtatbl_rqst";
    }
   

	/** 메타데이터 테이블 요청 상세 정보 조회 */
	@RequestMapping("/meta/mta/ajaxgrid/mtatbl_rqst_dtl.do")
	public String getmtatblDetail(WaqMtaTbl searchVo, ModelMap model) {
		
		logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	}
    	else {
    		//WaqMtaTbl resultvo =  mtaTblRqstService.getMtaTblRqstDetail(searchVo);
    		WaqMtaTbl resultvo = mtaTblRqstService.getMtaTblRqstDetail(searchVo);
    		logger.debug("결과값 : " +resultvo.getRvwStsCd() );
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}
		
		return "/meta/mta/mtatbl_rqst_dtl";
	}  
    
    /** 메타데이터 테이블 요청 리스트 조회 */
    @RequestMapping("/meta/mta/getmtatblrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqMtaTbl> getMtaTblRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WaqMtaTbl> list = mtaTblRqstService.getMtaTblrqstList(search);


		return new IBSheetListVO<WaqMtaTbl>(list, list.size());
	}

    /** 메타데이터 테이블 요청 리스트 등록 */
    @RequestMapping("/meta/mta/regmtatblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMtaTblRqstList(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqMtaTbl> list = data.get("data");
		
		String subjClDcd = message.getMessage("subjClDcd", null, locale);		
		
		reqmst.setSubjClDcd(subjClDcd);
		
		int result = mtaTblRqstService.register(reqmst, list);

		result += mtaTblRqstService.check(reqmst);

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
    
    
    /** 메타데이터 테이블 요청 리스트 등록 */
    @RequestMapping("/meta/mta/regMtaTblColRqstList.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMtaTblColRqstList( @RequestBody HashMap<String,Object> data, WaqMstr reqmst, Locale locale) throws Exception {
 
		logger.debug("reqmst:{}\ndata:{}", reqmst, data); 
		
		List<WaqMtaTbl> listTbl =  (List<WaqMtaTbl> )data.get("dataTbl");
		
		List<WaqMtaCol> listCol = (List<WaqMtaCol>)data.get("dataCol");
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		listTbl = mapper.convertValue(listTbl,  new TypeReference<List<WaqMtaTbl>>(){} ); 
		
		listCol = mapper.convertValue(listCol,  new TypeReference<List<WaqMtaCol>>(){} );
		
		logger.debug("\n lstWord size:" + listTbl.size());
		logger.debug("\n lstDmn size:"  + listCol.size()); 
		
		
		int result = 0;
		
		result += mtaTblRqstService.register(reqmst, listTbl); 
		
		result += mtaTblRqstService.regMtaColList(reqmst, listCol);
		
		reqmst.setBizDcd("MTA");
		reqmst.setBizDtlCd("TBL");
		reqmst.getBizInfo().setBizDtlCd("TBL");
		
		//reqmst = requestMstService.getBizInfoInit(reqmst);

		result += mtaTblRqstService.check(reqmst);

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
		
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}


    /** 메타데이터 테이블 요청 리스트 삭제 */
    @RequestMapping("/meta/mta/delmtatblrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delMtaTblRqstList(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqMtaTbl> list = data.get("data");

		int result = mtaTblRqstService.delMtaTblRqst(reqmst, list);

		//result += mtaTblRqstService.check(reqmst);				

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
		
		int iRtn = waqMtaTblMapper.deleteNotExistsWaqMstr(reqmst.getRqstNo());  
		
		logger.debug("\n iRtn:" + iRtn);	
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

    /** 메타데이터 컬럼 상세조회 */
    @RequestMapping("/meta/mta/ajaxgrid/mtacol_rqst_dtl.do")
    public String getMtaColRqstDetail(WaqMtaCol search, ModelMap model) {
    	logger.debug("searchVO:{}", search);

    	if(search.getRqstDtlSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqMtaCol resultvo =  mtaTblRqstService.getMtaColRqstDetail(search);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/mta/mtacol_rqst_dtl";
    }


    /** 메타데이터 컬럼 요청 리스트 등록 */
    @RequestMapping("/meta/mta/regmtacolrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMtaColRqstList(@RequestBody WaqMtaCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqMtaCol> list = data.get("data");
		System.out.println("세이브브이오리스트 : " + list.toString());
		int result = mtaTblRqstService.regMtaColList(reqmst, list);
		
		result += mtaTblRqstService.check(reqmst);

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
    
    /** 메타데이터 컬럼 요청 리스트 등록 */
    @RequestMapping("/meta/mta/updatePrsnInfo.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> updatePrsnInfo(@RequestBody WaqMtaCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WaqMtaCol> list = data.get("data");
		
		int result = mtaTblRqstService.updatePrsnColList(reqmst, list);
	
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
    

    /** 메타데이터 컬럼 요청 리스트 조회 */
    @RequestMapping("/meta/mta/getmtacolrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqMtaCol> getMtaColRqstList(WaqMstr search, WaqMtaCol waqMtaCol) {
		logger.debug("search:{}",search);
		logger.debug("waqMtaCol:{}",waqMtaCol);
 
		List<WaqMtaCol> list = mtaTblRqstService.getMtaColRqstList(search);

		return new IBSheetListVO<WaqMtaCol>(list, list.size());
	}

    /** 메타데이터 컬럼 요청 리스트 삭제 */
    @RequestMapping("/meta/mta/delmtacolrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delMtaColRqstList(@RequestBody WaqMtaCols data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqMtaCol> list = data.get("data");

		int result = mtaTblRqstService.delMtaColRqst(reqmst, list);

		result += mtaTblRqstService.check(reqmst);

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
    
    
    /** 메타데이터 테이블 승인
     * ESB 요청 파일 생성
     *  */
    /*@RequestMapping("/meta/mta/approvemtatbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveMtaTbl(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqMtaTbl> list = data.get("data");

		int result  = mtaTblRqstService.approve(reqmst, list);
        
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
			
			try {
				
				logger.debug("== ESP FILE SEND Start... ==");
				
				EsbFilesendVO fileVO = new EsbFilesendVO();
				
				fileVO.setFileGb(EsbConfig.MTA);
				
				String sysCd = message.getMessage("sysCd", null, Locale.getDefault());
				
				fileVO.setSrcSysCd(sysCd); 
				
				fileVO.setSrcOrgCd(reqmst.getOrgCd());
				fileVO.setMtaRqstNo(reqmst.getRqstNo());
				
				mtaTblRqstService.sendEsb(fileVO);
				
				logger.debug("== ESP FILE SEND End... ==");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}*/
    
    
    /** 메타데이터 테이블 결재 없이 바로 승인 
     * ESB 요청 파일 생성
     *  */
    @RequestMapping("/meta/mta/nonApproveMtaTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> nonApproveMtaTbl(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//기관코드세팅 
		reqmst.setOrgCd(user.getTopOrgCd()); 

		//결재자인지 확인한다.
		
		/*Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}*/
		

		ArrayList<WaqMtaTbl> list = data.get("data");

		int result  = mtaTblRqstService.approve(reqmst, list);
        
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
						
			logger.debug("== ESP FILE SEND Start... ==");
			
			EsbFilesendVO fileVO = new EsbFilesendVO();
			
			fileVO.setFileGb(EsbConfig.MTA);
			
			/*String sysCd = message.getMessage("sysCd", null, Locale.getDefault());
			
			fileVO.setSrcSysCd(sysCd); */
			
			fileVO.setSrcOrgCd(reqmst.getOrgCd());
			fileVO.setMtaRqstNo(reqmst.getRqstNo());
			
			mtaTblRqstService.sendEsb(fileVO);
			
			logger.debug("== ESP FILE SEND End... ==");
			
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
    
    /*@RequestMapping("/meta/mta/autoMtaTbl.do")	//minoce
	@ResponseBody 
	public Map<String, String> autoMtaTbl(@ModelAttribute WaqMtaCol search, Model model) throws IOException {
	
    	logger.debug("== ESP FILE SEND Start... ==");
    	List<WaqMtaTbl>mList = mtaTblRqstService.getMatTblAuto(null);
    	for(WaqMtaTbl tempList : mList)
    	{
			EsbFilesendVO fileVO = new EsbFilesendVO();
			
			fileVO.setFileGb(EsbConfig.MTA);
			
			String sysCd = message.getMessage("sysCd", null, Locale.getDefault());
			
			fileVO.setSrcSysCd(sysCd); 
			double randomValue = Math.random();
			int intValue = (int)(randomValue*100000);
			String result = String.format("%07d", intValue);
			fileVO.setSrcOrgCd(result);
			fileVO.setMtaTblId(tempList.getMtaTblId());//여기에 필요한 id를 넣는다.
			
			mtaTblRqstService.sendEsbAuto(fileVO);
    	}
		logger.debug("== ESP FILE SEND End... ==");
	
		Map<String, String> result = new HashMap<String, String>();
		result.put("chk", "chk");
		
		return result; 
	}*/
    
    /** 팝업) 메타데이터 비공개사유코드찾기팝업 화면 호출 */
   	@RequestMapping("/meta/mta/auto/goAutoMeta.do")
   	public String goAutoMeta(@ModelAttribute("search") WaqMtaTbl search, String sFlag, Model model, Locale locale) {
   		logger.debug("nopenRsnSearchPop {}", search);
   		logger.debug("nopenRsnSearchPop sFlag{}", sFlag);
   		
   		//model.addAttribute("sFlag", sFlag);

   		return "/meta/admin/auto_meta";
   	}
   	
   	
    /** 팝업) 메타데이터 비공개사유코드찾기팝업 화면 호출 */
	@RequestMapping("/meta/mta/popup/nopenRsnSearchPop.do")
	public String goNopenRsnSearchPop(@ModelAttribute("search") WaqMtaTbl search, String sFlag, Model model, Locale locale) {
		logger.debug("nopenRsnSearchPop {}", search);
		logger.debug("nopenRsnSearchPop sFlag{}", sFlag);
		
		model.addAttribute("sFlag", sFlag);

		return "/meta/mta/popup/nopenRsnSearch_pop";
	}
	
	/** 팝업) 메타데이터 비공개사유코드찾기팝업 리스트 조회 */
	@RequestMapping("/meta/mta/popup/getNopenRsnList.do")
	@ResponseBody
	public IBSheetListVO<CodeListVo> getNopenRsnList(@ModelAttribute WaqMtaTbl data, Locale locale) {

		logger.debug("data:{}", data);
		//기존 공통코드 사용 조회
		//List<WaqMtaTbl> list = mtaTblRqstService.getNopenRsnList(data);
		//logger.debug("list:{}", list); 
		List<CodeListVo> list = cmcdCodeService.getCodeList("NOPEN_RSN_CD");
		
		return new IBSheetListVO<CodeListVo>(list, list.size());

	}
    
    
    /** 팝업) 메타데이터 테이블 변경 화면 호출 */
	@RequestMapping("/meta/mta/popup/mtatblSearchPop.do")
	public String goMtaTblPop(@ModelAttribute("search") WaqMtaTbl search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mta/popup/mtaTblSearch_pop";
	}
	
	/** 팝업) 메타데이터 리스트 조회 */
	@RequestMapping("/meta/mta/popup/getMtaTblList.do")
	@ResponseBody
	public IBSheetListVO<WaqMtaTbl> getMtaTblList(@ModelAttribute WaqMtaTbl data, Locale locale) {

		logger.info("==MtaTblRqstCtrl.getMtaTblList started.... data:{}", data);

		//DBC테이블
		//List<WaqMtaTbl> list = mtaTblRqstService.getDbcTblList(data);
		List<WaqMtaTbl> list = mtaTblRqstService.getMtaTblList(data);
		//logger.debug("list:{}", list);
		
		return new IBSheetListVO<WaqMtaTbl>(list, list.size());

	}
	
	/** 팝업) 메타데이터 테이블 변경대상 요청 리스트 추가(WAM --> WAQ) */
    @RequestMapping("/meta/mta/regWam2WaqMtaTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.info("==MtaTblRqstCtrl.regWam2Waqlist reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqMtaTbl> list = data.get("data");

		int result = mtaTblRqstService.regWam2Waq(reqmst, list);

		result += mtaTblRqstService.check(reqmst);

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
    
    
    /** 팝업) 메타데이터 테이블 변경 화면 호출 */
	@RequestMapping("/meta/mta/popup/mtaWatTblSearch_pop.do")
	public String goMtaWatTblPop(@ModelAttribute("search") WaqMtaTbl search, Model model, Locale locale) {
		logger.debug("{}", search);
		
		return "/meta/mta/popup/mtaWatTblSearch_pop"; 
	}
	
	/** 팝업) 메타데이터 테이블 변경대상 요청 리스트 추가(WAM --> WAQ) */
    @RequestMapping("/meta/mta/regWat2WaqMtaTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regWa2Waqlist(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {
		logger.info("==MtaTblRqstCtrl.regWam2Waqlist reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqMtaTbl> list = data.get("data");

		int result = mtaTblRqstService.regWat2Waq(reqmst, list);

		result += mtaTblRqstService.check(reqmst);

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
    
/*    public IBSheetListVO<CodeListVo> responseMetaBrmList() throws Exception {
	    	List<CodeListVo> list = codeListService.getCodeList("brmCd");
//	    	ArrayList<BrmInfoVo> list = stndWordRqstService.getMetaBrmList(paramMap);
	    	
			return new IBSheetListVO<CodeListVo>(list, list.size());
		}*/
    
    
    
    /** 팝업) BRM정보 검색 팝업 화면 호출 
     * @throws Exception */
	@RequestMapping("/meta/mta/popup/brmSearchpop.do")
	public String goBrmSearchPop(@RequestParam HashMap<String, Object> search, String sFlag, Model model, Locale locale) throws Exception {
		logger.debug("brmSearchpop {}", search);
		logger.debug("brmSearchpop sFlag{}", sFlag);
		
		model.addAttribute("sFlag", sFlag);
		model.addAttribute("search", search);
		
		//===============brm 조회===================
		/*String ctrMetaUrl = message.getMessage("ctrMetaUrl", null, null);
		
		Map<String,Object> brmMap = CommNetApiUtil.callJsonStringApi(ctrMetaUrl+"/response/metabrmList.do", HttpMethod.POST, null);
				
    	model.addAttribute("brmCd", brmMap.get("DATA"));*/
    	//===========================================
		Map<String,Object> brmMap = new HashMap<String,Object>();

/**/
		
		return "/meta/mta/popup/brmSearch_pop";
	}
	
	@RequestMapping("/meta/mta/popup/getBrmInfoVoList.do")
	@ResponseBody
	public IBSheetListVO<BrmInfoVo> getBrmInfoVolist(@ModelAttribute BrmInfoVo data, Locale locale ) {
		
		List<BrmInfoVo> list = mtaTblRqstService.getBrmInfoVoList(); 
		
		return new IBSheetListVO<BrmInfoVo>(list, list.size());
	}
	
	
    /** 팝업) 추천용어 검색 팝업 화면 호출 */
	@RequestMapping("/meta/mta/popup/rcmdTermSearchpop.do")
	public String goRcmdTermSearchPop(@RequestParam HashMap<String, Object> search, String sFlag, Model model, Locale locale) {
				
		logger.debug("\n search:" + search.get("keyword") ); 
		logger.debug("\n popType:" + search.get("popType"));
		
		logger.debug("\n rcmdTermSearch_pop sFlag{}", sFlag); 
		
		model.addAttribute("sFlag", sFlag);
		model.addAttribute("search", search ); 
		
		return "/meta/mta/popup/rcmdTermSearch_pop";
	}
	
	/** 추천용어 검색 */
	@RequestMapping("/meta/mta/popup/getRcmdsditmlist.do")
	@ResponseBody
	public IBSheetListVO<WamSditm> getStndItemList(@ModelAttribute WamSditm data, Locale locale) {

		logger.debug("req vo:{}", data);

//		List<WamSditm> list = stndSditmService.getRcmdStndItemList(data);
		
		List<WamSditm> list = null;

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamSditm>(list, list.size());

	}
	
	@RequestMapping("/meta/mta/ajax/getOrgInfoSys.do")
	@ResponseBody
	public List<HashMap> getOrgInfoSys(@RequestBody HashMap<String,String> data ) {
		logger.debug("data.toString() >>>> " + data.toString());

		logger.debug("\n orgCd:" + (String)data.get("orgCd"));
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		data.put("userId", user.getUniqId()); 
		//data.put("isSysAdminYn", user.getIsSysAdminYn());
		//data.put("isAdminYn", user.getIsAdminYn());
	
		List<HashMap> list = mtaTblRqstService.getOrgInfoSys(data); 
		
		return list;

	}
	
	@RequestMapping("/meta/mta/ajax/getInfoSysDbConnTrg.do")
	@ResponseBody
	public List<HashMap> getInfoSysDbConnTrg(@RequestBody HashMap<String,String> data ) {

		logger.debug("\n orgCd:" + (String)data.get("orgCd"));
		logger.debug("\n infoSysCd:" + (String)data.get("infoSysCd"));
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		//data.put("isSysAdminYn", user.getIsSysAdminYn()); 
		//data.put("isAdminYn", user.getIsAdminYn());
	
		List<HashMap> list = mtaTblRqstService.getInfoSysDbConnTrg(data); 
		
		return list;
	}
	
	@RequestMapping("/meta/mta/ajax/getInfoSysDbSch.do")
	@ResponseBody
	public List<HashMap> getInfoSysDbSch(@RequestBody HashMap<String,String> data ) {

		logger.debug("\n dbConnTrgId:" + (String)data.get("dbConnTrgId"));
	
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		//data.put("isSysAdminYn", user.getIsSysAdminYn());
		//data.put("isAdminYn", user.getIsAdminYn());
	
		List<HashMap> list = mtaTblRqstService.getInfoSysDbSch(data); 
		
		return list;

	}
	
	/** 팝업) 컬럼등록 팝업 */
	@RequestMapping("/meta/mta/popup/mtaColRqstPop.do")
	public String goMtaColRqstPop(@ModelAttribute WaqMtaCol search, Model model, Locale locale) {
		logger.debug(" {}", search);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		model.addAttribute("search", search); 
		
		//WaqMtaCol resultvo =  mtaTblRqstService.getMtaColRqstDetail(search);
	
		WaqMtaCol resultvo =  mtaTblRqstService.getPopMtaColRqstDetail(search); 
					
		logger.debug("\n tblRow:" +search.getTblRow());
		
		resultvo.setTblRow(search.getTblRow());
		 
		model.addAttribute("result", resultvo);
		model.addAttribute("saction", "U");
		
		return "/meta/mta/popup/mtaColRqst_pop";
	}
	/** 팝업) 컬럼상세조희 팝업 */
	@RequestMapping("/meta/mta/popup/mtaColViewPop.do")
	public String goMtaColViewPop(@ModelAttribute WaqMtaCol search, Model model, Locale locale) {
		logger.debug(" {}", search);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		model.addAttribute("search", search); 
		
		//WaqMtaCol resultvo =  mtaTblRqstService.getMtaColRqstDetail(search);
	
		WaqMtaCol resultvo =  mtaTblRqstService.getPopMtaColRqstDetail(search); 
					
		logger.debug("\n tblRow:" +search.getTblRow());
		
		resultvo.setTblRow(search.getTblRow());
		 
		model.addAttribute("result", resultvo);
		model.addAttribute("saction", "U");
		
		return "/meta/mta/popup/mtaColView_pop";
	}
	
	/** 팝업) 테이블 컬럼등록 팝업 
	 * @throws Exception */
	@RequestMapping("/meta/mta/popup/mtaTblColRqst_pop.do")
	public String goMtaTblColRqstPop(@ModelAttribute WaqMtaTbl search, WaqMstr reqmst, Model model, Locale locale) throws Exception {
		logger.debug(" {}", search);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		model.addAttribute("search", search); 
				
		search.setWritUserId(user.getUniqId()); 	
		
		logger.debug("\n rqstDcd:" + search.getGapStsCd()); 
		logger.debug("\n rqstDcd:" + search.getRqstDcd()); 
		
		String gapStsCd = UtilString.null2Blank(search.getGapStsCd());
		
		WaqMtaTbl resultTbl = null;
		
		
		//신규/변경/전송완료 
		if(!gapStsCd.equals("D")) {
			
			//임시저장상태 체크  
			ArrayList<WaqMtaTbl> chklist = mtaTblRqstService.getMtaTblByDbSchId(search);
						
			//ArrayList<WaqMtaTbl> chklist = new ArrayList<WaqMtaTbl>(); 
			
			if(chklist.size() == 0) {
												
				String rqstNo = requestIdGnrService.getNextStringId(); 
				
				reqmst.setRqstNo(rqstNo);
				reqmst.setRqstStepCd("N");
				reqmst.setWritUserId(user.getUniqId()); 
				
				ArrayList<WaqMtaTbl> list = new ArrayList<WaqMtaTbl>();
				
				list.add(search);
				
				List<WaqMtaTbl> wamlist = waqMtaTblMapper.selectwatlist(reqmst, list);    	
							
				mtaTblRqstService.registerWat(reqmst, wamlist); 	
				
				search.setRqstNo(reqmst.getRqstNo());
				search.setRqstSno(1);
				
			}else{
														
				for(int i = 0 ; i < chklist.size() ; i++){
					
					search.setRqstNo(chklist.get(i).getRqstNo());
					search.setRqstSno(chklist.get(i).getRqstSno());
					
					reqmst.setRqstNo(chklist.get(i).getRqstNo());
					reqmst.setRqstSno(chklist.get(i).getRqstSno());
					
					//임시저장 변경 체크
					ArrayList<WaqMtaTbl> chkUpd = mtaTblRqstService.getUpdWatMtaCheck(search);
					
					//변경내역 있으면 
					if(chkUpd.size() > 0){
						
						//신규생성
						String rqstNo = requestIdGnrService.getNextStringId(); 
						
						reqmst.setRqstNo(rqstNo);
						reqmst.setRqstStepCd("N");
						reqmst.setWritUserId(user.getUniqId()); 
						
						ArrayList<WaqMtaTbl> list = new ArrayList<WaqMtaTbl>();
						
						list.add(search);
						
						List<WaqMtaTbl> wamlist = waqMtaTblMapper.selectwatlist(reqmst, list);    	
									
						mtaTblRqstService.registerWat(reqmst, wamlist); 	
						
						search.setRqstNo(reqmst.getRqstNo());
						search.setRqstSno(1);
					}
					
					
					break;
				} 											
			}
		}else{
			//삭제 
			String rqstNo = requestIdGnrService.getNextStringId(); 
			
			reqmst.setRqstNo(rqstNo);
			reqmst.setRqstStepCd("N");
			reqmst.setWritUserId(user.getUniqId()); 			
			
			ArrayList<WaqMtaTbl> list = new ArrayList<WaqMtaTbl>();
			
			list.add(search); 			
			
			List<WaqMtaTbl> wamlist = waqMtaTblMapper.selectwamlist(reqmst, list);    	
			
			mtaTblRqstService.registerWat(reqmst, wamlist); 	
			
			search.setRqstNo(reqmst.getRqstNo());
			search.setRqstSno(1);
		}
								
		reqmst.setBizDcd("MTA");
		reqmst.setRqstStepCd("S");  
		
		//검증
		mtaTblRqstService.check(reqmst);
		
		resultTbl = mtaTblRqstService.getMtaTblRqstDetail(search);
				
		model.addAttribute("result", resultTbl); 
		model.addAttribute("saction", "U"); 
		
		
		return "/meta/mta/popup/mtaTblColRqst_pop";
	}
	
	@RequestMapping("/meta/mta/popup/ajax/mtatbl_rqst_dtl_pop.do") 	
	public String getMtaTblRqstDetail(@ModelAttribute WaqMtaTbl search, Model model) {
	
		WaqMtaTbl resultTbl = mtaTblRqstService.getMtaTblRqstDetail(search);
		
		model.addAttribute("result", resultTbl);
		
		return "/meta/mta/popup/mtatbl_rqst_dtl_pop"; 
	}
	
	@RequestMapping("/meta/mta/popup/getMtaTblInfoDetail.do") 	
	@ResponseBody 
	public WaqMtaTbl getMtaTblInfoDetail(@ModelAttribute WaqMtaTbl search, Model model) {
	
		WaqMtaTbl resultTbl = mtaTblRqstService.getMtaTblRqstDetail(search); 
	
		return resultTbl; 
	}

	@RequestMapping("/meta/mta/popup/checkMtaColNm.do") 	
	@ResponseBody 
	public Map<String, String> checkMtaColNm(@ModelAttribute WaqMtaCol search, Model model) {
	
		String chk = mtaTblRqstService.checkMtaColNm(search); 
	
		Map<String, String> result = new HashMap<String, String>();
		result.put("chk", chk);
		
		return result; 
	}
	
	@RequestMapping("/meta/mta/popup/mta_tbl_help.do") 	
	public String getMtaTblHelp(@ModelAttribute WaqMtaTbl search, String tabIndex, Model model) {
			
		model.addAttribute("tabIndex", tabIndex);
		return "/meta/mta/popup/mta_tbl_help"; 
	}
	
	@RequestMapping("/meta/mta/popup/mta_col_help.do") 	
	public String getMtaColHelp(@ModelAttribute WaqMtaTbl search, Model model) {
			
		
		return "/meta/mta/popup/mta_col_help"; 
	}

    /** BRM정보 검색
     * @throws Exception */
/*	@RequestMapping("/meta/mta/popup/brmCodeData.do")
	@ResponseBody
	public List<CodeListVo> gobrmCodeData(String brmId, Model model, Locale locale) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brmId", brmId);
		
		//===============brm 조회===================
		String ctrMetaUrl = message.getMessage("ctrMetaUrl", null, null);
		
		Map<String,Object> brmMap = CommNetApiUtil.callJsonStringApi(ctrMetaUrl+"/meta/stnd/api/response/getBrmRqstComtoList.do", HttpMethod.POST, params);
		Map<String, Object> RESULT = (Map<String, Object>)brmMap.get("RESULT");
		String code = RESULT.get("CODE").toString();
		List<WaaBrmDetail> list = new ArrayList<WaaBrmDetail>();
		int totCnt = 0;
		if("0".equals(code)) {
			list.addAll((List<WaaBrmDetail>)brmMap.get("DATA"));
			totCnt = (Integer)brmMap.get("TOTAL");
		}
    	//===========================================
		
		//List<CodeListVo> list = codeListService.getCodeList2(brmId);
		
		return list;
	}*/
	
	
	
}
