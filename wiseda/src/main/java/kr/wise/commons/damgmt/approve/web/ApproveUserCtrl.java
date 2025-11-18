/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ApproveUserCtrl.java
 * 2. Package : kr.wise.commons.damgmt.approve.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 27. 오전 8:39:25
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 27. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.approve.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveUserService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.damgmt.approve.service.WaaAprgUser;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ApproveUserCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.approve.web
 * 4. Comment  : 결재그룹별 결재자를 관리한다.
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 27. 오전 8:39:25
 * </PRE>
 */
@Controller("ApproveUserCtrl")
@RequestMapping("/commons/damgmt/approve/*")
public class ApproveUserCtrl {

	static class WaaAprgUsers extends HashMap<String, ArrayList<WaaAprgUser>> { }
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private ApproveUserService approveUserService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codelistService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private RequestApproveService requestApproveService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	
	/** 결재자 관리 초기 화면 */
	@RequestMapping("approveuser_lst.do")
	public String formpage() {
		return "/commons/damgmt/approve/approveuser_lst";
	}
	
	

	/** 그룹별 결재자 리스트 조회 - IBSheet JSON */
	@RequestMapping("approvegroupuser_lst.do")
	@ResponseBody
	public IBSheetListVO<WaaAprgUser> selectList(@ModelAttribute WaaAprgUser search) {
		logger.debug("{}", search);
		List<WaaAprgUser> list = approveUserService.getListById(search.getAprgId());
		
		return new IBSheetListVO<WaaAprgUser>(list, list.size());
		
	}
	
	/** 결재자 리스트 grid_sub에서 조회 - IBSheet JSON */
	@RequestMapping("approvegroupuserSub_lst.do")
	@ResponseBody
	public IBSheetListVO<WaaAprgUser> selectListSub(@RequestParam("aprgId") String aprgId, @RequestParam("userNm") String userNm) {
		logger.debug("{}, {}", aprgId, userNm);
		List<WaaAprgUser> list = approveUserService.getListById(aprgId, userNm);
		
		return new IBSheetListVO<WaaAprgUser>(list, list.size());
	
	}
	
	
	
	/** 결재자 리스트 삭제 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("approveuserDelete.do")
	@ResponseBody
	public IBSResultVO<WaaAprgUser> delList(@RequestBody WaaAprgUsers data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaAprgUser> list = data.get("data");

		int result = approveUserService.delList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		//결재라인 추가 및 수정시 요청서별 결재자 일괄 삭제, 업데이트, 추가 처리...
		requestApproveService.updateAllbyLine();

		return new IBSResultVO<WaaAprgUser>(result, resmsg, action);

	}
 
	/** 결재자 리스트 등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("approveuserSave.do")
	@ResponseBody
	public IBSResultVO<WaaAprgUser> regList(@RequestBody WaaAprgUsers data, @RequestParam String aprgId, Locale locale) throws Exception {
		ArrayList<WaaAprgUser> list = data.get("data");
		int result = approveUserService.regList(list, aprgId);
		String resmsg;
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		//결재라인 추가 및 수정시 요청서별 결재자 일괄 삭제, 업데이트, 추가 처리...
		requestApproveService.updateAllbyLine();
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaAprgUser>(result, resmsg, action);

	}
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String usergrouplnm2 = UtilJson.convertJsonString(codelistService.getCodeList("userglnm"));
//		String usergrouplnm1 = UtilJson.convertJsonString(codelistService.getCodeListIBS("userglnm"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String usergTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("USERG_TYP_CD"));
		List<CodeListVo> approvegroup = codeListService.getCodeList(CodeListAction.approvegroup);
		
//		codeMap.put("userglnm", usergrouplnm2);
//		codeMap.put("userglnmibs", usergrouplnm1);
		//공통코드 - IBSheet Combo Code용
		codeMap.put("usergTypCdibs", usergTypCd);
		codeMap.put("regTypCdibs", regTypCd);

		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(approvegroup)));
		codeMap.put("aprgId", approvegroup);
		
		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));


		return codeMap;
	}
	
}
