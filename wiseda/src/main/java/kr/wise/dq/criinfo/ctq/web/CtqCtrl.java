/**
 * 0. Project  : NHIS DQMS 프로젝트
 *
 * 1. FileName : CtqCtrl.java
 * 2. Package : kr.wise.dq.criinfo.ctq.web
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 19. :            : 최초작성
 */
package kr.wise.dq.criinfo.ctq.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.criinfo.ctq.service.CtqService;
import kr.wise.dq.criinfo.ctq.service.WahCtqVO;
import kr.wise.dq.criinfo.ctq.service.WamCtqVO;
import kr.wise.dq.criinfo.ctq.service.WamCtqWhereUsed;

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
 * 1. FileName : CtqCtrl.java
 * 2. Package : kr.wise.dq.criinfo.ctq.web
 * 3. Comment : 중요정보항목 Controllor
 * 4. 작성자  : shshin(신상현)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    shshin(신상현) : 2014. 3. 19. :            : 신규 개발.
 * </PRE>
 */
@Controller
public class CtqCtrl {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WamCtqVOs extends HashMap<String, ArrayList<WamCtqVO>> { }
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CtqService ctqService;
	
	@RequestMapping("/dq/criinfo/ctq/ctq_lst.do")
	public String formpage(@ModelAttribute("search") WamCtqVO search,String linkFlag,Model model) {
		logger.debug("search : {}",search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag", linkFlag);
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/criinfo/ctq/ctq_lst";
	}
	
	@RequestMapping("/dq/criinfo/ctq/getCtqList.do")
	@ResponseBody
	public IBSheetListVO<WamCtqVO> getCtqList(@ModelAttribute WamCtqVO search, Locale locale) throws Exception {
		
		logger.debug("{}", search);
		//중요정보항목의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("CTQ");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		List<WamCtqVO> list = ctqService.getCtqList(search);
		
		return new IBSheetListVO<WamCtqVO>(list, list.size());
		
	}
	
	
	/** 중요정보항목 상세 조회 */
	@RequestMapping("/dq/criinfo/ctq/detail/getCtqDetail.do")
	public String getCtqDetail(String ctqId, Model model, Locale locale) {
		logger.debug(" {}", ctqId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ctqId)) {

			WamCtqVO result = (WamCtqVO) ctqService.getCtqDetail(ctqId);
			model.addAttribute("result", result);
		}
		
		return "/dq/criinfo/ctq/ctq_dtl";
	}
	
	
	@RequestMapping("/dq/criinfo/ctq/popup/ctq_pop.do")
	public String formPoppage(@ModelAttribute("search")WamCtqVO search,String sflag,  ModelMap model) {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	logger.debug("{}", search);
    	model.addAttribute("sflag", sflag);
		return "/dq/criinfo/ctq/popup/ctq_pop";
	}
	
	@RequestMapping("/dq/criinfo/ctq/getCtqPopList.do")
	@ResponseBody
	public IBSheetListVO<WamCtqVO> getCtqPopList(@ModelAttribute WamCtqVO search, Locale locale) throws Exception {
		
		logger.debug("{}", search);
		//중요정보항목의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("CTQ");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		List<WamCtqVO> list = ctqService.getCtqList(search);
		
		return new IBSheetListVO<WamCtqVO>(list, list.size());
		
	}

	/* 이력조회 */
	@RequestMapping("/dq/criinfo/ctq/getCtqHstLst.do")
	@ResponseBody
	public IBSheetListVO<WahCtqVO> getCtqHstLst(String ctqId) {
		
		logger.debug("{}", ctqId);
		List<WahCtqVO> list = ctqService.getCtqHstLst(ctqId);
		
		return new IBSheetListVO<WahCtqVO>(list, list.size());
		
	}
	
	@RequestMapping("/dq/criinfo/ctq/getCtqWhereusedLst.do")
	@ResponseBody
	public IBSheetListVO<WamCtqWhereUsed> getCtqWhereusedLst(String ctqId) {
		
		logger.debug("{}", ctqId);
		List<WamCtqWhereUsed> list = ctqService.getCtqWhereusedLst(ctqId);
		
		return new IBSheetListVO<WamCtqWhereUsed>(list, list.size());
		
	}
}
