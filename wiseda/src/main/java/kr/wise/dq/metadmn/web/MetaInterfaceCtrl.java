/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileCtrl.java
 * 2. Package : kr.wise.dq.criinfo.profile.web
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:29:21
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.metadmn.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.metadmn.service.MetaDmnItfVO;
import kr.wise.dq.metadmn.service.MetaInterfaceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MetaInterfaceCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MetaInterfaceService metaItfService;
	

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	/*@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		codeMap = new HashMap<String, Object>();
		return codeMap;
	}*/
	
	/** 메타도메인조회 */
	@RequestMapping("/dq/profile/popup/metadmn_pop.do")
	public String goMetaDmnPage(@ModelAttribute MetaDmnItfVO search ,ModelMap model){
		logger.debug("search : "+search);
		search = metaItfService.getMetaDmnDtl(search);
		model.addAttribute("metaDmnVO", search);
    	return "/dq/profile/popup/metadmn_pop";
	}
	
	/** 메타도메인유효값조회 */
	@RequestMapping("/dq/profile/popup/metaCdDmnLst.do")
	@ResponseBody
	public IBSheetListVO<MetaDmnCdValItfVO> getMetaDmnCdValLst(String dmnId, ModelMap model, Locale locale){
		logger.debug("dmnId  :  "+ dmnId);
		List<MetaDmnCdValItfVO> list = metaItfService.getMetaDmnCdValLst(dmnId);
		return new IBSheetListVO<MetaDmnCdValItfVO>(list, list.size());
	}
	
}
