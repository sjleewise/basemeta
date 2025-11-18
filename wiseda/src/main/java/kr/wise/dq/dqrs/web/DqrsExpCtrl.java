package kr.wise.dq.dqrs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.dqrs.service.DqrsExpCol;
import kr.wise.dq.dqrs.service.DqrsExpService;
import kr.wise.dq.dqrs.service.DqrsExpTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DqrsExpCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqrsExpService dqrsExpService;
	
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
	
	static class DqrsExpTbls extends HashMap<String, ArrayList<DqrsExpTbl>> { }
	static class DqrsExpCols extends HashMap<String, ArrayList<DqrsExpCol>> { }

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		//진단대상(DB_CONN_TRG_ID) 정보 (Grantor)
		List<CodeListVo> connTrgDbms2   = codeListService.getCodeList("connTrgDbms"); 
		codeMap.put("ConnTrgDbmsibs2", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms2)));
		codeMap.put("ConnTrgDbms2", connTrgDbms2);
		
		Map<String, ComboIBSVo> connTrgSchIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("ConnTrgSchibs2",  UtilJson.convertJsonString(connTrgSchIbs));
		
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		return codeMap;
	}
	
	//////////////진단 제외 테이블
	
	@RequestMapping("/dq/dqrs/dqrs_exp_tbl.do")
	public String goDqrsExpTbl() {
		return "/dq/dqrs/dqrs_exp_tbl";
	}

	@RequestMapping("/dq/dqrs/getTrgTbl.do")
	@ResponseBody
	public IBSheetListVO<DqrsExpTbl> getExpTbl(DqrsExpTbl vo, ModelMap model) { 
		 List<DqrsExpTbl> list = dqrsExpService.getExpTbl(vo);
						
		return new IBSheetListVO<DqrsExpTbl>(list, list.size()); 
	}
	
	
	@RequestMapping("/dq/dqrs/regTrgTbl.do")
	@ResponseBody
	public IBSResultVO<DqrsExpTbl> regExpTblLst(@RequestBody DqrsExpTbls data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<DqrsExpTbl> list = data.get("data");
		
		int result = dqrsExpService.regExpTblLst(list);  
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<DqrsExpTbl>(result, resmsg, action);

	}
	
	@RequestMapping("/dq/dqrs/popup/trgtbl_xls.do")
    public String goTrgTblxls(@ModelAttribute DqrsExpTbl search) {

    	return "/dq/dqrs/popup/trgtbl_xls"; 
    }
	
	//////////////진단 제외 컬럼
	@RequestMapping("/dq/dqrs/dqrs_exp_col.do")
	public String goExpCol(ModelMap model) throws Exception{

    	return "/dq/dqrs/dqrs_exp_col";
	}
	
	@RequestMapping("/dq/dqrs/getExpCol.do")
	@ResponseBody
	public IBSheetListVO<DqrsExpCol> getExpCol(@ModelAttribute DqrsExpCol search, Locale locale) {
		List<DqrsExpCol> list = dqrsExpService.getExpCol(search);
		
		return new IBSheetListVO<DqrsExpCol>(list, list.size());
	}
	
	@RequestMapping("/dq/dqrs/regExpCol.do")
	@ResponseBody
	public IBSResultVO<DqrsExpCol> regExpCol(@RequestBody DqrsExpCols data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<DqrsExpCol> list = data.get("data");
		
		int result = dqrsExpService.regExpColLst(list);  
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<DqrsExpCol>(result, resmsg, action);

	}
	
}
