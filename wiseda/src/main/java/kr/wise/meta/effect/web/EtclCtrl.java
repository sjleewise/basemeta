/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : EtclCtrl
 * 2. Package : kr.wise.meta.effect.web
 * 3. Comment : ETCL관련 작업
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 4. 오후 2:44:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열   : 2014. 7. 4. :            : 신규 개발.
 */
package kr.wise.meta.effect.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.effect.service.EtclService;
import kr.wise.meta.effect.service.EtclVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : EtclCtrl.java
 * 3. Package  : kr.wise.meta.effect.web
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 4. 오후 2:44:00
 * </PRE>
 */
@Controller
public class EtclCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;

	@Inject
	private EtclService etclService;

	private Map<String, Object> codeMap;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//ETCL작업유형코드
		String etclTskTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("ETCL_TSK_TYP_CD"));
		codeMap.put("etclTskTypCd", etclTskTypCd);
		
		
		return codeMap;
	}
	
	
	/** ETCL 작업 조회 View @return 유성열 */
	@RequestMapping("/meta/effect/etcltask_lst.do")
	public String goetcltask_lstform() {
		
		return "/meta/effect/etcltask_lst";
	}


	/** ETCL 작업 - 기본 조회 - IBSheet-JSON @return 유성열 */
	@RequestMapping("/meta/effect/getEtclTaskList.do")
	@ResponseBody
	public IBSheetListVO<EtclVO> getEtclTaskList(EtclVO search) {
		logger.debug("ETCL작업정보 : {}", search);
		List<EtclVO> list = etclService.selectEtclTaskList(search);

		return new IBSheetListVO<EtclVO>(list, list.size());
	}
	
	/** ETCL 작업 - 소스정보 조회 - IBSheet-JSON @return 유성열 */
	@RequestMapping("/meta/effect/getEtclTaskSource.do")
	@ResponseBody
	public IBSheetListVO<EtclVO> getEtclTaskSource(EtclVO search) {
		logger.debug("ETCL소스정보 : {}", search);
		List<EtclVO> list = etclService.selectEtclTaskSource(search);

		return new IBSheetListVO<EtclVO>(list, list.size());
	}
	
	/** ETCL 작업 - 타겟정보 조회 - IBSheet-JSON @return 유성열 */
	@RequestMapping("/meta/effect/getEtclTaskTarget.do")
	@ResponseBody
	public IBSheetListVO<EtclVO> getEtclTaskTarget(EtclVO search) {
		logger.debug("ETCL타겟정보 : {}", search);
		List<EtclVO> list = etclService.selectEtclTaskTarget(search);

		return new IBSheetListVO<EtclVO>(list, list.size());
	}

}
