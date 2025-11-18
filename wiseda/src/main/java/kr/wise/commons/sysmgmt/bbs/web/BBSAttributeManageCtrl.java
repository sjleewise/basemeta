package kr.wise.commons.sysmgmt.bbs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BoardMasterVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.helper.grid.JqGridVO;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/commons/sys/bbs")
public class BBSAttributeManageCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private BBSAttributeManageService bbsattributemanageservice;

	@Inject
	private MessageSource message;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codelistService;

	private Map<String, Object> codeMap;

	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class BoardMasterVOs extends HashMap<String, ArrayList<BoardMasterVO>> { }

  /**
   * <PRE>
   * 1. MethodName : bbsAttrManage
   * 2. ClassName  : BBSManagerCtrl
   * 3. Comment   : 게시판 관리화면
   * 4. 작성자    : 최결
   * 5. 작성일    : 2014. 03. 13. 오후 5:06:05
   * </PRE>
   *   @return String
   *   @param searchVO
   *   @param model
   *   @return
   *   @throws Exception
   */
  @RequestMapping("/BoardMstrList.do")
  public String selectBbs( @ModelAttribute("BoardMasterVO") BoardMasterVO BoardMasterVO,	ModelMap model)     throws Exception {
      // 0. Spring Security 사용자권한 처리
  	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
  	if(!isAuthenticated) {
  	}
    	return "/commons/sys/bbs/BoardMstrList";

  }

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String usergrouplnm2 = UtilJson.convertJsonString(codelistService.getCodeList("userglnm"));
//		String usergrouplnm1 = UtilJson.convertJsonString(codelistService.getCodeListIBS("userglnm"));
		String usergTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("USERG_TYP_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String bbsTy = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("COM004"));
		String bbsTmplt = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("COM005"));
		String bbsAttr = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("COM009"));

		List<CodeListVo> catecode = codelistService.getCodeList("cateCode");
		String bbscate =  UtilJson.convertJsonString(codelistService.getCodeListIBS(catecode));

//		codeMap.put("userglnm", usergrouplnm2);
//		codeMap.put("userglnmibs", usergrouplnm1);
		//공통코드 - IBSheet Combo Code용
		codeMap.put("usergTypCdibs", usergTypCd);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("bbsTyC", bbsTy);
		codeMap.put("bbsTmpltt", bbsTmplt);
		codeMap.put("bbsAttrib", bbsAttr);
		codeMap.put("bbscateCode", bbscate);

		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));
		codeMap.put("bbsTy", cmcdCodeService.getCodeList("COM004"));
		codeMap.put("bbsTmplT", cmcdCodeService.getCodeList("COM005"));
		codeMap.put("bbsAttr", cmcdCodeService.getCodeList("COM009"));
		codeMap.put("bbscateCod", catecode);
		codeMap.put("bbsmenuCode", cmcdCodeService.getCodeList("MENU_DCD"));

		return codeMap;
	}

	/** 게시판 리스트 조회-IBSheet Json
	 * @throws Exception */

	@RequestMapping("/selectAllBoardMstrList.do")
	@ResponseBody
	public IBSheetListVO<BoardMasterVO> selectAllBoardMstrList( BoardMasterVO vo) throws Exception {
		log.debug("loginLog : {}", vo);
		List<BoardMasterVO> resultlist = bbsattributemanageservice.selectAllBoardMstrList(vo);


		return new IBSheetListVO<BoardMasterVO>(resultlist, resultlist.size());
	}


	 /** 게시판 상세 정보
	 * @throws Exception */
    @RequestMapping("/ajaxgrid/selectBBSMasterInf.do")
    //@ResponseBody
    public String selectBbsDetail(BoardMasterVO searchVO, String saction,  ModelMap model) throws Exception {
    	log.debug("searvhVO : {}", searchVO);

    	model.addAttribute("saction", "I");

    	if( StringUtils.hasLength(searchVO.getBbsId()) ){
    		BoardMasterVO resultVO = bbsattributemanageservice.selectBBSMasterInf(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}

    	return "/commons/sys/bbs/BoardMstr_dtl";
    }

    /** 프로그램 저장 단건 -  결과는 IBSResult Json 리턴
     * @throws Exception */
    @RequestMapping("/ajaxgrid/insertBBSMastetInf.do")
    @ResponseBody
    public IBSResultVO<BoardMasterVO> saveBbsRow(BoardMasterVO saveVO, String saction, Locale locale) throws Exception {

    	log.debug("saveVO:{}, saction:{}", saveVO, saction);

    	int result = bbsattributemanageservice.saveBBS(saveVO, saction);
//    	int result = 0;

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

    	return new IBSResultVO<BoardMasterVO>(saveVO, result, resmsg, action);
    }

    /** 프로그램 엑셀 등록 팝업 화면 호출 */
    @RequestMapping("/popup/bbs_xls.do")
    public String bbsxls() {

    	return  "/commons/sys/bbs/popup/bbs_xls";
    }

    /** 프로그램 리스트 저장 -  결과는 IBSResult Json 리턴
     * @throws Exception */
    @RequestMapping("/regBbslist.do")
    @ResponseBody
    public IBSResultVO<BoardMasterVO> saveBbsList(@RequestBody BoardMasterVOs data, Locale locale) throws Exception  {

		ArrayList<BoardMasterVO> list = data.get("data");

    	int result = bbsattributemanageservice.regBbsList(list);

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

    	return new IBSResultVO<BoardMasterVO>(result, resmsg, action);
    }

    /** 게시판 삭제 - IBSheet 용...
     * @throws Exception */
    @RequestMapping("/deleteBBSMasterInf.do")
    @ResponseBody
    public IBSResultVO<BoardMasterVO> delBbsRow(BoardMasterVO delVO, Locale locale) throws Exception {

    	log.debug("delVO:{}", delVO);
    	log.debug("delVO Get ID:{}", delVO.getBbsId());

    	int result = bbsattributemanageservice.delBBS(delVO);

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

    	return new IBSResultVO<BoardMasterVO>(delVO, result, resmsg, action);
    }

    	 /** 게시판 삭제 */
        @RequestMapping("/ajaxgrid/deleteBBSMasterInf.do")
        @ResponseBody
        public JqGridVO<BoardMasterVO> deleteBbs(BoardMasterVO delVO) {

        	log.debug("delVO:{}", delVO);

        	int result = bbsattributemanageservice.delBBS(delVO);

        	if(result > 0) {
    			result = 0;
    		} else {
    			result = -1;
    		}

        	return new JqGridVO<BoardMasterVO>(null, result, "", "D");
        }




}
