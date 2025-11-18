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
package kr.wise.commons.sysmgmt.dept.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.dept.service.DeptService;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;

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
import org.springframework.web.servlet.ModelAndView;

/**
 * <PRE>
 * 1. ClassName : DeptController
 * 2. Package  : kr.wise.common.dept
 * 3. Comment  :
 * 4. 작성자   : (yhkim)김연호 주임
 * 5. 작성일   : 2014. 3. 13.
 * </PRE>
 */
@Controller("DeptCtrl")
@RequestMapping("/commons/sys/dept/*")
public class DeptCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

//	IBSJsonSearch ibsresult = new IBSJsonSearch();
//	IBSResult ibsres = new IBSResult();

	static class WaaDepts extends HashMap<String, ArrayList<WaaDept>> { }

	private Map<String, Object> codeMap;
	@Inject
	private DeptService deptService;

	@Inject
	private CodeListService codelistService;

	@Inject
	private MessageSource message;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/** 사용자 관리 화면 */
	@RequestMapping("dept_lst.do")
	public String formpage() {
		return "/commons/sys/dept/dept_lst";
	}
	


	/** 사용자 팝업 화면 */
	@RequestMapping("pop/userSearchPop.do")
	public String gosubjSearchPop(@ModelAttribute WaaUser search, Model model, Locale locale) {
		logger.debug("{}", search);

		ModelAndView mv = new ModelAndView();
		if(!UtilString.isBlank(search.getUserId())) {
			model.addAttribute("userId" , search.getUserId());
		}
		return "/commons/user/userSearchPop";
	}


	/** 사용자 리스트 조회 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("deptSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaDept> selectList(@ModelAttribute WaaDept search) throws Exception {
		logger.debug("{}", search);
		
		//부서의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("DEPT");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
				
		search.setLvlList(lvlList);
		
		List<WaaDept> list = deptService.getList(search);
		
		return new IBSheetListVO<WaaDept>(list, list.size());
	}


	/** 사용자 리스트 등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("deptReglist.do")
	@ResponseBody
	public IBSResultVO<WaaDept> regList(@RequestBody WaaDepts data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDept> list = data.get("data");
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getIbsStatus().equals("I") || list.get(i).getIbsStatus().equals("U")){
				int deptCnt = deptService.getDeptCnt(list.get(i));
				
				if(deptCnt > 0){
					int result = -1;
					
					String resmsg = "중복된 부서명이 있습니다. <br> 입력한 값을 확인해주세요";

			        String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

			        return new IBSResultVO<WaaDept>(result, resmsg, action);  
				}
			}
		}
		
		int result = deptService.regList(list);
		
		//부서의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("DEPT");
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -5) {
			result = -1;
			resmsg = message.getMessage("ERR.BSCLVL", new Object[]{"부서",bscLvl.getBscLvl()}, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDept>(result, resmsg, action);
	}

	/** 사용자 리스트 삭제 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("deptDellist.do")
	@ResponseBody
	public IBSResultVO<WaaDept> delList(@RequestBody WaaDepts data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDept> list = data.get("data");

		int result = deptService.delList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaDept>(result, resmsg, action);
	}


	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");

		List<CodeListVo> userglist = codelistService.getCodeList("usergroup");
//		String usergroup2 = UtilJson.convertJsonString(codelistService.getCodeList("usergroup"));
		String usergroup1 = UtilJson.convertJsonString(codelistService.getCodeListIBS(userglist));
//		codeMap.put("usergroup", usergroup2);
		codeMap.put("userglist", userglist);
		codeMap.put("usergp", usergroup1);


		return codeMap;
	}



}
