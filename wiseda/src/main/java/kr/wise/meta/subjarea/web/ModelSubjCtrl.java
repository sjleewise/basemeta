/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelSubjController.java
 * 2. Package : kr.wise.meta.model
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오후 1:14:55
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.meta.subjarea.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.subjarea.service.ModelSubjService;
import kr.wise.meta.subjarea.service.WaaSubj;

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

/**
 * <PRE>
 * 1. ClassName : ModelSubjController
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
 * 2. FileName  : ModelSubjCtrl.java
 * 3. Package  : kr.wise.meta.subjarea.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 24. 오후 5:29:14
 * </PRE>
 */ 
@Controller("ModelSubjController")
public class ModelSubjCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private ModelSubjService modelSubjService;

	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService; 
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	@Inject
	private MessageSource message;

//	private IBSResult ibsRes = new IBSResult();
//
//	private IBSJsonSearch ibsJson = new IBSJsonSearch();

	private Map<String, String> codeMap;

	static class WaaSubjs extends HashMap<String, ArrayList<WaaSubj>> {}

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

		String dbmsTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCdibs);
		
		String stndAsrt = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT"));
		codeMap.put("stndAsrtibs", stndAsrt);
		codeMap.put("stndAsrt", UtilJson.convertJsonString(cmcdCodeService.getCodeList("STNDASRT")));

		//등록유형코드
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCdibs);
				
