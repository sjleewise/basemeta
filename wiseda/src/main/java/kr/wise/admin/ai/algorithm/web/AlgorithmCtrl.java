/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : AlgorithmCtrl.java
 * 2. Package : kr.wise.admin.ai.algorithm.web
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 23. 오후 3:33:44
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 23. :            : 신규 개발.
 */
package kr.wise.admin.ai.algorithm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.admin.ai.algorithm.service.AlgorithmService;
import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AlgorithmCtrl.java
 * 3. Package  : kr.wise.admin.ai.algorithm.web
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 23. 오후 3:33:44
 * </PRE>
 */
@Controller
public class AlgorithmCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private AlgorithmService algorithmService;
	
	static class WaaAlgs extends HashMap<String, ArrayList<WaaAlg>> { }
	static class WaaAlgArgs extends HashMap<String, ArrayList<WaaAlgArg>> { }
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@RequestMapping("/admin/ai/algorithm/getalgorithmlist.do")
	@ResponseBody
	public IBSheetListVO<WaaAlg> getAlgorithm(WaaAlg search, Locale locale) {
		logger.debug("알고리즘 목록 조회 :{}", search);
		
		List<WaaAlg> list = algorithmService.getAlgorithmList(search);
		
		return new IBSheetListVO<WaaAlg>(list, list.size());
	}

	@RequestMapping("/admin/ai/algorithm/getalgorithmparam.do")
	@ResponseBody
	public IBSheetListVO<WaaAlgArg> getAlgoritymParam(WaaAlg search, Locale locale) {
		logger.debug("알고리즘 파라미터 목록 조회 by 알고리즘ID :{}", search.getAlgId());
		
		List<WaaAlgArg> list = algorithmService.getAlgorithmParam(search);
		
		return new IBSheetListVO<WaaAlgArg>(list, list.size());
	}
	
	@RequestMapping("/admin/ai/algorithm/regalgorithmlist.do")
	@ResponseBody
	public IBSResultVO<CommonVo> regAlgorithm(@RequestBody WaaAlgs data, Locale locale) throws Exception {
		logger.debug("알고리즘 저장");
		
		List<WaaAlg> list = data.get("data");
		
		int result = algorithmService.regAlgorithm(list);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
//			resmsg = "도메인판별을 완료했습니다.";
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
//			resmsg = "도메인판별 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();
		
		return new IBSResultVO<CommonVo>(result, resmsg, action);
	}

	@RequestMapping("/admin/ai/algorithm/regalgorithmparam.do")
	@ResponseBody
	public IBSResultVO<WaaAlg> regAlgorithmParam(@RequestBody WaaAlgArgs data, WaaAlg algvo, Locale locale) throws Exception {
		logger.debug("알고리즘 파라미터 저장:{}", algvo.getAlgId());
		
		List<WaaAlgArg> list = data.get("data");
		
		int result = algorithmService.regAlgorithmParam(algvo, list);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaaAlg>(algvo, result, resmsg, action);
	}

	@RequestMapping("/admin/ai/algorithm/delalgorithm.do")
	@ResponseBody
	public IBSResultVO<CommonVo> delAlgorithm(@RequestBody WaaAlgs data, Locale locale) throws Exception {
		logger.debug("알고리즘 삭제");
		
		List<WaaAlg> list = data.get("data");
		
		int result = algorithmService.delAlgorithm(list);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		
		return new IBSResultVO<CommonVo>(result, resmsg, action);
	}
	
	@RequestMapping("/admin/ai/algorithm/delalgorithmparam.do")
	@ResponseBody
	public IBSResultVO<WaaAlg> delAlgorithmParam(@RequestBody WaaAlgArgs data, WaaAlg algvo, Locale locale) throws Exception {
		logger.debug("알고리즘 파라미터 삭제 by 알고리즘ID:{}", algvo.getAlgId());
		
		List<WaaAlgArg> list = data.get("data");
		
		int result = algorithmService.delAlgorithmParam(algvo, list);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaaAlg>(result, resmsg, action);
	}
}
