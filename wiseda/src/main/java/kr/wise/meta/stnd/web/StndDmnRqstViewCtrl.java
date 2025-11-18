/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDmnRqstCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준도메인 요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 15. 오후 2:18:13
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 15. :            : 신규 개발.
 */
package kr.wise.meta.stnd.web;
 
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
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.StndDmnRqstService;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WaqCdVal;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.web.StndItemRqstCtrl.WapDvCanAsms;

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
 * 2. FileName  : StndDmnRqstViewCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : lsi
 * 6. 작성일   : 2019.01.17
 * </PRE>
 * 
 * 
 *190117 url요청 시 마다 불필요하게 코드맵이 호출되어 속도저하를 일으킴.(modelattribute가 붙은 메소드는 모든 요청시에 실행되기 때문에 쿼리가 불필요하게 많이 조회됨)
 *       view만 관리하는 컨트롤러와 나머지 request&response를 관리하는 컨트롤러로 분리
 *       StndDmnRqstViewCtrl.java , StndDmnRqstCtrl.java
 */
@Controller("StndDmnRqstViewCtrl")
public class StndDmnRqstViewCtrl {

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
	private StndDmnRqstService stndDmnRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private InfotpService infotpService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;


	static class WaqDmns extends HashMap<String, ArrayList<WaqDmn>> {}

	static class WaqCdVals extends HashMap<String, ArrayList<WaqCdVal>> {}


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
		//도메인출처구분코드
		codeMap.put("dmnOrgDsibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DMN_ORG_DS_CD")));
		codeMap.put("dmnOrgDs", cmcdCodeService.getCodeList("DMN_ORG_DS_CD"));

		//표준구분 이베이코리아
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		List<CodeListVo> dmng 	= codeListService.getCodeList("dmng");

		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
		codeMap.put("dmng", UtilJson.convertJsonString(dmng));
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dmng)));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp")));
		codeMap.put("dmnginfotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmnginfotp")));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp")));
		codeMap.put("codedmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("codedmnginfotp")));
		codeMap.put("infotpinfolst", UtilJson.convertJsonString(infotpService.getInfoTypeList()));

		Map<String, ComboIBSVo> infoTpCodeListIBS = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("infoTpCodeListIBS"));
		codeMap.put("infoTpCodeListIBS",  UtilJson.convertJsonString(infoTpCodeListIBS));
		
		Map<String, ComboIBSVo> dmngCodeListByStndAsrtIBS = codeListService.getDmngByStndAsrtIBS(codeListService.getCodeList("dmngLowDgr"));
		codeMap.put("dmngCodeListByStndAsrtIBS",  UtilJson.convertJsonString(dmngCodeListByStndAsrtIBS));
		
		return codeMap;
	}


    /** 표준도메인 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/stnd/stnddmn_rqst.do")
    public String goStndDmnRqstForm(WaqMstr reqmst, ModelMap model) throws Exception {
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

    	return "/meta/stnd/stnddmn_rqst";

    }


    /** 표준도메인 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/stnd/stnddmn_rqst_ifm.do")
    public String goStndDmnRqstIframe(WaqMstr reqmst, ModelMap model) throws Exception {
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

    	return "/meta/stnd/stnddmn_rqst_ifm";

    }

    /** 표준도메인 요청 상세 정보 조회 @return insomnia 
     * @throws Exception */
    @RequestMapping("/meta/stnd/ajaxgrid/stnddmn_rqst_dtl.do")
    public String getstnddmnDetail(WaqDmn searchVo, ModelMap model) throws Exception {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDmn resultvo =  stndDmnRqstService.getStndDmnRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
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
    	
    	return "/meta/stnd/stnddmn_rqst_dtl";

    }
	
    //표준도메인  자동분할
    @RequestMapping("/meta/stnd/stnddmndiv_lst.do")
    public String gostnddmnxlspage(@ModelAttribute("search") WaqDmn search) {

    	return "/meta/stnd/stnddmndiv_lst";
    }
	
	/** 도메인 자동분할 팝업... @return insomnia */
	@RequestMapping("/meta/stnd/popup/dmndivsion_pop.do")
	public String domaindivisionpop(@ModelAttribute("search") WaqDmn search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		/*if(StringUtils.hasText(search.getDmnLnm())) {
			model.addAttribute("dmnLnm" , search.getDmnLnm());
		}
		if(StringUtils.hasText(search.getRqstNo())) {
			model.addAttribute("rqstNo" , search.getRqstNo());
		}*/
		return "/meta/stnd/popup/dmndivsion_pop";
	}


	/** 부분유효값 팝업 추가... @return insomnia */
	@RequestMapping("/meta/stnd/popup/stnddmn_xls.do")
	public String gostndcdval(@ModelAttribute("search") WaqDmn search ){
		
		return "/meta/stnd/popup/stnddmn_xls";
	}
	
	
	/** 도메인 엑셀등록 추가...  */
	@RequestMapping("/meta/stnd/popup/stndcdval_pop.do")
	public String gostnddmnxls(@ModelAttribute("search") WaqDmn search ){

		return "/meta/stnd/popup/stndcdval_pop";
	}
	
	    /** 코드도메인 요청서 입력 폼.... @throws Exception LSI */
    @RequestMapping("/meta/stnd/codedmn_rqst.do")
    public String goCodeDmnRqst(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("CDM");  //업무구분코드: 코드도메인
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else if (!"N".equals(reqmst.getRqstStepCd())){
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
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

    	return "/meta/stnd/codedmn_rqst";

    }

    /** 코드도메인 요청 상세 정보 조회 @return LSI
     * @throws Exception */
    @RequestMapping("/meta/stnd/ajaxgrid/codedmn_rqst_dtl.do")
    public String getcodedmnDetail(WaqDmn searchVo, ModelMap model) throws Exception {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqDmn resultvo =  stndDmnRqstService.getStndDmnRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
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
    	
    	return "/meta/stnd/codedmn_rqst_dtl";

    }


}





