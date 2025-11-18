/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : OlapCtrl
 * 2. Package : kr.wise.meta.effect.web
 * 3. Comment : OLAP 관련 작업
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 8. 오후 1:40:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열   : 2014. 7. 8. :            : 신규 개발.
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
import kr.wise.meta.effect.service.OlapService;
import kr.wise.meta.effect.service.OlapVO;

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
 * 2. FileName  : OlapCtrl.java
 * 3. Package  : kr.wise.meta.effect.web
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 8. 오후 1:40:00
 * </PRE>
 */
@Controller
public class OlapCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;

	@Inject
	private OlapService olapService;

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
	
	
	/** OLAP 보고서 조회 View @return 유성열 */
	@RequestMapping("/meta/effect/olapreport_lst.do")
	public String goolapreport_lstform() {
		
		return "/meta/effect/olapreport_lst";
	}


	/** OLAP - 보고서 기본 조회 - IBSheet-JSON @return 유성열 */
	@RequestMapping("/meta/effect/getOlapReportList.do")
	@ResponseBody
	public IBSheetListVO<OlapVO> getOlapReportList(OlapVO search) {
		logger.debug("ETCL작업정보 : {}", search);
		List<OlapVO> list = olapService.selectOlapReportList(search);

		return new IBSheetListVO<OlapVO>(list, list.size());
	}
	
	/** OLAP - 보고서 목록 조회 - IBSheet-JSON @return 유성열 */
	@RequestMapping("/meta/effect/getOlapReportDetailList.do")
	@ResponseBody
	public IBSheetListVO<OlapVO> getOlapReportDetailList(OlapVO search) {
		logger.debug("ETCL소스정보 : {}", search);
		List<OlapVO> list = olapService.selectOlapReportDetailList(search);

		return new IBSheetListVO<OlapVO>(list, list.size());
	}


}
