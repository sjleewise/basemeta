package kr.wise.meta.govstnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.govstnd.service.GovDmnService;
import kr.wise.meta.govstnd.service.GovDmnVo;
import kr.wise.meta.govstnd.service.GovSditm;
import kr.wise.meta.govstnd.service.GovSditmService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/meta/govstnd/*")
public class GovSditmCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private GovSditmService govSditmService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private GovDmnService govDmnService;
	
	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	private Map<String, Object> codeMap;
	
	static class GovSditms extends HashMap<String, ArrayList<GovSditm>> {}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));

		codeMap.put("dmngibs", dmngibs);
		codeMap.put("dmngId", codeListService.getCodeList("dmng"));
		codeMap.put("infotpibs", infotpibs);
		codeMap.put("dmnginfotp", dmnginfotp);
		codeMap.put("infotpId", codeListService.getCodeList("infotp"));

		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		//표준구분 멀티사전용
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		
		//시스템영역 코드리스트 JSON
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
		String sysarea = UtilJson.convertJsonString(sysareatmp);
		String sysareaibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));
//		List<CodeListVo> sysareaOwnertmp = codeListService.getCodeList("sysareaOwner");
//		String sysareaOwner = UtilJson.convertJsonString(sysareaOwnertmp);
//		
//		codeMap.put("sysareaOwner", sysareaOwner);
		
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs); 
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		//진단대상/스키마 정보(double_select용 목록성코드)
//		String connTrgSchByOnwer   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchIdByOwner"));
//		codeMap.put("connTrgSchByOnwer", connTrgSchByOnwer);
		
		//subj
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("getSubjLnm"));
		codeMap.put("subjLnm", subjLnm);
		
//		List<CodeListVo> sysareaOwner = codeListService.getCodeList("sysareaOwner");
//		codeMap.put("sysareaOwner", UtilJson.convertJsonString(sysareaOwner));
		
		
		return codeMap;
	}
	
	/**
	 * 진단 표준용어 조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="diag_sditm_lst.do")
	public String goStndDiagSditmList(Model model) throws Exception {

		//model.addAttribute("codeMap", getcodeMap());
		return "/meta/govstnd/diag_sditm_lst";
	}

	/** 진단 표준용어 조회 -IBSheet json */
	@RequestMapping("/meta/govstnd/getdiagSditmList.do")
	@ResponseBody
	public IBSheetListVO<GovSditm> getdiagSditmList(@ModelAttribute GovSditm data, Locale locale) {
		
		logger.debug("data:{}", data);
		logger.debug("data:{}", data.getDbSchId());
		
		List<GovSditm> list = govSditmService.getdiagSditmList(data);

		return new IBSheetListVO<GovSditm>(list, list.size());
	}
	
	/**
	 * 진단 표준용어 저장 및 검증 
	 */
	
	@RequestMapping("/meta/govstnd/addDiagSditmList.do")
	@ResponseBody
	public IBSResultVO<GovSditm> addDiagSditmList(@RequestBody GovSditms data, HttpSession session, Locale locale) throws Exception {

		logger.debug("data:{}", data);

		ArrayList<GovSditm> list = data.get("data");

		int result = govSditmService.register(list);

//		int result = stndWordRqstService.regStndWordRqstlist(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG.getAction();

		/*
		 * //마지막에 최종 업데이트 된 요청마스터 정보를 가져온다. reqmst =
		 * requestMstService.getrequestMst(reqmst);
		 */

		return new IBSResultVO<GovSditm>(result, resmsg, action);
	}
	
	/** 진단 표준용어 삭제
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("/meta/govstnd/deleteDiagSditmList.do")
	@ResponseBody
	public IBSResultVO<GovSditm> deleteDiagSditmList(WaqMstr reqmst, @RequestBody GovSditms data, Locale locale) throws Exception {

		logger.debug("data:{}", data);

		ArrayList<GovSditm> list = data.get("data");

		int result = govSditmService.delDiagSditmList(list);

		String resmsg ;

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
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<GovSditm>(result, resmsg, action);
	}
	
//	@RequestMapping("/meta/stnd/getInfoSys.do")
//	@ResponseBody
//	public List<HashMap> getInfoSys() {
//		
//		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
//	
//		List<HashMap> list = govSditmService.getInfoSys(); 
//		
//		return list;
//
//	}
	
	@RequestMapping("/meta/govstnd/autoCreStnd.do")
	@ResponseBody
	public IBSResultVO<GovDmnVo> autoCreStnd(String dbConnTrgId, String dbSchId, Locale locale) throws Exception {
		// 개별DB표준용어/도메인 자동생성

		logger.debug("dbConnTrgId:{}", dbConnTrgId);
		logger.debug("dbSchId:{}", dbSchId);
		
		int result = 0;
		
		result += govSditmService.regAutoCreGovSdtimDmn(dbSchId);
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);

		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<GovDmnVo>(result, resmsg, action);
	}
	
}
