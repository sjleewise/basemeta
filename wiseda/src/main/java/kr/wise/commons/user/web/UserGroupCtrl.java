/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UserGroupCtrl.java
 * 2. Package : kr.wise.cmvw.user.controller
 * 3. Comment :
 * 4. 작성자  : 유연철
 * 5. 작성일  : 2013. 4. 20
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 20. 		: 신규 개발.
 */
package kr.wise.commons.user.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.user.WaaUserg;
import kr.wise.commons.user.service.UserGroupService;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : CmvwSysareaController
 * 2. Package  : kr.wise.cmvw.user.controller
 * 3. Comment  :
 * 4. 작성자   : 유연철
 * 5. 작성일   : 2013. 4. 21.
 * </PRE>
 */
@Controller
@RequestMapping("/commons/user/*")
public class UserGroupCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

//	IBSJsonSearch ibsresult = new IBSJsonSearch();
//	IBSResult ibsres = new IBSResult();

	static class WaaUsergs extends HashMap<String, ArrayList<WaaUserg>> { }

	private Map<String, Object> codeMap;


	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private UserGroupService userGroupService;

	@Inject
	private MessageSource message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("usergroup_lst.do")
	public String formpage() {
		return "/commons/user/usergroup_lst";
	}

	@RequestMapping("usergSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUserg> selectList(@ModelAttribute WaaUserg search) {
		logger.debug("{}", search);
		List<WaaUserg> list = userGroupService.getUsergList(search);


		return new IBSheetListVO<WaaUserg>(list, list.size());
	}

	@RequestMapping("usergReglist.do")
	@ResponseBody
	public IBSResultVO<WaaUserg> regList(@RequestBody WaaUsergs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaUserg> list = data.get("data");
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getIbsStatus().equals("I") || list.get(i).getIbsStatus().equals("U")){
				int usergCnt = userGroupService.getUsergCnt(list.get(i));
				
				if(usergCnt > 0){
					int result = -1;
					String resmsg = "중복된 사용자그룹이 있습니다. <br> 입력한 값을 확인해주세요";
					
					String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
					
					return new IBSResultVO<WaaUserg>(result, resmsg, action);
				}
			}
		}
		
		int result = userGroupService.regUsergList(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);

		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaUserg>(result, resmsg, action);
	}

	@RequestMapping("usergDellist.do")
	@ResponseBody
	public IBSResultVO<WaaUserg> delList(@RequestBody WaaUsergs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaUserg> list = data.get("data");

		int result = userGroupService.delUsergList(list);

		String resmsg;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaUserg>(result, resmsg, action);
	}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//공통코드 - IBSheet Combo Code용
		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));
		codeMap.put("usergTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("USERG_TYP_CD")));
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));

		return codeMap;
	}



}
