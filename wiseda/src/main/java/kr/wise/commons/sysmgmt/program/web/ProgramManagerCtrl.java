package kr.wise.commons.sysmgmt.program.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.helper.grid.JqGridPageVO;
import kr.wise.commons.helper.grid.JqGridVO;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageService;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ProgramManagerCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.program.web
 * 4. Comment  : 프로그램 정보를 관리하는 컨트롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 11. 29. 오후 4:56:55
 * </PRE>
 */
@Controller
@RequestMapping("/commons/sys/program")
public class ProgramManagerCtrl {

	private final  Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private ProgrmManageService progrmManageService;

	@Inject
	private MessageSource message;

	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class ProgrmManageVOs extends HashMap<String, ArrayList<ProgrmManageVO>> { }

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

    /**
     * <PRE>
     * 1. MethodName : selectProgrmList
     * 2. ClassName  : ProgramManagerCtrl
     * 3. Comment   : 프로그램 메인화면
     * 4. 작성자    : insomnia(장명수)
     * 5. 작성일    : 2013. 11. 29. 오후 5:06:05
     * </PRE>
     *   @return String
     *   @param searchVO
     *   @param model
     *   @return
     *   @throws Exception
     */
    @RequestMapping("/selectProgramList.do")
    public String selectProgrm(	@ModelAttribute("searchVO") SearchVO searchVO,	ModelMap model)     throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	// 내역 조회
    	/** EgovPropertyService.sample */
//    	searchVO.setPageUnit(WiseConfig.PAGEUNIT);
//    	searchVO.setPageSize(WiseConfig.PAGESIZE);

