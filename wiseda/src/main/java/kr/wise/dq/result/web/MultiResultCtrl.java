package kr.wise.dq.result.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.handler.PoiHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.portal.dashboard.service.TotalDashService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ResultCtrl.java
 * 3. Package  : kr.wise.dq.result.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 11. 오전 9:48:36
 * </PRE>
 */ 
@Controller
public class MultiResultCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private TotalDashService totalDashService;
	
	@Inject
	private MessageSource message;

	@Inject
	private ResultService resultService;
	
	/** 프로파일 품질추이 폼 */
	@RequestMapping("/dq/result/multi_result_lst.do")
	public String formPage() throws Exception {

		return "/dq/result/multi_result_lst";
	}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
//		codeMap.put("regTypCdibs", regTypCd);
//		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
//		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
		
		//프로파일 종류코드
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
		
		//진단대상
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsCd", connTrgDbms);		
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		
		//스키마
		List<CodeListVo> schDbSchNm = codeListService.getCodeList(CodeListAction.connTrgSch);
		
		codeMap.put("schDbSchNm", schDbSchNm);
		codeMap.put("schDbSchNmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(schDbSchNm)));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
				
		return codeMap;
	}
	
}
