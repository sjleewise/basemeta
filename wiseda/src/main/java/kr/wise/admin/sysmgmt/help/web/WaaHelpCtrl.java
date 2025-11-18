package kr.wise.admin.sysmgmt.help.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.admin.sysmgmt.help.service.WaaHelpService;
import kr.wise.admin.sysmgmt.help.service.WaaHelpVO;
import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.security.XSS;
import kr.wise.commons.cmm.service.FileManagerService;
import kr.wise.commons.cmm.service.FileManagerUtil;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WaaHelpCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaHelpService waahelpservice ;
	
	@Inject
	private MessageSource message;

	@Inject
    private FileManagerUtil fileManagerUtil;

	@Inject
	private FileManagerService  fileMngService;

	@Inject
	private UsergMenuMapService usergMenuMapService;


	@Inject
	private CodeListService codeListService;

	private final Map<String, Object> codeMap = new HashMap<String, Object>();

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	//@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

//		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String ofBizType 		= UtilJson.convertJsonString(codeListService.getComCodeList(WiseConfig.META, "BIZ_TYPE"));
//		String requestStatus 	= UtilJson.convertJsonString(codeListService.getComCodeList(WiseConfig.META, "STATUS_CODE"));
//		String sysareaibs 	= UtilJson.convertJsonString(codelistService.getCodeListIBS("sysarea"));
//		codeMap.put("ofBizType", ofBizType);
//		codeMap.put("ofBizType", codeListService.getComCodeList(WiseConfig.META, "RQST_BIZ_TYPE"));
//		codeMap.put("requestStatus", codeListService.getComCodeList(WiseConfig.META, "STATUS_CODE"));
//		codeMap.put("sysareaibs", sysareaibs);
		codeMap.put("prjCode", codeListService.getCodeList(WiseConfig.PRJLIST));
		codeMap.put("reportType", codeListService.getComCodeList(WiseConfig.PORTAL, "BBS011"));

		return codeMap;
	}

	private void setusermenumap(String bbsid, ModelMap model) {
		String servletPath = "/commons/bbs/selectBoardList.do";
		if (StringUtils.hasText(bbsid)) {
			servletPath += "?bbsId=" + bbsid;
		}
//		Map<String, Object> menumap = usergMenuMapService.getMenuMap(servletPath);
		Map<String, Object> menumap = usergMenuMapService.getMenuMap2(servletPath);

		if (menumap.containsKey("REQ_MENU")) {
			model.addAttribute("REQ_MENU", menumap.get("REQ_MENU"));
		}
		if (menumap.containsKey("TOP_MENU")) {
			model.addAttribute("TOP_MENU", menumap.get("TOP_MENU"));
		}
		if (menumap.containsKey("SUB_MENU")) {
//			UtilJson.convertJsonString(menumap.get("SUB_MENU"));
			model.addAttribute("SUB_MENU", UtilJson.convertJsonString(menumap.get("SUB_MENU")));
		}
	}
	
	

	@RequestMapping("/admin/sys/help/HelpMstrList.do")
	public String goHelpList() {
		return "/admin/sys/help/helpMstrList";
	}
	
	
	/** 도움말 리스트 조회-IBSheet Json
	 * @throws Exception */

	@RequestMapping("/admin/sys/help/selectAllHelpMstrList.do")
	@ResponseBody
	public IBSheetListVO<WaaHelpVO> selectAllHelpMstrList(WaaHelpVO vo) throws Exception {
		log.debug("loginLog : {}", vo);
		List<WaaHelpVO> resultlist = waahelpservice.selectAllHelpMstrList(vo);


		return new IBSheetListVO<WaaHelpVO>(resultlist, resultlist.size());
	}
	
	/** 도움말 상세 정보
	 * @throws Exception */
    @RequestMapping("/admin/sys/help/HelpMstrdtl.do")
    //@ResponseBody
    public String selectHelpDetail(WaaHelpVO searchVO, String saction,  ModelMap model) throws Exception {
    	log.debug("searvhVO : {}", searchVO);
    	model.addAttribute("saction", "I");

    	if( StringUtils.hasLength(searchVO.getHelpId()) ){
    		WaaHelpVO resultVO = waahelpservice.selectHelpMasterInf(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}

    	return "/admin/sys/help/HelpMstrdtl";
    }
    
    /** 프로그램 저장 단건 -  결과는 IBSResult Json 리턴
     * @throws Exception */
    @RequestMapping("/admin/sys/help/ajaxgrid/insertHelpMastetInf.do")
    public String saveHelpRow(WaaHelpVO saveVO, String saction, Locale locale) throws Exception {

    	log.debug("saveVO:{}, saction:{}", saveVO, saction);
    	//saveVO.setHelpCtnt(XSS.unscript(saveVO.getHelpCtnt()));	// XSS 방지
    	int result = waahelpservice.saveHelp(saveVO, saction);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	return "redirect:/admin/sys/help/HelpMstrList.do";
    }
		
	@RequestMapping("/admin/sys/help/deleteHelpMasterInf.do")
    @ResponseBody
    public IBSResultVO<WaaHelpVO> delHelpRow(WaaHelpVO delVO, Locale locale) throws Exception {

    	log.debug("delVO:{}", delVO);
    	log.debug("delVO Get ID:{}", delVO.getHelpId());

    	int result = waahelpservice.delHelp(delVO);
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

    	return new IBSResultVO<WaaHelpVO>(delVO, result, resmsg, action);
    }
	
	
    @RequestMapping("/admin/sys/help/popup/helpPop.do")
	public String helpPop(WaaHelpVO searchvo, WaaHelpVO record, HttpServletRequest request, Model model) throws Exception {
		String helpId = request.getParameter("helpId");
		String replacedHelpCtnt;
		WaaHelpVO result;
		
		if(helpId!=null && !"".equals(helpId)) {
			record.setHelpId(helpId);
			result = waahelpservice.selectHelpCtnt(helpId);
			
		} else {
			result = record;
		}
		replacedHelpCtnt = result.getHelpCtnt();
		replacedHelpCtnt =replacedHelpCtnt.replace("\"", "\\\"");
		replacedHelpCtnt =replacedHelpCtnt.replace("\'", "\\\'");
		replacedHelpCtnt =replacedHelpCtnt.replace("\r", "\\");
		result.setHelpCtnt(replacedHelpCtnt);
		
		model.addAttribute("evalmst", result);
		return "/admin/sys/help/popup/helpPop";
	}
	

}
