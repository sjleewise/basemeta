/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDomainCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 25. 오전 12:15:33
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 25. :            : 신규 개발.
 */
package kr.wise.meta.govstnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.dmnginfo.service.DmngService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
//import kr.wise.commons.user.web.UserCtrl.WaaUsers;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.govstnd.service.GovDmnService;
import kr.wise.meta.govstnd.service.GovDmnVo;
import kr.wise.meta.stnd.service.MsgStdRqstService;
import kr.wise.meta.stnd.service.StndDomainService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDomainCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 25. 오전 12:15:33
 * </PRE>
 */
@Controller("GovDmnCtrl")
public class GovDmnCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static class GovDmnVos extends HashMap<String, ArrayList<GovDmnVo>> { }

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private StndDomainService stndDomainService;
	
	@Inject
	private DmngService dmngService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private GovDmnService govDmnService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private MsgStdRqstService msgStdRqstService;
	
	
	
//	@Resource(name="diagDmnService")
//	private DiagDmnService diagDmnService;
	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	
	/** 도메인 리스트 조회 */
	@RequestMapping("/meta/govstnd/getDiagDmnList.do")
	@ResponseBody
	public IBSheetListVO<GovDmnVo> getDiagDmnList(@ModelAttribute GovDmnVo data, Locale locale) {

		logger.debug("reqvo:{}", data);
		//System.out.println("조회 조회 조회");

		List<GovDmnVo> list = govDmnService.getDomainList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<GovDmnVo>(list, list.size());

	}
	
	
	@RequestMapping(value="/meta/govstnd/diag_dmn_lst.do")
	public String goDiagStndDmnList(HttpSession session, @RequestParam(value="objId", required=false) String dmnId,  String linkFlag,Model model) throws Exception {
		logger.debug("objId 조회 {}", dmnId);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("dmnId", dmnId);
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/govstnd/diag_dmn_lst";
	}
	
	
	@RequestMapping("/meta/govstnd/save_diag_dmn_lst.do")
	@ResponseBody
	public IBSResultVO<GovDmnVo> insertSelective(@RequestBody GovDmnVos data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<GovDmnVo> list = data.get("data");
	
		int result = govDmnService.register(list);

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
//삭제
	/** 도메인 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/govstnd/delDiagDmnList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delDiagDmnList(@RequestBody GovDmnVos data, WaqMstr reqmst,  Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
	
		ArrayList<GovDmnVo> list = data.get("data");

		int result = govDmnService.delDiagDmnList(list);
		
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
    //	System.out.println(action);

    	//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
    	//reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(  reqmst,result, resmsg, action);
		
	}
	
	
	



//	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		

		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> infonm = codeListService.getCodeList("infoSysNm");
		
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng")));
		
		String infsys = UtilJson.convertJsonString(infonm);
		String infosysibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(infonm));
		
		codeMap.put("infoSysNm", infsys);
		codeMap.put("infosysibs", infosysibs); 
		
		
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng")));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp")));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp")));
		codeMap.put("infotpId", infonm);
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);
//		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
//		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
//		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
//
//		codeMap.put("dmngibs", dmngibs);
//		codeMap.put("dmngId", codeListService.getCodeList("dmng"));
//		codeMap.put("infotpibs", infotpibs);
//		codeMap.put("dmnginfotp", dmnginfotp);
//		codeMap.put("infotpId", codeListService.getCodeList("infotp"));
		
		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String cdValIvwCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD"));
		String cdValTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD"));

		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("dataTypeCd", cmcdCodeService.getCodeList("DATA_TYPE"));

		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("cdValIvwCdibs", cdValIvwCd);
		codeMap.put("cdValTypCdibs", cdValTypCd);
		
		//도메인출처구분코드
		codeMap.put("dmnOrgDsibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DMN_ORG_DS_CD")));
		codeMap.put("dmnOrgDs", cmcdCodeService.getCodeList("DMN_ORG_DS_CD"));
		
		//표준구분 이베이코리아
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);
		
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId"));
		codeMap.put("devConnTrgSch", devConnTrgSch);
		
		//시스템영역 코드리스트 JSON
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysareaOwner");
		String sysarea = UtilJson.convertJsonString(sysareatmp);
		String sysareaibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));
		
		codeMap.put("stndTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STND_TYP_CD")));
	    codeMap.put("stndTypCd", cmcdCodeService.getCodeList("STND_TYP_CD"));
		
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs); 
		
//		String devConnTrgSchByOwner   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchByOwner"));
//		codeMap.put("devConnTrgSchByOwner", devConnTrgSchByOwner);
		
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
	


	

	
	@RequestMapping(value="/meta/govstnd/auto_dmn_sditm.do")
	public String goAutoDmnSditm(Model model) throws Exception {
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/govstnd/auto_dmn_sditm";
	}
	
	
	
	@RequestMapping(value="/meta/govstnd/diag_dmn_sditm.do")
	public String goDiagDmnSditm(Model model) throws Exception {
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/govstnd/diag_dmn_sditm";
	}

}
