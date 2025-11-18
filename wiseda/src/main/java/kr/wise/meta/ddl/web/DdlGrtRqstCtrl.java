/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlGrtRqstCtrl.java
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
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
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
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.meta.ddl.service.DdlGrtRqstService;
import kr.wise.meta.ddl.service.WamDdlGrt;
import kr.wise.meta.ddl.service.WaqDdlGrt;

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
 * 2. FileName  : DdlGrtRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL 인덱스 요청 컨트롤러...
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 6. 14:57:00
 * </PRE>
 */
@Controller("DdlGrtRqstCtrl")
public class DdlGrtRqstCtrl {

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
	private DdlGrtRqstService ddlGrtRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private CmvwDbService cmvwDbService;

	static class WaqDdlGrts extends HashMap<String, ArrayList<WaqDdlGrt>> {}

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
		
		//오브젝트유형코드
		codeMap.put("objDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OBJ_DCD")));
		codeMap.put("objDcd", cmcdCodeService.getCodeList("OBJ_DCD"));
		

		//목록성 코드(시스템영역 코드리스트)
		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));


		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs);

		return codeMap;
	}

	/** DDL 인덱스 엑셀업로드 팝업 @return yeonho */
    @RequestMapping("/meta/ddl/popup/ddlgrt_xls.do")
    public String goDdlGrtXlsPop(@ModelAttribute("search") WaqDdlGrt search, Model model, Locale locale) {
    	
    	return "/meta/ddl/popup/ddlgrt_xls";
    }
    
	
    /** DDL인덱스 요청서 입력 폼.... @throws Exception yeonho */
    @RequestMapping("/meta/ddl/ddlgrt_rqst.do")
    public String goDdlGrtRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DDG");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	model.addAttribute("waqMstr", reqmst);
    	
    	MstrAprPrcVO mapvo = new MstrAprPrcVO();
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        int mapcount = approveLineServie.checkRequst(mapvo); 
        
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
        model.addAttribute("rqstbizdcd",reqmst.getBizDcd());

    	return "/meta/ddl/ddlgrt_rqst";

    }

    /** DDL 인덱스 요청 리스트 조회 @return yeonho */
    @RequestMapping("/meta/ddl/getddlgrtrqstlist.do")
    @ResponseBody
	public IBSheetListVO<WaqDdlGrt> getDdlGrtRqstList(WaqMstr search) {

		List<WaqDdlGrt> list = ddlGrtRqstService.getDdlGrtRqstList(search);


		return new IBSheetListVO<WaqDdlGrt>(list, list.size());
	}

    /** DDL 인덱스 요청 상세 정보 조회 @return yeonho */
    @RequestMapping("/meta/ddl/ajaxgrid/ddlgrt_rqst_dtl.do")
    public String getDdlGrtDetail(WaqDdlGrt searchVo, ModelMap model) {


    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	} else {
    		WaqDdlGrt resultvo =  ddlGrtRqstService.getDdlGrtRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/ddl/ddlgrt_rqst_dtl";

    }
    

    /** 인덱스 변경대상 추가 @throws Exception yeonho */
    @RequestMapping("/meta/ddl/regWam2WaqDdlGrt.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WaqDdlGrts data, WaqMstr reqmst, Locale locale) throws Exception {


		ArrayList<WaqDdlGrt> list = data.get("data");

		int result = ddlGrtRqstService.regWam2Waq(reqmst, list);

		result += ddlGrtRqstService.check(reqmst);

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

    @RequestMapping("/meta/ddl/delddlgrtrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delDdlGrtRqstList(@RequestBody WaqDdlGrts data, WaqMstr reqmst, Locale locale) throws Exception {

		ArrayList<WaqDdlGrt> list = data.get("data");

		int result = ddlGrtRqstService.delDdlGrtRqst(reqmst, list);

		result += ddlGrtRqstService.check(reqmst);

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
    @RequestMapping("/meta/ddl/regddlgrtrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regPdmTblRqstList(@RequestBody WaqDdlGrts data, WaqMstr reqmst, Locale locale) throws Exception {

		ArrayList<WaqDdlGrt> list = data.get("data");


		int result = ddlGrtRqstService.register(reqmst, list);
		result += ddlGrtRqstService.check(reqmst);

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

    
    

    /** DDL인덱스 승인 yeonho */
    @RequestMapping("/meta/ddl/approveddlgrt.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveDdlGrt(@RequestBody WaqDdlGrts data, WaqMstr reqmst, Locale locale) throws Exception {

		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}


		ArrayList<WaqDdlGrt> list = data.get("data");

		int result  = ddlGrtRqstService.approve(reqmst, list);


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
