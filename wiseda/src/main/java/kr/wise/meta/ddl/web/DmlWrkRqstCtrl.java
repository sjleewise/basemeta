/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTblRqstCtrl.java
 * 2. Package : kr.wise.meta.ddl.web
 * 3. Comment : DDL 테이블 요청 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2015. 1. 28.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2015. 1. 28. :            : 신규 개발.
 */
package kr.wise.meta.ddl.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.ddl.service.DmlWorkRqstService;
import kr.wise.meta.ddl.service.WaqDmlWrk;

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
 * 2. FileName  : DmlWrkRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DML 작업 요청 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2015. 1. 28.
 * </PRE>
 */
@Controller("DmlWrkRqstCtrl")
public class DmlWrkRqstCtrl {

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
	private DmlWorkRqstService dmlWorkRqstService;

	@Inject
	private ApproveLineServie approveLineServie;


	static class WaqDmlWrks extends HashMap<String, ArrayList<WaqDmlWrk>> {}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);

		//공통코드 - IBSheet Combo Code용
		//TODO : 요청에 포함되는 공통코드는 한곳에 몰자 나중에 시간되면....
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

		codeMap.put("dbmsverscdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD")));
		codeMap.put("dbmstypcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD")));
		codeMap.put("ddlTrgDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DDL_TRG_DCD")));
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));

//		REQ_RSN_CD	요청사유코드
		codeMap.put("reqRsnCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REQ_RSN_CD")));
		codeMap.put("reqRsnCd", cmcdCodeService.getCodeList("REQ_RSN_CD"));
//		WORK_OBJT_CD	작업대상
		codeMap.put("workObjtCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WORK_OBJT_CD")));
		codeMap.put("workObjtCd", cmcdCodeService.getCodeList("WORK_OBJT_CD"));
//		JOB_DVD_CD	업무구분
		codeMap.put("jobDvdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOB_DVD_CD")));
		codeMap.put("jobDvdCd", cmcdCodeService.getCodeList("JOB_DVD_CD"));
//		WORK_DVD_CD	작업구분
		codeMap.put("workDvdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WORK_DVD_CD")));
		codeMap.put("workDvdCd", cmcdCodeService.getCodeList("WORK_DVD_CD"));


		//목록성 코드(시스템영역 코드리스트)
//		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
//		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));
//
//		codeMap.put("sysarea", sysarea);
//		codeMap.put("sysareaibs", sysareaibs);



		return codeMap;
	}


    /** DML 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/ddl/dmlinic_rqst.do")
    public String goDmlWorkRqstFrom(WaqMstr reqmst, ModelMap model) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DML");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/ddl/dmlinic_rqst";

    }

    @RequestMapping("/meta/ddl/regdmlworkrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regdmlWorkRqstList(@RequestBody WaqDmlWrks data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqDmlWrk> list = data.get("data");

		int result = dmlWorkRqstService.register(reqmst, list);

		result += dmlWorkRqstService.check(reqmst);

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

}