    	/** pageing */
/*    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list", list_progrmmanage);
        model.addAttribute("searchVO", searchVO);

        int totCnt = progrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);*/

//      	return "/commons/sys/program/programList";
      	return "/commons/sys/program/program_lst";

    }


    /** 프로그램 리스트 조회 - jqgrid */
    @RequestMapping("/ajaxgrid/selectProgramList.do")
    @ResponseBody
    public JqGridListVO<ProgrmManageVO> selectProgramList(ProgrmManageVO searchVO, JqGridPageVO pageVO)     throws Exception {
    	//_search=false&nd=1387270885005&rows=20&page=1&sidx=progrmFileNm&sord=desc

//    	log.debug("page info : {}", pageVO);

    	// 내역 조회
    	/** 페이지당 레코드 수 */
//    	int rows = pageVO.getRows(); //페이지당 레코드 수
//    	int page = pageVO.getPage(); //현재 페이지

//    	searchVO.setPageUnit(rows);
//    	searchVO.setFirstIndex((page-1) * rows); 		//첫번째 레코드 순번
//    	searchVO.setRecordCountPerPage(rows);	//마지막 레코드 순번
    	//searchVO.setPageSize(WiseConfig.PAGESIZE);		//페이지 사이즈

    	List list_progrmmanage = progrmManageService.selectProgrmList(searchVO);

    	// 총 건수
//    	int totalSize = progrmManageService.selectProgrmListTotCnt(searchVO);

    	// Paging 관련 연산
    	/*int pageSize = 0;
    	if (totalSize != 0) {
    		if ((totalSize % rows) == 0) {
    			pageSize = (totalSize / rows);
    		} else {
    			pageSize = (totalSize / rows + 1);
    		}
    	}*/

//    	log.debug("json result : {}", list_progrmmanage);

    	/** (레코드 리스트, 현재페이지, 전체페이지 사이즈, 조회레코드 수) **/
//    	return new JqGridListVO<ProgrmManageVO>(list_progrmmanage, page, pageSize, totalSize);
    	return new JqGridListVO<ProgrmManageVO>(list_progrmmanage);
    }

	/** 프로그램 리스트 조회-IBSheet Json */
	@RequestMapping("/selectProgramListIBS.do")
	@ResponseBody
	public IBSheetListVO<ProgrmManageVO> selectProgramListIBS(ProgrmManageVO searchVO) {
		logger.debug("loginLog : {}", searchVO);

		List<ProgrmManageVO> resultlist = progrmManageService.selectProgrmList(searchVO);

		return new IBSheetListVO<ProgrmManageVO>(resultlist, resultlist.size());
	}

	/** 프로그램 단건 조회-IBSheet Json */
	@RequestMapping("/selectProgramRow.do")
	@ResponseBody
	public IBSheetListVO<ProgrmManageVO> selectProgramRow(ProgrmManageVO searchVO) {
		logger.debug("loginLog : {}", searchVO);

		List<ProgrmManageVO> resultlist = progrmManageService.selectProgrmRow(searchVO);

		return new IBSheetListVO<ProgrmManageVO>(resultlist, resultlist.size());
	}

    /** 프로그램 상세 정보 */
    @RequestMapping("/ajaxgrid/selectProgramDetail.do")
    public String selectProgramDetail(ProgrmManageVO searchVO, String saction,  ModelMap model) {

    	logger.debug("searvhVO : {}", searchVO);

    	model.addAttribute("saction", "I");

    	if( StringUtils.hasLength(searchVO.getProgrmFileNm()) ){

    		ProgrmManageVO resultVO = progrmManageService.selectProgrmDetail(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}

//    	return "/commons/sys/program/programDetail";
    	return "/commons/sys/program/program_dtl";
    }

    /** 프로그램 엑셀 등록 팝업 화면 호출 */
    @RequestMapping("/popup/program_xls.do")
    public String programxls() {

    	return  "/commons/sys/program/popup/program_xls";
    }



    /** 프로그램 저장 - 단건 */
    @RequestMapping("/ajaxgrid/saveProgram.do")
    @ResponseBody
    public JqGridVO<ProgrmManageVO> saveProgram(ProgrmManageVO saveVO, String saction) {

    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);

    	int result = progrmManageService.saveProgram(saveVO, saction);

    	return new JqGridVO<ProgrmManageVO>(saveVO, result, "", saction);
    }

    /** 프로그램 리스트 저장 -  결과는 IBSResult Json 리턴 */
    @RequestMapping("/regProgramlist.do")
    @ResponseBody
    public IBSResultVO<ProgrmManageVO> saveProgramList(@RequestBody ProgrmManageVOs data, Locale locale) {

    	logger.debug("{}", data);
		ArrayList<ProgrmManageVO> list = data.get("data");

    	int result = progrmManageService.regProgramList(list);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

    	return new IBSResultVO<ProgrmManageVO>(result, resmsg, action);
    }

    /** 프로그램 저장 단건 -  결과는 IBSResult Json 리턴 */
    @RequestMapping("/saveProgramRow.do")
    @ResponseBody
    public IBSResultVO<ProgrmManageVO> saveProgramRow(ProgrmManageVO saveVO, String saction, Locale locale) {

    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);

    	int result = progrmManageService.saveProgram(saveVO, saction);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<ProgrmManageVO>(saveVO, result, resmsg, action);
    }

    /** 프로그램 삭제 - IBSheet 용... */
    @RequestMapping("/deleteProgramList.do")
    @ResponseBody
    public IBSResultVO<ProgrmManageVO> deleteProgramList(ProgrmManageVO saveVO, Locale locale) {

    	logger.debug("saveVO:{}", saveVO);

    	int result = progrmManageService.delProgram(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.DEL.getAction();

    	return new IBSResultVO<ProgrmManageVO>(result, resmsg, action);
    }

    /** 프로그램 삭제 */
    @RequestMapping("/ajaxgrid/deleteProgram.do")
    @ResponseBody
    public JqGridVO<ProgrmManageVO> deleteProgram(ProgrmManageVO saveVO) {

    	logger.debug("saveVO:{}", saveVO);

    	int result = progrmManageService.delProgram(saveVO);

    	if(result > 0) {
			result = 0;
		} else {
			result = -1;
		}

    	return new JqGridVO<ProgrmManageVO>(null, result, "", "D");
    }

    /** 프로그램 조회 팝업 호출 */
    @RequestMapping("/ajaxgrid/programPop.do")
    public String programPop() {

    	return "/commons/sys/program/programPop";
    }

    /** 프로그램 조회 팝업 호출 */
    @RequestMapping("/pop/programPop.do")
    public String programListPop() {

    	return "/commons/sys/program/programPop";
    }


}
