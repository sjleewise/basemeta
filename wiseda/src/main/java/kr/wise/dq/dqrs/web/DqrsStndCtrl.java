package kr.wise.dq.dqrs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.dqrs.service.DqrsCodeService;
import kr.wise.dq.dqrs.service.DqrsDmn;
import kr.wise.dq.dqrs.service.DqrsResult;
import kr.wise.dq.dqrs.service.DqrsSditm;
import kr.wise.dq.dqrs.service.DqrsStndService;

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

@Controller
public class DqrsStndCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqrsStndService dqrsStndService;
	
	@Inject
	private DqrsCodeService dqrsCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	private Map<String, Object> codeMap;
	
	static class DqrsSditms extends HashMap<String, ArrayList<DqrsSditm>> {}
	static class DqrsDmns extends HashMap<String, ArrayList<DqrsDmn>> {}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId"));
		codeMap.put("devConnTrgSch", devConnTrgSch);
		//진단대상(DB_CONN_TRG_ID) 정보 (Grantor) - 데이터권한관리
		List<CodeListVo> connTrgDbms2   = codeListService.getCodeList("connTrgDbms"); 
		codeMap.put("ConnTrgDbmsibs2", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms2)));
		codeMap.put("ConnTrgDbms2", connTrgDbms2);
		//스키마 Double Select(IBSheet) (Grantor) - 데이터권한
		Map<String, ComboIBSVo> connTrgSchIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("ConnTrgSchibs2",  UtilJson.convertJsonString(connTrgSchIbs));
		
		//코드 리스트 조회 쿼리(기존 CodeList에 추가 하지 않고 신규 모듈에 추가(기존 소스 영향 없게 하기 위함.))
		//공통 표준분류 리스트
		String pubSditmConnTrgId   = UtilJson.convertJsonString(dqrsCodeService.getPubSditmConnTrgId());
		codeMap.put("pubSditmConnTrgId", pubSditmConnTrgId);
		String pubDmnConnTrgId   = UtilJson.convertJsonString(dqrsCodeService.getPubDmnConnTrgId());
		codeMap.put("pubDmnConnTrgId", pubDmnConnTrgId);
		//검증표준+모델 관리 > 일괄삭제 DBMS정보
		String allDbms   = UtilJson.convertJsonString(dqrsCodeService.getAllDbms());
		codeMap.put("allDbms", allDbms);
		//검증표준+모델 관리 > 일괄삭제 DBMS정보
		String allDbmsSchId   = UtilJson.convertJsonString(dqrsCodeService.getAllDbmsSchId());
		codeMap.put("allDbmsSchId",allDbmsSchId);
		
		return codeMap;
	}
	
	///////표준 화면
	@RequestMapping("/dq/dqrs/dqrs_stnd.do")
	public String goDqrsStnd(Model model) throws Exception {
		model.addAttribute("codeMap", getcodeMap());
		
		return "/dq/dqrs/dqrs_stnd";
	}
	
	///////////////용어관리
	@RequestMapping("/dq/dqrs/getDqrsSditmLst.do")
	@ResponseBody
	public IBSheetListVO<DqrsSditm> getdiagSditmList(@ModelAttribute DqrsSditm data, Locale locale) {
		logger.debug("data:{}", data);
		logger.debug("data:{}", data.getDbSchId());
		
		List<DqrsSditm> list = dqrsStndService.getDqrsSditmLst(data);

		return new IBSheetListVO<DqrsSditm>(list, list.size());
	}
	
	/**
	 * 진단 표준용어 저장 및 검증 
	 */
	
	@RequestMapping("/dq/dqrs/saveDqrsSditmLst.do")
	@ResponseBody
	public IBSResultVO<DqrsSditm> saveDqrsSditmLst(@RequestBody DqrsSditms data, HttpSession session, Locale locale) throws Exception {
		logger.debug("data:{}", data);

		ArrayList<DqrsSditm> list = data.get("data");

		int result = dqrsStndService.saveDqrsSditmLst(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG.getAction();

		return new IBSResultVO<DqrsSditm>(result, resmsg, action);
	}
	
	/** 진단 표준용어 삭제
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("/dq/dqrs/delDqrsSditmLst.do")
	@ResponseBody
	public IBSResultVO<DqrsSditm> delDqrsSditmLst(WaqMstr reqmst, @RequestBody DqrsSditms data, Locale locale) throws Exception {

		logger.debug("data:{}", data);

		ArrayList<DqrsSditm> list = data.get("data");

		int result = dqrsStndService.delDqrsSditmLst(list);

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

		return new IBSResultVO<DqrsSditm>(result, resmsg, action);
	}
	
	/////////////////도메인 관리
	/** 도메인 리스트 조회 */
	@RequestMapping("/dq/dqrs/getDqrsDmnLst.do")
	@ResponseBody
	public IBSheetListVO<DqrsDmn> getDqrsDmnLst(@ModelAttribute DqrsDmn data, Locale locale) {

		List<DqrsDmn> list = dqrsStndService.getDqrsDmnLst(data);

		return new IBSheetListVO<DqrsDmn>(list, list.size());

	}
	
	@RequestMapping("/dq/dqrs/saveDqrsDmnLst.do")
	@ResponseBody
	public IBSResultVO<DqrsDmn> saveDqrsDmnLst(@RequestBody DqrsDmns data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<DqrsDmn> list = data.get("data");
	
		int result = dqrsStndService.saveDqrsDmnLst(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);

		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<DqrsDmn>(result, resmsg, action);
	}
	
	@RequestMapping("/dq/dqrs/delDqrsDmnLst.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delDqrsDmnLst(@RequestBody DqrsDmns data, WaqMstr reqmst,  Locale locale) throws Exception {

		//logger.debug("reqmst:{}\ndata:{}", reqmst, data);
	
		ArrayList<DqrsDmn> list = data.get("data");

		int result = dqrsStndService.delDqrsDmnLst(list);
		
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

		return new IBSResultVO<WaqMstr>(  reqmst,result, resmsg, action);
		
	}
	
	
	///////////////공통표준 용어관리
	/** 진단 표준용어 조회 -IBSheet json */
	@RequestMapping("/dq/dqrs/getDqrsPubSditmLst.do")
	@ResponseBody
	public IBSheetListVO<DqrsSditm> getDqrsPubSditmLst(@ModelAttribute DqrsSditm data, Locale locale) {
		logger.debug("data:{}", data);
		
		List<DqrsSditm> list = dqrsStndService.getDqrsPubSditmLst(data);

		return new IBSheetListVO<DqrsSditm>(list, list.size());
	}
	
	////////////공통표준 도메인
	@RequestMapping("/dq/dqrs/getDqrsPubDmnLst.do")
	@ResponseBody
	public IBSheetListVO<DqrsDmn> getDqrsPubDmnLst(@ModelAttribute DqrsDmn data, Locale locale) {

		List<DqrsDmn> list = dqrsStndService.getDqrsPubDmnLst(data);

		return new IBSheetListVO<DqrsDmn>(list, list.size());

	}
	
	
	
	/** 검증표준관리 일괄삭제
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("/dq/dqrs/batchDeleteDiagSditmList.do")
	@ResponseBody
	public IBSResultVO<DqrsSditm> batchDeleteDqrsSditmLst( @RequestBody  HashMap<String,Object>  data,Locale locale) throws Exception {
		
		String allDbms      = (String) data.get("allDbms");
		String allDbmsSchId = (String) data.get("allDbmsSchId");
		String delType      = (String) data.get("delType");
		
		int result = dqrsStndService.batchDeleteDqrsSditmLst(allDbms,allDbmsSchId,delType);
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


		return new IBSResultVO<DqrsSditm>(result, resmsg, action);
	}
	
	
	
	
	/////////////표준준수 분석
	@RequestMapping("/dq/dqrs/dqrs_stnd_gap.do")    
	public String goDqrsStndColGapLst() {
		return "/dq/dqrs/dqrs_stnd_gap";  
	}
	
	@RequestMapping("/dq/dqrs/getDqrsStndColGapLst.do")
	@ResponseBody
	public IBSheetListVO<DqrsResult> getDqrsStndColGapLst(@ModelAttribute DqrsResult search) {
		logger.debug("search:{}", search);
		List<DqrsResult> list = dqrsStndService.getDqrsStndColGapLst(search); 
		
		return new IBSheetListVO<DqrsResult>(list, list.size());
	}
	
	
	
	
	
}
