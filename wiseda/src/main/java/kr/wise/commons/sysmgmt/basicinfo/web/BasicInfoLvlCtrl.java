/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BasicInfoLvlCtrl.java
 * 2. Package : kr.wise.commons.sysmgmt.basicinfo.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 7. 오후 4:25:31
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 7. :            : 신규 개발.
 */
package kr.wise.commons.sysmgmt.basicinfo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.user.service.WaaUser;
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
 * 1. ClassName : 
 * 2. FileName  : BasicInfoLvlCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.basicinfo.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 8. 오전 10:56:18
 * </PRE>
 */ 
@Controller
public class BasicInfoLvlCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	static class WaaBscLvls extends HashMap<String, ArrayList<WaaBscLvl>> { }
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 기본정보 레벨관리 페이지 */
	@RequestMapping("/commons/sysmgmt/basicinfo/basicinfolvl_lst.do")
	public String FormPage() {
		return "/commons/sys/basicinfo/basicinfolvl_lst";
	}
	
	/** 기본정보 레벨 조회 -IBSheet json */
	@RequestMapping("/commons/sysmgmt/basicinfo/basicInfoLvlList.do")
	@ResponseBody
	public IBSheetListVO<WaaBscLvl> selectBasicLvlList(@ModelAttribute("searchVO") WaaBscLvl search) throws Exception {
		List<WaaBscLvl> list = basicInfoLvlService.selectBasicInfoLvlList(search);
		
		return new IBSheetListVO<WaaBscLvl>(list, list.size()); 
	}
	
	/** 기본정보 레벨 저장 */
	@RequestMapping("/commons/sysmgmt/basicinfo/basicInfoLvlReglist.do")
	@ResponseBody
	public IBSResultVO<WaaBscLvl> regList(@RequestBody WaaBscLvls data, Locale locale) throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaBscLvl> list = data.get("data");
		String resmsg = null;
		String action = null;
		int result = -1;
		
		logger.debug("List 사이즈 : "+list.size());
		
		for(int i=0; i<list.size(); i++) {
			
			if(list.get(i).getIbsStatus().equals("I") || list.get(i).getIbsStatus().equals("U")) {

				int bscCnt = basicInfoLvlService.getBscIdCnt(list.get(i));
				if(bscCnt > 1) {
					resmsg = "중복된 기본정보명이 있습니다. <br> 입력한 값을 확인해주세요";
	
					action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
					return new IBSResultVO<WaaBscLvl>(result, resmsg, action);
				}
			}
		}
		
//	    Set<String> set = new HashSet<String>();
//	      
//	    for(WaaBscLvl bsc : list) {
//	    	WaaBscLvl temp = bsc;
//	       set.add(temp.getBscId());
//	    }
//	    
//	    if(list.size() != set.size()) {
//
//	    	resmsg = "중복된 아이디가 있습니다. <br> 입력한 값을 확인해주세요";
//	        
//	        action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
//
//	        return new IBSResultVO<WaaBscLvl>(result, resmsg, action);         
//	    }
		
		result = basicInfoLvlService.regBasicInfoLvlList(list);
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaaBscLvl>(result, resmsg, action);
		
		
	}
	
	/** 기본정보 레벨 삭제 */
	@RequestMapping("/commons/sysmgmt/basicinfo/basicInfoLvlDellist.do")
	@ResponseBody
	public IBSResultVO<WaaBscLvl> delList(@RequestBody WaaBscLvls data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaBscLvl> list = data.get("data");
		
		int result = basicInfoLvlService.delBasicInfoLvlList(list);
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaBscLvl>(result, resmsg, action);
	}
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//공통코드
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));

		//기본정보레벨코드
		codeMap.put("bscLvlCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BSC_LVL_CD")));
		codeMap.put("bscLvlCd", cmcdCodeService.getCodeList("BSC_LVL_CD"));

		//목록성코드
		
		return codeMap;
	}
}
