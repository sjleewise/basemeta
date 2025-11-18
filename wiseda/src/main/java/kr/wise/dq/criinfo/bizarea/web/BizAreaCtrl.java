/**
 * 0. Project  : NHIS DQMS 프로젝트
 *
 * 1. FileName : BizAreaCtrl.java
 * 2. Package : kr.wise.dq.criinfo.bizarea.web
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 19. :            : 최초작성
 */
package kr.wise.dq.criinfo.bizarea.web;

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
import kr.wise.dq.criinfo.bizarea.service.BizAreaService;
import kr.wise.dq.criinfo.bizarea.service.WahBizAreaVO;
import kr.wise.dq.criinfo.bizarea.service.WamBizAreaVO;

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
 * 1. FileName : BizAreaCtrl.java
 * 2. Package : kr.wise.dq.criinfo.bizarea.web
 * 3. Comment : 업무영역 Controllor
 * 4. 작성자  : shshin(신상현)
 * 5. 작성일  : 2014. 3. 19. 오후 5:33:27
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    shshin(신상현) : 2014. 3. 19. :            : 신규 개발.
 *                    meta(김연호) : 2014. 5. 08. : 			 : 추가 변경
 * </PRE>
 */
@Controller
public class BizAreaCtrl {
	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WamBizAreaVOs extends HashMap<String, ArrayList<WamBizAreaVO>> { }

	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codelistService;

	@Inject
	private BizAreaService bizAreaService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	@RequestMapping("/dq/criinfo/bizarea/bizarea_lst.do")
	public String formpage(@ModelAttribute("search") WamBizAreaVO search,String linkFlag,Model model) {
        logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);
		
		// 0. Spring Security 사용자권한 처리
		logger.debug("search : {}",search);
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/criinfo/bizarea/bizarea_lst";
	}

	@RequestMapping("/dq/criinfo/bizarea/getBizAreaList.do")
	@ResponseBody
	public IBSheetListVO<WamBizAreaVO> getBizAreaList(@ModelAttribute WamBizAreaVO search, Locale locale) throws Exception {

		logger.debug("{}", search);
		
		//업무영역의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("BZA");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		
		List<WamBizAreaVO> list = bizAreaService.getBizAreaList(search);

		return new IBSheetListVO<WamBizAreaVO>(list, list.size());

	}
	
	@RequestMapping("/dq/criinfo/bizarea/getBizAreaListPop.do")
	@ResponseBody
	public IBSheetListVO<WamBizAreaVO> getBizAreaListPop(@ModelAttribute WamBizAreaVO search, Locale locale) throws Exception {

		logger.debug("{}", search);
		
		//업무영역의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("BZA");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		
		List<WamBizAreaVO> list = bizAreaService.getBizAreaListPop(search);

		return new IBSheetListVO<WamBizAreaVO>(list, list.size());

	}

	/** 업무영역 상세 조회 */
	@RequestMapping("/dq/criinfo/bizarea/detail/getBizAreaDetail.do")
	public String getBizAreaDetail(String bizAreaId, Model model, Locale locale) {
		logger.debug(" {}", bizAreaId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(bizAreaId)) {

			WamBizAreaVO result = bizAreaService.getBizAreaDetail(bizAreaId);
			model.addAttribute("result", result);
		}

		return "/dq/criinfo/bizarea/bizarea_dtl";
	}


	@RequestMapping("/dq/criinfo/bizarea/popup/bizarea_pop.do")
	public String formPoppage(@ModelAttribute("search") WamBizAreaVO search, String sflag, ModelMap model) {
		logger.debug("{}", search);
		
    	model.addAttribute("sflag", sflag);
    	logger.debug(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   sflag     ", sflag);

		return "/dq/criinfo/bizarea/popup/bizarea_pop";
	}

	@RequestMapping("/dq/criinfo/bizarea/getBizAreaPopList.do")
	@ResponseBody
	public IBSheetListVO<WamBizAreaVO> getBizAreaPopList(@ModelAttribute WamBizAreaVO search, Locale locale) throws Exception {

		logger.debug("{}", search);
		
		//업무영역의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("BZA");
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<data.getBscLvl(); i++) {
			lvlList.add(i+1);
		}
		search.setLvlList(lvlList);
		List<WamBizAreaVO> list = bizAreaService.getBizAreaList(search);

		return new IBSheetListVO<WamBizAreaVO>(list, list.size());

	}

	/* 이력조회 */
	@RequestMapping("/dq/criinfo/bizarea/getBizAreaHstLst.do")
	@ResponseBody
	public IBSheetListVO<WahBizAreaVO> getBizAreaHstLst(String bizAreaId) {

		logger.debug("{}", bizAreaId);
		List<WahBizAreaVO> list = bizAreaService.getBizAreaHstLst(bizAreaId);

		return new IBSheetListVO<WahBizAreaVO>(list, list.size());

	}
}
