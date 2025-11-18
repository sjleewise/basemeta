package kr.wise.dq.profile.mstr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.ProfileExcelRqstMstrService;
import kr.wise.dq.profile.mstr.service.ProfileSchRqstService;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.meta.model.service.ExerdPdmTblRqstService;
import kr.wise.meta.model.service.R7PdmTblRqstService;

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

@Controller
public class ProfileSchRqstCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class WaqPrfs extends HashMap<String, ArrayList<?>> { }
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	/* RQST_NO 채번*/
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private R7PdmTblRqstService r7pdmTblRqstService;

	@Inject
	private ExerdPdmTblRqstService exerdpdmTblRqstService;
	
	/* 결재라인조회*/
	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private ProfileExcelRqstMstrService prfExlRqstMstrService; 

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@Inject
	private ProfileSchRqstService batchService;
	
	@Inject
	private ProfileExcelRqstMstrCtrl rqstCtrl;

	/** 컬럼프로파일 일괄등록 화면 호출 
	 * @throws Exception */
	@RequestMapping("/dq/profile/popup/prfschrqst_pop")
	public String formpage(WaqMstr reqmst, ModelMap model) throws Exception {
		logger.debug(" reqmst {} "+reqmst);
		
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("PRF");
    		reqmst.setBizDtlCd("PT01");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//BIZ_DTL_CD 이걸 가져와야 하네...
    		String prfKndCd = prfExlRqstMstrService.getPrfKndCd(reqmst.getRqstNo());
    		reqmst.setBizDtlCd(prfKndCd);
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}
    	
    	//결재라인 결재레벨 조회
    	//결재레벨 미존재 시 검증 후 등록 버튼 활성화
    	WaaRqstBizApr aprSearch = new WaaRqstBizApr();
    	aprSearch.setBizDcd(reqmst.getBizDcd());
    	List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(aprSearch);
    	logger.debug("list SIZE : ", list.size());
    	reqmst.setAprLvl(list.size());
    	
    	model.addAttribute("waqMstr", reqmst);
		
		return "/dq/profile/popup/prfschrqst_pop";
	}
	
	/** 컬럼프로파일 등록대상 조회후 저장 */
	 /** @throws Exception */
	@RequestMapping("/dq/profile/getDbcColList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> selectList(@RequestBody WaqPrfs data, WaqMstr reqmst, Locale locale) throws Exception{
		logger.debug("{}", data);
		logger.debug("Mstr Info : {}", reqmst);
		
		ArrayList<?> list = data.get("data");
		List<?> resultVo;
		
		//BIZ_DTL_CD에 따라 가져오는 쿼리가 다름...
		//PC02, PC05는
		if (reqmst.getBizDtlCd().equals("PC01")){
			resultVo = batchService.getDbcColListPC01(list);
		} else if (reqmst.getBizDtlCd().equals("PC02")){
			resultVo = batchService.getDbcColListPC02(list);
		} else if (reqmst.getBizDtlCd().equals("PC03")){
			resultVo = batchService.getDbcColListPC03(list);
		} else if (reqmst.getBizDtlCd().equals("PC04")){
			resultVo = batchService.getDbcColListPC04(list);
		} else { //PC05
			resultVo = batchService.getDbcColListPC05(list);
		}
		
		logger.debug(" resultVo {}, {}" ,resultVo.size(), resultVo);
		
		int result = 0;
		String resmsg = "";
		String action = "";
		
		if(resultVo.size() > 0) {
			result = rqstCtrl.registerPorfile(reqmst, resultVo);
			action = WiseMetaConfig.RqstAction.REGISTER.getAction();
			//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
			reqmst = requestMstService.getrequestMst(reqmst);
			
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.PRFSCH", null, locale);
		}
		
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		
		
	}
	
	/** 등록요청중인 프로파일 일괄등록건 조회 */
	/** @param search
	/** @return meta */
	@RequestMapping("/dq/profile/getVrfProfileListIBS.do")
	@ResponseBody
	public IBSheetListVO<WaqPrfColAnaVO> getVrfProfileListIBS(@ModelAttribute WaqMstr search) {
		logger.debug("searchVO {} : ", search);
		List<WaqPrfColAnaVO> list = batchService.getVrfProfileListIBS(search);
		
		return new IBSheetListVO<WaqPrfColAnaVO>(list, list.size());
	}
	
	
	// 모델마트 관계정보 조회
	@RequestMapping("/dq/r7mart/searchModelMartRelLst.do")
	@ResponseBody
	public IBSheetListVO<WaqPrfRelColVO> getErMart7RelLst(@ModelAttribute WaqPrfRelColVO search, Locale locale) {
		logger.debug("{}", search);
		List<WaqPrfRelColVO> list = r7pdmTblRqstService.getErMart7RelLst(search);
		return new IBSheetListVO<WaqPrfRelColVO>(list, list.size());
	}
	
	// exerd마트 관계정보 조회
	@RequestMapping("/dq/r7mart/searchExerdRelLst.do")
	@ResponseBody
	public IBSheetListVO<WaqPrfRelColVO> getExerdRelLst(@ModelAttribute WaqPrfRelColVO search, Locale locale) {
		logger.debug("{}", search);
		List<WaqPrfRelColVO> list = exerdpdmTblRqstService.getExerdRelLst(search);
		return new IBSheetListVO<WaqPrfRelColVO>(list, list.size());
	}
	
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		codeMap = new HashMap<String, Object>();
		
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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		//프로파일종류코드(PRF_KND_CD)
		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSch"));
		codeMap.put("connTrgSch", connTrgSch);
		
		//유효값분석
		codeMap.put("efvaAnaKndCd",cmcdCodeService.getCodeList("EFVA_ANA_KND_CD"));
		//날짜형식코드
		codeMap.put("dateFrmCd",cmcdCodeService.getCodeList("DATE_FRM_CD"));
		//범위분석코드
		codeMap.put("rngOprCd",cmcdCodeService.getCodeList("RNG_OPR_CD"));
		//유효값유형
		codeMap.put("efvaAnaKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("EFVA_ANA_KND_CD")));
		//날짜분석유형코드
		codeMap.put("dateFrmCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DATE_FRM_CD")));
		//범위분석코드
		codeMap.put("rngOprCdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RNG_OPR_CD")));
		//범위분석 연결자코드
		codeMap.put("rngCncibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RNG_CNC_CD")));
		
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String cdValIvwCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD"));
		String cdValTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD"));
		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("cdValIvwCdibs", cdValIvwCd);
		codeMap.put("cdValTypCdibs", cdValTypCd);
		codeMap.put("dmngibs", dmngibs);
		codeMap.put("infotpibs", infotpibs);
		//domain ,infotp  double selet...
		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
		codeMap.put("dmnginfotp", dmnginfotp);
		
		return codeMap;
	}
	
	
}
