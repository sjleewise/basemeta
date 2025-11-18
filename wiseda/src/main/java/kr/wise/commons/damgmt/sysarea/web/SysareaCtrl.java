/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwSysareaController.java
 * 2. Package : kr.wise.cmvw.sysarea.controller
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 12. 오전 10:57:27
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 12. 		: 신규 개발.
 */
package kr.wise.commons.damgmt.sysarea.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.sysarea.service.DaSysareaService;
import kr.wise.commons.damgmt.sysarea.service.WaaSysArea;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;

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
 * 2. FileName  : SysareaCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.sysarea.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 29. 오전 11:13:36
 * </PRE>
 */ 
@Controller
//@RequestMapping("/cmvw/sysarea/*")
public class SysareaCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	static class WaaSysAreas extends HashMap<String, ArrayList<WaaSysArea>> { }
	
	@Inject
	private DaSysareaService sysareaService;
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService	cmcdCodeService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 사용자 팝업 화면 */
	@RequestMapping("/commons/damgmt/sysarea/popup/userlst_pop.do")
	public String goUserSearchPop(@ModelAttribute("search") WaaUser search, Model model, @RequestParam(value="row") String row, Locale locale) {
		
		model.addAttribute("row", row);
		return "/commons/damgmt/sysarea/popup/userlst_pop";
	}
	
	
	/** 시스템 영역 뷰 페이지 이동 */
	@RequestMapping("/commons/damgmt/sysarea/sysarea_lst.do")
	public String formpage() {
		return "/commons/damgmt/sysarea/sysarea_lst";
	}
	
	/** 시스템 영역 리스트 조회 - IBSheet JSON 으로 리턴... */
	@RequestMapping("/commons/damgmt/sysarea/selectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaSysArea> selectList(@ModelAttribute WaaSysArea search) {
		logger.debug("{}", search);
		List<WaaSysArea> list = sysareaService.getSysareaList(search);
		
		
		return new IBSheetListVO<WaaSysArea>(list, list.size());

	}
	
	
	/** 시스템 영역 리스트 저장 - IBSheet JSON  */
	@RequestMapping("/commons/damgmt/sysarea/reglist.do")
	@ResponseBody
	public IBSResultVO<WaaSysArea> regList(@RequestBody WaaSysAreas data, Locale locale)  {
		
		logger.debug("{}", data);
		ArrayList<WaaSysArea> list = data.get("data");
		
		int result;
		String resmsg ;
		
		try {
			result = sysareaService.regSysareaList(list);
	
	    	if(result > 0) {
	    		result = 0;
	    		resmsg = message.getMessage("MSG.SAVE", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}
		} catch (Exception e) {
			logger.error("{}", e);
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaaSysArea>(result, resmsg, action);
	}

	
	/** 시스템영역 삭제 이력이 있을 경우 기간 만료 후 삭제 데이터 추가...- IBSheet 용... 
	 * @throws Exception */
	@RequestMapping("/commons/damgmt/sysarea/delsysarealistu.do")
	@ResponseBody
	public IBSResultVO<WaaSysArea> delSysareaListu(@RequestBody WaaSysAreas data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaSysArea> list = data.get("data");
		
		int result = sysareaService.delSysareaList(list);
		
		String resmsg ;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}else if(result == -2) {
			result = -1;
			resmsg = message.getMessage("ERR.INUSESUBJ", null, locale);
		}else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		
		return new IBSResultVO<WaaSysArea>(result, resmsg, action);
	}	

	/** 시스템영역 삭제 - IBSheet 용... */
    @RequestMapping("/commons/damgmt/sysarea/delsysarealist.do")
    @ResponseBody
    public IBSResultVO<WaaSysArea> delSysareaList(WaaSysArea saveVO, Locale locale) {
    	
    	logger.debug("saveVO:{}", saveVO);
    	
    	int result = sysareaService.delsysarealist(saveVO);
    	
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
    	
    	return new IBSResultVO<WaaSysArea>(result, resmsg, action);
    }	
	
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드
		List<CodeListVo> userglist = codelistService.getCodeList(WiseMetaConfig.CodeListAction.usergroup);
		String usergroup1 = UtilJson.convertJsonString(codelistService.getCodeListIBS(userglist));
		codeMap.put("userglist", userglist);
		codeMap.put("usergp", usergroup1);

		//공통코드
		String lecyDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("LECY_DCD"));
		codeMap.put("lecyDcdibs", lecyDcd);
		

		return codeMap;
	}
    
	

}
