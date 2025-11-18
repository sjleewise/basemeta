package kr.wise.commons.sysmgmt.pms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.menu.service.MenuManageService;
import kr.wise.commons.sysmgmt.pms.service.ProjectService;
import kr.wise.commons.sysmgmt.pms.service.ProjectVO;
import kr.wise.commons.util.UtilJson;

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


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProjectManagerCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.pms.web
 * 4. Comment  : 프로젝트 관리 컨터롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2014. 2. 1. 오전 11:30:09
 * </PRE>
 */ 
@Controller
public class ProjectManagerCtrl { 
	
	static class ProjectVOs extends HashMap<String, ArrayList<ProjectVO>> { }
	
	private final  Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ProjectService projectService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MenuManageService menuManageService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	
	/** 프로젝트 관리 메인 화면 */
	@RequestMapping("/commons/sys/pms/selectProject.do")
	public String selectProject(@ModelAttribute("searchVO") ProjectVO searchVO) {
		
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		
		return "/commons/sys/pms/project_lst";
		
	}
	
	
	/** 프로젝트 리스트 조회 - IBSheet */
	@RequestMapping("/commons/sys/pms/ajaxgrid/selectProjectList.do")
	@ResponseBody
	public IBSheetListVO<ProjectVO> selectProjectList( @ModelAttribute("searchVO") ProjectVO searchVO) {
		
		List<ProjectVO> list = projectService.selectProjectList(searchVO);
		
		return new IBSheetListVO<ProjectVO>(list, list.size());
	}
	
	/** 프로젝트 상세 조회 */
	@RequestMapping("/commons/sys/pms/ajaxgrid/selectProjectDetail.do")
	public String selectProjectDetail(ProjectVO searchVO, String saction, ModelMap model) {
		log.debug("searvhVO : {}", searchVO);
		
		model.addAttribute("saction", "I");
		if( StringUtils.hasLength(searchVO.getPrjId())) {
			ProjectVO result = projectService.selectProjectDetail(searchVO);
			
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		
		return "/commons/sys/pms/projectDetail";
	}
	
	/** 프로젝트 저장 
	 * @throws Exception */
	@RequestMapping("/commons/sys/pms/ajaxgrid/saveProject.do")
	@ResponseBody
	public IBSResultVO<ProjectVO> saveProject(@RequestBody ProjectVOs data, Locale locale) throws Exception {
		
		log.debug("{}", data);
		ArrayList<ProjectVO> list = data.get("data");
		int result = projectService.regProject(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<ProjectVO>(result, resmsg, action);
	}
	
	
	/** 프로젝트 삭제 */
	@RequestMapping("/commons/sys/pms/ajaxgrid/deleteProgram.do")
	@ResponseBody
	public IBSResultVO<ProjectVO> delList(@RequestBody ProjectVOs data, Locale locale) throws Exception {

		log.debug("{}", data);
		ArrayList<ProjectVO> list = data.get("data");

		int result = projectService.deleteProject(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<ProjectVO>(result, resmsg, action);
	}
	
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);



		return codeMap;
	}

}
