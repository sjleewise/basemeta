/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlEtcCtrl.java
 * 2. Package : kr.wise.meta.ddletc.web
 * 3. Comment : DDL 인덱스 요청 컨트롤러...
 * 4. 작성자  : 
 * 5. 작성일  : 2014. 8. 6. 14:57:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 6.   :            : 신규 개발.
 */
package kr.wise.meta.ddletc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
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
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddletc.service.DdlEtcRqstService;
import kr.wise.meta.ddletc.service.DdlEtcService;
import kr.wise.meta.ddletc.service.WamDdlEtc;
import kr.wise.meta.ddletc.service.WaqDdlEtc;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.stnd.service.WamStwd;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlIdxRqstCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL 인덱스 요청 컨트롤러...
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 6. 14:57:00
 * </PRE>
 */
@Controller("DdlEtcCtrl") 
public class DdlEtcCtrl {

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
	private DdlEtcService ddlEtcService; 


	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

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
		
		
		codeMap.put("etcObjDcd", cmcdCodeService.getCodeList("ETC_OBJ_DCD"));
		codeMap.put("etcObjDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ETC_OBJ_DCD")));
				
		return codeMap;
	}

	
	
	@RequestMapping("/meta/ddletc/popup/ddletc_pop.do")
	public String goDdlEtcPop(@ModelAttribute("search") WamDdlEtc search, Model model, Locale locale) {
		logger.debug("{}", search);


		return "/meta/ddletc/popup/ddletc_pop";
	}
	
	/** DDL 기타오브젝트  리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddletc/getDdlEtcList.do")
	@ResponseBody
	public IBSheetListVO<WamDdlEtc> selectList(@ModelAttribute WamDdlEtc search) {
		logger.debug("{}", search);
		List<WamDdlEtc> list = ddlEtcService.getDdlEtcList(search);

		return new IBSheetListVO<WamDdlEtc>(list, list.size());
	}
	
	@RequestMapping("/meta/ddletc/ddletc_lst.do")
	public String goDdlEtcLst(@ModelAttribute("search") WamDdlEtc search, Model model, Locale locale) {
		logger.debug("{}", search);


		return "/meta/ddletc/ddletc_lst";
	}
	
	
	/** 기타오브젝트 상세정보 조회 */
	@RequestMapping("/meta/ddletc/ajaxgrid/ddletcinfo_dtl.do")
	public String selecDdlEtcInfoDetail(String ddlEtcId, String rqstNo, ModelMap model) {
		logger.debug(" {}", ddlEtcId);
	
		if(!UtilObject.isNull(ddlEtcId) && !UtilObject.isNull(rqstNo)) {

			WamDdlEtc result = ddlEtcService.gettDdlEtcDetail(ddlEtcId, rqstNo); 
			model.addAttribute("result", result);
		}
		return "/meta/ddletc/ddletcinfo_dtl";
	}


}
