package kr.wise.dq.gov.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.gov.service.GovTblService;
import kr.wise.dq.gov.service.GovTblVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : MenuManageCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.web
 * 4. Comment  : 메뉴 관리 컨트롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 24. 오후 2:05:16
 * </PRE>
 */ 
@Controller
@RequestMapping("/dq/gov")

public class GovTblCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class GovTblVOs extends HashMap<String, ArrayList<GovTblVO>> { }
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private GovTblService govTblService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 리스트 조회 폼 */
	@RequestMapping("/govTbl_lst.do")
	public String selectVrfc(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/gov/govTbl_lst";
	}
	
	/** 리스트 조회 -IBSheet json */
	@RequestMapping("/selectGovTblList.do")
	@ResponseBody
	public IBSheetListVO<GovTblVO> selectVrfcList(@ModelAttribute("searchVO") GovTblVO search) throws Exception {
		logger.debug("serach >>> " + search.toString());
		
		List<GovTblVO> list = govTblService.selectGovTblList(search);
		
		return new IBSheetListVO<GovTblVO>(list, list.size()); 
	}
	
//	/** 메뉴 저장 단건 -  결과는 IBSResult Json 리턴 
//	 * @throws Exception */
//    @RequestMapping("/saveVrfcRow.do")
//    @ResponseBody
//    public IBSResultVO<GovTblVO> saveVrfcRow(GovTblVO saveVO, String saction, Locale locale) throws Exception {
//    	
//    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
//    	logger.debug("saveVO.getVrfcRule >>> " + saveVO.getVrfcRule());
//    	saveVO.setVrfcRule(saveVO.getVrfcRule().replaceAll("(\r\n|\r|\n|\n\r)", ""));
//    	
//    	int result = govTblService.saveVrfc(saveVO, saction);
//
//    	String resmsg ;
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//    	String action = WiseMetaConfig.IBSAction.REG.getAction();
//
//    	return new IBSResultVO<GovTblVO>(saveVO, result, resmsg, action);
//    }
//
//	
//	/** 메뉴 상세 정보 조회 */
//	@RequestMapping("/selectGovTblDetail.do")
//	public String selectVrfcDetail(String vrfcId, String saction, ModelMap model) {
//		logger.debug(" {}", vrfcId);
//
//		//신규 입력으로 초기화
//		model.addAttribute("saction", "I");
//		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
//		if(!UtilObject.isNull(vrfcId)) {
//
//			GovTblVO result = govTblService.selectVrfcDetail(vrfcId);
//			model.addAttribute("result", result);
//			model.addAttribute("saction", "U");
//		}
//		
//		return "/dq/gov/govTbl_dtl";
//	}
//	
	/** 엑셀로 메뉴리스트 (여러건)등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/saveGovTbl.do")
	@ResponseBody
	public IBSResultVO<GovTblVO> regList(@RequestBody GovTblVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<GovTblVO> list = data.get("data");
		int result = govTblService.regGovTbl(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<GovTblVO>(result, resmsg, action);
	}
	
	
    /** 메뉴 삭제 for IBSheet
     * @throws Exception */
    @RequestMapping("/deleteGovTbl.do")
    @ResponseBody
    public IBSResultVO<GovTblVO> deleteVrfc(@RequestBody GovTblVOs data, Locale locale) throws Exception {
    	
    	logger.debug("vrfcNo:{}", data);
    	ArrayList<GovTblVO> list = data.get("data");
    	int result = govTblService.deleteGovTbl(list);
    	
    	
    	String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else{
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<GovTblVO>(result, resmsg, action);
    }
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId"));
		codeMap.put("devConnTrgSch", devConnTrgSch);
		String connTrgSchId   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSchId", connTrgSchId);
		List<CodeListVo> connTrgDbms = codeListService.getCodeList("connTrgDbms");
		String connTrgDbmsIbs = UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms));
		codeMap.put("connTrgDbmsIbs", connTrgDbmsIbs);
//		List<CodeListVo> dbSchId   = codeListService.getCodeList("dbSchId");
//		String dbSchIdIbs = UtilJson.convertJsonString(codeListService.getCodeListIBS(dbSchId));
//		codeMap.put("dbSchIdIbs", dbSchIdIbs);
		// DBMS + 스키마 (double_select)
		String connTrgDbmsSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2"));
		codeMap.put("connTrgDbmsSch", connTrgDbmsSch);
		//진단대상(DB_CONN_TRG_ID) 정보 (Grantor) - 데이터권한관리
		List<CodeListVo> connTrgDbms2   = codeListService.getCodeList("connTrgDbms"); 
		codeMap.put("ConnTrgDbmsibs2", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms2)));
		codeMap.put("ConnTrgDbms2", connTrgDbms2);
		
		List<CodeListVo> dbSchLnm   = codeListService.getCodeList("dbSchLnm"); 
		codeMap.put("dbSchLnmOnlyibs2", UtilJson.convertJsonString(codeListService.getCodeListIBS(dbSchLnm)));
//		codeMap.put("ConnTrgDbms2", connTrgDbms2);
		
		//스키마 Double Select(IBSheet) (Grantor) - 데이터권한
		Map<String, ComboIBSVo> connTrgSchIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("ConnTrgSchibs2",  UtilJson.convertJsonString(connTrgSchIbs));
				
		//시스템영역 코드리스트 JSON
//		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
//		String sysarea = UtilJson.convertJsonString(sysareatmp);
//		List<CodeListVo> sysareatmpOnwer = codeListService.getCodeList("sysareaOwner");
//		String sysareaOwner = UtilJson.convertJsonString(sysareatmpOnwer);
//		String sysareaibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));
		
//		codeMap.put("sysarea", sysarea);
//		codeMap.put("sysareaOwner", sysareaOwner);
//		codeMap.put("sysareaibs", sysareaibs); 

		codeMap.put("connTrgSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("connTrgSchId")));
		
		
		return codeMap;
	}
}
