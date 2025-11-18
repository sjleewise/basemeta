/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : OrgController.java
 * 2. Package : kr.wise.meta.model
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오후 1:14:55
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.meta.admin.web;

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
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.admin.service.OrgService;
import kr.wise.meta.admin.service.WaaOrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : OrgController
 * 2. Package  : kr.wise.meta.model
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 15.
 * </PRE>
 */
/**
 * @author yeonho
 *
 */
/**
 * @author yeonho
 *
 */
/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : OrgCtrl.java
 * 3. Package  : kr.wise.meta.Orgarea.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 24. 오후 5:29:14
 * </PRE>
 */ 
@Controller("OrgController")
public class OrgCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private OrgService orgService;

	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService; 

	@Inject
	private MessageSource message;

	private Map<String, String> codeMap;

	static class WaaOrgs extends HashMap<String, ArrayList<WaaOrg>> {}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

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
	@ModelAttribute("codeMap")
	public Map<String, String> getcodeMap() {

		codeMap = new HashMap<String, String>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
		List<CodeListVo> sysarea = codelistService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(sysarea)));
				
		//공통코드
		String lecyDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("LECY_DCD"));
		codeMap.put("lecyDcdibs", lecyDcd);
		codeMap.put("lecyDcd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("LECY_DCD")));
		
		return codeMap;
	}


	/**
	 * <PRE>
	 * 1. MethodName : goOrgForm
	 * 2. Comment    : 주제영역 기본 화면...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return String
	 *   @return
	 * @throws Exception 
	 */
	/** 주제영역 관리페이지 (관리자) */
	@RequestMapping("/meta/admin/org_lst.do")
	public String goOrgForm() {
		
		return "/admin/sys/org_lst";
	}

	/**
	 * <PRE>
	 * 1. MethodName : getSujbList
	 * 2. Comment    : 주제영역 리스트 조회 for IBS
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return IBSJsonSearch
	 *   @param search
	 *   @param locale
	 *   @return
	 * @throws Exception 
	 */
	@RequestMapping("/meta/admin/getOrglist.do")
	@ResponseBody
	public IBSheetListVO<WaaOrg> getOrgList(@ModelAttribute WaaOrg search, Locale locale) throws Exception {
		
		List<WaaOrg> list = orgService.getOrgList(search);

		return new IBSheetListVO<WaaOrg>(list, list.size());

	}
	
	/**
	 * <PRE>
	 * 1. MethodName : regOrgList
	 * 2. Comment    : 주제영역 리스트 등록 for IBS
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 20.
	 * </PRE>
	 *   @return IBSResult
	 *   @param data
	 *   @param locale
	 *   @return
	 * @throws Exception 
	 */
	@RequestMapping("/meta/admin/regOrglist.do")
	@ResponseBody
	public IBSResultVO<WaaOrg> regOrgList(@RequestBody WaaOrgs data, Locale locale) throws Exception {

		logger.debug("data : {}", data);

		int result = -1;
		String resmsg = null;

		result = orgService.regOrgList(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			if(result == -5){
				result = -1;
				resmsg = "이미 존재하는 기관코드입니다.";
			} else{
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
				
			}
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaOrg>(result, resmsg, action);
	}

	@RequestMapping("/meta/admin/delOrglist.do")
	@ResponseBody
	public IBSResultVO<WaaOrg> delOrgList(@RequestBody WaaOrgs data, Locale locale) throws Exception {
		logger.debug("{}", data);

		int result = -1;
		String resmsg = null;

		result = orgService.delOrgList(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaOrg>(result, resmsg, action);
	}
		
}
