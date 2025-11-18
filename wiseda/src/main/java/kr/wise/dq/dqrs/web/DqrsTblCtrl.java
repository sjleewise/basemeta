package kr.wise.dq.dqrs.web;

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
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.dqrs.service.DqrsCodeService;
import kr.wise.dq.dqrs.service.DqrsResult;
import kr.wise.dq.dqrs.service.DqrsTbl;
import kr.wise.dq.dqrs.service.DqrsTblService;

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
public class DqrsTblCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class DqrsTbls extends HashMap<String, ArrayList<DqrsTbl>> { }
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private DqrsTblService dqrsTblService;
	
	@Inject
	private DqrsCodeService dqrsCodeService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		String connTrgSchId   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSchId", connTrgSchId);

		//진단대상(DB_CONN_TRG_ID) 정보 (Grantor) - 데이터권한관리
		List<CodeListVo> connTrgDbms2   = codeListService.getCodeList("connTrgDbms"); 
		codeMap.put("ConnTrgDbmsibs2", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms2)));
		codeMap.put("ConnTrgDbms2", connTrgDbms2);
		//스키마 Double Select(IBSheet) (Grantor) - 데이터권한
		Map<String, ComboIBSVo> connTrgSchIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("ConnTrgSchibs2",  UtilJson.convertJsonString(connTrgSchIbs));
		
		String pubSditmConnTrgId   = UtilJson.convertJsonString(dqrsCodeService.getPubSditmConnTrgId());
		codeMap.put("pubSditmConnTrgId", pubSditmConnTrgId);
		
		//모델 관리 > 일괄삭제 DBMS ID
		String allDbms   = UtilJson.convertJsonString(dqrsCodeService.getGovTblDbms());
		codeMap.put("allDbms", allDbms);
				
		//모델 관리 > 일괄삭제 스키마 ID
		String allDbmsSchId   = UtilJson.convertJsonString(dqrsCodeService.getGovTblSchId());
		codeMap.put("allDbmsSchId",allDbmsSchId);
				
				
		return codeMap;
	}
	
	/** 리스트 조회 폼 */
	@RequestMapping("/dq/dqrs/dqrs_tbl_lst.do")
	public String selectDqrsTbl(@ModelAttribute("searchVO") SearchVO searchVO, Model model) throws Exception {
		model.addAttribute("codeMap", getcodeMap());
		
		return "/dq/dqrs/dqrs_tbl_lst";
	}
	
	/** 리스트 조회 -IBSheet json */
	@RequestMapping("/dq/dqrs/getDqrsTblLst.do")
	@ResponseBody
	public IBSheetListVO<DqrsTbl> getDqrsTblLst(@ModelAttribute("searchVO") DqrsTbl search) throws Exception {
		
		List<DqrsTbl> list = dqrsTblService.getDqrsTblLst(search);
		
		return new IBSheetListVO<DqrsTbl>(list, list.size()); 
	}
	
	/** 엑셀로 메뉴리스트 (여러건)등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/dq/dqrs/saveDqrsTbl.do")
	@ResponseBody
	public IBSResultVO<DqrsTbl> regList(@RequestBody DqrsTbls data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<DqrsTbl> list = data.get("data");
		
		int result = dqrsTblService.regDqrsTbl(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<DqrsTbl>(result, resmsg, action);
	}
	
	
    /** 메뉴 삭제 for IBSheet
     * @throws Exception */
    @RequestMapping("/dq/dqrs/delDqrsTbl.do")
    @ResponseBody
    public IBSResultVO<DqrsTbl> delDqrsTbl(@RequestBody DqrsTbls data, Locale locale) throws Exception {
    	
    	logger.debug("vrfcNo:{}", data);
    	
    	ArrayList<DqrsTbl> list = data.get("data");
    	
    	int result = dqrsTblService.delDqrsTbl(list);
    	
    	
    	String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else{
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<DqrsTbl>(result, resmsg, action);
    }
    
    
    //////////구조품질 분석
    @RequestMapping("/dq/dqrs/dqrs_mdl_gap.do")    
	public String goDqrsMdlGapLst() {
		return "/dq/dqrs/dqrs_mdl_gap";  
	}
    
    @RequestMapping("/dq/dqrs/getDqrsMdlGapLst.do")
    @ResponseBody
    public IBSheetListVO<DqrsResult> getDqrsMdlGapLst(@ModelAttribute DqrsResult search) {
        logger.debug("getDqrsMdlGapLst{}", search);
        
        List<DqrsResult> list = dqrsTblService.getDqrsMdlGapLst(search);
        
        return new IBSheetListVO<DqrsResult>(list, list.size());
    }
    
    @RequestMapping("/dq/dqrs/getDqrsMdlColGapLst.do")
    @ResponseBody
    public IBSheetListVO<DqrsResult> getDqrsMdlColGapLst(@ModelAttribute DqrsResult search) {
        logger.debug("getDqrsMdlGapLst{}", search);
        
        List<DqrsResult> list = dqrsTblService.getDqrsMdlColGapLst(search);
        
        return new IBSheetListVO<DqrsResult>(list, list.size());
    }
}
