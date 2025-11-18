/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstViewCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준항목 view 요청 컨트롤러
 * 4. 작성자  : lsi
 * 5. 작성일  : 2019. 1. 17.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    lsi : 2019. 1. 17. :            : 신규 개발.
 *                    
 *      *190117 url요청 시 마다 불필요하게 코드맵이 호출되어 속도저하를 일으킴.(modelattribute가 붙은 메소드는 모든 요청시에 실행되기 때문에 쿼리가 불필요하게 많이 조회됨)
 *       view만 관리하는 컨트롤러와 나머지 request&response를 관리하는 컨트롤러로 분리
 *       StndItemRqstViewCtrl.java , StndItemRqstCtrl.java                 
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
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.web.StndWordCtrl.WamStwdAbrs;

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

@Controller("StndItemRqstViewCtrl")
public class StndItemRqstViewCtrl {

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
	private InfotpService infotpService;

	@Inject
	private StndItemRqstService stndItemRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CodeListService codelistService;

	static class WaqSditms extends HashMap<String, ArrayList<WaqSditm>> {}
	
	static class WapDvCanAsms extends HashMap<String, ArrayList<WapDvCanAsm>> {}


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
		
		//표준구분 멀티사전용
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));
		
		//dbType
		codeMap.put("dbmsTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD")));
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		
		//목록성 코드(시스템영역 코드리스트)
//		String sysarea 		= UtilJson.convertJsonString(codeListService.getCodeList("sysarea"));
//		String sysareaibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("sysarea"));
		String dmng 		= UtilJson.convertJsonString(codeListService.getCodeList("dmng"));
		String dmngibs 		= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		String dmnginfotp 	= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
		String infotpinfolst = UtilJson.convertJsonString(infotpService.getInfoTypeList());

//		codeMap.put("sysarea", sysarea);
//		codeMap.put("sysareaibs", sysareaibs);
		codeMap.put("dmng", dmng);
		codeMap.put("dmngibs", dmngibs);
		codeMap.put("infotpibs", infotpibs);
		codeMap.put("dmnginfotp", dmnginfotp);
		codeMap.put("infotpinfolst", infotpinfolst);
		
		//범정부연계항목
		//비공개사유
		codeMap.put("nopenRsnCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NOPEN_RSN_CD")));
		codeMap.put("nopenRsnCd", cmcdCodeService.getCodeList("NOPEN_RSN_CD"));
	
		return codeMap;
	}
	
	
	
    //표준항목 분할
    @RequestMapping("/meta/stnd/stnditemdiv_lst.do")
    public String gostnditemxlspage(@ModelAttribute("search") WaqSditm search) {

    	return "/meta/stnd/stnditemdiv_lst";
    }
    
    /** 표준항목 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/stnd/stnditem_rqst.do")
    public String goStndItemRqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/stnd/stnditem_rqst";

    }

    /** 표준항목 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/stnd/stnditem_rqst_ifm.do")
    public String goStndItemRqstIframe(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);

    	return "/meta/stnd/stnditem_rqst_ifm";

    }
    
    /** 비표준항목 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/stnd/notstnditem_rqst.do")
    public String goNotStndItemRqstIframe(WaqMstr reqmst, ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);
    	
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("NDIC");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}
    	
    	logger.debug("reqmst:{}", reqmst);
    	
    	model.addAttribute("waqMstr", reqmst);
    	
    	return "/meta/stnd/notstnditem_rqst";
    	
    }


    /** 표준항목 요청서 상세정보 조회 @return insomnia 
     * @throws Exception */
    @RequestMapping("/meta/stnd/ajaxgrid/stnditem_rqst_dtl.do")
    public String goStndItemDetail(WaqSditm searchVo, ModelMap model) throws Exception {
    	logger.debug("searhcVo:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");
    	} else {
    		WaqSditm result = stndItemRqstService.getStndItemRqstDetail(searchVo);
    		model.addAttribute("result", result);
    		model.addAttribute("saction", "U");
    	}

    	//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
		}
    	
    	return "/meta/stnd/stnditem_rqst_dtl";
    }

    /** 표준항목 자동분할 팝업 호출.... @return insomnia */
    @RequestMapping("/meta/stnd/popup/stnditemdivision_pop.do")
    public String goitemdivisionpop(@ModelAttribute("search") WaqSditm search, Model model, Locale locale) {
    	logger.debug("search:{}", search);

    	return "/meta/stnd/popup/stnditemdivision_pop";
    }

    @RequestMapping("/meta/stnd/popup/stnditem_xls.do")
    public String gostnditemxls(@ModelAttribute("search") WaqSditm search) {

    	return "/meta/stnd/popup/stnditem_xls";
    }

    @RequestMapping("/meta/stnd/popup/persSearchPop.do")
    public String persSearchPop(@ModelAttribute("search") WaqSditm search, ModelMap model) {

    	List<Map<String, Object>> list = stndItemRqstService.getPersCode();
    	model.addAttribute("result",list);
    	
    	return "/meta/stnd/popup/persSearch_pop";
    }
}
