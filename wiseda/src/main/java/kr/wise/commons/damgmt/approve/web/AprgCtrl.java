/**
 * 0. Project  : WiseDA 프로젝트
 *
 * 1. FileName : DeptCtrl.java
 * 2. Package : kr.wise.common.dept
 * 3. Comment :
 * 4. 작성자  : (yhkim)김연호 주임
 * 5. 작성일  : 2014. 3. 13
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    김연호 	: 2013. 3. 13. 		: 신규 개발.
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
import kr.wise.commons.damgmt.approve.service.AprgService;
import kr.wise.commons.damgmt.approve.service.WaaAprg;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : AprgController
 * 2. Package  : kr.wise.common.Aprg
 * 3. Comment  :
 * 4. 작성자   : (yhkim)김연호 주임
 * 5. 작성일   : 2014. 3. 20.
 * </PRE>
 */
@Controller("AprgCtrl")
@RequestMapping("/commons/sys/aprg/*")
public class AprgCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

//	IBSJsonSearch ibsresult = new IBSJsonSearch();
//	IBSResult ibsres = new IBSResult();

	static class WaaAprgs extends HashMap<String, ArrayList<WaaAprg>> { }

	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private AprgService aprgService;

	@Inject
	private CodeListService codelistService;

	@Inject
	private MessageSource message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/** 결재그룹 관리 초기 화면 */
	@RequestMapping("aprg_lst.do")
	public String formpage() {
		return "/commons/damgmt/approve/aprg_lst";
	}
	


	/** 결재그룹 리스트 조회 - IBSheet JSON */
	@RequestMapping("aprgSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaAprg> selectList(@ModelAttribute WaaAprg search) {
		logger.debug("{}", search);
		List<WaaAprg> list = aprgService.getList(search);
		
		return new IBSheetListVO<WaaAprg>(list, list.size());
		
	}


	/** 결재그룹 리스트 등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("aprgReglist.do")
	@ResponseBody
	public IBSResultVO<WaaAprg> regList(@RequestBody WaaAprgs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaAprg> list = data.get("data");
		
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getIbsStatus().equals("I") || list.get(i).getIbsStatus().equals("U")) {
				int aprgCnt = aprgService.getAprgCnt(list.get(i));
				
				if(aprgCnt > 0) {
					int result = -1;
					String resmsg = "중복된 결재그룹이 있습니다. <br> 입력한 값을 확인해주세요";
	
					String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
					return new IBSResultVO<WaaAprg>(result, resmsg, action);
				}
			}
		}
		
		int result = aprgService.regList(list);
		String resmsg;
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaAprg>(result, resmsg, action);

	}

	/** 결재그룹 리스트 삭제 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("aprgDellist.do")
	@ResponseBody
	public IBSResultVO<WaaAprg> delList(@RequestBody WaaAprgs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaAprg> list = data.get("data");

		int result = aprgService.delList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaAprg>(result, resmsg, action);

	}


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String usergrouplnm2 = UtilJson.convertJsonString(codelistService.getCodeList("userglnm"));
//		String usergrouplnm1 = UtilJson.convertJsonString(codelistService.getCodeListIBS("userglnm"));
		List<CodeListVo> approvegroup = codelistService.getCodeList(CodeListAction.approvegroup);
//		String usergTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("USERG_TYP_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		//결재그룹 코드 리스트
		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(approvegroup)));
		codeMap.put("aprgId", approvegroup);
		
		
//		codeMap.put("userglnm", usergrouplnm2);
//		codeMap.put("userglnmibs", usergrouplnm1);
		//공통코드 - IBSheet Combo Code용
//		codeMap.put("usergTypCdibs", usergTypCd);
		codeMap.put("regTypCdibs", regTypCd);

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));


		return codeMap;
	}



}