//		//주제영역권한쪽
		List<CodeListVo> subjLnmByLVL2 = codelistService.getCodeList("subjLnmByLVL2");
		codeMap.put("subjLnmByLVL2", UtilJson.convertJsonString(subjLnmByLVL2));
		codeMap.put("subjLnmByLVL2ibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(subjLnmByLVL2)));
//		
		List<CodeListVo> SubjLstByLvl0Lvl1 = codelistService.getCodeList("SubjLstByLvl0Lvl1");
		codeMap.put("SubjLstByLvl0Lvl1", UtilJson.convertJsonString(SubjLstByLvl0Lvl1));
		codeMap.put("SubjLstByLvl0Lvl1ibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(SubjLstByLvl0Lvl1)));
		
		return codeMap;
	}


	/**
	 * <PRE>
	 * 1. MethodName : gosubjFrom
	 * 2. Comment    : 주제영역 기본 화면...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return String
	 *   @return
	 * @throws Exception 
	 */
	/** 주제영역 관리페이지 (관리자) */
	@RequestMapping("/commons/damgmt/subjarea/subj_lst.do")
	public String gosubjFrom() {
		
//		return "/meta/subjarea/subj_lst"; // tiles.xml 레이아웃 추가하기 번거로우므로 파일 경로를 변경.
		return "/commons/damgmt/subjarea/subj_lst";
	}
	
	
	/** 메타용 조회페이지 */
	/** @return yeonho */
	@RequestMapping("/meta/model/metasubj_lst.do")
	public String goMetaSubjForm(@ModelAttribute("search")WaaSubj search ) {
		logger.debug("search : {}",search);
		return "/meta/subjarea/metasubj_lst";
	}

	@RequestMapping("/meta/subjarea/popup/subjSearchPop.do")
	public String gosubjSearchPop(@ModelAttribute("search") WaaSubj search, String sFlag, Model model, Locale locale) {
		logger.debug("search:{}", search);
		logger.debug("sFlag : {}", sFlag);
//		ModelAndView mv = new ModelAndView();
		if(!UtilString.isBlank(search.getSubjLnm())) {
			model.addAttribute("subjLnm" , search.getSubjLnm());

//			mv.addObject("subjLnm", search.getSubjLnm());
		}
//		mv.setViewName("/meta/model/subjSearchPop");

//		return mv;
		model.addAttribute("sFlag", sFlag);

		return "/meta/model/popup/subjSearchPop";
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
	@RequestMapping("/meta/subjarea/getsubjlist.do")
	@ResponseBody
	public IBSheetListVO<WaaSubj> getSubjList(@ModelAttribute WaaSubj search, Locale locale) throws Exception {
		
		logger.debug("{}", search);
		
		//주제영역의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("SUBJ");
				
		logger.debug("{}", bscLvl);
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=1; i<=bscLvl.getBscLvl(); i++){ // 1부터up
			lvlList.add(i);
		}
		
		search.setLvlList(lvlList);
		
		
		List<WaaSubj> list = modelSubjService.getSubjList(search);

		return new IBSheetListVO<WaaSubj>(list, list.size());

	}
	
	/**
	 * <PRE>
	 * 1. MethodName : getSubjListL2
	 * 2. Comment    : 주제영역 리스트 조회 for IBS _ 레벨2까지
	 * 3. 작성자       : 
	 * 4. 작성일       : 
	 * </PRE>
	 *   @return IBSJsonSearch
	 *   @param search
	 *   @param locale
	 *   @return
	 * @throws Exception 
	 */
	@RequestMapping("/meta/subjarea/getsubjlistl2.do")
	@ResponseBody
	public IBSheetListVO<WaaSubj> getSubjListL2(@ModelAttribute WaaSubj search, Locale locale) throws Exception {
		
		logger.debug("{}", search);
		
		//주제영역의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("SUBJ");
		
		logger.debug("{}", bscLvl);
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=1; i<=bscLvl.getBscLvl()-1; i++){ // 1부터up 3단계인경우 2단계 2단계인경우 1단계..
			lvlList.add(i);
		}
		
		search.setLvlList(lvlList);
		
		List<WaaSubj> list = modelSubjService.getSubjList(search);
		
		return new IBSheetListVO<WaaSubj>(list, list.size());
		
	}

	/** 메타용 주제영역 리스트 조회 */
	/** @param search
	/** @param locale
	/** @return yeonho 
	 * @throws Exception */
	@RequestMapping("/meta/model/getMetaSubjList.do")
	@ResponseBody
	public IBSheetListVO<WaaSubj> getMetaSubjList(@ModelAttribute WaaSubj search, Locale locale) throws Exception {

		logger.debug("{}", search);
		
		//주제영역의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("SUBJ");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=1; i<=bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
		
		search.setLvlList(lvlList);
		
		List<WaaSubj> list = modelSubjService.getMetaSubjList(search);

		return new IBSheetListVO<WaaSubj>(list, list.size());

	}
	
	/**
	 * <PRE>
	 * 1. MethodName : regSubjList
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
	@RequestMapping("/meta/subjarea/regsubjlist.do")
	@ResponseBody
	public IBSResultVO<WaaSubj> regSubjList(@RequestBody WaaSubjs data, Locale locale) throws Exception {

		logger.debug("data : {}", data);

		int result = -1;
		String resmsg = null;

		result = modelSubjService.regSubjList(data.get("data"));
		
		//주제영역의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("SUBJ");
		

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -5) {
			result = -1;
			resmsg = message.getMessage("ERR.BSCLVL", new Object[]{"주제영역",bscLvl.getBscLvl()}, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaSubj>(result, resmsg, action);
	}

	@RequestMapping("/meta/subjarea/delsubjlist.do")
	@ResponseBody
	public IBSResultVO<WaaSubj> delSubjList(@RequestBody WaaSubjs data, Locale locale) throws Exception {
		logger.debug("{}", data);

		int result = -1;
		String resmsg = null;
		
		result = modelSubjService.delSubjList(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaSubj>(result, resmsg, action);
	}

	
	//여기부터 주제영역 권한 추가 190425

	//주제영역권한
	 @RequestMapping("/meta/subj/subjownerlst.do")
	    public String goSubjOwnerPage(String linkFlag,Model model) {
	    	//logger.debug("linkFlag : {}",linkFlag);
	    	model.addAttribute("linkFlag",linkFlag);
	    	return "/meta/subjarea/subj_owner_lst";
	    }
	 
	 @RequestMapping("/meta/subjarea/getsubjOnwerlist.do")
		@ResponseBody
		public IBSheetListVO<WaaSubj> getsubjOnwerlist(@ModelAttribute WaaSubj search) {
			//logger.debug("{}", search);
			List<WaaSubj> list = modelSubjService.getsubjOnwerlist(search);
			return new IBSheetListVO<WaaSubj>(list, list.size());
		}
		
		/** 사용자 상세 리스트 조회 - IBSheet JSON */
		@RequestMapping("/meta/subjarea/getsubjOnwerDetList.do")
		@ResponseBody
		public IBSheetListVO<WaaSubj> getsubjOnwerDetList(@ModelAttribute WaaSubj search) {
			//logger.debug("{}", search);
			
			List<WaaSubj> list = modelSubjService.getsubjOnwerDetList(search);
			
			return new IBSheetListVO<WaaSubj>(list, list.size());
		}
		
		/** 주제영역오너쉽 관리페이지 (관리자) */
		@RequestMapping("/commons/damgmt/subjarea/subjonwermng.do")
		public String gosubjOnwerFrom() {
			return "/commons/damgmt/subjarea/subj_owner_mng";
		}
		
		@RequestMapping("/meta/subjarea/regsubjonwerlist.do")
		@ResponseBody
		public IBSResultVO<WaaSubj> regSubjOnwerList(@RequestBody WaaSubjs data, Locale locale) throws Exception {
			//logger.debug("data : {}", data);
			int result = -1;
			String resmsg = null;
			
			result = modelSubjService.regSubjOwnerList(data.get("data"));
			
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
			
			//erwin 권한 추가 전달
//			if(result==0){
//				List<WaaSubj> list = data.get("data");  
//				for (WaaSubj record : list) {
//					String subjLnm = record.getSubjLnm();
//					
//					String cResult = "";					
//					MartUserVO martUserVO = new MartUserVO();
//					martUserVO.setUsername("ebaykorea\\"+record.getUserId());
//					martUserVO.setUsertype("1");
//					martUserVO.setSubjectpath(UtilApi.getSubjectpath(subjLnm)); 	// 주제영역 경로		    	
//					martUserVO.setRolename("Modeler");
//					martUserVO.setcResult("");						
//					
//					// 권한추가
//					cResult = pt01Service.addProfileassignment(martUserVO);
//					logger.debug("cResult : "+ cResult);
//				}
//			}
			
			String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
			return new IBSResultVO<WaaSubj>(result, resmsg, action);
		}
		@RequestMapping("/meta/subjarea/delSubjOwnerList.do")
		@ResponseBody
		public IBSResultVO<WaaSubj> delSubjOwnerList(@RequestBody WaaSubjs data,  Locale locale) throws Exception {
			//logger.debug("data : {}", data);
			int result = -1;
			String resmsg = null;
			
			result = modelSubjService.delSubjOwnerList(data.get("data"));
			
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
			
			//erwin 권한 제거 전달
//			if(result==0){
//				List<WaaSubj> list = data.get("data");  
//				for (WaaSubj record : list) {
//					String subjLnm = record.getSubjLnm();
//					
//					String cResult = "";					
//					MartUserVO martUserVO = new MartUserVO();
//					martUserVO.setUsername("ebaykorea\\"+record.getUserId());
//					martUserVO.setUsertype("1");
//					martUserVO.setSubjectpath(UtilApi.getSubjectpath(subjLnm)); 	// 주제영역 경로		    	
//					martUserVO.setRolename("Modeler");
//					martUserVO.setcResult("");						
//					
//					// 권한제거
//					cResult = pt01Service.delProfileassignment(martUserVO);
//					logger.debug("cResult : "+ cResult);
//				}
//			}			
			
			String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
			return new IBSResultVO<WaaSubj>(result, resmsg, action);
		}
		
	//관심 주제영역권한
	 @RequestMapping("/meta/subj/subjFavoritelst.do")
	    public String goSubjFavoritePage(String linkFlag,Model model) {
	    	//logger.debug("linkFlag : {}",linkFlag);
	    	model.addAttribute("linkFlag",linkFlag);
	    	return "/meta/subjarea/subj_favorite_lst";
	    }
	 
	@RequestMapping("/meta/subjarea/getsubjFavoritelist.do")
	@ResponseBody
	public IBSheetListVO<WaaSubj> getsubjFavoritelist(@ModelAttribute WaaSubj search) {
		//logger.debug("{}", search);
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();		
		search.setUserId(userid);// 본인대상 검색
		
		List<WaaSubj> list = modelSubjService.getsubjFavoritelist(search);
		return new IBSheetListVO<WaaSubj>(list, list.size());
	}	 
	
	/***
	 * 관심주제영역 등록
	 * @param data
	 * @param reqmst
	 * @param locale
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/meta/subjarea/subjFavoritelist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> subjFavoritelist(@RequestBody WaaSubjs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaaSubj> list = data.get("data");

		int result = modelSubjService.registerSubjFavorite(reqmst, list);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}	
    
    @RequestMapping("/meta/subjarea/delsubjFavoritelist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delsubjFavoritelist(@RequestBody WaaSubjs data, WaqMstr reqmst, Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaaSubj> list = data.get("data");

		int result = modelSubjService.delSubjFavorite(reqmst, list);
		
		String resmsg;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		//reqmst = requestMstService.getrequestMst(reqmst);
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}    

    /***
     * 관심주제영역 기존입력여부 체크
     * @param data
     * @param locale
     * @return
     * @throws Exception
     */
	@RequestMapping("/meta/subj/subjFavoriteCntBySubjId.do")
	@ResponseBody
	public Integer subjFavoriteCntBySubjId(WaaSubj search, Locale locale) throws Exception {
	
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();		
		search.setUserId(userid);// 본인대상 검색		
		int result = modelSubjService.subjFavoriteCntBySubjId(search);
		
		return result;
	}
	
	

		
}
