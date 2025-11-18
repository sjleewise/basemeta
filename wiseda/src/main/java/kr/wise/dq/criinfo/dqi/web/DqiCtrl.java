/**
 * 0. Project  : NHIS DQMS 프로젝트
 *
 * 1. FileName : DqiCtrl.java
 * 2. Package : kr.wise.dq.criinfo.dqi.web
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 19. :            : 최초작성
 */
package kr.wise.dq.criinfo.dqi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.criinfo.dqi.service.DqiService;
import kr.wise.dq.criinfo.dqi.service.WahDqiVO;
import kr.wise.dq.criinfo.dqi.service.WamDqiVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DqiCtrl.java
 * 2. Package : kr.wise.dq.criinfo.dqi.web
 * 3. Comment : 데이터품질지표 Controllor
 * 4. 작성자  : shshin(신상현)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    shshin(신상현) : 2014. 3. 19. :            : 신규 개발.
 * </PRE>
 */
@Controller
public class DqiCtrl {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WamDqiVOs extends HashMap<String, ArrayList<WamDqiVO>> { }
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;	
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private DqiService dqiService;
	
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		codeMap = new HashMap<String, Object>();
		
		String vrfcTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP"));
		
		codeMap.put("vrfcTypibs", vrfcTypCd);
		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		
		return codeMap;
	}	
	
	@RequestMapping("/dq/criinfo/dqi/dqi_lst.do")
	public String formpage(@ModelAttribute("search") WamDqiVO search,String linkFlag,Model model) {
		logger.debug("search : {}",search);
        logger.debug("linkFlag : {}",linkFlag);
        model.addAttribute("linkFlag", linkFlag);
		// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/criinfo/dqi/dqi_lst";
	}
	
	@RequestMapping("/dq/criinfo/dqi/getDqiList.do")
	@ResponseBody
	public IBSheetListVO<WamDqiVO> getDqiList(@ModelAttribute WamDqiVO search, Locale locale) throws Exception {
		logger.debug("{}", search);
		//데이터품질지표의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DQI");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		
		List<WamDqiVO> list = dqiService.getDqiList(search);
		return new IBSheetListVO<WamDqiVO>(list, list.size());
	}
	
	/** 데이터품질지표 상세 조회 */
	
	@RequestMapping("/dq/criinfo/dqi/detail/getDqiDetail.do")
	public String getCtqDetail(String dqiId, Model model, Locale locale) {
		logger.debug(" {}", dqiId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dqiId)) {

			WamDqiVO result = (WamDqiVO) dqiService.getDqiDetail(dqiId);
			model.addAttribute("result", result);
		}
		
		return "/dq/criinfo/dqi/dqi_dtl";
	}
	
	
	
	@RequestMapping("/dq/criinfo/dqi/popup/dqi_pop.do")
	public String formPoppage(@ModelAttribute("search") WamDqiVO search, String sflag,  ModelMap model) {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	logger.debug("{}", search);
    	model.addAttribute("sflag", sflag);
		return "/dq/criinfo/dqi/popup/dqi_pop";
	}
	
	@RequestMapping("/dq/criinfo/dqi/getDqiPopList.do")
	@ResponseBody
	public IBSheetListVO<WamDqiVO> getDqiPopList(@ModelAttribute WamDqiVO search, Locale locale) throws Exception {
		
		logger.debug("{}", search);
		
		//데이터품질지표의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DQI");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		
		List<WamDqiVO> list = dqiService.getDqiList(search);
		
		return new IBSheetListVO<WamDqiVO>(list, list.size());
		
	}

	/* 이력조회 */
	@RequestMapping("/dq/criinfo/dqi/getDqiHstLst.do")
	@ResponseBody
	public IBSheetListVO<WahDqiVO> getDqiHstLst(String dqiId) {
		
		logger.debug("{}", dqiId);
		List<WahDqiVO> list = dqiService.getDqiHstLst(dqiId);
		
		return new IBSheetListVO<WahDqiVO>(list, list.size());
		
	}
}
