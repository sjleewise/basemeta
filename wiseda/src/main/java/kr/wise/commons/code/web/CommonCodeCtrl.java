package kr.wise.commons.code.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.CodeSyncService;
import kr.wise.commons.code.service.ComCodeService;
import kr.wise.commons.code.service.Comtccmmncode;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.helper.grid.JqGridPageVO;
import kr.wise.commons.helper.grid.JqGridVO;
import kr.wise.sysinf.messageSync.service.MessageSyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonCodeCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ComCodeService comCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CodeSyncService codeSyncServiceTest;
	
	@Inject
	private MessageSyncService messageSyncServiceTest;
	
	private Map<String, Object> codeMap = new HashMap<String, Object>();
	
	/** 공통코드 코드리스트 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
		//코드분류 리스트 조회
		codeMap.put("clCodeList", codeListService.getCodeList("CL_CODE"));
		
		return codeMap;
	}
	
	/** 공통코드 리스트 폼 */
	@RequestMapping("/commons/code/selectComCodeList.do")
	public String selectCommonCodeList() throws Exception{
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	
    	return "/commons/code/comCodeList";
		
	}
	
	
    /** 공통코드 리스트 조회 - jqgrid */
    @RequestMapping("/commons/code/ajaxgrid/selectComCodeList.do")
    @ResponseBody
    public JqGridListVO<Comtccmmncode> selectComCodeList(@ModelAttribute("searchVO") SearchVO searchVO, JqGridPageVO pageVO)     throws Exception { 
    	
//    	List list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
    	List<Comtccmmncode> resultlist = comCodeService.selectComCodeList(searchVO);
    	
    	return new JqGridListVO<Comtccmmncode>(resultlist); 
    }
    
    /** 공통코드 상세정보 조회 */
    @RequestMapping("/commons/code/ajaxgrid/selectComCodeDetail.do")
    public String selectComCodeDetail(Comtccmmncode searchVO, String saction,  ModelMap model) {
    	
    	log.debug("searvhVO : {}", searchVO);
    	
    	model.addAttribute("saction", "I");
    	
    	if( StringUtils.hasLength(searchVO.getCodeId()) ){
    		
    		Comtccmmncode resultVO = comCodeService.selectComCodeDetail(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}
    	
    	return "/commons/code/comCodeDetail";
    }
	
    /** 공통코드 저장 - ajax 이용 */
    @RequestMapping("/commons/code/ajaxgrid/saveComCode.do")
    @ResponseBody
    public JqGridVO<Comtccmmncode> saveComCode(Comtccmmncode saveVO, String saction) {
    	
    	log.debug("saveVO:{}, saction:{}", saveVO, saction);
    	
    	LoginVO user=  (LoginVO) UserDetailHelper.getAuthenticatedUser();
    	
    	saveVO.setFrstRegisterId(user.getId());
    	saveVO.setLastUpdusrId(user.getId());
    	List<CodeListVo> codelist = (List<CodeListVo>) codeMap.get("clCodeList");
    	for (CodeListVo vo : codelist) {
			if(saveVO.getClCode().equals(vo.getCodeCd())) {
				saveVO.setClCodeNm(vo.getCodeLnm());
			}
		}
    	
    	int result = comCodeService.saveComCode(saveVO, saction);
    	
    	return new JqGridVO<Comtccmmncode>(saveVO, result, "", saction);
    }
    
    @RequestMapping("/commons/code/ajaxgrid/deleteComCode.do")
    @ResponseBody
    public JqGridVO<Comtccmmncode> deleteComCode(Comtccmmncode deleteVO) {
    	log.debug("deleteVO:{}", deleteVO);
    	
    	int result = comCodeService.deleteComCode(deleteVO);
    	
    	return new JqGridVO<Comtccmmncode>(null, result, "", "D");
    }
    
    @RequestMapping("/commons/code/codeSync.do")
    @ResponseBody
    public void codeSyncTest() {
    	log.debug("코드싱크 테스트", "");
    	
    	codeSyncServiceTest.syncCode();
    	
    	
    }
      @RequestMapping("/messageSync.do")
    @ResponseBody
    public void messageSyncTest() {
    	log.debug("코드싱크 테스트", "");
    	
    	messageSyncServiceTest.syncMessage();
    	
    	
    }
    
}
