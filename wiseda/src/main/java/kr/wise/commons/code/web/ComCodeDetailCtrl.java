package kr.wise.commons.code.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComCodeDetailService;
import kr.wise.commons.code.service.ComCodeDetailVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.helper.grid.JqGridPageVO;
import kr.wise.commons.helper.grid.JqGridVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ComCodeDetailCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ComCodeDetailService comCodeDetailService;
	
	@Inject
	private CodeListService codeListService;
	
	private Map<String, Object> codeMap = new HashMap<String, Object>();
	
	/** 공통상세코드 코드리스트 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		
		//코드분류 리스트 조회
		codeMap.put("comCodeList", codeListService.getCodeList("COM_CODE"));
		
		return codeMap;
	}
	
	/** 공통상세코드 리스트 폼 */
	@RequestMapping("/commons/code/selectComDetailCodeList.do")
	public String selectCommonCodeList() throws Exception{
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	
    	return "/commons/code/comDetailCodeList";
		
	}
	
	
    /** 공통상세코드 리스트 조회 - jqgrid */
    @RequestMapping("/commons/code/ajaxgrid/selectComDetailCodeList.do")
    @ResponseBody
    public JqGridListVO<ComCodeDetailVO> selectComCodeList(@ModelAttribute("searchVO") ComCodeDetailVO searchVO, JqGridPageVO pageVO)     throws Exception { 
    	
//    	List list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
    	List<ComCodeDetailVO> resultlist = comCodeDetailService.selectComCodeDetailList(searchVO);
    	
    	return new JqGridListVO<ComCodeDetailVO>(resultlist); 
    }
    
    /** 공통상세코드 상세정보 조회 */
    @RequestMapping("/commons/code/ajaxgrid/selectComDetailCodeInfo.do")
    public String selectComCodeDetail(ComCodeDetailVO searchVO, String saction,  ModelMap model) {
    	
    	log.debug("searvhVO : {}", searchVO);
    	
    	model.addAttribute("saction", "I");
    	
    	if( StringUtils.hasLength(searchVO.getCodeId()) ){
    		
    		ComCodeDetailVO resultVO = comCodeDetailService.selectComCodeDetailInfo(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}
    	
    	return "/commons/code/comDetailCodeInfo";
    }
	
    /** 공통상세코드 저장 - ajax 이용 */
    @RequestMapping("/commons/code/ajaxgrid/saveComDetailCode.do")
    @ResponseBody
    public JqGridVO<ComCodeDetailVO> saveComCode(ComCodeDetailVO saveVO, String saction) {
    	
    	log.debug("saveVO:{}, saction:{}", saveVO, saction);
    	
    	LoginVO user=  (LoginVO) UserDetailHelper.getAuthenticatedUser();
    	
    	saveVO.setFrstRegisterId(user.getId());
    	saveVO.setLastUpdusrId(user.getId());
    	List<CodeListVo> codelist = (List<CodeListVo>) codeMap.get("comCodeList");
    	for (CodeListVo vo : codelist) {
			if(saveVO.getCodeId().equals(vo.getCodeCd())) {
				saveVO.setCodeIdNm(vo.getCodeLnm());
			}
		}
    	
    	int result = comCodeDetailService.saveComCodeDetail(saveVO, saction);
    	
    	return new JqGridVO<ComCodeDetailVO>(saveVO, result, "", saction);
    }
    
    
    /** 공통상세코드 삭제-ajajx */
    @RequestMapping("/commons/code/ajaxgrid/deleteComDetailCode.do")
    @ResponseBody
    public JqGridVO<ComCodeDetailVO> deleteComCode(ComCodeDetailVO deleteVO) {
    	log.debug("deleteVO:{}", deleteVO);
    	
    	int result = comCodeDetailService.deleteComCodeDetail(deleteVO);
    	
    	return new JqGridVO<ComCodeDetailVO>(null, result, "", "D");
    }
}
